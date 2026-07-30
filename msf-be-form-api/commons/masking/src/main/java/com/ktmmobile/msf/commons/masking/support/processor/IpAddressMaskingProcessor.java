package com.ktmmobile.msf.commons.masking.support.processor;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ktmmobile.msf.commons.masking.domain.code.MaskingType;

/**
 * IP 주소 마스킹 Processor
 */
public class IpAddressMaskingProcessor implements MaskingProcessor {

    /** IPv6에서 앞쪽에 노출할 hextet 개수 */
    private static final int VISIBLE_IPV6_PREFIX_HEXTET_COUNT = 4;

    /** IPv6 전체 hextet 개수 */
    private static final int IPV6_HEXTET_COUNT = 8;

    /** IPv4 옥텟 조회 */
    private static final Pattern IPV4_ADDRESS = Pattern.compile("^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$");

    /** IPv6 리터럴 후보 문자 검증 */
    private static final Pattern IPV6_CANDIDATE = Pattern.compile("^[0-9A-Fa-f:.]+$");

    @Override
    public MaskingType type() {
        return MaskingType.IP_ADDRESS;
    }

    @Override
    public String mask(String value) {
        Matcher matcher = IPV4_ADDRESS.matcher(value);
        if (matcher.matches()) {
            return maskIpv4(value, matcher);
        }

        return maskIpv6(value);
    }

    /** IPv4 3번째 옥텟 마스킹 */
    private String maskIpv4(String value, Matcher matcher) {
        if (!isValidIpv4(matcher)) {
            return value;
        }
        return matcher.group(1)
            + "."
            + matcher.group(2)
            + "."
            + String.valueOf(MaskingProcessorUtils.MASK).repeat(matcher.group(3).length())
            + "."
            + matcher.group(4);
    }

    /** IPv6 앞 64비트와 마지막 hextet 노출 및 중간 hextet 마스킹 */
    private String maskIpv6(String value) {
        if (!isIpv6Candidate(value)) {
            return value;
        }

        try {
            InetAddress address = InetAddress.getByName(value);
            if (!(address instanceof Inet6Address inet6Address)) {
                return value;
            }
            return maskIpv6Bytes(inet6Address.getAddress());
        } catch (UnknownHostException ex) {
            return value;
        }
    }

    /** IPv4 옥텟 범위 검증 */
    private boolean isValidIpv4(Matcher matcher) {
        for (int group = 1; group <= 4; group++) {
            int octet = Integer.parseInt(matcher.group(group));
            if (octet > 255) {
                return false;
            }
        }
        return true;
    }

    /** IPv6 리터럴 후보 여부 판단 */
    private boolean isIpv6Candidate(String value) {
        return value.contains(":") && IPV6_CANDIDATE.matcher(value).matches();
    }

    /** IPv6 byte 배열을 hextet 단위로 변환 후 마스킹 */
    private String maskIpv6Bytes(byte[] bytes) {
        StringBuilder masked = new StringBuilder();
        for (int hextetIndex = 0; hextetIndex < IPV6_HEXTET_COUNT; hextetIndex++) {
            if (hextetIndex > 0) {
                masked.append(':');
            }
            if (isVisibleIpv6Hextet(hextetIndex)) {
                masked.append(toHextet(bytes, hextetIndex));
            } else {
                masked.append("****");
            }
        }
        return masked.toString();
    }

    /** IPv6에서 노출할 hextet 여부 판단 */
    private boolean isVisibleIpv6Hextet(int hextetIndex) {
        return hextetIndex < VISIBLE_IPV6_PREFIX_HEXTET_COUNT
            || hextetIndex == IPV6_HEXTET_COUNT - 1;
    }

    /** IPv6 byte 배열의 hextet 값 변환 */
    private String toHextet(byte[] bytes, int hextetIndex) {
        int byteIndex = hextetIndex * 2;
        int hextet = (Byte.toUnsignedInt(bytes[byteIndex]) << 8) + Byte.toUnsignedInt(bytes[byteIndex + 1]);
        return Integer.toHexString(hextet);
    }
}
