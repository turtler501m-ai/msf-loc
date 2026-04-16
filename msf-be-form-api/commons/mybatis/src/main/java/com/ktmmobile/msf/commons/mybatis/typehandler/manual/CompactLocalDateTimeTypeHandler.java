package com.ktmmobile.msf.commons.mybatis.typehandler.manual;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import static java.time.temporal.ChronoField.NANO_OF_SECOND;

/**
 * 특정 문자열 컬럼이 yyyyMMddHHmmss 포맷으로 저장될 때 사용하는 LocalDateTime TypeHandler.
 * 필요한 컬럼에만 명시적으로 typeHandler를 지정해서 사용한다.
 */
public class CompactLocalDateTimeTypeHandler extends BaseTypeHandler<LocalDateTime> {

    private static final DateTimeFormatter DASHED_FORMAT = new DateTimeFormatterBuilder()
        .appendPattern("yyyy-MM-dd HH:mm:ss")
        .optionalStart()
        .appendFraction(NANO_OF_SECOND, 1, 9, true)
        .toFormatter();
    private static final DateTimeFormatter COMPACT_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, LocalDateTime parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.format(COMPACT_FORMAT));
    }

    @Override
    public LocalDateTime getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return parse(rs.getString(columnName));
    }

    @Override
    public LocalDateTime getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return parse(rs.getString(columnIndex));
    }

    @Override
    public LocalDateTime getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return parse(cs.getString(columnIndex));
    }

    private LocalDateTime parse(String value) throws SQLException {
        if (value == null || value.isBlank()) {
            return null;
        }

        String trimmedValue = value.trim();
        try {
            return LocalDateTime.parse(trimmedValue, COMPACT_FORMAT);
        } catch (DateTimeParseException compactException) {
            try {
                return LocalDateTime.parse(trimmedValue, DASHED_FORMAT);
            } catch (DateTimeParseException dashedException) {
                SQLException sqlException = new SQLException("Invalid LocalDateTime value: " + trimmedValue);
                sqlException.initCause(dashedException);
                throw sqlException;
            }
        }
    }
}
