package com.ktmmobile.msf.domains.form.common.mspservice;

import java.util.List;
import com.ktmmobile.msf.domains.form.common.dto.PhoneMspDto;
import com.ktmmobile.msf.domains.form.common.exception.McpCommonException;
import com.ktmmobile.msf.domains.form.common.mspservice.dto.CmnGrpCdMst;
import com.ktmmobile.msf.domains.form.common.mspservice.dto.MspNoticSupportMstDto;
import com.ktmmobile.msf.domains.form.common.mspservice.dto.MspOrgDto;
import com.ktmmobile.msf.domains.form.common.dto.MspRateMstDto;
import com.ktmmobile.msf.domains.form.common.dto.MspSaleAgrmMst;
import com.ktmmobile.msf.domains.form.common.mspservice.dto.MspSaleDto;
import com.ktmmobile.msf.domains.form.common.dto.MspSalePlcyMstDto;
import com.ktmmobile.msf.domains.form.common.dto.MspSaleSubsdMstDto;


/**
 * @Class Name : MspService
 * @Description : Msp 정보 조회 서비스
 * dblink 되어있는 MSP 테이블로만 이루어져있는 서비스 형태의 I/F
 *
 * @author : ant
 * @Create Date : 2016. 1. 12.
 */
public interface MspService {

    /**
    * @Description : MSP 제조사,공급사 조회 한다.
    * @param mspOrgDto
    * @return
    * @Author : ant
    * @Create Date : 2016. 1. 5.
    */
    public List<MspOrgDto> findMspOrgList(MspOrgDto mspOrgDto);


    /**
     * @Description : MSP 제조사 정보를  NMCP_PROD_BAS 상품정보 테이블 조인 해서 조회 한다.
     * @param
     * @return
     * @Author : papier
     * @Create Date : 2017. 6. 22.
     */
     public List<MspOrgDto> findMspOrgListRe(String prodType);

    /**
    * @Description :
    * <pre>
    * 해당기관의 정책정보를 조회한다.
    * 정책조회 기준정보는
    * 결제상태가 Y 이면서 오늘날짜가 판매시작일과 판매종료일 안에 포함되는값을 기본필수값으로 가져간다.
    * 검색조건에 필수적으로 아래 항목들을 선택해줘야된다.
    * orgnId :기관코드
    * plcyTypeCd 온라인(직영):D ,오프라인(도매):W , 특수:S ,제휴 :A
    * prdtSctnCd LTE,3G
    * plcySctnCd 01:단말,02:유심
    * sprtTp KD:단말할인,PM:요금할인
    * prdtId :상품ID
    * </pre>
    * @param mspSalePlcyMstDto
    * @return
    * @Author : ant
    * @Create Date : 2016. 1. 12.
    */
    public List<MspSalePlcyMstDto> findMspSalePlcyMst(MspSalePlcyMstDto mspSalePlcyMstDto);

    /**
    * @Description :
    * 해당 상품의 핸드폰단말기코드(nrds) 코드로 msp 정잭청보를 조회한다.
    * 상품에는 최대 2가지의 정책정보를 가져올수있다 2가지 이상이 나올경우
    * 단말할인정책을 기준으로 가져온다.
    * @param prdtId nrds 단품 코드
    * @param mspSalePlcyMstDto 상품정책정보 조회를 위한 검색조건 bean
    * @return msp 판매 상품 정보
    * 		    단품(판매) 수수료정보와 판매정책정보를 포함하고있다.
    *
    * @Author : ant
    * @Create Date : 2016. 1. 12.
    */
    public MspSaleDto getMspSale(String prdtId ,MspSalePlcyMstDto mspSalePlcyMstDto) throws McpCommonException;


    /**
    * @Description :
    * 판매정책크도에 해당하는 약정개월수 정보를 조회한다.
    * @param salePlcyCd 판매정책코드
    * @return 약정개월정보 리스트
    * @throws McpCommonException
    * @Author : ant
    * @Create Date : 2016. 1. 12.
    */
    public List<MspSaleAgrmMst> listMspSaleAgrmMst(String salePlcyCd) throws McpCommonException ;

    public List<MspSaleAgrmMst> listMspSaleAgrmMst2(MspSaleAgrmMst mspSaleAgrmMst) throws McpCommonException ;


