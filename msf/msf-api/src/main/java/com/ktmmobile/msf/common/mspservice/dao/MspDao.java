package com.ktmmobile.msf.common.mspservice.dao;

import java.util.List;
import com.ktmmobile.msf.form.common.dto.PhoneMspDto;
import com.ktmmobile.msf.common.mspservice.dto.CmnGrpCdMst;
import com.ktmmobile.msf.common.mspservice.dto.MspNoticSupportMstDto;
import com.ktmmobile.msf.common.mspservice.dto.MspRateMstDto;
import com.ktmmobile.msf.common.mspservice.dto.MspSaleAgrmMst;
import com.ktmmobile.msf.common.mspservice.dto.MspSalePlcyMstDto;
import com.ktmmobile.msf.common.mspservice.dto.MspSalePrdtMstDto;
import com.ktmmobile.msf.common.mspservice.dto.MspSaleSubsdMstDto;


/**
 * @Class Name : MspDao
 * @Description : Msp 영역(Db link) 를 통한 조회 I/F
 *
 * @author : ant
 * @Create Date : 2016. 1. 12.
 */
public interface MspDao {

       /**
        * @Description : 해당 조직의 해당하는 정책정보를 가져온다.
        * 조직의 과 정책정보는 N:N 관계 이기 때문에
        * 정책정보의 분류값을 추가로 검색조건에 setting 해줘야된다.
        * @param mspSaleOrgnMstDto
        * @return
        * @Author : ant
        * @Create Date : 2016. 1. 12.
        */
        public List<MspSalePlcyMstDto> findMspSalePlcyMst(MspSalePlcyMstDto mspSalePlcyMstDto);

        /**
        * @Description :
        * 정책별 단품의 수수료 정보등을 가져온다
        * @param mspSalePrdtMstDto
        * @return
        * @Author : ant
        * @Create Date : 2016. 1. 12.
        */
        public MspSalePrdtMstDto findMspSalePrdMst(MspSalePrdtMstDto mspSalePrdtMstDto);


        /**
        * @Description :해당 요금정책코드에
        * 해당하는 요금약정개월수정보를가져온다.
        * @param salePlcyCd 요금정책코드
        * @return 요금약정개월정보
        * @Author : ant
        * @Create Date : 2016. 1. 12.
        */
        public List<MspSaleAgrmMst> listMspSaleAgrmMst(String salePlcyCd);

        /**
        * @Description : MSP MSP_SALE_SUBSD_MST 테이블에서 각종 요금정보를 조회한다.
        * @param mspSaleSubsdMstDto
        * @return
        * @Author : ant
        * @Create Date : 2016. 1. 25.
        */
        public MspSaleSubsdMstDto getMspSaleSubsdMst( MspSaleSubsdMstDto mspSaleSubsdMstDto) ;

        /**
        * @Description : MSP 코드 정보를 조회한다.
        * @param cmnGrpCdMst
        * @return
        * @Author : ant
        * @Create Date : 2016. 1. 25.
        */
        public CmnGrpCdMst findCmnGrpCdMst(CmnGrpCdMst cmnGrpCdMst);

        /**
        * @Description :
        * @param mspSalePlcyMstDto
        * @return
        * @Author : ant
        * @Create Date : 2016. 2. 4.
        */
        public List<MspRateMstDto> listRateByOrgnInfos(
                MspSalePlcyMstDto mspSalePlcyMstDto);

        /**
        * @Description :
        * @param mspSalePlcyMstDto
        * @return
        * @Author : ant
        * @Create Date : 2016. 2. 5.
        */
        public List<MspSalePlcyMstDto> listMspSalePlcyInfoByOnlyOrgn(MspSalePlcyMstDto mspSalePlcyMstDto);


        /**
        * @Description : Msp 테이블(CMN_INTM_MDL) 을 prdt_id (단품id)로 조회한다.
        * @param prdtId
        * @return
        * @Author : ant
        * @Create Date : 2016. 3. 17.
        */
        public PhoneMspDto findMspPhoneInfo(String prdtId);


        /**
        * @Description : 공시지원금 요금제 목록을 가져온다.
        * @param
        * @return
        * @Author : ant
        * @Create Date : 2016. 9. 6
        */
        public List<MspNoticSupportMstDto> listMspOfficialSupportRateNm();

        /**
        * @Description : 공시지원금 목록을 가져온다.
        * @param
        * @return
        * @Author : ant
        * @Create Date : 2016. 9. 6
        */
        public List<MspNoticSupportMstDto> listMspOfficialNoticeSupport(MspNoticSupportMstDto mspNoticSupportMstDto, int skipResult, int maxResult);

        /**
        * @Description : 공시지원금 갯수를 가져온다.
        * @param
        * @return
        * @Author : ant
        * @Create Date : 2016. 9. 6
        */
        public int listMspOfficialNoticeSupportCount(MspNoticSupportMstDto mspNoticSupportMstDto);



        /**
        * @Description : 계약번호의 주민번호
        * @param : contractNum 계약번호
        * @return : 암호화 된 주민번호
        * @Author : power
        * @Create Date : 2016. 12. 14
        */
        public String getCustomerSsn(String contractNum) ;


         /**
          * @Description : 요금제 정보 조회
          * @param : rateCd 요금제 코드
          * @return :
          * @Author : power
          * @Create Date : 2019. 12. 14
          */
         public MspRateMstDto getMspRateMst(String rateCd) ;

         public  List<MspSaleSubsdMstDto> listMspSaleMst(MspSaleSubsdMstDto mspSaleSubsdMstDto) ;


    List<MspSaleAgrmMst> findMspSaleMnth(String salePlcyCd);

    String getMspSubStatus(String contractNum);
}
