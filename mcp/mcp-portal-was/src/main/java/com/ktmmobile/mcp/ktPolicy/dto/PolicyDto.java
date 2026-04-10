package com.ktmmobile.mcp.ktPolicy.dto;


import java.io.Serializable;

/**
 * @Class Name : PhoneProdBasDto
 * @Description : MCP 상품 기본 Dto
 *
 * @author : ant
 * @Create Date : 2016. 1. 5.
 */
public class PolicyDto implements Serializable {

    private static final long serialVersionUID = 1L;

        private int stpltSeq; 			//약관 일련번호
        private String stpltCtgCd;		//약관 분류코드
        private String stpltCtgNm;		//약관 분류 이름
        private String stpltTitle; 		//약관제목
        private String showYn;			//노출여부
        private String stpltSbst; 		//약관 내용
        private String cretId; 			//생성자아이디
        private String amdId;			//수정자아이디
        private String cretDt;		 	//생성일시
        private String amdDt;			//수정일시
        private int  stpltHit; 			//조회수
        private int pageNo;				//페이지넘버
        private String tabDiv;			//탭 구분
        private String stpltBasSeq;		//파일의 상위테이블 일련번호
        private String fileNm;			//파일이름
        private String realFileNm;		//실제 파일이름
        private String fileType;		//파일타입
        private String filePathNm;		//파일경로명
        private int rNum;
        private int rowNum;
        private String attSeq; 			// 파일 seq

        private String nextSeq;
        private String nextTitle;
        private String nextCretDt;
        private String preSeq;
        private String preTitle;
        private String preCretDt;
        private String siteCode;

        private String paramStpltCtgCd;


        public String getParamStpltCtgCd() {
			return paramStpltCtgCd;
		}
		public void setParamStpltCtgCd(String paramStpltCtgCd) {
			this.paramStpltCtgCd = paramStpltCtgCd;
		}
		public String getSiteCode() {
			return siteCode;
		}
		public void setSiteCode(String siteCode) {
			this.siteCode = siteCode;
		}
		public int getStpltSeq() {
            return stpltSeq;
        }
        public void setStpltSeq(int stpltSeq) {
            this.stpltSeq = stpltSeq;
        }
        public String getStpltCtgCd() {
            return stpltCtgCd;
        }
        public void setStpltCtgCd(String stpltCtgCd) {
            this.stpltCtgCd = stpltCtgCd;
        }
        public String getStpltCtgNm() {
            return stpltCtgNm;
        }
        public void setStpltCtgNm(String stpltCtgNm) {
            this.stpltCtgNm = stpltCtgNm;
        }
        public String getStpltTitle() {
            return stpltTitle;
        }
        public void setStpltTitle(String stpltTitle) {
            this.stpltTitle = stpltTitle;
        }
        public String getShowYn() {
            return showYn;
        }
        public void setShowYn(String showYn) {
            this.showYn = showYn;
        }
        public String getStpltSbst() {
            return stpltSbst;
        }
        public void setStpltSbst(String stpltSbst) {
            this.stpltSbst = stpltSbst;
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
        public int getStpltHit() {
            return stpltHit;
        }
        public void setStpltHit(int stpltHit) {
            this.stpltHit = stpltHit;
        }
        public int getPageNo() {
            return pageNo;
        }
        public void setPageNo(int pageNo) {
            this.pageNo = pageNo;
        }
        public String getTabDiv() {
            return tabDiv;
        }
        public void setTabDiv(String tabDiv) {
            this.tabDiv = tabDiv;
        }
        public String getStpltBasSeq() {
            return stpltBasSeq;
        }
        public void setStpltBasSeq(String stpltBasSeq) {
            this.stpltBasSeq = stpltBasSeq;
        }
        public String getFileNm() {
            return fileNm;
        }
        public void setFileNm(String fileNm) {
            this.fileNm = fileNm;
        }
        public String getRealFileNm() {
            return realFileNm;
        }
        public void setRealFileNm(String realFileNm) {
            this.realFileNm = realFileNm;
        }
        public String getFileType() {
            return fileType;
        }
        public void setFileType(String fileType) {
            this.fileType = fileType;
        }
        /**
         * @return the filePathNm
         */
        public String getFilePathNm() {
            return filePathNm;
        }
        /**
         * @param filePathNm the filePathNm to set
         */
        public void setFilePathNm(String filePathNm) {
            this.filePathNm = filePathNm;
        }

        public int getrNum() {
            return rNum;
        }
        public void setrNum(int rNum) {
            this.rNum = rNum;
        }
        /**
         * @return the rowNum
         */
        public int getRowNum() {
            return rowNum;
        }
        /**
         * @param rowNum the rowNum to set
         */
        public void setRowNum(int rowNum) {
            this.rowNum = rowNum;
        }
        /**
         * @return the nextSeq
         */
        public String getNextSeq() {
            return nextSeq;
        }
        /**
         * @param nextSeq the nextSeq to set
         */
        public void setNextSeq(String nextSeq) {
            this.nextSeq = nextSeq;
        }
        /**
         * @return the nextTitle
         */
        public String getNextTitle() {
            return nextTitle;
        }
        /**
         * @param nextTitle the nextTitle to set
         */
        public void setNextTitle(String nextTitle) {
            this.nextTitle = nextTitle;
        }
        /**
         * @return the nextCretDt
         */
        public String getNextCretDt() {
            return nextCretDt;
        }
        /**
         * @param nextCretDt the nextCretDt to set
         */
        public void setNextCretDt(String nextCretDt) {
            this.nextCretDt = nextCretDt;
        }
        /**
         * @return the preSeq
         */
        public String getPreSeq() {
            return preSeq;
        }
        /**
         * @param preSeq the preSeq to set
         */
        public void setPreSeq(String preSeq) {
            this.preSeq = preSeq;
        }
        /**
         * @return the preTitle
         */
        public String getPreTitle() {
            return preTitle;
        }
        /**
         * @param preTitle the preTitle to set
         */
        public void setPreTitle(String preTitle) {
            this.preTitle = preTitle;
        }
        /**
         * @return the preCretDt
         */
        public String getPreCretDt() {
            return preCretDt;
        }
        /**
         * @param preCretDt the preCretDt to set
         */
        public void setPreCretDt(String preCretDt) {
            this.preCretDt = preCretDt;
        }
        public String getAttSeq() {
            return attSeq;
        }
        public void setAttSeq(String attSeq) {
            this.attSeq = attSeq;
        }


}