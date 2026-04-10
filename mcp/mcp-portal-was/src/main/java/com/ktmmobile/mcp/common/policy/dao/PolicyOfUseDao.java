package com.ktmmobile.mcp.common.policy.dao;

import com.ktmmobile.mcp.board.dto.FileDownloadDto;


public interface PolicyOfUseDao {

	public FileDownloadDto getPolicyFile(int attrSeq);

}
