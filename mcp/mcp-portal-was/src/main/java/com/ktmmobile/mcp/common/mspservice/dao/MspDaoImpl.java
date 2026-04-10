package com.ktmmobile.mcp.common.mspservice.dao;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.ktmmobile.mcp.common.mspservice.dto.CmnGrpCdMst;
import com.ktmmobile.mcp.common.mspservice.dto.MspNoticSupportMstDto;
import com.ktmmobile.mcp.common.mspservice.dto.MspRateMstDto;
import com.ktmmobile.mcp.common.mspservice.dto.MspSaleAgrmMst;
import com.ktmmobile.mcp.common.mspservice.dto.MspSalePlcyMstDto;
import com.ktmmobile.mcp.common.mspservice.dto.MspSalePrdtMstDto;
import com.ktmmobile.mcp.common.mspservice.dto.MspSaleSubsdMstDto;
import com.ktmmobile.mcp.phone.dto.PhoneMspDto;


/**
 * @Class Name : MspDaoImpl
 * @Description : Msp 영역 조회 Dao 구현체
 *
 * @author : ant
 * @Create Date : 2016. 1. 12.
 */
@Repository
public class MspDaoImpl implements MspDao {

    private static final Logger logger = LoggerFactory.getLogger(MspDaoImpl.class);

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    /* (non-Javadoc)
     * @see com.ktmmobile.mcp.phone.dao.PhoneDao#findMspSaleOrgnMst(com.ktmmobile.mcp.phone.dto.msp.MspSaleOrgnMstDto)
     */
    @Override
    public List<MspSalePlcyMstDto> findMspSalePlcyMst(
            MspSalePlcyMstDto mspSalePlcyMstDto) {

    	RestTemplate restTemplate = new RestTemplate();
    	MspSalePlcyMstDto[] resultList = restTemplate.postForObject(apiInterfaceServer + "/msp/mspSaleOrgnMst", mspSalePlcyMstDto, MspSalePlcyMstDto[].class); // Msp_Query.findMspSaleOrgnMst
		List<MspSalePlcyMstDto> list = Arrays.asList(resultList);
		return list;

    }

    /* (non-Javadoc)
     * @see com.ktmmobile.mcp.phone.dao.PhoneDao#findMspSalePrdMst(com.ktmmobile.mcp.phone.dto.msp.MspSalePrdtMstDto)
     */
    @Override
    public MspSalePrdtMstDto findMspSalePrdMst(
            MspSalePrdtMstDto mspSalePrdtMstDto) {
        RestTemplate restTemplate = new RestTemplate();
		return restTemplate.postForObject(apiInterfaceServer + "/msp/mspSalePrdMst", mspSalePrdtMstDto, MspSalePrdtMstDto.class); // Msp_Query.findMspSalePrdMst
    }


    /* (non-Javadoc)
     * @see com.ktmmobile.mcp.mspservice.dao.MspDao#listMspSaleAgrmMst(java.lang.String)
     */
    @Override
    public List<MspSaleAgrmMst> listMspSaleAgrmMst(String salePlcyCd) {
    	RestTemplate restTemplate = new RestTemplate();
    	MspSaleAgrmMst[] resultList = restTemplate.postForObject(apiInterfaceServer + "/msp/mspSaleAgrmMst", salePlcyCd, MspSaleAgrmMst[].class); // Msp_Query.listMspSaleAgrmMst
		List<MspSaleAgrmMst> list = Arrays.asList(resultList);
		return list;
    }


    @Override
    public MspSaleSubsdMstDto getMspSaleSubsdMst( MspSaleSubsdMstDto mspSaleSubsdMstDto) {

		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.postForObject(apiInterfaceServer + "/msp/lowPriceChargeInfoByProd", mspSaleSubsdMstDto, MspSaleSubsdMstDto.class); // Msp_Query.getLowPriceChargeInfoByProd

    }


    /* (non-Javadoc)
     * @see com.ktmmobile.mcp.mspservice.dao.MspDao#findCmnGrpCdMst(com.ktmmobile.mcp.mspservice.dto.CmnGrpCdMst)
     */
    @Override
    public CmnGrpCdMst findCmnGrpCdMst(CmnGrpCdMst cmnGrpCdMst) {

		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.postForObject(apiInterfaceServer + "/msp/cmnGrpCdMst", cmnGrpCdMst, CmnGrpCdMst.class); // Msp_Query.findCmnGrpCdMst

    }

