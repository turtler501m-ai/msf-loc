package com.ktmmobile.mcp.cs.dao;

import java.util.List;

import com.ktmmobile.mcp.cs.dto.TransferDto;

public interface TransferDao {
	
	public int countTransferList(TransferDto transferDto);

	public List<TransferDto> transferList(TransferDto transferDto, int skipResult, int maxResult);
	
	public TransferDto transferDetail(int trnsApyNo);
	
	public int trnsStausEdit();
	
	public int transferDetailSave(TransferDto transferDto);
	
	public int transferDetailDel(TransferDto transferDto);
}
