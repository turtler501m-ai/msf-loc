package com.ktmmobile.mcp.common.policy.service;

import com.ktmmobile.mcp.board.dto.FileDownloadDto;

public interface PolicyOfUseSvc {

	public FileDownloadDto getPolicyFile(int attrSeq);
}
