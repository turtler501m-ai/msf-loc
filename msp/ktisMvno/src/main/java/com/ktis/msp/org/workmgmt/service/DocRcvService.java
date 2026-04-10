package com.ktis.msp.org.workmgmt.service;

import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.cmn.smsmgmt.mapper.SmsMgmtMapper;
import com.ktis.msp.cmn.smsmgmt.vo.KtMsgQueueReqVO;
import com.ktis.msp.img.applicationForm.service.AppFormService;
import com.ktis.msp.img.applicationForm.vo.ScanRequestVO;
import com.ktis.msp.org.workmgmt.mapper.DocRcvMapper;
import com.ktis.msp.org.workmgmt.vo.*;
import com.ktis.msp.util.StringUtil;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

import static com.ktis.msp.org.workmgmt.constants.DocRcvConstants.*;
import static com.ktis.msp.org.workmgmt.util.DocRcvUtil.getAddDocNameList;
import static com.ktis.msp.org.workmgmt.util.DocRcvUtil.getReqDocName;
import static com.ktis.msp.util.StringUtil.*;

@Service
public class DocRcvService extends BaseService {

    public static final String SMS_TEMPLATE_SEND_URL_OTP = "394";
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;

    @Autowired
    private DocRcvMapper docRcvMapper;

    @Autowired
    private SmsMgmtMapper smsMgmtMapper;

    @Autowired
    private MaskingService maskingService;

    @Autowired
    private AppFormService appFormService;

    public String getMobileNoByResNo(DocRcvRequestVO docRcvRequest) {
        String mobileNo = "";

        if (VIEW_TYPE_REQUEST.equals(docRcvRequest.getViewType())) {
            mobileNo = docRcvMapper.getRequestMobileNoByResNo(docRcvRequest.getResNo());
        } else if (VIEW_TYPE_OPEN.equals(docRcvRequest.getViewType())) {
            mobileNo = docRcvMapper.getContractMobileNoByResNo(docRcvRequest.getResNo());
        } else {
            throw new IllegalArgumentException("viewType[" + docRcvRequest.getViewType() + "]");
        }

        return mobileNo;
    }

    public void requestDocRcv(DocRcvRequestVO docRcvRequest) throws MvnoServiceException {
        DocRcvVO docRcvVO = DocRcvVO.of(docRcvRequest);
        docRcvMapper.insertDocRcv(docRcvVO);

        this.insertDocRcvStatusInitial(docRcvVO);
        this.insertDocRcvItem(docRcvVO);

        this.generateUrlAndOtp(docRcvVO);
        this.sendUrlAndOtp(docRcvVO);
    }

    public void generateUrlAndOtp(DocRcvVO docRcvVO) throws MvnoServiceException {
        String rcvUrlId = UUID.randomUUID().toString();
        String url = propertiesService.getString("doc.rcv.url") + "?i=" + docRcvVO.getDocRcvId() + "&u=" + rcvUrlId;

        DocRcvUrlVO docRcvUrlVO = new DocRcvUrlVO();
        docRcvUrlVO.setDocRcvId(docRcvVO.getDocRcvId());
        docRcvUrlVO.setRcvUrlId(rcvUrlId);
        docRcvUrlVO.setUrl(url);
        docRcvUrlVO.setExpireDt(docRcvVO.getExpireDt());
        docRcvUrlVO.setUserId(docRcvVO.getUserId());

        docRcvMapper.insertDocRcvUrl(docRcvUrlVO);
        this.generateUrlOtp(docRcvUrlVO);
    }

    public void sendUrlAndOtp(DocRcvVO docRcvVO) throws MvnoServiceException {
        DocRcvDetailVO docRcvDetail = this.getDocRcvDetail(docRcvVO.getDocRcvId());
        docRcvDetail.setUserId(docRcvVO.getUserId());
        docRcvDetail.setMenuId(docRcvVO.getMenuId());
        this.sendUrl(docRcvDetail);
        this.sendOtp(docRcvDetail);
    }

    public DocRcvDetailVO getDocRcvDetail(String docRcvId) {
        return docRcvMapper.getDocRcvDetail(docRcvId);
    }

