package com.ktmmobile.msf.commons.file.application.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.ktmmobile.msf.commons.file.application.port.in.CommonFileService.DEFAULT_FILE_LIMIT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("파일 목록 요청")
class FileListRequestTest {

    @Test
    @DisplayName("최대 개수가 없으면 기본값을 사용한다")
    void usesDefaultLimitWhenLimitIsNull() {
        FileListRequest request = new FileListRequest("/docs/images/", null);

        assertThat(request.directoryPath()).isEqualTo("docs/images");
        assertThat(request.limit()).isEqualTo(DEFAULT_FILE_LIMIT);
    }

    @Test
    @DisplayName("최대 개수는 1 이상이어야 한다")
    void throwsExceptionWhenLimitIsLessThanOne() {
        assertThatThrownBy(() -> new FileListRequest("docs", 0))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("limit은 1 이상이어야 합니다.");
    }

    @Test
    @DisplayName("최대 개수는 1000 이하여야 한다")
    void throwsExceptionWhenLimitIsGreaterThanDefaultLimit() {
        assertThatThrownBy(() -> new FileListRequest("docs", 1_001))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("limit은 1000 이하여야 합니다.");
    }
}
