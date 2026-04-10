package com.ktmmobile.mcp.board.dto;

import java.util.Date;
import java.io.Serializable;


public class BoardDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private int rownum;
    private int boardCtgSeq;
    private String sbstCtg;
    private String sbstSubCtgCd;
    private int boardSeq;
    private String boardSubject;
    private String viewYn;
    private String notiYn;
    private String boardContents;
    private String boardWriterNm;
    private String boardWriteDt;
    private String boardWriteTm;
    private String boardAttYn;
    private int boardHitCnt;
    private String imgNm;
    private String cretId;
    private String amdId;
    private String cretDt;
    private String amdDt;
    private String sbstCtgNm;
    private String boardCtgNm;
    private String searchNm;
    private String searchOpt;
    private int rstCnt;
    private String attSeq;
    private String fileType;
    private int fileCapa;
    private String filePathNM;
    private String[] editorPhotoSeqArr; // 에디터 사진업로드 seqarr
    private String ctgVal;	// front faq 게시판에서 쓰고있으니  지우지마삼
    private String rnum;
    private int nextSeq;
    private String nextSubject;
    private Date nextDt;
    private int preSeq;
    private String preSubject;
    private Date preDt;
    private int downloadCount;
    //페이징
    private int skipResult;   // 셀렉트 하지 않고 뛰어넘을 만큼의 rownum
    private int maxResult;  // Pagesize
    private int pageNo = 0;		//현재 넘버값
    private int prevIndex; //이전글
    private int nextIndex; //다음글

    //페이징
    private int pagingPosition;		//<<  <   1 2 3 4 5   >  >>
    private int pagingStartNo;		//페이지네이션 시작 변수
    private int pagingEndNo;		//페이지네이션 끝 변수
    private int pagingStart;		//페이지 처음 <<
    private int pagingFront;		//페이지 앞을호 <
    private int pagingNext;			//페이지 다음 >
    private int pagingEnd;			//페이지 마지막 >>
    private int pagingSize;			//페이지 사이즈>>
    private String searchYn;
    private int boardNum;

    private String dstnctKey;	// 리스트 요청 구분

    private String fileDownFlag = "BOARD";  //파일 다운로드 구분
    private int selectCnt = 0;
    private String charDt;



    public String getCharDt() {
        return charDt;
    }
    public void setCharDt(String charDt) {
        this.charDt = charDt;
    }
    public String getRnum() {
        return rnum;
    }
    public void setRnum(String rnum) {
        this.rnum = rnum;
    }
    public int getSelectCnt() {
        return selectCnt;
    }
    public void setSelectCnt(int selectCnt) {
        this.selectCnt = selectCnt;
    }
    public String getFileDownFlag() {
        return fileDownFlag;
    }
    public void setFileDownFlag(String fileDownFlag) {
        this.fileDownFlag = fileDownFlag;
    }

    public String getSearchYn() {
        return searchYn;
    }
    public void setSearchYn(String searchYn) {
        this.searchYn = searchYn;
    }
    public int getRownum() {
        return rownum;
    }
    public void setRownum(int rownum) {
        this.rownum = rownum;
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

    public String getSbstSubCtgCd() {
        return sbstSubCtgCd;
    }
    public void setSbstSubCtgCd(String sbstSubCtgCd) {
        this.sbstSubCtgCd = sbstSubCtgCd;
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
    public String getImgNm() {
        return imgNm;
    }
    public void setImgNm(String imgNm) {
        this.imgNm = imgNm;
    }
    public static long getSerialversionuid() {
        return serialVersionUID;
    }
    public String getBoardContents() {
        if (boardContents != null) {

            boardContents = boardContents.replaceAll("&amp;", "&");
            boardContents = boardContents.replaceAll("amp;", "");// &amp;amp;  중복으로 존재 할때.. 삭제를 위해 한번 더.. 처리

        }
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
    public String getBoardWriteDt() {
        return boardWriteDt;
    }
    public void setBoardWriteDt(String boardWriteDt) {
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
    public String getCretDt() {
        return cretDt;
    }
    public void setCretDt(String cretDt) {
        this.cretDt = cretDt;
    }
    public String getAmdDt() {
        return amdDt;
    }
    public void setAmdDt(String amdDt) {
        this.amdDt = amdDt;
    }
    public String getSbstCtgNm() {
        return sbstCtgNm;
    }
    public void setSbstCtgNm(String sbstCtgNm) {
        this.sbstCtgNm = sbstCtgNm;
    }
    public String getBoardCtgNm() {
        return boardCtgNm;
    }
    public void setBoardCtgNm(String boardCtgNm) {
        this.boardCtgNm = boardCtgNm;
    }
    public String getSearchNm() {
        return searchNm;
    }
    public void setSearchNm(String searchNm) {
        this.searchNm = searchNm;
    }
    public String getSearchOpt() {
        return searchOpt;
    }
    public void setSearchOpt(String searchOpt) {
        this.searchOpt = searchOpt;
    }
    public int getRstCnt() {
        return rstCnt;
    }
    public void setRstCnt(int rstCnt) {
        this.rstCnt = rstCnt;
    }
    public String getAttSeq() {
        return attSeq;
    }
    public void setAttSeq(String attSeq) {
        this.attSeq = attSeq;
    }
    public String getFileType() {
        return fileType;
    }
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
    public int getFileCapa() {
        return fileCapa;
    }
    public void setFileCapa(int fileCapa) {
        this.fileCapa = fileCapa;
    }
    public String getFilePathNM() {
        return filePathNM;
    }
    public void setFilePathNM(String filePathNM) {
        this.filePathNM = filePathNM;
    }
    public String[] getEditorPhotoSeqArr() {
        return editorPhotoSeqArr;
    }
    public void setEditorPhotoSeqArr(String[] editorPhotoSeqArr) {
        this.editorPhotoSeqArr = editorPhotoSeqArr;
    }
    public String getCtgVal() {
        return ctgVal;
    }
    public void setCtgVal(String ctgVal) {
        this.ctgVal = ctgVal;
    }
    public int getSkipResult() {
        return skipResult;
    }
    public void setSkipResult(int skipResult) {
        this.skipResult = skipResult;
    }
    public int getMaxResult() {
        return maxResult;
    }
    public void setMaxResult(int maxResult) {
        this.maxResult = maxResult;
    }
    public int getPageNo() {
        return pageNo;
    }
    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }
    public int getPrevIndex() {
        return prevIndex;
    }
    public void setPrevIndex(int prevIndex) {
        this.prevIndex = prevIndex;
    }
    public int getNextIndex() {
        return nextIndex;
    }
    public void setNextIndex(int nextIndex) {
        this.nextIndex = nextIndex;
    }
    public int getPagingPosition() {
        return pagingPosition;
    }
    public void setPagingPosition(int pagingPosition) {
        this.pagingPosition = pagingPosition;
    }
    public int getPagingStartNo() {
        return pagingStartNo;
    }
    public void setPagingStartNo(int pagingStartNo) {
        this.pagingStartNo = pagingStartNo;
    }
    public int getPagingEndNo() {
        return pagingEndNo;
    }
    public void setPagingEndNo(int pagingEndNo) {
        this.pagingEndNo = pagingEndNo;
    }
    public int getPagingStart() {
        return pagingStart;
    }
    public void setPagingStart(int pagingStart) {
        this.pagingStart = pagingStart;
    }
    public int getPagingFront() {
        return pagingFront;
    }
    public void setPagingFront(int pagingFront) {
        this.pagingFront = pagingFront;
    }
    public int getPagingNext() {
        return pagingNext;
    }
    public void setPagingNext(int pagingNext) {
        this.pagingNext = pagingNext;
    }
    public int getPagingEnd() {
        return pagingEnd;
    }
    public void setPagingEnd(int pagingEnd) {
        this.pagingEnd = pagingEnd;
    }
    public int getPagingSize() {
        return pagingSize;
    }
    public void setPagingSize(int pagingSize) {
        this.pagingSize = pagingSize;
    }
    public String getBoardWriteTm() {
        return boardWriteTm;
    }
    public void setBoardWriteTm(String boardWriteTm) {
        this.boardWriteTm = boardWriteTm;
    }
    public int getNextSeq() {
        return nextSeq;
    }
    public void setNextSeq(int nextSeq) {
        this.nextSeq = nextSeq;
    }
    public String getNextSubject() {
        return nextSubject;
    }
    public void setNextSubject(String nextSubject) {
        this.nextSubject = nextSubject;
    }
    public Date getNextDt() {
        return nextDt;
    }
    public void setNextDt(Date nextDt) {
        this.nextDt = nextDt;
    }
    public int getPreSeq() {
        return preSeq;
    }
    public void setPreSeq(int preSeq) {
        this.preSeq = preSeq;
    }
    public String getPreSubject() {
        return preSubject;
    }
    public void setPreSubject(String preSubject) {
        this.preSubject = preSubject;
    }
    public Date getPreDt() {
        return preDt;
    }
    public void setPreDt(Date preDt) {
        this.preDt = preDt;
    }
    public int getDownloadCount() {
        return downloadCount;
    }
    public void setDownloadCount(int downloadCount) {
        this.downloadCount = downloadCount;
    }
    public int getBoardNum() {
        return boardNum;
    }
    public void setBoardNum(int boardNum) {
        this.boardNum = boardNum;
    }
    public String getDstnctKey() {
        return dstnctKey;
    }
    public void setDstnctKey(String dstnctKey) {
        this.dstnctKey = dstnctKey;
    }

}