    public List<EgovMap> getDocRcvList(DocRcvRequestVO docRcvRequest) {
        List<EgovMap> list = docRcvMapper.getDocRcvList(docRcvRequest);
        maskingService.setMask(list, this.getMaskFields(), docRcvRequest);

        return list;
    }

    public List<EgovMap> getDocRcvListByExcel(DocRcvRequestVO docRcvRequest) {
        List<EgovMap> list = docRcvMapper.getDocRcvListByExcel(docRcvRequest);
        maskingService.setMask(list, this.getMaskFields(), docRcvRequest);

        return list;
    }

    public DocRcvUrlOtpVO resendNewOtp(String docRcvId) throws MvnoServiceException {
        DocRcvUrlVO docRcvUrlVO = docRcvMapper.getDocRcvUrlLastIssued(docRcvId);
        this.generateUrlOtp(docRcvUrlVO);

        DocRcvDetailVO docRcvDetail = this.getDocRcvDetail(docRcvId);
        this.sendOtp(docRcvDetail);

        return docRcvDetail.getDocRcvUrlOtpVO();
    }

    public void verifyDocRcvItems(DocRcvRequestVO docRcvRequest) throws MvnoServiceException {
        DocRcvVO docRcvVO = DocRcvVO.of(docRcvRequest);
        if (StringUtils.isBlank(docRcvVO.getDocRcvId())) {
            throw new MvnoServiceException("요청이 유효하지 않습니다.");
        }

        if (docRcvVO.getItemSeqList().isEmpty()) {
            throw new MvnoServiceException("서류 목록이 유효하지 않습니다.");
        }

        List<DocRcvItemVO> docRcvItemList = docRcvMapper.getDocRcvItemList(docRcvVO.getDocRcvId());
        for (DocRcvItemVO item : docRcvItemList) {
            if (!docRcvVO.getItemSeqList().contains(item.getItemSeq())) {
                continue;
            }
            if (!DOC_RCV_ITEM_STATUS_RECEIVE.equals(item.getStatus())) {
                throw new MvnoServiceException("접수된 서류만 검수(처리)완료 가능합니다.");
            }
        }

        docRcvMapper.verifyDocRcvItems(docRcvVO);

        List<DocRcvItemVO> newDocRcvItemList = docRcvMapper.getDocRcvItemList(docRcvVO.getDocRcvId());
        boolean isAllCompleted = true;
        for (DocRcvItemVO item : newDocRcvItemList) {
            if (!DOC_RCV_ITEM_STATUS_COMPLETED.equals(item.getStatus())) {
                isAllCompleted = false;
                break;
            }
        }
        if (isAllCompleted) {
            this.changeStatusVerify(docRcvVO);
        }
    }

    public void retryDocRcvItems(DocRcvRequestVO docRcvRequest) throws MvnoServiceException {
        DocRcvVO docRcvVO = DocRcvVO.of(docRcvRequest);
        if (StringUtils.isBlank(docRcvVO.getDocRcvId())) {
            throw new MvnoServiceException("요청이 유효하지 않습니다.");
        }

        if (docRcvVO.getItemSeqList().isEmpty()) {
            throw new MvnoServiceException("서류 목록이 유효하지 않습니다.");
        }

        List<DocRcvItemVO> docRcvItemList = docRcvMapper.getDocRcvItemList(docRcvVO.getDocRcvId());
        for (DocRcvItemVO item : docRcvItemList) {
            if (!docRcvVO.getItemSeqList().contains(item.getItemSeq())) {
                continue;
            }
            if (DOC_RCV_ITEM_STATUS_COMPLETED.equals(item.getStatus())) {
                throw new MvnoServiceException("검수완료된 서류는 재접수요청이 불가합니다.");
            }
        }

        this.syncItemsNewPending(docRcvVO);
        this.mergeFromDuplicatedPendingItems(docRcvVO);
        this.changeStatusRetry(docRcvVO);

        this.expireLastUrl(docRcvVO);
        this.generateUrlAndOtp(docRcvVO);
        this.sendUrlAndOtp(docRcvVO);

        List<DocRcvItemFileVO> itemFileList = docRcvMapper.getItemFileListByItemSeqs(docRcvVO);
        for (DocRcvItemFileVO itemFile : itemFileList) {
            ScanRequestVO scanRequestVO = new ScanRequestVO();
            scanRequestVO.setFileId(itemFile.getFileId());
            scanRequestVO.setPrcsSbst("서류 재접수요청으로 인한 삭제 처리");
            scanRequestVO.setUserId(docRcvVO.getUserId());
            appFormService.deleteScanFileByFileId(scanRequestVO);
        }
    }

