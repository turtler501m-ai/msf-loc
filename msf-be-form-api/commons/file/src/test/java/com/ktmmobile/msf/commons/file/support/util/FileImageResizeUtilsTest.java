package com.ktmmobile.msf.commons.file.support.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import com.ktmmobile.msf.commons.file.application.dto.FileRequest;
import com.ktmmobile.msf.commons.file.application.dto.FileVariantOptions;
import com.ktmmobile.msf.commons.file.support.properties.FileImageVariantProperties;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("파일 이미지 변형 유틸")
class FileImageResizeUtilsTest {

    @Test
    @DisplayName("TIF 포맷 변형 요청을 생성한다")
    void createVariantRequestsCreatesTifFormat() throws Exception {
        MultipartFile originalFile = new InMemoryMultipartFile(
            "file",
            "photo.png",
            "image/png",
            createPngBytes()
        );
        FileRequest originalRequest = FileRequest.ofFilePath(originalFile, "images/photo.png");
        FileVariantOptions variantOptions = new FileVariantOptions(false, false, List.of("tif"));

        Map<String, FileRequest> variantRequests = FileImageResizeUtils.createVariantRequests(
            originalRequest,
            variantOptions,
            imageVariantProperties()
        );

        FileRequest tifRequest = variantRequests.get("tif");
        assertThat(tifRequest).isNotNull();
        assertThat(tifRequest.getFilePath()).isEqualTo("images/variants/tif/photo.tif");
        assertThat(tifRequest.file().getOriginalFilename()).isEqualTo("photo.tif");
        assertThat(tifRequest.file().getContentType()).isEqualTo("image/tiff");
        assertThat(ImageIO.read(new ByteArrayInputStream(tifRequest.file().getBytes()))).isNotNull();
    }

    @Test
    @DisplayName("JPG 포맷 변형 요청을 생성한다")
    void createVariantRequestsCreatesJpgFormat() throws Exception {
        MultipartFile originalFile = new InMemoryMultipartFile(
            "file",
            "photo.png",
            "image/png",
            createPngBytes()
        );
        FileRequest originalRequest = FileRequest.ofFilePath(originalFile, "images/photo.png");
        FileVariantOptions variantOptions = new FileVariantOptions(false, false, List.of("jpg"));

        Map<String, FileRequest> variantRequests = FileImageResizeUtils.createVariantRequests(
            originalRequest,
            variantOptions,
            imageVariantProperties()
        );

        FileRequest jpgRequest = variantRequests.get("jpg");
        assertThat(jpgRequest).isNotNull();
        assertThat(jpgRequest.getFilePath()).isEqualTo("images/variants/jpg/photo.jpg");
        assertThat(jpgRequest.file().getOriginalFilename()).isEqualTo("photo.jpg");
        assertThat(jpgRequest.file().getContentType()).isEqualTo("image/jpeg");
        assertThat(ImageIO.read(new ByteArrayInputStream(jpgRequest.file().getBytes()))).isNotNull();
    }

    @Test
    @DisplayName("PNG 포맷 변형 요청을 생성한다")
    void createVariantRequestsCreatesPngFormat() throws Exception {
        MultipartFile originalFile = new InMemoryMultipartFile(
            "file",
            "photo.jpg",
            "image/jpeg",
            createJpgBytes()
        );
        FileRequest originalRequest = FileRequest.ofFilePath(originalFile, "images/photo.jpg");
        FileVariantOptions variantOptions = new FileVariantOptions(false, false, List.of("png"));

        Map<String, FileRequest> variantRequests = FileImageResizeUtils.createVariantRequests(
            originalRequest,
            variantOptions,
            imageVariantProperties()
        );

        FileRequest pngRequest = variantRequests.get("png");
        assertThat(pngRequest).isNotNull();
        assertThat(pngRequest.getFilePath()).isEqualTo("images/variants/png/photo.png");
        assertThat(pngRequest.file().getOriginalFilename()).isEqualTo("photo.png");
        assertThat(pngRequest.file().getContentType()).isEqualTo("image/png");
        assertThat(ImageIO.read(new ByteArrayInputStream(pngRequest.file().getBytes()))).isNotNull();
    }

    @Test
    @DisplayName("TIFF 포맷 요청은 TIF 포맷 변형으로 처리한다")
    void imageFormatsConvertsTiffAliasToTif() {
        FileVariantOptions variantOptions = new FileVariantOptions(false, false, List.of("tiff"));

        assertThat(variantOptions.imageFormats())
            .containsExactly(FileVariantOptions.ImageVariantFormat.TIF);
    }

    private static byte[] createPngBytes() throws Exception {
        return createImageBytes("png");
    }

    private static byte[] createJpgBytes() throws Exception {
        return createImageBytes("jpg");
    }

    private static byte[] createImageBytes(String formatName) throws Exception {
        BufferedImage image = new BufferedImage(4, 4, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        try {
            graphics.setColor(Color.BLUE);
            graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
        } finally {
            graphics.dispose();
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, formatName, outputStream);
        return outputStream.toByteArray();
    }

    private static FileImageVariantProperties imageVariantProperties() {
        return new FileImageVariantProperties(
            new FileImageVariantProperties.ResizedProperties(
                new FileImageVariantProperties.ResizeProperties(200, 200),
                new FileImageVariantProperties.ResizeProperties(800, 800)
            ),
            new FileImageVariantProperties.FormatProperties(
                new FileImageVariantProperties.JpgProperties(0.9f),
                new FileImageVariantProperties.PngProperties(0.9f),
                new FileImageVariantProperties.TiffProperties("lzw")
            )
        );
    }
}
