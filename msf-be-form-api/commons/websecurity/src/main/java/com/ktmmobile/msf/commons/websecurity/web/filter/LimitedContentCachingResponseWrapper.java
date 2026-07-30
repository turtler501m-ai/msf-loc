package com.ktmmobile.msf.commons.websecurity.web.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import org.springframework.util.StringUtils;

/**
 * 응답 본문을 원 응답으로 즉시 전달하면서 제한된 크기만 캐시하는 wrapper
 */
class LimitedContentCachingResponseWrapper extends HttpServletResponseWrapper {

    private final ByteArrayOutputStream cachedContent;
    private final int cacheLimit;

    private ServletOutputStream outputStream;
    private PrintWriter writer;
    private boolean overflowed;

    LimitedContentCachingResponseWrapper(HttpServletResponse response, int cacheLimit) {
        super(response);
        this.cacheLimit = cacheLimit;
        this.cachedContent = new ByteArrayOutputStream(Math.min(cacheLimit, 1024));
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (outputStream != null) {
            return outputStream;
        }
        if (writer != null) {
            throw new IllegalStateException("getWriter() has already been called on this response");
        }
        outputStream = new ResponseCachingOutputStream(getResponse().getOutputStream());
        return outputStream;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if (writer != null) {
            return writer;
        }
        if (outputStream != null) {
            throw new IllegalStateException("getOutputStream() has already been called on this response");
        }
        writer = new PrintWriter(new OutputStreamWriter(getOutputStream(), responseCharset()));
        return writer;
    }

    @Override
    public void flushBuffer() throws IOException {
        flushCachedContent();
        super.flushBuffer();
    }

    byte[] getContentAsByteArray() {
        return cachedContent.toByteArray();
    }

    boolean isOverflowed() {
        return overflowed;
    }

    void flushCachedContent() throws IOException {
        if (writer != null) {
            writer.flush();
        }
        if (outputStream != null) {
            outputStream.flush();
        }
    }

    private Charset responseCharset() {
        String characterEncoding = getCharacterEncoding();
        if (!StringUtils.hasText(characterEncoding)) {
            return StandardCharsets.UTF_8;
        }
        return Charset.forName(characterEncoding);
    }

    private void cache(byte[] bytes, int offset, int length) {
        if (length <= 0 || cachedContent.size() >= cacheLimit) {
            overflowed = overflowed || length > 0;
            return;
        }

        int writableLength = Math.min(length, cacheLimit - cachedContent.size());
        cachedContent.write(bytes, offset, writableLength);
        overflowed = overflowed || writableLength < length;
    }

    private class ResponseCachingOutputStream extends ServletOutputStream {

        private final ServletOutputStream delegate;

        private ResponseCachingOutputStream(ServletOutputStream delegate) {
            this.delegate = delegate;
        }

        @Override
        public void write(int value) throws IOException {
            delegate.write(value);
            if (cachedContent.size() < cacheLimit) {
                cachedContent.write(value);
            } else {
                overflowed = true;
            }
        }

        @Override
        public void write(byte[] bytes, int offset, int length) throws IOException {
            delegate.write(bytes, offset, length);
            cache(bytes, offset, length);
        }

        @Override
        public void flush() throws IOException {
            delegate.flush();
        }

        @Override
        public void close() throws IOException {
            delegate.close();
        }

        @Override
        public boolean isReady() {
            return delegate.isReady();
        }

        @Override
        public void setWriteListener(WriteListener writeListener) {
            delegate.setWriteListener(writeListener);
        }
    }
}
