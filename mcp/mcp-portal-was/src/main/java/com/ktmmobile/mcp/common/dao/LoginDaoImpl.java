package com.ktmmobile.mcp.common.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ktmmobile.mcp.common.dto.McpUserDarkwebDto;
import com.ktmmobile.mcp.common.dto.NiceResDto;
import com.ktmmobile.mcp.common.dto.PersonalPolicyDto;
import com.ktmmobile.mcp.common.dto.RoleMenuDto;
import com.ktmmobile.mcp.common.dto.UserSessionDto;
import com.ktmmobile.mcp.common.dto.db.NmcpAutoLoginTxnDto;

@Repository
public class LoginDaoImpl implements LoginDao {

    @Autowired
    SqlSessionTemplate sqlSessionTemplate;


    @Override
    public int addBirthGenderAjax(UserSessionDto userSessionDto) {
        return sqlSessionTemplate.update("LoginMapper.addBirthGenderAjax", userSessionDto);
    }

    @Override
    public boolean updateUserSet(UserSessionDto userSessionDto) {
        return 0 < sqlSessionTemplate.update("LoginMapper.updateUserSet", userSessionDto);
    }

    @Override
    public UserSessionDto loginProcess(UserSessionDto userSessionDto) {
        return sqlSessionTemplate.selectOne("LoginMapper.loginProcess", userSessionDto);
    }

    @Override
    public UserSessionDto getUserInfo(UserSessionDto userSessionDto) {
        return sqlSessionTemplate.selectOne("LoginMapper.getUserInfo", userSessionDto);
    }

    @Override
    public UserSessionDto getDormancyInfo(UserSessionDto userSessionDto) {
        return sqlSessionTemplate.selectOne("LoginMapper.getDormancyInfo", userSessionDto);
    }

    @Override
    public List<RoleMenuDto> getRoleMenuList(String roleCode) {
        // TODO Auto-generated method stub
        return sqlSessionTemplate.selectList("LoginMapper.getRoleMenuList", roleCode);
    }

    @Override
    public int selectIdCheckCount(Map paraMap) {
        // TODO Auto-generated method stub
        return (Integer)sqlSessionTemplate.selectOne("LoginMapper.selectIdCheckCount", paraMap);
    }

    @Override
    public UserSessionDto getUserAuthSms(UserSessionDto userSessionDto) {
        // TODO Auto-generated method stub
        return sqlSessionTemplate.selectOne("LoginMapper.getUserAuthSms", userSessionDto);
    }

    @Override
    public UserSessionDto checkNiceCertAjax(UserSessionDto searchVO) {
        return sqlSessionTemplate.selectOne("LoginMapper.checkNiceCertAjax", searchVO);
    }


    @Override
    public UserSessionDto checkNiceCertDormantAjax(UserSessionDto searchVO) {
        return sqlSessionTemplate.selectOne("LoginMapper.checkNiceCertDormantAjax", searchVO);
    }

    @Override
    public UserSessionDto checkIdNameAjax(UserSessionDto searchVO) {
        return sqlSessionTemplate.selectOne("LoginMapper.checkIdNameAjax", searchVO);
    }

    @Override
    public UserSessionDto checkIdNameDormantAjax(UserSessionDto searchVO) {
        return sqlSessionTemplate.selectOne("LoginMapper.checkIdNameDormantAjax", searchVO);
    }

    @Override
    public int tmpPasswordUpdate(UserSessionDto userSessionDto) {
        return sqlSessionTemplate.update("LoginMapper.tmpPasswordUpdate", userSessionDto);
    }

    @Override
    public int updateHit(String userId) {
        return sqlSessionTemplate.update("LoginMapper.updateHit", userId);
    }

    @Override
    public PersonalPolicyDto personalPolicySelect() {
        return sqlSessionTemplate.selectOne("LoginMapper.personalPolicySelect", null);
    }

    @Override
    public int updateNewPassword(HashMap<String, String> map) {
        return sqlSessionTemplate.update("LoginMapper.updateNewPassword", map);
    }

    @Override
    public int updateNewPasswordDormant(HashMap<String, String> map) {
        return sqlSessionTemplate.update("LoginMapper.updateNewPasswordDormant", map);
    }

