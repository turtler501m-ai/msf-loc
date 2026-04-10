/**
 *
 */
package com.ktmmobile.msf.domains.form.form.servicechange.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.ktmmobile.msf.domains.form.common.dto.db.McpMrktHistDto;
import com.ktmmobile.msf.domains.form.common.dto.db.McpRequestAgrmDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.BillWayChgDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.McpRateChgDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.McpRetvRststnDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.McpServiceAlterTraceDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.NmcpProdImgDtlDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.SuspenChgTmlDto;

/**
 * @author ANT_FX700_02
 *
 */
@Repository
public class MypageDaoImpl implements MypageDao{

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;




    /**
    * @Description : 휴대폰 모델 ID 에 따른 이미지 경로를 가지고 온다.
    * @param modelId
    * @return NmcpProdImgDtlDto
    * @Author : ant
    * @Create Date : 2016. 1. 22.
    */
    public NmcpProdImgDtlDto selectHpImgPath(String modelId) {
        return sqlSessionTemplate.selectOne("MypageMapper.selectHpImgPath", modelId);
    }

    /**
     * @Description : 접속한 횟수를 가져온다
     * @param modelId
     * @return int
     * @Author : ant
     * @Create Date : 2016. 1. 29.
     */
    @Override
    public McpRetvRststnDto retvRstrtn(HashMap<String, String> map) {
        // TODO Auto-generated method stub
        return sqlSessionTemplate.selectOne("MypageMapper.retvRstrtn", map);
    }

    /**
     * @Description : 접속한 횟수를 설정한다
     * @param modelId
     * @return int
     * @Author : ant
     * @Create Date : 2016. 1. 29.
     */
    @Override
    public void retvRstrtnInsert(HashMap<String, String> map) {
        // TODO Auto-generated method stub
        sqlSessionTemplate.insert("MypageMapper.retvRstrtnInsert", map);
    }

    /**
     * @Description : 접속제한 횟수감소
     * @param modelId
     * @return int
     * @Author : ant
     * @Create Date : 2016. 1. 29.
     */
    @Override
    public void retvRstrtnUpCnt(HashMap<String, String> map) {
        // TODO Auto-generated method stub
        sqlSessionTemplate.update("MypageMapper.retvRstrtnUpCnt", map);
    }

    /**
     * @Description : 접속한 횟수, 접속일자 현재일자로 초기화
     * @param modelId
     * @return int
     * @Author : ant
     * @Create Date : 2016. 1. 29.
     */
    @Override
    public void retvRstrtnUpSysDate(HashMap<String, String> map) {
        // TODO Auto-generated method stub
        sqlSessionTemplate.update("MypageMapper.retvRstrtnUpSysDate", map);
    }




    /**
     * @Description : 제주항공 회원 아이디 가지고 오기
     * @param String contractNum
     * @return int
     * @Author : ant
     * @Create Date : 2016. 1. 29.
     */
    @Override
    public String selectJejuid(String contractNum) {
        return sqlSessionTemplate.selectOne("MypageMapper.selectJejuid", contractNum);
    }

    /**
     * @Description : 제주항공 회원 아이디 기간 종료 업데이트
     * @param HashMap<String, Stirng> map
     * @return void
     * @Author : ant
     * @Create Date : 2016. 1. 29.
     */
    @Override
    public void updatePointMgmt(Map<String,String> map) {
        // SRM19040318884   M전산 사용하지 않는 소스 및 테이블 삭제
    }

    /**
     * @Description : 제주항공 회원 아이디 신규 인서트
     * @param HashMap<String, Stirng> map
     * @return void
     * @Author : ant
     * @Create Date : 2016. 1. 29.
     */
    @Override
    public void insertPointMgmt(Map<String,String> map) {
        // SRM19040318884   M전산 사용하지 않는 소스 및 테이블 삭제
    }

    /**
     * @Description : 원부증명서 가지고 오기
     * @param String userId
     * @return Map<String, String>
     * @Author : ant
     * @Create Date : 2016. 2. 22.
     */
    @Override
    public Map<String, Object> selectCertHist(String userId) {
        return sqlSessionTemplate.selectOne("MypageMapper.selectCertHist", userId);
    }

    /**
     * @Description : 원부증명서 신규 인서트
     * @param HashMap<String, Stirng> map
     * @return void
     * @Author : ant
     * @Create Date : 2016. 2. 22.
     */
    @Override
    public void insertCertHist(Map<String,String> map) {
        sqlSessionTemplate.insert("MypageMapper.insertCertHist", map);
    }

    /**
     * @Description : 단말 할부개월, 단말 할부원금 가지고 오기
     * @param String ncn
     * @return Map<String, String>
     * @Author : ant
     * @Create Date : 2016. 2. 22.
     */
    public Map<String, BigDecimal> selectModelSaleInfo(String ncn) {
        return sqlSessionTemplate.selectOne("MypageMapper.selectModelSaleInfo", ncn);
    }


