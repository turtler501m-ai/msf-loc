package com.ktmmobile.mcp.document.receive.mapper;

import com.ktmmobile.mcp.document.receive.dto.DocRcvDetailDto;
import com.ktmmobile.mcp.document.receive.dto.DocRcvItemDto;
import com.ktmmobile.mcp.document.receive.dto.DocRcvRequestDto;
import com.ktmmobile.mcp.document.receive.dto.ForScanResponseDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DocumentReceiveMapper {
    DocRcvDetailDto getDocRcvDetail(String docRcvId);

    int insertDocRcvStatusCompleteUpload(DocRcvRequestDto docRcvRequest);

    int updateItemListCompleteUpload(DocRcvRequestDto docRcvRequest);

    int updateItemListPendingToNotReceive(DocRcvRequestDto docRcvRequest);

    ForScanResponseDto getForScan(String docRcvId);

    int updateDocRcvScanId(DocRcvRequestDto docRcvRequest);

    int insertDocRcvStatusMaskingSuccess(DocRcvRequestDto docRcvRequest);

    int insertDocRcvStatusMaskingFail(DocRcvRequestDto docRcvRequest);

    int countDocRcvItemPending(long itemSeq);

    int updateItemUploadComplete(DocRcvItemDto item);

    int insertItemUploadComplete(DocRcvItemDto item);

    int updateItemUploadFail(DocRcvItemDto item);

    int insertItemUploadFail(DocRcvItemDto item);

    int insertDocRcvItemFileList(DocRcvItemDto item);
}
