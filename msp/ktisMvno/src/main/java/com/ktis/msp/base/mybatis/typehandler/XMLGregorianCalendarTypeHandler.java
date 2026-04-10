/**
 * Copyright (c) 2012 KT Corp, Inc.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of KT
 * Corp, Inc. You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with KT Corp.
 *
 * Revision History
 * Author              Date             Description
 * ------------------   --------------    ------------------
 * yngwie          2013. 03. 12.      First Draft.
 */
package com.ktis.msp.base.mybatis.typehandler;


import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.GregorianCalendar;


/**
 * <PRE>
 * <b>timestamp typeHandler
 * timestamp typeHandler </b>
 * </PRE>
 * @author 장익준
 * @version 1.0
 * @created 12-3-2013 오전 11:02:50
 * @updated
 */
public class XMLGregorianCalendarTypeHandler extends BaseTypeHandler<XMLGregorianCalendar> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, XMLGregorianCalendar parameter, JdbcType jdbcType)
            throws SQLException {
        if(parameter == null) {
            ps.setTimestamp(i, null);
            return;
        }
        GregorianCalendar cal = ((XMLGregorianCalendar) parameter).toGregorianCalendar();
        ps.setTimestamp(i, new Timestamp(cal.getTimeInMillis()));

    }

    @Override
    public XMLGregorianCalendar getNullableResult(ResultSet rs, String columnName)
            throws SQLException {

        if(rs.wasNull()) {
            return null;
        }

        return getXMLGregorianCalendar(rs.getTimestamp(columnName).getTime());
    }

    @Override
    public XMLGregorianCalendar getNullableResult(ResultSet rs, int columnIndex)
            throws SQLException {

        if(rs.wasNull()) {
            return null;
        }

        return getXMLGregorianCalendar(rs.getTimestamp(columnIndex).getTime());
    }

    @Override
    public XMLGregorianCalendar getNullableResult(CallableStatement cs, int columnIndex)
            throws SQLException {

        if(cs.wasNull()) {
            return null;
        }

        return getXMLGregorianCalendar(cs.getTimestamp(columnIndex).getTime());
    }

    private XMLGregorianCalendar getXMLGregorianCalendar(long millisecond) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTimeInMillis(millisecond);

        XMLGregorianCalendar xcal = null;
        try {
            xcal = DatatypeFactory.newInstance().newXMLGregorianCalendar((GregorianCalendar)cal);
        } catch (DatatypeConfigurationException e) {
            throw new IllegalStateException(e);
        }
        return xcal;
    }
}
