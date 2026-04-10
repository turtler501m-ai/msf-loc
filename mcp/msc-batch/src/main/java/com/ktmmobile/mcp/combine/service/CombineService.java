package com.ktmmobile.mcp.combine.service;

import com.ktmmobile.mcp.combine.dao.CombineDao;
import com.ktmmobile.mcp.combine.dto.MspCombineDto;
import com.ktmmobile.mcp.combine.dto.ReqCombineDto;
import com.ktmmobile.mcp.log.dto.BathHistDto;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;

@Service
public class CombineService {
    private static final Logger logger = LoggerFactory.getLogger(CombineService.class);

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    private final CombineDao combineDao;
    public CombineService(CombineDao combineDao) {
        this.combineDao = combineDao;
    }

    public BathHistDto syncCombineSoloRequest(int baseDate) {
        List<MspCombineDto> syncCombineSoloList = this.getSyncCombineSoloListWithMspInfo(baseDate);
        if (syncCombineSoloList == null || syncCombineSoloList.isEmpty()) {
            BathHistDto bathHistDto = new BathHistDto();
            bathHistDto.setSuccessCascnt(0);
            bathHistDto.setFailCascnt(0);
            return bathHistDto;
        }

        int successCount = 0;
        int failCount = 0;
        ReqCombineDto masterCombineLineInfo = combineDao.getMasterCombineLineInfo();
        for (MspCombineDto combineDto : syncCombineSoloList) {
            try {
                this.saveCombineSoloRequest(combineDto, masterCombineLineInfo);
                successCount++;
            } catch (Exception e) {
                logger.error(e.getMessage());
                logger.error(e.getStackTrace()[0].toString());
                failCount++;
            }
        }

        BathHistDto bathHistDto = new BathHistDto();
        bathHistDto.setSuccessCascnt(successCount);
        bathHistDto.setFailCascnt(failCount);
        return bathHistDto;
    }

    private void saveCombineSoloRequest(MspCombineDto combineDto, ReqCombineDto masterCombineLineInfo) {
        ReqCombineDto insertDto = buildCombineSoloInsert(combineDto, masterCombineLineInfo);
        combineDao.insertReqCombine(insertDto);
    }

    private static ReqCombineDto buildCombineSoloInsert(MspCombineDto combineDto, ReqCombineDto masterCombineLineInfo) {
        ReqCombineDto insertDto = new ReqCombineDto();
        insertDto.setCombTypeCd("04");
        insertDto.setmCtn(combineDto.getSubscriberNo());
        insertDto.setmCustNm(combineDto.getSubLinkName());
        insertDto.setmCustBirth(combineDto.getBirth());
        insertDto.setmSexCd(getSexCd(combineDto.getGender()));
        insertDto.setmSvcCntrNo(combineDto.getContractNum());
        insertDto.setmRateCd(combineDto.getFstRateCd());
        insertDto.setmRateNm(combineDto.getFstRateNm());
        insertDto.setmRateAdsvcCd(combineDto.getrRateCd());
        insertDto.setmRateAdsvcNm(combineDto.getrRateNm());
        insertDto.setCombSvcNo(masterCombineLineInfo.getCombSvcNo());
        insertDto.setCombSvcCntrNo(masterCombineLineInfo.getCombSvcCntrNo());
        insertDto.setCombCustNm("주식회사 케이티엠모바일");
        insertDto.setCombSexCd("03");
        insertDto.setCombSocCd("PL249Q804");
        insertDto.setCombSocNm("MVNO마스터결합전용 더미요금제 (엠모바일)");
        insertDto.setCombRateAdsvcCd("EMPTY");
        insertDto.setCombRateAdsvcNm("데이터 제공 없음");
        insertDto.setCombTgtTypeCd("03");
        insertDto.setRsltCd("S");
        insertDto.setSysDt(combineDto.getLstComActvDate());
        insertDto.setRequestKey(combineDto.getRequestKey());
        return insertDto;
    }

    private List<MspCombineDto> getSyncCombineSoloListWithMspInfo(int baseDate) {
        RestTemplate restTemplate = new RestTemplate();
        String uri = UriComponentsBuilder.fromUriString(apiInterfaceServer + "/msp/getSyncCombineSoloListWithMspInfo")
                .queryParam("baseDate", baseDate)
                .toUriString();
        MspCombineDto[] combineArray = restTemplate.getForObject(uri, MspCombineDto[].class);
        return combineArray == null ? null : Arrays.asList(combineArray);
    }

    private static String getSexCd(String gender) {
        if (StringUtils.isEmpty(gender)) {
            return "";
        }

        return (Integer.parseInt(gender) % 2 == 0) ? "02" : "01";
    }
}
