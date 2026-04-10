package com.ktis.msp.org.workmgmt.constants;

public class DocRcvConstants {

    /* ORG0081 서류접수 처리상태 */
    public static final String DOC_RCV_PROC_STATUS_NOT_RECEIVED = "N";
    public static final String DOC_RCV_PROC_STATUS_PENDING_VERIFY = "P";
    public static final String DOC_RCV_PROC_STATUS_RETRY_REQUEST = "R";
    public static final String DOC_RCV_PROC_STATUS_VERIFY = "V";
    public static final String DOC_RCV_PROC_STATUS_COMPLETED = "C";
    public static final String DOC_RCV_PROC_STATUS_EXPIRE = "E";
    public static final String DOC_RCV_PROC_STATUS_INCOMPLETED = "I";

    /* ORG0084 서류(파일) 처리상태 */
    public static final String DOC_RCV_ITEM_STATUS_PENDING = "P";
    public static final String DOC_RCV_ITEM_STATUS_NOT_RECEIVE = "N";
    public static final String DOC_RCV_ITEM_STATUS_RECEIVE = "Y";
    public static final String DOC_RCV_ITEM_STATUS_COMPLETED = "C";

    public static final String VIEW_TYPE_REQUEST = "REQUEST";
    public static final String VIEW_TYPE_OPEN = "OPEN";
}
