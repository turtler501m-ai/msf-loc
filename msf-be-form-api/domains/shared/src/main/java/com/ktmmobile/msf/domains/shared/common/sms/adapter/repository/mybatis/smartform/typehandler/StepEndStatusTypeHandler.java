package com.ktmmobile.msf.domains.shared.common.sms.adapter.repository.mybatis.smartform.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import com.ktmmobile.msf.commons.common.commonenum.core.CommonEnum;
import com.ktmmobile.msf.domains.shared.common.sms.domain.code.StepEndStatus;

@MappedTypes(StepEndStatus.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class StepEndStatusTypeHandler extends BaseTypeHandler<StepEndStatus> {

    @Override public void setNonNullParameter(PreparedStatement ps, int i, StepEndStatus parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getCode());
    }

    @Override public StepEndStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return toStatus(rs.getString(columnName));
    }

    @Override public StepEndStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return toStatus(rs.getString(columnIndex));
    }

    @Override public StepEndStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return toStatus(cs.getString(columnIndex));
    }

    private StepEndStatus toStatus(String code) {
        return CommonEnum.valueOfCode(StepEndStatus.class, code);
    }
}
