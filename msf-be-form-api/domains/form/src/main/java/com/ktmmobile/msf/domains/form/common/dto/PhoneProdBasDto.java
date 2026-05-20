package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class PhoneProdBasDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<MspSaleSubsdMstDto> mspSaleSubsdMstListForLowPrice; // 상품 최저가를 위한 리스트정보
    private List<PhoneSntyBasDto> phoneSntyBasDtosList;               // 단품정보
    private List<PhoneSntyBasDto> phoneSntyBasDtoListY;               // 단품정보 판매여부에 상관없이 전체 출력
    private List<PhoneSntyBasDto> phoneSntyBasDtoListOnline;          // 단품정보리스트 (품절,판매중단 표기 안함)
    private List<PhoneProdImgDto> phoneProdImgDtoList;                // 상품이미지
    private PhoneProdImgDto rprtPhoneProdImgDto;                      // 대표상품의 상품이미지 정보
    private MultipartFile layerFileItem;                              // 레이어보이기 파일 아이템
    private String prodId;                                            // 상품id
    private String prodCtgId;                                         // 상품분류아이디 LTE:04, 3G:03
    private String prodNm;                                            // 상품명
    private String makrCd;                                            // 제조사코드
    private String showYn;                                            // 노출여부
    private String saleYn;                                            // 판매여부
    private String ofwDate;                                           // 출고일자
    private String shandYn;                                           // 중고여부
    private String listShowText;                                      // 목록노출 텍스트
    private String listShowOptn;                                      // 목록노출옵션
    private String apdDesc1;                                          // 추가설명 1
    private String apdDesc2;                                          // 추가설명 2
    private String apdDesc3;                                          // 추가설명 3
    private String stckTypeTop;   // 스티커노출(상) 01:BEST, 02:NEW, 03:신상, 04:특가, 05:인기상품
    private String stckTypeTail;  // 스티커노출(하) 01:최대공시금지원, 02:공시지원금확대, 03:이벤트, 04:국내유일
    private int showOdrg;                                             // 노출순서
    private String sntyProdDesc;                                      // 단말기 상품설명
    private String sntyNet;                                           // 단말기네트워크
    private String sntyDisp;                                          // 단말기 디스플레이
    private String sntySize;                                          // 단말기 크기
    private String sntyWeight;                                        // 단말기 무게
    private String sntyMemr;                                          // 단말기 메모리
    private String sntyBtry;                                          // 단말기 베터리
    private String sntyOs;                                            // 단말기 os
    private String sntyWaitTime;                                      // 단말기 대기시간
    private String sntyCam;                                           // 단말기 카메라
    private String sntyVideTlk;                                       // 단말기 영상통화
    private String cretId;                                            // 생성자 id
    private String amdId;                                             // 수정자 id
    private int payMnthAmt;                                           // 최저가 가격
    private int instAmt;                                              // 최저가 할부원금
    private Date cretDt;                                              // 생성일시
    private Date amdDt;                                               // 수정일시
    private String rprsPrdtId;                                        // 대표모델id
    private String layerYn;                                           // 레이어팝업 노출여부
    private String layerImageUrl;                                     // 레이어이미지팝업경로
    private String sntyProdNm;                                        // 단말기명
    private String sntyRelMonth;                                      // 출시월
    private String sntyColor;                                         // 단말기 색상
    private String sntyMaker;                                         // 단말기 제조사/브랜드명
    private String sntyModelId;                                       // 단말기 모델번호
    private String sntyDetailSpec;                                    // 핸드폰 상세 설명
    private String mnfctNm;                                           // 제조사명
    private String saleYnLabel;                                       // 판매여부 text
    private String showYnLabel;                                       // 노출 여부 text
    private String prodCtgIdLabel;                                    // 상품카테고리(3G,LTE) TEXT
    private String makrNm;                                            // 제조사명
    private int inventoryAmt;                                         // 재고수량
    private int salePrice;                                            // 중고폰 판매가격
    private String prodGrade;                                         // 상품등급
    private String shandType;                                         // 중고폰분류값 (중고폰:01, 외산폰:02)
    private String shandTypeLabel;                                    // 중고폰분류값 label
    private String recommendRate;                                     // 추천요금제 정보
    private String usedWarranty;                                      // 중고폰 워런티
    private String orderCretDt;                                       // 상품순서 정렬용 생성일자 -> 출시일로변경
    private String hndsetModelId;                                     // 선택한 단품의 대표 이미지를 불러오기 위해서 세팅하는 모델Id
    private String rinkUrl;                                           // 중고폰 링크 url
    private String prodType;                                          // 상품 분류 (일반:01, 0원 상품:02)
    private String prodTypeLabel;
    private int rentalBaseAmt;                                        // 렌탈 기본료 금액
    private int rentalBaseDcAmt;                                      // 렌탈 기본료 할인 금액
    private int rentalModelCpAmt;                                     // 단말기 배상 금액
    private String salePlcyCd;   // Phone_Query.listPhoneProdBasForFrontOneQuery 상품리스트 조회에 정책 코드
    private String sesplsYn;                                          // 자급제폰 여부(Y:자급제폰, N:자급제폰아님)
    private int outUnitPric;                                          // 출고단가 할인가
    private int inUnitPric;                                           // 입고단가 최초가
    private String agrmTrmBase;
    private String recommendRateNoargm;
    private String repRate;
    private String repRateDataType;                                   // 추천요금제의 dataType(LTE, 5G)
    private String settlYn;                                           // SETTL_YN -- 결제여부(Y,N)

    /** 멀티파트 일경우 입력값들의 Xss 방어를 위한 처리 작업을한다. */
    /*
    public void cleanXssAtMultipart() {

        prodNm = RequestWrapper.cleanXSS(prodNm);
        apdDesc1 = RequestWrapper.cleanXSS(apdDesc1);
        apdDesc2 = RequestWrapper.cleanXSS(apdDesc2);
        apdDesc3 = RequestWrapper.cleanXSS(apdDesc3);
        sntyProdNm = RequestWrapper.cleanXSS(sntyProdNm);
        sntySize = RequestWrapper.cleanXSS(sntySize);
        sntyProdDesc = RequestWrapper.cleanXSS(sntyProdDesc);
        sntyWeight = RequestWrapper.cleanXSS(sntyWeight);
        sntyModelId = RequestWrapper.cleanXSS(sntyModelId);
        sntyMemr = RequestWrapper.cleanXSS(sntyMemr);
        sntyNet = RequestWrapper.cleanXSS(sntyNet);
        sntyBtry = RequestWrapper.cleanXSS(sntyBtry);
        sntyMaker = RequestWrapper.cleanXSS(sntyMaker);
        sntyOs = RequestWrapper.cleanXSS(sntyOs);
        sntyRelMonth = RequestWrapper.cleanXSS(sntyRelMonth);
        sntyWaitTime = RequestWrapper.cleanXSS(sntyWaitTime);
        sntyColor = RequestWrapper.cleanXSS(sntyColor);
        sntyCam = RequestWrapper.cleanXSS(sntyCam);
        sntyDisp = RequestWrapper.cleanXSS(sntyDisp);
        sntyVideTlk = RequestWrapper.cleanXSS(sntyVideTlk);
    }
    */

    public PhoneProdImgDto getRprtPhoneProdImgDto() {
        List<PhoneSntyBasDto> tempSnty = getPhoneSntyBasDtosList();
        String atribValCd1 = "";
        if (tempSnty != null && tempSnty.size() > 0) {
            for (PhoneSntyBasDto basket: tempSnty) {
                if (StringUtils.isNoneEmpty(hndsetModelId)) {
                    if (hndsetModelId.equals(basket.getHndsetModelId())) {
                        atribValCd1 = basket.getAtribValCd1();
                        break;
                    }
                } else {
                    if ("Y".equals(basket.getRprsPrdtYn())) {
                        atribValCd1 = basket.getAtribValCd1();
                        break;
                    }
                }
            }
        }

        List<PhoneProdImgDto> tempImage = getPhoneProdImgDtoList();
        if (tempImage != null && tempImage.size() > 0) {
            for (PhoneProdImgDto basket: tempImage) {
                if (atribValCd1.equals(basket.getSntyColorCd())) {
                    rprtPhoneProdImgDto = basket;
                    break;
                }
            }
        }
        return rprtPhoneProdImgDto;
    }

    /*
    public void doSort() {
        Collections.sort(mspSaleSubsdMstListForLowPrice, new Comparator<MspSaleSubsdMstDto>() {
            @Override
            public int compare(MspSaleSubsdMstDto o1, MspSaleSubsdMstDto o2) {
                OrderEnum oe = getOrderEnum();
                return oe.orderStragety(o1, o2);
            }
        });
    }
    */

    public boolean doNotSale() {
        if ("N".equals(saleYn)) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    public String getShandTypeLabel() {
        if ("01".equals(shandType)) {
            this.shandTypeLabel = "중고폰";
            return shandTypeLabel;
        } else if ("02".equals(shandType)) {
            this.shandTypeLabel = "액세서리";
            return shandTypeLabel;
        } else if ("03".equals(shandType)) {
            this.shandTypeLabel = "직구";
            return shandTypeLabel;
        }
        return shandTypeLabel;
    }

    public String getSaleYnLabel() {
        if (saleYn == null || saleYn.equals("N")) {
            this.saleYnLabel = "미판매";
        } else {
            this.saleYnLabel = "판매";
        }
        return saleYnLabel;
    }

    public String getShowYnLabel() {
        if (showYn == null || showYn.equals("N")) {
            this.showYnLabel = "미노출";
        } else {
            this.showYnLabel = "노출";
        }
        return showYnLabel;
    }

    public String getProdCtgIdLabel() {
        if (prodCtgId == null) {
            this.prodCtgIdLabel = "알수없음";
        } else if (prodCtgId.equals("03")) {
            this.prodCtgIdLabel = "3G";
        } else if (prodCtgId.equals("04")) {
            this.prodCtgIdLabel = "LTE";
        }
        return prodCtgIdLabel;
    }

    public String getProdGradeTxt() {
        if (prodGrade != null && prodGrade.equals("N")) {
            return "신품";
        } else if (prodGrade != null) {
            return prodGrade + "등급";
        } else {
            return "";
        }
    }

    public String getUsedWarranty() {
        if (usedWarranty == null || usedWarranty.trim().equals("")) {
            return "없음";
        }
        return usedWarranty;
    }

    public List<PhoneSntyBasDto> getPhoneSntyBasDtoListY() {
        List<PhoneSntyBasDto> saleYList = null;
        if (phoneSntyBasDtosList != null) {
            saleYList = new ArrayList<PhoneSntyBasDto>();
            for (PhoneSntyBasDto a: phoneSntyBasDtosList) {
                if ("Y".equals(a.getSaleYn())) {
                    saleYList.add(a);
                }
            }
        }
        return saleYList;
    }

    public List<PhoneSntyBasDto> getPhoneSntyBasDtoListOnline() {
        List<PhoneSntyBasDto> onlineList = null;
        if (phoneSntyBasDtosList != null) {
            onlineList = new ArrayList<PhoneSntyBasDto>();
            for (PhoneSntyBasDto a: phoneSntyBasDtosList) {
                if ("Y".equals(a.getSaleYn()) && !"Y".equals(a.getSdoutYn())) {
                    onlineList.add(a);
                }
            }
        }
        return onlineList;
    }

    public String getProdTypeLabel() {
        if ("01".equals(prodType)) {
            prodTypeLabel = "일반";
        } else if ("04".equals(prodType)) {
            prodTypeLabel = "중고폰";
        } else if ("02".equals(prodType)) {
            prodTypeLabel = "0원상품";
        } else if ("05".equals(prodType)) {
            prodTypeLabel = "자급제폰";
        } else {
            prodTypeLabel = "";
        }
        return prodTypeLabel;
    }

    /*public OrderEnum getOrderEnum() {
        return orderEnum;
    }

    public void setOrderEnum(OrderEnum orderEnum) {
        this.orderEnum = orderEnum;
    }*/

}
