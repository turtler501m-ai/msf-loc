package com.ktmmobile.mcp.document.receive.constants;

public final class DocumentReceiveConstants {
    /* 서류접수 처리상태 */
    public static final String DOC_RCV_PROC_STATUS_NOT_RECEIVED = "N";
    public static final String DOC_RCV_PROC_STATUS_PENDING_VERIFY = "P";
    public static final String DOC_RCV_PROC_STATUS_RETRY_REQUEST = "R";
    public static final String DOC_RCV_PROC_STATUS_COMPLETED = "C";
    public static final String DOC_RCV_PROC_STATUS_EXPIRE = "E";
    public static final String DOC_RCV_PROC_STATUS_INCOMPLETED = "I";

    /* 서류 접수 URL 상태 */
    public static final String DOC_RCV_URL_STATUS_ACTIVE = "A";
    public static final String DOC_RCV_URL_STATUS_EXPIRE = "E";

    /* 서류 접수 URL OTP 상태 */
    public static final String DOC_RCV_URL_OTP_STATUS_ACTIVE = "A";
    public static final String DOC_RCV_URL_OTP_STATUS_EXPIRE = "E";

    /* 서류(파일) 처리상태 */
    public static final String DOC_RCV_ITEM_STATUS_PENDING = "P";
    public static final String DOC_RCV_ITEM_STATUS_NOT_RECEIVE = "N";
    public static final String DOC_RCV_ITEM_STATUS_RECEIVE = "Y";
    public static final String DOC_RCV_ITEM_STATUS_COMPLETED = "C";
}
