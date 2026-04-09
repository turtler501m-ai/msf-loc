//package com.ktmmobile.msf.common.service;
//
//import java.util.HashMap;
//import javax.servlet.http.HttpServletRequest;
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataAccessException;
//import org.springframework.stereotype.Service;
//import com.ktmmobile.msf.common.controller.NiceCertifyController;
//import com.ktmmobile.msf.common.dao.NiceLogDao;
//import com.ktmmobile.msf.common.dto.NiceLogDto;
//import com.ktmmobile.msf.common.dto.NiceResDto;
//import com.ktmmobile.msf.common.dto.NiceTryLogDto;
//import com.ktmmobile.msf.common.util.EncryptUtil;
//
//@Service
//public class NiceLogSvcImpl implements NiceLogSvc {
//
//    private static final Logger logger = LoggerFactory.getLogger(NiceCertifyController.class);
//
//    @Autowired
//    private NiceLogDao niceLogDao;
//
//    @Autowired
//    IpStatisticService ipstatisticService;
//
//    @Override
//    public void insert(HttpServletRequest request, NiceResDto niceDto , NiceLogDto nicelogDto) {
//        // TODO Auto-generated method stub
//
//
//        //접속 구분 P:피씨 , M:모바일
//
//        //파리미터로 받은 값이 있으면
//        if(null!=nicelogDto.getnReferer() && !nicelogDto.getnReferer().equals("")){
//            if(nicelogDto.getnReferer().indexOf("/m/")==-1){
//                nicelogDto.setnCertify("P");
//            }else{
//                nicelogDto.setnCertify("M");
//            }
//        //referer 존재할때
//        }else if(null!=request.getHeader("referer") && !request.getHeader("referer").equals("")){
//
//            if(request.getHeader("referer").indexOf("/m/")==-1){
//                nicelogDto.setnCertify("P");
//            }else{
//                nicelogDto.setnCertify("M");
//            }
//        }else{
//            nicelogDto.setnCertify("Q");
//        }
//
//        if (nicelogDto.getnReferer() != null) {
//            //호출 페이지
//            String [] referer = nicelogDto.getnReferer().split(request.getServerName());
//            nicelogDto.setnReferer(referer[referer.length-1]);
//        }
//
//        //인증성공시
//        if(request.getHeader("X-Forwarded-For") == null) {
//            nicelogDto.setnIp(request.getRemoteAddr());
//        } else {
//            String ip = request.getHeader("X-Forwarded-For");
//            if(ip !=null && !ip.equals("") && ip.indexOf(",")>-1){
//                ip =  ip.split("\\,")[0].trim();
//            }
//            nicelogDto.setnIp(ip);//아이피
//        }
//
//        if(null!=nicelogDto.getnAuthType() && !nicelogDto.getnAuthType().equals("")){
//            nicelogDto.setnAuthType(nicelogDto.getnAuthType());//인증수단
//        }else{
//            nicelogDto.setnAuthType(niceDto.getAuthType());//인증수단
//        }
//
//        nicelogDto.setnName(niceDto.getName());//이름
//        nicelogDto.setnGender(niceDto.getGender());//성별
//
//        if(null!=niceDto.getBirthDate() && !niceDto.getBirthDate().trim().equals("")){
//            nicelogDto.setnBirthDate(EncryptUtil.ace256Enc(niceDto.getBirthDate()));//생년월일
//        }
//
//        if(null!=niceDto.getBankCode() && !niceDto.getBankCode().trim().equals("")){
//            nicelogDto.setnBankCode(niceDto.getBankCode());//은행정보
//        }
//
//        if(null!=niceDto.getAccountNo() && !niceDto.getAccountNo().trim().equals("")){
//            nicelogDto.setnAccountNo(EncryptUtil.ace256Enc(niceDto.getAccountNo()));//계좌정보
//        } else {
//            if(!StringUtils.isBlank(niceDto.getDupInfo())){
//                nicelogDto.setnAccountNo(niceDto.getDupInfo());
//            }
//        }
//        if(null!=nicelogDto.getnResult()){
//            nicelogDto.setnResult(nicelogDto.getnResult());//결과
//        }else{
//            nicelogDto.setnResult("O");
//        }
//        niceLogDao.insert(nicelogDto);
//    }
//
//    @Override
//    public void insert(HttpServletRequest request, HashMap map) {
//        // TODO Auto-generated method stub
//        //인증실패시
//        NiceLogDto nicelogDto =new NiceLogDto();
//        if(request.getHeader("X-Forwarded-For") == null) {
//            nicelogDto.setnIp(request.getRemoteAddr());
//        } else {
//            String ip = request.getHeader("X-Forwarded-For");
//            if(ip !=null && !ip.equals("") && ip.indexOf(",")>-1){
//                ip =  ip.split("\\,")[0].trim();
//            }
//            nicelogDto.setnIp(ip);//아이피
//        }
//        if(null!=map.get("nCertify")){
//            nicelogDto.setnCertify(map.get("nCertify").toString());
//        }
//        nicelogDto.setnAuthType((String)map.get("AUTH_TYPE"));//인증수단
//        nicelogDto.setnResult("X");
//        nicelogDto.setnErrorCode((String)map.get("ERR_CODE"));
//        niceLogDao.insert(nicelogDto);
//    }
//
//    @Override
//    public void insert(HttpServletRequest request, HashMap map , String sReserved1) {
//        // TODO Auto-generated method stub
//        NiceLogDto nicelogDto =new NiceLogDto();
//        //웹모바일구분 P:피씨 , M:모바일
//        if(sReserved1.indexOf("/m/")==-1){
//            nicelogDto.setnCertify("P");
//        }else{
//            nicelogDto.setnCertify("M");
//        }
//
//        //호출 페이지
//        String [] referer = sReserved1.split(request.getServerName());
//        nicelogDto.setnReferer(referer[referer.length-1]);
//
//        //인증실패시
//        if(request.getHeader("X-Forwarded-For") == null) {
//            nicelogDto.setnIp(request.getRemoteAddr());
//        } else {
//            String ip = request.getHeader("X-Forwarded-For");
//            if(ip !=null && !ip.equals("") && ip.indexOf(",")>-1){
//                ip =  ip.split("\\,")[0].trim();
//            }
//            nicelogDto.setnIp(ip);//아이피
//        }
//        if(null!=map.get("nCertify")){
//            nicelogDto.setnCertify(map.get("nCertify").toString());
//        }
//        nicelogDto.setnAuthType((String)map.get("AUTH_TYPE"));//인증수단
//        nicelogDto.setnResult("X");
//        nicelogDto.setnErrorCode((String)map.get("ERR_CODE"));
//        niceLogDao.insert(nicelogDto);
//    }
//
//    @Override
//    public long insertMcpNiceHist(NiceLogDto niceLogDto) {
//        niceLogDto.setRip(ipstatisticService.getClientIp());
//        niceLogDto.setnReferer(ipstatisticService.getReferer());
//        if( null!=niceLogDto.getBirthDate() && !niceLogDto.getBirthDate().trim().equals("") ){
//            niceLogDto.setBirthDate(EncryptUtil.ace256Enc(niceLogDto.getBirthDate()));//생년월일
//        }
//
//        return niceLogDao.insertMcpNiceHist(niceLogDto);
//    }
//
//    @Override
//    public long saveMcpNiceHist(NiceLogDto niceLogDto) {
//        //동일 이름, 생년 원일 종료 처리
//        NiceLogDto niceLogTmp = new NiceLogDto();
//        niceLogTmp.setNiceHistSeq(niceLogDto.getNiceHistSeq());
//        niceLogTmp.setName(niceLogDto.getName());
//        niceLogTmp.setBirthDate(niceLogDto.getBirthDate());
//        niceLogTmp.setParamR3(niceLogDto.getParamR3());
//        niceLogTmp.setEndYn("Y");
//        this.updateMcpNiceHist(niceLogTmp);
//
//        return this.insertMcpNiceHist(niceLogDto);
//    }
//
//    @Override
//    public boolean updateMcpNiceHist(NiceLogDto niceLogDto) {
//        if( null!=niceLogDto.getBirthDate() && !niceLogDto.getBirthDate().trim().equals("") ){
//            niceLogDto.setBirthDate(EncryptUtil.ace256Enc(niceLogDto.getBirthDate()));//생년월일
//        }
//        if( null!=niceLogDto.getToBirthDate() && !niceLogDto.getToBirthDate().trim().equals("") ){
//            niceLogDto.setToBirthDate(EncryptUtil.ace256Enc(niceLogDto.getToBirthDate()));//생년월일
//        }
//        return niceLogDao.updateMcpNiceHist(niceLogDto);
//    }
//
//    @Override
//    public NiceLogDto getMcpNiceHist(NiceLogDto niceLogDto) {
//        if( null!=niceLogDto.getBirthDate() && !niceLogDto.getBirthDate().trim().equals("") ){
//            niceLogDto.setBirthDate(EncryptUtil.ace256Enc(niceLogDto.getBirthDate()));//생년월일
//        }
//        return niceLogDao.getMcpNiceHist(niceLogDto);
//    }
//
//    /* nice 본인인증 로그 조회 with seq */
//    @Override
//    public NiceLogDto getMcpNiceHistWithSeq(long niceHistSeq) {
//        return niceLogDao.getMcpNiceHistWithSeq(niceHistSeq);
//    }
//
//    @Override
//    public NiceLogDto showMcpNiceHist(NiceLogDto niceLogDto) {
//        if( null!=niceLogDto.getBirthDate() && !niceLogDto.getBirthDate().trim().equals("") ){
//            niceLogDto.setBirthDate(EncryptUtil.ace256Enc(niceLogDto.getBirthDate()));//생년월일
//        }
//
//        NiceLogDto rtnLogDto = niceLogDao.getMcpNiceHist(niceLogDto);
//        return rtnLogDto ;
//    }
//
//    @Override
//    public NiceLogDto getMcpNiceHistWithReqSeq(NiceLogDto niceLogDto) {
//        NiceLogDto rtnLogDto = niceLogDao.getMcpNiceHistWithReqSeq(niceLogDto);
//        
//        if (rtnLogDto == null) {
//            /*
//            * 오늘 날짜에 없으면 
//            *  이전 날짜로 다시 검색
//            *  23시 59분 58초 이후에 검색 못 할 수도 있음
//             */
//            rtnLogDto = niceLogDao.getMcpNiceHistWithReqSeq2(niceLogDto);
//        }
//        
//        return rtnLogDto ;
//    }
//    
//
//    @Override
//	public long insertSelfSmsAuth(NiceLogDto niceLogDto) {
//    	int cnt = 0;
//    	try {
//        	cnt = niceLogDao.dupChkSelfSmsAuth(niceLogDto);
//        	if (cnt == 0) { // 중복 신청건 체크
//                niceLogDto.setRip(ipstatisticService.getClientIp());
//                niceLogDto.setnReferer(ipstatisticService.getReferer());
//                if( null != niceLogDto.getBirthDate() && !"".equals(niceLogDto.getBirthDate().trim()) ){
//                    niceLogDto.setBirthDate(EncryptUtil.ace256Enc(niceLogDto.getBirthDate()));//생년월일
//                }
//
//                return niceLogDao.insertSelfSmsAuth(niceLogDto);
//        	} else {
//                return 0l;
//        	}
//    	} catch(DataAccessException e) {
//            logger.error("## insertSelfSmsAuth Exception >> {} ", e.getMessage());
//            return 0l;
//        } catch (Exception e) {
//    		logger.error("## insertSelfSmsAuth Exception >> {} ", e.getMessage());
//            return 0l;
//    	}
//	}
//
//
//    @Override
//    public long insertMcpNiceTryHist(NiceTryLogDto niceTryLogDto) {
//
//        niceTryLogDto.setRip(ipstatisticService.getClientIp());      // IP 세팅
//        niceTryLogDto.setnReferer(ipstatisticService.getReferer());  // REFERENCE 세팅
//
//        return niceLogDao.insertMcpNiceTryHist(niceTryLogDto);
//    }
//
//    @Override
//    public NiceTryLogDto getMcpNiceTryHist(NiceTryLogDto niceTryLogDto) {
//        return niceLogDao.getMcpNiceTryHist(niceTryLogDto);
//    }
//
//    @Override
//    public boolean updateMcpNiceTryHist(NiceTryLogDto niceTryLogDto) {
//
//        niceTryLogDto.setnReferer(ipstatisticService.getReferer());
//        return niceLogDao.updateMcpNiceTryHist(niceTryLogDto);
//    }
//
//    @Override
//    public NiceLogDto getMcpNiceHistWithTime(NiceLogDto niceLogDto) {
//        return niceLogDao.getMcpNiceHistWithTime(niceLogDto);
//    }
//
//
//
//}
//
