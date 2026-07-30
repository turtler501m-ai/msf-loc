package com.ktmmobile.msf.commons.websecurity.web.filter;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("제한 응답 본문 캐싱 wrapper")
class LimitedContentCachingResponseWrapperTest {

    @Test
    @DisplayName("응답 본문은 원 응답에 모두 전달하고 설정된 크기까지만 캐시한다")
    void cachesResponseBodyUpToLimit() throws Exception {
        MockHttpServletResponse response = new MockHttpServletResponse();
        LimitedContentCachingResponseWrapper wrapper = new LimitedContentCachingResponseWrapper(response, 5);

        wrapper.getOutputStream().write("1234567890".getBytes(StandardCharsets.UTF_8));
        wrapper.flushCachedContent();

        assertThat(response.getContentAsString()).isEqualTo("1234567890");
        assertThat(new String(wrapper.getContentAsByteArray(), StandardCharsets.UTF_8)).isEqualTo("12345");
        assertThat(wrapper.isOverflowed()).isTrue();
    }

    @Test
    @DisplayName("Writer 응답도 설정된 크기까지만 캐시한다")
    void cachesWriterResponseBodyUpToLimit() throws Exception {
        MockHttpServletResponse response = new MockHttpServletResponse();
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        LimitedContentCachingResponseWrapper wrapper = new LimitedContentCachingResponseWrapper(response, 6);

        wrapper.getWriter().write("가나다");
        wrapper.flushCachedContent();

        assertThat(response.getContentAsString()).isEqualTo("가나다");
        assertThat(new String(wrapper.getContentAsByteArray(), StandardCharsets.UTF_8)).isEqualTo("가나");
        assertThat(wrapper.isOverflowed()).isTrue();
    }
}
