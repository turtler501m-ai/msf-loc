package com.ktmmobile.msf.commons.websecurity.web.exhandler;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import tools.jackson.databind.ObjectMapper;

import com.ktmmobile.msf.commons.websecurity.BindingProperties;
import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponseType;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseHandlingProperties;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("공통 컨트롤러 예외 처리")
class CommonControllerAdviceTest {

    @BeforeAll
    static void setUp() {
        ResponseUtils.initialize(new ObjectMapper(), new ResponseHandlingProperties(null));
    }

    @Test
    @DisplayName("매핑되지 않은 API 경로는 API 없음 응답을 반환한다")
    void apiNotFoundException() {
        CommonControllerAdvice advice = new CommonControllerAdvice(new BindingProperties(true, false));

        ResponseEntity<CommonResponse<Void>> response = advice.apiNotFoundException(
            new NoResourceFoundException(HttpMethod.GET, "/api/files/local/list", "api/files/local/list")
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody())
            .extracting(CommonResponse::code, CommonResponse::message)
            .containsExactly(CommonResponseType.API_NOT_FOUND.code(), CommonResponseType.API_NOT_FOUND.message());
    }
}
