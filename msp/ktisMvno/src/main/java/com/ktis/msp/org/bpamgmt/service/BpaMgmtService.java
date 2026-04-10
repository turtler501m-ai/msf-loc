package com.ktis.msp.org.bpamgmt.service;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.btchmgmt.vo.BatchJobVO;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.org.bpamgmt.mapper.BpaMgmtMapper;
import com.ktis.msp.org.bpamgmt.vo.BpaMgmtFileReqVO;
import com.ktis.msp.org.bpamgmt.vo.BpaMgmtReqVO;
import com.ktis.msp.org.bpamgmt.vo.BpaMgmtUploadInfoVO;
import egovframework.rte.fdl.property.EgovPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BpaMgmtService extends BaseService {

    @Autowired
    private BpaMgmtMapper bpaMgmtMapper;

    @Autowired
    private FileDownService fileDownService;

    /**
     * propertiesService
     */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;

    /**
     * @Description : [정산 데이터] 업무 리스트 조회
     * @Param  : searchVO
     * @Return : List<?>
     * @Author : yhk
     * @CreateDate : 2026.02.24
     */
    public List<?> getDataTaskList(BpaMgmtReqVO searchVO) {
        return bpaMgmtMapper.getDataTaskList(searchVO);
    }

    /**
     * @Description : [정산 데이터] 업무 등록
     * @Param  : searchVO
     * @Return :
     * @Author : yhk
     * @CreateDate : 2026.02.24
     */
    public void insertDataTask(BpaMgmtReqVO searchVO) throws MvnoServiceException {
        int bpaIdCount = bpaMgmtMapper.selectBpaIdCnt(searchVO.getBpaId());

        if (bpaIdCount > 0) {
          throw new MvnoServiceException("이미 등록된 업무입니다.");
        }

        bpaMgmtMapper.insertDataTask(searchVO);
    }

    /**
     * @Description : [정산 데이터] 업무 수정
     * @Param  : searchVO
     * @Return :
     * @Author : yhk
     * @CreateDate : 2026.02.24
     */
    public void updateDataTask(BpaMgmtReqVO searchVO) throws MvnoServiceException {
        int bpaIdCount = bpaMgmtMapper.selectBpaIdCnt(searchVO.getBpaId());

        if (bpaIdCount <= 0) {
            throw new MvnoServiceException("등록되지 않은 업무입니다.");
        }

        bpaMgmtMapper.updateDataTask(searchVO);
    }

    /**
     * @Description : [정산 데이터] 파일관리 > 파일 리스트 조회
     * @Param  : searchVO
     * @Return : List<?>
     * @Author : yhk
     * @CreateDate : 2026.02.27
     */
    public List<?> getDataTaskFileList(BpaMgmtFileReqVO searchVO) {
       return bpaMgmtMapper.getDataTaskFileList(searchVO);
    }

    /**
     * @Description : [정산 데이터] 파일관리 > 파일 등록
     * @Param  : searchVO
     * @Return :
     * @Author : yhk
     * @CreateDate : 2026.02.27
     */
    public void insertDataTaskFile(BpaMgmtFileReqVO searchVO) {
        bpaMgmtMapper.insertDataTaskFile(searchVO);
    }

    /**
     * @Description : [정산 데이터] 파일관리 > 파일 수정
     * @Param  : bpaMgmtFileReqVO
     * @Return :
     * @Author : yhk
     * @CreateDate : 2026.03. 02
     */
    public void updateDataTaskFile(BpaMgmtFileReqVO searchVO) {
        bpaMgmtMapper.updateDataTaskFile(searchVO);
    }

    public String getCntrChannelExcelTempDown(HttpServletRequest request, HttpServletResponse response) {

        List<BpaMgmtUploadInfoVO> resultList = new ArrayList<BpaMgmtUploadInfoVO>();

        // 파일명
        String serverInfo = propertiesService.getString("excelPath");
        String strFilename = serverInfo + "계약번호별_채널맵핑_"; // 파일명
        String strSheetname = "계약번호별_채널맵핑"; // 시트명

        // 엑셀 제목
        String [] strHead = {"서비스계약번호"};
        String [] strValue = {"svcCntrNo"};

        //엑셀 컬럼 사이즈
        int[] intWidth = {5000};
        int[] intLen = {};

        //파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
        return fileDownService.excelDownProc(strFilename, strSheetname, resultList.iterator(), strHead, intWidth, strValue, request, response, intLen);
    }

    /**
     * @Description : [정산 데이터] 자료생성 > 업로드된 데이터 건수 조회 (startRow/endRow 계산)
     * @Param  : bpaId
     * @Return :
     * @Author : yhk
     * @CreateDate : 2026.03. 02
     */
    public int selectBpaUploadInfoCnt(String bpaId) {
        return bpaMgmtMapper.selectBpaUploadInfoCnt(bpaId);
    }

    /**
     * @Description : [정산 데이터] 엑셀업로드 > 업로드할 데이터 등록
     * @Param  : items, searchVO
     * @Return :
     * @Author : yhk
     * @CreateDate : 2026.03. 04
     */
    @Transactional(rollbackFor=Exception.class)
    public void regDataTaskExcelTempComplete(List<BpaMgmtUploadInfoVO> items, BpaMgmtUploadInfoVO searchVO) {
        if(KtisUtil.isEmpty(items)) {
            throw new MvnoRunException(-1,"등록할 데이터가 없습니다.");
        }

        String sessionUserId = searchVO.getSessionUserId();

        for (int i = 0; i < items.size(); i++) {
            BpaMgmtUploadInfoVO vo = items.get(i);

            // 유효성 체크
            if(KtisUtil.isEmpty(vo.getUploadFileNm())) {
                throw new MvnoRunException(-1,"업로드된 파일이 없습니다.");
            }

            String contractNum = bpaMgmtMapper.selectContractNum(vo.getSvcCntrNo());
            if (KtisUtil.isEmpty(contractNum)) {
                throw new MvnoRunException(-1, "["+ (i+1) +"행] 서비스 계약번호가 존재하지 않습니다. ["+vo.getSvcCntrNo()+"]");
            }

            // 최초 1회만 삭제
            if(i == 0) {
                // 기존 데이터 모두 삭제 후 등록
                bpaMgmtMapper.deleteBpaUploadInfo(vo.getBpaId());
            }

            vo.setRegstId(sessionUserId);
            vo.setSeq(i+1);

            bpaMgmtMapper.insertBpaUploadInfo(vo);
        }

    }

    /**
     * @Description : [정산 데이터] 엑셀업로드 > 업로드된 데이터 리스트 조회
     * @Param  : pRequestParamMap
     * @Return :
     * @Author : yhk
     * @CreateDate : 2026.03. 04
     */
    public List<?> getDataExcelList(BpaMgmtUploadInfoVO searchVO) {
        return bpaMgmtMapper.getDataExcelList(searchVO);
    }

    /**
     * @Description : [정산 데이터] 엑셀업로드 > 데이터 초기화 (사용여부 N 변경)
     * @Param  : searchVO
     * @Return :
     * @Author : yhk
     * @CreateDate : 2026.03. 04
     */
    public void updateDataExcelListUseYn(BpaMgmtUploadInfoVO searchVO) {
        bpaMgmtMapper.updateDataExcelListUseYn(searchVO);
    }

    /**
     * @Description : [정산 데이터] 엑셀업로드 > 배치코드 별 파라미터 초기화
     * @Param  : searchVO
     * @Return :
     * @Author : yhk
     * @CreateDate : 2026.03. 04
     */
    public void initBpaExcelParameter(Map<String, Object> excelMap, Map<String, Object> paramMap, String baseDt) {
        String searchVal = "업무코드:" + paramMap.get("bpaId") +
                "|기준일자:" + baseDt;
        if (searchVal.length() > 500) {
            searchVal = searchVal.substring(0, 500);
        }

        excelMap.put("SEARCH_VAL", searchVal);
        paramMap.put("baseDt", baseDt);
    }

    public void initBpaExcelParameter(Map<String, Object> excelMap, Map<String, Object> paramMap, String baseDt, String rateCd) {
        String searchVal = "업무코드:" + paramMap.get("bpaId") +
                "|기준일자:" + baseDt +
                "|요금제코드:" + rateCd;
        if (searchVal.length() > 500) {
            searchVal = searchVal.substring(0, 500);
        }

        excelMap.put("SEARCH_VAL", searchVal);
        paramMap.put("baseDt", baseDt);
        paramMap.put("rateCd", rateCd);
    }

    public void initBpaExcelParameterPeriod(Map<String, Object> excelMap, Map<String, Object> paramMap, String startDt, String endDt) {
        String searchVal = "업무코드:" + paramMap.get("bpaId") +
                "|시작일자:" + startDt +
                "|종료일자:" + endDt;
        if (searchVal.length() > 500) {
            searchVal = searchVal.substring(0, 500);
        }

        excelMap.put("SEARCH_VAL", searchVal);
        paramMap.put("startDt", startDt);
        paramMap.put("endDt", endDt);
    }
}
