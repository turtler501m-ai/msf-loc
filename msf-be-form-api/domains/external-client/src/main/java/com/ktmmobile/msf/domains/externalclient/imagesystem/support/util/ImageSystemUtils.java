package com.ktmmobile.msf.domains.externalclient.imagesystem.support.util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

import com.ktmmobile.msf.commons.common.exception.SimpleDomainException;
import com.ktmmobile.msf.commons.file.domain.dto.FileContent;
import com.ktmmobile.msf.domains.externalclient.imagesystem.domain.code.DocTypeCd;
import com.ktmmobile.msf.domains.externalclient.imagesystem.domain.code.FormTypeCd;
import com.ktmmobile.msf.domains.externalclient.imagesystem.domain.code.ImageDocTypeCd;
import com.ktmmobile.msf.domains.externalclient.imagesystem.domain.code.OperTypeCd;
import com.ktmmobile.msf.domains.externalclient.imagesystem.domain.code.WorkTypeCd;

public final class ImageSystemUtils {

    public static final String TIFF_CONTENT_TYPE = "image/tiff";
    public static final String PDF_CONTENT_TYPE = "application/pdf";

    private static final int MAX_WIDTH = 1400;
    private static final int MAX_HEIGHT = 1400;

    private ImageSystemUtils() {
    }

    public static UploadFile prepareUploadFile(
        String fileName,
        FileContent fileContent
    ) {
        if (isPdf(fileName)) {
            return new UploadFile(
                fileName,
                PDF_CONTENT_TYPE,
                fileContent.content()
            );
        }

        if (isTif(fileName)) {
            return new UploadFile(
                fileName,
                TIFF_CONTENT_TYPE,
                fileContent.content()
            );
        }

        if (isJpgJpegPng(fileName)) {
            return convertToResizedTif(fileName, fileContent);
        }

        throw new SimpleDomainException(
            "지원하지 않는 파일 형식입니다. fileName=" + fileName
        );
    }

    public static String extractFileName(String pathFileName) {
        if (pathFileName == null || pathFileName.isBlank()) {
            return "upload.tif";
        }

        int index = pathFileName.lastIndexOf("/");
        return index >= 0
            ? pathFileName.substring(index + 1)
            : pathFileName;
    }

    private static UploadFile convertToResizedTif(
        String fileName,
        FileContent fileContent
    ) {
        try {
            BufferedImage originalImage = ImageIO.read(
                new ByteArrayInputStream(fileContent.content())
            );

            if (originalImage == null) {
                throw new SimpleDomainException(
                    "이미지 파일을 읽을 수 없습니다. fileName=" + fileName
                );
            }

            BufferedImage resizedImage = resizeIfNeeded(originalImage);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            boolean written = ImageIO.write(
                resizedImage,
                "TIFF",
                outputStream
            );

            if (!written) {
                throw new SimpleDomainException(
                    "TIFF 변환 Writer를 찾을 수 없습니다."
                );
            }

            return new UploadFile(
                changeExtensionToTif(fileName),
                TIFF_CONTENT_TYPE,
                outputStream.toByteArray()
            );

        } catch (IOException e) {
            throw new SimpleDomainException(
                "TIFF 변환 중 오류가 발생했습니다. fileName=" + fileName,
                e
            );
        }
    }

    private static BufferedImage resizeIfNeeded(BufferedImage originalImage) {
        int originalWidth = originalImage.getWidth();
        int originalHeight = originalImage.getHeight();

        if (originalWidth <= MAX_WIDTH && originalHeight <= MAX_HEIGHT) {
            return toRgbImage(originalImage);
        }

        double widthRatio = (double) MAX_WIDTH / originalWidth;
        double heightRatio = (double) MAX_HEIGHT / originalHeight;
        double ratio = Math.min(widthRatio, heightRatio);

        int resizedWidth = Math.max(1, (int) (originalWidth * ratio));
        int resizedHeight = Math.max(1, (int) (originalHeight * ratio));

        BufferedImage resizedImage = new BufferedImage(
            resizedWidth,
            resizedHeight,
            BufferedImage.TYPE_INT_RGB
        );

        Graphics2D graphics = resizedImage.createGraphics();

        try {
            graphics.setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR
            );
            graphics.setRenderingHint(
                RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY
            );
            graphics.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
            );

            graphics.drawImage(
                originalImage,
                0,
                0,
                resizedWidth,
                resizedHeight,
                null
            );

