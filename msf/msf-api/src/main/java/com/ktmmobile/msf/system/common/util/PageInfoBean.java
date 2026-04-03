/*=================================================
 * KT Copyright
 =================================================*/
package com.ktmmobile.msf.system.common.util;


import java.io.Serializable;

public class PageInfoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 현재 페이지 */
    private int pageNo = 0;
    /** 한페이지당 보여줄 리스트 수 */
    private int recordCount = 5;
    /** 블럭 사이즈 */
    private int pageSize = 10;
    /** 총 카운트 */
    private int totalCount = 0;
    /** 총 페이지 카운트 */
    private int totalPageCount = 0;

    private int firstPageNoOnPageList = 0;
    private int lastPageNoOnPageList = 0;
    private int firstRecordIndex = 0;
    private int lastRecordIndex = 0;

    private int mobileFirstRecordCount = 0;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public void setTotalPageCount(int totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

    public int getTotalPageCount() {
        this.totalPageCount = ((getTotalCount() - 1) / getRecordCount() + 1);
        return this.totalPageCount;
    }

    public int getFirstPageNo() {
        return 1;
    }

    public int getLastPageNo() {
        return getTotalPageCount();
    }

    public int getFirstPageNoOnPageList() {
        this.firstPageNoOnPageList = ((getPageNo() - 1) / getPageSize() * getPageSize() + 1);
        return this.firstPageNoOnPageList;
    }

    public int getLastPageNoOnPageList() {
        this.lastPageNoOnPageList = (getFirstPageNoOnPageList() + getPageSize() - 1);
        if (this.lastPageNoOnPageList > getTotalPageCount()) {
            this.lastPageNoOnPageList = getTotalPageCount();
        }
        return this.lastPageNoOnPageList;
    }

    public int getFirstRecordIndex() {
        this.firstRecordIndex = ((getPageNo() - 1) * getRecordCount());
        return this.firstRecordIndex;
    }

    public int getLastRecordIndex() {
        this.lastRecordIndex = (getPageNo() * getRecordCount());
        return this.lastRecordIndex;
    }

    public int getMobileFirstRecordCount() {
        return mobileFirstRecordCount;
    }

    public void setMobileFirstRecordCount(int mobileFirstRecordCount) {
        this.mobileFirstRecordCount = mobileFirstRecordCount;
    }

}
