package com.ktmmobile.mcp.phone.dto;

import org.apache.commons.lang3.StringUtils;

import com.ktmmobile.mcp.common.mspservice.dto.MspSaleSubsdMstDto;



/**
 * @Class Name : OrderEnum
 * @Description : 상품상세에서 정렬처리 스타일 정의를 휘한 열거형 type
 * 정렬순서에 대한 전략정보를 가지고 있다.
 * @author : ant
 * @Create Date : 2016. 1. 29.
 */
public enum OrderEnum {




    /**
     * @Class Name : 1
     * @Description : 단말기 실 구매가 낮은 금액 (상품리스트 기본 정렬용)
     *
     * @author : ant
     * @Create Date : 2016. 3. 19.
     */
    LIST_DEFAULT {
        @Override
        public int orderStragety(MspSaleSubsdMstDto o1, MspSaleSubsdMstDto o2) {
            int c1 = o1.getInstAmt();
            int c2 = o2.getInstAmt();

            if (c1 < c2) {
                return -1;
            }

            if (c1 > c2) {
                return 1;
            }
            return 0;
        }
    },
    /**
     * @Class Name : 1
     * @Description : 월납부금액 낮은순으로 처리
     *
     * @author : ant
     * @Create Date : 2016. 1. 29.
     */
    CHARGE_ROW {
        @Override
        public int orderStragety(MspSaleSubsdMstDto o1, MspSaleSubsdMstDto o2) {
            int c1 = o1.getPayMnthChargeAmt() + o1.getPayMnthAmt();
            int c2 = o2.getPayMnthChargeAmt() + o2.getPayMnthAmt();

            if (c1 < c2) {
                return -1;
            }

            if (c1 > c2) {
                return 1;
            }
            return 0;
        }
    },


    /**
     * @Class Name : 2
     * @Description : 월납부금액 높은순으로 처리
     *
     * @author : ant
     * @Create Date : 2016. 1. 29.
     */
    CHARGE_HIGH {
        @Override
        public int orderStragety(MspSaleSubsdMstDto o1, MspSaleSubsdMstDto o2) {
            int c1 = o1.getPayMnthChargeAmt() + o1.getPayMnthAmt();
            int c2 = o2.getPayMnthChargeAmt() + o2.getPayMnthAmt();

            if (c1 < c2) {
                return 1;
            }

            if (c1 > c2) {
                return -1;
            }
            return 0;
        }
    },

    /**
     * @Class Name : 3
     * @Description : 음성대이터 적은순
     *
     * @author : ant
     * @Create Date : 2016. 1. 29.
     */
    VOICE_ROW {
        @Override
        public int orderStragety(MspSaleSubsdMstDto o1, MspSaleSubsdMstDto o2) {
            String c1 = StringUtils.defaultIfEmpty(o1.getMspRateMstDto().getFreeCallCnt(), "0");
            String c2 = StringUtils.defaultIfEmpty(o2.getMspRateMstDto().getFreeCallCnt(), "0");

            int i1 = stringWithTongFromInt(c1);
            int i2 = stringWithTongFromInt(c2);

            if (i1 > i2) {
                return 1;
            }

            if (i1 < i2) {
                return -1;
            }
            return 0;
        }
    },

    /**
     * @Class Name : 4
     * @Description : 음성데이터 많은순
     *
     * @author : ant
     * @Create Date : 2016. 1. 29.
     */
    VOICE_HIGH {
        @Override
        public int orderStragety(MspSaleSubsdMstDto o1, MspSaleSubsdMstDto o2) {
            String c1 = StringUtils.defaultIfEmpty(o1.getMspRateMstDto().getFreeCallCnt(), "0");
            String c2 = StringUtils.defaultIfEmpty(o2.getMspRateMstDto().getFreeCallCnt(), "0");

            int i1 = stringWithTongFromInt(c1);
            int i2 = stringWithTongFromInt(c2);

            if (i1 < i2) {
                return 1;
            }

            if (i1 > i2) {
                return -1;
            }
            return 0;
        }
    },

    /**
     * @Class Name : 5
     * @Description : 데이터 적은순
     *
     * @author : ant
     * @Create Date : 2016. 1. 29.
     */
    DATA_ROW {
        @Override
        public int orderStragety(MspSaleSubsdMstDto o1, MspSaleSubsdMstDto o2) {
            String c1 = StringUtils.defaultIfEmpty(o1.getMspRateMstDto().getFreeDataCnt(), "0");
            String c2 = StringUtils.defaultIfEmpty(o2.getMspRateMstDto().getFreeDataCnt(), "0");

            int i1 = stringWithSizeFromInt(c1);
            int i2 = stringWithSizeFromInt(c2);
            if (i1 > i2) {
                return 1;
            }

            if (i2 < i1) {
                return -1;
            }
            return 0;
        }
    },


