package com.ktis.msp.org.workmgmt.util;

import com.ktis.msp.org.workmgmt.vo.DocRcvItemVO;

import java.util.List;

import static com.ktis.msp.org.workmgmt.constants.DocRcvConstants.DOC_RCV_ITEM_STATUS_PENDING;

public class DocRcvUtil {
    public static String getReqDocName(List<DocRcvItemVO> itemList) {
        StringBuilder reqDocSb = new StringBuilder();
        for (DocRcvItemVO item : itemList) {
            if ("Y".equals(item.getRequestYn())) {
                if (reqDocSb.length() > 0) {
                    reqDocSb.append(", ");
                }
                reqDocSb.append(item.getItemNm());
            }
        }
        return reqDocSb.toString();
    }

    public static String getAddDocNameList(List<DocRcvItemVO> itemList) {
        int addDocCount = 0;
        StringBuilder addDocSb = new StringBuilder();
        for (DocRcvItemVO item : itemList) {
            if ("N".equals(item.getRequestYn()) && DOC_RCV_ITEM_STATUS_PENDING.equals(item.getStatus())) {
                if (addDocSb.length() > 0) {
                    addDocSb.append("\n");
                }
                addDocSb.append((char)('\u2460' + addDocCount++)).append(" ").append(item.getItemNm());
            }
        }
        return addDocSb.toString();
    }
}
