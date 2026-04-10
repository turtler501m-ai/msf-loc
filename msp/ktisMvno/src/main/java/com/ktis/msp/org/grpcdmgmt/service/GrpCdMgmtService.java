package com.ktis.msp.org.grpcdmgmt.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.org.grpcdmgmt.mapper.GrpCdMgmtMapper;
import com.ktis.msp.org.grpcdmgmt.vo.GrpCdMgmtVO;
import com.ktis.msp.org.grpcdmgmt.vo.GrpMgmtVO;
import com.ktis.msp.org.orgnrelmgmt.vo.OrgnRelMgmtVO;

/**
 * @Class Name : GrpCdMgmtService
 * @Description : 공통코드 관리 서비스
 * @
 * @ 수정일       수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @2014.08.28 고은정 최초생성
 * @
 * @author : 고은정
 * @Create Date : 2014. 8. 28.
 */
@Service
public class GrpCdMgmtService extends BaseService {
    
    @Autowired
    private GrpCdMgmtMapper grpCdMgmtMapper;
    
    public GrpCdMgmtService() {
        setLogPrefix("[GrpCdMgmtService] ");
    }

    /**
     * @Description : 공통코드 리스트 조회
     * @Param  : GrpMgmtVO
     * @Return : List<?>
     * @Author : 고은정
     * @Create Date : 2014. 8. 28.
     */
    public List<?> selectGrpMgmtList(GrpMgmtVO grpMgmtVO){
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("selectGrpMgmtList START."));
        logger.info(generateLogMsg("================================================================="));
        
        List<?> listMgmts = new ArrayList<OrgnRelMgmtVO>();
        
        try {
            listMgmts = grpCdMgmtMapper.selectGrpMgmtList(grpMgmtVO);
        } catch (NullPointerException e) {
            // TODO Auto-generated catch block
            logger.info(generateLogMsg(String.format("공통코드 리스트 오류 CAUSE:%s MESSAGE:%s", e.getCause(), e.getMessage())));
        }
        