    /**
    * @Description :
    * MSP 코드테이블을 조회한다.
    * @param grpCd 그룹cd
    * @param cdVal 항목cd
    * @return
    * @Author : ant
    * @Create Date : 2016. 1. 25.
    */
    public CmnGrpCdMst findCmnGrpCdMst(String grpCd,String cdVal);


    /**
    * @Description :해당 단품에(NRDS코드) 해당하는
    *    요금제 정보를 리스트조회한다. 정책이 요금할인(PM),단말할인(KD) 2개가 존재할경우
    *    해당 모든 요금제 리스트를 가져온다.
    *   ->리스트 조회시 2가지 정보를 추가해준다.
    *   할부수수료와 ,기본수수료값을 조회해서 요금제정보에 추가로 할당한다.

    * @param prdtId 상품id(nrds4자리코드)
    * @param mspSaleDto 정책종합정보 Bean
    * @param oldYn 중고YN
    * @param orgnId 조직코드
    * @param operType 가입유형 (null 허용)
    * @param instNom 할부개월 (null 허용)
    * @param rateCd 요금제코드 (null 허용)
    * @param noArgmYn Y(무약정요금은 출력하지 않는다) (null 허용)
    * @return 요금제 정보 리스트
    * @Author : ant
    * @Create Date : 2016. 1. 27.
    */
    public List<MspSaleSubsdMstDto> listChargeInfo(String prdtId,MspSaleDto mspSaleDto,String oldYn,String orgnId,String operType,String instNom,String rateCd,String noArgmYn);


    /**
     * @Description :해당 단품에(NRDS코드) 해당하는
     *    요금제 정보를 리스트조회한다. 정책이 요금할인(PM),단말할인(KD) 2개가 존재할경우
     *    해당 모든 요금제 리스트를 가져온다.
     *   ->리스트 조회시 2가지 정보를 추가해준다.
     *   할부수수료와 ,기본수수료값을 조회해서 요금제정보에 추가로 할당한다.

     * @param prdtId 상품id(nrds4자리코드)
     * @param mspSaleDto 정책종합정보 Bean
     * @param oldYn 중고YN
     * @param orgnId 조직코드
     * @param operType 가입유형 (null 허용)
     * @param instNom 약정개월  (null 허용)
     * @param modelMonthly 단말개월  (null 허용)
     * @param rateCd 요금제코드 (null 허용)
     * @param noArgmYn Y(무약정요금은 출력하지 않는다) (null 허용)
     * @return 요금제 정보 리스트
     * @Author : ant
     * @Create Date : 2016. 1. 27.
     */
    public List<MspSaleSubsdMstDto> listChargeInfo(String prdtId,MspSaleDto mspSaleDto,String oldYn,String orgnId,String operType,String instNom,String modelMonthly,String rateCd,String noArgmYn);

    public List<MspSaleSubsdMstDto> listChargeInfo(String prdtId,MspSaleDto mspSaleDto,String oldYn,String orgnId,String operType,String instNom,String modelMonthly,String rateCd,String noArgmYn,String onOffType);



    /**
     * <font color="red" size="2">
    * 파라메터로 입력받는 값(기관코드,할인유형,정책구분(유심,단말),들로<BR>
    * 정책을 조회 하여 해당 정책에 맵핑 되있는 요금제 정보를 가져온다.
    * </font>
    * @param orgnId	기관코드
    * @param sprtTp 할인유형		 => 단말할인:KD , 요금할인:PM
    * @param plcySctnCd 정책구분코드 => LTE:LTE     , 3G:3G
    * @param prdtSctnCd 제품구분코드 => 3G:02       , LGE:03
    * @param plcyTypeCd 정책유형코드 => D:온라인(직영) , W:오프라인(도매)
    * @return 요금제정보 리스트
    * @Author : ant
    * @Create Date : 2016. 2. 4.
    */
    public List<MspRateMstDto> listRateByOrgnInfos(String orgnId,String sprtTp,String plcySctnCd,String prdtSctnCd,String plcyTypeCd);


    /**
    * @Description :해당 단품에(NRDS코드) 해당하는
    *    요금제 정보를 리스트조회한다.
    *   ->리스트 조회시 2가지 정보를 추가해준다.
    *   할부수수료와 ,기본수수료값을 조회해서 요금제정보에 추가로 할당한다.

    * @param MspSaleSubsdMstDto mspSaleSubsdMstDto
    * @Create Date : 2016. 2. 04.
    */
    public List<MspSaleSubsdMstDto> listChargeInfoUsim(MspSaleSubsdMstDto inputMspSaleSubsdMstDto);



