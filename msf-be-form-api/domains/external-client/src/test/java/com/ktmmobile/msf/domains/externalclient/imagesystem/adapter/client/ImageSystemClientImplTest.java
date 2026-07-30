package com.ktmmobile.msf.domains.externalclient.imagesystem.adapter.client;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.MultiValueMap;

import com.ktmmobile.msf.domains.externalclient.imagesystem.adapter.client.httpclient.ImageSystemHttpClient;
import com.ktmmobile.msf.domains.externalclient.imagesystem.application.dto.ImageSystemPdfUploadRequest;
import com.ktmmobile.msf.domains.externalclient.imagesystem.application.dto.ImageSystemPdfUploadResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("이미지 시스템 Client 구현체")
class ImageSystemClientImplTest {

    private final ImageSystemHttpClient httpClient = mock(ImageSystemHttpClient.class);
    private final ImageSystemClientImpl client = new ImageSystemClientImpl(httpClient);

    @Test
    @DisplayName("PDF 업로드 요청을 규격서 필드명의 request parameter와 multipart 파일로 전달한다")
    @SuppressWarnings("unchecked")
    void sendPdfUploadRequestParametersAndMultipartFile() {
        stubUploadPdf(new ImageSystemPdfUploadResponse("Y", "/scan/2026/test.pdf"));

        ImageSystemPdfUploadResponse response = client.uploadPdf(request());

        assertThat(response.success()).isTrue();
        assertThat(response.result()).isEqualTo("Y");
        assertThat(response.filepath()).isEqualTo("/scan/2026/test.pdf");

        ArgumentCaptor<MultiValueMap<String, Object>> formCaptor = ArgumentCaptor.forClass(MultiValueMap.class);
        verify(httpClient).uploadPdf(
            eq("PDFUPLOAD"),
            eq("DOC001"),
            eq("550e8400e29b41d4a716446655440000"),
            eq("202605151530451234567"),
            eq("SE00001"),
            eq("신규 등록"),
            eq("KT12345"),
            eq("ORG001"),
            eq("홍길동"),
            eq("테스트 업로드"),
            eq("Y"),
            eq("ORG001"),
            formCaptor.capture()
        );

        MultiValueMap<String, Object> form = formCaptor.getValue();
        assertThat(form.keySet()).containsOnly("file");

        Object filePart = form.getFirst("file");
        assertThat(filePart).isInstanceOf(HttpEntity.class);
        HttpEntity<?> fileEntity = (HttpEntity<?>) filePart;
        assertThat(fileEntity.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_PDF);
        assertThat(fileEntity.getBody()).isInstanceOf(Resource.class);
        Resource fileResource = (Resource) fileEntity.getBody();
        assertThat(fileResource.getFilename()).isEqualTo("test.pdf");
    }

    @Test
    @DisplayName("실패 응답은 result=N으로 반환한다")
    void parseFailureResponse() {
        stubUploadPdf(new ImageSystemPdfUploadResponse("N", ""));

        ImageSystemPdfUploadResponse response = client.uploadPdf(request());

        assertThat(response.success()).isFalse();
        assertThat(response.result()).isEqualTo("N");
        assertThat(response.filepath()).isEmpty();
    }

    @Test
    @DisplayName("파일 content type이 없으면 확장자로 multipart 파일 content type을 보정한다")
    @SuppressWarnings("unchecked")
    void fallbackMultipartFileContentTypeByExtension() {
        ImageSystemPdfUploadRequest request = request("test.tif", null);
        when(httpClient.uploadPdf(
            eq("PDFUPLOAD"),
            eq("DOC001"),
            eq("550e8400e29b41d4a716446655440000"),
            eq("202605151530451234567"),
            eq("SE00001"),
            eq("신규 등록"),
            eq("KT12345"),
            eq("ORG001"),
            eq("홍길동"),
            eq("테스트 업로드"),
            eq("Y"),
            eq("ORG001"),
            any()
        )).thenReturn(new ImageSystemPdfUploadResponse("Y", "/scan/2026/test.tif"));

        client.uploadPdf(request);

        ArgumentCaptor<MultiValueMap<String, Object>> formCaptor = ArgumentCaptor.forClass(MultiValueMap.class);
        verify(httpClient).uploadPdf(
            eq("PDFUPLOAD"),
            eq("DOC001"),
            eq("550e8400e29b41d4a716446655440000"),
            eq("202605151530451234567"),
            eq("SE00001"),
            eq("신규 등록"),
            eq("KT12345"),
            eq("ORG001"),
            eq("홍길동"),
            eq("테스트 업로드"),
            eq("Y"),
            eq("ORG001"),
            formCaptor.capture()
        );

        Object filePart = formCaptor.getValue().getFirst("file");
        assertThat(filePart).isInstanceOf(HttpEntity.class);
        HttpEntity<?> fileEntity = (HttpEntity<?>) filePart;
        assertThat(fileEntity.getHeaders().getContentType()).isEqualTo(MediaType.parseMediaType("image/tiff"));
    }

    private void stubUploadPdf(ImageSystemPdfUploadResponse response) {
        when(httpClient.uploadPdf(
            eq("PDFUPLOAD"),
            eq("DOC001"),
            eq("550e8400e29b41d4a716446655440000"),
            eq("202605151530451234567"),
            eq("SE00001"),
            eq("신규 등록"),
            eq("KT12345"),
            eq("ORG001"),
            eq("홍길동"),
            eq("테스트 업로드"),
            eq("Y"),
            eq("ORG001"),
            any()
        )).thenReturn(response);
    }

    private ImageSystemPdfUploadRequest request() {
        return request("test.pdf", "application/pdf");
    }

    private ImageSystemPdfUploadRequest request(String originalFilename, String contentType) {
        return ImageSystemPdfUploadRequest.builder()
            .docCd("DOC001")
            .parentScanId("550e8400e29b41d4a716446655440000")
            .fileId("202605151530451234567")
            .workCd("SE00001")
            .workNm("신규 등록")
            .rgstPrsnId("KT12345")
            .orgId("ORG001")
            .custNm("홍길동")
            .memo("테스트 업로드")
            .onlineYn("Y")
            .companyId("ORG001")
            .file(new MockMultipartFile("file", originalFilename, contentType, "%PDF-1.4".getBytes()))
            .build();
    }
}
