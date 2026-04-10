package com.ktmmobile.mcp.content.dto;

import java.util.List;

public class MyCombinationResDto {

		private String socYn; //요금제 관련여부
		private String custTypeYn; //고객구분
		private String statusYn; //상태코드

		private String mskSvcNo;
		private String svcNo; //서비스번호
		private String socNm; //요금제번호
		private String combiSvcNo; //가입된 결합번혼
		private String combiSvcType;// 가입된 결합상품 구분
		private List<MyCombinationResDto> svcDivList;
		private String reqSocNm ;

		private String pRateCd; // 결합 가능 요금제
		private String rRateCd; // 결합 부가서비스
		private String rRateNm; // 결합 부가서비스 이름
		private String pRateNm; // 결합 요금제 이름


		public String getrRateNm() {
			return rRateNm;
		}

		public void setrRateNm(String rRateNm) {
			this.rRateNm = rRateNm;
		}

		public String getpRateCd() {
			return pRateCd;
		}

		public void setpRateCd(String pRateCd) {
			this.pRateCd = pRateCd;
		}

		public String getrRateCd() {
			return rRateCd;
		}

		public void setrRateCd(String rRateCd) {
			this.rRateCd = rRateCd;
		}

		public String getSocYn() {
			return socYn;
		}

		public void setSocYn(String socYn) {
			this.socYn = socYn;
		}

		public String getCustTypeYn() {
			return custTypeYn;
		}

		public void setCustTypeYn(String custTypeYn) {
			this.custTypeYn = custTypeYn;
		}

		public String getStatusYn() {
			return statusYn;
		}

		public void setStatusYn(String statusYn) {
			this.statusYn = statusYn;
		}

		public String getSvcNo() {
			if(svcNo == null) {
				return "";
			}
			return svcNo;
		}

		public void setSvcNo(String svcNo) {
			this.svcNo = svcNo;
		}

		public String getSocNm() {
			return socNm;
		}

		public void setSocNm(String socNm) {
			this.socNm = socNm;
		}

		public List<MyCombinationResDto> getSvcDivList() {
			return svcDivList;
		}

		public void setSvcDivList(List<MyCombinationResDto> svcDivList) {
			this.svcDivList = svcDivList;
		}

		public String getCombiSvcNo() {
			return combiSvcNo;
		}

		public void setCombiSvcNo(String combiSvcNo) {
			this.combiSvcNo = combiSvcNo;
		}

		public String getMskSvcNo() {
			return mskSvcNo;
		}

		public void setMskSvcNo(String mskSvcNo) {
			this.mskSvcNo = mskSvcNo;
		}

		public String getCombiSvcType() {
			return combiSvcType;
		}

		public void setCombiSvcType(String combiSvcType) {
			this.combiSvcType = combiSvcType;
		}

		public String getReqSocNm() {
			return reqSocNm;
		}

		public void setReqSocNm(String reqSocNm) {
			this.reqSocNm = reqSocNm;
		}




}
