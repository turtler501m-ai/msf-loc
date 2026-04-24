package com.ktis.msp.batch.job.dis.dismgmt.mapper;

import com.ktis.msp.batch.job.dis.dismgmt.vo.DisPrcVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface DisPrcMapper {

    /**
     * 프로모션 가입 대상자 정보 조회
     * @param trgFnlSeq
     * @return DisPrcVO
     */
    DisPrcVO getPrcCstmr(String trgFnlSeq);

    /**
     * 프로모션 부가서비스 조회
     * @param prmtId
     * @return List<String>
     */
    List<String> getPrcSocList(String prmtId);

    /**
     * 프로모션 가입 대상 처리결과 UPDATE (MSP_DIS_TRG_FNL UPDATE)
     * @param paraMap
     */
    void updateTrgFnlProc(Map<String,String> paraMap);

    /**
     * MST 시퀀스 생성
     * @return String
     */
    String getPrcMstSeq();

    /**
     * DTL 시퀀스 생성
     * @return String
     */
    String getPrcDtlSeq();

    /**
     * 프로모션 부가서비스 DTL insert
     * @param disPrcVo
     * @return int
     */
    int insertPrmtAutoAddDtl(DisPrcVO disPrcVo);

    /**
     * 프로모션 부가서비스 MST insert
     * @param disPrcVo
     * @return int
     */
    int insertPrmtAutoAddMst(DisPrcVO disPrcVo);


}
