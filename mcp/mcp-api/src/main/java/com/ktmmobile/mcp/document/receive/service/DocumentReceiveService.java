package com.ktmmobile.mcp.document.receive.service;

import com.ktmmobile.mcp.cmmn.exception.McpCommonJsonException;
import com.ktmmobile.mcp.cmmn.util.StringUtil;
import com.ktmmobile.mcp.document.receive.dto.*;
import com.ktmmobile.mcp.document.receive.mapper.DocumentReceiveMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DocumentReceiveService {
    public static final String DOC_RCV_PROC_STATUS_NOT_RECEIVED = "N";
    public static final String DOC_RCV_PROC_STATUS_RETRY_REQUEST = "R";
    public static final String DOC_RCV_PROC_STATUS_MASKING_PROCESSING = "M";
    public static final String DOC_RCV_URL_STATUS_ACTIVATE = "A";
    public static final String DOC_RCV_ITEM_STATUS_PENDING = "P";
    public static final String FILE_RESULT_CODE_SUCCESS = "0000";

    @Autowired
    private DocumentReceiveMapper documentReceiveMapper;

    public DocRcvDetailDto getDocRcvDetail(String docRcvId) {
        return documentReceiveMapper.getDocRcvDetail(docRcvId);
    }

    public ForScanResponseDto getForScan(String docRcvId) {
        ForScanResponseDto forScanResponse = documentReceiveMapper.getForScan(docRcvId);
        if (forScanResponse == null) {
            throw new McpCommonJsonException("40004", "대상이 존재하지 않습니다.");
        }
        if (StringUtil.isBlank(forScanResponse.getCustNm())) {
            throw new McpCommonJsonException("40005", "고객 정보를 찾을 수 없습니다.");
        }
        return forScanResponse;
    }

    @Transactional(rollbackFor = Exception.class)
    public void completeUpload(DocRcvRequestDto docRcvRequest) {
        DocRcvDetailDto docRcvDetail = this.getDocRcvDetail(docRcvRequest.getDocRcvId());
        validateDetail(docRcvDetail);

        List<DocRcvItemDto> docRcvitemList = docRcvDetail.getItemList();
        Map<Long, DocRcvItemDto> itemMap = docRcvitemList.stream()
                        .collect(Collectors.toMap(DocRcvItemDto::getItemSeq, Function.identity()));

        for (DocRcvItemDto item : docRcvRequest.getItems()) {
            if (!itemMap.containsKey(item.getItemSeq())) {
                throw new McpCommonJsonException("42211", "해당 업무의 서류만 서류 등록 가능합니다.(" + item.getItemSeq() + ")");
            }
            DocRcvItemDto docRcvItem = itemMap.get(item.getItemSeq());
            if (!DOC_RCV_ITEM_STATUS_PENDING.equals(docRcvItem.getStatus())) {
                throw new McpCommonJsonException("42212", "접수요청 서류만 서류 등록 가능합니다.(" + item.getItemSeq() + ")");
            }
        }

        if (!StringUtil.isBlank(docRcvDetail.getScanId())) {
            if (!docRcvRequest.getScanId().equals(docRcvDetail.getScanId())) {
                throw new McpCommonJsonException("42213", "scanId 가 일치하지 않습니다.");
            }
        } else {
            documentReceiveMapper.updateDocRcvScanId(docRcvRequest);
        }
        documentReceiveMapper.insertDocRcvStatusCompleteUpload(docRcvRequest);

        for (DocRcvItemDto item : docRcvRequest.getItems()) {
            DocRcvItemDto docRcvItem = itemMap.get(item.getItemSeq());
            item.setDocRcvId(docRcvItem.getDocRcvId());
            item.setItemId(docRcvItem.getItemId());
            item.setServiceId(docRcvRequest.getServiceId());

            this.mergeItemUploadComplete(item);
            this.mergeItemUploadFail(item);
        }

        documentReceiveMapper.updateItemListPendingToNotReceive(docRcvRequest);
    }

    public void completeMasking(DocRcvRequestDto docRcvRequest) {
        DocRcvDetailDto docRcvDetail = this.getDocRcvDetail(docRcvRequest.getDocRcvId());
        if (docRcvDetail == null) {
            throw new McpCommonJsonException("40004", "대상이 존재하지 않습니다.");
        }

        DocRcvStatusDto docRcvStatusDto = docRcvDetail.getDocRcvStatusDto();
        if (!DOC_RCV_PROC_STATUS_MASKING_PROCESSING.equals(docRcvStatusDto.getProcStatus())) {
            throw new McpCommonJsonException("42214", "마스킹 처리 중 상태만 마스킹 처리 결과 수신 가능합니다.");
        }

        if ("Y".equals(docRcvRequest.getSuccessYn())) {
            documentReceiveMapper.insertDocRcvStatusMaskingSuccess(docRcvRequest);
        } else {
            documentReceiveMapper.insertDocRcvStatusMaskingFail(docRcvRequest);
        }
    }

    private void mergeItemUploadComplete(DocRcvItemDto item) {
        DocRcvItemDto itemDto = item.deepCopy();
        List<DocRcvItemFileDto> completeFileList = itemDto.getFiles().stream()
                .filter(file -> FILE_RESULT_CODE_SUCCESS.equals(file.getResultCode()))
                .collect(Collectors.toList());

        if (completeFileList.isEmpty()) {
            return;
        }

        if (this.isRemainPendingItem(itemDto.getItemSeq())) {
            documentReceiveMapper.updateItemUploadComplete(itemDto);
        } else {
            documentReceiveMapper.insertItemUploadComplete(itemDto);
        }
        itemDto.setFiles(completeFileList);
        documentReceiveMapper.insertDocRcvItemFileList(itemDto);
    }

    private void mergeItemUploadFail(DocRcvItemDto item) {
        DocRcvItemDto itemDto = item.deepCopy();
        List<DocRcvItemFileDto> failFileList = itemDto.getFiles().stream()
                .filter(file -> !FILE_RESULT_CODE_SUCCESS.equals(file.getResultCode()))
                .collect(Collectors.toList());

        if (failFileList.isEmpty()) {
            return;
        }

        if (this.isRemainPendingItem(itemDto.getItemSeq())) {
            documentReceiveMapper.updateItemUploadFail(itemDto);
        } else {
            documentReceiveMapper.insertItemUploadFail(itemDto);
        }
        itemDto.setFiles(failFileList);
        documentReceiveMapper.insertDocRcvItemFileList(itemDto);
    }

    public static void validateDetail(DocRcvDetailDto docRcvDetail) {
        if (docRcvDetail == null) {
            throw new McpCommonJsonException("40004", "대상이 존재하지 않습니다.");
        }

        DocRcvStatusDto docRcvStatusDto = docRcvDetail.getDocRcvStatusDto();
        if (!DOC_RCV_PROC_STATUS_NOT_RECEIVED.equals(docRcvStatusDto.getProcStatus()) && !DOC_RCV_PROC_STATUS_RETRY_REQUEST.equals(docRcvStatusDto.getProcStatus())) {
            throw new McpCommonJsonException("42201", "미접수, 재접수요청 상태만 서류 등록 가능합니다.");
        }

        DocRcvUrlDto docRcvUrlDto = docRcvDetail.getDocRcvUrlDto();
        if (!DOC_RCV_URL_STATUS_ACTIVATE.equals(docRcvUrlDto.getStatus())) {
            throw new McpCommonJsonException("42202", "URL이 만료되었습니다.");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime localdatetime = LocalDateTime.now();
        LocalDateTime issueDateTime = LocalDateTime.parse(docRcvUrlDto.getIssueDt(), formatter);
        LocalDateTime expireDateTime = LocalDateTime.parse(docRcvUrlDto.getExpireDt(), formatter);
        if (issueDateTime.isAfter(localdatetime) || expireDateTime.isBefore(localdatetime)) {
            throw new McpCommonJsonException("42202", "URL이 만료되었습니다.");
        }

        DocRcvUrlOtpDto docRcvUrlOtpDto = docRcvDetail.getDocRcvUrlOtpDto();
        String lastAuthDt = docRcvUrlOtpDto.getLastAuthDt();
        if (StringUtil.isBlank(lastAuthDt)) {
            throw new McpCommonJsonException("42203", "신규 인증번호로 다시 인증하여야 합니다.");
        }

        LocalDateTime lastAuthDateTime = LocalDateTime.parse(lastAuthDt, formatter);
        LocalDateTime authLimitDateTime = localdatetime.minusMinutes(30);
        if (lastAuthDateTime.isBefore(authLimitDateTime)) {
            throw new McpCommonJsonException("42204", "인증 완료 후 30분이 초과하였습니다.");
        }
    }

    private boolean isRemainPendingItem(long itemSeq) {
        return documentReceiveMapper.countDocRcvItemPending(itemSeq) > 0;
    }
}
