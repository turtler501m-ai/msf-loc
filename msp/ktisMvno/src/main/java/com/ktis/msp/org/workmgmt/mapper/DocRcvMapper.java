package com.ktis.msp.org.workmgmt.mapper;

import com.ktis.msp.org.workmgmt.vo.*;
import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

import java.util.List;

@Mapper("DocRcvMapper")
public interface DocRcvMapper {
    String getRequestMobileNoByResNo(String resNo);

    String getContractMobileNoByResNo(String resNo);

    int insertDocRcv(DocRcvVO docRcvVO);

    int insertDocRcvStatusInitial(DocRcvStatusVO docRcvStatusVO);

    int insertDocRcvItem(DocRcvItemVO docRcvItemVO);

    int insertDocRcvUrl(DocRcvUrlVO docRcvUrlVO);

    int insertDocRcvUrlOtp(DocRcvUrlOtpVO docRcvUrlOtpVO);

    DocRcvDetailVO getDocRcvDetail(String docRcvId);

    List<EgovMap> getDocRcvList(DocRcvRequestVO docRcvRequest);

    List<EgovMap> getDocRcvListByExcel(DocRcvRequestVO docRcvRequest);

    List<DocRcvItemVO> getDocRcvItemList(String docRcvId);

    int verifyDocRcvItems(DocRcvVO docRcvVO);

    int updateDocRcvItemsPendingToNotReceived(DocRcvVO docRcvVO);

    int updateDocRcvItemsPending(DocRcvVO docRcvVO);

    int updateDocRcvUrlExpire(DocRcvUrlVO docRcvUrlVO);

    DocRcvUrlVO getDocRcvUrlLastIssued(String docRcvId);

    int insertDocRcvStatusRetry(DocRcvStatusVO docRcvStatusVO);

    DocRcvUrlOtpVO getDocRcvUrlOtp(String rcvUrlId);

    int insertDocRcvStatusProc(DocRcvStatusVO docRcvStatusVO);

    List<DocRcvItemFileVO> getItemFileListByItemSeqs(DocRcvVO docRcvVO);

    String getMspScanIdByResNo(String resNo);

    String getMcpScanIdByResNo(String resNo);

    List<DocRcvItemFileVO> getItemFileCompletedList(String docRcvId);

    int updateItemFileMerged(DocRcvItemFileVO itemFile);

    List<DocRcvItemVO> getDuplicatedPendingItemList(String docRcvId);

    int appendDocRcvItemFileList(DocRcvItemVO item);

    int deleteDocRcvItem(long itemSeq);

    List<EgovMap> getDocRcvItemFileListByItemId(DocRcvRequestVO docRcvRequest);
}