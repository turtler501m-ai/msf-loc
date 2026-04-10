package com.ktmmobile.mcp.cont.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ktmmobile.mcp.cont.dao.ContDao;
import com.ktmmobile.mcp.cont.dto.ContDTO;
import com.ktmmobile.mcp.cont.dto.NationRateDto;
import com.ktmmobile.mcp.cont.dto.WireCounselHistoryReqDto;
import com.ktmmobile.mcp.cont.dto.WireCounselReqDto;
import com.ktmmobile.mcp.cont.dto.WireProductDTO;
import com.ktmmobile.mcp.common.util.FileUtil;

@Service
public class ContSvcImpl implements ContSvc {
    @Autowired
    ContDao dao;

    @Autowired
    FileUtil fileUtil;

    private static final String IMAGEPATH ="cont";

    @Override
    public int selectContRateListCNT(ContDTO dto) {
        // TODO Auto-generated method stub
        return dao.selectContRateListCNT(dto);
    }

    @Override
    public List<ContDTO> selectContRateList(ContDTO dto){
        // TODO Auto-generated method stub
        return dao.selectContRateList(dto);
    }

    @Override
    @Transactional
    public void insertContRate(ContDTO dto){
        // TODO Auto-generated method stub
        if(null!=dto.getUploadImg()){
            MultipartFile uploadImg = dto.getUploadImg();
            if (null != uploadImg && !uploadImg.isEmpty()) {
                String uploadImgUrl = fileUtil.fileTrans(uploadImg,IMAGEPATH);
                dto.setContImg(uploadImgUrl);
            }
        }
        dao.insertContRate(dto);
    }

    @Override
    public ContDTO selectView(ContDTO dto){
        // TODO Auto-generated method stub
        return dao.selectView(dto);
    }

    @Override
    @Transactional
    public void updateContRate(ContDTO dto){
        // TODO Auto-generated method stub
        if(null!=dto.getUploadImg() && dto.getUploadImg().getSize()>0){
            MultipartFile uploadImg = dto.getUploadImg();
            String uploadImgUrl = fileUtil.fileTrans(uploadImg,IMAGEPATH);
            dto.setContImg(uploadImgUrl);
        }
        dao.updateContRate(dto);
    }

    @Override
    @Transactional
    public void deleteContRate(ContDTO dto){
        // TODO Auto-generated method stub
        dao.deleteContRate(dto);
    }

    /**
     * 모바일 부가서비스 게시물 총 개수
     */
    @Override
    public int getTotalCount(ContDTO dto) {
        return dao.getTotalCount(dto);
    }

    /**
     * 모바일 부가서비스 게시물 가져오기
     */
    @Override
    public List<ContDTO> getMobileList(ContDTO dto, int skipResult,
            int maxResult) {
        return dao.getList(dto, skipResult, maxResult);
    }

    @Override
    public List<ContDTO> selectAddList(ContDTO dto, int skipResult, int maxResult) {
        // TODO Auto-generated method stub
        return dao.selectAddList(dto,skipResult,maxResult);
    }

    @Override
    public List<NationRateDto> selectNationRateList() {
        return dao.selectNationRateList();
    }

    @Override
    public NationRateDto nationRateAjax(NationRateDto nationRateDto) {
        return dao.nationRateAjax(nationRateDto);
    }

    @Override
    public int getAddTotalCount(ContDTO dto) {
        // TODO Auto-generated method stub
        return dao.getAddTotalCount(dto);
    }

    @Override
    public void insertWireProductCont(WireProductDTO wireProductDTO) {
        dao.insertWireProductCont(wireProductDTO);
    }

    @Override
    public WireProductDTO selectWireProductCont(String wireProdCd) {
        return dao.selectWireProductCont(wireProdCd);
    }

    @Override
    public List<WireProductDTO> selectWireProductContList() {
        return dao.selectWireProductContList();
    }

    @Override
    public void updateWireProductCont(WireProductDTO wireProductDTO) {
        dao.updateWireProductCont(wireProductDTO);
    }

    @Override
    public void deleteWireProductCont(String wireProdCd) {
        dao.deleteWireProductCont(wireProdCd);
    }

    @Override
    public void insertWireProdDtl(WireProductDTO wireProductDTO) {
        dao.insertWireProdDtl(wireProductDTO);
    }

    @Override
    public void updateWireProductDtl(WireProductDTO wireProductDTO) {
        dao.updateWireProductDtl(wireProductDTO);
    }

    @Override
    public WireProductDTO selectWireProductDtl(String wireProdCd) {
        return dao.selectWireProductDtl(wireProdCd);
    }

    @Override
    public void deleteWireProductDtl(String wireProdDtlSeq) {
        dao.deleteWireProductDtl(wireProdDtlSeq);
    }

    @Override
    public int selectWireProductDtlCnt(WireProductDTO wireProductDTO) {
        return dao.selectWireProductDtlCnt(wireProductDTO);
    }

    @Override
    public List<WireProductDTO> selectWireProductDtlList(WireProductDTO wireProductDTO, int skipResult, int maxResul) {
        return dao.selectWireProductDtlList(wireProductDTO, skipResult, maxResul);
    }

    @Override
    public int countWireCounselList(WireCounselReqDto wireCounselReqDto){
        return dao.countWireCounselList(wireCounselReqDto);
    }


    @Override
    public List<WireCounselReqDto> getWireCounselList(WireCounselReqDto wireCounselReqDto, int skipResult, int maxResul) {
        return dao.getWireCounselList(wireCounselReqDto, skipResult, maxResul);
    }

    @Override
    public WireCounselReqDto getWireCounsel(WireCounselReqDto wireCounselReqDto) {
        return dao.getWireCounsel(wireCounselReqDto);
    }

    @Override
    public boolean updateCounsel(WireCounselReqDto wireCounselReqDto) {
        return dao.updateCounsel(wireCounselReqDto);
    }


    @Override
    public boolean deleteCounsel(WireCounselReqDto wireCounselReqDto) {
        return dao.deleteCounsel(wireCounselReqDto);
    }

    @Override
    public boolean insertCounselHistory(WireCounselHistoryReqDto wireCounselHistoryReqDto) {
        return dao.insertCounselHistory(wireCounselHistoryReqDto);
    }

    @Override
    public List<WireCounselHistoryReqDto> getCounselHistoryList(WireCounselHistoryReqDto wireCounselHistoryReqDto) {
        return dao.getCounselHistoryList(wireCounselHistoryReqDto);
    }




}
