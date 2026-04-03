
package com.ktmmobile.msf.form.newchange.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import com.ktmmobile.msf.form.common.dto.UsimBasDto;
import com.ktmmobile.msf.form.newchange.dto.AcenDto;
import com.ktmmobile.msf.form.newchange.dto.AppformReqDto;
import com.ktmmobile.msf.form.newchange.dto.IntmInsrRelDTO;
import com.ktmmobile.msf.form.newchange.dto.JuoSubInfoDto;
import com.ktmmobile.msf.form.newchange.dto.McpRequestPayInfoDto;
import com.ktmmobile.msf.form.newchange.dto.McpUploadPhoneInfoDto;
import com.ktmmobile.msf.form.newchange.dto.OsstUc0ReqDto;
import com.ktmmobile.msf.system.common.dto.db.McpRequestAdditionDto;
import com.ktmmobile.msf.system.common.dto.db.McpRequestCstmrDto;
import com.ktmmobile.msf.system.common.dto.db.McpRequestDlvryDto;
import com.ktmmobile.msf.system.common.dto.db.McpRequestDto;
import com.ktmmobile.msf.system.common.dto.db.McpRequestMoveDto;
import com.ktmmobile.msf.system.common.dto.db.McpRequestOsstDto;
import com.ktmmobile.msf.system.common.dto.db.McpRequestSaleinfoDto;
import com.ktmmobile.msf.system.common.dto.db.McpRequestSelfDlvryDto;
import com.ktmmobile.msf.system.common.dto.db.NmcpAppFormMstDto;

@Repository
public class AppformDaoImpl implements AppformDao {

    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    @Override
    public AppformReqDto getAppFormTemp(String userId) {
        return sqlSessionTemplate.selectOne("AppformMapper.getAppFormTempById", userId);
    }

    @Override
    public Map<String,String> getAppFormData(long requestKey){

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(apiInterfaceServer + "/appform/requestJoinDataByRjoinKeyNew", requestKey, Map.class); // Appform_Query.getRequestJoinDataByRjoinKeyNew
    }

    @Override
    public Map<String,String> getAppFormUserData(long requestKey){
        return sqlSessionTemplate.selectOne("AppformMapper.getRequestJoinUserData", requestKey);
    }

    @Override
    public List<HashMap<String,String>> getAppFormPointList() {
        return sqlSessionTemplate.selectList("AppformMapper.getAppFormPointList");
    }

    @Override
    public List<HashMap<String, String>> getAppFormPointList(String groupCode) {
        return sqlSessionTemplate.selectList("AppformMapper.getAppFormGroupPointList", groupCode);
    }

    @Override
    public List<HashMap<String, String>> getAppFormPageList(String pageCode) {
        return sqlSessionTemplate.selectList("AppformMapper.getAppFormPageList", pageCode);
    }

    @Override
    public boolean updateAppFormXmlYn(long requestKey) {
        return  0 < sqlSessionTemplate.update("AppformMapper.updateAppFormXmlYn", requestKey);
    }

    @Override
    public boolean updateAppForPstate(long requestKey) {
        return  0 < sqlSessionTemplate.update("AppformMapper.updateAppForPstate", requestKey);
    }

    @Override
    public boolean updateMcpRequestSaleinfo(McpRequestSaleinfoDto mcpRequestSaleinfoDto) {
        return  0 < sqlSessionTemplate.update("AppformMapper.updateMcpRequestSaleinfo", mcpRequestSaleinfoDto);
    }

    @Override
    public boolean insertMcpRequest(McpRequestDto appformReq) {
        return     0 < sqlSessionTemplate.insert("AppformMapper.insertMcpRequest",appformReq);
    }

    @Override
    public boolean insertMcpRequestCstmr(AppformReqDto appformReq)  {
        return     0 < sqlSessionTemplate.insert("AppformMapper.insertMcpRequestCstmr",appformReq);
    }

    @Override
    public boolean insertMcpRequestAgent(AppformReqDto appformReq) {
        return     0 < sqlSessionTemplate.insert("AppformMapper.insertMcpRequestAgent",appformReq);
    }

    @Override
    public boolean insertMcpRequestMove(AppformReqDto appformReq) {
        return     0 < sqlSessionTemplate.insert("AppformMapper.insertMcpRequestMove",appformReq);
    }

    @Override
    public boolean insertMcpRequestPayment(AppformReqDto appformReq) {
        return     0 < sqlSessionTemplate.insert("AppformMapper.insertMcpRequestPayment",appformReq);
    }

