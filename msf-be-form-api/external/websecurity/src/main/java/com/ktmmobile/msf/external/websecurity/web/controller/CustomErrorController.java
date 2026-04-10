package com.ktmmobile.msf.external.websecurity.web.controller;

import java.util.List;
import java.util.Map;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.webmvc.autoconfigure.error.AbstractErrorController;
import org.springframework.boot.webmvc.autoconfigure.error.ErrorViewResolver;
import org.springframework.boot.webmvc.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.msf.external.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.external.websecurity.web.util.response.ResponseUtils;

@RequestMapping("${server.error.path:${error.path:/error}}")
@RestController
public class CustomErrorController extends AbstractErrorController {

    public CustomErrorController(ErrorAttributes errorAttributes, List<ErrorViewResolver> errorViewResolvers) {
        super(errorAttributes, errorViewResolvers);
    }

    @RequestMapping
    public ResponseEntity<CommonResponse<Map<String, Object>>> error(HttpServletRequest request) {
        HttpStatus status = getStatus(request);
        Map<String, Object> body = getErrorAttributes(request, ErrorAttributeOptions.defaults());
        // String body1 = String.format("%s %s", body.get("path"), body.get("error"))
        return ResponseUtils.unknownError(body, status);
    }
}
