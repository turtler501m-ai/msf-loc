package com.ktis.msp.batch.job.rcp.cantransfermgmt.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.rcp.cantransfermgmt.mapper.CanPpImgTransferMapper;
import com.ktis.msp.batch.job.rcp.cantransfermgmt.vo.CanTransferVO;

import egovframework.rte.fdl.property.EgovPropertyService;

@Service
public class CanPpImgTransferService extends BaseService {

	@Autowired
	private CanPpImgTransferMapper transferPpImgMapper;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	// 해지고객 선불 이미지 정보이관
	final static int TMNT_CUST_DT      = 180;
	//서식지 경로 선언
	final static String ORIGIN_DIR = "ORIGIN_DIR";

	
		
	/**
	 * 해지후 180일 지난 고객 이관
	 * @return
	 * @throws MvnoServiceException
	 */
	@Transactional(rollbackFor=Exception.class)
	public int procTmntPpImgTransfer2() throws MvnoServiceException {
		int procCnt = 0;
		
		List<CanTransferVO> list = transferPpImgMapper.getTmntPpImgList(TMNT_CUST_DT);
		
		try{
			for(CanTransferVO vo : list){
				
				LOGGER.debug("계약번호=" + vo.getContractNum());
				LOGGER.debug("서비스계약번호=" + vo.getSvcCntrNo());
				transferPpImgMapper.insertCanPpImg(vo.getContractNum());

			}
		}catch(Exception e){
			throw new MvnoServiceException("ERCP1006", e);
		}
		
		return procCnt;
	}
	
