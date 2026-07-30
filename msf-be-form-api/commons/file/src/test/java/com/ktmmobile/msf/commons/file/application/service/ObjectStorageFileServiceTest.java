package com.ktmmobile.msf.commons.file.application.service;

import java.time.Duration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectResponse;

import com.ktmmobile.msf.commons.file.support.properties.ObjectStorageProperties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("오브젝트 스토리지 파일 서비스")
class ObjectStorageFileServiceTest {

    @Test
    @DisplayName("파일 경로는 기본 경로 하위로 조회한다")
    void fileExistsResolvesPathUnderBasePath() {
        S3Client s3Client = mock(S3Client.class);
        when(s3Client.headObject(any(HeadObjectRequest.class))).thenReturn(HeadObjectResponse.builder().build());
        ObjectStorageFileService service = createService(s3Client);

        service.fileExists("docs/a.txt");

        ArgumentCaptor<HeadObjectRequest> requestCaptor = ArgumentCaptor.forClass(HeadObjectRequest.class);
        verify(s3Client).headObject(requestCaptor.capture());
        assertThat(requestCaptor.getValue().key()).isEqualTo("files/docs/a.txt");
    }

    @Test
    @DisplayName("기본 경로가 포함된 파일 경로는 중복하지 않는다")
    void fileExistsDoesNotDuplicateBasePath() {
        S3Client s3Client = mock(S3Client.class);
        when(s3Client.headObject(any(HeadObjectRequest.class))).thenReturn(HeadObjectResponse.builder().build());
        ObjectStorageFileService service = createService(s3Client);

        service.fileExists("/files/docs/a.txt");

        ArgumentCaptor<HeadObjectRequest> requestCaptor = ArgumentCaptor.forClass(HeadObjectRequest.class);
        verify(s3Client).headObject(requestCaptor.capture());
        assertThat(requestCaptor.getValue().key()).isEqualTo("files/docs/a.txt");
    }

    @Test
    @DisplayName("기본 경로 밖으로 벗어나는 경로는 허용하지 않는다")
    void fileExistsThrowsExceptionWhenPathEscapesBasePath() {
        ObjectStorageFileService service = createService(mock(S3Client.class));

        assertThatThrownBy(() -> service.fileExists("../secret.txt"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("파일 경로는 기본 경로 하위여야 합니다.");
    }

    private static ObjectStorageFileService createService(S3Client s3Client) {
        return new ObjectStorageFileService(
            new ObjectStorageProperties(
                "https://storage.example.com",
                "ap-northeast-2",
                "files",
                new ObjectStorageProperties.Credentials("access-key", "secret-key"),
                "test-bucket",
                new ObjectStorageProperties.Configurations(
                    10,
                    Duration.ofSeconds(1),
                    Duration.ofSeconds(1),
                    Duration.ofSeconds(1)
                )
            ),
            null,
            null,
            s3Client,
            null
        );
    }
}
