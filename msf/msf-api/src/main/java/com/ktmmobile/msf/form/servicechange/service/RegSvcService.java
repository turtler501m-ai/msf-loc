package com.ktmmobile.msf.form.servicechange.service;

import java.net.SocketTimeoutException;
import java.util.List;
import java.util.Map;
import com.ktmmobile.msf.form.servicechange.dto.McpRegServiceDto;
import com.ktmmobile.msf.form.servicechange.dto.MyPageSearchDto;
import com.ktmmobile.msf.system.common.mplatform.vo.MpAddSvcInfoParamDto;
import com.ktmmobile.msf.system.common.mplatform.vo.MpRegSvcChgVO;
import com.ktmmobile.msf.system.common.mplatform.vo.MpSocVO;

public interface RegSvcService {

    /**
     * 부가서비스 신청 목록 조회
     * @author bsj
     * @Date : 2021.12.30
     * @param ncn
     * @param ctn
     * @param custId
     * @return
     */

     public List<String> selectAddSvcInfoDto(String ncn, String ctn, String custId);

     /**
      * 이용중인 부가서비스 조회 상세설명
      * @author bsj
      * @Date : 2021.12.30
      * @param rateAdsvcCtgBasDTO
      * @return
      */

//     public RateAdsvcGdncBasXML selectAddSvcDtl(RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO);

     /**
      *  부가서비스 신청
      * @author bsj
      * @Date : 2021.12.30
      * @param ncn
      * @param ctn
      * @param custId
      * @param soc
      * @param ftrNewParam
      * @return
      * @throws SocketTimeoutException
      */

     public MpRegSvcChgVO regSvcChg(String ncn, String ctn, String custId, String soc, String ftrNewParam) throws SocketTimeoutException;

     /**
      *  이용중인 부가서비스 조회
      * @author bsj
      * @Date : 2021.12.30
      * @param ncn
      * @param ctn
      * @param custId
      * @return
      */

     public MpAddSvcInfoParamDto selectmyAddSvcList(String ncn, String ctn, String custId);
     public MpAddSvcInfoParamDto selectmyAddSvcList(String ncn, String ctn, String custId,String lstComActvDate);

     /**
      * 부가서비스 해지
      * @author bsj
      * @Date : 2021.12.30
      * @param searchVO
      * @param rateAdsvcCd
      * @return
      * @throws SocketTimeoutException
      */
     public Map<String, Object> moscRegSvcCanChg(MyPageSearchDto searchVO, String rateAdsvcCd) throws SocketTimeoutException;

     /**
      * 부가서비스 해지(prodHstSeq 포함)
      * @author 김동혁
      * @Date : 2023.08.02
      * @param searchVO
      * @param rateAdsvcCd
      * @return
      * @throws SocketTimeoutException
      */
     public Map<String, Object> moscRegSvcCanChgSeq(MyPageSearchDto searchVO, String rateAdsvcCd, String prodHstSeq) throws SocketTimeoutException;

     /**
      * 부가서비스 조회 구분(addDivCd : G = 일반, R = 로밍)
      * @author 김동혁
      * @Date : 2023.06.21
      * @param mpSocList
      * @param addDivCd
      * @return
      */
     public void getMpSocListByDiv(List<MpSocVO> mpSocList, String addDivCd);

     /**
      * 부가서비스 조회 구분(addDivCd : G = 일반, R = 로밍)
      * @author 김동혁
      * @Date : 2023.06.21
      * @param mcpRegServiceList
      * @param addDivCd
      * @return
      */
     public void getMcpRegServiceListByDiv(List<McpRegServiceDto> mcpRegServiceList, String addDivCd);

     /**
      * 로밍 부가서비스 조회
      * @author 김동혁
      * @Date : 2023.06.21
      * @param
      * @return List<RateAdsvcCtgBasDTO>
      */
//     public List<RateAdsvcCtgBasDTO> getRoamList();

     /**
      * 이용중인 상품 변경가능여부 판단
      * @author 김동혁
      * @Date : 2023.07.20
      * @param mpSoc
      * @return String
      */
     public String getUpdateYn(MpSocVO mpSoc);

     /**
      * 시작일, 이용가능기간으로 종료일 계산
      * @author 김동혁
      * @Date : 2023.07.20
      * @param mpSoc
      * @param usePrd
      * @return String
      */
     public String getEndDttmUsePrd(MpSocVO mpSoc, String usePrd);

     /**
      * 계약번호로 회선번호 가져오기
      * @author 김동혁
      * @Date : 2023.07.24
      * @param ncn
      * @return String
      */
     public String getCtnByNcn(String ncn, boolean flagMasking);
}
