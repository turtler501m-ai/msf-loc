package com.ktmmobile.mcp.cs.dto;

import java.io.Serializable;

public class InquiryBoardDto implements Serializable{

	/**
	 *dtlCd
	 */
	private static final long serialVersionUID = 1L;

	private int qnaSeq;					//문의일련번호
	private String qnaCtg;				//문의카테고리
	private String qnaCtgNm;		//문의카테고리이름
	private String qnaSubCtgCd;  // 서브카테고리
	private String qnaSubCtgNm;	// 서브카테고리이름
	private String email;  // 이메일
	private String password;  //비밀번호
	private String certSndRsltMot; // 인증발신결과전문
	private String qnaSubject;		//문의제목
	private String qnaWriterID;		//작성자아이디
	private String qnaNM;				//작성자명
	private String mobileNo;			//핸드폰번호
	private String qnaContent;		//문의내용
	private String ansSubject;		//답변제목
	private String ansContent;		//답변내용
	private String ansStatVal;		//답변상태
	private String ansNM;				//답변자
	private String ansTeamNM;		//답변자소속팀명
	private String smsSendYN;		//SMS발송여부
	private String delYN;				//삭제여부
	private String cretID;				//생성자아이디
	private String amdID;				//수정자아이디
	private String cretDT;				//생성일시
	private String amdDT;				//수정일시
	private String qnaSmsSendYn;	//고객수신여부
	private String boardCtgNm;
	private String searchNm;
	private String searchOpt;
	private String inquirySrchNm;
	private String[] editorPhotoSeqArr; // 에디터 사진업로드 seqarr
	private String myQna; 				// 내문의확인함
	private int boardCtgSeq; 			//게시판seq(51)
	private String rnumShow;

	private String attSeq;
	private String fileType;
	private int fileCapa;
	private int pageNo;

	//페이징
	private int pagingPosition;		//<<  <   1 2 3 4 5   >  >>
	private int pagingStartNo;		//페이지네이션 시작 변수
	private int pagingEndNo;		//페이지네이션 끝 변수
	private int pagingStart;		//페이지 처음 <<
	private int pagingFront;		//페이지 앞을호 <
	private int pagingNext;			//페이지 다음 >
	private int pagingEnd;			//페이지 마지막 >>
	private int pagingSize;			//페이지 사이즈>>

	private String svcCntrNo;

	private String userNmAll;
	private String userDivision;

