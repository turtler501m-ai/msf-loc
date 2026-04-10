package com.ktis.msp.img.applicationForm.mapper;

import java.util.HashMap;
import java.util.List;

import com.ktis.msp.img.applicationForm.vo.ScanFileVO;
import com.ktis.msp.img.applicationForm.vo.ScanLogVO;
import com.ktis.msp.img.applicationForm.vo.ScanRequestVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("AppFormMapper")
public interface AppFormMapper {
	
	public HashMap<String, String> getScnViewerInfo(HashMap<String, String> params);
	
	public HashMap<String, String> getScnViewerInfoTypeS(HashMap<String, String> params);
	
	public String getScanViewerVersion();

	public void deleteScanImageCallTypeByC(String fileId);
	
	public void deleteScanImageCallTypeByS(String fileId);

	public String getScnViewerRgstDtTypeS(HashMap<String, String> params);
	
	public String getScnViewerRgstDt(HashMap<String, String> params);

    ScanFileVO getScanFile(String fileId);

    int deleteScanFileByFileId(String fileId);

    int insertScanLogTxn(ScanLogVO scanLogVO);

    int insertOriginScanFileFrom(ScanFileVO scanFileVO);
}
