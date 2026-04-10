package com.ktis.msp.org.workmgmt.service;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.org.workmgmt.mapper.WorkMgmtMapper;

import com.ktis.msp.org.workmgmt.vo.WorkMgmtVO;
import com.ktis.msp.util.StringUtil;
import egovframework.rte.fdl.cmmn.exception.EgovBizException;
import egovframework.rte.fdl.property.EgovPropertyService;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class WorkMgmtService extends BaseService {
    @Autowired
    private WorkMgmtMapper workMgmtMapper;

	@Autowired
	private MaskingService maskingService;

    /** propertiesService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;

    /** Constructor */
    public WorkMgmtService() {
        setLogPrefix("[WorkMgmtService] ");
    }


	//업무 목록 조회
	public List<EgovMap> getWorkTmplList(WorkMgmtVO workMgmtVO, Map<String, Object> paramMap) {

		/* 유효성 검사 */
		if(KtisUtil.isEmpty(workMgmtVO.getSearchBaseDate())) {
			throw new MvnoRunException(-1, "기준일자가 존재하지 않습니다.");
		}
		List<EgovMap> list = workMgmtMapper.getWorkTmplList(workMgmtVO);
		maskingService.setMask(list, maskingService.getMaskFields(), paramMap);

		return list;
	}

	//업무 목록 엑셀다운로드
	public List<EgovMap> getWorkTmplListExcel(WorkMgmtVO workMgmtVO, Map<String, Object> paramMap) {

		/* 유효성 검사 */
		if(KtisUtil.isEmpty(workMgmtVO.getSearchBaseDate())) {
			throw new MvnoRunException(-1, "기준일자가 존재하지 않습니다.");
		}
		List<EgovMap> list = workMgmtMapper.getWorkTmplListExcel(workMgmtVO);
		maskingService.setMask(list, maskingService.getMaskFields(), paramMap);

		return list;
	}

	//수취서류구분 저장 (등록/수정)
	public void saveWorkItem(WorkMgmtVO workMgmtVO) throws EgovBizException  {
		if(StringUtil.isEmpty(workMgmtVO.getItemNm())) throw new EgovBizException("서류명이 존재하지 않습니다");
		if(workMgmtVO.getItemNm().length() > 100) throw new EgovBizException("서류명은 최대 100자까지 입력 가능합니다.");
		if(StringUtil.isEmpty(workMgmtVO.getRequestYn())) throw new EgovBizException("신청서여부가 존재하지 않습니다");
		//중복체크
		int cnt = workMgmtMapper.dupChkItem(workMgmtVO);
		if(cnt > 0) throw new EgovBizException("이미 등록된 서류명입니다.");

		String itemId = workMgmtVO.getItemId();

		// 신규등록
		if(StringUtil.isEmpty(itemId)){
			workMgmtMapper.insertWorkItem(workMgmtVO);
		} else { //수정
 			workMgmtMapper.updateWorkItem(workMgmtVO);
		}
	}

	//수취서류구분 종료(삭제)
	@Transactional(rollbackFor = Exception.class)
	public void updateWorkItemEnd(WorkMgmtVO workMgmtVO) throws EgovBizException  {
		if(workMgmtVO.getItemList() == null || workMgmtVO.getItemList().isEmpty()) throw new EgovBizException("선택한 수취서류가 존재하지 않습니다");
		for (WorkMgmtVO vo : workMgmtVO.getItemList()) {

			String itemId = vo.getItemId();
			String itemNm = vo.getItemNm();

			//업무템플릿에서 사용중인지 확인
			checkWorkTmplInUse("itemId", itemId, itemNm);

			workMgmtVO.setItemId(itemId);
			workMgmtMapper.updateWorkItemEnd(workMgmtVO);
		}
	}

	//수취서류구분 목록 조회
	public List<EgovMap> getWorkItemList(WorkMgmtVO workMgmtVO, Map<String, Object> paramMap)  {
		List<EgovMap> list = workMgmtMapper.getWorkItemList(workMgmtVO);
		maskingService.setMask(list, maskingService.getMaskFields(), paramMap);
		return list;
	}

	//명의자구분 저장(등록/수정)
	public void saveWorkOwner(WorkMgmtVO workMgmtVO) throws EgovBizException {
		if(StringUtil.isEmpty(workMgmtVO.getOwnerNm())) throw new EgovBizException("명의자명이 존재하지 않습니다");
		if(workMgmtVO.getOwnerNm().length() > 20) throw new EgovBizException("명의자명은 최대 20자까지 입력 가능합니다.");

		//중복체크
		int cnt = workMgmtMapper.dupChkOwner(workMgmtVO);
		if(cnt > 0) throw new EgovBizException("이미 등록된 명의자명 입니다.");

		String ownerId = workMgmtVO.getOwnerId();
		// 신규등록
		if(StringUtil.isEmpty(ownerId)){
			workMgmtMapper.insertWorkOwner(workMgmtVO);
		} else { //수정
			workMgmtMapper.updateWorkOwner(workMgmtVO);
		}
	}

	//명의자구분 종료(삭제)
	@Transactional(rollbackFor = Exception.class)
	public void updateWorkOwnerEnd(WorkMgmtVO workMgmtVO) throws EgovBizException  {
		if(workMgmtVO.getOwnerList() == null || workMgmtVO.getOwnerList().isEmpty()) throw new EgovBizException("선택한 명의자구분이 존재하지 않습니다");
		for (WorkMgmtVO vo : workMgmtVO.getOwnerList()) {

			String ownerId = vo.getOwnerId();
			String ownerNm = vo.getOwnerNm();
			//업무템플릿에서 사용중인지 확인
			checkWorkTmplInUse("ownerId", ownerId, ownerNm);

			workMgmtVO.setOwnerId(ownerId);
			workMgmtMapper.updateWorkOwnerEnd(workMgmtVO);
		}
	}

	//명의자구분 목록 조회
	public List<EgovMap> getWorkOwnerList(WorkMgmtVO workMgmtVO, Map<String, Object> paramMap)  {
		List<EgovMap> list = workMgmtMapper.getWorkOwnerList(workMgmtVO);
		maskingService.setMask(list, maskingService.getMaskFields(), paramMap);
		return list;
	}

	//요청자구분 저장(등록/수정)
	public void saveWorkRqstr(WorkMgmtVO workMgmtVO) throws EgovBizException {
		if(StringUtil.isEmpty(workMgmtVO.getRqstrNm())) throw new EgovBizException("요청자명이 존재하지 않습니다");
		if(workMgmtVO.getRqstrNm().length() > 30) throw new EgovBizException("요청자명은 최대 30자까지 입력 가능합니다.");
		//중복체크
		int cnt = workMgmtMapper.dupChkRqstr(workMgmtVO);
		if(cnt > 0) throw new EgovBizException("이미 등록된 요청자명 입니다.");

		String rqstrId = workMgmtVO.getRqstrId();
		// 신규등록
		if(StringUtil.isEmpty(rqstrId)){
			workMgmtMapper.insertWorkRqstr(workMgmtVO);
		} else { //수정
			workMgmtMapper.updateWorkRqstr(workMgmtVO);
		}
	}

	//요청자구분 종료(삭제)
	@Transactional(rollbackFor = Exception.class)
	public void updateWorkRqstrEnd(WorkMgmtVO workMgmtVO) throws EgovBizException  {
		if(workMgmtVO.getRqstrList() == null || workMgmtVO.getRqstrList().isEmpty()) throw new EgovBizException("선택한 요청자구분이 존재하지 않습니다");
		for (WorkMgmtVO vo : workMgmtVO.getRqstrList()) {

			String rqstrId = vo.getRqstrId();
			String rqstrNm = vo.getRqstrNm();

			//업무템플릿에서 사용중인지 확인
			checkWorkTmplInUse("rqstrId", rqstrId, rqstrNm);

			workMgmtVO.setRqstrId(rqstrId);
			workMgmtMapper.updateWorkRqstrEnd(workMgmtVO);
		}
	}


	//요청자구분 목록 조회
	public List<EgovMap> getWorkRqstrList(WorkMgmtVO workMgmtVO, Map<String, Object> paramMap)  {
		List<EgovMap> list = workMgmtMapper.getWorkRqstrList(workMgmtVO);
		maskingService.setMask(list, maskingService.getMaskFields(), paramMap);
		return list;
	}

    //문자템플릿 목록조회
    public List<EgovMap> getWorkSmsList(WorkMgmtVO workMgmtVO)  {
        return workMgmtMapper.getWorkSmsList(workMgmtVO);
    }

    //문자템플릿 목록조회
    public List<EgovMap> getWorkSmsListByWorkTmplId(WorkMgmtVO workMgmtVO)  {
        return workMgmtMapper.getWorkSmsListByWorkTmplId(workMgmtVO);
    }

	//업무템플릿 저장(등록/수정)
	@Transactional(rollbackFor = Exception.class)
	public void saveWorkTmpl(WorkMgmtVO workMgmtVO) throws EgovBizException, ParseException {

		if(StringUtil.isEmpty(workMgmtVO.getWorkTmplNm())) throw new EgovBizException("업무명이 존재하지 않습니다");
		if(workMgmtVO.getWorkTmplNm().length() > 50) throw new EgovBizException("업무명은 최대 50자까지 입력 가능합니다.");
		if(StringUtil.isEmpty(workMgmtVO.getWorkId())) throw new EgovBizException("업무유형이 존재하지 않습니다");
		if(StringUtil.isEmpty(workMgmtVO.getUseYn())) throw new EgovBizException("사용여부가 존재하지 않습니다");

		if(workMgmtVO.getOwnerList() == null || workMgmtVO.getOwnerList().isEmpty()) throw new EgovBizException("선택한 명의자구분이 존재하지 않습니다");
		if(workMgmtVO.getRqstrList() == null || workMgmtVO.getRqstrList().isEmpty()) throw new EgovBizException("선택한 요청자구분이 존재하지 않습니다");
		if(workMgmtVO.getItemList() == null || workMgmtVO.getItemList().isEmpty()) throw new EgovBizException("선택한 수취서류가 존재하지 않습니다");
		if(workMgmtVO.getItemList().size() > 10) throw new EgovBizException("수취서류는 최대 10개까지만 선택할 수 있습니다.");
		if(workMgmtVO.getSmsTemplateList() == null || workMgmtVO.getSmsTemplateList().isEmpty()) throw new EgovBizException("선택한 문자템플릿이 존재하지 않습니다");

		String workTmplId = workMgmtVO.getWorkTmplId();

		//업무템플릿 신규등록
		if(StringUtil.isEmpty(workTmplId)) {
			this.insertWorkTmpl(workMgmtVO);
		} else {
			//종료여부 날짜 확인
			String dbEndDtStr = workMgmtMapper.getWorkTmplEndDt(workTmplId);
			if(StringUtil.isEmpty(dbEndDtStr)) throw new EgovBizException("종료일이 존재하지 않습니다.");

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			try {
				Date dbEndDt = sdf.parse(dbEndDtStr);
				Date now = new Date();
				//현재보다 이전날짜의 경우
				if (dbEndDt.before(now)) {
					throw new EgovBizException("이미 종료된 업무는 수정할 수 없습니다.");
				}
			} catch (ParseException e) {
				throw new EgovBizException(e.getMessage());
			}

			//이전 수취서류, 명의자, 요청자, 문자템플릿 조회
			List<String> oldItemList = workMgmtMapper.getWorkTmplItemList(workTmplId);
			List<String> oldOwnerList = workMgmtMapper.getWorkTmplOwnerList(workTmplId);
			List<String> oldRqstrList = workMgmtMapper.getWorkTmplRqstrList(workTmplId);
			List<String> oldSmsList = workMgmtMapper.getWorkTmplSmsList(workTmplId);
			// 비교를 위해 Set으로 변환
			Set<String> oldItemSet = new HashSet<String>(oldItemList);
			Set<String> oldOwnerSet = new HashSet<String>(oldOwnerList);
			Set<String> oldRqstrSet = new HashSet<String>(oldRqstrList);
			Set<String> oldSmsSet = new HashSet<String>(oldSmsList);

			Set<String> newItemSet = new HashSet<String>();
			for (WorkMgmtVO vo : workMgmtVO.getItemList()) {
				newItemSet.add(vo.getItemId());
			}

			Set<String> newOwnerSet = new HashSet<String>();
			for (WorkMgmtVO vo : workMgmtVO.getOwnerList()) {
				newOwnerSet.add(vo.getOwnerId());
			}

			Set<String> newRqstrSet = new HashSet<String>();
			for (WorkMgmtVO vo : workMgmtVO.getRqstrList()) {
				newRqstrSet.add(vo.getRqstrId());
			}

			Set<String> newSmsSet = new HashSet<String>();
			for (WorkMgmtVO vo : workMgmtVO.getSmsTemplateList()) {
				newSmsSet.add(vo.getSmsTemplateId());
			}

			// 기존 데이터 변경 여부 확인
			boolean isChanged = !oldItemSet.equals(newItemSet) ||
					!oldOwnerSet.equals(newOwnerSet) ||
					!oldRqstrSet.equals(newRqstrSet) ||
					!oldSmsSet.equals(newSmsSet);

			if(isChanged) {
				//업무템플릿 기존 데이터 종료
				workMgmtMapper.updateWorkTmplEnd(workMgmtVO);

				//업무템플릿 신규등록
				this.insertWorkTmpl(workMgmtVO);
			} else {
				//업무템플릿 수정(업무유형, 업무명, 사용여부)
				workMgmtMapper.updateWorkTmpl(workMgmtVO);
			}
		}
	}

	//업무정보 신규등록
	@Transactional(rollbackFor = Exception.class)
	public void insertWorkTmpl(WorkMgmtVO workMgmtVO) throws EgovBizException {

		try {

			//업무 템플릿 등록
			workMgmtMapper.insertWorkTmpl(workMgmtVO);

			//업무 템플릿 수취서류 관계 등록
			for (WorkMgmtVO vo : workMgmtVO.getItemList()) {
				workMgmtVO.setItemId(vo.getItemId());
				workMgmtMapper.insertWorkTmplItem(workMgmtVO);
			}
			//업무 템플릿 명의자 관계 등록
			for (WorkMgmtVO vo : workMgmtVO.getOwnerList()) {
				workMgmtVO.setOwnerId(vo.getOwnerId());
				workMgmtMapper.insertWorkTmplOwner(workMgmtVO);
			}
			//업무 템플릿 요청자 관계 등록
			for (WorkMgmtVO vo : workMgmtVO.getRqstrList()) {
				workMgmtVO.setRqstrId(vo.getRqstrId());
				workMgmtMapper.insertWorkTmplRqstr(workMgmtVO);
			}

			//업무 템플릿 문자템플릿 관계 등록
			for (WorkMgmtVO vo : workMgmtVO.getSmsTemplateList()) {
				workMgmtVO.setSmsTemplateId(vo.getSmsTemplateId());
				workMgmtMapper.insertWorkTmplSms(workMgmtVO);
			}
		} catch(Exception e) {
			logger.info(e.getMessage());
			throw new EgovBizException("저장 처리 중 오류가 발생하였습니다.");
		}
	}

	//업무유형 목록조회
	public List<EgovMap> getWorkIdList() {
		List<EgovMap> list = workMgmtMapper.getWorkIdList();
		return list;
	}

    public List<EgovMap> getRqstrCdList() {
        return workMgmtMapper.getRqstrCdList();
    }

	// id가 업무템플릿에서 사용중인지 확인
	private void checkWorkTmplInUse(String type, String id, String Nm) throws EgovBizException {
		Map<String, String> paramMap = new HashMap<String, String>();

		paramMap.put(type, id);
		EgovMap workMap = workMgmtMapper.checkWorkTmplInUse(paramMap);

		if(workMap != null) {
			String workTmplNm = (String) workMap.get("workTmplNm");
			String workTmplId = (String) workMap.get("workTmplId");
			throw new EgovBizException("["+ Nm +"]는 <br> 현재[" + workTmplNm + "/" + workTmplId + "]업무에서 <br>사용 중이므로 삭제가 불가합니다.");
		}
	}

}
