package com.ktmmobile.msf.commons.common.logging.http;

import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ArrayNode;
import tools.jackson.databind.node.ObjectNode;

/**
 * HTTP 요청/응답 로그의 헤더와 바디 민감정보 마스킹 및 값 축약 처리기
 */
public class HttpExchangeLogSanitizer {

    private static final Pattern SIZE_RULE_PATTERN = Pattern.compile("^(.+)\\[(\\d*)(?::(\\d*))?]$");
    private static final Pattern CONTENT_DISPOSITION_NAME_PATTERN = Pattern.compile("name=\"([^\"]+)\"");
    // multipart/form-data part에 filename이 있으면 파일로 보고 본문 로깅 생략
    private static final Pattern CONTENT_DISPOSITION_FILENAME_PATTERN = Pattern.compile("filename=\"([^\"]*)\"");

    private final ObjectMapper objectMapper;
    private final List<HeaderLogRule> loggingHeaderRules;
    private final Set<String> headerNameExcludes;
    private final Set<String> bodyMaskedIncludes;
    private final Set<String> bodyMaskedExcludes;
    private final List<BodyTruncateRule> bodyTruncatedIncludes;
    private final Set<String> bodyTruncatedExcludes;

    /**
     * HTTP 로그 룰 기반 sanitizer 생성
     */
    public HttpExchangeLogSanitizer(
        ObjectMapper objectMapper,
        HttpLogMatchRule headerNames,
        HttpLogMatchRule bodyMaskedFields,
        HttpLogMatchRule bodyTruncatedFields
    ) {
        this.objectMapper = objectMapper;
        this.loggingHeaderRules = toHeaderLogRules(headerNames);
        this.headerNameExcludes = toNormalizedMatchSet(headerNames.mergedExclude());
        this.bodyMaskedIncludes = toNormalizedMatchSet(bodyMaskedFields.mergedInclude());
        this.bodyMaskedExcludes = toNormalizedMatchSet(bodyMaskedFields.mergedExclude());
        this.bodyTruncatedIncludes = toBodyTruncateRules(bodyTruncatedFields.mergedInclude(),
            bodyTruncatedFields.defaultTruncatedSize());
        this.bodyTruncatedExcludes = toNormalizedMatchSet(bodyTruncatedFields.mergedExclude());
    }

    /**
     * 로깅 대상 헤더만 복사하고 설정된 헤더 값 마스킹 적용
     */
    public Map<String, List<String>> copyHeaders(Map<String, List<String>> headers) {
        Map<String, List<String>> copied = new LinkedHashMap<>();
        if (headers == null || headers.isEmpty()) {
            return copied;
        }

        headers.forEach((name, values) -> {
            HeaderLogRule headerLogRule = findHeaderLogRule(name);
            if (headerLogRule != null) {
                copied.put(name, applyLogRule(values, headerLogRule));
            }
        });
        return copied;
    }

    /**
     * Content-Type에 맞는 요청/응답 바디 텍스트 변환 및 민감정보 처리
     */
    public String toText(Map<String, List<String>> headers, byte[] body) {
        if (body == null || body.length == 0) {
            return "";
        }

        String contentType = firstHeaderValue(headers, "Content-Type");
        Charset charset = resolveCharset(contentType);
        String bodyText = new String(body, charset);
        String normalizedContentType = normalizeContentType(contentType);

        if (isJson(normalizedContentType)) {
            return maskJsonBody(bodyText);
        }

        if (isFormUrlEncoded(normalizedContentType)) {
            return maskFormUrlEncodedBody(bodyText, charset);
        }

        if (isMultipartFormData(normalizedContentType)) {
            return maskMultipartFormDataBody(bodyText, contentType);
        }

        return bodyText;
    }

    private HeaderLogRule findHeaderLogRule(String headerName) {
        String normalizedHeaderName = normalizeMatchTarget(headerName);
        if (matchesAny(normalizedHeaderName, headerNameExcludes)) {
            return null;
        }

        return loggingHeaderRules.stream()
            .filter(rule -> normalizedHeaderName.contains(rule.normalizedMatchToken()))
            .findFirst()
            .orElse(null);
    }

