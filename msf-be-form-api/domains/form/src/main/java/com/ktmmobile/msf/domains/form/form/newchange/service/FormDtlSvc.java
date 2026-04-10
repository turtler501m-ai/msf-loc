package com.ktmmobile.msf.domains.form.form.newchange.service;

import java.util.List;

import com.ktmmobile.msf.domains.form.form.newchange.dto.FormDtlDTO;
import com.ktmmobile.msf.domains.form.common.commCode.dto.CommCodeInstDTO;

public interface FormDtlSvc {

    /*
     * 서식지 관리자 공통 코드 목록 가져오기
     */
    public List<CommCodeInstDTO> FormDtlCommonCodeList(String id);


    public CommCodeInstDTO FormDtlCommonCode(String id);

    /*
     *서식지 이용약관 목록갯수 가져오기
     */
    public int FormDtlListCNT(FormDtlDTO formDtlDTO);

    /*
     *서식지 이용약관 목록 가져오기
     */
    public List<FormDtlDTO> FormDtlList(FormDtlDTO formDtlDTO, int skipResult, int maxResult);


    /*
     *서식지 이용약관 등록
     */
    public boolean FormDtlInsert(FormDtlDTO formDtlDTO);


    /*
     *서식지 이용약관 보기
     */
    public FormDtlDTO FormDtlView(FormDtlDTO formDtlDTO);


    /*
     *서식지 이용약관 수정
     */
    public boolean FormDtlUpdate(FormDtlDTO formDtlDTO);


    /*
     *서식지 이용약관 버전 가져오기
     */
    public String FormDtlGetVersionAjax(String cdGroupId1, String cdGroupId2);


    public int etcNoticeListCnt(FormDtlDTO formDtlDTO);

    public List<FormDtlDTO> etcNoticeList(FormDtlDTO formDtlDTO, int skipResult, int maxResult);

    public List<FormDtlDTO> getCdGroupIdList(FormDtlDTO formDtlDTO);

    public List<FormDtlDTO> getEtcCdGroupIdList(FormDtlDTO formDtlDTO);

    List<FormDtlDTO> FormDtlLists(FormDtlDTO formDtlDTO);

    FormDtlDTO FormDtlViewByDate(FormDtlDTO oFormDtlDTO);
}
