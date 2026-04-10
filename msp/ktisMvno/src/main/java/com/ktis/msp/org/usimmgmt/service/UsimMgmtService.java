package com.ktis.msp.org.usimmgmt.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ktis.msp.base.KtisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.org.usimmgmt.mapper.UsimMgmtMapper;
import com.ktis.msp.org.usimmgmt.vo.UsimMgmtVO;

import egovframework.rte.psl.dataaccess.util.EgovMap;

@Service
public class UsimMgmtService extends BaseService {
		
	@Autowired
	private MaskingService maskingService;
	
	@Autowired
	private UsimMgmtMapper usimMgmtMapper;
	
	public List<?> getUsimMgmtList(UsimMgmtVO usimMgmtVO) {
		
		List<EgovMap> result = (List<EgovMap>) usimMgmtMapper.getUsimMgmtList(usimMgmtVO);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
        
		paramMap.put("SESSION_USER_ID", usimMgmtVO.getSessionUserId());
		
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("regstId",	"SYSTEM_ID");
		maskFields.put("rvisnId",	"SYSTEM_ID");
		maskFields.put("usimNoMsk",		"USIM");
		
		maskingService.setMask(result, maskFields, paramMap);
		
		return result;
	}
	
	public List<?> getActiveUsimList(UsimMgmtVO usimMgmtVO, Map<String, Object> paramMap) {
		
		List<EgovMap> result = (List<EgovMap>) usimMgmtMapper.getActiveUsimList(usimMgmtVO);
		
		maskingService.setMask(result, maskingService.getMaskFields(), paramMap);
		
		return result;
	}
	
	public List<?> getActiveUsimListByExcel(UsimMgmtVO usimMgmtVO, Map<String, Object> paramMap) {
		
		List<EgovMap> result = (List<EgovMap>) usimMgmtMapper.getActiveUsimListByExcel(usimMgmtVO);
		
		maskingService.setMask(result, maskingService.getMaskFields(), paramMap);
		
		return result;
	}
	
	public List<?> getUsimHistList(UsimMgmtVO usimMgmtVO) {
		
		List<EgovMap> result = (List<EgovMap>) usimMgmtMapper.getUsimHistList(usimMgmtVO);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
        
		paramMap.put("SESSION_USER_ID", usimMgmtVO.getSessionUserId());
		
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("evntRegstId",	"SYSTEM_ID");
		maskFields.put("usimNo",		"USIM");
		
		maskingService.setMask(result, maskFields, paramMap);
		
		return result;
	}
	
	public boolean getUsimDupChk(String usimNo) {
		return usimMgmtMapper.getUsimDupChk(usimNo) == 0;
	}
	
	@Transactional(rollbackFor=Exception.class)
	public void insertUsimMgmt(UsimMgmtVO usimMgmtVO) {

		// 유심접점 미입력 시 MVNO-RDS 연동 데이터로 INSERT (접점, 모델ID, 주문번호, 배송일자)
		if(KtisUtil.isEmpty(usimMgmtVO.getOrgnId())){

			UsimMgmtVO dlvrTxnInfo = usimMgmtMapper.getMdsIntmDlvrTxn(usimMgmtVO.getUsimNo());
			if(dlvrTxnInfo == null){
				throw new MvnoRunException(-1, "해당 유심번호가 MVNO-RDS 연동 데이터에 존재하지 않거나 처리 기준과 맞지 않습니다.");
			}

			usimMgmtVO.setOrgnId(dlvrTxnInfo.getOrgnId());
			usimMgmtVO.setDeliveryDttm(dlvrTxnInfo.getDeliveryDttm());
			usimMgmtVO.setOrderNum(dlvrTxnInfo.getOrderNum());
			usimMgmtVO.setUsimPrdtId(dlvrTxnInfo.getUsimPrdtId());
			usimMgmtVO.setUsimPrdtCode(dlvrTxnInfo.getUsimPrdtCode());
		}

		usimMgmtMapper.insertUsimMgmt(usimMgmtVO);
		usimMgmtVO.setEvntCd("I");
		usimMgmtMapper.insertUsimHist(usimMgmtVO);
	}
	
	@Transactional(rollbackFor=Exception.class)
	public void updateUsimMgmt(UsimMgmtVO usimMgmtVO) {
		usimMgmtMapper.updateUsimMgmt(usimMgmtVO);
		usimMgmtVO.setEvntCd("U");
		usimMgmtMapper.insertUsimHist(usimMgmtVO);
	}
	
