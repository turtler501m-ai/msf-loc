//package com.ktmmobile.msf.common.controller;
//
//import static com.ktmmobile.msf.common.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;
//
//import java.util.List;
//
//import org.apache.ibatis.session.RowBounds;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.ktmmobile.msf.common.exception.McpCommonJsonException;
//import com.ktmmobile.msf.common.util.ObjectUtils;
//import com.ktmmobile.msf.form.common.dto.CommonSearchDto;
//import com.ktmmobile.msf.common.mspservice.dto.MspSalePlcyMstDto;
//import com.ktmmobile.msf.form.common.dto.UsimBasDto;
//import com.ktmmobile.msf.form.common.dto.UsimMspDto;
//import com.ktmmobile.msf.form.common.dto.UsimMspPlcyDto;
//import com.ktmmobile.msf.form.common.mapper.UsimMapper;
//
//@RestController
//public class UsimController {
//
//	private static final Logger logger = LoggerFactory.getLogger(UsimController.class);
//
//	@Autowired
//	UsimMapper usimMapper;
//
//	/**
//	 * USIM 판매정책 리스트 정보 조회
//	 * @param
//	 * @return List<UsimMspPlcyDto>
//	 */
//	@RequestMapping(value = "/usim/listUsimMspPlcyDto", method = RequestMethod.POST)
//	public List<UsimMspPlcyDto> listUsimMspPlcys() {
//
//		List<UsimMspPlcyDto> listUsimMspPlcys = null;
//
//		try {
//
//			// Database 에서 조회함.
//			listUsimMspPlcys = usimMapper.listUsimMspPlcyDto();
//
//		} catch(Exception e) {
//			throw new McpCommonJsonException(COMMON_EXCEPTION);
//		}
//
//		return listUsimMspPlcys;
//	}
//
//	/**
//	 * USIM 판매정책코드를 이용한 USIM 상품 리스트 정보 조회
//	 * @param UsimMspDto
//	 * @return List<UsimMspDto>
//	 */
//	@RequestMapping(value = "/usim/listUsimMspDto", method = RequestMethod.POST)
//	public List<UsimMspDto> listUsimMsp(@RequestBody UsimMspDto usimMspDto) {
//
//		List<UsimMspDto> listUsimMsp = null;
//
//		try {
//
//			// Database 에서 조회함.
//			listUsimMsp = usimMapper.listUsimMspDto(usimMspDto);
//
//		} catch(Exception e) {
//			throw new McpCommonJsonException(COMMON_EXCEPTION);
//		}
//
//		return listUsimMsp;
//	}
//
//	/**
//	 * USIM 흥보 글 총 count
//	 * @param CommonSearchDto
//	 * @return int
//	 */
//	@RequestMapping(value = "/usim/usimBasTotalCount", method = RequestMethod.POST)
//	public int usimBasTotalCount(@RequestBody CommonSearchDto commonSearchDto) {
//
//		int count = 0;
//
//		try {
//
//			// Database 에서 조회함.
//			count = usimMapper.selectUsimBasTotalCount(commonSearchDto);
//
//		} catch(Exception e) {
//			throw new McpCommonJsonException(COMMON_EXCEPTION);
//		}
//
//		return count;
//	}
//
//	/**
//	 * Usimbas 테이블조회
//	 * @param UsimBasDto
//	 * @return List<UsimBasDto>
//	 */
//	@RequestMapping(value = "/usim/usimBasList", method = RequestMethod.POST)
//	public List<UsimBasDto> usimBasList(@RequestBody CommonSearchDto commonSearchDto) {
//
//		// skipResult, maxResult 값은 dto 에 포함
//		int skipResult = commonSearchDto.getApiParam1();
//		int maxResult = commonSearchDto.getApiParam2();
//		List<UsimBasDto> usimBasList = null;
//
//		try {
//
//			usimBasList = usimMapper.selectUsimBasList(commonSearchDto, new RowBounds(skipResult, maxResult));
//		} catch(Exception e) {
//			throw new McpCommonJsonException(COMMON_EXCEPTION);
//		}
//
//		return usimBasList;
//	}
//
//	/**
//     * 유효정책에 속하는 상품정보 가져오기
//     * @param UsimBasDto
//     * @return List<UsimBasDto>
//	 */
//	@RequestMapping(value = "/usim/usimPrdtList", method = RequestMethod.POST)
//	public List<UsimBasDto> usimPrdtList(@RequestBody UsimBasDto usimBasDto) {
//
//		List<UsimBasDto> usimPrdtList = null;
//
//		try {
//
//			// Database 에서 조회함.
//			usimPrdtList = usimMapper.selectUsimPrdtList(usimBasDto);
//
//		} catch(Exception e) {
//			throw new McpCommonJsonException(COMMON_EXCEPTION);
//		}
//
//		return usimPrdtList;
//	}
//
//	/**
//	 * 상품아이디로 유효정책 가져오기
//	 * @param UsimBasDto
//	 * @return List<UsimBasDto>
//	 */
//	@RequestMapping(value = "/usim/usimSalePlcyCdToPrdtList", method = RequestMethod.POST)
//	public List<UsimBasDto> usimSalePlcyCdToPrdtList(@RequestBody UsimBasDto usimBasDto) {
//
//		List<UsimBasDto> PrdtList = null;
//
//		try {
//
//			// Database 에서 조회함.
//			PrdtList = usimMapper.selectUsimSalePlcyCdToPrdtList(usimBasDto);
//
//		} catch(Exception e) {
//			throw new McpCommonJsonException(COMMON_EXCEPTION);
//		}
//
//		return PrdtList;
//	}
//
//	/**
//     * 유효정책으로 요금제 들고오기
//	 * @param UsimBasDto
//	 * @return List<UsimBasDto>
//	 */
//	@RequestMapping(value = "/usim/usimNewRateList", method = RequestMethod.POST)
//	public List<UsimBasDto> usimNewRateList(@RequestBody UsimBasDto usimBasDto) {
//
//		List<UsimBasDto> usimNewRateList = null;
//
//		try {
//
//			// Database 에서 조회함.
//			usimNewRateList = usimMapper.selectUsimNewRateList(usimBasDto);
//
//		} catch(Exception e) {
//			throw new McpCommonJsonException(COMMON_EXCEPTION);
//		}
//
//		return usimNewRateList;
//	}
//
//	/**
//	 * 배너용 정책조회
//	 * @param MspSalePlcyMstDto
//	 * @return List<MspSalePlcyMstDto>
//	 */
//	@RequestMapping(value = "/usim/usimSalePlcyCdBannerList", method = RequestMethod.POST)
//	public List<MspSalePlcyMstDto> usimSalePlcyCdBannerList(@RequestBody MspSalePlcyMstDto mspSalePlcyMstDto) {
//
//		List<MspSalePlcyMstDto> bannerList = null;
//
//		try {
//
//			// Database 에서 조회함.
//			bannerList = usimMapper.selectUsimSalePlcyCdBannerList(mspSalePlcyMstDto);
//
//		} catch(Exception e) {
//			throw new McpCommonJsonException(COMMON_EXCEPTION);
//		}
//
//		return bannerList;
//	}
//
//	/**
//	 * 약정조회  다정책반영
//	 * @param UsimBasDto
//	 * @return List<UsimBasDto>
//	 */
//	@RequestMapping(value = "/usim/mspSaleAgrmMstMoreTwoRows", method = RequestMethod.POST)
//	public List<UsimBasDto> mspSaleAgrmMstMoreTwoRows(@RequestBody UsimBasDto usimBasDto) {
//
//		List<UsimBasDto> mspSaleAgrm = null;
//
//		try {
//
//			// Database 에서 조회함.
//			mspSaleAgrm = usimMapper.selectMspSaleAgrmMstMoreTwoRows(usimBasDto);
//
//		} catch(Exception e) {
//			throw new McpCommonJsonException(COMMON_EXCEPTION);
//		}
//
//		return mspSaleAgrm;
//	}
//
//	@RequestMapping(value = "/usim/selectUsimBasDto", method = RequestMethod.POST)
//	public UsimBasDto selectUsimBasDto(@RequestBody UsimBasDto usimBasDto) {
//
//		UsimBasDto retUsimBasDto = null;
//
//		try {
//
//			// Database 에서 조회함.
//			retUsimBasDto = usimMapper.selectUsimBasDto(usimBasDto);
//
//		} catch(Exception e) {
//			throw new McpCommonJsonException(COMMON_EXCEPTION);
//		}
//
//		return retUsimBasDto;
//	}
//
//	@RequestMapping(value = "/usim/insertMcpUsimProdSort", method = RequestMethod.POST)
//	public int insertMcpUsimProdSort(@RequestBody List<UsimBasDto> usimBasDtoList) {
//
//		int insertCount = 0;
//
//		try {
//			insertCount = usimMapper.insertMcpUsimProdSort(usimBasDtoList);
//
//		} catch(Exception e) {
//			throw new McpCommonJsonException(COMMON_EXCEPTION);
//		}
//
//		return insertCount;
//	}
//
//}