    private List<String> applyLogRule(List<String> values, HeaderLogRule headerLogRule) {
        if (values == null) {
            return List.of();
        }

        if (headerLogRule.raw()) {
            return List.copyOf(values);
        }

        return values.stream()
            .map(value -> maskHeaderValue(value, headerLogRule))
            .toList();
    }

    private String maskHeaderValue(String value, HeaderLogRule headerLogRule) {
        if (value == null || value.isEmpty()) {
            return value;
        }

        int valueLength = value.length();
        int prefixLength = Math.min(headerLogRule.prefixLength(), valueLength);
        int suffixStart = Math.max(prefixLength, valueLength - headerLogRule.suffixLength());

        String prefix = value.substring(0, prefixLength);
        String suffix = suffixStart >= valueLength ? "" : value.substring(suffixStart);
        boolean hasMaskedMiddle = suffixStart > prefixLength;

        return prefix
            + (hasMaskedMiddle ? "..." : "")
            + suffix
            + "(length=" + valueLength + ")";
    }

    private String maskJsonBody(String bodyText) {
        if ((bodyMaskedIncludes.isEmpty() && bodyTruncatedIncludes.isEmpty()) || bodyText.isBlank()) {
            return bodyText;
        }

        try {
            // JSON 파싱 성공 시 필드 단위 마스킹/축약 적용
            JsonNode rootNode = objectMapper.readTree(bodyText);
            maskJsonNode(rootNode, "");
            return objectMapper.writeValueAsString(rootNode);
        } catch (JacksonException _) {
            return maskPartialJsonBody(bodyText);
        }
    }

    private String maskPartialJsonBody(String bodyText) {
        StringBuilder sanitizedBody = new StringBuilder(bodyText.length());
        int appendedIndex = 0;
        int searchIndex = 0;
        while (searchIndex < bodyText.length()) {
            PartialJsonSanitizeResult result = sanitizeNextPartialJsonField(bodyText, sanitizedBody, appendedIndex, searchIndex);
            if (result == null) {
                break;
            }

            if (result.changed()) {
                appendedIndex = result.appendedIndex();
            }
            searchIndex = result.searchIndex();
            if (result.stop()) {
                break;
            }
        }

        if (appendedIndex == 0) {
            return bodyText;
        }
        sanitizedBody.append(bodyText, appendedIndex, bodyText.length());
        return sanitizedBody.toString();
    }

    private PartialJsonSanitizeResult sanitizeNextPartialJsonField(
        String bodyText,
        StringBuilder sanitizedBody,
        int appendedIndex,
        int searchIndex
    ) {
        PartialJsonField field = findNextPartialJsonField(bodyText, searchIndex);
        if (field == null) {
            return null;
        }
        if (!field.hasValue()) {
            return PartialJsonSanitizeResult.unchanged(field.nextSearchIndex());
        }

        BodyValueSanitizer valueSanitizer = findBodyValueSanitizer(field.name());
        if (valueSanitizer == null) {
            return PartialJsonSanitizeResult.unchanged(field.valueQuoteIndex() + 1);
        }

        int valueStartIndex = field.valueQuoteIndex() + 1;
        JsonStringEnd stringEnd = findJsonStringEnd(bodyText, valueStartIndex);
        String value = bodyText.substring(valueStartIndex, stringEnd.index());
        sanitizedBody.append(bodyText, appendedIndex, valueStartIndex);
        sanitizedBody.append(sanitizeBodyValue(value, valueSanitizer, stringEnd.complete()));

        if (!stringEnd.complete()) {
            return PartialJsonSanitizeResult.changed(stringEnd.index(), stringEnd.index(), true);
        }

        sanitizedBody.append('"');
        int nextSearchIndex = stringEnd.index() + 1;
        return PartialJsonSanitizeResult.changed(nextSearchIndex, nextSearchIndex, false);
    }

    private PartialJsonField findNextPartialJsonField(String bodyText, int searchIndex) {
        JsonStringEnd fieldStart = findNextJsonStringStart(bodyText, searchIndex);
        if (fieldStart == null) {
            return null;
        }

        JsonStringEnd fieldEnd = findJsonStringEnd(bodyText, fieldStart.index() + 1);
        if (!fieldEnd.complete()) {
            return null;
        }

        int valueQuoteIndex = findJsonStringValueStart(bodyText, fieldEnd.index() + 1);
        if (valueQuoteIndex < 0) {
            return PartialJsonField.withoutValue(fieldEnd.index() + 1);
        }

        String fieldName = bodyText.substring(fieldStart.index() + 1, fieldEnd.index());
        return PartialJsonField.withValue(fieldName, valueQuoteIndex);
    }

