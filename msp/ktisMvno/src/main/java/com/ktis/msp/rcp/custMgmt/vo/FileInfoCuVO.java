package com.ktis.msp.rcp.custMgmt.vo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ktis.msp.base.mvc.BaseVo;

public class FileInfoCuVO extends BaseVo implements Serializable{

		/** serialVersionUID */
	    private static final long serialVersionUID = -8694902998995625053L;
	    
		private String regstId; /** 등록자ID */
	    private String regDttm; /** 등록일시 */
	    private String rvisnId; /** 수정자ID */
	    private String rvisnDttm; /** 수정일시 */
	    private String attDttm; /** 첨부일시 */
	    private String fileNm; /** 파일명 */
	    private String attDsc; /** 첨부설명 */
	    private String attRout; /** 첨부경로 */
	    private String attSctn; /** 업무구분 */
	    private String fileSeq; /** SEQ */
	    private String fileId; /** 파일ID */
	    
	    private String orgnId;
	    private String orgnNm;
	    
	    private String file_upload1_count;
	    private String file_upload1_s_0;
	    private String file_upload1_s_1;
	    private String file_upload1_s_2;
	    

	    private String file_upload2_count;
	    private String file_upload2_s_0;
	    private String file_upload2_s_1;
	    private String file_upload2_s_2;
	    

	    private String file_upload3_count;
	    private String file_upload3_s_0;
	    
	    private String contractNum;


		public String getContractNum() {
			return contractNum;
		}



		public void setContractNum(String contractNum) {
			this.contractNum = contractNum;
		}



		@Override
	    public String toString() {
	       return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	    }
	    
	    
	    
		/**
		 * @return the file_upload2_count
		 */
		public String getFile_upload2_count() {
			return file_upload2_count;
		}



		/**
		 * @param file_upload2_count the file_upload2_count to set
		 */
		public void setFile_upload2_count(String file_upload2_count) {
			this.file_upload2_count = file_upload2_count;
		}



		/**
		 * @return the file_upload2_s_0
		 */
		public String getFile_upload2_s_0() {
			return file_upload2_s_0;
		}



		/**
		 * @param file_upload2_s_0 the file_upload2_s_0 to set
		 */
		public void setFile_upload2_s_0(String file_upload2_s_0) {
			this.file_upload2_s_0 = file_upload2_s_0;
		}



		/**
		 * @return the file_upload2_s_1
		 */
		public String getFile_upload2_s_1() {
			return file_upload2_s_1;
		}



		/**
		 * @param file_upload2_s_1 the file_upload2_s_1 to set
		 */
		public void setFile_upload2_s_1(String file_upload2_s_1) {
			this.file_upload2_s_1 = file_upload2_s_1;
		}



		/**
		 * @return the file_upload2_s_2
		 */
		public String getFile_upload2_s_2() {
			return file_upload2_s_2;
		}



		/**
		 * @param file_upload2_s_2 the file_upload2_s_2 to set
		 */
		public void setFile_upload2_s_2(String file_upload2_s_2) {
			this.file_upload2_s_2 = file_upload2_s_2;
		}



		/**
		 * @return the file_upload3_count
		 */
		public String getFile_upload3_count() {
			return file_upload3_count;
		}



		/**
		 * @param file_upload3_count the file_upload3_count to set
		 */
		public void setFile_upload3_count(String file_upload3_count) {
			this.file_upload3_count = file_upload3_count;
		}



		/**
		 * @return the file_upload3_s_0
		 */
		public String getFile_upload3_s_0() {
			return file_upload3_s_0;
		}



		/**
		 * @param file_upload3_s_0 the file_upload3_s_0 to set
		 */
		public void setFile_upload3_s_0(String file_upload3_s_0) {
			this.file_upload3_s_0 = file_upload3_s_0;
		}



		/**
		 * @return the file_upload1_count
		 */
		public String getFile_upload1_count() {
			return file_upload1_count;
		}


		/**
		 * @param file_upload1_count the file_upload1_count to set
		 */
		public void setFile_upload1_count(String file_upload1_count) {
			this.file_upload1_count = file_upload1_count;
		}


		/**
		 * @return the file_upload1_s_0
		 */
		public String getFile_upload1_s_0() {
			return file_upload1_s_0;
		}


