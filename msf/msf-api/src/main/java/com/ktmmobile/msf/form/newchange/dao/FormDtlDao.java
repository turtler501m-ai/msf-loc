package com.ktmmobile.msf.form.newchange.dao;

import java.util.List;
import com.ktmmobile.msf.form.newchange.dto.FormDtlDTO;

public interface FormDtlDao {

    public int FormDtlListCNT(FormDtlDTO formDtlDTO);

    public List<FormDtlDTO> FormDtlList(FormDtlDTO formDtlDTO,int skipResult, int maxResult);

    public boolean FormDtlInsert(FormDtlDTO formDtlDTO);

    public FormDtlDTO FormDtlView(FormDtlDTO formDtlDTO);

    public boolean FormDtlUpdate(FormDtlDTO formDtlDTO);

    public String FormDtlGetVersionAjax(String cdGroupId1, String cdGroupId2);
    

    /**
     * <pre>
     * 설명     : 사용 가능한 모든 약관 정보를 가지고 온다.
     * @return
     * @return: List<FormDtlDTO>
     * </pre>
     */
    public List<FormDtlDTO> getFormDtlList() ;


    /**
     * <pre>
     * 설명     : 약관 정보를 조회한다.
     * @return
     * @return: List<FormDtlDTO>
     * </pre>
     */
    public List<FormDtlDTO> getFormList(FormDtlDTO formDtlDTO) ;

    /**
     * 기타약관 및 편의점 유의사항
     * */
    public int etcNoticeListCnt(FormDtlDTO formDtlDTO);
    public List<FormDtlDTO> etcNoticeList(FormDtlDTO formDtlDTO,int skipResult, int maxResult);

    public List<FormDtlDTO> getCdGroupIdList(FormDtlDTO formDtlDTO);
    public List<FormDtlDTO> getEtcCdGroupIdList(FormDtlDTO formDtlDTO);

    FormDtlDTO FormDtlViewByDate(FormDtlDTO formDtlDTO);
}
