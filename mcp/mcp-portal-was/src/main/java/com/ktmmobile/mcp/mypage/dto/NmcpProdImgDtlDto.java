package com.ktmmobile.mcp.mypage.dto;

import java.io.Serializable;

public class NmcpProdImgDtlDto implements Serializable {

    private static final long serialVersionUID = 1L;


    private String prodId;
    private String hndsetModelId;
    private String imgPath;

    /**
     * @return the prodId
     */
    public String getProdId() {
        return prodId;
    }
    /**
     * @param prodId the prodId to set
     */
    public void setProdId(String prodId) {
        this.prodId = prodId;
    }
    /**
     * @return the hndsetModelId
     */
    public String getHndsetModelId() {
        return hndsetModelId;
    }
    /**
     * @param hndsetModelId the hndsetModelId to set
     */
    public void setHndsetModelId(String hndsetModelId) {
        this.hndsetModelId = hndsetModelId;
    }
    /**
     * @return the imgPath
     */
    public String getImgPath() {
        return imgPath;
    }
    /**
     * @param imgPath the imgPath to set
     */
    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

}
