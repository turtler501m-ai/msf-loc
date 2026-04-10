/**
 * 
 */
package com.ktis.msp.cmn.help.mapper;

import java.util.List;

import com.ktis.msp.cmn.help.vo.HelpMgmtVO;
import com.ktis.msp.org.common.vo.FileInfoVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @Class Name : helpMgmtMapper
 * @Description : 도움말관리
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2017. 1. 11. 강무성 최초생성
 * @
 * @author : 강무성
 * @Create Date : 2017. 1. 11.
 */
@Mapper("helpMgmtMapper")
public interface HelpMgmtMapper {
	
	int insertFile(FileInfoVO vo);
	
	List<HelpMgmtVO> getHelpMgmtList(HelpMgmtVO vo);
	
	String getHelpFileNm(String fileId);
}