    /**
     * @Class Name : 6
     * @Description : 데이터 많은순
     *
     * @author : ant
     * @Create Date : 2016. 1. 29.
     */
    DATA_HIGH {
        @Override
        public int orderStragety(MspSaleSubsdMstDto o1, MspSaleSubsdMstDto o2) {
            String c1 = StringUtils.defaultIfEmpty(o1.getMspRateMstDto().getFreeDataCnt(), "0");
            String c2 = StringUtils.defaultIfEmpty(o2.getMspRateMstDto().getFreeDataCnt(), "0");


            int i1 = stringWithSizeFromInt(c1);
            int i2 = stringWithSizeFromInt(c2);
            if (i1 < i2) {
                return 1;
            }

            if (i2 > i1) {
                return -1;
            }
            return 0;
        }
    },


    /**
     * @Class Name : 7
     * @Description : 요금제명 순
     *
     * @author : ant
     * @Create Date : 2016. 1. 29.
     */
    CHARGE_NM_ROW {
        @Override
        public int orderStragety(MspSaleSubsdMstDto o1, MspSaleSubsdMstDto o2) {
            String c1 = o1.getMspRateMstDto().getRateNm();
            String c2 = o2.getMspRateMstDto().getRateNm();
            int res = String.CASE_INSENSITIVE_ORDER.compare(c1, c2);
            if (res == 0) {
                res = c1.compareTo(c2);
            }
            return res;
        }
    },
    CHARGE_NM_HIGH {
        @Override
        public int orderStragety(MspSaleSubsdMstDto o1, MspSaleSubsdMstDto o2) {
            String c1 = o1.getMspRateMstDto().getRateNm();
            String c2 = o2.getMspRateMstDto().getRateNm();
            int res = String.CASE_INSENSITIVE_ORDER.compare(c2, c1);
            if (res == 0) {
                res = c2.compareTo(c1);
            }
            return res;
        }
    },

    /**
     * @Class Name : 8
     * @Description : 월 단말요금 낮은 순
     *
     * @author : papier
     * @Create Date : 2018. 2. 22.
     */
    INST_MNTH_ROW {
        @Override
        public int orderStragety(MspSaleSubsdMstDto o1, MspSaleSubsdMstDto o2) {
            int c1 = o1.getInstMnthAmt() ;
            int c2 = o2.getInstMnthAmt() ;

            if (c1 < c2) {
                return -1;
            }

            if (c1 > c2) {
                return 1;
            }
            return 0;
        }
    },

    /**
     * @Class Name : 9
     * @Description : 단말지원금 높은 순
     *
     * @author : papier
     * @Create Date : 2018. 2. 22.
     */
    HNDST_DC_HIGH {
        @Override
        public int orderStragety(MspSaleSubsdMstDto o1, MspSaleSubsdMstDto o2) {
            int c1 = o1.getHndstDcAmt() ;
            int c2 = o2.getHndstDcAmt() ;

            if (c1 < c2) {
                return 1;
            }

            if (c1 > c2) {
                return -1;
            }
            return 0;
        }
    },

    /**
     * @Class Name : 9
     * @Description : 추천 순
     *
     * @author : papier
     * @Create Date : 2018. 2. 22.
     */
    RECOMM_ROW {
        @Override
        public int orderStragety(MspSaleSubsdMstDto o1, MspSaleSubsdMstDto o2) {
            int c1 = o1.getPaySortInt() ;
            int c2 = o2.getPaySortInt() ;

            if (c1 < c2) {
                return -1;
            }

            if (c1 > c2) {
                return 1;
            }
            return 0;
        }
    },
    
    /**
     * @Class Name : 10
     * @Description : 월납부금액 낮은순으로 처리
     *
     * @author : ant
     * @Create Date : 2016. 1. 29.
     */
    XML_CHARGE_ROW {
        @Override
        public int orderStragety(MspSaleSubsdMstDto o1, MspSaleSubsdMstDto o2) {
        	int c1 = o1.getXmlPayMnthAmt();
            int c2 = o2.getXmlPayMnthAmt();

            if (c1 < c2) {
                return -1;
            }

            if (c1 > c2) {
                return 1;
            }
            return 0;
        }
    },


