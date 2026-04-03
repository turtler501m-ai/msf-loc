package com.ktmmobile.msf.system.common.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ktmmobile.mcp.appform.dto.FormDtlDTO;

public class PagingUtil {

    @Deprecated
	private static Logger logger = LoggerFactory.getLogger(PagingUtil.class);

	Map<String, Object> pagingMap = null;

	public Map<String, Object> pageCalculator(int pageInput, int totalCount) {

		pagingMap = new HashMap<String, Object>();

		int pageNo = pageInput;

		int pageSize =10;

		if (pageNo == 0)
			pageNo = 1;

	    int finalPage = (totalCount + (pageSize - 1)) / pageSize; // 마지막 페이지
        if (pageNo > finalPage) pageNo=finalPage; // 기본 값 설정

        if (pageNo < 0 || pageNo > finalPage) pageNo = 1; // 현재 페이지 유효성 체크

        boolean isNowFirst = pageNo == 1 ? true : false; // 시작 페이지 (전체)
        boolean isNowFinal = pageNo == finalPage ? true : false; // 마지막 페이지 (전체)

        int startPage = ((pageNo - 1) / 10) * 10 + 1; // 시작 페이지 (페이징 네비 기준)
        int endPage = startPage + 10 - 1; // 끝 페이지 (페이징 네비 기준)

        if (endPage > finalPage) { // [마지막 페이지 (페이징 네비 기준) > 마지막 페이지] 보다 큰 경우
            endPage = finalPage;
        }

        int firstPageNo=1; // 첫 번째 페이지 번호
        int prevPageNo; // 이전 페이지 번호
        int nextPageNo; // 다음 페이지 번호

        if (isNowFirst) {
            prevPageNo=1; // 이전 페이지 번호
        } else {
            prevPageNo=((pageNo - 1) < 1 ? 1 : (pageNo - 1)); // 이전 페이지 번호
        }

        int startPageNo=startPage; // 시작 페이지 (페이징 네비 기준)
        int endPageNo=endPage; // 끝 페이지 (페이징 네비 기준)

        if (isNowFinal) {
            nextPageNo=finalPage; // 다음 페이지 번호
        } else {
            nextPageNo=((pageNo + 1) > finalPage ? finalPage : (pageNo + 1)); // 다음 페이지 번호
        }

        int finalPageNo=finalPage; // 마지막 페이지 번호

        int pagingSize =  pageNo == 1 ?  1: pageSize;

        pagingMap.put("pageNo", pageNo);
        pagingMap.put("startPageNo", startPageNo);
        pagingMap.put("endPageNo", endPageNo);
        pagingMap.put("firstPageNo", firstPageNo);
        pagingMap.put("prevPageNo", prevPageNo);
        pagingMap.put("nextPageNo", nextPageNo);
        pagingMap.put("finalPageNo", finalPageNo);
        pagingMap.put("pageSize", pageSize);
        pagingMap.put("pagingSize", pagingSize);
        pagingMap.put("totalCount", totalCount);

		return pagingMap;
	}

	public FormDtlDTO parseDataFormDtlDTO(FormDtlDTO formDtlDTO) {
		if (pagingMap == null) {
			return formDtlDTO;
		}
		formDtlDTO.setPagingStartNo((Integer)pagingMap.get("pageNo"));
		formDtlDTO.setPagingEndNo((Integer)pagingMap.get("pageSize"));
		formDtlDTO.setPagingSize((Integer)pagingMap.get("pagingSize"));
		return formDtlDTO;
	}

}