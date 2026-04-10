package com.ktis.msp.org.workmgmt.mapper;

import com.ktis.msp.org.workmgmt.vo.WorkMgmtVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

import java.util.List;
import java.util.Map;


@Mapper("WorkMgmtMapper")
public interface WorkMgmtMapper {

    List<EgovMap> getWorkTmplList(WorkMgmtVO vo);
    List<EgovMap> getWorkTmplListExcel(WorkMgmtVO vo);

    int dupChkItem(WorkMgmtVO vo);

    int insertWorkItem(WorkMgmtVO vo);
    int updateWorkItem(WorkMgmtVO vo);
    int updateWorkItemEnd(WorkMgmtVO vo);

    List<EgovMap> getWorkItemList(WorkMgmtVO vo);

    int dupChkOwner(WorkMgmtVO vo);

    int insertWorkOwner(WorkMgmtVO vo);
    int updateWorkOwner(WorkMgmtVO vo);
    int updateWorkOwnerEnd(WorkMgmtVO vo);

    List<EgovMap> getWorkOwnerList(WorkMgmtVO vo);

    int dupChkRqstr(WorkMgmtVO vo);
    int insertWorkRqstr(WorkMgmtVO vo);
    int updateWorkRqstr(WorkMgmtVO vo);
    EgovMap checkWorkTmplInUse(Map<String, String> paramMap);
    int updateWorkRqstrEnd(WorkMgmtVO vo);

    List<EgovMap> getWorkRqstrList(WorkMgmtVO vo);

    List<EgovMap> getWorkSmsList(WorkMgmtVO vo);
    List<EgovMap> getWorkSmsListByWorkTmplId(WorkMgmtVO vo);

    int insertWorkTmpl(WorkMgmtVO vo);
    int insertWorkTmplItem(WorkMgmtVO vo);
    int insertWorkTmplOwner(WorkMgmtVO vo);
    int insertWorkTmplRqstr(WorkMgmtVO vo);
    int insertWorkTmplSms(WorkMgmtVO vo);

    String getWorkTmplEndDt(String workTmplId);
    List<String> getWorkTmplItemList(String workTmplId);
    List<String> getWorkTmplOwnerList(String workTmplId);
    List<String> getWorkTmplRqstrList(String workTmplId);
    List<String> getWorkTmplSmsList(String workTmplId);

    int updateWorkTmplEnd(WorkMgmtVO vo);
    int updateWorkTmpl(WorkMgmtVO vo);

    List<EgovMap> getWorkIdList();
    List<EgovMap> getRqstrCdList();
}