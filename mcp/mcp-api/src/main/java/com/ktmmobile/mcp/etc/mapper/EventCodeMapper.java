package com.ktmmobile.mcp.etc.mapper;

import com.ktmmobile.mcp.appform.dto.AppformReqDto;
import com.ktmmobile.mcp.etc.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface EventCodeMapper {

    List<EventCodeRateDto> selectEventCodeRateList(EventCodeRateDto eventCodeRateDto);

    List<EventCodeRateDto> selectEventCodeRateListByEcpSeq(int ecpSeq);

    List<EventCodePrmtDto> selectEventCodePrmtListByEcpSeq(int ecpSeq);

    List<EventCodePrmtDto> selectEventPrmtPrdtList(int ecpSeq);

    //XML 파일 생성용 mst
    List<RateEventCodePrmtDto> selectRateEventCodePrmtMstXmlList();

    //XML 파일 생성용 list
    List<RateEventCodePrmtDto> selectRateEventCodePrmtXmlList();
}