    private BodyValueSanitizer findBodyValueSanitizer(String fieldName) {
        if (shouldMaskBodyField(fieldName)) {
            return BodyValueSanitizer.mask();
        }

        BodyTruncateRule bodyTruncateRule = findBodyTruncateRule(fieldName);
        return bodyTruncateRule == null ? null : BodyValueSanitizer.truncate(bodyTruncateRule);
    }

    private String sanitizeBodyValue(String value, BodyValueSanitizer valueSanitizer, boolean complete) {
        return valueSanitizer.masked()
            ? maskBodyValue(value, complete)
            : truncateBodyValue(value, valueSanitizer.bodyTruncateRule(), complete);
    }

    private JsonStringEnd findNextJsonStringStart(String bodyText, int startIndex) {
        for (int index = startIndex; index < bodyText.length(); index++) {
            if (bodyText.charAt(index) == '"' && !isEscapedQuote(bodyText, index)) {
                return new JsonStringEnd(index, true);
            }
        }
        return null;
    }

    private int findJsonStringValueStart(String bodyText, int startIndex) {
        int index = skipWhitespace(bodyText, startIndex);
        if (index >= bodyText.length() || bodyText.charAt(index) != ':') {
            return -1;
        }

        index = skipWhitespace(bodyText, index + 1);
        if (index >= bodyText.length() || bodyText.charAt(index) != '"' || isEscapedQuote(bodyText, index)) {
            return -1;
        }
        return index;
    }

    private JsonStringEnd findJsonStringEnd(String bodyText, int startIndex) {
        boolean escaped = false;
        for (int index = startIndex; index < bodyText.length(); index++) {
            char character = bodyText.charAt(index);
            if (escaped) {
                escaped = false;
                continue;
            }
            if (character == '\\') {
                escaped = true;
                continue;
            }
            if (character == '"') {
                return new JsonStringEnd(index, true);
            }
        }
        return new JsonStringEnd(bodyText.length(), false);
    }

    private int skipWhitespace(String value, int startIndex) {
        int index = startIndex;
        while (index < value.length() && Character.isWhitespace(value.charAt(index))) {
            index++;
        }
        return index;
    }

    private boolean isEscapedQuote(String bodyText, int quoteIndex) {
        int backslashCount = 0;
        for (int index = quoteIndex - 1; index >= 0 && bodyText.charAt(index) == '\\'; index--) {
            backslashCount++;
        }
        return backslashCount % 2 == 1;
    }

    private String maskFormUrlEncodedBody(String bodyText, Charset charset) {
        if ((bodyMaskedIncludes.isEmpty() && bodyTruncatedIncludes.isEmpty()) || bodyText.isBlank()) {
            return bodyText;
        }

        // form-urlencoded는 key/value 단위로 디코딩 후 필드 룰 적용
        List<FormField> formFields = parseFormUrlEncodedBody(bodyText, charset);
        if (formFields.isEmpty()) {
            return bodyText;
        }

        return formFields.stream()
            .map(formField -> formField.key() + "=" + nullSafe(maskFormFieldValue(formField.key(), formField.value())))
            .collect(java.util.stream.Collectors.joining("&"));
    }

    private String maskMultipartFormDataBody(String bodyText, String contentType) {
        String boundary = contentTypeParameter(contentType, "boundary");
        if (boundary == null || boundary.isBlank()) {
            return "<multipart/form-data omitted>";
        }

        // multipart는 part별 헤더와 바디 분리 후 텍스트 part만 로깅
        List<MultipartPart> multipartParts = parseMultipartParts(bodyText, boundary);
        if (multipartParts.isEmpty()) {
            return "<multipart/form-data omitted>";
        }

        return multipartParts.stream()
            .map(this::formatMultipartPart)
            .collect(java.util.stream.Collectors.joining("&"));
    }

    private void maskJsonNode(JsonNode node, String fieldPath) {
        if (node == null) {
            return;
        }

        if (node.isObject()) {
            maskObjectNode((ObjectNode) node, fieldPath);
            return;
        }

        if (node.isArray()) {
            maskArrayNode((ArrayNode) node, fieldPath);
        }
    }

