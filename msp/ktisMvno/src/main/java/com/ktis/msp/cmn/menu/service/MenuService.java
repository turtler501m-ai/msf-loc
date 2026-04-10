package com.ktis.msp.cmn.menu.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktis.msp.cmn.menu.mapper.MenuMapper;


/**
 * <PRE>
 * 1. ClassName	: 
 * 2. FileName	: MenuService.java
 * 3. Package	: com.ktis.msp.cmn.menu.service
 * 4. Commnet	: 
 * 5. 작성자	: Administrator
 * 6. 작성일	: 2014. 9. 25. 오후 4:00:12
 * </PRE>
 */
@Service
public class MenuService {
	
		@Autowired
		private MenuMapper menuMapper;
		
		/**
		 * <PRE>
		 * 1. MethodName: menuPrgmMstList
		 * 2. ClassName	: MenuService
		 * 3. Commnet	: 
		 * 4. 작성자	: Administrator
		 * 5. 작성일	: 2014. 9. 25. 오후 4:00:14
		 * </PRE>
		 * 		@return List<?>
		 * 		@param p_reqParamMap
		 * 		@return
		 */
		public List<?> menuPrgmMstList(Map<String, Object> pReqParamMap){
			List<?>  resultList = menuMapper.menuPrgmMstList(pReqParamMap);
			return resultList;
		}

}
