package com.ktmmobile.msf.commons.file.application.dto;

import java.util.List;
import java.util.Locale;

import com.ktmmobile.msf.commons.common.exception.SimpleDomainException;

// 파일 변형 옵션
public record FileVariantOptions(
    Boolean small,
    Boolean medium,
    List<String> formats
) {

    public FileVariantOptions {
        small = small != null && small;
        medium = medium != null && medium;
        formats = formats == null ? List.of() : List.copyOf(formats);
    }

    public boolean hasVariants() {
        return small || medium || !formats.isEmpty();
    }

    public List<ImageVariantFormat> imageFormats() {
        return formats.stream()
            .map(ImageVariantFormat::from)
            .toList();
    }

    public static FileVariantOptions empty() {
        return new FileVariantOptions(false, false, List.of());
    }


    public enum ImageVariantFormat {
        PNG("png", "png", "image/png", "png"),
        JPG("jpg", "jpg", "image/jpeg", "jpg"),
        TIF("tif", "tif", "image/tiff", "TIFF");

        private final String variantName;
        private final String extension;
        private final String contentType;
        private final String formatName;

        ImageVariantFormat(String variantName, String extension, String contentType, String formatName) {
            this.variantName = variantName;
            this.extension = extension;
            this.contentType = contentType;
            this.formatName = formatName;
        }

        public String variantName() {
            return variantName;
        }

        public String extension() {
            return extension;
        }

        public String contentType() {
            return contentType;
        }

        public String formatName() {
            return formatName;
        }

        public static ImageVariantFormat from(String format) {
            try {
                String normalizedFormat = format.trim().toUpperCase(Locale.ROOT);
                if ("TIFF".equals(normalizedFormat)) {
                    return TIF;
                }
                return ImageVariantFormat.valueOf(normalizedFormat);
            } catch (RuntimeException _) {
                throw new SimpleDomainException("지원하지 않는 이미지 포맷입니다. format=" + format);
            }
        }
    }
}