	@Transactional(rollbackFor=Exception.class)
	public void deleteUsimMgmt(UsimMgmtVO usimMgmtVO) {
		usimMgmtMapper.deleteUsimMgmt(usimMgmtVO);
		usimMgmtVO.setEvntCd("D");
		usimMgmtMapper.insertUsimHist(usimMgmtVO);
	}
	
	
//	@Transactional(rollbackFor=Exception.class)
//	public int updateUsimVrfyYn(UsimMgmtVO usimMgmtVO) {
//		return usimMgmtMapper.updateUsimVrfyYn(usimMgmtVO);
//	}
	
	// 2021.01.21 [SRM21011583814] M전산 제휴유심관리 內 단말기코드 항목 추가요청 모델코드 기준으로 모델ID 구하는 로직 및  모델ID,모델코드 추가하여 INSERT하도록 수정
	@Transactional(rollbackFor=Exception.class)
	public void regUsimMgmtList(UsimMgmtVO usimMgmtVO, String usrId) {
		
		List<UsimMgmtVO> itemList = usimMgmtVO.getItems();
		String usimPrdtId = ""; //모델ID

		if(KtisUtil.isEmpty(itemList)){
			throw new MvnoRunException(-1,"등록할 데이터가 없습니다.");
		}

		for(int i = 0; i < itemList.size(); i++) {

			UsimMgmtVO vo = itemList.get(i);

			if (vo.getUsimNo() == null || "".equals(vo.getUsimNo())){
				throw new MvnoRunException(-1, "["+ (i+1) +"행]유심일련번호가 존재하지 않습니다.");
			}

			if (usimMgmtMapper.getUsimDupChk(vo.getUsimNo()) > 0) {
				throw new MvnoRunException(-1, "["+ (i+1) +"행]이미 등록된 유심일련번호가 존재 합니다.["+vo.getUsimNo()+"]");
			}

			// 유심접점 미입력 시 MVNO-RDS 연동 데이터로 INSERT (접점, 모델ID, 주문번호, 배송일자)
			if (vo.getOrgnId() == null || "".equals(vo.getOrgnId())){

				UsimMgmtVO dlvrTxnInfo = usimMgmtMapper.getMdsIntmDlvrTxn(vo.getUsimNo());
				if(dlvrTxnInfo == null){
					throw new MvnoRunException(-1, "["+ (i+1) +"행]유심일련번호가 MVNO-RDS 연동 데이터에 존재하지 않거나 처리 기준과 맞지 않습니다.");
				}

				vo.setOrgnId(dlvrTxnInfo.getOrgnId());
				vo.setDeliveryDttm(dlvrTxnInfo.getDeliveryDttm());
				vo.setOrderNum(dlvrTxnInfo.getOrderNum());
				vo.setUsimPrdtId(dlvrTxnInfo.getUsimPrdtId());
				vo.setUsimPrdtCode(dlvrTxnInfo.getUsimPrdtCode());
			}

			if (vo.getDeliveryDttm() != null && vo.getDeliveryDttm().length() != 0 && vo.getDeliveryDttm().length() != 8 ) {
				throw new MvnoRunException(-1, "["+ (i+1) +"행]배송일자를 [YYYYMMDD]형식으로 입력해주세요.["+vo.getDeliveryDttm()+"]");
			}

			if (vo.getOrderNum() != null && vo.getOrderNum().length() != 0 && vo.getOrderNum().length() > 20) {
				throw new MvnoRunException(-1, "["+ (i+1) +"행]주문번호를 20자 이내로 입력해주세요.["+vo.getOrderNum()+"]");
			}

			if(vo.getUsimPrdtCode() != null && vo.getUsimPrdtCode().length() != 0 && KtisUtil.isEmpty(vo.getUsimPrdtId())) {
				vo.setUsimPrdtCode(vo.getUsimPrdtCode().toUpperCase());

				//모델id 존재 여부 체크
				usimPrdtId =usimMgmtMapper.isExistRprsPrdtIdUsimMgmt(vo);
				
				if(usimPrdtId == null || "".equals(usimPrdtId)) {
					throw new MvnoRunException(-1, "["+ (i+1) +"행]모델코드를 확인해 주세요.["+vo.getUsimPrdtCode()+"]");
				}

				vo.setUsimPrdtId(usimPrdtId);
			}
			
			vo.setSessionUserId(usrId);
			
			usimMgmtMapper.insertUsimMgmt(vo);
			vo.setEvntCd("I");
			usimMgmtMapper.insertUsimHist(vo);
						
		}
  }
}
