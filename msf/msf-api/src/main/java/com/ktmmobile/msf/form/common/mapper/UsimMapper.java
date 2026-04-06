//package com.ktmmobile.msf.form.common.mapper;
//
//import java.util.List;
//
//import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.session.RowBounds;
//
//import com.ktmmobile.msf.form.common.dto.CommonSearchDto;
//import com.ktmmobile.msf.common.mspservice.dto.MspSalePlcyMstDto;
//import com.ktmmobile.msf.form.common.dto.UsimBasDto;
//import com.ktmmobile.msf.form.common.dto.UsimMspDto;
//import com.ktmmobile.msf.form.common.dto.UsimMspPlcyDto;
//
//@Mapper
//public interface UsimMapper {
//
//    /**
//    * USIM 판매정책 리스트 정보 조회 only msp db링크를 통한 조회만 한다.
//    * @param
//    * @return List<UsimMspPlcyDto>
//    */
//	public List<UsimMspPlcyDto> listUsimMspPlcyDto();
//
//    /**
//    * USIM 판매정책코드를 이용한 USIM 상품 리스트 정보 조회 only msp db링크를 통한 조회만 한다.
//    * @param UsimMspDto
//    * @return List<UsimMspDto>
//    */
//	public List<UsimMspDto> listUsimMspDto(UsimMspDto usimMspDto);
//
//    /**
//	* NMCP_USIM_BAS 의 USIM 흥보 글 총 count
//	* @param CommonSearchDto
//	* @return int
//	*/
//	public int selectUsimBasTotalCount(CommonSearchDto searchDto);
//
//    /**
//    * Usimbas 테이블조회
//	* @param UsimBasDto
//	* @return List<UsimBasDto>
//    */
//	public List<UsimBasDto> selectUsimBasList(CommonSearchDto commonSearchDto, RowBounds rowBounds);
//
//    /**
//    * 유효정책에 속하는 상품정보 가져오기
//    * @param UsimBasDto
//    * @return List<UsimBasDto>
//    */
//	public List<UsimBasDto> selectUsimPrdtList(UsimBasDto usimBasDto);
//
//    /**
//    * 상품아이디로 유효정책 가져오기
//    * @param UsimBasDto
//    * @return List<UsimBasDto>
//    */
//	public List<UsimBasDto> selectUsimSalePlcyCdToPrdtList(UsimBasDto usimBasDto);
//
//    /**
//    * 유효정책으로 요금제 들고오기
//    * @param UsimBasDto
//    * @return List<UsimBasDto>
//    */
//	public List<UsimBasDto> selectUsimNewRateList(UsimBasDto usimBasDto);
//
//    /**
//    * 배너용 정책조회
//    * @param UsimBasDto
//    * @return List<MspSalePlcyMstDto>
//    */
//	public List<MspSalePlcyMstDto> selectUsimSalePlcyCdBannerList(MspSalePlcyMstDto mspSalePlcyMstDto);
//
//    /**
//    * 약정조회  다정책반영
//    * @param UsimBasDto
//    * @return List<UsimBasDto>
//    */
//	public List<UsimBasDto> selectMspSaleAgrmMstMoreTwoRows(UsimBasDto usimBasDto);
//
//	public UsimBasDto selectUsimBasDto(UsimBasDto usimBasDto);
//
//	public int insertMcpUsimProdSort(List<UsimBasDto> usimBasDtoList);
//
//}
