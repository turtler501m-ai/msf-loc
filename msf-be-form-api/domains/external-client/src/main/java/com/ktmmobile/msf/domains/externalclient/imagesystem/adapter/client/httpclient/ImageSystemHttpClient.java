package com.ktmmobile.msf.domains.externalclient.imagesystem.adapter.client.httpclient;

import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import com.ktmmobile.msf.domains.externalclient.imagesystem.application.dto.ImageSystemPdfUploadResponse;

/**
 * 이미지 시스템 선언형 HTTP client
 */
@HttpExchange
public interface ImageSystemHttpClient {

    String JSON_EUC_KR_VALUE = MediaType.APPLICATION_JSON_VALUE + ";charset=EUC-KR";

    /**
     * 스마트 서식지 파일 업로드
     */
    @PostExchange(
        url = "/webscan/pdfUpload.do",
        contentType = MediaType.MULTIPART_FORM_DATA_VALUE,
        accept = JSON_EUC_KR_VALUE
    )
    ImageSystemPdfUploadResponse uploadPdf(
        @RequestParam("method") String method,
        @RequestParam("DOC_CD") String docCd,
        @RequestParam("PARENT_SCAN_ID") String parentScanId,
        @RequestParam("FILE_ID") String fileId,
        @RequestParam("WORK_CD") String workCd,
        @RequestParam("WORK_NM") String workNm,
        @RequestParam("RGST_PRSN_ID") String rgstPrsnId,
        @RequestParam("ORG_ID") String orgId,
        @RequestParam("CUST_NM") String custNm,
        @RequestParam("MEMO") String memo,
        @RequestParam("ONLINE_YN") String onlineYn,
        @RequestParam("COMPANY_ID") String companyId,
        @RequestBody MultiValueMap<String, Object> form
    );
}
