package com.ktmmobile.mcp.mypage.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktmmobile.mcp.mypage.dao.MPayViewDao;
import com.ktmmobile.mcp.mypage.dto.MPayViewDto;



@Service
public class MPayViewServiceImpl implements MPayViewService{

    private static Logger logger = LoggerFactory.getLogger(MPayViewServiceImpl.class);

    @Autowired
    private MPayViewDao mPayViewDao;
  
	@Override
	public List<MPayViewDto> selectMPayList(MPayViewDto mPayViewDto) {
		List<MPayViewDto> mPayList = mPayViewDao.selectMPayList(mPayViewDto);
		return mPayList;
	}

	@Override
	public List<Map<String,String>> getDateListFromOpening(String openingDate) {
		List<Map<String,String>> dateList= mPayViewDao.getDateListFromOpening();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy.MM.dd");
		
		Calendar calendar = Calendar.getInstance();
		String strToday = sdf.format(calendar.getTime());
		String todayDate = sdf2.format(calendar.getTime());
		
		int dateListCnt= 13; // 최대 13개월
			
		for(int i=0;i<dateList.size();i++) {
			
			// 당월일 경우 조회일까지 노출
			if(strToday.equals(dateList.get(i).get("monthTitle"))) {
				dateList.get(i).put("monthLastDay", todayDate);
			}
			
			//  개통일 이전의 날짜는 제거
			if(openingDate!=null && !"".equals(openingDate)) {
				if(Integer.parseInt(openingDate) > Integer.parseInt(dateList.get(i).get("monthTitle"))) {
					dateListCnt=i; 
					break;
				}
			}
		}
		
		// 202207 부터 노출 
		if(dateListCnt > dateList.size()) {
			dateListCnt= dateList.size();
		}
		
		return dateList.subList(0, dateListCnt);
	}

	@Override
	public String getTmonLmtAmt(MPayViewDto mPayViewDto) {
		
		String tmonLmtAmt= "0"; 
		
		// 1) 차단여부 조회
		String mpayAgree= mPayViewDao.getMpayAgree(mPayViewDto);
		
		if("Y".equals(mpayAgree)) { // 소액결제 차단여부 (최근1건)
			tmonLmtAmt= "0"; 
			return tmonLmtAmt;
		}
			
		// 2) 소액결제 한도조정 조회
		String adjAmt= mPayViewDao.getMpayAdjAmt(mPayViewDto);
			
		if( adjAmt!= null && !"".equals(adjAmt)) { // 소액결제 한도조정 내용이 존재하는 경우 (최근 1건)
			tmonLmtAmt= adjAmt; 
			return tmonLmtAmt;
		}
		
		// 3) 최근 결제내역 1건 조회하여 최근 소액결제 한도 확인
		String lastTmonLmtAmt= mPayViewDao.getLastTmonLmtAmt(mPayViewDto);
		if(lastTmonLmtAmt== null || "".equals(lastTmonLmtAmt)) {
			tmonLmtAmt= "0";  //최종적으로 소액결제 한도를 확인할 방법이 없으므로 0 처리
		}else {
			tmonLmtAmt= lastTmonLmtAmt;
		}

		return tmonLmtAmt;
	}

}