    @Override
    public McpRateChgDto inertNmcpRateChg(McpRateChgDto mcpRateChgDto) {

        int result = (Integer)sqlSessionTemplate.insert("MypageMapper.inertNmcpRateChg", mcpRateChgDto);
        if (result > 0) {
            mcpRateChgDto.setResultFlag(true);
        }

        return mcpRateChgDto;
    }

    @Override
    public int updateNmcpRateChg(McpRateChgDto mcpRateChgDto) {
        return (Integer)sqlSessionTemplate.insert("MypageMapper.updateNmcpRateChg", mcpRateChgDto);
    }

    @Override
    public McpRateChgDto selectNmcpRateChg(McpRateChgDto mcpRateChgDto) {
        return sqlSessionTemplate.selectOne("MypageMapper.selectNmcpRateChg",mcpRateChgDto);
    }

    @Override
    public HashMap<Object, Object> callMcpRateChgImg(McpRateChgDto mcpRateChgDto) {
        return sqlSessionTemplate.selectOne("MypageMapper.callMcpRateChgImg", mcpRateChgDto);
    }

    @Override
    public List<McpRateChgDto> selectNmcpRateChgList(McpRateChgDto mcpRateChgDto) {
        return sqlSessionTemplate.selectList("MypageMapper.selectNmcpRateChgList", mcpRateChgDto);
    }

    @Override
    public int selectCallSvcLimitCount(HashMap<String, String> hm) {
        return (Integer)sqlSessionTemplate.selectOne("MypageMapper.selectCallSvcLimitCount", hm);
    }

    @Override
    public void insertMcpSelfcareStatistic(HashMap<String, String> hm) {
        sqlSessionTemplate.insert("MypageMapper.insertMcpSelfcareStatistic", hm);
    }


    @Override
    public boolean insertMcpRequestArm(McpRequestAgrmDto mcpRequestAgrmDto) {
        return  0 < sqlSessionTemplate.insert("MypageMapper.insertMcpRequestArm", mcpRequestAgrmDto);
    }

    @Override
    public String getCommendId(String contractNum){
        return sqlSessionTemplate.selectOne("MypageMapper.getCommendId", contractNum);
    }



      public List<SuspenChgTmlDto> selectSuspenChgTmp(SuspenChgTmlDto suspenChgTmlDto) {
          return sqlSessionTemplate.selectList("MypageMapper.selectSuspenChgTmp",suspenChgTmlDto);
      }
      public List<SuspenChgTmlDto> selectSuspenChgTmpList() {
          return sqlSessionTemplate.selectList("MypageMapper.selectSuspenChgTmpList");
      }

      public int suspenChgUpdate(SuspenChgTmlDto reqDto) {
          return sqlSessionTemplate.update("MypageMapper.suspenChgUpdate",reqDto);
      }

    @Override
    public boolean insertServiceAlterTrace(McpServiceAlterTraceDto serviceAlterTrace) {
        return  0 <  sqlSessionTemplate.insert("MypageMapper.insertServiceAlterTrace", serviceAlterTrace);
    }

    @Override
    public void insertMcpBillwayResend(BillWayChgDto billWayChgDto) {
        sqlSessionTemplate.insert("MypageMapper.insertMcpBillwayResend", billWayChgDto);
    }


    @Override
    public int checkAllreadPlanchgCount(McpServiceAlterTraceDto serviceAlterTrace) {
        Object resultObj = sqlSessionTemplate.selectOne("MypageMapper.checkAllreadPlanchgCount",serviceAlterTrace);
        if(resultObj instanceof Number){
            Number number = (Number) resultObj;
            return number.intValue();
        }else{
            throw new IllegalArgumentException(String.format("Wrong  resultClass type(%s) with queryId:%s, resultClass must be subclass of java.lang.Number", resultObj.getClass().getName(), "MypageMapper.checkAllreadPlanchgCount"));
        }
    }

    @Override
    public List<McpMrktHistDto> selectExistingConsent(String userId) {
        return this.sqlSessionTemplate.selectList("MypageMapper.selectExistingConsent", userId);
    }


    @Override
    public McpMrktHistDto getMrktHistInfo(McpMrktHistDto mrktHist){
        return sqlSessionTemplate.selectOne("MypageMapper.getMrktHistInfo", mrktHist);
    }

    @Override
    public List<SuspenChgTmlDto> selectChrgPrmtCheckTmp() {
        return sqlSessionTemplate.selectList("MypageMapper.selectChrgPrmtCheckTmp");
    }

    @Override
    public int updateChrgPrmtCheckTmp(SuspenChgTmlDto suspenChgTmlDto) {
        return sqlSessionTemplate.update("MypageMapper.updateChrgPrmtCheckTmp",suspenChgTmlDto);
    }

    @Override
    public int insertSuspenChgTmp(SuspenChgTmlDto suspenChgTmlDto) {
        return sqlSessionTemplate.insert("MypageMapper.insertSuspenChgTmp", suspenChgTmlDto);
    }


    @Override
    public Map<String, String> selectRateMst(String rateCd) {
        return sqlSessionTemplate.selectOne("MypageMapper.selectRateMst", rateCd);
    }
}
