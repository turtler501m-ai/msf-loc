package com.ktmmobile.msf.commons.file.support.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("파일 이미지 경로 유틸")
class FileImagePathUtilsTest {

    @Test
    @DisplayName("원본 이미지 기준 변형 이미지 경로를 생성한다")
    void resolveVariantImageFilePath() {
        String filePath = "images/2026/06/20/photo.jpg";

        assertThat(FileImagePathUtils.resolveSmallImageFilePath(filePath))
            .isEqualTo("images/2026/06/20/variants/small/photo.jpg");
        assertThat(FileImagePathUtils.resolveMediumImageFilePath(filePath))
            .isEqualTo("images/2026/06/20/variants/medium/photo.jpg");
    }

    @Test
    @DisplayName("변형 이미지 경로에서 원본 이미지 경로를 조회한다")
    void resolveBaseImageFilePathFromVariantImageFilePath() {
        String filePath = "images/2026/06/20/variants/small/photo.jpg";

        assertThat(FileImagePathUtils.resolveBaseImageFilePath(filePath))
            .isEqualTo("images/2026/06/20/photo.jpg");
    }

    @Test
    @DisplayName("지정한 변형 이름의 이미지 경로를 생성한다")
    void resolveNamedVariantImageFilePath() {
        String filePath = "images/2026/06/20/photo.jpg";

        assertThat(FileImagePathUtils.resolveVariantImageFilePath(filePath, "webp"))
            .isEqualTo("images/2026/06/20/variants/webp/photo.jpg");
    }

    @Test
    @DisplayName("원본과 변형 이미지 전체 경로를 조회한다")
    void resolveImageFilePaths() {
        assertThat(FileImagePathUtils.resolveImageFilePaths("images/photo.jpg"))
            .containsExactly(
                "images/photo.jpg",
                "images/variants/small/photo.jpg",
                "images/variants/medium/photo.jpg",
                "images/variants/png/photo.png",
                "images/variants/jpg/photo.jpg",
                "images/variants/tif/photo.tif"
            );
    }
}