    @Override
    public boolean insertMcpRequestSaleinfo(AppformReqDto appformReq) {
        return     0 < sqlSessionTemplate.insert("AppformMapper.insertMcpRequestSaleinfo",appformReq);
    }

    @Override
    public boolean insertMcpRequestDlvry(AppformReqDto appformReq) {
        return     0 < sqlSessionTemplate.insert("AppformMapper.insertMcpRequestDlvry",appformReq);
    }

    @Override
    public boolean insertMcpRequestReq(AppformReqDto appformReq) {
        return     0 < sqlSessionTemplate.insert("AppformMapper.insertMcpRequestReq",appformReq);
    }

    @Override
    public boolean insertMcpRequestChange(AppformReqDto appformReq) {
        return     0 < sqlSessionTemplate.insert("AppformMapper.insertMcpRequestChange",appformReq);
    }



    @Override
    public boolean insertMcpRequestDvcChg(AppformReqDto appformReq) {
        return     0 < sqlSessionTemplate.insert("AppformMapper.insertMcpRequestDvcChg",appformReq);
    }

    @Override
    public boolean insertMcpRequestAddition(AppformReqDto appformReqDto) {
        return     0 < sqlSessionTemplate.update("AppformMapper.insertMcpRequestAddition",appformReqDto);
    }

    @Override
    public int insertMcpRequestAdditionPromotion(AppformReqDto appformReqDto) {
        RestTemplate restTemplate = new RestTemplate();
        return    restTemplate.postForObject(apiInterfaceServer + "/appform/mcpRequestAdditionPromotion", appformReqDto, Integer.class); // Appform_Query.insertMcpRequestAdditionPromotion
    }

    @Override
    public boolean insertMcpRequestState(AppformReqDto appformReqDto) {
        return     0 < sqlSessionTemplate.insert("AppformMapper.insertMcpRequestState",appformReqDto);
    }

    @Override
    public Long generateRequestKey() {
        return  sqlSessionTemplate.selectOne("AppformMapper.generateRequestKey");
    }

    @Override
    public String generateResNo(){
        return  sqlSessionTemplate.selectOne("AppformMapper.generateResNo");
    }

    @Override
    public JuoSubInfoDto selRMemberAjax(JuoSubInfoDto juoSubInfoDto){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(apiInterfaceServer + "/appform/selRMemberAjax", juoSubInfoDto, JuoSubInfoDto.class); // Appform_Query.selRMemberAjax
    }

    @Override
    public McpRequestDto getMcpRequest(long requestKey) {
        McpRequestDto requestDto = new McpRequestDto();
        requestDto.setRequestKey(requestKey);
        return  sqlSessionTemplate.selectOne("AppformMapper.getMcpRequest",requestDto);
    }

    @Override
    public McpRequestDto getMcpRequest(McpRequestDto requestDto) {
        return  sqlSessionTemplate.selectOne("AppformMapper.getMcpRequest",requestDto);
    }

    @Override
    public AppformReqDto getCopyMcpRequest(AppformReqDto appformReq) {
        return  sqlSessionTemplate.selectOne("AppformMapper.getCopyMcpRequest",appformReq);
    }

    @Override
    public McpRequestCstmrDto getMcpRequestCstmr(long requestKey) {
        return  sqlSessionTemplate.selectOne("Appform_Query.getMcpRequestCstmr",requestKey);
    }

    @Override
    public McpRequestDlvryDto getMcpRequestDlvry(long requestKey) {
        return  sqlSessionTemplate.selectOne("Appform_Query.getMcpRequestDlvry",requestKey);
    }

    @Override
    public McpRequestSaleinfoDto getMcpRequestSaleinfo(long requestKey) {
        return  sqlSessionTemplate.selectOne("Appform_Query.getMcpRequestSaleinfo",requestKey);
    }

    @Override
    public List<McpRequestAdditionDto> getMcpAdditionList (McpRequestAdditionDto mcpRequestAdditionDto) {
        return  sqlSessionTemplate.selectList("AppformMapper.getMcpAdditionList", mcpRequestAdditionDto) ;
    }

    @Override
    public boolean insertMcpRequestClause(HashMap<String, Object> hm) {
        return     0 < sqlSessionTemplate.insert("Appform_Query.insertMcpRequestClause",hm);
    }

