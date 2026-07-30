package com.ktmmobile.msf.commons.file.support.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;

@ConfigurationProperties(prefix = "file.variants.image")
public record FileImageVariantProperties(
    ResizedProperties resized,
    FormatProperties formats
) {

    public FileImageVariantProperties {
        formats = formats == null ? FormatProperties.defaultProperties() : formats;
    }

    public record ResizedProperties(
        ResizeProperties small,
        ResizeProperties medium
    ) {
    }

    public record ResizeProperties(
        int maxWidth,
        int maxHeight
    ) {
    }

    public record FormatProperties(
        JpgProperties jpg,
        PngProperties png,
        TiffProperties tiff
    ) {

        private static FormatProperties defaultProperties() {
            return new FormatProperties(
                JpgProperties.defaultProperties(),
                PngProperties.defaultProperties(),
                TiffProperties.defaultProperties()
            );
        }

        public FormatProperties {
            jpg = jpg == null ? JpgProperties.defaultProperties() : jpg;
            png = png == null ? PngProperties.defaultProperties() : png;
            tiff = tiff == null ? TiffProperties.defaultProperties() : tiff;
        }
    }

    public record JpgProperties(
        Float compressionQuality
    ) {

        private static final float DEFAULT_COMPRESSION_QUALITY = 0.9f;

        public JpgProperties {
            compressionQuality = normalizeCompressionQuality(compressionQuality, DEFAULT_COMPRESSION_QUALITY);
        }

        private static JpgProperties defaultProperties() {
            return new JpgProperties(DEFAULT_COMPRESSION_QUALITY);
        }
    }

    public record PngProperties(
        Float compressionQuality
    ) {

        private static final float DEFAULT_COMPRESSION_QUALITY = 0.9f;

        public PngProperties {
            compressionQuality = normalizeCompressionQuality(compressionQuality, DEFAULT_COMPRESSION_QUALITY);
        }

        private static PngProperties defaultProperties() {
            return new PngProperties(DEFAULT_COMPRESSION_QUALITY);
        }
    }

    public record TiffProperties(
        String compressionType
    ) {

        private static final String DEFAULT_COMPRESSION_TYPE = "LZW";

        public TiffProperties {
            compressionType = StringUtils.hasText(compressionType) ? compressionType.trim() : DEFAULT_COMPRESSION_TYPE;
        }

        private static TiffProperties defaultProperties() {
            return new TiffProperties(DEFAULT_COMPRESSION_TYPE);
        }
    }

    private static float normalizeCompressionQuality(Float compressionQuality, float defaultCompressionQuality) {
        float normalizedCompressionQuality = compressionQuality == null ? defaultCompressionQuality : compressionQuality;
        if (normalizedCompressionQuality < 0.0f || normalizedCompressionQuality > 1.0f) {
            throw new IllegalArgumentException("이미지 압축 품질은 0.0 이상 1.0 이하여야 합니다.");
        }
        return normalizedCompressionQuality;
    }
}
