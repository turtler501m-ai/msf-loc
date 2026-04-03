package com.ktmmobile.msf.system.common.dto.db;

import static com.ktmmobile.msf.system.common.constants.Constants.BOARD_CTG_711_INFO;
import static com.ktmmobile.msf.system.common.constants.Constants.BOARD_CTG_DIRECT_INFO;
import static com.ktmmobile.msf.system.common.constants.Constants.BOARD_CTG_GS25_INFO;
import static com.ktmmobile.msf.system.common.constants.Constants.BOARD_CTG_GUIDE;
import static com.ktmmobile.msf.system.common.constants.Constants.BOARD_CTG_MINISTOP_INFO;
import static com.ktmmobile.msf.system.common.constants.Constants.BOARD_CTG_WIRE_INFO;
import static com.ktmmobile.msf.system.common.constants.Constants.GROUP_CODE_711_CTG;
import static com.ktmmobile.msf.system.common.constants.Constants.GROUP_CODE_DIRECTFAQB_CTG;
import static com.ktmmobile.msf.system.common.constants.Constants.GROUP_CODE_GS25_CTG;
import static com.ktmmobile.msf.system.common.constants.Constants.GROUP_CODE_MINISTOP_CTG;
import static com.ktmmobile.msf.system.common.constants.Constants.GROUP_CODE_SERVICE_GUIDE_CTG;
import static com.ktmmobile.msf.system.common.constants.Constants.GROUP_CODE_WIRE_CTG;
import java.io.Serializable;
import java.util.Date;
import com.ktmmobile.msf.system.common.util.NmcpServiceUtils;

/**
 * <pre>
 * 프로젝트 : kt M mobile
 * 파일명   : NmcpBoardBasDto.java
 * 날짜     : 2017. 7. 03. 오후 2:28:01
 * 작성자   : papier
 * 설명     : 통합게시판기본 (NMCP_BOARD_BAS)
 * </pre>
 */