    private void maskObjectNode(ObjectNode objectNode, String parentPath) {
        List.copyOf(objectNode.propertyNames()).forEach(fieldName -> {
            JsonNode childNode = objectNode.get(fieldName);
            String fieldPath = jsonFieldPath(parentPath, fieldName);
            if (shouldMaskBodyField(fieldName, fieldPath) && childNode != null && !childNode.isNull()) {
                maskMatchedJsonField(objectNode, fieldName, childNode, fieldPath);
                return;
            }

            BodyTruncateRule bodyTruncateRule = findBodyTruncateRule(fieldName, fieldPath);
            if (bodyTruncateRule != null && childNode != null && !childNode.isNull()) {
                objectNode.put(fieldName, truncateBodyValue(bodyValueText(childNode), bodyTruncateRule));
                return;
            }

            maskJsonNode(childNode, fieldPath);
        });
    }

    private void maskMatchedJsonField(ObjectNode objectNode, String fieldName, JsonNode childNode, String fieldPath) {
        if (childNode.isValueNode()) {
            objectNode.put(fieldName, maskBodyValue(childNode.asString()));
            return;
        }

        maskJsonNode(childNode, fieldPath);
    }

    private void maskArrayNode(ArrayNode arrayNode, String fieldPath) {
        arrayNode.forEach(childNode -> maskJsonNode(childNode, fieldPath));
    }

    private String maskFormFieldValue(String fieldName, String value) {
        if (shouldMaskBodyField(fieldName)) {
            return maskBodyValue(value);
        }

        BodyTruncateRule bodyTruncateRule = findBodyTruncateRule(fieldName);
        if (bodyTruncateRule != null) {
            return truncateBodyValue(value, bodyTruncateRule);
        }

        return value;
    }

    private List<MultipartPart> parseMultipartParts(String bodyText, String boundary) {
        List<MultipartPart> multipartParts = new ArrayList<>();
        String delimiter = "--" + boundary;
        String[] rawParts = bodyText.split(Pattern.quote(delimiter));
        for (String rawPart: rawParts) {
            if (rawPart == null || rawPart.isBlank()) {
                continue;
            }

            String trimmedPart = trimMultipartPart(rawPart);
            if (trimmedPart.equals("--") || trimmedPart.isBlank()) {
                continue;
            }

            int separatorIndex = trimmedPart.indexOf("\r\n\r\n");
            int separatorLength = 4;
            if (separatorIndex < 0) {
                separatorIndex = trimmedPart.indexOf("\n\n");
                separatorLength = 2;
            }
            if (separatorIndex < 0) {
                continue;
            }

            String headerText = trimmedPart.substring(0, separatorIndex);
            String bodyPart = trimmedPart.substring(separatorIndex + separatorLength);
            Map<String, List<String>> partHeaders = parsePartHeaders(headerText);
            String fieldName = extractPartName(partHeaders);
            boolean binaryPart = isBinaryMultipartPart(partHeaders);

            multipartParts.add(new MultipartPart(
                fieldName == null || fieldName.isBlank() ? "part" : fieldName,
                trimTrailingLineBreaks(bodyPart),
                binaryPart
            ));
        }
        return multipartParts;
    }

    private String formatMultipartPart(MultipartPart multipartPart) {
        if (multipartPart.binary()) {
            return multipartPart.name() + "=<multipart binary omitted>";
        }

        return multipartPart.name() + "=" + nullSafe(maskFormFieldValue(multipartPart.name(), multipartPart.value()));
    }

    private Map<String, List<String>> parsePartHeaders(String headerText) {
        Map<String, List<String>> headers = new LinkedHashMap<>();
        for (String headerLine: headerText.split("\\r?\\n")) {
            int separatorIndex = headerLine.indexOf(':');
            if (separatorIndex < 0) {
                continue;
            }

            String headerName = headerLine.substring(0, separatorIndex).trim();
            String headerValue = headerLine.substring(separatorIndex + 1).trim();
            headers.computeIfAbsent(headerName, _ -> new ArrayList<>()).add(headerValue);
        }
        return headers;
    }

