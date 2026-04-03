package com.ktmmobile.msf.usim.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ktmmobile.msf.form.newchange.dto.FormDtlDTO;
import com.ktmmobile.msf.form.common.dto.AuthSmsDto;
import com.ktmmobile.msf.form.common.dto.db.McpRequestSelfDlvryDto;
import com.ktmmobile.msf.form.common.mspservice.dto.MspPlcyOperTypeDto;
import com.ktmmobile.msf.form.common.mspservice.dto.MspRateMstDto;
import com.ktmmobile.msf.form.common.mspservice.dto.MspSaleAgrmMst;
import com.ktmmobile.msf.form.common.mspservice.dto.MspSalePlcyMstDto;
import com.ktmmobile.msf.usim.dto.KtRcgDto;
import com.ktmmobile.msf.usim.dto.UsimBasDto;
import com.ktmmobile.msf.usim.dto.UsimMspPlcyDto;
import com.ktmmobile.msf.usim.dto.UsimMspRateDto;

/**
 * @Class Name : UsimDaoImpl
 * @Description : 유심 상품 Dao 구현클래스
 *
 * @author : ant
 * @Create Date : 2016. 01. 18.
 */
@Repository
public class UsimDaoImpl implements UsimDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Override
    public UsimBasDto selectUsimBasDto(UsimBasDto usimBasDto) {
        return sqlSessionTemplate.selectOne("Usim_Query.selectUsimBasDto", usimBasDto);
    }

    @Override
    public List<UsimMspPlcyDto> listUsimMspPlcyDto() {
        return sqlSessionTemplate.selectList("Usim_Query.listUsimMspPlcyDto");
    }

    @Override
    public KtRcgDto selectKtRcgSeq(KtRcgDto ktRcgDto) {
        sqlSessionTemplate.selectOne("StoreUsim_Query.selectKtRcgSeq", ktRcgDto);
        return ktRcgDto;
    }

    @Override
    public Map<String, Object> selectRcg(Map<String, Object> map) {
        sqlSessionTemplate.selectList("StoreUsim_Query.selectRcg", map);
        return map;
    }

    @Override
    public List<UsimBasDto> selectNmcpUsimBasShopList(UsimBasDto usimBasDto) {
        return sqlSessionTemplate.selectList("StoreUsim_Query.selectNmcpUsimBasShopList", usimBasDto);
    }

    @Override
    public List<UsimBasDto> selectPlcyOperTypeList(UsimBasDto usimBasDto) {
        return sqlSessionTemplate.selectList("StoreUsim_Query.selectPlcyOperTypeList", usimBasDto);
    }

    @Override
    public List<UsimMspRateDto> selectRateList(UsimBasDto usimBasDto) {
        return sqlSessionTemplate.selectList("StoreUsim_Query.selectRateList", usimBasDto);
    }

    @Override
    public List<UsimBasDto> selectModelList(UsimBasDto usimBasDto) {
        return sqlSessionTemplate.selectList("StoreUsim_Query.selectModelList", usimBasDto);
    }

    @Override
    public List<UsimMspRateDto> selectJoinUsimPriceNew(String gubun) {
        return sqlSessionTemplate.selectList("StoreUsim_Query.selectJoinUsimPriceNew", gubun);
    }

    @Override
    public UsimMspRateDto selectUsimDcamt(String rateCd) {
        return sqlSessionTemplate.selectOne("StoreUsim_Query.selectUsimDcamt", rateCd);
    }

    @Override
    public AuthSmsDto selectUserChargeInfo(AuthSmsDto authSmsDto) {
        return sqlSessionTemplate.selectOne("StoreUsim_Query.selectUserChargeInfo", authSmsDto);
    }

    @Override
    public List<UsimMspRateDto> selectAgreeDcAmt(UsimBasDto usimBasDto) {
        return sqlSessionTemplate.selectList("StoreUsim_Query.selectAgreeDcAmt", usimBasDto);
    }

    @Override
    public UsimBasDto findUsimBas(String dataType, String payClCd) {
        UsimBasDto usimBasDto = new UsimBasDto();
        usimBasDto.setPayClCd(payClCd);
        usimBasDto.setDataType(dataType);
        return sqlSessionTemplate.selectOne("Usim_Query.findUsimBas", usimBasDto);
    }

    @Override
    public List<UsimBasDto> selectUsimPrdtList(UsimBasDto usimBasDto) {
        return sqlSessionTemplate.selectList("Usim_Query.selectUsimPrdtList", usimBasDto);
    }

    @Override
    public List<UsimBasDto> selectUsimsalePlcyCdToPrdtList(UsimBasDto usimBasDto) {
        return sqlSessionTemplate.selectList("Usim_Query.selectUsimsalePlcyCdToPrdtList", usimBasDto);
    }

    @Override
    public List<UsimBasDto> selectUsimNewRateList(UsimBasDto usimBasDto) {
        return sqlSessionTemplate.selectList("Usim_Query.selectUsimNewRateList", usimBasDto);
    }

    @Override
    public List<MspSaleAgrmMst> listMspSaleAgrmMst(String salePlcyCd)  {
        return sqlSessionTemplate.selectList("Usim_Query.selectUsimagrmTrmList", salePlcyCd);
    }

    @Override
    public List<UsimBasDto> selectUsimBasList(UsimBasDto usimBasDto) {
        return sqlSessionTemplate.selectList("StoreUsim_Query.selectUsimBasList", usimBasDto);
    }

    @Override
    public List<MspSalePlcyMstDto> selectUsimSalePlcyCdList(
            MspSalePlcyMstDto mspSalePlcyMstDto) {
        return sqlSessionTemplate.selectList("StoreUsim_Query.selectUsimSalePlcyCdList", mspSalePlcyMstDto);
    }

    @Override
    public List<MspRateMstDto> selectMspRateMstList(
            MspSalePlcyMstDto mspSalePlcyMstDto) {
        return sqlSessionTemplate.selectList("StoreUsim_Query.selectMspRateMstList", mspSalePlcyMstDto);
    }

    @Override
    public List<MspPlcyOperTypeDto> selectPlcyOperTypeList(
            MspSalePlcyMstDto mspSalePlcyMstDto) {
        return sqlSessionTemplate.selectList("StoreUsim_Query.selectPlcyOperTypeList", mspSalePlcyMstDto);
    }

    @Override
    public List<MspSalePlcyMstDto> selectUsimSalePlcyCdBannerList(
            MspSalePlcyMstDto mspSalePlcyMstDto) {
        return sqlSessionTemplate.selectList("Usim_Query.selectUsimSalePlcyCdBannerList", mspSalePlcyMstDto);
    }

    @Override
    public FormDtlDTO selectTermsData() {
        // TODO Auto-generated method stub
        return sqlSessionTemplate.selectOne("Usim_Query.selectTermsData");
    }

    @Override
    public List<UsimBasDto> listMspSaleAgrmMstMoreTwoRows(UsimBasDto usimBasDto) {
        // TODO Auto-generated method stub
        return sqlSessionTemplate.selectList("Usim_Query.listMspSaleAgrmMstMoreTwoRows",usimBasDto);
    }

    @Override
    public List<UsimMspRateDto> selectRateListMoreTwoRows(UsimBasDto usimBasDto) {
        // TODO Auto-generated method stub
        return sqlSessionTemplate.selectList("StoreUsim_Query.selectRateListMoreTwoRows",usimBasDto);
    }

    @Override
    public List<McpRequestSelfDlvryDto> selectUserUsimList(McpRequestSelfDlvryDto dupInfo) {
        return sqlSessionTemplate.selectList("Usim_Query.selectUserUsimList", dupInfo);
    }

    @Override
    public List<UsimBasDto> selectMcpUsimProdSortList(UsimBasDto usimBasDto) {
        return sqlSessionTemplate.selectList("Usim_Query.selectMcpUsimProdSortList", usimBasDto);
    }

    @Override
    public int deleteMcpUsimProdSort(UsimBasDto usimBasDto){
    	return sqlSessionTemplate.delete("Usim_Query.deleteMcpUsimProdSort",usimBasDto);
    }
    @Override
    public int insertMcpUsimProdSort(List<UsimBasDto> usimBasDtoList){
    	return sqlSessionTemplate.insert("Usim_Query.insertMcpUsimProdSort",usimBasDtoList);
    }


}