    @Override
    public int isOwnerCount(AppformReqDto appformReqDto) {

        Object resultObj = sqlSessionTemplate.selectOne("AppformMapper.isOwnerCount",appformReqDto);
        if(resultObj instanceof Number){
            Number number = (Number) resultObj;
            return number.intValue();
        }else{
            throw new IllegalArgumentException(String.format("Wrong  resultClass type(%s) with queryId:%s, resultClass must be subclass of java.lang.Number", resultObj.getClass().getName(), "Appform_Query.isOwnerCount"));
        }
    }



    @Override
    public int getMcpRequestCount(AppformReqDto appformReqDto) {

        Object resultObj = sqlSessionTemplate.selectOne("Appform_Query.getMcpRequestCount",appformReqDto);
        if(resultObj instanceof Number){
            Number number = (Number) resultObj;
            return number.intValue();
        }else{
            throw new IllegalArgumentException(String.format("Wrong  resultClass type(%s) with queryId:%s, resultClass must be subclass of java.lang.Number", resultObj.getClass().getName(), "Appform_Query.getMcpRequestCount"));
        }
    }

    @Override
    public int checkJejuCodeCount(String rateCd) {

        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("rateCd", rateCd);

        Object resultObj = restTemplate.postForObject(apiInterfaceServer + "/appform/checkJejuCodeCount", params, Integer.class); // Appform_Query.checkJejuCodeCount

        if(resultObj instanceof Number){
            Number number = (Number) resultObj;
            return number.intValue();
        }else{
            throw new IllegalArgumentException(String.format("Wrong  resultClass type(%s) with queryId:%s, resultClass must be subclass of java.lang.Number", resultObj.getClass().getName(), apiInterfaceServer + "/appform/checkJejuCodeCount")); // Appform_Query.checkJejuCodeCount
        }
    }

    @Override
    public int checkClauseJehuRatecd(String rateCd) {
        Object resultObj = sqlSessionTemplate.selectOne("Appform_Query.checkClauseJehuRatecd",rateCd);
        if(resultObj instanceof Number){
            Number number = (Number) resultObj;
            return number.intValue();
        }else{
            throw new IllegalArgumentException(String.format("Wrong  resultClass type(%s) with queryId:%s, resultClass must be subclass of java.lang.Number", resultObj.getClass().getName(), "Appform_Query.checkClauseJehuRatecd"));
        }
    }

