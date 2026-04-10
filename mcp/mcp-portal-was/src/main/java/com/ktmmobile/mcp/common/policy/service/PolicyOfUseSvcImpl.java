package com.ktmmobile.mcp.common.policy.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ktmmobile.mcp.board.dto.FileDownloadDto;
import com.ktmmobile.mcp.common.policy.dao.PolicyOfUseDao;
import com.ktmmobile.mcp.common.util.FileUtil;

@Service
public class PolicyOfUseSvcImpl implements PolicyOfUseSvc {

	/** 파일업로드 경로*/
	@Value("${common.fileupload.base}")
	private String fileuploadBase;

	/** 파일업로드 접근가능한 웹경로 */
	@Value("${common.fileupload.web}")
	private String fileuploadWeb;

	@Autowired
	PolicyOfUseDao policyOfUseDao;

	@Autowired
    FileUtil fileUtil;

	private static Logger logger = LoggerFactory.getLogger(PolicyOfUseSvcImpl.class);

	@Override
	public FileDownloadDto getPolicyFile(int attrSeq) {
		// TODO Auto-generated method stub
		return policyOfUseDao.getPolicyFile(attrSeq);
	}
}