		/**
		 * @param file_upload1_s_0 the file_upload1_s_0 to set
		 */
		public void setFile_upload1_s_0(String file_upload1_s_0) {
			this.file_upload1_s_0 = file_upload1_s_0;
		}


		/**
		 * @return the file_upload1_s_1
		 */
		public String getFile_upload1_s_1() {
			return file_upload1_s_1;
		}


		/**
		 * @param file_upload1_s_1 the file_upload1_s_1 to set
		 */
		public void setFile_upload1_s_1(String file_upload1_s_1) {
			this.file_upload1_s_1 = file_upload1_s_1;
		}


		/**
		 * @return the file_upload1_s_2
		 */
		public String getFile_upload1_s_2() {
			return file_upload1_s_2;
		}


		/**
		 * @param file_upload1_s_2 the file_upload1_s_2 to set
		 */
		public void setFile_upload1_s_2(String file_upload1_s_2) {
			this.file_upload1_s_2 = file_upload1_s_2;
		}


		/**
		 * @return the orgnId
		 */
		public String getOrgnId() {
			return orgnId;
		}
		/**
		 * @param orgnId the orgnId to set
		 */
		public void setOrgnId(String orgnId) {
			this.orgnId = orgnId;
		}
		/**
		 * @return the orgnNm
		 */
		public String getOrgnNm() {
			return orgnNm;
		}
		/**
		 * @param orgnNm the orgnNm to set
		 */
		public void setOrgnNm(String orgnNm) {
			this.orgnNm = orgnNm;
		}
		/**
		 * @return the regstId
		 */
		public String getRegstId() {
			return regstId;
		}
		/**
		 * @param regstId the regstId to set
		 */
		public void setRegstId(String regstId) {
			this.regstId = regstId;
		}
		/**
		 * @return the regDttm
		 */
		public String getRegDttm() {
			return regDttm;
		}
		/**
		 * @param regDttm the regDttm to set
		 */
		public void setRegDttm(String regDttm) {
			this.regDttm = regDttm;
		}
		/**
		 * @return the rvisnId
		 */
		public String getRvisnId() {
			return rvisnId;
		}
		/**
		 * @param rvisnId the rvisnId to set
		 */
		public void setRvisnId(String rvisnId) {
			this.rvisnId = rvisnId;
		}
		/**
		 * @return the rvisnDttm
		 */
		public String getRvisnDttm() {
			return rvisnDttm;
		}
		/**
		 * @param rvisnDttm the rvisnDttm to set
		 */
		public void setRvisnDttm(String rvisnDttm) {
			this.rvisnDttm = rvisnDttm;
		}
		/**
		 * @return the attDttm
		 */
		public String getAttDttm() {
			return attDttm;
		}
		/**
		 * @param attDttm the attDttm to set
		 */
		public void setAttDttm(String attDttm) {
			this.attDttm = attDttm;
		}
		/**
		 * @return the fileNm
		 */
		public String getFileNm() {
			return fileNm;
		}
		/**
		 * @param fileNm the fileNm to set
		 */
		public void setFileNm(String fileNm) {
			this.fileNm = fileNm;
		}
		/**
		 * @return the attDsc
		 */
		public String getAttDsc() {
			return attDsc;
		}
		/**
		 * @param attDsc the attDsc to set
		 */
		public void setAttDsc(String attDsc) {
			this.attDsc = attDsc;
		}
		/**
		 * @return the attRout
		 */
		public String getAttRout() {
			return attRout;
		}
		/**
		 * @param attRout the attRout to set
		 */
		public void setAttRout(String attRout) {
			this.attRout = attRout;
		}
		/**
		 * @return the attSctn
		 */
		public String getAttSctn() {
			return attSctn;
		}
		/**
		 * @param attSctn the attSctn to set
		 */
		public void setAttSctn(String attSctn) {
			this.attSctn = attSctn;
		}
		/**
		 * @return the fileSeq
		 */
		public String getFileSeq() {
			return fileSeq;
		}
		/**
		 * @param fileSeq the fileSeq to set
		 */
		public void setFileSeq(String fileSeq) {
			this.fileSeq = fileSeq;
		}
		/**
		 * @return the fileId
		 */
		public String getFileId() {
			return fileId;
		}
		/**
		 * @param fileId the fileId to set
		 */
		public void setFileId(String fileId) {
			this.fileId = fileId;
		}

	    
	}
