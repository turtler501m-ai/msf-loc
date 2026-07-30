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

    /**
     * UserType 파라미터 문자열 코드 설정
     *
     * @param ps PreparedStatement
     * @param i 파라미터 위치
     * @param parameter 사용자 유형
     * @param jdbcType JDBC 타입
     */
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, UserType parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getCode());
    }

    /**
     * 컬럼명 기준 UserType 결과 조회
     *
     * @param rs ResultSet
     * @param columnName 컬럼명
     * @return 사용자 유형
     */
    @Override
    public UserType getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return toUserType(rs.getString(columnName));
    }

    /**
     * 컬럼 위치 기준 UserType 결과 조회
     *
     * @param rs ResultSet
     * @param columnIndex 컬럼 위치
     * @return 사용자 유형
     */
    @Override
    public UserType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return toUserType(rs.getString(columnIndex));
    }

    /**
     * CallableStatement 기준 UserType 결과 조회
     *
     * @param cs CallableStatement
     * @param columnIndex 컬럼 위치
     * @return 사용자 유형
     */
    @Override
    public UserType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return toUserType(cs.getString(columnIndex));
    }

    /**
     * 문자열 코드 기준 UserType 변환
     *
     * @param code 사용자 유형 코드
     * @return 사용자 유형
     */
    private UserType toUserType(String code) {
        return UserType.valueOfCode(code);
    }
}
