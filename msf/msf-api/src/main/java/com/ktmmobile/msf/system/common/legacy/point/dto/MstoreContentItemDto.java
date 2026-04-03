package com.ktmmobile.msf.system.common.legacy.point.dto;

public class MstoreContentItemDto {
    private long itemSeq;
    private long contentSeq;
    private String itemType;
    private String name;
    private String url;
    private String imgUrlPc;
    private String imgUrlMo;
    private long originalPrice;
    private long discountPrice;
    private String userId;

    private String imgUrl;

    public long getItemSeq() {
        return itemSeq;
    }

    public void setItemSeq(long itemSeq) {
        this.itemSeq = itemSeq;
    }

    public long getContentSeq() {
        return contentSeq;
    }

    public void setContentSeq(long contentSeq) {
        this.contentSeq = contentSeq;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImgUrlPc() {
        return imgUrlPc;
    }

    public void setImgUrlPc(String imgUrlPc) {
        this.imgUrlPc = imgUrlPc;
    }

    public String getImgUrlMo() {
        return imgUrlMo;
    }

    public void setImgUrlMo(String imgUrlMo) {
        this.imgUrlMo = imgUrlMo;
    }

    public long getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(long originalPrice) {
        this.originalPrice = originalPrice;
    }

    public long getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(long discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
