package com.ktis.msp.cmn.board.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.board.mapper.BoardMapper;
import com.ktis.msp.cmn.board.vo.BoardVO;
import com.ktis.msp.cmn.masking.service.MaskingService;

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
public class BoardService extends BaseService {
	
	@Autowired
	private BoardMapper boardMapper;
	
	@Autowired
	private MaskingService maskingService;
	
	
	public BoardService() {
		setLogPrefix("[BoardService] ");
	}
	
	public List<?> noticeList(Map<String, Object> param){
		
		List<?> result = boardMapper.noticeList(param);
		
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("REGST_USR_NM",	"CUST_NAME");
		
		maskingService.setMask(result, maskFields, param);
		
		return result;
	}
	
	public String hqAuth(Map<String, Object> param){
		return boardMapper.hqAuth(param);
	}
	
	public String getNoticeId(){
		return boardMapper.getNoticeId();
	}
	
	@Transactional(rollbackFor=Exception.class)
	public String noticeInsert(Map<String, Object> param){
		
		String id = boardMapper.getNoticeId();
		
		param.put("NOTICE_ID", id);
		
		boardMapper.noticeInsert(param);
		
		return id;
	}
	
	@Transactional(rollbackFor=Exception.class)
	public void noticeUpdate(Map<String, Object> param){
		
		boardMapper.noticeUpdate(param);
	}
	
	@Transactional(rollbackFor=Exception.class)
	public int noticeDelete(Map<String, Object> param){
		
		return boardMapper.noticeDelete(param);
	}
	
	public List<?> getFile1(Map<String, Object> param){
		return boardMapper.getFile1(param);
	}
	
	public List<?> getNoticeFile(Map<String, Object> param){
		return boardMapper.getNoticeFile(param);
	}
	
	public List<?> getBoardFile(Map<String, Object> param){
		return boardMapper.getBoardFile(param);
	}
	
	public String getUserAuth(BoardVO vo){
		return boardMapper.getUserAuth(vo);
	}
	
	public List<?> getBoardList(BoardVO vo){
		if(vo.getBoardCd() == null || "".equals(vo.getBoardCd())){
			throw new MvnoRunException(-1, "게시판코드가 존재하지 않습니다");
		}
		
		List<?> result = boardMapper.getBoardList(vo);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("SESSION_USER_ID", vo.getSessionUserId());
		
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("regstNm",	"CUST_NAME");
		
		maskingService.setMask(result, maskFields, paramMap);
		
		return result;
	}
	
	@Transactional(rollbackFor=Exception.class)
	public String insertBoardMgmt(BoardVO vo){
		if(vo.getBoardCd() == null || "".equals(vo.getBoardCd())){
			throw new MvnoRunException(-1, "게시판코드가 존재하지 않습니다");
		}
		
		String id = boardMapper.getNoticeId();
		
		vo.setSrlNum(Integer.parseInt(id));
		
		boardMapper.insertBoardMgmt(vo);
		
		return id;
	}
	
	@Transactional(rollbackFor=Exception.class)
	public void updateBoardMgmt(BoardVO vo){
		if(vo.getBoardCd() == null || "".equals(vo.getBoardCd())){
			throw new MvnoRunException(-1, "게시판코드가 존재하지 않습니다");
		}
		if(vo.getSrlNum() <= 0){
			throw new MvnoRunException(-1, "게시물ID가 존재하지 않습니다");
		}
		
		boardMapper.updateBoardMgmt(vo);
	}
	
	@Transactional(rollbackFor=Exception.class)
	public void deleteBoardMgmt(BoardVO vo){
		boardMapper.deleteBoardMgmt(vo);
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
    	return boardMapper.getFileExtList(map);
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
        return boardMapper.checkNoticeAuth(noticeId);
    }
    /**
     * @Description : 게시물 작성자 조직 조회
     * @Param  : String
     * @Return : List<?>
     * @Author : 권오승
     * @Create Date : 2022. 03. 03.
     */
	public String getRegOrg(BoardVO vo){
		return boardMapper.getRegOrg(vo);
	}
	public String getUpdateAuth(BoardVO vo){
		return boardMapper.getUpdateAuth(vo);
	}
	
}