    @Override
    public int checkLoginAttack(UserSessionDto userSessionDto) {
        // TODO Auto-generated method stub

        Object resultObj = sqlSessionTemplate.selectOne("LoginMapper.checkLoginAttack",userSessionDto);
        if(resultObj instanceof Number){
            Number number = (Number) resultObj;
            return number.intValue();
        }else{
            throw new IllegalArgumentException(String.format("Wrong  resultClass type(%s) with queryId:%s, resultClass must be subclass of java.lang.Number", resultObj.getClass().getName(), "Appform_Query.checkLoginAttack"));
        }
    }

    @Override
    public NmcpAutoLoginTxnDto getLoginAutoLogin(UserSessionDto userSessionDto) {
        return sqlSessionTemplate.selectOne("LoginMapper.getLoginAutoLogin", userSessionDto);
    }

    public void insertAutoLoginTxn(NmcpAutoLoginTxnDto nmcpAutoLoginTxnDto) {
        sqlSessionTemplate.update("LoginMapper.insertAutoLoginTxn", nmcpAutoLoginTxnDto);
    }

    public int updateAutoLoginTxn(NmcpAutoLoginTxnDto nmcpAutoLoginTxnDto) {
        return sqlSessionTemplate.update("LoginMapper.updateAutoLoginTxn", nmcpAutoLoginTxnDto);
    }

    @Override
    public int insertDormantToMcpUser(NiceResDto niceResDto) {
        return sqlSessionTemplate.update("LoginMapper.insertDormantToMcpUser", niceResDto);
    }

    @Override
    public int updateDormantUserChg(String dupInfo) {
        return sqlSessionTemplate.update("LoginMapper.updateDormantUserChg", dupInfo);

    }

    @Override
    public UserSessionDto dormancyLoginProcess(UserSessionDto userSessionDto) {
        return sqlSessionTemplate.selectOne("LoginMapper.dormancyLoginProcess", userSessionDto);
    }

    @Override
    public List<McpUserDarkwebDto> getMcpUserDarkwebList(String userId) {
        return sqlSessionTemplate.selectList("LoginMapper.getMcpUserDarkwebList", userId);
    }
    @Override
    public int updateMcpUserDarkweb(String userId) {
        return sqlSessionTemplate.update("LoginMapper.updateMcpUserDarkweb", userId);
    }

    @Override
    public int updatePwChgInfoNoShow(String userId) {
        return sqlSessionTemplate.update("LoginMapper.updatePwChgInfoNoShow", userId);
    }

    @Override
    public int findPinToIdCnt(UserSessionDto searchVO) {
        return sqlSessionTemplate.selectOne("LoginMapper.findPinToIdCnt", searchVO);
    }

    @Override
    public int resetDormancy(UserSessionDto searchVO) {
        return sqlSessionTemplate.update("LoginMapper.resetDormancy", searchVO);
    }

    @Override
    public int limitCnt(UserSessionDto searchVO) {
        return sqlSessionTemplate.selectOne("LoginMapper.limitCnt", searchVO);
    }

    @Override
    public int insertIpLimit(UserSessionDto searchVO) {
        return sqlSessionTemplate.insert("LoginMapper.insertIpLimit", searchVO);
    }

    @Override
    public int limitTime() {
        return sqlSessionTemplate.selectOne("LoginMapper.limitTime");
    }

    @Override
    public boolean intDormancyInfoSub(UserSessionDto userSessionDto) {
        return 0 < sqlSessionTemplate.update("LoginMapper.insertDormantToMcpUserSub", userSessionDto);
    }

    @Override
    public boolean delDormancyInfoDel(UserSessionDto userSessionDto) {
        return 0 < sqlSessionTemplate.update("LoginMapper.deleteDormantToMcpUser", userSessionDto);
    }

    @Override
    public int selCntrCnt(String userId) {
        return sqlSessionTemplate.selectOne("LoginMapper.selCntrCnt", userId);
    }

    @Override
    public boolean updCntr(String userId) {
        return 0 < sqlSessionTemplate.update("LoginMapper.updCntr", userId);
    }

    @Override
    public int getRecaptchaTraceCnt(Map<String, String> paraMap) {
        return sqlSessionTemplate.selectOne("LoginMapper.getRecaptchaTraceCnt", paraMap);
    }

    @Override
    public String getDupInfoByUserId(String userId) {
        return sqlSessionTemplate.selectOne("LoginMapper.getDupInfoByUserId", userId);
    }
}