        return listMgmts;
    }
    
    /**
     * @Description : 공통코드 추가
     * @Param  : GrpMgmtVO
     * @Return : int
     * @Author : 고은정
     * @Create Date : 2014. 8. 28.
     */
    @Transactional(rollbackFor=Exception.class)
    public int insertGrpMgmt(GrpMgmtVO grpMgmtVO){
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("insertGrpMgmt START."));
        logger.info(generateLogMsg("================================================================="));
        
        int resultCnt = 0;
        
        try {
            resultCnt = grpCdMgmtMapper.insertGrpMgmt(grpMgmtVO);
        } catch (NullPointerException e) {
            // TODO Auto-generated catch block
            logger.info(generateLogMsg(String.format("공통코드 추가 오류 CAUSE:%s MESSAGE:%s", e.getCause(), e.getMessage())));
        }
        
        return resultCnt;
    }
    
    /**
     * @Description : 공통코드 수정
     * @Param  : GrpMgmtVO
     * @Return : int
     * @Author : 고은정
     * @Create Date : 2014. 8. 28.
     */
    @Transactional(rollbackFor=Exception.class)
    public int updateGrpMgmt(GrpMgmtVO grpMgmtVO){
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("updateGrpMgmt START."));
        logger.info(generateLogMsg("================================================================="));
        
        int resultCnt = 0;
        
        try {
            resultCnt = grpCdMgmtMapper.updateGrpMgmt(grpMgmtVO);
        } catch (NullPointerException e) {
            // TODO Auto-generated catch block
            logger.info(generateLogMsg(String.format("공통코드 수정 오류 CAUSE:%s MESSAGE:%s", e.getCause(), e.getMessage())));
        }
        
        return resultCnt;
    }
    
    /**
     * @Description : 공통코드 삭제
     * @Param  : GrpMgmtVO
     * @Return : int
     * @Author : 고은정
     * @Create Date : 2014. 8. 28.
     */
    @Transactional(rollbackFor=Exception.class)
    public int deleteGrpMgmt(GrpMgmtVO grpMgmtVO){
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("deleteGrpMgmt START."));
        logger.info(generateLogMsg("================================================================="));
 
        int resultCnt = 0;
        
        try {
            resultCnt = grpCdMgmtMapper.deleteAllGrpCdMgmt(grpMgmtVO);
            
            if (resultCnt > 0 ){
                resultCnt = grpCdMgmtMapper.deleteGrpMgmt(grpMgmtVO);
            } else {
                throw new MvnoServiceException("error");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.info(generateLogMsg(String.format("공통코드 삭제 오류 CAUSE:%s MESSAGE:%s", e.getCause(), e.getMessage())));
        }
        
        return resultCnt;
        
    }
    /**
     * @Description : 공통코드상세 리스트 조회
     * @Param  : GrpCdMgmtVO
     * @Return : List<?>
     * @Author : 고은정
     * @Create Date : 2014. 8. 28.
     */
    public List<?> selectGrpCdMgmtList(GrpCdMgmtVO grpCdMgmtVO){
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("selectGrpCdMgmtList START."));
        logger.info(generateLogMsg("================================================================="));
        
        List<?> listMgmts = new ArrayList<OrgnRelMgmtVO>();
        
        try {
            listMgmts = grpCdMgmtMapper.selectGrpCdMgmtList(grpCdMgmtVO);
        } catch (NullPointerException e) {
            // TODO Auto-generated catch block
            logger.info(generateLogMsg(String.format("공통코드 상세 리스트 오류 CAUSE:%s MESSAGE:%s", e.getCause(), e.getMessage())));
        }
        
        return listMgmts;
        
    }
    
    /**
     * @Description : 공통코드 상세 추가
     * @Param  : GrpCdMgmtVO
     * @Return : int
     * @Author : 고은정
     * @Create Date : 2014. 8. 28.
     */
    @Transactional(rollbackFor=Exception.class)
    public int insertGrpCdMgmt(GrpCdMgmtVO grpCdMgmtVO){
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("insertGrpCdMgmt START."));
        logger.info(generateLogMsg("================================================================="));
        
        int resultCnt = 0;
        
        try {
            resultCnt = grpCdMgmtMapper.insertGrpCdMgmt(grpCdMgmtVO);
            
        } catch (NullPointerException e) {
            // TODO Auto-generated catch block
            logger.info(generateLogMsg(String.format("공통코드 상세 추가 오류 CAUSE:%s MESSAGE:%s", e.getCause(), e.getMessage())));
        }
        
        return resultCnt;
    }
    
    /**
     * @Description : 공통코드 상세 수정
     * @Param  : GrpCdMgmtVO
     * @Return : int
     * @Author : 고은정
     * @Create Date : 2014. 8. 28.
     */
    @Transactional(rollbackFor=Exception.class)
    public int updateGrpCdMgmt(GrpCdMgmtVO grpCdMgmtVO){
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("updateGrpCdMgmt START."));
        logger.info(generateLogMsg("================================================================="));
        
        int resultCnt = 0;
        
        try {
            resultCnt = grpCdMgmtMapper.updateGrpCdMgmt(grpCdMgmtVO);
            
        } catch (NullPointerException e) {
            // TODO Auto-generated catch block
            logger.info(generateLogMsg(String.format("공통코드 상세 수정 오류 CAUSE:%s MESSAGE:%s", e.getCause(), e.getMessage())));
        }
        
        return resultCnt;
    }
    
    /**
     * @Description : 공통코드 상세 삭제
     * @Param  : GrpCdMgmtVO
     * @Return : int
     * @Author : 고은정
     * @Create Date : 2014. 8. 28.
     */
    @Transactional(rollbackFor=Exception.class)
    public int deleteGrpCdMgmt(GrpCdMgmtVO grpCdMgmtVO){
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("deleteGrpCdMgmt START."));
        logger.info(generateLogMsg("================================================================="));
        
        int resultCnt = 0;
        
        try {
            resultCnt = grpCdMgmtMapper.deleteGrpCdMgmt(grpCdMgmtVO);
            
        } catch (NullPointerException e) {
            // TODO Auto-generated catch block
            logger.info(generateLogMsg(String.format("공통코드 상세 삭제 오류 CAUSE:%s MESSAGE:%s", e.getCause(), e.getMessage())));
        }
        
        return resultCnt;
    }
    
    /**
    * @Description : 공통코드 LOV 파일
    * @Param  : 
    * @Return : List<HashMap<String,String>>
    * @Author : 고은정
    * @Create Date : 2014. 9. 18.
     */
    public List<HashMap<String, String>> listGrpCdLOV(){
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("listGrpCdLOV START."));
        logger.info(generateLogMsg("================================================================="));
        
        List<HashMap<String, String>> listLOV = new ArrayList<HashMap<String,String>>();
        
        try{
            listLOV = grpCdMgmtMapper.listGrpCdLOV();
            
        }catch (NullPointerException e) {
            // TODO Auto-generated catch block
            logger.info(generateLogMsg(String.format("공통코드 LOV 리스트 오류 CAUSE:%s MESSAGE:%s", e.getCause(), e.getMessage())));
        }
        
        return listLOV;
    }
    
    /**
    * @Description : 공통코드 그룹명 체크 
    * @Param  : GrpMgmtVO
    * @Return : int
    * @Author : 고은정
    * @Create Date : 2014. 9. 20.
     */
    public int existKorNmGrpMgmt(GrpMgmtVO grpMgmtVO){
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("existKorNmGrpMgmt START."));
        logger.info(generateLogMsg("================================================================="));
        
        int countExistKorNm = 0;
        
        try{
            countExistKorNm = grpCdMgmtMapper.existKorNmGrpMgmt(grpMgmtVO);
        } catch (NullPointerException e) {
            // TODO Auto-generated catch block
            logger.info(generateLogMsg(String.format("공통코드 그룹명 체크 CAUSE:%s MESSAGE:%s", e.getCause(), e.getMessage())));
        }
        
        return countExistKorNm;
    }
    
    /**
     * @Description : 그룹ID 중복체크
     * @Param  : HndstModelVo
     * @Return : int
     * @Author : 고은정
     * @Create Date : 2014. 8. 29.
     */
    public int isExistGrpCdMgmt(GrpMgmtVO grpMgmtVO){
        
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("그룹ID 중복체크 START."));
        logger.info(generateLogMsg("================================================================="));
        
        int resultCnt = grpCdMgmtMapper.isExistGrpCdMgmt(grpMgmtVO);
        
        return resultCnt;
    }    
    
    /**
     * 
    * @Description : 공통코드 리스트 조회
    * @Param  : 
    * @Return : List<?>
    * @Author : 고은정
    * @Create Date : 2014. 10. 7.
     */
    public List<?> listCmnCd(String code){
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("listCmnCd START."));
        logger.info(generateLogMsg("================================================================="));
        
        List<?> listCmnCd = new ArrayList<String>();
        
        try {
            listCmnCd = grpCdMgmtMapper.listCmnCd(code);
        } catch (NullPointerException e) {
            logger.info(generateLogMsg(String.format("공통코드 리스트 오류 CAUSE:%s MESSAGE:%s", e.getCause(), e.getMessage())));
        }
        
        return listCmnCd;
    }
}
