package com.ktmmobile.msf.form.servicechange.service;


import java.io.IOException;
import java.net.SocketTimeoutException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.ktmmobile.msf.form.servicechange.dto.FarPricePlanResDto;
import com.ktmmobile.msf.form.servicechange.dto.MapWrapper;
import com.ktmmobile.msf.form.servicechange.dto.McpFarPriceDto;
import com.ktmmobile.msf.form.servicechange.dto.McpServiceAlterTraceDto;
import com.ktmmobile.msf.form.servicechange.dto.MyPageSearchDto;
import com.ktmmobile.msf.form.servicechange.dto.RateAdsvcCtgBasDTO;
import com.ktmmobile.msf.system.common.dto.McpIpStatisticDto;
import com.ktmmobile.msf.system.common.mplatform.dto.MoscFarPriceChgDto;
import com.ktmmobile.msf.system.common.mplatform.dto.MoscFarPriceResDto;
import com.ktmmobile.msf.system.common.mplatform.dto.MoscWireUseTimeInfoRes;
import com.ktmmobile.msf.system.common.mplatform.dto.RegSvcChgRes;
import com.ktmmobile.msf.system.common.mplatform.vo.MpCommonXmlVO;
import com.ktmmobile.msf.system.common.mspservice.dto.MspSaleSubsdMstDto;
import jakarta.xml.bind.JAXBException;

/**
 * 요금제 변경
 * @author bsj
 * @Date : 2021.12.30
 */

public interface MsfFarPricePlanService {

    /**
     * 요금제 안내 문구 xml 조회해서 안내문구
     * @author bsj
     * @Date : 2021.12.30
     * @param mcpFarPriceDto
     * @return
     */

    public FarPricePlanResDto getFarPricePlanWrapper(McpFarPriceDto mcpFarPriceDto);

    /**
     * x20 요금제 불가능 체크
     * @author bsj
     * @Date : 2021.12.30
     * @param ncn
     * @param ctn
     * @param custId
     * @return
     */

    public String selectFarPricePlanYn(String ncn, String ctn, String custId);

    /**
     * x88 요금상품예약변경
     * @author bsj
     * @Date : 2021.12.30
     * @param searchVO
     * @param soc
     * @param ftrNewParam
     * @param ip
     * @param userId
     * @return
     * @throws ParseException
     */
    public MoscFarPriceChgDto doFarPricePlanRsrvChg(MyPageSearchDto  searchVO, String soc, String ftrNewParam, String ip, String userId ,String befChgRateCd , String befChgRateAmnt ) throws ParseException;

    /**
     * 요금제상품 예약 변경 조회 x89
     * @author bsj
     * @Date : 2021.12.30
     * @param searchVO
     * @return
     */

    public MoscFarPriceResDto doFarPricePlanRsrvSearch(MyPageSearchDto searchVO);

    /**
     * 요금제 예약변경 취소 x90
     * @author bsj
     * @Date : 2021.12.30
     * @param searchVO
     * @return
     */

    public MpCommonXmlVO  doFarPricePlanRsrvCancel(MyPageSearchDto searchVO);

    /**
     * 83.회선 사용기간조회
     * @author bsj
     * @Date : 2021.12.30
     * @param ncn
     * @param ctn
     * @param custId
     * @return
     */

    public MoscWireUseTimeInfoRes moscWireUseTimeInfo(String ncn, String ctn, String custId);

    /**
     * 요금제 변경 첫번째 카테고리 조회
     * @author bsj
     * @Date : 2021.12.30
     * @return
     */

    public MapWrapper getCtgMapWrapper();


    /**
     * m전산 요금제목록
     * 가입할수 있는 요금제 목록 조회
     * @author bsj
     * @Date : 2021.12.30
     * @param rateAdsvcCtgBasDTO
     * @param rateCd
     * @return
     * @throws JAXBException
     */

    public List<RateAdsvcCtgBasDTO> selectFarPricePlanList(RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO , String rateCd) throws JAXBException, IOException;

    /**
     *  이용중인 부가서비스 조회 체크로직
     * @author bsj
     * @Date : 2021.12.30
     * @param searchVO
     * @param toSocCode
     * @param nowPriceSocCode
     * @param paraAlterTrace
     * @return
     * @throws InterruptedException
     */

    public Map<String, Object> getAddSvcInfoDto(MyPageSearchDto searchVO, String  toSocCode , String  nowPriceSocCode, McpServiceAlterTraceDto paraAlterTrace) throws InterruptedException;

    /**
     * 자회선 부가서비스 가입
     * @author bsj
     * @Date : 2021.12.30
     * @param ncn
     * @param ctn
     * @param custId
     * @param soc
     * @param otpNo
     * @return
     * @throws SocketTimeoutException
     */

    public RegSvcChgRes regSvcChgNe(String ncn, String ctn, String custId, String soc, String otpNo) throws SocketTimeoutException;

    /**
     * 가입가능한 요금제 M전산 + 어드민 XML 정리
     * @author bsj
     * @Date : 2021.12.30
     * @param dto
     * @param list
     * @param rateCd
     * @return
     * @throws SocketTimeoutException
     * @throws JAXBException
     */

    public List<RateAdsvcCtgBasDTO> getStrCtgXmlList(RateAdsvcCtgBasDTO dto, List<RateAdsvcCtgBasDTO> list, String rateCd)
            throws IOException, JAXBException;

    /**
     * 요금제 예약 변경 부가서비스 일괄 처리
     *  1.해당 회선이 실제 요금제 예약변경 대상 요금으로 요금제 변경 처리 되었는지 체크
     *  2.요금제별 배타관계 부가서비스 해지 처리??? (추가 제공하는 부가 서비스 해지)
     *  3.부가 서비스 가입 처리
     *  3-1. 부가 서비스 가입 처리 해야 할 리스트 조회(할인 금액)
     * @author bsj
     * @Date : 2021.12.30
     * @param ipStatistic
     * @return
     * @throws InterruptedException
     */

    public HashMap<String, Object> batchResChangeAddPrdCall(McpIpStatisticDto ipStatistic, int instMnthAmtPric) throws InterruptedException ;

    /**
     * 할인금액 확인
     * @author PAPIER
     * @Date : 2022.05.24
     * @param rateCd 요금제 코드
     * @return
     */
    public int getPromotionDcAmt(String rateCd);

    /**
     * 요금제  월 납부 금액 확인
     * @param ncn
     * 월납부금액 (VAT) - 프로모션 할인 금액
     * @return int
     */
    public MspSaleSubsdMstDto getRateInfo(String rateCd) ;


}
