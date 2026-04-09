/**
 *
 */
package com.ktmmobile.msf.form.servicechange.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import com.ktmmobile.msf.form.servicechange.dto.AgreeDto;
import com.ktmmobile.msf.form.servicechange.dto.McpUserCntrMngDto;
import com.ktmmobile.msf.common.mplatform.vo.CodeVO;
import com.ktmmobile.msf.common.mplatform.vo.UserVO;

/**
 * @author ANT_FX700_02
 *
 */
@Repository
public class MypageUserDaoImpl implements MypageUserDao{

    private static Logger logger = LoggerFactory.getLogger(MypageUserDaoImpl.class);

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    /**
     * @Description : 공통코드를 가져온다
     * @param key
     * @return
     * @Author : ant
     * @Create Date : 2016. 1. 20
     */
    @Override
    public List<CodeVO> selectCodeList(String key) {
        // TODO Auto-generated method stub
        return sqlSessionTemplate.selectList("MypageMapper.selectCodeList", key);
    }

    /**
    * @Description : 회원을 선택해서 가져온다.
    * @param key
    * @return
    * @Author : ant
    * @Create Date : 2016. 1. 20
    */
    @Override
    public UserVO selectUser(String userid) {
        // TODO Auto-generated method stub
        return sqlSessionTemplate.selectOne("MypageMapper.selectUser", userid);
    }

    @Override
    public int pwCheck(HashMap<String, String> map) {
        // TODO Auto-generated method stub
        return (Integer)sqlSessionTemplate.selectOne("MypageMapper.pwCheck", map);
    }

    @Override
    public void pwUpdate(HashMap<String, String> map) {
        // TODO Auto-generated method stub
        sqlSessionTemplate.update("MypageMapper.pwUpdate", map);
    }

    @Override
    public int userUpdate(UserVO userVO) {
        // TODO Auto-generated method stub
        return sqlSessionTemplate.update("MypageMapper.userUpdate", userVO);
    }

    @Override
    public int userRegularUpdate(UserVO userVO) {
        // TODO Auto-generated method stub
        return sqlSessionTemplate.update("MypageMapper.userRegularUpdate", userVO);
    }

    @Override
    public String userRegularCheck(UserVO userVO) {
        // TODO Auto-generated method stub
        return sqlSessionTemplate.selectOne("MypageMapper.userRegularCheck", userVO);
    }

    @Override
    public void insertRegularUpdate(UserVO userVO) {
        // TODO Auto-generated method stub
        sqlSessionTemplate.insert("MypageMapper.insertRegularUpdate", userVO);
    }

    @Override
    public void userChange(HashMap<String, String> map) {
        // TODO Auto-generated method stub
        sqlSessionTemplate.update("MypageMapper.userChange", map);
    }

    @Override
    public int userRepChange(HashMap<String, String> map) {
        // TODO Auto-generated method stub
        return sqlSessionTemplate.update("MypageMapper.userRepChange", map);
    }


    @Override
    public int updateUserCntrMng(McpUserCntrMngDto mcpUserCntrMng) {
        return sqlSessionTemplate.update("MypageMapper.updateUserCntrMng", mcpUserCntrMng);
    }

    @Override
    public void insertRec(UserVO userVO) {
        // TODO Auto-generated method stub
        sqlSessionTemplate.update("MypageMapper.insertRec", userVO);
    }

    @Override
    public void updateRec(UserVO userVO) {
        // TODO Auto-generated method stub
        sqlSessionTemplate.update("MypageMapper.updateRec", userVO);
    }

    @Override
    public void deleteUserCntr(String userid) {
        // TODO Auto-generated method stub
        sqlSessionTemplate.delete("MypageMapper.deleteUserCntr", userid);
    }

    @Override
    public UserVO selectUserByUseridAndPassword(HashMap<String, String> map) {
        // TODO Auto-generated method stub
        return sqlSessionTemplate.selectOne("MypageMapper.selectUserByUseridAndPassword", map);
    }

