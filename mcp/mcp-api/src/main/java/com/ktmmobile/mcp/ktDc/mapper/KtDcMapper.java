package com.ktmmobile.mcp.ktDc.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.ktmmobile.mcp.ktDc.dto.KtDcDto;


@Mapper
public interface KtDcMapper {

    List<KtDcDto> selectRateNmList(KtDcDto ktDcDto);
}
