package com.ktmmobile.mcp.cs.controller;

import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.SAMPLE_EXCEPTION;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ktmmobile.mcp.board.dto.BoardDto;
import com.ktmmobile.mcp.board.service.CsBorderService;
import com.ktmmobile.mcp.common.dto.db.NmcpCdDtlDto;
import com.ktmmobile.mcp.common.exception.McpCommonException;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import com.ktmmobile.mcp.common.util.ObjectUtils;
import com.ktmmobile.mcp.common.util.PageInfoBean;
import com.ktmmobile.mcp.common.util.ParseHtmlTagUtil;
import com.ktmmobile.mcp.common.util.StringUtil;

@Controller
public class CsPrivacyGuideController {

    private static Logger logger = LoggerFactory.getLogger(CsPrivacyGuideController.class);

    @Autowired
    private CsBorderService csBorderService;

    /**
     * 고객센터 > 고객지원 > 이용자 피해예방 가이드 > 피해예방정보
     * @param
     * @param model
     * @param bind
     * @return
     */
    @RequestMapping(value = {"/m/cs/privacyBoardList.do"})
    public String privacyBoardList(@ModelAttribute BoardDto boardDto, BindingResult bind, @ModelAttribute  PageInfoBean pageInfoBean, Model model, String tabIndex) {

        //    	UI0031 : 피해예방정보

        List<NmcpCdDtlDto> tabMenuList = NmcpServiceUtils.getCodeList("UI0031");
        // dtlCd=UI00310100
        // dtlCd=UI00310200
        // dtlCd=UI00310300,
        //dtlCdNm=피해사례별 예방법

        //    	int pageNo = boardDto.getPageNo();
        //    	if(pageNo==0) {
        //    		boardDto.setPageNo(1);
        //    	}
        String sbstCtg = "";
        if(tabMenuList !=null && !tabMenuList.isEmpty()) {
            sbstCtg = tabMenuList.get(0).getDtlCd();
        }
        boardDto.setSbstCtg(sbstCtg);
        if(pageInfoBean.getPageNo() == 0){
            pageInfoBean.setPageNo(1);
        }
        //임시 한페이지당 보여줄 리스트 수
        pageInfoBean.setRecordCount(10);

        //페이지 카운트
        int totalCount = csBorderService.getTotalCount(boardDto);
        pageInfoBean.setTotalCount(totalCount);

        //RowBound 필수값 세팅
        int skipResult  = (pageInfoBean.getPageNo() - 1) * pageInfoBean.getRecordCount();   // 셀렉트 하지 않고 뛰어넘을 만큼의 rownum
        int maxResult   = pageInfoBean.getRecordCount();  // Pagesize

        boardDto.setSearchNm(ParseHtmlTagUtil.percentToEscape(boardDto.getSearchNm()));
        List<BoardDto> privacyBoardList = csBorderService.selectBoardList(boardDto,0,999);
        boardDto.setSearchNm(ParseHtmlTagUtil.escapeToPercent(boardDto.getSearchNm()));

        int listCount = (pageInfoBean.getPageNo()-1) * pageInfoBean.getRecordCount() + privacyBoardList.size();

        model.addAttribute("listCount", listCount); //일반게시판
        model.addAttribute("privacyBoardList", privacyBoardList); //일반게시판
        model.addAttribute("pageInfoBean", pageInfoBean);
        model.addAttribute("pageNo",pageInfoBean.getPageNo());
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("boardDto",boardDto);
        model.addAttribute("tabMenuList", tabMenuList);
        model.addAttribute("tabIndex", tabIndex);

        return "/mobile/cs/privacy/privacyBoardList";
    }

    /**
     * 고객센터 > 고객지원 > 이용자 피해예방 가이드 > 피해예방정보 > 공지사항 상세
     * @param
     * @param model
     * @param bind
     * @return
    */
    @RequestMapping(value = {"/cs/privacyView.do","/m/cs/privacyView.do"})
    public String boardView(@ModelAttribute BoardDto boardDto,Model model ) {

        String rtnPageNm = "";

        if("Y".equals(NmcpServiceUtils.isMobile())){
            rtnPageNm = "/mobile/cs/privacy/privacyView";
        } else {
            rtnPageNm = "/portal/cs/privacy/privacyView";
        }

        if (boardDto.getBoardSeq()==0) {
               throw new McpCommonException(SAMPLE_EXCEPTION);
        }

        //조회수
        csBorderService.updateHit(boardDto.getBoardSeq());

        //공지 글 상세
        boardDto.setBoardCtgSeq(91);
        BoardDto rst = csBorderService.selectBoardArticle(boardDto);
        rst.setBoardContents(ParseHtmlTagUtil.convertHtmlchars(rst.getBoardContents()));

        //이전글 다음글 가져오기
        boardDto.setSearchNm(ParseHtmlTagUtil.percentToEscape(boardDto.getSearchNm()));
        BoardDto notiDto = csBorderService.selectIndex(boardDto);

        boardDto.setSearchNm(ParseHtmlTagUtil.escapeToPercent(boardDto.getSearchNm()));

        List<NmcpCdDtlDto> tabMenuList = NmcpServiceUtils.getCodeList("UI0031");
        model.addAttribute("tabMenuList",tabMenuList);
        model.addAttribute("boardDto",rst);
        model.addAttribute("notiDto",notiDto);
        model.addAttribute("searchDto",boardDto);
        return rtnPageNm;
    }


