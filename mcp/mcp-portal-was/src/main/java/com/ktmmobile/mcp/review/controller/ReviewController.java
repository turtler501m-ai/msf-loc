package com.ktmmobile.mcp.review.controller;

import com.ktmmobile.mcp.common.dto.UserSessionDto;
import com.ktmmobile.mcp.common.dto.db.NmcpCdDtlDto;
import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
import com.ktmmobile.mcp.common.util.DateTimeUtil;
import com.ktmmobile.mcp.common.util.MaskingUtil;
import com.ktmmobile.mcp.common.util.PageInfoBean;
import com.ktmmobile.mcp.common.util.SessionUtils;
import com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto;
import com.ktmmobile.mcp.mypage.service.MypageService;
import com.ktmmobile.mcp.review.dto.ReviewDto;
import com.ktmmobile.mcp.review.service.ReviewService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

import static com.ktmmobile.mcp.common.constants.Constants.AJAX_SUCCESS;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.*;

@Controller
public class ReviewController {

    private static Logger logger = LoggerFactory.getLogger(ReviewController.class);

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private MypageService mypageService;

    /**
     * 설명 : 우수 사용 후기 리스트 호출Ajax
     */
    @RequestMapping(value = "/review/bestReviewListAjax.do")
    @ResponseBody
    public Map<String,Object> bestReviewListAjax(ReviewDto reviewDto) {
        Map<String, Object> rtnMap = new HashMap<>();

        List<ReviewDto> bestReviewDtoList = reviewService.selectBestReviewList(reviewDto);
        if (bestReviewDtoList != null && !bestReviewDtoList.isEmpty()) {
            for (ReviewDto dto : bestReviewDtoList) {
                dto.setRegstDt(DateTimeUtil.changeFormat(dto.getRegstDttm(), "yyyy.MM.dd"));
                dto.setMkRegstNm(MaskingUtil.getMaskedName(dto.getRegstNm()));
                dto.setRegstNm("");
            }
            bestReviewDtoList = bestReviewDtoList.stream()
                    .sorted(Comparator.comparing(ReviewDto::getPrizeRnk, Comparator.nullsLast(Comparator.naturalOrder())).reversed()
                            .thenComparing(ReviewDto::getNotiYn).reversed())
                    .limit(3).collect(Collectors.toList());
        }

        if (bestReviewDtoList != null) {
            logger.debug("bestReviewDtoList len :{}", bestReviewDtoList.size());
            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
            rtnMap.put("RESULT_MSG", "성공");
        } else {
            logger.debug("bestReviewDtoList is null");
        }

        rtnMap.put("bestReviewDtoList", bestReviewDtoList);
        return rtnMap;
    }

