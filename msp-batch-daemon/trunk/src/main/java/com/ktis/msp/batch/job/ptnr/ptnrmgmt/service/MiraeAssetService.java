package com.ktis.msp.batch.job.ptnr.ptnrmgmt.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.ptnr.PointConstants;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.mapper.MiraeAssetMapper;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.mapper.PartnerPointSettleMapper;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.vo.MiraeAssetVO;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.vo.PointFileVO;
import com.ktis.msp.batch.util.StringUtil;

import egovframework.rte.fdl.property.EgovPropertyService;

/**
 */
@Service
public class MiraeAssetService extends BaseService {
   
   @Autowired
   private PartnerPointSettleMapper ptnrMapper;
   
   @Autowired
   private MiraeAssetMapper miraeAssetMapper;
   
   @Autowired
   private EgovPropertyService propertiesService;
   
   @Transactional(rollbackFor=Exception.class)
   public PointFileVO regPointFileInfo(String ifNo, String tradeCd, String gubun) throws MvnoServiceException {
      
      LOGGER.debug("제휴사 파일 정보 생성 시작");
      
      String lDownBaseDir = propertiesService.getString("msp.sftp.local.base.dir");
      String lUpBaseDir = propertiesService.getString("msp.sftp.local.base.dir");
      String hDownBaseDir = "";
      String hUpBaseDir = "";
      
      List<?> clist = null;
      
      clist = ptnrMapper.getPartnerSubList(ifNo);
      
      if(clist.isEmpty()) {
         throw new MvnoServiceException("EPTNR1012");
      }
      
      ObjectMapper op = new ObjectMapper();
      
      StringBuffer buf3 = new StringBuffer();
      
      for (Object fromValue : clist) {
         PointFileVO vo = op.convertValue(fromValue, PointFileVO.class);
         
         hDownBaseDir = vo.getDownDir();
         hUpBaseDir = vo.getUpDir();
         
         // 첫번째 문자열에 / 가 있으면 빼준다.
         if(hDownBaseDir.charAt(0) == '/') {
            hDownBaseDir = hDownBaseDir.substring(1);
         }
         
         // 첫번째 문자열에 / 가 있으면 빼준다.
         if(hUpBaseDir.charAt(0) == '/') {
            hUpBaseDir = hUpBaseDir.substring(1);
         }
         
         // 마지막 문자열에 / 가 없으면 추가해준다.
         if(hDownBaseDir.charAt(hDownBaseDir.length()-1) != '/') {
            buf3.append(hDownBaseDir);
            buf3.append("/");
            hDownBaseDir = buf3.toString();
         }
         
         buf3.setLength(0);
         if(hUpBaseDir.charAt(hUpBaseDir.length()-1) != '/') {
            buf3.append(hUpBaseDir);
            buf3.append("/");
            hUpBaseDir = buf3.toString();
         }
      }
      
      // 로컬 다운로드 경로
      String lDownDir = lDownBaseDir + PointConstants.STR_MIRAE + "/" + hDownBaseDir;
      // 서버 다운로드 경로
      String hDownDir = "";
      StringBuffer buf = new StringBuffer();
      buf.append(PointConstants.STR_MIRAE);
      buf.append("/");
      buf.append(hDownBaseDir);
      hDownDir = buf.toString();
      
      // 로컬 업로드 경로
      String lUpDir = lUpBaseDir + PointConstants.STR_MIRAE + "/" + hUpBaseDir;
      // 서버 업로드 경로 구하기
      StringBuffer buf1 = new StringBuffer();
      buf1.append(PointConstants.STR_MIRAE);
      buf1.append("/");
      buf1.append(hUpBaseDir);
      String hUpDir = buf1.toString();
      
      String fileName = "";
      
      SimpleDateFormat formatter = new SimpleDateFormat ( "yyyyMM", Locale.KOREA );
      Date currentTime = new Date ( );
      String dDate = formatter.format ( currentTime );
      
      fileName = dDate + tradeCd + gubun;
      
      PointFileVO fileVo = new PointFileVO();
      
      fileVo.setPartnerId(PointConstants.STR_MIRAE);
      fileVo.setLocalUpDir(lUpDir);
      fileVo.setHostUpDir(hUpDir);
      fileVo.setLocalDownDir(lDownDir);
      fileVo.setHostDownDir(hDownDir);
      fileVo.setFileNm(fileName);
      
      if(PointConstants.RCV_BIZ_CD.equals(gubun)) {
         fileVo.setSendFlag("S");
      }
      
      if(PointConstants.SEND_BIZ_CD.equals(gubun)) {
         fileVo.setSendResult("S");
      }
      
      ptnrMapper.deletePointFile(fileVo);
      ptnrMapper.insertPointFile(fileVo);
      
      LOGGER.debug("제휴사 파일 정보 생성 끝");
      
      return fileVo;
   }
   
