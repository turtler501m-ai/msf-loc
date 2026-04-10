package com.ktmmobile.mcp.phone.constant;

/**
 * @Class Name : PhoneConstant
 * @Description : 핸드폰상품관리 상수 클래스
 *
 * @author : ant
 * @Create Date : 2016. 1. 6.
 */
public final class PhoneConstant {
    /** 핸드폰 상품 업로드 경로  */
    public static final String PHONE_DIRECTORY = "phone";

    /** MSP 판매정책 조회코드값 plcyTypeCd => 온라인(직영):D ,오프라인(도매):W , 특수:S ,제휴 :A */
    public static final String ONLINE_FOR_MSP = "D";

    /** MSP 판매정책 조회코드값 plcyTypeCd => 온라인(직영):D ,오프라인(도매):W , 특수:S ,제휴 :A */
    public static final String OFFLINE_FOR_MSP = "W";

    /** MSP 판매정책 조회코드값 plcyTypeCd => 렌탈  :D ,오프라인(도매):W , 특수:S ,제휴 :A ,렌탈 :R */
    public static final String RENTAL_FOR_MSP = "R";

    /** MSP 판매정책 조회코드값 prdtSctnCd(LTE) => LTE,3G,5G,LTE5G */
    public static final String LTE_FOR_MSP = "LTE";

    /** MSP 판매정책 조회코드값 prdtSctnCd(3G) => LTE,3G,5G,LTE5G */
    public static final String THREE_G_FOR_MSP = "3G";

    /** MSP 판매정책 조회코드값 prdtSctnCd(5G) => LTE,3G,5G,LTE5G */
    public static final String FIVE_G_FOR_MSP = "5G";

    /** MSP 판매정책 조회코드값 prdtSctnCd(LTE5G) => LTE,3G,5G,LTE5G */
    public static final String LTEFIVE_G_FOR_MSP = "LTE5G";

    public static final String NFC_FOR_MSP = "NFC";

    /** MSP 판매정책조회 코드값  plcySctnCd(01) => 단말:01,유심:02*/
    public static final String PHONE_FOR_MSP = "01";

    /** MSP 판매정책조회 코드값  plcySctnCd(02) => 단말:01,유심:02*/
    public static final String USIM_FOR_MSP	= "02";

    /** MSP 판매정책조회 코드값  sprtTp(KD) => 단말할인:KD,요금할인:PM*/
    public static final String PHONE_DISCOUNT_FOR_MSP = "KD";

    /** MSP 판매정책조회 코드값  sprtTp(PM) => 단말할인:KD,요금할인:PM*/
    public static final String CHARGE_DISCOUNT_FOR_MSP = "PM";

    /** SM 심플할인*/
    public static final String SIMPLE_USIM_DISCOUNT_FOR_MSP = "SM";

    /** 단품색상속성그룹코드  */
    public static final String ATTR_COLOR_CODE = "P0";

    /** 단품메모리속성그룹코드  */
    public static final String ATTR_MEMORY_CODE = "P1";

    /** PROD_CTG_ID LTE:04,3G:03*/
    public static final String PROD_LTE_CD = "04";

    /** PROD_CTG_ID LTE:04,3G:03*/
    public static final String PROD_3G_CD = "03";

    /** PROD_CTG_ID LTE:04,3G:03*/
    public static final String PROD_5G_CD = "08";

    /** PROD_CTG_ID LTE:04,3G:03,5G:08,LTE5G:45*/
    public static final String PROD_LTE5G_CD = "45";

    /** 신규가입 MSP 코드 */
    public static final String OPER_NEW = "NAC3";

    /** 번호이동 MSP 코드 */
    public static final String OPER_PHONE_NUMBER_TRANS = "MNP3";

    /** 기기변경 MSP 코드 */
    public static final String OPER_CHANGE_DEVICE = "HCN3";

    /** 선후불코드 선불(PP) */
    public static final String PRE_PAY_CL_CD		  = "PP";

    /** 선후불코드 후불(PO) */
    public static final String DEF_PAY_CL_CD		  = "PO";

    /** 핸드폰 상품 배너 카테고리 코드 */
    public static final String BANNER_CATG_CD		  = "07";

    /** 모바일 > 중고폰 배너 카테고리 코드 */
    public static final String BANNER_CATG_CD_USED_PHONE         = "60";

    /** 모바일 > 중고폰 배너 카테고리 코드 */
    public static final String BANNER_CATG_CD_USED_PHONE_MOBILE         = "61";

    /** 유심 > 선불유심 배너 카테고리 코드 */
    public static final String BANNER_CATG_CD_USIM_PRE         = "62";

    /** 유심 > 선불유심 배너 카테고리 코드 */
    public static final String BANNER_CATG_CD_USIM_PRE_MOBILE         = "63";

    /** 중고폰분류값(중고폰) (중고폰:01,외산폰:02) */
    public static final String SHAND_TYPE_OLD_PHON		  = "01";

    /** 중고폰분류값(외산폰) (중고폰:01,외산폰:02) */
    public static final String SHAND_TYPE_FOREIGN_PHON		  = "02";

    /** 상품 분류 일반 (일반 :01, 0원 상품 :02 직구:03)' */
    public static final String PROD_TYPE_COMMON		  = "01";

    /** 상품 분류 0원렌탈 상품 (일반 :01, 0원 상품 :02 직구:03)' */
    public static final String PROD_TYPE_RENTAL		  = "02";

    /** 상품 분류 직구 (일반 :01, 0원 상품 :02 직구:03)'phoneDirect */
    public static final String PROD_TYPE_DIRECT      = "03";

    /** 상품 분류 직구 (일반 :01, 0원 상품 :02 직구:03 phoneDirect  중고폰 :04 */
    public static final String PROD_TYPE_USED      = "04";

    /** 모바일 > 중고폰 배너 카테고리 코드 */
    public static final String BANNER_CATG_CD_PHONE_MOBILE         = "68";


    /** no instance */
    private PhoneConstant() {

    }
}