	public static boolean fileIsLive(String isLivefile){
		File file = new File(isLivefile);
		if(file.exists()) {
			return true;
		} else {
			return false;
		}
	}
	
	
	/**
	 * 해지후 180일 지난 선불 고객 파일 이관
	 * @return
	 * @throws MvnoServiceException
	 */
	@Transactional(rollbackFor=Exception.class)
	public int procTmntPpImgTransfer() throws MvnoServiceException {
		int procCnt = 0;

		LOGGER.info("########## procTmntPpImgTransfer START ##########");
		String originNasDir = "/jboss/data/";
		// 20251023 backupNas 경로 변경 /jboss/data2/ -->> /jboss/data_can/
		String backupNasDir = "/jboss/data_can/";
		
		// 해지고객 이관대상 조회
		List<CanTransferVO> list = transferPpImgMapper.getTmntPpImgList(TMNT_CUST_DT);
		
		try{
			for(CanTransferVO vo : list){
				LOGGER.info("## contractNum : {}", vo.getContractNum());
		       				
				vo.setTmntCustDt(TMNT_CUST_DT);

				// msp PPS_CUSTOMER_IMAGE 테이블에서 서식지파일 조회
				List<CanTransferVO> scanList = transferPpImgMapper.getScanFilePpsCustomerImage(vo);
				
				// 이관고객 서식지 백업 NAS 경로 이동
				// AS-IS, TO-BE 경로 및 파일 선언
				// TO-BE 경로 확인, 없을시 경로 생성
				// AS-IS 파일 있을시 TO-BE 경로로 파일 이동 후 AS-IS 파일 삭제
				// TO-BE 경로에 파일 없으면 STATUS D 로 UPDATE
				// PPS_CUSTOMER_IMAGE의 IMG_PATH TO-BE 경로로 업데이트
				// PPS_CUSTOMER_IMAGE@DL_CAN insert / 이미 데이터 존재시 IMG_PATH TO-BE 경로로 업데이트
				for(CanTransferVO scanVO : scanList){
					try {
						scanVO.setContractNum(vo.getContractNum());

						// AS-IS, TO-BE 경로 및 파일 선언
						String asIsFileInfo = scanVO.getFileInfo();
						String fileDirDtl = asIsFileInfo.replace(originNasDir,""); // webscan/prd/image/ORIGIN_DIR/E0001/2017/07/27/EA9DD96A63DC62499AFF08BC0680F2AD.JPG
						fileDirDtl = fileDirDtl.replace("?", "_"); // 파일명 ? -> _ 로 치환
						String sysFileNm = fileDirDtl.substring(fileDirDtl.lastIndexOf("/")+1, fileDirDtl.length());

						String tobeFileInfo = backupNasDir + fileDirDtl;
			    		String tobeFileDir = tobeFileInfo.replace("/" + sysFileNm, "");

						LOGGER.info("# AS-IS File : {}", asIsFileInfo);  // /jboss/data_can/webscan/prd/image/ORIGIN_DIR/E0001/2017/07/27/EA9DD96A63DC62499AFF08BC0680F2AD.JPG
					    LOGGER.debug("# sysFileNm : {}", sysFileNm); // EA9DD96A63DC62499AFF08BC0680F2AD.JPG
					    LOGGER.debug("# TO-BE File : {}", tobeFileInfo); // /jboss/data_can/webscan/prd/image/ORIGIN_DIR/E0001/2017/07/27/EA9DD96A63DC62499AFF08BC0680F2AD.JPG
			    		LOGGER.debug("# TO-BE FileDir : {}", tobeFileDir);  // /jboss/data_can/webscan/prd/image/ORIGIN_DIR/E0001/2017/07/27

					    File asIsFile = new File(asIsFileInfo);
					    File toBeFile = new File(tobeFileInfo);

						// TO-BE 경로 확인, 없을시 경로 생성
				    	try{
				    		File tobeFolder = new File(tobeFileDir);

							if(!tobeFolder.exists()){
								LOGGER.debug("# TO-BE Directory Create >> {}", tobeFileDir);
								if (!tobeFolder.mkdirs()){
					               throw new MvnoServiceException("[" + tobeFileDir + "] Create Directory Fail");
					            }
							}else{
								LOGGER.debug("# TO-BE Directory exist");
							}
				    	}catch(Exception e){
				    		throw new MvnoServiceException("ERCP1006", e);
				    	}
						
						//서식지 파일 이관
						try {
							CanTransferVO updateVo = new CanTransferVO();
							updateVo.setFileId(scanVO.getFileId());     		//파일아이디
							updateVo.setTobeFileInfo(tobeFileInfo);     		//TO-BE 경로
							updateVo.setContractNum(scanVO.getContractNum());   //계약번호
							updateVo.setImgSeq(scanVO.getImgSeq());
							updateVo.setStatus("A");

							// AS-IS 파일 있을시 TO-BE 경로로 파일 이동 후 AS-IS 파일 삭제
							int data = 0;
							if (fileIsLive(asIsFileInfo)) {

						    	FileInputStream fis = null;
						    	FileOutputStream fos = null;
						    	try {
									LOGGER.info("# AS-IS File Exist ");
									fis = new FileInputStream(asIsFile);
									fos = new FileOutputStream(toBeFile);
									while((data=fis.read())!=-1){
										fos.write(data);
									}
									//AS-IS 경로 서식지 삭제
									LOGGER.debug("# AS-IS File Delete ");
									asIsFile.delete();									
						    	} catch (Exception e) {
									throw new MvnoServiceException("File Exception",e);
						    	} finally {
									try {
										if (fis != null) {
											fis.close();
										}
										if (fos != null) {
											fos.close();
										}
									} catch (IOException e) {
										throw new MvnoServiceException("Resource close Exception",e);
									}
								}
							} else {
								LOGGER.info("# AS-IS File Empty ");
							}
							
							// TO-BE 경로에 파일 확인
							if(!fileIsLive(tobeFileInfo)) {
								LOGGER.info("# TO-BE File Empty ");
								updateVo.setStatus("D");
							}

							// PPS_CUSTOMER_IMAGE의 IMG_PATH TO-BE 경로로 업데이트
							transferPpImgMapper.updateMspPpsCustomerImage(updateVo);
							
							// PPS_CUSTOMER_IMAGE@DL_CAN insert / 이미 데이터 존재시 IMG_PATH TO-BE 경로로 업데이트
							if (transferPpImgMapper.chkCanPpsCustomerImage(updateVo) == 0) {
								transferPpImgMapper.insertCanPpsCustomerImage(updateVo);
							} else {
								transferPpImgMapper.updateCanPpsCustomerImage(updateVo);
							}
						} catch (Exception e) {
							throw new MvnoServiceException("ERCP1006", e);
						}
					} catch(Exception e) {
						LOGGER.error("Exception] pass >> contractNum : {}, AS-IS FileInfo : {} ", vo.getContractNum(), scanVO.getFileInfo());
						continue;
					}
				}
				procCnt++;
			}
		}catch(Exception e){
			throw new MvnoServiceException("ERCP1006", e);
		}

		LOGGER.info("########## procTmntPpImgTransfer End ##########");

		return procCnt;
	}
}