            return resizedImage;
        } finally {
            graphics.dispose();
        }
    }

    private static BufferedImage toRgbImage(BufferedImage originalImage) {
        if (originalImage.getType() == BufferedImage.TYPE_INT_RGB) {
            return originalImage;
        }

        BufferedImage rgbImage = new BufferedImage(
            originalImage.getWidth(),
            originalImage.getHeight(),
            BufferedImage.TYPE_INT_RGB
        );

        Graphics2D graphics = rgbImage.createGraphics();

        try {
            graphics.drawImage(originalImage, 0, 0, null);
            return rgbImage;
        } finally {
            graphics.dispose();
        }
    }

    private static boolean isPdf(String fileName) {
        return fileName.toLowerCase().endsWith(".pdf");
    }

    private static boolean isJpgJpegPng(String fileName) {
        String lowerFileName = fileName.toLowerCase();

        return lowerFileName.endsWith(".jpg")
            || lowerFileName.endsWith(".jpeg")
            || lowerFileName.endsWith(".png");
    }

    private static boolean isTif(String fileName) {
        String lowerFileName = fileName.toLowerCase();

        return lowerFileName.endsWith(".tif")
            || lowerFileName.endsWith(".tiff");
    }

    private static String changeExtensionToTif(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');

        if (dotIndex < 0) {
            return fileName + ".tif";
        }

        return fileName.substring(0, dotIndex) + ".tif";
    }

    public record UploadFile(
        String fileName,
        String contentType,
        byte[] content
    ) {
    }

    public static ImageSystemMeta resolveMeta(
        String formTypeCd,
        String operTypeCd,
        String docTypeCd
    ) {
        FormTypeCd formType = FormTypeCd.from(formTypeCd);

        ImageDocTypeCd imageDocTypeCd = resolveImageDocType(
            formType,
            operTypeCd,
            docTypeCd
        );

        WorkTypeCd workTypeCd = resolveWorkType(formType, operTypeCd);

        return new ImageSystemMeta(
            imageDocTypeCd.getCode(),
            workTypeCd.getCode(),
            workTypeCd.getTitle()
        );
    }

    private static ImageDocTypeCd resolveImageDocType(
        FormTypeCd formTypeCd,
        String operTypeCd,
        String docTypeCd
    ) {
        if (docTypeCd != null && !docTypeCd.isBlank()) {
            DocTypeCd.from(docTypeCd);
            return ImageDocTypeCd.ETC; // E0016
        }

        return switch (formTypeCd) {
            case NEW_CHANGE -> switch (OperTypeCd.from(operTypeCd)) {
                case NAC -> ImageDocTypeCd.SERVICE_JOIN;        // E0001
                case MNP -> ImageDocTypeCd.NUMBER_PORTING;      // E0023
                case HCN -> ImageDocTypeCd.PHONE_DEVICE_CHANGE; // E0025
                case HDN -> ImageDocTypeCd.DEVICE_USIM_CHANGE;  // E0004
                default -> throw new SimpleDomainException(
                    "지원하지 않는 신규/변경 operTypeCd=" + operTypeCd
                );
            };

            case OWNER_CHANGE -> switch (OperTypeCd.from(operTypeCd)) {
                case MCN -> ImageDocTypeCd.OWNER_CHANGE;        // E0003
                default -> throw new SimpleDomainException(
                    "지원하지 않는 명의변경 operTypeCd=" + operTypeCd
                );
            };

            case SERVICE_CHANGE -> ImageDocTypeCd.CHANGE_REQUEST; // E0002
            case TERMINATION -> ImageDocTypeCd.TERMINATION;       // E0005
        };
    }

    private static WorkTypeCd resolveWorkType(
        FormTypeCd formTypeCd,
        String operTypeCd
    ) {
        return switch (formTypeCd) {
            case NEW_CHANGE -> switch (OperTypeCd.from(operTypeCd)) {
                case NAC -> WorkTypeCd.JOIN;                 // S0001
                case MNP -> WorkTypeCd.NUMBER_PORTING;       // S0013
                case HCN -> WorkTypeCd.PHONE_DEVICE_CHANGE;  // S0015
                case HDN -> WorkTypeCd.DEVICE_USIM_CHANGE;   // S0004
                default -> throw new SimpleDomainException(
                    "지원하지 않는 신규/변경 operTypeCd=" + operTypeCd
                );
            };

            case OWNER_CHANGE -> switch (OperTypeCd.from(operTypeCd)) {
                case MCN -> WorkTypeCd.OWNER_CHANGE;         // S0003
                default -> throw new SimpleDomainException(
                    "지원하지 않는 명의변경 operTypeCd=" + operTypeCd
                );
            };

            case SERVICE_CHANGE -> WorkTypeCd.CHANGE;        // S0002, operTypeCd 무시
            case TERMINATION -> WorkTypeCd.TERMINATION;      // S0005, operTypeCd 무시
        };
    }

    public record ImageSystemMeta(
        String docCd,
        String workCd,
        String workNm
    ) {
    }
}