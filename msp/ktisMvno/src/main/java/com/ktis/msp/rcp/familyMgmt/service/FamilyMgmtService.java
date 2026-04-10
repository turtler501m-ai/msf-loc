package com.ktis.msp.rcp.familyMgmt.service;

import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.rcp.familyMgmt.mapper.FamilyMgmtMapper;
import com.ktis.msp.rcp.familyMgmt.vo.FamilyMemberVO;
import com.ktis.msp.rcp.familyMgmt.vo.FamilyMgmtVO;
import com.ktis.msp.util.StringUtil;
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
public class FamilyMgmtService extends BaseService {

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertyService;
	
	@Autowired
	private FamilyMgmtMapper familyMgmtMapper;

	/** 마스킹 처리 서비스 */
	@Autowired
	private MaskingService maskingService;
	
	public List<EgovMap> getFamilyRelList(FamilyMgmtVO vo, Map<String, Object> paramMap) {
		if( vo.getSearchGbn() == null || "".equals(vo.getSearchGbn()) ) {
			if ( vo.getSrchStrtDt() == null || "".equals(vo.getSrchStrtDt())
				|| vo.getSrchEndDt() == null || "".equals(vo.getSrchEndDt()) ) {
				throw new MvnoRunException(-1, "조회일자가 존재 하지 않습니다.");
			}
		}
		
		List<EgovMap> list = familyMgmtMapper.getFamilyRelList(vo);
		
		HashMap<String, String> maskFields = getMaskFields();
		
		maskingService.setMask(list, maskFields, paramMap);
		
		return list;
	}

	public List<EgovMap> getFamilyRelListExcel(FamilyMgmtVO vo, Map<String, Object> paramMap) {
		List<EgovMap> list = familyMgmtMapper.getFamilyRelListExcel(vo);

		HashMap<String, String> maskFields = getMaskFields();

		maskingService.setMask(list, maskFields, paramMap);

		return list;
	}

	public List<EgovMap> getFamilyMemberList(FamilyMgmtVO vo, Map<String, Object> paramMap) {
		List<EgovMap> list = familyMgmtMapper.getFamilyMemberList(vo);

		HashMap<String, String> maskFields = getMaskFields();

		maskingService.setMask(list, maskFields, paramMap);

		return list;
	}

	@Transactional(rollbackFor = Exception.class)
	public void regstFamilyRel(Map<String, Object> paramMap) throws MvnoRunException {
		String strtDttm = this.getNowDttm();
		paramMap.put("strtDttm", strtDttm);

		String sessionUserId = String.valueOf(paramMap.get("SESSION_USER_ID"));
		FamilyMgmtVO familyMgmtVO = new FamilyMgmtVO();
		familyMgmtVO.setSessionUserId(sessionUserId);
		familyMgmtVO.setStrtDttm(strtDttm);

		familyMgmtMapper.insertFamilyRel(familyMgmtVO);

		paramMap.put("famSeq", familyMgmtVO.getFamSeq());
		this.addFamilyParent(paramMap);
		this.addFamilyChild(paramMap);
	}

