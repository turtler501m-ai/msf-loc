package com.ktmmobile.mcp.etc.dto;

import java.io.Serializable;

public class RateGiftPrmtSubDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String code;    // 코드
	private String name;    // 이름
	private String useYn;   // 사용여부
	private String imgUrl;  // 사은품 이미지경로

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

}
