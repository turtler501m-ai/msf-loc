package com.ktmmobile.msf.domains.login.adapter.repository.mybatis.smartform.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import com.ktmmobile.msf.commons.common.data.type.UserType;

@MappedTypes(UserType.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class UserTypeTypeHandler extends BaseTypeHandler<UserType> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, UserType parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getCode());
    }

    @Override
    public UserType getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return toUserType(rs.getString(columnName));
    }

    @Override
    public UserType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return toUserType(rs.getString(columnIndex));
    }

    @Override
    public UserType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return toUserType(cs.getString(columnIndex));
    }

    private UserType toUserType(String code) {
        return UserType.valueOfCode(code);
    }
}