    /* (non-Javadoc)
     * @see com.ktmmobile.mcp.mspservice.dao.MspDao#listRateByOrgnInfos(com.ktmmobile.mcp.mspservice.dto.MspSalePlcyMstDto)
     */
    @Override
    public List<MspRateMstDto> listRateByOrgnInfos(
            MspSalePlcyMstDto mspSalePlcyMstDto) {

    	RestTemplate restTemplate = new RestTemplate();
		return restTemplate.postForObject(apiInterfaceServer + "/msp/rateByOrgnInfos", mspSalePlcyMstDto, List.class); // Msp_Query.listRateByOrgnInfos

    }

    /* (non-Javadoc)
     * @see com.ktmmobile.mcp.mspservice.dao.MspDao#findMspSalePlcyInfoByOnlyOrgn(com.ktmmobile.mcp.mspservice.dto.MspSalePlcyMstDto)
     */
    @Override
    public List<MspSalePlcyMstDto> listMspSalePlcyInfoByOnlyOrgn(
            MspSalePlcyMstDto mspSalePlcyMstDto) {
    	RestTemplate restTemplate = new RestTemplate();
    	MspSalePlcyMstDto[] resultList = restTemplate.postForObject(apiInterfaceServer + "/msp/mspSalePlcyInfoByOnlyOrgn", mspSalePlcyMstDto, MspSalePlcyMstDto[].class); // Msp_Query.findMspSalePlcyInfoByOnlyOrgn
		List<MspSalePlcyMstDto> list = Arrays.asList(resultList);
		return list;
    }

    @Override
    public PhoneMspDto findMspPhoneInfo(String prdtId) {
    	RestTemplate restTemplate = new RestTemplate();
		return restTemplate.postForObject(apiInterfaceServer + "/msp/mspPhoneInfo", prdtId, PhoneMspDto.class); // Msp_Query.findMspPhoneInfo
    }

    @Override
    public List<MspNoticSupportMstDto> listMspOfficialSupportRateNm() {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.postForObject(apiInterfaceServer + "/msp/mspOfficialSupportRateNm", null, List.class); // Msp_Query.listMspOfficialSupportRateNm
    }

    @Override
    public List<MspNoticSupportMstDto> listMspOfficialNoticeSupport(MspNoticSupportMstDto mspNoticSupportMstDto, int skipResult, int maxResult) {
    	mspNoticSupportMstDto.setApiParam1(skipResult);
    	mspNoticSupportMstDto.setApiParam2(maxResult);
        RestTemplate restTemplate = new RestTemplate();
		MspNoticSupportMstDto[] resultList = restTemplate.postForObject(apiInterfaceServer + "/msp/mspOfficialNoticeSupport", mspNoticSupportMstDto, MspNoticSupportMstDto[].class); // Msp_Query.listMspOfficialNoticeSupport
		List<MspNoticSupportMstDto> list = Arrays.asList(resultList);
		return list;

    }

    @Override
    public int listMspOfficialNoticeSupportCount(MspNoticSupportMstDto mspNoticSupportMstDto) {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.postForObject(apiInterfaceServer + "/msp/mspOfficialNoticeSupportCount", mspNoticSupportMstDto, int.class); // Msp_Query.listMspOfficialNoticeSupportCount
    }

    @Override
    public String getCustomerSsn(String contractNum) {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.postForObject(apiInterfaceServer + "/msp/customerSsn", contractNum, String.class); // Msp_Query.getCustomerSsn
    }

    @Override
    public MspRateMstDto getMspRateMst(String rateCd) {
        RestTemplate restTemplate = new RestTemplate();
		return restTemplate.postForObject(apiInterfaceServer + "/msp/mspRateMst", rateCd, MspRateMstDto.class); // Msp_Query.getMspRateMst
    }

    @Override
    public  List<MspSaleSubsdMstDto> listMspSaleMst(MspSaleSubsdMstDto mspSaleSubsdMstDto) {
        RestTemplate restTemplate = new RestTemplate();
        MspSaleSubsdMstDto[] resultList = restTemplate.postForObject(apiInterfaceServer + "/msp/mspSaleMst", mspSaleSubsdMstDto, MspSaleSubsdMstDto[].class); // Msp_Query.listMspSaleMst
    	List<MspSaleSubsdMstDto> retList = Arrays.asList(resultList);

		return retList;
    }

    @Override
    public List<MspSaleAgrmMst> findMspSaleMnth(String salePlcyCd) {

        RestTemplate restTemplate = new RestTemplate();
        MspSaleAgrmMst[] resultList = restTemplate.postForObject(apiInterfaceServer + "/msp/mspSaleAgrmMstSing", salePlcyCd, MspSaleAgrmMst[].class); // Msp_Query.selectMspSaleAgrmMst
        List<MspSaleAgrmMst> list = Arrays.asList(resultList);
        return list;
    }

    @Override
    public String getMspSubStatus(String contractNum) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(apiInterfaceServer + "/msp/getMspJuoSubStatus", contractNum, String.class);
    }
}
