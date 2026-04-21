package com.ktmmobile.msf.domains.form.common.dao;

import java.util.List;
import java.util.Map;

import com.ktmmobile.msf.domains.form.common.dto.*;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.ktmmobile.msf.domains.form.common.dto.MspCommDatPrvTxnDto;
import com.ktmmobile.msf.domains.form.common.dto.MspSmsTemplateMstDto;
import com.ktmmobile.msf.domains.form.common.dto.NmcpCdDtlDto;

import com.ktmmobile.msf.domains.form.common.dto.MspRateMstDto;

/**
 * @Class Name : CommonDaoImpl
 * @Description : 공용 Dao 구현클래스
 *
 * @author :
 * @Create Date :
 */
@Repository
@RequiredArgsConstructor
public class FCommonDaoImpl implements FCommonDao {

    private final SqlSessionTemplate sqlSessionTemplate;

    @Qualifier("mspSqlSession")
    private final SqlSessionTemplate mspSqlSession; //postgresql

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    /* (non-Javadoc)
     * @see com.ktmmobile.msf.domains.form.common.dao.CommonDao#insertIp(com.ktmmobile.msf.domains.form.common.dto.McpIpStatisticDto)
     */
    @Override
    public int insertIpStat(McpIpStatisticDto mcpIpStatisticDto) {
        return sqlSessionTemplate.insert("CommonMapper.insertIpStat", mcpIpStatisticDto);
    }

    @Override
    public List<CdGroupBean> getCodeAllList() {
        return sqlSessionTemplate.selectList("CommCodeMapper.getCodeAllList");
    }

    @Override
    public List<BannerDto> getBannerAllList(){
        return sqlSessionTemplate.selectList("BannerMapper.getBannerAllList");
    };

    @Override
    public List<BannerDto> getBannerApdList(){
        return sqlSessionTemplate.selectList("BannerMapper.getBannerApdList");
    };

    @Override
    public List<PopupDto> getPopupAllList(){
        return sqlSessionTemplate.selectList("CommonMapper.getPopupAllList");
    };

    @Override
    public List<SiteMenuDto> getMenuAllList(){
        return sqlSessionTemplate.selectList("SiteMenuMapper.getMenuAllList");
    };

    @Override
    public List<SiteMenuDto> getMenuAuthList(){
        return sqlSessionTemplate.selectList("SiteMenuMapper.getMenuAuthList");
    };

    @Override
    public List<WorkNotiDto> getMenuUrlAllList(){
        return sqlSessionTemplate.selectList("SiteMenuMapper.getMenuUrlAllList");
    };

    @Override
    public List<AcesAlwdDto> getAcesAlwdList(){
        return sqlSessionTemplate.selectList("SiteMenuMapper.getAcesAlwdList");
    }

    @Override
    public List<BannerTextDto> getBannerTextList() {
        return sqlSessionTemplate.selectList("BannerMapper.getBannerTextList");
    }

    ;

    @Override
    public List<BannerFloatDto> getBannerFloatList() {
        return sqlSessionTemplate.selectList("BannerMapper.getBannerFloatList");
    }



    @Override
    public List<NmcpCdDtlDto> getCodeList(NmcpCdDtlDto nmcpCdDtlDto) {
        return sqlSessionTemplate.selectList("CommCodeMapper.getCodeList", nmcpCdDtlDto);
    }

    @Override
    public NmcpCdDtlDto getCodeNm(NmcpCdDtlDto nmcpCdDtlDto) {
        return sqlSessionTemplate.selectOne("CommCodeMapper.getCodeNm", nmcpCdDtlDto);
    }

    @Override
    public MspRateMstDto getMspRateMst(String rateCd) {
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("rateCd", rateCd);
        return restTemplate.postForObject(apiInterfaceServer + "/common/mspRateMst", params, MspRateMstDto.class); // CommonMapper.getMspRateMst
    }


    /**
     * <pre>
     * 설명     : 팝업 리스트 조회
     * @param PopupDto
     * @return
     * @return: List<PopupDto>
     * </pre>
     */
    @Override
    public List<PopupDto> getPopupList(PopupDto popupDto) {
        return sqlSessionTemplate.selectList("CommonMapper.getPopupList", popupDto);
    }

    /**
     * <pre>
     * 설명     : 팝업 상세 조회
     * @param popupSeq
     * @return
     * @return: PopupDto
     * </pre>
     */
    @Override
    public PopupDto getPopupDetail(PopupDto popupDto) {
        return sqlSessionTemplate.selectOne("CommonMapper.getPopupDetail", popupDto);
    }

    /**
     * <pre>
     * 설명     : 팝업 리스트 조회 메인PC
     * @param
     * @return
     * @return: List<PopupDto>
     * </pre>
     */
    @Override
    public List<PopupDto> getPopupMainList(String menuCode) {
        // TODO Auto-generated method stub
        return sqlSessionTemplate.selectList("CommonMapper.getPopupMainList",menuCode);
    }

    @Override
    public boolean insertmspCommDatPrvTxn(MspCommDatPrvTxnDto mspCommDatPrvTxnDto) {
        RestTemplate restTemplate = new RestTemplate();
        return 0 < restTemplate.postForObject(apiInterfaceServer + "/common/mspCommDatPrvTxn", mspCommDatPrvTxnDto, Integer.class); // CommonMapper.insertmspCommDatPrvTxn
    }

