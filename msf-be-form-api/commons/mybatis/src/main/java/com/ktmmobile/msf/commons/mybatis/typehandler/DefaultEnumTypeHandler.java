package com.ktmmobile.msf.commons.mybatis.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.EnumTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.ktmmobile.msf.commons.common.commonenum.core.CommonEnum;

public class DefaultEnumTypeHandler<E extends Enum<E>> extends EnumTypeHandler<E> {

    private final Class<E> type;
    private final boolean commonEnumType;

    public DefaultEnumTypeHandler(Class<E> type) {
        super(type);
        this.type = type;
        this.commonEnumType = CommonEnum.class.isAssignableFrom(type);
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        if (parameter instanceof CommonEnum commonEnum) {
            ps.setString(i, commonEnum.getCode());
            return;
        }
        super.setNonNullParameter(ps, i, parameter, jdbcType);
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        if (commonEnumType) {
            return resolveCommonEnum(rs.getString(columnName));
        }
        return super.getNullableResult(rs, columnName);
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        if (commonEnumType) {
            return resolveCommonEnum(rs.getString(columnIndex));
        }
        return super.getNullableResult(rs, columnIndex);
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        if (commonEnumType) {
            return resolveCommonEnum(cs.getString(columnIndex));
        }
        return super.getNullableResult(cs, columnIndex);
    }

    @SuppressWarnings("unchecked")
    private E resolveCommonEnum(String code) {
        return (E) CommonEnum.valueOfCode((Class<? extends CommonEnum>) type, code);
    }
}
