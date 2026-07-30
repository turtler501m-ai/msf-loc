package com.ktmmobile.msf.commons.file.application.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import com.ktmmobile.msf.commons.common.exception.SimpleDomainException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("파일 요청")
class FileRequestTest {

    @Test
    @DisplayName("파일 분류가 없으면 파일 요청을 생성하지 않는다")
    void ofThrowsExceptionWhenFileCategoryIsBlank() {
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "test".getBytes());

        assertThatThrownBy(() -> FileRequest.of(file, " "))
            .isInstanceOf(SimpleDomainException.class)
            .hasMessage("파일 분류는 필수입니다.");
    }

    @Test
    @DisplayName("파일 분류가 2글자 미만이면 파일 요청을 생성하지 않는다")
    void ofThrowsExceptionWhenFileCategoryIsLessThanTwoCharacters() {
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "test".getBytes());

        assertThatThrownBy(() -> FileRequest.of(file, "a"))
            .isInstanceOf(SimpleDomainException.class)
            .hasMessage("파일 분류는 2글자 이상이어야 합니다.");
    }

    @Test
    @DisplayName("파일 분류가 상위 경로를 포함하면 파일 요청을 생성하지 않는다")
    void ofThrowsExceptionWhenFileCategoryContainsParentPath() {
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "test".getBytes());

        assertThatThrownBy(() -> FileRequest.of(file, "../docs"))
            .isInstanceOf(SimpleDomainException.class)
            .hasMessage("파일 분류는 디렉토리 경로로 사용할 수 있는 문자열이어야 합니다.");
    }

    @Test
    @DisplayName("파일 분류가 디렉토리 특수문자를 포함하면 파일 요청을 생성하지 않는다")
    void ofThrowsExceptionWhenFileCategoryContainsInvalidDirectoryPathCharacter() {
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "test".getBytes());

        assertThatThrownBy(() -> FileRequest.of(file, "do?cs"))
            .isInstanceOf(SimpleDomainException.class)
            .hasMessage("파일 분류는 디렉토리 경로로 사용할 수 있는 문자열이어야 합니다.");
    }

    @Test
    @DisplayName("파일 분류가 있으면 파일 요청을 생성한다")
    void ofCreatesFileRequestWhenFileCategoryExists() {
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "test".getBytes());

        FileRequest request = FileRequest.of(file, " /docs/images/ ");

        assertThat(request.fileCategory()).isEqualTo("docs/images");
        assertThat(request.resolvedFilePath()).startsWith("docs/images/");
    }

    @Test
    @DisplayName("직접 지정 경로 파일 요청은 파일 분류 없이 생성한다")
    void ofFilePathCreatesFileRequestWithoutFileCategory() {
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "test".getBytes());

        FileRequest request = FileRequest.ofFilePath(file, "docs/test.txt");

        assertThat(request.fileCategory()).isNull();
        assertThat(request.getFilePath()).isEqualTo("docs/test.txt");
    }
}