	public void addFamilyMember(Map<String, Object> paramMap) {
		paramMap.put("strtDttm", this.getNowDttm());
		String memType = String.valueOf(paramMap.get("memType"));
		if ( "10".equals(memType) ) {
			this.addFamilyParent(paramMap);
		}
		if ( "20".equals(memType) ) {
			this.addFamilyChild(paramMap);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void cancelFamilyRel(Map<String, Object> paramMap) {
		String endDttm = this.getNowDttm();
		paramMap.put("endDttm", endDttm);

		FamilyMgmtVO familyMgmtVO = new FamilyMgmtVO();
		familyMgmtVO.setFamSeq(String.valueOf(paramMap.get("famSeq")));
		familyMgmtVO.setEndCode("DEL");
		familyMgmtVO.setEndMsg(String.valueOf(paramMap.get("endMsg")));
		familyMgmtVO.setEndDttm(endDttm);
		familyMgmtVO.setSessionUserId(String.valueOf(paramMap.get("SESSION_USER_ID")));
		familyMgmtMapper.cancelFamilyRel(familyMgmtVO);

		this.cancelFamilyAllMember(paramMap);
	}

	@Transactional(rollbackFor = Exception.class)
	public void cancelFamilyMember(Map<String, Object> paramMap) {
		FamilyMemberVO familyMemberVO = new FamilyMemberVO();
		familyMemberVO.setFamSeq(String.valueOf(paramMap.get("famSeq")));
		familyMemberVO.setSeq(String.valueOf(paramMap.get("seq")));
		familyMemberVO.setEndCode("DEL");
		familyMemberVO.setEndMsg(String.valueOf(paramMap.get("endMsg")));
		familyMemberVO.setEndDttm(this.getNowDttm());
		familyMemberVO.setSessionUserId(String.valueOf(paramMap.get("SESSION_USER_ID")));
		familyMgmtMapper.cancelFamilyMember(familyMemberVO);

		int parentCnt = familyMgmtMapper.countFamilyMemberParent(familyMemberVO);
		int childCnt = familyMgmtMapper.countFamilyMemberChild(familyMemberVO);

		if ( parentCnt > 0 && childCnt > 0 ) {
			return;
		}

		String endMsg = "";
		if ( parentCnt == 0 ) {
			endMsg = "법정대리인 부재로 인한 시스템 삭제 처리";
		}
		if ( childCnt == 0 ) {
			endMsg = "피부양자 부재로 인한 시스템 삭제 처리";
		}
		FamilyMemberVO cancelMemberVO = new FamilyMemberVO();
		cancelMemberVO.setFamSeq(familyMemberVO.getFamSeq());
		cancelMemberVO.setEndCode("SYS");
		cancelMemberVO.setEndMsg(endMsg);
		cancelMemberVO.setEndDttm(familyMemberVO.getEndDttm());
		cancelMemberVO.setSessionUserId("SYSTEM");
		familyMgmtMapper.cancelFamilyAllMember(cancelMemberVO);

		FamilyMgmtVO cancelRelVO = new FamilyMgmtVO();
		cancelRelVO.setFamSeq(familyMemberVO.getFamSeq());
		cancelRelVO.setEndCode("SYS");
		cancelRelVO.setEndMsg(endMsg);
		cancelRelVO.setEndDttm(familyMemberVO.getEndDttm());
		cancelRelVO.setSessionUserId("SYSTEM");
		familyMgmtMapper.cancelFamilyRel(cancelRelVO);
	}

	public HashMap<String, String> getMaskFields() {
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("subLinkName","CUST_NAME");
		maskFields.put("subscriberNo","MOBILE_PHO");
		maskFields.put("regstId","CUST_NAME");
		maskFields.put("rvisnId","CUST_NAME");

		return maskFields;
	}

	private void addFamilyParent(Map<String, Object> paramMap) {
		FamilyMemberVO parentVo = new FamilyMemberVO();
		parentVo.setSvcCntrNo( String.valueOf(paramMap.get("parentSvcCntrNo")) );
		parentVo.setFamSeq( String.valueOf(paramMap.get("famSeq")) );
		parentVo.setStrtDttm( String.valueOf(paramMap.get("strtDttm")) );
		parentVo.setSessionUserId( String.valueOf(paramMap.get("SESSION_USER_ID")) );

		// 필수 값 확인
		if ( StringUtil.isEmpty(parentVo.getSvcCntrNo()) ) {
			throw new MvnoRunException(2001, "법정대리인이 존재하지 않습니다.");
		}
		if ( StringUtil.isEmpty(parentVo.getFamSeq()) ) {
			throw new MvnoRunException(2002, "필수 값이 존재하지 않습니다.");
		}

		// 법정대리인과 피부양자 정보가 기등록 고객인지 확인
		if ( "Y".equals(familyMgmtMapper.isFamilyMemberParent(parentVo)) ) {
			throw new MvnoRunException(2101, "서비스계약번호[" + parentVo.getSvcCntrNo() + "]<br/>이미 법정대리인으로 등록된 고객입니다.");
		}
		if ( "Y".equals(familyMgmtMapper.isFamilyMemberChild(parentVo)) ) {
			throw new MvnoRunException(2102, "서비스계약번호[" + parentVo.getSvcCntrNo() + "]<br/>이미 피부양자로 등록된 고객입니다.");
		}

		familyMgmtMapper.insertFamilyParent(parentVo);
	}

	private void addFamilyChild(Map<String, Object> paramMap) {
		FamilyMemberVO childVo = new FamilyMemberVO();
		childVo.setSvcCntrNo( String.valueOf(paramMap.get("childSvcCntrNo")) );
		childVo.setChildType( String.valueOf(paramMap.get("childType")) );
		childVo.setFamSeq( String.valueOf(paramMap.get("famSeq")) );
		childVo.setStrtDttm( String.valueOf(paramMap.get("strtDttm")) );
		childVo.setSessionUserId( String.valueOf(paramMap.get("SESSION_USER_ID")) );

		if ( StringUtil.isEmpty(childVo.getSvcCntrNo()) ) {
			throw new MvnoRunException(3001, "피부양자가 존재하지 않습니다.");
		}
		if ( StringUtil.isEmpty(childVo.getChildType()) ) {
			throw new MvnoRunException(3002, "피부양자구분을 선택해주세요.");
		}
		if ( StringUtil.isEmpty(childVo.getFamSeq()) ) {
			throw new MvnoRunException(3003, "필수 값이 존재하지 않습니다.");
		}

		if ( "Y".equals(familyMgmtMapper.isFamilyMemberParent(childVo)) ) {
			throw new MvnoRunException(3101, "서비스계약번호[" + childVo.getSvcCntrNo() + "]<br/>이미 법정대리인으로 등록된 고객입니다.");
		}
		if ( "Y".equals(familyMgmtMapper.isFamilyMemberChild(childVo)) ) {
			throw new MvnoRunException(3102, "서비스계약번호[" + childVo.getSvcCntrNo() + "]<br/>이미 피부양자로 등록된 고객입니다.");
		}

		if ( "21".equals(childVo.getChildType()) ) {
			String childBirth = String.valueOf(paramMap.get("childBirth"));

			if ( this.getAge(childBirth) >= 19 ) {
				throw new MvnoRunException(3103, "피부양자가 미성년자가 아닙니다.");
			}

			// 미성년자일 경우 만 19세가 되는 날을 종료일시로 설정.
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				Date birth = sdf.parse(childBirth);
				Calendar cal = Calendar.getInstance();
				cal.setTime(birth);
				cal.add(Calendar.YEAR, 19);

				String endDttm = sdf.format(cal.getTime()) + "000000";
				childVo.setEndDttm(endDttm);
			} catch (ParseException e) {
				throw new MvnoRunException(3104, "정보가 올바르지 않습니다.");
			}
		}

		familyMgmtMapper.insertFamilyChild(childVo);
	}

	private void cancelFamilyAllMember(Map<String, Object> paramMap) {
		FamilyMemberVO familyMemberVO = new FamilyMemberVO();
		familyMemberVO.setFamSeq(String.valueOf(paramMap.get("famSeq")));
		familyMemberVO.setEndCode("DEL");
		familyMemberVO.setEndMsg(String.valueOf(paramMap.get("endMsg")));
		familyMemberVO.setEndDttm(String.valueOf(paramMap.get("endDttm")));
		familyMemberVO.setSessionUserId(String.valueOf(paramMap.get("SESSION_USER_ID")));

		familyMgmtMapper.cancelFamilyAllMember(familyMemberVO);
	}

	private String getNowDttm() {
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(today);
	}

	private int getAge(String birth) {
		int birthYear	= Integer.parseInt(birth.substring(0, 4));
		int birthMonth	= Integer.parseInt(birth.substring(4, 6));
		int birthDay	= Integer.parseInt(birth.substring(6, 8));

		Calendar current = Calendar.getInstance();

		int currentYear  = current.get(Calendar.YEAR);
		int currentMonth = current.get(Calendar.MONTH) + 1;
		int currentDay   = current.get(Calendar.DAY_OF_MONTH);

		int age = currentYear - birthYear;
		if (birthMonth * 100 + birthDay > currentMonth * 100 + currentDay)
			age--;

		return age;
	}
}