    public void completeDocRcv(DocRcvRequestVO docRcvRequest) throws MvnoServiceException {
        DocRcvVO docRcvVO = DocRcvVO.of(docRcvRequest);
        if (StringUtils.isBlank(docRcvVO.getDocRcvId())) {
            throw new MvnoServiceException("요청이 유효하지 않습니다.");
        }

        DocRcvDetailVO docRcvDetail = this.getDocRcvDetail(docRcvVO.getDocRcvId());
        if (docRcvDetail == null) {
            throw new MvnoServiceException("대상이 유효하지 않습니다.");
        }
        if (StringUtil.isEmpty(docRcvDetail.getScanId())) {
            throw new MvnoServiceException("합본 전 서식지가 존재하지 않습니다.");
        }

        String originScanId = this.getOriginScanIdByResNo(docRcvDetail.getResNo());
        if (StringUtil.isEmpty(originScanId)) {
            throw new MvnoServiceException("개통/신청 서식지가 존재하지 않습니다.");
        }

        List<DocRcvItemFileVO> itemFileCompletedList = docRcvMapper.getItemFileCompletedList(docRcvVO.getDocRcvId());
        if (itemFileCompletedList.isEmpty()) {
            throw new MvnoServiceException("검수완료 서류가 존재하지 않습니다.");
        }

        this.changeStatusCompleted(docRcvVO);
        for (DocRcvItemFileVO itemFile : itemFileCompletedList) {
            itemFile.setUserId(docRcvVO.getUserId());
            docRcvMapper.updateItemFileMerged(itemFile);
        }

        for (DocRcvItemFileVO itemFile : itemFileCompletedList) {
            ScanRequestVO scanRequestVO = new ScanRequestVO();
            scanRequestVO.setScanId(docRcvVO.getScanId());
            scanRequestVO.setFileId(itemFile.getFileId());
            scanRequestVO.setOriginScanId(originScanId);
            scanRequestVO.setPrcsSbst("서류 접수 처리 완료 합본");
            scanRequestVO.setUserId(docRcvVO.getUserId());
            appFormService.insertScanFileToOrigin(scanRequestVO);
        }
    }

    public List<EgovMap> getDocRcvItemFileListByItemId(DocRcvRequestVO docRcvRequest) {
        return docRcvMapper.getDocRcvItemFileListByItemId(docRcvRequest);
    }

    private void syncItemsNewPending(DocRcvVO docRcvVO) {
        docRcvMapper.updateDocRcvItemsPendingToNotReceived(docRcvVO);
        docRcvMapper.updateDocRcvItemsPending(docRcvVO);
    }

    private void mergeFromDuplicatedPendingItems(DocRcvVO docRcvVO) {
        List<DocRcvItemVO> duplicatedPendingItemList = docRcvMapper.getDuplicatedPendingItemList(docRcvVO.getDocRcvId());
        for (DocRcvItemVO item : duplicatedPendingItemList) {
            if (item.getItemSeqFrom() == item.getItemSeqTo()) {
                continue;
            }
            item.setUserId(docRcvVO.getUserId());
            docRcvMapper.appendDocRcvItemFileList(item);
            docRcvMapper.deleteDocRcvItem(item.getItemSeqFrom());
        }
    }

    private void expireLastUrl(DocRcvVO docRcvVO) {
        DocRcvUrlVO docRcvUrlVO = docRcvMapper.getDocRcvUrlLastIssued(docRcvVO.getDocRcvId());

        DocRcvUrlVO urlUpdateVO = new DocRcvUrlVO();
        urlUpdateVO.setRcvUrlId(docRcvUrlVO.getRcvUrlId());
        urlUpdateVO.setUserId(docRcvVO.getUserId());
        docRcvMapper.updateDocRcvUrlExpire(urlUpdateVO);
    }