    /**
     * 설명 : 사용 후기 리스트 호출Ajax
     * @Date 2024.12.02
     * @param request
     * @param reviewDto
     * @param onlyMine
     * @param pageInfoBean
     * @return
     */
    @RequestMapping(value = "/review/reviewListAjax.do")
    @ResponseBody
    public Map<String, Object> reviewListAjax(HttpServletRequest request
                                              , @ModelAttribute ReviewDto reviewDto
                                              , PageInfoBean pageInfoBean
                                              , @RequestParam("onlyMine") boolean onlyMine) {
        HashMap<String, Object> rtnMap = new HashMap<>();
        UserSessionDto userSession = SessionUtils.getUserCookieBean();

        // 로그인 여부, 내 후기만 보기 설정
        List<String> contractNumList = new ArrayList<>();
        if (userSession != null && !StringUtils.isEmpty(userSession.getUserId())) {
            List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
            contractNumList = Optional.ofNullable(cntrList).orElseGet(Collections::emptyList).stream()
                    .map(McpUserCntrMngDto::getContractNum)
                    .collect(Collectors.toList());
        }
        if (onlyMine) {
            reviewDto.setContractNumList(contractNumList);
        }

        /* 현재 페이지 번호 초기화 */
        if(pageInfoBean.getPageNo() == 0){
            pageInfoBean.setPageNo(1);
        }
        /* 한페이지당 보여줄 리스트 수 */
        pageInfoBean.setRecordCount(request.getParameter("recordCount") == null ? 20 : pageInfoBean.getRecordCount()); // 폰 상세보기 리뷰에서 10개씩 보여줘야 해서 수정함
        /* 카운터 조회 */
        int total = reviewService.selectReviewTotalCnt(reviewDto);
        pageInfoBean.setTotalCount(total);
        int skipResult = (pageInfoBean.getPageNo() - 1) * pageInfoBean.getRecordCount();	//셀렉트 하지 않고 뛰어넘을 만큼의 rownum
        int maxResult = pageInfoBean.getRecordCount();	// Pagesize

        List<ReviewDto> reviewDtoList = reviewService.selectReviewListByNoti(reviewDto, skipResult, maxResult);

        if (reviewDtoList != null && !reviewDtoList.isEmpty()) {
            for (ReviewDto dto : reviewDtoList) {
                dto.setRegstDt(DateTimeUtil.changeFormat(dto.getRegstDttm(), "yyyy.MM.dd"));
                dto.setMkRegstNm(MaskingUtil.getMaskedName(dto.getRegstNm()));
                dto.setRegstNm("");
                if (contractNumList.contains(dto.getContractNum())) {
                    dto.setIsOwn("Y");
                }
            }

            reviewDtoList = reviewDtoList.stream()
                    .sorted(Comparator.comparing(ReviewDto::getPrizeRnk, Comparator.nullsLast(Comparator.naturalOrder())).reversed()
                            .thenComparing(ReviewDto::getNotiYn).reversed())
                    .collect(Collectors.toList());
        }

        // reviewType으로 해당 리뷰 유형의 리뷰 이벤트 코드 목록 조회
        List<NmcpCdDtlDto> reviewEventCdList = reviewService.getReviewEventCdList(reviewDto.getReviewType());
        int reviewTypeTotal = 0;
        if (reviewEventCdList != null && !reviewEventCdList.isEmpty()) {
            for (NmcpCdDtlDto reviewEventCd : reviewEventCdList) {
                reviewDto.setReviewTypeDtl(reviewEventCd.getDtlCd());
                int cnt = reviewService.selectReviewTotalCnt(reviewDto);
                reviewTypeTotal += cnt;
                reviewEventCd.setEventCdCtn(cnt);
            }
            reviewDto.setReviewTypeDtl(null);
        }
        rtnMap.put("reviewTypeTotal", reviewTypeTotal);
        rtnMap.put("reviewEventCdList", reviewEventCdList);

        rtnMap.put("pageInfoBean", pageInfoBean);
        rtnMap.put("total", total);
        rtnMap.put("reviewDtoList", reviewDtoList);
        rtnMap.put("contractNumList", contractNumList);

        return rtnMap;
    }

    /**
     * 설명 : 사용 후기 삭제 Ajax
     * @Date 2024.12.11
     * @param reviewSeq
     * @return
     */
    @RequestMapping(value = "/review/deleteReviewAjax.do")
    @ResponseBody
    public Map<String, Object> deleteReviewAjax(@RequestParam(value = "reviewSeq") int reviewSeq) {
        Map<String, Object> rtnMap = new HashMap<>();

        // 로그인 여부
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if (userSession == null && StringUtils.isEmpty(userSession.getUserId())) {
            throw new McpCommonJsonException("00001", NO_SESSION_EXCEPTION);
        }

        // 사용자의 리뷰인지 확인
        if (!reviewService.checkOwnReview(reviewSeq)) {
            throw new McpCommonJsonException("00002", INVALID_REFERER_EXCEPTION);
        }

        // 리뷰 삭제
        if (!reviewService.deleteReview(reviewSeq)) {
            throw new McpCommonJsonException("00003", DB_EXCEPTION);
        }

        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        return rtnMap;
    }
}
