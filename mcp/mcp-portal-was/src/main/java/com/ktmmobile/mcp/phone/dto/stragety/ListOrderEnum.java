package com.ktmmobile.mcp.phone.dto.stragety;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.ktmmobile.mcp.common.mspservice.dto.MspSaleSubsdMstDto;
import com.ktmmobile.mcp.common.util.StringUtil;
import com.ktmmobile.mcp.phone.dto.PhoneProdBasDto;


/**
 * @Class Name : ListOrderEnum
 * @Description : 핸드폰 상세 리스트에서 정렬순서 처리
 *
 * @author : ant
 * @Create Date : 2016. 2. 1.
 */
public enum ListOrderEnum {

	/**
	 * @Class Name : 1
	 * @Description : 월납부금액 낮은순으로 처리
	 *
	 * @author : ant
	 * @Create Date : 2016. 1. 29.
	 */
	CHARGE_ROW {
		@Override
		public void orderStragety(List<PhoneProdBasDto> lteList) {
			Collections.sort(lteList, new Comparator<PhoneProdBasDto>() {
				@Override
				public int compare(PhoneProdBasDto o1, PhoneProdBasDto o2) {
					int c1 = nullToZero(o1);
					int c2 = nullToZero(o2);
					if (c1 < c2) {
						return -1;
					}

					if (c1 > c2) {
						return 1;
					}
					return 0;
				}

	        });
		}
	},
	CHARGE_HIGH {
		@Override
		public void orderStragety(List<PhoneProdBasDto> lteList) {
			Collections.sort(lteList, new Comparator<PhoneProdBasDto>() {
				@Override
				public int compare(PhoneProdBasDto o1, PhoneProdBasDto o2) {
					int c1 = nullToZero(o1);
					int c2 = nullToZero(o2);
					if (c1 < c2) {
						return 1;
					}

					if (c1 > c2) {
						return -1;
					}
					return 0;
				}

	        });
		}
	},
	NEW_ITEM {
		@Override
		public void orderStragety(List<PhoneProdBasDto> lteList) {
			Collections.sort(lteList, new Comparator<PhoneProdBasDto>() {
				@Override
				public int compare(PhoneProdBasDto o1, PhoneProdBasDto o2) {
					if (StringUtil.NVL(o2.getOrderCretDt(), "").compareTo(o1.getOrderCretDt()) < 0) { //o1 더큼
						return -1;
					}

					if (StringUtil.NVL(o1.getOrderCretDt(), "").compareTo(o2.getOrderCretDt()) < 0) { //o2 더큼
						return 1;
					}
					return 0;
				}

	        });
		}
	},
	RECOMMEND {
		@Override
		public void orderStragety(List<PhoneProdBasDto> lteList) {
			Collections.sort(lteList, new Comparator<PhoneProdBasDto>() {
				@Override
				public int compare(PhoneProdBasDto o1, PhoneProdBasDto o2) {
					int c1 = getRecommend(o1.getShowOdrg());
					int c2 = getRecommend(o2.getShowOdrg());

					if (c1 > c2) {
						return 1;
					}

					if (c1 < c2) {
						return -1;
					}
					return 0;
				}

				private int getRecommend(int orderNo) {
					if (orderNo == 0) {
						return 9999;
					}
					return orderNo;
				}

	        });
		}
	},
	//중고폰상품 정렬처리용(가격낮은순)
	USED_PRICE_ROW {
		@Override
		public void orderStragety(List<PhoneProdBasDto> lteList) {
			Collections.sort(lteList, new Comparator<PhoneProdBasDto>() {
				@Override
				public int compare(PhoneProdBasDto o1, PhoneProdBasDto o2) {
					int c1 = o1.getSalePrice();
					int c2 = o2.getSalePrice();

					if (c1 > c2) {
						return 1;
					}

					if (c1 < c2) {
						return -1;
					}
					return 0;
				}

	        });
		}
	},
	//중고폰상품 정렬처리용(가격높은순)
		USED_PRICE_HIGH {
			@Override
			public void orderStragety(List<PhoneProdBasDto> lteList) {
				Collections.sort(lteList, new Comparator<PhoneProdBasDto>() {
					@Override
					public int compare(PhoneProdBasDto o1, PhoneProdBasDto o2) {
						int c1 = o1.getSalePrice();
						int c2 = o2.getSalePrice();

						if (c1 > c2) {
							return -1;
						}

						if (c1 < c2) {
							return 1;
						}
						return 0;
					}

		        });
			}
		},
		//중고폰상품 정렬처리용(신상품순
		USED_NEW_ARRIVAL {
			@Override
			public void orderStragety(List<PhoneProdBasDto> lteList) {
				Collections.sort(lteList, new Comparator<PhoneProdBasDto>() {
					@Override
					public int compare(PhoneProdBasDto o1, PhoneProdBasDto o2) {
						long c1 = o1.getCretDt().getTime();
						long c2 = o2.getCretDt().getTime();

						if (c1 > c2) {
							return -1;
						}

						if (c1 < c2) {
							return 1;
						}
						return 0;
					}

		        });
			}
		};

	public int getNewStick(String stckTypeTop) {
		if (stckTypeTop == null) {
			return 0;
		}
		if (stckTypeTop.indexOf("03") > -1) {
			return 9999;
		} else {
			return 1;
		}
	}

	public int nullToZero(PhoneProdBasDto param) {

		if (param == null) {
			return 0;
		}

		List<MspSaleSubsdMstDto> list = param.getMspSaleSubsdMstListForLowPrice();

		if (list == null || list.size() == 0) {
			return 0;
		}

		return list.get(0).getPayMnthAmt();
	}
	/**
	* @Description :
	*   정렬순서에 대한 각종 전략을 가지고있다.
	* @param o1 대상요금제
	* @param o2 비교대상요금제
	* @return -1 ,  0 , 1
	* @Author : ant
	* @Create Date : 2016. 1. 29.
	*/
	public abstract void orderStragety(List<PhoneProdBasDto> lteList);
}
