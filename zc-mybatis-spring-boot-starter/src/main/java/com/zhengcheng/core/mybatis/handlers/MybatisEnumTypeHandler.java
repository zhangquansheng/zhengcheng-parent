package com.zhengcheng.core.mybatis.handlers;

import com.zhengcheng.core.mybatis.enums.IEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * 自定义枚举属性转换器
 *
 * @author :    quansheng.zhang
 * @date :    2020/04/01 15:21
 */
@Slf4j
public class MybatisEnumTypeHandler<E extends Enum<E> & IEnum> extends BaseTypeHandler<E> {

    private final Class<E> type;

    public MybatisEnumTypeHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        if (Objects.isNull(jdbcType)) {
            ps.setInt(i, parameter.getValue());
        } else {
            ps.setObject(i, parameter.getValue(), jdbcType.TYPE_CODE);
        }
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int code = rs.getInt(columnName);
        return rs.wasNull() ? null : this.codeOf(this.type, code);
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int code = rs.getInt(columnIndex);
        return rs.wasNull() ? null : this.codeOf(this.type, code);
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int code = cs.getInt(columnIndex);
        return cs.wasNull() ? null : this.codeOf(this.type, code);
    }

    private <T extends Enum<?> & IEnum> T codeOf(Class<T> enumClass, int code) {
        T[] enumConstants = enumClass.getEnumConstants();
        for (T t : enumConstants) {
            if (t.getValue() == code) {
                return t;
            }
        }
        return null;
    }

}
