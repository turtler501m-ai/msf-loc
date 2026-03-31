# 스마트서식지 — 인터페이스 설계서 (내부 API)

**문서번호** : 스마트서식지-DS-06  
**문서버전** : v1.0  
**작성일자** : 2026.00.00  
**작성자** : 홍길동

---

## 승인 이력

| 수행 역할 | 소속 | 이름 | 승인일 | 서명 |
|---|---|---|---|---|
| 작성자 | kt ds | 홍길동 | 2026.00.00 | 홍길동 |
| 검토자 | kt ds | 홍길동 | 2026.00.00 | 홍길동 |
| 프로젝트관리자(PM) | kt ds | 홍길동 | 2026.00.00 | 홍길동 |
| 사업총괄 PM | 고객사 | 홍길동 | 2026.00.00 | 홍길동 |

---

## 개정 이력

| 날짜 | 버전 | 변경내용 | 작성자/검토자 |
|---|---|---|---|
| 2026.00.00 | v0.6 | 최초 작성 | 홍길동 |
| 2026.00.00 | v0.7 | 내부 동료 검토 완료 | 홍길동 |
| 2026.00.00 | v0.8 | QA 검토 완료 | 홍길동 |
| 2026.00.00 | v0.9 | PM 검토 완료 | 홍길동 |
| 2026.00.00 | v1.0 | 고객 검토 완료 | 홍길동 |

---

## 참고 — 연동 서버 정보

| 서버 | IP | Zone | 서비스 | 서비스 설명 | 연동 IP | 연동 시스템 |
|---|---|---|---|---|---|---|
| PRX WAS | 128.134.37.215 | DMZ | Self Care | 고객계약정보 조회 및 처리 | 10.220.172.141:7006 | MVNOAP L4 |
| | | | 스마트개통(KOS) | 스마트서식지 (KOS UI 전송) | 203.229.169.123:8810 | KOS GW L4 |
| | | | 간편개통(KOS) | 즉시개통 | 10.220.172.141:7006 | MVNOAP L4 |
| | | | 주소로 | 주소검색 | http://update.juso.go.kr/updateInfo.do | — |
| | | | 이니시스결제 | 이니시스 결제 | — | — |
| | | | 다우페이결제 | 신용카드, 선불 결제 | — | — |
| | | | 가상계좌연동 | 쿠콘 가상계좌 서비스 | 128.134.37.215:39114 | — |
| | | | Cashgate연동(편의점) | 갤럭시아컴즈 | 222.122.229.179:30321 | — |
| | | | 자동이체연동(PAYINFO) | 효성FMS | 121.134.74.90(24000) / 106.250.169.67(25000) | — |

---

## 1. 인터페이스 목록

**사업명** : 스마트서식지 | **단계** : 설계 | **총 API 수** : 152개

| No | 연동 ID | 연동명(영문) | 연동명(한글) | 송/수신 | 주기 | 엔드포인트 | Mapper | SQL ID | parameterType | resultType | 사용 | SQL 유형 | 대상 테이블 | 담당자 |
|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|
| 1 | API_0001 | selectUsrRateInfo | NCN 요금제 정보 조회 | 양방향 | 수시 | `/app/usrRateInfo` | AppMapper.xml | selectUsrRateInfo | java.lang.String | java.util.Map | O |  | MSP_JUO_FEATURE_INFO, MSP_RATE_MST | 강채신 |
| 2 | API_0002 | checkJejuCodeCount | 요금제 여부 확인 | 양방향 | 수시 | `/appform/checkJejuCodeCount` | AppformMapper.xml | checkJejuCodeCount | java.lang.String | java.lang.Integer | O |  | CMN_GRP_CD_MST | 강채신 |
| 3 | API_0003 | insertMcpRequestAdditionPromotion | 부가서비스 프로모션 등록 | 양방향 | 수시 | `/appform/mcpRequestAdditionPromotion` | AppformMapper.xml | insertMcpRequestAdditionPromotion | com.ktmmobile.mcp.appform.dto.AppformReqDto | java.lang.Integer | O |  | VW_CHRG_PRMT_DTL | 강채신 |
| 4 | API_0004 | getMspPrdtCode | 서식지 관련 MSP 코드 조회 | 양방향 | 수시 | `/appform/mspPrdtCode` | AppformMapper.xml | getMspPrdtCode | com.ktmmobile.mcp.appform.dto.AppformReqDto | com.ktmmobile.mcp.appform.dto.McpRequestDto | O |  | CMN_INTM_MDL | 강채신 |
| 5 | API_0005 | getRequestJoinDataByRjoinKeyNew | 요금조회 | 양방향 | 수시 | `/appform/requestJoinDataByRjoinKeyNew` | AppformMapper.xml | getRequestJoinDataByRjoinKeyNew | java.lang.Long | java.util.Map | O |  | CMN_GRP_CD_MST, MSP_RATE_MST, ORG_ORGN_INFO_MST, MSP_SALE_PL | 강채신 |
| 6 | API_0006 | selRMemberAjax | 기기변경 고객정보 확인 일반기변 , 우수기변 | 양방향 | 수시 | `/appform/selRMemberAjax` | AppformMapper.xml | selRMemberAjax | com.ktmmobile.mcp.appform.dto.JuoSubInfoDto | com.ktmmobile.mcp.appform.dto.JuoSubInfoDto | O |  | MSP_JUO_ADD_INFO, MSP_JUO_SUB_INFO, MSP_JUO_CUS_INFO, MSP_DV | 강채신 |
| 7 | API_0007 | selRMemberAjaxReal | 기기변경 고객정보 확인 일반기변 , 우수기변 | 양방향 | 수시 | `/appform/selRMemberAjaxReal` | AppformMapper.xml | selRMemberAjaxReal | com.ktmmobile.mcp.appform.dto.JuoSubInfoDto | com.ktmmobile.mcp.appform.dto.JuoSubInfoDto | O |  | MSP_JUO_ADD_INFO, MSP_JUO_SUB_INFO, MSP_JUO_CUS_INFO, MSP_DV | 강채신 |
| 8 | API_0008 | selPrdtcolCd | 상품 색상 정보 | 양방향 | 수시 | `/appform/selPrdtcolCd` | AppformMapper.xml | selPrdtcolCd | com.ktmmobile.mcp.appform.dto.AppformReqDto | java.lang.String | O |  | CMN_INTM_MDL | 강채신 |
| 9 | API_0009 | getMarketRequest | 오픈마켓 (외부서식지) | 양방향 | 수시 | `/appform/marketRequest` | AppformMapper.xml | getMarketRequest | com.ktmmobile.mcp.appform.dto.AppformReqDto | com.ktmmobile.mcp.appform.dto.AppformReqDto | O |  | ORG_ORGN_INFO_MST, MSP_SALE_ORGN_MST, MSP_SALE_PLCY_MST, MSP | 강채신 |
| 10 | API_0010 | selectModelMonthlyList | 할부개월 | 양방향 | 수시 | `/appform/modelMonthlyList` | AppformMapper.xml | selectModelMonthlyList | com.ktmmobile.mcp.appform.dto.AppformReqDto | com.ktmmobile.mcp.appform.dto.AppformReqDto | O |  | ORG_INST_NOM_MST | 강채신 |
| 11 | API_0011 | selectMonthlyListMarket | 약정 | 양방향 | 수시 | `/appform/monthlyListMarket` | AppformMapper.xml | selectMonthlyListMarket | com.ktmmobile.mcp.appform.dto.AppformReqDto | com.ktmmobile.mcp.appform.dto.AppformReqDto | O |  | MSP_SALE_AGRM_MST | 강채신 |
| 12 | API_0012 | selectPrdtColorList | 색상정보 | 양방향 | 수시 | `/appform/prdtColorList` | AppformMapper.xml | selectPrdtColorList | com.ktmmobile.mcp.appform.dto.AppformReqDto | com.ktmmobile.mcp.appform.dto.AppformReqDto | O |  | CMN_INTM_MDL, CMN_GRP_CD_MST | 강채신 |
| 13 | API_0013 | getAgentCode | 대리점 코드 패치 | 양방향 | 수시 | `/appform/agentCode` | AppformMapper.xml | getAgentCode | java.lang.String | java.lang.String | O |  | ORG_ORGN_INFO_MST | 강채신 |
| 14 | API_0014 | selectInsrCodeList | 보험코드 정보 | 양방향 | 수시 | `/appform/insrCodeList` | AppformMapper.xml | selectInsrCodeList |  | com.ktmmobile.mcp.appform.dto.AppformReqDto | O |  | CMN_GRP_CD_MST, MSP_GRP_INSR_MST | 강채신 |
| 15 | API_0015 | getInsrProdList | 분실파손 보험 조회 | 양방향 | 수시 | `/appform/insrProdList` | AppformMapper.xml | getInsrProdList | com.ktmmobile.mcp.appform.dto.IntmInsrRelDTO | com.ktmmobile.mcp.appform.dto.IntmInsrRelDTO | O |  | MSP_INTM_INSR_MST, MSP_INTM_INSR_REL | 강채신 |
| 16 | API_0016 | getLimitForm | 고객CI정보에 대한 개통  정보 추출 [다회선 제한 기 | 양방향 | 수시 | `/appform/limitForm` | AppformMapper.xml | getLimitForm | com.ktmmobile.mcp.appform.dto.AppformReqDto | com.ktmmobile.mcp.appform.dto.AppformReqDto | O |  | MSP_JUO_SUB_INFO, MSP_REQUEST_DTL | 강채신 |
| 17 | API_0017 | selectContentBanner | 컨텐츠 이미지 배너 선택 | 양방향 | 수시 | `/banner/contentBanner` | BannerMapper.xml | selectContentBanner | com.ktmmobile.mcp.banner.dto.BannerDto | com.ktmmobile.mcp.banner.dto.BannerDto | O |  | MSP_SALE_AGRM_MST, MSP_RATE_MST, MSP_SALE_RATE_MST | 강채신 |
| 18 | API_0018 | selectBannerDtl | 배너 하위 관리 선택 | 양방향 | 수시 | `/banner/bannerDtl` | BannerMapper.xml | selectBannerDtl | com.ktmmobile.mcp.banner.dto.BannerDto | com.ktmmobile.mcp.banner.dto.BannerDto | O |  | MSP_SALE_AGRM_MST, MSP_RATE_MST, MSP_SALE_RATE_MST | 강채신 |
| 19 | API_0019 | listBannerBySubCtg | 조건에 해당하는 배너 리스트를 가져온다. (메인용) | 양방향 | 수시 | `/banner/listBannerBySubCtg` | BannerMapper.xml | listBannerBySubCtg | com.ktmmobile.mcp.banner.dto.BannerDto | com.ktmmobile.mcp.banner.dto.BannerDto | O |  | MSP_RATE_MST, MSP_SALE_RATE_MST, CMN_GRP_CD_MST | 강채신 |
| 20 | API_0020 | getMspRateMst |  | 양방향 | 수시 | `/common/mspRateMst` | CommonMapper.xml | getMspRateMst | java.lang.String | com.ktmmobile.mcp.common.dto.MspRateMstDto | O |  | MSP_RATE_MST | 강채신 |
| 21 | API_0021 | insertmspCommDatPrvTxn |  | 양방향 | 수시 | `/common/mspCommDatPrvTxn` | CommonMapper.xml | insertmspCommDatPrvTxn | com.ktmmobile.mcp.common.dto.MspCommDatPrvTxnDto | java.lang.Integer | O |  | SQ_COMM_DAT_PRV_SEQ.NEXTVAL, MSP_COMM_DAT_PRV_TXN | 강채신 |
| 22 | API_0022 | getMspSmsTemplateMst |  | 양방향 | 수시 | `/common/mspSmsTemplateMst` | CommonMapper.xml | getMspSmsTemplateMst | java.lang.Integer | com.ktmmobile.mcp.common.dto.MspSmsTemplateMstDto | O |  | MSP_SMS_TEMPLATE_MST | 강채신 |
| 23 | API_0023 | inquiryInsertByProcedure |  | 양방향 | 수시 | `` |  |  |  |  | X |  | P_MSP_VOC_LINK_REG | 강채신 |
| 24 | API_0024 | findMspPhoneInfo | 단품모델ID로 핸드폰정보를 조회 | 양방향 | 수시 | `/msp/mspPhoneInfo` | MspMapper.xml | findMspPhoneInfo | java.lang.String | com.ktmmobile.mcp.phone.dto.PhoneMspDto | O |  | CMN_INTM_MDL | 김대원 |
| 25 | API_0025 | findMspSalePlcyInfoByOnlyOrgn | 판매정책정보를 조회한다.상품과 상관없이 기관별 조회 | 양방향 | 수시 | `/msp/mspSalePlcyInfoByOnlyOrgn` | MspMapper.xml | findMspSalePlcyInfoByOnlyOrgn | com.ktmmobile.mcp.mspservice.dto.MspSalePlcyMstDto | com.ktmmobile.mcp.mspservice.dto.MspSalePlcyMstDto | O |  | MSP_SALE_PLCY_MST, MSP_SALE_ORGN_MST | 김대원 |
| 26 | API_0026 | findMspSaleOrgnMst | 판매정책정보를 조회 | 양방향 | 수시 | `/msp/mspSaleOrgnMst` | MspMapper.xml | findMspSaleOrgnMst | com.ktmmobile.mcp.mspservice.dto.MspSalePlcyMstDto | com.ktmmobile.mcp.mspservice.dto.MspSalePlcyMstDto | O |  | MSP_SALE_PLCY_MST, MSP_SALE_ORGN_MST, MSP_SALE_PRDT_MST | 김대원 |
| 27 | API_0027 | findMspSalePrdMst | 판매중인 상품정보를 조회 | 양방향 | 수시 | `/msp/mspSalePrdMst` | MspMapper.xml | findMspSalePrdMst | com.ktmmobile.mcp.mspservice.dto.MspSalePrdtMstDto | com.ktmmobile.mcp.mspservice.dto.MspSalePrdtMstDto | O |  | MSP_SALE_PRDT_MST | 김대원 |
| 28 | API_0028 | listOrgnMnfctMst | MSP 에서 제조사/공급사 정보를 조회 | 양방향 | 수시 | `/msp/orgnMnfctMst` | MspMapper.xml | listOrgnMnfctMst | com.ktmmobile.mcp.mspservice.dto.MspOrgDto | com.ktmmobile.mcp.mspservice.dto.MspOrgDto | O |  | ORG_MNFCT_MST | 김대원 |
| 29 | API_0029 | listOrgnMnfctMstRe | MSP 에서 제조사/공급사 정보를 조회 | 양방향 | 수시 | `/msp/orgnMnfctMstRe` | MspMapper.xml | listOrgnMnfctMstRe |  | com.ktmmobile.mcp.mspservice.dto.MspOrgDto | O |  | ORG_MNFCT_MST | 김대원 |
| 30 | API_0030 | listMspRateMst | 해당정책코드에 해당하는 판매요금제 정보 리스트 조회 | 양방향 | 수시 | `/msp/mspRateMsp` | MspMapper.xml | listMspRateMst | com.ktmmobile.mcp.mspservice.dto.MspRateMstDto | com.ktmmobile.mcp.mspservice.dto.MspRateMstDto | O |  | MSP_RATE_MST, MSP_SALE_RATE_MST, CMN_GRP_CD_MST | 김대원 |
| 31 | API_0031 | listMspSaleAgrmMst | 해당정책코드에 해당하는 약정개월정보 리스트 조회 | 양방향 | 수시 | `/msp/mspSaleAgrmMst` | MspMapper.xml | listMspSaleAgrmMst | string | com.ktmmobile.mcp.mspservice.dto.MspSaleAgrmMst | O |  | MSP_SALE_AGRM_MST, ORG_INST_NOM_MST | 김대원 |
| 32 | API_0032 | withMspSalePlcyMst |  | 양방향 | 수시 | `` |  |  |  |  | X |  | MSP_SALE_PLCY_MST | 김대원 |
| 33 | API_0033 | findMspSaleSubsdMst | 상품의 판매금액관련 상세내역을 조회 | 양방향 | 수시 | `/msp/mspSaleSubsdMst` | MspMapper.xml | findMspSaleSubsdMst | com.ktmmobile.mcp.mspservice.dto.MspSaleSubsdMstDto | com.ktmmobile.mcp.mspservice.dto.MspSaleSubsdMstDto | O |  | MSP_SALE_SUBSD_MST | 김대원 |
| 34 | API_0034 | getMspSalePlcyMst |  | 양방향 | 수시 | `/msp/mspSalePlcyMst` | MspMapper.xml | getMspSalePlcyMst | java.lang.String | com.ktmmobile.mcp.mspservice.dto.MspSalePlcyMstDto | O |  | MSP_SALE_PLCY_MST | 김대원 |
| 35 | API_0035 | getromotionDcAmt |  | 양방향 | 수시 | `` |  |  |  |  | X |  | VW_CHRG_PRMT_DTL | 김대원 |
| 36 | API_0036 | getLowPriceChargeInfoByProd | 최저가를 구하기 위한 해당 상품의 요금제 정보 1건 조 | 양방향 | 수시 | `/msp/lowPriceChargeInfoByProd` | MspMapper.xml | getLowPriceChargeInfoByProd | com.ktmmobile.mcp.mspservice.dto.MspSaleSubsdMstDto | com.ktmmobile.mcp.mspservice.dto.MspSaleSubsdMstDto | O |  | MSP_SALE_SUBSD_MST, MSP_RATE_MST | 김대원 |
| 37 | API_0037 | selectMspSaleSubsdMstListWithRateInfo | 최저가를 구하기 위한 해당 상품의 요금제 정보 리스트를 | 양방향 | 수시 | `/msp/mspSaleSubsdMstList` | MspMapper.xml | selectMspSaleSubsdMstListWithRateInfo | com.ktmmobile.mcp.mspservice.dto.MspSaleSubsdMstDto | com.ktmmobile.mcp.mspservice.dto.MspSaleSubsdMstDto | O |  | MSP_SALE_SUBSD_MST, MSP_RATE_MST | 김대원 |
| 38 | API_0038 | selectMspSaleSubsdMstListForLowPrice | 최저가를 구하기 위한 해당 상품의 요금제 정보 리스트를 | 양방향 | 수시 | `/msp/mspSaleSubsdMstList` | MspMapper.xml | selectMspSaleSubsdMstListForLowPrice | com.ktmmobile.mcp.mspservice.dto.MspSaleSubsdMstDto | com.ktmmobile.mcp.mspservice.dto.MspSaleSubsdMstDto | O |  | MSP_SALE_SUBSD_MST | 김대원 |
| 39 | API_0039 | findCmnGrpCdMst |  | 양방향 | 수시 | `/msp/cmnGrpCdMst` | MspMapper.xml | findCmnGrpCdMst | com.ktmmobile.mcp.mspservice.dto.CmnGrpCdMst | com.ktmmobile.mcp.mspservice.dto.CmnGrpCdMst | O |  | CMN_GRP_CD_MST | 김대원 |
| 40 | API_0040 | listRateByOrgnInfos |  | 양방향 | 수시 | `/msp/rateByOrgnInfos` | MspMapper.xml | listRateByOrgnInfos | com.ktmmobile.mcp.mspservice.dto.MspSalePlcyMstDto | com.ktmmobile.mcp.mspservice.dto.MspRateMstDto | O |  | MSP_SALE_ORGN_MST, MSP_SALE_PLCY_MST, MSP_SALE_RATE_MST, MSP | 김대원 |
| 41 | API_0041 | listRateBySalePlcyCd | 정책코드로 해당정책에 속해있는 요금제 리스트 조회 | 양방향 | 수시 | `/msp/mspRateMspBySalePlcyCd` | MspMapper.xml | listRateBySalePlcyCd | com.ktmmobile.mcp.mspservice.dto.MspRateMstDto | com.ktmmobile.mcp.mspservice.dto.MspRateMstDto | O |  | MSP_SALE_PLCY_MST, MSP_SALE_RATE_MST, MSP_RATE_MST | 김대원 |
| 42 | API_0042 | findMspSalePlcyMstBySalePlcyCd |  | 양방향 | 수시 | `/msp/mspSalePlcyInfo` | MspMapper.xml | findMspSalePlcyMstBySalePlcyCd | java.lang.String | com.ktmmobile.mcp.mspservice.dto.MspSalePlcyMstDto | O |  | MSP_SALE_PLCY_MST | 김대원 |
| 43 | API_0043 | listMspSalePrdtMstBySalePlcyCd | 정책에 해당하는 상품의 상세정보 조회 | 양방향 | 수시 | `/msp/mspSalePrdtMstBySalePlcyCd` | MspMapper.xml | listMspSalePrdtMstBySalePlcyCd | java.lang.String | com.ktmmobile.mcp.mspservice.dto.MspSalePrdtMstDto | O |  | MSP_SALE_PRDT_MST, CMN_INTM_MDL | 김대원 |
| 44 | API_0044 | listMspOfficialSupportRateNm | 요금제 목록 조회 | 양방향 | 수시 | `/msp/mspOfficialSupportRateNm` | MspMapper.xml | listMspOfficialSupportRateNm |  | com.ktmmobile.mcp.mspservice.dto.MspNoticSupportMstDto | O |  | MSP_OFCL_SUBSD_MST, MSP_RATE_MST | 김대원 |
| 45 | API_0045 | listMspOfficialNoticeSupport | 공시지원금 목록 조회 | 양방향 | 수시 | `/msp/mspOfficialNoticeSupport` | MspMapper.xml | listMspOfficialNoticeSupport | com.ktmmobile.mcp.mspservice.dto.MspNoticSupportMstDto | com.ktmmobile.mcp.mspservice.dto.MspNoticSupportMstDto | O |  | MSP_RATE_MST, CMN_INTM_MDL, MSP_OFCL_SUBSD_MST, CMN_HNDST_AM | 김대원 |
| 46 | API_0046 | listMspOfficialNoticeSupportCount |  | 양방향 | 수시 | `/msp/mspOfficialNoticeSupportCount` | MspMapper.xml | listMspOfficialNoticeSupportCount | com.ktmmobile.mcp.mspservice.dto.MspNoticSupportMstDto | Integer | O |  | MSP_RATE_MST, CMN_INTM_MDL, MSP_OFCL_SUBSD_MST, CMN_HNDST_AM | 김대원 |
| 47 | API_0047 | getCustomerSsn | 계약번호의 주민번호 조회 | 양방향 | 수시 | `/msp/customerSsn` | MspMapper.xml | getCustomerSsn | java.lang.String | java.lang.String | O |  | MSP_JUO_CUS_INFO, MSP_JUO_SUB_INFO | 김대원 |
| 48 | API_0048 | sellUsimMgmtCount |  | 양방향 | 수시 | `/msp/sellUsimMgmtCount` | MspMapper.xml | sellUsimMgmtCount | java.lang.String | Integer | O |  | MSP_PARTNER_USIM_MST | 김대원 |
| 49 | API_0049 | getMspRateMst | 요금제 정보 리스트 조회 | 양방향 | 수시 | `/msp/mspRateMst` | MspMapper.xml | getMspRateMst | string | com.ktmmobile.mcp.mspservice.dto.MspRateMstDto | O |  | MSP_RATE_MST | 김대원 |
| 50 | API_0050 | listMspSaleMst | 할인금액을 조회 | 양방향 | 수시 | `/msp/mspSaleMst` | MspMapper.xml | listMspSaleMst | com.ktmmobile.mcp.mspservice.dto.MspSaleSubsdMstDto | com.ktmmobile.mcp.mspservice.dto.MspSaleSubsdMstDto | O |  | MSP_RATE_SPEC, MSP_RATE_MST | 김대원 |
| 51 | API_0051 | juoSubIngoCount |  | 양방향 | 수시 | `/msp/juoSubIngoCount` | MspMapper.xml | juoSubIngoCount | java.lang.String | Integer | O |  | MSP_JUO_SUB_INFO | 김대원 |
| 52 | API_0052 | selectCntrList |  | 양방향 | 수시 | `/mypage/cntrList` | MypageMapper.xml | selectCntrList | java.lang.String | com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto | O |  | MSP_JUO_SUB_INFO | 강문재 |
| 53 | API_0053 | selectSocDesc |  | 양방향 | 수시 | `/mypage/socDesc` | MypageMapper.xml | selectSocDesc | java.lang.String | com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto | O |  | MSP_JUO_ADD_INFO, MSP_JUO_FEATURE_INFO, MSP_RATE_MST, MSP_JU | 강문재 |
| 54 | API_0054 | selectMspAddInfo |  | 양방향 | 수시 | `/mypage/mspAddInfo` | MypageMapper.xml | selectMspAddInfo | java.lang.String | com.ktmmobile.mcp.mypage.dto.MspJuoAddInfoDto | O |  | MSP_JUO_ADD_INFO | 강문재 |
| 55 | API_0055 | selectUserMultiLine |  | 양방향 | 수시 | `/mypage/userMultiLine` | MypageMapper.xml | selectUserMultiLine | java.lang.String | com.ktmmobile.mcp.common.mplatform.vo.UserVO | O |  | MSP_JUO_SUB_INFO | 강문재 |
| 56 | API_0056 | selectContractNum |  | 양방향 | 수시 | `/mypage/contractNum` | MypageMapper.xml | selectContractNum | java.util.Map | java.lang.String | O | 파라미터를 Map으로 넘김 | MSP_JUO_SUB_INFO | 강문재 |
| 57 | API_0057 | selectPPSList |  | 양방향 | 수시 | `/mypage/ppsList` | MypageMapper.xml | selectPPSList | java.lang.String | com.ktmmobile.mcp.common.dto.db.MspPpsRcgTesDto | O |  | PPS_RCG_TES | 강문재 |
| 58 | API_0058 | selectRegService |  | 양방향 | 수시 | `/mypage/regService` | MypageMapper.xml | selectRegService | java.lang.String | com.ktmmobile.mcp.mypage.dto.McpRegServiceDto | O |  | MSP_RATE_MST, MSP_PROD_REL_MST, MSP_JUO_FEATURE_INFO | 강문재 |
| 59 | API_0059 | selectFarPricePlan |  | 양방향 | 수시 | `/mypage/farPricePlan` | MypageMapper.xml | selectFarPricePlan | java.lang.String | com.ktmmobile.mcp.mypage.dto.McpFarPriceDto | O |  | MSP_RATE_MST, MSP_JUO_FEATURE_INFO | 강문재 |
| 60 | API_0060 | selectFarPricePlanList |  | 양방향 | 수시 | `/mypage/farPricePlanList` | MypageMapper.xml | selectFarPricePlanList |  | com.ktmmobile.mcp.mypage.dto.McpFarPriceDto | O | 다중파라미터 사용
mapper > RowBounds 클래스 사용 | VW_GROUP_BY_RATE_INFO | 강문재 |
| 61 | API_0061 | countFarPricePlanList |  | 양방향 | 수시 | `/mypage/farPricePlanListCount` | MypageMapper.xml | countFarPricePlanList | java.lang.String | int | O | SQL ID 변경 | VW_GROUP_BY_RATE_INFO | 강문재 |
| 62 | API_0062 | selectFarPriceAddInfo |  | 양방향 | 수시 | `/mypage/farPriceAddInfo` | MypageMapper.xml | selectFarPriceAddInfo | java.util.Map | java.lang.String | O |  | MSP_JUO_FEATURE_INFO | 강문재 |
| 63 | API_0063 | selectJehuList |  | 양방향 | 수시 | `/mypage/jehuList` | MypageMapper.xml | selectJehuList | java.util.Map | com.ktmmobile.mcp.mypage.dto.JehuDto | O | 다중파라미터 사용
mapper > RowBounds 클래스 사용 | CMN_GRP_CD_MST, MSP_PARTNER_POINT_MST, MSP_PARTNER_MST | 강문재 |
| 64 | API_0064 | selectJehuListCnt |  | 양방향 | 수시 | `/mypage/jehuListCount` | MypageMapper.xml | selectJehuListCnt | java.util.Map | int | O | SQL ID 변경 | MSP_PARTNER_POINT_MST, MSP_PARTNER_MST | 강문재 |
| 65 | API_0065 | selectJehuListAll |  | 양방향 | 수시 | `/mypage/jehuListAll` | MypageMapper.xml | selectJehuListAll | java.util.Map | com.ktmmobile.mcp.mypage.dto.JehuDto | O |  | CMN_GRP_CD_MST, MSP_PARTNER_POINT_MST, MSP_PARTNER_MST | 강문재 |
| 66 | API_0066 | selectMspMrktAgrYn | 회선별 마케팅 수신 동의 여부 조회 | 양방향 | 수시 | `/mypage/mspMrktAgrYn` | MypageMapper.xml | selectMspMrktAgrYn | java.lang.String | java.util.Map | O |  | MSP_MRKT_AGR_MGMT | 강문재 |
| 67 | API_0067 | callMspMrktAgr | 회선별 마케팅 수신 동의 여부 저장 | 양방향 | 수시 | `/mypage/callMspMrktAgrYn` | MypageMapper.xml | callMspMrktAgr | java.util.HashMap |  | O | 다중파라미터 사용
mapper 리턴이 void인 경우 | CALL P_MSP_MRKT_AGR | 강문재 |
| 68 | API_0068 | selectPrePayment | 선불 요금제 사용 여부 조회 | 양방향 | 수시 | `/mypage/prePayment` | MypageMapper.xml | selectPrePayment | java.lang.String | int | O |  | MSP_JUO_FEATURE_INFO, MSP_RATE_MST | 강문재 |
| 69 | API_0069 | getCommendStateList |  | 양방향 | 수시 | `/mypage/commendStateList` | MypageMapper.xml | getCommendStateList | java.lang.String | com.ktmmobile.mcp.mypage.dto.CommendStateDto | O | SQL ID 변경 | MSP_JUO_SUB_INFO | 강문재 |
| 70 | API_0070 | selectSvcCntrNo |  | 양방향 | 수시 | `/mypage/svcCntrNo` | MypageMapper.xml | selectSvcCntrNo | java.util.Map | java.lang.String | O | 다중파라미터 사용 | MSP_JUO_SUB_INFO | 강문재 |
| 71 | API_0071 | selectSocDescNoLogin |  | 양방향 | 수시 | `/mypage/socDescNoLogin` | MypageMapper.xml | selectSocDescNoLogin |  |  | O |  | MSP_JUO_ADD_INFO, MSP_JUO_FEATURE_INFO, MSP_RATE_MST, MSP_JU | 강문재 |
| 72 | API_0072 | selectCntrListNoLogin | 계약 현행화 정보 | 양방향 | 수시 | `/mypage/cntrListNoLogin` | MypageMapper.xml | selectCntrListNoLogin | com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto |  | O | 파라미터를 DTO로 넘김 | MSP_JUO_SUB_INFO | 강문재 |
| 73 | API_0073 | getJuoFeature | 부가 서비스 존재 여부(3개월 이내 결합이력이 존재하여 | 양방향 | 수시 | `/mypage/juoFeature` | MypageMapper.xml | getJuoFeature | com.ktmmobile.mcp.mypage.dto.JuoFeatureDto |  | O | 파라미터를 DTO로 넘김
SQL ID 변경 | MSP_JUO_FEATURE_INFO | 강문재 |
| 74 | API_0074 | selectOrderList |  | 양방향 | 수시 | `/order/orderList` | OrderMapper.xml | selectOrderList | com.ktmmobile.mcp.order.dto.OrderDto | com.ktmmobile.mcp.order.dto.OrderDto | O |  | MSP_RATE_MST | 강채신 |
| 75 | API_0075 | selectOrderView |  | 양방향 | 수시 | `/order/orderView` | OrderMapper.xml | selectOrderView | com.ktmmobile.mcp.order.dto.OrderDto | com.ktmmobile.mcp.order.dto.OrderDto | O |  | MSP_RATE_MST, MSP_SALE_SUBSD_MST, CMN_GRP_CD_MST, MSP_SALE_P | 강채신 |
| 76 | API_0076 | getSeq | 페이인포 시퀀스 채번 | 양방향 | 수시 | `/payinfo/seq` | PayInfoMapper.xml | getSeq |  | java.lang.String | O |  | SQ_MSP_PAYINFO_01.NEXTVAL | 강채신 |
| 77 | API_0077 | selectMspJuoSubinfo | EvidenceDto | 양방향 | 수시 | `/payinfo/mspJuoSubinfo` | PayInfoMapper.xml | selectMspJuoSubinfo | com.ktmmobile.mcp.payinfo.dto.PayInfoDto | com.ktmmobile.mcp.payinfo.dto.EvidenceDto | O |  | MSP_JUO_SUB_INFO | 강채신 |
| 78 | API_0078 | updateEvidence |  | 양방향 | 수시 | `/payinfo/modifyEvidence` | PayInfoMapper.xml | updateEvidence | com.ktmmobile.mcp.payinfo.dto.EvidenceDto | java.lang.Integer | O |  | CMN_KFTC_EVIDENCE | 강채신 |
| 79 | API_0079 | checkEvidenceCount | 계약번호에 존재여부 확인 CMN_KFTC_EVIDENC | 양방향 | 수시 | `/payinfo/checkEvidenceCount` | PayInfoMapper.xml | checkEvidenceCount | com.ktmmobile.mcp.payinfo.dto.EvidenceDto | java.lang.Integer | O |  | CMN_KFTC_EVIDENCE | 강채신 |
| 80 | API_0080 | insetEvidence | CMN_KFTC_EVIDENCE 테이블 데이터 등록 | 양방향 | 수시 | `/payinfo/addEvidence` | PayInfoMapper.xml | insetEvidence | com.ktmmobile.mcp.payinfo.dto.EvidenceDto | java.lang.Integer | O |  | CMN_KFTC_EVIDENCE_SEQ.NEXTVAL, CMN_KFTC_EVIDENCE | 강채신 |
| 81 | API_0081 | callMspPayinfoImg |  | 양방향 | 수시 | `/payinfo/mspPayinfoImg` | PayInfoMapper.xml | callMspPayinfoImg | java.util.Map | java.util.Map | O |  | MSP_PAYINFO_IMG | 강채신 |
| 82 | API_0082 | listPhoneMspDto | 정책 판매중인 핸드폰 리스트 정보 조회 only msp | 양방향 | 수시 | `/phone/pHoneMspDto` | PhoneMapper.xml | listPhoneMspDto | com.ktmmobile.mcp.phone.dto.CommonSearchDto | com.ktmmobile.mcp.phone.dto.PhoneMspDto | O |  | CMN_INTM_MDL, ORG_MNFCT_MST | 김대원 |
| 83 | API_0083 | listPhoneProdsCount |  | 양방향 | 수시 | `/phone/phoneProdsCount` | PhoneMapper.xml | listPhoneProdsCount | com.ktmmobile.mcp.phone.dto.CommonSearchDto | java.lang.Integer | O |  | ORG_MNFCT_MST | 김대원 |
| 84 | API_0084 | listphoneProd | 핸드폰 상품관리 리스트 조회 | 양방향 | 수시 | `/phone/phoneProd` | PhoneMapper.xml | listphoneProd | com.ktmmobile.mcp.phone.dto.CommonSearchDto | com.ktmmobile.mcp.phone.dto.PhoneProdBasDto | O |  | ORG_MNFCT_MST | 김대원 |
| 85 | API_0085 | findNmcpProdBas | 핸드폰 상품관리 상세 조회 | 양방향 | 수시 | `/phone/nmcpProdBas` | PhoneMapper.xml | findNmcpProdBas | com.ktmmobile.mcp.phone.dto.PhoneProdBasDto | com.ktmmobile.mcp.phone.dto.PhoneProdBasDto | O |  | ORG_MNFCT_MST | 김대원 |
| 86 | API_0086 | listphoneProdForSort | 핸드폰 상품순서관리 | 양방향 | 수시 | `/phone/phoneProdForSort` | PhoneMapper.xml | listphoneProdForSort | com.ktmmobile.mcp.phone.dto.CommonSearchDto | com.ktmmobile.mcp.phone.dto.PhoneProdBasDto | O |  | ORG_MNFCT_MST | 김대원 |
| 87 | API_0087 | listPhoneProdBasForFront | 핸드폰 상품 리스트 프론트 | 양방향 | 수시 | `/phone/phoneProdBasForFront` | PhoneMapper.xml | listPhoneProdBasForFront | com.ktmmobile.mcp.phone.dto.CommonSearchDto | com.ktmmobile.mcp.phone.dto.PhoneProdBasDto | O |  | ORG_MNFCT_MST | 김대원 |
| 88 | API_0088 | listPhoneProdBasForFrontOneQuery | 상품리스트 조회 | 양방향 | 수시 | `/phone/phoneProdBasForFrontOneQuery` | PhoneMapper.xml | listPhoneProdBasForFrontOneQuery | com.ktmmobile.mcp.phone.dto.CommonSearchDto | com.ktmmobile.mcp.phone.dto.PhoneProdBasForFrontListDto | O |  | ORG_MNFCT_MST, MSP_SALE_PRDT_MST | 김대원 |
| 89 | API_0089 | findLte3GphoneCount |  | 양방향 | 수시 | `/phone/lte3GphoneCount` | PhoneMapper.xml | findLte3GphoneCount | com.ktmmobile.mcp.phone.dto.CommonSearchDto | map | O |  | ORG_MNFCT_MST, MSP_SALE_PRDT_MST | 김대원 |
| 90 | API_0090 | getromotionDcAmt |  | 양방향 | 수시 | `` |  |  |  |  | X |  | VW_CHRG_PRMT_DTL | 김대원 |
| 91 | API_0091 | selectRatePrepia |  | 양방향 | 수시 | `/prepia/ratePrepia` | PrepiaMapper.xml | selectRatePrepia |  | com.ktmmobile.mcp.mspservice.dto.MspSaleSubsdMstDto | O |  | MSP_RATE_MST, MSP_RATE_SPEC | 김대원 |
| 92 | API_0092 | getRateList |  | 양방향 | 수시 | `/prepia/rateList` | PrepiaMapper.xml | getRateList | java.util.Map | com.ktmmobile.mcp.mspservice.dto.MspSaleSubsdMstDto | O |  | MSP_RATE_MST, MSP_RATE_SPEC | 김대원 |
| 93 | API_0093 | selectMcpRequestData | 고객 신청서 정보 조회 | 양방향 | 수시 | `/requestreview/mcpRequestData` | RequestReviewMapper.xml | selectMcpRequestData | java.lang.String | com.ktmmobile.mcp.requestreview.dto.RequestReviewDto | O |  | MSP_JUO_SUB_INFO, MSP_REQUEST | 강채신 |
| 94 | API_0094 | selectMspJuoSubInfo | 고객 신청서 정보 조회 | 양방향 | 수시 | `/requestreview/RequestReviewDto` | RequestReviewMapper.xml | selectMspJuoSubInfo | java.lang.String | com.ktmmobile.mcp.requestreview.dto.RequestReviewDto | O |  | MSP_JUO_SUB_INFO | 강채신 |
| 95 | API_0095 | checkUsimRegCnt |  | 양방향 | 수시 | `/sellUsim/checkUsimRegCount` | SellUsimMapper.xml | checkUsimRegCnt | com.ktmmobile.mcp.usim.dto.SellUsimDto | int | O | SQL ID 변경 | MSP_JUO_SUB_INFO | 강문재 |
| 96 | API_0096 | selectMspApplyCount |  | 양방향 | 수시 | `/sms/mspApplyCount` | SmsMapper.xml | selectMspApplyCount | java.util.Map | java.lang.Integer | O |  | MSP_REQUEST, MSP_REQUEST_CSTMR, MSP_REQUEST_STATE | 강채신 |
| 97 | API_0097 | insertSms |  | 양방향 | 수시 | `/sms/addSms` | SmsMapper.xml | insertSms | java.util.Map | java.lang.Integer | O |  | SMS_SEQ.NEXTVAL, MSG_QUEUE | 강채신 |
| 98 | API_0098 | insertKakaoNoti |  | 양방향 | 수시 | `/sms/addKakaoNoti` | SmsMapper.xml | insertKakaoNoti | java.util.Map | java.lang.Integer | O |  | SMS_SEQ.NEXTVAL, MSG_QUEUE | 강채신 |
| 99 | API_0099 | selectKtRcgSeq |  | 양방향 | 수시 | `/storeUsim/ktRcgSeq` | StoreUsimMapper.xml | selectKtRcgSeq | com.ktmmobile.mcp.usim.dto.KtRcgDto | com.ktmmobile.mcp.usim.dto.KtRcgDto | O | 파라미터를 DTO로 넘김 | P_REMAINS_QUERY_B2C | 강문재 |
| 100 | API_0100 | selectRcg |  | 양방향 | 수시 | `/storeUsim/ktRcg` | StoreUsimMapper.xml | selectRcg | java.util.Map | java.util.Map | O | 파라미터를 Map으로 넘김 | P_RCG_QUERY_B2C | 강문재 |
| 101 | API_0101 | selectRateList | usim 상품 요금제 가져오기 | 양방향 | 수시 | `` | StoreUsimMapper.xml | selectRateList | com.ktmmobile.mcp.usim.dto.UsimBasDto | com.ktmmobile.mcp.usim.dto.UsimMspRateDto | O | 파라미터를 DTO로 넘김 | MSP_SALE_ORGN_MST, MSP_SALE_PLCY_MST, MSP_SALE_RATE_MST, MSP | 강문재 |
| 102 | API_0102 | selectModelList |  | 양방향 | 수시 | `/storeUsim/modelList` | StoreUsimMapper.xml | selectModelList | com.ktmmobile.mcp.usim.dto.UsimBasDto | com.ktmmobile.mcp.usim.dto.UsimBasDto | O | 파라미터를 DTO로 넘김 | MSP_SALE_PRDT_MST, CMN_INTM_MDL | 강문재 |
| 103 | API_0103 | selectJoinUsimPriceNew | USIM 가입비조회 | 양방향 | 수시 | `/storeUsim/joinUsimPriceNew` | StoreUsimMapper.xml | selectJoinUsimPriceNew | String | com.ktmmobile.mcp.usim.dto.UsimMspRateDto | O |  | CMN_GRP_CD_MST | 강문재 |
| 104 | API_0104 | selectUsimDcamt | usim 상품 약정기간 없는 할인율 조회 | 양방향 | 수시 | `/storeUsim/usimDcamt` | StoreUsimMapper.xml | selectUsimDcamt | String | com.ktmmobile.mcp.usim.dto.UsimMspRateDto | O |  | MSP_RATE_SPEC | 강문재 |
| 105 | API_0105 | selectAgreeDcAmt | usim 상품 약정 할인 조회 | 양방향 | 수시 | `/storeUsim/agreeDcAmt` | StoreUsimMapper.xml | selectAgreeDcAmt | com.ktmmobile.mcp.usim.dto.UsimMspRateDto | com.ktmmobile.mcp.usim.dto.UsimMspRateDto | O | 파라미터를 DTO로 넘김 | MSP_RATE_SPEC, MSP_SALE_PLCY_MST, MSP_SALE_ORGN_MST, MSP_SAL | 강문재 |
| 106 | API_0106 | selectUserChargeInfo | 이름과 전화번호로 사용 요금제 조회 | 양방향 | 수시 | `/storeUsim/userChargeInfo` | StoreUsimMapper.xml | selectUserChargeInfo | com.ktmmobile.mcp.common.dto.AuthSmsDto | com.ktmmobile.mcp.common.dto.AuthSmsDto | O | 파라미터를 DTO로 넘김 | MSP_RATE_MST, MSP_JUO_FEATURE_INFO, MSP_JUO_SUB_INFO | 강문재 |
| 107 | API_0107 | selectUsimSalePlcyCdList | 상품정보로 유효한 정책정보를  조회(약정포함) | 양방향 | 수시 | `/storeUsim/usimSalePlcyCdList` | StoreUsimMapper.xml | selectUsimSalePlcyCdList | com.ktmmobile.mcp.mspservice.dto.MspSalePlcyMstDto | com.ktmmobile.mcp.mspservice.dto.MspSalePlcyMstDto | O | 파라미터를 DTO로 넘김 | MSP_SALE_PRDT_MST, CMN_INTM_MDL, MSP_SALE_PLCY_MST, MSP_RATE | 강문재 |
| 108 | API_0108 | selectMspRateMstList | 해당정책코드에 해당하는 판매요금제 정보 리스트 조회 | 양방향 | 수시 | `` |  |  |  |  | X |  | MSP_RATE_MST, MSP_SALE_RATE_MST, CMN_GRP_CD_MST, MSP_SALE_AG | 강문재 |
| 109 | API_0109 | selectPlcyOperTypeList | 가입유형 가져오기 | 양방향 | 수시 | `/storeUsim/plcyOperTypeList` | StoreUsimMapper.xml | selectPlcyOperTypeList | com.ktmmobile.mcp.mspservice.dto.MspSalePlcyMstDto | com.ktmmobile.mcp.mspservice.dto.MspPlcyOperTypeDto | O | 파라미터를 DTO로 넘김 | MSP_SALE_ORGN_MST, MSP_SALE_PLCY_MST, MSP_SALE_PRDT_MST | 강문재 |
| 110 | API_0110 | selectRateListMoreTwoRows |  | 양방향 | 수시 | `/storeUsim/rateListMoreTwoRows` | StoreUsimMapper.xml | selectRateListMoreTwoRows | com.ktmmobile.mcp.usim.dto.UsimBasDto | com.ktmmobile.mcp.usim.dto.UsimMspRateDto | O | 파라미터를 DTO로 넘김 | MSP_SALE_ORGN_MST, MSP_SALE_PLCY_MST, MSP_SALE_RATE_MST, MSP | 강문재 |
| 111 | API_0111 | callMspProcedureinsertTelCounsel |  | 양방향 | 수시 | `/telCounsel/telCounsel` | TelCounselMapper.xml | callMspProcedureinsertTelCounsel | com.ktmmobile.mcp.telcounsel.dto.TelCounselDto | void | X |  | P_MSP_VOC_LINK_REG | 김대원 |
| 112 | API_0112 | getMspJuoSubInfo |  | 양방향 | 수시 | `/usermanage/mspJuoSubInfo` | UserManageMapper.xml | getMspJuoSubInfo | com.ktmmobile.mcp.usermanage.dto.McpUserCertDto | com.ktmmobile.mcp.usermanage.dto.McpUserCertDto | O |  | MSP_JUO_SUB_INFO | 강채신 |
| 113 | API_0113 | getMcpUserCntrDtoList |  | 양방향 | 수시 | `/usermanage/mcpUserCntrDtoList` | UserManageMapper.xml | getMcpUserCntrDtoList | java.lang.String | com.ktmmobile.mcp.usermanage.dto.McpUserCertDto | O |  | MSP_JUO_SUB_INFO | 강채신 |
| 114 | API_0114 | listUsimMspPlcyDto | USIM 판매정책 리스트 정보 조회 only msp d | 양방향 | 수시 | `/usim/listUsimMspPlcyDto` | UsimMapper.xml | listUsimMspPlcyDto |  | com.ktmmobile.mcp.usim.dto.UsimMspPlcyDto | O | SQL ID 변경 | MSP_SALE_PLCY_MST | 강문재 |
| 115 | API_0115 | listUsimMspDto | USIM 판매정책코드를 이용한 USIM 상품 리스트 정 | 양방향 | 수시 | `/usim/listUsimMspDto` | UsimMapper.xml | listUsimMspDto | com.ktmmobile.mcp.usim.dto.UsimMspDto | com.ktmmobile.mcp.usim.dto.UsimMspDto | O | SQL ID 변경 | MSP_RATE_MST, MSP_SALE_RATE_MST, CMN_GRP_CD_MST | 강문재 |
| 116 | API_0116 | getUsimBasTotalCount | 게시판 글 수 받아오기 | 양방향 | 수시 | `/usim/usimBasTotalCount` | UsimMapper.xml | getUsimBasTotalCount | com.ktmmobile.mcp.phone.dto.CommonSearchDto | int | O | SQL ID 변경 | MSP_SALE_PLCY_MST | 강문재 |
| 117 | API_0117 | selectUsimBasList | 글 리스트 조회 | 양방향 | 수시 | `/usim/usimBasList` | UsimMapper.xml | selectUsimBasList | com.ktmmobile.mcp.phone.dto.CommonSearchDto | com.ktmmobile.mcp.usim.dto.UsimBasDto | O | 파라미터를 DTO로 넘김 | MSP_SALE_PLCY_MST | 강문재 |
| 118 | API_0118 | listUsimPlcyMspDto |  | 양방향 | 수시 | `` |  |  |  |  | X |  | MSP_RATE_MST, MSP_SALE_RATE_MST, CMN_GRP_CD_MST, MSP_SALE_PL | 강문재 |
| 119 | API_0119 | selectUsimPrdtList | 정책에 해당하는 상품의 상세정보 조회 | 양방향 | 수시 | `/usim/usimPrdtList` | UsimMapper.xml | selectUsimPrdtList | com.ktmmobile.mcp.usim.dto.UsimBasDto | com.ktmmobile.mcp.usim.dto.UsimBasDto | O | 파라미터를 DTO로 넘김 | MSP_SALE_PRDT_MST, CMN_INTM_MDL], MSP_SALE_PLCY_MST, MSP_RAT | 강문재 |
| 120 | API_0120 | selectUsimsalePlcyCdToPrdtList | 상품정보로 유효한 정책정보 조회 | 양방향 | 수시 | `/usim/usimSalePlcyCdToPrdtList` | UsimMapper.xml | selectUsimsalePlcyCdToPrdtList | com.ktmmobile.mcp.usim.dto.UsimBasDto | com.ktmmobile.mcp.usim.dto.UsimBasDto | O | SQL ID 변경
파라미터를 DTO로 넘김 | MSP_SALE_PRDT_MST, CMN_INTM_MDL, MSP_SALE_PLCY_MST, MSP_RATE | 강문재 |
| 121 | API_0121 | selectUsimNewRateList |  | 양방향 | 수시 | `/usim/usimNewRateList` | UsimMapper.xml | selectUsimNewRateList | com.ktmmobile.mcp.usim.dto.UsimBasDto | com.ktmmobile.mcp.usim.dto.UsimBasDto | O | 파라미터를 DTO로 넘김 | MSP_RATE_MST, MSP_SALE_RATE_MST, CMN_GRP_CD_MST, MSP_SALE_AG | 강문재 |
| 122 | API_0122 | selectUsimagrmTrmList | 정책정보로 약정할인 조회 | 양방향 | 수시 | `` |  |  |  |  | X |  | MSP_SALE_AGRM_MST | 강문재 |
| 123 | API_0123 | selectUsimSalePlcyCdBannerList | 상품정보로 유효한 정책정보를 조회(약정포함) | 양방향 | 수시 | `/usim/usimSalePlcyCdBannerList` | UsimMapper.xml | selectUsimSalePlcyCdBannerList | com.ktmmobile.mcp.mspservice.dto.MspSalePlcyMstDto | com.ktmmobile.mcp.mspservice.dto.MspSalePlcyMstDto | O | 파라미터를 DTO로 넘김 | MSP_SALE_PRDT_MST, CMN_INTM_MDL, MSP_SALE_PLCY_MST, MSP_RATE | 강문재 |
| 124 | API_0124 | listMspSaleAgrmMstMoreTwoRows | 정책정보로 약정할인 조회 | 양방향 | 수시 | `/usim/mspSaleAgrmMstMoreTwoRows` | UsimMapper.xml | listMspSaleAgrmMstMoreTwoRows | com.ktmmobile.mcp.usim.dto.UsimBasDto | com.ktmmobile.mcp.usim.dto.UsimBasDto | O | SQL ID 변경
파라미터를 DTO로 넘김 | MSP_SALE_AGRM_MST | 강문재 |
| 125 | API_0125 | pAppSms |  | 양방향 | 수시 | `/appPps/pAppSms` | AppMobilePpsMapper.xml | pAppSms | java.util.Map | void | O |  | P_APP_SMS | 박정웅 |
| 126 | API_0126 | pAppUserInfo |  | 양방향 | 수시 | `/appPps/pAppUserInfo` | AppMobilePpsMapper.xml | pAppUserInfo | java.util.Map | void | O |  | P_APP_USER_INFO | 박정웅 |
| 127 | API_0127 | pAppChgVac |  | 양방향 | 수시 | `/appPps/pAppChgVac` | AppMobilePpsMapper.xml | pAppChgVac | java.util.Map | void | O |  | P_APP_CHG_VAC | 박정웅 |
| 128 | API_0128 | pAppPpReq |  | 양방향 | 수시 | `/appPps/pAppPpReq` | AppMobilePpsMapper.xml | pAppPpReq | java.util.Map | void | O |  | P_APP_PP_REQ | 박정웅 |
| 129 | API_0129 | pAppPpRes |  | 양방향 | 수시 | `/appPps/pAppPpRes` | AppMobilePpsMapper.xml | pAppPpRes | java.util.Map | void | O |  | P_APP_PP_RES | 박정웅 |
| 130 | API_0130 | pAppPpRst |  | 양방향 | 수시 | `/appPps/pAppPpRst` | AppMobilePpsMapper.xml | pAppPpRst | java.util.Map | void | O |  | P_APP_PP_RST | 박정웅 |
| 131 | API_0131 | callMcpMrktAgr | userId 기준 마케팅 수신 동의 여부 히스토리 저장 | 양방향 | 수시 | `/mypage/callMcpMrktAgr` | MypageMapper.xml | callMcpMrktAgr | java.util.HashMap | void | O |  | P_MCP_MRKT_AGR | 박정웅 |
| 132 | API_0132 | callMcpMrktAgrNew | userId 기준 마케팅 수신 동의 여부 히스토리 저장 | 양방향 | 수시 | `/mypage/callMcpMrktAgrNew` | MypageMapper.xml | callMcpMrktAgrNew | java.util.HashMap | void | O |  | P_MCP_MRKT_AGR_NEW | 장성환 |
| 133 | API_0133 | callMcpRateChgImg | 금융제휴상품요금제 변경 서식지 이미지 합본 프로시져 | 양방향 | 수시 | `/mypage/callMcpRateChgImg` | MypageMapper.xml | callMcpRateChgImg | com.ktmmobile.mcp.mypage.dto.McpRateChgDto | java.util.Map | O |  | P_MCP_RATE_CHG | 박정웅 |
| 134 | API_0134 | getPrmtChk |  | 양방향 | 수시 | `/appForm/getPrmtChk` | GiftMapper.xml | getPrmtChk | com.ktmmobile.mcp.etc.dto.GiftPromotionDto | com.ktmmobile.mcp.etc.dto.GiftPromotionDto | O |  | MSP_GIFT_PRMT | 장성환 |
| 135 | API_0135 | getGiftList |  | 양방향 | 수시 | `/appForm/getGiftList` | GiftMapper.xml | getGiftList | com.ktmmobile.mcp.etc.dto.GiftPromotionDto | com.ktmmobile.mcp.etc.dto.GiftPromotionDto | O |  | MSP_GIFT_PRMT_PRDT, MSP_GIFT_PRDT | 장성환 |
| 136 | API_0136 | getMspGiftPrmtCustomer |  | 양방향 | 수시 | `/appForm/getMspGiftPrmtCustomer` | GiftMapper.xml | getMspGiftPrmtCustomer | com.ktmmobile.mcp.etc.dto.GiftPromotionDto | com.ktmmobile.mcp.etc.dto.GiftPromotionDto | O |  | MSP_GIFT_PRMT_CUSTOMER | 장성환 |
| 137 | API_0137 | getMspJuoSubInfoData |  | 양방향 | 수시 | `/appForm/getMspJuoSubInfoData` | GiftMapper.xml | getMspJuoSubInfoData | com.ktmmobile.mcp.etc.dto.GiftPromotionDto | com.ktmmobile.mcp.etc.dto.GiftPromotionDto | O |  | MSP_JUO_SUB_INFO | 장성환 |
| 138 | API_0138 | getChkMspGiftPrmt |  | 양방향 | 수시 | `/appForm/getChkMspGiftPrmt` | GiftMapper.xml | getChkMspGiftPrmt | com.ktmmobile.mcp.etc.dto.GiftPromotionDto | com.ktmmobile.mcp.etc.dto.GiftPromotionDto | O |  | MSP_GIFT_PRMT_RESULT | 장성환 |
| 139 | API_0139 | updateMspGiftPrmtCustomer |  | 양방향 | 수시 | `/appForm/updateMspGiftPrmtCustomer` | GiftMapper.xml | updateMspGiftPrmtCustomer | com.ktmmobile.mcp.etc.dto.GiftPromotionDto |  | O |  | MSP_GIFT_PRMT_CUSTOMER | 장성환 |
| 140 | API_0140 | insertMspGiftPrmtResult |  | 양방향 | 수시 | `/appForm/insertMspGiftPrmtResult` | GiftMapper.xml | insertMspGiftPrmtResult | com.ktmmobile.mcp.etc.dto.GiftPromotionDto |  | O |  | MSP_GIFT_PRMT_RESULT | 장성환 |
| 141 | API_0141 | getSaveGiftList |  | 양방향 | 수시 | `/appForm/getSaveGiftList` | GiftMapper.xml | getSaveGiftList | com.ktmmobile.mcp.etc.dto.GiftPromotionDto | com.ktmmobile.mcp.etc.dto.GiftPromotionDto | O |  | MSP_GIFT_PRMT_RESULT, MSP_GIFT_PRDT | 장성환 |
| 142 | API_0142 | getEnggInfo |  | 양방향 | 수시 | `/mypage/enggInfo1` | MypageMapper.xml | getEnggInfo | string | com.ktmmobile.mcp.mypage.dto.MspJuoAddInfoDto | O |  | VW_CNTR_ENGG_SELF | 백승주 |
| 143 | API_0143 | getChannelInfo |  | 양방향 | 수시 | `/mypage/channelInfo` | MypageMapper.xml | getChannelInfo | string | com.ktmmobile.mcp.mypage.dto.MspJuoAddInfoDto | O |  | MSP_JUO_SUB_INFO, CMN_GRP_CD_MST | 백승주 |
| 144 | API_0144 | getCloseSubList |  | 양방향 | 수시 | `/mypage/closeSubList` | MypageMapper.xml | getCloseSubList | string | com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto | O |  | MSP_JUO_FEATURE_INFO, CMN_GRP_CD_MST, MSP_JUO_SUB_INFO | 백승주 |
| 145 | API_0145 | insertSocfailProcMst |  | 양방향 | 수시 | `/mypage/insertSocfailProcMst` | MypageMapper.xml | insertSocfailProcMst | com.ktmmobile.mcp.mypage.dto.McpServiceAlterTraceDto |  | O |  | MSP_SOCFAIL_PROC_MST | 백승주 |
| 146 | API_0146 | getromotionDcList |  | 양방향 | 수시 | `/mypage/romotionDcList` | MypageMapper.xml | getromotionDcList | string | com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto | O |  | VW_CHRG_PRMT_DTL, MCP_ADDITION | 백승주 |
| 147 | API_0147 | selectUsimBasDto |  | 양방향 | 수시 | `/usim/selectUsimBasDto` | UsimMapper.xml | selectUsimBasDto | com.ktmmobile.mcp.usim.dto.UsimBasDto | com.ktmmobile.mcp.usim.dto.UsimBasDto
com.ktmmobile.mcp.usim.dto.UsimMspPlcyDto | O |  | NMCP_USIM_BAS, MSP_SALE_PLCY_MST | 박정웅 |
| 148 | API_0148 | insertMcpUsimProdSort |  | 양방향 | 수시 | `/usim/insertMcpUsimProdSort` | UsimMapper.xml | insertMcpUsimProdSort | List<UsimBasDto> | java.lang.Integer | O |  | MCP_USIM_PROD_SORT, MSP_RATE_MST | 박정웅 |
| 149 | API_0149 | checkLimitOpenFormCount | 동일명의 회선 90일 이내에에 개통/개통취소 이력이 1 | 양방향 | 수시 | `/appform/checkLimitOpenFormCount` | Appform_Query.xml | checkLimitOpenFormCount | com.ktmmobile.mcp.appform.dto.AppformReqDto | java.lang.Integer | O |  | MSP_JUO_SUB_INFO@DL_MSP, MSP_JUO_CUS_INFO@DL_MSP | 장성환 |
| 150 | API_0150 | getPrmtBasList | 사은품 조회 | 양방향 | 수시 | `/appForm/getPrmtBasList` | GiftMapper.xml | getPrmtBasList | com.ktmmobile.mcp.etc.dto.GiftPromotionBas | com.ktmmobile.mcp.etc.dto.GiftPromotionBas | O |  |  | 장성환 |
| 151 | API_0151 | getPrmtId | 사은품 상세 조회 | 양방향 | 수시 | `/appForm/getPrmtId` | GiftMapper.xml | getPrmtId | com.ktmmobile.mcp.etc.dto.GiftPromotionDtl | com.ktmmobile.mcp.etc.dto.GiftPromotionDtl | O |  |  | 장성환 |
| 152 | API_0152 | getGiftArrList | 사은품 리스트 조회 | 양방향 | 수시 | `/appForm/getGiftArrList` | GiftMapper.xml | getGiftArrList | com.ktmmobile.mcp.etc.dto.GiftPromotionDto | java.lang.String | O |  |  | 장성환 |

---

## 2. 인터페이스 설계서 (상세)

> 각 API는 연동 ID, 엔드포인트, IN/OUT 파라미터, SQL 및 대상 테이블을 포함합니다.

---

### MYPAGE 도메인 (30개)

#### API_0052 — selectCntrList

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `selectCntrList` |
| 엔드포인트 | `/mypage/cntrList` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | MypageMapper.xml |
| SQL ID | `selectCntrList` |
| parameterType | `java.lang.String` |
| resultType | `com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_JUO_SUB_INFO |
| 담당자 | 강문재 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `userid` | 고객아이디 | `String` | 고객아이디 | USERID | MCP_USER_CNTR_MNG |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `userid` | 고객아이디 | `String` | 고객아이디 | USERID | MCP_USER_CNTR_MNG |
| `cntrMobileNo` | 휴대폰번호 | `String` | 휴대폰번호 | CNTR_MOBILE_NO | MCP_USER_CNTR_MNG |
| `sysRdate` | 입력일 | `String` | 입력일 | SYS_RDATE | MCP_USER_CNTR_MNG |
| `svcCntrNo` | 서비스 계약번호 | `String` | 서비스 계약번호 | SVC_CNTR_NO | MSP_JUO_SUB_INFO |
| `contractNum` | 가입계약번호 | `String` | 가입계약번호 | CONTRACT_NUM | MSP_JUO_SUB_INFO |
| `repNo` |  | `String` |  | REP_NO | MCP_USER_CNTR_MNG |
| `custId` | 고객 ID | `String` | 고객 ID | CUSTOMER_ID AS CUST_ID | MSP_JUO_SUB_INFO |
| `modelName` | 단말기 모델명 | `String` | 단말기 모델명 | MODEL_NAME | MSP_JUO_SUB_INFO |
| `modelId` | 모델ID | `String` | 모델ID | MODEL_ID | MSP_JUO_SUB_INFO |
| `subStatus` | 상태값 | `String` | 상태값 | SUB_STATUS | MSP_JUO_SUB_INFO |

**SQL**

```sql
SELECT
A.USERID
, A.CNTR_MOBILE_NO
, A.SYS_RDATE
, B.SVC_CNTR_NO
, B.CONTRACT_NUM
, A.REP_NO
, B.CUSTOMER_ID AS CUST_ID
, B.MODEL_NAME
, B.MODEL_ID
, B.SUB_STATUS
FROM MCP_USER_CNTR_MNG A, MSP_JUO_SUB_INFO@DL_MSP B
WHERE UPPER(A.USERID) = UPPER(#{userid})
AND A.SVC_CNTR_NO = B.CONTRACT_NUM
AND B.SUB_STATUS <![CDATA[<>]]> 'C'
ORDER BY A.REP_NO ASC
```


#### API_0053 — selectSocDesc

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `selectSocDesc` |
| 엔드포인트 | `/mypage/socDesc` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | MypageMapper.xml |
| SQL ID | `selectSocDesc` |
| parameterType | `java.lang.String` |
| resultType | `com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_JUO_ADD_INFO, MSP_JUO_FEATURE_INFO, MSP_RATE_MST, MSP_JUO_SUB_INFO |
| 담당자 | 강문재 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `svcCntrNo` | 서비스 계약번호 | `String` | 서비스 계약번호 | CONTRACT_NUM | MSP_JUO_ADD_INFO |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `userid` | 고객아이디 | `String` | 고객아이디 | USERID | MCP_USER_CNTR_MNG |
| `cntrMobileNo` | 휴대폰번호 | `String` | 휴대폰번호 | CNTR_MOBILE_NO | MCP_USER_CNTR_MNG |
| `svcCntrNo` | 서비스 계약번호 | `String` | 서비스 계약번호 | SVC_CNTR_NO | MSP_JUO_SUB_INFO |
| `soc` | 상품코드 | `String` | 상품코드 | SOC | MSP_JUO_FEATURE_INFO |
| `enggMnthCnt` | 약정개월수 | `long` | 약정개월수 | ENGG_MNTH_CNT | MSP_JUO_ADD_INFO |
| `rateNm` | 요금제명 | `String` | 요금제명 | RATE_NM | MSP_RATE_MST |
| `baseAmt` | 기본료 | `int` | 기본료 | BASE_AMT | MSP_RATE_MST |
| `rmk` | 비고 | `String` | 비고 | RMK | MSP_RATE_MST |
| `modelName` | 단말기 모델명 | `String` | 단말기 모델명 | MODEL_NAME | MSP_JUO_SUB_INFO |
| `dobyyyymmdd` | 생년월일 | `String` | 생년월일 | DOBYYYYMMDD | MSP_JUO_SUB_INFO |
| `userSsn` | 주민등록번호 | `String` | 주민등록번호 | USER_SSN | MSP_JUO_SUB_INFO |
| `intmSrlNo` | 단말기일련번호 | `String` | 단말기일련번호 | INTM_SRL_NO | MSP_JUO_SUB_INFO |
| `modelId` | 모델ID | `String` | 모델ID | MODEL_ID | MSP_JUO_SUB_INFO |
| `dcAmt` | 할인금액(약정할인선택시 할인금액) | `int` | 할인금액(약정할인선택시 할인금액) | DC_AMT | MCP_USER_CNTR_MNG |
| `pointId` | 제주항공 회원번호 | `String` | 제주항공 회원번호 | POINT_ID | MCP_USER_CNTR_MNG |

**SQL**

```sql
SELECT
AA.USERID, AA.CNTR_MOBILE_NO, AA.SVC_CNTR_NO, AA.SOC, AA.ENGG_MNTH_CNT, AA.RATE_NM, AA.BASE_AMT, AA.RMK, AA.MODEL_NAME
, AA.DOBYYYYMMDD
, AA.USER_SSN
, AA.INTM_SRL_NO
, AA.MODEL_ID
, NVL(AA.DC_AMT, 0) DC_AMT, POINT_ID
FROM
(
SELECT
A.USERID, A.CNTR_MOBILE_NO, D.SVC_CNTR_NO, D.CONTRACT_NUM
, B.SOC
, NVL((SELECT ENGG_MNTH_CNT
FROM (SELECT ENGG_MNTH_CNT , ROW_NUMBER() OVER (ORDER BY EVNT_TRTM_DT DESC) AS RNUM
FROM MSP_JUO_ADD_INFO@DL_MSP
WHERE CONTRACT_NUM = #{svcCntrNo}
AND ENGG_MNTH_CNT IS NOT NULL
AND APP_END_DATE > TO_CHAR (SYSDATE, 'YYYYMMDDHH24MISS')
)WHERE RNUM = 1
), 0) AS ENGG_MNTH_CNT
, C.RATE_NM, C.BASE_AMT, C.RMK
, D.MODEL_NAME
, D.DOBYYYYMMDD
, D.USER_SSN
, D.INTM_SRL_NO
, D.MODEL_ID
, '' AS POINT_ID
, 0 AS DC_AMT
FROM
MCP_USER_CNTR_MNG A
, MSP_JUO_FEATURE_INFO@DL_MSP B
, MSP_RATE_MST@DL_MSP C
, MSP_JUO_SUB_INFO@DL_MSP D
WHERE A.SVC_CNTR_NO = #{svcCntrNo}
AND B.CONTRACT_NUM = A.SVC_CNTR_NO
AND B.SERVICE_TYPE = 'P'
AND B.EXPIRATION_DATE > TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')
AND B.SOC = C.RATE_CD
AND D.CONTRACT_NUM = A.SVC_CNTR_NO
) AA
```


#### API_0054 — selectMspAddInfo

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `selectMspAddInfo` |
| 엔드포인트 | `/mypage/mspAddInfo` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | MypageMapper.xml |
| SQL ID | `selectMspAddInfo` |
| parameterType | `java.lang.String` |
| resultType | `com.ktmmobile.mcp.mypage.dto.MspJuoAddInfoDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_JUO_ADD_INFO |
| 담당자 | 강문재 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `svcCntrNo` | 서비스 계약번호 | `String` | 서비스 계약번호 | CONTRACT_NUM | MSP_JUO_ADD_INFO |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `contractNum` | 가입계약번호 | `String` | 가입계약번호 | CONTRACT_NUM | MSP_JUO_ADD_INFO |
| `dvcOfwAmnt` | 단말출고가 | `int` | 단말출고가 | DVC_OFW_AMNT | MSP_JUO_ADD_INFO |
| `instOrginAmnt` | 할부원금 | `int` | 할부원금 | INST_ORGIN_AMNT | MSP_JUO_ADD_INFO |
| `instMnthCnt` | 할부개월수 | `int` | 할부개월수 | INST_MNTH_CNT | MSP_JUO_ADD_INFO |
| `enggMnthCnt` | 약정개월수 | `long` | 약정개월수 | ENGG_MNTH_CNT | MSP_JUO_ADD_INFO |
| `modelName` | 단말기 모델명 | `String` | 단말기 모델명 | MODEL_NAME | MSP_JUO_ADD_INFO |
| `modelId` | 모델ID | `String` | 모델ID | MODEL_ID | MSP_JUO_ADD_INFO |

**SQL**

```sql
SELECT CONTRACT_NUM --계약번호
, DVC_OFW_AMNT --단말출고가
, INST_ORGIN_AMNT --할부원금
, INST_MNTH_CNT --할부개월수
, ENGG_MNTH_CNT --약정개월수
, MODEL_NAME	--단말기 모델 이름
, MODEL_ID		--단말기 모델 ID
FROM(
SELECT CONTRACT_NUM --계약번호
, DVC_OFW_AMNT --단말출고가
, INST_ORGIN_AMNT --할부원금
, INST_MNTH_CNT --할부개월수
, ENGG_MNTH_CNT --약정개월수
, MODEL_NAME	--단말기 모델 이름
, MODEL_ID		--단말기 모델 ID
, ROW_NUMBER() OVER (ORDER BY EVNT_TRTM_DT DESC) AS RNUM
FROM MSP_JUO_ADD_INFO@DL_MSP
WHERE CONTRACT_NUM = #{svcCntrNo}
AND APP_END_DATE > TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')
) WHERE RNUM = 1
```


#### API_0055 — selectUserMultiLine

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `selectUserMultiLine` |
| 엔드포인트 | `/mypage/userMultiLine` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | MypageMapper.xml |
| SQL ID | `selectUserMultiLine` |
| parameterType | `java.lang.String` |
| resultType | `com.ktmmobile.mcp.common.mplatform.vo.UserVO` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_JUO_SUB_INFO |
| 담당자 | 강문재 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `userid` | 고객아이디 | `String` | 고객아이디 | USERID | MCP_USER_CNTR_MNG |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `mobileNo` | 핸드폰번호 | `String` | 핸드폰번호 | CNTR_MOBILE_NO AS MOBILE_NO | MCP_USER_CNTR_MNG |
| `modelName` | 단말기 모델명 | `String` | 단말기 모델명 | MODEL_NAME | MCP_USER_CNTR_MNG |
| `repNo` | 대표번호 | `String` | 대표번호 | REP_NO | MCP_USER_CNTR_MNG |
| `no` | ROW번호 | `int` | ROW번호 | ROW_NUMBER() OVER (ORDER BY REP_NO, CNTR_MOBILE_NO) AS NO | MCP_USER_CNTR_MNG |
| `phone1` | 핸드폰번호 | `String` | 핸드폰번호 | CNTR_MOBILE_NO AS PHONE1 | MCP_USER_CNTR_MNG |
| `contractNum` | 가입계약번호 | `String` | 가입계약번호 | CONTRACT_NUM | MSP_JUO_SUB_INFO |

**SQL**

```sql
SELECT SUBSTR(A.CNTR_MOBILE_NO,0,3)||'-'||DECODE(LENGTH(A.CNTR_MOBILE_NO),11,SUBSTR(A.CNTR_MOBILE_NO,4,4),SUBSTR(A.CNTR_MOBILE_NO,5,3))
||'-'||SUBSTR(A.CNTR_MOBILE_NO,-4,4) AS MOBILE_NO
, MODEL_NAME
, REP_NO
, ROW_NUMBER() OVER (ORDER BY REP_NO, CNTR_MOBILE_NO) AS NO
, A.CNTR_MOBILE_NO AS PHONE1
, B.CONTRACT_NUM
FROM MCP_USER_CNTR_MNG A
, MSP_JUO_SUB_INFO@DL_MSP B
WHERE B.CONTRACT_NUM=A.SVC_CNTR_NO
AND UPPER(A.USERID) = UPPER(#{userid})
ORDER BY REP_NO
```


#### API_0056 — selectContractNum

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `selectContractNum` |
| 엔드포인트 | `/mypage/contractNum` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | MypageMapper.xml |
| SQL ID | `selectContractNum` |
| parameterType | `java.util.Map` |
| resultType | `java.lang.String` |
| 사용 여부 | ✅ 사용 |
| SQL 유형 | 파라미터를 Map으로 넘김 |
| 대상 테이블 | MSP_JUO_SUB_INFO |
| 담당자 | 강문재 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `mobileNo` | 핸드폰번호 | `String` | 핸드폰번호 | SUBSCRIBER_NO | MSP_JUO_SUB_INFO |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `contractNum` | 가입계약번호 | `String` | 가입계약번호 | CONTRACT_NUM | MSP_JUO_SUB_INFO |
| `mobileNo` | 핸드폰번호 | `String` | 핸드폰번호 | SUBSCRIBER_NO AS MOBILE_NO | MSP_JUO_SUB_INFO |

**SQL**

```sql
SELECT CONTRACT_NUM, SUBSCRIBER_NO AS MOBILE_NO
FROM MSP_JUO_SUB_INFO@DL_MSP
WHERE SUB_STATUS = 'A'
AND SUBSCRIBER_NO= TRIM(#{mobileNo})
AND UPPER(REPLACE(SUB_LINK_NAME,' ','')) = UPPER(REPLACE(#{name},' ',''))
```


#### API_0057 — selectPPSList

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `selectPPSList` |
| 엔드포인트 | `/mypage/ppsList` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | MypageMapper.xml |
| SQL ID | `selectPPSList` |
| parameterType | `java.lang.String` |
| resultType | `com.ktmmobile.mcp.common.dto.db.MspPpsRcgTesDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | PPS_RCG_TES |
| 담당자 | 강문재 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `contractNum` | 가입계약번호 | `String` | 가입계약번호 | CONTRACT_NUM | PPS_RCG_TES |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `tesSeq` | SEQ | `long` | SEQ | TES_SEQ | PPS_RCG_TES |
| `reqType` | 충전유형 | `long` | pps_rcg_type 테이블참조 | REQ_TYPE | PPS_RCG_TES |
| `contractNum` | 가입계약번호 | `String` | 가입계약번호 | CONTRACT_NUM | PPS_RCG_TES |
| `rechargeAgent` | 충전대리점 | `String` | 충전대리점 | RECHARGE_AGENT | PPS_RCG_TES |
| `recharge` | 충전금액 | `long` | 충전금액 | RECHARGE | PPS_RCG_TES |
| `retCode` | 충전결과코드 | `String` | 충전결과코드 | RET_CODE | PPS_RCG_TES |
| `retMsg` | 충전결과메세지 | `String` | 충전결과메세지 | RET_MSG | PPS_RCG_TES |
| `rcgSeq` | SEQ | `long` | SEQ | RCG_SEQ | PPS_RCG_TES |
| `oAmount` | 충전된금액 | `int` | 충전된금액 | O_AMOUNT | PPS_RCG_TES |
| `oTesChargeMax` | 최대충전가능금액 | `String` | 최대충전가능금액 | O_TES_CHARGE_MAX | PPS_RCG_TES |
| `oTesBaser` | 기본알 | `String` | 기본알 | O_TES_BASER | PPS_RCG_TES |
| `oTesChgr` | 충전알 | `String` | 충전알 | O_TES_CHGR | PPS_RCG_TES |
| `oTesMagicr` | 데이터알 | `String` | 데이터알 | O_TES_MAGICR | PPS_RCG_TES |
| `oTesFsmsr` | 문자알 | `String` | 문자알 | O_TES_FSMSR | PPS_RCG_TES |
| `oTesVideor` | 영상알 | `String` | 영상알 | O_TES_VIDEOR | PPS_RCG_TES |
| `oTesIpvasr` | 정보료전용알 | `String` | 정보료전용알 | O_TES_IPVASR | PPS_RCG_TES |
| `oTesIpmaxr` | 알캡상한알 | `String` | 알캡상한알 | O_TES_IPMAXR | PPS_RCG_TES |
| `oTesSmsm` | 문자건수 | `String` | 문자건수 | O_TES_SMSM | PPS_RCG_TES |
| `oTesDataplusv` | 데이타계정 | `String` | 데이타계정(단위:Byte) | O_TES_DATAPLUSV | PPS_RCG_TES |
| `rechargeIp` | 요청IP | `String` | 요청IP | RECHARGE_IP | PPS_RCG_TES |
| `reqDate` | 요청일자 | `Date` | 요청일자 | REQ_DATE | PPS_RCG_TES |
| `adminId` | 충전요청한 로그인아이디 | `String` | 충전요청한 로그인아이디 | ADMIN_ID | PPS_RCG_TES |

**SQL**

```sql
SELECT /* Mypage.selectPPSList 충전 내역조회 테이블   */
TES_SEQ
, REQ_TYPE
, CONTRACT_NUM
, RECHARGE_AGENT
, RECHARGE
, RET_CODE
, RET_MSG
, RCG_SEQ
, O_AMOUNT
, O_TES_CHARGE_MAX
, O_TES_BASER
, O_TES_CHGR
, O_TES_MAGICR
, O_TES_FSMSR
, O_TES_VIDEOR
, O_TES_IPVASR
, O_TES_IPMAXR
, O_TES_SMSM
, O_TES_DATAPLUSV
, RECHARGE_IP
, REQ_DATE
, ADMIN_ID
FROM PPS_RCG_TES@DL_MSP
WHERE 1=1
AND CONTRACT_NUM = #{contractNum}
AND REQ_TYPE = 'ARS_TES_CHARGE'
ORDER BY REQ_DATE DESC
```


#### API_0058 — selectRegService

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `selectRegService` |
| 엔드포인트 | `/mypage/regService` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | MypageMapper.xml |
| SQL ID | `selectRegService` |
| parameterType | `java.lang.String` |
| resultType | `com.ktmmobile.mcp.mypage.dto.McpRegServiceDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_RATE_MST, MSP_PROD_REL_MST, MSP_JUO_FEATURE_INFO |
| 담당자 | 강문재 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `ncn` | 서비스계약번호 | `String` | 서비스계약번호 | CONTRACT_NUM | MSP_JUO_FEATURE_INFO |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `serviceType` | 서비스유형 | `String` | 서비스유형 | SERVICE_TYPE | MSP_RATE_MST |
| `rateCd` | 요금제코드 | `String` | 요금제코드 | RATE_CD | MSP_RATE_MST |
| `applEndDt` | 적용종료일자 | `String` | 적용종료일자 | APPL_END_DT | MSP_RATE_MST |
| `applStrtDt` | 적용시작일자 | `String` | 적용시작일자 | APPL_STRT_DT | MSP_RATE_MST |
| `rateNm` | 요금제명 | `String` | 요금제명 | RATE_NM | MSP_RATE_MST |
| `rateGrpCd` | 요금제그룹코드 | `String` | 요금제그룹코드 | RATE_GRP_CD | MSP_RATE_MST |
| `payClCd` | 선후불구분 | `String` | 선후불구분 | PAY_CL_CD | MSP_RATE_MST |
| `rateType` | 요금제유형 | `String` | 요금제유형(ORG0008) | RATE_TYPE | MSP_RATE_MST |
| `dataType` | 데이터유형 | `String` | 데이터유형 (ORG0008) 'LTE,3G | DATA_TYPE | MSP_RATE_MST |
| `baseAmt` | 기본료 | `int` | 기본료 | BASE_AMT | MSP_RATE_MST |
| `freeCallClCd` | 망내외무료통화구분 | `String` | 망내외무료통화구분 | FREE_CALL_CL_CD | MSP_RATE_MST |
| `freeCallCnt` | 무료통화건수 | `String` | 무료통화건수 | FREE_CALL_CNT | MSP_RATE_MST |
| `nwInCallCnt` | 망내무료통화건수 | `String` | 망내무료통화건수 | NW_IN_CALL_CNT | MSP_RATE_MST |
| `nwOutCallCnt` | 망외무료통화건수 | `String` | 망외무료통화건수 | NW_OUT_CALL_CNT | MSP_RATE_MST |
| `freeSmsCnt` | 무료문자건수 | `String` | 무료문자건수 | FREE_SMS_CNT | MSP_RATE_MST |
| `freeDataCnt` | 무료데이터건수 | `String` | 무료데이터건수 | FREE_DATA_CNT | MSP_RATE_MST |
| `rmk` | 비고 | `String` | 비고 | RMK | MSP_RATE_MST |
| `regstId` | 등록자ID | `String` | 등록자ID | REGST_ID | MSP_RATE_MST |
| `regstDttm` | 등록일시 | `Date` | 등록일시 | REGST_DTTM | MSP_RATE_MST |
| `rvisnId` | 수정자ID | `String` | 수정자ID | RVISN_ID | MSP_RATE_MST |
| `rvisnDttm` | 수정일시 | `Date` | 수정일시 | RVISN_DTTM | MSP_RATE_MST |
| `onlineTypeCd` | 온라인유형코드 | `String` | 온라인유형코드 | ONLINE_TYPE_CD | MSP_RATE_MST |
| `alFlag` | 알요금제구분자 | `String` | 알요금제구분자 | AL_FLAG | MSP_RATE_MST |
| `svcRelTp` | 가입유형 | `String` | 가입유형 :c 가능: b 필수 (자동가입): d 가입불가 | SVC_REL_TP | MSP_PROD_REL_MST |

**SQL**

```sql
SELECT A.SERVICE_TYPE --서비스유형
, A.RATE_CD --요금제코드
, A.APPL_END_DT --적용종료일자
, A.APPL_STRT_DT --적용시작일자
, A.RATE_NM --요금제명
, A.RATE_GRP_CD --요금제그룹코드
, A.PAY_CL_CD --선후불구분
, A.RATE_TYPE --요금제유형( ORG0008 )
, A.DATA_TYPE --데이터유형( ORG0018 )
, A.BASE_AMT --기본료
, A.FREE_CALL_CL_CD --망내외무료통화구분
, A.FREE_CALL_CNT --무료통화건수
, A.NW_IN_CALL_CNT --망내무료통화건수
, A.NW_OUT_CALL_CNT --망외무료통화건수
, A.FREE_SMS_CNT --무료문자건수
, A.FREE_DATA_CNT --무료데이터건수
, A.RMK --비고
, A.REGST_ID --등록자ID
, A.REGST_DTTM --등록일시
, A.RVISN_ID --수정자ID
, A.RVISN_DTTM --수정일시
, A.ONLINE_TYPE_CD --온라인유형코드
, A.AL_FLAG --알요금제 구분자
, B.SVC_REL_TP --가입유형 :c 가능: b 필수 (자동가입): d 가입불가
FROM MSP_RATE_MST@DL_MSP A
, (SELECT RATE_CD,ADD_SVC_CD,SERVICE_TYPE, SVC_REL_TP
FROM MSP_PROD_REL_MST@DL_MSP
WHERE RATE_CD = (SELECT SOC
FROM MSP_JUO_FEATURE_INFO@DL_MSP
WHERE CONTRACT_NUM = #{ncn}
AND APP_END_DATE = '99991231000000'
AND EXPIRATION_DATE = '99991231000000'
AND SERVICE_TYPE = 'P'
)
AND SERVICE_TYPE = 'P'
AND SVC_REL_TP <![CDATA[<>]]> 'D'
) B
WHERE A.SERVICE_TYPE= 'R'
AND A.APPL_END_DT = '99991231'
AND A.RATE_CD = B.ADD_SVC_CD
AND NOT EXISTS(SELECT RATE_CD
FROM(SELECT DISTINCT RATE_CD
FROM MSP_PROD_REL_MST@DL_MSP
WHERE RATE_CD IN (SELECT DISTINCT SOC
FROM MSP_JUO_FEATURE_INFO@DL_MSP
WHERE CONTRACT_NUM = #{ncn}
AND APP_END_DATE = '99991231000000'
AND EXPIRATION_DATE = '99991231000000'
AND SERVICE_TYPE = 'R'
)
AND SERVICE_TYPE = 'R'
) BB
WHERE A.RATE_CD = BB.RATE_CD
)
ORDER BY A.BASE_AMT DESC, A.APPL_STRT_DT DESC
```


#### API_0059 — selectFarPricePlan

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `selectFarPricePlan` |
| 엔드포인트 | `/mypage/farPricePlan` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | MypageMapper.xml |
| SQL ID | `selectFarPricePlan` |
| parameterType | `java.lang.String` |
| resultType | `com.ktmmobile.mcp.mypage.dto.McpFarPriceDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_RATE_MST, MSP_JUO_FEATURE_INFO |
| 담당자 | 강문재 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `ncn` | 서비스계약번호 | `String` | 서비스계약번호 | CONTRACT_NUM | MSP_JUO_FEATURE_INFO |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `prvRateCd` | 변경전_요금제코드 | `String` | 변경전_요금제코드 | RATE_CD AS PRV_RATE_CD | MSP_RATE_MST |
| `prvRateGrpNm` | 요금제명 | `String` | 요금제명 | RATE_NM AS PRV_RATE_GRP_NM | MSP_RATE_MST |
| `prvBaseAmt` | 변경전_요금금액 | `int` | 변경전_요금금액 | BASE_AMT AS PRV_BASE_AMT | MSP_RATE_MST |
| `prvRmk` | 변경전_요금설명 | `String` | 변경전_요금설명 | RMK AS PRV_RMK | MSP_RATE_MST |
| `appStartDd` | 적용일시 | `String` | 적용일시 | APP_START_DD | MSP_RATE_MST |
| `baseVatAmt` | VAT포함금액 | `int` | VAT포함금액 | NVL(A.BASE_AMT,0)+(NVL(A.BASE_AMT,0)*0.1) AS BASE_VAT_AMT | MSP_RATE_MST |

**SQL**

```sql
SELECT A.RATE_CD AS PRV_RATE_CD  --요금제코드
, A.RATE_NM AS PRV_RATE_GRP_NM--요금제명
, A.BASE_AMT AS PRV_BASE_AMT--기본료
, A.RMK AS PRV_RMK--비고
, B.APP_START_DD --적용일시
, NVL(A.BASE_AMT,0)+(NVL(A.BASE_AMT,0)*0.1) AS BASE_VAT_AMT
FROM MSP_RATE_MST@DL_MSP A
, (SELECT *
FROM MSP_JUO_FEATURE_INFO@DL_MSP
WHERE CONTRACT_NUM = #{ncn}
AND APP_END_DATE = '99991231000000'
AND EXPIRATION_DATE = '99991231000000'
AND SERVICE_TYPE = 'P'
) B
WHERE A.RATE_CD = B.SOC
AND a.SERVICE_TYPE = 'P'
AND ROWNUM = 1
```


#### API_0060 — selectFarPricePlanList

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `selectFarPricePlanList` |
| 엔드포인트 | `/mypage/farPricePlanList` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | MypageMapper.xml |
| SQL ID | `selectFarPricePlanList` |
| parameterType | `` |
| resultType | `com.ktmmobile.mcp.mypage.dto.McpFarPriceDto` |
| 사용 여부 | ✅ 사용 |
| SQL 유형 | 다중파라미터 사용
mapper > RowBounds 클래스 사용 |
| 대상 테이블 | VW_GROUP_BY_RATE_INFO |
| 담당자 | 강문재 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `rateCd` | 요금제코드 | `String` | 요금제코드 | PRV_RATE_CD | VW_GROUP_BY_RATE_INFO |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `prvRateGrpCd` | 변경전_요금그룹코드 | `String` | 변경전_요금그룹코드 | PRV_RATE_GRP_CD | VW_GROUP_BY_RATE_INFO |
| `prvRateGrpNm` | 변경전_요금그룹명 | `String` | 변경전_요금그룹명 | PRV_RATE_GRP_NM | VW_GROUP_BY_RATE_INFO |
| `prvRateGrpTypeCd` | 변경전_요금그룹유형코드 | `String` | 변경전_요금그룹유형코드 | PRV_RATE_GRP_TYPE_CD | VW_GROUP_BY_RATE_INFO |
| `prvRateGrpTypeNm` | 변경전_요금그룹유형명 | `String` | 변경전_요금그룹유형명 | PRV_RATE_GRP_TYPE_NM | VW_GROUP_BY_RATE_INFO |
| `prvRateCd` | 변경전_요금제코드 | `String` | 변경전_요금제코드 | PRV_RATE_CD | VW_GROUP_BY_RATE_INFO |
| `prvRateNm` | 변경전_요금제명 | `String` | 변경전_요금제명 | PRV_RATE_NM | VW_GROUP_BY_RATE_INFO |
| `prvPtrnRateYn` | 변경전_제휴요금여부 | `String` | 변경전_제휴요금여부 | PRV_PTRN_RATE_YN | VW_GROUP_BY_RATE_INFO |
| `prvPayClCd` | 변경전_선후불코드 | `String` | 변경전_선후불코드 | PRV_PAY_CL_CD | VW_GROUP_BY_RATE_INFO |
| `prvDataType` | 변경전_데이터유형 | `String` | 변경전_데이터유형 | PRV_DATA_TYPE | VW_GROUP_BY_RATE_INFO |
| `prvRateType` | 변경전_요금제유형 | `String` | 변경전_요금제유형 | PRV_RATE_TYPE | VW_GROUP_BY_RATE_INFO |
| `prvRmk` | 변경전_요금설명 | `String` | 변경전_요금설명 | PRV_RMK | VW_GROUP_BY_RATE_INFO |
| `prvBaseAmt` | 변경전_요금금액 | `int` | 변경전_요금금액 | PRV_BASE_AMT | VW_GROUP_BY_RATE_INFO |
| `prvApplStrtDt` | 변경전_요금제적용시작일자 | `String` | 변경전_요금제적용시작일자 | PRV_APPL_STRT_DT | VW_GROUP_BY_RATE_INFO |
| `prvApplEndDt` | 변경전_요금적용종료일자 | `String` | 변경전_요금적용종료일자 | PRV_APPL_END_DT | VW_GROUP_BY_RATE_INFO |
| `nxtRateGrpCd` | 변경후_요금그룹코드 | `String` | 변경후_요금그룹코드 | NXT_RATE_GRP_CD | VW_GROUP_BY_RATE_INFO |
| `nxtRateGrpNm` | 변경후_요금그룹명 | `String` | 변경후_요금그룹명 | NXT_RATE_GRP_NM | VW_GROUP_BY_RATE_INFO |
| `nxtRateGrpTypeCd` | 변경후_요금그룹유형코드 | `String` | 변경후_요금그룹유형코드 | NXT_RATE_GRP_TYPE_CD | VW_GROUP_BY_RATE_INFO |
| `nxtRateGrpTypeNm` | 변경후_요금그룹유형명 | `String` | 변경후_요금그룹유형명 | NXT_RATE_GRP_TYPE_NM | VW_GROUP_BY_RATE_INFO |
| `nxtRateCd` | 변경후_요금제코드 | `String` | 변경후_요금제코드 | NXT_RATE_CD | VW_GROUP_BY_RATE_INFO |
| `nxtRateNm` | 변경후_요금제명 | `String` | 변경후_요금제명 | NXT_RATE_NM | VW_GROUP_BY_RATE_INFO |
| `nxtPtrnRateYn` | 변경후_제휴요금여부 | `String` | 변경후_제휴요금여부 | NXT_PTRN_RATE_YN | VW_GROUP_BY_RATE_INFO |
| `nxtPayClCd` | 변경후_선후불코드 | `String` | 변경후_선후불코드 | NXT_PAY_CL_CD | VW_GROUP_BY_RATE_INFO |
| `nxtDataType` | 변경후_데이터유형 | `String` | 변경후_데이터유형 | NXT_DATA_TYPE | VW_GROUP_BY_RATE_INFO |
| `nxtRateType` | 변경후_요금제유형 | `String` | 변경후_요금제유형 | NXT_RATE_TYPE | VW_GROUP_BY_RATE_INFO |
| `nxtRmk` | 변경후_요금설명 | `String` | 변경후_요금설명 | NXT_RMK | VW_GROUP_BY_RATE_INFO |
| `nxtBaseAmt` | 변경후_요금금액 | `int` | 변경후_요금금액 | NXT_BASE_AMT | VW_GROUP_BY_RATE_INFO |
| `nxtApplStrtDt` | 변경후_요금제적용시작일자 | `String` | 변경후_요금제적용시작일자 | NXT_APPL_STRT_DT | VW_GROUP_BY_RATE_INFO |
| `nxtApplEndDt` | 변경후_요금적용종료일자 | `String` | 변경후_요금적용종료일자 | NXT_APPL_END_DT | VW_GROUP_BY_RATE_INFO |
| `baseVatAmt` | VAT포함금액 | `int` | VAT포함금액 | NVL(NXT_BASE_AMT,0)+(NVL(NXT_BASE_AMT,0)*0.1) AS BASE_VAT_AMT | VW_GROUP_BY_RATE_INFO |

**SQL**

```sql
SELECT PRV_RATE_GRP_CD , PRV_RATE_GRP_NM , PRV_RATE_GRP_TYPE_CD , PRV_RATE_GRP_TYPE_NM
, PRV_RATE_CD , PRV_RATE_NM , PRV_PTRN_RATE_YN , PRV_PAY_CL_CD , PRV_DATA_TYPE
, PRV_RATE_TYPE , PRV_RMK , PRV_BASE_AMT , PRV_APPL_STRT_DT , PRV_APPL_END_DT
, NXT_RATE_GRP_CD , NXT_RATE_GRP_NM , NXT_RATE_GRP_TYPE_CD , NXT_RATE_GRP_TYPE_NM
, NXT_RATE_CD , NXT_RATE_NM , NXT_PTRN_RATE_YN , NXT_PAY_CL_CD , NXT_DATA_TYPE
, NXT_RATE_TYPE , NXT_RMK , NXT_BASE_AMT , NXT_APPL_STRT_DT , NXT_APPL_END_DT
, NVL(NXT_BASE_AMT,0)+(NVL(NXT_BASE_AMT,0)*0.1) AS BASE_VAT_AMT
FROM VW_GROUP_BY_RATE_INFO@DL_MSP
WHERE PRV_RATE_CD =  #{rateCd}
AND NXT_APPL_END_DT = '99991231'
ORDER BY NXT_BASE_AMT DESC
```


#### API_0061 — countFarPricePlanList

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `countFarPricePlanList` |
| 엔드포인트 | `/mypage/farPricePlanListCount` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | MypageMapper.xml |
| SQL ID | `countFarPricePlanList` |
| parameterType | `java.lang.String` |
| resultType | `int` |
| 사용 여부 | ✅ 사용 |
| SQL 유형 | SQL ID 변경 |
| 대상 테이블 | VW_GROUP_BY_RATE_INFO |
| 담당자 | 강문재 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `rateCd` | 요금제코드 | `String` | 요금제코드 | PRV_RATE_CD | VW_GROUP_BY_RATE_INFO |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `count` | 조회개수 | `int` | 조회개수 | COUNT |  |

**SQL**

```sql
SELECT COUNT(*)
FROM VW_GROUP_BY_RATE_INFO@DL_MSP
WHERE PRV_RATE_CD =  #{rateCd}
AND NXT_APPL_END_DT = '99991231'
```


#### API_0062 — selectFarPriceAddInfo

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `selectFarPriceAddInfo` |
| 엔드포인트 | `/mypage/farPriceAddInfo` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | MypageMapper.xml |
| SQL ID | `selectFarPriceAddInfo` |
| parameterType | `java.util.Map` |
| resultType | `java.lang.String` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_JUO_FEATURE_INFO |
| 담당자 | 강문재 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `cntrNo` | 서비스계약번호 | `Map<String>` | 서비스계약번호 | CONTRACT_NUM | MSP_JUO_FEATURE_INFO |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `appStartDd` | 적용일시 | `String` | 적용일시 | APP_START_DD | MSP_JUO_FEATURE_INFO |

**SQL**

```sql
SELECT APP_START_DD
FROM( SELECT SUBSTR(APP_START_DD,0,6) AS APP_START_DD , ROW_NUMBER() OVER (ORDER BY DECODE(SOC,#{soc},SUBSTR(APP_START_DD,0,6)+1,SUBSTR(APP_START_DD,0,6)) DESC) AS RNUM
FROM MSP_JUO_FEATURE_INFO@DL_MSP
WHERE SERVICE_TYPE='P'
AND CONTRACT_NUM= #{cntrNo}
AND APP_END_DATE = '99991231000000'
)WHERE RNUM=1
```


#### API_0063 — selectJehuList

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `selectJehuList` |
| 엔드포인트 | `/mypage/jehuList` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | MypageMapper.xml |
| SQL ID | `selectJehuList` |
| parameterType | `java.util.Map` |
| resultType | `com.ktmmobile.mcp.mypage.dto.JehuDto` |
| 사용 여부 | ✅ 사용 |
| SQL 유형 | 다중파라미터 사용
mapper > RowBounds 클래스 사용 |
| 대상 테이블 | CMN_GRP_CD_MST, MSP_PARTNER_POINT_MST, MSP_PARTNER_MST |
| 담당자 | 강문재 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `contractNum` | 가입계약번호 | `Map<String>` | 가입계약번호 | CONTRACT_NUM | MSP_PARTNER_POINT_MST |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `partnerId` | 제휴사아이디 | `String` | 제휴사아이디 | PARTNER_ID | MSP_PARTNER_POINT_MST |
| `partnerNm` | 제휴사명 | `String` | 제휴사명 | PARTNER_NM | MSP_PARTNER_MST |
| `billYm` | 청구일 | `String` | 청구일 | BILL_YM | MSP_PARTNER_POINT_MST |
| `svcCntrNo` | 서비스 계약번호 | `String` | 서비스 계약번호 | SVC_CNTR_NO | MSP_PARTNER_POINT_MST |
| `payPoint` | 포인트 | `String` | 포인트 | CASE WHEN A.FULL_PAY_YN = 'N' THEN 0
WHEN A.RESULT_DTL_CD != '0000' then 0
ELSE A.POINT
END AS PAY_POINT | MSP_PARTNER_POINT_MST |
| `fullPayYn` | 완납미납여부 | `String` | 완납미납여부 | DECODE(A.FULL_PAY_YN'Y''완납''미납') AS FULL_PAY_YN | MSP_PARTNER_POINT_MST |
| `resultDtlCd` | 결과상세코드 | `String` | 결과상세코드 | RESULT_DTL_CD | MSP_PARTNER_POINT_MST |

**SQL**

```sql
SELECT
A.PARTNER_ID,
A.PARTNER_NM,
A.BILL_YM,
A.SVC_CNTR_NO,
CASE WHEN A.FULL_PAY_YN = 'N' THEN 0
WHEN A.RESULT_DTL_CD != '0000' then 0
ELSE A.POINT
END AS PAY_POINT,
DECODE(A.FULL_PAY_YN,'Y','완납','미납') AS FULL_PAY_YN,
(SELECT CD_DSC
FROM   CMN_GRP_CD_MST@DL_MSP
WHERE  GRP_ID = 'CMN0200'
AND    ETC1 = A.PARTNER_ID
AND    ETC2 = A.RESULT_DTL_CD) AS RESULT_DTL_CD
FROM   (SELECT
A.PARTNER_ID,
E.PARTNER_NM,
A.BILL_YM,
A.SVC_CNTR_NO,
SUM(A.CAL_POINT) AS POINT,
A.FULL_PAY_YN,
A.RESULT_DTL_CD
FROM   MSP_PARTNER_POINT_MST@DL_MSP A ,
MSP_PARTNER_MST@DL_MSP E
WHERE  A.PARTNER_ID = E.PARTNER_ID
AND    A.CONTRACT_NUM = #{contractNum}
AND    A.BILL_YM||'15' <![CDATA[<]]> TO_CHAR(ADD_MONTHS(SYSDATE,-1),'YYYYMMDD')
AND    A.SEND_FLAG != 'B'
GROUP BY  A.PARTNER_ID, E.PARTNER_NM, A.BILL_YM, A.SVC_CNTR_NO, A.FULL_PAY_YN, A.RESULT_DTL_CD
) A
ORDER BY  A.BILL_YM DESC, A.PARTNER_ID DESC, A.SVC_CNTR_NO DESC
```


#### API_0064 — selectJehuListCnt

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `selectJehuListCnt` |
| 엔드포인트 | `/mypage/jehuListCount` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | MypageMapper.xml |
| SQL ID | `selectJehuListCnt` |
| parameterType | `java.util.Map` |
| resultType | `int` |
| 사용 여부 | ✅ 사용 |
| SQL 유형 | SQL ID 변경 |
| 대상 테이블 | MSP_PARTNER_POINT_MST, MSP_PARTNER_MST |
| 담당자 | 강문재 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `contractNum` | 가입계약번호 | `Map<String>` | 가입계약번호 | CONTRACT_NUM | MSP_PARTNER_POINT_MST |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `count` | 조회개수 | `int` | 조회개수 | COUNT |  |

**SQL**

```sql
SELECT
COUNT(*)
FROM   (SELECT A.PARTNER_ID,
E.PARTNER_NM,
A.BILL_YM,
A.SVC_CNTR_NO,
SUM(A.CAL_POINT) AS CAL_POINT,
A.FULL_PAY_YN,
A.RESULT_DTL_CD
FROM   MSP_PARTNER_POINT_MST@DL_MSP A ,
MSP_PARTNER_MST@DL_MSP E
WHERE  A.PARTNER_ID = E.PARTNER_ID
AND    A.CONTRACT_NUM = #{contractNum}
AND    A.BILL_YM||'15' <![CDATA[<]]> TO_CHAR(ADD_MONTHS(SYSDATE,-1),'YYYYMMDD')
AND    A.SEND_FLAG != 'B'
GROUP BY  A.PARTNER_ID, E.PARTNER_NM, A.BILL_YM, A.SVC_CNTR_NO, A.FULL_PAY_YN, A.RESULT_DTL_CD
) A
ORDER BY  A.BILL_YM DESC, A.PARTNER_ID DESC, A.SVC_CNTR_NO DESC
```


#### API_0065 — selectJehuListAll

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `selectJehuListAll` |
| 엔드포인트 | `/mypage/jehuListAll` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | MypageMapper.xml |
| SQL ID | `selectJehuListAll` |
| parameterType | `java.util.Map` |
| resultType | `com.ktmmobile.mcp.mypage.dto.JehuDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | CMN_GRP_CD_MST, MSP_PARTNER_POINT_MST, MSP_PARTNER_MST |
| 담당자 | 강문재 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `contractNum` | 가입계약번호 | `Map<String>` | 가입계약번호 | CONTRACT_NUM | MSP_PARTNER_POINT_MST |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `partnerId` | 제휴사아이디 | `String` | 제휴사아이디 | PARTNER_ID | MSP_PARTNER_POINT_MST |
| `partnerNm` | 제휴사명 | `String` | 제휴사명 | PARTNER_NM | MSP_PARTNER_MST |
| `billYm` | 청구일 | `String` | 청구일 | BILL_YM | MSP_PARTNER_POINT_MST |
| `svcCntrNo` | 서비스 계약번호 | `String` | 서비스 계약번호 | SVC_CNTR_NO | MSP_PARTNER_POINT_MST |
| `payPoint` | 포인트 | `String` | 포인트 | CASE WHEN A.FULL_PAY_YN = 'N' THEN 0
WHEN A.RESULT_DTL_CD != '0000' then 0
ELSE A.POINT
END AS PAY_POINT | MSP_PARTNER_POINT_MST |
| `fullPayYn` | 완납미납여부 | `String` | 완납미납여부 | DECODE(A.FULL_PAY_YN'Y''완납''미납') AS FULL_PAY_YN | MSP_PARTNER_POINT_MST |
| `resultDtlCd` | 결과상세코드 | `String` | 결과상세코드 | RESULT_DTL_CD | MSP_PARTNER_POINT_MST |

**SQL**

```sql
SELECT
A.PARTNER_ID,
A.PARTNER_NM,
A.BILL_YM,
A.SVC_CNTR_NO,
CASE WHEN A.FULL_PAY_YN = 'N' THEN 0
WHEN A.RESULT_DTL_CD != '0000' then 0
ELSE A.POINT
END AS PAY_POINT,
DECODE(A.FULL_PAY_YN,'Y','완납','미납') AS FULL_PAY_YN,
(SELECT CD_DSC
FROM   CMN_GRP_CD_MST@DL_MSP
WHERE  GRP_ID = 'CMN0200'
AND    ETC1 = A.PARTNER_ID
AND    ETC2 = A.RESULT_DTL_CD) AS RESULT_DTL_CD
FROM   (SELECT
A.PARTNER_ID,
E.PARTNER_NM,
A.BILL_YM,
A.SVC_CNTR_NO,
SUM(A.CAL_POINT) AS POINT,
A.FULL_PAY_YN,
A.RESULT_DTL_CD
FROM   MSP_PARTNER_POINT_MST@DL_MSP A ,
MSP_PARTNER_MST@DL_MSP E
WHERE  A.PARTNER_ID = E.PARTNER_ID
AND    A.CONTRACT_NUM = #{contractNum}
AND    A.BILL_YM||'15' <![CDATA[<]]> TO_CHAR(ADD_MONTHS(SYSDATE,-1),'YYYYMMDD')
AND    A.SEND_FLAG != 'B'
GROUP BY  A.PARTNER_ID, E.PARTNER_NM, A.BILL_YM, A.SVC_CNTR_NO, A.FULL_PAY_YN, A.RESULT_DTL_CD
) A
ORDER BY  A.BILL_YM DESC, A.PARTNER_ID DESC, A.SVC_CNTR_NO DESC
```


#### API_0066 — 회선별 마케팅 수신 동의 여부 조회

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `selectMspMrktAgrYn` |
| 연동명(한글) | 회선별 마케팅 수신 동의 여부 조회 |
| 엔드포인트 | `/mypage/mspMrktAgrYn` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | MypageMapper.xml |
| SQL ID | `selectMspMrktAgrYn` |
| parameterType | `java.lang.String` |
| resultType | `java.util.Map` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_MRKT_AGR_MGMT |
| 담당자 | 강문재 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `contractNum` | 가입계약번호 | `String` | 가입계약번호 | CONTRACT_NUM | MCP_USER_CNTR_MNG |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `agrYn` | 동의여부 | `Map<String>` | 동의여부 | NVL(AGR_YN ,'N') AS AGR_YN | MCP_USER_CNTR_MNG |
| `regstDttm` | 등록일시 | `Map<String>` | 등록일시 | REGST_DTTM | MCP_USER_CNTR_MNG |
| `rvisnDttm` | 수정일시 | `Map<String>` | 수정일시 | RVISN_DTTM | MCP_USER_CNTR_MNG |

**SQL**

```sql
SELECT
NVL(AGR_YN ,'N') AS AGR_YN,
TO_CHAR(REGST_DTTM, 'YYYY.MM.DD') AS REGST_DTTM,
TO_CHAR(RVISN_DTTM, 'YYYY.MM.DD') AS RVISN_DTTM
FROM
MCP_USER_CNTR_MNG A,
MSP_MRKT_AGR_MGMT@DL_MSP B
WHERE
A.SVC_CNTR_NO = #{contractNum}
AND TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS') BETWEEN B.STRT_DTTM(+) AND B.END_DTTM(+)
AND A.SVC_CNTR_NO=B.CONTRACT_NUM(+)
AND ROWNUM=1
```


#### API_0067 — 회선별 마케팅 수신 동의 여부 저장

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `callMspMrktAgr` |
| 연동명(한글) | 회선별 마케팅 수신 동의 여부 저장 |
| 엔드포인트 | `/mypage/callMspMrktAgrYn` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | MypageMapper.xml |
| SQL ID | `callMspMrktAgr` |
| parameterType | `java.util.HashMap` |
| resultType | `` |
| 사용 여부 | ✅ 사용 |
| SQL 유형 | 다중파라미터 사용
mapper 리턴이 void인 경우 |
| 대상 테이블 | CALL P_MSP_MRKT_AGR |
| 담당자 | 강문재 |
| 비고 | FUNCTION |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `ctn` | 휴대폰번호 | `Map<String>` | 휴대폰번호 |  | P_MSP_MRKT_AGR |
| `mrktAgr` | 마케팅동의여부 | `Map<String>` | 마케팅동의여부 |  | P_MSP_MRKT_AGR |

**SQL**

```sql
{CALL P_MSP_MRKT_AGR@DL_MSP(#{ctn}, #{mrktAgr}, 'MCP')}
```


#### API_0068 — 선불 요금제 사용 여부 조회

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `selectPrePayment` |
| 연동명(한글) | 선불 요금제 사용 여부 조회 |
| 엔드포인트 | `/mypage/prePayment` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | MypageMapper.xml |
| SQL ID | `selectPrePayment` |
| parameterType | `java.lang.String` |
| resultType | `int` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_JUO_FEATURE_INFO, MSP_RATE_MST |
| 담당자 | 강문재 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `contractNum` | 가입계약번호 | `String` | 가입계약번호 | CONTRACT_NUM | MSP_JUO_FEATURE_INFO |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `count` | 조회개수 | `int` | 조회개수 | COUNT |  |

**SQL**

```sql
SELECT
COUNT(*)
FROM
MSP_JUO_FEATURE_INFO@DL_MSP A,
MSP_RATE_MST@DL_MSP B
WHERE
A.CONTRACT_NUM = #{contractNum}
AND A.SERVICE_TYPE = 'P'
AND A.EXPIRATION_DATE > TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')
AND A.SOC = B.RATE_CD
AND B.PAY_CL_CD = 'PP'
```


#### API_0069 — getCommendStateList

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `getCommendStateList` |
| 엔드포인트 | `/mypage/commendStateList` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | MypageMapper.xml |
| SQL ID | `getCommendStateList` |
| parameterType | `java.lang.String` |
| resultType | `com.ktmmobile.mcp.mypage.dto.CommendStateDto` |
| 사용 여부 | ✅ 사용 |
| SQL 유형 | SQL ID 변경 |
| 대상 테이블 | MSP_JUO_SUB_INFO |
| 담당자 | 강문재 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `commendId` | 추천 아이디 | `String` | 추천 아이디 | COMMEND_ID | MCP_REQUEST_COMMEND |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `commendId` | 추천 아이디 | `String` | 추천 아이디 | COMMEND_ID | MCP_REQUEST_COMMEND |
| `reqBuyType` | 구매유형 단말 | `String` | 구매유형 단말      * 구매:MM      * USIM(유심)단독 구매:UU | REQ_BUY_TYPE | MCP_REQUEST_COMMEND |
| `contractNum` | 가입계약번호 | `String` | 가입계약번호 | CONTRACT_NUM | MCP_REQUEST |
| `lstComActvDate` | 최초 개통일자 | `String` | 최초 개통일자 | LST_COM_ACTV_DATE | MCP_REQUEST_COMMEND |

**SQL**

```sql
SELECT
LST_COM_ACTV_DATE
,REQ_BUY_TYPE
,COMMEND_ID
,COUNT(1) AS sumCount
FROM
(
SELECT
COMMEND_ID
, REQ_BUY_TYPE
, T2.CONTRACT_NUM
, SUBSTR(LST_COM_ACTV_DATE, 0, 6) AS LST_COM_ACTV_DATE
FROM
MCP_REQUEST_COMMEND T1
, MCP_REQUEST T2
, MSP_JUO_SUB_INFO@DL_MSP T3
WHERE 1=1
AND T1.REQUEST_KEY = T2.REQUEST_KEY
AND T2.CONTRACT_NUM = T3.CONTRACT_NUM
AND COMMEND_ID = #{commendId}
AND T1.SYS_RDATE > TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE,-2),'yyyy-mm') || '-01' , 'yyyy-mm-dd')
AND T2.CONTRACT_NUM IS NOT NULL
AND T3.SUB_STATUS <![CDATA[<>]]>  'C'
)
GROUP BY LST_COM_ACTV_DATE,REQ_BUY_TYPE,COMMEND_ID
```


#### API_0070 — selectSvcCntrNo

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `selectSvcCntrNo` |
| 엔드포인트 | `/mypage/svcCntrNo` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | MypageMapper.xml |
| SQL ID | `selectSvcCntrNo` |
| parameterType | `java.util.Map` |
| resultType | `java.lang.String` |
| 사용 여부 | ✅ 사용 |
| SQL 유형 | 다중파라미터 사용 |
| 대상 테이블 | MSP_JUO_SUB_INFO |
| 담당자 | 강문재 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `mobileNo` | 핸드폰번호 | `String` | 핸드폰번호 | SUBSCRIBER_NO | MSP_JUO_SUB_INFO |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `svcCntrNo` | 서비스 계약번호 | `String` | 서비스 계약번호 | SVC_CNTR_NO | MSP_JUO_SUB_INFO |

**SQL**

```sql
SELECT
SVC_CNTR_NO
FROM MSP_JUO_SUB_INFO@DL_MSP
WHERE 1=1
AND SUB_STATUS = 'A'
AND SUBSCRIBER_NO= TRIM(#{mobileNo})
AND UPPER(REPLACE(SUB_LINK_NAME,' ','')) = UPPER(REPLACE(#{name},' ',''))
```


#### API_0071 — selectSocDescNoLogin

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `selectSocDescNoLogin` |
| 엔드포인트 | `/mypage/socDescNoLogin` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | MypageMapper.xml |
| SQL ID | `selectSocDescNoLogin` |
| parameterType | `` |
| resultType | `` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_JUO_ADD_INFO, MSP_JUO_FEATURE_INFO, MSP_RATE_MST, MSP_JUO_SUB_INFO |
| 담당자 | 강문재 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `svcCntrNo` | 서비스 계약번호 | `String` | 서비스 계약번호 | CONTRACT_NUM | MSP_JUO_ADD_INFO |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `svcCntrNo` | 서비스 계약번호 | `String` | 서비스 계약번호 | SVC_CNTR_NO | MSP_JUO_SUB_INFO |
| `soc` | 상품코드 | `String` | 상품코드 | SOC | MSP_JUO_FEATURE_INFO |
| `enggMnthCnt` | 약정개월수 | `long` | 약정개월수 | ENGG_MNTH_CNT | MSP_JUO_ADD_INFO |
| `rateNm` | 요금제명 | `String` | 요금제명 | RATE_NM | MSP_RATE_MST |
| `baseAmt` | 기본료 | `int` | 기본료 | BASE_AMT | MSP_RATE_MST |
| `rmk` | 비고 | `String` | 비고 | RMK | MSP_RATE_MST |
| `modelName` | 단말기 모델명 | `String` | 단말기 모델명 | MODEL_NAME | MSP_JUO_SUB_INFO |
| `dobyyyymmdd` | 생년월일 | `String` | 생년월일 | DOBYYYYMMDD | MSP_JUO_SUB_INFO |
| `userSsn` | 주민등록번호 | `String` | 주민등록번호 | USER_SSN | MSP_JUO_SUB_INFO |
| `intmSrlNo` | 단말기일련번호 | `String` | 단말기일련번호 | INTM_SRL_NO | MSP_JUO_SUB_INFO |
| `modelId` | 모델ID | `String` | 모델ID | MODEL_ID | MSP_JUO_SUB_INFO |
| `dcAmt` | 할인금액(약정할인선택시 할인금액) | `int` | 할인금액(약정할인선택시 할인금액) | DC_AMT | MSP_JUO_FEATURE_INFO |
| `pointId` | 제주항공 회원번호 | `String` | 제주항공 회원번호 | POINT_ID | MSP_JUO_FEATURE_INFO |
| `subLinkName` | 실사용자이름 | `String` | 실사용자이름 | SUB_LINK_NAME | MSP_JUO_SUB_INFO |

**SQL**

```sql
SELECT
AA.SVC_CNTR_NO
, AA.SOC
, AA.ENGG_MNTH_CNT
, AA.RATE_NM
, AA.BASE_AMT
, AA.RMK
, AA.MODEL_NAME
, AA.DOBYYYYMMDD
, AA.USER_SSN
, AA.INTM_SRL_NO
, AA.MODEL_ID
, NVL(AA.DC_AMT, 0) DC_AMT
, POINT_ID
, AA.SUB_LINK_NAME
FROM
(
SELECT
D.SVC_CNTR_NO
, D.CONTRACT_NUM
, B.SOC
, NVL((SELECT ENGG_MNTH_CNT
FROM (SELECT ENGG_MNTH_CNT , ROW_NUMBER() OVER (ORDER BY EVNT_TRTM_DT DESC) AS RNUM
FROM MSP_JUO_ADD_INFO@DL_MSP
WHERE CONTRACT_NUM = #{svcCntrNo}
AND ENGG_MNTH_CNT IS NOT NULL
AND APP_END_DATE > TO_CHAR (SYSDATE, 'YYYYMMDDHH24MISS')
)WHERE RNUM = 1
), 0) AS ENGG_MNTH_CNT
, C.RATE_NM
, C.BASE_AMT
, C.RMK
, D.MODEL_NAME
, D.DOBYYYYMMDD
, D.USER_SSN
, D.INTM_SRL_NO
, D.MODEL_ID
, '' AS POINT_ID
, 0 AS DC_AMT
, D.SUB_LINK_NAME
FROM
MSP_JUO_FEATURE_INFO@DL_MSP B
, MSP_RATE_MST@DL_MSP C
, MSP_JUO_SUB_INFO@DL_MSP D
WHERE 1=1
AND D.SVC_CNTR_NO = #{svcCntrNo}
AND D.CONTRACT_NUM = B.CONTRACT_NUM
AND B.SERVICE_TYPE = 'P'
AND B.EXPIRATION_DATE > TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')
AND B.SOC = C.RATE_CD
) AA
```


#### API_0072 — 계약 현행화 정보

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `selectCntrListNoLogin` |
| 연동명(한글) | 계약 현행화 정보 |
| 엔드포인트 | `/mypage/cntrListNoLogin` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | MypageMapper.xml |
| SQL ID | `selectCntrListNoLogin` |
| parameterType | `com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto` |
| resultType | `` |
| 사용 여부 | ✅ 사용 |
| SQL 유형 | 파라미터를 DTO로 넘김 |
| 대상 테이블 | MSP_JUO_SUB_INFO |
| 담당자 | 강문재 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `svcCntrNo` | 서비스 계약번호 | `String` | 서비스 계약번호 | SVC_CNTR_NO | MSP_JUO_SUB_INFO |
| `cntrMobileNo` | 휴대폰번호 | `String` | 휴대폰번호 | SUBSCRIBER_NO | MSP_JUO_SUB_INFO |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `svcCntrNo` | 서비스 계약번호 | `String` | 서비스 계약번호 | SVC_CNTR_NO | MSP_JUO_SUB_INFO |
| `contractNum` | 가입계약번호 | `String` | 가입계약번호 | CONTRACT_NUM | MSP_JUO_SUB_INFO |
| `custId` | 고객 ID | `String` | 고객 ID | CUSTOMER_ID AS CUST_ID | MSP_JUO_SUB_INFO |
| `modelName` | 단말기 모델명 | `String` | 단말기 모델명 | MODEL_NAME | MSP_JUO_SUB_INFO |
| `modelId` | 모델ID | `String` | 모델ID | MODEL_ID | MSP_JUO_SUB_INFO |
| `cntrMobileNo` | 휴대폰번호 | `String` | 휴대폰번호 | SUBSCRIBER_NO AS CNTR_MOBILE_NO | MSP_JUO_SUB_INFO |
| `subStatus` | 상태값 | `String` | 상태값 | SUB_STATUS | MSP_JUO_SUB_INFO |

**SQL**

```sql
SELECT
SVC_CNTR_NO
, CONTRACT_NUM
, CUSTOMER_ID AS CUST_ID
, MODEL_NAME
, MODEL_ID
, SUBSCRIBER_NO AS CNTR_MOBILE_NO
, SUB_STATUS
FROM MSP_JUO_SUB_INFO@DL_MSP
WHERE 1=1
<if test="svcCntrNo != null and svcCntrNo != ''">
AND SVC_CNTR_NO = #{svcCntrNo}
</if>
<if test="cntrMobileNo != null and cntrMobileNo != ''">
AND SUBSCRIBER_NO= TRIM(#{cntrMobileNo})
</if>
<if test="subLinkName != null and subLinkName != ''">
AND UPPER(REPLACE(SUB_LINK_NAME,' ','')) = UPPER(REPLACE(#{subLinkName},' ',''))
</if>
AND SUB_STATUS <![CDATA[<>]]> 'C'
```


#### API_0073 — 부가 서비스 존재 여부(3개월 이내 결합이력이 존재하여 결합이 불가)

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `getJuoFeature` |
| 연동명(한글) | 부가 서비스 존재 여부(3개월 이내 결합이력이 존재하여 결합이 불가) |
| 엔드포인트 | `/mypage/juoFeature` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | MypageMapper.xml |
| SQL ID | `getJuoFeature` |
| parameterType | `com.ktmmobile.mcp.mypage.dto.JuoFeatureDto` |
| resultType | `` |
| 사용 여부 | ✅ 사용 |
| SQL 유형 | 파라미터를 DTO로 넘김
SQL ID 변경 |
| 대상 테이블 | MSP_JUO_FEATURE_INFO |
| 담당자 | 강문재 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `contractNum` | 가입계약번호 | `String` | 가입계약번호 | CONTRACT_NUM | MSP_JUO_FEATURE_INFO |
| `soc` | 상품코드 | `String` | 상품코드 | SOC | MSP_JUO_FEATURE_INFO |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `expirationDate` | 만기일시 | `String` | 만기일시 | EXPIRATION_DATE | MSP_JUO_FEATURE_INFO |

**SQL**

```sql
SELECT
EXPIRATION_DATE
FROM MSP_JUO_FEATURE_INFO@DL_MSP
WHERE
CONTRACT_NUM = #{contractNum}
AND SOC = #{soc}
AND SYSDATE BETWEEN TO_DATE(EFFECTIVE_DATE, 'YYYYMMDDHH24MISS') AND ADD_MONTHS(TO_DATE(EXPIRATION_DATE, 'YYYYMMDDHH24MISS'),#{intAddMonths})
AND ROWNUM = 1
```


#### API_0131 — userId 기준 마케팅 수신 동의 여부 히스토리 저장 프로시져 호출

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `callMcpMrktAgr` |
| 연동명(한글) | userId 기준 마케팅 수신 동의 여부 히스토리 저장 프로시져 호출 |
| 엔드포인트 | `/mypage/callMcpMrktAgr` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | MypageMapper.xml |
| SQL ID | `callMcpMrktAgr` |
| parameterType | `java.util.HashMap` |
| resultType | `void` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | P_MCP_MRKT_AGR |
| 담당자 | 박정웅 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `userId` |  | `string` |  |  | P_MCP_MRKT_AGR |
| `emailAgrYn` |  | `string` |  |  | P_MCP_MRKT_AGR |
| `smsAgrYn` |  | `string` |  |  | P_MCP_MRKT_AGR |

**SQL**

```sql
{CALL P_MCP_MRKT_AGR(#{userId},#{emailAgrYn},#{smsAgrYn})}
```


#### API_0132 — userId 기준 마케팅 수신 동의 여부 히스토리 저장 프로시져 호출(신규가입시)

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `callMcpMrktAgrNew` |
| 연동명(한글) | userId 기준 마케팅 수신 동의 여부 히스토리 저장 프로시져 호출(신규가입시) |
| 엔드포인트 | `/mypage/callMcpMrktAgrNew` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | MypageMapper.xml |
| SQL ID | `callMcpMrktAgrNew` |
| parameterType | `java.util.HashMap` |
| resultType | `void` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | P_MCP_MRKT_AGR_NEW |
| 담당자 | 장성환 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `userId` |  | `string` |  |  | P_MCP_MRKT_AGR_NEW |
| `emailAgrYn` |  | `string` |  |  | P_MCP_MRKT_AGR_NEW |
| `smsAgrYn` |  | `string` |  |  | P_MCP_MRKT_AGR_NEW |

**SQL**

```sql
{CALL P_MCP_MRKT_AGR_NEW(#{userId},#{emailAgrYn},#{smsAgrYn})}
```


#### API_0133 — 금융제휴상품요금제 변경 서식지 이미지 합본 프로시져

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `callMcpRateChgImg` |
| 연동명(한글) | 금융제휴상품요금제 변경 서식지 이미지 합본 프로시져 |
| 엔드포인트 | `/mypage/callMcpRateChgImg` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | MypageMapper.xml |
| SQL ID | `callMcpRateChgImg` |
| parameterType | `com.ktmmobile.mcp.mypage.dto.McpRateChgDto` |
| resultType | `java.util.Map` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | P_MCP_RATE_CHG |
| 담당자 | 박정웅 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `chgSeq` |  | `string` |  |  | P_MCP_RATE_CHG |
| `contractNum` |  | `string` |  |  | P_MCP_RATE_CHG |

**SQL**

```sql
{CALL P_MCP_RATE_CHG(#{chgSeq}, #{contractNum})}
```


#### API_0142 — getEnggInfo

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `getEnggInfo` |
| 엔드포인트 | `/mypage/enggInfo1` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | MypageMapper.xml |
| SQL ID | `getEnggInfo` |
| parameterType | `string` |
| resultType | `com.ktmmobile.mcp.mypage.dto.MspJuoAddInfoDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | VW_CNTR_ENGG_SELF |
| 담당자 | 백승주 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `contractNum` |  | `string` |  | CONTRACT_NUM | VW_CNTR_ENGG_SELF |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `contractNum` | 계약번호 | `string` | 계약번호 | CONTRACT_NUM | VW_CNTR_ENGG_SELF |
| `subscriberNo` | 전화번호 | `string` | 전화번호 | SUBSCRIBER_NO | VW_CNTR_ENGG_SELF |
| `appEndDate` | 약정종료일자 | `string` | 약정종료일자 | APP_END_DATE | VW_CNTR_ENGG_SELF |
| `enggYn` | 약정여부 | `string` | 약정여부 | ENGG_YN | VW_CNTR_ENGG_SELF |

**SQL**

```sql
SELECT /*Mypage.getEnggInfo 약정정보   */
<![CDATA[
CONTRACT_NUM -- 계약번호
, SUBSCRIBER_NO -- 전화번호
, APP_END_DATE  -- 약정종료일자  appEndDate
, CASE
WHEN APP_END_DATE  < TO_CHAR(SYSDATE , 'YYYYMMDD') THEN 'N'
WHEN APP_END_DATE  >= TO_CHAR(SYSDATE , 'YYYYMMDD') THEN 'Y'
WHEN APP_END_DATE IS NULL THEN  'N'
END  AS ENGG_YN -- 약정여부
]]>
FROM (
SELECT
CONTRACT_NUM
,SUBSCRIBER_NO
,ENGG_END_DT AS APP_END_DATE
FROM VW_CNTR_ENGG_SELF@DL_MSP
WHERE CONTRACT_NUM = #{contractNum}
)
```


#### API_0143 — getChannelInfo

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `getChannelInfo` |
| 엔드포인트 | `/mypage/channelInfo` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | MypageMapper.xml |
| SQL ID | `getChannelInfo` |
| parameterType | `string` |
| resultType | `com.ktmmobile.mcp.mypage.dto.MspJuoAddInfoDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_JUO_SUB_INFO, CMN_GRP_CD_MST |
| 담당자 | 백승주 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `contractNum` |  | `string` |  | CONTRACT_NUM | MSP_JUO_SUB_INFO |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `agentCd` |  | `string` |  | AGENT_CD | MSP_JUO_SUB_INFO |
| `agentNm` |  | `string` |  | AGENT_NM | CMN_GRP_CD_MST |
| `channelCd` |  | `string` |  | CHANNEL_CD | CMN_GRP_CD_MST |
| `channelNm` |  | `string` |  | CHANNEL_NM | CMN_GRP_CD_MST |

**SQL**

```sql
SELECT  /*Mypage.getChannelInfo 개통 채널정보   */
SUB.OPEN_AGNT_CD AS AGENT_CD
, CMN.CD_DSC AS AGENT_NM
, CMN.ETC1 AS CHANNEL_CD
, CMN.ETC2 AS CHANNEL_NM
FROM
MSP_JUO_SUB_INFO@DL_MSP SUB
, CMN_GRP_CD_MST@DL_MSP CMN
WHERE
SUB.OPEN_AGNT_CD = CMN.CD_VAL
AND CMN.GRP_ID = 'RCP9031'
AND CMN.USG_YN = 'Y'
AND SUB.CONTRACT_NUM = #{contractNum}
```


#### API_0144 — getCloseSubList

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `getCloseSubList` |
| 엔드포인트 | `/mypage/closeSubList` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | MypageMapper.xml |
| SQL ID | `getCloseSubList` |
| parameterType | `string` |
| resultType | `com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_JUO_FEATURE_INFO, CMN_GRP_CD_MST, MSP_JUO_SUB_INFO |
| 담당자 | 백승주 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `contractNum` |  | `string` |  | CONTRACT_NUM | MSP_JUO_SUB_INFO |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `socNm` |  | `string` |  | SOC_NM | CMN_GRP_CD_MST |
| `socCode` |  | `string` |  | SOC_CODE | MSP_JUO_FEATURE_INFO |

**SQL**

```sql
SELECT /*Mypage.getCloseSubList 해지 해야할 부가 서비스 리스트  */
CMN.CD_DSC AS SOC_NM
, FEA.SOC AS SOC_CODE
FROM
MSP_JUO_FEATURE_INFO@DL_MSP FEA
, CMN_GRP_CD_MST@DL_MSP CMN
, MSP_JUO_SUB_INFO@DL_MSP SUB
WHERE 1=1
AND FEA.CONTRACT_NUM = SUB.CONTRACT_NUM
AND FEA.SOC = CMN.CD_VAL
AND CMN.GRP_ID = 'RCP9030'
AND CMN.USG_YN = 'Y'
AND EXPIRATION_DATE >= TO_CHAR(SYSDATE , 'YYYYMMDDHH24MISS')
AND SUB.CONTRACT_NUM=  #{contractNum}
AND SUB.SUB_STATUS = 'A'
ORDER BY SOC
```


#### API_0145 — insertSocfailProcMst

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `insertSocfailProcMst` |
| 엔드포인트 | `/mypage/insertSocfailProcMst` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | MypageMapper.xml |
| SQL ID | `insertSocfailProcMst` |
| parameterType | `com.ktmmobile.mcp.mypage.dto.McpServiceAlterTraceDto` |
| resultType | `` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_SOCFAIL_PROC_MST |
| 담당자 | 백승주 |
| 비고 | INSERT |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `contractNum` | 계약번호 | `string` | 계약번호 | CONTRACT_NUM | MSP_SOCFAIL_PROC_MST |
| `prcsMdlInd` | 처리 모듈 구분 | `string` | 처리 모듈 구분 | PRCS_MDL_IND |  |
| `aSocCode` | 이전 코드 값 | `string` | 이전 코드 값 | A_SOC_CODE |  |
| `tSocCode` | 이후 코드 값 | `string` | 이후 코드 값 | T_SOC_CODE |  |
| `aSocAmnt` | 변경전 요금제 할인 월정액 | `string` | 변경전 요금제 할인 월정액 | A_SOC_AMNT |  |
| `tSocAmnt` | 변경후 요금제 할인 월정액 | `string` | 변경후 요금제 할인 월정액 | T_SOC_AMNT |  |
| `succYn` | 성공여부 | `string` | 성공시 : Y , 실패시 : N | SUCC_YN |  |
| `sysRdate` | 등록일시 | `string` | 등록일시 | SYS_RDATE |  |

**SQL**

```sql
INSERT INTO /*Mypage.insertSocfailProcMst 요금제셀프변경실패 INSERT  */
MSP_SOCFAIL_PROC_MST@DL_MSP (
CONTRACT_NUM                      --계약번호
, PRCS_MDL_IND             --처리 모듈 구분
, A_SOC_CODE               --이전 코드 값
, T_SOC_CODE               --이후 코드 값
, A_SOC_AMNT               --변경전 요금제 할인 월정액
, T_SOC_AMNT               --변경후 요금제 할인 월정액
, SUCC_YN                  --성공여부  ==> 성공시 : Y , 실패시 : N
, SYS_RDATE                --등록일시
) VALUES (
#{contractNum}
, #{prcsMdlInd}
, #{aSocCode}
, #{tSocCode}
, #{aSocAmnt}
, #{tSocAmnt}
, #{succYn}
, SYSDATE
)
```


#### API_0146 — getromotionDcList

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `getromotionDcList` |
| 엔드포인트 | `/mypage/romotionDcList` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | MypageMapper.xml |
| SQL ID | `getromotionDcList` |
| parameterType | `string` |
| resultType | `com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | VW_CHRG_PRMT_DTL, MCP_ADDITION |
| 담당자 | 백승주 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `rateCd` | 요금제코드 | `string` | 요금제코드 | RATE_CD | VW_CHRG_PRMT_DTL |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `socCode` |  | `string` |  | SOC_CODE | MCP_ADDITION |
| `socNm` |  | `string` |  | SOC_NM | MCP_ADDITION |
| `socPrice` |  | `string` |  | SOC_PRICE | MCP_ADDITION |

**SQL**

```sql
SELECT /*Mypage.getromotionDcList 가입 해야할 부가 서비스 리스트  */
DISTINCT
B.SOC AS SOC_CODE
, ADDITION_NAME AS SOC_NM
, RANTAL AS SOC_PRICE
FROM
VW_CHRG_PRMT_DTL@DL_MSP A
, MCP_ADDITION B
WHERE 1=1
AND A.ADDITION_KEY = B.ADDITION_KEY
AND TO_CHAR(SYSDATE, 'YYYYMMDD') BETWEEN A.STRT_DT AND A.END_DT
AND A.ORGN_ID = '1100011741'  --접점코드_직영온라인
AND A.REQ_BUY_TYPE IN('UU','AL')  -- 유심
AND A.ENGG_CNT_0 = 'Y' --무약정
AND A.MNP_YN = 'Y'
AND A.RATE_CD = #{rateCd} --요금제코드
AND A.ON_OFF_TYPE = '0' --온라인오프라인구분 (온라인 )
AND A.USG_YN = 'Y'
```


---

### MSP 도메인 (26개)

#### API_0024 — 단품모델ID로 핸드폰정보를 조회

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `findMspPhoneInfo` |
| 연동명(한글) | 단품모델ID로 핸드폰정보를 조회 |
| 엔드포인트 | `/msp/mspPhoneInfo` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | MspMapper.xml |
| SQL ID | `findMspPhoneInfo` |
| parameterType | `java.lang.String` |
| resultType | `com.ktmmobile.mcp.phone.dto.PhoneMspDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | CMN_INTM_MDL |
| 담당자 | 김대원 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `prdtId` | 단말ID | `String` | 단말ID | PRDT_ID | CMN_INTM_MDL |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `prdtId` | 단말ID | `String` | 단말ID | PRDT_ID | CMN_INTM_MDL |
| `rprsPrdtId` | 대표모델아이디 | `String` | 대표모델아이디 | RPRS_PRDT_ID | CMN_INTM_MDL |
| `rprsYn` | 대표제품 여부 | `String` | 대표제품 여부 | RPRS_YN | CMN_INTM_MDL |
| `prdtNm` | 상품명 | `String` | 상품명 | PRDT_NM | CMN_INTM_MDL |
| `prdtCode` | 제품코드 | `String` | 제품코드 | PRDT_CODE | CMN_INTM_MDL |
| `prdtTypeCd` | 제품유형코드 | `String` | 제품유형코드 | PRDT_TYPE_CD | CMN_INTM_MDL |
| `prdtIndCd` | 제품구분코드 | `String` | 제품구분코드  (LTE:04, 3G:03) | PRDT_IND_CD | CMN_INTM_MDL |
| `prdtColrCd` | 색상코드 | `String` | 색상코드 | PRDT_COLR_CD | CMN_INTM_MDL |
| `mnfctId` | 제조사 ID | `String` | 제조사 ID | MNFCT_ID | CMN_INTM_MDL |
| `prdtLnchDt` | 제품 출시일자 | `String` | 제품 출시일자 | PRDT_LNCH_DT | CMN_INTM_MDL |
| `prdtDt` | 제품단종일자 | `String` | 제품단종일자 | PRDT_DT | CMN_INTM_MDL |

**SQL**

```sql
SELECT
A.PRDT_ID,
A.RPRS_PRDT_ID,
A.RPRS_YN,
A.PRDT_NM,
A.PRDT_CODE,
A.PRDT_TYPE_CD,
A.PRDT_IND_CD,
A.PRDT_COLR_CD,
A.MNFCT_ID,
A.PRDT_LNCH_DT,
A.PRDT_DT
FROM
CMN_INTM_MDL@DL_MSP A
WHERE
A.PRDT_DT >= TO_CHAR(SYSDATE, 'YYYYMMDD')
AND A.PRDT_ID = #{prdtId}
```


#### API_0025 — 판매정책정보를 조회한다.상품과 상관없이 기관별 조회

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `findMspSalePlcyInfoByOnlyOrgn` |
| 연동명(한글) | 판매정책정보를 조회한다.상품과 상관없이 기관별 조회 |
| 엔드포인트 | `/msp/mspSalePlcyInfoByOnlyOrgn` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | MspMapper.xml |
| SQL ID | `findMspSalePlcyInfoByOnlyOrgn` |
| parameterType | `com.ktmmobile.mcp.mspservice.dto.MspSalePlcyMstDto` |
| resultType | `com.ktmmobile.mcp.mspservice.dto.MspSalePlcyMstDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_SALE_PLCY_MST, MSP_SALE_ORGN_MST |
| 담당자 | 김대원 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `plcyTypeCd` | 정책유형코드 | `String` | 정책유형코드 M:오프라인, O:온라인, D:직영온라인 | PLCY_TYPE_CD | MSP_SALE_PLCY_MST |
| `plcySctnCd` | 정책구분코드 | `String` | 정책구분코드 할부개월표시(외부서식지)      * 01 :  단말 구매   할부 개월 리스트 표현      * 02 :   USIM(유심)단독  | PLCY_SCTN_CD | MSP_SALE_PLCY_MST |
| `orgnId` | 조직코드 | `String` | 조직코드 | ORGN_ID | MSP_SALE_ORGN_MST |
| `prdtSctnCd` | 제품구분코드 | `String` | 제품구분코드(02:3G , 03:LTE , 5G ) | PRDT_SCTN_CD | MSP_SALE_PLCY_MST |
| `sprtTp` | 지원금유형 | `String` | KD:단말할인,PM:요금할인 | SPRT_TP | MSP_SALE_PLCY_MST |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `salePlcyCd` | 판매정책코드 | `String` | 판매정책코드 | SALE_PLCY_CD | MSP_SALE_PLCY_MST |
| `salePlcyNm` | 판매정책명 | `String` | 판매정책명 | SALE_PLCY_NM | MSP_SALE_PLCY_MST |
| `saleStrtDttm` | 판매시작일시 | `String` | 판매시작일시 | SALE_STRT_DTTM | MSP_SALE_PLCY_MST |
| `saleEndDttm` | 판매종료일시 | `String` | 판매종료일시 | SALE_END_DTTM | MSP_SALE_PLCY_MST |
| `plcyTypeCd` | 정책유형코드 | `String` | 정책유형코드 M:오프라인, O:온라인, D:직영온라인 | PLCY_TYPE_CD | MSP_SALE_PLCY_MST |
| `orgnType` | 조직유형 | `String` | 조직유형 | ORGN_TYPE | MSP_SALE_PLCY_MST |
| `plcySctnCd` | 정책구분코드 | `String` | 정책구분코드 할부개월표시(외부서식지)      * 01 :  단말 구매   할부 개월 리스트 표현      * 02 :   USIM(유심)단독  | PLCY_SCTN_CD | MSP_SALE_PLCY_MST |
| `prdtSctnCd` | 제품구분코드 | `String` | 제품구분코드(02:3G , 03:LTE , 5G ) | PRDT_SCTN_CD | MSP_SALE_PLCY_MST |
| `applSctnCd` | 적용구분코드 | `String` | 적용구분코드 O:개통일, R:접수일 | APPL_SCTN_CD | MSP_SALE_PLCY_MST |
| `instRate` | 할부이자율 | `BigDecimal` | 할부이자율 | INST_RATE | MSP_SALE_PLCY_MST |
| `newYn` | 신규여부 | `String` | 신규여부 | NEW_YN | MSP_SALE_PLCY_MST |
| `mnpYn` | 번호이동여부 | `String` | 번호이동여부 | MNP_YN | MSP_SALE_PLCY_MST |
| `hcnYn` | 일반 기변여부 | `String` | 일반 기변여부 | HCN_YN | MSP_SALE_PLCY_MST |
| `hdnYn` | 우수기변여부 | `String` | 우수기변여부 | HDN_YN | MSP_SALE_PLCY_MST |
| `cnfmYn` | 확정여부 | `String` | 확정여부 | CNFM_YN | MSP_SALE_PLCY_MST |
| `cnfmId` | 확정자ID | `String` | 확정자ID | CNFM_ID | MSP_SALE_PLCY_MST |
| `cnfmDttm` | 확정일시 | `String` | 확정일시 | CNFM_DTTM | MSP_SALE_PLCY_MST |
| `regstId` | 등록자ID | `String` | 등록자ID | REGST_ID | MSP_SALE_PLCY_MST |
| `regstDttm` | 등록일시 | `Date` | 등록일시 | REGST_DTTM | MSP_SALE_PLCY_MST |
| `rvisnId` | 수정자ID | `String` | 수정자ID | RVISN_ID | MSP_SALE_PLCY_MST |
| `rvisnDttm` | 수정일시 | `Date` | 수정일시 | RVISN_DTTM | MSP_SALE_PLCY_MST |
| `sprtTp` | 지원금유형 | `String` | 지원금유형 (단말할인:KD , 요금할인:PM)      * 단말할인:KD      * 요금할인:PM      * 심플할인:SM   (유심, 약정 | SPRT_TP | MSP_SALE_PLCY_MST |
| `orgnId` | 조직코드 | `String` | 조직코드 | ORGN_ID | MSP_SALE_ORGN_MST |

**SQL**

```sql
SELECT
PL.SALE_PLCY_CD ,					--판매정책코드
PL.SALE_PLCY_NM,                    --판매정책명
PL.SALE_STRT_DTTM,                  --판매시작일시
PL.SALE_END_DTTM,                   --판매종료일시
PL.PLCY_TYPE_CD,                    --정책유형코드 M:오프라인, O:온라인
PL.ORGN_TYPE,                       --조직유형
PL.PLCY_SCTN_CD,                    --정책구분코드 01:단말, 02:USIM
PL.PRDT_SCTN_CD,                    --제품구분코   02:3G, 03:LTE
PL.APPL_SCTN_CD,                    --적용구분코드 O:개통일, R:접수일
PL.INST_RATE,                       --할부이자율
PL.NEW_YN,                          --신규여부
PL.MNP_YN,                          --번호이동여부
PL.HCN_YN,                          --일반 기변여부
PL.HDN_YN,                          --우수기변여부
PL.CNFM_YN,                         --확정여부
PL.CNFM_ID,                         --확정자ID
PL.CNFM_DTTM,                       --확정일시
PL.REGST_ID,                        --등록자ID
PL.REGST_DTTM,                      --등록일시
PL.RVISN_ID,                        --수정자ID
PL.RVISN_DTTM,                      --수정일시
PL.SPRT_TP,                         --지원금유형
ORG.ORGN_ID
FROM
MSP_SALE_PLCY_MST@DL_MSP PL,
MSP_SALE_ORGN_MST@DL_MSP ORG
WHERE
TO_CHAR(SYSDATE,'yyyymmddhh24miss') BETWEEN PL.SALE_STRT_DTTM AND PL.SALE_END_DTTM --판매기간 (필수)
AND PL.CNFM_YN='Y'      								--결제 YN (필수)
AND PL.SALE_PLCY_CD = ORG.SALE_PLCY_CD
AND PL.PLCY_TYPE_CD = #{plcyTypeCd}   -- 오프라인:M , 직영온라인 : D
AND PL.PLCY_SCTN_CD = #{plcySctnCd}   -- 01:단말,02:유심
AND ORG.ORGN_ID     = #{orgnId}
<if test="prdtSctnCd != null and prdtSctnCd != ''">
AND PL.PRDT_SCTN_CD = #{prdtSctnCd}   -- LTE,3G
</if>
<if test="sprtTp != null and sprtTp != ''">
AND PL.SPRT_TP 		= #{sprtTp}   		-- KD:단말할인,PM:요금할인
</if>
```


#### API_0026 — 판매정책정보를 조회

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `findMspSaleOrgnMst` |
| 연동명(한글) | 판매정책정보를 조회 |
| 엔드포인트 | `/msp/mspSaleOrgnMst` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | MspMapper.xml |
| SQL ID | `findMspSaleOrgnMst` |
| parameterType | `com.ktmmobile.mcp.mspservice.dto.MspSalePlcyMstDto` |
| resultType | `com.ktmmobile.mcp.mspservice.dto.MspSalePlcyMstDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_SALE_PLCY_MST, MSP_SALE_ORGN_MST, MSP_SALE_PRDT_MST |
| 담당자 | 김대원 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `plcyTypeCd` | 정책유형코드 | `String` | 정책유형코드 M:오프라인, O:온라인, D:직영온라인 | PLCY_TYPE_CD | MSP_SALE_PLCY_MST |
| `prdtSctnCd` | 제품구분코드 | `String` | 제품구분코드(02:3G , 03:LTE , 5G ) | PRDT_SCTN_CD | MSP_SALE_PLCY_MST |
| `plcySctnCd` | 정책구분코드 | `String` | 정책구분코드 할부개월표시(외부서식지)      * 01 :  단말 구매   할부 개월 리스트 표현      * 02 :   USIM(유심)단독  | PLCY_SCTN_CD | MSP_SALE_PLCY_MST |
| `prdtId` | 단말기ID | `String` | 단말기ID | PRDT_ID | MSP_SALE_PRDT_MST |
| `sprtTp` | 지원금유형 | `String` | KD:단말할인,PM:요금할인 | SPRT_TP | MSP_SALE_PLCY_MST |
| `orgnId` | 조직코드 | `String` | 조직코드 | ORGN_ID | MSP_SALE_ORGN_MST |
| `salePlcyNm` | 판매정책명 | `String` | 판매정책명 | SALE_PLCY_NM | MSP_SALE_PLCY_MST |
| `salePlcyCd` | 판매정책코드 | `String` | 판매정책코드 | SALE_PLCY_CD | MSP_SALE_PLCY_MST |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `salePlcyCd` | 판매정책코드 | `String` | 판매정책코드 | SALE_PLCY_CD | MSP_SALE_PLCY_MST |
| `salePlcyNm` | 판매정책명 | `String` | 판매정책명 | SALE_PLCY_NM | MSP_SALE_PLCY_MST |
| `saleStrtDttm` | 판매시작일시 | `String` | 판매시작일시 | SALE_STRT_DTTM | MSP_SALE_PLCY_MST |
| `saleEndDttm` | 판매종료일시 | `String` | 판매종료일시 | SALE_END_DTTM | MSP_SALE_PLCY_MST |
| `plcyTypeCd` | 정책유형코드 | `String` | 정책유형코드 M:오프라인, O:온라인, D:직영온라인 | PLCY_TYPE_CD | MSP_SALE_PLCY_MST |
| `orgnType` | 조직유형 | `String` | 조직유형 | ORGN_TYPE | MSP_SALE_PLCY_MST |
| `plcySctnCd` | 정책구분코드 | `String` | 정책구분코드 할부개월표시(외부서식지)      * 01 :  단말 구매   할부 개월 리스트 표현      * 02 :   USIM(유심)단독  | PLCY_SCTN_CD | MSP_SALE_PLCY_MST |
| `prdtSctnCd` | 제품구분코드 | `String` | 제품구분코드(02:3G , 03:LTE , 5G ) | PRDT_SCTN_CD | MSP_SALE_PLCY_MST |
| `applSctnCd` | 적용구분코드 | `String` | 적용구분코드 O:개통일, R:접수일 | APPL_SCTN_CD | MSP_SALE_PLCY_MST |
| `instRate` | 할부이자율 | `BigDecimal` | 할부이자율 | INST_RATE | MSP_SALE_PLCY_MST |
| `newYn` | 신규여부 | `String` | 신규여부 | NEW_YN | MSP_SALE_PLCY_MST |
| `mnpYn` | 번호이동여부 | `String` | 번호이동여부 | MNP_YN | MSP_SALE_PLCY_MST |
| `hcnYn` | 일반 기변여부 | `String` | 일반 기변여부 | HCN_YN | MSP_SALE_PLCY_MST |
| `hdnYn` | 우수기변여부 | `String` | 우수기변여부 | HDN_YN | MSP_SALE_PLCY_MST |
| `cnfmYn` | 확정여부 | `String` | 확정여부 | CNFM_YN | MSP_SALE_PLCY_MST |
| `cnfmId` | 확정자ID | `String` | 확정자ID | CNFM_ID | MSP_SALE_PLCY_MST |
| `cnfmDttm` | 확정일시 | `String` | 확정일시 | CNFM_DTTM | MSP_SALE_PLCY_MST |
| `regstId` | 등록자ID | `String` | 등록자ID | REGST_ID | MSP_SALE_PLCY_MST |
| `regstDttm` | 등록일시 | `Date` | 등록일시 | REGST_DTTM | MSP_SALE_PLCY_MST |
| `rvisnId` | 수정자ID | `String` | 수정자ID | RVISN_ID | MSP_SALE_PLCY_MST |
| `rvisnDttm` | 수정일시 | `Date` | 수정일시 | RVISN_DTTM | MSP_SALE_PLCY_MST |
| `sprtTp` | 지원금유형 | `String` | 지원금유형 (단말할인:KD , 요금할인:PM)      * 단말할인:KD      * 요금할인:PM      * 심플할인:SM   (유심, 약정 | SPRT_TP | MSP_SALE_PLCY_MST |
| `orgnId` | 조직코드 | `String` | 조직코드 | ORGN_ID | MSP_SALE_ORGN_MST |

**SQL**

```sql
SELECT
PL.SALE_PLCY_CD ,					--판매정책코드
PL.SALE_PLCY_NM,                    --판매정책명
PL.SALE_STRT_DTTM,                  --판매시작일시
PL.SALE_END_DTTM,                   --판매종료일시
PL.PLCY_TYPE_CD,                    --정책유형코드 M:오프라인, O:온라인
PL.ORGN_TYPE,                       --조직유형
PL.PLCY_SCTN_CD,                    --정책구분코드 01:단말, 02:USIM
PL.PRDT_SCTN_CD,                    --제품구분코   02:3G, 03:LTE
PL.APPL_SCTN_CD,                    --적용구분코드 O:개통일, R:접수일
PL.INST_RATE,                       --할부이자율
PL.NEW_YN,                          --신규여부
PL.MNP_YN,                          --번호이동여부
PL.HCN_YN,                          --기변여부
PL.HDN_YN,                          --우수기변여부
PL.CNFM_YN,                         --확정여부
PL.CNFM_ID,                         --확정자ID
PL.CNFM_DTTM,                       --확정일시
PL.REGST_ID,                        --등록자ID
PL.REGST_DTTM,                      --등록일시
PL.RVISN_ID,                        --수정자ID
PL.RVISN_DTTM,                      --수정일시
PL.SPRT_TP,                         --지원금유형
ORG.ORGN_ID
FROM
MSP_SALE_PLCY_MST@DL_MSP PL,
MSP_SALE_ORGN_MST@DL_MSP ORG,
MSP_SALE_PRDT_MST@DL_MSP PRDT
WHERE
TO_CHAR(SYSDATE,'yyyymmddhh24miss') BETWEEN PL.SALE_STRT_DTTM AND PL.SALE_END_DTTM --판매기간 (필수)
AND PL.CNFM_YN='Y'      								--결제 YN (필수)
<if test="plcyTypeCd != null and plcyTypeCd != ''">
AND PL.PLCY_TYPE_CD = #{plcyTypeCd}   -- 온라인:O , 오프라인:M , 직영온라인 : D
</if>
<if test="prdtSctnCd != null and prdtSctnCd != ''">
AND PL.PRDT_SCTN_CD = #{prdtSctnCd}   -- LTE,3G
</if>
<if test="prdtSctnCd != null and prdtSctnCd != ''">
AND PL.PLCY_SCTN_CD = #{plcySctnCd}   -- 01:단말,02:유심
</if>
AND PRDT.PRDT_ID = #{prdtId}		  -- 단말기ID
<if test="sprtTp != null and sprtTp != ''">
AND PL.SPRT_TP 		= #{sprtTp}   		-- KD:단말할인,PM:요금할인
</if>
<if test="orgnId != null and orgnId != ''">
AND ORG.ORGN_ID     = #{orgnId}
</if>
<if test="salePlcyNm != null and salePlcyNm != ''">
AND PL.SALE_PLCY_NM LIKE #{salePlcyNm}
</if>
<if test="salePlcyCd != null and salePlcyCd != ''">
AND PL.SALE_PLCY_CD = #{salePlcyCd}
</if>
AND PL.SALE_PLCY_CD = ORG.SALE_PLCY_CD
AND PL.SALE_PLCY_CD = PRDT.SALE_PLCY_CD
AND ORG.SALE_PLCY_cD = PRDT.SALE_PLCY_CD
```


#### API_0027 — 판매중인 상품정보를 조회

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `findMspSalePrdMst` |
| 연동명(한글) | 판매중인 상품정보를 조회 |
| 엔드포인트 | `/msp/mspSalePrdMst` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | MspMapper.xml |
| SQL ID | `findMspSalePrdMst` |
| parameterType | `com.ktmmobile.mcp.mspservice.dto.MspSalePrdtMstDto` |
| resultType | `com.ktmmobile.mcp.mspservice.dto.MspSalePrdtMstDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_SALE_PRDT_MST |
| 담당자 | 김대원 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `salePlcyCd` | 판매정책코드 | `String` | 판매정책코드 | SALE_PLCY_CD | MSP_SALE_PLCY_MST |
| `prdtId` | 단말기ID | `String` | 단말기ID | PRDT_ID | MSP_SALE_PRDT_MST |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `salePlcyCd` | 판매정책코드 | `String` | 판매정책코드 | SALE_PLCY_CD | MSP_SALE_PRDT_MST |
| `prdtId` | 제품ID | `String` | 제품ID | PRDT_ID | MSP_SALE_PRDT_MST |
| `oldYn` | 중고여부 | `String` | 중고여부 | OLD_YN | MSP_SALE_PRDT_MST |
| `newCmsnAmt` | 신규수수료 | `BigInteger` | 신규수수료 | NEW_CMSN_AMT | MSP_SALE_PRDT_MST |
| `mnpCmsnAmt` | MNP수수료 | `BigInteger` | MNP수수료 | MNP_CMSN_AMT | MSP_SALE_PRDT_MST |
| `hcnCmsnAmt` | 기변수수료 | `BigInteger` | 기변수수료 | HCN_CMSN_AMT | MSP_SALE_PRDT_MST |
| `regstId` | 등록자ID | `String` | 등록자ID | REGST_ID | MSP_SALE_PRDT_MST |
| `regstDttm` | 등록일시 | `Date` | 등록일시 | REGST_DTTM | MSP_SALE_PRDT_MST |
| `rvisnId` | 수정자ID | `String` | 수정자ID | RVISN_ID | MSP_SALE_PRDT_MST |
| `rvisnDttm` | 수정일시 | `Date` | 수정일시 | RVISN_DTTM | MSP_SALE_PRDT_MST |

**SQL**

```sql
SELECT
SALE_PLCY_CD,			--판매정책코드
PRDT_ID,                --제품ID
OLD_YN,                 --중고여부
NEW_CMSN_AMT,           --신규수수료
MNP_CMSN_AMT,           --MNP수수료
HCN_CMSN_AMT,           --기변수수료
REGST_ID,               --등록자ID
REGST_DTTM,             --등록일시
RVISN_ID,               --수정자ID
RVISN_DTTM              --수정일시
FROM
MSP_SALE_PRDT_MST@DL_MSP
WHERE
SALE_PLCY_CD = #{salePlcyCd}
AND PRDT_ID = #{prdtId}
```


#### API_0028 — MSP 에서 제조사/공급사 정보를 조회

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `listOrgnMnfctMst` |
| 연동명(한글) | MSP 에서 제조사/공급사 정보를 조회 |
| 엔드포인트 | `/msp/orgnMnfctMst` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | MspMapper.xml |
| SQL ID | `listOrgnMnfctMst` |
| parameterType | `com.ktmmobile.mcp.mspservice.dto.MspOrgDto` |
| resultType | `com.ktmmobile.mcp.mspservice.dto.MspOrgDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | ORG_MNFCT_MST |
| 담당자 | 김대원 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `mnfctYn` | 제조사여부 아닐경우(공급사) | `String` | 제조사여부 아닐경우(공급사) | MNFCT_YN | ORG_MNFCT_MST |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `mnfctId` | 제조사 ID | `String` | 제조사 ID | MNFCT_ID | ORG_MNFCT_MST |
| `mnfctNm` | 제조사명 | `String` | 제조사명 | MNFCT_NM | ORG_MNFCT_MST |
| `bizRegNum` | 사업자등록번호 | `String` | 사업자등록번호 | BIZ_REG_NUM | ORG_MNFCT_MST |
| `rprsenNm` | 대표자명 | `String` | 대표자명 | RPRSEN_NM | ORG_MNFCT_MST |
| `zipcd` | 우편번호 | `String` | 우편번호 | ZIPCD | ORG_MNFCT_MST |
| `addr` | 주소 | `String` | 주소 | ADDR | ORG_MNFCT_MST |
| `dtlAddr` | 상세 주소 | `String` | 상세 주소 | DTL_ADDR | ORG_MNFCT_MST |
| `mnfctYn` | 제조사여부 아닐경우(공급사) | `String` | 제조사여부 아닐경우(공급사) | MNFCT_YN | ORG_MNFCT_MST |
| `telnum` | 전화번호 | `String` | 전화번호 | TELNUM | ORG_MNFCT_MST |
| `fax` | 팩스번호 | `String` | 팩스번호 | FAX | ORG_MNFCT_MST |
| `email` | 이메일 | `String` | 이메일 | EMAIL | ORG_MNFCT_MST |
| `serialnoFlg` |  | `String` |  | SERIALNO_FLG | ORG_MNFCT_MST |
| `regId` |  | `String` |  | REG_ID | ORG_MNFCT_MST |
| `regDttm` |  | `String` |  | REG_DTTM | ORG_MNFCT_MST |
| `rvisnId` | 수정자ID | `String` | 수정자ID | RVISN_ID | ORG_MNFCT_MST |
| `rvisnDttm` | 수정일시 | `Date` | 수정일시 | RVISN_DTTM | ORG_MNFCT_MST |
| `tempOrderNo` |  | `String` |  | CASE
    WHEN
        MNFCT_ID = '0141' THEN 1 --삼성전자
    WHEN
        MNFCT_ID = '0142' THEN 2 --LG 전자
    ELSE 3
END AS TEMP_ORDER_NO | ORG_MNFCT_MST |

**SQL**

```sql
SELECT
MNFCT_ID,
MNFCT_NM,
BIZ_REG_NUM,
RPRSEN_NM,
ZIPCD,
ADDR,
DTL_ADDR,
MNFCT_YN,
TELNUM,
FAX,
EMAIL,
SERIALNO_FLG,
REG_ID,
REG_DTTM,
RVISN_ID,
RVISN_DTTM,
CASE			--정렬순서 를 특정값으로 강제 하기위한부분
WHEN
MNFCT_ID = '0141' THEN 1 --삼성전자
WHEN
MNFCT_ID = '0142' THEN 2 --LG 전자
ELSE 3
END AS TEMP_ORDER_NO
FROM
ORG_MNFCT_MST@DL_MSP
WHERE
1 = 1
--AND MNFCT_ID IN ('0141','0142','0122')
<if test="mnfctYn != null" >
AND MNFCT_YN = #{mnfctYn}
</if>
ORDER BY TEMP_ORDER_NO
```


#### API_0029 — MSP 에서 제조사/공급사 정보를 조회

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `listOrgnMnfctMstRe` |
| 연동명(한글) | MSP 에서 제조사/공급사 정보를 조회 |
| 엔드포인트 | `/msp/orgnMnfctMstRe` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | MspMapper.xml |
| SQL ID | `listOrgnMnfctMstRe` |
| parameterType | `` |
| resultType | `com.ktmmobile.mcp.mspservice.dto.MspOrgDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | ORG_MNFCT_MST |
| 담당자 | 김대원 |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `mnfctId` | 제조사 ID | `String` | 제조사 ID | MAKR_CD AS MNFCT_ID | NMCP_PROD_BAS |
| `mnfctNm` | 제조사명 | `String` | 제조사명 | MNFCT_NM AS MNFCT_NM | NMCP_PROD_BAS |

**SQL**

```sql
SELECT
DISTINCT MAKR_CD AS MNFCT_ID
, MNFCT_NM AS MNFCT_NM
FROM
NMCP_PROD_BAS T1
, ORG_MNFCT_MST@DL_MSP T2
WHERE 1=1
AND T1.MAKR_CD =T2.MNFCT_ID
AND T1.SHOW_YN ='Y'
AND T1.SALE_YN ='Y'
AND T2.MNFCT_YN ='Y'
AND T1.PROD_TYPE ='01'
ORDER BY DECODE(MAKR_CD ,'0141',1,'0142',2,'0222',3,MAKR_CD)
```


#### API_0030 — 해당정책코드에 해당하는 판매요금제 정보 리스트 조회

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `listMspRateMst` |
| 연동명(한글) | 해당정책코드에 해당하는 판매요금제 정보 리스트 조회 |
| 엔드포인트 | `/msp/mspRateMsp` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | MspMapper.xml |
| SQL ID | `listMspRateMst` |
| parameterType | `com.ktmmobile.mcp.mspservice.dto.MspRateMstDto` |
| resultType | `com.ktmmobile.mcp.mspservice.dto.MspRateMstDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_RATE_MST, MSP_SALE_RATE_MST, CMN_GRP_CD_MST |
| 담당자 | 김대원 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `salePlcyCd` | 판매정책코드 | `String` | 판매정책코드 | SALE_PLCY_CD | MSP_SALE_RATE_MST |
| `payClCd` | 선후불구분 | `String` | 선후불구분 | PAY_CL_CD | MSP_RATE_MST |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `rateCd` | 요금제코드 | `String` | 요금제코드 | RATE_CD | MSP_RATE_MST |
| `applEndDt` | 적용종료일자 | `String` | 적용종료일자 | APPL_END_DT | MSP_RATE_MST |
| `applStrtDt` | 적용시작일자 | `String` | 적용시작일자 | APPL_STRT_DT | MSP_RATE_MST |
| `rateNm` | 요금제명 | `String` | 요금제명 | RATE_NM | MSP_RATE_MST |
| `rateGrpCd` | 요금제그룹코드 | `String` | 요금제그룹코드 | RATE_GRP_CD | MSP_RATE_MST |
| `payClCd` | 선후불구분 | `String` | 선후불구분 | PAY_CL_CD | MSP_RATE_MST |
| `rateType` | 요금제유형 | `String` | 요금제유형(ORG0008) | RATE_TYPE | MSP_RATE_MST |
| `dataType` | 데이터유형 | `String` | 데이터유형 (ORG0008) 'LTE,3G | DATA_TYPE | MSP_RATE_MST |
| `baseAmt` | 기본료 | `int` | 기본료 | BASE_AMT | MSP_RATE_MST |
| `freeCallClCd` | 망내외무료통화구분 | `String` | 망내외무료통화구분 | FREE_CALL_CL_CD | MSP_RATE_MST |
| `freeCallCnt` | 무료통화건수 | `String` | 무료통화건수 | FREE_CALL_CNT | MSP_RATE_MST |
| `nwInCallCnt` | 망내무료통화건수 | `String` | 망내무료통화건수 | NW_IN_CALL_CNT | MSP_RATE_MST |
| `nwOutCallCnt` | 망외무료통화건수 | `String` | 망외무료통화건수 | NW_OUT_CALL_CNT | MSP_RATE_MST |
| `freeSmsCnt` | 무료문자건수 | `String` | 무료문자건수 | FREE_SMS_CNT | MSP_RATE_MST |
| `freeDataCnt` | 무료데이터건수 | `String` | 무료데이터건수 | FREE_DATA_CNT | MSP_RATE_MST |
| `rmk` | 비고 | `String` | 비고 | RMK | MSP_RATE_MST |
| `regstId` | 등록자ID | `String` | 등록자ID | REGST_ID | MSP_RATE_MST |
| `regstDttm` | 등록일시 | `Date` | 등록일시 | REGST_DTTM | MSP_RATE_MST |
| `rvisnId` | 수정자ID | `String` | 수정자ID | RVISN_ID | MSP_RATE_MST |
| `rvisnDttm` | 수정일시 | `Date` | 수정일시 | RVISN_DTTM | MSP_RATE_MST |
| `onlineTypeCd` | 온라인유형코드 | `String` | 온라인유형코드 | ONLINE_TYPE_CD | MSP_RATE_MST |
| `alFlag` | 알요금제구분자 | `String` | 알요금제구분자 | AL_FLAG | MSP_RATE_MST |
| `serviceType` | 서비스유형 | `String` | 서비스유형 | SERVICE_TYPE | MSP_RATE_MST |
| `cmnt` | 요금제혜택 | `String` | 요금제혜택 | CMNT | MSP_RATE_MST |

**SQL**

```sql
SELECT
RA.RATE_CD,						--요금제코드
RA.APPL_END_DT,                    --적용종료일자
RA.APPL_STRT_DT,                   --적용시작일자
RA.RATE_NM,                        --요금제명
RA.RATE_GRP_CD,                    --요금제그룹코드
RA.PAY_CL_CD,                      --선후불구분
RA.RATE_TYPE,                      --요금제유형( ORG0008 )
RA.DATA_TYPE,                      --데이터유형( ORG0018 )
RA.BASE_AMT,                       --기본료
RA.FREE_CALL_CL_CD,                --망내외무료통화구분
RA.FREE_CALL_CNT,                  --무료통화건수
RA.NW_IN_CALL_CNT,                 --망내무료통화건수
RA.NW_OUT_CALL_CNT,                --망외무료통화건수
RA.FREE_SMS_CNT,                   --무료문자건수
RA.FREE_DATA_CNT,                  --무료데이터건수
RA.RMK,                            --비고
RA.REGST_ID,                       --등록자ID
RA.REGST_DTTM,                     --등록일시
RA.RVISN_ID,                       --수정자ID
RA.RVISN_DTTM,                     --수정일시
RA.ONLINE_TYPE_CD,                 --온라인유형코드
RA.AL_FLAG,                        --알요금제 구분자
RA.SERVICE_TYPE,                    --서비스유형
RA.CMNT							   -- 요금제혜택
FROM
MSP_RATE_MST@DL_MSP RA,
MSP_SALE_RATE_MST@DL_MSP SA,
CMN_GRP_CD_MST@DL_MSP GR
WHERE
APPL_END_DT = '99991231'
AND RA.PAY_CL_CD = GR.CD_VAL
AND GR.GRP_ID='CMN0032'	--선불후불 구분 코트값의 그룹코드
AND SA.SALE_PLCY_CD = #{salePlcyCd}
AND RA.RATE_CD = SA.RATE_CD
<if test="payClCd == 'PO'"> <!-- 후불일경우에는 후불요금만 보여준다. AS-IS 상 선불일경우에는 선불,후불 관련없이 뿌리는걸로 파악중 -->
AND RA.PAY_CL_CD = #{payClCd}
</if>
```


#### API_0031 — 해당정책코드에 해당하는 약정개월정보 리스트 조회

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `listMspSaleAgrmMst` |
| 연동명(한글) | 해당정책코드에 해당하는 약정개월정보 리스트 조회 |
| 엔드포인트 | `/msp/mspSaleAgrmMst` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | MspMapper.xml |
| SQL ID | `listMspSaleAgrmMst` |
| parameterType | `string` |
| resultType | `com.ktmmobile.mcp.mspservice.dto.MspSaleAgrmMst` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_SALE_AGRM_MST, ORG_INST_NOM_MST |
| 담당자 | 김대원 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `salePlcyCd` | 판매정책코드 | `String` | 판매정책코드 | SALE_PLCY_CD | MSP_SALE_AGRM_MST |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `salePlcyCd` | 판매정책코드 | `String` | 판매정책코드 | SALE_PLCY_CD | MSP_SALE_AGRM_MST |
| `agrmTrm` | 약정코드 | `String` | 약정코드 | AGRM_TRM | MSP_SALE_AGRM_MST |
| `regstId` | 등록자ID | `String` | 등록자ID | REGST_ID | MSP_SALE_AGRM_MST |
| `regstDttm` | 등록일시 | `Date` | 등록일시 | REGST_DTTM | MSP_SALE_AGRM_MST |
| `rvisnId` | 수정자ID | `String` | 수정자ID | RVISN_ID | MSP_SALE_AGRM_MST |
| `rvisnDttm` | 수정일시 | `Date` | 수정일시 | RVISN_DTTM | MSP_SALE_AGRM_MST |
| `instNom` | 할부기간 | `String` | 할부기간 | INST_NOM | ORG_INST_NOM_MST |

**SQL**

```sql
SELECT
RA.SALE_PLCY_CD,
RA.AGRM_TRM,
RA.REGST_ID,
RA.REGST_DTTM,
RA.RVISN_ID,
RA.RVISN_DTTM,
ORG.INST_NOM
FROM
MSP_SALE_AGRM_MST@DL_MSP RA,
ORG_INST_NOM_MST@DL_MSP ORG
WHERE
RA.SALE_PLCY_CD = #{salePlcyCd}
AND ORG.SALE_PLCY_CD = RA.SALE_PLCY_CD
AND ORG.INST_NOM = RA.AGRM_TRM
AND ORG.INST_NOM != 0
ORDER BY INST_NOM
```


#### API_0033 — 상품의 판매금액관련 상세내역을 조회

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `findMspSaleSubsdMst` |
| 연동명(한글) | 상품의 판매금액관련 상세내역을 조회 |
| 엔드포인트 | `/msp/mspSaleSubsdMst` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | MspMapper.xml |
| SQL ID | `findMspSaleSubsdMst` |
| parameterType | `com.ktmmobile.mcp.mspservice.dto.MspSaleSubsdMstDto` |
| resultType | `com.ktmmobile.mcp.mspservice.dto.MspSaleSubsdMstDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_SALE_SUBSD_MST |
| 담당자 | 김대원 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `salePlcyCd` | 판매정책코드 | `String` | 판매정책코드 | SALE_PLCY_CD | MSP_SALE_SUBSD_MST |
| `prdtId` | 상품ID | `String` | 상품ID | PRDT_ID | MSP_SALE_SUBSD_MST |
| `oldYn` | 중고여부 | `String` | 중고여부 | OLD_YN | MSP_SALE_SUBSD_MST |
| `agrmTrm` | 약정기간 | `String` | 약정기간 | AGRM_TRM | MSP_SALE_SUBSD_MST |
| `rateCd` | 요금제코드 | `String` | 요금제코드 | RATE_CD | MSP_SALE_SUBSD_MST |
| `operType` | 가입유형 | `String` | 업무구분      * HCN3 : 기기변경      * HDN3 : 보상기변3G      * MNP3 : 번호이동      * NAC3 : 신규 | OPER_TYPE | MSP_SALE_SUBSD_MST |
| `orgnId` | 조직코드 | `String` | 조직코드 | ORGN_ID | MSP_SALE_SUBSD_MST |
| `sprtTp` | 할인유형 | `String` | 할인유형 (단말,요금) | SPRT_TP | MSP_SALE_SUBSD_MST |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `salePlcyCd` | 판매정책코드 | `String` | 판매정책코드 | SALE_PLCY_CD | MSP_SALE_SUBSD_MST |
| `rateCd` | 요금제코드 | `String` | 요금제코드 | RATE_CD | MSP_SALE_SUBSD_MST |
| `prdtId` | 단말ID | `String` | 단말ID | PRDT_ID | MSP_SALE_SUBSD_MST |
| `oldYn` | 중고여부 | `String` | 중고여부 | OLD_YN | MSP_SALE_SUBSD_MST |
| `agrmTrm` | 약정코드 | `String` | 약정코드 | AGRM_TRM | MSP_SALE_SUBSD_MST |
| `operType` | 업무구분 | `String` | 업무구분      * HCN3 : 기기변경      * HDN3 : 보상기변3G      * MNP3 : 번호이동      * NAC3 : 신규 | OPER_TYPE | MSP_SALE_SUBSD_MST |
| `orgnId` | 조직코드 | `String` | 조직코드 | ORGN_ID | MSP_SALE_SUBSD_MST |
| `hndstAmt` | 단말금액(VAT)포함 | `int` | 단말금액(VAT)포함 | HNDST_AMT | MSP_SALE_SUBSD_MST |
| `subsdAmt` | 공시지원금(VAT포함) | `int` | 공시지원금(VAT포함) | SUBSD_AMT | MSP_SALE_SUBSD_MST |
| `instAmt` | 할부원금 (단말기 가격 - 공시지원금) (VAT포함) | `int` | 할부원금 (단말기 가격 - 공시지원금) (VAT포함) | INST_AMT | MSP_SALE_SUBSD_MST |
| `instCmsn` | 할부수수료(VAT포함) | `int` | 할부수수료(VAT포함) | INST_CMSN | MSP_SALE_SUBSD_MST |
| `agncySubsdMax` | 대리점보조금[추가지원금]MAX(VAT포함) | `int` | 대리점보조금[추가지원금]MAX(VAT포함) | AGNCY_SUBSD_MAX | MSP_SALE_SUBSD_MST |
| `agncySubsdAmt` | 대리점보조금[추가지원금](VAT포함) | `int` | 대리점보조금[추가지원금](VAT포함) | AGNCY_SUBSD_AMT | MSP_SALE_SUBSD_MST |
| `regstId` | 등록자ID | `String` | 등록자ID | REGST_ID | MSP_SALE_SUBSD_MST |
| `regstDttm` | 등록일시 | `Date` | 등록일시 | REGST_DTTM | MSP_SALE_SUBSD_MST |
| `rvisnId` | 수정자ID | `String` | 수정자ID | RVISN_ID | MSP_SALE_SUBSD_MST |
| `rvisnDttm` | 수정일시 | `Date` | 수정일시 | RVISN_DTTM | MSP_SALE_SUBSD_MST |
| `sprtTp` | 지원금유형 | `String` | 지원금유형 (단말할인:KD , 요금할인:PM)      * 단말할인:KD      * 요금할인:PM      * 심플할인:SM   (유심, 약정 | SPRT_TP | MSP_SALE_SUBSD_MST |
| `baseAmt` | 기본료 | `int` | 기본료 | BASE_AMT | MSP_SALE_SUBSD_MST |
| `dcAmt` | 할인금액(약정할인선택시 할인금액) | `int` | 할인금액(약정할인선택시 할인금액) | DC_AMT | MSP_SALE_SUBSD_MST |
| `addDcAmt` | 추가할인금액(요금할인선택시 할인금액  단말할인과는 반대된다(단말할인시 공시지원금 대리금 지원금 지원)) | `int` | 추가할인금액(요금할인선택시 할인금액  단말할인과는 반대된다(단말할인시 공시지원금 대리금 지원금 지원)) | ADD_DC_AMT | MSP_SALE_SUBSD_MST |

**SQL**

```sql
SELECT
SALE_PLCY_CD,            --판매정책코드
RATE_CD,                 --요금제코드
PRDT_ID,                 --제품ID
OLD_YN,                  --중고여부
AGRM_TRM,                --약정기간
OPER_TYPE,               --업무유형
ORGN_ID,                 --조직ID
HNDST_AMT,               --단말금액(VAT포함)
SUBSD_AMT,               --공시지원금(VAT포함)
INST_AMT,                --할부원금(VAT포함)
INST_CMSN,               --할부수수료(VAT포함)
AGNCY_SUBSD_MAX,         --대리점보조금MAX(VAT포함)
AGNCY_SUBSD_AMT,         --대리점보조금(VAT포함)
REGST_ID,                --등록자ID
REGST_DTTM,              --등록일시
RVISN_ID,                --수정자ID
RVISN_DTTM,              --수정일시
SPRT_TP,                 --지원금유형
BASE_AMT,                --기본료
DC_AMT,                  --기본할인금액
ADD_DC_AMT               --추가할인금액
FROM
MSP_SALE_SUBSD_MST@DL_MSP
WHERE
SALE_PLCY_CD  = #{salePlcyCd}
AND PRDT_ID = #{prdtId}  		--상품ID
AND OLD_YN  = #{oldYn}  		--중고여부
AND AGRM_TRM = #{agrmTrm} 		--약정기간
AND RATE_CD = #{rateCd} 		--요금제코드
AND OPER_TYPE = #{operType}      --가입유형
AND ORGN_ID = #{orgnId} 		--조직코드
AND SPRT_TP =#{sprtTp} 			--할인유형 (단말,요금)
```


#### API_0034 — getMspSalePlcyMst

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `getMspSalePlcyMst` |
| 엔드포인트 | `/msp/mspSalePlcyMst` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | MspMapper.xml |
| SQL ID | `getMspSalePlcyMst` |
| parameterType | `java.lang.String` |
| resultType | `com.ktmmobile.mcp.mspservice.dto.MspSalePlcyMstDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_SALE_PLCY_MST |
| 담당자 | 김대원 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `salePlcyCd` | 판매정책코드 | `String` | 판매정책코드 | SALE_PLCY_CD | MSP_SALE_PLCY_MST |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `salePlcyCd` | 판매정책코드 | `String` | 판매정책코드 | SALE_PLCY_CD | MSP_SALE_PLCY_MST |
| `salePlcyNm` | 판매정책명 | `String` | 판매정책명 | SALE_PLCY_NM | MSP_SALE_PLCY_MST |
| `saleStrtDttm` | 판매시작일시 | `String` | 판매시작일시 | SALE_STRT_DTTM | MSP_SALE_PLCY_MST |
| `saleEndDttm` | 판매종료일시 | `String` | 판매종료일시 | SALE_END_DTTM | MSP_SALE_PLCY_MST |
| `plcyTypeCd` | 정책유형코드 | `String` | 정책유형코드 M:오프라인, O:온라인, D:직영온라인 | PLCY_TYPE_CD | MSP_SALE_PLCY_MST |
| `orgnType` | 조직유형 | `String` | 조직유형 | ORGN_TYPE | MSP_SALE_PLCY_MST |
| `plcySctnCd` | 정책구분코드 | `String` | 정책구분코드 할부개월표시(외부서식지)      * 01 :  단말 구매   할부 개월 리스트 표현      * 02 :   USIM(유심)단독  | PLCY_SCTN_CD | MSP_SALE_PLCY_MST |
| `prdtSctnCd` | 제품구분코드 | `String` | 제품구분코드(02:3G , 03:LTE , 5G ) | PRDT_SCTN_CD | MSP_SALE_PLCY_MST |
| `applSctnCd` | 적용구분코드 | `String` | 적용구분코드 O:개통일, R:접수일 | APPL_SCTN_CD | MSP_SALE_PLCY_MST |
| `instRate` | 할부이자율 | `BigDecimal` | 할부이자율 | INST_RATE | MSP_SALE_PLCY_MST |
| `newYn` | 가입유형 신규 | `String` | 가입유형 신규 | NEW_YN | MSP_SALE_PLCY_MST |
| `mnpYn` | 가입유형 번호이동 | `String` | 가입유형 번호이동 | MNP_YN | MSP_SALE_PLCY_MST |
| `hcnYn` | 가입유형 기기변경 | `String` | 가입유형 기기변경 | HCN_YN | MSP_SALE_PLCY_MST |
| `hdnYn` | 우수기변여부 | `String` | 우수기변여부 | HDN_YN | MSP_SALE_PLCY_MST |
| `cnfmYn` | 확정여부 | `String` | 확정여부 | CNFM_YN | MSP_SALE_PLCY_MST |
| `cnfmId` | 확정자ID | `String` | 확정자ID | CNFM_ID | MSP_SALE_PLCY_MST |
| `cnfmDttm` | 확정일시 | `String` | 확정일시 | CNFM_DTTM | MSP_SALE_PLCY_MST |
| `regstId` | 등록자ID | `String` | 등록자ID | REGST_ID | MSP_SALE_PLCY_MST |
| `regstDttm` | 등록일시 | `Date` | 등록일시 | REGST_DTTM | MSP_SALE_PLCY_MST |
| `rvisnId` | 수정자ID | `String` | 수정자ID | RVISN_ID | MSP_SALE_PLCY_MST |
| `rvisnDttm` | 수정일시 | `Date` | 수정일시 | RVISN_DTTM | MSP_SALE_PLCY_MST |
| `sprtTp` | 지원금유형 | `String` | 지원금유형 (단말할인:KD , 요금할인:PM)      * 단말할인:KD      * 요금할인:PM      * 심플할인:SM   (유심, 약정 | SPRT_TP | MSP_SALE_PLCY_MST |

**SQL**

```sql
SELECT
PL.SALE_PLCY_CD ,					--판매정책코드
PL.SALE_PLCY_NM,                    --판매정책명
PL.SALE_STRT_DTTM,                  --판매시작일시
PL.SALE_END_DTTM,                   --판매종료일시
PL.PLCY_TYPE_CD,                    --정책유형코드 M:오프라인, O:온라인
PL.ORGN_TYPE,                       --조직유형
PL.PLCY_SCTN_CD,                    --정책구분코드 01:단말, 02:USIM
PL.PRDT_SCTN_CD,                    --제품구분코   02:3G, 03:LTE
PL.APPL_SCTN_CD,                    --적용구분코드 O:개통일, R:접수일
PL.INST_RATE,                       --할부이자율
PL.NEW_YN,                          --신규여부
PL.MNP_YN,                          --번호이동여부
PL.HCN_YN,                          --기변여부
PL.HDN_YN,                          --우수기변여부
PL.CNFM_YN,                         --확정여부
PL.CNFM_ID,                         --확정자ID
PL.CNFM_DTTM,                       --확정일시
PL.REGST_ID,                        --등록자ID
PL.REGST_DTTM,                      --등록일시
PL.RVISN_ID,                        --수정자ID
PL.RVISN_DTTM,                      --수정일시
PL.SPRT_TP                         --지원금유형
FROM
MSP_SALE_PLCY_MST@DL_MSP PL
WHERE
PL.SALE_PLCY_CD = #{salePlcyCd}
```


#### API_0036 — 최저가를 구하기 위한 해당 상품의 요금제 정보 1건 조회

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `getLowPriceChargeInfoByProd` |
| 연동명(한글) | 최저가를 구하기 위한 해당 상품의 요금제 정보 1건 조회 |
| 엔드포인트 | `/msp/lowPriceChargeInfoByProd` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | MspMapper.xml |
| SQL ID | `getLowPriceChargeInfoByProd` |
| parameterType | `com.ktmmobile.mcp.mspservice.dto.MspSaleSubsdMstDto` |
| resultType | `com.ktmmobile.mcp.mspservice.dto.MspSaleSubsdMstDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_SALE_SUBSD_MST, MSP_RATE_MST |
| 담당자 | 김대원 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `salePlcyCd` | 판매정책코드 | `String` | 판매정책코드 | SALE_PLCY_CD | MSP_SALE_SUBSD_MST |
| `payClCd` | 선후불구분 | `String` | 선후불구분 | PAY_CL_CD | MSP_SALE_SUBSD_MST |
| `prdtId` | 상품ID | `String` | 상품ID | PRDT_ID | MSP_SALE_SUBSD_MST |
| `oldYn` | 중고여부 | `String` | 중고여부 | OLD_YN | MSP_SALE_SUBSD_MST |
| `orgnId` | 조직코드 | `String` | 조직코드 | ORGN_ID | MSP_SALE_SUBSD_MST |
| `operType` | 가입유형 | `String` | 업무구분      * HCN3 : 기기변경      * HDN3 : 보상기변3G      * MNP3 : 번호이동      * NAC3 : 신규 | OPER_TYPE | MSP_SALE_SUBSD_MST |
| `rateCd` | 요금제코드 | `String` | 요금제코드 | RATE_CD | MSP_SALE_SUBSD_MST |
| `agrmTrm` | 약정코드 | `String` | 약정코드 | AGRM_TRM | MSP_SALE_SUBSD_MST |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `salePlcyCd` | 판매정책코드 | `String` | 판매정책코드 | SALE_PLCY_CD | MSP_SALE_SUBSD_MST |
| `rateCdSu` | 요금제코드 | `String` | 요금제코드 | RATE_CD_SU | MSP_SALE_SUBSD_MST |
| `prdtId` | 단말ID | `String` | 단말ID | PRDT_ID | MSP_SALE_SUBSD_MST |
| `oldYn` | 중고여부 | `String` | 중고여부 | OLD_YN | MSP_SALE_SUBSD_MST |
| `agrmTrm` | 약정코드 | `String` | 약정코드 | AGRM_TRM | MSP_SALE_SUBSD_MST |
| `operType` | 업무구분 | `String` | 업무구분      * HCN3 : 기기변경      * HDN3 : 보상기변3G      * MNP3 : 번호이동      * NAC3 : 신규 | OPER_TYPE | MSP_SALE_SUBSD_MST |
| `orgnId` | 조직코드 | `String` | 조직코드 | ORGN_ID | MSP_SALE_SUBSD_MST |
| `hndstAmt` | 단말금액(VAT)포함 | `int` | 단말금액(VAT)포함 | HNDST_AMT | MSP_SALE_SUBSD_MST |
| `subsdAmt` | 공시지원금(VAT포함) | `int` | 공시지원금(VAT포함) | SUBSD_AMT | MSP_SALE_SUBSD_MST |
| `instAmt` | 할부원금 (단말기 가격 - 공시지원금) (VAT포함) | `int` | 할부원금 (단말기 가격 - 공시지원금) (VAT포함) | INST_AMT | MSP_SALE_SUBSD_MST |
| `instCmsn` | 할부수수료(VAT포함) | `int` | 할부수수료(VAT포함) | INST_CMSN | MSP_SALE_SUBSD_MST |
| `agncySubsdMax` | 대리점보조금[추가지원금]MAX(VAT포함) | `int` | 대리점보조금[추가지원금]MAX(VAT포함) | AGNCY_SUBSD_MAX | MSP_SALE_SUBSD_MST |
| `agncySubsdAmt` | 대리점보조금[추가지원금](VAT포함) | `int` | 대리점보조금[추가지원금](VAT포함) | AGNCY_SUBSD_AMT | MSP_SALE_SUBSD_MST |
| `regstIdSu` | 등록자ID | `String` | 등록자ID | REGST_ID_SU | MSP_SALE_SUBSD_MST |
| `regstDttmSu` | 등록일시 | `Date` | 등록일시 | REGST_DTTM_SU | MSP_SALE_SUBSD_MST |
| `rvisnIdSu` | 수정자ID | `String` | 수정자ID | RVISN_ID_SU | MSP_SALE_SUBSD_MST |
| `sprtTp` | 지원금유형 | `String` | 지원금유형 (단말할인:KD , 요금할인:PM)      * 단말할인:KD      * 요금할인:PM      * 심플할인:SM   (유심, 약정 | SPRT_TP | MSP_SALE_SUBSD_MST |
| `dcAmt` | 할인금액(약정할인선택시 할인금액) | `int` | 할인금액(약정할인선택시 할인금액) | DC_AMT | MSP_SALE_SUBSD_MST |
| `addDcAmt` | 추가할인금액(요금할인선택시 할인금액  단말할인과는 반대된다(단말할인시 공시지원금 대리금 지원금 지원)) | `int` | 추가할인금액(요금할인선택시 할인금액  단말할인과는 반대된다(단말할인시 공시지원금 대리금 지원금 지원)) | ADD_DC_AMT | MSP_SALE_SUBSD_MST |
| `rateCdRa` | 요금제코드 | `String` | 요금제코드 | RATE_CD_RA | MSP_RATE_MST |
| `applEndDt` | 적용종료일자 | `String` | 적용종료일자 | APPL_END_DT | MSP_RATE_MST |
| `applStrtDt` | 적용시작일자 | `String` | 적용시작일자 | APPL_STRT_DT | MSP_RATE_MST |
| `rateNm` | 요금제명 | `String` | 요금제명 | RATE_NM | MSP_RATE_MST |
| `rateGrpCd` | 요금제그룹코드 | `String` | 요금제그룹코드 | RATE_GRP_CD | MSP_RATE_MST |
| `payClCd` | 선후불구분 | `String` | 선후불구분 | PAY_CL_CD | MSP_RATE_MST |
| `rateType` | 요금제유형 | `String` | 요금제유형(ORG0008) | RATE_TYPE | MSP_RATE_MST |
| `dataType` | 데이터유형 | `String` | 데이터유형 (ORG0008) 'LTE,3G | DATA_TYPE | MSP_RATE_MST |
| `baseAmt` | 기본료 | `int` | 기본료 | BASE_AMT | MSP_RATE_MST |
| `freeCallClCd` | 망내외무료통화구분 | `String` | 망내외무료통화구분 | FREE_CALL_CL_CD | MSP_RATE_MST |
| `freeCallCnt` | 무료통화건수 | `String` | 무료통화건수 | FREE_CALL_CNT | MSP_RATE_MST |
| `nwInCallCnt` | 망내무료통화건수 | `String` | 망내무료통화건수 | NW_IN_CALL_CNT | MSP_RATE_MST |
| `nwOutCallCnt` | 망외무료통화건수 | `String` | 망외무료통화건수 | NW_OUT_CALL_CNT | MSP_RATE_MST |
| `freeSmsCnt` | 무료문자건수 | `String` | 무료문자건수 | FREE_SMS_CNT | MSP_RATE_MST |
| `freeDataCnt` | 무료데이터건수 | `String` | 무료데이터건수 | FREE_DATA_CNT | MSP_RATE_MST |
| `regstId` | 등록자ID | `String` | 등록자ID | REGST_ID | MSP_RATE_MST |
| `regstDttm` | 등록일시 | `Date` | 등록일시 | REGST_DTTM | MSP_RATE_MST |
| `rvisnId` | 수정자ID | `String` | 수정자ID | RVISN_ID | MSP_RATE_MST |
| `rvisnDttm` | 수정일시 | `Date` | 수정일시 | RVISN_DTTM | MSP_RATE_MST |
| `onlineTypeCd` | 온라인유형코드 | `String` | 온라인유형코드 | ONLINE_TYPE_CD | MSP_RATE_MST |
| `alFlag` | 알요금제구분자 | `String` | 알요금제구분자 | AL_FLAG | MSP_RATE_MST |
| `cmnt` | 문구 | `String` | 문구 | CMNT | MSP_RATE_MST |
| `serviceType` | 서비스유형 | `String` | 서비스유형 | SERVICE_TYPE | MSP_RATE_MST |
| `plcySctnCd` | 정책구분코드 | `String` | 정책구분코드 할부개월표시(외부서식지)      * 01 :  단말 구매   할부 개월 리스트 표현      * 02 :   USIM(유심)단독  | PLCY_SCTN_CD | MSP_RATE_MST |
| `onOffType` | 온라인오프라인구분 | `String` | 온라인오프라인구분 | ON_OFF_TYPE | MSP_RATE_MST |

**SQL**

```sql
SELECT
SALE_PLCY_CD
, RATE_CD_SU
, PRDT_ID
, OLD_YN
, AGRM_TRM
, OPER_TYPE
, ORGN_ID
, HNDST_AMT
, SUBSD_AMT
, INST_AMT
, INST_CMSN
, AGNCY_SUBSD_MAX
, AGNCY_SUBSD_AMT
, REGST_ID_SU
, REGST_DTTM_SU
, RVISN_ID_SU
, SPRT_TP
, DC_AMT
, ADD_DC_AMT
, RATE_CD_RA
, APPL_END_DT
, APPL_STRT_DT
, RATE_NM
, RATE_GRP_CD
, PAY_CL_CD
, RATE_TYPE
, DATA_TYPE
, BASE_AMT
, FREE_CALL_CL_CD
, FREE_CALL_CNT
, NW_IN_CALL_CNT
, NW_OUT_CALL_CNT
, FREE_SMS_CNT
, FREE_DATA_CNT
, REGST_ID
, REGST_DTTM
, RVISN_ID
, RVISN_DTTM
, ONLINE_TYPE_CD
, AL_FLAG
, CMNT
, SERVICE_TYPE
, PLCY_SCTN_CD
, ON_OFF_TYPE
FROM (
SELECT
SU.SALE_PLCY_CD,            --판매정책코드
SU.RATE_CD AS RATE_CD_SU,   --요금제코드
SU.PRDT_ID,                 --제품ID
SU.OLD_YN,                  --중고여부
SU.AGRM_TRM,                --약정기간
SU.OPER_TYPE,               --업무유형
SU.ORGN_ID,                 --조직ID
SU.HNDST_AMT,               --단말금액(VAT포함)
SU.SUBSD_AMT,               --공시지원금(VAT포함)
SU.INST_AMT,                --할부원금(VAT포함)
SU.INST_CMSN,               --할부수수료(VAT포함)
SU.AGNCY_SUBSD_MAX,         --대리점보조금MAX(VAT포함)
SU.AGNCY_SUBSD_AMT,         --대리점보조금(VAT포함)
SU.REGST_ID AS REGST_ID_SU,     --등록자ID
SU.REGST_DTTM AS REGST_DTTM_SU, --등록일시
SU.RVISN_ID AS RVISN_ID_SU,     --수정자ID
SU.RVISN_DTTM AS RVISN_DTTM_SU, --수정일시
SU.SPRT_TP,                 --지원금유형
SU.DC_AMT,                  --기본할인금액
SU.ADD_DC_AMT,               --추가할인금액
RA.RATE_CD AS RATE_CD_RA,    --요금제코드
RA.APPL_END_DT,              --적용종료일자
RA.APPL_STRT_DT,             --적용시작일자
RA.RATE_NM,                  --요금제명
RA.RATE_GRP_CD,              --요금제그룹코드
RA.PAY_CL_CD,                --선후불구분
RA.RATE_TYPE,                --요금제유형( ORG0008 )
RA.DATA_TYPE,                --데이터유형( ORG0018 )
RA.BASE_AMT,                 --기본료
RA.FREE_CALL_CL_CD,          --망내외무료통화구분
RA.FREE_CALL_CNT,            --무료통화건수
RA.NW_IN_CALL_CNT,           --망내무료통화건수
RA.NW_OUT_CALL_CNT,          --망외무료통화건수
RA.FREE_SMS_CNT,             --무료문자건수
RA.FREE_DATA_CNT,            --무료데이터건수
RA.REGST_ID,                 --등록자ID
RA.REGST_DTTM,               --등록일시
RA.RVISN_ID,                 --수정자ID
RA.RVISN_DTTM,               --수정일시
RA.ONLINE_TYPE_CD,           --온라인유형코드
RA.AL_FLAG,                  --알요금제 구분자
RA.CMNT,                       -- 요금제혜택
RA.SERVICE_TYPE              --서비스유형
, #{plcySctnCd}  AS PLCY_SCTN_CD --정책구분코드(01:단말,02:유심)
, #{onOffType}   AS ON_OFF_TYPE
FROM
MSP_SALE_SUBSD_MST@DL_MSP SU
INNER JOIN
MSP_RATE_MST@DL_MSP RA
ON
SU.RATE_CD = RA.RATE_CD
WHERE
1=1
<if test="salePlcyCd != null and salePlcyCd != ''">
AND SALE_PLCY_CD  = #{salePlcyCd}   --정책코드 할인유형과 결합시 유니크
</if>
<if test="payClCd != null and payClCd != ''">
AND RA.PAY_CL_CD = #{payClCd}
</if>
AND PRDT_ID = #{prdtId}             --상품ID
AND OLD_YN  = #{oldYn}              --중고여부
AND ORGN_ID = #{orgnId}             --조직코드
<if test="operType != null and operType != ''">
AND OPER_TYPE = #{operType}      --가입유형
</if>
<if test="rateCd != null and rateCd != ''">
AND SU.RATE_CD = #{rateCd}      --요금제코드
</if>
<choose>
<when test='noArgmYn
-- ... (이하 생략)
```


#### API_0037 — 최저가를 구하기 위한 해당 상품의 요금제 정보 리스트를 조회

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `selectMspSaleSubsdMstListWithRateInfo` |
| 연동명(한글) | 최저가를 구하기 위한 해당 상품의 요금제 정보 리스트를 조회 |
| 엔드포인트 | `/msp/mspSaleSubsdMstList` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | MspMapper.xml |
| SQL ID | `selectMspSaleSubsdMstListWithRateInfo` |
| parameterType | `com.ktmmobile.mcp.mspservice.dto.MspSaleSubsdMstDto` |
| resultType | `com.ktmmobile.mcp.mspservice.dto.MspSaleSubsdMstDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_SALE_SUBSD_MST, MSP_RATE_MST |
| 담당자 | 김대원 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `salePlcyCd` | 판매정책코드 | `String` | 판매정책코드 | SALE_PLCY_CD | MCP_USIM_PROD_SORT |
| `prdtId` | 단말ID | `String` | 단말ID | PRDT_ID | MSP_SALE_SUBSD_MST |
| `payClCd` | 선후불구분 | `String` | 선후불구분 | PAY_CL_CD | MSP_SALE_SUBSD_MST |
| `oldYn` | 중고여부 | `String` | 중고여부 | OLD_YN | MSP_SALE_SUBSD_MST |
| `orgnId` | 조직코드 | `String` | 조직코드 | ORGN_ID | MSP_SALE_SUBSD_MST |
| `operType` | 업무구분 | `String` | 업무구분      * HCN3 : 기기변경      * HDN3 : 보상기변3G      * MNP3 : 번호이동      * NAC3 : 신규 | OPER_TYPE | MSP_SALE_SUBSD_MST |
| `rateCd` | 요금제코드 | `String` | 요금제코드 | RATE_CD | MSP_SALE_SUBSD_MST |
| `noArgmYn` | 무약정여부 | `String` | 무약정여부 |  |  |
| `agrmTrm` | 약정코드 | `String` | 약정코드 | AGRM_TRM | MSP_SALE_SUBSD_MST |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `salePlcyCd` | 판매정책코드 | `String` | 판매정책코드 | SALE_PLCY_CD | MSP_SALE_SUBSD_MST |
| `rateCdSu` | 요금제코드 | `String` | 요금제코드 | RATE_CD_SU | MSP_SALE_SUBSD_MST |
| `prdtId` | 단말ID | `String` | 단말ID | PRDT_ID | MSP_SALE_SUBSD_MST |
| `oldYn` | 중고여부 | `String` | 중고여부 | OLD_YN | MSP_SALE_SUBSD_MST |
| `agrmTrm` | 약정코드 | `String` | 약정코드 | AGRM_TRM | MSP_SALE_SUBSD_MST |
| `operType` | 업무구분 | `String` | 업무구분      * HCN3 : 기기변경      * HDN3 : 보상기변3G      * MNP3 : 번호이동      * NAC3 : 신규 | OPER_TYPE | MSP_SALE_SUBSD_MST |
| `orgnId` | 조직코드 | `String` | 조직코드 | ORGN_ID | MSP_SALE_SUBSD_MST |
| `hndstAmt` | 단말금액(VAT)포함 | `int` | 단말금액(VAT)포함 | HNDST_AMT | MSP_SALE_SUBSD_MST |
| `subsdAmt` | 공시지원금(VAT포함) | `int` | 공시지원금(VAT포함) | SUBSD_AMT | MSP_SALE_SUBSD_MST |
| `instAmt` | 할부원금 (단말기 가격 - 공시지원금) (VAT포함) | `int` | 할부원금 (단말기 가격 - 공시지원금) (VAT포함) | INST_AMT | MSP_SALE_SUBSD_MST |
| `instCmsn` | 할부수수료(VAT포함) | `int` | 할부수수료(VAT포함) | INST_CMSN | MSP_SALE_SUBSD_MST |
| `agncySubsdMax` | 대리점보조금[추가지원금]MAX(VAT포함) | `int` | 대리점보조금[추가지원금]MAX(VAT포함) | AGNCY_SUBSD_MAX | MSP_SALE_SUBSD_MST |
| `agncySubsdAmt` | 대리점보조금[추가지원금](VAT포함) | `int` | 대리점보조금[추가지원금](VAT포함) | AGNCY_SUBSD_AMT | MSP_SALE_SUBSD_MST |
| `regstIdSu` | 등록자ID | `String` | 등록자ID | REGST_ID_SU | MSP_SALE_SUBSD_MST |
| `regstDttmSu` | 등록일시 | `Date` | 등록일시 | REGST_DTTM_SU | MSP_SALE_SUBSD_MST |
| `rvisnIdSu` | 수정자ID | `String` | 수정자ID | RVISN_ID_SU | MSP_SALE_SUBSD_MST |
| `sprtTp` | 지원금유형 | `String` | 지원금유형 (단말할인:KD , 요금할인:PM)      * 단말할인:KD      * 요금할인:PM      * 심플할인:SM   (유심, 약정 | SPRT_TP | MSP_SALE_SUBSD_MST |
| `dcAmt` | 할인금액(약정할인선택시 할인금액) | `int` | 할인금액(약정할인선택시 할인금액) | DC_AMT | MSP_SALE_SUBSD_MST |
| `addDcAmt` | 추가할인금액(요금할인선택시 할인금액  단말할인과는 반대된다(단말할인시 공시지원금 대리금 지원금 지원)) | `int` | 추가할인금액(요금할인선택시 할인금액  단말할인과는 반대된다(단말할인시 공시지원금 대리금 지원금 지원)) | ADD_DC_AMT | MSP_SALE_SUBSD_MST |
| `rateCdRa` | 요금제코드 | `String` | 요금제코드 | RATE_CD_RA | MSP_RATE_MST |
| `applEndDt` | 적용종료일자 | `String` | 적용종료일자 | APPL_END_DT | MSP_RATE_MST |
| `applStrtDt` | 적용시작일자 | `String` | 적용시작일자 | APPL_STRT_DT | MSP_RATE_MST |
| `rateNm` | 요금제명 | `String` | 요금제명 | RATE_NM | MSP_RATE_MST |
| `rateGrpCd` | 요금제그룹코드 | `String` | 요금제그룹코드 | RATE_GRP_CD | MSP_RATE_MST |
| `payClCd` | 선후불구분 | `String` | 선후불구분 | PAY_CL_CD | MSP_RATE_MST |
| `rateType` | 요금제유형 | `String` | 요금제유형(ORG0008) | RATE_TYPE | MSP_RATE_MST |
| `dataType` | 데이터유형 | `String` | 데이터유형 (ORG0008) 'LTE,3G | DATA_TYPE | MSP_RATE_MST |
| `baseAmt` | 기본료 | `int` | 기본료 | BASE_AMT | MSP_RATE_MST |
| `freeCallClCd` | 망내외무료통화구분 | `String` | 망내외무료통화구분 | FREE_CALL_CL_CD | MSP_RATE_MST |
| `freeCallCnt` | 무료통화건수 | `String` | 무료통화건수 | FREE_CALL_CNT | MSP_RATE_MST |
| `nwInCallCnt` | 망내무료통화건수 | `String` | 망내무료통화건수 | NW_IN_CALL_CNT | MSP_RATE_MST |
| `nwOutCallCnt` | 망외무료통화건수 | `String` | 망외무료통화건수 | NW_OUT_CALL_CNT | MSP_RATE_MST |
| `freeSmsCnt` | 무료문자건수 | `String` | 무료문자건수 | FREE_SMS_CNT | MSP_RATE_MST |
| `freeDataCnt` | 무료데이터건수 | `String` | 무료데이터건수 | FREE_DATA_CNT | MSP_RATE_MST |
| `rmk` | 비고 | `String` | 비고 | RMK | MSP_RATE_MST |
| `regstId` | 등록자ID | `String` | 등록자ID | REGST_ID | MSP_RATE_MST |
| `regstDttm` | 등록일시 | `Date` | 등록일시 | REGST_DTTM | MSP_RATE_MST |
| `rvisnId` | 수정자ID | `String` | 수정자ID | RVISN_ID | MSP_RATE_MST |
| `rvisnDttm` | 수정일시 | `Date` | 수정일시 | RVISN_DTTM | MSP_RATE_MST |
| `onlineTypeCd` | 온라인유형코드 | `String` | 온라인유형코드 | ONLINE_TYPE_CD | MSP_RATE_MST |
| `alFlag` | 알요금제구분자 | `String` | 알요금제구분자 | AL_FLAG | MSP_RATE_MST |
| `cmnt` | 문구 | `String` | 문구 | CMNT | MSP_RATE_MST |
| `serviceType` | 서비스유형 | `String` | 서비스유형 | SERVICE_TYPE | MSP_RATE_MST |
| `plcySctnCd` | 정책구분코드 | `String` | 정책구분코드 할부개월표시(외부서식지)      * 01 :  단말 구매   할부 개월 리스트 표현      * 02 :   USIM(유심)단독  | PLCY_SCTN_CD | MSP_SALE_SUBSD_MST |
| `onOffType` | 온라인오프라인구분 | `String` | 온라인오프라인구분 | ON_OFF_TYPE | MSP_SALE_SUBSD_MST |
| `paySort` | 관리자 정렬 기준 | `String` | 관리자 정렬 기준 | PAY_SORT | MSP_SALE_SUBSD_MST |

**SQL**

```sql
SELECT
SALE_PLCY_CD
, RATE_CD_SU
, PRDT_ID
, OLD_YN
, AGRM_TRM
, OPER_TYPE
, ORGN_ID
, HNDST_AMT
, SUBSD_AMT
, INST_AMT
, INST_CMSN
, AGNCY_SUBSD_MAX
, AGNCY_SUBSD_AMT
, REGST_ID_SU
, REGST_DTTM_SU
, RVISN_ID_SU
, SPRT_TP
, DC_AMT
, ADD_DC_AMT
, RATE_CD_RA
, APPL_END_DT
, APPL_STRT_DT
, RATE_NM
, RATE_GRP_CD
, PAY_CL_CD
, RATE_TYPE
, DATA_TYPE
, BASE_AMT
, FREE_CALL_CL_CD
, FREE_CALL_CNT
, NW_IN_CALL_CNT
, NW_OUT_CALL_CNT
, FREE_SMS_CNT
, FREE_DATA_CNT
<if test='forFrontFastYn == "C"'>
, RMK
</if>
, REGST_ID
, REGST_DTTM
, RVISN_ID
, RVISN_DTTM
, ONLINE_TYPE_CD
, AL_FLAG
, CMNT
, SERVICE_TYPE
, PLCY_SCTN_CD
, ON_OFF_TYPE
, PAY_SORT
FROM (
SELECT
SU.SALE_PLCY_CD,            --판매정책코드
SU.RATE_CD AS RATE_CD_SU,	--요금제코드
SU.PRDT_ID,                 --제품ID
SU.OLD_YN,                  --중고여부
SU.AGRM_TRM,                --약정기간
SU.OPER_TYPE,               --업무유형
SU.ORGN_ID,                 --조직ID
SU.HNDST_AMT,               --단말금액(VAT포함)
SU.SUBSD_AMT,               --공시지원금(VAT포함)
SU.INST_AMT,                --할부원금(VAT포함)
SU.INST_CMSN,               --할부수수료(VAT포함)
SU.AGNCY_SUBSD_MAX,         --대리점보조금MAX(VAT포함)
SU.AGNCY_SUBSD_AMT,         --대리점보조금(VAT포함)
SU.REGST_ID AS REGST_ID_SU,     --등록자ID
SU.REGST_DTTM AS REGST_DTTM_SU, --등록일시
SU.RVISN_ID AS RVISN_ID_SU,     --수정자ID
SU.RVISN_DTTM AS RVISN_DTTM_SU, --수정일시
SU.SPRT_TP,                 --지원금유형
SU.DC_AMT,                  --기본할인금액
SU.ADD_DC_AMT,               --추가할인금액
RA.RATE_CD AS RATE_CD_RA,	 --요금제코드
RA.APPL_END_DT,              --적용종료일자
RA.APPL_STRT_DT,             --적용시작일자
RA.RATE_NM,                  --요금제명
RA.RATE_GRP_CD,              --요금제그룹코드
RA.PAY_CL_CD,                --선후불구분
RA.RATE_TYPE,                --요금제유형( ORG0008 )
RA.DATA_TYPE,                --데이터유형( ORG0018 )
RA.BASE_AMT,                 --기본료
RA.FREE_CALL_CL_CD,          --망내외무료통화구분
RA.FREE_CALL_CNT,            --무료통화건수
RA.NW_IN_CALL_CNT,           --망내무료통화건수
RA.NW_OUT_CALL_CNT,          --망외무료통화건수
RA.FREE_SMS_CNT,             --무료문자건수
RA.FREE_DATA_CNT,            --무료데이터건수
<if test='forFrontFastYn == "C"'>
RA.RMK,                   --비고
</if>
RA.REGST_ID,                 --등록자ID
RA.REGST_DTTM,               --등록일시
RA.RVISN_ID,                 --수정자ID
RA.RVISN_DTTM,               --수정일시
RA.ONLINE_TYPE_CD,           --온라인유형코드
RA.AL_FLAG,                  --알요금제 구분자
RA.CMNT,					   -- 요금제혜택
RA.SERVICE_TYPE ,             --서비스유형
#{plcySctnCd}  AS PLCY_SCTN_CD  ,--정책구분코드(01:단말,02:유심)
#{onOffType}   AS ON_OFF_TYPE ,
NVL(PAY_SORT,'9999') PAY_SORT  -- 관리자 정렬 기준
FROM
MSP_SALE_SUBSD_MST@DL_MSP SU
, MSP_RATE_MST@DL_MSP RA
, (
SELECT * FROM MCP_USIM_PROD_SORT
WHERE 1=1
<choose>
<when test="salePlcyCd != null and salePlcyCd != ''">
AND SALE_PLCY_CD  = #{salePlcyCd}
</when>
<otherwise>
AND 1 = 0
</otherwise>
</choose>
AND PRDT_ID = #{prdtId}
) PR
WHERE
1=1
AND SU.RATE_CD = RA.RATE_CD
AND RA.RATE_CD = PR.RATE_CD(+)
<if test="salePlcyCd != null and salePlcyCd != ''">
AND SU.SALE_PLCY_CD  = #{salePlcyCd}	--정책코드 할인유형과 결합시 유니크
</if>
<if test="payClCd !=
-- ... (이하 생략)
```


#### API_0038 — 최저가를 구하기 위한 해당 상품의 요금제 정보 리스트를 조회

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `selectMspSaleSubsdMstListForLowPrice` |
| 연동명(한글) | 최저가를 구하기 위한 해당 상품의 요금제 정보 리스트를 조회 |
| 엔드포인트 | `/msp/mspSaleSubsdMstList` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | MspMapper.xml |
| SQL ID | `selectMspSaleSubsdMstListForLowPrice` |
| parameterType | `com.ktmmobile.mcp.mspservice.dto.MspSaleSubsdMstDto` |
| resultType | `com.ktmmobile.mcp.mspservice.dto.MspSaleSubsdMstDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_SALE_SUBSD_MST |
| 담당자 | 김대원 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `salePlcyCd` | 판매정책코드 | `String` | 판매정책코드 | SALE_PLCY_CD | MSP_SALE_SUBSD_MST |
| `prdtId` | 단말ID | `String` | 단말ID | PRDT_ID | MSP_SALE_SUBSD_MST |
| `orgnId` | 조직코드 | `String` | 조직코드 | ORGN_ID | MSP_SALE_SUBSD_MST |
| `oldYn` | 중고여부 | `String` | 중고여부 | OLD_YN | MSP_SALE_SUBSD_MST |
| `operType` | 업무구분 | `String` | 업무구분      * HCN3 : 기기변경      * HDN3 : 보상기변3G      * MNP3 : 번호이동      * NAC3 : 신규 | OPER_TYPE | MSP_SALE_SUBSD_MST |
| `rateCd` | 요금제코드 | `String` | 요금제코드 | RATE_CD | MSP_SALE_SUBSD_MST |
| `noArgmYn` | 무약정여부 | `String` | 무약정여부 |  |  |
| `agrmTrm` | 약정코드 | `String` | 약정코드 | AGRM_TRM | MSP_SALE_SUBSD_MST |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `hndstAmt` | 단말금액(VAT)포함 | `int` | 단말금액(VAT)포함 | HNDST_AMT | MSP_SALE_SUBSD_MST |
| `subsdAmt` | 공시지원금(VAT포함) | `int` | 공시지원금(VAT포함) | SUBSD_AMT | MSP_SALE_SUBSD_MST |
| `instAmt` | 할부원금 (단말기 가격 - 공시지원금) (VAT포함) | `int` | 할부원금 (단말기 가격 - 공시지원금) (VAT포함) | INST_AMT | MSP_SALE_SUBSD_MST |
| `instCmsn` | 할부수수료(VAT포함) | `int` | 할부수수료(VAT포함) | INST_CMSN | MSP_SALE_SUBSD_MST |
| `agncySubsdMax` | 대리점보조금[추가지원금]MAX(VAT포함) | `int` | 대리점보조금[추가지원금]MAX(VAT포함) | AGNCY_SUBSD_MAX | MSP_SALE_SUBSD_MST |
| `agncySubsdAmt` | 대리점보조금[추가지원금](VAT포함) | `int` | 대리점보조금[추가지원금](VAT포함) | AGNCY_SUBSD_AMT | MSP_SALE_SUBSD_MST |
| `sprtTp` | 지원금유형 | `String` | 지원금유형 (단말할인:KD , 요금할인:PM)      * 단말할인:KD      * 요금할인:PM      * 심플할인:SM   (유심, 약정 | SPRT_TP | MSP_SALE_SUBSD_MST |
| `baseAmt` | 기본료 | `int` | 기본료 | BASE_AMT | MSP_SALE_SUBSD_MST |
| `dcAmt` | 할인금액(약정할인선택시 할인금액) | `int` | 할인금액(약정할인선택시 할인금액) | DC_AMT | MSP_SALE_SUBSD_MST |
| `addDcAmt` | 추가할인금액(요금할인선택시 할인금액  단말할인과는 반대된다(단말할인시 공시지원금 대리금 지원금 지원)) | `int` | 추가할인금액(요금할인선택시 할인금액  단말할인과는 반대된다(단말할인시 공시지원금 대리금 지원금 지원)) | ADD_DC_AMT | MSP_SALE_SUBSD_MST |
| `rateCd` | 요금제코드 | `String` | 요금제코드 | RATE_CD | MSP_SALE_SUBSD_MST |
| `prdtId` | 단말ID | `String` | 단말ID | PRDT_ID | MSP_SALE_SUBSD_MST |
| `agrmTrm` | 약정코드 | `String` | 약정코드 | AGRM_TRM | MSP_SALE_SUBSD_MST |

**SQL**

```sql
SELECT
HNDST_AMT,               --단말금액(VAT포함)
SUBSD_AMT,               --공시지원금(VAT포함)
INST_AMT,                --할부원금(VAT포함)
INST_CMSN,               --할부수수료(VAT포함)
AGNCY_SUBSD_MAX,         --대리점보조금MAX(VAT포함)
AGNCY_SUBSD_AMT,         --대리점보조금(VAT포함)
SPRT_TP,                 --지원금유형
BASE_AMT,                --기본료
DC_AMT,                  --기본할인금액
ADD_DC_AMT,               --추가할인금액
RATE_CD,
PRDT_ID ,
AGRM_TRM
FROM
MSP_SALE_SUBSD_MST@DL_MSP
WHERE 1=1
<if test="salePlcyCd != null and salePlcyCd != ''">
AND SALE_PLCY_CD  = #{salePlcyCd}   --정책코드 할인유형과 결합시 유니크
</if>
<if test="prdtId != null and prdtId != ''">
AND PRDT_ID = #{prdtId}             --상품ID
</if>
<if test="orgnId != null and orgnId != ''">
AND ORGN_ID = #{orgnId}             --조직코드
</if>
AND OLD_YN  = #{oldYn}  			--중고여부
<if test="operType != null and operType != ''">
AND OPER_TYPE = #{operType}      --가입유형
</if>
<if test="rateCd != null and rateCd != ''">
AND RATE_CD = #{rateCd}
</if>
<choose>
<when test='noArgmYn == "Y"'>
<if test="agrmTrm !=null and agrmTrm != ''">
AND AGRM_TRM = #{agrmTrm} 					-- 약정기간 무약정0은 기본적으로 조회한다.
</if>
</when>
<otherwise>
<if test="agrmTrm !=null and agrmTrm != ''">
AND (AGRM_TRM = #{agrmTrm} or AGRM_TRM = '0')		-- 약정기간 무약정0은 기본적으로 조회한다.
</if>
</otherwise>
</choose>
```


#### API_0039 — findCmnGrpCdMst

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `findCmnGrpCdMst` |
| 엔드포인트 | `/msp/cmnGrpCdMst` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | MspMapper.xml |
| SQL ID | `findCmnGrpCdMst` |
| parameterType | `com.ktmmobile.mcp.mspservice.dto.CmnGrpCdMst` |
| resultType | `com.ktmmobile.mcp.mspservice.dto.CmnGrpCdMst` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | CMN_GRP_CD_MST |
| 담당자 | 김대원 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `grpId` | 코드그룹아이디 | `String` | 코드그룹아이디 | GRP_ID | CMN_GRP_CD_MST |
| `cdVal` | 코드값 | `String` | 코드값 | CD_VAL | CMN_GRP_CD_MST |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `grpId` | 코드그룹아이디 | `String` | 코드그룹아이디 | GRP_ID | CMN_GRP_CD_MST |
| `cdVal` | 코드값 | `String` | 코드값 | CD_VAL | CMN_GRP_CD_MST |
| `cdDsc` | 코드명 | `String` | 코드명 | CD_DSC | CMN_GRP_CD_MST |
| `usgYn` | 사용여부 | `String` | 사용여부 | USG_YN | CMN_GRP_CD_MST |
| `regstId` | 등록자ID | `String` | 등록자ID | REGST_ID | CMN_GRP_CD_MST |
| `regstDttm` | 등록일시 | `Date` | 등록일시 | REGST_DTTM | CMN_GRP_CD_MST |
| `rvisnId` | 수정자ID | `String` | 수정자ID | RVISN_ID | CMN_GRP_CD_MST |
| `rvisnDttm` | 수정일시 | `Date` | 수정일시 | RVISN_DTTM | CMN_GRP_CD_MST |
| `etc1` | 기타1 | `String` | 기타1 | ETC1 | CMN_GRP_CD_MST |
| `etc2` | 기타2 | `String` | 기타2 | ETC2 | CMN_GRP_CD_MST |
| `etc3` | 기타3 | `String` | 기타3 | ETC3 | CMN_GRP_CD_MST |
| `etc4` | 기타4 | `String` | 기타4 | ETC4 | CMN_GRP_CD_MST |
| `etc5` | 기타5 | `String` | 기타5 | ETC5 | CMN_GRP_CD_MST |
| `etc6` | 기타6 | `String` | 기타6 | ETC6 | CMN_GRP_CD_MST |

**SQL**

```sql
SELECT
GRP_ID,
CD_VAL,
CD_DSC,
USG_YN,
REGST_ID,
REGST_DTTM,
RVISN_ID,
RVISN_DTTM,
ETC1,
ETC2,
ETC3,
ETC4,
ETC5,
ETC6
FROM
CMN_GRP_CD_MST@DL_MSP
WHERE
GRP_ID = #{grpId}
AND CD_VAL = #{cdVal}
```


#### API_0040 — listRateByOrgnInfos

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `listRateByOrgnInfos` |
| 엔드포인트 | `/msp/rateByOrgnInfos` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | MspMapper.xml |
| SQL ID | `listRateByOrgnInfos` |
| parameterType | `com.ktmmobile.mcp.mspservice.dto.MspSalePlcyMstDto` |
| resultType | `com.ktmmobile.mcp.mspservice.dto.MspRateMstDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_SALE_ORGN_MST, MSP_SALE_PLCY_MST, MSP_SALE_RATE_MST, MSP_RATE_MST |
| 담당자 | 김대원 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `orgnId` | 조직코드 | `String` | 조직코드 | ORGN_ID | MSP_SALE_ORGN_MST |
| `sprtTp` | 지원금유형 | `String` | 지원금유형 (단말할인:KD , 요금할인:PM)      * 단말할인:KD      * 요금할인:PM      * 심플할인:SM   (유심, 약정 | SPRT_TP | MSP_SALE_ORGN_MST |
| `plcySctnCd` | 정책구분코드 | `String` | 정책구분코드 할부개월표시(외부서식지)      * 01 :  단말 구매   할부 개월 리스트 표현      * 02 :   USIM(유심)단독  | PLCY_SCTN_CD | MSP_SALE_PLCY_MST |
| `prdtSctnCd` | 제품구분코드 | `String` | 제품구분코드(02:3G , 03:LTE , 5G ) | PRDT_SCTN_CD | MSP_SALE_PLCY_MST |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `rateCd` | 요금제코드 | `String` | 요금제코드 | RATE_CD | MSP_RATE_MST |
| `applEndDt` | 적용종료일자 | `String` | 적용종료일자 | APPL_END_DT | MSP_RATE_MST |
| `applStrtDt` | 적용시작일자 | `String` | 적용시작일자 | APPL_STRT_DT | MSP_RATE_MST |
| `rateNm` | 요금제명 | `String` | 요금제명 | RATE_NM | MSP_RATE_MST |
| `rateGrpCd` | 요금제그룹코드 | `String` | 요금제그룹코드 | RATE_GRP_CD | MSP_RATE_MST |
| `payClCd` | 선후불구분 | `String` | 선후불구분 | PAY_CL_CD | MSP_RATE_MST |
| `rateType` | 요금제유형 | `String` | 요금제유형(ORG0008) | RATE_TYPE | MSP_RATE_MST |
| `dataType` | 데이터유형 | `String` | 데이터유형 (ORG0008) 'LTE,3G | DATA_TYPE | MSP_RATE_MST |
| `baseAmt` | 기본료 | `int` | 기본료 | BASE_AMT | MSP_RATE_MST |
| `freeCallClCd` | 망내외무료통화구분 | `String` | 망내외무료통화구분 | FREE_CALL_CL_CD | MSP_RATE_MST |
| `freeCallCnt` | 무료통화건수 | `String` | 무료통화건수 | FREE_CALL_CNT | MSP_RATE_MST |
| `nwInCallCnt` | 망내무료통화건수 | `String` | 망내무료통화건수 | NW_IN_CALL_CNT | MSP_RATE_MST |
| `nwOutCallCnt` | 망외무료통화건수 | `String` | 망외무료통화건수 | NW_OUT_CALL_CNT | MSP_RATE_MST |
| `freeSmsCnt` | 무료문자건수 | `String` | 무료문자건수 | FREE_SMS_CNT | MSP_RATE_MST |
| `freeDataCnt` | 무료데이터건수 | `String` | 무료데이터건수 | FREE_DATA_CNT | MSP_RATE_MST |
| `rmk` | 비고 | `String` | 비고 | RMK | MSP_RATE_MST |
| `onlineTypeCd` | 온라인유형코드 | `String` | 온라인유형코드 | ONLINE_TYPE_CD | MSP_RATE_MST |
| `alFlag` | 알요금제구분자 | `String` | 알요금제구분자 | AL_FLAG | MSP_RATE_MST |
| `cmnt` | 요금제혜택 | `String` | 요금제혜택 | CMNT | MSP_RATE_MST |
| `serviceType` | 서비스유형 | `String` | 서비스유형 | SERVICE_TYPE | MSP_RATE_MST |

**SQL**

```sql
SELECT
DISTINCT
RATE_MST.RATE_CD,
RATE_MST.APPL_END_DT,
RATE_MST.APPL_STRT_DT,
RATE_MST.RATE_NM,
RATE_MST.RATE_GRP_CD,
RATE_MST.PAY_CL_CD,
RATE_MST.RATE_TYPE,
RATE_MST.DATA_TYPE,
RATE_MST.BASE_AMT,
RATE_MST.FREE_CALL_CL_CD,
RATE_MST.FREE_CALL_CNT,
RATE_MST.NW_IN_CALL_CNT,
RATE_MST.NW_OUT_CALL_CNT,
RATE_MST.FREE_SMS_CNT,
RATE_MST.FREE_DATA_CNT,
RATE_MST.RMK,
RATE_MST.ONLINE_TYPE_CD,
RATE_MST.AL_FLAG,
RATE_MST.CMNT,	-- 요금제혜택
RATE_MST.SERVICE_TYPE
FROM
MSP_SALE_ORGN_MST@DL_MSP ORGN,
MSP_SALE_PLCY_MST@DL_MSP PLCY,
MSP_SALE_RATE_MST@DL_MSP RATE,
MSP_RATE_MST@DL_MSP RATE_MST
WHERE
ORGN.ORGN_ID = #{orgnId}
AND TO_CHAR(SYSDATE,'yyyymmddhh24miss') BETWEEN PLCY.SALE_STRT_DTTM AND PLCY.SALE_END_DTTM --판매기간 (필수)
AND PLCY.CNFM_YN = 'Y'
AND SPRT_TP = #{sprtTp}
AND PLCY.PLCY_SCTN_CD = #{plcySctnCd}
AND PLCY.PRDT_SCTN_CD = #{prdtSctnCd}
AND ORGN.SALE_PLCY_CD = PLCY.SALE_PLCY_CD
AND PLCY.SALE_PLCY_CD = RATE.SALE_PLCY_CD
AND RATE_MST.RATE_CD = RATE.RATE_CD
AND RATE_TYPE != '02'
ORDER BY RATE_MST.RATE_NM
```


#### API_0041 — 정책코드로 해당정책에 속해있는 요금제 리스트 조회

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `listRateBySalePlcyCd` |
| 연동명(한글) | 정책코드로 해당정책에 속해있는 요금제 리스트 조회 |
| 엔드포인트 | `/msp/mspRateMspBySalePlcyCd` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | MspMapper.xml |
| SQL ID | `listRateBySalePlcyCd` |
| parameterType | `com.ktmmobile.mcp.mspservice.dto.MspRateMstDto` |
| resultType | `com.ktmmobile.mcp.mspservice.dto.MspRateMstDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_SALE_PLCY_MST, MSP_SALE_RATE_MST, MSP_RATE_MST |
| 담당자 | 김대원 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `salePlcyCd` | 판매정책코드 | `String` | 판매정책코드 | SALE_PLCY_CD | MSP_SALE_PLCY_MST |
| `payClCd` | 선후불구분 | `String` | 선후불구분 | PAY_CL_CD | MSP_SALE_PLCY_MST |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `rateCd` | 요금제코드 | `String` | 요금제코드 | RATE_CD | MSP_RATE_MST |
| `applEndDt` | 적용종료일자 | `String` | 적용종료일자 | APPL_END_DT | MSP_RATE_MST |
| `applStrtDt` | 적용시작일자 | `String` | 적용시작일자 | APPL_STRT_DT | MSP_RATE_MST |
| `rateNm` | 요금제명 | `String` | 요금제명 | RATE_NM | MSP_RATE_MST |
| `rateGrpCd` | 요금제그룹코드 | `String` | 요금제그룹코드 | RATE_GRP_CD | MSP_RATE_MST |
| `payClCd` | 선후불구분 | `String` | 선후불구분 | PAY_CL_CD | MSP_RATE_MST |
| `rateType` | 요금제유형 | `String` | 요금제유형(ORG0008) | RATE_TYPE | MSP_RATE_MST |
| `dataType` | 데이터유형 | `String` | 데이터유형 (ORG0008) 'LTE,3G | DATA_TYPE | MSP_RATE_MST |
| `baseAmt` | 기본료 | `int` | 기본료 | BASE_AMT | MSP_RATE_MST |
| `freeCallClCd` | 망내외무료통화구분 | `String` | 망내외무료통화구분 | FREE_CALL_CL_CD | MSP_RATE_MST |
| `freeCallCnt` | 무료통화건수 | `String` | 무료통화건수 | FREE_CALL_CNT | MSP_RATE_MST |
| `nwInCallCnt` | 망내무료통화건수 | `String` | 망내무료통화건수 | NW_IN_CALL_CNT | MSP_RATE_MST |
| `nwOutCallCnt` | 망외무료통화건수 | `String` | 망외무료통화건수 | NW_OUT_CALL_CNT | MSP_RATE_MST |
| `freeSmsCnt` | 무료문자건수 | `String` | 무료문자건수 | FREE_SMS_CNT | MSP_RATE_MST |
| `freeDataCnt` | 무료데이터건수 | `String` | 무료데이터건수 | FREE_DATA_CNT | MSP_RATE_MST |
| `rmk` | 비고 | `String` | 비고 | RMK | MSP_RATE_MST |
| `onlineTypeCd` | 온라인유형코드 | `String` | 온라인유형코드 | ONLINE_TYPE_CD | MSP_RATE_MST |
| `alFlag` | 알요금제구분자 | `String` | 알요금제구분자 | AL_FLAG | MSP_RATE_MST |
| `cmnt` | 요금제혜택 | `String` | 요금제혜택 | CMNT | MSP_RATE_MST |
| `serviceType` | 서비스유형 | `String` | 서비스유형 | SERVICE_TYPE | MSP_RATE_MST |

**SQL**

```sql
SELECT
RATE_MST.RATE_CD,
RATE_MST.APPL_END_DT,
RATE_MST.APPL_STRT_DT,
RATE_MST.RATE_NM,
RATE_MST.RATE_GRP_CD,
RATE_MST.PAY_CL_CD,
RATE_MST.RATE_TYPE,
RATE_MST.DATA_TYPE,
RATE_MST.BASE_AMT,
RATE_MST.FREE_CALL_CL_CD,
RATE_MST.FREE_CALL_CNT,
RATE_MST.NW_IN_CALL_CNT,
RATE_MST.NW_OUT_CALL_CNT,
RATE_MST.FREE_SMS_CNT,
RATE_MST.FREE_DATA_CNT,
RATE_MST.RMK,
RATE_MST.ONLINE_TYPE_CD,
RATE_MST.AL_FLAG,
RATE_MST.CMNT,	-- 요금제혜택
RATE_MST.SERVICE_TYPE
FROM
MSP_SALE_PLCY_MST@DL_MSP PLCY,
MSP_SALE_RATE_MST@DL_MSP RATE,
MSP_RATE_MST@DL_MSP RATE_MST
WHERE
TO_CHAR(SYSDATE,'yyyymmddhh24miss') BETWEEN PLCY.SALE_STRT_DTTM AND PLCY.SALE_END_DTTM --판매기간 (필수)
AND PLCY.CNFM_YN = 'Y'
AND PLCY.SALE_PLCY_CD = RATE.SALE_PLCY_CD
AND RATE_MST.RATE_CD = RATE.RATE_CD
AND PLCY.SALE_PLCY_CD = #{salePlcyCd}
<if test="payClCd != null and payClCd != ''">
AND PAY_CL_CD = #{payClCd}
</if>
ORDER BY RATE_MST.RATE_NM
```


#### API_0042 — findMspSalePlcyMstBySalePlcyCd

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `findMspSalePlcyMstBySalePlcyCd` |
| 엔드포인트 | `/msp/mspSalePlcyInfo` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | MspMapper.xml |
| SQL ID | `findMspSalePlcyMstBySalePlcyCd` |
| parameterType | `java.lang.String` |
| resultType | `com.ktmmobile.mcp.mspservice.dto.MspSalePlcyMstDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_SALE_PLCY_MST |
| 담당자 | 김대원 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `salePlcyCd` | 판매정책코드 | `String` | 판매정책코드 | SALE_PLCY_CD | MSP_SALE_PLCY_MST |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `salePlcyCd` | 판매정책코드 | `String` | 판매정책코드 | SALE_PLCY_CD | MSP_SALE_PLCY_MST |
| `salePlcyNm` | 판매정책명 | `String` | 판매정책명 | SALE_PLCY_NM | MSP_SALE_PLCY_MST |
| `saleStrtDttm` | 판매시작일시 | `String` | 판매시작일시 | SALE_STRT_DTTM | MSP_SALE_PLCY_MST |
| `saleEndDttm` | 판매종료일시 | `String` | 판매종료일시 | SALE_END_DTTM | MSP_SALE_PLCY_MST |
| `plcyTypeCd` | 정책유형코드 | `String` | 정책유형코드 M:오프라인, O:온라인, D:직영온라인 | PLCY_TYPE_CD | MSP_SALE_PLCY_MST |
| `orgnType` | 조직유형 | `String` | 조직유형 | ORGN_TYPE | MSP_SALE_PLCY_MST |
| `plcySctnCd` | 정책구분코드 | `String` | 정책구분코드 할부개월표시(외부서식지)      * 01 :  단말 구매   할부 개월 리스트 표현      * 02 :   USIM(유심)단독  | PLCY_SCTN_CD | MSP_SALE_PLCY_MST |
| `prdtSctnCd` | 제품구분코드 | `String` | 제품구분코드(02:3G , 03:LTE , 5G ) | PRDT_SCTN_CD | MSP_SALE_PLCY_MST |
| `applSctnCd` | 적용구분코드 | `String` | 적용구분코드 O:개통일, R:접수일 | APPL_SCTN_CD | MSP_SALE_PLCY_MST |
| `instRate` | 할부이자율 | `BigDecimal` | 할부이자율 | INST_RATE | MSP_SALE_PLCY_MST |
| `newYn` | 가입유형 신규 | `String` | 가입유형 신규 | NEW_YN | MSP_SALE_PLCY_MST |
| `mnpYn` | 가입유형 번호이동 | `String` | 가입유형 번호이동 | MNP_YN | MSP_SALE_PLCY_MST |
| `hcnYn` | 가입유형 기기변경 | `String` | 가입유형 기기변경 | HCN_YN | MSP_SALE_PLCY_MST |
| `hdnYn` | 우수기변여부 | `String` | 우수기변여부 | HDN_YN | MSP_SALE_PLCY_MST |
| `cnfmYn` | 확정여부 | `String` | 확정여부 | CNFM_YN | MSP_SALE_PLCY_MST |
| `cnfmId` | 확정자ID | `String` | 확정자ID | CNFM_ID | MSP_SALE_PLCY_MST |
| `cnfmDttm` | 확정일시 | `String` | 확정일시 | CNFM_DTTM | MSP_SALE_PLCY_MST |
| `regstId` | 등록자ID | `String` | 등록자ID | REGST_ID | MSP_SALE_PLCY_MST |
| `regstDttm` | 등록일시 | `Date` | 등록일시 | REGST_DTTM | MSP_SALE_PLCY_MST |
| `rvisnId` | 수정자ID | `String` | 수정자ID | RVISN_ID | MSP_SALE_PLCY_MST |
| `rvisnDttm` | 수정일시 | `Date` | 수정일시 | RVISN_DTTM | MSP_SALE_PLCY_MST |
| `sprtTp` | 지원금유형 | `String` | 지원금유형 (단말할인:KD , 요금할인:PM)      * 단말할인:KD      * 요금할인:PM      * 심플할인:SM   (유심, 약정 | SPRT_TP | MSP_SALE_PLCY_MST |

**SQL**

```sql
SELECT
PL.SALE_PLCY_CD ,					--판매정책코드
PL.SALE_PLCY_NM,                    --판매정책명
PL.SALE_STRT_DTTM,                  --판매시작일시
PL.SALE_END_DTTM,                   --판매종료일시
PL.PLCY_TYPE_CD,                    --정책유형코드 M:오프라인, O:온라인
PL.ORGN_TYPE,                       --조직유형
PL.PLCY_SCTN_CD,                    --정책구분코드 01:단말, 02:USIM
PL.PRDT_SCTN_CD,                    --제품구분코   02:3G, 03:LTE
PL.APPL_SCTN_CD,                    --적용구분코드 O:개통일, R:접수일
PL.INST_RATE,                       --할부이자율
PL.NEW_YN,                          --신규여부
PL.MNP_YN,                          --번호이동여부
PL.HCN_YN,                          --기변여부
PL.HDN_YN,                          --우수기변여부
PL.CNFM_YN,                         --확정여부
PL.CNFM_ID,                         --확정자ID
PL.CNFM_DTTM,                       --확정일시
PL.REGST_ID,                        --등록자ID
PL.REGST_DTTM,                      --등록일시
PL.RVISN_ID,                        --수정자ID
PL.RVISN_DTTM,                      --수정일시
PL.SPRT_TP                         --지원금유형
FROM
MSP_SALE_PLCY_MST@DL_MSP PL
WHERE
PL.SALE_PLCY_CD = #{salePlcyCd}
AND TO_CHAR(SYSDATE,'yyyymmddhh24miss') BETWEEN PL.SALE_STRT_DTTM AND PL.SALE_END_DTTM --판매기간 (필수)
AND PL.CNFM_YN = 'Y'
```


#### API_0043 — 정책에 해당하는 상품의 상세정보 조회

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `listMspSalePrdtMstBySalePlcyCd` |
| 연동명(한글) | 정책에 해당하는 상품의 상세정보 조회 |
| 엔드포인트 | `/msp/mspSalePrdtMstBySalePlcyCd` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | MspMapper.xml |
| SQL ID | `listMspSalePrdtMstBySalePlcyCd` |
| parameterType | `java.lang.String` |
| resultType | `com.ktmmobile.mcp.mspservice.dto.MspSalePrdtMstDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_SALE_PRDT_MST, CMN_INTM_MDL |
| 담당자 | 김대원 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `salePlcyCd` | 판매정책코드 | `String` | 판매정책코드 | SALE_PLCY_CD | MSP_SALE_PRDT_MST |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `prdtId` | 단말ID | `String` | 단말ID | PRDT_ID | MSP_SALE_PRDT_MST |
| `rprsPrdtId` | 대표모델아이디 | `String` | 대표모델아이디 | RPRS_PRDT_ID | MSP_SALE_PRDT_MST |
| `rprsYn` | 대표제품 여부 | `String` | 대표제품 여부 | RPRS_YN | MSP_SALE_PRDT_MST |
| `prdtNm` | 상품명 | `String` | 상품명 | PRDT_NM | MSP_SALE_PRDT_MST |
| `prdtCode` | 제품코드 | `String` | 제품코드 | PRDT_CODE | MSP_SALE_PRDT_MST |
| `prdtTypeCd` | 제품유형코드 | `String` | 제품유형코드 | PRDT_TYPE_CD | MSP_SALE_PRDT_MST |
| `prdtIndCd` | 제품구분코드 | `String` | 제품구분코드  (LTE:04, 3G:03) | PRDT_IND_CD | MSP_SALE_PRDT_MST |
| `prdtColrCd` | 색상코드 | `String` | 색상코드 | PRDT_COLR_CD | MSP_SALE_PRDT_MST |
| `mnfctId` | 제조사 ID | `String` | 제조사 ID | MNFCT_ID | MSP_SALE_PRDT_MST |
| `prdtLnchDt` | 제품 출시일자 | `String` | 제품 출시일자 | PRDT_LNCH_DT | MSP_SALE_PRDT_MST |
| `prdtDt` | 제품단종일자 | `String` | 제품단종일자 | PRDT_DT | MSP_SALE_PRDT_MST |
| `regId` |  | `String` |  | REG_ID | MSP_SALE_PRDT_MST |
| `regDttm` |  | `String` |  | REG_DTTM | MSP_SALE_PRDT_MST |
| `rvisnId` | 수정자ID | `String` | 수정자ID | RVISN_ID | MSP_SALE_PRDT_MST |
| `rvisnDttm` | 수정일시 | `Date` | 수정일시 | RVISN_DTTM | MSP_SALE_PRDT_MST |
| `salePlcyCd` | 판매정책코드 | `String` | 판매정책코드 | SALE_PLCY_CD | MSP_SALE_PRDT_MST |
| `prdtIdassPrdtId` |  | `String` |  | PRDT_IDASS_PRDT_ID | MSP_SALE_PRDT_MST |
| `oldYn` | 중고여부 | `String` | 중고여부 | OLD_YN | MSP_SALE_PRDT_MST |
| `newCmsnAmt` | 신규수수료 | `BigInteger` | 신규수수료 | NEW_CMSN_AMT | MSP_SALE_PRDT_MST |
| `mnpCmsnAmt` | MNP수수료 | `BigInteger` | MNP수수료 | MNP_CMSN_AMT | MSP_SALE_PRDT_MST |
| `hcnCmsnAmt` | 기변수수료 | `BigInteger` | 기변수수료 | HCN_CMSN_AMT | MSP_SALE_PRDT_MST |

**SQL**

```sql
SELECT
CMN.PRDT_ID,
CMN.RPRS_PRDT_ID,
CMN.RPRS_YN,
CMN.PRDT_NM,
CMN.PRDT_CODE,
CMN.PRDT_TYPE_CD,
CMN.PRDT_IND_CD,
CMN.PRDT_COLR_CD,
CMN.MNFCT_ID,
CMN.PRDT_LNCH_DT,
CMN.PRDT_DT,
CMN.REG_ID,
CMN.REG_DTTM,
CMN.RVISN_ID,
CMN.RVISN_DTTM,
SALE.SALE_PLCY_CD,
SALE.PRDT_ID AS S_PRDT_ID,
SALE.OLD_YN,
SALE.NEW_CMSN_AMT,
SALE.MNP_CMSN_AMT,
SALE.HCN_CMSN_AMT
FROM
MSP_SALE_PRDT_MST@DL_MSP SALE
INNER JOIN
CMN_INTM_MDL@DL_MSP CMN ON SALE.PRDT_ID = CMN.PRDT_ID
WHERE  SALE.SALE_PLCY_CD = #{salePlcyCd}
```


#### API_0044 — 요금제 목록 조회

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `listMspOfficialSupportRateNm` |
| 연동명(한글) | 요금제 목록 조회 |
| 엔드포인트 | `/msp/mspOfficialSupportRateNm` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | MspMapper.xml |
| SQL ID | `listMspOfficialSupportRateNm` |
| parameterType | `` |
| resultType | `com.ktmmobile.mcp.mspservice.dto.MspNoticSupportMstDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_OFCL_SUBSD_MST, MSP_RATE_MST |
| 담당자 | 김대원 |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `rateCd` | 요금제코드 | `String` | 요금제코드 | RATE_CD | MSP_OFCL_SUBSD_MST |
| `rateNm` | 요금제명 | `String` | 요금제명 | RATE_NM | MSP_RATE_MST |

**SQL**

```sql
SELECT
DISTINCT A.RATE_CD,
B.RATE_NM
FROM
MSP_OFCL_SUBSD_MST@DL_MSP A,
MSP_RATE_MST@DL_MSP B
WHERE
TO_CHAR(SYSDATE, 'yyyymmdd') BETWEEN A.APPL_STRT_DT AND A.APPL_END_DT
AND B.RATE_CD = A.RATE_CD
ORDER BY RATE_CD
```


#### API_0045 — 공시지원금 목록 조회

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `listMspOfficialNoticeSupport` |
| 연동명(한글) | 공시지원금 목록 조회 |
| 엔드포인트 | `/msp/mspOfficialNoticeSupport` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | MspMapper.xml |
| SQL ID | `listMspOfficialNoticeSupport` |
| parameterType | `com.ktmmobile.mcp.mspservice.dto.MspNoticSupportMstDto` |
| resultType | `com.ktmmobile.mcp.mspservice.dto.MspNoticSupportMstDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_RATE_MST, CMN_INTM_MDL, MSP_OFCL_SUBSD_MST, CMN_HNDST_AMT, CMN_GRP_CD_MST |
| 담당자 | 김대원 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `prdtIndCd` | 제품구분코드 | `String` | 제품구분코드  (LTE:04, 3G:03) | PRDT_IND_CD | CMN_INTM_MDL |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `rateCd` | 요금제코드 | `String` | 요금제코드 | RATE_CD | MSP_OFCL_SUBSD_MST |
| `prdtNm` | 상품명 | `String` | 상품명 | PRDT_NM | CMN_INTM_MDL |
| `prdtIndCd` | 제품구분코드 | `String` | 제품구분코드  (LTE:04, 3G:03) | PRDT_IND_CD | CMN_INTM_MDL |
| `prdtNm` | 상품명 | `String` | 상품명 | PRDT_NM | CMN_INTM_MDL |
| `rateNm` | 요금제명 | `String` | 요금제명 | RATE_NM | MSP_RATE_MST |
| `outUnitPric` | 출고가 | `String` | 출고가 | OUT_UNIT_PRIC | CMN_HNDST_AMT |
| `subsdAmt` | 공시지원금(VAT포함) | `int` | 공시지원금(VAT포함) | SUBSD_AMT | MSP_OFCL_SUBSD_MST |
| `pricAmt` | 판매가 | `String` | 판매가 | CASE
                  WHEN D.OUT_UNIT_PRIC - C.SUBSD_AMT<![CDATA[<]]> 0 THEN 0
                  ELSE D.OUT_UNIT_PRIC - C.SUBSD_AMT
               END AS PRIC_AMT | CMN_HNDST_AMT |
| `applStrtDt` | 적용시작일자 | `String` | 적용시작일자 | APPL_STRT_DT | MSP_OFCL_SUBSD_MST |

**SQL**

```sql
SELECT
DECODE(B.PRDT_IND_CD , '03', '3G' , '04', 'LTE') AS PRDT_IND_CD		-- 단말구분
,B.PRDT_NM  				--  모델명
,A.RATE_NM  				--  요금제
,D.OUT_UNIT_PRIC  	--  출고가
,C.SUBSD_AMT  			--  공시지원금
,CASE
WHEN D.OUT_UNIT_PRIC - C.SUBSD_AMT<![CDATA[<]]> 0 THEN 0
ELSE D.OUT_UNIT_PRIC - C.SUBSD_AMT
END AS PRIC_AMT -- 판매가
,C.APPL_STRT_DT -- 공시일
FROM
MSP_RATE_MST@DL_MSP A
,CMN_INTM_MDL@DL_MSP B
,(SELECT
A.RATE_CD,
A.PRDT_ID,
A.OLD_YN,
A.SUBSD_AMT,
A.APPL_STRT_DT,
A.APPL_END_DT
FROM
MSP_OFCL_SUBSD_MST@DL_MSP A
WHERE
to_char(sysdate, 'yyyymmdd') BETWEEN A.APPL_STRT_DT AND A.APPL_END_DT
and a.PRDT_ID not in (
select dtl_cd
from NMCP_CD_DTL
where cd_group_id ='OFCLLIST'
and use_yn ='Y'
)
GROUP BY A.RATE_CD, A.PRDT_ID, A.OLD_YN, A.SUBSD_AMT, A.APPL_STRT_DT, A.APPL_END_DT
) C
, (SELECT
RPRS_PRDT_ID,
OUT_UNIT_PRIC * 1.1 AS OUT_UNIT_PRIC FROM CMN_HNDST_AMT@DL_MSP A
WHERE
to_char(sysdate, 'yyyymmddHH24MISS') BETWEEN A.UNIT_PRIC_APPL_DTTM AND A.UNIT_PRIC_EXPR_DTTM
) D
WHERE
A.RATE_CD = C.RATE_CD
AND B.PRDT_ID   = C.PRDT_ID
AND C.PRDT_ID = D.RPRS_PRDT_ID
AND B.PRDT_DT > to_char(sysdate, 'yyyymmdd')
AND A.RATE_CD NOT IN (SELECT CD_VAL FROM CMN_GRP_CD_MST@DL_MSP WHERE GRP_ID = 'RCP9019' AND USG_YN = 'Y')
<choose>
<when test="prdtIndCd !=null and prdtIndCd != ''">
AND B.PRDT_IND_CD = #{prdtIndCd}  -- 단말유형LTE, 3G
<choose>
<when test='prdtIndCd == "03"'>
AND A.DATA_TYPE = '3G'
</when>
<when test='prdtIndCd == "04"'>
AND A.DATA_TYPE = 'LTE'
</when>
</choose>
</when>
<otherwise>
AND A.DATA_TYPE = DECODE (B.PRDT_IND_CD,'03','3G','LTE')
</otherwise >
</choose>
<if test="rateCd !=null and rateCd != ''">
AND A.RATE_CD = #{rateCd}  -- 요금제
</if>
<if test="prdtNm !=null and prdtNm != ''">
AND (UPPER(fnHTMLtoTEXT(B.PRDT_NM)) LIKE '%' || UPPER(#{prdtNm}) || '%' || '%' ESCAPE '!') --모델명으로 검색
</if>
ORDER 	BY
<if test="sortType !=null and sortType !=''">
<choose>
<when test='sortType == "sptH"'>
C.SUBSD_AMT DESC,
</when>
<when test='sortType == "sptL"'>
C.SUBSD_AMT ASC,
</when>
<when test='sortType == "prcH"'>
PRIC_AMT DESC,
</when>
<when test='sortType == "prcL"'>
PRIC_AMT ASC,
</when>
</choose>
</if>
B.PRDT_IND_CD DESC , B.PRDT_NM, A.RATE_NM
```


#### API_0046 — listMspOfficialNoticeSupportCount

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `listMspOfficialNoticeSupportCount` |
| 엔드포인트 | `/msp/mspOfficialNoticeSupportCount` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | MspMapper.xml |
| SQL ID | `listMspOfficialNoticeSupportCount` |
| parameterType | `com.ktmmobile.mcp.mspservice.dto.MspNoticSupportMstDto` |
| resultType | `Integer` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_RATE_MST, CMN_INTM_MDL, MSP_OFCL_SUBSD_MST, CMN_HNDST_AMT, CMN_GRP_CD_MST |
| 담당자 | 김대원 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `prdtNm` | 상품명 | `String` | 상품명 | PRDT_NM | CMN_INTM_MDL |
| `prdtIndCd` | 제품구분코드 | `String` | 제품구분코드  (LTE:04, 3G:03) | PRDT_IND_CD | CMN_INTM_MDL |
| `rateCd` | 요금제코드 | `String` | 요금제코드 | RATE_CD | MSP_RATE_MST |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `count` | 조회개수 | `int` | 조회개수 | COUNT |  |

**SQL**

```sql
SELECT
COUNT(*)
FROM
MSP_RATE_MST@DL_MSP A
,CMN_INTM_MDL@DL_MSP B
,(SELECT
A.RATE_CD,
A.PRDT_ID
FROM
MSP_OFCL_SUBSD_MST@DL_MSP A
WHERE
to_char(sysdate, 'yyyymmdd') BETWEEN A.APPL_STRT_DT AND A.APPL_END_DT
and a.PRDT_ID not in (
select dtl_cd
from NMCP_CD_DTL
where cd_group_id ='OFCLLIST'
and use_yn ='Y'
)
GROUP BY A.RATE_CD, A.PRDT_ID, A.OLD_YN, A.SUBSD_AMT, A.APPL_STRT_DT, A.APPL_END_DT
) C
, (SELECT
RPRS_PRDT_ID
FROM
CMN_HNDST_AMT@DL_MSP A
WHERE
to_char(sysdate, 'yyyymmddHH24MISS') BETWEEN A.UNIT_PRIC_APPL_DTTM AND A.UNIT_PRIC_EXPR_DTTM
) D
WHERE
A.RATE_CD = C.RATE_CD
AND B.PRDT_ID   = C.PRDT_ID
AND C.PRDT_ID = D.RPRS_PRDT_ID
AND B.PRDT_DT > to_char(sysdate, 'yyyymmdd')
AND A.RATE_CD NOT IN (SELECT CD_VAL FROM CMN_GRP_CD_MST@DL_MSP WHERE GRP_ID = 'RCP9019' AND USG_YN = 'Y')
<if test="prdtNm !=null and prdtNm != ''">
AND (UPPER(fnHTMLtoTEXT(B.PRDT_NM)) LIKE '%' || UPPER(#{prdtNm}) || '%' || '%' ESCAPE '!') --모델명으로 검색
</if>
<choose>
<when test="prdtIndCd !=null and prdtIndCd != ''">
AND B.PRDT_IND_CD = #{prdtIndCd}  -- 단말유형LTE, 3G
<choose>
<when test='prdtIndCd == "03"'>
AND A.DATA_TYPE = '3G'
</when>
<when test='prdtIndCd == "04"'>
AND A.DATA_TYPE = 'LTE'
</when>
</choose>
</when>
<otherwise>
AND A.DATA_TYPE = DECODE (B.PRDT_IND_CD,'03','3G','LTE')
</otherwise >
</choose>
<if test="rateCd !=null and rateCd != ''">
AND A.RATE_CD = #{rateCd}  -- 요금제
</if>
```


#### API_0047 — 계약번호의 주민번호 조회

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `getCustomerSsn` |
| 연동명(한글) | 계약번호의 주민번호 조회 |
| 엔드포인트 | `/msp/customerSsn` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | MspMapper.xml |
| SQL ID | `getCustomerSsn` |
| parameterType | `java.lang.String` |
| resultType | `java.lang.String` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_JUO_CUS_INFO, MSP_JUO_SUB_INFO |
| 담당자 | 김대원 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `contractNum` | 가입계약번호 | `String` | 가입계약번호 | CONTRACT_NUM | MSP_JUO_SUB_INFO |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `customerSsn` | 주민등록번호 | `String` | 주민등록번호 | CUSTOMER_SSN | MSP_JUO_CUS_INFO |

**SQL**

```sql
SELECT
CUSTOMER_SSN
FROM
MSP_JUO_CUS_INFO@DL_MSP T1
, MSP_JUO_SUB_INFO@DL_MSP T2
WHERE
T1.CUSTOMER_ID = T2.CUSTOMER_ID
AND T1.CUSTOMER_TYPE = 'I'
AND T1.CUST_IDNT_NO_IND_CD IN ('01','05')
AND T2.CONTRACT_NUM = #{contractNum}
```


#### API_0048 — sellUsimMgmtCount

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `sellUsimMgmtCount` |
| 엔드포인트 | `/msp/sellUsimMgmtCount` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | MspMapper.xml |
| SQL ID | `sellUsimMgmtCount` |
| parameterType | `java.lang.String` |
| resultType | `Integer` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_PARTNER_USIM_MST |
| 담당자 | 김대원 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `searchUsimNo` | USIM 카드 일련번호 | `String` | USIM 카드 일련번호 | USIM_NO | MSP_PARTNER_USIM_MST |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `count` | 조회개수 | `int` | 조회개수 | COUNT |  |

**SQL**

```sql
SELECT
COUNT(*)
FROM MSP_PARTNER_USIM_MST@DL_MSP
WHERE USIM_NO = #{searchUsimNo}
```


#### API_0049 — 요금제 정보 리스트 조회

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `getMspRateMst` |
| 연동명(한글) | 요금제 정보 리스트 조회 |
| 엔드포인트 | `/msp/mspRateMst` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | MspMapper.xml |
| SQL ID | `getMspRateMst` |
| parameterType | `string` |
| resultType | `com.ktmmobile.mcp.mspservice.dto.MspRateMstDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_RATE_MST |
| 담당자 | 김대원 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `rateCd` | 요금제코드 | `String` | 요금제코드 | RATE_CD | MSP_RATE_MST |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `rateCd` | 요금제코드 | `String` | 요금제코드 | RATE_CD | MSP_RATE_MST |
| `applEndDt` | 적용종료일자 | `String` | 적용종료일자 | APPL_END_DT | MSP_RATE_MST |
| `applStrtDt` | 적용시작일자 | `String` | 적용시작일자 | APPL_STRT_DT | MSP_RATE_MST |
| `rateNm` | 요금제명 | `String` | 요금제명 | RATE_NM | MSP_RATE_MST |
| `rateGrpCd` | 요금제그룹코드 | `String` | 요금제그룹코드 | RATE_GRP_CD | MSP_RATE_MST |
| `payClCd` | 선후불구분 | `String` | 선후불구분 | PAY_CL_CD | MSP_RATE_MST |
| `rateType` | 요금제유형 | `String` | 요금제유형(ORG0008) | RATE_TYPE | MSP_RATE_MST |
| `dataType` | 데이터유형 | `String` | 데이터유형 (ORG0008) 'LTE,3G | DATA_TYPE | MSP_RATE_MST |
| `baseAmt` | 기본료 | `int` | 기본료 | BASE_AMT | MSP_RATE_MST |
| `freeCallClCd` | 망내외무료통화구분 | `String` | 망내외무료통화구분 | FREE_CALL_CL_CD | MSP_RATE_MST |
| `freeCallCnt` | 무료통화건수 | `String` | 무료통화건수 | FREE_CALL_CNT | MSP_RATE_MST |
| `nwInCallCnt` | 망내무료통화건수 | `String` | 망내무료통화건수 | NW_IN_CALL_CNT | MSP_RATE_MST |
| `nwOutCallCnt` | 망외무료통화건수 | `String` | 망외무료통화건수 | NW_OUT_CALL_CNT | MSP_RATE_MST |
| `freeSmsCnt` | 무료문자건수 | `String` | 무료문자건수 | FREE_SMS_CNT | MSP_RATE_MST |
| `freeDataCnt` | 무료데이터건수 | `String` | 무료데이터건수 | FREE_DATA_CNT | MSP_RATE_MST |
| `rmk` | 비고 | `String` | 비고 | RMK | MSP_RATE_MST |
| `regstId` | 등록자ID | `String` | 등록자ID | REGST_ID | MSP_RATE_MST |
| `regstDttm` | 등록일시 | `Date` | 등록일시 | REGST_DTTM | MSP_RATE_MST |
| `rvisnId` | 수정자ID | `String` | 수정자ID | RVISN_ID | MSP_RATE_MST |
| `rvisnDttm` | 수정일시 | `Date` | 수정일시 | RVISN_DTTM | MSP_RATE_MST |
| `onlineTypeCd` | 온라인유형코드 | `String` | 온라인유형코드 | ONLINE_TYPE_CD | MSP_RATE_MST |
| `alFlag` | 알요금제구분자 | `String` | 알요금제구분자 | AL_FLAG | MSP_RATE_MST |
| `serviceType` | 서비스유형 | `String` | 서비스유형 | SERVICE_TYPE | MSP_RATE_MST |
| `cmnt` | 요금제혜택 | `String` | 요금제혜택 | CMNT | MSP_RATE_MST |

**SQL**

```sql
SELECT
RA.RATE_CD,                     --요금제코드
RA.APPL_END_DT,                    --적용종료일자
RA.APPL_STRT_DT,                   --적용시작일자
RA.RATE_NM,                        --요금제명
RA.RATE_GRP_CD,                    --요금제그룹코드
RA.PAY_CL_CD,                      --선후불구분
RA.RATE_TYPE,                      --요금제유형( ORG0008 )
RA.DATA_TYPE,                      --데이터유형( ORG0018 )
RA.BASE_AMT,                       --기본료
RA.FREE_CALL_CL_CD,                --망내외무료통화구분
RA.FREE_CALL_CNT,                  --무료통화건수
RA.NW_IN_CALL_CNT,                 --망내무료통화건수
RA.NW_OUT_CALL_CNT,                --망외무료통화건수
RA.FREE_SMS_CNT,                   --무료문자건수
RA.FREE_DATA_CNT,                  --무료데이터건수
RA.RMK,                            --비고
RA.REGST_ID,                       --등록자ID
RA.REGST_DTTM,                     --등록일시
RA.RVISN_ID,                       --수정자ID
RA.RVISN_DTTM,                     --수정일시
RA.ONLINE_TYPE_CD,                 --온라인유형코드
RA.AL_FLAG,                        --알요금제 구분자
RA.SERVICE_TYPE,                    --서비스유형
RA.CMNT                            -- 요금제혜택
FROM
MSP_RATE_MST@DL_MSP RA
WHERE RATE_CD = #{rateCd}
```


#### API_0050 — 할인금액을 조회

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `listMspSaleMst` |
| 연동명(한글) | 할인금액을 조회 |
| 엔드포인트 | `/msp/mspSaleMst` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | MspMapper.xml |
| SQL ID | `listMspSaleMst` |
| parameterType | `com.ktmmobile.mcp.mspservice.dto.MspSaleSubsdMstDto` |
| resultType | `com.ktmmobile.mcp.mspservice.dto.MspSaleSubsdMstDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_RATE_SPEC, MSP_RATE_MST |
| 담당자 | 김대원 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `agrmTrm` | 약정코드 | `String` | 약정코드 | AGRM_TRM | MSP_RATE_SPEC |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `rateCd` | 요금제코드 | `String` | 요금제코드 | RATE_CD | MSP_RATE_SPEC |
| `rateNm` | 요금제명 | `String` | 요금제명 | RATE_NM | MSP_RATE_MST |
| `agrmTrm` | 약정코드 | `String` | 약정코드 | AGRM_TRM | MSP_RATE_SPEC |
| `dcAmt` | 할인금액(약정할인선택시 할인금액) | `int` | 할인금액(약정할인선택시 할인금액) | NVL(A.TM_AMT, 0) AS DC_AMT | MSP_RATE_SPEC |
| `addDcAmt` | 추가할인금액(요금할인선택시 할인금액  단말할인과는 반대된다(단말할인시 공시지원금 대리금 지원금 지원)) | `int` | 추가할인금액(요금할인선택시 할인금액  단말할인과는 반대된다(단말할인시 공시지원금 대리금 지원금 지원)) | NVL(A.SP_AMT, 0) AS ADD_DC_AMT | MSP_RATE_SPEC |

**SQL**

```sql
SELECT  A.RATE_CD
, B.RATE_NM
, A.AGRM_TRM
, NVL(A.TM_AMT, 0) AS DC_AMT
, NVL(A.SP_AMT, 0) AS ADD_DC_AMT
FROM    (   SELECT  RATE_CD
, AGRM_TRM
, SUM(DECODE(A.SPRT_TP, 'KD', NVL(A.DC_AMT, 0))) AS TM_AMT
, SUM(DECODE(A.SPRT_TP, 'PM', NVL(A.DC_AMT, 0))) AS RT_AMT
, SUM(DECODE(A.SPRT_TP, 'SM', NVL(A.DC_AMT, 0))) AS SP_AMT
, MAX(DECODE(A.SPRT_TP, 'PM', A.APPL_STRT_DTTM, 'SM', A.APPL_STRT_DTTM)) AS APPL_STRT_DTTM
, MAX(DECODE(A.SPRT_TP, 'PM', A.APPL_END_DTTM, 'SM', A.APPL_END_DTTM)) AS APPL_END_DTTM
FROM    MSP_RATE_SPEC@DL_MSP A
WHERE   A.RATE_CD = #{rateCd}
AND     TO_CHAR(SYSDATE, 'YYYYMMDD')|| '000000' BETWEEN A.APPL_STRT_DTTM AND A.APPL_END_DTTM
GROUP BY A.RATE_CD, A.AGRM_TRM
) A
, MSP_RATE_MST@DL_MSP B
WHERE   A.RATE_CD = B.RATE_CD
AND AGRM_TRM =#{agrmTrm}
```


#### API_0051 — juoSubIngoCount

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `juoSubIngoCount` |
| 엔드포인트 | `/msp/juoSubIngoCount` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | MspMapper.xml |
| SQL ID | `juoSubIngoCount` |
| parameterType | `java.lang.String` |
| resultType | `Integer` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_JUO_SUB_INFO |
| 담당자 | 김대원 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `subscriberNo` | 전화번호_이전CTN | `String` | 전화번호_이전CTN | SUBSCRIBER_NO | MSP_JUO_SUB_INFO |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `count` | 조회개수 | `int` | 조회개수 | COUNT |  |

**SQL**

```sql
<![CDATA[
SELECT
COUNT(*)
FROM MSP_JUO_SUB_INFO@DL_MSP
WHERE SUBSCRIBER_NO = #{subscriberNo}
AND SUB_STATUS <> 'C'
]]>
```


---

### APPFORM 도메인 (16개)

#### API_0002 — 요금제 여부 확인

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `checkJejuCodeCount` |
| 연동명(한글) | 요금제 여부 확인 |
| 엔드포인트 | `/appform/checkJejuCodeCount` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | AppformMapper.xml |
| SQL ID | `checkJejuCodeCount` |
| parameterType | `java.lang.String` |
| resultType | `java.lang.Integer` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | CMN_GRP_CD_MST |
| 담당자 | 강채신 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `rateCd` | 요금제코드 | `String` | 요금제코드 | CD_VAL | CMN_GRP_CD_MST |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `` | 조회개수 | `int` | 조회개수 | COUNT(CD_VAL) | CMN_GRP_CD_MST |

**SQL**

```sql
SELECT COUNT(CD_VAL)
FROM CMN_GRP_CD_MST@DL_MSP
WHERE GRP_ID = 'ORG0051'
AND USG_YN = 'Y'
AND CD_VAL = #{rateCd}
```


#### API_0003 — 부가서비스 프로모션 등록

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `insertMcpRequestAdditionPromotion` |
| 연동명(한글) | 부가서비스 프로모션 등록 |
| 엔드포인트 | `/appform/mcpRequestAdditionPromotion` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | AppformMapper.xml |
| SQL ID | `insertMcpRequestAdditionPromotion` |
| parameterType | `com.ktmmobile.mcp.appform.dto.AppformReqDto` |
| resultType | `java.lang.Integer` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | VW_CHRG_PRMT_DTL |
| 담당자 | 강채신 |
| 비고 | INSERT |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `requestKey` | 가입신청_키 | `long` | 가입신청_키 | REQUEST_KEY | MCP_REQUEST_ADDITION |
| `cntpntShopId` | 접점코드 | `String` | 접점코드 | ORGN_ID | VW_CHRG_PRMT_DTL |
| `reqBuyType` | 구매유형 단말 | `String` | 구매유형 단말      * 구매:MM      * USIM(유심)단독 구매:UU | REQ_BUY_TYPE | VW_CHRG_PRMT_DTL |
| `enggMnthCnt` | 약정개월수 | `long` | 약정개월수 | ENGG_CNT_0
ENGG_CNT_12
ENGG_CNT_18
ENGG_CNT_24
ENGG_CNT_30
ENGG_CNT_36 | VW_CHRG_PRMT_DTL |
| `operType` | 업무구분 | `String` | 업무구분      * HCN3 : 기기변경      * HDN3 : 보상기변3G      * MNP3 : 번호이동      * NAC3 : 신규 | MNP_YN | VW_CHRG_PRMT_DTL |
| `socCode` | 요금제코드 | `String` | 요금제코드 | RATE_CD | VW_CHRG_PRMT_DTL |
| `onOffType` | 온라인오프라인구분 | `String` | 온라인오프라인구분 | ON_OFF_TYPE | VW_CHRG_PRMT_DTL |

**SQL**

```sql
INSERT INTO MCP_REQUEST_ADDITION (
REQUEST_KEY
, ADDITION_KEY
, ADDITION_NAME
, RANTAL
)
SELECT
#{requestKey}
, A.ADDITION_KEY
, B.ADDITION_NAME
, B.RANTAL
FROM
VW_CHRG_PRMT_DTL@DL_MSP A
, MCP_ADDITION B
WHERE 1=1
AND A.ADDITION_KEY = B.ADDITION_KEY
AND TO_CHAR(SYSDATE, 'YYYYMMDD') BETWEEN A.STRT_DT AND A.END_DT
AND A.ORGN_ID = #{cntpntShopId} --접점코드
AND A.REQ_BUY_TYPE = #{reqBuyType}       --구매유형
<choose>
<when test="enggMnthCnt == 0" >
AND     A.ENGG_CNT_0 = 'Y'
</when>
<when test="enggMnthCnt == 12" >
AND     A.ENGG_CNT_12 = 'Y'
</when>
<when test="enggMnthCnt == 18" >
AND     A.ENGG_CNT_18 = 'Y'
</when>
<when test="enggMnthCnt == 24" >
AND     A.ENGG_CNT_24 = 'Y'
</when>
<when test="enggMnthCnt == 30" >
AND     A.ENGG_CNT_30 = 'Y'
</when>
<when test="enggMnthCnt == 36" >
AND     A.ENGG_CNT_36 = 'Y'
</when>
<otherwise>
AND    1 = 0
</otherwise>
</choose>
<choose>
<when test="operType == 'MNP3'" >
AND     A.MNP_YN = 'Y'
</when>
<when test="operType == 'NAC3'" >
AND     A.NAC_YN = 'Y'
</when>
<otherwise>
AND    1 = 0
</otherwise>
</choose>
AND     A.RATE_CD = #{socCode} --요금제코드
AND     A.ON_OFF_TYPE = #{onOffType} --온라인오프라인구분
AND     A.USG_YN = 'Y'
```


#### API_0004 — 서식지 관련 MSP 코드 조회

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `getMspPrdtCode` |
| 연동명(한글) | 서식지 관련 MSP 코드 조회 |
| 엔드포인트 | `/appform/mspPrdtCode` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | AppformMapper.xml |
| SQL ID | `getMspPrdtCode` |
| parameterType | `com.ktmmobile.mcp.appform.dto.AppformReqDto` |
| resultType | `com.ktmmobile.mcp.appform.dto.McpRequestDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | CMN_INTM_MDL |
| 담당자 | 강채신 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `rprsPrdtId` | 대표모델아이디 | `String` | 대표모델아이디 | RPRS_PRDT_ID | CMN_INTM_MDL |
| `modelId` | 모델ID | `String` | 모델ID | PRDT_ID | CMN_INTM_MDL |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `reqModelName` | 모델명 | `String` | 모델명 | PRDT_CODE AS REQ_MODEL_NAME | CMN_INTM_MDL |
| `reqModelColor` | 모델색상 | `String` | 모델색상 | PRDT_COLR_CD AS REQ_MODEL_COLOR | CMN_INTM_MDL |
| `prdtNm` | 상품명 | `String` | 상품명 | PRDT_NM | CMN_INTM_MDL |
| `prdtId` | 단말ID | `String` | 단말ID | PRDT_ID | CMN_INTM_MDL |

**SQL**

```sql
SELECT
(SELECT PRDT_CODE FROM CMN_INTM_MDL@DL_MSP  WHERE PRDT_ID = T1.RPRS_PRDT_ID) AS REQ_MODEL_NAME
, PRDT_COLR_CD AS REQ_MODEL_COLOR
, PRDT_NM
, PRDT_ID
FROM
CMN_INTM_MDL@DL_MSP T1
WHERE 1=1
<if test="rprsPrdtId != null and rprsPrdtId != ''">
AND RPRS_PRDT_ID = #{rprsPrdtId}
</if>
AND PRDT_ID = #{modelId}
AND ROWNUM =1
</select>
```


#### API_0005 — 요금조회

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `getRequestJoinDataByRjoinKeyNew` |
| 연동명(한글) | 요금조회 |
| 엔드포인트 | `/appform/requestJoinDataByRjoinKeyNew` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | AppformMapper.xml |
| SQL ID | `getRequestJoinDataByRjoinKeyNew` |
| parameterType | `java.lang.Long` |
| resultType | `java.util.Map` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | CMN_GRP_CD_MST, MSP_RATE_MST, ORG_ORGN_INFO_MST, MSP_SALE_PLCY_MST, MSP_SALE_SUBSD_MST |
| 담당자 | 강채신 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `requestKey` | 가입신청_키 | `Long` | 가입신청_키 | REQUEST_KEY | MCP_REQUEST |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `MODEL_MM_CHARGE` |  | `Map<String>` |  | MODEL_MM_CHARGE |  |
| `MODEL_MM_PAYMENT` |  | `Map<String>` | 핸드폰 월 납부액  단말요금 : (할부원금+총할부수수료)/할부개월수 | MODEL_MM_PAYMENT |  |
| `REAL_MM_MONEY` |  | `Map<String>` | 월 할부금 | REAL_MM_MONEY |  |
| `BASIC_MM_PAYMENT` |  | `Map<String>` |  | BASIC_MM_PAYMENT |  |
| `CONTRACT_SUPPORT` |  | `Map<String>` |  | CONTRACT_SUPPORT |  |
| `MODEL_MONTHLY` |  | `Map<String>` |  | MODEL_MONTHLY |  |
| `PAGE4DEFAULT` |  | `Map<String>` |  | PAGE4DEFAULT |  |
| `REQUEST_KEY` |  | `Map<String>` |  | REQUEST_KEY |  |
| `INST_AMT` | 할부원금 | `Map<String>` | 할부원금 | INST_AMT |  |
| `VAL_POW` | 년 수수년 계산 | `Map<String>` | 년 수수년 계산 | VAL_POW |  |
| `DISCOUNTPR` |  | `Map<String>` |  | DISCOUNTPR |  |
| `MODEL_PRICE` |  | `Map<String>` |  | MODEL_PRICE |  |
| `PROD_TYPE` |  | `Map<String>` |  | PROD_TYPE |  |
| `MODEL_INSTALLMENT` |  | `Map<String>` |  | MODEL_INSTALLMENT |  |
| `INST_INTRATE` |  | `Map<String>` |  | INST_INTRATE |  |
| `MODEL_MONTHLY_SUB` |  | `Map<String>` |  | MODEL_MONTHLY_SUB |  |
| `RES_NO` |  | `Map<String>` |  | RES_NO |  |
| `OPER_TYPE` |  | `Map<String>` |  | OPER_TYPE |  |
| `CSTMR_TYPE_ORG` |  | `Map<String>` |  | CSTMR_TYPE_ORG |  |
| `CSTMR_TYPE` |  | `Map<String>` |  | CSTMR_TYPE |  |
| `ETC_SPECIAL` |  | `Map<String>` |  | ETC_SPECIAL |  |
| `REQ_USIM_NAME` |  | `Map<String>` |  | REQ_USIM_NAME |  |
| `REQ_USIM_SN` |  | `Map<String>` |  | REQ_USIM_SN |  |
| `PHONE_PAYMENT` |  | `Map<String>` |  | PHONE_PAYMENT |  |
| `REQ_IN_DAY_YYYY` |  | `Map<String>` |  | REQ_IN_DAY_YYYY |  |
| `REQ_IN_DAY_MM` |  | `Map<String>` |  | REQ_IN_DAY_MM |  |
| `REQ_IN_DAY_DD` |  | `Map<String>` |  | REQ_IN_DAY_DD |  |
| `REQ_ADDITION` |  | `Map<String>` |  | REQ_ADDITION |  |
| `REQ_ADDITION_PRICE` |  | `Map<String>` |  | REQ_ADDITION_PRICE |  |
| `USIM_PRICE_TYPE` |  | `Map<String>` |  | USIM_PRICE_TYPE |  |
| `USIM_PRICE` |  | `Map<String>` |  | USIM_PRICE |  |
| `JOIN_PRICE_TYPE` |  | `Map<String>` |  | JOIN_PRICE_TYPE |  |
| `CSTMR_NAME` |  | `Map<String>` |  | CSTMR_NAME |  |
| `CLAUSE_5G_COVERAGE_FLAG` |  | `Map<String>` |  | CLAUSE_5G_COVERAGE_FLAG |  |
| `CSTMR_NATIVE_RRN` |  | `Map<String>` |  | CSTMR_NATIVE_RRN |  |
| `CSTMR_FOREIGNER_RRN` |  | `Map<String>` |  | CSTMR_FOREIGNER_RRN |  |
| `CSTMR_FOREIGNER_NATION` |  | `Map<String>` |  | CSTMR_FOREIGNER_NATION |  |
| `CSTMR_FOREIGNER_PN` |  | `Map<String>` |  | CSTMR_FOREIGNER_PN |  |
| `CSTMR_RECEIVE_TEL_FN` |  | `Map<String>` |  | CSTMR_RECEIVE_TEL_FN |  |
| `CSTMR_BILL_SEND_CODE` |  | `Map<String>` |  | CSTMR_BILL_SEND_CODE |  |
| `CSTMR_MAIL` |  | `Map<String>` |  | CSTMR_MAIL |  |
| `CSTMR_FOREIGNER_SDATE_YYYY` |  | `Map<String>` |  | CSTMR_FOREIGNER_SDATE_YYYY |  |
| `CSTMR_FOREIGNER_SDATE_MM` |  | `Map<String>` |  | CSTMR_FOREIGNER_SDATE_MM |  |
| `CSTMR_FOREIGNER_SDATE_DD` |  | `Map<String>` |  | CSTMR_FOREIGNER_SDATE_DD |  |
| `CSTMR_FOREIGNER_EDATE_YYYY` |  | `Map<String>` |  | CSTMR_FOREIGNER_EDATE_YYYY |  |
| `CSTMR_FOREIGNER_EDATE_MM` |  | `Map<String>` |  | CSTMR_FOREIGNER_EDATE_MM |  |
| `CSTMR_FOREIGNER_EDATE_DD` |  | `Map<String>` |  | CSTMR_FOREIGNER_EDATE_DD |  |
| `CSTMR_ADDR` |  | `Map<String>` |  | CSTMR_ADDR |  |
| `CSTMR_ADDR_BJD` |  | `Map<String>` |  | CSTMR_ADDR_BJD |  |
| `CSTMR_PRIVATE_NUMBER` |  | `Map<String>` |  | CSTMR_PRIVATE_NUMBER |  |
| `CSTMR_JURIDICAL_NUMBER` |  | `Map<String>` |  | CSTMR_JURIDICAL_NUMBER |  |
| `CSTMR_JURIDICAL_RRN` |  | `Map<String>` |  | CSTMR_JURIDICAL_RRN |  |
| `REQ_PAY_TYPE` |  | `Map<String>` |  | REQ_PAY_TYPE |  |
| `REQ_BANK` |  | `Map<String>` |  | REQ_BANK |  |
| `REQ_ACCOUNT_NUMBER` |  | `Map<String>` |  | REQ_ACCOUNT_NUMBER |  |
| `REQ_CARD_YY` |  | `Map<String>` |  | REQ_CARD_YY |  |
| `REQ_CARD_MM` |  | `Map<String>` |  | REQ_CARD_MM |  |
| `REQ_ACCOUNT_NAME` |  | `Map<String>` |  | REQ_ACCOUNT_NAME |  |
| `REQ_ACCOUNT_RELATION` |  | `Map<String>` |  | REQ_ACCOUNT_RELATION |  |
| `REQ_WANT_NUMBER` |  | `Map<String>` |  | REQ_WANT_NUMBER |  |
| `REQ_WANT_NUMBER2` |  | `Map<String>` |  | REQ_WANT_NUMBER2 |  |
| `REQ_WANT_NUMBER3` |  | `Map<String>` |  | REQ_WANT_NUMBER3 |  |
| `REQ_WIRE_TYPE` |  | `Map<String>` |  | REQ_WIRE_TYPE |  |
| `REQ_AC01_BALANCE` |  | `Map<String>` |  | REQ_AC01_BALANCE |  |
| `REQ_AC01_AMOUNT` |  | `Map<String>` |  | REQ_AC01_AMOUNT |  |
| `REQ_AC02_DAY` |  | `Map<String>` |  | REQ_AC02_DAY |  |
| `REQ_AC02_AMOUNT` |  | `Map<String>` |  | REQ_AC02_AMOUNT |  |
| `REQ_GUIDE` |  | `Map<String>` |  | REQ_GUIDE |  |
| `REQ_GUIDE_FN` |  | `Map<String>` |  | REQ_GUIDE_FN |  |
| `REQ_GUIDE_MN` |  | `Map<String>` |  | REQ_GUIDE_MN |  |
| `REQ_GUIDE_RN` |  | `Map<String>` |  | REQ_GUIDE_RN |  |
| `MOVE_MOBILE_FN` | 핸드폰번호 | `Map<String>` | 핸드폰번호 | MOVE_MOBILE_FN |  |
| `MOVE_AUTH_TYPE` |  | `Map<String>` |  | MOVE_AUTH_TYPE |  |
| `MOVE_AUTH_NUMBER_1` |  | `Map<String>` |  | MOVE_AUTH_NUMBER_1 |  |
| `MOVE_AUTH_NUMBER_2` |  | `Map<String>` |  | MOVE_AUTH_NUMBER_2 |  |
| `MOVE_AUTH_NUMBER_3` |  | `Map<String>` |  | MOVE_AUTH_NUMBER_3 |  |
| `MOVE_AUTH_NUMBER_4` |  | `Map<String>` |  | MOVE_AUTH_NUMBER_4 |  |
| `MOVE_THISMONTH_PAY_TYPE` |  | `Map<String>` |  | MOVE_THISMONTH_PAY_TYPE |  |
| `MOVE_ALLOTMENT_STAT` |  | `Map<String>` |  | MOVE_ALLOTMENT_STAT |  |
| `MOVE_REFUND_AGREE_FLAG` |  | `Map<String>` |  | MOVE_REFUND_AGREE_FLAG |  |
| `MOVE_COMPANY` |  | `Map<String>` |  | MOVE_COMPANY |  |
| `MOVE_COMPANY_MVNO` |  | `Map<String>` |  | MOVE_COMPANY_MVNO |  |
| `MONTHLY_FEE` |  | `Map<String>` |  | MONTHLY_FEE |  |
| `DISCOUNT_PRICE` |  | `Map<String>` |  | DISCOUNT_PRICE |  |
| `RATE_NAME` |  | `Map<String>` |  | RATE_NAME |  |
| `TEL_MM_PATMENT` |  | `Map<String>` |  | TEL_MM_PATMENT |  |
| `REQ_CARD_COMPANY` |  | `Map<String>` |  | REQ_CARD_COMPANY |  |
| `REQ_CARD_NO` |  | `Map<String>` |  | REQ_CARD_NO |  |
| `PAGE3DEFAULT` |  | `Map<String>` |  | PAGE3DEFAULT |  |
| `AGENT_CODE` | 대리점_코드 | `Map<String>` | 대리점_코드 | AGENT_CODE |  |
| `NAME_CHANGE_NM` |  | `Map<String>` |  | NAME_CHANGE_NM |  |
| `NAME_CHANGE_TEL_FN` |  | `Map<String>` |  | NAME_CHANGE_TEL_FN |  |
| `NAME_CHANGE_RRN` |  | `Map<String>` |  | NAME_CHANGE_RRN |  |
| `NAME_CHANGE_PINSTALLMENT` |  | `Map<String>` |  | NAME_CHANGE_PINSTALLMENT |  |
| `MINOR_AGENT_NAME` |  | `Map<String>` |  | MINOR_AGENT_NAME |  |
| `MINOR_AGENT_RELATION` |  | `Map<String>` |  | MINOR_AGENT_RELATION |  |
| `MINOR_AGENT_RRN` |  | `Map<String>` |  | MINOR_AGENT_RRN |  |
| `MINOR_AGENT_TEL_FN` |  | `Map<String>` |  | MINOR_AGENT_TEL_FN |  |
| `ENTRUST_REQ_NM` |  | `Map<String>` |  | ENTRUST_REQ_NM |  |
| `ENTRUST_RES_NM` |  | `Map<String>` |  | ENTRUST_RES_NM |  |
| `ENTRUST_REQ_RELATION` |  | `Map<String>` |  | ENTRUST_REQ_RELATION |  |
| `ENTRUST_RES_RRN` |  | `Map<String>` |  | ENTRUST_RES_RRN |  |
| `ENTRUST_RES_TEL_FN` |  | `Map<String>` |  | ENTRUST_RES_TEL_FN |  |
| `SERVICE_TYPE` |  | `Map<String>` |  | SERVICE_TYPE |  |
| `REQ_PAY_OTHER_FLAG` |  | `Map<String>` |  | REQ_PAY_OTHER_FLAG |  |
| `REQ_CARD_NAME` |  | `Map<String>` |  | REQ_CARD_NAME |  |
| `CLAUSE_PRI_AD_FLAG` |  | `Map<String>` |  | CLAUSE_PRI_AD_FLAG |  |
| `REQ_BUY_TYPE` |  | `Map<String>` |  | REQ_BUY_TYPE |  |
| `REQ_MODEL_NAME` |  | `Map<String>` |  | REQ_MODEL_NAME |  |
| `REQ_MODEL_COLOR` |  | `Map<String>` |  | REQ_MODEL_COLOR |  |
| `MODEL_MONTHLY_TYPE` |  | `Map<String>` |  | MODEL_MONTHLY_TYPE |  |
| `OTHERS_PAYMENT_RRN` |  | `Map<String>` |  | OTHERS_PAYMENT_RRN |  |
| `OTHERS_PAYMENT_AG` |  | `Map<String>` |  | OTHERS_PAYMENT_AG |  |
| `OTHERS_PAYMENT_NM` |  | `Map<String>` |  | OTHERS_PAYMENT_NM |  |
| `OTHERS_PAYMENT_RELATION` |  | `Map<String>` |  | OTHERS_PAYMENT_RELATION |  |
| `SOC_CODECNTPNT_SHOP_ID` |  | `Map<String>` |  | SOC_CODECNTPNT_SHOP_ID |  |
| `MODEL_SALE_POLICY_CODE` |  | `Map<String>` |  | MODEL_SALE_POLICY_CODE |  |
| `ENGG_MNTH_CNT` |  | `Map<String>` |  | ENGG_MNTH_CNT |  |
| `NW_BLCK_AGRM_YN` |  | `Map<String>` |  | NW_BLCK_AGRM_YN |  |
| `APP_BLCK_AGRM_YN` |  | `Map<String>` |  | APP_BLCK_AGRM_YN |  |
| `CLAUSE_JEHU_FLAG` |  | `Map<String>` |  | CLAUSE_JEHU_FLAG |  |
| `APP_CD` |  | `Map<String>` |  | APP_CD |  |
| `APP_CD_NAMEETC_DISCOUNT` |  | `Map<String>` |  | APP_CD_NAMEETC_DISCOUNT |  |
| `MODEL_MONTHLY` |  | `Map<String>` |  | MODEL_MONTHLY |  |
| `CLAUSE_RENTAL_MODEL_CP` | 단말배상금 안내사항 동의여부 | `Map<String>` | 단말배상금 안내사항 동의여부 | CLAUSE_RENTAL_MODEL_CP |  |
| `CLAUSE_RENTAL_MODEL_CP_PR` | 단말배상금(부분파손) 안내사항 동의여부 | `Map<String>` | 단말배상금(부분파손) 안내사항 동의여부 | CLAUSE_RENTAL_MODEL_CP_PR |  |
| `CLAUSE_RENTAL_SERVICE` | 중고렌탈 프로그램 서비스 이용에 대한 동의서 동의여부 | `Map<String>` | 중고렌탈 프로그램 서비스 이용에 대한 동의서 동의여부 | CLAUSE_RENTAL_SERVICE |  |
| `CLAUSE_MPPS35_FLAG` | 선불요금제 MPPS35 제약사항 동의 여부 | `Map<String>` | 선불요금제 MPPS35 제약사항 동의 여부 | CLAUSE_MPPS35_FLAG |  |
| `CLAUSE_FINANCE_FLAG` | 동부화재 제휴 요금제 약관 동의 여부 | `Map<String>` | 동부화재 제휴 요금제 약관 동의 여부 | CLAUSE_FINANCE_FLAG |  |
| `CLAUSE_INSURANCE_FLAG` |  | `Map<String>` |  | CLAUSE_INSURANCE_FLAG |  |
| `INSR_CD` |  | `Map<String>` |  | INSR_CD |  |
| `ONLINE_AUTH_TYPE` | 온라인인증유형(C:카드X:공인인증서) | `Map<String>` | 온라인인증유형(C:카드X:공인인증서) | ONLINE_AUTH_TYPE |  |
| `INSR_PROD_CD` | 단말보험코드 | `Map<String>` | 단말보험코드 | INSR_PROD_CD |  |
| `CLAUSE_INSR_PROD_FLAG` | 단말보험가입동의 | `Map<String>` | 단말보험가입동의 | CLAUSE_INSR_PROD_FLAG |  |
| `INSR_AUTH_INFO` | 단말보험인증정보 | `Map<String>` | 단말보험인증정보 | INSR_AUTH_INFO |  |

**SQL**

```sql
SELECT CASE WHEN MAINDATA.MODEL_MONTHLY_SUB = 1 then 0
WHEN MODEL_PRICE-(MAINDATA.OPEN_DISCOUNT + MAINDATA.ADD_DISCOUNT) <![CDATA[<=]]> 0 then 0
ELSE TRUNC((MODEL_PRICE-(MAINDATA.OPEN_DISCOUNT + MAINDATA.ADD_DISCOUNT))*0.059/12*POWER((1+0.059/12), MODEL_MONTHLY)/(POWER((1+0.059/12), MODEL_MONTHLY)-1),0) - TRUNC(((MODEL_PRICE-(MAINDATA.OPEN_DISCOUNT + MAINDATA.ADD_DISCOUNT))/MODEL_MONTHLY),0)
END AS MODEL_MM_CHARGE,
CASE WHEN INST_AMT <![CDATA[<=]]> 0 THEN 0
ELSE DECODE(MAINDATA.MODEL_MONTHLY_SUB, 1, 0,
ROUND(
(  INST_AMT + ( TRUNC(INST_AMT * 0.059 / 12 * VAL_POW / (VAL_POW-1),0) * MODEL_MONTHLY_SUB - INST_AMT )
) / MODEL_MONTHLY_SUB
,0)
)
END AS MODEL_MM_PAYMENT, /*핸드폰 월 납부액  단말요금 : (할부원금+총할부수수료)/할부개월수 */
CASE WHEN MAINDATA.MODEL_MONTHLY_SUB = 1 THEN 0
WHEN MODEL_PRICE-(MAINDATA.OPEN_DISCOUNT + MAINDATA.ADD_DISCOUNT) <![CDATA[<=]]> 0 THEN 0
ELSE DECODE(MAINDATA.MODEL_MONTHLY_SUB, 1, 0, TRUNC((MODEL_PRICE-(MAINDATA.OPEN_DISCOUNT + MAINDATA.ADD_DISCOUNT))/ MAINDATA.MODEL_MONTHLY_SUB) )
END AS REAL_MM_MONEY, /* 월 할부금 */
CASE WHEN INST_AMT <![CDATA[>]]> 0 THEN
DECODE(MAINDATA.MODEL_MONTHLY_SUB, 1, MAINDATA.TEL_MM_PATMENT,
ROUND(
(  INST_AMT + ( TRUNC(INST_AMT * 0.059 / 12 * VAL_POW / (VAL_POW-1),0) * MODEL_MONTHLY_SUB - INST_AMT )
) / MODEL_MONTHLY_SUB
,0) + MAINDATA.TEL_MM_PATMENT
)
ELSE MAINDATA.TEL_MM_PATMENT
END AS BASIC_MM_PAYMENT,
MAINDATA.OPEN_DISCOUNT + MAINDATA.ADD_DISCOUNT AS CONTRACT_SUPPORT,
MODEL_MONTHLY,
'4' AS PAGE4DEFAULT,
MAINDATA.*
FROM         (
SELECT    REQUEST_KEY,
MODEL_PRICE-(NVL(MODEL_DISCOUNT1, 0) + NVL(MODEL_DISCOUNT2, 0)  + NVL(MODEL_DISCOUNT3, 0)) AS INST_AMT ,  -- 할부원금
POWER((1+0.059/12), MODEL_MONTHLY_SUB) AS VAL_POW , -- 년 수수년 계산
CASE WHEN SPRT_TP = 'KD' THEN '0'
WHEN SPRT_TP = 'PM' THEN '2'
WHEN REQ_BUY_TYPE = 'UU' AND ENGG_MNTH_CNT <![CDATA[>]]> 1 THEN '1'
ELSE '' END DISCOUNTPR,
MODEL_PRICE ,
PROD_TYPE ,
/* GREATEST(NVL( (MODEL_PRICE ) - NVL(MODEL_DISCOUNT1, 0) - NVL(MODEL_DISCOUNT2, 0) - NVL(MODEL_DISCOUNT3, 0), 0),0) AS MODEL_INSTALLMENT, */
DECODE(
PROD_TYPE,'02','0',
'01',GREATEST(NVL( (MODEL_PRICE ) - NVL(MODEL_DISCOUNT1, 0) - NVL(MODEL_DISCOUNT2, 0) - NVL(MODEL_DISCOUNT3, 0), 0),0),
NULL,GREATEST(NVL( (MODEL_PRICE ) - NVL(MODEL_DISCOUNT1, 0) - NVL(MODEL_DISCOUNT2, 0) - NVL(MODEL_DISCOUNT3, 0), 0),0)
) AS MODEL_INSTALLMENT,
/* MODEL_PRICE  as model_price,*/
/* GREATEST(NVL( (MODEL_PRICE ) - NVL(MODEL_DISCOUNT1, 0) - NVL(MODEL_DISCOUNT2, 0) - NVL(MODEL_DISCOUNT3, 0), 0),0) AS MODEL_INSTALLMENT,*/
NVL(MODEL_DISCOUNT1, 0) + NVL(MODEL_DISCOUNT2, 0) AS OPEN_DISCOUNT,
NVL(MODEL_DISCOUNT3, 0) AS ADD_DISCOUNT,
0 AS MODEL_DISCOUNT,
NVL(INST_INTRATE, 0.25 ) AS INST_INTRATE,
MODEL_MONTHLY_SUB,
RES_NO,
OPER_TYPE,
CSTMR_TYPE_ORG,
CSTMR_TYPE,
ETC_SPECIAL,
REQ_USIM_NAME,
REQ_USIM_SN,
PHONE_PAYMENT,
REQ_IN_DAY_YYYY,
REQ_IN_DAY_MM,
REQ_IN_DAY_DD,
REQ_ADDITION,
REQ_ADDITION_PRICE,
USIM_PRICE_TYPE,
USIM_PRICE,
JOIN_PRICE_TYPE,
CSTMR_NAME,
CLAUSE_5G_COVERAGE_FLAG,
CSTMR_NATIVE_RRN,
CSTMR_FOREIGNER_RRN,
CSTMR_FOREIGNER_NATION,
CSTM
-- ... (이하 생략)
```


#### API_0006 — 기기변경 고객정보 확인 일반기변 , 우수기변

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `selRMemberAjax` |
| 연동명(한글) | 기기변경 고객정보 확인 일반기변 , 우수기변 |
| 엔드포인트 | `/appform/selRMemberAjax` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | AppformMapper.xml |
| SQL ID | `selRMemberAjax` |
| parameterType | `com.ktmmobile.mcp.appform.dto.JuoSubInfoDto` |
| resultType | `com.ktmmobile.mcp.appform.dto.JuoSubInfoDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_JUO_ADD_INFO, MSP_JUO_SUB_INFO, MSP_JUO_CUS_INFO, MSP_DVC_CHG_AGNT_HST, CMN_GRP_CD_MST |
| 담당자 | 강채신 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `customerLinkName` | 고객명 | `String` | 고객명 | CUSTOMER_LINK_NAME | MSP_JUO_CUS_INFO |
| `subscriberNo` | 전화번호_이전CTN | `String` | 전화번호_이전CTN | SUBSCRIBER_NO | MSP_JUO_SUB_INFO |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `contractNum` | 가입계약번호 | `String` | 가입계약번호 | CONTRACT_NUM | MSP_JUO_SUB_INFO |
| `customerSsn` | 주민등록번호 | `String` | 주민등록번호 | USER_SSN | MSP_JUO_SUB_INFO |
| `customerLinkName` | 고객명 | `String` | 고객명 | CUSTOMER_LINK_NAME | MSP_JUO_CUS_INFO |
| `subscriberNo` | 전화번호_이전CTN | `String` | 전화번호_이전CTN | SUBSCRIBER_NO | MSP_JUO_SUB_INFO |
| `lstComActvDate` | 최초 개통일자 | `String` | 최초 개통일자 | LST_COM_ACTV_DATE | MSP_JUO_SUB_INFO |
| `iccId` | USIM 카드 일련번호 | `String` | USIM 카드 일련번호 | ICC_ID | MSP_JUO_SUB_INFO |

**SQL**

```sql
<![CDATA[
WITH CNTR AS (
SELECT
A.CONTRACT_NUM
, A.USER_SSN
, D.CUSTOMER_LINK_NAME
, A.SUBSCRIBER_NO
, A.SUB_STATUS
, A.LST_COM_ACTV_DATE
, A.ICC_ID
, ( -- 기변이력이 2건 이상인 경우 처리를 위하여 MAX 사용
SELECT  DISTINCT
MAX(DECODE(X.GRNT_INSR_MNGM_NO, 'none', SUBSTR(X.APP_START_DD, 1, 8), SUBSTR(X.GRNT_INSR_MNGM_NO, 1, 8))
|| NVL(X.ENGG_MNTH_CNT, '0')) OVER (PARTITION BY X.CONTRACT_NUM)
FROM    MSP_JUO_ADD_INFO@DL_MSP X
, MSP_DVC_CHG_AGNT_HST@DL_MSP Y
WHERE   X.CONTRACT_NUM = A.CONTRACT_NUM
AND     X.MODEL_CHN_CD = '01'
AND     X.CONTRACT_NUM = Y.CONTRACT_NUM
AND     X.EVNT_TRTM_NO = Y.EVNT_TRTM_NO
AND     Y.APPL_END_DTTM LIKE '99991231%'
UNION
--심플할인
SELECT  SUBSTR(MAX(X.SIM_START_DT), 1, 8) ||
MAX(X.ENGG_MNTH_CNT)
FROM    MSP_JUO_ADD_INFO@DL_MSP X
WHERE   X.CONTRACT_NUM = A.CONTRACT_NUM
AND     X.EVNT_CD = 'SIM'
AND     X.GRNT_INSR_STAT_CD = 'A'
AND     X.APP_END_DATE LIKE '99991231%'
GROUP BY X.CONTRACT_NUM
UNION
-- 기변개발전 우수기변 대상
SELECT  DECODE(X.GRNT_INSR_MNGM_NO, 'none', MIN(SUBSTR(X.APP_START_DD, 1, 8)), SUBSTR(X.GRNT_INSR_MNGM_NO, 1, 8)) ||
MAX(X.ENGG_MNTH_CNT)
FROM    MSP_JUO_ADD_INFO@DL_MSP X
WHERE   X.CONTRACT_NUM = A.CONTRACT_NUM
AND     X.EVNT_CD IN ('UPD','UAD')
AND     X.MODEL_CHN_CD = '01'
AND     NOT EXISTS ( SELECT 1 FROM MSP_DVC_CHG_AGNT_HST@DL_MSP WHERE CONTRACT_NUM = X.CONTRACT_NUM AND APPL_END_DTTM LIKE '99991231%' )
GROUP BY X.GRNT_INSR_MNGM_NO, X.CONTRACT_NUM
UNION
-- 일반적인 경우
SELECT  DISTINCT
X.LST_COM_ACTV_DATE ||
NVL(Y.ENGG_MNTH_CNT, '0')
FROM    MSP_JUO_SUB_INFO@DL_MSP X
, MSP_JUO_ADD_INFO@DL_MSP Y
WHERE   X.CONTRACT_NUM = A.CONTRACT_NUM
AND     X.CONTRACT_NUM = Y.CONTRACT_NUM
AND     Y.MODEL_CHN_CD <> '01'
AND     Y.ENGG_MNTH_CNT IS NOT NULL
AND     NOT EXISTS ( SELECT 1 FROM MSP_JUO_ADD_INFO@DL_MSP WHERE CONTRACT_NUM = Y.CONTRACT_NUM AND EVNT_CD = 'SIM' AND GRNT_INSR_STAT_CD = 'A' AND APP_END_DATE LIKE '99991231%' )
AND     NOT EXISTS ( SELECT 1 FROM MSP_JUO_ADD_INFO@DL_MSP WHERE CONTRACT_NUM = Y.CONTRACT_NUM AND EVNT_CD IN ('UPD', 'UAD') AND MODEL_CHN_CD = '01' )
AND     NOT EXISTS ( SELECT 1 FROM MSP_DVC_CHG_AGNT_HST@DL_MSP WHERE CONTRACT_NUM = X.CONTRACT_NUM AND APPL_END_DTTM LIKE '99991231%' )
) AS ENGG_INFO
, DECODE(B.CONTRACT_NUM, NULL, 'N', 'Y') AS HIST_YN
, DECODE(B.CONTRACT_NUM, NULL, '', SUBSTR(MAX(B.APPL_STRT_DTTM) OVER(PARTITION BY B.APPL_STRT_DTTM), 1, 8)) AS DVC_CHG_DT
, C.ENGG_MNTH_CNT AS DVC_ENGG_MNTH_CNT
FROM    MSP_JUO_SUB_INFO@DL_MSP A
, MSP_DVC_CHG_AGNT_HST@DL_MSP B
, MSP_JUO_ADD_INFO@DL_MSP C
, MSP_JUO_CUS_INFO@DL_MSP D
WHERE   A.SUB_STATUS <> 'C'
AND     A.PPPO = 'PO'
AND     A.CONTRACT_NUM = B.CONTRACT_NUM(+)
AND     B.REQUEST_KEY(+) IS NOT NULL
AND     ( B.APPL_END_DTTM(+) LIKE '99991231%'
OR TO_DATE(SUBSTR(B.APPL_END_DTTM(+), 1, 8), 'YYYYMMDD') - TO_DATE(SUBSTR(B.APPL_STRT_DTTM(+), 1, 8), 'YYYYMMDD') >= 15 )
AND     B.CONTRACT_NUM = C.CONTRACT_NUM(+)
AND     B.EVNT_TRTM_NO = C.EVNT_TRTM_NO(+)
AND     A.CUSTOMER_ID = D.CUSTOMER_ID
AND     REPLACE(D.CUSTOMER_LINK_NAME,' ') = UPPER(TRIM(#{customerLinkName}))
AND 
-- ... (이하 생략)
```


#### API_0007 — 기기변경 고객정보 확인 일반기변 , 우수기변

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `selRMemberAjaxReal` |
| 연동명(한글) | 기기변경 고객정보 확인 일반기변 , 우수기변 |
| 엔드포인트 | `/appform/selRMemberAjaxReal` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | AppformMapper.xml |
| SQL ID | `selRMemberAjaxReal` |
| parameterType | `com.ktmmobile.mcp.appform.dto.JuoSubInfoDto` |
| resultType | `com.ktmmobile.mcp.appform.dto.JuoSubInfoDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_JUO_ADD_INFO, MSP_JUO_SUB_INFO, MSP_JUO_CUS_INFO, MSP_DVC_CHG_AGNT_HST, CMN_GRP_CD_MST |
| 담당자 | 강채신 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `contractNum` | 가입계약번호 | `String` | 가입계약번호 | CONTRACT_NUM | MSP_JUO_SUB_INFO |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `contractNum` | 가입계약번호 | `String` | 가입계약번호 | CONTRACT_NUM | MSP_JUO_SUB_INFO |
| `userSsn` | 주민등록번호 | `String` | 주민등록번호 | USER_SSN | MSP_JUO_SUB_INFO |
| `customerLinkName` | 고객명 | `String` | 고객명 | CUSTOMER_LINK_NAME | MSP_JUO_CUS_INFO |
| `subscriberNo` | 전화번호_이전CTN | `String` | 전화번호_이전CTN | SUBSCRIBER_NO | MSP_JUO_SUB_INFO |
| `dvcChgYn` | 기변 여부 | `String` | 기변 여부 | DVC_CHG_YN |  |
| `dvcChgTp` |  | `String` |  | DVC_CHG_TP |  |
| `iccId` | USIM 카드 일련번호 | `String` | USIM 카드 일련번호 | ICC_ID | MSP_JUO_SUB_INFO |
| `cstmrMobileFn` | 핸드폰번호 | `String` | 핸드폰번호 | SUBSTR(A.SUBSCRIBER_NO, 1, 3) AS CSTMR_MOBILE_FN | MSP_JUO_SUB_INFO |
| `cstmrMobileMn` | 핸드폰번호 | `String` | 핸드폰번호 | SUBSTR(A.SUBSCRIBER_NO, 4, 4) AS CSTMR_MOBILE_MN | MSP_JUO_SUB_INFO |
| `cstmrMobileRn` | 핸드폰번호 | `String` | 핸드폰번호 | SUBSTR(A.SUBSCRIBER_NO, 8, 4) AS CSTMR_MOBILE_RN | MSP_JUO_SUB_INFO |

**SQL**

```sql
<![CDATA[
WITH CNTR AS (
SELECT  A.CONTRACT_NUM
, A.USER_SSN
, D.CUSTOMER_LINK_NAME
, A.SUBSCRIBER_NO
, A.SUB_STATUS
, A.LST_COM_ACTV_DATE
, A.ICC_ID
, ( -- 기변이력이 2건 이상인 경우 처리를 위하여 MAX 사용
SELECT  DISTINCT
MAX(DECODE(X.GRNT_INSR_MNGM_NO, 'none', SUBSTR(X.APP_START_DD, 1, 8), SUBSTR(X.GRNT_INSR_MNGM_NO, 1, 8))
|| NVL(X.ENGG_MNTH_CNT, '0')) OVER (PARTITION BY X.CONTRACT_NUM)
FROM    MSP_JUO_ADD_INFO@DL_MSP X
, MSP_DVC_CHG_AGNT_HST@DL_MSP Y
WHERE   X.CONTRACT_NUM = A.CONTRACT_NUM
AND     X.MODEL_CHN_CD = '01'
AND     X.CONTRACT_NUM = Y.CONTRACT_NUM
AND     X.EVNT_TRTM_NO = Y.EVNT_TRTM_NO
AND     Y.APPL_END_DTTM LIKE '99991231%'
UNION
--심플할인
SELECT  SUBSTR(MAX(X.SIM_START_DT), 1, 8) ||
MAX(X.ENGG_MNTH_CNT)
FROM    MSP_JUO_ADD_INFO@DL_MSP X
WHERE   X.CONTRACT_NUM = A.CONTRACT_NUM
AND     X.EVNT_CD = 'SIM'
AND     X.GRNT_INSR_STAT_CD = 'A'
AND     X.APP_END_DATE LIKE '99991231%'
GROUP BY X.CONTRACT_NUM
UNION
-- 기변개발전 우수기변 대상
SELECT  DECODE(X.GRNT_INSR_MNGM_NO, 'none', MIN(SUBSTR(X.APP_START_DD, 1, 8)), SUBSTR(X.GRNT_INSR_MNGM_NO, 1, 8)) ||
MAX(X.ENGG_MNTH_CNT)
FROM    MSP_JUO_ADD_INFO@DL_MSP X
WHERE   X.CONTRACT_NUM = A.CONTRACT_NUM
AND     X.EVNT_CD IN ('UPD','UAD')
AND     X.MODEL_CHN_CD = '01'
AND     NOT EXISTS ( SELECT 1 FROM MSP_DVC_CHG_AGNT_HST@DL_MSP WHERE CONTRACT_NUM = X.CONTRACT_NUM AND APPL_END_DTTM LIKE '99991231%' )
GROUP BY X.GRNT_INSR_MNGM_NO, X.CONTRACT_NUM
UNION
-- 일반적인 경우
SELECT  DISTINCT
X.LST_COM_ACTV_DATE ||
NVL(Y.ENGG_MNTH_CNT, '0')
FROM    MSP_JUO_SUB_INFO@DL_MSP X
, MSP_JUO_ADD_INFO@DL_MSP Y
WHERE   X.CONTRACT_NUM = A.CONTRACT_NUM
AND     X.CONTRACT_NUM = Y.CONTRACT_NUM
AND     Y.MODEL_CHN_CD <> '01'
AND     Y.ENGG_MNTH_CNT IS NOT NULL
AND     NOT EXISTS ( SELECT 1 FROM MSP_JUO_ADD_INFO@DL_MSP WHERE CONTRACT_NUM = Y.CONTRACT_NUM AND EVNT_CD = 'SIM' AND GRNT_INSR_STAT_CD = 'A' AND APP_END_DATE LIKE '99991231%' )
AND     NOT EXISTS ( SELECT 1 FROM MSP_JUO_ADD_INFO@DL_MSP WHERE CONTRACT_NUM = Y.CONTRACT_NUM AND EVNT_CD IN ('UPD', 'UAD') AND MODEL_CHN_CD = '01' )
AND     NOT EXISTS ( SELECT 1 FROM MSP_DVC_CHG_AGNT_HST@DL_MSP WHERE CONTRACT_NUM = X.CONTRACT_NUM AND APPL_END_DTTM LIKE '99991231%' )
) AS ENGG_INFO
, DECODE(B.CONTRACT_NUM, NULL, 'N', 'Y') AS HIST_YN
, DECODE(B.CONTRACT_NUM, NULL, '', SUBSTR(MAX(B.APPL_STRT_DTTM) OVER(PARTITION BY B.APPL_STRT_DTTM), 1, 8)) AS DVC_CHG_DT
, C.ENGG_MNTH_CNT AS DVC_ENGG_MNTH_CNT
FROM    MSP_JUO_SUB_INFO@DL_MSP A
, MSP_DVC_CHG_AGNT_HST@DL_MSP B
, MSP_JUO_ADD_INFO@DL_MSP C
, MSP_JUO_CUS_INFO@DL_MSP D
WHERE   A.SUB_STATUS <> 'C'
AND     A.PPPO = 'PO'
AND     A.CONTRACT_NUM = B.CONTRACT_NUM(+)
AND     B.REQUEST_KEY(+) IS NOT NULL
AND     ( B.APPL_END_DTTM(+) LIKE '99991231%'
OR TO_DATE(SUBSTR(B.APPL_END_DTTM(+), 1, 8), 'YYYYMMDD') - TO_DATE(SUBSTR(B.APPL_STRT_DTTM(+), 1, 8), 'YYYYMMDD') >= 15 )
AND     B.CONTRACT_NUM = C.CONTRACT_NUM(+)
AND     B.EVNT_TRTM_NO = C.EVNT_TRTM_NO(+)
AND     A.CUSTOMER_ID = D.CUSTOMER_ID
AND     A.CONTRACT_NUM = '563132144'
--    AND     D.CUSTOMER_LINK_NAME = '김종민'

-- ... (이하 생략)
```


#### API_0008 — 상품 색상 정보

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `selPrdtcolCd` |
| 연동명(한글) | 상품 색상 정보 |
| 엔드포인트 | `/appform/selPrdtcolCd` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | AppformMapper.xml |
| SQL ID | `selPrdtcolCd` |
| parameterType | `com.ktmmobile.mcp.appform.dto.AppformReqDto` |
| resultType | `java.lang.String` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | CMN_INTM_MDL |
| 담당자 | 강채신 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `rprsPrdtId` | 대표모델아이디 | `String` | 대표모델아이디 | RPRS_PRDT_ID | CMN_INTM_MDL |
| `modelId` | 모델ID | `String` | 모델ID | PRDT_ID | CMN_INTM_MDL |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `prdtColrCd` | 색상코드 | `String` | 색상코드 | PRDT_COLR_CD | CMN_INTM_MDL |

**SQL**

```sql
SELECT
PRDT_COLR_CD
FROM
CMN_INTM_MDL@DL_MSP
WHERE
RPRS_PRDT_ID = #{rprsPrdtId}
AND PRDT_ID = #{modelId}
```


#### API_0009 — 오픈마켓 (외부서식지)

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `getMarketRequest` |
| 연동명(한글) | 오픈마켓 (외부서식지) |
| 엔드포인트 | `/appform/marketRequest` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | AppformMapper.xml |
| SQL ID | `getMarketRequest` |
| parameterType | `com.ktmmobile.mcp.appform.dto.AppformReqDto` |
| resultType | `com.ktmmobile.mcp.appform.dto.AppformReqDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | ORG_ORGN_INFO_MST, MSP_SALE_ORGN_MST, MSP_SALE_PLCY_MST, MSP_SALE_PRDT_MST, CMN_INTM_MDL, MSP_SALE_OPER_MST, CMN_GRP_CD_MST, CMN_HNDST_AMT |
| 담당자 | 강채신 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `operType` | 업무구분 | `String` | 업무구분      * HCN3 : 기기변경      * HDN3 : 보상기변3G      * MNP3 : 번호이동      * NAC3 : 신규 | OPER_TYPE | MSP_SALE_OPER_MST |
| `reqModelName` | 모델명 | `String` | 모델명 | PRDT_CODE | CMN_INTM_MDL |
| `sprtTp` | 지원금유형 | `String` | 지원금유형 (단말할인:KD , 요금할인:PM)      * 단말할인:KD      * 요금할인:PM      * 심플할인:SM   (유심, 약정 | SPRT_TP | MSP_SALE_PLCY_MST |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `rprsPrdtId` | 대표모델아이디 | `String` | 대표모델아이디 | PRDT_ID AS RPRS_PRDT_ID | MSP_SALE_PRDT_MST |
| `prdtNm` | 상품명 | `String` | 상품명 | PRDT_NM | MSP_SALE_PRDT_MST |
| `prdtSctnCd` | 제품구분코드 | `String` | 제품구분코드(02:3G , 03:LTE , 5G ) | PRDT_SCTN_CD | MSP_SALE_PRDT_MST |
| `sprtTp` | 지원금유형 | `String` | 지원금유형 (단말할인:KD , 요금할인:PM)      * 단말할인:KD      * 요금할인:PM      * 심플할인:SM   (유심, 약정 | SPRT_TP | MSP_SALE_PLCY_MST |
| `plcySctnCd` | 정책구분코드 | `String` | 정책구분코드 할부개월표시(외부서식지)      * 01 :  단말 구매   할부 개월 리스트 표현      * 02 :   USIM(유심)단독  | PLCY_SCTN_CD | MSP_SALE_PLCY_MST |
| `cntpntShopId` | 채널점아이디_판매점코드 | `String` | 채널점아이디_판매점코드 | ORGN_ID AS CNTPNT_SHOP_ID | MSP_SALE_ORGN_MST |
| `modelSalePolicyCode` | 판매정책 코드 | `String` | 판매정책 코드 | SALE_PLCY_CD AS MODEL_SALE_POLICY_CODE | MSP_SALE_PLCY_MST |
| `operType` | 업무구분 | `String` | 업무구분      * HCN3 : 기기변경      * HDN3 : 보상기변3G      * MNP3 : 번호이동      * NAC3 : 신규 | OPER_TYPE | MSP_SALE_OPER_MST |
| `reqModelName` | 모델명 | `String` | 모델명 | PRDT_CODE AS REQ_MODEL_NAME | CMN_INTM_MDL |
| `agentCode` | 대리점_코드 | `String` | 대리점_코드 | AGENT_CODE | ORG_ORGN_INFO_MST |

**SQL**

```sql
SELECT
C.PRDT_ID AS RPRS_PRDT_ID
, PRDT_NM
, PRDT_SCTN_CD
, B.SPRT_TP
, PLCY_SCTN_CD
, A.ORGN_ID AS CNTPNT_SHOP_ID
, B.SALE_PLCY_CD AS MODEL_SALE_POLICY_CODE
, E.OPER_TYPE
, D.PRDT_CODE AS REQ_MODEL_NAME
, B.INST_RATE
, (
SELECT KT_ORG_ID
FROM ORG_ORGN_INFO_MST@DL_MSP
WHERE 1=1
AND ORGN_ID = #{cntpntShopId}
AND ROWNUM = 1
) AS AGENT_CODE
FROM
MSP_SALE_ORGN_MST@DL_MSP A
, MSP_SALE_PLCY_MST@DL_MSP B
, MSP_SALE_PRDT_MST@DL_MSP C
, CMN_INTM_MDL@DL_MSP D
, MSP_SALE_OPER_MST@DL_MSP E
, CMN_GRP_CD_MST@DL_MSP F
, CMN_HNDST_AMT@DL_MSP G
WHERE   TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS') BETWEEN B.SALE_STRT_DTTM AND B.SALE_END_DTTM
AND     B.CNFM_YN        = 'Y'
AND     A.SALE_PLCY_CD = B.SALE_PLCY_CD
AND     B.SALE_PLCY_CD = C.SALE_PLCY_CD
AND     C.PRDT_ID = D.PRDT_ID
AND     B.SALE_PLCY_CD = E.SALE_PLCY_CD
AND     A.ORGN_ID = #{cntpntShopId}
AND     B.SALE_PLCY_CD = #{modelSalePolicyCode}
AND     D.RPRS_YN = 'Y'
AND     D.PRDT_DT = '99991231'
AND     PRDT_IND_CD = F.CD_VAL
AND     E.OPER_TYPE = #{operType}
AND     D.PRDT_CODE = #{reqModelName}
<if test="sprtTp != null and sprtTp != ''">
AND     B.SPRT_TP = #{sprtTp}
</if>
and     F.GRP_ID = 'CMN0045'
AND     PRDT_IND_CD = F.CD_VAL
AND     C.PRDT_ID = G.RPRS_PRDT_ID
AND     TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS') BETWEEN G.UNIT_PRIC_APPL_DTTM AND G.UNIT_PRIC_EXPR_DTTM
```


#### API_0010 — 할부개월

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `selectModelMonthlyList` |
| 연동명(한글) | 할부개월 |
| 엔드포인트 | `/appform/modelMonthlyList` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | AppformMapper.xml |
| SQL ID | `selectModelMonthlyList` |
| parameterType | `com.ktmmobile.mcp.appform.dto.AppformReqDto` |
| resultType | `com.ktmmobile.mcp.appform.dto.AppformReqDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | ORG_INST_NOM_MST |
| 담당자 | 강채신 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `modelSalePolicyCode` | 판매정책 코드 | `String` | 판매정책 코드 | SALE_PLCY_CD | ORG_INST_NOM_MST |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `modelMonthly` | 단말할부개월수 | `String` | 단말할부개월수 | INST_NOM AS MODEL_MONTHLY | ORG_INST_NOM_MST |

**SQL**

```sql
SELECT
INST_NOM AS MODEL_MONTHLY
FROM    ORG_INST_NOM_MST@DL_MSP
WHERE   SALE_PLCY_CD = #{modelSalePolicyCode}
GROUP   BY INST_NOM
ORDER   BY INST_NOM
```


#### API_0011 — 약정

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `selectMonthlyListMarket` |
| 연동명(한글) | 약정 |
| 엔드포인트 | `/appform/monthlyListMarket` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | AppformMapper.xml |
| SQL ID | `selectMonthlyListMarket` |
| parameterType | `com.ktmmobile.mcp.appform.dto.AppformReqDto` |
| resultType | `com.ktmmobile.mcp.appform.dto.AppformReqDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_SALE_AGRM_MST |
| 담당자 | 강채신 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `modelSalePolicyCode` | 판매정책 코드 | `String` | 판매정책 코드 | SALE_PLCY_CD | MSP_SALE_AGRM_MST |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `enggMnthCnt` | 약정개월수 | `long` | 약정개월수 | AGRM_TRM AS ENGG_MNTH_CNT | MSP_SALE_AGRM_MST |

**SQL**

```sql
SELECT  AGRM_TRM AS ENGG_MNTH_CNT
FROM    MSP_SALE_AGRM_MST@DL_MSP
WHERE   SALE_PLCY_CD = #{modelSalePolicyCode}
```


#### API_0012 — 색상정보

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `selectPrdtColorList` |
| 연동명(한글) | 색상정보 |
| 엔드포인트 | `/appform/prdtColorList` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | AppformMapper.xml |
| SQL ID | `selectPrdtColorList` |
| parameterType | `com.ktmmobile.mcp.appform.dto.AppformReqDto` |
| resultType | `com.ktmmobile.mcp.appform.dto.AppformReqDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | CMN_INTM_MDL, CMN_GRP_CD_MST |
| 담당자 | 강채신 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `rprsPrdtId` | 대표모델아이디 | `String` | 대표모델아이디 | RPRS_PRDT_ID | CMN_INTM_MDL |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `reqModelColor` | 색상 코드 | `String` | 색상 코드 | PRDT_COLR_CD AS REQ_MODEL_COLOR | CMN_INTM_MDL |
| `reqModelColorNm` | 색상명 | `String` | 색상명 | CD_DSC AS REQ_MODEL_COLOR_NM | CMN_GRP_CD_MST |
| `modelId` | 모델ID | `String` | 모델ID | PRDT_ID AS MODEL_ID | CMN_INTM_MDL |
| `prdtNm` | 상품명 | `String` | 상품명 | PRDT_NM | CMN_INTM_MDL |

**SQL**

```sql
SELECT  A.PRDT_COLR_CD AS REQ_MODEL_COLOR
,B.CD_DSC AS REQ_MODEL_COLOR_NM
, PRDT_ID AS MODEL_ID
, PRDT_NM
FROM    CMN_INTM_MDL@DL_MSP A
, CMN_GRP_CD_MST@DL_MSP B
WHERE   A.RPRS_PRDT_ID = #{rprsPrdtId}
AND     A.RPRS_YN = 'N'
AND     A.PRDT_COLR_CD = B.CD_VAL
AND     B.GRP_ID = 'CMN0044'
```


#### API_0013 — 대리점 코드 패치

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `getAgentCode` |
| 연동명(한글) | 대리점 코드 패치 |
| 엔드포인트 | `/appform/agentCode` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | AppformMapper.xml |
| SQL ID | `getAgentCode` |
| parameterType | `java.lang.String` |
| resultType | `java.lang.String` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | ORG_ORGN_INFO_MST |
| 담당자 | 강채신 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `cntpntShopId` | 대리점 코드 | `String` | 대리점 코드 | ORGN_ID | ORG_ORGN_INFO_MST |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `ktOrgId` | 대리점 코드 | `String` | 대리점 코드 | KT_ORG_ID | ORG_ORGN_INFO_MST |

**SQL**

```sql
SELECT
KT_ORG_ID
FROM ORG_ORGN_INFO_MST@DL_MSP
WHERE 1=1
AND ORGN_ID = #{cntpntShopId}
AND ROWNUM = 1
```


#### API_0014 — 보험코드 정보

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `selectInsrCodeList` |
| 연동명(한글) | 보험코드 정보 |
| 엔드포인트 | `/appform/insrCodeList` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | AppformMapper.xml |
| SQL ID | `selectInsrCodeList` |
| parameterType | `` |
| resultType | `com.ktmmobile.mcp.appform.dto.AppformReqDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | CMN_GRP_CD_MST, MSP_GRP_INSR_MST |
| 담당자 | 강채신 |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `value` | 코드값 | `String` | 코드값 | CD_VAL AS VALUE | CMN_GRP_CD_MST |
| `text` | 코드명 | `String` | 코드명 | CD_DSC AS TEXT | CMN_GRP_CD_MST |

**SQL**

```sql
<![CDATA[
SELECT  A.CD_VAL    AS VALUE
, A.CD_DSC  AS TEXT
FROM  CMN_GRP_CD_MST@DL_MSP A
, ( SELECT  DISTINCT
REPLACE(SUBSTR(INSR_CD, START_POS, END_POS - START_POS), ',', '') AS INSR_CD
FROM    (   SELECT  DISTINCT
INSR_CD
, DECODE(LEVEL, 1, 1, INSTR(INSR_CD, ',', 1, LEVEL-1)) AS START_POS
, INSTR(INSR_CD, ',', 1, LEVEL) AS END_POS
, LEVEL AS LVL
FROM    (   SELECT  A.INSR_CD || ',' AS INSR_CD
, LENGTH(A.INSR_CD) AS ORI_LEN
, LENGTH(REPLACE(A.INSR_CD, ',', '')) AS NEW_LEN
FROM    MSP_GRP_INSR_MST@DL_MSP A
WHERE   TO_CHAR(SYSDATE,'YYYYMMDD') BETWEEN A.STRT_DT AND A.END_DT
AND     A.USG_YN = 'Y'
)
CONNECT BY LEVEL <= ORI_LEN - NEW_LEN + 1
)
) B
WHERE   A.CD_VAL = B.INSR_CD
AND     A.GRP_ID = 'CMN0246'
AND     A.USG_YN = 'Y'
AND     A.ETC2 = 'Y'
ORDER BY A.CD_VAL
]]>
```


#### API_0015 — 분실파손 보험 조회

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `getInsrProdList` |
| 연동명(한글) | 분실파손 보험 조회 |
| 엔드포인트 | `/appform/insrProdList` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | AppformMapper.xml |
| SQL ID | `getInsrProdList` |
| parameterType | `com.ktmmobile.mcp.appform.dto.IntmInsrRelDTO` |
| resultType | `com.ktmmobile.mcp.appform.dto.IntmInsrRelDTO` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_INTM_INSR_MST, MSP_INTM_INSR_REL |
| 담당자 | 강채신 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `rprsPrdtId` | 단말ID | `String` | 단말ID | RPRS_PRDT_ID | MSP_INTM_INSR_REL |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `insrProdCd` | 보험상품코드 | `String` | 보험상품코드 | INSR_PROD_CD | MSP_INTM_INSR_MST |
| `cmpnLmtAmt` | 보상한도금액 | `String` | 보상한도금액 | CMPN_LMT_AMT | MSP_INTM_INSR_MST |
| `insrEnggCnt` | 보험약정기간 | `String` | 보험약정기간 | INSR_ENGG_CNT | MSP_INTM_INSR_MST |
| `insrProdNm` | 보험상품명 | `String` | 보험상품명 | DTL_CD_NM AS INSR_PROD_NM | MSP_INTM_INSR_MST |

**SQL**

```sql
SELECT
DISTINCT
T1.INSR_PROD_CD
, T1.CMPN_LMT_AMT
, T1.INSR_ENGG_CNT
, DTL_CD_NM AS INSR_PROD_NM
FROM
MSP_INTM_INSR_MST@DL_MSP T1
, (
SELECT
DTL_CD
, DTL_CD_NM
FROM NMCP_CD_DTL
WHERE
CD_GROUP_ID = 'IntmInsrRelNm'
) T2
WHERE   1=1
AND T1.INSR_PROD_CD = T2.DTL_CD(+)
AND T1.USG_YN = 'Y'
<if test="reqBuyType != null and reqBuyType != ''">
<choose>
<when test='reqBuyType == "UU"'>
AND T1.INSR_TYPE_CD = '02'
</when>
<when test='reqBuyType == "MM"'>
<if test="rprsPrdtId != null and rprsPrdtId != ''">
AND T1.INSR_PROD_CD IN (SELECT INSR_PROD_CD FROM MSP_INTM_INSR_REL@DL_MSP WHERE RPRS_PRDT_ID = #{rprsPrdtId} AND APPL_YN = 'Y')
</if>
</when>
</choose>
</if>
ORDER BY T1.CMPN_LMT_AMT DESC
```


#### API_0016 — 고객CI정보에 대한 개통  정보 추출 [다회선 제한 기능]

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `getLimitForm` |
| 연동명(한글) | 고객CI정보에 대한 개통  정보 추출 [다회선 제한 기능] |
| 엔드포인트 | `/appform/limitForm` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | AppformMapper.xml |
| SQL ID | `getLimitForm` |
| parameterType | `com.ktmmobile.mcp.appform.dto.AppformReqDto` |
| resultType | `com.ktmmobile.mcp.appform.dto.AppformReqDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_JUO_SUB_INFO, MSP_REQUEST_DTL |
| 담당자 | 강채신 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `selfCstmrCi` | 본인인증한  CI 정보 | `String` | 본인인증한  CI 정보 | SELF_CSTMR_CI | MCP_REQUEST |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `contractNum` | 서비스계약번호 | `String` | 서비스계약번호 | CONTRACT_NUM | MCP_REQUEST |
| `socCode` | 요금제코드 | `String` | 요금제코드 | FST_RATE_CD AS SOC_CODE | MCP_REQUEST |
| `selfCstmrCi` | 본인인증한  CI 정보 | `String` | 본인인증한  CI 정보 | SELF_CSTMR_CI | MCP_REQUEST |

**SQL**

```sql
SELECT
CONTRACT_NUM
, SOC_CODE
, SELF_CSTMR_CI
FROM (
SELECT
T1.CONTRACT_NUM
, FST_RATE_CD AS SOC_CODE
, SELF_CSTMR_CI
FROM
MCP_REQUEST T1
, MCP_REQUEST_CSTMR  T2
, MSP_JUO_SUB_INFO@DL_MSP T3
, MSP_REQUEST_DTL@DL_MSP T4
WHERE T1.REQUEST_KEY = T2.REQUEST_KEY
AND T1.CONTRACT_NUM = T3.CONTRACT_NUM
AND T1.CONTRACT_NUM = T4.CONTRACT_NUM
AND T1.ON_OFF_TYPE  IN ('5','7','9')                     -- 셀프개통 5 온라인(셀프개통)    7 모바일(셀프개통) 9 오프라인(셀프개통)
AND T1.OPER_TYPE = 'NAC3'                                -- 신규 서직지
AND SUB_STATUS <![CDATA[<> ]]> 'C'                       -- 활성 , 정지??? , 취소 제외
AND LST_COM_ACTV_DATE > TO_CHAR(SYSDATE-90, 'YYYYMMDD')  -- 개통일 기준 D+90  이내 (셀프개통 때문에 등록일 , 개통일 동일)  ???
AND SELF_CSTMR_CI = #{selfCstmrCi}
ORDER BY T1.SYS_RDATE DESC                               -- 최근
) WHERE  ROWNUM =1
```


#### API_0149 — 동일명의 회선 90일 이내에에 개통/개통취소 이력이 10회

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `checkLimitOpenFormCount` |
| 연동명(한글) | 동일명의 회선 90일 이내에에 개통/개통취소 이력이 10회 |
| 엔드포인트 | `/appform/checkLimitOpenFormCount` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | Appform_Query.xml |
| SQL ID | `checkLimitOpenFormCount` |
| parameterType | `com.ktmmobile.mcp.appform.dto.AppformReqDto` |
| resultType | `java.lang.Integer` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_JUO_SUB_INFO@DL_MSP, MSP_JUO_CUS_INFO@DL_MSP |
| 담당자 | 장성환 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `cstmrNativeRrn` | 주민번호 | `string` | 주민번호 | CUSTOMER_SSN | MSP_JUO_SUB_INFO
MSP_JUO_CUS_INFO |
| `cstmrForeignerRrn` | 외국인번호 | `string` | 외국인번호 | CUSTOMER_SSN | MSP_JUO_SUB_INFO
MSP_JUO_CUS_INFO |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `count` | 회수 | `Integer` | 회수 | COUNT | MSP_JUO_SUB_INFO
MSP_JUO_CUS_INFO |

**SQL**

```sql
SELECT /*Appform_Query.checkLimitOpenFormCount 동일명의 회선 90일 이내에에 개통/개통취소 이력이 10회 */
COUNT(A.CONTRACT_NUM)
FROM MSP_JUO_SUB_INFO@DL_MSP A,MSP_JUO_CUS_INFO@DL_MSP B
WHERE 1=1
AND A.CUSTOMER_ID = B.CUSTOMER_ID
AND     A.LST_COM_ACTV_DATE > SYSDATE - 90
AND     B.CUSTOMER_TYPE = 'I'                  -- I 개인 B 법인
AND     A.PPPO = 'PO'                          -- 후불
<choose>
<when test="cstmrType == 'NA'" >
AND B.CUSTOMER_SSN = #{cstmrNativeRrn} --주민번호
</when>
<when test="cstmrType == 'NM'" >
AND B.CUSTOMER_SSN = #{cstmrNativeRrn} --주민번호
</when>
<when test="cstmrType == 'FN'" >
AND B.CUSTOMER_SSN = #{cstmrForeignerRrn} --외국인번호
</when>
<otherwise>
AND 1 = 0
</otherwise>
</choose>
```


---

### USIM 도메인 (11개)

#### API_0114 — USIM 판매정책 리스트 정보 조회 only msp db링크를 통한 조회

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `listUsimMspPlcyDto` |
| 연동명(한글) | USIM 판매정책 리스트 정보 조회 only msp db링크를 통한 조회 |
| 엔드포인트 | `/usim/listUsimMspPlcyDto` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | UsimMapper.xml |
| SQL ID | `listUsimMspPlcyDto` |
| parameterType | `` |
| resultType | `com.ktmmobile.mcp.usim.dto.UsimMspPlcyDto` |
| 사용 여부 | ✅ 사용 |
| SQL 유형 | SQL ID 변경 |
| 대상 테이블 | MSP_SALE_PLCY_MST |
| 담당자 | 강문재 |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `salePlcyCd` | 판매정책코드 | `String` | 판매정책코드 | SALE_PLCY_CD | MSP_SALE_PLCY_MST |
| `salePlcyNm` | 판매정책명 | `String` | 판매정책명 | SALE_PLCY_NM | MSP_SALE_PLCY_MST |

**SQL**

```sql
SELECT
SALE_PLCY_CD	--판매정책코드
,SALE_PLCY_NM	--판매정책명
FROM   MSP_SALE_PLCY_MST@DL_MSP D
WHERE
TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS') BETWEEN
SALE_STRT_DTTM AND SALE_END_DTTM
AND D.PLCY_TYPE_CD = 'D'		  -- 온라인:O , 오프라인:M , 직영온라인 : D
AND D.PLCY_SCTN_CD = '02'		  -- 01:단말,02:유심
AND D.CNFM_YN = 'Y'				  --결제 YN (필수)
```


#### API_0115 — USIM 판매정책코드를 이용한 USIM 상품 리스트 정보 조회 only msp db링크를 통한 조회

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `listUsimMspDto` |
| 연동명(한글) | USIM 판매정책코드를 이용한 USIM 상품 리스트 정보 조회 only msp db링크를 통한 조회 |
| 엔드포인트 | `/usim/listUsimMspDto` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | UsimMapper.xml |
| SQL ID | `listUsimMspDto` |
| parameterType | `com.ktmmobile.mcp.usim.dto.UsimMspDto` |
| resultType | `com.ktmmobile.mcp.usim.dto.UsimMspDto` |
| 사용 여부 | ✅ 사용 |
| SQL 유형 | SQL ID 변경 |
| 대상 테이블 | MSP_RATE_MST, MSP_SALE_RATE_MST, CMN_GRP_CD_MST |
| 담당자 | 강문재 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `salePlcyCd` | 판매정책코드 | `String` | 판매정책코드 | SALE_PLCY_CD | MSP_SALE_RATE_MST |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `salePlcyCd` | 판매정책코드 | `String` | 판매정책코드 | SALE_PLCY_CD | MSP_SALE_RATE_MST |
| `rateCd` | 상품코드 | `String` | 상품코드 | RATE_CD | MSP_RATE_MST |
| `rateNm` | 상품명 | `String` | 상품명 | RATE_NM | MSP_RATE_MST |
| `payClCd` | 선불 후불구분 | `String` | 선불 후불구분 | PAY_CL_CD | MSP_RATE_MST |
| `dataType` | 데이터 타입 | `String` | 데이터 타입 | DATA_TYPE | MSP_RATE_MST |

**SQL**

```sql
SELECT
SA.SALE_PLCY_CD --정책코드
,RA.RATE_CD    --상품코드
,RA.RATE_NM   --상품명
,RA.PAY_CL_CD    -- 선불 후불구분 PO 후불 PP선불
,RA.DATA_TYPE	 -- 데이터 타입
FROM MSP_RATE_MST@DL_MSP RA,
MSP_SALE_RATE_MST@DL_MSP SA,
CMN_GRP_CD_MST@DL_MSP GR
WHERE
APPL_END_DT = '99991231'
AND RA.PAY_CL_CD = GR.CD_VAL
AND GR.GRP_ID='CMN0032'	--선불후불 구분 코트값의 그룹코드
AND SA.SALE_PLCY_CD = #{salePlcyCd}
AND RA.RATE_CD = SA.RATE_CD
```


#### API_0116 — 게시판 글 수 받아오기

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `getUsimBasTotalCount` |
| 연동명(한글) | 게시판 글 수 받아오기 |
| 엔드포인트 | `/usim/usimBasTotalCount` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | UsimMapper.xml |
| SQL ID | `getUsimBasTotalCount` |
| parameterType | `com.ktmmobile.mcp.phone.dto.CommonSearchDto` |
| resultType | `int` |
| 사용 여부 | ✅ 사용 |
| SQL 유형 | SQL ID 변경 |
| 대상 테이블 | MSP_SALE_PLCY_MST |
| 담당자 | 강문재 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `showYn` | 노출여부 | `String` | 노출여부 | VIEW_YN | NMCP_USIM_BAS |
| `searchValue` | 검색값 | `String` | 검색값 | PRDT_NM | NMCP_USIM_BAS |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `count` | 게시판 글 총 개수 | `int` | 게시판 글 총 개수 | COUNT | NMCP_USIM_BAS |

**SQL**

```sql
SELECT
COUNT(A.USIM_SEQ)
FROM
NMCP_USIM_BAS A,
MSP_SALE_PLCY_MST@DL_MSP B
WHERE
1=1
AND A.SALE_PLCY_CD = B.SALE_PLCY_CD(+)
<if test="showYn != null and showYn != ''">
AND A.VIEW_YN = #{showYn}
</if>
<if test='searchCategoryId == "rateNm"'>
AND A.PRDT_NM LIKE '%' || #{searchValue} || '%'
</if>
<if test='searchCategoryId == "prdtSctnCd"'>
AND B.PRDT_SCTN_CD LIKE '%' || #{searchValue} || '%'
</if>
```


#### API_0117 — 글 리스트 조회

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `selectUsimBasList` |
| 연동명(한글) | 글 리스트 조회 |
| 엔드포인트 | `/usim/usimBasList` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | UsimMapper.xml |
| SQL ID | `selectUsimBasList` |
| parameterType | `com.ktmmobile.mcp.phone.dto.CommonSearchDto` |
| resultType | `com.ktmmobile.mcp.usim.dto.UsimBasDto` |
| 사용 여부 | ✅ 사용 |
| SQL 유형 | 파라미터를 DTO로 넘김 |
| 대상 테이블 | MSP_SALE_PLCY_MST |
| 담당자 | 강문재 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `showYn` | 노출여부 | `String` | 노출여부 | VIEW_YN | NMCP_USIM_BAS |
| `searchValue` | 검색값 | `String` | 검색값 | PRDT_NM | NMCP_USIM_BAS |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `rowNum` | 글번호 | `String` | 글번호 | ROW_NUM | NMCP_USIM_BAS |
| `prdtSctnCd` | 상품종류 | `String` | 상품종류 | PRDT_SCTN_CD | MSP_SALE_PLCY_MST |
| `usimSeq` | 유심아이디 | `int` | 유심아이디 | USIM_SEQ | NMCP_USIM_BAS |
| `salePlcyCd` | 정책코드 | `String` | 정책코드 | SALE_PLCY_NM | NMCP_USIM_BAS |
| `rateNm` | 상품명 | `String` | 상품명 | RATE_NM | NMCP_USIM_BAS |
| `viewYn` | 노출여부 | `String` | 노출여부 | VIEW_YN | NMCP_USIM_BAS |
| `prdtNm` | 상품명 | `String` | 상품명 | PRDT_NM | NMCP_USIM_BAS |
| `payClCd` | 선불 후불구분 | `String` | 선불 후불구분 | PAY_CL_CD | NMCP_USIM_BAS |
| `prdtId` | 단말ID | `String` | 단말ID | PRDT_ID | NMCP_USIM_BAS |

**SQL**

```sql
SELECT
ROW_NUMBER() OVER (ORDER BY USIM_SEQ ASC) AS ROW_NUM,
A.USIM_SEQ,
A.SALE_PLCY_NM,
A.RATE_NM,
B.PRDT_SCTN_CD,
A.VIEW_YN,
A.PRDT_NM,
A.PAY_CL_CD,
A.PRDT_ID
FROM
NMCP_USIM_BAS A,
MSP_SALE_PLCY_MST@DL_MSP B
WHERE
1=1
AND A.SALE_PLCY_CD = B.SALE_PLCY_CD(+)
<if test="showYn != null and showYn != ''">
AND A.VIEW_YN = #{showYn}
</if>
<if test='searchCategoryId == "rateNm"'>
AND A.PRDT_NM LIKE '%' || #{searchValue} || '%'
</if>
<if test='searchCategoryId == "prdtSctnCd"'>
AND B.PRDT_SCTN_CD LIKE '%' || #{searchValue} || '%'
</if>
ORDER BY USIM_SEQ DESC
```


#### API_0119 — 정책에 해당하는 상품의 상세정보 조회

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `selectUsimPrdtList` |
| 연동명(한글) | 정책에 해당하는 상품의 상세정보 조회 |
| 엔드포인트 | `/usim/usimPrdtList` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | UsimMapper.xml |
| SQL ID | `selectUsimPrdtList` |
| parameterType | `com.ktmmobile.mcp.usim.dto.UsimBasDto` |
| resultType | `com.ktmmobile.mcp.usim.dto.UsimBasDto` |
| 사용 여부 | ✅ 사용 |
| SQL 유형 | 파라미터를 DTO로 넘김 |
| 대상 테이블 | MSP_SALE_PRDT_MST, CMN_INTM_MDL], MSP_SALE_PLCY_MST, MSP_RATE_MST, MSP_SALE_RATE_MST, MSP_SALE_ORGN_MST |
| 담당자 | 강문재 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `payClCd` | 선불 후불구분 | `String` | 선불 후불구분 | PAY_CL_CD | MSP_SALE_PRDT_MST |
| `prdtSctnCd` | 상품종류 | `String` | 상품종류 | PRDT_SCTN_CD | MSP_SALE_PRDT_MST |
| `orgnId` | 유심아이디 | `String` | 유심아이디 | ORGN_ID | MSP_SALE_ORGN_MST |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `prdtNm` | 상품명 | `String` | 상품명 | PRDT_NM | CMN_INTM_MDL |
| `prdtId` | 단말ID | `String` | 단말ID | PRDT_ID | MSP_SALE_PRDT_MST |
| `prdtSctnCd` | 상품종류 | `String` | 상품종류 | PRDT_SCTN_CD | MSP_SALE_PLCY_MST |
| `payClCd` | 선불 후불구분 | `String` | 선불 후불구분 | PAY_CL_CD | MSP_RATE_MST |

**SQL**

```sql
SELECT
DISTINCT
B.PRDT_NM,
A.PRDT_ID,
C.PRDT_SCTN_CD,
RA.PAY_CL_CD
FROM	MSP_SALE_PRDT_MST@DL_MSP A -- 정책
, CMN_INTM_MDL@DL_MSP B
, MSP_SALE_PLCY_MST@DL_MSP C
, MSP_RATE_MST@DL_MSP RA
, MSP_SALE_RATE_MST@DL_MSP SA
, MSP_SALE_ORGN_MST@DL_MSP ORG
WHERE 	C.PLCY_SCTN_CD = 02
AND A.PRDT_ID = B.PRDT_ID
AND A.SALE_PLCY_CD = C.SALE_PLCY_CD
AND A.SALE_PLCY_CD = SA.SALE_PLCY_CD
AND RA.RATE_CD = SA.RATE_CD
AND C.SALE_PLCY_CD = ORG.SALE_PLCY_CD
AND TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS') BETWEEN C.SALE_STRT_DTTM AND C.SALE_END_DTTM
AND PAY_CL_CD = #{payClCd}
AND PRDT_SCTN_CD = #{prdtSctnCd}
AND ORG.ORGN_ID = #{orgnId}
```


#### API_0120 — 상품정보로 유효한 정책정보 조회

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `selectUsimsalePlcyCdToPrdtList` |
| 연동명(한글) | 상품정보로 유효한 정책정보 조회 |
| 엔드포인트 | `/usim/usimSalePlcyCdToPrdtList` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | UsimMapper.xml |
| SQL ID | `selectUsimsalePlcyCdToPrdtList` |
| parameterType | `com.ktmmobile.mcp.usim.dto.UsimBasDto` |
| resultType | `com.ktmmobile.mcp.usim.dto.UsimBasDto` |
| 사용 여부 | ✅ 사용 |
| SQL 유형 | SQL ID 변경
파라미터를 DTO로 넘김 |
| 대상 테이블 | MSP_SALE_PRDT_MST, CMN_INTM_MDL, MSP_SALE_PLCY_MST, MSP_RATE_MST, MSP_SALE_RATE_MST, MSP_SALE_ORGN_MST, MSP_SALE_AGRM_MST |
| 담당자 | 강문재 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `prdtId` | 단말ID | `String` | 단말ID | PRDT_ID | MSP_SALE_PRDT_MST |
| `payClCd` | 선불 후불구분 | `String` | 선불 후불구분 | PAY_CL_CD | MSP_RATE_MST |
| `prdtSctnCd` | 상품종류 | `String` | 상품종류 | PRDT_SCTN_CD | MSP_SALE_PLCY_MST |
| `orgnId` | 조직코드 | `String` | 조직코드 | ORGN_ID | MSP_SALE_ORGN_MST |
| `agrmTrm` | 약정기간 | `String` | 약정기간 | AGRM_TRM | MSP_SALE_AGRM_MST |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `salePlcyCd` | 정책코드 | `String` | 정책코드 | SALE_PLCY_CD | MSP_SALE_PLCY_MST |
| `salePlcyNm` | 정책명 | `String` | 정책명 | SALE_PLCY_NM | MSP_SALE_PLCY_MST |

**SQL**

```sql
SELECT/*상품정보로 유효한 정책정보를 들고온다.*/
DISTINCT
C.SALE_PLCY_CD,
C.SALE_PLCY_NM
FROM	MSP_SALE_PRDT_MST@DL_MSP A -- 정책
, CMN_INTM_MDL@DL_MSP B
, MSP_SALE_PLCY_MST@DL_MSP C
, MSP_RATE_MST@DL_MSP RA
, MSP_SALE_RATE_MST@DL_MSP SA
, MSP_SALE_ORGN_MST@DL_MSP ORG
, MSP_SALE_AGRM_MST@DL_MSP AG
WHERE 	C.PLCY_SCTN_CD = 02
AND A.PRDT_ID = B.PRDT_ID
AND SA.SALE_PLCY_CD = AG.SALE_PLCY_CD
AND A.SALE_PLCY_CD = C.SALE_PLCY_CD
AND A.SALE_PLCY_CD = SA.SALE_PLCY_CD
AND RA.RATE_CD = SA.RATE_CD
AND C.SALE_PLCY_CD = ORG.SALE_PLCY_CD
AND TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS') BETWEEN C.SALE_STRT_DTTM AND C.SALE_END_DTTM
and A.PRDT_ID = #{prdtId}
AND RA.PAY_CL_CD = #{payClCd}
AND C.PRDT_SCTN_CD = #{prdtSctnCd}
AND ORG.ORGN_ID = #{orgnId}
<if test="agrmTrm !=null and agrmTrm != ''">
AND AG.AGRM_TRM= #{agrmTrm} 								-- 약정기간 무약정0은 기본적으로 조회한다.
</if>
ORDER BY SALE_PLCY_CD
```


#### API_0121 — selectUsimNewRateList

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `selectUsimNewRateList` |
| 엔드포인트 | `/usim/usimNewRateList` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | UsimMapper.xml |
| SQL ID | `selectUsimNewRateList` |
| parameterType | `com.ktmmobile.mcp.usim.dto.UsimBasDto` |
| resultType | `com.ktmmobile.mcp.usim.dto.UsimBasDto` |
| 사용 여부 | ✅ 사용 |
| SQL 유형 | 파라미터를 DTO로 넘김 |
| 대상 테이블 | MSP_RATE_MST, MSP_SALE_RATE_MST, CMN_GRP_CD_MST, MSP_SALE_AGRM_MST |
| 담당자 | 강문재 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `salePlcyCdList.item` | 판매정책코드 | `List<String>` | 판매정책코드 | SALE_PLCY_CD | MSP_SALE_RATE_MST |
| `payClCd` | 선불 후불구분 | `String` | 선불 후불구분 | PAY_CL_CD | MSP_RATE_MST |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `agrmTrm` | 약정기간 | `String` | 약정기간 | AGRM_TRM | MSP_SALE_AGRM_MST |
| `salePlcyCd` | 정책코드 | `String` | 정책코드 | SALE_PLCY_CD | MSP_SALE_RATE_MST |
| `rateCd` | 상품코드 | `String` | 상품코드 | RATE_CD | MSP_RATE_MST |
| `rateNm` | 상품명 | `String` | 상품명 | RATE_NM | MSP_RATE_MST |
| `payClCd` | 선불 후불구분 | `String` | 선불 후불구분 | PAY_CL_CD | MSP_RATE_MST |
| `dataType` | 데이터 타입 | `String` | 데이터 타입 | DATA_TYPE | MSP_RATE_MST |

**SQL**

```sql
SELECT
DISTINCT
AG.AGRM_TRM
,SA.SALE_PLCY_CD --정책코드
,RA.RATE_CD    --상품코드
,RA.RATE_NM   --상품명
,RA.PAY_CL_CD    -- 선불 후불구분 PO 후불 PP선불
,RA.DATA_TYPE	 -- 데이터 타입
FROM MSP_RATE_MST@DL_MSP RA,
MSP_SALE_RATE_MST@DL_MSP SA,
CMN_GRP_CD_MST@DL_MSP GR,
MSP_SALE_AGRM_MST@DL_MSP AG
WHERE
APPL_END_DT = '99991231'
AND RA.PAY_CL_CD = GR.CD_VAL
AND SA.SALE_PLCY_CD IN
<foreach item="item" collection="salePlcyCdList" open="(" separator="," close=")">
#{item}
</foreach>
AND RA.PAY_CL_CD = #{payClCd}
--AND RA.RATE_TYPE = '02'
AND RA.RATE_CD = SA.RATE_CD
AND SA.SALE_PLCY_CD = AG.SALE_PLCY_CD
```


#### API_0123 — 상품정보로 유효한 정책정보를 조회(약정포함)

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `selectUsimSalePlcyCdBannerList` |
| 연동명(한글) | 상품정보로 유효한 정책정보를 조회(약정포함) |
| 엔드포인트 | `/usim/usimSalePlcyCdBannerList` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | UsimMapper.xml |
| SQL ID | `selectUsimSalePlcyCdBannerList` |
| parameterType | `com.ktmmobile.mcp.mspservice.dto.MspSalePlcyMstDto` |
| resultType | `com.ktmmobile.mcp.mspservice.dto.MspSalePlcyMstDto` |
| 사용 여부 | ✅ 사용 |
| SQL 유형 | 파라미터를 DTO로 넘김 |
| 대상 테이블 | MSP_SALE_PRDT_MST, CMN_INTM_MDL, MSP_SALE_PLCY_MST, MSP_RATE_MST, MSP_SALE_RATE_MST, MSP_SALE_ORGN_MST, MSP_SALE_AGRM_MST |
| 담당자 | 강문재 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `payClCd` | 선후불구분 | `String` | 선후불구분 | PAY_CL_CD | MSP_RATE_MST |
| `prdtSctnCd` | 제품구분코드 | `String` | (02:3G , 03:LTE) | PRDT_SCTN_CD | MSP_SALE_PLCY_MST |
| `orgnId` | 조직코드 | `String` | 조직코드 | ORGN_ID | MSP_SALE_ORGN_MST |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `salePlcyCd` | 판매정책코드 | `String` | 판매정책코드 | SALE_PLCY_CD | MSP_SALE_PLCY_MST |
| `salePlcyNm` | 판매정책코드명 | `String` | 판매정책코드명 | SALE_PLCY_NM | MSP_SALE_PLCY_MST |
| `agrmTrm` | 약정기간 | `String` | 약정기간 | AGRM_TRM | MSP_SALE_AGRM_MST |
| `payClCd` | 선후불구분 | `String` | 선후불구분 | PAY_CL_CD | MSP_RATE_MST |

**SQL**

```sql
SELECT
DISTINCT
C.SALE_PLCY_CD,
C.SALE_PLCY_NM,
AG.AGRM_TRM,
RA.PAY_CL_CD
FROM	MSP_SALE_PRDT_MST@DL_MSP A -- 정책
, CMN_INTM_MDL@DL_MSP B
, MSP_SALE_PLCY_MST@DL_MSP C
, MSP_RATE_MST@DL_MSP RA
, MSP_SALE_RATE_MST@DL_MSP SA
, MSP_SALE_ORGN_MST@DL_MSP ORG
, MSP_SALE_AGRM_MST@DL_MSP AG
WHERE 	C.PLCY_SCTN_CD = 02
AND A.PRDT_ID = B.PRDT_ID
AND SA.SALE_PLCY_CD = AG.SALE_PLCY_CD
AND A.SALE_PLCY_CD = C.SALE_PLCY_CD
AND A.SALE_PLCY_CD = SA.SALE_PLCY_CD
AND RA.RATE_CD = SA.RATE_CD
AND C.SALE_PLCY_CD = ORG.SALE_PLCY_CD
AND TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS') BETWEEN C.SALE_STRT_DTTM AND C.SALE_END_DTTM
AND RA.PAY_CL_CD = #{payClCd}
AND C.PRDT_SCTN_CD = #{prdtSctnCd}
AND ORG.ORGN_ID = #{orgnId}
AND ORG.ORGN_ID = '1100011741'
ORDER BY AGRM_TRM ASC
```


#### API_0124 — 정책정보로 약정할인 조회

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `listMspSaleAgrmMstMoreTwoRows` |
| 연동명(한글) | 정책정보로 약정할인 조회 |
| 엔드포인트 | `/usim/mspSaleAgrmMstMoreTwoRows` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | UsimMapper.xml |
| SQL ID | `listMspSaleAgrmMstMoreTwoRows` |
| parameterType | `com.ktmmobile.mcp.usim.dto.UsimBasDto` |
| resultType | `com.ktmmobile.mcp.usim.dto.UsimBasDto` |
| 사용 여부 | ✅ 사용 |
| SQL 유형 | SQL ID 변경
파라미터를 DTO로 넘김 |
| 대상 테이블 | MSP_SALE_AGRM_MST |
| 담당자 | 강문재 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `salePlcyCdList` | 판매정책코드 | `List<String>` | 판매정책코드 | SALE_PLCY_CD | MSP_SALE_AGRM_MST |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `agrmTrm` | 약정코드 | `String` | 약정코드 | AGRM_TRM | MSP_SALE_AGRM_MST |

**SQL**

```sql
SELECT
RA.AGRM_TRM
FROM
MSP_SALE_AGRM_MST@DL_MSP RA
WHERE
RA.SALE_PLCY_CD IN
<foreach item="item" collection="salePlcyCdList" open="(" separator="," close=")">
#{item}
</foreach>
GROUP BY AGRM_TRM
ORDER BY AGRM_TRM ASC
```


#### API_0147 — selectUsimBasDto

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `selectUsimBasDto` |
| 엔드포인트 | `/usim/selectUsimBasDto` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | UsimMapper.xml |
| SQL ID | `selectUsimBasDto` |
| parameterType | `com.ktmmobile.mcp.usim.dto.UsimBasDto` |
| resultType | `com.ktmmobile.mcp.usim.dto.UsimBasDto
com.ktmmobile.mcp.usim.dto.UsimMspPlcyDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | NMCP_USIM_BAS, MSP_SALE_PLCY_MST |
| 담당자 | 박정웅 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `usimSeq` |  | `string` |  | USIM_SEQ |  |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `usimSeq` |  | `string` |  | USIM_SEQ | NMCP_USIM_BAS |
| `usimDesc` |  | `string` |  | USIM_DESC | NMCP_USIM_BAS |
| `salePlcyCd` |  | `string` |  | SALE_PLCY_CD | NMCP_USIM_BAS |
| `salePlcyCd` |  | `string` |  | SALE_PLCY_CD | NMCP_USIM_BAS |
| `salePlcyNm` |  | `string` |  | SALE_PLCY_NM | NMCP_USIM_BAS |
| `rateCd` |  | `string` |  | RATE_CD | NMCP_USIM_BAS |
| `rateNm` |  | `string` |  | RATE_NM | NMCP_USIM_BAS |
| `viewYn` |  | `string` |  | VIEW_YN | NMCP_USIM_BAS |
| `imgPath` |  | `string` |  | IMG_PATH | NMCP_USIM_BAS |
| `payClCd` |  | `string` |  | PAY_CL_CD | NMCP_USIM_BAS |
| `dataType` |  | `string` |  | DATA_TYPE | NMCP_USIM_BAS |
| `cretId` |  | `string` |  | CRET_ID | NMCP_USIM_BAS |
| `amdId` |  | `string` |  | AMD_ID | NMCP_USIM_BAS |
| `cretDt` |  | `string` |  | CRET_DT | NMCP_USIM_BAS |
| `amdDt` |  | `string` |  | AMD_DT | NMCP_USIM_BAS |
| `bestRate` |  | `string` |  | BEST_RATE | NMCP_USIM_BAS |
| `prdtNm` |  | `string` |  | PRDT_NM | NMCP_USIM_BAS |
| `prdtId` |  | `string` |  | PRDT_ID | NMCP_USIM_BAS |
| `agrmTrm` |  | `string` |  | AGRM_TRM | NMCP_USIM_BAS |
| `salePlcyCd` | 판매정책코드 | `string` | 판매정책코드 | SALE_PLCY_CD | MSP_SALE_PLCY_MST |
| `salePlcyNm` | 판매정책명 | `string` | 판매정책명 | SALE_PLCY_NM | MSP_SALE_PLCY_MST |

**SQL**

```sql
SELECT
USIM_SEQ
,USIM_DESC
,SALE_PLCY_CD
,SALE_PLCY_CD AS salePlcyCd
,SALE_PLCY_NM
,RATE_CD
,RATE_NM
,VIEW_YN
,IMG_PATH
,PAY_CL_CD      -- 선불 후불구분 PO 후불 PP선불
,DATA_TYPE     -- 데이터 타입
,CRET_ID
,AMD_ID
,CRET_DT
,AMD_DT
,BEST_RATE    --추천 요금제
,PRDT_NM
,PRDT_ID
,AGRM_TRM
FROM NMCP_USIM_BAS
WHERE USIM_SEQ = #{usimSeq}
SELECT
SALE_PLCY_CD --판매정책코드
,SALE_PLCY_NM --판매정책명
FROM   MSP_SALE_PLCY_MST@DL_MSP D
WHERE
TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS') BETWEEN
SALE_STRT_DTTM AND SALE_END_DTTM
AND D.PLCY_TYPE_CD = 'D'    -- 온라인:O , 오프라인:M , 직영온라인 : D
AND D.PLCY_SCTN_CD = '02'    -- 01:단말,02:유심
AND D.CNFM_YN = 'Y'      --결제 YN (필수)
```


#### API_0148 — insertMcpUsimProdSort

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `insertMcpUsimProdSort` |
| 엔드포인트 | `/usim/insertMcpUsimProdSort` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | UsimMapper.xml |
| SQL ID | `insertMcpUsimProdSort` |
| parameterType | `List<UsimBasDto>` |
| resultType | `java.lang.Integer` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MCP_USIM_PROD_SORT, MSP_RATE_MST |
| 담당자 | 박정웅 |
| 비고 | INSERT |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `usimSeq` |  | `string` |  | USIM_SEQ | MCP_USIM_PROD_SORT |
| `salePlcyCd` |  | `string` |  | SALE_PLCY_CD | MCP_USIM_PROD_SORT |
| `paySort` |  | `string` |  | PAY_SORT | MCP_USIM_PROD_SORT |
| `rateCd` |  | `string` |  | RATE_CD | MCP_USIM_PROD_SORT |
| `rateNm` |  | `string` |  | RATE_NM | MCP_USIM_PROD_SORT |
| `prdtNm` |  | `string` |  | PRDT_NM | MCP_USIM_PROD_SORT |
| `prdtId` |  | `string` |  | PRDT_ID | MCP_USIM_PROD_SORT |
| `cretId` |  | `string` |  | CRET_ID | MCP_USIM_PROD_SORT |
| `amdId` |  | `string` |  | AMD_ID | MCP_USIM_PROD_SORT |
| `cretDt` |  | `string` |  | CRET_DT | MCP_USIM_PROD_SORT |
| `amdDt` |  | `string` |  | AMD_DT | MCP_USIM_PROD_SORT |

**SQL**

```sql
<foreach collection="usimBasDtoList" item="item" index="index" separator=" " open="INSERT ALL" close="SELECT * FROM DUAL">
INTO MCP_USIM_PROD_SORT
(
USIM_SEQ
,SALE_PLCY_CD
,PAY_SORT
,RATE_CD
,RATE_NM
,PRDT_NM
,PRDT_ID
,CRET_ID
,AMD_ID
,CRET_DT
,AMD_DT
) VALUES
(
#{item.usimSeq}
, #{item.salePlcyCd}
, #{item.paySort}
, #{item.rateCd}
, (SELECT NVL(RATE_NM,'-') AS RATE_NM FROM MSP_RATE_MST@DL_MSP WHERE RATE_CD = #{item.rateCd})
, #{item.prdtNm}
, #{item.prdtId}
, #{item.cretId}
, #{item.amdId}
, SYSDATE
, SYSDATE
)
</foreach>
```


---

### APPFORM 도메인 (11개)

#### API_0134 — getPrmtChk

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `getPrmtChk` |
| 엔드포인트 | `/appForm/getPrmtChk` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | GiftMapper.xml |
| SQL ID | `getPrmtChk` |
| parameterType | `com.ktmmobile.mcp.etc.dto.GiftPromotionDto` |
| resultType | `com.ktmmobile.mcp.etc.dto.GiftPromotionDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_GIFT_PRMT |
| 담당자 | 장성환 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `prmtId` |  | `string` |  | PRMT_ID | MSP_GIFT_PRMT |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `prmtId` |  | `string` |  | PRMT_ID | MSP_GIFT_PRMT |
| `prmtNm` |  | `string` |  | PRMT_NM | MSP_GIFT_PRMT |
| `nacYn` |  | `string` |  | NAC_YN | MSP_GIFT_PRMT |
| `mnpYn` |  | `string` |  | MNP_YN | MSP_GIFT_PRMT |
| `reqBuyType` |  | `string` |  | REQ_BUY_TYPE | MSP_GIFT_PRMT |
| `amountLimit` |  | `string` |  | AMOUNT_LIMIT | MSP_GIFT_PRMT |

**SQL**

```sql
SELECT
PRMT_ID
, PRMT_NM
, NAC_YN
, MNP_YN
, REQ_BUY_TYPE
, NVL(AMOUNT_LIMIT,0) AS AMOUNT_LIMIT
, PRMT_TEXT
FROM MSP_GIFT_PRMT@DL_MSP
WHERE 1=1
AND USG_YN = 'Y'
AND PRMT_ID = #{prmtId}
AND TO_DATE(TO_CHAR(SYSDATE,'YYYYMMDD'),'YYYYMMDD') BETWEEN TO_DATE(STRT_DT,'YYYYMMDD') AND (TO_DATE(END_DT,'YYYYMMDD')+8)
```


#### API_0135 — getGiftList

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `getGiftList` |
| 엔드포인트 | `/appForm/getGiftList` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | GiftMapper.xml |
| SQL ID | `getGiftList` |
| parameterType | `com.ktmmobile.mcp.etc.dto.GiftPromotionDto` |
| resultType | `com.ktmmobile.mcp.etc.dto.GiftPromotionDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_GIFT_PRMT_PRDT, MSP_GIFT_PRDT |
| 담당자 | 장성환 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `prmtId` |  | `string` |  | PRMT_ID | MSP_GIFT_PRMT_PRDT |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `prmtId` |  | `string` |  | PRMT_ID | MSP_GIFT_PRMT_PRDT |
| `prdtId` |  | `string` |  | PRDT_ID | MSP_GIFT_PRDT |
| `prdtNm` |  | `string` |  | PRDT_NM | MSP_GIFT_PRDT |
| `prdtDesc` |  | `string` |  | PRDT_DESC | MSP_GIFT_PRDT |
| `outUnitPric` |  | `string` |  | OUT_UNIT_PRIC | MSP_GIFT_PRDT |
| `imgFile` |  | `string` |  | IMG_FILE | MSP_GIFT_PRDT |
| `webUrl` |  | `string` |  | WEB_URL | MSP_GIFT_PRDT |

**SQL**

```sql
SELECT
PRMT.PRMT_ID
, PRDT.PRDT_ID
, PRDT.PRDT_NM
, PRDT.PRDT_DESC
, PRDT.OUT_UNIT_PRIC
, PRDT.IMG_FILE
, PRDT.WEB_URL
FROM MSP_GIFT_PRMT_PRDT@DL_MSP PRMT
INNER JOIN MSP_GIFT_PRDT@DL_MSP PRDT
ON PRMT.PRDT_ID = PRDT.PRDT_ID
WHERE 1=1
AND PRMT.PRMT_ID = #{prmtId}
AND PRDT.USE_YN = 'Y'
ORDER BY PRMT.SORT
```


#### API_0136 — getMspGiftPrmtCustomer

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `getMspGiftPrmtCustomer` |
| 엔드포인트 | `/appForm/getMspGiftPrmtCustomer` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | GiftMapper.xml |
| SQL ID | `getMspGiftPrmtCustomer` |
| parameterType | `com.ktmmobile.mcp.etc.dto.GiftPromotionDto` |
| resultType | `com.ktmmobile.mcp.etc.dto.GiftPromotionDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_GIFT_PRMT_CUSTOMER |
| 담당자 | 장성환 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `prmtId` |  | `string` |  | PRMT_ID | MSP_GIFT_PRMT_CUSTOMER |
| `subscriberNo` |  | `string` |  | SUBSCRIBER_NO | MSP_GIFT_PRMT_CUSTOMER |
| `subLinkName` |  | `string` |  | SUB_LINK_NAME | MSP_GIFT_PRMT_CUSTOMER |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `smsSendDate` |  | `string` |  | SMS_SEND_DATE | MSP_GIFT_PRMT_CUSTOMER |

**SQL**

```sql
SELECT
SMS_SEND_DATE
FROM MSP_GIFT_PRMT_CUSTOMER@DL_MSP
WHERE 1=1
AND SEND_YN = 'Y'
AND PRMT_ID = #{prmtId}
AND SUBSCRIBER_NO = #{phone}
AND SUB_LINK_NAME = #{name}
```


#### API_0137 — getMspJuoSubInfoData

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `getMspJuoSubInfoData` |
| 엔드포인트 | `/appForm/getMspJuoSubInfoData` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | GiftMapper.xml |
| SQL ID | `getMspJuoSubInfoData` |
| parameterType | `com.ktmmobile.mcp.etc.dto.GiftPromotionDto` |
| resultType | `com.ktmmobile.mcp.etc.dto.GiftPromotionDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_JUO_SUB_INFO |
| 담당자 | 장성환 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `subscriberNo` |  | `string` |  | SUBSCRIBER_NO | MSP_JUO_SUB_INFO |
| `subLinkName` |  | `string` |  | SUB_LINK_NAME | MSP_JUO_SUB_INFO |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `contractNum` |  | `string` |  | CONTRACT_NUM | MSP_JUO_SUB_INFO |
| `subscriberNo` |  | `string` |  | SUBSCRIBER_NO | MSP_JUO_SUB_INFO |
| `subLinkName` |  | `string` |  | SUB_LINK_NAME | MSP_JUO_SUB_INFO |
| `nowToActvDate` |  | `string` |  | NOW_TO_ACTV_DATE | MSP_JUO_SUB_INFO |

**SQL**

```sql
SELECT
CONTRACT_NUM
, SUBSCRIBER_NO
, SUB_LINK_NAME
, TO_DATE(TO_CHAR(SYSDATE,'YYYYMMDD'),'YYYYMMDD')-TO_DATE(LST_COM_ACTV_DATE,'YYYYMMDD') AS NOW_TO_ACTV_DATE
FROM MSP_JUO_SUB_INFO@DL_MSP
WHERE 1=1
AND SUBSCRIBER_NO = #{phone}
AND SUB_LINK_NAME = #{name}
AND PPPO = 'PO'
AND SUB_STATUS = 'A'
```


#### API_0138 — getChkMspGiftPrmt

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `getChkMspGiftPrmt` |
| 엔드포인트 | `/appForm/getChkMspGiftPrmt` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | GiftMapper.xml |
| SQL ID | `getChkMspGiftPrmt` |
| parameterType | `com.ktmmobile.mcp.etc.dto.GiftPromotionDto` |
| resultType | `com.ktmmobile.mcp.etc.dto.GiftPromotionDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_GIFT_PRMT_RESULT |
| 담당자 | 장성환 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `contractNum` |  | `string` |  | CONTRACT_NUM | MSP_GIFT_PRMT_RESULT |
| `prmtId` |  | `string` |  | PRMT_ID | MSP_GIFT_PRMT_RESULT |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `prmtId` |  | `string` |  | PRMT_ID | MSP_GIFT_PRMT_RESULT |
| `contractNum` |  | `string` |  | CONTRACT_NUM | MSP_GIFT_PRMT_RESULT |

**SQL**

```sql
SELECT
PRMT_ID
, CONTRACT_NUM
FROM MSP_GIFT_PRMT_RESULT@DL_MSP
WHERE 1=1
AND CONTRACT_NUM = #{contractNum}
AND PRMT_ID = #{prmtId}
```


#### API_0139 — updateMspGiftPrmtCustomer

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `updateMspGiftPrmtCustomer` |
| 엔드포인트 | `/appForm/updateMspGiftPrmtCustomer` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | GiftMapper.xml |
| SQL ID | `updateMspGiftPrmtCustomer` |
| parameterType | `com.ktmmobile.mcp.etc.dto.GiftPromotionDto` |
| resultType | `` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_GIFT_PRMT_CUSTOMER |
| 담당자 | 장성환 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `giftRegstDate` |  | `string` |  | GIFT_REGST_DATE | MSP_GIFT_PRMT_CUSTOMER |
| `mngNm` |  | `string` |  | MNG_NM | MSP_GIFT_PRMT_CUSTOMER |
| `telFn1` |  | `string` |  | TEL_FN1 | MSP_GIFT_PRMT_CUSTOMER |
| `telMn1` |  | `string` |  | TEL_MN1 | MSP_GIFT_PRMT_CUSTOMER |
| `telRn1` |  | `string` |  | TEL_RN1 | MSP_GIFT_PRMT_CUSTOMER |
| `post` |  | `string` |  | POST | MSP_GIFT_PRMT_CUSTOMER |
| `addr` |  | `string` |  | ADDR | MSP_GIFT_PRMT_CUSTOMER |
| `addrDtl` |  | `string` |  | ADDR_DTL | MSP_GIFT_PRMT_CUSTOMER |
| `prmtId` |  | `string` |  | PRMT_ID | MSP_GIFT_PRMT_CUSTOMER |
| `contractNum` |  | `string` |  | CONTRACT_NUM | MSP_GIFT_PRMT_CUSTOMER |
| `subscriberNo` |  | `string` |  | SUBSCRIBER_NO | MSP_GIFT_PRMT_CUSTOMER |

**SQL**

```sql
UPDATE MSP_GIFT_PRMT_CUSTOMER@DL_MSP SET
GIFT_REGST_DATE = TO_CHAR(SYSDATE,'YYYYMMDD')
, MNG_NM = #{name}
, TEL_FN1 = #{telFn1}
, TEL_MN1 = #{telMn1}
, TEL_RN1 = #{telRn1}
, POST = #{post}
, ADDR = #{addr}
, ADDR_DTL = #{addrDtl}
WHERE 1=1
AND PRMT_ID = #{prmtId}
AND CONTRACT_NUM = #{contractNum}
AND SUBSCRIBER_NO = #{phone}
```


#### API_0140 — insertMspGiftPrmtResult

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `insertMspGiftPrmtResult` |
| 엔드포인트 | `/appForm/insertMspGiftPrmtResult` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | GiftMapper.xml |
| SQL ID | `insertMspGiftPrmtResult` |
| parameterType | `com.ktmmobile.mcp.etc.dto.GiftPromotionDto` |
| resultType | `` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_GIFT_PRMT_RESULT |
| 담당자 | 장성환 |
| 비고 | INSERT |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `prmtId` |  | `string` |  | PRMT_ID | MSP_GIFT_PRMT_RESULT |
| `contractNum` |  | `string` |  | CONTRACT_NUM | MSP_GIFT_PRMT_RESULT |
| `prdtId` |  | `string` |  | PRDT_ID | MSP_GIFT_PRMT_RESULT |
| `quantity` |  | `string` |  | QUANTITY | MSP_GIFT_PRMT_RESULT |
| `regstDttm` |  | `string` |  | REGST_DTTM | MSP_GIFT_PRMT_RESULT |

**SQL**

```sql
INSERT INTO MSP_GIFT_PRMT_RESULT_HIST@DL_MSP
(
GRP_NUM
,PRMT_ID
,CONTRACT_NUM
,PRDT_ID
,QUANTITY
,RVISN_RSN
,EVNT_CD
,REGST_DTTM
)
VALUES
(
1
, #{prmtId}
, #{contractNum}
, #{prdtId}
, '1'
, 'PORTAL'
, 'I'
, SYSDATE
)
<selectKey resultType="int" keyProperty="inResult" order="AFTER">
SELECT 1 AS inResult FROM DUAL@DL_MSP
</selectKey>
```


#### API_0141 — getSaveGiftList

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `getSaveGiftList` |
| 엔드포인트 | `/appForm/getSaveGiftList` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | GiftMapper.xml |
| SQL ID | `getSaveGiftList` |
| parameterType | `com.ktmmobile.mcp.etc.dto.GiftPromotionDto` |
| resultType | `com.ktmmobile.mcp.etc.dto.GiftPromotionDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_GIFT_PRMT_RESULT, MSP_GIFT_PRDT |
| 담당자 | 장성환 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `contractNum` |  | `string` |  | CONTRACT_NUM | MSP_GIFT_PRMT_RESULT |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `prdtNm` |  | `string` |  | PRDT_NM | MSP_GIFT_PRDT |

**SQL**

```sql
SELECT
'- '||GIFT.PRDT_NM AS PRDT_NM
FROM MSP_GIFT_PRMT_RESULT@DL_MSP RESULT
INNER JOIN MSP_GIFT_PRDT@DL_MSP GIFT
ON RESULT.PRDT_ID = GIFT.PRDT_ID
WHERE 1=1
AND RESULT.CONTRACT_NUM = #{contractNum}
```


#### API_0150 — 사은품 조회

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `getPrmtBasList` |
| 연동명(한글) | 사은품 조회 |
| 엔드포인트 | `/appForm/getPrmtBasList` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | GiftMapper.xml |
| SQL ID | `getPrmtBasList` |
| parameterType | `com.ktmmobile.mcp.etc.dto.GiftPromotionBas` |
| resultType | `com.ktmmobile.mcp.etc.dto.GiftPromotionBas` |
| 사용 여부 | ✅ 사용 |
| 담당자 | 장성환 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `onOffType` | 신청유형 | `string` | 신청유형 | ON_OFF_TYPE | MSP_GIFT_PRMT_ONOFF |
| `rateCd` | 요금제코드 | `string` | 요금제코드 | RATE_CD | MSP_GIFT_PRMT_SOC |
| `reqBuyType` | 구매유형 단말 | `string` | 구매유형 단말 | REQ_BUY_TYPE | MSP_GIFT_PRMT |
| `orgnId` | 조직 | `string` | 조직 | ORGN_ID | ORG_ORGN_INFO_MST |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `prmtId` | 프로모션아이디 | `string` | 프로모션아이디 | PRMT_ID | MSP_GIFT_PRMT |
| `prmtText` | 프로모션명 | `string` | 프로모션명 | PRMT_TEXT | MSP_GIFT_PRMT |
| `prmtType` | 프로모션타입 | `string` | 프로모션타입 | PRMT_TYPE | MSP_GIFT_PRMT |
| `choiceLimit` | 선택개수 | `string` | 선택개수 | CHOICE_LIMIT | MSP_GIFT_PRMT |

**SQL**

```sql
SELECT /*com.ktmmobile.mcp.etc.mapper.GiftMapper.getPrmtBasList   */
DISTINCT A.PRMT_ID
, PRMT_TEXT AS PRMT_NM
, PRMT_TYPE
, NVL(CHOICE_LIMIT,0) AS LIMIT_CNT
FROM MSP_GIFT_PRMT@DL_MSP A
, MSP_GIFT_PRMT_ONOFF@DL_MSP B
, MSP_GIFT_PRMT_ORG@DL_MSP C
, MSP_GIFT_PRMT_SOC@DL_MSP D
, ORG_ORGN_INFO_MST@DL_MSP E
WHERE A.USG_YN = 'Y'
AND A.PRMT_ID = B.PRMT_ID
AND A.PRMT_ID = C.PRMT_ID
AND A.PRMT_ID = D.PRMT_ID
AND C.ORGN_ID = E.ORGN_ID
AND E.TYPE_DTL_CD2 = DECODE(A.ORGN_TYPE,'ALL',E.TYPE_DTL_CD2,A.ORGN_TYPE) /* 채널유형 */
AND TO_DATE(TO_CHAR(SYSDATE,'YYYYMMDD'),'YYYYMMDD') BETWEEN TO_DATE(A.STRT_DT,'YYYYMMDD') AND (TO_DATE(A.END_DT,'YYYYMMDD')+8)
AND B.ON_OFF_TYPE = #{onOffType}
AND D.RATE_CD = #{rateCd}
AND A.REQ_BUY_TYPE = #{reqBuyType}
AND E.ORGN_ID = #{orgnId}
<choose>
<when test='agrmTrm == "0"'>
AND A.ENGG_CNT_0 = 'Y'
</when>
<when test='agrmTrm == "12"'>
AND A.ENGG_CNT_12 = 'Y'
</when>
<when test='agrmTrm == "18"'>
AND A.ENGG_CNT_18 = 'Y'
</when>
<when test='agrmTrm == "24"'>
AND A.ENGG_CNT_24 = 'Y'
</when>
<when test='agrmTrm == "30"'>
AND A.ENGG_CNT_30 = 'Y'
</when>
<when test='agrmTrm == "36"'>
AND A.ENGG_CNT_36 = 'Y'
</when>
<otherwise>
AND 1 = 2
</otherwise>
</choose>
<choose>
<when test='operType == "NAC3"'>
AND A.NAC_YN = 'Y'
</when>
<when test='operType == "MNP3"'>
AND A.MNP_YN = 'Y'
</when>
<otherwise>
AND 1 = 2
</otherwise>
</choose>
ORDER BY PRMT_TYPE DESC
```


#### API_0151 — 사은품 상세 조회

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `getPrmtId` |
| 연동명(한글) | 사은품 상세 조회 |
| 엔드포인트 | `/appForm/getPrmtId` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | GiftMapper.xml |
| SQL ID | `getPrmtId` |
| parameterType | `com.ktmmobile.mcp.etc.dto.GiftPromotionDtl` |
| resultType | `com.ktmmobile.mcp.etc.dto.GiftPromotionDtl` |
| 사용 여부 | ✅ 사용 |
| 담당자 | 장성환 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `prmtId` | 프로모션아이디 | `string` | 프로모션아이디 | PRMT_ID | MSP_GIFT_PRMT_PRDT |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `prmtId` | 프로모션아이디 | `string` | 프로모션아이디 | PRMT_ID | MSP_GIFT_PRMT_PRDT |
| `prdtId` | 사은품아이디 | `string` | 사은품아이디 | PRDT_ID | MSP_GIFT_PRDT |
| `prdtNm` | 사은품명 | `string` | 사은품명 | PRDT_NM | MSP_GIFT_PRDT |
| `prdtDesc` | 사은품설명 | `string` | 사은품설명 | PRDT_DESC | MSP_GIFT_PRDT |
| `outUnitPric` | 단가 | `string` | 단가 | OUT_UNIT_PRIC | MSP_GIFT_PRDT |
| `imgFile` | 이미지 | `string` | 이미지 | IMG_FILE | MSP_GIFT_PRDT |
| `webUrl` | 이미지 | `string` | 이미지 | WEB_URL | MSP_GIFT_PRDT |

**SQL**

```sql
SELECT
PRMT.PRMT_ID
, PRDT.PRDT_ID
, PRDT.PRDT_NM
, PRDT.PRDT_DESC
, PRDT.OUT_UNIT_PRIC
, PRDT.IMG_FILE
, PRDT.WEB_URL
FROM MSP_GIFT_PRMT_PRDT@DL_MSP PRMT
INNER JOIN MSP_GIFT_PRDT@DL_MSP PRDT
ON PRMT.PRDT_ID = PRDT.PRDT_ID
WHERE 1=1
AND PRDT.USE_YN = 'Y'
AND PRMT.PRMT_ID = #{prmtId}
```


#### API_0152 — 사은품 리스트 조회

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `getGiftArrList` |
| 연동명(한글) | 사은품 리스트 조회 |
| 엔드포인트 | `/appForm/getGiftArrList` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | GiftMapper.xml |
| SQL ID | `getGiftArrList` |
| parameterType | `com.ktmmobile.mcp.etc.dto.GiftPromotionDto` |
| resultType | `java.lang.String` |
| 사용 여부 | ✅ 사용 |
| 담당자 | 장성환 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `onOffType` | 신청유형 | `string` | 신청유형 | ON_OFF_TYPE | MSP_GIFT_PRMT_ONOFF |
| `rateCd` | 요금제코드 | `string` | 요금제코드 | RATE_CD | MSP_GIFT_PRMT_SOC |
| `reqBuyType` | 구매유형 단말 | `string` | 구매유형 단말 | REQ_BUY_TYPE | MSP_GIFT_PRMT |
| `orgnId` | 조직 | `string` | 조직 | ORGN_ID | ORG_ORGN_INFO_MST |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `prmtId` | 프로모션아이디 | `string` | 프로모션아이디 | PRMT_ID | MSP_GIFT_PRMT |

**SQL**

```sql
SELECT
DISTINCT A.PRMT_ID
FROM MSP_GIFT_PRMT@DL_MSP A
, MSP_GIFT_PRMT_ONOFF@DL_MSP B
, MSP_GIFT_PRMT_ORG@DL_MSP C
, MSP_GIFT_PRMT_SOC@DL_MSP D
, ORG_ORGN_INFO_MST@DL_MSP E
WHERE A.USG_YN = 'Y'
AND A.PRMT_ID = B.PRMT_ID
AND A.PRMT_ID = C.PRMT_ID
AND A.PRMT_ID = D.PRMT_ID
AND E.TYPE_DTL_CD2 = DECODE(A.ORGN_TYPE,'ALL',E.TYPE_DTL_CD2,A.ORGN_TYPE) /* 채널유형 */
AND TO_DATE(TO_CHAR(SYSDATE,'YYYYMMDD'),'YYYYMMDD') BETWEEN TO_DATE(A.STRT_DT,'YYYYMMDD') AND (TO_DATE(A.END_DT,'YYYYMMDD')+8)
AND B.ON_OFF_TYPE = #{onOffType}
AND D.RATE_CD = #{rateCd}
AND A.REQ_BUY_TYPE = #{reqBuyType}
AND E.ORGN_ID = #{orgnId}
<choose>
<when test='agrmTrm == "0"'>
AND A.ENGG_CNT_0 = 'Y'
</when>
<when test='agrmTrm == "12"'>
AND A.ENGG_CNT_12 = 'Y'
</when>
<when test='agrmTrm == "18"'>
AND A.ENGG_CNT_18 = 'Y'
</when>
<when test='agrmTrm == "24"'>
AND A.ENGG_CNT_24 = 'Y'
</when>
<when test='agrmTrm == "30"'>
AND A.ENGG_CNT_30 = 'Y'
</when>
<when test='agrmTrm == "36"'>
AND A.ENGG_CNT_36 = 'Y'
</when>
<otherwise>
AND 1 = 2
</otherwise>
</choose>
<choose>
<when test='operType == "NAC3"'>
AND A.NAC_YN = 'Y'
</when>
<when test='operType == "MNP3"'>
AND A.MNP_YN = 'Y'
</when>
<otherwise>
AND 1 = 2
</otherwise>
</choose>
```


---

### STOREUSIM 도메인 (10개)

#### API_0099 — selectKtRcgSeq

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `selectKtRcgSeq` |
| 엔드포인트 | `/storeUsim/ktRcgSeq` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | StoreUsimMapper.xml |
| SQL ID | `selectKtRcgSeq` |
| parameterType | `com.ktmmobile.mcp.usim.dto.KtRcgDto` |
| resultType | `com.ktmmobile.mcp.usim.dto.KtRcgDto` |
| 사용 여부 | ✅ 사용 |
| SQL 유형 | 파라미터를 DTO로 넘김 |
| 대상 테이블 | P_REMAINS_QUERY_B2C |
| 담당자 | 강문재 |
| 비고 | FUNCTION |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `iReqType` |  | `String` |  |  | P_REMAINS_QUERY_B2C |
| `iSubscriberNo` |  | `String` |  |  | P_REMAINS_QUERY_B2C |
| `iReqIp` |  | `String` |  |  | P_REMAINS_QUERY_B2C |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `oRcgSeq` |  | `String` |  |  | P_REMAINS_QUERY_B2C |
| `oRetCode` |  | `String` |  |  | P_REMAINS_QUERY_B2C |
| `oRetMsg` |  | `String` |  |  | P_REMAINS_QUERY_B2C |

**SQL**

```sql
<![CDATA[
{call P_REMAINS_QUERY_B2C@DL_MSP(?,?,?,?,?,?)}
]]>
```


#### API_0100 — selectRcg

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `selectRcg` |
| 엔드포인트 | `/storeUsim/ktRcg` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | StoreUsimMapper.xml |
| SQL ID | `selectRcg` |
| parameterType | `java.util.Map` |
| resultType | `java.util.Map` |
| 사용 여부 | ✅ 사용 |
| SQL 유형 | 파라미터를 Map으로 넘김 |
| 대상 테이블 | P_RCG_QUERY_B2C |
| 담당자 | 강문재 |
| 비고 | FUNCTION |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `I_RCG_SEQ` |  | `Map<String>` |  |  | P_RCG_QUERY_B2C |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `O_RES_CODE` |  | `Map<String>` |  |  | P_RCG_QUERY_B2C |
| `O_AMOUNT` |  | `Map<String>` |  |  | P_RCG_QUERY_B2C |
| `O_OLD_REMAINS` |  | `Map<String>` |  |  | P_RCG_QUERY_B2C |
| `O_REMAINS` |  | `Map<String>` |  |  | P_RCG_QUERY_B2C |
| `O_OLD_EXPIRE` |  | `Map<String>` |  |  | P_RCG_QUERY_B2C |
| `O_EXPIRE` |  | `Map<String>` |  |  | P_RCG_QUERY_B2C |
| `O_RET_CODE` |  | `Map<String>` |  |  | P_RCG_QUERY_B2C |
| `O_RET_MSG` |  | `Map<String>` |  |  | P_RCG_QUERY_B2C |

**SQL**

```sql
<![CDATA[
{call P_RCG_QUERY_B2C@DL_MSP(?,?,?,?,?,?,?,?,?)}
]]>
```


#### API_0102 — selectModelList

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `selectModelList` |
| 엔드포인트 | `/storeUsim/modelList` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | StoreUsimMapper.xml |
| SQL ID | `selectModelList` |
| parameterType | `com.ktmmobile.mcp.usim.dto.UsimBasDto` |
| resultType | `com.ktmmobile.mcp.usim.dto.UsimBasDto` |
| 사용 여부 | ✅ 사용 |
| SQL 유형 | 파라미터를 DTO로 넘김 |
| 대상 테이블 | MSP_SALE_PRDT_MST, CMN_INTM_MDL |
| 담당자 | 강문재 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `salePlcyCd` | 판매정책코드 | `String` | 판매정책코드 | SALE_PLCY_CD | MSP_SALE_PRDT_MST |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `prdtId` | 단말ID | `String` | 단말ID | PRDT_ID | MSP_SALE_PRDT_MST |
| `prdtCode` | 제품코드 | `String` | 제품코드 | PRDT_CODE | CMN_INTM_MDL |
| `prdtNm` | 상품명 | `String` | 상품명 | PRDT_NM | CMN_INTM_MDL |

**SQL**

```sql
SELECT
A.PRDT_ID,
PRDT_CODE,
PRDT_NM
FROM 	MSP_SALE_PRDT_MST@DL_MSP A
,CMN_INTM_MDL@DL_MSP B
WHERE 	SALE_PLCY_CD = #{salePlcyCd}
AND 	A.PRDT_ID = B.PRDT_ID
```


#### API_0103 — USIM 가입비조회

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `selectJoinUsimPriceNew` |
| 연동명(한글) | USIM 가입비조회 |
| 엔드포인트 | `/storeUsim/joinUsimPriceNew` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | StoreUsimMapper.xml |
| SQL ID | `selectJoinUsimPriceNew` |
| parameterType | `String` |
| resultType | `com.ktmmobile.mcp.usim.dto.UsimMspRateDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | CMN_GRP_CD_MST |
| 담당자 | 강문재 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `gubun` | 코드값 | `String` | 코드값 | CD_VAL | CMN_GRP_CD_MST |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `joinPrice` | 가입비 | `String` | 가입비 | DECODE(ETC3,'Y',ETC4,ETC1) AS JOIN_PRICE | CMN_GRP_CD_MST |
| `usimPrice` | 유심가격 | `String` | 유심가격 | DECODE(ETC3,'Y',ETC5,ETC2) AS USIM_PRICE | CMN_GRP_CD_MST |

**SQL**

```sql
SELECT 	DECODE(ETC3,'Y',ETC4,ETC1) AS JOIN_PRICE
, DECODE(ETC3,'Y',ETC5,ETC2) AS USIM_PRICE
from 	CMN_GRP_CD_MST@dl_msp
WHERE 	GRP_ID = 'RCP0053'
AND 	CD_VAL = #{gubun}
AND ROWNUM =1
```


#### API_0104 — usim 상품 약정기간 없는 할인율 조회

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `selectUsimDcamt` |
| 연동명(한글) | usim 상품 약정기간 없는 할인율 조회 |
| 엔드포인트 | `/storeUsim/usimDcamt` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | StoreUsimMapper.xml |
| SQL ID | `selectUsimDcamt` |
| parameterType | `String` |
| resultType | `com.ktmmobile.mcp.usim.dto.UsimMspRateDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_RATE_SPEC |
| 담당자 | 강문재 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `rateCd` | 요금제코드 | `String` | 요금제코드 | RATE_CD | MSP_RATE_SPEC |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `dcAmt` | 할인율 | `String` | 할인율 | DC_AMT | MSP_RATE_SPEC |

**SQL**

```sql
SELECT DC_AMT
FROM MSP_RATE_SPEC@DL_MSP F
WHERE RATE_CD =#{rateCd}
AND AGRM_TRM ='0'
AND ROWNUM =1
ORDER BY APPL_STRT_DTTM DESC
```


#### API_0105 — usim 상품 약정 할인 조회

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `selectAgreeDcAmt` |
| 연동명(한글) | usim 상품 약정 할인 조회 |
| 엔드포인트 | `/storeUsim/agreeDcAmt` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | StoreUsimMapper.xml |
| SQL ID | `selectAgreeDcAmt` |
| parameterType | `com.ktmmobile.mcp.usim.dto.UsimMspRateDto` |
| resultType | `com.ktmmobile.mcp.usim.dto.UsimMspRateDto` |
| 사용 여부 | ✅ 사용 |
| SQL 유형 | 파라미터를 DTO로 넘김 |
| 대상 테이블 | MSP_RATE_SPEC, MSP_SALE_PLCY_MST, MSP_SALE_ORGN_MST, MSP_SALE_AGRM_MST |
| 담당자 | 강문재 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `rateCd` | 요금제 코드 | `String` | 요금제 코드 | RATE_CD | MSP_RATE_SPEC |
| `salePlcyCd` | 판매정책코드 | `String` | 판매정책코드 | SALE_PLCY_CD | MSP_SALE_PLCY_MST |
| `orgnId` | 조직코드 | `String` | 조직코드 | ORGN_ID | MSP_SALE_ORGN_MST |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `agrmTrm` | 약정기간 | `String` | 약정기간 | AGRM_TRM | MSP_RATE_SPEC |
| `dcAmt` | 할인율 | `String` | 할인율 | DC_AMT | MSP_RATE_SPEC |

**SQL**

```sql
SELECT
AGRM_TRM,
DC_AMT
FROM MSP_RATE_SPEC@DL_MSP
WHERE RATE_CD = #rateCd#
AND AGRM_TRM IN
(
SELECT AGRM.AGRM_TRM
FROM
MSP_SALE_PLCY_MST@DL_MSP PLCY,
MSP_SALE_ORGN_MST@DL_MSP ORNG,
MSP_SALE_AGRM_MST@DL_MSP AGRM
WHERE PLCY.SALE_PLCY_CD = #salePlcyCd#
AND PLCY.SALE_PLCY_CD = AGRM.SALE_PLCY_CD
AND PLCY.SALE_PLCY_CD = ORNG.SALE_PLCY_CD
AND ORNG.SALE_PLCY_CD = AGRM.SALE_PLCY_CD
AND ORNG.ORGN_ID = #orgnId#
AND     TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS') BETWEEN PLCY.SALE_STRT_DTTM AND PLCY.SALE_END_DTTM
AND     PLCY.CNFM_YN = 'Y'
)
AND AGRM_TRM !='0'
ORDER BY AGRM_TRM ASC
```


#### API_0106 — 이름과 전화번호로 사용 요금제 조회

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `selectUserChargeInfo` |
| 연동명(한글) | 이름과 전화번호로 사용 요금제 조회 |
| 엔드포인트 | `/storeUsim/userChargeInfo` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | StoreUsimMapper.xml |
| SQL ID | `selectUserChargeInfo` |
| parameterType | `com.ktmmobile.mcp.common.dto.AuthSmsDto` |
| resultType | `com.ktmmobile.mcp.common.dto.AuthSmsDto` |
| 사용 여부 | ✅ 사용 |
| SQL 유형 | 파라미터를 DTO로 넘김 |
| 대상 테이블 | MSP_RATE_MST, MSP_JUO_FEATURE_INFO, MSP_JUO_SUB_INFO |
| 담당자 | 강문재 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `phoneNum` | 휴대폰번호 | `String` | 휴대폰번호 | SUBSCRIBER_NO | MSP_JUO_SUB_INFO |
| `memberName` | 이름 | `String` | 이름 | SUB_LINK_NAME | MSP_JUO_SUB_INFO |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `rateCd` | 요금제코드 | `String` | 요금제코드 | RATE_CD | MSP_RATE_MST |
| `rateNm` | 요금제명 | `String` | 요금제명 | RATE_NM | MSP_RATE_MST |

**SQL**

```sql
<![CDATA[
SELECT DISTINCT RATE_CD
,RATE_NM
FROM MSP_RATE_MST@DL_MSP A,
MSP_JUO_FEATURE_INFO@DL_MSP B
WHERE 1=1
AND B.CONTRACT_NUM = (SELECT CONTRACT_NUM FROM MSP_JUO_SUB_INFO@DL_MSP WHERE SUBSCRIBER_NO=#{phoneNum} AND SUB_LINK_NAME=#{memberName} AND SUB_STATUS<>'C' )
AND A.RATE_CD = B.SOC
AND A.SERVICE_TYPE = 'P'
AND ROWNUM=1
]]>
```


#### API_0107 — 상품정보로 유효한 정책정보를  조회(약정포함)

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `selectUsimSalePlcyCdList` |
| 연동명(한글) | 상품정보로 유효한 정책정보를  조회(약정포함) |
| 엔드포인트 | `/storeUsim/usimSalePlcyCdList` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | StoreUsimMapper.xml |
| SQL ID | `selectUsimSalePlcyCdList` |
| parameterType | `com.ktmmobile.mcp.mspservice.dto.MspSalePlcyMstDto` |
| resultType | `com.ktmmobile.mcp.mspservice.dto.MspSalePlcyMstDto` |
| 사용 여부 | ✅ 사용 |
| SQL 유형 | 파라미터를 DTO로 넘김 |
| 대상 테이블 | MSP_SALE_PRDT_MST, CMN_INTM_MDL, MSP_SALE_PLCY_MST, MSP_RATE_MST, MSP_SALE_RATE_MST, MSP_SALE_ORGN_MST, MSP_SALE_AGRM_MST |
| 담당자 | 강문재 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `prdtId` | 단품코드 | `String` | 단품코드 | PRDT_ID | MSP_SALE_PRDT_MST |
| `payClCd` | 선후불구분 | `String` | 선후불구분 | PAY_CL_CD | MSP_RATE_MST |
| `prdtSctnCd` | 제품구분코드 | `String` | 02:3G , 03:LTE | PRDT_SCTN_CD | MSP_SALE_PLCY_MST |
| `orgnId` | 조직코드 | `String` | 조직코드 | ORGN_ID | MSP_SALE_ORGN_MST |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `salePlcyCd` | 판매정책코드 | `String` | 판매정책코드 | SALE_PLCY_CD | MSP_SALE_PLCY_MST |
| `salePlcyNm` | 판매정책명 | `String` | 판매정책명 | SALE_PLCY_NM | MSP_SALE_PLCY_MST |
| `prdtSctnCd` | 제품구분코드 | `String` | 02:3G , 03:LTE | PRDT_SCTN_CD | MSP_SALE_PLCY_MST |
| `agrmTrm` | 약정기간 | `String` | 약정기간 | AGRM_TRM | MSP_SALE_AGRM_MST |
| `payClCd` | 선후불구분 | `String` | 선후불구분 | PAY_CL_CD | MSP_RATE_MST |
| `orgnId` | 조직코드 | `String` | 조직코드 | ORGN_ID | MSP_SALE_ORGN_MST |

**SQL**

```sql
SELECT/*상품정보로 유효한 정책정보를 들고온다.(약정포함)*/
DISTINCT
C.SALE_PLCY_CD,
C.SALE_PLCY_NM,
AG.AGRM_TRM,
RA.PAY_CL_CD,
C.PRDT_SCTN_CD,
ORG.ORGN_ID
FROM
MSP_SALE_PRDT_MST@DL_MSP A -- 정책
, CMN_INTM_MDL@DL_MSP B
, MSP_SALE_PLCY_MST@DL_MSP C
, MSP_RATE_MST@DL_MSP RA
, MSP_SALE_RATE_MST@DL_MSP SA
, MSP_SALE_ORGN_MST@DL_MSP ORG
, MSP_SALE_AGRM_MST@DL_MSP AG
WHERE 	1=1
AND C.CNFM_YN ='Y'
AND C.PLCY_SCTN_CD = 02
AND A.PRDT_ID = B.PRDT_ID
AND SA.SALE_PLCY_CD = AG.SALE_PLCY_CD
AND A.SALE_PLCY_CD = C.SALE_PLCY_CD
AND A.SALE_PLCY_CD = SA.SALE_PLCY_CD
AND RA.RATE_CD = SA.RATE_CD
AND C.SALE_PLCY_CD = ORG.SALE_PLCY_CD
AND TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS') BETWEEN C.SALE_STRT_DTTM AND C.SALE_END_DTTM
AND A.PRDT_ID = #{prdtId}
AND RA.PAY_CL_CD = #{payClCd}
<if test="prdtSctnCd != null and prdtSctnCd != ''">
AND C.PRDT_SCTN_CD = #{prdtSctnCd}
</if>
AND ORG.ORGN_ID = #{orgnId}
ORDER BY AGRM_TRM ASC
```


#### API_0109 — 가입유형 가져오기

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `selectPlcyOperTypeList` |
| 연동명(한글) | 가입유형 가져오기 |
| 엔드포인트 | `/storeUsim/plcyOperTypeList` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | StoreUsimMapper.xml |
| SQL ID | `selectPlcyOperTypeList` |
| parameterType | `com.ktmmobile.mcp.mspservice.dto.MspSalePlcyMstDto` |
| resultType | `com.ktmmobile.mcp.mspservice.dto.MspPlcyOperTypeDto` |
| 사용 여부 | ✅ 사용 |
| SQL 유형 | 파라미터를 DTO로 넘김 |
| 대상 테이블 | MSP_SALE_ORGN_MST, MSP_SALE_PLCY_MST, MSP_SALE_PRDT_MST |
| 담당자 | 강문재 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `orgnId` | 조직코드 | `String` | 조직코드 | ORGN_ID | MSP_SALE_ORGN_MST |
| `salePlcyCd` | 판매정책코드 | `String` | 판매정책코드 | SALE_PLCY_CD | MSP_SALE_ORGN_MST |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `operType` | 가입유형 코드 | `String` | 가입유형 코드 | OPER_TYPE | MSP_SALE_PLCY_MST |
| `operName` | 가입유형 이름 | `String` | 가입유형 이름 | OPER_NAME | MSP_SALE_PLCY_MST |
| `salePlcyCd` | 판매정책코드 | `String` | 판매정책코드 | SALE_PLCY_CD | MSP_SALE_ORGN_MST |

**SQL**

```sql
SELECT 	SALE_PLCY_CD,
CASE
WHEN LVL=1 THEN 'NAC3'
WHEN LVL=2 THEN 'MNP3'
WHEN LVL=3 THEN 'HCN3' END  OPER_TYPE,
CASE
WHEN LVL=1 THEN '신규가입'
WHEN LVL=2 THEN '번호이동'
WHEN LVL=3 THEN '기기변경' END  OPER_NAME
FROM	(
SELECT 	A.SALE_PLCY_CD
,(B.NEW_YN||B.MNP_YN||B.HCN_YN) BIT_MASK
FROM 	MSP_SALE_ORGN_MST@DL_MSP A
,MSP_SALE_PLCY_MST@DL_MSP B
,MSP_SALE_PRDT_MST@DL_MSP C
WHERE 	1=1
AND 	A.ORGN_ID = #{orgnId}
AND A.SALE_PLCY_CD = #{salePlcyCd}
AND 	A.SALE_PLCY_CD = B.SALE_PLCY_CD
AND 	TO_CHAR(SYSDATE,'yyyymmddhh24miss') BETWEEN B.SALE_STRT_DTTM AND B.SALE_END_DTTM
AND 	B.CNFM_YN = 'Y'
AND 	A.SALE_PLCY_CD = C.SALE_PLCY_CD
GROUP BY A.SALE_PLCY_CD
,(B.NEW_YN||B.MNP_YN||B.HCN_YN)
) PLCY,
(SELECT LEVEL LVL FROM DUAL CONNECT BY LEVEL <![CDATA[<=]]> 3 ) LVL
WHERE 	SUBSTR(BIT_MASK, LVL,1)='Y' ORDER BY OPER_NAME ASC
```


#### API_0110 — selectRateListMoreTwoRows

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `selectRateListMoreTwoRows` |
| 엔드포인트 | `/storeUsim/rateListMoreTwoRows` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | StoreUsimMapper.xml |
| SQL ID | `selectRateListMoreTwoRows` |
| parameterType | `com.ktmmobile.mcp.usim.dto.UsimBasDto` |
| resultType | `com.ktmmobile.mcp.usim.dto.UsimMspRateDto` |
| 사용 여부 | ✅ 사용 |
| SQL 유형 | 파라미터를 DTO로 넘김 |
| 대상 테이블 | MSP_SALE_ORGN_MST, MSP_SALE_PLCY_MST, MSP_SALE_RATE_MST, MSP_RATE_MST, CMN_GRP_CD_MST, MSP_SALE_AGRM_MST |
| 담당자 | 강문재 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `salePlcyCdList.item` | 판매정책코드 | `List<String>` | 판매정책코드 | SALE_PLCY_CD | MSP_SALE_ORGN_MST |
| `orgnId` | 조직코드 | `String` | 조직코드 | ORGN_ID | MSP_SALE_ORGN_MST |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `rateCd` | 요금제 코드 | `String` | 요금제 코드 | RATE_CD | MSP_RATE_MST |
| `rateNm` | 요금제명 | `String` | 요금제명 | RATE_NM | MSP_RATE_MST |
| `payClCd` | 선후불코드 | `String` | 선후불코드 | PAY_CL_CD | MSP_RATE_MST |
| `cdDsc` | 선후불명 | `String` | 선후불명 | CD_DSC | MSP_RATE_MST |
| `rateType` | 요금타입 | `String` | 요금타입 | RATE_TYPE | MSP_RATE_MST |
| `rateDesc` | 요금설명 | `String` | 요금설명 | RATE_DESC | MSP_RATE_MST |
| `vat` | 세금 | `String` | 세금 | VAT | MSP_RATE_MST |
| `baseAmt` | 요금 | `String` | 요금 | BASE_AMT | MSP_RATE_MST |
| `dataTypeCode` | 데이터 타입 | `String` | 데이터 타입 | DATA_TYPE AS DATA_TYPE_CODE | MSP_RATE_MST |
| `freeCallCnt` | 무료통화 | `String` | 무료통화 | FREE_CALL_CNT | MSP_RATE_MST |
| `nwInCallCnt` | 망내통화 | `String` | 망내통화 | NW_IN_CALL_CNT | MSP_RATE_MST |
| `nwOutCallCnt` | 망외통화 | `String` | 망외통화 | NW_OUT_CALL_CNT | MSP_RATE_MST |
| `freeSmsCnt` | SMS | `String` | SMS | FREE_SMS_CNT | MSP_RATE_MST |
| `freeDataCnt` | 데이터 | `String` | 데이터 | FREE_DATA_CNT | MSP_RATE_MST |
| `sprtTp` | 할인유형 | `String` | 할인유형 | SPRT_TP | MSP_SALE_PLCY_MST |
| `sprtTpNm` | 할인유형이름 | `String` | 할인유형이름 | GET_CODE_NM('F002',SPRT_TP)AS SPRT_TP_NM | MSP_SALE_PLCY_MST |
| `salePlcyCd` | 판매정책코드 | `String` | 판매정책코드 | SALE_PLCY_CD | MSP_SALE_PLCY_MST |

**SQL**

```sql
SELECT
RATE_CD,   						--요금제 코드
RATE_NM, 						--요금제명
PAY_CL_CD, 						--선후불코드
CD_DSC, 						--선후불명
RATE_TYPE, 						--요금타입
RATE_DESC, 						--요금설명
VAT, 							-- 세금
BASE_AMT, 						--요금
DATA_TYPE AS DATA_TYPE_CODE,		--데이터 타입
FREE_CALL_CNT,					-- 무료통화
NW_IN_CALL_CNT,					-- 망내통화
NW_OUT_CALL_CNT,				-- 망외통화
FREE_SMS_CNT,					-- SMS
FREE_DATA_CNT,					-- 데이터
SPRT_TP,						--할인유형
GET_CODE_NM('F002',SPRT_TP)AS SPRT_TP_NM --할인유형이름
,SALE_PLCY_CD
FROM (
SELECT 	D.RATE_CD, D.RATE_NM, PAY_CL_CD, CD_DSC, D.RATE_TYPE
, NVL(D.RMK, 0) AS RATE_DESC , D.BASE_AMT, (BASE_AMT*0.1) VAT, D.DATA_TYPE , D.FREE_CALL_CNT
,D.NW_IN_CALL_CNT,D.NW_OUT_CALL_CNT,D.FREE_SMS_CNT,D.FREE_DATA_CNT , B.SPRT_TP , B.SALE_PLCY_CD
FROM	MSP_SALE_ORGN_MST@DL_MSP A
,MSP_SALE_PLCY_MST@DL_MSP B
,MSP_SALE_RATE_MST@DL_MSP C
,MSP_RATE_MST@DL_MSP D
,CMN_GRP_CD_MST@DL_MSP E
,MSP_SALE_AGRM_MST@DL_MSP G
WHERE 	TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS') BETWEEN B.SALE_STRT_DTTM AND B.SALE_END_DTTM
and 	A.SALE_PLCY_CD  IN
<foreach item="item" collection="salePlcyCdList" open="(" separator="," close=")">
#{item}
</foreach>
AND   	A.ORGN_ID       = #{orgnId}
AND   	B.CNFM_YN        = 'Y'
AND		A.SALE_PLCY_CD = B.SALE_PLCY_CD
AND		A.SALE_PLCY_CD = C.SALE_PLCY_CD
AND 	C.RATE_CD = D.RATE_CD
AND 	D.APPL_END_DT = '99991231'
AND 	E.GRP_ID = 'CMN0032'
AND 	D.PAY_CL_CD = E.CD_VAL
AND 	G.SALE_PLCY_CD = A.SALE_PLCY_CD
)
GROUP BY 	RATE_CD, RATE_NM, PAY_CL_CD, CD_DSC, RATE_TYPE, RATE_DESC, VAT,
BASE_AMT, DATA_TYPE , FREE_CALL_CNT, NW_IN_CALL_CNT, NW_OUT_CALL_CNT,
FREE_SMS_CNT, FREE_DATA_CNT , SPRT_TP , SALE_PLCY_CD
ORDER BY BASE_AMT ASC
```


---

### N/A 도메인 (8개)

#### API_0023 — inquiryInsertByProcedure

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `inquiryInsertByProcedure` |
| 엔드포인트 | `` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper |  |
| SQL ID | `` |
| parameterType | `` |
| resultType | `` |
| 사용 여부 | ❌ 미사용 |
| 대상 테이블 | P_MSP_VOC_LINK_REG |
| 담당자 | 강채신 |
| 비고 | FUNCTION |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `qnaSeq` | 문의일련번호 | `int` | 문의일련번호 |  | P_MSP_VOC_LINK_REG |
| `qnaCtg` | 문의카테고리 | `String` | 문의카테고리 |  | P_MSP_VOC_LINK_REG |
| `qnaSubject` | 문의제목 | `String` | 문의제목 |  | P_MSP_VOC_LINK_REG |
| `qnaWriterID` | 작성자아이디 | `String` | 작성자아이디 |  | P_MSP_VOC_LINK_REG |
| `qnaNM` | 작성자명 | `String` | 작성자명 |  | P_MSP_VOC_LINK_REG |
| `mobileNo` | 핸드폰번호 | `String` | 핸드폰번호 |  | P_MSP_VOC_LINK_REG |
| `qnaContent` | 문의내용 | `String` | 문의내용 |  | P_MSP_VOC_LINK_REG |
| `qnaWriterID` | 작성자아이디 | `String` | 작성자아이디 |  | P_MSP_VOC_LINK_REG |
| `qnaSmsSendYn` | 고객수신여부 | `String` | 고객수신여부 |  | P_MSP_VOC_LINK_REG |

**SQL**

```sql
<selectKey keyProperty="qnaSeq" resultType="Integer" order="BEFORE">
SELECT SQ_QNA_BOARD_SEQ.NEXTVAL FROM DUAL
</selectKey>
{CALL P_MSP_VOC_LINK_REG@dl_msp(#{qnaSeq},#{qnaCtg},#{qnaSubject},#{qnaWriterID},#{qnaNM},#{mobileNo},#{qnaContent},#{qnaWriterID},#{qnaSmsSendYn})}
```


#### API_0032 — withMspSalePlcyMst

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `withMspSalePlcyMst` |
| 엔드포인트 | `` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper |  |
| SQL ID | `` |
| parameterType | `` |
| resultType | `` |
| 사용 여부 | ❌ 미사용 |
| 대상 테이블 | MSP_SALE_PLCY_MST |
| 담당자 | 김대원 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `salePlcyCd` | 판매정책코드 | `String` | 판매정책코드 | SALE_PLCY_CD | MSP_SALE_PLCY_MST |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `salePlcyCd` | 판매정책코드 | `String` | 판매정책코드 | SALE_PLCY_CD | MSP_SALE_PLCY_MST |
| `salePlcyNm` | 판매정책명 | `String` | 판매정책명 | SALE_PLCY_NM | MSP_SALE_PLCY_MST |
| `saleStrtDttm` | 판매시작일시 | `String` | 판매시작일시 | SALE_STRT_DTTM | MSP_SALE_PLCY_MST |
| `saleEndDttm` | 판매종료일시 | `String` | 판매종료일시 | SALE_END_DTTM | MSP_SALE_PLCY_MST |
| `plcyTypeCd` | 정책유형코드 | `String` | 정책유형코드 M:오프라인, O:온라인, D:직영온라인 | PLCY_TYPE_CD | MSP_SALE_PLCY_MST |
| `orgnType` | 조직유형 | `String` | 조직유형 | ORGN_TYPE | MSP_SALE_PLCY_MST |
| `plcySctnCd` | 정책구분코드 | `String` | 정책구분코드 할부개월표시(외부서식지)      * 01 :  단말 구매   할부 개월 리스트 표현      * 02 :   USIM(유심)단독  | PLCY_SCTN_CD | MSP_SALE_PLCY_MST |
| `prdtSctnCd` | 제품구분코드 | `String` | 제품구분코드(02:3G , 03:LTE , 5G ) | PRDT_SCTN_CD | MSP_SALE_PLCY_MST |
| `applSctnCd` | 적용구분코드 | `String` | 적용구분코드 O:개통일, R:접수일 | APPL_SCTN_CD | MSP_SALE_PLCY_MST |
| `instRate` | 할부이자율 | `BigDecimal` | 할부이자율 | INST_RATE | MSP_SALE_PLCY_MST |
| `newYn` | 가입유형 신규 | `String` | 가입유형 신규 | NEW_YN | MSP_SALE_PLCY_MST |
| `mnpYn` | 가입유형 번호이동 | `String` | 가입유형 번호이동 | MNP_YN | MSP_SALE_PLCY_MST |
| `hcnYn` | 가입유형 기기변경 | `String` | 가입유형 기기변경 | HCN_YN | MSP_SALE_PLCY_MST |
| `hdnYn` | 우수기변여부 | `String` | 우수기변여부 | HDN_YN | MSP_SALE_PLCY_MST |
| `cnfmYn` | 확정여부 | `String` | 확정여부 | CNFM_YN | MSP_SALE_PLCY_MST |
| `cnfmId` | 확정자ID | `String` | 확정자ID | CNFM_ID | MSP_SALE_PLCY_MST |
| `cnfmDttm` | 확정일시 | `String` | 확정일시 | CNFM_DTTM | MSP_SALE_PLCY_MST |
| `regstId` | 등록자ID | `String` | 등록자ID | REGST_ID | MSP_SALE_PLCY_MST |
| `regstDttm` | 등록일시 | `Date` | 등록일시 | REGST_DTTM | MSP_SALE_PLCY_MST |
| `rvisnId` | 수정자ID | `String` | 수정자ID | RVISN_ID | MSP_SALE_PLCY_MST |
| `rvisnDttm` | 수정일시 | `Date` | 수정일시 | RVISN_DTTM | MSP_SALE_PLCY_MST |
| `sprtTp` | 지원금유형 | `String` | 지원금유형 (단말할인:KD , 요금할인:PM)      * 단말할인:KD      * 요금할인:PM      * 심플할인:SM   (유심, 약정 | SPRT_TP | MSP_SALE_PLCY_MST |

**SQL**

```sql
SELECT
PL.SALE_PLCY_CD ,					--판매정책코드
PL.SALE_PLCY_NM,                    --판매정책명
PL.SALE_STRT_DTTM,                  --판매시작일시
PL.SALE_END_DTTM,                   --판매종료일시
PL.PLCY_TYPE_CD,                    --정책유형코드 M:오프라인, O:온라인
PL.ORGN_TYPE,                       --조직유형
PL.PLCY_SCTN_CD,                    --정책구분코드 01:단말, 02:USIM
PL.PRDT_SCTN_CD,                    --제품구분코   02:3G, 03:LTE
PL.APPL_SCTN_CD,                    --적용구분코드 O:개통일, R:접수일
PL.INST_RATE,                       --할부이자율
PL.NEW_YN,                          --신규여부
PL.MNP_YN,                          --번호이동여부
PL.HCN_YN,                          --기변여부
PL.HDN_YN,                          --우수기변여부
PL.CNFM_YN,                         --확정여부
PL.CNFM_ID,                         --확정자ID
PL.CNFM_DTTM,                       --확정일시
PL.REGST_ID,                        --등록자ID
PL.REGST_DTTM,                      --등록일시
PL.RVISN_ID,                        --수정자ID
PL.RVISN_DTTM,                      --수정일시
PL.SPRT_TP                         --지원금유형
FROM
MSP_SALE_PLCY_MST@DL_MSP PL
WHERE
PL.SALE_PLCY_CD = #{salePlcyCd}
```


#### API_0035 — getromotionDcAmt

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `getromotionDcAmt` |
| 엔드포인트 | `` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper |  |
| SQL ID | `` |
| parameterType | `` |
| resultType | `` |
| 사용 여부 | ❌ 미사용 |
| 대상 테이블 | VW_CHRG_PRMT_DTL |
| 담당자 | 김대원 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `orgnId` | 접점코드 | `String` | 접점코드 | ORGN_ID | VW_CHRG_PRMT_DTL |
| `rateCd` | 요금제코드 | `String` | 요금제코드 | RATE_CD | VW_CHRG_PRMT_DTL |
| `onOffType` | 온라인오프라인구분 | `String` | 온라인오프라인구분 | ON_OFF_TYPE | VW_CHRG_PRMT_DTL |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `rantal` | 렌탈비용 | `int` | 렌탈비용 | SUM(RANTAL) | VW_CHRG_PRMT_DTL |

**SQL**

```sql
SELECT SUM(RANTAL) FROM (
SELECT
CEIL(-RANTAL * 1.1) AS RANTAL
FROM
VW_CHRG_PRMT_DTL@DL_MSP A
, MCP_ADDITION B
WHERE 1=1
AND A.ADDITION_KEY = B.ADDITION_KEY
AND TO_CHAR(SYSDATE, 'YYYYMMDD') BETWEEN A.STRT_DT AND A.END_DT
AND A.ORGN_ID = #{orgnId} --접점코드
<choose>
<when test="plcySctnCd == '01'" >
AND   A.REQ_BUY_TYPE IN('MM','AL')
</when>
<when test="plcySctnCd == '02'" >
AND    A.REQ_BUY_TYPE IN('UU','AL')
</when>
<otherwise>
AND    1 = 0
</otherwise>
</choose>
<choose>
<when test="agrmTrm == 0" >
AND     A.ENGG_CNT_0 = 'Y'
</when>
<when test="agrmTrm == 12" >
AND     A.ENGG_CNT_12 = 'Y'
</when>
<when test="agrmTrm == 18" >
AND     A.ENGG_CNT_18 = 'Y'
</when>
<when test="agrmTrm == 24" >
AND     A.ENGG_CNT_24 = 'Y'
</when>
<when test="agrmTrm == 30" >
AND     A.ENGG_CNT_30 = 'Y'
</when>
<when test="agrmTrm == 36" >
AND     A.ENGG_CNT_36 = 'Y'
</when>
<otherwise>
AND    1 = 0
</otherwise>
</choose>
<choose>
<when test="operType == 'MNP3'" >
AND     A.MNP_YN = 'Y'
</when>
<when test="operType == 'NAC3'" >
AND     A.NAC_YN = 'Y'
</when>
<otherwise>
AND    1 = 0
</otherwise>
</choose>
AND     A.RATE_CD = #{rateCd} --요금제코드
AND     A.ON_OFF_TYPE = #{onOffType} --온라인오프라인구분
AND     A.USG_YN = 'Y'
)
```


#### API_0090 — getromotionDcAmt

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `getromotionDcAmt` |
| 엔드포인트 | `` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper |  |
| SQL ID | `` |
| parameterType | `` |
| resultType | `` |
| 사용 여부 | ❌ 미사용 |
| 대상 테이블 | VW_CHRG_PRMT_DTL |
| 담당자 | 김대원 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `orgnId` | 조직코드 | `String` | 조직코드 | ORGN_ID | VW_CHRG_PRMT_DTL |
| `rateCd` | 요금제코드 | `String` | 요금제코드 | RATE_CD | VW_CHRG_PRMT_DTL |
| `onOffType` | 온라인오프라인구분 | `String` | 온라인오프라인구분 | ON_OFF_TYPE | VW_CHRG_PRMT_DTL |
| `agrmTrm` | 약정코드 | `String` | 약정코드 |  |  |
| `operType` | 업무구분 | `String` | 업무구분 |  |  |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `rantal` | 렌탈비용 | `int` | 렌탈비용 | SUM(RANTAL) | VW_CHRG_PRMT_DTL |

**SQL**

```sql
SELECT SUM(RANTAL) FROM (
SELECT
CEIL(-RANTAL * 1.1) AS RANTAL
FROM
VW_CHRG_PRMT_DTL@DL_MSP A
, MCP_ADDITION B
WHERE 1=1
AND A.ADDITION_KEY = B.ADDITION_KEY
AND TO_CHAR(SYSDATE, 'YYYYMMDD') BETWEEN A.STRT_DT AND A.END_DT
AND A.ORGN_ID = #{orgnId} --접점코드
AND A.REQ_BUY_TYPE IN('UU','AL')
<choose>
<when test="agrmTrm == 0" >
AND     A.ENGG_CNT_0 = 'Y'
</when>
<when test="agrmTrm == 12" >
AND     A.ENGG_CNT_12 = 'Y'
</when>
<when test="agrmTrm == 18" >
AND     A.ENGG_CNT_18 = 'Y'
</when>
<when test="agrmTrm == 24" >
AND     A.ENGG_CNT_24 = 'Y'
</when>
<when test="agrmTrm == 30" >
AND     A.ENGG_CNT_30 = 'Y'
</when>
<when test="agrmTrm == 36" >
AND     A.ENGG_CNT_36 = 'Y'
</when>
<otherwise>
AND    1 = 0
</otherwise>
</choose>
<choose>
<when test="operType == 'MNP3'" >
AND     A.MNP_YN = 'Y'
</when>
<when test="operType == 'NAC3'" >
AND     A.NAC_YN = 'Y'
</when>
<otherwise>
AND    1 = 0
</otherwise>
</choose>
AND     A.RATE_CD = #{rateCd} --요금제코드
AND     A.ON_OFF_TYPE = #{onOffType} --온라인오프라인구분
AND     A.USG_YN = 'Y'
)
```


#### API_0101 — usim 상품 요금제 가져오기

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `selectRateList` |
| 연동명(한글) | usim 상품 요금제 가져오기 |
| 엔드포인트 | `` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | StoreUsimMapper.xml |
| SQL ID | `selectRateList` |
| parameterType | `com.ktmmobile.mcp.usim.dto.UsimBasDto` |
| resultType | `com.ktmmobile.mcp.usim.dto.UsimMspRateDto` |
| 사용 여부 | ✅ 사용 |
| SQL 유형 | 파라미터를 DTO로 넘김 |
| 대상 테이블 | MSP_SALE_ORGN_MST, MSP_SALE_PLCY_MST, MSP_SALE_RATE_MST, MSP_RATE_MST, CMN_GRP_CD_MST, MSP_SALE_AGRM_MST |
| 담당자 | 강문재 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `salePlcyCd` | 판매정책코드 | `String` | 판매정책코드 | SALE_PLCY_CD | MSP_SALE_ORGN_MST |
| `orgnId` | 조직코드 | `String` | 조직코드 | ORGN_ID | MSP_SALE_ORGN_MST |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `rateCd` | 요금제 코드 | `String` | 요금제 코드 | RATE_CD | MSP_RATE_MST |
| `rateNm` | 요금제명 | `String` | 요금제명 | RATE_NM | MSP_RATE_MST |
| `payClCd` | 선후불코드 | `String` | 선후불코드 | PAY_CL_CD | MSP_RATE_MST |
| `cdDsc` | 선후불명 | `String` | 선후불명 | CD_DSC | MSP_RATE_MST |
| `rateType` | 요금타입 | `String` | 요금타입 | RATE_TYPE | MSP_RATE_MST |
| `rateDesc` | 요금설명 | `String` | 요금설명 | RATE_DESC | MSP_RATE_MST |
| `vat` | 세금 | `String` | 세금 | VAT | MSP_RATE_MST |
| `baseAmt` | 요금 | `String` | 요금 | BASE_AMT | MSP_RATE_MST |
| `DataTypeCode` | 데이터 타입 | `String` | 데이터 타입 | DATA_TYPE AS DATA_TYPE_CODE | MSP_RATE_MST |
| `freeCallCnt` | 무료통화 | `String` | 무료통화 | FREE_CALL_CNT | MSP_RATE_MST |
| `nwInCallCnt` | 망내통화 | `String` | 망내통화 | NW_IN_CALL_CNT | MSP_RATE_MST |
| `nwOutCallCnt` | 망외통화 | `String` | 망외통화 | NW_OUT_CALL_CNT | MSP_RATE_MST |
| `freeSmsCnt` | SMS | `String` | SMS | FREE_SMS_CNT | MSP_RATE_MST |
| `freeDataCnt` | 데이터 | `String` | 데이터 | FREE_DATA_CNT | MSP_RATE_MST |
| `sprtTp` | 지원금유형 | `String` | 지원금유형 (단말할인:KD , 요금할인:PM)      * 단말할인:KD      * 요금할인:PM      * 심플할인:SM   (유심, 약정 | SPRT_TP | MSP_SALE_PLCY_MST |
| `sprtTpNm` | 할인유형이름 | `String` | 할인유형이름 | GET_CODE_NM('F002',SPRT_TP)AS SPRT_TP_NM | MSP_SALE_PLCY_MST |
| `salePlcyCd` | 판매정책코드 | `String` | 판매정책코드 | SALE_PLCY_CD | MSP_SALE_PLCY_MST |

**SQL**

```sql
SELECT
RATE_CD,   						--요금제 코드
RATE_NM, 						--요금제명
PAY_CL_CD, 						--선후불코드
CD_DSC, 						--선후불명
RATE_TYPE, 						--요금타입
RATE_DESC, 						--요금설명
VAT, 							-- 세금
BASE_AMT, 						--요금
DATA_TYPE AS DATA_TYPE_CODE,		--데이터 타입
FREE_CALL_CNT,					-- 무료통화
NW_IN_CALL_CNT,					-- 망내통화
NW_OUT_CALL_CNT,				-- 망외통화
FREE_SMS_CNT,					-- SMS
FREE_DATA_CNT					-- 데이터
FROM (
SELECT 	D.RATE_CD, D.RATE_NM, PAY_CL_CD, CD_DSC, D.RATE_TYPE
, NVL(D.RMK, 0) AS RATE_DESC , D.BASE_AMT, (BASE_AMT*0.1) VAT, D.DATA_TYPE , D.FREE_CALL_CNT
,D.NW_IN_CALL_CNT,D.NW_OUT_CALL_CNT,D.FREE_SMS_CNT,D.FREE_DATA_CNT
FROM	MSP_SALE_ORGN_MST@DL_MSP A
,MSP_SALE_PLCY_MST@DL_MSP B
,MSP_SALE_RATE_MST@DL_MSP C
,MSP_RATE_MST@DL_MSP D
,CMN_GRP_CD_MST@DL_MSP E
,MSP_SALE_AGRM_MST@DL_MSP G
WHERE 	TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS') BETWEEN B.SALE_STRT_DTTM AND B.SALE_END_DTTM
and 	A.SALE_PLCY_CD  = #{salePlcyCd}
AND   	A.ORGN_ID       = #{orgnId}
AND   	B.CNFM_YN        = 'Y'
AND		A.SALE_PLCY_CD = B.SALE_PLCY_CD
AND		A.SALE_PLCY_CD = C.SALE_PLCY_CD
AND 	C.RATE_CD = D.RATE_CD
AND 	D.APPL_END_DT = '99991231'
AND 	E.GRP_ID = 'CMN0032'
AND 	D.PAY_CL_CD = E.CD_VAL
AND 	G.SALE_PLCY_CD = A.SALE_PLCY_CD
)
GROUP BY 	RATE_CD, RATE_NM, PAY_CL_CD, CD_DSC, RATE_TYPE, RATE_DESC, VAT,
BASE_AMT, DATA_TYPE , FREE_CALL_CNT, NW_IN_CALL_CNT, NW_OUT_CALL_CNT,
FREE_SMS_CNT, FREE_DATA_CNT
ORDER BY BASE_AMT ASC
```


#### API_0108 — 해당정책코드에 해당하는 판매요금제 정보 리스트 조회

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `selectMspRateMstList` |
| 연동명(한글) | 해당정책코드에 해당하는 판매요금제 정보 리스트 조회 |
| 엔드포인트 | `` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper |  |
| SQL ID | `` |
| parameterType | `` |
| resultType | `` |
| 사용 여부 | ❌ 미사용 |
| 대상 테이블 | MSP_RATE_MST, MSP_SALE_RATE_MST, CMN_GRP_CD_MST, MSP_SALE_AGRM_MST |
| 담당자 | 강문재 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `salePlcyCd` | 판매정책코드 | `String` | 판매정책코드 | SALE_PLCY_CD | MSP_SALE_RATE_MST |
| `payClCd` | 선불후불 구분 | `String` | 선불후불 구분 | PAY_CL_CD | MSP_RATE_MST |
| `agrmTrm` | 약정기간 | `String` | 약정기간 | AGRM_TRM | MSP_SALE_AGRM_MST |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `rateCd` | 요금제코드 | `String` | 요금제코드 | RATE_CD | MSP_RATE_MST |
| `applEndDt` | 적용종료일자 | `String` | 적용종료일자 | APPL_END_DT | MSP_RATE_MST |
| `applStrtDt` | 적용시작일자 | `String` | 적용시작일자 | APPL_STRT_DT | MSP_RATE_MST |
| `rateNm` | 요금제명 | `String` | 요금제명 | RATE_NM | MSP_RATE_MST |
| `rateGrpCd` | 요금제그룹코드 | `String` | 요금제그룹코드 | RATE_GRP_CD | MSP_RATE_MST |
| `payClCd` | 선후불구분 | `String` | 선후불구분 | PAY_CL_CD | MSP_RATE_MST |
| `rateType` | 요금제유형 | `String` | ORG0008 | RATE_TYPE | MSP_RATE_MST |
| `dataType` | 데이터유형 | `String` | ORG0018 | DATA_TYPE | MSP_RATE_MST |
| `baseAmt` | 기본료 | `int` | 기본료 | BASE_AMT | MSP_RATE_MST |
| `freeCallClCd` | 망내외무료통화구분 | `String` | 망내외무료통화구분 | FREE_CALL_CL_CD | MSP_RATE_MST |
| `freeCallCnt` | 무료통화건수 | `String` | 무료통화건수 | FREE_CALL_CNT | MSP_RATE_MST |
| `nwInCallCnt` | 망내무료통화건수 | `String` | 망내무료통화건수 | NW_IN_CALL_CNT | MSP_RATE_MST |
| `nwOutCallCnt` | 망외무료통화건수 | `String` | 망외무료통화건수 | NW_OUT_CALL_CNT | MSP_RATE_MST |
| `freeSmsCnt` | 무료문자건수 | `String` | 무료문자건수 | FREE_SMS_CNT | MSP_RATE_MST |
| `freeDataCnt` | 무료데이터건수 | `String` | 무료데이터건수 | FREE_DATA_CNT | MSP_RATE_MST |
| `rmk` | 비고 | `String` | 비고 | RMK | MSP_RATE_MST |
| `regstId` | 등록자ID | `String` | 등록자ID | REGST_ID | MSP_RATE_MST |
| `regstDttm` | 등록일시 | `Date` | 등록일시 | REGST_DTTM | MSP_RATE_MST |
| `rvisnId` | 수정자ID | `String` | 수정자ID | RVISN_ID | MSP_RATE_MST |
| `rvisnDttm` | 수정일시 | `Date` | 수정일시 | RVISN_DTTM | MSP_RATE_MST |
| `onlineTypeCd` | 온라인유형코드 | `String` | 온라인유형코드 | ONLINE_TYPE_CD | MSP_RATE_MST |
| `alFlag` | 알요금제 구분자 | `String` | 알요금제 구분자 | AL_FLAG | MSP_RATE_MST |
| `serviceType` | 서비스유형 | `String` | 서비스유형 | SERVICE_TYPE | MSP_RATE_MST |
| `cmnt` | 조회개수 | `int` | 조회개수 | CMNT | MSP_RATE_MST |

**SQL**

```sql
SELECT
RA.RATE_CD,						--요금제코드
RA.APPL_END_DT,                    --적용종료일자
RA.APPL_STRT_DT,                   --적용시작일자
RA.RATE_NM,                        --요금제명
RA.RATE_GRP_CD,                    --요금제그룹코드
RA.PAY_CL_CD,                      --선후불구분
RA.RATE_TYPE,                      --요금제유형( ORG0008 )
RA.DATA_TYPE,                      --데이터유형( ORG0018 )
RA.BASE_AMT,                       --기본료
RA.FREE_CALL_CL_CD,                --망내외무료통화구분
RA.FREE_CALL_CNT,                  --무료통화건수
RA.NW_IN_CALL_CNT,                 --망내무료통화건수
RA.NW_OUT_CALL_CNT,                --망외무료통화건수
RA.FREE_SMS_CNT,                   --무료문자건수
RA.FREE_DATA_CNT,                  --무료데이터건수
RA.RMK,                            --비고
RA.REGST_ID,                       --등록자ID
RA.REGST_DTTM,                     --등록일시
RA.RVISN_ID,                       --수정자ID
RA.RVISN_DTTM,                     --수정일시
RA.ONLINE_TYPE_CD,                 --온라인유형코드
RA.AL_FLAG,                        --알요금제 구분자
RA.SERVICE_TYPE,                    --서비스유형
RA.CMNT							   -- 요금제혜택
FROM
MSP_RATE_MST@DL_MSP RA,
MSP_SALE_RATE_MST@DL_MSP SA,
CMN_GRP_CD_MST@DL_MSP GR,
MSP_SALE_AGRM_MST@DL_MSP AG
WHERE
APPL_END_DT = '99991231'
AND RA.PAY_CL_CD = GR.CD_VAL
AND GR.GRP_ID='CMN0032'	--선불후불 구분 코트값의 그룹코드
AND SA.SALE_PLCY_CD = #{salePlcyCd}
AND SA.SALE_PLCY_CD = AG.SALE_PLCY_CD
AND RA.RATE_CD = SA.RATE_CD
AND RA.PAY_CL_CD = #{payClCd}
AND AG.AGRM_TRM= #{agrmTrm}
```


#### API_0118 — listUsimPlcyMspDto

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `listUsimPlcyMspDto` |
| 엔드포인트 | `` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper |  |
| SQL ID | `` |
| parameterType | `` |
| resultType | `` |
| 사용 여부 | ❌ 미사용 |
| 대상 테이블 | MSP_RATE_MST, MSP_SALE_RATE_MST, CMN_GRP_CD_MST, MSP_SALE_PLCY_MST |
| 담당자 | 강문재 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `dataType` | 데이터 타입 | `String` | 데이터 타입 | DATA_TYPE | MSP_RATE_MST |
| `payClCd` | 선불 후불구분 | `String` | 선불 후불구분 | PAY_CL_CD | MSP_RATE_MST |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `salePlcyCd` | 판매정책코드 | `String` | 판매정책코드 | SALE_PLCY_CD | MSP_SALE_PLCY_MST |
| `salePlcyNm` | 판매정책코드명 | `String` | 판매정책코드명 | SALE_PLCY_NM | MSP_SALE_PLCY_MST |
| `payClCd` | 선불 후불구분 | `String` | 선불 후불구분 | PAY_CL_CD | MSP_RATE_MST |
| `dataType` | 데이터 타입 | `String` | 데이터 타입 | DATA_TYPE | MSP_RATE_MST |

**SQL**

```sql
SELECT
SALE_PLCY_CD
, SALE_PLCY_NM
, PAY_CL_CD
, DATA_TYPE
FROM (
SELECT SP.SALE_PLCY_CD --정책코드
,SP.SALE_PLCY_NM
,RA.PAY_CL_CD    -- 선불 후불구분 PO 후불 PP선불
,RA.DATA_TYPE	 -- 데이터 타입
FROM MSP_RATE_MST@DL_MSP RA,
MSP_SALE_RATE_MST@DL_MSP SA,
CMN_GRP_CD_MST@DL_MSP GR,
MSP_SALE_PLCY_MST@DL_MSP SP
WHERE
APPL_END_DT = '99991231'
AND RA.PAY_CL_CD = GR.CD_VAL
AND GR.GRP_ID='CMN0032'	--선불후불 구분 코트값의 그룹코드
AND SP.PLCY_TYPE_CD = 'D'		  -- 온라인:O , 오프라인:M , 직영온라인 : D
AND DATA_TYPE = #{dataType}
AND PAY_CL_CD = #{payClCd}
AND RA.RATE_CD = SA.RATE_CD
AND SA.SALE_PLCY_CD = SP.SALE_PLCY_CD
GROUP BY SP.SALE_PLCY_CD
,SP.SALE_PLCY_NM
,RA.PAY_CL_CD
,RA.DATA_TYPE
)ORDER BY SALE_PLCY_CD DESC
```


#### API_0122 — 정책정보로 약정할인 조회

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `selectUsimagrmTrmList` |
| 연동명(한글) | 정책정보로 약정할인 조회 |
| 엔드포인트 | `` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper |  |
| SQL ID | `` |
| parameterType | `` |
| resultType | `` |
| 사용 여부 | ❌ 미사용 |
| 대상 테이블 | MSP_SALE_AGRM_MST |
| 담당자 | 강문재 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `salePlcyCd` | 판매정책코드 | `String` | 판매정책코드 | SALE_PLCY_CD | MSP_SALE_AGRM_MST |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `salePlcyCd` | 판매정책코드 | `String` | 판매정책코드 | SALE_PLCY_CD | MSP_SALE_AGRM_MST |
| `agrmTrm` | 약정기간 | `String` | 약정기간 | AGRM_TRM | MSP_SALE_AGRM_MST |
| `regstId` | 등록자id | `String` | 등록자id | REGST_ID | MSP_SALE_AGRM_MST |
| `regstDttm` | 등록일시 | `Date` | 등록일시 | REGST_DTTM | MSP_SALE_AGRM_MST |
| `rvisnId` | 수정자id | `String` | 수정자id | RVISN_ID | MSP_SALE_AGRM_MST |
| `rvisnDttm` | 수정일시 | `Date` | 수정일시 | RVISN_DTTM | MSP_SALE_AGRM_MST |

**SQL**

```sql
SELECT
RA.SALE_PLCY_CD,
RA.AGRM_TRM,
RA.REGST_ID,
RA.REGST_DTTM,
RA.RVISN_ID,
RA.RVISN_DTTM
--ORG.INST_NOM
FROM
MSP_SALE_AGRM_MST@DL_MSP RA
WHERE
RA.SALE_PLCY_CD = #{salePlcyCd}
ORDER BY AGRM_TRM ASC
```


---

### PHONE 도메인 (8개)

#### API_0082 — 정책 판매중인 핸드폰 리스트 정보 조회 only msp db링크를 통한 조회

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `listPhoneMspDto` |
| 연동명(한글) | 정책 판매중인 핸드폰 리스트 정보 조회 only msp db링크를 통한 조회 |
| 엔드포인트 | `/phone/pHoneMspDto` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | PhoneMapper.xml |
| SQL ID | `listPhoneMspDto` |
| parameterType | `com.ktmmobile.mcp.phone.dto.CommonSearchDto` |
| resultType | `com.ktmmobile.mcp.phone.dto.PhoneMspDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | CMN_INTM_MDL, ORG_MNFCT_MST |
| 담당자 | 김대원 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `rprsYn` | 대표상품여부 | `String` | YN | RPRS_YN | CMN_INTM_MDL |
| `searchValue` | 검색값 | `String` | 검색값 | MNFCT_NM | ORG_MNFCT_MST |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `prdtId` | 제품ID | `String` | 제품ID | PRDT_ID | CMN_INTM_MDL |
| `rprsPrdtId` | 대표제품ID | `String` | 대표제품ID | RPRS_PRDT_ID | CMN_INTM_MDL |
| `rprsYn` | 대표제품 여부 | `String` | 대표제품 여부 | RPRS_YN | CMN_INTM_MDL |
| `prdtNm` | 제품명 | `String` | 제품명 | PRDT_NM | CMN_INTM_MDL |
| `prdtCode` | 제품코드 | `String` | 제품코드 | PRDT_CODE | CMN_INTM_MDL |
| `prdtTypeCd` | 제품유형코드 | `String` | 제품유형코드 | PRDT_TYPE_CD | CMN_INTM_MDL |
| `prdtIndCd` | 제품구분코드 | `String` | LTE:04, 3G:03 | PRDT_IND_CD | CMN_INTM_MDL |
| `prdtColrCd` | 제품색상코드 | `String` | 제품색상코드 | PRDT_COLR_CD | CMN_INTM_MDL |
| `mnfctId` | 제조사 ID | `String` | 제조사 ID | MNFCT_ID | CMN_INTM_MDL |
| `prdtLnchDt` | 제품 출시일자 | `String` | 제품 출시일자 | PRDT_LNCH_DT | CMN_INTM_MDL |
| `prdtDt` | 제품단종일자 | `String` | 제품단종일자 | PRDT_DT | CMN_INTM_MDL |
| `mnfctNm` | 제조사 명 | `String` | 제조사 명 | MNFCT_NM | ORG_MNFCT_MST |

**SQL**

```sql
SELECT
A.PRDT_ID,
A.RPRS_PRDT_ID,
A.RPRS_YN,
A.PRDT_NM,
A.PRDT_CODE,
A.PRDT_TYPE_CD,
A.PRDT_IND_CD,
A.PRDT_COLR_CD,
A.MNFCT_ID,
A.PRDT_LNCH_DT,
A.PRDT_DT,
C.MNFCT_NM
FROM
CMN_INTM_MDL@DL_MSP A,
ORG_MNFCT_MST@DL_MSP C
WHERE
A.PRDT_DT >= TO_CHAR(SYSDATE, 'YYYYMMDD')
AND A.MNFCT_ID = C.MNFCT_ID
AND C.MNFCT_YN = 'Y' --제조사 여부 N 일경우 공급사
<if test="rprsYn != null and rprsYn != ''">
AND A.RPRS_YN = #{rprsYn} -- 대표상품여부
</if>
<choose>
<when test="searchCategoryId == ''"> <!-- 전체 -->
AND  (
C.MNFCT_NM LIKE '%' || UPPER(#{searchValue}) || '%'
or UPPER(PRDT_CODE) LIKE '%' || UPPER(#{searchValue}) || '%'
or UPPER(PRDT_NM) LIKE '%' || UPPER(#{searchValue}) || '%'
)
</when>
<when test="searchCategoryId == '01'"> <!-- 제조사 -->
AND C.MNFCT_NM LIKE '%' || UPPER(#{searchValue}) || '%'
</when>
<when test="searchCategoryId == '02'"> <!-- 모델명 -->
AND UPPER(PRDT_CODE) LIKE '%' || UPPER(#{searchValue}) || '%'
</when>
<when test="searchCategoryId == '03'"> <!-- 상품명 -->
AND UPPER(PRDT_NM) LIKE '%' || UPPER(#{searchValue}) || '%'
</when>
<when test="searchCategoryId == '04'"> <!-- 대표모델ID -->
AND A.RPRS_PRDT_ID = #{searchValue}
</when>
</choose>
ORDER BY A.PRDT_IND_CD DESC
,A.MNFCT_ID
,A.RPRS_PRDT_ID
```


#### API_0083 — listPhoneProdsCount

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `listPhoneProdsCount` |
| 엔드포인트 | `/phone/phoneProdsCount` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | PhoneMapper.xml |
| SQL ID | `listPhoneProdsCount` |
| parameterType | `com.ktmmobile.mcp.phone.dto.CommonSearchDto` |
| resultType | `java.lang.Integer` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | ORG_MNFCT_MST |
| 담당자 | 김대원 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `shandYn` | 중고폰 여부 | `String` | YN | SHAND_YN | NMCP_PROD_BAS |
| `searchSaleYn` | 판매여부 | `String` | YN | SALE_YN | NMCP_PROD_BAS |
| `searchShowYn` | 노출여부 | `String` | YN | SHOW_YN | NMCP_PROD_BAS |
| `prodType` | 상품 분류 | `String` | 일반 :01, 0원 상품 :02 | PROD_TYPE | NMCP_PROD_BAS |
| `searchValue` | 검색값 | `String` | 검색값 | PROD_NM | NMCP_PROD_BAS |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `count` | 조회개수 | `int` | 조회개수 | COUNT |  |

**SQL**

```sql
SELECT /* Phone_Query.listPhoneProdsCount*/
COUNT(1)
FROM
NMCP_PROD_BAS A,
ORG_MNFCT_MST@DL_MSP B
WHERE
A.MAKR_CD = B.MNFCT_ID(+)
AND DEL_YN  <![CDATA[<>]]> 'Y'
AND B.MNFCT_YN(+) = 'Y' --제조사 여부 N 일경우 공급사
<if test="shandYn != null and shandYn != ''">
AND A.SHAND_YN = #{shandYn} --중고폰여부
</if>
<if test="searchSaleYn != null and searchSaleYn != ''">
AND A.SALE_YN = #{searchSaleYn}
</if>
<if test="searchShowYn != null and searchShowYn != ''">
AND A.SHOW_YN = #{searchShowYn}
</if>
<if test="prodType != null and prodType != ''">
AND A.PROD_TYPE = #{prodType}
</if>
<if test="searchValue != null and searchValue != ''">
<choose>
<when test="searchCategoryId == ''"> <!-- 전체 -->
AND  (
A.PROD_NM LIKE '%' ||#{searchValue}|| '%'
or B.MNFCT_NM LIKE '%' ||#{searchValue}|| '%'
or A.PROD_ID = #{searchValue}
)
</when>
<when test="searchCategoryId == '01'"> <!-- 상품명 -->
AND A.PROD_NM LIKE '%' ||#{searchValue}|| '%'
</when>
<when test="searchCategoryId == '02'"> <!-- 제조사 -->
<choose>
<when test='shandYn == "Y"'>
AND A.MAKR_NM LIKE '%' ||#{searchValue}|| '%'
</when>
<otherwise>
AND B.MNFCT_NM LIKE '%' ||#{searchValue}|| '%'
</otherwise>
</choose>
</when>
<when test="searchCategoryId == '03'"> <!-- 상품코드 -->
AND A.PROD_ID = #{searchValue}
</when>
</choose>
</if>
```


#### API_0084 — 핸드폰 상품관리 리스트 조회

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `listphoneProd` |
| 연동명(한글) | 핸드폰 상품관리 리스트 조회 |
| 엔드포인트 | `/phone/phoneProd` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | PhoneMapper.xml |
| SQL ID | `listphoneProd` |
| parameterType | `com.ktmmobile.mcp.phone.dto.CommonSearchDto` |
| resultType | `com.ktmmobile.mcp.phone.dto.PhoneProdBasDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | ORG_MNFCT_MST |
| 담당자 | 김대원 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `shandYn` | 중고폰 여부 | `String` | YN | SHAND_YN | NMCP_PROD_BAS |
| `searchSaleYn` | 판매여부 | `String` | YN | SALE_YN | NMCP_PROD_BAS |
| `searchShowYn` | 노출여부 | `String` | YN | SHOW_YN | NMCP_PROD_BAS |
| `prodType` | 상품 분류 | `String` | 일반 :01, 0원 상품 :02 | PROD_TYPE | NMCP_PROD_BAS |
| `searchValue` | 검색값 | `String` | 검색값 | PROD_NM | NMCP_PROD_BAS |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `prodId` | 상품아이디 | `String` | 상품아이디 | PROD_ID | NMCP_PROD_BAS |
| `prodId` | 상품 아이디 | `String` | 상품 아이디 | PROD_ID as prodId | NMCP_PROD_BAS |
| `prodCtgId` | 상품분류아이디 (lte,3g) | `String` | 상품분류아이디 (lte,3g) | PROD_CTG_ID | NMCP_PROD_BAS |
| `prodNm` | 상품명 | `String` | 상품명 | PROD_NM | NMCP_PROD_BAS |
| `makrCd` | 제조사코드 | `String` | 제조사코드 | MAKR_CD | NMCP_PROD_BAS |
| `rprsPrdtId` | 대표모델ID | `String` | 대표모델ID | RPRS_PRDT_ID | NMCP_PROD_BAS |
| `showYn` | 노출여부 | `String` | 노출여부 | SHOW_YN | NMCP_PROD_BAS |
| `saleYn` | 판매여부 | `String` | 판매여부 | SALE_YN | NMCP_PROD_BAS |
| `ofwDate` | 출고일자 | `String` | 출고일자 | OFW_DATE | NMCP_PROD_BAS |
| `listShowText` | 목록노출텍스트 | `String` | 목록노출텍스트 | LIST_SHOW_TEXT | NMCP_PROD_BAS |
| `listShowOptn` | 목록노출옵션 | `String` | 목록노출옵션 | LIST_SHOW_OPTN | NMCP_PROD_BAS |
| `apdDesc1` | 추가설명1 | `String` | 추가설명1 | APD_DESC_1 | NMCP_PROD_BAS |
| `apdDesc2` | 추가설명2 | `String` | 추가설명2 | APD_DESC_2 | NMCP_PROD_BAS |
| `apdDesc3` | 추가설명3 | `String` | 추가설명3 | APD_DESC_3 | NMCP_PROD_BAS |
| `stckTypeTop` | 스티커노출(상) | `String` | 스티커노출(상) | STCK_TYPE_TOP | NMCP_PROD_BAS |
| `stckTypeTail` | 스티커노출(하) | `String` | 스티커노출(하) | STCK_TYPE_TAIL | NMCP_PROD_BAS |
| `layerYn` | 레이어팝업 노출여부 | `String` | 레이어팝업 노출여부 | LAYER_YN | NMCP_PROD_BAS |
| `layerImageUrl` | 레이어이미지URL | `String` | 레이어이미지URL | LAYER_IMAGE_URL | NMCP_PROD_BAS |
| `showOdrg` | 노출순서 | `int` | 노출순서 | SHOW_ODRG | NMCP_PROD_BAS |
| `sntyProdDesc` | 단말기상품설명 | `String` | 단말기상품설명 | SNTY_PROD_DESC | NMCP_PROD_BAS |
| `sntyNet` | 단말기네트워크 | `String` | 단말기네트워크 | SNTY_NET | NMCP_PROD_BAS |
| `sntyDisp` | 단말기디스플레이 | `String` | 단말기디스플레이 | SNTY_DISP | NMCP_PROD_BAS |
| `sntySize` | 단말기크기 | `String` | 단말기크기 | SNTY_SIZE | NMCP_PROD_BAS |
| `sntyWeight` | 단말기무게 | `String` | 단말기무게 | SNTY_WEIGHT | NMCP_PROD_BAS |
| `sntyMemr` | 단말기메모리 | `String` | 단말기메모리 | SNTY_MEMR | NMCP_PROD_BAS |
| `sntyBtry` | 단말기배터리 | `String` | 단말기배터리 | SNTY_BTRY | NMCP_PROD_BAS |
| `sntyOs` | 단말기OS | `String` | 단말기OS | SNTY_OS | NMCP_PROD_BAS |
| `sntyWaitTime` | 단말기대기시간 | `String` | 단말기대기시간 | SNTY_WAIT_TIME | NMCP_PROD_BAS |
| `sntyCam` | 단말기카메라 | `String` | 단말기카메라 | SNTY_CAM | NMCP_PROD_BAS |
| `sntyVideTlk` | 단말기영상통화 | `String` | 단말기영상통화 | SNTY_VIDE_TLK | NMCP_PROD_BAS |
| `sntyProdNm` | 단말기명 | `String` | 단말기명 | SNTY_PROD_NM | NMCP_PROD_BAS |
| `sntyRelMonth` | 출시월 | `String` | 출시월 | SNTY_REL_MONTH | NMCP_PROD_BAS |
| `sntyColor` | 단말기색상 | `String` | 단말기색상 | SNTY_COLOR | NMCP_PROD_BAS |
| `sntyMaker` | 단말기제조사/브랜드명 | `String` | 단말기제조사/브랜드명 | SNTY_MAKER | NMCP_PROD_BAS |
| `sntyModelId` | 단말기모델번호 | `String` | 단말기모델번호 | SNTY_MODEL_ID | NMCP_PROD_BAS |
| `cretId` | 생성자아이디 | `String` | 생성자아이디 | CRET_ID | NMCP_PROD_BAS |
| `amdId` | 수정자아이디 | `String` | 수정자아이디 | AMD_ID | NMCP_PROD_BAS |
| `cretDt` | 생성일시 | `Date` | 생성일시 | CRET_DT | NMCP_PROD_BAS |
| `amdDt` | 수정일시 | `Date` | 수정일시 | AMD_DT | NMCP_PROD_BAS |
| `makrNm` | 제조사명 | `String` | 중고폰에서만사용 | MAKR_NM | NMCP_PROD_BAS |
| `inventoryAmt` | 중고폰 재고수량 | `int` | 중고폰 재고수량 | INVENTORY_AMT | NMCP_PROD_BAS |
| `salePrice` | 중고폰 판매가격 | `int` | 중고폰 판매가격 | SALE_PRICE | NMCP_PROD_BAS |
| `prodGrade` | 종고폰 등급 | `String` | 종고폰 등급 | PROD_GRADE | NMCP_PROD_BAS |
| `shandType` | 중고폰type 구분(중고폰,외산폰) | `String` | 중고폰type 구분(중고폰,외산폰) | SHAND_TYPE | NMCP_PROD_BAS |
| `recommendRate` | 추천요금제 | `String` | 추천요금제 | RECOMMEND_RATE | NMCP_PROD_BAS |
| `usedWarranty` | 중고폰 워런티 | `String` | 중고폰 워런티 | USED_WARRANTY | NMCP_PROD_BAS |
| `orderCretDt` | 상품순서 정렬용 생성일자 | `String` | -> 출시일로변경 | VL(A.OFW_DATE,'') as ORDER_CRET_DT | NMCP_PROD_BAS |
| `prodType` | 상품 분류 | `String` | 일반 :01, 0원 상품 :02 | PROD_TYPE | NMCP_PROD_BAS |
| `mnfctNm` | 제조사명 | `String` | 제조사명 | MNFCT_NM | ORG_MNFCT_MST |

**SQL**

```sql
SELECT
A.PROD_ID,                 --상품아이디
A.PROD_ID as prodId,       -- 상품 아이디
A.PROD_CTG_ID,            --상품분류아이디 (lte,3g)
A.PROD_NM,                --상품명
A.MAKR_CD,                --제조사코드
A.RPRS_PRDT_ID,           --대표모델ID
A.SHOW_YN,                --노출여부
A.SALE_YN,                --판매여부
A.OFW_DATE,               --출고일자
A.LIST_SHOW_TEXT,         --목록노출텍스트
A.LIST_SHOW_OPTN,         --목록노출옵션
A.APD_DESC_1,             --추가설명1
A.APD_DESC_2,             --추가설명2
A.APD_DESC_3,             --추가설명3
A.STCK_TYPE_TOP,          --스티커노출(상)
A.STCK_TYPE_TAIL,         --스티커노출(하)
A.LAYER_YN,               --레이어팝업 노출여부
A.LAYER_IMAGE_URL,        --레이어이미지URL
A.SHOW_ODRG,              --노출순서
A.SNTY_PROD_DESC,         --단말기상품설명
A.SNTY_NET,               --단말기네트워크
A.SNTY_DISP,              --단말기디스플레이
A.SNTY_SIZE,              --단말기크기
A.SNTY_WEIGHT,            --단말기무게
A.SNTY_MEMR,              --단말기메모리
A.SNTY_BTRY,              --단말기배터리
A.SNTY_OS,                --단말기OS
A.SNTY_WAIT_TIME,         --단말기대기시간
A.SNTY_CAM,               --단말기카메라
A.SNTY_VIDE_TLK,          --단말기영상통화
A.SNTY_PROD_NM,           --단말기명
A.SNTY_REL_MONTH,         --출시월
A.SNTY_COLOR,             --단말기색상
A.SNTY_MAKER,             --단말기제조사/브랜드명
A.SNTY_MODEL_ID,          --단말기모델번호
A.CRET_ID,                --생성자아이디
A.AMD_ID,                 --수정자아이디
A.CRET_DT,                --생성일시
A.AMD_DT,                 --수정일시
B.MNFCT_NM,               --제조사명
A.MAKR_NM,              --제조사명 중고폰에서만사용
A.INVENTORY_AMT,            --중고폰 재고수량
A.SALE_PRICE,               --중고폰 판매가격
A.PROD_GRADE,               --종고폰 등급
A.SHAND_TYPE,               --중고폰type 구분(중고폰,외산폰)
A.RECOMMEND_RATE,       --추천요금제
A.USED_WARRANTY,
NVL(A.OFW_DATE,'') as ORDER_CRET_DT,
A.PROD_TYPE				--상품구분 (01:일반 , 02:0원렌탈)
FROM
NMCP_PROD_BAS A,
ORG_MNFCT_MST@DL_MSP B
WHERE
A.MAKR_CD = B.MNFCT_ID(+)
AND DEL_YN  <![CDATA[<>]]> 'Y'
AND B.MNFCT_YN(+) = 'Y' --제조사 여부 N 일경우 공급사
<if test="shandYn != null and shandYn != ''">
AND A.SHAND_YN = #{shandYn} --중고폰여부
</if>
<if test="searchSaleYn != null and searchSaleYn != ''">
AND A.SALE_YN = #{searchSaleYn}
</if>
<if test="searchShowYn != null and searchShowYn != ''">
AND A.SHOW_YN = #{searchShowYn}
</if>
<choose>
<when test="prodType == '01'"> <!-- 전체 -->
AND A.PROD_TYPE  IN('01','04')
</when>
<when test="prodType != null and prodType != ''"> <!-- 상품명 -->
AND A.PROD_TYPE = #{prodType}
</when>
</choose>
<if test="searchValue != null and searchValue != ''">
<choose>
<when test="searchCategoryId == ''"> <!-- 전체 -->
AND  (
A.PROD_NM LIKE '%' ||#{searchValue}|| '%'
or B.MNFCT_NM LIKE '%' ||#{searchValue}|| '%'
or A.PROD_ID = #{searchValue}
)
</when>
<when test="searchCategoryId == '01'"> <!-- 상품명 -->
AND A.PROD_NM LIKE '%' ||#{searchValue}|| '%'
</when>
<when test="searchCategoryId == '02'"> <!-- 제조사 -->
<choose>
<when test='shandYn == "Y"'>
AND A.MAKR_NM LIKE '%' ||#{searchValue}|| '%'
</when>
<otherwise>
AND B.MNFCT_NM LIKE '%' ||#{searchValue}|| '%'
</otherwise>
</choose>
</when>
<when test="searchCategoryId == '03'"> <
-- ... (이하 생략)
```


#### API_0085 — 핸드폰 상품관리 상세 조회

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `findNmcpProdBas` |
| 연동명(한글) | 핸드폰 상품관리 상세 조회 |
| 엔드포인트 | `/phone/nmcpProdBas` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | PhoneMapper.xml |
| SQL ID | `findNmcpProdBas` |
| parameterType | `com.ktmmobile.mcp.phone.dto.PhoneProdBasDto` |
| resultType | `com.ktmmobile.mcp.phone.dto.PhoneProdBasDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | ORG_MNFCT_MST |
| 담당자 | 김대원 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `prodId` | 상품아이디 | `String` | 상품아이디 | PROD_ID | NMCP_PROD_BAS |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `prodId` | 상품아이디 | `String` | 상품아이디 | PROD_ID | NMCP_PROD_BAS |
| `prodId` | 상품 아이디 | `String` | 상품 아이디 | PROD_ID as prodId | NMCP_PROD_BAS |
| `prodCtgId` | 상품분류아이디 | `String` | 상품분류아이디 (lte,3g) | PROD_CTG_ID | NMCP_PROD_BAS |
| `prodNm` | 상품명 | `String` | 상품명 | PROD_NM | NMCP_PROD_BAS |
| `makrCd` | 제조사코드 | `String` | 제조사코드 | MAKR_CD | NMCP_PROD_BAS |
| `rprsPrdtId` | 대표모델ID | `String` | 대표모델ID | RPRS_PRDT_ID | NMCP_PROD_BAS |
| `showYn` | 노출여부 | `String` | 노출여부 | SHOW_YN | NMCP_PROD_BAS |
| `saleYn` | 판매여부 | `String` | 판매여부 | SALE_YN | NMCP_PROD_BAS |
| `ofwDate` | 출고일자 | `String` | 출고일자 | OFW_DATE | NMCP_PROD_BAS |
| `listShowText` | 목록노출텍스트 | `String` | 목록노출텍스트 | LIST_SHOW_TEXT | NMCP_PROD_BAS |
| `listShowOptn` | 목록노출옵션 | `String` | 목록노출옵션 | LIST_SHOW_OPTN | NMCP_PROD_BAS |
| `apdDesc1` | 추가설명1 | `String` | 추가설명1 | APD_DESC_1 | NMCP_PROD_BAS |
| `apdDesc2` | 추가설명2 | `String` | 추가설명2 | APD_DESC_2 | NMCP_PROD_BAS |
| `apdDesc3` | 추가설명3 | `String` | 추가설명3 | APD_DESC_3 | NMCP_PROD_BAS |
| `stckTypeTop` | 스티커노출(상) | `String` | 스티커노출(상) | STCK_TYPE_TOP | NMCP_PROD_BAS |
| `stckTypeTail` | 스티커노출(하) | `String` | 스티커노출(하) | STCK_TYPE_TAIL | NMCP_PROD_BAS |
| `layerYn` | 레이어팝업 노출여부 | `String` | 레이어팝업 노출여부 | LAYER_YN | NMCP_PROD_BAS |
| `layerImageUrl` | 레이어이미지URL | `String` | 레이어이미지URL | LAYER_IMAGE_URL | NMCP_PROD_BAS |
| `showOdrg` | 노출순서 | `int` | 노출순서 | SHOW_ODRG | NMCP_PROD_BAS |
| `sntyProdDesc` | 단말기상품설명 | `String` | 단말기상품설명 | SNTY_PROD_DESC | NMCP_PROD_BAS |
| `sntyNet` | 단말기네트워크 | `String` | 단말기네트워크 | SNTY_NET | NMCP_PROD_BAS |
| `sntyDisp` | 단말기디스플레이 | `String` | 단말기디스플레이 | SNTY_DISP | NMCP_PROD_BAS |
| `sntySize` | 단말기크기 | `String` | 단말기크기 | SNTY_SIZE | NMCP_PROD_BAS |
| `sntyWeight` | 단말기무게 | `String` | 단말기무게 | SNTY_WEIGHT | NMCP_PROD_BAS |
| `sntyMemr` | 단말기메모리 | `String` | 단말기메모리 | SNTY_MEMR | NMCP_PROD_BAS |
| `sntyBtry` | 단말기배터리 | `String` | 단말기배터리 | SNTY_BTRY | NMCP_PROD_BAS |
| `sntyOs` | 단말기OS | `String` | 단말기OS | SNTY_OS | NMCP_PROD_BAS |
| `sntyWaitTime` | 단말기대기시간 | `String` | 단말기대기시간 | SNTY_WAIT_TIME | NMCP_PROD_BAS |
| `sntyCam` | 단말기카메라 | `String` | 단말기카메라 | SNTY_CAM | NMCP_PROD_BAS |
| `sntyVideTlk` | 단말기영상통화 | `String` | 단말기영상통화 | SNTY_VIDE_TLK | NMCP_PROD_BAS |
| `sntyProdNm` | 단말기명 | `String` | 단말기명 | SNTY_PROD_NM | NMCP_PROD_BAS |
| `sntyRelMonth` | 출시월 | `String` | 출시월 | SNTY_REL_MONTH | NMCP_PROD_BAS |
| `sntyColor` | 단말기색상 | `String` | 단말기색상 | SNTY_COLOR | NMCP_PROD_BAS |
| `sntyMaker` | 단말기제조사/브랜드명 | `String` | 단말기제조사/브랜드명 | SNTY_MAKER | NMCP_PROD_BAS |
| `sntyModelId` | 단말기모델번호 | `String` | 단말기모델번호 | SNTY_MODEL_ID | NMCP_PROD_BAS |
| `cretId` | 생성자아이디 | `String` | 생성자아이디 | CRET_ID | NMCP_PROD_BAS |
| `amdId` | 수정자아이디 | `String` | 수정자아이디 | AMD_ID | NMCP_PROD_BAS |
| `cretDt` | 생성일시 | `Date` | 생성일시 | CRET_DT | NMCP_PROD_BAS |
| `amdDt` | 수정일시 | `Date` | 수정일시 | AMD_DT | NMCP_PROD_BAS |
| `mnfctNm` | 제조사명 | `String` | 제조사명 | MNFCT_NM | ORG_MNFCT_MST |
| `sntyDetailSpec` | 휴대폰상세기능 | `String` | 휴대폰상세기능 | SNTY_DETAIL_SPEC | NMCP_PROD_BAS |
| `makrNm` | 제조사명 중고폰에서만사용 | `String` | 제조사명 중고폰에서만사용 | MAKR_NM | NMCP_PROD_BAS |
| `inventoryAmt` | 중고폰 재고수량 | `int` | 중고폰 재고수량 | INVENTORY_AMT | NMCP_PROD_BAS |
| `salePrice` | 중고폰 판매가격 | `int` | 중고폰 판매가격 | SALE_PRICE | NMCP_PROD_BAS |
| `prodGrade` | 종고폰 등급 | `String` | 종고폰 등급 | PROD_GRADE | NMCP_PROD_BAS |
| `shandType` | 중고폰type 구분 | `String` | (중고폰,외산폰) | SHAND_TYPE | NMCP_PROD_BAS |
| `recommendRate` | 추천요금제 | `String` | 추천요금제 | RECOMMEND_RATE | NMCP_PROD_BAS |
| `usedWarranty` | 중고폰 워런티 | `String` | 중고폰 워런티 | USED_WARRANTY | NMCP_PROD_BAS |
| `orderCretDt` | 상품순서 정렬용 생성일자 | `String` | -> 출시일로변경 | NVL(A.OFW_DATE,'') as ORDER_CRET_DT | NMCP_PROD_BAS |
| `rinkUrl` | 링크url | `String` | 링크url | RINK_URL | NMCP_PROD_BAS |
| `prodType` | 상품 분류 | `String` | 일반 :01, 0원 상품 :02 | PROD_TYPE | NMCP_PROD_BAS |
| `rentalBaseAmt` | 렌탈 기본료 금액 | `int` | 렌탈 기본료 금액 | RENTAL_BASE_AMT | NMCP_PROD_BAS |
| `rentalBaseDcAmt` | 렌탈 기본료 할인 금액 | `int` | 렌탈 기본료 할인 금액 | RENTAL_BASE_DC_AMT | NMCP_PROD_BAS |
| `rentalModelCpAmt` | 단말기 배상 금액 | `int` | 단말기 배상 금액 | RENTAL_MODEL_CP_AMT | NMCP_PROD_BAS |

**SQL**

```sql
SELECT
A.PROD_ID,                 --상품아이디
A.PROD_ID as prodId,       -- 상품 아이디
A.PROD_CTG_ID,            --상품분류아이디 (lte,3g)
A.PROD_NM,                --상품명
A.MAKR_CD,                --제조사코드
A.RPRS_PRDT_ID,           --대표모델ID
A.SHOW_YN,                --노출여부
A.SALE_YN,                --판매여부
A.OFW_DATE,               --출고일자
A.LIST_SHOW_TEXT,         --목록노출텍스트
A.LIST_SHOW_OPTN,         --목록노출옵션
A.APD_DESC_1,             --추가설명1
A.APD_DESC_2,             --추가설명2
A.APD_DESC_3,             --추가설명3
A.STCK_TYPE_TOP,          --스티커노출(상)
A.STCK_TYPE_TAIL,         --스티커노출(하)
A.LAYER_YN,               --레이어팝업 노출여부
A.LAYER_IMAGE_URL,        --레이어이미지URL
A.SHOW_ODRG,              --노출순서
A.SNTY_PROD_DESC,         --단말기상품설명
A.SNTY_NET,               --단말기네트워크
A.SNTY_DISP,              --단말기디스플레이
A.SNTY_SIZE,              --단말기크기
A.SNTY_WEIGHT,            --단말기무게
A.SNTY_MEMR,              --단말기메모리
A.SNTY_BTRY,              --단말기배터리
A.SNTY_OS,                --단말기OS
A.SNTY_WAIT_TIME,         --단말기대기시간
A.SNTY_CAM,               --단말기카메라
A.SNTY_VIDE_TLK,          --단말기영상통화
A.SNTY_PROD_NM,           --단말기명
A.SNTY_REL_MONTH,         --출시월
A.SNTY_COLOR,             --단말기색상
A.SNTY_MAKER,             --단말기제조사/브랜드명
A.SNTY_MODEL_ID,          --단말기모델번호
A.CRET_ID,                --생성자아이디
A.AMD_ID,                 --수정자아이디
A.CRET_DT,                --생성일시
A.AMD_DT,                 --수정일시
B.MNFCT_NM,               --제조사명
A.SNTY_DETAIL_SPEC,       --휴대폰상세기능
A.MAKR_NM,              --제조사명 중고폰에서만사용
A.INVENTORY_AMT,            --중고폰 재고수량
A.SALE_PRICE,               --중고폰 판매가격
A.PROD_GRADE,               --종고폰 등급
A.SHAND_TYPE,               --중고폰type 구분(중고폰,외산폰)
A.RECOMMEND_RATE,       --추천요금제
A.USED_WARRANTY,
NVL(A.OFW_DATE,'') as ORDER_CRET_DT,
A.RINK_URL,				--링크유알엘
A.PROD_TYPE,			--상품 분류 (일반 :01, 0원 상품 :02)
A.RENTAL_BASE_AMT,		--렌탈 기본료 금액
A.RENTAL_BASE_DC_AMT,	--렌탈 기본료 할인 금액
A.RENTAL_MODEL_CP_AMT	-- 단말기 배상 금액
FROM
NMCP_PROD_BAS A,
ORG_MNFCT_MST@DL_MSP B
WHERE
A.MAKR_CD = B.MNFCT_ID(+)
AND B.MNFCT_YN(+) = 'Y' --제조사 여부 N 일경우 공급사
AND PROD_ID = #{prodId}
```


#### API_0086 — 핸드폰 상품순서관리

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `listphoneProdForSort` |
| 연동명(한글) | 핸드폰 상품순서관리 |
| 엔드포인트 | `/phone/phoneProdForSort` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | PhoneMapper.xml |
| SQL ID | `listphoneProdForSort` |
| parameterType | `com.ktmmobile.mcp.phone.dto.CommonSearchDto` |
| resultType | `com.ktmmobile.mcp.phone.dto.PhoneProdBasDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | ORG_MNFCT_MST |
| 담당자 | 김대원 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `shandYn` | 중고폰 여부 | `String` | 중고폰 여부 | SHAND_YN | NMCP_PROD_BAS |
| `saleYn` | 판매여부 | `String` | YN | SALE_YN | NMCP_PROD_BAS |
| `searchValue` | 검색값 | `String` | 검색값 | PROD_NM | NMCP_PROD_BAS |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `prodId` | 상품아이디 | `String` | 상품아이디 | PROD_ID | NMCP_PROD_BAS |
| `prodId` | 상품 아이디 | `String` | 상품 아이디 | PROD_ID as prodId | NMCP_PROD_BAS |
| `prodCtgId` | 상품분류아이디 | `String` | 상품분류아이디 (lte,3g) | PROD_CTG_ID | NMCP_PROD_BAS |
| `prodNm` | 상품명 | `String` | 상품명 | PROD_NM | NMCP_PROD_BAS |
| `makrCd` | 제조사코드 | `String` | 제조사코드 | MAKR_CD | NMCP_PROD_BAS |
| `rprsPrdtId` | 대표모델ID | `String` | 대표모델ID | RPRS_PRDT_ID | NMCP_PROD_BAS |
| `showYn` | 노출여부 | `String` | 노출여부 | SHOW_YN | NMCP_PROD_BAS |
| `saleYn` | 판매여부 | `String` | 판매여부 | SALE_YN | NMCP_PROD_BAS |
| `ofwDate` | 출고일자 | `String` | 출고일자 | OFW_DATE | NMCP_PROD_BAS |
| `listShowText` | 목록노출텍스트 | `String` | 목록노출텍스트 | LIST_SHOW_TEXT | NMCP_PROD_BAS |
| `listShowOptn` | 목록노출옵션 | `String` | 목록노출옵션 | LIST_SHOW_OPTN | NMCP_PROD_BAS |
| `apdDesc1` | 추가설명1 | `String` | 추가설명1 | APD_DESC_1 | NMCP_PROD_BAS |
| `apdDesc2` | 추가설명2 | `String` | 추가설명2 | APD_DESC_2 | NMCP_PROD_BAS |
| `apdDesc3` | 추가설명3 | `String` | 추가설명3 | APD_DESC_3 | NMCP_PROD_BAS |
| `stckTypeTop` | 스티커노출(상) | `String` | 스티커노출(상) | STCK_TYPE_TOP | NMCP_PROD_BAS |
| `stckTypeTail` | 스티커노출(하) | `String` | 스티커노출(하) | STCK_TYPE_TAIL | NMCP_PROD_BAS |
| `layerYn` | 레이어팝업 노출여부 | `String` | 레이어팝업 노출여부 | LAYER_YN | NMCP_PROD_BAS |
| `layerImageUrl` | 레이어이미지URL | `String` | 레이어이미지URL | LAYER_IMAGE_URL | NMCP_PROD_BAS |
| `showOdrg` | 노출순서 | `int` | 노출순서 | SHOW_ODRG | NMCP_PROD_BAS |
| `sntyProdDesc` | 단말기상품설명 | `String` | 단말기상품설명 | SNTY_PROD_DESC | NMCP_PROD_BAS |
| `sntyNet` | 단말기네트워크 | `String` | 단말기네트워크 | SNTY_NET | NMCP_PROD_BAS |
| `sntyDisp` | 단말기디스플레이 | `String` | 단말기디스플레이 | SNTY_DISP | NMCP_PROD_BAS |
| `sntySize` | 단말기크기 | `String` | 단말기크기 | SNTY_SIZE | NMCP_PROD_BAS |
| `sntyWeight` | 단말기무게 | `String` | 단말기무게 | SNTY_WEIGHT | NMCP_PROD_BAS |
| `sntyMemr` | 단말기메모리 | `String` | 단말기메모리 | SNTY_MEMR | NMCP_PROD_BAS |
| `sntyBtry` | 단말기배터리 | `String` | 단말기배터리 | SNTY_BTRY | NMCP_PROD_BAS |
| `sntyOs` | 단말기OS | `String` | 단말기OS | SNTY_OS | NMCP_PROD_BAS |
| `sntyWaitTime` | 단말기대기시간 | `String` | 단말기대기시간 | SNTY_WAIT_TIME | NMCP_PROD_BAS |
| `sntyCam` | 단말기카메라 | `String` | 단말기카메라 | SNTY_CAM | NMCP_PROD_BAS |
| `sntyVideTlk` | 단말기영상통화 | `String` | 단말기영상통화 | SNTY_VIDE_TLK | NMCP_PROD_BAS |
| `sntyProdNm` | 단말기명 | `String` | 단말기명 | SNTY_PROD_NM | NMCP_PROD_BAS |
| `sntyRelMonth` | 출시월 | `String` | 출시월 | SNTY_REL_MONTH | NMCP_PROD_BAS |
| `sntyColor` | 단말기색상 | `String` | 단말기색상 | SNTY_COLOR | NMCP_PROD_BAS |
| `sntyMaker` | 단말기제조사/브랜드명 | `String` | 단말기제조사/브랜드명 | SNTY_MAKER | NMCP_PROD_BAS |
| `sntyModelId` | 단말기모델번호 | `String` | 단말기모델번호 | SNTY_MODEL_ID | NMCP_PROD_BAS |
| `cretId` | 생성자아이디 | `String` | 생성자아이디 | CRET_ID | NMCP_PROD_BAS |
| `amdId` | 수정자아이디 | `String` | 수정자아이디 | AMD_ID | NMCP_PROD_BAS |
| `cretDt` | 생성일시 | `Date` | 생성일시 | CRET_DT | NMCP_PROD_BAS |
| `amdDt` | 수정일시 | `Date` | 수정일시 | AMD_DT | NMCP_PROD_BAS |
| `mnfctNm` | 제조사명 | `String` | 제조사명 | MNFCT_NM | ORG_MNFCT_MST |
| `recommendRate` | 추천요금제 | `String` | 추천요금제 | RECOMMEND_RATE | NMCP_PROD_BAS |
| `usedWarranty` | 중고폰 워런티 | `String` | 중고폰 워런티 | USED_WARRANTY | NMCP_PROD_BAS |
| `orderCretDt` | 상품순서 정렬용 생성일자 | `String` | -> 출시일로변경 | NVL(A.OFW_DATE,'') as ORDER_CRET_DT | NMCP_PROD_BAS |

**SQL**

```sql
SELECT
A.PROD_ID,                 --상품아이디
A.PROD_ID as prodId,       -- 상품 아이디
A.PROD_CTG_ID,            --상품분류아이디 (lte,3g)
A.PROD_NM,                --상품명
A.MAKR_CD,                --제조사코드
A.RPRS_PRDT_ID,           --대표모델ID
A.SHOW_YN,                --노출여부
A.SALE_YN,                --판매여부
A.OFW_DATE,               --출고일자
A.LIST_SHOW_TEXT,         --목록노출텍스트
A.LIST_SHOW_OPTN,         --목록노출옵션
A.APD_DESC_1,             --추가설명1
A.APD_DESC_2,             --추가설명2
A.APD_DESC_3,             --추가설명3
A.STCK_TYPE_TOP,          --스티커노출(상)
A.STCK_TYPE_TAIL,         --스티커노출(하)
A.LAYER_YN,               --레이어팝업 노출여부
A.LAYER_IMAGE_URL,        --레이어이미지URL
A.SHOW_ODRG,              --노출순서
A.SNTY_PROD_DESC,         --단말기상품설명
A.SNTY_NET,               --단말기네트워크
A.SNTY_DISP,              --단말기디스플레이
A.SNTY_SIZE,              --단말기크기
A.SNTY_WEIGHT,            --단말기무게
A.SNTY_MEMR,              --단말기메모리
A.SNTY_BTRY,              --단말기배터리
A.SNTY_OS,                --단말기OS
A.SNTY_WAIT_TIME,         --단말기대기시간
A.SNTY_CAM,               --단말기카메라
A.SNTY_VIDE_TLK,          --단말기영상통화
A.SNTY_PROD_NM,           --단말기명
A.SNTY_REL_MONTH,         --출시월
A.SNTY_COLOR,             --단말기색상
A.SNTY_MAKER,             --단말기제조사/브랜드명
A.SNTY_MODEL_ID,          --단말기모델번호
A.CRET_ID,                --생성자아이디
A.AMD_ID,                 --수정자아이디
A.CRET_DT,                --생성일시
A.AMD_DT,                 --수정일시
B.MNFCT_NM,               --제조사명
A.RECOMMEND_RATE,           --추천요금제
A.USED_WARRANTY,            --중고폰 워런티
NVL(A.OFW_DATE,'') as ORDER_CRET_DT
FROM
NMCP_PROD_BAS A,
ORG_MNFCT_MST@DL_MSP B
WHERE
A.MAKR_CD = B.MNFCT_ID(+)
AND B.MNFCT_YN(+) = 'Y' --제조사 여부 N 일경우 공급사
AND A.SHOW_YN = 'Y' --노출여부 Y/N
AND A.SALE_YN = 'Y'
AND A.SHAND_YN = #{shandYn} --중고폰여부
<if test="saleYn != null and saleYn != ''">
AND A.SALE_YN = #{saleYn}
</if>
<if test="searchValue != null and searchValue != ''">
<choose>
<when test="searchCategoryId == ''"> <!-- 전체 -->
AND  (
A.PROD_NM LIKE '%' ||#{searchValue}|| '%'
or B.MNFCT_NM LIKE '%' ||#{searchValue}|| '%'
or A.PROD_ID = #{searchValue}
)
</when>
<when test="searchCategoryId == '01'"> <!-- 상품명 -->
AND A.PROD_NM LIKE '%' ||#{searchValue}|| '%'
</when>
<when test="searchCategoryId == '02'"> <!-- 제조사 -->
AND B.MNFCT_NM LIKE '%' ||#{searchValue}|| '%'
</when>
<when test="searchCategoryId == '03'"> <!-- 상품코드 -->
AND A.PROD_ID = #{searchValue}
</when>
</choose>
</if>
ORDER BY SHOW_ODRG
```


#### API_0087 — 핸드폰 상품 리스트 프론트

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `listPhoneProdBasForFront` |
| 연동명(한글) | 핸드폰 상품 리스트 프론트 |
| 엔드포인트 | `/phone/phoneProdBasForFront` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | PhoneMapper.xml |
| SQL ID | `listPhoneProdBasForFront` |
| parameterType | `com.ktmmobile.mcp.phone.dto.CommonSearchDto` |
| resultType | `com.ktmmobile.mcp.phone.dto.PhoneProdBasDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | ORG_MNFCT_MST |
| 담당자 | 김대원 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `shandYn` | 중고폰 여부 YN | `String` | LTE:04,3G:03 | SHAND_YN | NMCP_PROD_BAS |
| `prodCtgId` | 핸드폰 type | `String` | 핸드폰 type | PROD_CTG_ID | NMCP_PROD_BAS |
| `makrCd` | 제조사 id | `String` | 제조사 id | MNFCT_ID | ORG_MNFCT_MST |
| `shandType` | 중고폰분류값 | `String` | 중고폰:01,외산폰:02 | SHAND_TYPE | NMCP_PROD_BAS |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `prodId` | 상품아이디 | `String` | 상품아이디 | PROD_ID | NMCP_PROD_BAS |
| `prodId` | 상품 아이디 | `String` | 상품 아이디 | PROD_ID as prodId | NMCP_PROD_BAS |
| `prodCtgId` | 상품분류아이디 (lte,3g) | `String` | 상품분류아이디 (lte,3g) | PROD_CTG_ID | NMCP_PROD_BAS |
| `prodNm` | 상품명 | `String` | 상품명 | PROD_NM | NMCP_PROD_BAS |
| `makrCd` | 제조사코드 | `String` | 제조사코드 | MAKR_CD | NMCP_PROD_BAS |
| `rprsPrdtId` | 대표모델ID | `String` | 대표모델ID | RPRS_PRDT_ID | NMCP_PROD_BAS |
| `showYn` | 노출여부 | `String` | 노출여부 | SHOW_YN | NMCP_PROD_BAS |
| `saleYn` | 판매여부 | `String` | 판매여부 | SALE_YN | NMCP_PROD_BAS |
| `ofwDate` | 출고일자 | `String` | 출고일자 | OFW_DATE | NMCP_PROD_BAS |
| `listShowText` | 목록노출텍스트 | `String` | 목록노출텍스트 | LIST_SHOW_TEXT | NMCP_PROD_BAS |
| `listShowOptn` | 목록노출옵션 | `String` | 목록노출옵션 | LIST_SHOW_OPTN | NMCP_PROD_BAS |
| `apdDesc1` | 추가설명1 | `String` | 추가설명1 | APD_DESC_1 | NMCP_PROD_BAS |
| `apdDesc2` | 추가설명2 | `String` | 추가설명2 | APD_DESC_2 | NMCP_PROD_BAS |
| `apdDesc3` | 추가설명3 | `String` | 추가설명3 | APD_DESC_3 | NMCP_PROD_BAS |
| `stckTypeTop` | 스티커노출(상) | `String` | 01:BEST ,02:NEW, 03:신상 , 04:특가, 05:인기상품 | STCK_TYPE_TOP | NMCP_PROD_BAS |
| `stckTypeTail` | 스티커노출(하) | `String` | 01:최대공시금지원, 02:공시지원금확대, 03:이벤트, 04:국내유일 | STCK_TYPE_TAIL | NMCP_PROD_BAS |
| `layerYn` | 레이어팝업 노출여부 | `String` | 레이어팝업 노출여부 | LAYER_YN | NMCP_PROD_BAS |
| `layerImageUrl` | 레이어이미지URL | `String` | 레이어이미지URL | LAYER_IMAGE_URL | NMCP_PROD_BAS |
| `showOdrg` | 노출순서 | `int` | 노출순서 | SHOW_ODRG | NMCP_PROD_BAS |
| `sntyProdDesc` | 단말기상품설명 | `String` | 단말기상품설명 | SNTY_PROD_DESC | NMCP_PROD_BAS |
| `sntyNet` | 단말기네트워크 | `String` | 단말기네트워크 | SNTY_NET | NMCP_PROD_BAS |
| `sntyDisp` | 단말기디스플레이 | `String` | 단말기디스플레이 | SNTY_DISP | NMCP_PROD_BAS |
| `sntySize` | 단말기크기 | `String` | 단말기크기 | SNTY_SIZE | NMCP_PROD_BAS |
| `sntyWeight` | 단말기무게 | `String` | 단말기무게 | SNTY_WEIGHT | NMCP_PROD_BAS |
| `sntyMemr` | 단말기메모리 | `String` | 단말기메모리 | SNTY_MEMR | NMCP_PROD_BAS |
| `sntyBtry` | 단말기배터리 | `String` | 단말기배터리 | SNTY_BTRY | NMCP_PROD_BAS |
| `sntyOs` | 단말기OS | `String` | 단말기OS | SNTY_OS | NMCP_PROD_BAS |
| `sntyWaitTime` | 단말기대기시간 | `String` | 단말기대기시간 | SNTY_WAIT_TIME | NMCP_PROD_BAS |
| `sntyCam` | 단말기카메라 | `String` | 단말기카메라 | SNTY_CAM | NMCP_PROD_BAS |
| `sntyVideTlk` | 단말기영상통화 | `String` | 단말기영상통화 | SNTY_VIDE_TLK | NMCP_PROD_BAS |
| `sntyProdNm` | 단말기명 | `String` | 단말기명 | SNTY_PROD_NM | NMCP_PROD_BAS |
| `sntyRelMonth` | 출시월 | `String` | 출시월 | SNTY_REL_MONTH | NMCP_PROD_BAS |
| `sntyColor` | 단말기색상 | `String` | 단말기색상 | SNTY_COLOR | NMCP_PROD_BAS |
| `sntyMaker` | 단말기제조사/브랜드명 | `String` | 단말기제조사/브랜드명 | SNTY_MAKER | NMCP_PROD_BAS |
| `sntyModelId` | 단말기모델번호 | `String` | 단말기모델번호 | SNTY_MODEL_ID | NMCP_PROD_BAS |
| `cretId` | 생성자아이디 | `String` | 생성자아이디 | CRET_ID | NMCP_PROD_BAS |
| `amdId` | 수정자아이디 | `String` | 수정자아이디 | AMD_ID | NMCP_PROD_BAS |
| `cretDt` | 생성일시 | `Date` | 생성일시 | CRET_DT | NMCP_PROD_BAS |
| `amdDt` | 수정일시 | `Date` | 수정일시 | AMD_DT | NMCP_PROD_BAS |
| `sntyDetailSpec` | 휴대폰상세기능 | `String` | 휴대폰상세기능 | SNTY_DETAIL_SPEC | NMCP_PROD_BAS |
| `makrNm` | 제조사명 | `String` | 제조사명 | MAKR_NM | NMCP_PROD_BAS |
| `inventoryAmt` | 중고폰 재고수량 | `int` | 중고폰 재고수량 | INVENTORY_AMT | NMCP_PROD_BAS |
| `salePrice` | 중고폰 판매가격 | `int` | 중고폰 판매가격 | SALE_PRICE | NMCP_PROD_BAS |
| `prodGrade` | 상품등급 | `String` | 상품등급 | PROD_GRADE | NMCP_PROD_BAS |
| `shandType` | 중고폰type 구분 | `String` | (중고폰,외산폰) | SHAND_TYPE | NMCP_PROD_BAS |
| `recommendRate` | 추천요금제 | `String` | 추천요금제 | RECOMMEND_RATE | NMCP_PROD_BAS |
| `mnfctNm` | 제조사명 | `String` | 제조사명 | MNFCT_NM | ORG_MNFCT_MST |
| `usedWarranty` | 중고폰 워런티 | `String` | 중고폰 워런티 | USED_WARRANTY | NMCP_PROD_BAS |
| `orderCretDt` | 상품순서 정렬용 생성일자 | `String` | -> 출시일로변경 | NVL(A.OFW_DATE,'') as ORDER_CRET_DT | NMCP_PROD_BAS |
| `rinkUrl` | 중고폰 링크URL | `String` | 중고폰 링크URL | RINK_URL | NMCP_PROD_BAS |

**SQL**

```sql
SELECT
A.PROD_ID,                 --상품아이디
A.PROD_ID as prodId,       -- 상품 아이디
A.PROD_CTG_ID,            --상품분류아이디 (lte,3g)
A.PROD_NM,                --상품명
A.MAKR_CD,                --제조사코드
A.RPRS_PRDT_ID,           --대표모델ID
A.SHOW_YN,                --노출여부
A.SALE_YN,                --판매여부
A.OFW_DATE,               --출고일자
A.LIST_SHOW_TEXT,         --목록노출텍스트
A.LIST_SHOW_OPTN,         --목록노출옵션
A.APD_DESC_1,             --추가설명1
A.APD_DESC_2,             --추가설명2
A.APD_DESC_3,             --추가설명3
A.STCK_TYPE_TOP,          --스티커노출(상)
A.STCK_TYPE_TAIL,         --스티커노출(하)
A.LAYER_YN,               --레이어팝업 노출여부
A.LAYER_IMAGE_URL,        --레이어이미지URL
A.SHOW_ODRG,              --노출순서
A.SNTY_PROD_DESC,         --단말기상품설명
A.SNTY_NET,               --단말기네트워크
A.SNTY_DISP,              --단말기디스플레이
A.SNTY_SIZE,              --단말기크기
A.SNTY_WEIGHT,            --단말기무게
A.SNTY_MEMR,              --단말기메모리
A.SNTY_BTRY,              --단말기배터리
A.SNTY_OS,                --단말기OS
A.SNTY_WAIT_TIME,         --단말기대기시간
A.SNTY_CAM,               --단말기카메라
A.SNTY_VIDE_TLK,          --단말기영상통화
A.SNTY_PROD_NM,           --단말기명
A.SNTY_REL_MONTH,         --출시월
A.SNTY_COLOR,             --단말기색상
A.SNTY_MAKER,             --단말기제조사/브랜드명
A.SNTY_MODEL_ID,          --단말기모델번호
A.CRET_ID,                --생성자아이디
A.AMD_ID,                 --수정자아이디
A.CRET_DT,                --생성일시
A.AMD_DT,                 --수정일시
A.SNTY_DETAIL_SPEC,       --휴대폰상세기능
A.MAKR_NM,                --제조사명 중고폰에서만사용
A.INVENTORY_AMT,          --중고폰 재고수량
A.SALE_PRICE,             --중고폰 판매가격
A.PROD_GRADE,             --종고폰 등급
A.SHAND_TYPE,             --중고폰type 구분(중고폰,외산폰)
A.RECOMMEND_RATE,         --추천요금제
B.MNFCT_NM,               --제조사명
A.USED_WARRANTY,          --중고폰 워런티
NVL(A.OFW_DATE,'') as ORDER_CRET_DT ,
A.RINK_URL                -- 중고폰 링크URL
FROM
NMCP_PROD_BAS A,
ORG_MNFCT_MST@DL_MSP B
WHERE
A.MAKR_CD = B.MNFCT_ID(+)
AND B.MNFCT_YN(+) = 'Y' --제조사 여부 N 일경우 공급사
AND A.SHOW_YN = 'Y' --노출여부 Y/N
AND A.SHAND_YN = #{shandYn} --중고폰여부
AND A.SALE_YN = 'Y' --판매여부
<if test="prodCtgId != null and prodCtgId != ''">
AND A.PROD_CTG_ID = #{prodCtgId}
</if>
<if test="makrCd != null and makrCd != ''">
AND B.MNFCT_ID = #{makrCd}
</if>
<if test="shandType!= null and shandType != ''">
AND A.SHAND_TYPE = #{shandType}
</if>
ORDER BY SHOW_ODRG
```


#### API_0088 — 상품리스트 조회

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `listPhoneProdBasForFrontOneQuery` |
| 연동명(한글) | 상품리스트 조회 |
| 엔드포인트 | `/phone/phoneProdBasForFrontOneQuery` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | PhoneMapper.xml |
| SQL ID | `listPhoneProdBasForFrontOneQuery` |
| parameterType | `com.ktmmobile.mcp.phone.dto.CommonSearchDto` |
| resultType | `com.ktmmobile.mcp.phone.dto.PhoneProdBasForFrontListDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | ORG_MNFCT_MST, MSP_SALE_PRDT_MST |
| 담당자 | 김대원 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `prdt.salePlcyCd` | 정책 리스트 | `List<String>` | 상품리스트에서 정책정보가 존재하는 상품만 끓어오기위한 정책정보 리스트 | SALE_PLCY_CD | MSP_SALE_PRDT_MST |
| `shandYn` | 중고폰 여부 YN | `String` | LTE:04,3G:03 | SHAND_YN | NMCP_PROD_BAS |
| `prodCtgId` | 핸드폰 type | `String` | 핸드폰 type | PROD_CTG_ID | NMCP_PROD_BAS |
| `makrCd` | 제조사 id | `String` | 제조사 id | MAKR_CD | NMCP_PROD_BAS |
| `prodType` | 상품 분류 | `String` | 일반 :01, 0원 상품 :02 | PROD_TYPE | NMCP_PROD_BAS |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `prodId` | 상품아이디 | `String` | 상품아이디 | PROD_ID | NMCP_PROD_BAS |
| `prodId` | 상품 아이디 | `String` | 상품 아이디 | PROD_ID as prodId | NMCP_PROD_BAS |
| `prodCtgId` | 상품분류아이디 (lte,3g) | `String` | 상품분류아이디 (lte,3g) | PROD_CTG_ID | NMCP_PROD_BAS |
| `prodNm` | 상품명 | `String` | 상품명 | PROD_NM | NMCP_PROD_BAS |
| `makrCd` | 제조사코드 | `String` | 제조사코드 | MAKR_CD | NMCP_PROD_BAS |
| `rprsPrdtId` | 대표모델ID | `String` | 대표모델ID | RPRS_PRDT_ID | NMCP_PROD_BAS |
| `showYn` | 노출여부 | `String` | 노출여부 | SHOW_YN | NMCP_PROD_BAS |
| `saleYn` | 판매여부 | `String` | 판매여부 | SALE_YN | NMCP_PROD_BAS |
| `ofwDate` | 출고일자 | `String` | 출고일자 | OFW_DATE | NMCP_PROD_BAS |
| `listShowText` | 목록노출텍스트 | `String` | 목록노출텍스트 | LIST_SHOW_TEXT | NMCP_PROD_BAS |
| `listShowOptn` | 목록노출옵션 | `String` | 목록노출옵션 | LIST_SHOW_OPTN | NMCP_PROD_BAS |
| `apdDesc1` | 추가설명1 | `String` | 추가설명1 | APD_DESC_1 | NMCP_PROD_BAS |
| `apdDesc2` | 추가설명2 | `String` | 추가설명2 | APD_DESC_2 | NMCP_PROD_BAS |
| `apdDesc3` | 추가설명3 | `String` | 추가설명3 | APD_DESC_3 | NMCP_PROD_BAS |
| `stckTypeTop` | 스티커노출(상) | `String` | 01:BEST ,02:NEW, 03:신상 , 04:특가, 05:인기상품 | STCK_TYPE_TOP | NMCP_PROD_BAS |
| `stckTypeTail` | 스티커노출(하) | `String` | 01:최대공시금지원, 02:공시지원금확대, 03:이벤트, 04:국내유일 | STCK_TYPE_TAIL | NMCP_PROD_BAS |
| `layerYn` | 레이어팝업 노출여부 | `String` | 레이어팝업 노출여부 | LAYER_YN | NMCP_PROD_BAS |
| `layerImageUrl` | 레이어이미지URL | `String` | 레이어이미지URL | LAYER_IMAGE_URL | NMCP_PROD_BAS |
| `showOdrg` | 노출순서 | `int` | 노출순서 | SHOW_ODRG | NMCP_PROD_BAS |
| `sntyProdDesc` | 단말기상품설명 | `String` | 단말기상품설명 | SNTY_PROD_DESC | NMCP_PROD_BAS |
| `sntyNet` | 단말기네트워크 | `String` | 단말기네트워크 | SNTY_NET | NMCP_PROD_BAS |
| `sntyDisp` | 단말기디스플레이 | `String` | 단말기디스플레이 | SNTY_DISP | NMCP_PROD_BAS |
| `sntySize` | 단말기크기 | `String` | 단말기크기 | SNTY_SIZE | NMCP_PROD_BAS |
| `sntyWeight` | 단말기무게 | `String` | 단말기무게 | SNTY_WEIGHT | NMCP_PROD_BAS |
| `sntyMemr` | 단말기메모리 | `String` | 단말기메모리 | SNTY_MEMR | NMCP_PROD_BAS |
| `sntyBtry` | 단말기배터리 | `String` | 단말기배터리 | SNTY_BTRY | NMCP_PROD_BAS |
| `sntyOs` | 단말기OS | `String` | 단말기OS | SNTY_OS | NMCP_PROD_BAS |
| `sntyWaitTime` | 단말기대기시간 | `String` | 단말기대기시간 | SNTY_WAIT_TIME | NMCP_PROD_BAS |
| `sntyCam` | 단말기카메라 | `String` | 단말기카메라 | SNTY_CAM | NMCP_PROD_BAS |
| `sntyVideTlk` | 단말기영상통화 | `String` | 단말기영상통화 | SNTY_VIDE_TLK | NMCP_PROD_BAS |
| `sntyProdNm` | 단말기명 | `String` | 단말기명 | SNTY_PROD_NM | NMCP_PROD_BAS |
| `sntyRelMonth` | 출시월 | `String` | 출시월 | SNTY_REL_MONTH | NMCP_PROD_BAS |
| `sntyColor` | 단말기색상 | `String` | 단말기색상 | SNTY_COLOR | NMCP_PROD_BAS |
| `sntyMaker` | 단말기제조사/브랜드명 | `String` | 단말기제조사/브랜드명 | SNTY_MAKER | NMCP_PROD_BAS |
| `sntyModelId` | 단말기모델번호 | `String` | 단말기모델번호 | SNTY_MODEL_ID | NMCP_PROD_BAS |
| `cretId` | 생성자아이디 | `String` | 생성자아이디 | CRET_ID | NMCP_PROD_BAS |
| `amdId` | 수정자아이디 | `String` | 수정자아이디 | AMD_ID | NMCP_PROD_BAS |
| `cretDt` | 생성일시 | `Date` | 생성일시 | CRET_DT | NMCP_PROD_BAS |
| `amdDt` | 수정일시 | `Date` | 수정일시 | AMD_DT | NMCP_PROD_BAS |
| `mnfctNm` | 제조사명 | `String` | 제조사명 | MNFCT_NM | ORG_MNFCT_MST |
| `makrNm` | 제조사명 | `String` | 중고폰에서만사용 | MAKR_NM | NMCP_PROD_BAS |
| `inventoryAmt` | 중고폰 재고수량 | `int` | 중고폰 재고수량 | INVENTORY_AMT | NMCP_PROD_BAS |
| `salePrice` | 중고폰 판매가격 | `int` | 중고폰 판매가격 | SALE_PRICE | NMCP_PROD_BAS |
| `prodGrade` | 상품등급 | `String` | 상품등급 | PROD_GRADE | NMCP_PROD_BAS |
| `shandType` | 중고폰type 구분 | `String` | (중고폰,외산폰) | SHAND_TYPE | NMCP_PROD_BAS |
| `recommendRate` | 추천요금제 | `String` | 추천요금제 | RECOMMEND_RATE | NMCP_PROD_BAS |
| `usedWarranty` | 중고폰 워런티 | `String` | 중고폰 워런티 | USED_WARRANTY | NMCP_PROD_BAS |
| `orderCretDt` | 상품순서 정렬용 생성일자 | `String` | -> 출시일로변경 | NVL(A.OFW_DATE,'') as ORDER_CRET_DT | NMCP_PROD_BAS |
| `sntyColorCd` | 색상코드 | `String` | 색상코드 | SNTY_COLOR_CD | NMCP_PROD_IMG_DTL |
| `imgTypeCd` | 이미지유형구분(앞,뒤..) | `String` | 이미지유형구분(앞,뒤..) | IMG_TYPE_CD | NMCP_PROD_IMG_DTL |
| `imgPath` | 이미지경로 | `String` | 이미지경로 | IMG_PATH | NMCP_PROD_IMG_DTL |
| `salePlcyCd` | 정책코드 | `String` | 정책코드 | SALE_PLCY_CD | MSP_SALE_PRDT_MST |

**SQL**

```sql
SELECT
A.PROD_ID,                --상품아이디
A.PROD_ID as prodId,      -- 상품 아이디
A.PROD_CTG_ID,            --상품분류아이디 (lte,3g)
A.PROD_NM,                --상품명
A.MAKR_CD,                --제조사코드
A.RPRS_PRDT_ID,           --대표모델ID
A.SHOW_YN,                --노출여부
A.SALE_YN,                --판매여부
A.OFW_DATE,               --출고일자
A.LIST_SHOW_TEXT,         --목록노출텍스트
A.LIST_SHOW_OPTN,         --목록노출옵션
A.APD_DESC_1,             --추가설명1
A.APD_DESC_2,             --추가설명2
A.APD_DESC_3,             --추가설명3
A.STCK_TYPE_TOP,          --스티커노출(상)
A.STCK_TYPE_TAIL,         --스티커노출(하)
A.LAYER_YN,               --레이어팝업 노출여부
A.LAYER_IMAGE_URL,        --레이어이미지URL
A.SHOW_ODRG,              --노출순서
A.SNTY_PROD_DESC,         --단말기상품설명
A.SNTY_NET,               --단말기네트워크
A.SNTY_DISP,              --단말기디스플레이
A.SNTY_SIZE,              --단말기크기
A.SNTY_WEIGHT,            --단말기무게
A.SNTY_MEMR,              --단말기메모리
A.SNTY_BTRY,              --단말기배터리
A.SNTY_OS,                --단말기OS
A.SNTY_WAIT_TIME,         --단말기대기시간
A.SNTY_CAM,               --단말기카메라
A.SNTY_VIDE_TLK,          --단말기영상통화
A.SNTY_PROD_NM,           --단말기명
A.SNTY_REL_MONTH,         --출시월
A.SNTY_COLOR,             --단말기색상
A.SNTY_MAKER,             --단말기제조사/브랜드명
A.SNTY_MODEL_ID,          --단말기모델번호
A.CRET_ID,                --생성자아이디
A.AMD_ID,                 --수정자아이디
A.CRET_DT,                --생성일시
A.AMD_DT,                 --수정일시
B.MNFCT_NM,               --제조사명
A.MAKR_NM,                --제조사명 중고폰에서만사용
A.INVENTORY_AMT,          --중고폰 재고수량
A.SALE_PRICE,             --중고폰 판매가격
A.PROD_GRADE,             --종고폰 등급
A.SHAND_TYPE,             --중고폰type 구분(중고폰,외산폰)
A.RECOMMEND_RATE,         --추천요금제
A.USED_WARRANTY,          --중고폰 워런티
NVL(A.OFW_DATE,'') as ORDER_CRET_DT,
IMG.SNTY_COLOR_CD,        --색상코드
IMG.IMG_TYPE_CD,          --이미지유형구분(앞,뒤..)
IMG.IMG_PATH,             --이미지경로
PD_SALE.SALE_PLCY_CD      --정책코드
FROM
NMCP_PROD_BAS A,
ORG_MNFCT_MST@DL_MSP B,
NMCP_SNTY_PROD_BAS SNTY,
NMCP_PROD_IMG_DTL IMG,
(SELECT
DISTINCT PRDT_ID
, SALE_PLCY_CD
FROM
MSP_SALE_PRDT_MST@DL_MSP
WHERE
SALE_PLCY_CD IN
<foreach item="prdt" index="index" collection="listMspSaleDto"
open="(" separator="," close=")">
#{prdt.salePlcyCd}
</foreach>
) PD_SALE
WHERE
A.SHOW_YN ='Y'
AND A.RPRS_PRDT_ID = PD_SALE.PRDT_ID
AND A.SALE_YN ='Y'
AND A.MAKR_CD = B.MNFCT_ID
AND A.SHAND_YN = #{shandYn}
AND A.PROD_ID  = SNTY.PROD_ID
<if test="prodCtgId != null and prodCtgId != ''">
<choose>
<when test="prodCtgId =='04'">
AND A.PROD_CTG_ID IN('04','08')
</when>
<otherwise>
AND A.PROD_CTG_ID = #{prodCtgId}
</otherwise>
</choose>
</if>
AND SNTY.RPRS_PRDT_YN = 'Y'
AND A.PROD_ID = IMG.PROD_ID
AND SNTY.PROD_ID = IMG.PROD_ID
AND SNTY.ATRIB_VAL_CD_1 = IMG.SNTY_COLOR_CD
AND IMG_TYPE_CD='01'
<if test="makrCd != null and makrCd != ''">
AND A.MAKR_CD = #{makrCd}
</if>
AND PROD_TYPE =#{prodType}
```


#### API_0089 — findLte3GphoneCount

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `findLte3GphoneCount` |
| 엔드포인트 | `/phone/lte3GphoneCount` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | PhoneMapper.xml |
| SQL ID | `findLte3GphoneCount` |
| parameterType | `com.ktmmobile.mcp.phone.dto.CommonSearchDto` |
| resultType | `map` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | ORG_MNFCT_MST, MSP_SALE_PRDT_MST |
| 담당자 | 김대원 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `prdt.salePlcyCd` | 정책 리스트 | `List<String>` | 상품리스트에서 정책정보가 존재하는 상품만 끓어오기위한 정책정보 리스트 | SALE_PLCY_CD | MSP_SALE_PRDT_MST |
| `shandYn` | 중고폰 여부 YN | `String` | 중고폰 여부 YN | SHAND_YN | NMCP_PROD_BAS |
| `makrCd` | 제조사 id | `String` | 제조사 id | MAKR_CD | NMCP_PROD_BAS |
| `prodType` | 상품 분류 | `String` | 일반 :01, 0원 상품 :02 | PROD_TYPE | NMCP_PROD_BAS |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `ltec` | LTE | `Map<String>` | LTE | NVL(MAX(DECODE(PROD_CTG_ID, '${@com.ktmmobile.mcp.phone.constant.PhoneConstant@PROD_LTE_CD}', CT)),0) AS LTEC | NMCP_PROD_BAS |
| `g3c` | 3G | `Map<String>` | 3G | NVL(MAX(DECODE(PROD_CTG_ID, '${@com.ktmmobile.mcp.phone.constant.PhoneConstant@PROD_3G_CD}', CT)),0) AS G3C | NMCP_PROD_BAS |
| `g5c` | 5G | `Map<String>` | 5G | NVL(MAX(DECODE(PROD_CTG_ID, '${@com.ktmmobile.mcp.phone.constant.PhoneConstant@PROD_5G_CD}', CT)),0) AS G5C | NMCP_PROD_BAS |

**SQL**

```sql
SELECT
NVL(MAX(DECODE(PROD_CTG_ID, '${@com.ktmmobile.mcp.phone.constant.PhoneConstant@PROD_LTE_CD}', CT)),0) AS LTEC,
NVL(MAX(DECODE(PROD_CTG_ID, '${@com.ktmmobile.mcp.phone.constant.PhoneConstant@PROD_3G_CD}', CT)),0) AS G3C,
NVL(MAX(DECODE(PROD_CTG_ID, '${@com.ktmmobile.mcp.phone.constant.PhoneConstant@PROD_5G_CD}', CT)),0) AS G5C
FROM
(
SELECT A.PROD_CTG_ID,
COUNT(A.PROD_CTG_ID) CT
FROM
NMCP_PROD_BAS A,
ORG_MNFCT_MST@DL_MSP B,
NMCP_SNTY_PROD_BAS SNTY,
NMCP_PROD_IMG_DTL IMG,
(SELECT
DISTINCT prdt_id
FROM
msp_sale_prdt_mst@dl_msp
WHERE
sale_plcy_cd IN
<foreach item="prdt" index="index" collection="listMspSaleDto"
open="(" separator="," close=")">
#{prdt.salePlcyCd}
</foreach>
) PD_SALE
WHERE  A.SHOW_YN = 'Y'
AND A.RPRS_PRDT_ID = PD_SALE.PRDT_ID
AND A.SALE_YN = 'Y'
AND A.MAKR_CD = B.MNFCT_ID
AND A.SHAND_YN = #{shandYn}
AND A.PROD_ID  = SNTY.PROD_ID
AND SNTY.RPRS_PRDT_YN = 'Y'
AND A.PROD_ID = IMG.PROD_ID
AND SNTY.PROD_ID = IMG.PROD_ID
AND SNTY.ATRIB_VAL_CD_1 = IMG.SNTY_COLOR_CD
AND IMG_TYPE_CD='01'
<if test="makrCd != null and makrCd != ''">
AND A.MAKR_CD = #{makrCd}
</if>
<if test="prodType != null and prodType != ''">
AND A.PROD_TYPE = #{prodType}
</if>
GROUP  BY A.PROD_CTG_ID
)
```


---

### PAYINFO 도메인 (6개)

#### API_0076 — 페이인포 시퀀스 채번

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `getSeq` |
| 연동명(한글) | 페이인포 시퀀스 채번 |
| 엔드포인트 | `/payinfo/seq` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | PayInfoMapper.xml |
| SQL ID | `getSeq` |
| parameterType | `` |
| resultType | `java.lang.String` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | SQ_MSP_PAYINFO_01.NEXTVAL |
| 담당자 | 강채신 |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `RECSEQ` | 페이인포 시퀀스 채번 | `String` | 페이인포 시퀀스 채번 | RECSEQ | SQ_MSP_PAYINFO_01 |

**SQL**

```sql
SELECT /*페이인포 시퀀스 채번*/
TO_CHAR(SYSDATE, 'YYYYMMDD') || TRIM(TO_CHAR(SQ_MSP_PAYINFO_01.NEXTVAL@DL_MSP, '000000000000000000000')) AS RECSEQ
FROM DUAL
```


#### API_0077 — EvidenceDto

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `selectMspJuoSubinfo` |
| 연동명(한글) | EvidenceDto |
| 엔드포인트 | `/payinfo/mspJuoSubinfo` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | PayInfoMapper.xml |
| SQL ID | `selectMspJuoSubinfo` |
| parameterType | `com.ktmmobile.mcp.payinfo.dto.PayInfoDto` |
| resultType | `com.ktmmobile.mcp.payinfo.dto.EvidenceDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_JUO_SUB_INFO |
| 담당자 | 강채신 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `ctn` | 전화번호 | `String` | 전화번호 | SUBSCRIBER_NO | MSP_JUO_SUB_INFO |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `ban` | 청구번호 | `String` | 청구번호 | BAN | MSP_JUO_SUB_INFO |
| `contractNum` | 가입계약번호 | `String` | 가입계약번호 | CONTRACT_NUM | MSP_JUO_SUB_INFO |
| `svcCntrNo` | 서비스 계약번호 | `String` | 서비스 계약번호 | SVC_CNTR_NO | MSP_JUO_SUB_INFO |

**SQL**

```sql
SELECT
BAN ,CONTRACT_NUM, SVC_CNTR_NO
FROM MSP_JUO_SUB_INFO@DL_MSP
WHERE SUBSCRIBER_NO = #{ctn}
AND SUB_STATUS  <![CDATA[<>]]> 'C'
```


#### API_0078 — updateEvidence

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `updateEvidence` |
| 엔드포인트 | `/payinfo/modifyEvidence` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | PayInfoMapper.xml |
| SQL ID | `updateEvidence` |
| parameterType | `com.ktmmobile.mcp.payinfo.dto.EvidenceDto` |
| resultType | `java.lang.Integer` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | CMN_KFTC_EVIDENCE |
| 담당자 | 강채신 |
| 비고 | UPDATE |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `ext` | 파일확장자 | `String` | 파일확장자 | EXT | CMN_KFTC_EVIDENCE |
| `realFileNm` | 실 파일명 | `String` | 실 파일명 | REAL_FILE_NM | CMN_KFTC_EVIDENCE |
| `realFilePath` | 실 파일 경로 | `String` | 실 파일 경로 | REAL_FILE_PATH | CMN_KFTC_EVIDENCE |
| `rgstDt` | 등록일자 | `String` | 등록일자 | RGST_DT | CMN_KFTC_EVIDENCE |
| `validDt` | 유효일자 | `String` | 유효일자 | VALID_DT | CMN_KFTC_EVIDENCE |
| `rvisnId` | 수정자ID | `String` | 수정자ID | RVISN_ID | CMN_KFTC_EVIDENCE |
| `contractNum` | 가입계약번호 | `String` | 가입계약번호 | CONTRACT_NUM | CMN_KFTC_EVIDENCE |

**SQL**

```sql
UPDATE
CMN_KFTC_EVIDENCE@DL_MSP
SET
EXT = #{ext}
, REAL_FILE_NM = #{realFileNm}
, REAL_FILE_PATH = #{realFilePath}
, RGST_DT = #{rgstDt}
, VALID_DT = #{validDt}
, RVISN_DTTM=  SYSDATE
, RVISN_ID  = #{rvisnId}
, APPROVAL_ID =''
, APPROVAL_DT  =''
, APPROVAL_MSG =''
, APPROVAL_CD =''
WHERE CONTRACT_NUM = #{contractNum}
```


#### API_0079 — 계약번호에 존재여부 확인 CMN_KFTC_EVIDENCE

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `checkEvidenceCount` |
| 연동명(한글) | 계약번호에 존재여부 확인 CMN_KFTC_EVIDENCE |
| 엔드포인트 | `/payinfo/checkEvidenceCount` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | PayInfoMapper.xml |
| SQL ID | `checkEvidenceCount` |
| parameterType | `com.ktmmobile.mcp.payinfo.dto.EvidenceDto` |
| resultType | `java.lang.Integer` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | CMN_KFTC_EVIDENCE |
| 담당자 | 강채신 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `contractNum` | 가입계약번호 | `String` | 가입계약번호 | CONTRACT_NUM | CMN_KFTC_EVIDENCE |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `count` | 조회개수 | `int` | 조회개수 | COUNT |  |

**SQL**

```sql
SELECT
COUNT(CONTRACT_NUM)
FROM CMN_KFTC_EVIDENCE@DL_MSP
WHERE CONTRACT_NUM = #{contractNum}
```


#### API_0080 — CMN_KFTC_EVIDENCE 테이블 데이터 등록

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `insetEvidence` |
| 연동명(한글) | CMN_KFTC_EVIDENCE 테이블 데이터 등록 |
| 엔드포인트 | `/payinfo/addEvidence` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | PayInfoMapper.xml |
| SQL ID | `insetEvidence` |
| parameterType | `com.ktmmobile.mcp.payinfo.dto.EvidenceDto` |
| resultType | `java.lang.Integer` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | CMN_KFTC_EVIDENCE_SEQ.NEXTVAL, CMN_KFTC_EVIDENCE |
| 담당자 | 강채신 |
| 비고 | INSERT |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `ban` | 청구번호 | `String` | 청구번호 | BAN | CMN_KFTC_EVIDENCE |
| `contractNum` | 가입계약번호 | `String` | 가입계약번호 | CONTRACT_NUM | CMN_KFTC_EVIDENCE |
| `ext` | 파일확장자 | `String` | 파일확장자 | EXT | CMN_KFTC_EVIDENCE |
| `kftcEvidenceSeq` | 증거파일 SEQ | `String` | 증거파일 SEQ | KFTC_EVIDENCE_SEQ | CMN_KFTC_EVIDENCE |
| `realFileNm` | 실 파일명 | `String` | 실 파일명 | REAL_FILE_NM | CMN_KFTC_EVIDENCE |
| `realFilePath` | 실 파일 경로 | `String` | 실 파일 경로 | REAL_FILE_PATH | CMN_KFTC_EVIDENCE |
| `regstId` | 등록자ID | `String` | 등록자ID | REGST_ID | CMN_KFTC_EVIDENCE |
| `regYn` | 파일등록여부 | `String` | 파일등록여부 | REG_YN | CMN_KFTC_EVIDENCE |
| `rgstDt` | 등록일자 | `String` | 등록일자 | RGST_DT | CMN_KFTC_EVIDENCE |
| `rgstId` | 등록ID | `String` | 등록ID | RGST_ID | CMN_KFTC_EVIDENCE |
| `rvisnId` | 수정자ID | `String` | 수정자ID | RVISN_ID | CMN_KFTC_EVIDENCE |
| `validDt` | 유효일자 | `String` | 유효일자 | VALID_DT | CMN_KFTC_EVIDENCE |

**SQL**

```sql
<selectKey keyProperty="kftcEvidenceSeq" resultType="java.lang.String" order="BEFORE">
SELECT CMN_KFTC_EVIDENCE_SEQ.NEXTVAL@DL_MSP FROM DUAL
</selectKey>
INSERT INTO CMN_KFTC_EVIDENCE@DL_MSP ( /*CMN_KFTC_EVIDENCE 테이블 데이터 등록*/
BAN
, CONTRACT_NUM
, EXT
, KFTC_EVIDENCE_SEQ
, REAL_FILE_NM
, REAL_FILE_PATH
, REGST_DTTM
, REGST_ID
, REG_YN
, RGST_DT
, RGST_ID
, RVISN_ID
, RGST_INFLOW_CD
, VALID_DT
, EVIDENCE_TYPE_CD
) VALUES (
#{ban}
, #{contractNum}
, #{ext}
, #{kftcEvidenceSeq , jdbcType=NUMERIC}
, #{realFileNm}
, #{realFilePath}
, SYSDATE
, #{regstId}
, #{regYn}
, #{rgstDt}
, #{rgstId}
, #{rvisnId}
, '0003'
, #{validDt}
, '0001'
)
```


#### API_0081 — callMspPayinfoImg

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `callMspPayinfoImg` |
| 엔드포인트 | `/payinfo/mspPayinfoImg` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | PayInfoMapper.xml |
| SQL ID | `callMspPayinfoImg` |
| parameterType | `java.util.Map` |
| resultType | `java.util.Map` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_PAYINFO_IMG |
| 담당자 | 강채신 |
| 비고 | FUNCTION |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `contractNum` | 가입계약번호 | `Map<String>` | 가입계약번호 |  | MSP_PAYINFO_IMG |
| `reqBank` | 은행코드 | `Map<String>` | 은행코드 |  | MSP_PAYINFO_IMG |
| `reqAccountNumber` | 계좌번호 | `Map<String>` | 계좌번호 |  | MSP_PAYINFO_IMG |
| `ext` | 파일확장자 | `Map<String>` | 파일확장자 |  | MSP_PAYINFO_IMG |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `result` | 결과 | `Map<String>` | 결과 |  | MSP_PAYINFO_IMG |

**SQL**

```sql
{CALL MSP_PAYINFO_IMG@DL_MSP(#{contractNum},#{reqBank},#{reqAccountNumber},#{ext},#{result})}
```


---

### APPPPS 도메인 (6개)

#### API_0125 — pAppSms

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `pAppSms` |
| 엔드포인트 | `/appPps/pAppSms` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | AppMobilePpsMapper.xml |
| SQL ID | `pAppSms` |
| parameterType | `java.util.Map` |
| resultType | `void` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | P_APP_SMS |
| 담당자 | 박정웅 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `opCode` |  | `string` |  |  | P_APP_SMS |
| `subscriberNo` |  | `string` |  |  | P_APP_SMS |
| `userName` |  | `string` |  |  | P_APP_SMS |
| `smsAuthCode` |  | `string` |  |  | P_APP_SMS |
| `LANG` |  | `string` |  |  | P_APP_SMS |
| `appType` |  | `string` |  |  | P_APP_SMS |
| `appVer` |  | `string` |  |  | P_APP_SMS |
| `iUrl` |  | `string` |  |  | P_APP_SMS |
| `accessIp` |  | `string` |  |  | P_APP_SMS |
| `smsSeq` |  | `string` |  |  | P_APP_SMS |
| `O_CODE` |  | `string` |  |  | P_APP_SMS |
| `O_MSG` |  | `string` |  |  | P_APP_SMS |
| `O_DATA` |  | `string` |  |  | P_APP_SMS |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `O_CODE` |  | `string` |  |  | P_APP_SMS |
| `O_MSG` |  | `string` |  |  | P_APP_SMS |
| `O_DATA` |  | `string` |  |  | P_APP_SMS |

**SQL**

```sql
call P_APP_SMS(
#{opCode, mode=IN, jdbcType=VARCHAR, javaType=string}
,#{subscriberNo, mode=IN, jdbcType=VARCHAR, javaType=string}
,#{userName, mode=IN, jdbcType=VARCHAR, javaType=string}
,#{smsAuthCode, mode=IN, jdbcType=VARCHAR, javaType=string}
,#{LANG, mode=IN, jdbcType=VARCHAR, javaType=string}
,#{appType, mode=IN, jdbcType=VARCHAR, javaType=string}
,#{appVer, mode=IN, jdbcType=VARCHAR, javaType=string}
,#{iUrl, mode=IN, jdbcType=VARCHAR, javaType=string}
,#{accessIp, mode=IN, jdbcType=VARCHAR, javaType=string}
,#{smsSeq, mode=IN, jdbcType=VARCHAR, javaType=string}
,#{O_CODE, mode=INOUT, jdbcType=VARCHAR, javaType=string}
,#{O_MSG, mode=INOUT, jdbcType=VARCHAR, javaType=string}
,#{O_DATA, mode=INOUT, jdbcType=VARCHAR, javaType=string}
)
```


#### API_0126 — pAppUserInfo

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `pAppUserInfo` |
| 엔드포인트 | `/appPps/pAppUserInfo` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | AppMobilePpsMapper.xml |
| SQL ID | `pAppUserInfo` |
| parameterType | `java.util.Map` |
| resultType | `void` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | P_APP_USER_INFO |
| 담당자 | 박정웅 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `opCode` |  | `string` |  |  | P_APP_USER_INFO |
| `contractNum` |  | `string` |  |  | P_APP_USER_INFO |
| `LANG` |  | `string` |  |  | P_APP_USER_INFO |
| `iUrl` |  | `string` |  |  | P_APP_USER_INFO |
| `accessIp` |  | `string` |  |  | P_APP_USER_INFO |
| `sessionId` |  | `string` |  |  | P_APP_USER_INFO |
| `O_CODE` |  | `string` |  |  | P_APP_USER_INFO |
| `O_MSG` |  | `string` |  |  | P_APP_USER_INFO |
| `O_DATA` |  | `string` |  |  | P_APP_USER_INFO |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `O_CODE` |  | `string` |  |  | P_APP_USER_INFO |
| `O_MSG` |  | `string` |  |  | P_APP_USER_INFO |
| `O_DATA` |  | `string` |  |  | P_APP_USER_INFO |

**SQL**

```sql
call P_APP_USER_INFO(
#{opCode, mode=IN, jdbcType=VARCHAR, javaType=string}
,#{contractNum, mode=IN, jdbcType=VARCHAR, javaType=string}
,#{LANG, mode=IN, jdbcType=VARCHAR, javaType=string}
,#{iUrl, mode=IN, jdbcType=VARCHAR, javaType=string}
,#{accessIp, mode=IN, jdbcType=VARCHAR, javaType=string}
,#{sessionId, mode=IN, jdbcType=VARCHAR, javaType=string}
,#{O_CODE, mode=INOUT, jdbcType=VARCHAR, javaType=string}
,#{O_MSG, mode=INOUT, jdbcType=VARCHAR, javaType=string}
,#{O_DATA, mode=INOUT, jdbcType=VARCHAR, javaType=string}
)
```


#### API_0127 — pAppChgVac

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `pAppChgVac` |
| 엔드포인트 | `/appPps/pAppChgVac` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | AppMobilePpsMapper.xml |
| SQL ID | `pAppChgVac` |
| parameterType | `java.util.Map` |
| resultType | `void` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | P_APP_CHG_VAC |
| 담당자 | 박정웅 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `opCode` |  | `string` |  |  | P_APP_CHG_VAC |
| `contractNum` |  | `string` |  |  | P_APP_CHG_VAC |
| `bankCd` |  | `string` |  |  | P_APP_CHG_VAC |
| `LANG` |  | `string` |  |  | P_APP_CHG_VAC |
| `iUrl` |  | `string` |  |  | P_APP_CHG_VAC |
| `accessIp` |  | `string` |  |  | P_APP_CHG_VAC |
| `sessionId` |  | `string` |  |  | P_APP_CHG_VAC |
| `O_CODE` |  | `string` |  |  | P_APP_CHG_VAC |
| `O_MSG` |  | `string` |  |  | P_APP_CHG_VAC |
| `O_DATA` |  | `string` |  |  | P_APP_CHG_VAC |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `O_CODE` |  | `string` |  |  | P_APP_CHG_VAC |
| `O_MSG` |  | `string` |  |  | P_APP_CHG_VAC |
| `O_DATA` |  | `string` |  |  | P_APP_CHG_VAC |

**SQL**

```sql
call P_APP_CHG_VAC(
#{opCode, mode=IN, jdbcType=VARCHAR, javaType=string}
,#{contractNum, mode=IN, jdbcType=VARCHAR, javaType=string}
,#{bankCd, mode=IN, jdbcType=VARCHAR, javaType=string}
,#{LANG, mode=IN, jdbcType=VARCHAR, javaType=string}
,#{iUrl, mode=IN, jdbcType=VARCHAR, javaType=string}
,#{accessIp, mode=IN, jdbcType=VARCHAR, javaType=string}
,#{sessionId, mode=IN, jdbcType=VARCHAR, javaType=string}
,#{O_CODE, mode=INOUT, jdbcType=VARCHAR, javaType=string}
,#{O_MSG, mode=INOUT, jdbcType=VARCHAR, javaType=string}
,#{O_DATA, mode=INOUT, jdbcType=VARCHAR, javaType=string}
)
```


#### API_0128 — pAppPpReq

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `pAppPpReq` |
| 엔드포인트 | `/appPps/pAppPpReq` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | AppMobilePpsMapper.xml |
| SQL ID | `pAppPpReq` |
| parameterType | `java.util.Map` |
| resultType | `void` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | P_APP_PP_REQ |
| 담당자 | 박정웅 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `opCode` |  | `string` |  |  | P_APP_PP_REQ |
| `contractNum` |  | `string` |  |  | P_APP_PP_REQ |
| `pinNo` |  | `string` |  |  | P_APP_PP_REQ |
| `mngCd` |  | `string` |  |  | P_APP_PP_REQ |
| `ccdNo` |  | `string` |  |  | P_APP_PP_REQ |
| `ccdExpire` |  | `string` |  |  | P_APP_PP_REQ |
| `ccdPW` |  | `string` |  |  | P_APP_PP_REQ |
| `ccdBirth` |  | `string` |  |  | P_APP_PP_REQ |
| `ccdRcgAmt` |  | `string` |  |  | P_APP_PP_REQ |
| `ccdPayAmt` |  | `string` |  |  | P_APP_PP_REQ |
| `LANG` |  | `string` |  |  | P_APP_PP_REQ |
| `iUrl` |  | `string` |  |  | P_APP_PP_REQ |
| `accessIp` |  | `string` |  |  | P_APP_PP_REQ |
| `sessionId` |  | `string` |  |  | P_APP_PP_REQ |
| `O_CODE` |  | `string` |  |  | P_APP_PP_REQ |
| `O_MSG` |  | `string` |  |  | P_APP_PP_REQ |
| `O_DATA` |  | `string` |  |  | P_APP_PP_REQ |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `O_CODE` |  | `string` |  |  | P_APP_PP_REQ |
| `O_MSG` |  | `string` |  |  | P_APP_PP_REQ |
| `O_DATA` |  | `string` |  |  | P_APP_PP_REQ |

**SQL**

```sql
call P_APP_PP_REQ(
#{opCode, mode=IN, jdbcType=VARCHAR, javaType=string}
,#{contractNum, mode=IN, jdbcType=VARCHAR, javaType=string}
,#{pinNo, mode=IN, jdbcType=VARCHAR, javaType=string}
,#{mngCd, mode=IN, jdbcType=VARCHAR, javaType=string}
,#{ccdNo, mode=IN, jdbcType=VARCHAR, javaType=string}
,#{ccdExpire, mode=IN, jdbcType=VARCHAR, javaType=string}
,#{ccdPW, mode=IN, jdbcType=VARCHAR, javaType=string}
,#{ccdBirth, mode=IN, jdbcType=VARCHAR, javaType=string}
,#{ccdRcgAmt, mode=IN, jdbcType=VARCHAR, javaType=string}
,#{ccdPayAmt, mode=IN, jdbcType=VARCHAR, javaType=string}
,#{LANG, mode=IN, jdbcType=VARCHAR, javaType=string}
,#{iUrl, mode=IN, jdbcType=VARCHAR, javaType=string}
,#{accessIp, mode=IN, jdbcType=VARCHAR, javaType=string}
,#{sessionId, mode=IN, jdbcType=VARCHAR, javaType=string}
,#{O_CODE, mode=INOUT, jdbcType=VARCHAR, javaType=string}
,#{O_MSG, mode=INOUT, jdbcType=VARCHAR, javaType=string}
,#{O_DATA, mode=INOUT, jdbcType=VARCHAR, javaType=string}
)
```


#### API_0129 — pAppPpRes

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `pAppPpRes` |
| 엔드포인트 | `/appPps/pAppPpRes` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | AppMobilePpsMapper.xml |
| SQL ID | `pAppPpRes` |
| parameterType | `java.util.Map` |
| resultType | `void` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | P_APP_PP_RES |
| 담당자 | 박정웅 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `opCode` |  | `string` |  |  | P_APP_PP_RES |
| `contractNum` |  | `string` |  |  | P_APP_PP_RES |
| `rcgReq` |  | `string` |  |  | P_APP_PP_RES |
| `rcgAmt` |  | `string` |  |  | P_APP_PP_RES |
| `payAmt` |  | `string` |  |  | P_APP_PP_RES |
| `cardPayCode` |  | `string` |  |  | P_APP_PP_RES |
| `cardErrMsg` |  | `string` |  |  | P_APP_PP_RES |
| `cardOrderNum` |  | `string` |  |  | P_APP_PP_RES |
| `cardAmount` |  | `string` |  |  | P_APP_PP_RES |
| `cardDouTrx` |  | `string` |  |  | P_APP_PP_RES |
| `cardAuthNo` |  | `string` |  |  | P_APP_PP_RES |
| `cardAuthDate` |  | `string` |  |  | P_APP_PP_RES |
| `cardNointFlag` |  | `string` |  |  | P_APP_PP_RES |
| `cardCpName` |  | `string` |  |  | P_APP_PP_RES |
| `cardCpUrl` |  | `string` |  |  | P_APP_PP_RES |
| `cardDouRsv1` |  | `string` |  |  | P_APP_PP_RES |
| `cardDouRsv2` |  | `string` |  |  | P_APP_PP_RES |
| `LANG` |  | `string` |  |  | P_APP_PP_RES |
| `iUrl` |  | `string` |  |  | P_APP_PP_RES |
| `accessIp` |  | `string` |  |  | P_APP_PP_RES |
| `sessionId` |  | `string` |  |  | P_APP_PP_RES |
| `O_CODE` |  | `string` |  |  | P_APP_PP_RES |
| `O_MSG` |  | `string` |  |  | P_APP_PP_RES |
| `O_DATA` |  | `string` |  |  | P_APP_PP_RES |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `O_CODE` |  | `string` |  |  | P_APP_PP_RES |
| `O_MSG` |  | `string` |  |  | P_APP_PP_RES |
| `O_DATA` |  | `string` |  |  | P_APP_PP_RES |

**SQL**

```sql
call P_APP_PP_RES(
#{opCode, mode=IN, jdbcType=VARCHAR, javaType=string}
,#{contractNum, mode=IN, jdbcType=VARCHAR, javaType=string}
,#{rcgReq, mode=IN, jdbcType=VARCHAR, javaType=string}
,#{rcgAmt, mode=IN, jdbcType=VARCHAR, javaType=string}
,#{payAmt, mode=IN, jdbcType=VARCHAR, javaType=string}
,#{cardPayCode, mode=IN, jdbcType=VARCHAR, javaType=string}
,#{cardErrMsg, mode=IN, jdbcType=VARCHAR, javaType=string}
,#{cardOrderNum, mode=IN, jdbcType=VARCHAR, javaType=string}
,#{cardAmount, mode=IN, jdbcType=VARCHAR, javaType=string}
,#{cardDouTrx, mode=IN, jdbcType=VARCHAR, javaType=string}
,#{cardAuthNo, mode=IN, jdbcType=VARCHAR, javaType=string}
,#{cardAuthDate, mode=IN, jdbcType=VARCHAR, javaType=string}
,#{cardNointFlag, mode=IN, jdbcType=VARCHAR, javaType=string}
,#{cardCpName, mode=IN, jdbcType=VARCHAR, javaType=string}
,#{cardCpUrl, mode=IN, jdbcType=VARCHAR, javaType=string}
,#{cardDouRsv1, mode=IN, jdbcType=VARCHAR, javaType=string}
,#{cardDouRsv2, mode=IN, jdbcType=VARCHAR, javaType=string}
,#{LANG, mode=IN, jdbcType=VARCHAR, javaType=string}
,#{iUrl, mode=IN, jdbcType=VARCHAR, javaType=string}
,#{accessIp, mode=IN, jdbcType=VARCHAR, javaType=string}
,#{sessionId, mode=IN, jdbcType=VARCHAR, javaType=string}
,#{O_CODE, mode=INOUT, jdbcType=VARCHAR, javaType=string}
,#{O_MSG, mode=INOUT, jdbcType=VARCHAR, javaType=string}
,#{O_DATA, mode=INOUT, jdbcType=VARCHAR, javaType=string}
)
```


#### API_0130 — pAppPpRst

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `pAppPpRst` |
| 엔드포인트 | `/appPps/pAppPpRst` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | AppMobilePpsMapper.xml |
| SQL ID | `pAppPpRst` |
| parameterType | `java.util.Map` |
| resultType | `void` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | P_APP_PP_RST |
| 담당자 | 박정웅 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `opCode` |  | `string` |  |  | P_APP_PP_RST |
| `rcgReq` |  | `string` |  |  | P_APP_PP_RST |
| `LANG` |  | `string` |  |  | P_APP_PP_RST |
| `TMP` |  | `string` |  |  | P_APP_PP_RST |
| `appType` |  | `string` |  |  | P_APP_PP_RST |
| `appVer` |  | `string` |  |  | P_APP_PP_RST |
| `iUrl` |  | `string` |  |  | P_APP_PP_RST |
| `accessIp` |  | `string` |  |  | P_APP_PP_RST |
| `O_CODE` |  | `string` |  |  | P_APP_PP_RST |
| `O_MSG` |  | `string` |  |  | P_APP_PP_RST |
| `O_DATA` |  | `string` |  |  | P_APP_PP_RST |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `O_CODE` |  | `string` |  |  | P_APP_PP_RST |
| `O_MSG` |  | `string` |  |  | P_APP_PP_RST |
| `O_DATA` |  | `string` |  |  | P_APP_PP_RST |

**SQL**

```sql
call P_APP_PP_RST(
#{opCode, mode=IN, jdbcType=VARCHAR, javaType=string}
,#{rcgReq, mode=IN, jdbcType=VARCHAR, javaType=string}
,#{LANG, mode=IN, jdbcType=VARCHAR, javaType=string}
,#{TMP, mode=IN, jdbcType=VARCHAR, javaType=string}
,#{appType, mode=IN, jdbcType=VARCHAR, javaType=string}
,#{appVer, mode=IN, jdbcType=VARCHAR, javaType=string}
,#{iUrl, mode=IN, jdbcType=VARCHAR, javaType=string}
,#{accessIp, mode=IN, jdbcType=VARCHAR, javaType=string}
,#{O_CODE, mode=INOUT, jdbcType=VARCHAR, javaType=string}
,#{O_MSG, mode=INOUT, jdbcType=VARCHAR, javaType=string}
,#{O_DATA, mode=INOUT, jdbcType=VARCHAR, javaType=string}
)
```


---

### BANNER 도메인 (3개)

#### API_0017 — 컨텐츠 이미지 배너 선택

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `selectContentBanner` |
| 연동명(한글) | 컨텐츠 이미지 배너 선택 |
| 엔드포인트 | `/banner/contentBanner` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | BannerMapper.xml |
| SQL ID | `selectContentBanner` |
| parameterType | `com.ktmmobile.mcp.banner.dto.BannerDto` |
| resultType | `com.ktmmobile.mcp.banner.dto.BannerDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_SALE_AGRM_MST, MSP_RATE_MST, MSP_SALE_RATE_MST |
| 담당자 | 강채신 |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `bannSeq` | 배너일련번호 | `int` | 배너일련번호 | BANN_SEQ | NMCP_BANN_BAS |
| `bannCtg` | 배너카테고리 | `String` | 배너카테고리 | BANN_CTG | NMCP_BANN_BAS |
| `bannType` | 배너종류 | `String` | 배너종류 | BANN_TYPE | NMCP_BANN_BAS |
| `bannNm` | 배너명 | `String` | 배너명 | BANN_NM | NMCP_BANN_BAS |
| `bannDesc` | 배너설명 | `String` | 배너설명 | BANN_DESC | NMCP_BANN_BAS |
| `bannImg` | 배너이미지 | `String` | 배너이미지 | BANN_IMG | NMCP_BANN_BAS |
| `imgDesc` | 이미지설명(alt) | `String` | 이미지설명(alt) | IMG_DESC | NMCP_BANN_BAS |
| `linkTarget` | 링크타겟 | `String` | 링크타겟 | LINK_TARGET | NMCP_BANN_BAS |
| `linkUrlAdr` | 링크URL | `String` | 링크URL | LINK_URL_ADR | NMCP_BANN_BAS |
| `statVal` | 상태 | `String` | 상태 | STAT_VAL | NMCP_BANN_BAS |
| `prodNm` | 상품명 | `String` | 상품명 | PROD_NM | NMCP_BANN_BAS |
| `prodId` | 상품아이디 | `int` | 상품아이디 | PROD_ID | NMCP_BANN_BAS |
| `sntyProdSeq` | 단품상품일련번호 | `int` | 단품상품일련번호 | SNTY_PROD_SEQ | NMCP_BANN_BAS |
| `sbscrbTypeCd` | 가입유형코드 | `String` | 가입유형코드 | SBSCRB_TYPE_CD | NMCP_BANN_BAS |
| `agrmTypeCd` | 약정유형코드 | `String` | 약정유형코드 | AGRM_TYPE_CD | NMCP_BANN_BAS |
| `chrgPlanCd` | 요금제코드 | `String` | 요금제코드 | CHRG_PLAN_CD | NMCP_BANN_BAS |
| `indcOdrg` | 표시순서 | `String` | 표시순서 | INDC_ODRG | NMCP_BANN_BAS |
| `cretId` | 생성자아이디 | `String` | 생성자아이디 | CRET_ID | NMCP_BANN_BAS |
| `amdId` | 수정자아이디 | `String` | 수정자아이디 | AMD_ID | NMCP_BANN_BAS |
| `cretDt` | 생성일시 | `String` | 생성일시 | CRET_DT | NMCP_BANN_BAS |
| `atribValCd1` | 색상 | `String` | 색상 | ATRIB_VAL_CD_1 | NMCP_BANN_BAS |
| `atribValCd2` | 용량 | `String` | 용량 | ATRIB_VAL_CD_2 | NMCP_BANN_BAS |
| `rateCd` | 약정명 | `String` | 약정명 | RATE_CD | NMCP_BANN_BAS |
| `salePlcyCd` | 판매정책코드 | `String` | 판매정책코드 | SALE_PLCY_CD | NMCP_BANN_BAS |
| `newYn` | 가입유형 신규 | `String` | 가입유형 신규 | NEW_YN | NMCP_BANN_BAS |
| `mnpYn` | 가입유형 번호이동 | `String` | 가입유형 번호이동 | MNP_YN | NMCP_BANN_BAS |
| `hcnYn` | 가입유형 기기변경 | `String` | 가입유형 기기변경 | HCN_YN | NMCP_BANN_BAS |
| `agrmTrm` | 약정코드 | `String` | 약정코드 | AGRM_TRM | NMCP_BANN_BAS |
| `bgColor` | 배너배경색 | `String` | 배너배경색 | BG_COLOR | NMCP_BANN_BAS |
| `rateNm` | 요금제명 | `String` | 요금제명 | RATE_NM | MSP_RATE_MST |
| `atribValNm1` | 색상명 | `String` | 색상명 | ATRIB_VAL_NM_1 | NMCP_SNTY_PROD_BAS |
| `atribValNm2` | 용량명 | `String` | 용량명 | ATRIB_VAL_NM_2 | NMCP_SNTY_PROD_BAS |
| `sdoutYn` | 품절여부 | `String` | 품절여부 | SDOUT_YN | NMCP_SNTY_PROD_BAS |
| `amdDt` | 수정일시 | `String` | 수정일시 | AMD_DT | NMCP_BANN_BAS |
| `adDesc` | 광고문구 | `String` | 광고문구 | AD_DESC | NMCP_BANN_BAS |

**SQL**

```sql
SELECT A.BANN_SEQ,
A.BANN_CTG,
A.BANN_TYPE,
A.BANN_NM,
A.BANN_DESC,
A.BANN_IMG,
A.IMG_DESC,
A.LINK_TARGET,
A.LINK_URL_ADR,
A.STAT_VAL,
A.PROD_NM,
A.PROD_ID,
A.SNTY_PROD_SEQ,
A.SBSCRB_TYPE_CD,
A.AGRM_TYPE_CD,
A.CHRG_PLAN_CD,
A.INDC_ODRG,
A.CRET_ID,
A.AMD_ID,
TO_CHAR (A.CRET_DT, 'YYYY.MM.DD') AS CRET_DT,
A.ATRIB_VAL_CD_1,
A.ATRIB_VAL_CD_2,
A.RATE_CD,
A.SALE_PLCY_CD,
A.NEW_YN,
A.MNP_YN,
A.HCN_YN,
A.AGRM_TRM,
A.BG_COLOR,
C.RATE_NM,
E.ATRIB_VAL_NM_1,
E.ATRIB_VAL_NM_2,
E.SDOUT_YN,
A.AMD_DT,
A.AD_DESC
FROM NMCP_BANN_BAS A,
MSP_SALE_AGRM_MST@DL_MSP B,
MSP_RATE_MST@DL_MSP C,
MSP_SALE_RATE_MST@DL_MSP D,
(select DISTINCT ATRIB_VAL_NM_1,
ATRIB_VAL_NM_2,
SDOUT_YN,
HNDSET_MODEL_ID,
b.BANN_SEQ
from  NMCP_SNTY_PROD_BAS a
, NMCP_BANN_BAS b
where a.HNDSET_MODEL_ID = b.SNTY_PROD_SEQ
AND A.PROD_ID = B.PROD_ID
and b.SNTY_PROD_SEQ <![CDATA[<>]]> 0
and b.SNTY_PROD_SEQ is not null
) E
WHERE
ROWNUM <![CDATA[<=]]> 5
AND A.BANN_CTG  = '07'
AND A.INDC_ODRG != '0'
AND a.BANN_SEQ = e.BANN_SEQ(+)
AND A.SALE_PLCY_CD = B.SALE_PLCY_CD(+)
AND A.AGRM_TRM = B.AGRM_TRM(+)
AND A.SALE_PLCY_CD = D.SALE_PLCY_CD(+)
AND A.RATE_CD = D.RATE_CD(+)
AND D.RATE_CD = C.RATE_CD(+)
<!--  AND A.SNTY_PROD_SEQ = E.HNDSET_MODEL_ID(+)
AND A.ATRIB_VAL_CD_1 = E.ATRIB_VAL_CD_1(+)
AND A.ATRIB_VAL_CD_2 = E.ATRIB_VAL_CD_2(+) -->
ORDER BY INDC_ODRG ASC
```


#### API_0018 — 배너 하위 관리 선택

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `selectBannerDtl` |
| 연동명(한글) | 배너 하위 관리 선택 |
| 엔드포인트 | `/banner/bannerDtl` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | BannerMapper.xml |
| SQL ID | `selectBannerDtl` |
| parameterType | `com.ktmmobile.mcp.banner.dto.BannerDto` |
| resultType | `com.ktmmobile.mcp.banner.dto.BannerDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_SALE_AGRM_MST, MSP_RATE_MST, MSP_SALE_RATE_MST |
| 담당자 | 강채신 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `bannSeq` | 배너일련번호 | `int` | 배너일련번호 | BANN_SEQ | NMCP_BANN_BAS |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `payClCd` | 선후불구분 | `String` | 선후불구분 | PAY_CL_CD | NMCP_BANN_BAS |
| `dataType` | 데이터유형 | `String` | 데이터유형 (ORG0008) 'LTE,3G | DATA_TYPE | NMCP_BANN_BAS |
| `bannSeq` | 배너일련번호 | `int` | 배너일련번호 | BANN_SEQ | NMCP_BANN_BAS |
| `bannCtg` | 배너카테고리 | `String` | 배너카테고리 | BANN_CTG | NMCP_BANN_BAS |
| `bannType` | 배너종류 | `String` | 배너종류 | BANN_TYPE | NMCP_BANN_BAS |
| `bannNm` | 배너명 | `String` | 배너명 | BANN_NM | NMCP_BANN_BAS |
| `bannDesc` | 배너설명 | `String` | 배너설명 | BANN_DESC | NMCP_BANN_BAS |
| `bannImg` | 배너이미지 | `String` | 배너이미지 | BANN_IMG | NMCP_BANN_BAS |
| `imgDesc` | 이미지설명(alt) | `String` | 이미지설명(alt) | IMG_DESC | NMCP_BANN_BAS |
| `linkTarget` | 링크타겟 | `String` | 링크타겟 | LINK_TARGET | NMCP_BANN_BAS |
| `linkUrlAdr` | 링크URL | `String` | 링크URL | LINK_URL_ADR | NMCP_BANN_BAS |
| `statVal` | 상태 | `String` | 상태 | STAT_VAL | NMCP_BANN_BAS |
| `prodNm` | 상품명 | `String` | 상품명 | PROD_NM | NMCP_BANN_BAS |
| `prodId` | 상품아이디 | `int` | 상품아이디 | PROD_ID | NMCP_BANN_BAS |
| `sntyProdSeq` | 단품상품일련번호 | `int` | 단품상품일련번호 | SNTY_PROD_SEQ | NMCP_BANN_BAS |
| `sbscrbTypeCd` | 가입유형코드 | `String` | 가입유형코드 | SBSCRB_TYPE_CD | NMCP_BANN_BAS |
| `agrmTypeCd` | 약정유형코드 | `String` | 약정유형코드 | AGRM_TYPE_CD | NMCP_BANN_BAS |
| `chrgPlanCd` | 요금제코드 | `String` | 요금제코드 | CHRG_PLAN_CD | NMCP_BANN_BAS |
| `indcOdrg` | 표시순서 | `String` | 표시순서 | INDC_ODRG | NMCP_BANN_BAS |
| `cretId` | 생성자아이디 | `String` | 생성자아이디 | CRET_ID | NMCP_BANN_BAS |
| `amdId` | 수정자아이디 | `String` | 수정자아이디 | AMD_ID | NMCP_BANN_BAS |
| `cretDt` | 생성일시 | `String` | 생성일시 | CRET_DT | NMCP_BANN_BAS |
| `atribValCd1` | 색상 | `String` | 색상 | ATRIB_VAL_CD_1 | NMCP_BANN_BAS |
| `atribValCd2` | 용량 | `String` | 용량 | ATRIB_VAL_CD_2 | NMCP_BANN_BAS |
| `rateCd` | 요금제코드 | `String` | 요금제코드 | RATE_CD | NMCP_BANN_BAS |
| `salePlcyCd` | 판매정책코드 | `String` | 판매정책코드 | SALE_PLCY_CD | NMCP_BANN_BAS |
| `newYn` | 가입유형 신규 | `String` | 가입유형 신규 | NEW_YN | NMCP_BANN_BAS |
| `mnpYn` | 가입유형 번호이동 | `String` | 가입유형 번호이동 | MNP_YN | NMCP_BANN_BAS |
| `hcnYn` | 가입유형 기기변경 | `String` | 가입유형 기기변경 | HCN_YN | NMCP_BANN_BAS |
| `agrmTrm` | 약정명 | `String` | 약정명 | AGRM_TRM | NMCP_BANN_BAS |
| `bgColor` | 배너배경색 | `String` | 배너배경색 | BG_COLOR | NMCP_BANN_BAS |
| `rateNm` | 요금제명 | `String` | 요금제명 | RATE_NM | MSP_RATE_MST |
| `atribValNm1` | 색상명 | `String` | 색상명 | ATRIB_VAL_NM_1 | NMCP_SNTY_PROD_BAS |
| `atribValNm2` | 용량명 | `String` | 용량명 | ATRIB_VAL_NM_2 | NMCP_SNTY_PROD_BAS |
| `sdoutYn` | 품절여부 | `String` | 품절여부 | SDOUT_YN | NMCP_SNTY_PROD_BAS |
| `amdDt` | 수정일시 | `String` | 수정일시 | AMD_DT | NMCP_BANN_BAS |
| `adDesc` | 광고문구 | `String` | 광고문구 | AD_DESC | NMCP_BANN_BAS |
| `eventType` | 이벤트배너타입 | `String` | 이벤트배너타입 이벤트:E,제휴:J,제휴카드:C | EVENT_TYPE | NMCP_BANN_BAS |
| `eventStartDt` | 이벤트시작일 | `String` | 이벤트시작일 | EVENT_START_DT | NMCP_BANN_BAS |
| `eventEndDt` | 이벤트종료일 | `String` | 이벤트종료일 | EVENT_END_DT | NMCP_BANN_BAS |
| `moreBannImg` | 더보기 배너이미지 | `String` | 더보기 배너이미지 | MORE_BANN_IMG | NMCP_BANN_BAS |

**SQL**

```sql
SELECT
PAY_CL_CD,
DATA_TYPE,
A.BANN_SEQ,
A.BANN_CTG,
A.BANN_TYPE,
A.BANN_NM,
A.BANN_DESC,
A.BANN_IMG,
A.IMG_DESC,
A.LINK_TARGET,
A.LINK_URL_ADR,
A.STAT_VAL,
A.PROD_NM,
A.PROD_ID,
A.SNTY_PROD_SEQ,
A.SBSCRB_TYPE_CD,
A.AGRM_TYPE_CD,
A.CHRG_PLAN_CD,
A.INDC_ODRG,
A.CRET_ID,
A.AMD_ID,
TO_CHAR (A.CRET_DT, 'YYYY.MM.DD') AS CRET_DT,
A.ATRIB_VAL_CD_1,
A.ATRIB_VAL_CD_2,
A.RATE_CD,
A.SALE_PLCY_CD,
A.NEW_YN,
A.MNP_YN,
A.HCN_YN,
A.AGRM_TRM,
A.BG_COLOR,
C.RATE_NM,
E.ATRIB_VAL_NM_1,
E.ATRIB_VAL_NM_2,
E.SDOUT_YN,
A.AMD_DT,
A.AD_DESC,
A.EVENT_TYPE, --이벤트배너타입 이벤트:E,제휴:J,제휴카드:C
A.EVENT_START_DT,
A.EVENT_END_DT,
A.MORE_BANN_IMG --더보기 배너이미지
FROM NMCP_BANN_BAS A,
MSP_SALE_AGRM_MST@DL_MSP B,
MSP_RATE_MST@DL_MSP C,
MSP_SALE_RATE_MST@DL_MSP D,
(select DISTINCT ATRIB_VAL_NM_1,
ATRIB_VAL_NM_2,
SDOUT_YN,
HNDSET_MODEL_ID,
b.BANN_SEQ
from  NMCP_SNTY_PROD_BAS a
, NMCP_BANN_BAS b
where a.HNDSET_MODEL_ID = b.SNTY_PROD_SEQ
AND A.PROD_ID = B.PROD_ID
and b.SNTY_PROD_SEQ <![CDATA[<>]]> 0
and b.SNTY_PROD_SEQ is not null
) E
WHERE     A.BANN_SEQ = #{bannSeq}
and a.BANN_SEQ = e.BANN_SEQ(+)
AND A.SALE_PLCY_CD = B.SALE_PLCY_CD(+)
AND A.AGRM_TRM = B.AGRM_TRM(+)
AND A.SALE_PLCY_CD = D.SALE_PLCY_CD(+)
AND A.RATE_CD = D.RATE_CD(+)
AND D.RATE_CD = C.RATE_CD(+)
<!-- AND A.SNTY_PROD_SEQ = E.HNDSET_MODEL_ID(+) -->
<!-- AND A.ATRIB_VAL_CD_1 = E.ATRIB_VAL_CD_1(+)
AND A.ATRIB_VAL_CD_2 = E.ATRIB_VAL_CD_2(+) -->
```


#### API_0019 — 조건에 해당하는 배너 리스트를 가져온다. (메인용)

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `listBannerBySubCtg` |
| 연동명(한글) | 조건에 해당하는 배너 리스트를 가져온다. (메인용) |
| 엔드포인트 | `/banner/listBannerBySubCtg` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | BannerMapper.xml |
| SQL ID | `listBannerBySubCtg` |
| parameterType | `com.ktmmobile.mcp.banner.dto.BannerDto` |
| resultType | `com.ktmmobile.mcp.banner.dto.BannerDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_RATE_MST, MSP_SALE_RATE_MST, CMN_GRP_CD_MST |
| 담당자 | 강채신 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `bannCtg` | 배너카테고리 | `String` | 배너카테고리 | BANN_CTG | NMCP_BANN_BAS |
| `cdGroupId` | 코드그룹아이디 | `String` | 값 = "MBN" | CD_GROUP_ID | NMCP_CD_DTL |
| `bannType` | 배너종류 | `String` | 배너종류 | BANN_TYPE | NMCP_BANN_BAS |
| `expnsnStrVal1` | 분류 | `String` | 분류 | EXPNSN_STR_VAL1 | NMCP_CD_DTL |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `bannSeq` | 배너일련번호 | `int` | 배너일련번호 | BANN_SEQ | NMCP_BANN_BAS |
| `bannCtg` | 배너카테고리 | `String` | 배너카테고리 | BANN_CTG | NMCP_BANN_BAS |
| `bannType` | 배너종류 | `String` | 배너종류 | BANN_TYPE | NMCP_BANN_BAS |
| `bannNm` | 배너명 | `String` | 배너명 | BANN_NM | NMCP_BANN_BAS |
| `bannDesc` | 배너설명 | `String` | 배너설명 | BANN_DESC | NMCP_BANN_BAS |
| `bannImg` | 배너이미지 | `String` | 배너이미지 | BANN_IMG | NMCP_BANN_BAS |
| `imgDesc` | 이미지설명(alt) | `String` | 이미지설명(alt) | IMG_DESC | NMCP_BANN_BAS |
| `linkTarget` | 링크타겟 | `String` | 링크타겟 | LINK_TARGET | NMCP_BANN_BAS |
| `linkUrlAdr` | 링크URL | `String` | 링크URL | LINK_URL_ADR | NMCP_BANN_BAS |
| `statVal` | 상태 | `String` | 상태 | STAT_VAL | NMCP_BANN_BAS |
| `prodNm` | 상품명 | `String` | 상품명 | PROD_NM | NMCP_BANN_BAS |
| `prodId` | 상품아이디 | `int` | 상품아이디 | PROD_ID | NMCP_BANN_BAS |
| `sntyProdSeq` | 단품상품일련번호 | `int` | 단품상품일련번호 | SNTY_PROD_SEQ | NMCP_BANN_BAS |
| `sbscrbTypeCd` | 가입유형코드 | `String` | 가입유형코드 | SBSCRB_TYPE_CD | NMCP_BANN_BAS |
| `agrmTypeCd` | 약정유형코드 | `String` | 약정유형코드 | AGRM_TYPE_CD | NMCP_BANN_BAS |
| `chrgPlanCd` | 요금제코드 | `String` | 요금제코드 | CHRG_PLAN_CD | NMCP_BANN_BAS |
| `cretId` | 생성자아이디 | `String` | 생성자아이디 | CRET_ID | NMCP_BANN_BAS |
| `amdId` | 수정자아이디 | `String` | 수정자아이디 | AMD_ID | NMCP_BANN_BAS |
| `cretDt` | 생성일시 | `String` | 생성일시 | CRET_DT | NMCP_BANN_BAS |
| `amdDt` | 수정일시 | `String` | 수정일시 | AMD_DT | NMCP_BANN_BAS |
| `indcOdrg` | 표시순서 | `String` | 표시순서 | INDC_ODRG | NMCP_BANN_BAS |
| `atribValCd1` | 색상 | `String` | 색상 | ATRIB_VAL_CD_1 | NMCP_BANN_BAS |
| `atribValCd2` | 용량 | `String` | 용량 | ATRIB_VAL_CD_2 | NMCP_BANN_BAS |
| `rateCd` | 요금제코드 | `String` | 요금제코드 | RATE_CD | NMCP_BANN_BAS |
| `salePlcyCd` | 판매정책코드 | `String` | 판매정책코드 | SALE_PLCY_CD | NMCP_BANN_BAS |
| `newYn` | 가입유형 신규 | `String` | 가입유형 신규 | NEW_YN | NMCP_BANN_BAS |
| `mnpYn` | 가입유형 번호이동 | `String` | 가입유형 번호이동 | MNP_YN | NMCP_BANN_BAS |
| `hcnYn` | 가입유형 기기변경 | `String` | 가입유형 기기변경 | HCN_YN | NMCP_BANN_BAS |
| `agrmTrm` | 약정명 | `String` | 약정명 | AGRM_TRM | NMCP_BANN_BAS |
| `bgColor` | 배너배경색 | `String` | 배너배경색 | BG_COLOR | NMCP_BANN_BAS |
| `adDesc` | 광고문구 | `String` | 광고문구 | AD_DESC | NMCP_BANN_BAS |
| `expnsnStrVal3` | 분류 3 | `String` | 분류 3 | EXPNSN_STR_VAL3 | NMCP_CD_DTL |
| `dtlCdNm` | 상세코드명 | `String` | 상세코드명 | DTL_CD_NM | NMCP_CD_DTL |
| `payClCd` | 선후불구분 | `String` | 선후불구분 | PAY_CL_CD | NMCP_BANN_BAS |
| `phoneYn` | 폰구분 | `String` | 폰구분 | PHONE_YN | NMCP_BANN_BAS |
| `eventType` | 이벤트배너타입 | `String` | 이벤트배너타입 이벤트:E,제휴:J,제휴카드:C | EVENT_TYPE | NMCP_BANN_BAS |
| `eventStartDt` | 이벤트시작일 | `String` | 이벤트시작일 | EVENT_START_DT | NMCP_BANN_BAS |
| `eventEndDt` | 이벤트종료일 | `String` | 이벤트종료일 | EVENT_END_DT | NMCP_BANN_BAS |
| `moreBannImg` | 더보기 배너이미지 | `String` | 더보기 배너이미지 | MORE_BANN_IMG | NMCP_BANN_BAS |

**SQL**

```sql
SELECT
A.BANN_SEQ,
A.BANN_CTG,
A.BANN_TYPE,
A.BANN_NM,
A.BANN_DESC,
A.BANN_IMG,
A.IMG_DESC,
A.LINK_TARGET,
A.LINK_URL_ADR,
A.STAT_VAL,
A.PROD_NM,
A.PROD_ID,
A.SNTY_PROD_SEQ,
A.SBSCRB_TYPE_CD,
A.AGRM_TYPE_CD,
A.CHRG_PLAN_CD,
A.CRET_ID,
A.AMD_ID,
A.CRET_DT,
A.AMD_DT,
A.INDC_ODRG,
A.ATRIB_VAL_CD_1,
A.ATRIB_VAL_CD_2,
A.RATE_CD,
A.SALE_PLCY_CD,
A.NEW_YN,
A.MNP_YN,
A.HCN_YN,
A.AGRM_TRM,
A.BG_COLOR,
A.AD_DESC,
B.EXPNSN_STR_VAL3,
B.DTL_CD_NM,
(SELECT RA.PAY_CL_CD    -- 선불 후불구분 PO 후불 PP선불
FROM MSP_RATE_MST@DL_MSP RA,
MSP_SALE_RATE_MST@DL_MSP SA,
CMN_GRP_CD_MST@DL_MSP GR
WHERE APPL_END_DT = '99991231'
AND RA.PAY_CL_CD = GR.CD_VAL
AND GR.GRP_ID='CMN0032'	--선불후불 구분 코트값의 그룹코드
AND SA.SALE_PLCY_CD = A.SALE_PLCY_CD
AND RA.RATE_CD = SA.RATE_CD
AND RA.RATE_CD = A.RATE_CD) as PAY_CL_CD,
(CASE WHEN (SELECT COUNT(*) FROM NMCP_PROD_BAS WHERE PROD_ID = A.PROD_ID) > 0 THEN 'Y' ELSE 'N' END) AS PHONE_YN,
A.EVENT_TYPE, --이벤트배너타입 이벤트:E,제휴:J,제휴카드:C
A.EVENT_START_DT,
A.EVENT_END_DT,
A.MORE_BANN_IMG
FROM
NMCP_BANN_BAS A,
NMCP_CD_DTL B
WHERE
1=1
AND CAST(NVL(A.INDC_ODRG,0) AS NUMERIC) >  0
<if test="bannCtg != null and bannCtg != ''">
AND A.BANN_CTG = #{bannCtg}
</if>
AND B.CD_GROUP_ID = #{cdGroupId}
AND A.BANN_CTG = B.DTL_CD
<if test="bannType != null and bannType != ''">
AND A.BANN_TYPE = #{bannType}
</if>
AND A.STAT_VAL = 'Y'
<if test="expnsnStrVal1 != null and expnsnStrVal1 != ''">
AND B.EXPNSN_STR_VAL1 = #{expnsnStrVal1}
ORDER BY A.INDC_ODRG, B.EXPNSN_STR_VAL2 DESC
</if>
<if test="expnsnStrVal1 eq null or expnsnStrVal1 eq ''">
ORDER BY A.INDC_ODRG
</if>
```


---

### COMMON 도메인 (3개)

#### API_0020 — getMspRateMst

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `getMspRateMst` |
| 엔드포인트 | `/common/mspRateMst` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | CommonMapper.xml |
| SQL ID | `getMspRateMst` |
| parameterType | `java.lang.String` |
| resultType | `com.ktmmobile.mcp.common.dto.MspRateMstDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_RATE_MST |
| 담당자 | 강채신 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `rateCd` | 요금제코드 | `String` | 요금제코드 | RATE_CD | MSP_RATE_MST |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `rateCd` | 요금제코드 | `String` | 요금제코드 | RATE_CD | MSP_RATE_MST |
| `applEndDt` | 적용종료일자 | `String` | 적용종료일자 | APPL_END_DT | MSP_RATE_MST |
| `applStrtDt` | 적용시작일자 | `String` | 적용시작일자 | APPL_STRT_DT | MSP_RATE_MST |
| `rateNm` | 요금제명 | `String` | 요금제명 | RATE_NM | MSP_RATE_MST |
| `rateGrpCd` | 요금제그룹코드 | `String` | 요금제그룹코드 | RATE_GRP_CD | MSP_RATE_MST |
| `payClCd` | 선후불구분 | `String` | 선후불구분 | PAY_CL_CD | MSP_RATE_MST |
| `rateType` | 요금제유형 | `String` | 요금제유형(ORG0008) | RATE_TYPE | MSP_RATE_MST |
| `dataType` | 데이터유형 | `String` | 데이터유형 (ORG0008) 'LTE,3G | DATA_TYPE | MSP_RATE_MST |
| `baseAmt` | 기본료 | `int` | 기본료 | BASE_AMT | MSP_RATE_MST |
| `freeCallClCd` | 망내외무료통화구분 | `String` | 망내외무료통화구분 | FREE_CALL_CL_CD | MSP_RATE_MST |
| `freeCallCnt` | 무료통화건수 | `String` | 무료통화건수 | FREE_CALL_CNT | MSP_RATE_MST |
| `nwInCallCnt` | 망내무료통화건수 | `String` | 망내무료통화건수 | NW_IN_CALL_CNT | MSP_RATE_MST |
| `nwOutCallCnt` | 망외무료통화건수 | `String` | 망외무료통화건수 | NW_OUT_CALL_CNT | MSP_RATE_MST |
| `freeSmsCnt` | 무료문자건수 | `String` | 무료문자건수 | FREE_SMS_CNT | MSP_RATE_MST |
| `freeDataCnt` | 무료데이터건수 | `String` | 무료데이터건수 | FREE_DATA_CNT | MSP_RATE_MST |
| `rmk` | 비고 | `String` | 비고 | RMK | MSP_RATE_MST |
| `regstId` | 등록자ID | `String` | 등록자ID | REGST_ID | MSP_RATE_MST |
| `regstDttm` | 등록일시 | `Date` | 등록일시 | REGST_DTTM | MSP_RATE_MST |
| `rvisnId` | 수정자ID | `String` | 수정자ID | RVISN_ID | MSP_RATE_MST |
| `rvisnDttm` | 수정일시 | `Date` | 수정일시 | RVISN_DTTM | MSP_RATE_MST |
| `onlineTypeCd` | 온라인유형코드 | `String` | 온라인유형코드 | ONLINE_TYPE_CD | MSP_RATE_MST |
| `alFlag` | 알요금제구분자 | `String` | 알요금제구분자 | AL_FLAG | MSP_RATE_MST |
| `serviceType` | 서비스유형 | `String` | 서비스유형 | SERVICE_TYPE | MSP_RATE_MST |
| `cmnt` | 문구 | `String` | 문구 | CMNT | MSP_RATE_MST |
| `sttcYn` |  | `String` |  | STTC_YN | MSP_RATE_MST |
| `onlineCanYn` | 해지 가능 여부 | `String` | 해지 가능 여부 | ONLINE_CAN_YN | MSP_RATE_MST |
| `canCmnt` | 해지 안내 문구 | `String` | 해지 안내 문구 | CAN_CMNT | MSP_RATE_MST |

**SQL**

```sql
SELECT
RATE_CD
,APPL_END_DT
,APPL_STRT_DT
,RATE_NM
,RATE_GRP_CD
,PAY_CL_CD
,RATE_TYPE
,DATA_TYPE
,BASE_AMT
,FREE_CALL_CL_CD
,FREE_CALL_CNT
,NW_IN_CALL_CNT
,NW_OUT_CALL_CNT
,FREE_SMS_CNT
,FREE_DATA_CNT
,RMK
,REGST_ID
,REGST_DTTM
,RVISN_ID
,RVISN_DTTM
,ONLINE_TYPE_CD
,AL_FLAG
,SERVICE_TYPE
,CMNT
,STTC_YN
,ONLINE_CAN_YN
,CAN_CMNT
FROM MSP_RATE_MST@dl_msp
WHERE RATE_CD = #{rateCd}
```


#### API_0021 — insertmspCommDatPrvTxn

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `insertmspCommDatPrvTxn` |
| 엔드포인트 | `/common/mspCommDatPrvTxn` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | CommonMapper.xml |
| SQL ID | `insertmspCommDatPrvTxn` |
| parameterType | `com.ktmmobile.mcp.common.dto.MspCommDatPrvTxnDto` |
| resultType | `java.lang.Integer` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | SQ_COMM_DAT_PRV_SEQ.NEXTVAL, MSP_COMM_DAT_PRV_TXN |
| 담당자 | 강채신 |
| 비고 | INSERT |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `apySeq` | 신청일련번호 | `int` | 신청일련번호 | APY_SEQ | MSP_COMM_DAT_PRV_TXN |
| `apyId` | 신청자ID | `String` | 신청자ID | APY_ID | MSP_COMM_DAT_PRV_TXN |
| `apyNm` | 신청자이름 | `String` | 신청자이름 | APY_NM | MSP_COMM_DAT_PRV_TXN |
| `bthday` | 생년월일 (YYYYMMDD) | `String` | 생년월일 (YYYYMMDD) | BTHDAY | MSP_COMM_DAT_PRV_TXN |
| `gender` | 성별 (1:남, 2:여) | `String` | 성별 (1:남, 2:여) | GENDER | MSP_COMM_DAT_PRV_TXN |
| `sbscPrdtCd` | 가입상품 (01:모바일) | `String` | 가입상품 (01:모바일) | SBSC_PRDT_CD | MSP_COMM_DAT_PRV_TXN |
| `cntcTelNo` | 연락처번호 | `String` | 연락처번호 | CNTC_TEL_NO | MSP_COMM_DAT_PRV_TXN |
| `tgtSvcNo` | 대상서비스번호 | `String` | 대상서비스번호 | TGT_SVC_NO | MSP_COMM_DAT_PRV_TXN |
| `recpEmail` | 수령이메일 | `String` | 수령이메일 | RECP_EMAIL | MSP_COMM_DAT_PRV_TXN |
| `confSbst01Yn` | 요청기간 | `String` | 요청기간 | CONF_SBST01_YN | MSP_COMM_DAT_PRV_TXN |
| `confSbst02Yn` | 제공일자 | `String` | 제공일자 | CONF_SBST02_YN | MSP_COMM_DAT_PRV_TXN |
| `confSbst03Yn` | 요청사유 | `String` | 요청사유 | CONF_SBST03_YN | MSP_COMM_DAT_PRV_TXN |
| `confSbst04Yn` | 제공내역 | `String` | 제공내역 | CONF_SBST04_YN | MSP_COMM_DAT_PRV_TXN |
| `myslfAthnCi` | 본인인증CI | `String` | 본인인증CI | MYSLF_ATHN_CI | MSP_COMM_DAT_PRV_TXN |

**SQL**

```sql
<selectKey keyProperty="apySeq" resultType="Integer" order="BEFORE">
SELECT SQ_COMM_DAT_PRV_SEQ.NEXTVAL@DL_MSP FROM DUAL
</selectKey>
INSERT INTO MSP_COMM_DAT_PRV_TXN@DL_MSP (
APY_SEQ                  --신청일련번호
, APY_ID                   --신청자ID
, APY_DT                   --신청일시
, APY_NM                   --신청자이름
, BTHDAY                   --생년월일 (YYYYMMDD)
, GENDER                   --성별 (1:남, 2:여)
, SBSC_PRDT_CD             --가입상품 (01:모바일)
, CNTC_TEL_NO              --연락처번호
, TGT_SVC_NO               --대상서비스번호
, RECP_EMAIL               --수령이메일
, CONF_SBST01_YN           --요청기간
, CONF_SBST02_YN           --제공일자
, CONF_SBST03_YN           --요청사유
, CONF_SBST04_YN           --제공내역
, MYSLF_ATHN_CI            --본인인증CI
) VALUES (
#{apySeq, jdbcType=NUMERIC}
, #{apyId}
, SYSDATE
, #{apyNm}
, #{bthday}
, #{gender}
, #{sbscPrdtCd}
, #{cntcTelNo}
, #{tgtSvcNo}
, #{recpEmail}
, #{confSbst01Yn}
, #{confSbst02Yn}
, #{confSbst03Yn}
, #{confSbst04Yn}
, #{myslfAthnCi}
)
```


#### API_0022 — getMspSmsTemplateMst

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `getMspSmsTemplateMst` |
| 엔드포인트 | `/common/mspSmsTemplateMst` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | CommonMapper.xml |
| SQL ID | `getMspSmsTemplateMst` |
| parameterType | `java.lang.Integer` |
| resultType | `com.ktmmobile.mcp.common.dto.MspSmsTemplateMstDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_SMS_TEMPLATE_MST |
| 담당자 | 강채신 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `templateId` | Template ID | `int` | Template ID | TEMPLATE_ID | MSP_SMS_TEMPLATE_MST |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `templateId` | Template ID | `int` | Template ID | TEMPLATE_ID | MSP_SMS_TEMPLATE_MST |
| `templateNm` | Template 명 | `String` | Template 명 | TEMPLATE_NM | MSP_SMS_TEMPLATE_MST |
| `templateDsc` | Template 상세 | `String` | Template 상세 | TEMPLATE_DSC | MSP_SMS_TEMPLATE_MST |
| `mgmtOrgnId` | 관리조직ID | `String` | 관리조직ID | MGMT_ORGN_ID | MSP_SMS_TEMPLATE_MST |
| `msgType` | 메시지 타입 | `String` | 메시지 타입(1:SMS) | MSG_TYPE | MSP_SMS_TEMPLATE_MST |
| `callback` | 발신자번호 | `String` | 발신자번호 | CALLBACK | MSP_SMS_TEMPLATE_MST |
| `text` | Template 내용 | `String` | Template 내용 | TEXT | MSP_SMS_TEMPLATE_MST |
| `regstId` | 등록자ID | `String` | 등록자ID | REGST_ID | MSP_SMS_TEMPLATE_MST |
| `regstDttm` | 등록일시 | `Date` | 등록일시 | REGST_DTTM | MSP_SMS_TEMPLATE_MST |
| `rvisnId` | 수정자ID | `String` | 수정자ID | RVISN_ID | MSP_SMS_TEMPLATE_MST |
| `rvisnDttm` | 수정일시 | `Date` | 수정일시 | RVISN_DTTM | MSP_SMS_TEMPLATE_MST |
| `expireHour` | 만료시간 | `int` | 만료시간 | EXPIRE_HOUR | MSP_SMS_TEMPLATE_MST |
| `subject` | 제목 | `String` | 제목 | SUBJECT | MSP_SMS_TEMPLATE_MST |
| `workType` | 업무구분 | `String` | 업무구분 | WORK_TYPE | MSP_SMS_TEMPLATE_MST |
| `retry` | 재시도횟수 | `int` | 재시도횟수 | RETRY | MSP_SMS_TEMPLATE_MST |
| `kTemplateCode` | 카카오 알림톡 템플릿 코드 | `String` | 카카오 알림톡 템플릿 코드 | K_TEMPLATE_CODE | MSP_SMS_TEMPLATE_MST |

**SQL**

```sql
SELECT /*Common_Query.getMspSmsTemplateMst  */
TEMPLATE_ID
,TEMPLATE_NM
,TEMPLATE_DSC
,MGMT_ORGN_ID
,MSG_TYPE
,CALLBACK
,TEXT
,REGST_ID
,REGST_DTTM
,RVISN_ID
,RVISN_DTTM
,EXPIRE_HOUR
,SUBJECT
,WORK_TYPE
,RETRY
,K_TEMPLATE_CODE
FROM MSP_SMS_TEMPLATE_MST@dl_msp
WHERE
TEMPLATE_ID = #{templateId}
```


---

### SMS 도메인 (3개)

#### API_0096 — selectMspApplyCount

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `selectMspApplyCount` |
| 엔드포인트 | `/sms/mspApplyCount` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | SmsMapper.xml |
| SQL ID | `selectMspApplyCount` |
| parameterType | `java.util.Map` |
| resultType | `java.lang.Integer` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_REQUEST, MSP_REQUEST_CSTMR, MSP_REQUEST_STATE |
| 담당자 | 강채신 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `cstmrName` | 고객명 | `Map<String>` | 고객명 | CSTMR_NAME | MSP_REQUEST_CSTMR |
| `cstmrMobileFn` | 핸드폰번호 | `Map<String>` | 핸드폰번호 | CSTMR_MOBILE_FN | MSP_REQUEST_CSTMR |
| `cstmrMobileMn` | 핸드폰번호 | `Map<String>` | 핸드폰번호 | CSTMR_MOBILE_MN | MSP_REQUEST_CSTMR |
| `cstmrMobileRn` | 핸드폰번호 | `Map<String>` | 핸드폰번호 | CSTMR_MOBILE_RN | MSP_REQUEST_CSTMR |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `count` | 조회개수 | `Map<String>` | 조회개수 | COUNT |  |

**SQL**

```sql
SELECT COUNT(*)
FROM
MSP_REQUEST@DL_MSP A
,MSP_REQUEST_CSTMR@DL_MSP B
,MSP_REQUEST_STATE@DL_MSP C
WHERE
B.CSTMR_NAME = #{cstmrName}
AND B.CSTMR_MOBILE_FN = #{cstmrMobileFn}
AND B.CSTMR_MOBILE_MN = #{cstmrMobileMn}
AND B.CSTMR_MOBILE_RN = #{cstmrMobileRn}
AND A.REQUEST_KEY = B.REQUEST_KEY
AND B.REQUEST_KEY = C.REQUEST_KEY
```


#### API_0097 — insertSms

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `insertSms` |
| 엔드포인트 | `/sms/addSms` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | SmsMapper.xml |
| SQL ID | `insertSms` |
| parameterType | `java.util.Map` |
| resultType | `java.lang.Integer` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | SMS_SEQ.NEXTVAL, MSG_QUEUE |
| 담당자 | 강채신 |
| 비고 | INSERT |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `SMS_SEQ` | 일련번호 | `Map<NUMERIC>` | 일련번호 | MSEQ | MSG_QUEUE |
| `I_SUBJECT` | 제목 | `Map<String>` | 제목 | SUBJECT | MSG_QUEUE |
| `I_MSG_TYPE` | 메시지유형 | `Map<String>` | 메시지유형 | MSG_TYPE | MSG_QUEUE |
| `I_CTN` | 휴대폰번호 | `Map<String>` | 휴대폰번호 | DSTADDR | MSG_QUEUE |
| `I_CALLBACK` | 콜백번호 | `Map<String>` | 콜백번호 | CALLBACK | MSG_QUEUE |
| `I_MSG` | 메시지 | `Map<String>` | 메시지 | TEXT | MSG_QUEUE |

**SQL**

```sql
<selectKey  keyProperty="SMS_SEQ" resultType="string" order="BEFORE">
SELECT SMS_SEQ.NEXTVAL@DL_MSP FROM DUAL
</selectKey >
INSERT INTO MSG_QUEUE@DL_MSP (
MSEQ,
<if test="I_SUBJECT != null and I_SUBJECT != ''">
SUBJECT ,
</if>
MSG_TYPE,
DSTADDR,
CALLBACK,
STAT,
TEXT,
EXPIRETIME,
REQUEST_TIME,
OPT_ID
) VALUES (
#{SMS_SEQ, jdbcType=NUMERIC},
<if test="I_SUBJECT != null and I_SUBJECT != ''">
#{I_SUBJECT},
</if>
#{I_MSG_TYPE},
#{I_CTN},
#{I_CALLBACK},
0,
#{I_MSG},
7200,
SYSDATE,
'MCP'
)
```


#### API_0098 — insertKakaoNoti

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `insertKakaoNoti` |
| 엔드포인트 | `/sms/addKakaoNoti` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | SmsMapper.xml |
| SQL ID | `insertKakaoNoti` |
| parameterType | `java.util.Map` |
| resultType | `java.lang.Integer` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | SMS_SEQ.NEXTVAL, MSG_QUEUE |
| 담당자 | 강채신 |
| 비고 | INSERT |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `SMS_SEQ` | 일련번호 | `Map<NUMERIC>` | 일련번호 | MSEQ | MSG_QUEUE |
| `I_SUBJECT` | 제목 | `Map<String>` | 제목 | SUBJECT | MSG_QUEUE |
| `I_MSG_TYPE` | 메시지유형 | `Map<String>` | 메시지유형 | MSG_TYPE | MSG_QUEUE |
| `I_CTN` | 휴대폰번호 | `Map<String>` | 휴대폰번호 | DSTADDR | MSG_QUEUE |
| `I_CALLBACK` | 콜백번호 | `Map<String>` | 콜백번호 | CALLBACK | MSG_QUEUE |
| `I_MSG` | 메시지 | `Map<String>` | 메시지 | TEXT | MSG_QUEUE |
| `I_MSG` | 메시지 | `Map<String>` | 메시지 | TEXT2 | MSG_QUEUE |
| `I_TEMPLATE_CD` | 탬플릿번호 | `Map<String>` | 탬플릿번호 | K_TEMPLATE_CODE | MSG_QUEUE |
| `I_NEXT_TYPE` | 다음메시지유형 | `Map<String>` | 다음메시지유형 | K_NEXT_TYPE | MSG_QUEUE |
| `I_SENDER_KEY` | 발송키 | `Map<String>` | 발송키 | SENDER_KEY | MSG_QUEUE |

**SQL**

```sql
<selectKey  keyProperty="SMS_SEQ" resultType="string" order="BEFORE">
SELECT SMS_SEQ.NEXTVAL@DL_MSP FROM DUAL
</selectKey >
INSERT INTO MSG_QUEUE@DL_MSP (
MSEQ,
<if test="I_SUBJECT != null and I_SUBJECT != ''">
SUBJECT ,
</if>
MSG_TYPE,
DSTADDR,
CALLBACK,
TEXT,
EXPIRETIME,
REQUEST_TIME,
OPT_ID,
TEXT2,
K_TEMPLATE_CODE,
K_NEXT_TYPE,
SENDER_KEY
) VALUES (
#{SMS_SEQ, jdbcType=NUMERIC},
<if test="I_SUBJECT != null and I_SUBJECT != ''">
#{I_SUBJECT},
</if>
#{I_MSG_TYPE},
#{I_CTN},
#{I_CALLBACK},
#{I_MSG},
7200,
SYSDATE,
'MCP',
#{I_MSG},
#{I_TEMPLATE_CD},
#{I_NEXT_TYPE},
#{I_SENDER_KEY}
)
```


---

### ORDER 도메인 (2개)

#### API_0074 — selectOrderList

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `selectOrderList` |
| 엔드포인트 | `/order/orderList` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | OrderMapper.xml |
| SQL ID | `selectOrderList` |
| parameterType | `com.ktmmobile.mcp.order.dto.OrderDto` |
| resultType | `com.ktmmobile.mcp.order.dto.OrderDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_RATE_MST |
| 담당자 | 강채신 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `cstmrName` | 이름 | `String` | 이름 | CSTMR_NAME | MCP_REQUEST_CSTMR |
| `cstmrMobileFn` | 핸드폰번호 | `String` | 핸드폰번호 | CSTMR_MOBILE_FN | MCP_REQUEST_CSTMR |
| `cstmrMobileMn` | 핸드폰번호 | `String` | 핸드폰번호 | CSTMR_MOBILE_MN | MCP_REQUEST_CSTMR |
| `cstmrMobileRn` | 핸드폰번호 | `String` | 핸드폰번호 | CSTMR_MOBILE_RN | MCP_REQUEST_CSTMR |
| `cretId` | 생성자아이디 | `String` | 생성자아이디 | CRET_ID | MCP_REQUEST |
| `searchStart` | 주문검색시작일 | `String` | 주문검색시작일 | REQ_IN_DAY | MCP_REQUEST |
| `searchEnd` | 주문검색종료일 | `String` | 주문검색종료일 | REQ_IN_DAY | MCP_REQUEST |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `requestKey` | 가입신청_키 | `String` | 가입신청_키 | REQUEST_KEY | MCP_REQUEST |
| `resNo` | 대표번호 | `String` | 대표번호 | RES_NO | MCP_REQUEST |
| `reqBuyType` | 구매유형 단말 | `String` | 구매유형 단말      * 구매:MM      * USIM(유심)단독 구매:UU | REQ_BUY_TYPE | MCP_REQUEST |
| `prodId` | 상품아이디 | `String` | 상품아이디 | PROD_ID | MCP_REQUEST |
| `requestStateCode` | 상태코드 | `String` | 상태코드 | REQUEST_STATE_CODE | MCP_REQUEST |
| `pstate` | 상태 | `String` | 상태 | PSTATE | MCP_REQUEST |
| `prodId` | 상품아이디 | `String` | 상품아이디 | PROD_ID | MCP_REQUEST |
| `sntyColorCd` | 새상 | `String` | 새상 | SNTY_COLOR_CD | MCP_REQUEST |
| `cstmrName` | 이름 | `String` | 이름 | CSTMR_NAME | MCP_REQUEST_CSTMR |
| `cstmrMobileFn` | 핸드폰번호 | `String` | 핸드폰번호 | CSTMR_MOBILE_FN | MCP_REQUEST_CSTMR |
| `cstmrMobileMn` | 핸드폰번호 | `String` | 핸드폰번호 | CSTMR_MOBILE_MN | MCP_REQUEST_CSTMR |
| `cstmrMobileRn` | 핸드폰번호 | `String` | 핸드폰번호 | CSTMR_MOBILE_RN | MCP_REQUEST_CSTMR |
| `tbCd` |  | `String` |  | TB_CD | MCP_REQUEST_STATE |
| `dlvryNo` | 배송번호 | `String` | 배송번호 | DLVRY_NO | MCP_REQUEST_STATE |
| `sysRdate` | 입력일 | `String` | 입력일 | TO_CHAR(C.SYS_RDATE,'YYYYMMDD') AS SYS_RDATE | MCP_REQUEST_STATE |
| `modelPrice` |  | `String` |  | NVL(D.MODEL_PRICE,0) AS MODEL_PRICE | MCP_REQUEST_SALEINFO |
| `settlAmt` |  | `String` |  | NVL(D.SETTL_AMT,0) AS SETTL_AMT | MCP_REQUEST_SALEINFO |
| `prodNm` | 상품명 | `String` | 상품명 | DECODE(A.REQ_BUY_TYPE,'MM',F.PROD_NM ,'UU','유심')AS PROD_NM | MCP_REQUEST |
| `listShowText` |  | `String` |  | DECODE(A.REQ_BUY_TYPE,'MM',F.LIST_SHOW_TEXT,
                   'UU',
               (SELECT RATE_NM FROM MSP_RATE_MST@DL_MSP WHERE RATE_CD=D.SOC_CODE)
           )AS LIST_SHOW_TEXT | MCP_REQUEST |
| `imgPath` |  | `String` |  | DECODE(A.REQ_BUY_TYPE,'MM',
                (SELECT IMG_PATH FROM NMCP_PROD_IMG_DTL WHERE PROD_ID = F.PROD_ID AND A.SNTY_COLOR_CD = SNTY_COLOR_CD AND IMG_TYPE_CD='01')
                ,'UU',
                (SELECT IMG_PATH FROM NMCP_USIM_BAS WHERE DATA_TYPE = (SELECT DATA_TYPE FROM MSP_RATE_MST@DL_MSP WHERE RATE_CD=D.SOC_CODE) AND ROWNUM = 1)
           )AS IMG_PATH | MCP_REQUEST |
| `rateNm` | 요금제명 | `String` | 요금제명 | (SELECT RATE_NM FROM MSP_RATE_MST@DL_MSP WHERE RATE_CD=D.SOC_CODE)AS RATE_NM | MSP_RATE_MST |
| `prdtSctnCd` | 제품구분코드 | `String` | 제품구분코드(02:3G , 03:LTE , 5G ) | (SELECT DATA_TYPE FROM MSP_RATE_MST@DL_MSP WHERE RATE_CD=D.SOC_CODE)AS PRDT_SCTN_CD | MSP_RATE_MST |
| `prdtSctnCd` | 제품구분코드 | `String` | 제품구분코드(02:3G , 03:LTE , 5G ) | NVL(A.PROD_TYPE,'01')AS PROD_TYPE | MCP_REQUEST |

**SQL**

```sql
SELECT
A.REQUEST_KEY
,A.RES_NO
,A.REQ_BUY_TYPE
,A.PROD_ID
,B.CSTMR_NAME
,B.CSTMR_MOBILE_FN
,B.CSTMR_MOBILE_MN
,B.CSTMR_MOBILE_RN
,C.TB_CD
,C.DLVRY_NO
,A.REQUEST_STATE_CODE
,A.PSTATE
,A.PROD_ID
,TO_CHAR(C.SYS_RDATE,'YYYYMMDD') AS SYS_RDATE
,NVL(D.MODEL_PRICE,0) AS MODEL_PRICE
,NVL(D.SETTL_AMT,0) AS SETTL_AMT
,DECODE(A.REQ_BUY_TYPE,'MM',F.PROD_NM ,'UU','유심')AS PROD_NM
,DECODE(A.REQ_BUY_TYPE,'MM',F.LIST_SHOW_TEXT,
'UU',
(SELECT RATE_NM FROM MSP_RATE_MST@DL_MSP WHERE RATE_CD=D.SOC_CODE)
)AS LIST_SHOW_TEXT
,A.SNTY_COLOR_CD
,DECODE(A.REQ_BUY_TYPE,'MM',
(SELECT IMG_PATH FROM NMCP_PROD_IMG_DTL WHERE PROD_ID = F.PROD_ID AND A.SNTY_COLOR_CD = SNTY_COLOR_CD AND IMG_TYPE_CD='01')
,'UU',
(SELECT IMG_PATH FROM NMCP_USIM_BAS WHERE DATA_TYPE = (SELECT DATA_TYPE FROM MSP_RATE_MST@DL_MSP WHERE RATE_CD=D.SOC_CODE) AND ROWNUM = 1)
)AS IMG_PATH
,(SELECT RATE_NM FROM MSP_RATE_MST@DL_MSP WHERE RATE_CD=D.SOC_CODE)AS RATE_NM
,(SELECT DATA_TYPE FROM MSP_RATE_MST@DL_MSP WHERE RATE_CD=D.SOC_CODE)AS PRDT_SCTN_CD
,NVL(A.PROD_TYPE,'01')AS PROD_TYPE
FROM
MCP_REQUEST A
,MCP_REQUEST_CSTMR B
,MCP_REQUEST_STATE C
,MCP_REQUEST_SALEINFO D
,MCP_REQUEST_DLVRY E
,NMCP_PROD_BAS F
WHERE 1=1
<if test="cretId ==null or cretId ==''">
AND( (B.CSTMR_NAME = #{cstmrName} AND B.CSTMR_MOBILE_FN || B.CSTMR_MOBILE_MN || B.CSTMR_MOBILE_RN = #{cstmrMobileFn}||#{cstmrMobileMn}||#{cstmrMobileRn} )
OR ( E.DLVRY_NAME =  #{cstmrName} AND E.DLVRY_MOBILE_FN || E.DLVRY_MOBILE_MN || E.DLVRY_MOBILE_RN = #{cstmrMobileFn}||#{cstmrMobileMn}||#{cstmrMobileRn}  ))
</if>
<if test="cretId !=null and cretId !=''">
AND A.CRET_ID = #{cretId}
</if>
AND A.REQUEST_KEY = B.REQUEST_KEY
AND B.REQUEST_KEY = C.REQUEST_KEY
AND A.REQUEST_KEY = D.REQUEST_KEY
AND A.REQUEST_KEY = E.REQUEST_KEY
AND A.PROD_ID = F.PROD_ID(+)
AND A.REQ_IN_DAY BETWEEN  TO_DATE(#{searchStart}||'000000', 'YYYYMMDDHH24MISS') AND TO_DATE(#{searchEnd}||'235959', 'YYYYMMDDHH24MISS')
ORDER BY A.REQUEST_KEY DESC
```


#### API_0075 — selectOrderView

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `selectOrderView` |
| 엔드포인트 | `/order/orderView` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | OrderMapper.xml |
| SQL ID | `selectOrderView` |
| parameterType | `com.ktmmobile.mcp.order.dto.OrderDto` |
| resultType | `com.ktmmobile.mcp.order.dto.OrderDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_RATE_MST, MSP_SALE_SUBSD_MST, CMN_GRP_CD_MST, MSP_SALE_PLCY_MST |
| 담당자 | 강채신 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `requestKey` | 가입신청_키 | `String` | 가입신청_키 | REQUEST_KEY | MCP_REQUEST |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `resNo` | 대표번호 | `String` | 대표번호 | RES_NO | MCP_REQUEST |
| `cntpntShopId` | 접점코드 | `String` | 접점코드 | CNTPNT_SHOP_ID | MCP_REQUEST |
| `reqPayType` |  | `String` |  | REQ_PAY_TYPE | MCP_REQUEST |
| `reqBuyType` | 구매유형 단말 | `String` | 구매유형 단말      * 구매:MM      * USIM(유심)단독 구매:UU | REQ_BUY_TYPE | MCP_REQUEST |
| `operType` | 업무구분 | `String` | 업무구분      * HCN3 : 기기변경      * HDN3 : 보상기변3G      * MNP3 : 번호이동      * NAC3 : 신규 | OPER_TYPE | MCP_REQUEST |
| `sysRdate` | 입력일 | `String` | 입력일 | TO_CHAR(A.SYS_RDATE,'YYYYMMDD') AS SYS_RDATE | MCP_REQUEST |
| `requestStateCode` | 상태코드 | `String` | 상태코드 | REQUEST_STATE_CODE | MCP_REQUEST |
| `pstate` | 상태 | `String` | 상태 | PSTATE | MCP_REQUEST |
| `prodId` | 상품아이디 | `String` | 상품아이디 | PROD_ID | MCP_REQUEST |
| `reqModelColor` | 모델색상 | `String` | 모델색상 | REQ_MODEL_COLOR | MCP_REQUEST |
| `sntyColorCd` | 새상 | `String` | 새상 | SNTY_COLOR_CD | MCP_REQUEST |
| `prodNm` | 상품명 | `String` | 상품명 | DECODE(A.REQ_BUY_TYPE,'MM',F.PROD_NM ,'UU','유심')AS PROD_NM | MCP_REQUEST |
| `cstmrName` | 이름 | `String` | 이름 | CSTMR_NAME | MCP_REQUEST_CSTMR |
| `cstmrMail` |  | `String` |  | CSTMR_MAIL | MCP_REQUEST_CSTMR |
| `cstmrMobileFn` | 핸드폰번호 | `String` | 핸드폰번호 | NVL(B.CSTMR_MOBILE_FN, B.CSTMR_TEL_FN) AS CSTMR_MOBILE_FN | MCP_REQUEST_CSTMR |
| `cstmrMobileMn` | 핸드폰번호 | `String` | 핸드폰번호 | NVL(B.CSTMR_MOBILE_MN, B.CSTMR_TEL_MN) AS CSTMR_MOBILE_MN | MCP_REQUEST_CSTMR |
| `cstmrMobileRn` | 핸드폰번호 | `String` | 핸드폰번호 | NVL(B.CSTMR_MOBILE_RN, B.CSTMR_TEL_RN) AS CSTMR_MOBILE_RN | MCP_REQUEST_CSTMR |
| `tbCd` |  | `String` |  | TB_CD | MCP_REQUEST_STATE |
| `dlvryNo` | 배송번호 | `String` | 배송번호 | DLVRY_NO | MCP_REQUEST_STATE |
| `modelInstallment` |  | `String` |  | NVL(D.MODEL_INSTALLMENT,0) AS MODEL_INSTALLMENT | MCP_REQUEST_SALEINFO |
| `modelPrice` |  | `String` |  | NVL(D.MODEL_PRICE,0) AS MODEL_PRICE | MCP_REQUEST_SALEINFO |
| `modelPriceVat` |  | `String` |  | NVL(D.MODEL_PRICE_VAT,0) AS MODEL_PRICE_VAT | MCP_REQUEST_SALEINFO |
| `modelDiscount1` |  | `String` |  | NVL(D.MODEL_DISCOUNT1,0) AS MODEL_DISCOUNT1 | MCP_REQUEST_SALEINFO |
| `modelDiscount2` |  | `String` |  | NVL(D.MODEL_DISCOUNT2,0) AS MODEL_DISCOUNT2 | MCP_REQUEST_SALEINFO |
| `modelDiscount3` |  | `String` |  | NVL(D.MODEL_DISCOUNT3,0) AS MODEL_DISCOUNT3 | MCP_REQUEST_SALEINFO |
| `maxDiscount3` |  | `String` |  | NVL(D.MAX_DISCOUNT3,0) AS MAX_DISCOUNT3 | MCP_REQUEST_SALEINFO |
| `modelMonthly` | 단말할부개월수 | `String` | 단말할부개월수 | NVL(D.MODEL_MONTHLY,0) AS MODEL_MONTHLY | MCP_REQUEST_SALEINFO |
| `enggMnthCnt` | 약정개월수 | `String` | 약정개월수 | ENGG_MNTH_CNT | MCP_REQUEST_SALEINFO |
| `joinPrice` |  | `String` |  | .JOIN_PRICE | MCP_REQUEST_SALEINFO |
| `usimPrice` |  | `String` |  | USIM_PRICE | MCP_REQUEST_SALEINFO |
| `sprtTp` | 지원금유형 | `String` | 지원금유형 (단말할인:KD , 요금할인:PM)      * 단말할인:KD      * 요금할인:PM      * 심플할인:SM   (유심, 약정 | NVL(D.SPRT_TP,'KD') AS SPRT_TP | MCP_REQUEST_SALEINFO |
| `settlAmt` |  | `String` |  | NVL(D.SETTL_AMT,0) AS SETTL_AMT | MCP_REQUEST_SALEINFO |
| `modelSalePolicyCode` | 정책코드 | `String` | 정책코드 | MODEL_SALE_POLICY_CODE | MCP_REQUEST_SALEINFO |
| `reqBank` |  | `String` |  | REQ_BANK | MCP_REQUEST_REQ |
| `reqAccountNumber` |  | `String` |  | REQ_ACCOUNT_NUMBER | MCP_REQUEST_REQ |
| `reqCardCompany` |  | `String` |  | REQ_CARD_COMPANY | MCP_REQUEST_REQ |
| `reqCardNo` |  | `String` |  | REQ_CARD_NO | MCP_REQUEST_REQ |
| `dlvryPost` |  | `String` |  | DLVRY_POST | MCP_REQUEST_DLVRY |
| `dlvryAddr` |  | `String` |  | DLVRY_ADDR | MCP_REQUEST_DLVRY |
| `dlvryAddrDtl` |  | `String` |  | DLVRY_ADDR_DTL | MCP_REQUEST_DLVRY |
| `rateNm` | 요금제명 | `String` | 요금제명 | (SELECT RATE_NM FROM MSP_RATE_MST@DL_MSP WHERE RATE_CD=D.SOC_CODE)AS RATE_NM | MSP_RATE_MST |
| `listShowText` |  | `String` |  | DECODE(A.REQ_BUY_TYPE,'MM',F.LIST_SHOW_TEXT,
                   'UU',
               (SELECT RATE_NM FROM MSP_RATE_MST@DL_MSP WHERE RATE_CD=D.SOC_CODE)
           )AS LIST_SHOW_TEXT | MSP_RATE_MST |
| `instCmsn` | 할부수수료(VAT포함) | `String` | 할부수수료(VAT포함) | DECODE(A.REQ_BUY_TYPE,'MM',
               (
                    SELECT
                            CASE WHEN TRUNC(
                                (SUBSD.HNDST_AMT - ( SUBSD.SUBSD_AMT + SUBSD.AGNCY_SUBSD_AMT ) ) *
                                NVL( PLCY.INST_RATE, CD.ETC1 ) / 100)   <![CDATA[<]]>   0
                            THEN 0
                            ELSE TRUNC(
                                (SUBSD.HNDST_AMT - ( SUBSD.SUBSD_AMT + SUBSD.AGNCY_SUBSD_AMT ) ) *
                                NVL( PLCY.INST_RATE, CD.ETC1 ) / 100)
                            END
                            AS INST_CMSN /* 할부수수료 */
                    FROM
                         MSP_SALE_SUBSD_MST@DL_MSP SUBSD ,
                         CMN_GRP_CD_MST@DL_MSP CD ,
                         MSP_SALE_PLCY_MST@DL_MSP PLCY ,
                         MCP_REQUEST MCP , MCP_REQUEST_SALEINFO SALE

                    WHERE 1=1
                    AND MCP.REQUEST_KEY = #{requestKey}
                    AND MCP.REQUEST_KEY = SALE.REQUEST_KEY
                    AND SUBSD.SALE_PLCY_CD  = SALE.MODEL_SALE_POLICY_CODE           /* 정책코드, 필수 */
                    AND SUBSD.OPER_TYPE     = MCP.OPER_TYPE    /* 개통유형, 필수 */
                    AND SUBSD.PRDT_ID       = SALE.MODEL_ID                /* 제품ID, 필수 */
                    AND SUBSD.ORGN_ID       = #{orgnId}                 /* 조직ID, 선택 */
                    AND SUBSD.RATE_CD       = SALE.SOC_CODE                  /* 요금제, 선택 */
                    AND SUBSD.AGRM_TRM      = SALE.ENGG_MNTH_CNT    /* 약정기간, 선택 */
                    AND CD.GRP_ID           = 'CMN0051'
                    AND CD.CD_VAL           = '20'
                    AND PLCY.SALE_PLCY_CD   = SUBSD.SALE_PLCY_CD
                    AND PLCY.CNFM_YN        = 'Y'
                ),
                'UU','0'
            )AS INST_CMSN | MCP_REQUEST_SALEINFO |
| `dcAmt` | 할인금액(약정할인선택시 할인금액) | `String` | 할인금액(약정할인선택시 할인금액) | NVL(D.DC_AMT,0) AS DC_AMT | MCP_REQUEST_SALEINFO |
| `addDcAmt` | 추가할인금액(요금할인선택시 할인금액  단말할인과는 반대된다(단말할인시 공시지원금 대리금 지원금 지원)) | `String` | 추가할인금액(요금할인선택시 할인금액  단말할인과는 반대된다(단말할인시 공시지원금 대리금 지원금 지원)) | NVL(D.ADD_DC_AMT,0) AS ADD_DC_AMT | MCP_REQUEST_SALEINFO |
| `prdtSctnCd` | 제품구분코드 | `String` | 제품구분코드 | (SELECT DATA_TYPE FROM MSP_RATE_MST@DL_MSP WHERE RATE_CD=D.SOC_CODE)AS PRDT_SCTN_CD | MSP_RATE_MST |
| `imgPath` |  | `String` |  | DECODE(A.REQ_BUY_TYPE,'MM',
                (SELECT IMG_PATH FROM NMCP_PROD_IMG_DTL WHERE PROD_ID = F.PROD_ID AND A.SNTY_COLOR_CD = SNTY_COLOR_CD AND IMG_TYPE_CD='01')
                ,'UU',
                (SELECT IMG_PATH FROM NMCP_USIM_BAS WHERE DATA_TYPE = (SELECT DATA_TYPE FROM MSP_RATE_MST@DL_MSP WHERE RATE_CD=D.SOC_CODE) AND ROWNUM = 1)
           )AS IMG_PATH | MCP_REQUEST |
| `baseAmt` | 기본료 | `String` | 기본료 | (SELECT NVL(BASE_AMT,0) FROM MSP_RATE_MST@DL_MSP WHERE RATE_CD=D.SOC_CODE) AS BASE_AMT | MSP_RATE_MST |
| `socCode` | 요금제코드 | `String` | 요금제코드 | SOC_CODE | MCP_REQUEST_SALEINFO |

**SQL**

```sql
SELECT
A.RES_NO,
MODEL_SALE_POLICY_CODE,
A.CNTPNT_SHOP_ID ,
A.REQ_PAY_TYPE,
A.REQ_BUY_TYPE,
A.OPER_TYPE,
TO_CHAR(A.SYS_RDATE,'YYYYMMDD') AS SYS_RDATE,
A.REQUEST_STATE_CODE,
A.PSTATE,
A.PROD_ID,
A.REQ_MODEL_COLOR,
A.SNTY_COLOR_CD,
B.CSTMR_NAME,
B.CSTMR_MAIL,
NVL(B.CSTMR_MOBILE_FN, B.CSTMR_TEL_FN) AS CSTMR_MOBILE_FN,
NVL(B.CSTMR_MOBILE_MN, B.CSTMR_TEL_MN) AS CSTMR_MOBILE_MN,
NVL(B.CSTMR_MOBILE_RN, B.CSTMR_TEL_RN) AS CSTMR_MOBILE_RN,
C.TB_CD,
C.DLVRY_NO,
NVL(D.MODEL_INSTALLMENT,0) AS MODEL_INSTALLMENT,
NVL(D.MODEL_PRICE,0) AS MODEL_PRICE,
NVL(D.MODEL_PRICE_VAT,0) AS MODEL_PRICE_VAT,
NVL(D.MODEL_DISCOUNT1,0) AS MODEL_DISCOUNT1,
NVL(D.MODEL_DISCOUNT2,0) AS MODEL_DISCOUNT2,
NVL(D.MODEL_DISCOUNT3,0) AS MODEL_DISCOUNT3,
NVL(D.MAX_DISCOUNT3,0) AS MAX_DISCOUNT3,
NVL(D.MODEL_MONTHLY,0) AS MODEL_MONTHLY,
D.ENGG_MNTH_CNT,
D.JOIN_PRICE,
D.USIM_PRICE,
NVL(D.SPRT_TP,'KD') AS SPRT_TP,
NVL(D.SETTL_AMT,0) AS SETTL_AMT,
DECODE(A.REQ_BUY_TYPE,'MM',F.PROD_NM ,'UU','유심')AS PROD_NM,
E.REQ_BANK,
E.REQ_ACCOUNT_NUMBER,
E.REQ_CARD_COMPANY,
E.REQ_CARD_NO,
H.DLVRY_POST,
H.DLVRY_ADDR,
H.DLVRY_ADDR_DTL,
(SELECT RATE_NM FROM MSP_RATE_MST@DL_MSP WHERE RATE_CD=D.SOC_CODE)AS RATE_NM,
DECODE(A.REQ_BUY_TYPE,'MM',F.LIST_SHOW_TEXT,
'UU',
(SELECT RATE_NM FROM MSP_RATE_MST@DL_MSP WHERE RATE_CD=D.SOC_CODE)
)AS LIST_SHOW_TEXT,
DECODE(A.REQ_BUY_TYPE,'MM',
(
SELECT
CASE WHEN TRUNC(
(SUBSD.HNDST_AMT - ( SUBSD.SUBSD_AMT + SUBSD.AGNCY_SUBSD_AMT ) ) *
NVL( PLCY.INST_RATE, CD.ETC1 ) / 100)   <![CDATA[<]]>   0
THEN 0
ELSE TRUNC(
(SUBSD.HNDST_AMT - ( SUBSD.SUBSD_AMT + SUBSD.AGNCY_SUBSD_AMT ) ) *
NVL( PLCY.INST_RATE, CD.ETC1 ) / 100)
END
AS INST_CMSN /* 할부수수료 */
FROM
MSP_SALE_SUBSD_MST@DL_MSP SUBSD ,
CMN_GRP_CD_MST@DL_MSP CD ,
MSP_SALE_PLCY_MST@DL_MSP PLCY ,
MCP_REQUEST MCP , MCP_REQUEST_SALEINFO SALE
WHERE 1=1
AND MCP.REQUEST_KEY = #{requestKey}
AND MCP.REQUEST_KEY = SALE.REQUEST_KEY
AND SUBSD.SALE_PLCY_CD  = SALE.MODEL_SALE_POLICY_CODE           /* 정책코드, 필수 */
AND SUBSD.OPER_TYPE     = MCP.OPER_TYPE    /* 개통유형, 필수 */
AND SUBSD.PRDT_ID       = SALE.MODEL_ID                /* 제품ID, 필수 */
AND SUBSD.ORGN_ID       = #{orgnId}                 /* 조직ID, 선택 */
AND SUBSD.RATE_CD       = SALE.SOC_CODE                  /* 요금제, 선택 */
AND SUBSD.AGRM_TRM      = SALE.ENGG_MNTH_CNT    /* 약정기간, 선택 */
AND CD.GRP_ID           = 'CMN0051'
AND CD.CD_VAL           = '20'
AND PLCY.SALE_PLCY_CD   = SUBSD.SALE_PLCY_CD
AND PLCY.CNFM_YN        = 'Y'
),
'UU','0'
)AS INST_CMSN,
NVL(D.DC_AMT,0) AS DC_AMT,
NVL(D.ADD_DC_AMT,0) AS ADD_DC_AMT,
(SELECT DATA_TYPE FROM MSP_RATE_MST@DL_MSP WHERE RATE_CD=D.SOC_CODE)AS PRDT_SCTN_CD
,
DECODE(A.REQ_BUY_TYPE,'MM',
(SELECT IMG_PATH FROM NMCP_PROD_IMG_DTL WHERE PROD_ID = F.PROD_ID AND A.SNTY_COLOR_CD = SNTY_COLOR_CD AND IMG_TYPE_CD='01')
,'UU',
(SELECT IMG_PATH FROM NMCP_USIM_BAS WHERE DATA_TYPE = (SELECT DATA_TYPE FROM MSP_RATE_MST@DL_MSP WHERE RATE_CD=D.SOC_CODE) AND ROWNUM = 1)
)AS IMG_PATH
,
(SELECT NVL(BASE_AMT,0) FROM MSP_RATE_MST@DL_MSP WHERE RATE_CD=D.SOC_CODE) AS BASE_AMT
,D.SOC_CODE
-- ... (이하 생략)
```


---

### PREPIA 도메인 (2개)

#### API_0091 — selectRatePrepia

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `selectRatePrepia` |
| 엔드포인트 | `/prepia/ratePrepia` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | PrepiaMapper.xml |
| SQL ID | `selectRatePrepia` |
| parameterType | `` |
| resultType | `com.ktmmobile.mcp.mspservice.dto.MspSaleSubsdMstDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_RATE_MST, MSP_RATE_SPEC |
| 담당자 | 김대원 |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `rateCd` | 요금제코드 | `String` | 요금제코드 | RATE_CD | MSP_RATE_MST |
| `rateNm` | 요금제명 | `String` | 요금제명 | RATE_NM | MSP_RATE_MST |
| `payClCd` | 선불 후불여부 | `String` | 선불 후불여부 | PAY_CL_CD | MSP_RATE_MST |
| `dataType` | data구분 | `String` | data구분 | DATA_TYPE | MSP_RATE_MST |
| `ratedesc` |  | `String` |  | RATEDESC | MSP_RATE_MST |
| `ktratename` |  | `String` |  | KTRATENAME | MSP_RATE_MST |
| `ktratecode` |  | `String` |  | KTRATECODE | MSP_RATE_MST |
| `agreement` |  | `String` |  | AGREEMENT | MSP_RATE_MST |
| `dcAmt` | 할인금액 | `int` | 약정할인선택시 할인금액 | DC_AMT | MSP_RATE_MST |
| `baseAmt` | 기본요금 | `int` | 기본요금 | BASE_AMT | MSP_RATE_MST |
| `vat` | 부가가치세 | `int` | 부가가치세 | (BASE_AMT*0.1) VAT | MSP_RATE_MST |
| `agrmTrm` | 약정기간 | `String` | 약정기간 | NVL(SPEC.AGRM_TRM, 0) AGRM_TRM | MSP_RATE_MST |

**SQL**

```sql
SELECT
RATE.RATE_CD
, RATE.RATE_NM
, RATE.PAY_CL_CD
, RATE.DATA_TYPE
,'' AS RATEDESC
,'' AS KTRATENAME
,'' AS KTRATECODE
,'' AS AGREEMENT
, NVL(DC_AMT, 0) AS DC_AMT
, BASE_AMT
, (BASE_AMT*0.1) VAT
NVL(SPEC.AGRM_TRM, 0) AGRM_TRM
FROM    MSP_RATE_MST@DL_MSP RATE
,MSP_RATE_SPEC@DL_MSP SPEC
WHERE   1=1
/*
AND     RATE_TYPE = '02'
AND     PAY_CL_CD = 'PO'
*/
AND     RATE.APPL_END_DT = '99991231'
AND     RATE.DATA_TYPE = 'LTE'
AND     RATE.RATE_CD = SPEC.RATE_CD(+)
AND     SPEC.AGRM_TRM(+) = '0'
AND     SPEC.APPL_END_DTTM = '99991231235959'
AND     ONLINE_TYPE_CD IN ('P','A')
ORDER BY  DATA_TYPE , RATE_NM
```


#### API_0092 — getRateList

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `getRateList` |
| 엔드포인트 | `/prepia/rateList` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | PrepiaMapper.xml |
| SQL ID | `getRateList` |
| parameterType | `java.util.Map` |
| resultType | `com.ktmmobile.mcp.mspservice.dto.MspSaleSubsdMstDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_RATE_MST, MSP_RATE_SPEC |
| 담당자 | 김대원 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `orgnId` | 조직코드 | `Map<String>` | 조직코드 | ORGN_ID | MSP_RATE_MST |
| `onOffType` | 온라인오프라인구분 | `Map<String>` | 온라인오프라인구분 | ON_OFF_TYPE | MSP_RATE_MST |
| `operType` | 업무구분 | `Map<String>` | 업무구분 | OPER_TYPE | MSP_RATE_MST |
| `prdtSctnCd` | 제품구분코드 | `Map<String>` | 제품구분코드 | DATA_TYPE | MSP_RATE_MST |
| `code` | 그룹코드 | `Map<String>` | 그룹코드 | CD_GROUP_ID | NMCP_CD_DTL |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `rateCd` | 요금제코드 | `String` | 요금제코드 | RATE_CD | MSP_RATE_MST |
| `rateNm` | 요금제명 | `String` | 요금제명 | RATE_NM | MSP_RATE_MST |
| `payClCd` | 선불 후불여부 | `String` | 선불 후불여부 | PAY_CL_CD | MSP_RATE_MST |
| `dataType` | data구분 | `String` | data구분 | DATA_TYPE | MSP_RATE_MST |
| `freeCallCnt` | 무료통화 | `String` | 무료통화 | FREE_CALL_CNT | MSP_RATE_MST |
| `nwInCallCnt` | 망내통화 | `String` | 망내통화 | NW_IN_CALL_CNT | MSP_RATE_MST |
| `nwOutCallCnt` | 망외통화 | `String` | 망외통화 | NW_OUT_CALL_CNT | MSP_RATE_MST |
| `freeSmsCnt` | SMS | `String` | SMS | FREE_SMS_CNT | MSP_RATE_MST |
| `freeDataCnt` | 데이터 | `String` | 데이터 | FREE_DATA_CNT | MSP_RATE_MST |
| `ratedesc` |  | `String` |  | RATEDESC |  |
| `ktratename` |  | `String` |  | KTRATENAME |  |
| `ktratecode` |  | `String` |  | KTRATECODE |  |
| `agreement` |  | `String` |  | AGREEMENT |  |
| `dcAmt` | 할인금액 | `int` | 약정할인선택시 할인금액 | NVL(DC_AMT, 0) AS DC_AMT | MSP_RATE_MST |
| `baseAmt` | 기본요금 | `int` | 기본요금 | BASE_AMT | MSP_RATE_MST |
| `vat` | 부가가치세 | `int` | 부가가치세 | (BASE_AMT*0.1) VAT | MSP_RATE_MST |
| `agrmTrm` | 약정기간 | `String` | 약정기간 | NVL(SPEC.AGRM_TRM, 0) AGRM_TRM | MSP_RATE_SPEC |
| `expnsnStrVal1` | 확장문자열값1 | `String` | 확장문자열값1 | EXPNSN_STR_VAL1 | NMCP_CD_DTL |
| `expnsnStrVal2` | 확장문자열값2 | `String` | 확장문자열값2 | EXPNSN_STR_VAL2 | NMCP_CD_DTL |
| `expnsnStrVal3` | 확장문자열값3 | `String` | 확장문자열값3 | EXPNSN_STR_VAL3 | NMCP_CD_DTL |
| `orgnId` | 조직코드 | `String` | 조직코드 | #{orgnId} AS  ORGN_ID |  |
| `onOffType` | 온라인오프라인구분 | `String` | 온라인오프라인구분 | #{onOffType} AS ON_OFF_TYPE |  |
| `operType` | 업무유형(가입유형) | `String` | NAC:신규가입 , MNP:번호이동,  HCN:기기변경 | #{operType} AS OPER_TYPE |  |

**SQL**

```sql
SELECT
RATE.RATE_CD
, RATE.RATE_NM
, RATE.PAY_CL_CD
, RATE.DATA_TYPE
, RATE.FREE_CALL_CNT
, RATE.NW_IN_CALL_CNT
, RATE.NW_OUT_CALL_CNT
, RATE.FREE_SMS_CNT
, RATE.FREE_DATA_CNT
, '' AS RATEDESC
, '' AS KTRATENAME
, '' AS KTRATECODE
, '' AS AGREEMENT
, NVL(DC_AMT, 0) AS DC_AMT
, BASE_AMT
, (BASE_AMT*0.1) VAT
, NVL(SPEC.AGRM_TRM, 0) AGRM_TRM
, CD.EXPNSN_STR_VAL1
, CD.EXPNSN_STR_VAL2
, CD.EXPNSN_STR_VAL3
, #{orgnId} AS  ORGN_ID
, #{onOffType} AS ON_OFF_TYPE
, #{operType} AS OPER_TYPE
FROM
MSP_RATE_MST@DL_MSP RATE
, MSP_RATE_SPEC@DL_MSP SPEC
, NMCP_CD_DTL CD
WHERE 	1=1
AND	RATE.APPL_END_DT = '99991231'
AND RATE.DATA_TYPE = #{prdtSctnCd}   --'LTE' 5G적용
AND RATE.RATE_CD = SPEC.RATE_CD(+)
AND SPEC.AGRM_TRM(+) = '0'
AND SPEC.APPL_END_DTTM = '99991231235959'
AND CD.USE_YN = 'Y'
AND CD.CD_GROUP_ID =#{code}
AND RATE.RATE_CD =CD.DTL_CD
ORDER BY CD.INDC_ODRG
```


---

### REQUESTREVIEW 도메인 (2개)

#### API_0093 — 고객 신청서 정보 조회

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `selectMcpRequestData` |
| 연동명(한글) | 고객 신청서 정보 조회 |
| 엔드포인트 | `/requestreview/mcpRequestData` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | RequestReviewMapper.xml |
| SQL ID | `selectMcpRequestData` |
| parameterType | `java.lang.String` |
| resultType | `com.ktmmobile.mcp.requestreview.dto.RequestReviewDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_JUO_SUB_INFO, MSP_REQUEST |
| 담당자 | 강채신 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `contractNum` | 계약번호 | `String` | 계약번호 | CONTRACT_NUM | MSP_JUO_SUB_INFO |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `contractNum` | 계약번호 | `String` | 계약번호 | CONTRACT_NUM | MSP_JUO_SUB_INFO |
| `modelId` | 모델ID | `String` | 모델ID | MODEL_ID | MSP_JUO_SUB_INFO |
| `agentCode` | 대리점_코드 | `String` | 대리점_코드 | AGENT_CODE | MSP_REQUEST |
| `reqBuyType` | 구매유형 단말 | `String` | 구매유형 단말      * 구매:MM      * USIM(유심)단독 구매:UU | REQ_BUY_TYPE | MSP_REQUEST |
| `prodId` | 상품아이디 | `String` | 단말구매에서만 존재 | PROD_ID | MSP_REQUEST |
| `sysRdate` | 등록일시 | `Date` | 등록일시 | SYS_RDATE | MSP_REQUEST |
| `requestKey` | 서식지 키 | `int` | 서식지 키 | REQUEST_KEY | MCP_REQUEST |

**SQL**

```sql
WITH MSPDB AS (
SELECT
MSP.CONTRACT_NUM
,MSP.MODEL_ID
,REQ.AGENT_CODE
,REQ.REQ_BUY_TYPE
,REQ.PROD_ID
,REQ.SYS_RDATE
FROM MSP_JUO_SUB_INFO@DL_MSP MSP
INNER JOIN MSP_REQUEST@DL_MSP REQ
ON MSP.CONTRACT_NUM = REQ.CONTRACT_NUM
WHERE 1=1
AND MSP.CONTRACT_NUM = #{contractNum}
)
SELECT
MSPDB.CONTRACT_NUM
,MSPDB.MODEL_ID
,MSPDB.AGENT_CODE
,MSPDB.REQ_BUY_TYPE
,MSPDB.PROD_ID
,MSPDB.SYS_RDATE
,REQUEST_KEY
FROM MCP_REQUEST MCP
INNER JOIN MSPDB
ON MCP.CONTRACT_NUM = MSPDB.CONTRACT_NUM
<![CDATA[
WHERE ROWNUM < 2
]]>
ORDER BY MSPDB.SYS_RDATE DESC ,MCP.REQUEST_KEY DESC
```


#### API_0094 — 고객 신청서 정보 조회

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `selectMspJuoSubInfo` |
| 연동명(한글) | 고객 신청서 정보 조회 |
| 엔드포인트 | `/requestreview/RequestReviewDto` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | RequestReviewMapper.xml |
| SQL ID | `selectMspJuoSubInfo` |
| parameterType | `java.lang.String` |
| resultType | `com.ktmmobile.mcp.requestreview.dto.RequestReviewDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_JUO_SUB_INFO |
| 담당자 | 강채신 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `contractNum` | 계약번호 | `String` | 계약번호 | CONTRACT_NUM | MSP_JUO_SUB_INFO |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `dayOver` | 작성가능여부 | `String` | Y : 작성가능/ N : 90일이상 경과 | DAY_OVER | MSP_JUO_SUB_INFO |
| `subLinkName` | 실사용자이름 | `String` | 실사용자이름 | SUB_LINK_NAME | MSP_JUO_SUB_INFO |

**SQL**

```sql
<![CDATA[
SELECT
CASE	WHEN TRUNC(SYSDATE)-TO_DATE(LST_COM_ACTV_DATE,'YYYYMMDD') > 90 THEN 'N'
WHEN TRUNC(SYSDATE)-TO_DATE(LST_COM_ACTV_DATE,'YYYYMMDD') <= 90 THEN 'Y'
ELSE 'N'
END AS DAY_OVER
,SUB_LINK_NAME
FROM MSP_JUO_SUB_INFO@DL_MSP
WHERE CONTRACT_NUM = #{contractNum}
]]>
```


---

### USERMANAGE 도메인 (2개)

#### API_0112 — getMspJuoSubInfo

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `getMspJuoSubInfo` |
| 엔드포인트 | `/usermanage/mspJuoSubInfo` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | UserManageMapper.xml |
| SQL ID | `getMspJuoSubInfo` |
| parameterType | `com.ktmmobile.mcp.usermanage.dto.McpUserCertDto` |
| resultType | `com.ktmmobile.mcp.usermanage.dto.McpUserCertDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_JUO_SUB_INFO |
| 담당자 | 강채신 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `mobileNo` | 핸드폰번호 | `String` | 핸드폰번호 | SUBSCRIBER_NO | MSP_JUO_SUB_INFO |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `contractNum` | 가입계약번호 | `String` | 가입계약번호 | CONTRACT_NUM | MSP_JUO_SUB_INFO |
| `subLinkName` | 실사용자이름 | `String` | 실사용자이름 | SUB_LINK_NAME | MSP_JUO_SUB_INFO |

**SQL**

```sql
SELECT
CONTRACT_NUM
,SUB_LINK_NAME
FROM MSP_JUO_SUB_INFO@DL_MSP
WHERE 1=1
AND SUBSCRIBER_NO = #{mobileNo}
AND SUB_STATUS = 'A'
```


#### API_0113 — getMcpUserCntrDtoList

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `getMcpUserCntrDtoList` |
| 엔드포인트 | `/usermanage/mcpUserCntrDtoList` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | UserManageMapper.xml |
| SQL ID | `getMcpUserCntrDtoList` |
| parameterType | `java.lang.String` |
| resultType | `com.ktmmobile.mcp.usermanage.dto.McpUserCertDto` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_JUO_SUB_INFO |
| 담당자 | 강채신 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `cntrUserId` | 고객아이디 | `String` | 고객아이디 | USERID | MCP_USER_CNTR_MNG |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `userNm` | 고객명 | `String` | 고객명 | SUB_LINK_NAME AS USER_NM | MSP_JUO_SUB_INFO |
| `modelName` | 단말기 모델명 | `String` | 단말기 모델명 | MODEL_NAME | MSP_JUO_SUB_INFO |
| `modelId` | 모델ID | `String` | 모델ID | MODEL_ID | MSP_JUO_SUB_INFO |
| `userId` | 고객아이디 | `String` | 고객아이디 | USERID | MCP_USER_CNTR_MNG |
| `mobileNo` | 핸드폰번호 | `String` | 핸드폰번호 | CNTR_MOBILE_NO AS MOBILE_NO | MCP_USER_CNTR_MNG |
| `contractNum` | 가입계약번호 | `String` | 가입계약번호 | SVC_CNTR_NO AS CONTRACT_NUM | MCP_USER_CNTR_MNG |
| `repNo` | 대표번호 | `String` | 대표번호 | REP_NO | MCP_USER_CNTR_MNG |
| `sysRdate` | 입력일 | `String` | 입력일 | SYS_RDATE | MCP_USER_CNTR_MNG |
| `subStatus` | 상태값 | `String` | 상태값 | SUB_STATUS | MCP_USER_CNTR_MNG |

**SQL**

```sql
SELECT
A.USERID
, B.SUB_LINK_NAME AS USER_NM
, A.CNTR_MOBILE_NO AS MOBILE_NO
, A.SVC_CNTR_NO AS CONTRACT_NUM
, A.REP_NO
, B.MODEL_NAME
, B.MODEL_ID
, SUB_STATUS
, A.SYS_RDATE
FROM MCP_USER_CNTR_MNG A, MSP_JUO_SUB_INFO@DL_MSP B
WHERE UPPER(A.USERID) = UPPER(#{cntrUserId})
AND A.SVC_CNTR_NO = B.CONTRACT_NUM
ORDER BY A.REP_NO ASC
```


---

### APP 도메인 (1개)

#### API_0001 — NCN 요금제 정보 조회

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `selectUsrRateInfo` |
| 연동명(한글) | NCN 요금제 정보 조회 |
| 엔드포인트 | `/app/usrRateInfo` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | AppMapper.xml |
| SQL ID | `selectUsrRateInfo` |
| parameterType | `java.lang.String` |
| resultType | `java.util.Map` |
| 사용 여부 | ✅ 사용 |
| 대상 테이블 | MSP_JUO_FEATURE_INFO, MSP_RATE_MST |
| 담당자 | 강채신 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `ncn` | 서비스계약번호 | `String` | 서비스계약번호 | SVC_CNTR_NO | MCP_USER_CNTR_MNG |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `USERID` | 고객아이디 | `Map<String>` | 고객아이디 | USERID | MCP_USER_CNTR_MNG |
| `CNTR_MOBILE_NO` | 핸드폰번호 | `Map<String>` | 핸드폰번호 | CNTR_MOBILE_NO | MCP_USER_CNTR_MNG |
| `SVC_CNTR_NO` | 서비스계약번호 | `Map<String>` | 서비스계약번호 | SVC_CNTR_NO | MCP_USER_CNTR_MNG |
| `SOC` | 상품코드 | `Map<String>` | 상품코드 | SOC | MSP_JUO_FEATURE_INFO |
| `RATE_NM` | 요금제명 | `Map<String>` | 요금제명 | RATE_NM | MSP_RATE_MST |
| `RATE_CD` | 요금제코드 | `Map<String>` | 요금제코드 | RATE_CD | MSP_RATE_MST |
| `DATA_TYPE` | 데이터유형 | `Map<String>` | 데이터유형 (ORG0008) 'LTE,3G | DATA_TYPE | MSP_RATE_MST |

**SQL**

```sql
SELECT
A.USERID, A.CNTR_MOBILE_NO, A.SVC_CNTR_NO
, B.SOC
, C.RATE_NM, C.RATE_CD, C.DATA_TYPE
FROM
MCP_USER_CNTR_MNG A
, MSP_JUO_FEATURE_INFO@DL_MSP B
, MSP_RATE_MST@DL_MSP C
WHERE A.SVC_CNTR_NO = #{ncn}
AND B.CONTRACT_NUM = A.SVC_CNTR_NO
AND B.SERVICE_TYPE = 'P'
AND B.EXPIRATION_DATE > TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')
AND B.SOC = C.RATE_CD
```


---

### SELLUSIM 도메인 (1개)

#### API_0095 — checkUsimRegCnt

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `checkUsimRegCnt` |
| 엔드포인트 | `/sellUsim/checkUsimRegCount` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | SellUsimMapper.xml |
| SQL ID | `checkUsimRegCnt` |
| parameterType | `com.ktmmobile.mcp.usim.dto.SellUsimDto` |
| resultType | `int` |
| 사용 여부 | ✅ 사용 |
| SQL 유형 | SQL ID 변경 |
| 대상 테이블 | MSP_JUO_SUB_INFO |
| 담당자 | 강문재 |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `mobileFn` | 휴대폰_앞자리 | `String` | 휴대폰_앞자리 | SUBSCRIBER_NO | MSP_JUO_SUB_INFO |
| `mobileMn` | 휴대폰_중간자리 | `String` | 휴대폰_중간자리 | SUBSCRIBER_NO | MSP_JUO_SUB_INFO |
| `mobileRn` | 휴대폰_뒷자리 | `String` | 휴대폰_뒷자리 | SUBSCRIBER_NO | MSP_JUO_SUB_INFO |
| `usimNo` | 유심_일련번호 | `String` | 유심_일련번호 | ICC_ID | MSP_JUO_SUB_INFO |

**Output Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `count` | 조회개수 | `int` | 조회개수 | COUNT |  |

**SQL**

```sql
SELECT
COUNT(*)
FROM
MSP_JUO_SUB_INFO@DL_MSP
WHERE SUBSCRIBER_NO = #{mobileFn}||#{mobileMn}||#{mobileRn}
AND SUBSTR(ICC_ID ,-3) = #{usimNo}
AND SUB_STATUS <![CDATA[<>]]> 'C'
```


---

### TELCOUNSEL 도메인 (1개)

#### API_0111 — callMspProcedureinsertTelCounsel

| 항목 | 내용 |
|---|---|
| 연동명(영문) | `callMspProcedureinsertTelCounsel` |
| 엔드포인트 | `/telCounsel/telCounsel` |
| 송/수신 | 양방향 |
| 인터페이스 주기 | 수시 |
| Mapper | TelCounselMapper.xml |
| SQL ID | `callMspProcedureinsertTelCounsel` |
| parameterType | `com.ktmmobile.mcp.telcounsel.dto.TelCounselDto` |
| resultType | `void` |
| 사용 여부 | ❌ 미사용 |
| 대상 테이블 | P_MSP_VOC_LINK_REG |
| 담당자 | 김대원 |
| 비고 | FUNCTION |

**Input Parameters**

| 필드명(영문) | 필드명(한글) | 타입 | 설명 | DB 컬럼 / ALIAS | 테이블 |
|---|---|---|---|---|---|
| `counselSeq` | 상담일련번호 | `Integer` | 상담일련번호 |  | P_MSP_VOC_LINK_REG |
| `counselCtg` | 전화상담카테고리구분 | `String` | 핸드폰:01,유심:02 |  | P_MSP_VOC_LINK_REG |
| `counselNm` | 상담신청자이름 | `String` | 상담신청자이름 |  | P_MSP_VOC_LINK_REG |
| `mobileNo` | 상담신청자 전화번호 | `String` | 상담신청자 전화번호 |  | P_MSP_VOC_LINK_REG |
| `fullTextForMsp` | MSP 프로시저 호출시에 풀텍스 전달을 위한 text | `String` | MSP 프로시저 호출시에 풀텍스 전달을 위한 text |  | P_MSP_VOC_LINK_REG |

**SQL**

```sql
<selectKey keyProperty="counselSeq" resultType="Integer" order="BEFORE">
SELECT SQ_TEL_COUNSEL_BAS_SEQ.NEXTVAL FROM DUAL
</selectKey>												<!-- writeid-->										<!--userid--><!--sms yn  -->
{CALL P_MSP_VOC_LINK_REG@dl_msp(#{counselSeq},#{counselCtg},'전화상담','',#{counselNm},#{mobileNo},#{fullTextForMsp},'userid','smsyn')}
```

