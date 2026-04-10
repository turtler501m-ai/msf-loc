package com.ktmmobile.mcp.texting.dto;

import java.io.Serializable;

public class TextingDto implements Serializable {


    private static final long serialVersionUID = 1L;

       private String mobileNo;

        public String getMobileNo() {
            return mobileNo;
        }

        public void setMobileNo(String mobileNo) {
            this.mobileNo = mobileNo;
        }

}
