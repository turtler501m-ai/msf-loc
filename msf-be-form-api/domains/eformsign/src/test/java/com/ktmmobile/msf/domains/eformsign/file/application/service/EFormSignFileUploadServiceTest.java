package com.ktmmobile.msf.domains.eformsign.file.application.service;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.ktmmobile.msf.commons.common.exception.SimpleDomainException;
import com.ktmmobile.msf.commons.file.domain.dto.CommonFile;
import com.ktmmobile.msf.domains.eformsign.file.application.dto.EFormSignFileUploadPrepareRequest;
import com.ktmmobile.msf.domains.eformsign.file.application.dto.EFormSignFileUploadUrlResponse;
import com.ktmmobile.msf.domains.eformsign.file.application.port.out.EFormSignFileStorage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("eFormSign 파일 업로드 서비스")
class EFormSignFileUploadServiceTest {

    private final FakeEFormSignFileStorage storage = new FakeEFormSignFileStorage();
    private final EFormSignFileUploadService service = new EFormSignFileUploadService(storage);

    @Test
    @DisplayName("업로드 준비 요청의 filePath를 저장 경로로 사용한다")
    void issueUploadUrlWithFilePath() {
        EFormSignFileUploadUrlResponse response = service.issueUploadUrl(new EFormSignFileUploadPrepareRequest(
            "company-1/documents/form-1/pdfs/2026/0625/folder-1/document.pdf"
        ));

        assertThat(response.filePath()).isEqualTo("company-1/documents/form-1/pdfs/2026/0625/folder-1/document.pdf");
        assertThat(response.uploadSignedUrl()).isEqualTo("https://storage.example/upload");
        assertThat(storage.uploadFilePath).isEqualTo("eformsign/company-1/documents/form-1/pdfs/2026/0625/folder-1/document.pdf");
    }

    @Test
    @DisplayName("filePath가 없으면 업로드 URL을 발급하지 않는다")
    void issueUploadUrlWithoutFilePath() {
        assertThatThrownBy(() -> service.issueUploadUrl(new EFormSignFileUploadPrepareRequest(null)))
            .isInstanceOf(SimpleDomainException.class)
            .hasMessage("파일 경로가 유효하지 않습니다.");
    }

    @Test
    @DisplayName("filePath에는 상위 경로 이동 구간을 사용할 수 없다")
    void issueUploadUrlWithInvalidFilePath() {
        assertThatThrownBy(() -> service.issueUploadUrl(new EFormSignFileUploadPrepareRequest(
            "company-1/documents/../document.pdf"
        )))
            .isInstanceOf(SimpleDomainException.class)
            .hasMessage("파일 경로가 유효하지 않습니다.");
    }

    @Test
    @DisplayName("filePath는 UTF-8 기준 1024 bytes를 초과할 수 없다")
    void issueUploadUrlWithTooLongFilePath() {
        String filePath = "documents/" + "가".repeat(340) + ".pdf";

        assertThatThrownBy(() -> service.issueUploadUrl(new EFormSignFileUploadPrepareRequest(filePath)))
            .isInstanceOf(SimpleDomainException.class)
            .hasMessage("파일 경로는 UTF-8 기준 1024 bytes를 초과할 수 없습니다.");
    }


    private static class FakeEFormSignFileStorage implements EFormSignFileStorage {

        private String uploadFilePath;

        @Override
        public String generateUploadSignedUrl(String filePath) {
            this.uploadFilePath = filePath;
            return "https://storage.example/upload";
        }

        @Override
        public Optional<CommonFile> getFile(String filePath) {
            return Optional.empty();
        }

        @Override
        public List<CommonFile> listFiles(String directoryPath, int limit) {
            return List.of();
        }

        @Override
        public void removeFile(String filePath) {
        }
    }
}
