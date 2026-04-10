package com.ktmmobile.mcp.point.service;

import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.NO_SESSION_EXCEPTION;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.ktmmobile.mcp.common.constants.Constants;
import com.ktmmobile.mcp.point.dao.PointDao;
import com.ktmmobile.mcp.point.dto.CustPointDto;
import com.ktmmobile.mcp.point.dto.CustPointTxnDtlDto;
import com.ktmmobile.mcp.point.dto.CustPointTxnDto;
import com.ktmmobile.mcp.point.dto.CustPointGiveTgtBasDto;
import com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto;

import com.ktmmobile.mcp.common.exception.McpCommonException;
import com.ktmmobile.mcp.common.util.SessionUtils;
import com.ktmmobile.mcp.mypage.dao.MyBenefitDao;

@Service
public class PointServiceImpl implements PointService{
	private static Logger logger = LoggerFactory.getLogger(PointServiceImpl.class);

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

	@Autowired
	PointDao pointDao;

    @Transactional
    public Map<String, Object> editPoint(CustPointTxnDto pointTxnDto){

    	Map<String, Object> map = new HashMap<String,Object>();

    	String userId = pointTxnDto.getCretId();
    	String ip = pointTxnDto.getCretIp();

    	pointTxnDto.setCretIp(ip);
		pointTxnDto.setCretId(userId);

    	if(pointTxnDto.getPointTrtCd().equals(Constants.POINT_TRT_SAVE)) {
    		/**
    		 * 포인트 지급
    		 */

    		//고객포인트지급기준기본 update
    		pointDao.updateCustPointGiveBaseBas(pointTxnDto);
    		//고객포인트내역 insert
    		int pointTxnSeq = pointDao.insertCustPointTxn(pointTxnDto);

    		//고객포인트적립내역 상세 insert
    		pointTxnDto.setPointTxnSeq(pointTxnSeq);
    		pointDao.insertCustPointAcuTxnDtl(pointTxnDto);

    	}else if(pointTxnDto.getPointTrtCd().equals(Constants.POINT_TRT_USE)){
    		/**
    		 * 포인트 사용
    		 */
    		int pointTxnSeq = pointDao.insertCustPointTxn(pointTxnDto);
    		CustPointTxnDtlDto useCustPointTxnDtlDto = new CustPointTxnDtlDto(); // 적립포인트 에서 사용될 포인트 정보 저장

    		useCustPointTxnDtlDto.setCretIp(ip);
    		useCustPointTxnDtlDto.setCretId(userId);

    		//--사용포인트일련번호
			useCustPointTxnDtlDto.setPointTxnSeq(pointTxnSeq);

    		//고객포인트내역상세 조회 (내역 별 사용가능 잔여포인트 리스트)
    		List<CustPointTxnDtlDto> custPointTxnDtlList = pointDao.selectPsblCustPointTxnDtlList(pointTxnDto);

    		//고객포인트내역상세 update
    		int nextRemainPoint = pointTxnDto.getPoint(); // 사용 할 포인트 잔여 (최초는 사용할 포인트)
    		if(custPointTxnDtlList.size() > 0) {
    			for(int i=0;i<custPointTxnDtlList.size();i++) {
    				CustPointTxnDtlDto rspCustPointTxnDtlDto = custPointTxnDtlList.get(i); // 적립포인트

    				rspCustPointTxnDtlDto.setCretId(userId);
    				rspCustPointTxnDtlDto.setCretIp(ip);


    				//--적립포인트인련번호
    				useCustPointTxnDtlDto.setAcuPointTxnSeq(rspCustPointTxnDtlDto.getPointTxnSeq());


    				if(nextRemainPoint > rspCustPointTxnDtlDto.getRemainPoint()) {
    					// 사용포인트가 잔여보다 클 경우
    					nextRemainPoint = nextRemainPoint - rspCustPointTxnDtlDto.getRemainPoint();

    					// 사용상세 insert
    					useCustPointTxnDtlDto.setUsePoint(rspCustPointTxnDtlDto.getRemainPoint()); //--사용포인트
    					//useCustPointTxnDtlDto.setRemainPoint(nextRemainPoint); //--잔여포인트
    					useCustPointTxnDtlDto.setRemainPoint(0); //--잔여포인트
    					useCustPointTxnDtlDto.setCretId(userId);
    					useCustPointTxnDtlDto.setCretIp(ip);
    					pointDao.insertCustPointTxnDtl(useCustPointTxnDtlDto);

    					//적립상세 잔여포인트 update
    					//rspCustPointTxnDtlDto.setRemainPoint(0);
    					pointDao.updateCustPointTxnDtl(rspCustPointTxnDtlDto);

    				}else {
    					// 잔여가 사용 포인트보다 같거나 클경우

    					// 사용상세 insert
    					useCustPointTxnDtlDto.setUsePoint(nextRemainPoint); //--사용포인트
    					useCustPointTxnDtlDto.setRemainPoint(rspCustPointTxnDtlDto.getRemainPoint() - nextRemainPoint); //--잔여포인트
    					useCustPointTxnDtlDto.setCretId(userId);
    					useCustPointTxnDtlDto.setCretIp(ip);
    					pointDao.insertCustPointTxnDtl(useCustPointTxnDtlDto);

    					//적립상세 잔여포인트 update
    					rspCustPointTxnDtlDto.setRemainPoint(nextRemainPoint);
    					pointDao.updateCustPointTxnDtl(rspCustPointTxnDtlDto);

    					nextRemainPoint = 0;

    					break;
    				}
    			}
    		}

    		//고객포인트지급기준기본 update
    		pointDao.updateCustPointGiveBaseBas(pointTxnDto);

    	}else {
    		// 에러
    		map.put("resultCode","90001"); // 포인트처리구분코드 맞지않음
    		return map;
    	}

    	return map;
    }




