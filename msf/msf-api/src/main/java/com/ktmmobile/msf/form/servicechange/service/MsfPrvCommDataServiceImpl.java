/**
 *
 */
package com.ktmmobile.msf.form.servicechange.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.ktmmobile.msf.system.common.dto.db.MspCommDatPrvTxnDto;
import com.ktmmobile.msf.system.common.util.StringMakerUtil;

@Service
public class MsfPrvCommDataServiceImpl implements MsfPrvCommDataService{

    private static final Logger logger = LoggerFactory.getLogger(MsfPrvCommDataServiceImpl.class);

    @Value("${SERVER_NAME}")
    private String serverName;
    
    @Value("${api.interface.server}")
    private String apiInterfaceServer;

	@Override
	public List<MspCommDatPrvTxnDto> pvcCommDataList(MspCommDatPrvTxnDto mspCommDatPrvTxnDto) {
        RestTemplate restTemplate = new RestTemplate();
        MspCommDatPrvTxnDto[] resultList = restTemplate.postForObject(apiInterfaceServer + "/mypage/pvcCommDataList", mspCommDatPrvTxnDto, MspCommDatPrvTxnDto[].class);

        List<MspCommDatPrvTxnDto> list = null;
        if(Optional.ofNullable(resultList).isPresent() && resultList.length != 0) {
            list = Arrays.asList(resultList);
            
            for (MspCommDatPrvTxnDto dto : list) {
            	if ("Y".equals(dto.getIsInvstProc())) {
                	dto.setIsInvstProcNm("제공사실 있음");
            	} else if ("N".equals(dto.getIsInvstProc())) {
                	dto.setIsInvstProcNm("제공사실 없음");
            	} else {
                	dto.setIsInvstProcNm("-");
            	}
            }
        }
		return list;
	}

	@Override
	public int insertPrvCommData(MspCommDatPrvTxnDto mspCommDatPrvTxnDto) {
        RestTemplate restTemplate = new RestTemplate();

        int resultCnt = restTemplate.postForObject(apiInterfaceServer + "/mypage/insertPrvCommData", mspCommDatPrvTxnDto, Integer.class);

        return resultCnt;
	}

	@Override
	public List<MspCommDatPrvTxnDto> pvcCommDataDtlList(MspCommDatPrvTxnDto mspCommDatPrvTxnDto) {
        RestTemplate restTemplate = new RestTemplate();
        MspCommDatPrvTxnDto[] resultDtlList = restTemplate.postForObject(apiInterfaceServer + "/mypage/pvcCommDataDtlList", mspCommDatPrvTxnDto, MspCommDatPrvTxnDto[].class);

        List<MspCommDatPrvTxnDto> list = null;
        if(Optional.ofNullable(resultDtlList).isPresent() && resultDtlList.length != 0) {
            list = Arrays.asList(resultDtlList);
            
            for (MspCommDatPrvTxnDto dto : list) {
            	dto.setBthday(dto.getBthday().substring(0,4) + "." + dto.getBthday().substring(4,6) + "." + dto.getBthday().substring(6,8));
            	dto.setTgtSvcNo(dto.getTgtSvcNo().substring(0,3) + "-" + dto.getTgtSvcNo().substring(3,7) + "-" + dto.getTgtSvcNo().substring(7,11));
            	dto.setProcDt(dto.getProcDt().substring(0,4) + "." + dto.getProcDt().substring(4,6) + "." + dto.getProcDt().substring(6,8));
            	dto.setSubscriberNo(dto.getSubscriberNo().substring(0,3) + "-" + dto.getSubscriberNo().substring(3,7) + "-" + dto.getSubscriberNo().substring(7,11));
            	dto.setUserSsn(dto.getUserSsn().substring(0,6) + "-" + dto.getUserSsn().substring(6,7) + "******");
            	dto.setOpenDt(dto.getOpenDt().substring(0,4) + "." + dto.getOpenDt().substring(4,6) + "." + dto.getOpenDt().substring(6,8));
            	if ("".equals(dto.getTmntDt()) && dto.getTmntDt() != null) {
                	dto.setTmntDt(dto.getTmntDt().substring(0,4) + "." + dto.getTmntDt().substring(4,6) + "." + dto.getTmntDt().substring(6,8));
            	}
            	
            	// 이름, 연락처, 주소 마스킹 처리 추가 2022.10.05
             	dto.setApyNm(StringMakerUtil.getName(dto.getApyNm()));
            	dto.setTgtSvcNo(StringMakerUtil.getPhoneNum(dto.getTgtSvcNo()));
            	dto.setCntcTelNo(StringMakerUtil.getPhoneNum(dto.getCntcTelNo()));
            	dto.setCstmrNm(StringMakerUtil.getName(dto.getCstmrNm()));
            	dto.setSubscriberNo(StringMakerUtil.getPhoneNum(dto.getSubscriberNo()));
            	dto.setCstmrAddr(StringMakerUtil.getAddress(dto.getCstmrAddr()));
            
            }
        }
		return list;
	}
}
