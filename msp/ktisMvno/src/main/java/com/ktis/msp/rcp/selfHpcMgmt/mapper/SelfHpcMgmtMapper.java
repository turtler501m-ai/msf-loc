package com.ktis.msp.rcp.selfHpcMgmt.mapper;

import java.util.List;

import com.ktis.msp.rcp.selfHpcMgmt.vo.SelfHpcMgmtVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Mapper("SelfHpcMgmtMapper")
public interface SelfHpcMgmtMapper {

    public List<EgovMap> getSelfHpcList(SelfHpcMgmtVO selfHpcMgmtVO);
    
    public List<EgovMap> getSelfHpcListByExcel(SelfHpcMgmtVO selfHpcMgmtVO);
    
    int updateHpcRst(SelfHpcMgmtVO selfHpcMgmtVO);
    
    int dupChkHpcStat(SelfHpcMgmtVO selfHpcMgmtVO);
}