    @Override
    public void callMcpMrktAgr(String userId, String emailAgrYn, String smsAgrYn) {
        HashMap<String,String> hm = new HashMap<String,String>();
        hm.put("userId", userId);
        hm.put("emailAgrYn", emailAgrYn);
        hm.put("smsAgrYn", smsAgrYn);
        sqlSessionTemplate.selectOne("MypageMapper.callMcpMrktAgr", hm);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, String> selectMspMrktAgrYn(String contractNum) {

        Map<String, String> mspMrktAgrHm = null;
        RestTemplate restTemplate = new RestTemplate();
         try{
             mspMrktAgrHm = restTemplate.postForObject(apiInterfaceServer + "/mypage/mspMrktAgrYn", contractNum, Map.class);
         }  catch (RestClientException e) {
             logger.debug("mspMrktAgrYn error==>" + e.getMessage());
         }   catch(Exception e) {
             logger.debug("mspMrktAgrYn error");
         }
         return mspMrktAgrHm;
    }

    @Override
    public void callMspMrktAgr(String ctn, String personalInfoCollectAgree, String othersTrnsAgree, String mrktAgr, String othersTrnsKtAgree, String othersAdReceiveAgree, String indvLocaPrvAgree, String userId) {

        RestTemplate restTemplate = new RestTemplate();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("personalInfoCollectAgree", personalInfoCollectAgree);
        params.put("mrktAgr", mrktAgr);
        params.put("othersTrnsAgree", othersTrnsAgree);
        params.put("othersTrnsKtAgree", othersTrnsKtAgree);
        params.put("othersAdReceiveAgree", othersAdReceiveAgree);
        params.put("indvLocaPrvAgree", indvLocaPrvAgree);
        params.put("ctn", ctn);
        params.put("userId", userId);

        try{
            restTemplate.postForObject(apiInterfaceServer + "/mypage/callMspMrktAgrYn", params, String.class); // MypageMapper.callMspMrktAgr
        }  catch (RestClientException e) {
            throw e;
        }   catch(Exception e) {
             throw e;
         }

    }



    @Override
    public List<Map<String, String>> selectMcpMrkthist(String userId) {
        return sqlSessionTemplate.selectList("MypageMapper.selectMcpMrkthist", userId);
    }


    @Override
    public List<String> selectUserList(String userId) {
        List<String> list = sqlSessionTemplate.selectList("MypageMapper.selectUserList", userId);
        return list;
    }

//    @Override
//    public void deleteUserSns(JoinDto joinDto) {
//        sqlSessionTemplate.delete("MypageMapper.deleteUserSns", joinDto);
//
//    }

    @Override
    public List<String> checkDatHst(UserVO userVO1) {

        List<String> val = sqlSessionTemplate.selectList("MypageMapper.checkDatHst", userVO1);

        return val;
    }

    @Override
    public void insertUserHst(UserVO userVO1) {
        sqlSessionTemplate.insert("MypageMapper.insertUserHst", userVO1);
    }

    @Override
    public void updateUserHst(UserVO userVO1) {
        sqlSessionTemplate.update("MypageMapper.updateUserHst", userVO1);
    }

    @Override
    public void deleteDormancyUserHst(UserVO userVO1) {
        sqlSessionTemplate.delete("MypageMapper.deleteDormancyUserHst", userVO1);
    }

    @Override
    public void deleteUserHst(UserVO userVO1) {
        sqlSessionTemplate.delete("MypageMapper.deleteUserHst", userVO1);
    }

    @Override
    public AgreeDto selectMspMrktAgrTime(String contractNum) {
        AgreeDto agreeInfo = null;
        RestTemplate restTemplate = new RestTemplate();
        try{
            agreeInfo = restTemplate.postForObject(apiInterfaceServer + "/mypage/selectMspMrktAgrTime", contractNum, AgreeDto.class);
        }  catch (RestClientException e) {
            logger.debug("mspMrktAgrYn error==>" + e.getMessage());
        }   catch(Exception e) {
            logger.debug("mspMrktAgrYn error");
        }
        return agreeInfo;
    }

    @Override
    public void deleteSnsLoginTxn(String userid) {
        sqlSessionTemplate.delete("MypageMapper.deleteSnsLoginTxn", userid);
    }

    @Override
    public void updateLoginHistory(String userid) {
        sqlSessionTemplate.update("MypageMapper.updateLoginHistory", userid);
    }

    @Override
    public int updateRemindYn(HashMap<String, String> map) {
        return sqlSessionTemplate.update("MypageMapper.updateRemindYn", map);
    }

    @Override
    public Map<String, Object> getAgentInfo(String userId) {
        return sqlSessionTemplate.selectOne("MypageMapper.getAgentInfo", userId);
    }

    @Override
    public int getRegularCnt(Map<String, String> map) {
        return sqlSessionTemplate.selectOne("MypageMapper.getRegularCnt", map);
    }
}
