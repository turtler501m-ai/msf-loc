package com.ktmmobile.mcp.cont.dao;

import java.util.List;

import com.ktmmobile.mcp.cont.dto.ContDTO;
import com.ktmmobile.mcp.cont.dto.NationRateDto;
import com.ktmmobile.mcp.cont.dto.WireCounselHistoryReqDto;
import com.ktmmobile.mcp.cont.dto.WireCounselReqDto;
import com.ktmmobile.mcp.cont.dto.WireProductDTO;

public interface ContDao {

    public int selectContRateListCNT(ContDTO dto);

    public List<ContDTO> selectContRateList(ContDTO dto);

    public void insertContRate(ContDTO dto);

    public ContDTO selectView(ContDTO dto);

    public void updateContRate(ContDTO dto);

    public void deleteContRate(ContDTO dto);

    public int getTotalCount(ContDTO dto);

    public List<ContDTO> getList(ContDTO dto, int skipResult, int maxResult);

    public List<ContDTO> selectAddList(ContDTO dto, int skipResult, int maxResult);

    public List<NationRateDto> selectNationRateList();

    public NationRateDto nationRateAjax(NationRateDto nationRateDto);

    public int getAddTotalCount(ContDTO dto);



    /**
     * 유선상품 컨텐츠(한눈에보기) 등록
     * @param wireProductDTO
     */
    public void insertWireProductCont(WireProductDTO wireProductDTO);

    /**
     * 유선상품 컨텐츠(한눈에보기) 수정
     * @param wireProductDTO
     */
    public void updateWireProductCont(WireProductDTO wireProductDTO);

    /**
     * 유선상품 컨텐츠(한눈에보기) 삭제
     * @param wireProductDTO
     */
    public void deleteWireProductCont(String wireProdCd);

    /**
     * 유선상품 컨텐츠(한눈에보기) 조회
     * @param wireProductDTO
     */
    public WireProductDTO selectWireProductCont(String wireProdCd);

    /**
     * 유선상품 컨텐츠(한눈에보기) 목록
     * @return
     */
    public List<WireProductDTO> selectWireProductContList();

    /**
     * 유선상품 상세 등록
     * @param wireProductDTO
     */
    public void insertWireProdDtl(WireProductDTO wireProductDTO);

    /**
     * 유선상품 상세 수정
     * @param wireProductDTO
     */
    public void updateWireProductDtl(WireProductDTO wireProductDTO);

    /**
     * 유선상품 상세 조회
     * @param wireProductDTO
     */
    public WireProductDTO selectWireProductDtl(String wireProdCd);

    /**
     * 유선상품 상세 조회
     * @param wireProductDTO
     */
    public void deleteWireProductDtl(String wireProdCd);

    /**
     * 유선상품 상세 리스트 갯수
     * @param wireProductDTO
     * @return
     */
    public int selectWireProductDtlCnt(WireProductDTO wireProductDTO);

    /**
     * 유선상품 상세 리스트
     * @param wireProductDTO
     * @return
     */
    public List<WireProductDTO> selectWireProductDtlList(WireProductDTO wireProductDTO, int skipResult, int maxResult);

    /**
     * 유선상담관리 리스트 갯수
     * @param WireCounselReqDto
     * @return
     */
    public int countWireCounselList(WireCounselReqDto wireCounselReqDto);

    /**
     * 유선상담관리 리스트
     * @param List<WireCounselReqDto>
     * @return
     */
    public List<WireCounselReqDto> getWireCounselList(WireCounselReqDto wireCounselReqDto, int skipResult, int maxResul);


    /**
     * 유선상담관리 상세
     * @param WireCounselReqDto
     * @return
     */
    public WireCounselReqDto getWireCounsel(WireCounselReqDto wireCounselReqDto) ;

    /**
     * 유선상담관리 상세 수정
     * @param WireCounselReqDto
     */
    public boolean updateCounsel(WireCounselReqDto wireCounselReqDto);


    /**
     * 유선상담관리 삭제
     * @param WireCounselReqDto
     */
    public boolean deleteCounsel(WireCounselReqDto wireCounselReqDto);

    /**
     * 유선상담 이력 등록
     * @param wireProductDTO
     */
    public boolean insertCounselHistory(WireCounselHistoryReqDto wireCounselHistoryReqDto);

    /**
     * 유선상담 이력 리스트
     * @param List<WireCounselHistoryReqDto>
     * @return
     */
    public List<WireCounselHistoryReqDto> getCounselHistoryList(WireCounselHistoryReqDto wireCounselHistoryReqDto);





}
