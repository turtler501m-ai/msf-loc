package com.ktis.msp.m2m.pscboard.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.m2m.pscboard.mapper.PscBoardMapper;

import egovframework.rte.psl.dataaccess.util.EgovMap;

/**
 * @Class Name : BoardService.java
 * @Description : BoardService Class
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2014.09.01    임지혜       최초생성
 *
 * @author 개발프레임웍크 실행환경 개발팀
 * @since 2009. 03.16
 * @version 1.0
 * @see
 *
 *  Copyright (C) by MOPAS All right reserved.
 */

@Service
public class PscBoardService extends BaseService {
	
	@Autowired
	private PscBoardMapper pscBoardMapper;
	
	@Autowired
	private MaskingService maskingService;
	
	
	public PscBoardService() {
		setLogPrefix("[BoardService] ");
	}
	
	public List<?> noticeList(Map<String, Object> param){
		
		List<EgovMap> result = (List<EgovMap>) pscBoardMapper.noticeList(param);
		
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("REGST_USR_NM",	"CUST_NAME");
		
		maskingService.setMask(result, maskFields, param);
		
		return result;
	}
	
	public String hqAuth(Map<String, Object> param){
		return pscBoardMapper.hqAuth(param);
	}
	
	public String getNoticeId(){
		return pscBoardMapper.getNoticeId();
	}
	
	@Transactional(rollbackFor=Exception.class)
	public String noticeInsert(Map<String, Object> param){
		
		String id = pscBoardMapper.getNoticeId();
		
		param.put("NOTICE_ID", id);
		
		pscBoardMapper.noticeInsert(param);
		
		return id;
	}
	
	@Transactional(rollbackFor=Exception.class)
	public void noticeUpdate(Map<String, Object> param){
		
		pscBoardMapper.noticeUpdate(param);
	}
	
	@Transactional(rollbackFor=Exception.class)
	public int noticeDelete(Map<String, Object> param){
		
		return pscBoardMapper.noticeDelete(param);
	}
	
	public List<?> getNoticeFile(Map<String, Object> param){
		return pscBoardMapper.getNoticeFile(param);
	}
	
    /**
     * @Description : 파일 목록 조회(파일유형에 따라서 조회)
     * @Param  : String
     * @Return : List<?>
     * @Author : 김용문
     * @Create Date : 2017. 11. 20.
     */
    @Transactional(rollbackFor=Exception.class)
    public int getFileExtList(HashMap<String, String> map) {
    	return pscBoardMapper.getFileExtList(map);
    }
    
    /**
     * @Description : 공지사항 게시글 수정/삭제권한 체크
     * @Param  : String
     * @Return : List<?>
     * @Author : 권성광
     * @Create Date : 2018. 11. 29.
     */
    @Transactional(rollbackFor=Exception.class)
    public String checkNoticeAuth(String noticeId) {
        return pscBoardMapper.checkNoticeAuth(noticeId);
    }
}