    private String extractPartName(Map<String, List<String>> partHeaders) {
        String contentDisposition = firstHeaderValue(partHeaders, "Content-Disposition");
        if (contentDisposition == null) {
            return null;
        }

        Matcher matcher = CONTENT_DISPOSITION_NAME_PATTERN.matcher(contentDisposition);
        return matcher.find() ? matcher.group(1) : null;
    }

    private boolean isBinaryMultipartPart(Map<String, List<String>> partHeaders) {
        String contentDisposition = firstHeaderValue(partHeaders, "Content-Disposition");
        if (contentDisposition != null) {
            Matcher filenameMatcher = CONTENT_DISPOSITION_FILENAME_PATTERN.matcher(contentDisposition);
            if (filenameMatcher.find()) {
                return true;
            }
        }

        String partContentType = normalizeContentType(firstHeaderValue(partHeaders, "Content-Type"));
        if (partContentType.isBlank() || partContentType.startsWith("text/")) {
            return false;
        }

        return !isJson(partContentType)
            && !isFormUrlEncoded(partContentType)
            && !isXml(partContentType);
    }

    private String trimMultipartPart(String rawPart) {
        String part = rawPart;
        if (part.startsWith("\r\n")) {
            part = part.substring(2);
        } else if (part.startsWith("\n")) {
            part = part.substring(1);
        }
        return part;
    }

    private String trimTrailingLineBreaks(String value) {
        String trimmed = value;
        while (trimmed.endsWith("\r\n")) {
            trimmed = trimmed.substring(0, trimmed.length() - 2);
        }
        while (trimmed.endsWith("\n")) {
            trimmed = trimmed.substring(0, trimmed.length() - 1);
        }
        return trimmed;
    }

    private String jsonFieldPath(String parentPath, String fieldName) {
        if (!StringUtils.hasText(parentPath)) {
            return fieldName;
        }
        return parentPath + "." + fieldName;
    }

    private boolean shouldMaskBodyField(String fieldName) {
        String normalizedFieldName = normalizeMatchTarget(fieldName);
        return matchesAny(normalizedFieldName, bodyMaskedIncludes)
            && !matchesAny(normalizedFieldName, bodyMaskedExcludes);
    }

    private boolean shouldMaskBodyField(String fieldName, String fieldPath) {
        String normalizedFieldName = normalizeMatchTarget(fieldName);
        String normalizedFieldPath = normalizeMatchTarget(fieldPath);
        return (matchesAny(normalizedFieldName, bodyMaskedIncludes) || matchesAny(normalizedFieldPath, bodyMaskedIncludes))
            && !matchesAny(normalizedFieldName, bodyMaskedExcludes)
            && !matchesAny(normalizedFieldPath, bodyMaskedExcludes);
    }

    private BodyTruncateRule findBodyTruncateRule(String fieldName) {
        String normalizedFieldName = normalizeMatchTarget(fieldName);
        if (matchesAny(normalizedFieldName, bodyTruncatedExcludes)) {
            return null;
        }

        return bodyTruncatedIncludes.stream()
            .filter(rule -> normalizedFieldName.contains(rule.normalizedMatchToken()))
            .findFirst()
            .orElse(null);
    }

    private BodyTruncateRule findBodyTruncateRule(String fieldName, String fieldPath) {
        String normalizedFieldName = normalizeMatchTarget(fieldName);
        String normalizedFieldPath = normalizeMatchTarget(fieldPath);
        if (matchesAny(normalizedFieldName, bodyTruncatedExcludes) || matchesAny(normalizedFieldPath, bodyTruncatedExcludes)) {
            return null;
        }

        return bodyTruncatedIncludes.stream()
            .filter(rule -> normalizedFieldName.contains(rule.normalizedMatchToken())
                || normalizedFieldPath.contains(rule.normalizedMatchToken()))
            .findFirst()
            .orElse(null);
    }

    private List<HeaderLogRule> toHeaderLogRules(HttpLogMatchRule headerRuleProperties) {
        List<HeaderLogRule> headerLogRules = new ArrayList<>();
        headerRuleProperties.mergedInclude().forEach(headerExpression -> headerLogRules.add(HeaderLogRule.parse(headerExpression)));
        return headerLogRules;
    }

    private List<BodyTruncateRule> toBodyTruncateRules(List<String> fieldExpressions, int defaultSize) {
        List<BodyTruncateRule> bodyTruncateRules = new ArrayList<>();
        fieldExpressions.forEach(fieldExpression -> bodyTruncateRules.add(BodyTruncateRule.parse(fieldExpression, defaultSize)));
        return bodyTruncateRules;
    }

