//package com.ktmmobile.msf.form.servicechange.service;
//
//import static com.ktmmobile.msf.common.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;
//
//import java.awt.Color;
//import java.awt.Font;
//import java.awt.Graphics;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//import java.lang.reflect.InvocationTargetException;
//import java.text.ParseException;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//
//import javax.imageio.IIOImage;
//import javax.imageio.ImageIO;
//import javax.imageio.ImageWriteParam;
//import javax.imageio.ImageWriter;
//import javax.imageio.stream.FileImageOutputStream;
//
//import org.apache.commons.beanutils.BeanUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import com.ktds.crypto.exception.CryptoException;
//import com.ktmmobile.msf.common.constants.Constants;
//import com.ktmmobile.msf.common.dto.db.NmcpCdDtlDto;
//import com.ktmmobile.msf.common.exception.McpCommonException;
//import com.ktmmobile.msf.common.util.DateTimeUtil;
//import com.ktmmobile.msf.common.util.EncryptUtil;
//import com.ktmmobile.msf.common.util.NmcpServiceUtils;
//import com.ktmmobile.msf.common.util.StringUtil;
//import com.ktmmobile.msf.payinfo.dao.PayInfoDao;
//import com.ktmmobile.msf.payinfo.dto.EvidenceDto;
//import com.ktmmobile.msf.payinfo.dto.PayInfoDto;
//import com.ktmmobile.msf.payinfo.dto.PayInfoFormDto;
//
//import CNPECMJava.CNPEncryptModule.crypto.FileHandle;
//
//@Service
//public class MsfPayInfoServiceImpl implements MsfPayInfoService {
//    private final Logger logger = LoggerFactory.getLogger(MsfPayInfoServiceImpl.class);
//
//    //날짜포맷
//    /*
//     SimpleDateFormat smf= new SimpleDateFormat("yyyy.MM.dd");
//    Date orgNow =new Date();
//    private final String now= smf.format(orgNow);
//    private final String nowPulsFive= smf.format(new Date(orgNow.getYear(),orgNow.getMonth(),(orgNow.getDate()+5)));
//    private final String nowSplit[] =now.split("\\.");
//    private final String nowPFSplit[] =nowPulsFive.split("\\.");
//     */
//
//    @Value("${payInfo.org.imgPath}")
//    private String orgImgPath;//템플릿 이미지 풀경로 이미지명 포함
//
//    @Value("${payInfo.org.fontPath}")
//    private String orgFontPath;//템플릿 폰트 풀경로 이미지명 포함
//
//    @Value("${payInfo.end.imgPath}")
//    private String endImgPath;	//최종 이미지 경로 이미지명 불포함  **ORIGIN_DIR/E0019/**
//
//    @Autowired
//    PayInfoDao dao;
//
//    //변경신청서 이미지 경로
//    //private String end_file_path ;
//
//    ////변경신청서 암호화 이미지 경로
//    //private String end_file_enc_path;
//
//    //이미지 합본 프로시져 필수파라미터 계약번호
//    //private String contractNum;
//
//
//    //변경신청서 재생성
//    public String remakePayInfoImage(PayInfoDto dto){
//        String result="";
//
//        PayInfoDto payInfoDto = new PayInfoDto();
//
//        try {
//            BeanUtils.copyProperties(payInfoDto, dto);
//        } catch (IllegalAccessException e) {
//            throw new McpCommonException(COMMON_EXCEPTION);
//        } catch (InvocationTargetException e) {
//            throw new McpCommonException(COMMON_EXCEPTION);
//        }
//
//
//        //등록 데이터  가져오기
//        payInfoDto=dao.selectPayInfo(payInfoDto);
//        //이미지가 생성이 안된것만 처리한다
//        if (payInfoDto.getCreateYn().equals(Constants.PAY_INFO_CREATEYN_SUCESS)
//                || payInfoDto.getCreateYn().equals(Constants.PAY_INFO_CREATEYN_TIMEOUT)) {
//            //dto 복호화
//            payInfoDto=this.dec(payInfoDto);
//            //이미지생성
//            if(createImage(payInfoDto)){
//                //등록된 데이터의 create_yn 업데이터 한다
//                payInfoDto.setCreateYn(Constants.PAY_INFO_CREATEYN_IMG);
//                dao.updatePayInto(payInfoDto);
//                //변경신청서 이미지 암호화
//                if(crytoImg(payInfoDto)){
//                    //msp 데이터 등록 처리
//                    this.processMsp(payInfoDto);
//                    result="success";
//                }
//            }
//        }else{
//            //이미지가 존재하면
//            result="existImage";
//        }
//        return result;
//    }
//
//    public void makePayInfoImage(PayInfoDto dto) {
//
//        PayInfoDto payInfoDto = new PayInfoDto();
//
//        try {
//            BeanUtils.copyProperties(payInfoDto, dto);
//        } catch (IllegalAccessException e) {
//            throw new McpCommonException(COMMON_EXCEPTION);
//        } catch (InvocationTargetException e) {
//            throw new McpCommonException(COMMON_EXCEPTION);
//        }
//
//        //시퀀스 채번
//        payInfoDto.setPaySeq(dao.getSeq());
//
//        //dto 암호화
//        payInfoDto= this.enc(payInfoDto);
//
//        //받은 데이터  최초저장
//        dao.insetPayInfo(payInfoDto);
//
//        //dto 복호화
//        payInfoDto=this.dec(payInfoDto);
//
//        //이미지생성
//        if(createImage(payInfoDto )){
//            //등록된 데이터의 create_yn 업데이터 한다
//            dao.updatePayInto(payInfoDto);
//            //변경신청서 이미지 암호화
//            if(crytoImg(payInfoDto)){
//                //msp 데이터 등록 처리
//                this.processMsp(payInfoDto);
//            }
//        }
//
//    }
//
//    public boolean createImage(PayInfoDto dto ){
//        //변수선언
//        boolean result=false;
//        File org_file = new File(orgImgPath);
//
//        //날짜 별 디렉토리 셋팅
//        String tempEndImgPath = endImgPath+DateTimeUtil.getFormatString("yyyy")+"/"+DateTimeUtil.getFormatString("MM")+"/"+DateTimeUtil.getFormatString("dd")+"/";//최종 이미지 경로 이미지명 불포함  **ORIGIN_DIR/E0002/yyyy/MM/dd**
//
//        //저장 디렉토리 경로 생성
//        File derectoryTemp = new File(tempEndImgPath);
//        if(!derectoryTemp.exists()){
//            derectoryTemp.mkdirs();
//        }
//        //저장 파일 전체 경로
//        File end_file = new File(tempEndImgPath+"TEMP_KIS"+dto.getPaySeq()+".jpg");
//
//        //상수에 저장
//        dto.setEndFilePath(tempEndImgPath+"TEMP_KIS"+dto.getPaySeq()+".jpg");
//
//
//
//        //템플릿 이미지 확인
//        if(org_file.isFile()){
//            FileImageOutputStream output = null;
//            try {
//                PayInfoFormDto form = new PayInfoFormDto();
//                //변경서식지 좌표데이터
//                List<PayInfoFormDto> formList  = dao.getPositionList();
//                HashMap<String,String> tempMap =new HashMap<String,String>();
//
//                //이미지 생성용 데이터 가져오기
//                tempMap=dtoToMap(dto);
//
//                Font font = Font.createFont(0, new File(orgFontPath));
//                //이미지파일 버퍼에 올리기
//                BufferedImage read_image = ImageIO.read(org_file);
//
//                if (read_image == null) {
//                    throw new McpCommonException(COMMON_EXCEPTION);
//                }
//                //버퍼이미지 그래픽으로 생성
//                Graphics g = read_image.getGraphics();
//                //그래픽 컬러 지정
//                g.setColor(Color.BLACK);
//                //그래픽 폰트 지정
//                g.setFont(font.deriveFont(30.0F));
//                //좌표 값
//                if(!formList.isEmpty()){
//                    for(int i=0; i<formList.size();i++){
//                        form=formList.get(i);
//                        if(null!=form){
//                            if(form.getCodedataYn().equals("N")){
//                                g.drawString(tempMap.get(form.getColunmName()), Integer.parseInt(form.getMetaLine()) , Integer.parseInt(form.getMetaRow()));
//                            }else{
//                                g.drawString(form.getCodedata(), Integer.parseInt(form.getMetaLine()) , Integer.parseInt(form.getMetaRow()));
//                            }
//                        }
//                    }
//                }
//
//                g.dispose();
//                //기존 이미지 생성
//                //ImageIO.write(read_image, "jpg", end_file);
//                //이미지 용량 300KB 이하로 생성
//                Iterator iter = ImageIO.getImageWritersByFormatName("jpg");
//                ImageWriter writer = (ImageWriter)iter.next();
//                ImageWriteParam iwp = writer.getDefaultWriteParam();
//
//                iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
//                iwp.setCompressionQuality(0.1f);   // 0~1사이의 값 float
//
//                output = new FileImageOutputStream(end_file);
//                writer.setOutput(output);
//                IIOImage image = new IIOImage(read_image, null, null);
//                writer.write(null, image, iwp);
//                writer.dispose();
//
//                //변경신청서 생성 완료여부
//                if(end_file.isFile()){
//                    result=true;
//                }
//            } catch (IOException e) {
//                logger.error(e.getMessage());
//            } catch (Exception e) {
//                logger.error(e.getMessage());
//            } finally {
//                if(output != null)
//                    try {
//                        output.close();
//                    } catch (IOException e) {
//                        logger.error("Exception e : {}", e.getMessage());
//                    }
//            }
//        }else{
//            logger.debug("====================PayInfo 템플릿 이미지 없음======================");
//        }
//
//        return result;
//    }
//
//
//    //dto 복호화
//    public PayInfoDto dec(PayInfoDto dto){
//        try {
//            dto.setBirthDate(EncryptUtil.ace256Dec(dto.getBirthDate()));//생년월일
//            dto.setCtn(EncryptUtil.ace256Dec(dto.getCtn()));//전화번호
//            dto.setReqAccountNumber(EncryptUtil.ace256Dec(dto.getReqAccountNumber()));//계좌번호
//        } catch (CryptoException e) {
//            throw new McpCommonException(COMMON_EXCEPTION);
//        }
//        return dto;
//    }
//
//    //dto 암호화
//    public PayInfoDto enc(PayInfoDto dto){
//        dto.setBirthDate(EncryptUtil.ace256Enc(dto.getBirthDate()));//생년월일
//        dto.setCtn(EncryptUtil.ace256Enc(dto.getCtn()));//전화번호
//        dto.setReqAccountNumber(EncryptUtil.ace256Enc(dto.getReqAccountNumber()));//계좌번호
//        return dto;
//    }
//
//
//    //이미지생성 전  데이터 셋팅
//    public HashMap<String,String> dtoToMap(PayInfoDto dto){
//        HashMap<String,String> tempMap =new HashMap<String,String>();
//
//        tempMap.put("PAY_SEQ",dto.getPaySeq());
//        tempMap.put("CSTMR_NAME",dto.getCstmrName());
//        tempMap.put("BIRTH_DATE",dto.getBirthDate());
//        tempMap.put("CTN",dto.getCtn());
//        tempMap.put("REQ_BANK",this.findBankName(dto.getReqBank()));
//        tempMap.put("REQ_ACCOUNT_NUMBER",dto.getReqAccountNumber());
//        tempMap.put("RES_NO",dto.getResNo());
//        tempMap.put("YEAR",DateTimeUtil.getFormatString("yyyy"));
//        tempMap.put("MONTH",DateTimeUtil.getFormatString("MM"));
//        tempMap.put("DAY",DateTimeUtil.getFormatString("dd"));
//        tempMap.put("CHECK_BOX","true");
//
//
//        return  tempMap;
//    }
//
//    //은행 코드값으로 은행이름 찾기
//    public String findBankName(String bankCode){
//        String result="";
//        List<NmcpCdDtlDto> tempList = NmcpServiceUtils.getCodeList("BNK");
//        NmcpCdDtlDto tempDto =new NmcpCdDtlDto();
//        if(tempList != null && !tempList.isEmpty()){
//            for(int i=0; i<tempList.size(); i++){
//                tempDto = tempList.get(i) != null ? tempList.get(i) : null ;
//                if(bankCode.equals(tempDto.getExpnsnStrVal1())){
//                    result=tempDto.getDtlCdNm();
//                }
//            }
//        }
//        return result;
//    }
//
//    //msp CMN_KFTC_EVIDENCE 데이터 등록
//    public boolean insertMspDate(PayInfoDto dto){
//        boolean result=false;
//
//        String fullPath [] = dto.getEndFilePath().split("TEMP_KIS");//경로중 KIS로짤라서 앞에껄 RealFilePath로
//
//        EvidenceDto eviDto = new EvidenceDto();
//        //ban , contract_num 채번
//        eviDto=dao.selectMspJuoSubinfo(dto);
//
//        //이미지 합본 프로시져 필수파라미터 셋팅
//        dto.setContractNum(eviDto.getContractNum());
//
//        String validDt = "" ; //DateTimeUtil
//
//        try {
//            validDt = DateTimeUtil.addDays(5);
//        } catch (ParseException e) {
//            throw new McpCommonException(COMMON_EXCEPTION);
//        }
//
//        //등록일자 유효기간
//        eviDto.setRgstDt(DateTimeUtil.getFormatString("yyyyMMdd"));
//        eviDto.setValidDt(validDt);
//
//        ///addDays
//
//        //파일경로 ,파일명 , 확장자 ,생성여부
//        if(fullPath!=null && fullPath.length>0){
//            eviDto.setRealFilePath(fullPath[0]);
//            eviDto.setRealFileNm("KIS"+fullPath[1]);
//            eviDto.setExt(fullPath[1].split("\\.")[1]);
//            eviDto.setRegYn("Y");
//        }
//
//        //등록 아이디 , 등록자 아이디 , 수정자아이디
//        eviDto.setRgstId(dto.getUserId());
//        eviDto.setRegstId("MPORTAL");
//        eviDto.setRvisnId("MPORTAL");
//
//        //if(dao.insetEvidence(eviDto)>0){
//        if(dao.insertOrUpdatePayInfo(eviDto)>0){
//            result=true;
//        }
//
//        return result;
//    }
//
//    public boolean crytoImg(PayInfoDto dto ){
//        String end_file_path = dto.getEndFilePath();
//        String end_file_enc_path = "";
//        //변경신청서 이미지 암호화
//        boolean result =false;
//        CNPECMJava.CNPEncryptModule.crypto.FileHandle fileHandle = new FileHandle();
//
//
//        try {
//            //같은 파일명이면 복호화가 안되기때문에 다를파일명으로 저장후 암호화
//            end_file_enc_path = end_file_path.replaceAll("TEMP_", "");
//            fileHandle.Encrypt(end_file_path,end_file_enc_path);
//            if(new File(end_file_path).isFile()){
//                result=true;
//            }
//            //암호화 파일생성후 암호화 전 임시파일 삭제
//            if(new File(end_file_enc_path).isFile()){
//                new File(end_file_path).delete();
//            }
//        } catch(NullPointerException e) {
//            logger.debug("변경신청서 이미지 암호화에 실패하였습니다");
//        } catch (Exception e) {
//            logger.debug("변경신청서 이미지 암호화에 실패하였습니다");
//        }
//        return result;
//    }
//
//    public void processMsp(PayInfoDto dto ){
//        //msp 데이터 등록 처리
//        if(this.insertMspDate(dto )){
//            // 자동이체 동의서 합본 처리
//            dao.callMspPayinfoImg(dto.getContractNum(),dto.getReqBank(),dto.getReqAccountNumber(),"jpg","MPORTAL");
//        }
//    }
//
//    @Override
//    public void insetPayInfo(PayInfoDto dto) {
//        dao.insetPayInfo(dto);
//    }
//
//    @Override
//    public PayInfoDto savePayInfoHist(PayInfoDto payInfoDto) {
//        PayInfoDto payInfoDtoTem = new PayInfoDto();
//
//        try {
//            BeanUtils.copyProperties(payInfoDtoTem, payInfoDto);
//        } catch (IllegalAccessException e) {
//            throw new McpCommonException(COMMON_EXCEPTION);
//        } catch (InvocationTargetException e) {
//            throw new McpCommonException(COMMON_EXCEPTION);
//        }
//
//        String createYn = StringUtil.NVL(payInfoDtoTem.getCreateYn(), "");
//
//        if (createYn.equals(Constants.PAY_INFO_CREATEYN_REQ)) { //요청
//
//            //시퀀스 채번
//            payInfoDtoTem.setPaySeq(dao.getSeq());
//            //dto 암호화
//            payInfoDtoTem= this.enc(payInfoDtoTem);
//            //받은 데이터  최초저장
//            int resultCnt = dao.insetPayInfo(payInfoDtoTem);
//            if (resultCnt > 0) {
//                payInfoDtoTem.setResult(true);
//            }
//
//        } else if (createYn.equals(Constants.PAY_INFO_CREATEYN_SUCESS)) { //엠플렛폼 연동 성공
//
//            //등록된 데이터의 create_yn 업데이터 한다
//            dao.updatePayInto(payInfoDtoTem);
//
//            //dto 복호화
//            payInfoDtoTem=this.dec(payInfoDtoTem);
//
//            //이미지생성
//            if(createImage(payInfoDtoTem)){
//                //등록된 데이터의 create_yn 업데이터 한다
//                payInfoDtoTem.setCreateYn(Constants.PAY_INFO_CREATEYN_IMG);
//                dao.updatePayInto(payInfoDtoTem);
//                //변경신청서 이미지 암호화
//                if(crytoImg(payInfoDtoTem )){
//                    //msp 데이터 등록 처리
//                    this.processMsp(payInfoDtoTem);
//                }
//            }
//
//        } else if (createYn.equals(Constants.PAY_INFO_CREATEYN_FAIL)
//                || (createYn.equals(Constants.PAY_INFO_CREATEYN_TIMEOUT))) { //엠플렛폼 연동 실패 or time out
//
//            dao.updatePayInto(payInfoDtoTem);
//
//        }
//
//        return payInfoDtoTem;
//    }
//
//}
