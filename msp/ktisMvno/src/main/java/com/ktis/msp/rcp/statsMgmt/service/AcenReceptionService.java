package com.ktis.msp.rcp.statsMgmt.service;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.mapper.MaskingMapper;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.rcp.rcpMgmt.service.MplatformService;
import com.ktis.msp.rcp.statsMgmt.mapper.AcenReceptionMapper;
import com.ktis.msp.rcp.statsMgmt.vo.AcenReceptionVo;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AcenReceptionService extends BaseService {

	@Autowired
	private AcenReceptionMapper acenReceptionMapper;

	@Autowired
	private MaskingService maskingService;

	@Autowired
	private MplatformService mplatformService;

	@Autowired
	private MaskingMapper maskingMapper;


	/** (A'Cen) 각종 내역서 발급요청 리스트 조회 */
	public List<?> getAcenReceptionList(AcenReceptionVo searchVO, Map<String, Object> paramMap) {

		List<EgovMap> list = acenReceptionMapper.getAcenReceptionList(searchVO);
		HashMap<String, String> maskFields = getMaskFields();
		maskingService.setMask(list, maskFields, paramMap);
		return list;
	}

	/** (A'Cen) 각종 내역서 발급요청 엑셀리스트 조회 */
	public List<?> getAcenReceptionListExcel(AcenReceptionVo searchVO, Map<String, Object> paramMap) {

		List<EgovMap> list = acenReceptionMapper.getAcenReceptionListExcel(searchVO);
		HashMap<String, String> maskFields = getMaskFields();
		maskingService.setMask(list, maskFields, paramMap);
		return list;
	}

	/** 이메일 조회 (MP 연동) */
	public String getEmail(AcenReceptionVo searchVO) {

		// MP 연동 데이터 조회
		Map<String, String> mpData = acenReceptionMapper.getMpData(searchVO.getContractNum());

		if(KtisUtil.isEmpty(mpData)){
			throw new MvnoRunException(-1, "잘못된 파라미터입니다.");
		}

		if("C".equals(mpData.get("SUB_STATUS"))){
			throw new MvnoRunException(-1, "해지 고객입니다.");
		}

		// 가입정보조회 (X01)
		Map<String, Object> result = mplatformService.perMyktfInfo(mpData.get("NCN"), mpData.get("CTN"), mpData.get("CUST_ID"));

		String code = String.valueOf(result.get("code"));
		String msg = String.valueOf(result.get("msg"));
		String email = (result.get("email") == null) ? "" : String.valueOf(result.get("email"));

		// MP 연동 오류
		if(!"0000".equals(code)){
			throw new MvnoRunException(-1, msg);
		}

		// 이메일정보 존재 → 권한에 따라 마스킹처리
		if(!"".equals(email)){

			HashMap<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("SESSION_USER_ID", searchVO.getSessionUserId());

			List<?> mskGrp =  maskingMapper.selectList(paramMap);
			Map mskGrpRow = (Map) mskGrp.get(0);
			String mskAuthYn = String.valueOf(mskGrpRow.get("mskAuthYn"));

			if(!"Y".equals(mskAuthYn)){
				Map<String,String> cmnMskGrp = new HashMap<String,String>();
				cmnMskGrp.put("mskGrpId", "EMAIL");
				email = maskingService.getMaskString(email, cmnMskGrp);
			}
		}

		return email;
	}

	/** (A'Cen) 각종 내역서 발급요청 완료처리 */
	@Transactional(rollbackFor=Exception.class)
	public void completeAcenReception(List<AcenReceptionVo> voList, AcenReceptionVo searchVO) {

		for(AcenReceptionVo vo : voList){

			if(KtisUtil.isEmpty(vo.getSeq())){
				throw new MvnoRunException(-1, "필수값이 누락되었습니다.");
			}

			if(!"00".equals(vo.getStatus())){
				throw new MvnoRunException(-1, "처리상태가 완료인 건이 존재합니다.");
			}

			vo.setSessionUserId(searchVO.getSessionUserId());
			vo.setStatus("01");

			acenReceptionMapper.completeAcenReception(vo);    // 완료처리
			acenReceptionMapper.insertAcenReceptionHist(vo);  // 이력저장
		}
	}

	/** (A'Cen) 각종 내역서 발급요청 완료 취소처리 */
	@Transactional(rollbackFor=Exception.class)
	public void cancelAcenReception(List<AcenReceptionVo> voList, AcenReceptionVo searchVO) {

		for(AcenReceptionVo vo : voList){

			if(KtisUtil.isEmpty(vo.getSeq())){
				throw new MvnoRunException(-1, "필수값이 누락되었습니다.");
			}

			if(!"01".equals(vo.getStatus())){
				throw new MvnoRunException(-1, "처리상태가 접수인 건이 존재합니다.");
			}

			vo.setSessionUserId(searchVO.getSessionUserId());
			vo.setStatus("00");

			acenReceptionMapper.cancelAcenReception(vo);      // 완료취소처리
			acenReceptionMapper.insertAcenReceptionHist(vo);  // 이력저장
		}
	}

	private HashMap<String, String> getMaskFields() {
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("cstmrName", "CUST_NAME");
		maskFields.put("procName", "CUST_NAME");
		maskFields.put("rvisnName", "CUST_NAME");
		maskFields.put("faxNo", "MOBILE_PHO");
		return maskFields;
	}

}
