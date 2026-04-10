package com.ktis.msp.org.bpamgmt.mapper;

import com.ktis.msp.org.bpamgmt.vo.BpaMgmtFileReqVO;
import com.ktis.msp.org.bpamgmt.vo.BpaMgmtReqVO;
import com.ktis.msp.org.bpamgmt.vo.BpaMgmtUploadInfoVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

import java.util.List;
import java.util.Map;

@Mapper("bpaMgmtMapper")
public interface BpaMgmtMapper {

    // 데이터정산업무
    List<EgovMap> getDataTaskList(BpaMgmtReqVO searchVO);
    int selectBpaIdCnt(String bpaId);
    void insertDataTask(BpaMgmtReqVO searchVO);
    void updateDataTask(BpaMgmtReqVO searchVO);

    // 파일관리
    List<EgovMap> getDataTaskFileList(BpaMgmtFileReqVO searchVO);
    void insertDataTaskFile(BpaMgmtFileReqVO searchVO);
    void updateDataTaskFile(BpaMgmtFileReqVO searchVO);

    // 엑셀 업로드
    int selectBpaUploadInfoCnt(String batchJobId);
    void insertBpaUploadInfo(BpaMgmtUploadInfoVO searchVO);
    List<EgovMap> getDataExcelList(BpaMgmtUploadInfoVO searchVO);
    void updateDataExcelListUseYn(BpaMgmtUploadInfoVO searchVO);
    void deleteBpaUploadInfo(String bpaId);

    String selectContractNum(String svcCntrNo);
}
