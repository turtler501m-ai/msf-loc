package com.ktis.mcpif.example.web;

import java.lang.reflect.InvocationTargetException;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ktis.mcpif.example.ExampleVo;
import com.ktis.mcpif.example.service.ExampleService;

/**
 * @Class Name : ExampleController
 * @Description : 샘플자료입니다.
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.13 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 13.
 */
@Controller
public class ExampleController {
	
	protected Log log = LogFactory.getLog(this.getClass());
	
	/**
	 * 샘플 서비스 선언
	 */
	@Resource(name = "exampleService")
	private ExampleService service; 

	@RequestMapping(value="/enc.do")
	public String encrypt(ModelMap map) {
	
		// 초기화 
		ExampleVo vo = new ExampleVo();
		vo.setUserName("Sangsun Park");
		vo.setPasswd("01025161123");
		
		try {
			// N-Step 암호화 
			ExampleVo aesVo = new ExampleVo();
			BeanUtils.copyProperties(aesVo, vo);
			aesVo = service.encryptNstep(aesVo);
			map.addAttribute("V1", aesVo);
			
			// N-Step 복호화
			ExampleVo aesVo2 = new ExampleVo();
			BeanUtils.copyProperties(aesVo2, aesVo);
			aesVo2 = service.decryptNstep(aesVo2);
			map.addAttribute("V2", aesVo2);
			
			// DBMS 암호화 
			ExampleVo dbmVo = new ExampleVo();
			BeanUtils.copyProperties(dbmVo, vo);
			dbmVo = service.encryptDBMS(dbmVo);
			map.addAttribute("V3", dbmVo);
			
			// DBMS 복호화
			ExampleVo dbmVo2 = new ExampleVo();
			BeanUtils.copyProperties(dbmVo2, dbmVo);
			dbmVo2 = service.decryptDBMS(dbmVo2);
			map.addAttribute("V4", dbmVo2);
			
			
			// SHA512 암호화 (단방향)
			ExampleVo shaVo = new ExampleVo();
			BeanUtils.copyProperties(shaVo, vo);
			shaVo = service.encryptSHA(shaVo);
			map.addAttribute("V5", shaVo);

		} catch (IllegalAccessException e) {
			log.error(e);
		} catch (InvocationTargetException e) {
			log.error(e);
		}
		
		return "example/enc";
	}
	

}