   /**
    * <pre>
    * 설명     :  고객센터 > 고객지원 > 이용자 피해예방 가이드 > 피해예방정보 > 최신피해 예방정보
    * 설명     :  고객센터 > 고객지원 > 이용자 피해예방 가이드 > 피해예방정보 > 피해사례별 예방법
    * @return
    * </pre>
    */
   @RequestMapping(value = "/m/cs/privacyEtcHtml.do")
   public String privacyEtcListAjax(@ModelAttribute BoardDto boardDto, BindingResult bind, @ModelAttribute  PageInfoBean pageInfoBean, Model model) {

       List<BoardDto> privacyEtcBoardList = csBorderService.selectBoardList(boardDto,0,100);
       String rtnPageNm = "";
       if(boardDto.getRownum()==1) { // 최신피해 예방정보
           rtnPageNm = "/mobile/cs/privacy/html/privacyRctDmgHtml";
       } else if(boardDto.getRownum()==2){ // 피해사례별 예방법
           rtnPageNm = "/mobile/cs/privacy/html/privacyDmgHtml";
       } else if(boardDto.getRownum()==3){ // 명의도용 방지
           rtnPageNm = "/mobile/cs/privacy/html/privacyAntiTheft";
       }

       model.addAttribute("privacyEtcBoardList",privacyEtcBoardList);
       return rtnPageNm;
   }


   /**
    * 고객센터 > 고객지원 > 이용자 피해예방 가이드 > 피해예방정보
    * @param
    * @param model
    * @param bind
    * @return
    */
   @RequestMapping(value = {"/cs/privacyBoardList.do"})
   public String pcPrivacyBoardList(@ModelAttribute BoardDto boardDto, BindingResult bind, @ModelAttribute  PageInfoBean pageInfoBean, Model model, String tabIndex) {

       // 최초 공지사항 코드 넣어준다.
       List<NmcpCdDtlDto> tabMenuList = NmcpServiceUtils.getCodeList("UI0031");
       String sbstCtg = "";
       if(tabMenuList !=null && !tabMenuList.isEmpty()) {
           sbstCtg = tabMenuList.get(0).getDtlCd();
       }
       boardDto.setSbstCtg(sbstCtg);
       //
       // 현재 페이지 번호 초기화
       if(pageInfoBean.getPageNo() == 0){
           pageInfoBean.setPageNo(1);
       }
       //임시 한페이지당 보여줄 리스트 수
       pageInfoBean.setRecordCount(10);

       //페이지 카운트
       int totalCount = csBorderService.getTotalCount(boardDto);
       pageInfoBean.setTotalCount(totalCount);

       //RowBound 필수값 세팅
       int skipResult = (pageInfoBean.getPageNo() - 1) * pageInfoBean.getRecordCount(); // 셀렉트 하지 않고 뛰어넘을 만큼의 rownum
       int maxResult = pageInfoBean.getRecordCount(); // Pagesize

       //게시판 리스트
       boardDto.setSearchNm(ParseHtmlTagUtil.percentToEscape(boardDto.getSearchNm()));
       List<BoardDto> privacyBoardList = csBorderService.selectBoardList(boardDto,skipResult,maxResult);
       boardDto.setSearchNm(ParseHtmlTagUtil.escapeToPercent(boardDto.getSearchNm()));

       model.addAttribute("boardDto",boardDto);
       model.addAttribute("privacyBoardList",privacyBoardList);
       model.addAttribute("pageInfoBean", pageInfoBean);
       model.addAttribute("tabMenuList", tabMenuList);
       model.addAttribute("totalCount", totalCount);
       model.addAttribute("tabIndex", tabIndex);

       return "/portal/cs/privacy/privacyBoardList";
   }