    private Set<String> toNormalizedMatchSet(List<String> matchExpressions) {
        return new HashSet<>(matchExpressions.stream()
            .map(this::normalizeMatchExpression)
            .toList());
    }

    private String normalizeMatchExpression(String expression) {
        if (expression == null || expression.isBlank()) {
            return "";
        }

        String trimmedExpression = expression.trim();
        Matcher matcher = SIZE_RULE_PATTERN.matcher(trimmedExpression);
        if (matcher.matches()) {
            return normalizeMatchTarget(matcher.group(1));
        }

        return normalizeMatchTarget(trimmedExpression);
    }

    private static String normalizeMatchTarget(String value) {
        if (value == null || value.isBlank()) {
            return "";
        }

        // 대소문자와 케이스 표기(camel/snake/kebab) 차이 흡수를 위한 정규화
        return value.trim()
            .replace("-", "")
            .replace("_", "")
            .replace(" ", "")
            .toLowerCase();
    }

    private boolean matchesAny(String target, Set<String> matchTokens) {
        return matchTokens.stream()
            .filter(token -> !token.isBlank())
            .anyMatch(target::contains);
    }

    private String maskBodyValue(String value) {
        return maskBodyValue(value, true);
    }

    private String maskBodyValue(String value, boolean complete) {
        if (value == null || value.isEmpty()) {
            return value;
        }

        return "******(" + lengthText(value, complete) + ")";
    }

    private String bodyValueText(JsonNode value) {
        if (value == null || value.isNull()) {
            return "";
        }
        if (value.isString()) {
            return value.asString();
        }
        return value.toString();
    }

    private String truncateBodyValue(String value, BodyTruncateRule bodyTruncateRule) {
        return truncateBodyValue(value, bodyTruncateRule, true);
    }

    private String truncateBodyValue(String value, BodyTruncateRule bodyTruncateRule, boolean complete) {
        if (value == null || value.isEmpty()) {
            return value;
        }

        int prefixLength = bodyTruncateRule.prefixLength();
        int suffixLength = complete ? bodyTruncateRule.suffixLength() : 0;
        if (!complete && prefixLength <= 0 && bodyTruncateRule.suffixLength() > 0) {
            return "...(" + lengthText(value, false) + ")";
        }
        if ((prefixLength <= 0 && suffixLength <= 0) || value.length() <= prefixLength + suffixLength) {
            return value;
        }

        String prefix = value.substring(0, Math.min(prefixLength, value.length()));
        String suffix = value.substring(Math.max(prefix.length(), value.length() - suffixLength));
        return prefix + "..." + suffix + "(" + lengthText(value, complete) + ")";
    }

    private String lengthText(String value, boolean complete) {
        return complete ? "length=" + value.length() : "length>=" + value.length();
    }

    private List<FormField> parseFormUrlEncodedBody(String bodyText, Charset charset) {
        List<FormField> formFields = new ArrayList<>();
        String[] pairs = bodyText.split("&");
        for (String pair: pairs) {
            if (pair == null || pair.isEmpty()) {
                continue;
            }

            int separatorIndex = pair.indexOf('=');
            String encodedKey = separatorIndex >= 0 ? pair.substring(0, separatorIndex) : pair;
            String encodedValue = separatorIndex >= 0 ? pair.substring(separatorIndex + 1) : "";

            formFields.add(new FormField(
                decodeFormComponent(encodedKey, charset),
                decodeFormComponent(encodedValue, charset)
            ));
        }
        return formFields;
    }

    private String decodeFormComponent(String value, Charset charset) {
        return URLDecoder.decode(value, charset);
    }

    private String firstHeaderValue(Map<String, List<String>> headers, String headerName) {
        if (headers == null || headers.isEmpty()) {
            return null;
        }

        String normalizedHeaderName = normalizeMatchTarget(headerName);
        return headers.entrySet().stream()
            .filter(entry -> normalizeMatchTarget(entry.getKey()).equals(normalizedHeaderName))
            .map(Map.Entry::getValue)
            .filter(values -> values != null && !values.isEmpty())
            .map(List::getFirst)
            .findFirst()
            .orElse(null);
    }