    /**
     * @Class Name : 11
     * @Description : 월납부금액 높은순으로 처리
     *
     * @author : ant
     * @Create Date : 2016. 1. 29.
     */
    XML_CHARGE_HIGH {
        @Override
        public int orderStragety(MspSaleSubsdMstDto o1, MspSaleSubsdMstDto o2) {
            int c1 = o1.getXmlPayMnthAmt();
            int c2 = o2.getXmlPayMnthAmt();

            if (c1 < c2) {
                return 1;
            }

            if (c1 > c2) {
                return -1;
            }
            return 0;
        }
    },
    /**
     * @Class Name : 12
     * @Description : 데이터 적은순
     *
     * @author : ant
     * @Create Date : 2016. 1. 29.
     */
    XML_DATA_ROW {
        @Override
        public int orderStragety(MspSaleSubsdMstDto o1, MspSaleSubsdMstDto o2) {
            String c1 = StringUtils.defaultIfEmpty(o1.getMspRateMstDto().getXmlDataCnt(), "0");
            String c2 = StringUtils.defaultIfEmpty(o2.getMspRateMstDto().getXmlDataCnt(), "0");

            int i1 = stringWithSizeFromInt(c1);
            int i2 = stringWithSizeFromInt(c2);
            if (i1 > i2) {
                return 1;
            }

            if (i1 < i2) {
                return -1;
            }
            return 0;
        }
    },


    /**
     * @Class Name : 13
     * @Description : 데이터 많은순
     *
     * @author : ant
     * @Create Date : 2016. 1. 29.
     */
    XML_DATA_HIGH {
        @Override
        public int orderStragety(MspSaleSubsdMstDto o1, MspSaleSubsdMstDto o2) {
        	if(o1.getMspRateMstDto() == null || o2.getMspRateMstDto() == null) {
        		return 0;
        	}
        	
    		String c1 = StringUtils.defaultIfEmpty(o1.getMspRateMstDto().getXmlDataCnt(), "0");
    		String c2 = StringUtils.defaultIfEmpty(o2.getMspRateMstDto().getXmlDataCnt(), "0");
    		
    		
    		int i1 = stringWithSizeFromInt(c1);
    		int i2 = stringWithSizeFromInt(c2);
    		if (i1 < i2) {
    			return 1;
    		}
    		
    		if (i1 > i2) {
    			return -1;
    		}
    		return 0;
        }
    },
    ;

    /**
    * @Description :
    *   정렬순서에 대한 각종 전략을 가지고있다.
    * @param o1 대상요금제
    * @param o2 비교대상요금제
    * @return -1 ,  0 , 1
    * @Author : ant
    * @Create Date : 2016. 1. 29.
    */
    public abstract int orderStragety(MspSaleSubsdMstDto o1, MspSaleSubsdMstDto o2);


    /**
    * @Description :
    * 입력값의 용량단위와 소수점을 삭제 하고
    * 용량단위 삭제 MB 일경우 곱하기 1024 , GB 일경우 곱하기 1024 * 1024 처리를 한다.
    * 입력받은 String 문자열을 int 형으로 파싱한다.
    * @param input 입력값
    * @return 숫자로 파싱된 입력값
    * @Author : ant
    * @Create Date : 2016. 1. 29.
    */
    private static int stringWithSizeFromInt(String input) {
        if (input == null) {
            return 0;
        }

        //무료데이터 : 데이터무제한, 무제한, 기본제공, 기본
        if (input.indexOf("기본") > -1 || input.indexOf("무제한") > -1 ) {
            return Integer.MAX_VALUE;
        }
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return 0;
        }

    }

    private static int stringWithTongFromInt(String input) {
        String upperC1 = input.trim();

        //ㅇ 무료통화 : 집/이동전화 무제한, 무제한, 유무선무제한, 통화무제한, 기본제공, 기본
        //if ("기본제공".equals(upperC1)) {
        if (upperC1.indexOf("기본") > -1 || upperC1.indexOf("무제한") > -1 ) {
            return Integer.MAX_VALUE;
        } else {
            upperC1 = (null!=upperC1.split("\\(")[0]) ? upperC1.split("\\(")[0].trim() : "0" ;
        }
        int rtnI = 0;
        try {
            rtnI = Integer.parseInt(upperC1.trim());
        } catch (NumberFormatException nfe) {
            rtnI = 0;
        }
        return rtnI;
    }

}
