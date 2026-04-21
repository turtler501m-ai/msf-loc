package com.ktmmobile.msf.commons.client.application.dto;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;

/**
 * 응답 바디를 한 번 읽어 로그에 남긴 뒤에도,
 * 실제 호출부가 다시 읽을 수 있도록 메모리에 보관한 바디를 재노출하는 래퍼 응답 객체
 */
public class BufferedClientHttpResponse implements ClientHttpResponse {

    private final ClientHttpResponse delegate;
    private final byte[] body;

    public BufferedClientHttpResponse(ClientHttpResponse delegate, byte[] body) {
        this.delegate = delegate;
        this.body = body;
    }

    @Override
    public HttpStatusCode getStatusCode() throws IOException {
        return delegate.getStatusCode();
    }

    @Override
    public String getStatusText() throws IOException {
        return delegate.getStatusText();
    }

    @Override
    public void close() {
        delegate.close();
    }

    @Override
    public InputStream getBody() {
        // 원본 스트림은 이미 interceptor에서 소비했기 때문에, 보관한 바이트 배열로 새 스트림을 만들어 반환
        return new ByteArrayInputStream(body);
    }

    @Override
    public HttpHeaders getHeaders() {
        return delegate.getHeaders();
    }
}
