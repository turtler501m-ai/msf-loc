package com.ktis.msp.cmn.usrobjhst.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.usrobjhst.mapper.UsrObjHstMapper;
import com.ktis.msp.cmn.usrobjhst.vo.UsrObjHstVO;

@Service
public class UsrObjHstService extends BaseService {

	@Autowired
	private UsrObjHstMapper usrObjHstMapper;
	
	public List<UsrObjHstVO> getObjHstList(UsrObjHstVO searchVO, Map<String, Object> paramMap){
		
		List<UsrObjHstVO> result = usrObjHstMapper.getObjHstList(searchVO);
		
		return result;
	}

	
	public List<UsrObjHstVO> getObjSource(UsrObjHstVO searchVO, Map<String, Object> paramMap){
		
		List<UsrObjHstVO> result = usrObjHstMapper.getObjSource(searchVO);

		UsrObjHstVO vo = new UsrObjHstVO();
		
		String objName = (String) result.get(0).getObjectName();
		String fullSource = "";
		
		//v2018.11 PMD 적용 소스 수정
		StringBuilder strBld = new StringBuilder(fullSource);
		for(int i=0; i<result.size(); i++){
			//fullSource = fullSource + result.get(i).getSourceText();
		   strBld.append(result.get(i).getSourceText());
		}
		fullSource = strBld.toString();
						
		vo.setObjectName(objName);
		vo.setSourceText(fullSource);
		
		result.clear();
		
		result.add(0, vo);
				
		return result;
	}
}