    private Charset resolveCharset(String contentType) {
        String charsetName = contentTypeParameter(contentType, "charset");
        if (!StringUtils.hasText(charsetName)) {
            return StandardCharsets.UTF_8;
        }

        try {
            return Charset.forName(charsetName);
        } catch (RuntimeException _) {
            return StandardCharsets.UTF_8;
        }
    }

    private String contentTypeParameter(String contentType, String parameterName) {
        if (!StringUtils.hasText(contentType)) {
            return null;
        }

        String normalizedParameterName = parameterName.toLowerCase();
        String[] parts = contentType.split(";");
        String parameterValue = null;
        for (int i = 1; i < parts.length && parameterValue == null; i++) {
            String part = parts[i].trim();
            int separatorIndex = part.indexOf('=');
            if (separatorIndex < 0) {
                continue;
            }

            String name = part.substring(0, separatorIndex).trim().toLowerCase();
            if (!name.equals(normalizedParameterName)) {
                continue;
            }

            parameterValue = trimQuotes(part.substring(separatorIndex + 1).trim());
        }
        return parameterValue;
    }

    private String trimQuotes(String value) {
        if (value.length() >= 2 && value.startsWith("\"") && value.endsWith("\"")) {
            return value.substring(1, value.length() - 1);
        }
        return value;
    }

    private String normalizeContentType(String contentType) {
        if (contentType == null || contentType.isBlank()) {
            return "";
        }
        int separatorIndex = contentType.indexOf(';');
        String mediaType = separatorIndex >= 0 ? contentType.substring(0, separatorIndex) : contentType;
        return mediaType.trim().toLowerCase();
    }

    private boolean isJson(String contentType) {
        return contentType.equals("application/json") || contentType.endsWith("+json");
    }

    private boolean isFormUrlEncoded(String contentType) {
        return contentType.equals("application/x-www-form-urlencoded");
    }

    private boolean isMultipartFormData(String contentType) {
        return contentType.equals("multipart/form-data");
    }

    private boolean isXml(String contentType) {
        return contentType.equals("application/xml")
            || contentType.equals("text/xml")
            || contentType.endsWith("+xml");
    }

    private String nullSafe(String value) {
        return value == null ? "" : value;
    }

    /**
     * form-urlencoded key/value 항목
     */
    private record FormField(
        String key,
        String value
    ) {
    }

    /**
     * multipart/form-data part 로깅 항목
     */
    private record MultipartPart(
        String name,
        String value,
        boolean binary
    ) {
    }

    /**
     * JSON 문자열 값 종료 위치
     */
    private record JsonStringEnd(
        int index,
        boolean complete
    ) {
    }

    /**
     * 잘린 JSON 문자열 필드 위치
     */
    private record PartialJsonField(
        String name,
        int valueQuoteIndex,
        int nextSearchIndex,
        boolean hasValue
    ) {

        private static PartialJsonField withValue(String name, int valueQuoteIndex) {
            return new PartialJsonField(name, valueQuoteIndex, valueQuoteIndex + 1, true);
        }

        private static PartialJsonField withoutValue(int nextSearchIndex) {
            return new PartialJsonField("", -1, nextSearchIndex, false);
        }
    }

    /**
     * 잘린 JSON 필드 처리 결과
     */
    private record PartialJsonSanitizeResult(
        int appendedIndex,
        int searchIndex,
        boolean changed,
        boolean stop
    ) {

        private static PartialJsonSanitizeResult unchanged(int searchIndex) {
            return new PartialJsonSanitizeResult(0, searchIndex, false, false);
        }

        private static PartialJsonSanitizeResult changed(int appendedIndex, int searchIndex, boolean stop) {
            return new PartialJsonSanitizeResult(appendedIndex, searchIndex, true, stop);
        }
    }

    /**
     * 잘린 JSON 문자열 필드 값 처리 방식
     */
    private record BodyValueSanitizer(
        boolean masked,
        BodyTruncateRule bodyTruncateRule
    ) {

        private static BodyValueSanitizer mask() {
            return new BodyValueSanitizer(true, null);
        }

        private static BodyValueSanitizer truncate(BodyTruncateRule bodyTruncateRule) {
            return new BodyValueSanitizer(false, bodyTruncateRule);
        }
    }

