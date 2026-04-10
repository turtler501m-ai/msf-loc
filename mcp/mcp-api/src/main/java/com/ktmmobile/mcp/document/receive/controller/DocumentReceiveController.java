package com.ktmmobile.mcp.document.receive.controller;

import com.ktmmobile.mcp.cmmn.exception.McpCommonJsonException;
import com.ktmmobile.mcp.cmmn.util.StringUtil;
import com.ktmmobile.mcp.document.receive.dto.*;
import com.ktmmobile.mcp.document.receive.service.DocumentReceiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ktmmobile.mcp.cmmn.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;
import static com.ktmmobile.mcp.document.receive.service.DocumentReceiveService.validateDetail;

@RequestMapping(value = "/document/receive")
@RestController
public class DocumentReceiveController {

    @Autowired
    DocumentReceiveService documentReceiveService;

    /**
     * 유효한 서류 접수 대상인지 검증
     * @param docRcvRequest
     * @return docRcvResponse
     */
    @RequestMapping(value = "/getForScan", method = RequestMethod.POST)
    public ResponseEntity<DocRcvResponseDto<?>> getForScan(@RequestHeader(value = "X-Service-Id", required = false) String serviceId, @RequestBody DocRcvRequestDto docRcvRequest) {
        try {
            if (StringUtil.isBlank(serviceId)) {
                throw new McpCommonJsonException("40001", "헤더 X-Service-Id 가 존재하지 않습니다.");
            }
            if (StringUtil.isBlank(docRcvRequest.getDocRcvId())) {
                throw new McpCommonJsonException("40002", "docRcvId 는 필수값입니다.");
            }
            DocRcvDetailDto docRcvDetail = documentReceiveService.getDocRcvDetail(docRcvRequest.getDocRcvId());
            validateDetail(docRcvDetail);

            ForScanResponseDto scanResponse = documentReceiveService.getForScan(docRcvRequest.getDocRcvId());
            return ResponseEntity.ok().body(new DocRcvResponseDto<>("20000", "", scanResponse));
        } catch (McpCommonJsonException e) {
            return ResponseEntity
                    .status(HttpStatus.valueOf(Integer.parseInt(e.getStatusCode().substring(0, 3))))
                    .body(new DocRcvResponseDto<Void>(e.getStatusCode(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new DocRcvResponseDto<Void>("40000", COMMON_EXCEPTION));
        }
    }

    /**
     * 서류 접수 업로드 완료 후 상태 업데이트
     * @param docRcvRequest
     * @return docRcvResponse
     */
    @RequestMapping(value = "/complete-upload", method = RequestMethod.POST)
    public ResponseEntity<DocRcvResponseDto<Void>> completeUpload(@RequestHeader(value = "X-Service-Id", required = false) String serviceId, @RequestBody DocRcvRequestDto docRcvRequest) {
        try {
            if (StringUtil.isBlank(serviceId)) {
                throw new McpCommonJsonException("40001", "헤더 X-Service-Id 가 존재하지 않습니다.");
            }
            if (StringUtil.isBlank(docRcvRequest.getDocRcvId())) {
                throw new McpCommonJsonException("40002", "docRcvId 는 필수값입니다.");
            }
            List<DocRcvItemDto> items = docRcvRequest.getItems();
            if (items == null || items.isEmpty()) {
                throw new McpCommonJsonException("40003", "등록 서류 목록이 존재하지 않습니다.");
            }
            if (StringUtil.isBlank(docRcvRequest.getScanId())) {
                throw new McpCommonJsonException("40006", "scanId 는 필수값입니다.");
            }
            items.forEach(item -> {
                if (StringUtil.isBlank(item.getItemId())) {
                    throw new McpCommonJsonException("40007", "itemId 가 존재하지 않습니다.");
                }
                if (item.getFiles() == null || item.getFiles().isEmpty()) {
                    throw new McpCommonJsonException("40011", "files 가 존재하지 않습니다.");
                }
                item.getFiles().forEach(file -> {
                    if (StringUtil.isBlank(file.getFileId())) {
                        throw new McpCommonJsonException("40008", "fileId 가 존재하지 않습니다.");
                    }
                    if (StringUtil.isBlank(file.getResultCode())) {
                        throw new McpCommonJsonException("40012", "resultCode 가 존재하지 않습니다.");
                    }
                });

                item.setItemSeq(Long.parseLong(item.getItemId()));
            });
            docRcvRequest.setServiceId(serviceId);
            documentReceiveService.completeUpload(docRcvRequest);

            return ResponseEntity.ok().body(new DocRcvResponseDto<>("20000", ""));
        } catch (McpCommonJsonException e) {
            return ResponseEntity
                    .status(HttpStatus.valueOf(Integer.parseInt(e.getStatusCode().substring(0, 3))))
                    .body(new DocRcvResponseDto<>(e.getStatusCode(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new DocRcvResponseDto<>("40000", COMMON_EXCEPTION));
        }
    }

    /**
     * 서류 접수 마스킹 처리 결과 수신
     * @param docRcvRequest
     * @return docRcvResponse
     */
    @RequestMapping(value = "/complete-masking", method = RequestMethod.POST)
    public ResponseEntity<DocRcvResponseDto<Void>> completeMasking(@RequestHeader(value = "X-Service-Id", required = false) String serviceId, @RequestBody DocRcvRequestDto docRcvRequest) {
        try {
            if (StringUtil.isBlank(serviceId)) {
                throw new McpCommonJsonException("40001", "헤더 X-Service-Id 가 존재하지 않습니다.");
            }
            if (StringUtil.isBlank(docRcvRequest.getDocRcvId())) {
                throw new McpCommonJsonException("40002", "docRcvId 는 필수값입니다.");
            }
            if (StringUtil.isBlank(docRcvRequest.getSuccessYn())) {
                throw new McpCommonJsonException("40009", "successYn 은 필수값입니다.");
            }
            if (!"Y".equals(docRcvRequest.getSuccessYn()) && !"N".equals(docRcvRequest.getSuccessYn())) {
                throw new McpCommonJsonException("40010", "successYn 이 유효하지 않습니다.{Y(성공) / N(실패)}");
            }
            docRcvRequest.setServiceId(serviceId);
            documentReceiveService.completeMasking(docRcvRequest);

            return ResponseEntity.ok().body(new DocRcvResponseDto<>("20000", ""));
        } catch (McpCommonJsonException e) {
            return ResponseEntity
                    .status(HttpStatus.valueOf(Integer.parseInt(e.getStatusCode().substring(0, 3))))
                    .body(new DocRcvResponseDto<>(e.getStatusCode(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new DocRcvResponseDto<>("40000", COMMON_EXCEPTION));
        }
    }
}
