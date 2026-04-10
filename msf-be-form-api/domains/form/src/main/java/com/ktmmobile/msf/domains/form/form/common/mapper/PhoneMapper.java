//package com.ktmmobile.msf.domains.form.form.common.mapper;
//
//import java.util.List;
//import java.util.Map;
//
//import com.ktmmobile.msf.domains.form.form.common.dto.PhoneSntyBasDto;
//import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.session.RowBounds;
//
//import com.ktmmobile.msf.domains.form.form.common.dto.CommonSearchDto;
//import com.ktmmobile.msf.domains.form.form.common.dto.PhoneMspDto;
//import com.ktmmobile.msf.domains.form.form.common.dto.PhoneProdBasDto;
//import com.ktmmobile.msf.domains.form.form.common.dto.PhoneSesplsDto;
//
//@Mapper
//public interface PhoneMapper {
//
//	/**
//	 * 정책 판매중인 핸드폰 리스트 정보 조회 only msp db링크를 통한 조회만 한다.
//	 * @param CommonSearchDto param
//	 * @return
//	 * @throws Exception
//	 */
//	List<PhoneMspDto> listPhoneMspDto(CommonSearchDto param);
//
//	/**
//	 * @param CommonSearchDto param
//	 * @return
//	 * @throws Exception
//	 */
//	int listPhoneProdsCount(CommonSearchDto commonSearchDto);
//
//	/**
//	 * @param CommonSearchDto param
//	 * @return
//	 * @throws Exception
//	 */
//	List<PhoneProdBasDto> listphoneProd(CommonSearchDto commonSearchDto, RowBounds rowBounds);
//
//	/**
//	 * 핸드폰 상품관리 상세 조회
//	 * @param CommonSearchDto param
//	 * @return
//	 * @throws Exception
//	 */
//	PhoneProdBasDto findNmcpProdBas(PhoneProdBasDto phoneProdBasDto);
//
//	/**
//	 * 핸드폰 상품순서관리
//	 * @param CommonSearchDto param
//	 * @return
//	 * @throws Exception
//	 */
//	List<PhoneProdBasDto> listphoneProdForSort(CommonSearchDto commonSearchDto);
//
//	/**
//	 * 핸드폰 상품 리스트 프론트
//	 * @param CommonSearchDto param
//	 * @return
//	 * @throws Exception
//	 */
//	List<PhoneProdBasDto> listPhoneProdBasForFront(
//            CommonSearchDto commonSearchDto);
//
//	/**
//	 * 상품리스트 조회 시작
//	 * @param CommonSearchDto param
//	 * @return
//	 * @throws Exception
//	 */
//	List<PhoneProdBasDto> listPhoneProdBasForFrontOneQuery(
//            CommonSearchDto commonSearchDto);
//	
//	/**
//	 * 자급제 상품리스트 조회 시작
//	 * @param CommonSearchDto param
//	 * @return
//	 * @throws Exception
//	 */
//	List<PhoneProdBasDto> listPhoneProdBasForFrontOneQuery2(
//            CommonSearchDto commonSearchDto);
//	
//
//	/**
//	 * 상품리스트 조회 시작
//	 * @param CommonSearchDto param
//	 * @return
//	 * @throws Exception
//	 */
//	Map<String, Integer> findLte3GphoneCount(
//            CommonSearchDto commonSearchDto);
//
//
//	/**
//	 * 자급제 입고가
//	 * @param commonSearchDto
//	 * @return
//	 * @throws Exception
//	 */
//	PhoneSesplsDto selectInUnitPricQuery(CommonSearchDto commonSearchDto);
//
//	/**
//	 * 자급제 출고가
//	 * @param commonSearchDto
//	 * @return
//	 * @throws Exception
//	 */
//	PhoneSesplsDto selectOutUnitPricQuery(CommonSearchDto commonSearchDto);
//
//	List<PhoneProdBasDto> selectRepModelIdList(CommonSearchDto commonSearchDto, RowBounds rowBounds);
//
//    List<PhoneSntyBasDto> selectPhonePordSnty(PhoneSntyBasDto phoneProdBasDto);
//}