    /**
     * 헤더 로깅 대상과 값 노출 범위 규칙
     */
    private record HeaderLogRule(
        String normalizedMatchToken,
        boolean raw,
        int prefixLength,
        int suffixLength
    ) {

        /**
         * 헤더 include 표현식의 헤더 로깅 규칙 변환
         */
        private static HeaderLogRule parse(String expression) {
            if (expression == null || expression.isBlank()) {
                throw new IllegalArgumentException(
                    "헤더 로깅 include 값은 '헤더명', '헤더명[글자수]', '헤더명[앞글자수:뒤글자수]', '헤더명[앞글자수:]' 또는 '헤더명[:뒤글자수]' 형식이어야 합니다.");
            }

            String trimmedExpression = expression.trim();
            Matcher matcher = SIZE_RULE_PATTERN.matcher(trimmedExpression);
            if (matcher.matches()) {
                try {
                    String headerName = matcher.group(1).trim();
                    int prefixLength = parseRuleSize(matcher.group(2));
                    int suffixLength = matcher.group(3) == null
                        ? prefixLength
                        : parseRuleSize(matcher.group(3));
                    return new HeaderLogRule(normalizeMatchTarget(headerName), false, prefixLength, suffixLength);
                } catch (NumberFormatException exception) {
                    throw new IllegalArgumentException(
                        "헤더 로깅 include 값은 '헤더명[숫자]', '헤더명[숫자:숫자]', '헤더명[숫자:]' 또는 '헤더명[:숫자]' 형식이어야 합니다. value=" + expression,
                        exception
                    );
                }
            }

            if (trimmedExpression.contains("[") || trimmedExpression.contains("]")) {
                throw new IllegalArgumentException(
                    "헤더 로깅 include 값은 '헤더명', '헤더명[글자수]', '헤더명[앞글자수:뒤글자수]', '헤더명[앞글자수:]' 또는 '헤더명[:뒤글자수]' 형식이어야 합니다. value=" + expression
                );
            }

            return new HeaderLogRule(normalizeMatchTarget(trimmedExpression), true, 0, 0);
        }
    }

    /**
     * 바디 필드 축약 대상과 값 노출 범위 규칙
     */
    private record BodyTruncateRule(
        String normalizedMatchToken,
        int prefixLength,
        int suffixLength
    ) {

        /**
         * 바디 필드 include 표현식의 축약 규칙 변환
         */
        private static BodyTruncateRule parse(String expression, int defaultSize) {
            if (expression == null || expression.isBlank()) {
                throw new IllegalArgumentException(
                    "바디 축약 include 값은 '필드명', '필드명[글자수]', '필드명[앞글자수:뒤글자수]', '필드명[앞글자수:]' 또는 '필드명[:뒤글자수]' 형식이어야 합니다."
                );
            }

            String trimmedExpression = expression.trim();
            Matcher matcher = SIZE_RULE_PATTERN.matcher(trimmedExpression);
            if (matcher.matches()) {
                try {
                    String fieldName = matcher.group(1).trim();
                    int prefixLength = parseRuleSize(matcher.group(2));
                    int suffixLength = matcher.group(3) == null
                        ? prefixLength
                        : parseRuleSize(matcher.group(3));
                    return new BodyTruncateRule(normalizeMatchTarget(fieldName), prefixLength, suffixLength);
                } catch (NumberFormatException exception) {
                    throw new IllegalArgumentException(
                        "바디 축약 include 값은 '필드명[숫자]', '필드명[숫자:숫자]', '필드명[숫자:]' 또는 '필드명[:숫자]' 형식이어야 합니다. value=" + expression,
                        exception
                    );
                }
            }

            if (trimmedExpression.contains("[") || trimmedExpression.contains("]")) {
                throw new IllegalArgumentException(
                    "바디 축약 include 값은 '필드명', '필드명[글자수]', '필드명[앞글자수:뒤글자수]', '필드명[앞글자수:]' 또는 '필드명[:뒤글자수]' 형식이어야 합니다. value=" + expression
                );
            }

            return new BodyTruncateRule(normalizeMatchTarget(trimmedExpression), defaultSize, defaultSize);
        }
    }

    private static int parseRuleSize(String value) {
        return StringUtils.hasText(value) ? Integer.parseInt(value) : 0;
    }
}
