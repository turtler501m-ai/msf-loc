package com.ktmmobile.mcp.phone.dto;

import java.io.Serializable;
import java.sql.Date;

import org.springframework.web.multipart.MultipartFile;


/**
 * @Class Name : PhoneProdImgDto
 * @Description : 상품의 색상별 이미지 Dto
 *
 * @author : ant
 * @Create Date : 2016. 1. 6.
 */
public class PhoneProdImgDetailDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 색상코드  */
    private String sntyColorCd;

    /** 상품아이디 */
    private String prodId;

    /** 이미지유형[앞,뒤,옆,모바일] */
    private String imgTypeCd;

    /** 이미지 유형 text  */
    private String imgTypeLabel;

    /**이미지경로 */
    private String imgPath;

    /**생성자id */
    private String cretId;

    /**수정자id */
    private String amdId;

    /**생성일시 */
    private Date cretDt;

    /**수정일시 */
    private Date amdDt;

    /** */
    private MultipartFile uploadImage;

    public String getSntyColorCd() {
        return sntyColorCd;
    }

    public void setSntyColorCd(String sntyColorCd) {
        this.sntyColorCd = sntyColorCd;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getImgTypeCd() {
        return imgTypeCd;
    }

    public void setImgTypeCd(String imgTypeCd) {
        this.imgTypeCd = imgTypeCd;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
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

    public MultipartFile getUploadImage() {
        return uploadImage;
    }

    public void setUploadImage(MultipartFile uploadImage) {
        this.uploadImage = uploadImage;
    }

    public String getImgTypeLabel() {
        if (imgTypeCd == null) {
            this.imgTypeLabel = "알수없음";
        } else if (imgTypeCd.equals("01")) {
            this.imgTypeLabel = "앞면";
        } else if (imgTypeCd.equals("02")) {
            this.imgTypeLabel = "뒷면";
        } else if (imgTypeCd.equals("03")) {
            this.imgTypeLabel = "옆면";
        } else if (imgTypeCd.equals("04")) {
            this.imgTypeLabel = "모바일면";
        }
        return imgTypeLabel;
    }
}