   public void readFile(String fileName) throws MvnoServiceException {
      
      LOGGER.debug("제휴사 파일 정보 읽기 시작");
      
      PointFileVO pointFileVO = new PointFileVO();
      
      pointFileVO.setPartnerId(PointConstants.STR_MIRAE);
      pointFileVO.setFileNm(fileName);
      
      List<?> list = ptnrMapper.getPointFileReadList(pointFileVO);
      
      ObjectMapper op = new ObjectMapper();
      LOGGER.debug("list.size() = {}", list.size());
      
      String[] errParam = new String[1];
      errParam[0] = pointFileVO.getPartnerId();
      
      // 읽을 파일이 없음
      if(list.isEmpty()) {
         throw new MvnoServiceException("EPTNR1008", errParam);
      }
      
      List<PointFileVO> fileList = new ArrayList<PointFileVO>();
      
      for (Object fromValue : list) {
         PointFileVO vo = op.convertValue(fromValue, PointFileVO.class);
         
         LOGGER.debug("읽어야할 제휴사아이디 = [{}], filename = [{}]", vo.getPartnerId(), vo.getFileNm());
         
         File f = new File(vo.getLocalDownDir() + vo.getFileNm());
         
         // 파일이 존재하는지 확인
         if(f.isFile()) {
            fileList.add(vo);
         }
      }
      
      SimpleDateFormat formatter = new SimpleDateFormat ( "yyyyMM", Locale.KOREA );
      Date currentTime = new Date ( );
      String dDate = formatter.format ( currentTime );
      
      MiraeAssetVO delVo = new MiraeAssetVO();
      delVo.setIfYm(dDate);
      delVo.setTradeCd(fileName.substring(6, 12));
      miraeAssetMapper.delMspMiraeAssetIf(delVo);
      
      String downloadFileName = "";
      
      // 파일 읽기
      try {
         
         for (PointFileVO vo : fileList) {
            downloadFileName = vo.getLocalDownDir() + vo.getFileNm();
            
            try {
               BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(downloadFileName), "EUC-KR"));
               
               String s;
               
               while ((s = in.readLine()) != null) {
                  // byte 단위로 읽기
                  byte[] b = s.getBytes("EUC-KR");
                  
                  String dataGuBun = new String(b, 6, 2);
                  
                  if(PointConstants.BODY_TYPE.equals(dataGuBun)) {
                     String tradeCd    = new String(b, 0, 6);
                     //String seq        = new String(b, 8, 7);
                     String maKey      = new String(b, 15, 10);
                     String custNm     = new String(b, 25, 50, "EUC-KR");
                     String ci         = new String(b, 75, 100);
                     String insrFee    = new String(b, 175, 12);
                     String insrJoinDt = new String(b, 187, 8);
                     String insrNm     = new String(b, 195, 100, "EUC-KR");
                     String insrCd     = new String(b, 295, 10);
                     
                     MiraeAssetVO vo1 = new MiraeAssetVO();
                     vo1.setIfYm(dDate.trim());
                     vo1.setTradeCd(tradeCd.trim());
                     vo1.setMaKey(maKey.trim());
                     vo1.setCustNm(custNm.trim());
                     vo1.setCi(ci.trim());
                     vo1.setInsrFee(insrFee.trim());
                     vo1.setInsrJoinDt(insrJoinDt.trim());
                     vo1.setInsrNm(insrNm.trim());
                     vo1.setInsrCd(insrCd.trim());
                     
                     if(PointConstants.TRADE_CODE03.equals(tradeCd)) {
                        String ktmCntrNo = new String(b, 305, 20);
                        String ktmAdFee  = new String(b, 325, 7);
                        
                        vo1.setKtmCntrNo(ktmCntrNo.trim());
                        vo1.setKtmAdFee(ktmAdFee.trim());
                     } else if(PointConstants.TRADE_CODE04.equals(tradeCd)) {
                        String ktmCntrNo  = new String(b, 305, 20);
                        String insrTmntDt = new String(b, 325, 8);
                        
                        vo1.setKtmCntrNo(ktmCntrNo.trim());
                        vo1.setInsrTmntDt(insrTmntDt.trim());
                     }
                     
                     miraeAssetMapper.regMspMiraeAssetIf(vo1);
                  }
                  
               }
               
               vo.setSendResult("S");
               ptnrMapper.updatePointFileDown(vo);
               in.close();
               
            } catch (Exception e1) {
               LOGGER.error("파일읽기에 실패했습니다. 파일명 = [{}]", downloadFileName);
               continue;
            }
         }
         
      } catch (Exception e) {
         throw new MvnoServiceException("EPTNR1011", errParam, e);
      }
      
      LOGGER.debug("제휴사 파일 정보 읽기 끝");
      
   }
   
   public void saveFile(PointFileVO pointFileVO) throws MvnoServiceException {
      
      LOGGER.debug("제휴사 파일 정보 생성 시작");
      
      SimpleDateFormat formatter = new SimpleDateFormat ( "yyyyMM", Locale.KOREA );
      Date currentTime = new Date ( );
      String dDate = formatter.format ( currentTime );
      
      MiraeAssetVO searchVO = new MiraeAssetVO();
      
      searchVO.setIfYm(dDate);
      searchVO.setTradeCd(PointConstants.TRADE_CODE01);
      
      List<MiraeAssetVO> aryMiraeAssetVO = miraeAssetMapper.getMspMiraeAssetIfList(searchVO);
      
      searchVO.setTradeCd(PointConstants.TRADE_CODE02);
      miraeAssetMapper.delMspMiraeAssetIf(searchVO);
      
      String uploadFileName = "";
      
      try {
         File desti = new File(pointFileVO.getLocalUpDir());
         
         // 디렉토리가 없다면 생성
         if (!desti.exists()) {
            desti.mkdirs();
         }
         
         uploadFileName = pointFileVO.getLocalUpDir() + pointFileVO.getFileNm();
         
         BufferedWriter fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(uploadFileName), "EUC-KR"));
         
         String strHeader = "";
         
         //Header
         StringBuffer sbufHeader = new StringBuffer();
         
         sbufHeader.append(PointConstants.TRADE_CODE02);
         sbufHeader.append(PointConstants.HEADER_TYPE);
         sbufHeader.append("0000000");
         sbufHeader.append(PointConstants.SEND_BIZ_CD);
         sbufHeader.append(StringUtils.leftPad(String.valueOf(aryMiraeAssetVO.size()), 7, "0"));
         sbufHeader.append(StringUtil.rightPad(new String(""), 309, " ", "EUC-KR"));
         
         strHeader = sbufHeader.toString();
         
         fw.write(strHeader);
         fw.newLine();
         
         //Body
         String strBody = "";
         
         int nDataCnt = 0;
         
         for (MiraeAssetVO miraeAssetVO : aryMiraeAssetVO) {
            
            MiraeAssetVO ktmCntrInfoVO = miraeAssetMapper.getMspJuoCntrByInfo(miraeAssetVO);
            
            miraeAssetVO.setKtmCustYn(ktmCntrInfoVO.getKtmCustYn());
            miraeAssetVO.setKtmOpenDt(ktmCntrInfoVO.getKtmOpenDt());
            miraeAssetVO.setKtmCntrNo(ktmCntrInfoVO.getKtmCntrNo());
            
            nDataCnt++;
            
            String seq = String.valueOf(nDataCnt);
            String maKey = miraeAssetVO.getMaKey();
            String custNm = miraeAssetVO.getCustNm();
            String ci = miraeAssetVO.getCi();
            String insrFee = String.valueOf(miraeAssetVO.getInsrFee());
            String insrJoinDt = miraeAssetVO.getInsrJoinDt();
            String insrNm = miraeAssetVO.getInsrNm();
            String insrCd = miraeAssetVO.getInsrCd();
            String ktmCustYn = miraeAssetVO.getKtmCustYn();
            String ktmOpenDt = "";
            String ktmCntrNo = "";
            
            if("1".equals(ktmCustYn)) {
               ktmOpenDt = miraeAssetVO.getKtmOpenDt();
               ktmCntrNo = miraeAssetVO.getKtmCntrNo();
            }
            
            seq         = StringUtils.leftPad(seq, 7, "0");
            maKey       = StringUtil.rightPad(maKey, 10, " ", "EUC-KR");
            custNm      = StringUtil.rightPad(custNm, 50, " ", "EUC-KR");
            ci          = StringUtil.rightPad(ci, 100, " ", "EUC-KR");
            insrFee     = StringUtils.leftPad(insrFee, 12, "0");
            insrJoinDt  = StringUtils.leftPad(insrJoinDt, 8, "0");
            insrNm      = StringUtil.rightPad(insrNm, 100, " ", "EUC-KR");
            insrCd      = StringUtil.rightPad(insrCd, 10, " ", "EUC-KR");
            ktmCustYn   = StringUtils.leftPad(ktmCustYn, 1, "0");
            ktmOpenDt   = StringUtils.leftPad(ktmOpenDt, 8, "0");
            ktmCntrNo   = StringUtil.rightPad(ktmCntrNo, 20, " ", "EUC-KR");
            
            StringBuffer sbufBody = new StringBuffer();
            
            sbufBody.append(PointConstants.TRADE_CODE02);
            sbufBody.append(PointConstants.BODY_TYPE);
            sbufBody.append(seq);
            sbufBody.append(maKey);
            sbufBody.append(custNm);
            sbufBody.append(ci);
            sbufBody.append(insrFee);
            sbufBody.append(insrJoinDt);
            sbufBody.append(insrNm);
            sbufBody.append(insrCd);
            sbufBody.append(ktmCustYn);
            sbufBody.append(ktmOpenDt);
            sbufBody.append(ktmCntrNo);
            
            LOGGER.debug("sbufBody.toString() = [{}]", sbufBody.toString());
            LOGGER.debug("sbufBody.toString().length() = [{}]", sbufBody.toString().length());
            
            strBody = sbufBody.toString();
            
            fw.write(strBody);
            fw.newLine();
            
            miraeAssetVO.setTradeCd(PointConstants.TRADE_CODE02);
            miraeAssetMapper.regMspMiraeAssetIf(miraeAssetVO);
         }
         
         //Tail
         String strTail = "";
         
         StringBuffer sbufTail = new StringBuffer();
         
         sbufTail.append(PointConstants.TRADE_CODE02);
         sbufTail.append(PointConstants.TAIL_TYPE);
         sbufTail.append("9999999");
         sbufTail.append(PointConstants.SEND_BIZ_CD);
         sbufTail.append(StringUtils.leftPad(String.valueOf(aryMiraeAssetVO.size()), 7, "0"));
         sbufTail.append(StringUtil.rightPad(new String(""), 309, " ", "EUC-KR"));
         
         strTail = sbufTail.toString();
         
         fw.write(strTail);
         fw.newLine();
         
         fw.flush();
         
         fw.close(); 
         
      } catch (Exception e) {
         throw new MvnoServiceException("EPTNR1013", e);
      }
      
      LOGGER.debug("제휴사 파일 정보 생성 끝");
   }
   
}