    private void changeStatusRetry(DocRcvVO docRcvVO) {
        DocRcvStatusVO docRcvStatusVO = new DocRcvStatusVO();
        docRcvStatusVO.setDocRcvId(docRcvVO.getDocRcvId());
        docRcvStatusVO.setUserId(docRcvVO.getUserId());
        docRcvMapper.insertDocRcvStatusRetry(docRcvStatusVO);
    }

    private void sendUrl(DocRcvDetailVO docRcvDetail) throws MvnoServiceException {
        DocRcvUrlVO docRcvUrlVO = docRcvDetail.getDocRcvUrlVO();
        if (docRcvUrlVO == null || !"A".equals(docRcvUrlVO.getStatus()) || StringUtils.isBlank(docRcvUrlVO.getUrl())) {
            throw new MvnoServiceException("URL이 유효하지 않습니다.", null);
        }
        String expireDt;
        try {
            SimpleDateFormat fromFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            Date expireDate = fromFormat.parse(docRcvUrlVO.getExpireDt());

            Calendar cal = Calendar.getInstance();
            cal.setTime(expireDate);
            cal.add(Calendar.DATE, -1);

            SimpleDateFormat toFormat = new SimpleDateFormat("yyyy-MM-dd");
            expireDt = toFormat.format(cal.getTime());
        } catch (ParseException e) {
            throw new MvnoServiceException("만료일자가 유효하지 않습니다.", null);
        }

        KtMsgQueueReqVO ktMsgQueueReqVO = smsMgmtMapper.getKtTemplateText(docRcvDetail.getSmsTemplateId());

        ktMsgQueueReqVO.setMessage(ktMsgQueueReqVO.getTemplateText()
                .replaceAll(Pattern.quote("#{url}"), docRcvUrlVO.getUrl())
                .replaceAll(Pattern.quote("#{workTmplNm}"), docRcvDetail.getWorkTmplNm())
                .replaceAll(Pattern.quote("#{reqDoc}"), getReqDocName(docRcvDetail.getItemList()))
                .replaceAll(Pattern.quote("#{addDoc}"), getAddDocNameList(docRcvDetail.getItemList()))
                .replaceAll(Pattern.quote("#{expireDt}"), expireDt));

        ktMsgQueueReqVO.setSubject(ktMsgQueueReqVO.getTemplateSubject());
        ktMsgQueueReqVO.setRcptData(docRcvDetail.getMobileNo());
        ktMsgQueueReqVO.setReserved01("MSP");
        ktMsgQueueReqVO.setReserved02(docRcvDetail.getMenuId());
        ktMsgQueueReqVO.setReserved03(docRcvDetail.getUserId());
        ktMsgQueueReqVO.setUserId(docRcvDetail.getUserId());

        smsMgmtMapper.insertKtMsgTmpQueue(ktMsgQueueReqVO);
        smsMgmtMapper.insertSmsSendMst(ktMsgQueueReqVO.toSmsSendVO());
    }

    private void sendOtp(DocRcvDetailVO docRcvDetail) throws MvnoServiceException {
        DocRcvUrlOtpVO docRcvUrlOtpVO = docRcvDetail.getDocRcvUrlOtpVO();
        if (docRcvUrlOtpVO == null || !"A".equals(docRcvUrlOtpVO.getStatus()) || StringUtils.isBlank(docRcvUrlOtpVO.getOtp())) {
            throw new MvnoServiceException("OTP가 유효하지 않습니다.", null);
        }

        KtMsgQueueReqVO ktMsgQueueReqVO = smsMgmtMapper.getKtTemplateText(SMS_TEMPLATE_SEND_URL_OTP);

        ktMsgQueueReqVO.setMessage(ktMsgQueueReqVO.getTemplateText()
                .replaceAll(Pattern.quote("#{otp}"), docRcvUrlOtpVO.getOtp()));

        ktMsgQueueReqVO.setSubject(ktMsgQueueReqVO.getTemplateSubject());
        ktMsgQueueReqVO.setRcptData(docRcvDetail.getMobileNo());
        ktMsgQueueReqVO.setReserved01("MSP");
        ktMsgQueueReqVO.setReserved02(docRcvDetail.getMenuId());
        ktMsgQueueReqVO.setReserved03(docRcvDetail.getUserId());
        ktMsgQueueReqVO.setUserId(docRcvDetail.getUserId());

        smsMgmtMapper.insertKtMsgTmpQueue(ktMsgQueueReqVO);
        smsMgmtMapper.insertSmsSendMst(ktMsgQueueReqVO.toSmsSendVO());
    }