    /**
    * @Description : NMCP Login 정보 저장 테이블에 저장한다.
    * @param loginHistoryDto
    * @return
    * @Author :
    * @Create Date :
    */
    public int insertLoginHistory(LoginHistoryDto loginHistoryDto) {
        return sqlSessionTemplate.insert("CommonMapper.insertLoginHistory", loginHistoryDto);
    }

    @Override
    public MspSmsTemplateMstDto getMspSmsTemplateMst(int templateId){
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, Integer> params = new LinkedMultiValueMap<String, Integer>();
        params.add("templateId", templateId);
        return restTemplate.postForObject(apiInterfaceServer + "/common/mspSmsTemplateMst", templateId, MspSmsTemplateMstDto.class); // CommonMapper.getMspSmsTemplateMst
    }


    @Override
    public int checkCrawlingCount(McpIpStatisticDto mcpIpStatisticDto) {
        Object resultObj = sqlSessionTemplate.selectOne("CommonMapper.checkCrawlingCount",mcpIpStatisticDto);
        if(resultObj instanceof Number){
            Number number = (Number) resultObj;
            return number.intValue();
        }else{
            throw new IllegalArgumentException(String.format("Wrong  resultClass type(%s) with queryId:%s, resultClass must be subclass of java.lang.Number", resultObj.getClass().getName(), "CommonMapper.checkCrawlingCount"));
        }
    }

    @Override
    public int insertIpStatAdmin(McpIpStatisticDto mcpIpStatisticDto) {
        return sqlSessionTemplate.insert("CommonMapper.insertIpStatAdmin", mcpIpStatisticDto);
    }

    @Override
    public List<McpIpStatisticDto> getAdminAccessTrace(McpIpStatisticDto mcpIpStatisticDto) {
        return sqlSessionTemplate.selectList("CommonMapper.getAdminAccessTrace", mcpIpStatisticDto);
    }

    @Override
    public int insertRateResChgAccessTrace(McpIpStatisticDto mcpIpStatisticDto) {
        return sqlSessionTemplate.insert("CommonMapper.insertRateResChgAccessTrace", mcpIpStatisticDto);
    }


    @Override
    public int deleteRateResChgAccessTrace(String rateResChgSeq) {
        return sqlSessionTemplate.delete("CommonMapper.deleteRateResChgAccessTrace", rateResChgSeq);
    }

    @Override
    public String selectRateResChgAccessTrace(McpIpStatisticDto mcpIpStatisticDto) {
        return sqlSessionTemplate.selectOne("CommonMapper.selectRateResChgAccessTrace", mcpIpStatisticDto);
    }

    @Override
    public List<McpIpStatisticDto> getRateResChgList(McpIpStatisticDto ipStatistic)  {
        return sqlSessionTemplate.selectList("CommonMapper.getRateResChgList", ipStatistic);
    }

    @Override
    public boolean updateNmcpRateResChgBas(McpIpStatisticDto ipStatistic) {
        return  0 < sqlSessionTemplate.update("CommonMapper.updateNmcpRateResChgBas", ipStatistic);
    }

    @Override
    public int insertRecaptchaLog(Map<String, String> recaptchaLogMap) {
        return mspSqlSession.insert("EventBoardMapper.insertRecaptchaLog", recaptchaLogMap);
    }

    @Override
    public NmcpCdDtlDto getDtlCodeWithNm(NmcpCdDtlDto nmcpCdDtlDto) {
        return sqlSessionTemplate.selectOne("CommCodeMapper.getDtlCodeWithNm", nmcpCdDtlDto);
    }

    @Override
    public List<NmcpCdDtlDto> getAllDtlCdList(String cdGroupId) {
        return sqlSessionTemplate.selectList("CommCodeMapper.getAllDtlCdList", cdGroupId);
    }

    @Override
    public boolean insertUserEventTrace(UserEventTraceDto userEventTraceDto) {
        return 0 <  sqlSessionTemplate.insert("CommonMapper.insertUserEventTrace", userEventTraceDto);
    }


    @Override
    public boolean updateUserEventTrace(UserEventTraceDto userEventTraceDto) {
        return  0 < sqlSessionTemplate.update("CommonMapper.updateUserEventTrace", userEventTraceDto);
    }

    @Override
    public List<UserEventTraceDto> getUserEventTraceList(UserEventTraceDto userEventTraceDto) {
        return sqlSessionTemplate.selectList("CommonMapper.getUserEventTraceList", userEventTraceDto);
    }

    @Override
    public PopupEditorDto getPopupEditor(PopupEditorDto popupEditorDto) {
        return sqlSessionTemplate.selectOne( "CommonMapper.getPopupEditor", popupEditorDto);
    }

    @Override
    public void updatePageViewCount(McpIpStatisticDto mcpIpStatisticDto) {
        sqlSessionTemplate.update("CommonMapper.updatePageViewCount", mcpIpStatisticDto);
    }

    @Override
    public int selectPageViewsCount(String url) {
        return sqlSessionTemplate.selectOne("CommonMapper.selectPageViewsCount", url);
    }

    @Override
    public String getLastedRateAdsvcGdncVersion() {
        return sqlSessionTemplate.selectOne("CommonMapper.getLastedRateAdsvcGdncVersion");
    }
}