    /**
    * @Description :
    * <pre>
    * 해당기관의 정책정보를 조회한다.
    * 정책조회 기준정보는
    * 결제상태가 Y 이면서 오늘날짜가 판매시작일과 판매종료일 안에 포함되는값을 기본필수값으로 가져간다.
    * 검색조건에 필수적으로 아래 항목들을 선택해줘야된다.
    * orgnId :기관코드
    * plcyTypeCd 온라인(직영):D ,오프라인(도매):W , 특수:S ,제휴 :A
    * prdtSctnCd LTE,3G
    * plcySctnCd 01:단말,02:유심
    * sprtTp KD:단말할인,PM:요금할인
    * </pre>
    * @param mspSalePlcyMstDto
    * @return
    * @Author : ant
    * @Create Date : 2016. 1. 12.
    */
    public List<MspSalePlcyMstDto> listMspSalePlcyInfoByOnlyOrgn(MspSalePlcyMstDto mspSalePlcyMstDto);

    /**
    * @Description : 단품id로 모델 정보를 조회한다.
    * @param prdtId 단품id (4자리 nrds코드)
    * @return
    * @Author : ant
    * @Create Date : 2016. 3. 17.
    */
    public PhoneMspDto findMspPhoneInfo(String prdtId);


    /**
     * @Description : 공시지원금 요금제 목록을 조회한다.
     * @param
     * @return
     * @Author :
     * @Create Date : 2016. 9. 6.
     */
    public List<MspNoticSupportMstDto> listMspOfficialSupportRateNm();

    /**
     * @Description : 공시지원금 목록을 조회한다.
     * @param
     * @return
     * @Author :
     * @Create Date : 2016. 9. 6.
     */
    public List<MspNoticSupportMstDto> listMspOfficialNoticeSupport(MspNoticSupportMstDto mspNoticSupportMstDtoint, int skipResult, int maxResult);

    /**
     * @Description : 공시지원금 갯수를 조회한다.
     * @param
     * @return
     * @Author :
     * @Create Date : 2016. 9. 6.
     */
    public int listMspOfficialNoticeSupportCount(MspNoticSupportMstDto mspNoticSupportMstDto);

    /**
     * @Description : 청소년 여부 확인
     * @param : contractNum 계약번호
     * @return : true 청소년 false 성인
     * @Author :
     * @Create Date : 2016. 9. 6.
     */
    public boolean checkKid(String contractNum);


    public MspSaleSubsdMstDto getLowPriceChargeInfoByProdList(String prdtId
            ,MspSalePlcyMstDto mspSalePlcyMstDto
            , String oldYn
            , String orgnId
            , String operType
            , String instNom
            , String rateCd
            , String noArgmYn
            , CmnGrpCdMst cmnGrpCdMst ) ;

     /**
      * @Description : 요금제 정보 조회
      * @param : rateCd 요금제 코드
      * @return :
      * @Author : power
      * @Create Date : 2019. 10. 01
      */
     public MspRateMstDto getMspRateMst(String rateCd) ;


     /**
      * @Description : 확인 정보 조회
      * @param : rateCd 요금제 코드
      * @return :
      * @Author : power
      * @Create Date : 2019. 10. 01
      */
     public List<MspSaleSubsdMstDto> listMspSaleMst(MspSaleSubsdMstDto mspSaleSubsdMstDto) ;


    List<MspSaleAgrmMst> mspSaleAgrmMstSing(String salePlcyCd);

    public MspSaleSubsdMstDto getLowPriceChargeInfoByProdList(String prdtId
            ,MspSalePlcyMstDto mspSalePlcyMstDto
            , String oldYn
            , String orgnId
            , String operType
            , String instNom
            , String rateCd
            , String noArgmYn
            , CmnGrpCdMst cmnGrpCdMst
            , String onOffType) ;

    public MspSaleSubsdMstDto getLowPriceChargeInfoByProdList(String prdtId
            ,MspSalePlcyMstDto mspSalePlcyMstDto
            , String oldYn
            , String orgnId
            , String operType
            , String instNom
            , String rateCd
            , String noArgmYn
            , CmnGrpCdMst cmnGrpCdMst
            , String onOffType
            , String  modelMonthly) ;

    String getMspSubStatus(String contractNum);
}