    private void insertDocRcvStatusInitial(DocRcvVO docRcvVO) {
        DocRcvStatusVO docRcvStatusVO = new DocRcvStatusVO();
        docRcvStatusVO.setDocRcvId(docRcvVO.getDocRcvId());
        docRcvStatusVO.setUserId(docRcvVO.getUserId());
        docRcvMapper.insertDocRcvStatusInitial(docRcvStatusVO);
    }

    private void insertDocRcvItem(DocRcvVO docRcvVO) {
        DocRcvItemVO docRcvItemVO = new DocRcvItemVO();
        docRcvItemVO.setDocRcvId(docRcvVO.getDocRcvId());
        docRcvItemVO.setUserId(docRcvVO.getUserId());
        docRcvMapper.insertDocRcvItem(docRcvItemVO);
    }

    private void generateUrlOtp(DocRcvUrlVO docRcvUrlVO) throws MvnoServiceException {
        DocRcvUrlOtpVO otpVO = docRcvMapper.getDocRcvUrlOtp(docRcvUrlVO.getRcvUrlId());
        if (otpVO != null && otpVO.getSeq() > 2) {
            throw new MvnoServiceException("OTP는 최대 3회까지 생성 가능합니다.", null);
        }

        String otp;
        try {
            otp = generateNumbers(6);
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage());
            throw new MvnoServiceException("OTP 생성에 실패했습니다.", null);
        }

        DocRcvUrlOtpVO docRcvUrlOtpVO = new DocRcvUrlOtpVO();
        docRcvUrlOtpVO.setRcvUrlId(docRcvUrlVO.getRcvUrlId());
        docRcvUrlOtpVO.setOtp(otp);
        docRcvUrlOtpVO.setUserId(docRcvUrlVO.getUserId());
        docRcvMapper.insertDocRcvUrlOtp(docRcvUrlOtpVO);
    }

    private void changeStatusVerify(DocRcvVO docRcvVO) {
        DocRcvStatusVO docRcvStatusVO = new DocRcvStatusVO();
        docRcvStatusVO.setDocRcvId(docRcvVO.getDocRcvId());
        docRcvStatusVO.setProcStatus(DOC_RCV_PROC_STATUS_VERIFY);
        docRcvStatusVO.setUserId(docRcvVO.getUserId());
        docRcvMapper.insertDocRcvStatusProc(docRcvStatusVO);
    }

    private void changeStatusCompleted(DocRcvVO docRcvVO) {
        DocRcvStatusVO docRcvStatusVO = new DocRcvStatusVO();
        docRcvStatusVO.setDocRcvId(docRcvVO.getDocRcvId());
        docRcvStatusVO.setProcStatus(DOC_RCV_PROC_STATUS_COMPLETED);
        docRcvStatusVO.setUserId(docRcvVO.getUserId());
        docRcvMapper.insertDocRcvStatusProc(docRcvStatusVO);
    }

    private String getOriginScanIdByResNo(String resNo) {
        String mspScanId = docRcvMapper.getMspScanIdByResNo(resNo);
        if (!StringUtil.isEmpty(mspScanId) && mspScanId.length() == 32) {
            return mspScanId;
        }
        String mcpScanId = docRcvMapper.getMcpScanIdByResNo(resNo);
        if (!StringUtil.isEmpty(mcpScanId) && mcpScanId.length() == 32) {
            return mcpScanId;
        }
        return null;
    }

    private HashMap<String, String> getMaskFields() {
        HashMap<String, String> maskFields = new HashMap<String, String>();
        maskFields.put("cstmrName","CUST_NAME");
        maskFields.put("mobileNo","MOBILE_PHO");
        maskFields.put("cretNm","CUST_NAME");
        return maskFields;
    }
}
