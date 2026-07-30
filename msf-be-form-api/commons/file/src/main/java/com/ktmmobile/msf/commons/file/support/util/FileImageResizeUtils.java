package com.ktmmobile.msf.commons.file.support.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ktmmobile.msf.commons.common.exception.SimpleDomainException;
import com.ktmmobile.msf.commons.file.application.dto.FileRequest;
import com.ktmmobile.msf.commons.file.application.dto.FileVariantOptions;
import com.ktmmobile.msf.commons.file.support.properties.FileImageVariantProperties;

public final class FileImageResizeUtils {

    private FileImageResizeUtils() {
    }

    public static Map<String, FileRequest> createVariantRequests(
        FileRequest originalRequest,
        FileVariantOptions variantOptions,
        FileImageVariantProperties variantProperties
    ) throws IOException {
        byte[] originalBytes = originalRequest.file().getBytes();
        BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(originalBytes));
        if (originalImage == null) {
            throw new SimpleDomainException("리사이징할 수 없는 이미지 형식입니다.");
        }

        String originalFileName = originalRequest.resolvedFileName();
        String contentType = originalRequest.file().getContentType();
        Map<String, FileRequest> variantRequests = new LinkedHashMap<>();
        if (variantOptions.small()) {
            variantRequests.put(
                "small",
                createResizedRequest(
                    originalImage,
                    contentType,
                    originalFileName,
                    FileImagePathUtils.resolveSmallImageFilePath(originalRequest.getFilePath()),
                    variantProperties.resized().small().maxWidth(),
                    variantProperties.resized().small().maxHeight()
                )
            );
        }
        if (variantOptions.medium()) {
            variantRequests.put(
                "medium",
                createResizedRequest(
                    originalImage,
                    contentType,
                    originalFileName,
                    FileImagePathUtils.resolveMediumImageFilePath(originalRequest.getFilePath()),
                    variantProperties.resized().medium().maxWidth(),
                    variantProperties.resized().medium().maxHeight()
                )
            );
        }
        for (FileVariantOptions.ImageVariantFormat format: variantOptions.imageFormats()) {
            String convertedFilePath = replaceExtension(originalRequest.getFilePath(), format.extension());
            variantRequests.put(
                format.variantName(),
                createFormatRequest(
                    originalImage,
                    originalFileName,
                    FileImagePathUtils.resolveVariantImageFilePath(convertedFilePath, format.variantName()),
                    format,
                    variantProperties
                )
            );
        }
        return variantRequests;
    }

    private static FileRequest createResizedRequest(
        BufferedImage originalImage,
        String contentType,
        String originalFileName,
        String resizedFilePath,
        int maxWidth,
        int maxHeight
    ) throws IOException {
        String formatName = getFormatName(originalFileName);
        BufferedImage resizedImage = resizePreservingAspectRatio(
            originalImage,
            maxWidth,
            maxHeight
        );
        byte[] resizedBytes = toBytes(resizedImage, formatName);
        MultipartFile multipartFile = new InMemoryMultipartFile(
            "file",
            originalFileName,
            contentType,
            resizedBytes
        );
        return FileRequest.ofFilePath(multipartFile, resizedFilePath);
    }

    private static FileRequest createFormatRequest(
        BufferedImage originalImage,
        String originalFileName,
        String convertedFilePath,
        FileVariantOptions.ImageVariantFormat format,
        FileImageVariantProperties variantProperties
    ) throws IOException {
        String convertedFileName = replaceExtension(originalFileName, format.extension());
        byte[] convertedBytes = toBytes(convertImageType(originalImage, format), format, variantProperties);
        MultipartFile multipartFile = new InMemoryMultipartFile(
            "file",
            convertedFileName,
            format.contentType(),
            convertedBytes
        );
        return FileRequest.ofFilePath(multipartFile, convertedFilePath);
    }

    private static BufferedImage resizePreservingAspectRatio(BufferedImage originalImage, int maxWidth, int maxHeight) {
        int originalWidth = originalImage.getWidth();
        int originalHeight = originalImage.getHeight();

        double widthRatio = (double) maxWidth / originalWidth;
        double heightRatio = (double) maxHeight / originalHeight;
        double scale = Math.min(1.0d, Math.min(widthRatio, heightRatio));

        int resizedWidth = Math.max(1, (int) Math.round(originalWidth * scale));
        int resizedHeight = Math.max(1, (int) Math.round(originalHeight * scale));

        int imageType = originalImage.getColorModel().hasAlpha() ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB;
        BufferedImage resizedImage = new BufferedImage(resizedWidth, resizedHeight, imageType);

        Graphics2D graphics = resizedImage.createGraphics();
        try {
            graphics.setComposite(AlphaComposite.Src);
            graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics.drawImage(originalImage, 0, 0, resizedWidth, resizedHeight, null);
        } finally {
            graphics.dispose();
        }

        return resizedImage;
    }

    private static byte[] toBytes(BufferedImage image, String formatName) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        boolean writable = ImageIO.write(image, formatName, outputStream);
        if (!writable) {
            throw new SimpleDomainException("리사이징 결과를 저장할 수 없는 이미지 형식입니다.");
        }
        return outputStream.toByteArray();
    }

    private static byte[] toBytes(
        BufferedImage image,
        FileVariantOptions.ImageVariantFormat format,
        FileImageVariantProperties variantProperties
    ) throws IOException {
        if (format == FileVariantOptions.ImageVariantFormat.JPG) {
            return toCompressedBytes(
                image,
                format.formatName(),
                null,
                variantProperties.formats().jpg().compressionQuality()
            );
        }
        if (format == FileVariantOptions.ImageVariantFormat.PNG) {
            return toCompressedBytes(
                image,
                format.formatName(),
                null,
                variantProperties.formats().png().compressionQuality()
            );
        }
        return toCompressedBytes(
            image,
            format.formatName(),
            variantProperties.formats().tiff().compressionType(),
            null
        );
    }

    private static byte[] toCompressedBytes(
        BufferedImage image,
        String formatName,
        String compressionType,
        Float compressionQuality
    ) throws IOException {
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName(formatName);
        if (!writers.hasNext()) {
            throw new SimpleDomainException("이미지를 저장할 수 없습니다. format=" + formatName);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageWriter writer = writers.next();
        try (ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(outputStream)) {
            writer.setOutput(imageOutputStream);
            ImageWriteParam writeParam = writer.getDefaultWriteParam();
            applyCompression(writeParam, compressionType, compressionQuality);
            writer.write(null, new IIOImage(image, null, null), writeParam);
        } finally {
            writer.dispose();
        }
        return outputStream.toByteArray();
    }

    private static void applyCompression(ImageWriteParam writeParam, String compressionType, Float compressionQuality) {
        if (!writeParam.canWriteCompressed()) {
            throw new SimpleDomainException("이미지 압축을 설정할 수 없습니다.");
        }
        writeParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        if (StringUtils.hasText(compressionType)) {
            writeParam.setCompressionType(resolveCompressionType(writeParam, compressionType));
        }
        if (compressionQuality != null) {
            writeParam.setCompressionQuality(compressionQuality);
        }
    }

    private static String resolveCompressionType(ImageWriteParam writeParam, String compressionType) {
        String[] compressionTypes = writeParam.getCompressionTypes();
        if (compressionTypes == null || compressionTypes.length == 0) {
            throw new SimpleDomainException("지원 가능한 TIFF 압축 형식을 확인할 수 없습니다.");
        }
        return Arrays.stream(compressionTypes)
            .filter(supportedCompressionType -> supportedCompressionType.equalsIgnoreCase(compressionType))
            .findFirst()
            .orElseThrow(() -> new SimpleDomainException(
                "지원하지 않는 이미지 압축 형식입니다. compressionType=" + compressionType
                    + ", supportedCompressionTypes=" + String.join(",", compressionTypes)
            ));
    }

    private static BufferedImage convertImageType(BufferedImage image, FileVariantOptions.ImageVariantFormat format) {
        if (format != FileVariantOptions.ImageVariantFormat.JPG || !image.getColorModel().hasAlpha()) {
            return image;
        }
        BufferedImage convertedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = convertedImage.createGraphics();
        try {
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, convertedImage.getWidth(), convertedImage.getHeight());
            graphics.drawImage(image, 0, 0, null);
        } finally {
            graphics.dispose();
        }
        return convertedImage;
    }

    private static String getFormatName(String originalFileName) {
        String extension = StringUtils.getFilenameExtension(originalFileName);
        if (!StringUtils.hasText(extension)) {
            throw new SimpleDomainException("리사이징 이미지 확장자를 확인할 수 없습니다.");
        }
        return extension.toLowerCase();
    }

    private static String replaceExtension(String filePath, String extension) {
        int directorySeparatorIndex = filePath.lastIndexOf('/');
        int extensionSeparatorIndex = filePath.lastIndexOf('.');
        if (extensionSeparatorIndex <= directorySeparatorIndex) {
            return filePath + "." + extension;
        }
        return filePath.substring(0, extensionSeparatorIndex + 1) + extension;
    }

}
