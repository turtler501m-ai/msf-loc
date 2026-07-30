package com.ktmmobile.msf.domains.form.common.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.ktmmobile.msf.commons.mybatis.config.MspMyBatisConfig;
import com.ktmmobile.msf.domains.form.common.dto.AcesAlwdDto;
import com.ktmmobile.msf.domains.form.common.dto.BannerDto;
import com.ktmmobile.msf.domains.form.common.dto.BannerFloatDto;
import com.ktmmobile.msf.domains.form.common.dto.BannerTextDto;
import com.ktmmobile.msf.domains.form.common.dto.CdGroupBean;
import com.ktmmobile.msf.domains.form.common.dto.McpIpStatisticDto;
import com.ktmmobile.msf.domains.form.common.dto.MspCommDatPrvTxnDto;
import com.ktmmobile.msf.domains.form.common.dto.MspRateMstDto;
import com.ktmmobile.msf.domains.form.common.dto.NmcpCdDtlDto;
import com.ktmmobile.msf.domains.form.common.dto.SiteMenuDto;
import com.ktmmobile.msf.domains.form.common.dto.WorkNotiDto;

/**
 * @Class Name : CommonDaoImpl
 * @Description : 공용 Dao 구현클래스
 *
 * @author :
 * @Create Date :
 */
@Repository
public class FCommonDaoImpl implements FCommonDao {

    private final SqlSessionTemplate sqlSessionTemplate;
    private final SqlSessionTemplate mspSqlSession;

    @Value("${api.interface.server:}")
    private String apiInterfaceServer;

    public FCommonDaoImpl(
        SqlSessionTemplate sqlSessionTemplate,
        @Qualifier(MspMyBatisConfig.SQL_SESSION_TEMPLATE) SqlSessionTemplate mspSqlSession
    ) {
        this.sqlSessionTemplate = sqlSessionTemplate;
        this.mspSqlSession = mspSqlSession;
    }

    /* (non-Javadoc)
     * @see com.ktmmobile.msf.domains.form.common.dao.CommonDao#insertIp(com.ktmmobile.msf.domains.form.common.dto.McpIpStatisticDto)
     */
    @Override
    public int insertIpStat(McpIpStatisticDto mcpIpStatisticDto) {
        return mspSqlSession.insert("CommonMapper.insertIpStat", mcpIpStatisticDto);
    }

    @Override
    public List<CdGroupBean> getCodeAllList() {
        return sqlSessionTemplate.selectList("CommCodeMapper.getCodeAllList");
    }

    @Override
    public List<BannerDto> getBannerAllList() {
        return sqlSessionTemplate.selectList("BannerMapper.getBannerAllList");
    }

    @Override
    public List<BannerDto> getBannerApdList() {
        return sqlSessionTemplate.selectList("BannerMapper.getBannerApdList");
    }

    @Override
    public List<SiteMenuDto> getMenuAllList() {
        return sqlSessionTemplate.selectList("SiteMenuMapper.getMenuAllList");
    }

    @Override
    public List<SiteMenuDto> getMenuAuthList() {
        return sqlSessionTemplate.selectList("SiteMenuMapper.getMenuAuthList");
    }

    @Override
    public List<WorkNotiDto> getMenuUrlAllList() {
        return sqlSessionTemplate.selectList("SiteMenuMapper.getMenuUrlAllList");
    }

    @Override
    public List<AcesAlwdDto> getAcesAlwdList() {
        return sqlSessionTemplate.selectList("SiteMenuMapper.getAcesAlwdList");
    }

    @Override
    public List<BannerTextDto> getBannerTextList() {
        return sqlSessionTemplate.selectList("BannerMapper.getBannerTextList");
    }

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

    @Override
    public boolean insertmspCommDatPrvTxn(MspCommDatPrvTxnDto mspCommDatPrvTxnDto) {
        RestTemplate restTemplate = new RestTemplate();
        return 0 < restTemplate.postForObject(apiInterfaceServer + "/common/mspCommDatPrvTxn",
            mspCommDatPrvTxnDto,
            Integer.class); // CommonMapper.insertmspCommDatPrvTxn
    }

    @Override
    public int insertIpStatAdmin(McpIpStatisticDto mcpIpStatisticDto) {
        return sqlSessionTemplate.insert("CommonMapper.insertIpStatAdmin", mcpIpStatisticDto);
    }

    @Override
    public int deleteRateResChgAccessTrace(String rateResChgSeq) {
        return mspSqlSession.delete("CommonMapper.deleteRateResChgAccessTrace", rateResChgSeq);
    }

    @Override
    public String selectRateResChgAccessTrace(McpIpStatisticDto mcpIpStatisticDto) {
        return mspSqlSession.selectOne("CommonMapper.selectRateResChgAccessTrace", mcpIpStatisticDto);
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
}