	public String getSvcCntrNo() {
		return svcCntrNo;
	}
	public void setSvcCntrNo(String svcCntrNo) {
		this.svcCntrNo = svcCntrNo;
	}
	public int getQnaSeq() {
		return qnaSeq;
	}
	public void setQnaSeq(int qnaSeq) {
		this.qnaSeq = qnaSeq;
	}
	public String getQnaCtg() {
		return qnaCtg;
	}
	public void setQnaCtg(String qnaCtg) {
		this.qnaCtg = qnaCtg;
	}
	public String getQnaCtgNm() {
		return qnaCtgNm;
	}
	public void setQnaCtgNm(String qnaCtgNm) {
		this.qnaCtgNm = qnaCtgNm;
	}
	public String getQnaSubject() {
		return qnaSubject;
	}
	public void setQnaSubject(String qnaSubject) {
		this.qnaSubject = qnaSubject;
	}
	public String getQnaWriterID() {
		return qnaWriterID;
	}
	public void setQnaWriterID(String qnaWriterID) {
		this.qnaWriterID = qnaWriterID;
	}
	public String getQnaNM() {
		return qnaNM;
	}
	public void setQnaNM(String qnaNM) {
		this.qnaNM = qnaNM;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getQnaContent() {
		return qnaContent;
	}
	public void setQnaContent(String qnaContent) {
		this.qnaContent = qnaContent;
	}
	public String getAnsSubject() {
		return ansSubject;
	}
	public void setAnsSubject(String ansSubject) {
		this.ansSubject = ansSubject;
	}
	public String getAnsContent() {
		return ansContent;
	}
	public void setAnsContent(String ansContent) {
		this.ansContent = ansContent;
	}
	public String getAnsStatVal() {
		return ansStatVal;
	}
	public void setAnsStatVal(String ansStatVal) {
		this.ansStatVal = ansStatVal;
	}
	public String getAnsNM() {
		return ansNM;
	}
	public void setAnsNM(String ansNM) {
		this.ansNM = ansNM;
	}
	public String getAnsTeamNM() {
		return ansTeamNM;
	}
	public void setAnsTeamNM(String ansTeamNM) {
		this.ansTeamNM = ansTeamNM;
	}
	public String getSmsSendYN() {
		return smsSendYN;
	}
	public void setSmsSendYN(String smsSendYN) {
		this.smsSendYN = smsSendYN;
	}
	public String getDelYN() {
		return delYN;
	}
	public void setDelYN(String delYN) {
		this.delYN = delYN;
	}
	public String getCretID() {
		return cretID;
	}
	public void setCretID(String cretID) {
		this.cretID = cretID;
	}
	public String getAmdID() {
		return amdID;
	}
	public void setAmdID(String amdID) {
		this.amdID = amdID;
	}
	public String getCretDT() {
		return cretDT;
	}
	public void setCretDT(String cretDT) {
		this.cretDT = cretDT;
	}
	public String getAmdDT() {
		return amdDT;
	}
	public void setAmdDT(String amdDT) {
		this.amdDT = amdDT;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public String getInquirySrchNm() {
		return inquirySrchNm;
	}
	public void setInquirySrchNm(String inquirySrchNm) {
		this.inquirySrchNm = inquirySrchNm;
	}
	public int getPagingSize() {
		return pagingSize;
	}
	public void setPagingSize(int pagingSize) {
		this.pagingSize = pagingSize;
	}
	public String getQnaSmsSendYn() {
		return qnaSmsSendYn;
	}
	public void setQnaSmsSendYn(String qnaSmsSendYn) {
		this.qnaSmsSendYn = qnaSmsSendYn;
	}
	public String[] getEditorPhotoSeqArr() {
		return editorPhotoSeqArr;
	}
	public void setEditorPhotoSeqArr(String[] editorPhotoSeqArr) {
		this.editorPhotoSeqArr = editorPhotoSeqArr;
	}
	public String getMyQna() {
		return myQna;
	}
	public void setMyQna(String myQna) {
		this.myQna = myQna;
	}
	public int getBoardCtgSeq() {
		return boardCtgSeq;
	}
	public void setBoardCtgSeq(int boardCtgSeq) {
		this.boardCtgSeq = boardCtgSeq;
	}
	/**
	 * @return the rnumShow
	 */
	public String getRnumShow() {
		return rnumShow;
	}
	/**
	 * @param rnumShow the rnumShow to set
	 */
	public void setRnumShow(String rnumShow) {
		this.rnumShow = rnumShow;
	}
	public String getQnaSubCtgCd() {
		return qnaSubCtgCd;
	}
	public void setQnaSubCtgCd(String qnaSubCtgCd) {
		this.qnaSubCtgCd = qnaSubCtgCd;
	}
	public String getQnaSubCtgNm() {
		return qnaSubCtgNm;
	}
	public void setQnaSubCtgNm(String qnaSubCtgNm) {
		this.qnaSubCtgNm = qnaSubCtgNm;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCertSndRsltMot() {
		return certSndRsltMot;
	}
	public void setCertSndRsltMot(String certSndRsltMot) {
		this.certSndRsltMot = certSndRsltMot;
	}

	public String getUserNmAll() {
		return userNmAll;
	}

	public void setUserNmAll(String userNmAll) {
		this.userNmAll = userNmAll;
	}

	public String getUserDivision() {
		return userDivision;
	}

	public void setUserDivision(String userDivision) {
		this.userDivision = userDivision;
	}

}