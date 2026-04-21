package com.ktmmobile.msf.commons.mybatis.typehandler.global;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import com.ktmmobile.msf.commons.common.data.type.UseYn;

@MappedTypes(UseYn.class)
@MappedJdbcTypes(value = JdbcType.VARCHAR, includeNullJdbcType = true)
public class UseYnTypeHandler extends BaseTypeHandler<UseYn> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, UseYn parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getCode());
    }

    @Override
    public UseYn getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return UseYn.valueOfCode(rs.getString(columnName));
    }

    @Override
    public UseYn getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return UseYn.valueOfCode(rs.getString(columnIndex));
    }

    @Override
    public UseYn getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return UseYn.valueOfCode(cs.getString(columnIndex));
    }
}
