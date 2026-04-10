package com.ktmmobile.mcp.dormant.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ktmmobile.mcp.common.util.DateTimeUtil;
import com.ktmmobile.mcp.mstore.dao.MstoreDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktmmobile.mcp.dormant.dao.DormantDao;
import com.ktmmobile.mcp.dormant.dto.DormantDto;

@Service
public class DormantSvc {

    private static final Logger logger = LoggerFactory.getLogger(DormantSvc.class);

	@Autowired
	private DormantDao dormantDao;

	@Autowired
	private MstoreDao mstoreDao;

	public List<DormantDto> selectDormantList() {
		return dormantDao.selectDormantList();
	}

	public int deleteAssociateMember() {
		return dormantDao.deleteAssociateMember();
	}

	public int updateAssociateMember() {

		// 준회원 탈퇴처리 전, M스토어 이용약관에 동의한 경우.. 동의 만료처리
		Map<String,String> paraMap= new HashMap<>();
		paraMap.put("rvisnDttm", DateTimeUtil.getFormatString("yyyyMMddHHmmss")); // update 대상 다시 select하기 위해 map으로 처리
		paraMap.put("rvisnId", "BATCH");

		int mstoreUpdCnt= mstoreDao.updateAgreeAssociateMember(paraMap);
		if(mstoreUpdCnt > 0){
			// M스토어 탈퇴연동 대상에 INSERT
			mstoreDao.insertMstoreCanTrg(paraMap);
		}

		return dormantDao.updateAssociateMember();
	}

	public List<DormantDto> selectDormantRegularList() {
		return dormantDao.selectDormantRegularList();
	}

	public int insertDormantRegularMember(String userid) {
		return dormantDao.insertDormantRegularMember(userid);
	}

	public int deleteDormantRegularMember(String userid) {
		return dormantDao.deleteDormantRegularMember(userid);
	}

}