   /**
    * <pre>
    * 설명     :  고객센터 > 고객지원 > 이용자 피해예방 가이드 > 피해예방정보 > 최신피해 예방정보
    * 설명     :  고객센터 > 고객지원 > 이용자 피해예방 가이드 > 피해예방정보 > 피해사례별 예방법
    * @return
    * </pre>
    */
   @RequestMapping(value = "/cs/privacyEtcHtml.do")
   public String privacyEtcHtml(@ModelAttribute BoardDto boardDto, BindingResult bind, @ModelAttribute  PageInfoBean pageInfoBean, Model model) {

       List<BoardDto> privacyEtcBoardList = csBorderService.selectBoardList(boardDto,0,100);
       String rtnPageNm = "";
       if(boardDto.getRownum()==1) { // 최신피해 예방정보
           rtnPageNm = "/portal/cs/privacy/html/privacyRctDmgHtml";
       } else if(boardDto.getRownum()==2){ // 피해사례별 예방법 ==> pc 버전 나중에 다시 작업
           rtnPageNm = "/portal/cs/privacy/html/privacyDmgHtml";

       } else if(boardDto.getRownum()==3){ // 명의도용 방지
           rtnPageNm = "/portal/cs/privacy/html/privacyAntiTheft";
       }

       model.addAttribute("privacyEtcBoardList",privacyEtcBoardList);
       return rtnPageNm;
   }

   /**
    * 모바일버전  피해예방가이드 리스트페이지 진입
    * @param
    * @param model
    * @param bind
    * @return /cs/notice/m_noticeList
    */
   @RequestMapping(value = {"/cs/privacyGuideList.do","/m/cs/privacyGuideList.do"})
   public String privacyGuideList(@ModelAttribute BoardDto boardDto, BindingResult bind,	@ModelAttribute  PageInfoBean pageInfoBean, Model model) {

       String rtnPageNm = "";

       if("Y".equals(NmcpServiceUtils.isMobile())){
           rtnPageNm = "/mobile/cs/privacy/privacyGuideList";
       } else {
           rtnPageNm = "/portal/cs/privacy/privacyGuideList";
       }

       List<NmcpCdDtlDto> tabMenuList = NmcpServiceUtils.getCodeList("UI0032");
       model.addAttribute("tabMenuList",tabMenuList);
       return rtnPageNm;
   }

   /**
    * <pre>
    * 설명     :  리스트 조회 AJAX
    * @return
    * </pre>
    */
   @RequestMapping(value = "/m/cs/privacyGuideHtml.do")
   public String privacyGuideHtml(@ModelAttribute BoardDto boardDto, BindingResult bind, @ModelAttribute  PageInfoBean pageInfoBean, Model model) {

       String rtnPageNm = "";
       List<BoardDto> privacyEtcBoardList = csBorderService.selectBoardList(boardDto,0,100);
       if(boardDto.getRownum()==1) { // 동영상으로 보기
           rtnPageNm = "/mobile/cs/privacy/html/quickGuideHtml";
       } else if(boardDto.getRownum()==2){ // 웹툰으로 보기
           rtnPageNm = "/mobile/cs/privacy/html/privacyMovieHtml";
       } else if(boardDto.getRownum()==3){ // 피해예방 교육
           rtnPageNm = "/mobile/cs/privacy/html/privacyWebtoonHtml";
       } else if(boardDto.getRownum()==4){ // 피해예방 교육
           rtnPageNm = "/mobile/cs/privacy/html/privacyPreventionEduHtml";
       }
       model.addAttribute("privacyEtcBoardList",privacyEtcBoardList);

       return rtnPageNm;
   }

   /**
    * <pre>
    * 설명     :  리스트 조회 AJAX
    * @return
    * </pre>
    */
   @RequestMapping(value = "/cs/privacyGuideHtml.do")
   public String pcPrivacyGuideHtml(@ModelAttribute BoardDto boardDto, BindingResult bind, @ModelAttribute  PageInfoBean pageInfoBean, Model model) {

       String rtnPageNm = "";

       List<BoardDto> privacyEtcBoardList = csBorderService.selectBoardList(boardDto,0,100);

       if(boardDto.getRownum()==1) { // 동영상으로 보기
           rtnPageNm = "/portal/cs/privacy/html/quickGuideHtml";
       } else if(boardDto.getRownum()==2){ // 웹툰으로 보기
           rtnPageNm = "/portal/cs/privacy/html/privacyMovieHtml";
       } else if(boardDto.getRownum()==3){ // 피해예방 교육
           rtnPageNm = "/portal/cs/privacy/html/privacyWebtoonHtml";
       } else if(boardDto.getRownum()==4){ // 피해예방 교육
           rtnPageNm = "/portal/cs/privacy/html/privacyPreventionEduHtml";
       }

       model.addAttribute("privacyEtcBoardList",privacyEtcBoardList);
       return rtnPageNm;
   }

}