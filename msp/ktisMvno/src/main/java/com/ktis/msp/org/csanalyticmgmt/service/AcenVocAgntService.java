package com.ktis.msp.org.csanalyticmgmt.service;

import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.org.csanalyticmgmt.mapper.AcenVocAgntMapper;
import com.ktis.msp.org.csanalyticmgmt.vo.AcenVocAgntVO;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AcenVocAgntService extends BaseService {

	@Autowired
	private AcenVocAgntMapper acenVocAgntMapper;

	@Autowired
	private MaskingService maskingService;

	/** (A'Cen) VOC 담당자 정보 리스트 조회 */
	public List<?> getAcenVocAgntList(AcenVocAgntVO searchVO, Map<String, Object> paramMap) {

		List<EgovMap> list = acenVocAgntMapper.getAcenVocAgntList(searchVO);
		HashMap<String, String> maskFields = getMaskFields();
		maskingService.setMask(list, maskFields, paramMap);
		return list;
	}

	/** (A'Cen) VOC 담당자 정보 리스트 엑셀 다운로드 */
	public List<?> getAcenVocAgntListExcel(AcenVocAgntVO searchVO, Map<String, Object> paramMap) {

		List<EgovMap> list = acenVocAgntMapper.getAcenVocAgntListExcel(searchVO);
		HashMap<String, String> maskFields = getMaskFields();
		maskingService.setMask(list, maskFields, paramMap);
		return list;
	}

	/** (A'Cen) VOC 담당자 정보 등록 및 수정 */
	public int mergeAcenVocAgnt(AcenVocAgntVO searchVO){

		int iCnt = 0;

		// 대리점 코드 검사
		int orgnIdCnt = acenVocAgntMapper.getOrgnIdCnt(searchVO.getVocAgntCd());
		if(orgnIdCnt <= 0){
			throw new MvnoRunException(-1, "올바른 대리점을 입력해주세요.");
		}

		// 사용자ID 검사
		int userIdCnt = acenVocAgntMapper.getUserIdCnt(searchVO.getUsrId());
		if(userIdCnt <= 0){
			throw new MvnoRunException(-1, "존재하지 않는 사용자입니다.");
		}

		// 중복 검사
		int dupCnt = acenVocAgntMapper.getAcenVocAgntDupCnt(searchVO);
		if(dupCnt > 0 && searchVO.getSeq() <= 0){
			throw new MvnoRunException(-1, "해당 대리점에 등록되어 있는 담당자입니다.");
		}

		if(searchVO.getSeq() <= 0){
			iCnt = acenVocAgntMapper.insertAcenVocAgnt(searchVO);
		}else{
			iCnt = acenVocAgntMapper.updateAcenVocAgnt(searchVO);
		}

		return iCnt;
	}

	private HashMap<String, String> getMaskFields() {
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("vocAgntName", "CUST_NAME");
		maskFields.put("regstName", "CUST_NAME");
		maskFields.put("rvisnName", "CUST_NAME");
		maskFields.put("mobileNum", "MOBILE_PHO");
		maskFields.put("vocAgntId",	"SYSTEM_ID");
		return maskFields;
	}

}
