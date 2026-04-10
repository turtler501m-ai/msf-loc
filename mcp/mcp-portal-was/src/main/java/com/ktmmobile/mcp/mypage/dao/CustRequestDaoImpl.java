
package com.ktmmobile.mcp.mypage.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.ktmmobile.mcp.mypage.dto.CancelConsultDto;
import com.ktmmobile.mcp.mypage.dto.CustRequestDto;
import com.ktmmobile.mcp.mypage.dto.MyNameChgReqDto;

@Repository
public class CustRequestDaoImpl implements CustRequestDao {

    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    @Override
    public long getCustRequestSeq() {
        return  sqlSessionTemplate.selectOne("CustRequestMapper.getCustRequestSeq");
    }

    @Override
    public boolean insertCustRequestMst(CustRequestDto custReuqestDto) {
        return 0 < sqlSessionTemplate.insert("CustRequestMapper.insertCustRequestMst", custReuqestDto);
    }

    public boolean insertCustRequestCallList(CustRequestDto custReuqestDto) {
        return 0 < sqlSessionTemplate.insert("CustRequestMapper.insertCustRequestCallList", custReuqestDto);
    }

    @Override
    public Map<String, String> getReqCallListData(long custRequestSeq) {
        return  sqlSessionTemplate.selectOne("CustRequestMapper.getReqCallListData", custRequestSeq);
    }

    public boolean updateEmvScanMstCL(String scanId) {
        return 0 < sqlSessionTemplate.update("CustRequestMapper.updateEmvScanMstCL", scanId);
    }

    @Override
    public boolean updateEmvScanFileCL(String scanId) {
        return 0 < sqlSessionTemplate.update("CustRequestMapper.updateEmvScanFileCL", scanId);
    }

    @Override
    public boolean updateCustRequestCallListScanId(CustRequestDto custReuqestDto) {
        return 0 < sqlSessionTemplate.update("CustRequestMapper.updateCustRequestCallListScanId", custReuqestDto);
    }

    @Override
    public Map<String, String> getReqNmChgData(long custRequestSeq) {
        return  sqlSessionTemplate.selectOne("CustRequestMapper.getReqNmChgData", custRequestSeq);
    }

    @Override
    public boolean updateEmvScanMstNC(String scanId) {
        return 0 < sqlSessionTemplate.update("CustRequestMapper.updateEmvScanMstNC", scanId);
    }

    @Override
    public boolean updateEmvScanFileNC(String scanId) {
        return 0 < sqlSessionTemplate.update("CustRequestMapper.updateEmvScanFileNC", scanId);
    }

    @Override
    public boolean updateReqNmChgScanId(MyNameChgReqDto myNameChgReqDto) {
        return 0 < sqlSessionTemplate.update("CustRequestMapper.updateReqNmChgScanId", myNameChgReqDto);
    }

    @Override
    public List<HashMap<String, String>> getAppFormPointList(String groupCode) {
        return sqlSessionTemplate.selectList("CustRequestMapper.getAppFormGroupPointList", groupCode);
    }

    public boolean insertCustRequestJoinForm(CustRequestDto custReuqestDto) {
        return 0 < sqlSessionTemplate.insert("CustRequestMapper.insertCustRequestJoinForm", custReuqestDto);
    }

    @Override
    public boolean updateEmvScanMst(CustRequestDto custReuqestDto) {
        return 0 < sqlSessionTemplate.update("CustRequestMapper.updateEmvScanMst", custReuqestDto);
    }

    @Override
    public boolean updateEmvScanFile(CustRequestDto custReuqestDto) {
        return 0 < sqlSessionTemplate.update("CustRequestMapper.updateEmvScanFile", custReuqestDto);
    }

    @Override
    public int getMaxFileNum(String orgScanId) {
        return sqlSessionTemplate.selectOne("CustRequestMapper.getMaxFileNum", orgScanId);
    }

    @Override
    public boolean insertEmvScanFile(CustRequestDto custRequestDto) {
        return 0 < sqlSessionTemplate.insert("CustRequestMapper.insertEmvScanFile", custRequestDto);
    }

    @Override
    public Map<String, String> getReqInsrData(long custRequestSeq) {
        return sqlSessionTemplate.selectOne("CustRequestMapper.getReqInsrData", custRequestSeq);
    }

    @Override
    public boolean insertCustRequestInsr(CustRequestDto custReuqestDto) {
        return 0 < sqlSessionTemplate.insert("CustRequestMapper.insertCustRequestInsr", custReuqestDto);
    }

    @Override
    public Map<String, String> getCancelConsultData(long custRequestSeq) {
        return sqlSessionTemplate.selectOne("CustRequestMapper.getCancelConsultData", custRequestSeq);
    }

    @Override
    public boolean updateCancelReqScanId(CancelConsultDto cancelConsultDto) {
        return 0 < sqlSessionTemplate.update("CustRequestMapper.updateCancelReqScanId", cancelConsultDto);
    }

    @Override
    public boolean updateEmvScanMstCC(String scanId) {
        return 0 < sqlSessionTemplate.update("CustRequestMapper.updateEmvScanMstCC", scanId);
    }

    @Override
    public boolean updateEmvScanFileCC(String scanId) {
        return 0 < sqlSessionTemplate.update("CustRequestMapper.updateEmvScanFileCC", scanId);
    }
}