public class NmcpBoardBasDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 카테고리일련번호
     * 51        공지사항
     * 52        고객센터 &amp;amp;gt;  1:1 상담
     * 53        각종신청서식
     * 54        이벤트 &amp;amp;amp;gt; 이벤트 게시판
     * 55        M mobile > 언론보도
     * 56        이벤트 &gt; 당첨자 발표/조회
     * 57        고객센터 FAQ
     * 59        고객서비스 안내
     * 58        자료실
     * 61        요금제/부가서비스 > 공시지원금
     * 62        테스트용
     * 63        이벤트&amp;gt;쿠폰
     * 64        이벤트 &gt; 쿠폰
     * 65        테스트2
     * 70        보도자료 관리
     * 80        팝업관리
     * 90        배너관리
     * */
    private int boardCtgSeq;
    /** 게시물카테고리 */
    private String sbstCtg;
    /** 게시물인련번호 */
    private int boardSeq;
    /** 게시물제목 */
    private String boardSubject;
    /** 노출여부 */
    private String viewYn;
    /** 공지여부 */
    private String notiYn;
    /** 게시물내용 */
    private String boardContents;
    /** 작성자 */
    private String boardWriterNm;
    /** 작성일 */
    private Date boardWriteDt;
    /** 첨부여부 */
    private String boardAttYn;
    /** 조회수 */
    private int boardHitCnt;
    /** 생성자아이디 */
    private String cretId;
    /** 수정자아이디 */
    private String amdId;
    /** 생성일시 */
    private Date cretDt;
    /** 수정일시 */
    private Date amdDt;

    private String searchNm ;
    
    private int[] boardCtgSeqArray;    
   
	public int[] getBoardCtgSeqArray() {
		return boardCtgSeqArray;
	}
	public void setBoardCtgSeqArray(int[] boardCtgSeqArray) {
		this.boardCtgSeqArray = boardCtgSeqArray;
	}
	public int getBoardCtgSeq() {
        return boardCtgSeq;
    }
    public void setBoardCtgSeq(int boardCtgSeq) {
        this.boardCtgSeq = boardCtgSeq;
    }
    public String getSbstCtg() {
        return sbstCtg;
    }
    public void setSbstCtg(String sbstCtg) {
        this.sbstCtg = sbstCtg;
    }

    public String getSbstCtgNm() {
        if (BOARD_CTG_GUIDE == boardCtgSeq) {
            return  NmcpServiceUtils.getCodeNm(GROUP_CODE_SERVICE_GUIDE_CTG,sbstCtg);
        } else if(BOARD_CTG_DIRECT_INFO == boardCtgSeq) {
        	return  NmcpServiceUtils.getCodeNm(GROUP_CODE_DIRECTFAQB_CTG,sbstCtg);
        } else if(BOARD_CTG_MINISTOP_INFO == boardCtgSeq) {
        	return  NmcpServiceUtils.getCodeNm(GROUP_CODE_MINISTOP_CTG,sbstCtg);
        } else if(BOARD_CTG_WIRE_INFO == boardCtgSeq) {
        	return  NmcpServiceUtils.getCodeNm(GROUP_CODE_WIRE_CTG,sbstCtg);
        } else if(BOARD_CTG_GS25_INFO == boardCtgSeq) {
        	return  NmcpServiceUtils.getCodeNm(GROUP_CODE_GS25_CTG,sbstCtg);
        } else if(BOARD_CTG_711_INFO == boardCtgSeq) {
        	return  NmcpServiceUtils.getCodeNm(GROUP_CODE_711_CTG,sbstCtg);
        } else {
            return sbstCtg;
        }
    }

    public int getBoardSeq() {
        return boardSeq;
    }
    public void setBoardSeq(int boardSeq) {
        this.boardSeq = boardSeq;
    }
    public String getBoardSubject() {
        return boardSubject;
    }
    public void setBoardSubject(String boardSubject) {
        this.boardSubject = boardSubject;
    }
    public String getViewYn() {
        return viewYn;
    }
    public void setViewYn(String viewYn) {
        this.viewYn = viewYn;
    }
    public String getNotiYn() {
        return notiYn;
    }
    public void setNotiYn(String notiYn) {
        this.notiYn = notiYn;
    }
    public String getBoardContents() {
        return boardContents;
    }
    public void setBoardContents(String boardContents) {
        this.boardContents = boardContents;
    }
    public String getBoardWriterNm() {
        return boardWriterNm;
    }
    public void setBoardWriterNm(String boardWriterNm) {
        this.boardWriterNm = boardWriterNm;
    }
    public Date getBoardWriteDt() {
        return boardWriteDt;
    }
    public void setBoardWriteDt(Date boardWriteDt) {
        this.boardWriteDt = boardWriteDt;
    }
    public String getBoardAttYn() {
        return boardAttYn;
    }
    public void setBoardAttYn(String boardAttYn) {
        this.boardAttYn = boardAttYn;
    }
    public int getBoardHitCnt() {
        return boardHitCnt;
    }
    public void setBoardHitCnt(int boardHitCnt) {
        this.boardHitCnt = boardHitCnt;
    }
    public String getCretId() {
        return cretId;
    }
    public void setCretId(String cretId) {
        this.cretId = cretId;
    }
    public String getAmdId() {
        return amdId;
    }
    public void setAmdId(String amdId) {
        this.amdId = amdId;
    }
    public Date getCretDt() {
        return cretDt;
    }
    public void setCretDt(Date cretDt) {
        this.cretDt = cretDt;
    }
    public Date getAmdDt() {
        return amdDt;
    }
    public void setAmdDt(Date amdDt) {
        this.amdDt = amdDt;
    }
    public String getSearchNm() {
        return searchNm;
    }
    public void setSearchNm(String searchNm) {
        this.searchNm = searchNm;
    }





}