    @Override
    public McpRequestDto getMspPrdtCode(AppformReqDto appformReqDto) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(apiInterfaceServer + "/appform/mspPrdtCode", appformReqDto, McpRequestDto.class); // Appform_Query.getMspPrdtCode
    }

    @Override
    public String selPrdtcolCd(AppformReqDto appformReqDto) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(apiInterfaceServer + "/appform/selPrdtcolCd", appformReqDto, String.class); // Appform_Query.selPrdtcolCd
    }

    @Override
    public String getAtribVal(HashMap<String, Object> hm) {
        return sqlSessionTemplate.selectOne("Appform_Query.getAtribVal",hm);
    }

    @Override
    public AppformReqDto getMarketRequest(AppformReqDto appformReqDto) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(apiInterfaceServer + "/appform/marketRequest", appformReqDto, AppformReqDto.class); // Appform_Query.getMarketRequest
    }

    @Override
    public List<AppformReqDto> selectModelMonthlyList(AppformReqDto appformReqDto){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(apiInterfaceServer + "/appform/modelMonthlyList", appformReqDto, List.class); // Appform_Query.selectModelMonthlyList
    }

    @Override
    public List<AppformReqDto> selectMonthlyListMarket(AppformReqDto appformReqDto){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(apiInterfaceServer + "/appform/monthlyListMarket", appformReqDto, List.class); // Appform_Query.selectMonthlyListMarket
    }

    @Override
    public List<AppformReqDto> selectPrdtColorList(AppformReqDto appformReqDto){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(apiInterfaceServer + "/appform/prdtColorList", appformReqDto, List.class); // Appform_Query.selectPrdtColorList
    }

    @Override
    public NmcpAppFormMstDto selectNmcpAppFormMst(String pageCode) {
        return sqlSessionTemplate.selectOne("Appform_Query.selectNmcpAppFormMst", pageCode);
    }

    @Override
    public String getAgentCode(String cntpntShopId) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(apiInterfaceServer + "/appform/agentCode", cntpntShopId, String.class);
    }

    @Override
    public Map<String,String> getAgentInfoOjb(String cntpntShopId){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(apiInterfaceServer + "/appform/agentInfoOjb", cntpntShopId, Map.class);
    }

    @Override
    public boolean updateMcpRequestCstmr(McpRequestCstmrDto mcpRequestCstmrDto){
        return     0 < sqlSessionTemplate.update("AppformMapper.updateMcpRequestCstmr",mcpRequestCstmrDto);
    }

    @Override
    public boolean updateMcpRequest(McpRequestDto mcpRequestDto){
        return     0 < sqlSessionTemplate.update("AppformMapper.updateMcpRequest",mcpRequestDto);
    }

    @Override
    public boolean updateMcpRequestCallBack(AppformReqDto appformReqDto) {
        return     0 < sqlSessionTemplate.update("AppformMapper.updateMcpRequestCallBack",appformReqDto);
    }

    @Override
    public boolean updateMcpRequestMove(McpRequestMoveDto mcpRequestMoveDto){
        return     0 < sqlSessionTemplate.update("AppformMapper.updateMcpRequestMove",mcpRequestMoveDto);
    }

    @Override
    public McpRequestOsstDto getRequestOsst(McpRequestOsstDto mcpRequestOsstDto) {
        return sqlSessionTemplate.selectOne("AppformMapper.getRequestOsst", mcpRequestOsstDto);
    }

    @Override
    public int requestOsstCount(McpRequestOsstDto mcpRequestOsstDto) {
        Object resultObj = sqlSessionTemplate.selectOne("AppformMapper.requestOsstCount",mcpRequestOsstDto);
        if(resultObj instanceof Number){
            Number number = (Number) resultObj;
            return number.intValue();
        }else{
            throw new IllegalArgumentException(String.format("Wrong  resultClass type(%s) with queryId:%s, resultClass must be subclass of java.lang.Number", resultObj.getClass().getName(), "AppFormMapper.requestOsstCount"));
        }
    }

    @Override
    public boolean insertMcpRequestOsst(McpRequestOsstDto mcpRequestOsstDto) {
        return     0 < sqlSessionTemplate.insert("AppformMapper.insertMcpRequestOsst",mcpRequestOsstDto);
    }

    @Override
    public boolean updateMcpRequestOsstOrdNo(McpRequestOsstDto mcpRequestOsstDto) {
        return     0 < sqlSessionTemplate.update("AppformMapper.updateMcpRequestOsstOrdNo",mcpRequestOsstDto);
    }


  //초기화 삭제
    /*appformDao.deleteMcpRequestReq(appformReqDto) ;
    appformDao.deleteMcpRequestAddition(appformReqDto) ;
    appformDao.deleteMcpRequestAgent(appformReqDto) ;
    appformDao.deleteRequestPayment(appformReqDto) ;
    appformDao.deleteMcpRequestChange(appformReqDto);
    appformDao.deleteMcpRequestCommend(appformReqDto);*/

    @Override
    public int deleteMcpRequestReq(AppformReqDto appformReqDto) {
        return sqlSessionTemplate.delete("AppformMapper.deleteMcpRequestReq",appformReqDto);
    }

    @Override
    public int deleteMcpRequestAddition(AppformReqDto appformReqDto) {
        return sqlSessionTemplate.delete("AppformMapper.deleteMcpRequestAddition",appformReqDto);
    }

    @Override
    public int deleteMcpRequestAgent(AppformReqDto appformReqDto) {
        return sqlSessionTemplate.delete("AppformMapper.deleteMcpRequestAgent",appformReqDto);
    }

    @Override
    public int deleteRequestPayment(AppformReqDto appformReqDto) {
        return sqlSessionTemplate.delete("AppformMapper.deleteRequestPayment",appformReqDto);
    }

    @Override
    public int deleteMcpRequestChange(AppformReqDto appformReqDto) {
        return sqlSessionTemplate.delete("AppformMapper.deleteMcpRequestChange",appformReqDto);
    }

    @Override
    public int deleteMcpRequestDlvry(AppformReqDto appformReqDto) {
        return sqlSessionTemplate.delete("AppformMapper.deleteMcpRequestDlvry",appformReqDto);
    }

    @Override
    public int deleteMcpRequestSaleinfo(AppformReqDto appformReqDto) {
        return sqlSessionTemplate.delete("AppformMapper.deleteMcpRequestSaleinfo",appformReqDto);
    }

    @Override
    public int deleteMcpRequestCommend(AppformReqDto appformReqDto) {
        return sqlSessionTemplate.delete("AppformMapper.deleteMcpRequestCommend",appformReqDto);
    }

    @Override
    public List<AppformReqDto> getInsrCode() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(apiInterfaceServer + "/appform/insrCodeList", null, List.class); //Appform_Query.selectInsrCodeList
    }

    @Override
    public List<IntmInsrRelDTO> getInsrProdList(IntmInsrRelDTO intmInsrRelDTO) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(apiInterfaceServer + "/appform/insrProdList", intmInsrRelDTO, List.class); // Appform_Query.getInsrProdList
    }

    @Override
    public Long getRequestSelfDlvrKey() {
        return  sqlSessionTemplate.selectOne("AppformMapper.getRequestSelfDlvrKey");
    }

    @Override
    public boolean insertMcpRequestSelfDlvryHist(McpRequestSelfDlvryDto reqSelfDlvry) {
        return     0 < sqlSessionTemplate.insert("AppformMapper.insertMcpRequestSelfDlvryHist",reqSelfDlvry);
    }

    @Override
    public boolean insertRequestSelfDlvry(McpRequestSelfDlvryDto reqSelfDlvry) {
        return     0 < sqlSessionTemplate.insert("AppformMapper.insertRequestSelfDlvry",reqSelfDlvry);
    }

    @Override
    public List<McpRequestSelfDlvryDto> getMcpRequestSelfDlvry(McpRequestSelfDlvryDto reqSelfDlvry) {
        return sqlSessionTemplate.selectList("AppformMapper.getMcpRequestSelfDlvry", reqSelfDlvry);
    }

    @Override
    public McpRequestSelfDlvryDto getMcpSelfDlvryDataHist(Long selfDlvryIdx) {
        return sqlSessionTemplate.selectOne("Appform_Query.getMcpSelfDlvryDataHist", selfDlvryIdx);
    }

    @Override
    public McpRequestSelfDlvryDto getMcpSelfDlvryData(Long selfDlvryIdx) {
        return sqlSessionTemplate.selectOne("AppformMapper.getMcpSelfDlvryData", selfDlvryIdx);
    }

    @Override
    public boolean insertMcpRequestSelfDlvry(McpRequestSelfDlvryDto reqSelfDlvry) {
        return 0 < sqlSessionTemplate.update("AppformMapper.insertMcpRequestSelfDlvry",reqSelfDlvry);
    }

    @Override
    public int deleteMcpRequestSelfDlvry(Long selfDlvryIdx) {
        return sqlSessionTemplate.delete("Appform_Query.deleteMcpRequestSelfDlvry",selfDlvryIdx);
    }

    @Override
    public boolean insertMcpRequestCommend(AppformReqDto appformReqDto) {
        sqlSessionTemplate.update("AppformMapper.updateRecommedFlag",appformReqDto);
        return 0 < sqlSessionTemplate.update("AppformMapper.insertMcpRequestCommend",appformReqDto);
    }

    @Override
    public AppformReqDto getLimitForm(AppformReqDto appformReqDto) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(apiInterfaceServer + "/appform/limitForm", appformReqDto, AppformReqDto.class);
    }

    @Override
    public int checkLimitFormCount(AppformReqDto appformReqDto) {
        Object resultObj = sqlSessionTemplate.selectOne("AppformMapper.checkLimitFormCount",appformReqDto);
        if(resultObj instanceof Number){
            Number number = (Number) resultObj;
            return number.intValue();
        }else{
            throw new IllegalArgumentException(String.format("Wrong  resultClass type(%s) with queryId:%s, resultClass must be subclass of java.lang.Number", resultObj.getClass().getName(), "AppFormMapper.checkLimitFormCount"));
        }
    }

    @Override
    public McpRequestSelfDlvryDto getMcpNowDlvryData(Long selfDlvryIdx) {
        return sqlSessionTemplate.selectOne("AppformMapper.getMcpNowDlvryData", selfDlvryIdx);
    }

    @Override
    public boolean insertMcpRequestNowDlvryHist(McpRequestSelfDlvryDto reqSelfDlvry) {
        return 0 < sqlSessionTemplate.update("AppformMapper.insertMcpRequestNowDlvryHist",reqSelfDlvry);
    }

    @Override
    public boolean updateMcpRequestNowDlvryHist(McpRequestSelfDlvryDto reqSelfDlvry) {
        return     0 < sqlSessionTemplate.update("AppformMapper.updateMcpRequestNowDlvryHist",reqSelfDlvry);
    }


    @Override
    public int deleteMcpRequestNowDlvry(Long selfDlvryIdx) {
        return sqlSessionTemplate.delete("Appform_Query.deleteMcpRequestNowDlvry",selfDlvryIdx);
    }

    @Override
    public boolean insertMcpRequestNowDlvry(McpRequestSelfDlvryDto reqSelfDlvry) {
        return 0 < sqlSessionTemplate.update("AppformMapper.insertMcpRequestNowDlvry",reqSelfDlvry);
    }

    @Override
    public McpRequestSelfDlvryDto getMcpNowDlvryDataHist(Long selfDlvryIdx) {
        return sqlSessionTemplate.selectOne("AppformMapper.getMcpNowDlvryDataHist", selfDlvryIdx);
    }

    @Override
    public boolean updateNowDlvry(McpRequestSelfDlvryDto mcpRequestSelfDlvryDto){
        return 0 < sqlSessionTemplate.update("AppformMapper.updateNowDlvry",mcpRequestSelfDlvryDto);
    }

    @Override
    public boolean updatePayCdNowDlvry(McpRequestSelfDlvryDto mcpRequestSelfDlvryDto){
        return 0 < sqlSessionTemplate.update("Appform_Query.updatePayCdNowDlvry",mcpRequestSelfDlvryDto);
    }

    @Override
    public int updateSelfViewYn(Long selfDlvryIdx){
        return sqlSessionTemplate.update("AppformMapper.updateSelfViewYn",selfDlvryIdx);
    }

    @Override
    public int updateNowViewYn(Long selfDlvryIdx){
        return sqlSessionTemplate.update("AppformMapper.updateNowViewYn",selfDlvryIdx);
    }


    @Override
    public List<AppformReqDto> getFormDlveyList(AppformReqDto appformReqDto) {
        return sqlSessionTemplate.selectList("AppformMapper.getFormDlveyList", appformReqDto);
    }

    @Override
    public boolean updateFormDlveyUsim(AppformReqDto appformReqDto){
        return 0 < sqlSessionTemplate.update("AppformMapper.updateFormDlveyUsim",appformReqDto);
    }

    @Override
    public long getTempRequestKey() {
        return sqlSessionTemplate.selectOne("AppformMapper.getTempRequestKey");
    }

    @Override
    public int insertAppFormTempSave(AppformReqDto appformReqDto) {
        return sqlSessionTemplate.insert("AppformMapper.insertAppFormTempSave",appformReqDto);
    }


    @Override
    public int insertSaleinfoTempSave(AppformReqDto appformReqDto) {
        return sqlSessionTemplate.insert("AppformMapper.insertSaleinfoTempSave",appformReqDto);
    }

    @Override
    public int insertAppFormApdTempSave(AppformReqDto appformReqDto) {
        return sqlSessionTemplate.insert("AppformMapper.insertAppFormApdTempSave",appformReqDto);
    }

    @Override
    public int insertSaleinfoApdTempSave(AppformReqDto appformReqDto) {
        return sqlSessionTemplate.insert("AppformMapper.insertSaleinfoApdTempSave",appformReqDto);
    }

    @Override
    public AppformReqDto getAppFormTemp(long requestKey) {
        return sqlSessionTemplate.selectOne("AppformMapper.getAppFormTemp", requestKey);
    }

    @Override
    public AppformReqDto getAppForm(AppformReqDto appformReqDto) {
        return sqlSessionTemplate.selectOne("AppformMapper.getAppForm", appformReqDto);
    }

    @Override
   public boolean updateRequestTemp(AppformReqDto appformReqDto) {
       return 0 < sqlSessionTemplate.update("AppformMapper.updateRequestTemp",appformReqDto);
   }

    @Override
   public boolean updateRequestCstmrTemp(AppformReqDto appformReqDto) {
       return 0 < sqlSessionTemplate.update("AppformMapper.updateRequestCstmrTemp",appformReqDto);
   }

   @Override
   public boolean updateRequestAgentTemp(AppformReqDto appformReqDto)  {
       return 0 < sqlSessionTemplate.update("AppformMapper.updateRequestAgentTemp",appformReqDto);
   }

   @Override
   public boolean updateRequestDlvryTemp(AppformReqDto appformReqDto) {
       return 0 < sqlSessionTemplate.update("AppformMapper.updateRequestDlvryTemp",appformReqDto);
   }

   @Override
   public boolean updateRequestMoveTemp(AppformReqDto appformReqDto) {
       return 0 < sqlSessionTemplate.update("AppformMapper.updateRequestMoveTemp",appformReqDto);
   }


   @Override
   public boolean insertMcpRequestAdditionTemp(AppformReqDto appformReqDto) {
       return 0 < sqlSessionTemplate.update("AppformMapper.insertMcpRequestAdditionTemp",appformReqDto);
   }

   @Override
   public boolean deleteMcpRequestAdditionTemp(AppformReqDto appformReqDto) {
       return 0 < sqlSessionTemplate.delete("AppformMapper.deleteMcpRequestAdditionTemp",appformReqDto);
   }

   @Override
   public List<String> getAdditionTempList(AppformReqDto appformReqDto) {
       return sqlSessionTemplate.selectList("AppformMapper.getAdditionTempList",appformReqDto);
   }

   @Override
   public boolean updateRequestReqTemp(AppformReqDto appformReqDto) {
       return 0 < sqlSessionTemplate.delete("AppformMapper.updateRequestReqTemp",appformReqDto);
   }

   @Override
   public UsimBasDto getUsimBasInfo(UsimBasDto usimBasObj) {
       return sqlSessionTemplate.selectOne("AppformMapper.getUsimBasInfo", usimBasObj) ;
   }

