package com.ktmmobile.mcp.common.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ktmmobile.mcp.common.util.PageInfoBean;


public class PageInfoTag extends TagSupport {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = LoggerFactory.getLogger(PageInfoTag.class);

    /** Page Bean 선언 */
    private PageInfoBean pageInfoBean;

    /** function 선언 */
    private String function;

    public void setPageInfoBean(PageInfoBean pageInfoBean) {
        this.pageInfoBean = pageInfoBean;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public int doStartTag() throws JspException {
        try {
            StringBuffer sb = new StringBuffer();

            int firstPageNo = pageInfoBean.getFirstPageNo();
            int firstPageNoOnPageList = pageInfoBean.getFirstPageNoOnPageList();
            int totalPageCount = pageInfoBean.getTotalPageCount();
            int pageSize = pageInfoBean.getPageSize();
            int lastPageNoOnPageList = pageInfoBean.getLastPageNoOnPageList();
            int currentPageNo = pageInfoBean.getPageNo();
            int lastPageNo = pageInfoBean.getLastPageNo();


            if(currentPageNo > 1 ){
                if (firstPageNoOnPageList > pageSize) {
                    sb.append("<a class=\"c-button\" href=\"javascript:;\" onclick=\"javascript:").append(function).append("('").append(firstPageNo).append("')\"><i class=\"c-icon c-icon--triangle-start\" aria-hidden=\"true\"></i><span class=\"c-hidden\">처음</span></a>\n");
                    sb.append("<a class=\"c-button\" href=\"javascript:;\" onclick=\"javascript:").append(function).append("('").append(firstPageNoOnPageList - 1).append("')\"><span>이전</span></a>\n");

                } else {
                    sb.append("<a class=\"c-button\" href=\"javascript:;\" onclick=\"javascript:").append(function).append("('").append(firstPageNo).append("')\"><i class=\"c-icon c-icon--triangle-start\" aria-hidden=\"true\"></i><span class=\"c-hidden\">처음</span></a>\n");
                    sb.append("<a class=\"c-button\" href=\"javascript:;\" onclick=\"javascript:").append(function).append("('").append(currentPageNo-1).append("')\"><span>이전</span></a>\n");
                }
            } else {
            	sb.append("<a class=\"c-button\" href=\"javascript:;\" ><i class=\"c-icon c-icon--triangle-start\" aria-hidden=\"true\"></i><span class=\"c-hidden\">처음</span></a>\n");
            	sb.append("<a class=\"c-button\" href=\"javascript:;\" ><span>이전</span></a>\n");
            }
            for (int i = firstPageNoOnPageList; i <= lastPageNoOnPageList; ++i) {
                if (i == currentPageNo) {
                	sb.append("<a class=\"c-paging__anchor c-paging__anchor--current c-paging__number\" href=\"javascript:;\"><span class=\"c-hidden\">현재 페이지</span>").append(i).append("</a>\n");
                } else {
                	sb.append("<a class=\"c-paging__anchor c-paging__number\" href=\"javascript:;\" onclick=\"javascript:").append(function).append("('").append(i).append("')\">").append(i).append("</a>\n");
                }
            }

            if(lastPageNo > currentPageNo ){
                if (lastPageNoOnPageList < totalPageCount) {
                	sb.append("<a class=\"c-button\" href=\"javascript:;\" onclick=\"javascript:").append(function).append("('").append(firstPageNoOnPageList + pageSize).append("')\"><span>다음</span></a>\n");
                	sb.append("<a class=\"c-button\" href=\"javascript:;\" onclick=\"javascript:").append(function).append("('").append(lastPageNo).append("')\"><i class=\"c-icon c-icon--triangle-end\" aria-hidden=\"true\"></i><span class=\"c-hidden\">마지막</span></a>\n");
                } else {
                	sb.append("<a class=\"c-button\" href=\"javascript:;\" onclick=\"javascript:").append(function).append("('").append(currentPageNo+1).append("')\"> <span>다음</span>	</a>\n");
                    sb.append("<a class=\"c-button\" href=\"javascript:;\" onclick=\"javascript:").append(function).append("('").append(lastPageNo).append("')\"><i class=\"c-icon c-icon--triangle-end\" aria-hidden=\"true\"></i><span class=\"c-hidden\">마지막</span></a>\n");
                }
            } else {
            	sb.append("<a class=\"c-button\" href=\"javascript:;\"><span>다음</span></a>\n");
                sb.append("<a class=\"c-button\" href=\"javascript:;\"><i class=\"c-icon c-icon--triangle-end\" aria-hidden=\"true\"></i><span class=\"c-hidden\">마지막</span></a>\n");
            }

            this.pageContext.getOut().write(sb.toString());
        } catch (Exception e){
            logger.error(e.getMessage());
        }
        return SKIP_BODY;
    }

}
