package com.ktmmobile.mcp.rate.dto;


import com.ktmmobile.mcp.common.constants.Constants;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;



@XmlRootElement(name="item")
@XmlType(propOrder = {"sortOdrg", "propertyCode","propertyCodeNm", "propertySbst"})
public class RateGdncPropertyDtlDTO implements Serializable {

    private static final long serialVersionUID = 5058950447833322210L;
    /** 정렬순서 */
    private long sortOdrg;
    /** 속성코드 */
    private String propertyCode;

    /** 속성명 */
    private String propertyCodeNm;

    /** 속성내용 */
    private String propertySbst;

    public long getSortOdrg() {
        return sortOdrg;
    }

    public void setSortOdrg(long sortOdrg) {
        this.sortOdrg = sortOdrg;
    }

    public String getPropertyCode() {
        return propertyCode;
    }

    public void setPropertyCodeNm(String propertyCodeNm) {
        this.propertyCodeNm = propertyCodeNm;
    }

    public String getPropertyCodeNm() {
        if (!StringUtils.isBlank(propertyCodeNm)) {
            return propertyCodeNm;
        } else if (StringUtils.isBlank(propertyCode)) {
            return "";
        } else {
            return NmcpServiceUtils.getCodeNm(Constants.GROUP_CODE_RATE_PROPERTY_LIST,propertyCode);
        }

    }

    public void setPropertyCode(String propertyCode) {
        this.propertyCode = propertyCode;
    }

    public String getPropertySbst() {
        return propertySbst;
    }

    public void setPropertySbst(String propertySbst) {
        this.propertySbst = propertySbst;
    }


}