//   @Override
//   public boolean insertGiftReqTxn(GiftPromotionDtl giftPromotionDtl) {
//       return 0 < sqlSessionTemplate.insert("AppformMapper.insertGiftReqTxn",giftPromotionDtl);
//   }
//
//
//   @Override
//   public int checkGiftReqCount(GiftPromotionDtl giftPromotionDtl) {
//       Object resultObj = sqlSessionTemplate.selectOne("AppformMapper.checkGiftReqCount",giftPromotionDtl);
//       if(resultObj instanceof Number){
//           Number number = (Number) resultObj;
//           return number.intValue();
//       }else{
//           throw new IllegalArgumentException(String.format("Wrong  resultClass type(%s) with queryId:%s, resultClass must be subclass of java.lang.Number", resultObj.getClass().getName(), "AppFormMapper.checkGiftReqCount"));
//       }
//   }

   @Override
   public boolean insertNmcpRequestApd(McpRequestDto appformReq) {
       return     0 < sqlSessionTemplate.insert("AppformMapper.insertNmcpRequestApd",appformReq);
   }

   @Override
   public boolean insertNmcpRequestApdDlvry(AppformReqDto appformReq){
       return     0 < sqlSessionTemplate.insert("AppformMapper.insertNmcpRequestApdDlvry",appformReq);
   }

   @Override
   public boolean insertNmcpRequestApdSaleinfo(AppformReqDto appformReq) {
       return     0 < sqlSessionTemplate.insert("AppformMapper.insertNmcpRequestApdSaleinfo",appformReq);
   }

   @Override
   public boolean insertNmcpRequestApdState(AppformReqDto appformReq) {
       return     0 < sqlSessionTemplate.insert("AppformMapper.insertNmcpRequestApdState",appformReq);
   }

   @Override
   public boolean updateNmcpRequestApd(AppformReqDto appformReq)  {
       return     0 < sqlSessionTemplate.update("AppformMapper.updateNmcpRequestApd",appformReq);
   }

   @Override
   public boolean updateNmcpRequestApdState(AppformReqDto appformReq) {
       return     0 < sqlSessionTemplate.update("AppformMapper.updateNmcpRequestApdState",appformReq);
   }

   @Override
   public AppformReqDto getNmcpRequestApdSaleinfo(Long selfDlvryIdx) {
       return sqlSessionTemplate.selectOne("AppformMapper.getNmcpRequestApdSaleinfo", selfDlvryIdx);
   }

   @Override
   public boolean updateMcpRequestState(AppformReqDto appformReq) {
       return     0 < sqlSessionTemplate.update("AppformMapper.updateMcpRequestState",appformReq);
   }

    @Override
    public int insertNmcpUsimBuyTxn(AppformReqDto appformReq) {
        return sqlSessionTemplate.insert("AppformMapper.insertNmcpUsimBuyTxn",appformReq);
    }

    @Override
    public int insertMcpSelfUsimChg(OsstUc0ReqDto osstUc0ReqDto) {
        return sqlSessionTemplate.insert("AppformMapper.insertMcpSelfUsimChg",osstUc0ReqDto);
    }


    @Override
    public int checkUploadPhoneInfoCount(long uploadPhoneSrlNo) {
        Object resultObj = sqlSessionTemplate.selectOne("AppformMapper.checkUploadPhoneInfoCount",uploadPhoneSrlNo);
        if(resultObj instanceof Number){
            Number number = (Number) resultObj;
            return number.intValue();
        }else{
            throw new IllegalArgumentException(String.format("Wrong  resultClass type(%s) with queryId:%s, resultClass must be subclass of java.lang.Number", resultObj.getClass().getName(), "AppFormMapper.checkUploadPhoneInfoCount"));
        }
    }


    @Override
    public McpUploadPhoneInfoDto getUploadPhoneInfo(long uploadPhoneSrlNo) {
        return sqlSessionTemplate.selectOne("AppformMapper.getUploadPhoneInfo", uploadPhoneSrlNo);
    }

    @Override
    public boolean insertMcpRequestKtInter(AppformReqDto appformReq) {
        return     0 < sqlSessionTemplate.insert("AppformMapper.insertMcpRequestKtInter",appformReq);
    }

    @Override
    public int insertAcenReqTrg(AcenDto acenDto) {
        return sqlSessionTemplate.insert("AppformMapper.insertAcenReqTrg", acenDto);
    }

    @Override
    public boolean insertMcpRequestPayInfo(AppformReqDto appformReq) {
        return     0 < sqlSessionTemplate.insert("AppformMapper.insertMcpRequestPayInfo",appformReq);
    }

    public boolean updateMcpRequestPayInfo(McpRequestPayInfoDto mcpRequestPayInfo) {
        return     0 < sqlSessionTemplate.update("AppformMapper.updateMcpRequestPayInfo",mcpRequestPayInfo);
    }

    @Override
    public int getNacSelfCount(Map<String, String> param) {
        return sqlSessionTemplate.selectOne("AppformMapper.getNacSelfCount", param);
    }

    @Override
    public List<String> getResNoByMoveMobileNum(Map<String, Object> paramMap) {
        return sqlSessionTemplate.selectList("AppformMapper.getResNoByMoveMobileNum", paramMap);
    }

    @Override
    public int getPreCheckTryCnt(Map<String, Object> paramMap) {
        return sqlSessionTemplate.selectOne("AppformMapper.getPreCheckTryCnt", paramMap);
    }

    @Override
    public int chkDupByReqUsimSn(Map<String, String> paramMap) {
        return sqlSessionTemplate.selectOne("AppformMapper.chkDupByReqUsimSn", paramMap);
    }

    @Override
    public int chkDupByMovePhoneNum(Map<String, String> paramMap) {
        return sqlSessionTemplate.selectOne("AppformMapper.chkDupByMovePhoneNum", paramMap);
    }

    @Override
    public int chkMcpReqDtl(long requestKey) {
        return sqlSessionTemplate.selectOne("AppformMapper.chkMcpReqDtl", requestKey);
    }

    @Override
    public AppformReqDto getMcpReqDtl(long requestKey) {
        return sqlSessionTemplate.selectOne("AppformMapper.getMcpReqDtl", requestKey);
    }

    @Override
    public int updateMcpReqDtl(AppformReqDto appformReq) {
        return sqlSessionTemplate.update("AppformMapper.updateMcpReqDtl", appformReq);
    }

    @Override
    public int insertMcpReqDtl(AppformReqDto appformReq) {
        return sqlSessionTemplate.insert("AppformMapper.insertMcpReqDtl", appformReq);
    }

    @Override
    public AppformReqDto getJehuUsimlessByResNo(String resNo) {
        return sqlSessionTemplate.selectOne("AppformMapper.getJehuUsimlessByResNo", resNo);
    }

    @Override
    public String selectUsimChgResult(String mvnoOrdNo) {
        return sqlSessionTemplate.selectOne("AppformMapper.selectUsimChgResult", mvnoOrdNo);
    }

    @Override
    public int updateMcpSelfUsimChgUC0(OsstUc0ReqDto osstUc0ReqDto) {
        return sqlSessionTemplate.update("AppformMapper.updateMcpSelfUsimChgUC0", osstUc0ReqDto);
    }

    @Override
    public int insertKtCounsel(AppformReqDto appformReqDto) {
        return sqlSessionTemplate.insert("AppformMapper.insertKtCounsel", appformReqDto);
    }
}