    @Transactional
    public Map<String, Object> editPointSUE(CustPointTxnDto pointTxnDto) {

    	Map<String, Object> map = new HashMap<String,Object>();

    	String userId = pointTxnDto.getCretId();
    	String ip = pointTxnDto.getCretIp();

        // 서비스계약번호 없는 경우
        if("".contentEquals(pointTxnDto.getSvcCntrNo())) {
            // 에러
            map.put("resultCode","P90001"); // 서비스계약번호 없음
            return map;
        }

        // 회선정보 조회
        McpUserCntrMngDto userCntrMngDto = new McpUserCntrMngDto();
        userCntrMngDto.setSvcCntrNo(pointTxnDto.getSvcCntrNo());

        //---- API 호출 S ----//
        RestTemplate restTemplate = new RestTemplate();
        McpUserCntrMngDto mcpUserCntrMngDto = restTemplate.postForObject(apiInterfaceServer + "/mypage/cntrListNoLogin", userCntrMngDto, McpUserCntrMngDto.class);

    	if(pointTxnDto.getPointTrtCd().equals(Constants.POINT_TRT_SAVE)) {
    		/*
    		 * 포인트 지급
    		 * */

            // 서비스계약번호 없는 경우
            if(mcpUserCntrMngDto == null) {
                // 서비스계약번호 없는 경우 적립 SKIP
                // 에러
                map.put("resultCode","P90001"); // 서비스계약번호 없음
                return map;

            }

            if(!mcpUserCntrMngDto.getSubStatus().equals("A")) {
                // 사용중(개통중)이 아닌 경우 적립 SKIP
                // 에러
                map.put("resultCode","P90002"); // 서비스계약번호 사용중(개통중) 아님
                return map;

            }

            // 포인트 회선일련번호 조회
            CustPointDto targetCustIn = new CustPointDto();
            targetCustIn.setSvcCntrNo(pointTxnDto.getSvcCntrNo());
            CustPointDto targetCust = pointDao.selectCustPointGiveBaseBas(targetCustIn);

            if(targetCust == null) {
                // 포인트 신규 고객 생성 + 포인트 적립

                //고객포인트지급기준기본 insert
                CustPointDto custPointGiveBaseBasDto = new CustPointDto();
                //custPointGiveBaseBasDto.setPointCustSeq();
                custPointGiveBaseBasDto.setUserId("");
                custPointGiveBaseBasDto.setSvcCntrNo(pointTxnDto.getSvcCntrNo());
                custPointGiveBaseBasDto.setTotAcuPoint(pointTxnDto.getPoint());
                custPointGiveBaseBasDto.setTotUsePoint(0);
                custPointGiveBaseBasDto.setTotRemainPoint(pointTxnDto.getPoint());
                custPointGiveBaseBasDto.setTotExtinctionPoint(0);
                //custPointGiveBaseBasDto.setPointSumBaseDate();
                custPointGiveBaseBasDto.setCretIp(pointTxnDto.getCretIp());
                custPointGiveBaseBasDto.setCretId(pointTxnDto.getCretId());
                custPointGiveBaseBasDto.setAmdIp(pointTxnDto.getCretIp());
                custPointGiveBaseBasDto.setAmdId(pointTxnDto.getCretId());

                int pointCustSeq = pointDao.insertCustPointGiveBaseBas(custPointGiveBaseBasDto);

             } else {

            	 //고객포인트지급기준기본 update
                 pointDao.updateCustPointGiveBaseBas(pointTxnDto);
             }


             // 포인트 고객정보 세팅
             CustPointDto targetCust2 = pointDao.selectCustPointGiveBaseBas(targetCustIn);
             pointTxnDto.setPointCustSeq(targetCust2.getPointCustSeq());


             //고객포인트내역 insert
             pointTxnDto.setTotRemainPoint(0); // 총잔여포인트(SQL에서 계산)
             int pointTxnSeq = pointDao.insertCustPointTxnSUE(pointTxnDto);


             //고객포인트적립내역 상세 insert
             pointTxnDto.setRemainPoint(pointTxnDto.getPoint()); // 적립 포인트
             pointDao.insertCustPointAcuTxnDtl(pointTxnDto);

            map.put("resultCode","P0000"); // 처리 성공

    	}else if(pointTxnDto.getPointTrtCd().equals(Constants.POINT_TRT_USE)){
    		/**
    		 * 포인트 사용
    		 */

            pointTxnDto.setTotRemainPoint(0); // 총잔여포인트(SQL에서 계산)
    		int pointTxnSeq = pointDao.insertCustPointTxnSUE(pointTxnDto);
    		CustPointTxnDtlDto useCustPointTxnDtlDto = new CustPointTxnDtlDto(); // 적립포인트 에서 사용될 포인트 정보 저장

    		useCustPointTxnDtlDto.setCretIp(ip);
    		useCustPointTxnDtlDto.setCretId(userId);
    		useCustPointTxnDtlDto.setAmdIp(ip);
    		useCustPointTxnDtlDto.setAmdId(userId);

    		//--사용포인트일련번호
			useCustPointTxnDtlDto.setPointTxnSeq(pointTxnSeq);

    		//고객포인트내역상세 조회 (내역 별 사용가능 잔여포인트 리스트)
    		List<CustPointTxnDtlDto> custPointTxnDtlList = pointDao.selectPsblCustPointTxnDtlList(pointTxnDto);

    		//고객포인트내역상세 update
    		int nextRemainPoint = pointTxnDto.getPoint(); // 사용 할 포인트 잔여 (최초는 사용할 포인트)
    		if(custPointTxnDtlList.size() > 0) {
    			for(int i=0;i<custPointTxnDtlList.size();i++) {
    				CustPointTxnDtlDto rspCustPointTxnDtlDto = custPointTxnDtlList.get(i); // 적립포인트

    				rspCustPointTxnDtlDto.setCretId(userId);
    				rspCustPointTxnDtlDto.setCretIp(ip);
    				rspCustPointTxnDtlDto.setAmdIp(ip);
    				rspCustPointTxnDtlDto.setAmdId(userId);

    				//--적립포인트인련번호
    				useCustPointTxnDtlDto.setAcuPointTxnSeq(rspCustPointTxnDtlDto.getPointTxnSeq());


    				if(nextRemainPoint > rspCustPointTxnDtlDto.getRemainPoint()) {
    					// 사용포인트가 잔여보다 클 경우
    					nextRemainPoint = nextRemainPoint - rspCustPointTxnDtlDto.getRemainPoint();


    					// 사용상세 insert
    					useCustPointTxnDtlDto.setUsePoint(rspCustPointTxnDtlDto.getRemainPoint()); //--사용포인트
    					//useCustPointTxnDtlDto.setRemainPoint(nextRemainPoint); //--잔여포인트
    					useCustPointTxnDtlDto.setRemainPoint(0); //--잔여포인트
    					pointDao.insertCustPointTxnDtl(useCustPointTxnDtlDto);

    					//적립상세 잔여포인트 update
    					//rspCustPointTxnDtlDto.setRemainPoint(0);
    					pointDao.updateCustPointTxnDtl(rspCustPointTxnDtlDto);
    				}else {
    					// 잔여가 사용 포인트보다 같거나 클경우

    					// 사용상세 insert
    					useCustPointTxnDtlDto.setUsePoint(nextRemainPoint); //--사용포인트
    					useCustPointTxnDtlDto.setRemainPoint(rspCustPointTxnDtlDto.getRemainPoint() - nextRemainPoint); //--잔여포인트
    					pointDao.insertCustPointTxnDtl(useCustPointTxnDtlDto);

    					//적립상세 잔여포인트 update
    					rspCustPointTxnDtlDto.setRemainPoint(nextRemainPoint);
    					pointDao.updateCustPointTxnDtl(rspCustPointTxnDtlDto);

    					nextRemainPoint = 0;

    					break;
    				}
    			}
    		}

    		//고객포인트지급기준기본 update
    		pointDao.updateCustPointGiveBaseBas(pointTxnDto);

    		map.put("resultCode","P0000"); // 처리 성공

    	//}else if(pointTxnDto.getPointTrtCd().equals(Constants.POINT_TRT_EXTINCTION)){


    	}else {
    		// 에러
    		map.put("resultCode","90001"); // 포인트처리구분코드 맞지않음
    		return map;
    	}


    	return map;
    }



    @Transactional
    public Map<String, Object> insertPointTgtBas(CustPointGiveTgtBasDto custPointGiveTgtBasDto) {

    	Map<String, Object> map = new HashMap<String,Object>();

		//고객포인트지급기준기본 update
		pointDao.insertCustPointGiveTgtBas(custPointGiveTgtBasDto);

		map.put("resultCode","P0000"); // 처리 성공
    	return map;
    }


}
