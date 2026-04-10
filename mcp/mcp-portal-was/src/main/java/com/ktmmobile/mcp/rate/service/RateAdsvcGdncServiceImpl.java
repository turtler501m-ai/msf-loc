package com.ktmmobile.mcp.rate.service;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.ktmmobile.mcp.common.util.XmlBindUtil;
import com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto;
import com.ktmmobile.mcp.rate.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.ktmmobile.mcp.common.constants.Constants;
import com.ktmmobile.mcp.common.dto.JsonReturnDto;
import com.ktmmobile.mcp.common.dto.db.NmcpCdDtlDto;
import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
import com.ktmmobile.mcp.common.mplatform.vo.MpAddSvcInfoParamDto;
import com.ktmmobile.mcp.common.mplatform.vo.MpSocVO;
import com.ktmmobile.mcp.common.util.DateTimeUtil;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import com.ktmmobile.mcp.common.util.StringUtil;
import com.ktmmobile.mcp.coupon.dto.McpUserCertDto;
import com.ktmmobile.mcp.mypage.service.RegSvcService;
import com.ktmmobile.mcp.rate.dao.RateAdsvcGdncDao;
import com.ktmmobile.mcp.requestReview.dto.RequestReviewDto;

import jakarta.xml.bind.JAXBException;

import static com.ktmmobile.mcp.common.constants.Constants.REG_SVC_CD_5;
import static com.ktmmobile.mcp.rate.RateAdsvcGdncUtil.getRateAdsvcGdncPath;

@Service
public class RateAdsvcGdncServiceImpl implements RateAdsvcGdncService {

    private static Logger logger = LoggerFactory.getLogger(RateAdsvcGdncServiceImpl.class);

    @Autowired
    RateAdsvcGdncDao rateAdsvcGdncDao;

    @Autowired
    private RegSvcService regSvcService;

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    /** 파일업로드 경로*/
    @Value("${common.fileupload.base}")
    private String fileuploadBase;

    /** 파일업로드 접근가능한 웹경로 */
    @Value("${common.fileupload.web}")
    private String fileuploadWeb;

    @Value("${SERVER_NAME}")
    private String serverName;

    /**
     * 설명 : 카테고리 xml 조회
     * @Author : 강채신
     * @Date : 2021.12.30
     * @return
     */
    @Override
    public MapWrapper getMapWrapper() {
        String fPer = File.separator;
        String realDir = getRateAdsvcGdncPath(fileuploadBase, serverName);
        MapWrapper mapWrapper = null;
        String realFilePath = realDir + fPer + "NMCP_RATE_ADSVC_CTG_BAS.xml";

        try {
            mapWrapper = XmlBindUtil.bindFromXml(MapWrapper.class, realFilePath);
        } catch(JAXBException e) {
            logger.error(e.toString());
        } catch(Exception e) {
            logger.error(e.toString());
        }

        return mapWrapper;
    }

    /**
     * 설명 : 2Depth 의 상품목록 갯수 조회
     * @Author : 강채신
     * @Date : 2021.12.30
     * @param rateAdsvcCtgBasDTO
     * @return
     */
    @Override
    public int getProdCount(RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO) {
        int prodCount = 0;
        MapWrapper mapWrapper = getMapWrapper();

        Map<String, ListXmlWrapper> ctgMapXml = mapWrapper.getItem();
        ListXmlWrapper ctgListXml = ctgMapXml.get("ctgList");
        List<RateAdsvcGdncProdXML> ctgList  = ctgListXml.getItem();

        if(!StringUtils.isEmpty(rateAdsvcCtgBasDTO.getRateAdsvcCtgCd())) {
            for(RateAdsvcGdncProdXML item : ctgList) {
                int depthKey = item.getDepthKey();
                String upRateAdsvcCtgCd = item.getUpRateAdsvcCtgCd();

                try {
                    if("RATE".equals(item.getRateAdsvcDivCd()) && depthKey == 2
                            && (rateAdsvcCtgBasDTO.getRateAdsvcCtgCd()).equals(upRateAdsvcCtgCd)
                            && DateTimeUtil.isMiddleDateTime(item.getPstngStartDate(), item.getPstngEndDate())
                            ) {
                        prodCount += item.getRelCnt();
                    }
                } catch (ParseException e) {
                    logger.error("Exception e : {}", e.getMessage());
                }
            }
        }

        return prodCount;
    }

    /**
     * 설명 : 카테고리 xml 의 ctgList 조회
     * @Author : 강채신
     * @Date : 2021.12.30
     * @param rateAdsvcCtgBasDTO
     * @return
     */
    @Override
    public List<RateAdsvcCtgBasDTO> getCtgXmlList(RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO) {
        return this.getCtgXmlList(rateAdsvcCtgBasDTO, getMapWrapper());
    }

    @Override
    public List<RateAdsvcCtgBasDTO> getCtgXmlList(RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO, MapWrapper mapWrapper) {
        List<RateAdsvcCtgBasDTO> rtnList = new ArrayList<RateAdsvcCtgBasDTO>();

        Map<String, ListXmlWrapper> ctgMapXml = mapWrapper.getItem();
        ListXmlWrapper ctgListXml = ctgMapXml.get("ctgList");
        List<RateAdsvcGdncProdXML> ctgList = ctgListXml.getItem();

        for(RateAdsvcGdncProdXML item : ctgList) {
            int depthKey = item.getDepthKey();
            String rateAdsvcDivCd = item.getRateAdsvcDivCd();
            String upRateAdsvcDivCd = item.getUpRateAdsvcCtgCd();

            try {
                if(rateAdsvcDivCd.equals(rateAdsvcCtgBasDTO.getRateAdsvcDivCd())
                        && depthKey == rateAdsvcCtgBasDTO.getDepthKey()
                        && upRateAdsvcDivCd.equals(rateAdsvcCtgBasDTO.getRateAdsvcCtgCd())
                        && DateTimeUtil.isMiddleDateTime(item.getPstngStartDate(), item.getPstngEndDate())
                        ) {

                    RateAdsvcCtgBasDTO rateAdsvcCtgBas = new RateAdsvcCtgBasDTO();

                    rateAdsvcCtgBas.setRateAdsvcCtgCd(item.getRateAdsvcCtgCd());
                    rateAdsvcCtgBas.setSortOdrg(item.getSortOdrg());
                    rateAdsvcCtgBas.setUseYn(item.getUseYn());
                    rateAdsvcCtgBas.setPstngStartDate(item.getPstngStartDate());
                    rateAdsvcCtgBas.setPstngEndDate(item.getPstngEndDate());
                    rateAdsvcCtgBas.setRateAdsvcCtgNm(item.getRateAdsvcCtgNm());
                    rateAdsvcCtgBas.setRateAdsvcCtgBasDesc(item.getRateAdsvcCtgBasDesc());
                    rateAdsvcCtgBas.setRateAdsvcCtgDtlDesc(item.getRateAdsvcCtgDtlDesc());
                    rateAdsvcCtgBas.setRateAdsvcCtgImgNm(item.getRateAdsvcCtgImgNm());
                    rateAdsvcCtgBas.setDepthKey(item.getDepthKey());
                    rateAdsvcCtgBas.setRateAdsvcDivCd(item.getRateAdsvcDivCd());
                    rateAdsvcCtgBas.setRelCnt(item.getRelCnt());

                    rtnList.add(rateAdsvcCtgBas);
                }
            } catch (ParseException e) {
                logger.error("Exception e : {}", e.getMessage());
            }
        }

        return rtnList;
    }


    @Override
    public List<RateAdsvcGdncProdXML> getCtgXmlAllList(RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO) {
        return this.getCtgXmlAllList(rateAdsvcCtgBasDTO, getMapWrapper());
    }

    @Override
    public List<RateAdsvcGdncProdXML> getCtgXmlAllList(RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO, MapWrapper mapWrapper) {
        List<RateAdsvcGdncProdXML> rtnList = new ArrayList<RateAdsvcGdncProdXML>();

        Map<String, ListXmlWrapper> ctgMapXml = mapWrapper.getItem();
        ListXmlWrapper ctgListXml = ctgMapXml.get("ctgList");
        List<RateAdsvcGdncProdXML> ctgList = ctgListXml.getItem();

        for(RateAdsvcGdncProdXML item : ctgList) {
            String rateAdsvcDivCd = item.getRateAdsvcDivCd();

            try {
                if(rateAdsvcDivCd.equals(rateAdsvcCtgBasDTO.getRateAdsvcDivCd())
                        && DateTimeUtil.isMiddleDateTime(item.getPstngStartDate(), item.getPstngEndDate())
                ) {

                    rtnList.add(item);
                }
            } catch (ParseException e) {
                logger.error("Exception e : {}", e.getMessage());
            }
        }

        return rtnList;
    }

    /**
     * 설명 : 카테고리 xml 의 proList 조회
     * @Author : 강채신
     * @Date : 2021.12.30
     * @param rateAdsvcCtgBasDTO
     * @return
     */
    @Override
    public List<RateAdsvcCtgBasDTO> getProdXmlList(RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO) {
        return this.getProdXmlList(rateAdsvcCtgBasDTO, getMapWrapper());
    }

    @Override
    public List<RateAdsvcCtgBasDTO> getProdXmlList(RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO, MapWrapper mapWrapper) {
        List<RateAdsvcCtgBasDTO> rtnList = new ArrayList<RateAdsvcCtgBasDTO>();

        Map<String, ListXmlWrapper> prodMapXml = mapWrapper.getItem();
        ListXmlWrapper prodListXml = prodMapXml.get("proList");
        List<RateAdsvcGdncProdXML> prodList = prodListXml.getItem();

        for(RateAdsvcGdncProdXML item : prodList) {
            String rateAdsvcCtgCd = item.getRateAdsvcCtgCd();

            try {
                if(rateAdsvcCtgCd.equals(rateAdsvcCtgBasDTO.getRateAdsvcCtgCd())
                        && DateTimeUtil.isMiddleDateTime(item.getPstngStartDate(), item.getPstngEndDate())
                        ) {

                    if("Y".equals(rateAdsvcCtgBasDTO.getFreeYn())) {
                        if(!"Y".equals(item.getFreeYn()))
                            continue;
                    }

                    if("Y".equals(rateAdsvcCtgBasDTO.getSelfYn())) {
                        if(!"Y".equals(item.getSelfYn()))
                            continue;
                    }

                    RateAdsvcCtgBasDTO rateAdsvcCtgBas = new RateAdsvcCtgBasDTO();

                    rateAdsvcCtgBas.setRateAdsvcCtgCd(item.getRateAdsvcCtgCd());
                    rateAdsvcCtgBas.setRateAdsvcGdncSeq(item.getRateAdsvcGdncSeq());
                    rateAdsvcCtgBas.setSortOdrg(item.getSortOdrg());
                    rateAdsvcCtgBas.setUseYn(item.getUseYn());
                    rateAdsvcCtgBas.setRateAdsvcImgNm(item.getRateAdsvcImgNm());
                    rateAdsvcCtgBas.setRateAdsvcCtgImgNm(item.getRateAdsvcCtgImgNm());
                    rateAdsvcCtgBas.setPstngStartDate(item.getPstngStartDate());
                    rateAdsvcCtgBas.setPstngEndDate(item.getPstngEndDate());
                    rateAdsvcCtgBas.setRateAdsvcCtgNm(item.getRateAdsvcCtgNm());
                    rateAdsvcCtgBas.setRateAdsvcCtgBasDesc(item.getRateAdsvcCtgBasDesc());
                    rateAdsvcCtgBas.setRateAdsvcCtgDtlDesc(item.getRateAdsvcCtgDtlDesc());
                    rateAdsvcCtgBas.setDepthKey(item.getDepthKey());
                    rateAdsvcCtgBas.setUpRateAdsvcCtgCd(item.getUpRateAdsvcCtgCd());
                    rateAdsvcCtgBas.setRateAdsvcNm(item.getRateAdsvcNm());
                    rateAdsvcCtgBas.setMmBasAmtDesc(item.getMmBasAmtDesc());
                    rateAdsvcCtgBas.setMmBasAmtVatDesc(item.getMmBasAmtVatDesc());
                    rateAdsvcCtgBas.setPromotionAmtDesc(item.getPromotionAmtDesc());
                    rateAdsvcCtgBas.setPromotionAmtVatDesc(item.getPromotionAmtVatDesc());
                    rateAdsvcCtgBas.setRelCnt(item.getRelCnt());
                    rateAdsvcCtgBas.setAddDivCd(item.getAddDivCd());
                    rateAdsvcCtgBas.setSelfYn(item.getSelfYn());
                    rateAdsvcCtgBas.setFreeYn(item.getFreeYn());
                    rateAdsvcCtgBas.setDateType(item.getDateType());
                    rateAdsvcCtgBas.setUsePrd(item.getUsePrd());
                    rateAdsvcCtgBas.setLineType(item.getLineType());
                    rateAdsvcCtgBas.setLineCnt(item.getLineCnt());
                    rateAdsvcCtgBas.setMtCd(item.getMtCd());
                    rateAdsvcCtgBas.setRateAdsvcBasDesc(item.getRateAdsvcBasDesc());

                    rtnList.add(rateAdsvcCtgBas);
                }
            } catch (ParseException e) {
                logger.error("Exception e : {}", e.getMessage());
            }
        }

        return rtnList;
    }

    /**
     * 설명 : prodList 중복 제거 및 카테고리명 조회
     * @Author : 김동혁
     * @Date : 2023.06.01
     * @param List<RateAdsvcCtgBasDTO> dupProdList
     * @return List<RateAdsvcCtgBasDTO> dedupProdList
     */
    public List<RateAdsvcCtgBasDTO> deduplicateProdList(List<RateAdsvcCtgBasDTO> dupProdList) {
        List<RateAdsvcCtgBasDTO> dedupProdList = new ArrayList<RateAdsvcCtgBasDTO>();

        for(RateAdsvcCtgBasDTO tempRateAdsvcProdBas : dupProdList) {
            int cnt = 0;
            for(RateAdsvcCtgBasDTO rateAdsvcProdBas : dedupProdList) {
                if(tempRateAdsvcProdBas.getRateAdsvcGdncSeq() == rateAdsvcProdBas.getRateAdsvcGdncSeq()) {
                    cnt++;
                    break;
                }
            }
            if(cnt == 0) {
                List<String> ctgNmList = new ArrayList<String>();
                ctgNmList = getCtgNmList(tempRateAdsvcProdBas.getRateAdsvcGdncSeq()); // 해당하는 카테고리명 모두 가져옴
                tempRateAdsvcProdBas.setCtgNmList(ctgNmList);
                dedupProdList.add(tempRateAdsvcProdBas); // 중복되지 않은 상품만 add
            }
        }

        return dedupProdList;
    }

    /**
     * 설명 : 상품이 속한 카테고리명 모두 가져옴
     * @Author : 김동혁
     * @Date : 2023.06.12
     * @param int rateAdsvcGdncSeq
     * @return List<String> ctgNmList
     */
    public List<String> getCtgNmList(int rateAdsvcGdncSeq) {
        MapWrapper mapWrapper = getMapWrapper();

        Map<String, ListXmlWrapper> ctgMapXml = mapWrapper.getItem();
        ListXmlWrapper proListXml = ctgMapXml.get("proList");
        List<RateAdsvcGdncProdXML> proList  = proListXml.getItem();

        List<String> ctgNmList = new ArrayList<String>();

        for(RateAdsvcGdncProdXML rateAdsvcProdBas : proList) {
            if(rateAdsvcGdncSeq == rateAdsvcProdBas.getRateAdsvcGdncSeq()
                    && !rateAdsvcProdBas.getRateAdsvcCtgNm().startsWith("Top")) {
                ctgNmList.add(rateAdsvcProdBas.getRateAdsvcCtgNm());
            }
        }

        return ctgNmList;
    }

    /**
     * 설명 : rateAdsvcGdncSeq로 해당하는 상품 정보 찾아옴
     * @Author : 김동혁
     * @Date : 2023.06.13
     * @param RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO
     * @return RateAdsvcCtgBasDTO
     */
    @Override
    public RateAdsvcCtgBasDTO getProdBySeq(RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO) {
        MapWrapper mapWrapper = getMapWrapper(); // service 단으로 변경 필요함

        Map<String, ListXmlWrapper> prodMapXml = mapWrapper.getItem();
        ListXmlWrapper prodListXml = prodMapXml.get("proList");
        List<RateAdsvcGdncProdXML> prodList = prodListXml.getItem();

        RateAdsvcCtgBasDTO rateAdsvcCtgBas = new RateAdsvcCtgBasDTO();

        for(RateAdsvcGdncProdXML item : prodList) {
            int rateAdsvcGdncSeq = item.getRateAdsvcGdncSeq();

            try {
                if(rateAdsvcGdncSeq == rateAdsvcCtgBasDTO.getRateAdsvcGdncSeq()
                        && DateTimeUtil.isMiddleDateTime(item.getPstngStartDate(), item.getPstngEndDate())
                ) {
                    rateAdsvcCtgBas.setRateAdsvcCtgCd(item.getRateAdsvcCtgCd());
                    rateAdsvcCtgBas.setRateAdsvcGdncSeq(item.getRateAdsvcGdncSeq());
                    rateAdsvcCtgBas.setSortOdrg(item.getSortOdrg());
                    rateAdsvcCtgBas.setUseYn(item.getUseYn());
                    rateAdsvcCtgBas.setRateAdsvcImgNm(item.getRateAdsvcImgNm());
                    rateAdsvcCtgBas.setRateAdsvcCtgImgNm(item.getRateAdsvcCtgImgNm());
                    rateAdsvcCtgBas.setPstngStartDate(item.getPstngStartDate());
                    rateAdsvcCtgBas.setPstngEndDate(item.getPstngEndDate());
                    rateAdsvcCtgBas.setRateAdsvcCtgNm(item.getRateAdsvcCtgNm());
                    rateAdsvcCtgBas.setRateAdsvcCtgBasDesc(item.getRateAdsvcCtgBasDesc());
                    rateAdsvcCtgBas.setRateAdsvcCtgDtlDesc(item.getRateAdsvcCtgDtlDesc());
                    rateAdsvcCtgBas.setDepthKey(item.getDepthKey());
                    rateAdsvcCtgBas.setUpRateAdsvcCtgCd(item.getUpRateAdsvcCtgCd());
                    rateAdsvcCtgBas.setRateAdsvcNm(item.getRateAdsvcNm());
                    rateAdsvcCtgBas.setMmBasAmtDesc(item.getMmBasAmtDesc());
                    rateAdsvcCtgBas.setMmBasAmtVatDesc(item.getMmBasAmtVatDesc());
                    rateAdsvcCtgBas.setPromotionAmtDesc(item.getPromotionAmtDesc());
                    rateAdsvcCtgBas.setPromotionAmtVatDesc(item.getPromotionAmtVatDesc());
                    rateAdsvcCtgBas.setRelCnt(item.getRelCnt());
                    rateAdsvcCtgBas.setAddDivCd(item.getAddDivCd());
                    rateAdsvcCtgBas.setSelfYn(item.getSelfYn());
                    rateAdsvcCtgBas.setFreeYn(item.getFreeYn());
                    rateAdsvcCtgBas.setUsePrd(item.getUsePrd());
                    rateAdsvcCtgBas.setDateType(item.getDateType());
                    rateAdsvcCtgBas.setLineType(item.getLineType());
                    rateAdsvcCtgBas.setLineCnt(item.getLineCnt());
                    rateAdsvcCtgBas.setMtCd(item.getMtCd());

                    return rateAdsvcCtgBas;
                }
            } catch (ParseException e) {
                logger.error("Exception e : {}", e.getMessage());
            }
        }

        return rateAdsvcCtgBas;
    }

    /**
     * 설명 : rateAdsvcGdncSeq로 해당하는 상품 관계 정보 찾아옴
     * @Author : 김동혁
     * @Date : 2023.06.13
     * @param RateAdsvcCtgBasDTO
     * @return RateAdsvcGdncProdRelXML
     */
    @Override
    public RateAdsvcGdncProdRelXML getRateAdsvcGdncProdRel(RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO) {
        List<RateAdsvcGdncProdRelXML> rateAdsvcGdncProdRelList= getRateAdsvcGdncProdRelXml();

        RateAdsvcGdncProdRelXML rateAdsvcGdncProdRel = new RateAdsvcGdncProdRelXML();

        for(RateAdsvcGdncProdRelXML item : rateAdsvcGdncProdRelList) {
            int rateAdsvcGdncSeq = item.getRateAdsvcGdncSeq();

            try {
                if(rateAdsvcGdncSeq == rateAdsvcCtgBasDTO.getRateAdsvcGdncSeq()
                        && DateTimeUtil.isMiddleDateTime(item.getPstngStartDate(), item.getPstngEndDate())
                ) {
                    rateAdsvcGdncProdRel.setRateAdsvcProdRelSeq(item.getRateAdsvcProdRelSeq());
                    rateAdsvcGdncProdRel.setRateAdsvcGdncSeq(item.getRateAdsvcGdncSeq());
                    rateAdsvcGdncProdRel.setRateAdsvcCd(item.getRateAdsvcCd());
                    rateAdsvcGdncProdRel.setRateAdsvcNm(item.getRateAdsvcNm());
                    rateAdsvcGdncProdRel.setUseYn(item.getUseYn());
                    rateAdsvcGdncProdRel.setPstngStartDate(item.getPstngStartDate());
                    rateAdsvcGdncProdRel.setPstngEndDate(item.getPstngEndDate());

                    return rateAdsvcGdncProdRel;
                }
            } catch (ParseException e) {
                logger.error("Exception e : {}", e.getMessage());
            }
        }

        return rateAdsvcGdncProdRel;
    }

    @Override
    public List<McpUserCertDto> getMcpUserCntrInfoA(McpUserCertDto mcpUserCertDto) {

        RestTemplate restTemplate = new RestTemplate();
        McpUserCertDto[] resultList = restTemplate.postForObject(apiInterfaceServer + "/usermanage/getMcpUserCntrInfoA", mcpUserCertDto, McpUserCertDto[].class);

        List<McpUserCertDto> returnDtoList = null;
        if(Optional.ofNullable(resultList).isPresent() && 0 != resultList.length) {
            returnDtoList = Arrays.asList(resultList);
        }

        return returnDtoList;
    }

    /**
     * 설명 : 요금제부가서비스안내기본 xml 조회
     * @Author : 강채신
     * @Date : 2021.12.30
     * @param rateAdsvcCtgBasDTO
     * @return
     */
    @Override
    public RateAdsvcGdncBasXML getRateAdsvcGdncBasXml(RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO) {
        RateAdsvcGdncBasXML xmlInfo = null;
        try {
            String fPer = File.separator;
            String realDir = getRateAdsvcGdncPath(fileuploadBase, serverName);
            String realFilePath = realDir + fPer + "NMCP_RATE_ADSVC_GDNC_BAS_" + rateAdsvcCtgBasDTO.getRateAdsvcGdncSeq() + ".xml";

            File file = new File(realFilePath);
            if(file.exists()) {
                xmlInfo = XmlBindUtil.bindFromXml(RateAdsvcGdncBasXML.class, file);
            }
        }catch(JAXBException e) {
            logger.error(e.toString());
        }  catch(Exception e) {
            logger.error(e.toString());
        }

        return xmlInfo;
    }

    /**
     * 설명 : 요금제부가서비스안내상세 xml 조회
     * @Author : 강채신
     * @Date : 2021.12.30
     * @param rateAdsvcCtgBasDTO
     * @return
     */
    @Override
    public List<RateAdsvcGdncDtlXML> getRateAdsvcGdncDtlXmlList(RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO) {
        List<RateAdsvcGdncDtlXML> rtnXmlList = null;
        try {
            String fPer = File.separator;
            String realDir = getRateAdsvcGdncPath(fileuploadBase, serverName);
            String realFilePath = realDir + fPer + "NMCP_RATE_ADSVC_GDNC_DTL_" + rateAdsvcCtgBasDTO.getRateAdsvcGdncSeq() + ".xml";

            File file = new File(realFilePath);
            if(file.exists()) {
                rtnXmlList = XmlBindUtil.bindListFromXml(RateAdsvcGdncDtlXML.class, file);

                for(Iterator<RateAdsvcGdncDtlXML> iter = rtnXmlList.iterator(); iter.hasNext();) {
                    RateAdsvcGdncDtlXML xmlVo = iter.next();
                    if(!DateTimeUtil.isMiddleDateTime(xmlVo.getPstngStartDate(), xmlVo.getPstngEndDate())) {
                        iter.remove();
                    }
                }
            }
        } catch(JAXBException e) {
            logger.error(e.toString());
        } catch(Exception e) {
            logger.error(e.toString());
        }

        return rtnXmlList;
    }

    /**
     * 설명 : 요금제부가서비스혜택안내상세 xml 조회
     * @Author : 강채신
     * @Date : 2021.12.30
     * @param rateAdsvcCtgBasDTO
     * @return
     */
    @Override
    public List<RateAdsvcBnfitGdncDtlXML> getRateAdsvcBnfitGdncDtlXmlList(RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO) {
        List<RateAdsvcBnfitGdncDtlXML> rtnXmlList = null;
        try {
            String fPer = File.separator;
            String realDir = getRateAdsvcGdncPath(fileuploadBase, serverName);
            String realFilePath = realDir + fPer + "NMCP_RATE_ADSVC_BNFIT_GDNC_DTL_" + rateAdsvcCtgBasDTO.getRateAdsvcGdncSeq() + ".xml";

            File file = new File(realFilePath);
            if(file.exists()) {
                rtnXmlList = XmlBindUtil.bindListFromXml(RateAdsvcBnfitGdncDtlXML.class, file);

                for(Iterator<RateAdsvcBnfitGdncDtlXML> iter = rtnXmlList.iterator(); iter.hasNext();) {
                    RateAdsvcBnfitGdncDtlXML xmlVo = iter.next();
                    if(!DateTimeUtil.isMiddleDateTime(xmlVo.getPstngStartDate(), xmlVo.getPstngEndDate())) {
                        iter.remove();
                    }
                }
            }

        } catch(JAXBException e) {
            logger.error(e.toString());
        } catch(Exception e) {
            logger.error(e.toString());
        }

        return rtnXmlList;
    }

    /**
     * 설명 : 요금제부가서비스혜택안내상세 xml 조회
     * @Author : 강채신
     * @Date : 2021.12.30
     * @param rateAdsvcCtgBasDTO
     * @return
     */
    @Override
    public List<RateAdsvcGdncLinkDtlXML> getRateAdsvcGdncLinkDtlXmlList(RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO) {
        List<RateAdsvcGdncLinkDtlXML> rtnXmlList = null;
        try {
            String fPer = File.separator;
            String realDir = getRateAdsvcGdncPath(fileuploadBase, serverName);
            String realFilePath = realDir + fPer + "NMCP_RATE_ADSVC_GDNC_LINK_DTL_" + rateAdsvcCtgBasDTO.getRateAdsvcGdncSeq() + ".xml";

            File file = new File(realFilePath);
            if(file.exists()) {
                rtnXmlList = XmlBindUtil.bindListFromXml(RateAdsvcGdncLinkDtlXML.class, file);

                for(Iterator<RateAdsvcGdncLinkDtlXML> iter = rtnXmlList.iterator(); iter.hasNext();) {
                    RateAdsvcGdncLinkDtlXML xmlVo = iter.next();
                    if(!DateTimeUtil.isMiddleDateTime(xmlVo.getPstngStartDate(), xmlVo.getPstngEndDate())) {
                        iter.remove();
                    }
                }
            }
        } catch(JAXBException e) {
            logger.error(e.toString());
        } catch(Exception e) {
            logger.error(e.toString());
        }

        return rtnXmlList;
    }

    /**
     * 설명 : 요금제부가서비스안내상품관계 xml 조회
     * @Author : 강채신
     * @Date : 2021.12.30
     * @return
     */
    @Override
    public List<RateAdsvcGdncProdRelXML> getRateAdsvcGdncProdRelXml() {
        List<RateAdsvcGdncProdRelXML> rtnXmlList = null;
        try {
            String fPer = File.separator;
            String realDir = getRateAdsvcGdncPath(fileuploadBase, serverName);
            String realFilePath = realDir + fPer + "NMCP_RATE_ADSVC_GDNC_PROD_REL.xml";

            File file = new File(realFilePath);
            if(file.exists()) {
                rtnXmlList = XmlBindUtil.bindListFromXml(RateAdsvcGdncProdRelXML.class, file);

                for(Iterator<RateAdsvcGdncProdRelXML> iter = rtnXmlList.iterator(); iter.hasNext();) {
                    RateAdsvcGdncProdRelXML xmlVo = iter.next();
                    if(!DateTimeUtil.isMiddleDateTime(xmlVo.getPstngStartDate(), xmlVo.getPstngEndDate())) {
                        iter.remove();
                    }
                }
            }

        } catch(JAXBException e) {
            logger.error(e.toString());
        }catch(Exception e) {
            logger.error(e.toString());
        }

        return rtnXmlList;
    }


    @Override
    public List<RateAdsvcGdncDtlXML> getRateAdsvcGdncDtlXml(String rateAdsvcCd) {

        List<RateAdsvcGdncDtlXML> rtnXmlList = null;
        try {
            String fPer = File.separator;
            String realDir = getRateAdsvcGdncPath(fileuploadBase, serverName);
            String realFilePath = realDir + fPer + "NMCP_RATE_ADSVC_GDNC_PROD_REL.xml";
            int rateAdsvcGdncSeq = 0;

            File file = new File(realFilePath);
            if(file.exists()) {
                List<RateAdsvcGdncProdRelXML> searchXmlList = XmlBindUtil.bindListFromXml(RateAdsvcGdncProdRelXML.class, file);

                for(Iterator<RateAdsvcGdncProdRelXML> iter = searchXmlList.iterator(); iter.hasNext();) {
                    RateAdsvcGdncProdRelXML xmlVo = iter.next();
                    if(rateAdsvcCd.equals(xmlVo.getRateAdsvcCd()) &&  DateTimeUtil.isMiddleDateTime(xmlVo.getPstngStartDate(), xmlVo.getPstngEndDate()) ) {
                        rateAdsvcGdncSeq = xmlVo.getRateAdsvcGdncSeq();
                        break;
                    }
                }
            }

            if (rateAdsvcGdncSeq > 0) {
                realFilePath = realDir + fPer + "NMCP_RATE_ADSVC_GDNC_DTL_" + rateAdsvcGdncSeq + ".xml";
                File file2 = new File(realFilePath);

                if(file2.exists()) {
                    rtnXmlList = XmlBindUtil.bindListFromXml(RateAdsvcGdncDtlXML.class, file2);

                    for(Iterator<RateAdsvcGdncDtlXML> iter = rtnXmlList.iterator(); iter.hasNext();) {
                        RateAdsvcGdncDtlXML xmlVo = iter.next();
                        if(!DateTimeUtil.isMiddleDateTime(xmlVo.getPstngStartDate(), xmlVo.getPstngEndDate())) {
                            iter.remove();
                        }
                    }
                }
            }

        } catch(JAXBException e) {
            logger.error(e.toString());
        } catch(Exception e) {
            logger.error(e.toString());
        }
        return rtnXmlList;
    }


    @Override
    public RateDtlInfo getRateDtlInfo(String rateAdsvcCd) {

        RateDtlInfo rateDtlInfo = new RateDtlInfo();

        try {
            String fPer = File.separator;
            String realDir = getRateAdsvcGdncPath(fileuploadBase, serverName);
            String realFilePath = realDir + fPer + "NMCP_RATE_ADSVC_GDNC_PROD_REL.xml";
            int rateAdsvcGdncSeq = 0;
              String giftPrmtSeqs = null;

            File file = new File(realFilePath);
            if(file.exists()) {
                List<RateAdsvcGdncProdRelXML> searchXmlList = XmlBindUtil.bindListFromXml(RateAdsvcGdncProdRelXML.class, file);

                for(Iterator<RateAdsvcGdncProdRelXML> iter = searchXmlList.iterator(); iter.hasNext();) {
                    RateAdsvcGdncProdRelXML xmlVo = iter.next();
                    if(rateAdsvcCd.equals(xmlVo.getRateAdsvcCd()) &&  DateTimeUtil.isMiddleDateTime(xmlVo.getPstngStartDate(), xmlVo.getPstngEndDate()) ) {
                        rateAdsvcGdncSeq = xmlVo.getRateAdsvcGdncSeq();
                          giftPrmtSeqs = xmlVo.getGiftPrmtSeqs();
                        break;
                    }
                }
            }

            // === START 요금제 혜택요약 ===
            if(!StringUtil.isEmpty(giftPrmtSeqs)){
              List<RateGiftPrmtXML> giftPrmtXmlList = this.getRateGiftPrmtXmlList();
              RateGiftPrmtListDTO rateGiftPrmtListDTO = this.initRateGiftPrmtInfo(giftPrmtXmlList, giftPrmtSeqs, "");
              rateDtlInfo.setRateGiftPrmtListDTO(rateGiftPrmtListDTO);
            }
            // === END 요금제 혜택요약 ===

            if (rateAdsvcGdncSeq > 0) {
                realFilePath = realDir + fPer + "NMCP_RATE_ADSVC_GDNC_BAS_" + rateAdsvcGdncSeq + ".xml";
                File file2 = new File(realFilePath);

                if(file2.exists()) {
                    RateAdsvcGdncBasXML xmlInfo = XmlBindUtil.bindFromXml(RateAdsvcGdncBasXML.class, file2);
                    rateDtlInfo.setRateAdsvcGdncBas(xmlInfo);
                }

                realFilePath = realDir + fPer + "NMCP_RATE_ADSVC_BNFIT_GDNC_DTL_" + rateAdsvcGdncSeq + ".xml";
                File file3 = new File(realFilePath);
                if(file3.exists()) {
                    List<RateAdsvcBnfitGdncDtlXML> rtnXmlList = XmlBindUtil.bindListFromXml(RateAdsvcBnfitGdncDtlXML.class, file3);
                    for(Iterator<RateAdsvcBnfitGdncDtlXML> iter = rtnXmlList.iterator(); iter.hasNext();) {
                        RateAdsvcBnfitGdncDtlXML xmlVo = iter.next();
                        if(!DateTimeUtil.isMiddleDateTime(xmlVo.getPstngStartDate(), xmlVo.getPstngEndDate())) {  iter.remove();           }
                    }

                    // Map을 사용하여 RateAdsvcBnfitGdncDtlXML getRateAdsvcBnfitItemCd 키로 매핑
                    Map<String, RateAdsvcBnfitGdncDtlXML> rateAdsvcBnfitGdncMap =
                            rtnXmlList.stream().collect(Collectors.toMap(
                                    RateAdsvcBnfitGdncDtlXML::getRateAdsvcBnfitItemCd,
                                    rateAdsvcBnfitGdnc -> rateAdsvcBnfitGdnc,
                                    (existing, replacement) -> replacement // 중복 시 대체 값 선택  //기존 값을 유지하고 싶다면 (existing, replacement) -> existing를 사용하면 됩니다
                            ));
                    rateDtlInfo.setRateAdsvcBnfitGdncMap(rateAdsvcBnfitGdncMap);
                }


                realFilePath = realDir + fPer + "NMCP_RATE_ADSVC_GDNC_BANNER_DTL_" + rateAdsvcGdncSeq + ".xml";  //NMCP_RATE_ADSVC_GDNC_BANNER_DTL_303
                File file4 = new File(realFilePath);
                if(file4.exists()) {
                    List<RateGdncBannerDtlDTO> xmlList = XmlBindUtil.bindListFromXml(RateGdncBannerDtlDTO.class, file4);
                    List<RateGdncBannerDtlDTO> rtnXmlList = new ArrayList<RateGdncBannerDtlDTO>(
                            xmlList.stream().filter(item -> item.getBannerSbst() != null && !item.getBannerSbst().isEmpty()).collect(Collectors.toList())
                    );

                    rateDtlInfo.setRateGdncBannerList(rtnXmlList);
                }


                realFilePath = realDir + fPer + "NMCP_RATE_ADSVC_GDNC_PROPERTY_DTL_" + rateAdsvcGdncSeq + ".xml";  //NMCP_RATE_ADSVC_GDNC_PROPERTY_DTL_303
                File file5 = new File(realFilePath);
                if(file5.exists()) {
                    List<RateGdncPropertyDtlDTO> rtnXmlList = XmlBindUtil.bindListFromXml(RateGdncPropertyDtlDTO.class, file5);

                    // Map을 사용하여 RateAdsvcBnfitGdncDtlXML getRateAdsvcBnfitItemCd 키로 매핑
                    Map<String, RateGdncPropertyDtlDTO> rateGdncPropertyMap =
                            rtnXmlList.stream().collect(Collectors.toMap(
                                    RateGdncPropertyDtlDTO::getPropertyCode,
                                    rateGdncProperty -> rateGdncProperty,
                                    (existing, replacement) -> replacement // 중복 시 대체 값 선택  //기존 값을 유지하고 싶다면 (existing, replacement) -> existing를 사용하면 됩니다
                            ));
                    rateDtlInfo.setRateGdncPropertyMap(rateGdncPropertyMap);
                }

                realFilePath = realDir + fPer + "NMCP_RATE_ADSVC_GDNC_EFF_PRICE_DTL_" + rateAdsvcGdncSeq + ".xml";  //NMCP_RATE_ADSVC_GDNC_EFF_PRICE_DTL_303
                File file6 = new File(realFilePath);
                if(file6.exists()) {
                    RateGdncEffPriceDtlDTO rateGdncEffPriceDtl = XmlBindUtil.bindFromXml(RateGdncEffPriceDtlDTO.class, file6);
                    rateDtlInfo.setRateGdncEffPriceDtl(rateGdncEffPriceDtl);

                    //제휴혜택
                    List<NmcpCdDtlDto> jehuPriceReflectList = NmcpServiceUtils.getCodeList(Constants.JEHU_PRICE_REFLECT);
                    //프로모션 혜택
                    List<NmcpCdDtlDto> promoPriceReflectList = NmcpServiceUtils.getCodeList(Constants.PROMO_PRICE_REFLECT);
                    //프로모션 혜택 금액
                    int totalPrice = 0;
                    if(!StringUtil.isEmpty(giftPrmtSeqs)) {
                        totalPrice = rateDtlInfo.getRateGiftPrmtListDTO().getTotalPrice();
                    }

                    //체감가 데이터 설정
                    this.initEffPriceInfo(rateDtlInfo, jehuPriceReflectList, promoPriceReflectList, totalPrice);
                }

            }

        } catch(JAXBException e) {
            logger.error(e.toString());
        } catch(Exception e) {
            logger.error(e.toString());
        }
        return rateDtlInfo;

    }

    /** 설명 : 요금제 코드로 상세 조회 (특정 날짜 기준) */
    @Override
    public RateDtlInfo getRateDtlInfoWithDate(String rateAdsvcCd, String baseDate) {

        RateDtlInfo rateDtlInfo = new RateDtlInfo();

        try {

            String lBaseDate = DateTimeUtil.changeFormat(baseDate, "yyyyMMddHHmmss", "yyyy-MM-dd HH:mm:ss");

            // 요금제부가서비스안내상품관계 xml 조회
            String fPer = File.separator;
            String realDir = getRateAdsvcGdncPath(fileuploadBase, serverName);
            String realFilePath = realDir + fPer + "NMCP_RATE_ADSVC_GDNC_PROD_REL.xml";

            int rateAdsvcGdncSeq = 0;
            String giftPrmtSeqs = null;

            File file = new File(realFilePath);
            if(file.exists()) {
                List<RateAdsvcGdncProdRelXML> searchXmlList = XmlBindUtil.bindListFromXml(RateAdsvcGdncProdRelXML.class, file);

                for(Iterator<RateAdsvcGdncProdRelXML> iter = searchXmlList.iterator(); iter.hasNext();) {
                    RateAdsvcGdncProdRelXML xmlVo = iter.next();
                    if(rateAdsvcCd.equals(xmlVo.getRateAdsvcCd()) &&  DateTimeUtil.isMiddleDateTime(xmlVo.getPstngStartDate(), xmlVo.getPstngEndDate(), lBaseDate)){
                        rateAdsvcGdncSeq = xmlVo.getRateAdsvcGdncSeq();
                        giftPrmtSeqs = xmlVo.getGiftPrmtSeqs();
                        break;
                    }
                }
            }

            // === START 요금제 혜택요약 ===
            if(!StringUtil.isEmpty(giftPrmtSeqs)){
                List<RateGiftPrmtXML> giftPrmtXmlList = this.getRateGiftPrmtXmlList();
                RateGiftPrmtListDTO rateGiftPrmtListDTO = this.initRateGiftPrmtInfo(giftPrmtXmlList, giftPrmtSeqs, lBaseDate);
                rateDtlInfo.setRateGiftPrmtListDTO(rateGiftPrmtListDTO);
            }
            // === END 요금제 혜택요약 ===

            if (rateAdsvcGdncSeq > 0) {

                // 요금제부가서비스안내기본 xml 조회
                RateAdsvcCtgBasDTO basDTO = new RateAdsvcCtgBasDTO();
                basDTO.setRateAdsvcGdncSeq(rateAdsvcGdncSeq);

                RateAdsvcGdncBasXML xmlInfo = this.getRateAdsvcGdncBasXml(basDTO);
                rateDtlInfo.setRateAdsvcGdncBas(xmlInfo);

                // 요금제부가서비스혜택안내상세 xml 조회
                realFilePath = realDir + fPer + "NMCP_RATE_ADSVC_BNFIT_GDNC_DTL_" + rateAdsvcGdncSeq + ".xml";
                File file3 = new File(realFilePath);
                if(file3.exists()) {
                    List<RateAdsvcBnfitGdncDtlXML> rtnXmlList = XmlBindUtil.bindListFromXml(RateAdsvcBnfitGdncDtlXML.class, file3);
                    for(Iterator<RateAdsvcBnfitGdncDtlXML> iter = rtnXmlList.iterator(); iter.hasNext();) {
                        RateAdsvcBnfitGdncDtlXML xmlVo = iter.next();
                        if(!DateTimeUtil.isMiddleDateTime(xmlVo.getPstngStartDate(), xmlVo.getPstngEndDate(), lBaseDate)){
                            iter.remove();
                        }
                    }

                    // Map을 사용하여 RateAdsvcBnfitGdncDtlXML getRateAdsvcBnfitItemCd 키로 매핑
                    Map<String, RateAdsvcBnfitGdncDtlXML> rateAdsvcBnfitGdncMap = rtnXmlList.stream().collect(Collectors.toMap(
                            RateAdsvcBnfitGdncDtlXML::getRateAdsvcBnfitItemCd,
                            rateAdsvcBnfitGdnc -> rateAdsvcBnfitGdnc,
                            (existing, replacement) -> replacement // 중복 시 대체 값 선택  //기존 값을 유지하고 싶다면 (existing, replacement) -> existing를 사용하면 됩니다
                    ));
                    rateDtlInfo.setRateAdsvcBnfitGdncMap(rateAdsvcBnfitGdncMap);
                }

                realFilePath = realDir + fPer + "NMCP_RATE_ADSVC_GDNC_BANNER_DTL_" + rateAdsvcGdncSeq + ".xml";  //NMCP_RATE_ADSVC_GDNC_BANNER_DTL_303
                File file4 = new File(realFilePath);
                if(file4.exists()) {
                    List<RateGdncBannerDtlDTO> xmlList = XmlBindUtil.bindListFromXml(RateGdncBannerDtlDTO.class, file4);
                    List<RateGdncBannerDtlDTO> rtnXmlList = new ArrayList<>(
                            xmlList.stream().filter(item -> item.getBannerSbst() != null && !item.getBannerSbst().isEmpty()).collect(Collectors.toList()));
                    rateDtlInfo.setRateGdncBannerList(rtnXmlList);
                }

                realFilePath = realDir + fPer + "NMCP_RATE_ADSVC_GDNC_PROPERTY_DTL_" + rateAdsvcGdncSeq + ".xml";  //NMCP_RATE_ADSVC_GDNC_PROPERTY_DTL_303
                File file5 = new File(realFilePath);
                if(file5.exists()) {
                    List<RateGdncPropertyDtlDTO> rtnXmlList = XmlBindUtil.bindListFromXml(RateGdncPropertyDtlDTO.class, file5);

                    // Map을 사용하여 RateAdsvcBnfitGdncDtlXML getRateAdsvcBnfitItemCd 키로 매핑
                    Map<String, RateGdncPropertyDtlDTO> rateGdncPropertyMap = rtnXmlList.stream().collect(Collectors.toMap(
                            RateGdncPropertyDtlDTO::getPropertyCode,
                            rateGdncProperty -> rateGdncProperty,
                            (existing, replacement) -> replacement // 중복 시 대체 값 선택  //기존 값을 유지하고 싶다면 (existing, replacement) -> existing를 사용하면 됩니다
                    ));
                    rateDtlInfo.setRateGdncPropertyMap(rateGdncPropertyMap);
                }

                realFilePath = realDir + fPer + "NMCP_RATE_ADSVC_GDNC_EFF_PRICE_DTL_" + rateAdsvcGdncSeq + ".xml";  //NMCP_RATE_ADSVC_GDNC_EFF_PRICE_DTL_303
                File file6 = new File(realFilePath);
                if(file6.exists()) {
                    RateGdncEffPriceDtlDTO rateGdncEffPriceDtl = XmlBindUtil.bindFromXml(RateGdncEffPriceDtlDTO.class, file6);
                    rateDtlInfo.setRateGdncEffPriceDtl(rateGdncEffPriceDtl);

                    //제휴혜택
                    List<NmcpCdDtlDto> jehuPriceReflectList = NmcpServiceUtils.getCodeList(Constants.JEHU_PRICE_REFLECT);
                    //프로모션 혜택
                    List<NmcpCdDtlDto> promoPriceReflectList = NmcpServiceUtils.getCodeList(Constants.PROMO_PRICE_REFLECT);
                    //프로모션 혜택 금액
                    int totalPrice = 0;
                    if(!StringUtil.isEmpty(giftPrmtSeqs)) {
                        totalPrice = rateDtlInfo.getRateGiftPrmtListDTO().getTotalPrice();
                    }
                    //체감가 데이터 설정
                    this.initEffPriceInfo(rateDtlInfo, jehuPriceReflectList, promoPriceReflectList, totalPrice);
                }
            }

        } catch(JAXBException | ParseException e) {
            logger.error(e.toString());
        } catch(Exception e) {
            logger.error(e.toString());
        }

        return rateDtlInfo;
    }

    /**
     * 설명 : 사용후기 목록 총갯수 조회
     * @Author : 강채신
     * @Date : 2021.12.30
     * @param rateAdsvcCtgBasDTO
     * @return
     */
    @Override
    public int getRequestreviewTotalCnt(RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO) {
        return rateAdsvcGdncDao.getRequestreviewTotalCnt(rateAdsvcCtgBasDTO);
    }

    /**
     * 설명 : 사용후기 목록 조회
     * @Author : 강채신
     * @Date : 2021.12.30
     * @param rateAdsvcCtgBasDTO
     * @return
     */
    @Override
    public List<RequestReviewDto> getRequestreviewList(RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO) {
        return rateAdsvcGdncDao.getRequestreviewList(rateAdsvcCtgBasDTO);
    }

    /**
     * 설명 : 약정할인 프로그램 및 할인반환금 안내 조회
     * @Author : 강채신
     * @Date : 2021.12.30
     * @return
     */
    @Override
    public RateAgreeDTO getRateAgreeView() {
        return rateAdsvcGdncDao.getRateAgreeView();
    }

    /**
     * 설명 : 요금제부가서비스혜택안내 목록 조회
     * @Author : 강채신
     * @Date : 2021.12.30
     * @param rateAdsvcCtgBasDTO
     * @return
     */
    @Override
    public List<RateAdsvcBnfitGdncDtlDTO> getRateAdsvcBnfitGdncDtlList(RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO) {
        return rateAdsvcGdncDao.getRateAdsvcBnfitGdncDtlList(rateAdsvcCtgBasDTO);
    }

    /**
     * 설명 : 현재일이 시작일과 종료일사이의 날자인지 비교
     * @Author : 강채신
     * @Date : 2021.12.30
     * @param pstngStartDate
     * @param pstngEndDate
     * @return
     * @throws ParseException
     */
    public boolean isMiddleDate(String pstngStartDate, String pstngEndDate) throws ParseException {
        LocalDate localdate = LocalDate.now();
        LocalDate startLocalDate = LocalDate.parse(pstngStartDate);
        LocalDate endLocalDate = LocalDate.parse(pstngEndDate);
        // endDate는 포함하지 않으므로 +1일을 해줘야한다.
        endLocalDate = endLocalDate.plusDays(1);

        return (!localdate.isBefore(startLocalDate)) && (localdate.isBefore(endLocalDate));
    }

    /**
     * 설명 : 코드 상세 조회
     * @Author : 강채신
     * @Date : 2021.12.30
     * @param grpCd
     * @param dtlCd
     * @return
     */
    @Override
    public NmcpCdDtlDto getDtlCode(String grpCd, String dtlCd) {
        if(!StringUtils.isEmpty(dtlCd)) {
            List<NmcpCdDtlDto> codeList = NmcpServiceUtils.getCodeList(grpCd);
            if ( codeList != null) {
                for(NmcpCdDtlDto codeDto : codeList) {
                    if(dtlCd.equals(codeDto.getDtlCd())) {
                        return codeDto;
                    }
                }
            }
        }
        return new NmcpCdDtlDto();
    }

    /**
     * 설명 : 상품코드(rateAdsvcCd) 값으로 시퀀스값 조회
     * @Date : 2023.06.22
     * @param rateAdsvcCtgBasDTO
     * @return
     */
    @Override
    public RateAdsvcGdncProdRelXML getRateAdsvcProdRelBySoc(String soc) {
        List<RateAdsvcGdncProdRelXML> rateAdsvcGdncProdRelList= getRateAdsvcGdncProdRelXml();

        RateAdsvcGdncProdRelXML rateAdsvcGdncProdRel = new RateAdsvcGdncProdRelXML();

        for(RateAdsvcGdncProdRelXML item : rateAdsvcGdncProdRelList) {
            String rateAdsvcCd = item.getRateAdsvcCd();

            try {
                if(rateAdsvcCd.equals(soc)
                        && DateTimeUtil.isMiddleDateTime(item.getPstngStartDate(), item.getPstngEndDate())
                ) {
                    rateAdsvcGdncProdRel.setRateAdsvcProdRelSeq(item.getRateAdsvcProdRelSeq());
                    rateAdsvcGdncProdRel.setRateAdsvcGdncSeq(item.getRateAdsvcGdncSeq());
                    rateAdsvcGdncProdRel.setRateAdsvcCd(item.getRateAdsvcCd());
                    rateAdsvcGdncProdRel.setRateAdsvcNm(item.getRateAdsvcNm());
                    rateAdsvcGdncProdRel.setUseYn(item.getUseYn());
                    rateAdsvcGdncProdRel.setPstngStartDate(item.getPstngStartDate());
                    rateAdsvcGdncProdRel.setPstngEndDate(item.getPstngEndDate());

                    return rateAdsvcGdncProdRel;
                }
            } catch (ParseException e) {
                logger.error("Exception e : {}", e.getMessage());
            }
        }

        return rateAdsvcGdncProdRel;
    }

    /**
     * 설명 : 로밍 가입신청 validation check
     * @Author : 김동혁
     * @Date : 2023.06.23
     * @param rateAdsvcCtgBasDTO
     * @param soc
     * @param strtDt
     * @param strtTm
     * @param endDt
     * @param addPhone
     * @return JsonReturnDto
     */
    public JsonReturnDto regRoamValidationChk(RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO, String soc, String strtDt, String strtTm, String endDt, String[] addPhone) {
        JsonReturnDto jsonReturnDto = new JsonReturnDto();
        String dateType = rateAdsvcCtgBasDTO.getDateType();
        String lineType = rateAdsvcCtgBasDTO.getLineType();

        // 1. 요금제코드 체크
        if("".equals(soc) || soc == null) {
            throw new McpCommonJsonException("11", "상품 정보를 조회할 수 없습니다.<br><br>잠시 후 다시 시도해 주시기 바랍니다.");
        }

        // 2. param 유효성 체크
        // 2-1. strtDt, strtTm, endDt null 체크 후 변환
        if(!"9".equals(dateType) && !"".equals(dateType) && dateType != null) {
            if("".equals(strtDt) || strtDt == null) {
                jsonReturnDto.setReturnCode("21");
                jsonReturnDto.setMessage("시작일자를 입력해주세요.");
                return jsonReturnDto;
            }
            if("2".equals(dateType) || "3".equals(dateType)) {
                if("".equals(strtTm) || strtTm == null) {
                    jsonReturnDto.setReturnCode("22");
                    jsonReturnDto.setMessage("시작시간을 선택해주세요.");
                    return jsonReturnDto;
                }
            }
            if("3".equals(dateType) || "4".equals(dateType)) {
                    if("".equals(endDt) || endDt == null) {
                    jsonReturnDto.setReturnCode("23");
                    jsonReturnDto.setMessage("종료일자를 입력해주세요.");
                    return jsonReturnDto;
                }
            }


            // strtMax : 2개월 뒤까지, endMax : 6개월 뒤까지
            LocalDate todayDt = LocalDate.now();
            LocalDate strtMax = todayDt.plusMonths(2);
            LocalDate endMax = todayDt.plusMonths(6);
            // 날짜 포맷 지정
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

            // 사용자가 입력한 시작일/종료일을 LocalDate로 변환
            LocalDate strtDate = LocalDate.now();
            LocalDate endDate = LocalDate.now();

            strtDate = LocalDate.parse(strtDt, formatter);

            if(strtDate.isAfter(strtMax)) {
                jsonReturnDto.setReturnCode("31");
                jsonReturnDto.setMessage("시작일자는 최대 2개월 이내 입력 가능합니다.");
                return jsonReturnDto;
            }
            if(strtDate.isBefore(todayDt)) {
                jsonReturnDto.setReturnCode("32");
                jsonReturnDto.setMessage("시작일자는 오늘보다 이전일 수 없습니다.");
                return jsonReturnDto;
            }

            // 2-3. endDate 체크
            if("3".equals(dateType) || "4".equals(dateType)) {
                endDate = LocalDate.parse(endDt, formatter);

                if(endDate.isBefore(strtDate)) {
                    jsonReturnDto.setReturnCode("41");
                    jsonReturnDto.setMessage("종료일자는 시작일자보다 이전일 수 없습니다.");
                    return jsonReturnDto;
                }
                if(endDate.isAfter(endMax)) {
                    jsonReturnDto.setReturnCode("42");
                    jsonReturnDto.setMessage("종료일자는 최대 6개월 이내 입력 가능합니다.");
                    return jsonReturnDto;
                }
            }
        }

        // 2-4. 대표상품 서브회선 체크
        List<String> subPhoneList = new ArrayList<>();  // 서브회선 전화번호 중복체크
        if("M".equals(lineType)) {
            if (addPhone != null && addPhone.length > 0) {
                for (String phone : addPhone) {
                    if (phone.length() != 0 && phone.length() < 10) {
                        jsonReturnDto.setReturnCode("51");
                        jsonReturnDto.setMessage("서브 회선 정보가 올바르지 않습니다. 확인 후 다시 입력해주세요.");
                        return jsonReturnDto;
                    }else if(10 <= phone.length()){

                        if(subPhoneList.contains(phone)){
                          jsonReturnDto.setReturnCode("51");
                          jsonReturnDto.setMessage("서브 회선 정보가 중복됩니다. 확인 후 다시 입력해주세요.");
                          return jsonReturnDto;
                        }

                        subPhoneList.add(phone);
                    }
                }
            }
        }

        //2-5. 서브상품 대표회선 체크
        if("S".equals(lineType)) {
            if (addPhone != null && addPhone.length > 0) {
                if (addPhone[0].length() != 0 && addPhone[0].length() < 10) {
                    jsonReturnDto.setReturnCode("52");
                    jsonReturnDto.setMessage("대표 회선 정보가 올바르지 않습니다. 확인 후 다시 입력해주세요.");
                    return jsonReturnDto;
                }
            }
        }

        //2-6. 로밍 하루종일 ON 투게더(대표) 상품 서브 회선 수 1개 이상 필수
        if(REG_SVC_CD_5.equals(soc)) {
            if(addPhone.length < 1) {
                jsonReturnDto.setReturnCode("53");
                jsonReturnDto.setMessage("서브 회선을 하나 이상 입력해주세요.");
                return jsonReturnDto;
            }
        }

        jsonReturnDto.setReturnCode("00");
        jsonReturnDto.setMessage("");
        return jsonReturnDto;
    }

    /**
     * 설명 : 로밍상품(서브) 대표상품 prodHstSeq 가져오기
     * @Author : 김동혁
     * @Date : 2023.07.11
     * @param soc
     * @param addPhone
     * @return String
     */
    public String getMtProdHstSeq(String mtCd, String strtDt, String endDt, String mtPhone, String subNcn) {
        String mtProdHstSeq = "";

        if(StringUtil.isEmpty(mtPhone)){
          throw new McpCommonJsonException("05", "입력한 번호로 정보를 조회할 수 없습니다.<br><br>대표자의 가입 정보 확인 후 다시 시도하시기 바랍니다.");
        }

        //대표회선 확인 및 연동(X97)하기 위한 parameter get
        McpUserCntrMngDto userCntrMngDto = new McpUserCntrMngDto();
        userCntrMngDto.setCntrMobileNo(mtPhone);

        RestTemplate restTemplate = new RestTemplate();
        McpUserCntrMngDto userDto = restTemplate.postForObject(apiInterfaceServer + "/mypage/cntrListNoLogin", userCntrMngDto, McpUserCntrMngDto.class);

        if(userDto == null || !"A".equals(userDto.getSubStatus())) {
            throw new McpCommonJsonException("05", "입력한 번호로 정보를 조회할 수 없습니다.<br><br>대표자의 가입 정보 확인 후 다시 시도하시기 바랍니다.");
        }

        String ncn = userDto.getSvcCntrNo();
        String custId = userDto.getCustId();

        if("".equals(ncn) || ncn == null ) {
            throw new McpCommonJsonException("06", "입력한 번호로 정보를 조회할 수 없습니다.<br><br>대표자의 가입 정보 확인 후 다시 시도하시기 바랍니다.");
        }
        if("".equals(custId) || custId == null ) {
            throw new McpCommonJsonException("07", "입력한 번호로 정보를 조회할 수 없습니다.<br><br>대표자의 가입 정보 확인 후 다시 시도하시기 바랍니다.");
        }

        //대표회선이 이용중인 부가서비스 조회(부가파람 포함, X97)
        MpAddSvcInfoParamDto res = regSvcService.selectmyAddSvcList(ncn, mtPhone, custId);

        List<MpSocVO> mpSocList = res.getList();

        for(MpSocVO mpSoc : mpSocList) {
            //0. mtProdHstSeq 찾으면 loop 종료
            if(!"".equals(mtProdHstSeq) && mtProdHstSeq != null) {
                break;
            }

            //1. 서브상품과 동일한 대표상품이 있는지 확인
            if(!mtCd.equals(mpSoc.getSoc())) {
                continue;
            }

            //2. 대표상품의 서브계약번호가 신청계약번호와 일치하는지 check
            if(mpSoc.getShareSubContidList() == null || mpSoc.getShareSubContidList().isEmpty()) {
                continue;
            }
            boolean chkNcn = false;
            for(String shareSubContid : mpSoc.getShareSubContidList()) {
                if(subNcn.equals(shareSubContid)) {
                    chkNcn = true;
                }
            }
            if(!chkNcn) {
                continue;
            }

            //3. 서브상품 신청기간(시작일~종료일)을 포함하는지
            //3-1. 대표상품 신청기간 확인(종료일(endDttm)이 없는 경우 코드로 이용기간(usePrd) 찾아서 종료일 계산)
            if("".equals(mpSoc.getEndDt()) || mpSoc.getEndDt() == null) {
                //대표상품 코드로 이용기간(usePrd) 찾기
                RateAdsvcGdncProdRelXML prodRel = getRateAdsvcProdRelBySoc(mtCd);
                RateAdsvcCtgBasDTO seqDTO = new RateAdsvcCtgBasDTO();
                seqDTO.setRateAdsvcGdncSeq(prodRel.getRateAdsvcGdncSeq());
                RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO = getProdBySeq(seqDTO);
                int usePrd = Integer.parseInt(rateAdsvcCtgBasDTO.getUsePrd());

                //이용기간(usePrd)으로 종료일 계산
                try {
                    SimpleDateFormat dateForm = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(dateForm.parse(mpSoc.getStrtDt())); //시작일 date 변환
                    cal.add(Calendar.DATE, usePrd); //시작일 + 이용기간
                    mpSoc.setEndDttm(dateForm.format(cal.getTime())); //종료일 set
                } catch(ParseException e) {
                    logger.error("ParseException e : {}", e.getMessage());
                }
            }

            //3-2. 대표상품 시작일 <= 서브상품 시작일 , 서브상품 종료일 <= 대표상품 종료일 확인
            if("".equals(mpSoc.getStrtDt()) || mpSoc.getStrtDt() == null || "".equals(mpSoc.getEndDttm()) || mpSoc.getEndDttm() == null) {
                continue;
            }

            // LocalDate로 변환 후 비교
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDate strtDate = LocalDate.now();
            LocalDate endDate = LocalDate.now();
            LocalDate mtStrtDate = LocalDate.now();
            LocalDate mtEndDate = LocalDate.now();

            strtDate = LocalDate.parse(strtDt, formatter); // 사용자 입력 시작일
            endDate = LocalDate.parse(endDt, formatter); // 사용자 입력 종료일
            mtStrtDate = LocalDate.parse(mpSoc.getStrtDt().substring(0, 8), formatter); // 대표상품 시작일
            mtEndDate = LocalDate.parse(mpSoc.getEndDttm().substring(0, 8), formatter); // 대표상품 종료일
            if(strtDate.isBefore(mtStrtDate) || endDate.isAfter(mtEndDate)) {
                continue;
            }

            /** 202309-wooki 추가 start **************************************************************
                조회 해 온 대표상품이 로밍 하루종일ON투게더(대표)-PL2079777-라면, 로밍 하루종일ON투게더(서브)를 가입하기 위한 것임.
                로밍 하루종일ON투게더(서브) 가입 조건은 다른 서브 상품과는 다른점이 많기 때문에 검사를 해야 함.
                (1) 상품시작일은 현재일보다 크거나 같아야함 --> regRoamValidationChk에서 체크
                (2) 상품 시작일은 대표상품의 종료일보다 작아야함
                (3) 시작일은 대표상품의 시작일보다 크거나 같아야함 --> 위에서 체크
                (4) 현재 시간이 대표 시작시간보다 클 경우 현재일자+1
                (5) 종료일은 대표상품의 종료일보다 작거나 같아야함 --> 위에서 체크하지만, 7번일 때에는 getEndDttm이 아닌 getEndDt로 다시 체크해야 함
                (6) 종료일은 시작일보다 커야함 --> 7번의 이유료 종료일과 시작일이 같을 수도 있음
                (7) 종료시간이 235959일 경우 시작일과 종료일 동일입력 가능
            **/
            if("PL2079777".equals(mtCd)) {
                if(StringUtil.isNotEmpty(mpSoc.getStrtDt()) && StringUtil.isNotEmpty(mpSoc.getEndDt())) {

                    //(2) 상품 시작일은 대표상품의 종료일보다 작아야함
                    if(!strtDate.isBefore(mtEndDate)) {
                        throw new McpCommonJsonException("08", "시작일자는 대표상품의 종료일보다 이전이어야 합니다.");
                    }

                    //(4) 현재 시간이 대표 시작시간보다 클 경우 현재일자+1
                    if(strtDate.isEqual(mtStrtDate)) {
                        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
                        LocalDateTime nowDateTime = LocalDateTime.now();
                        LocalDateTime startDateTime = LocalDateTime.parse(mpSoc.getStrtDt(), timeFormat);
                        if(nowDateTime.isAfter(startDateTime)) {
                            throw new McpCommonJsonException("09", "현재시간이 대표상품의 시작시간보다 작아야 합니다.");
                        }
                    }

                    if(14 == mpSoc.getEndDt().length()) {
                        if("235959".equals(mpSoc.getEndDt().substring(8))) {
                            //(5) 종료일은 대표상품의 종료일보다 작거나 같아야함
                            //(7) 종료시간이 235959일 경우 시작일과 종료일 동일입력 가능
                            //(==종료시간이 235959이면서 종료일이 대표상품의 종료일보다 작지 않고 같지 않으면(==크면) 리턴)
                            //235959인 경우 위에서는 +1초 한 날짜로 체크하기 때문에 여기서는 원래 날짜로 체크해야 함.
                            LocalDate mtEndDateReal = LocalDate.now();
                            mtEndDateReal = LocalDate.parse(mpSoc.getEndDt().substring(0, 8), formatter);
                            if(!endDate.isBefore(mtEndDateReal) && !endDate.isEqual(mtEndDateReal)) {
                                throw new McpCommonJsonException("10", "종료일자는 대표상품의 종료일보다 작거나 같아야 합니다.");
                            }
                        }else {
                            //(6) 종료일은 시작일보다 커야함
                            //(7) 종료시간이 235959일 경우 시작일과 종료일 동일입력 가능
                            //(==종료시간이 235959가 아니면서 종료일이 시작일보다 크지 않거나 시작일과 종료일이 같으면 리턴)
                            if(!endDate.isAfter(strtDate) || strtDate.equals(endDate)) {
                                throw new McpCommonJsonException("11", "종료일자는 시작일자 이후여야 합니다.");
                            }
                        }
                    }
                }
            }
            /** 202309-wooki 추가 end **************************************************************/

            mtProdHstSeq = mpSoc.getProdHstSeq();
        }

        return mtProdHstSeq;
    }

    /**
     * 설명 : 로밍 가입에 필요한 param 생성
     * @Author : 김동혁
     * @Date : 2023.06.23
     * @param rateAdsvcCtgBasDTO
     * @param strtDt
     * @param strtTm
     * @param endDt
     * @param addPhone
     * @return
     */
    public String getRoamParam(RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO, String soc, String strtDt, String strtTm, String endDt, String[] addPhone, String mtProdHstSeq) {
        StringBuilder ftrNewParam = new StringBuilder();
        String dateType = rateAdsvcCtgBasDTO.getDateType();
        String lineType = rateAdsvcCtgBasDTO.getLineType();

        // 1. 시작일
        if("1".equals(dateType) || "2".equals(dateType) || "3".equals(dateType) || "4".equals(dateType)) {
            if(!ftrNewParam.toString().isEmpty()) {
                ftrNewParam.append(":");
            }
            ftrNewParam.append(strtDt);
        }

        // 2. 시작시간
        if("2".equals(dateType) || "3".equals(dateType)) {
            // 시작일 뒤에 붙어서 들어가기 때문에 ":" 붙이지 않음
            ftrNewParam.append(strtTm);
        }

        // 3. 종료일
        if("3".equals(dateType) || "4".equals(dateType)) {
            if(!ftrNewParam.toString().isEmpty()) {
                ftrNewParam.append(":");
            }
            ftrNewParam.append(endDt);
        }

        // 4. 대표상품 서브회선
        if("M".equals(lineType)) {
            if (addPhone != null && addPhone.length > 0) {
                for (String phone : addPhone) {
                    if (10 <= phone.length()) {
                        if(!ftrNewParam.toString().isEmpty()) {
                            ftrNewParam.append(":");
                        }
                        ftrNewParam.append(phone);
                    }
                }
            }
        }

        // 5. 서브상품 대표회선, 상품일련번호 (하루종일ON 투게더(서브) 상품이 아니면 ftrNewParam 초기화 후 대표회선, 상품일련번호만 설정)
        if("S".equals(lineType)) {
            // 하루종일ON 투게더(서브) 상품이 아니면 초기화
            if(!"PL2079778".equals(soc)) {
                ftrNewParam.setLength(0);
            }

            // 대표회선
            if (addPhone != null && addPhone.length > 0) {
                if (10 <= addPhone[0].length()) {
                    if(!ftrNewParam.toString().isEmpty()) {
                        ftrNewParam.append(":");
                    }
                    ftrNewParam.append(addPhone[0]);
                }
            }

            // 상품일련번호
            if(!ftrNewParam.toString().isEmpty()) {
                ftrNewParam.append(":");
            }
            ftrNewParam.append(mtProdHstSeq);
        }


        // 6. ALW_FLG
        if("CNXDTROMC".equals(soc) || "PLSDTROMC".equals(soc)) {
            if(!ftrNewParam.toString().isEmpty()) {
                ftrNewParam.append(":");
            }
            ftrNewParam.append("0");
        }

        return ftrNewParam.toString();
    }


    @Override
    public List<RateAdsvcGdncProdXML> getCtgRateAllList(MapWrapper mapWrapper) {
        List<RateAdsvcGdncProdXML> rtnList = new ArrayList<RateAdsvcGdncProdXML>();

        Map<String, ListXmlWrapper> ctgMapXml = mapWrapper.getItem();
        ListXmlWrapper ctgListXml = ctgMapXml.get("ctgList");
        List<RateAdsvcGdncProdXML> ctgList = ctgListXml.getItem();

        for (RateAdsvcGdncProdXML item : ctgList) {
            try {
                String rateAdsvcDivCd = item.getRateAdsvcDivCd();
                boolean isMatchingRateAdsvcDivCd = rateAdsvcDivCd.equals("RATE"); // 요금제
                boolean isCtgOutputCodeValid = "CO00130001".equals(item.getCtgOutputCd()); // 카테고리출력코드_상품소개
                boolean isDateValid = DateTimeUtil.isMiddleDateTime(item.getPstngStartDate(), item.getPstngEndDate());

                if (isMatchingRateAdsvcDivCd && isCtgOutputCodeValid && isDateValid) {
                    /*int depthKey = item.getDepthKey();

                    if (depthKey > 1) {
                        // 상품목록 XML 조회
                        List<RateDtlInfo> rateDtlList = this.getRateDtlInfoList(item.getRateAdsvcCtgCd());
                        if (rateDtlList != null && !rateDtlList.isEmpty()) {
                            logger.info("getRateDtlInfoList - rateDtlList.size(): {}", rateDtlList.size());
                            item.setRateDtlList(rateDtlList);
                        }
                    }*/
                    rtnList.add(item);
                }
            } catch (ParseException e) {
                logger.error("Error parsing date for item with RateAdsvcDivCd {}: {}", item.getRateAdsvcDivCd(), e.getMessage());
            } catch (Exception e) {
                logger.error("Unexpected error processing item with RateAdsvcDivCd {}: {}", item.getRateAdsvcDivCd(), e.getMessage());
            }
        }

        return rtnList;
    }

    @Override
    public List<RateDtlInfo> getRateDtlInfoList(String rateAdsvcCtgCd, MapWrapper mapWrapper, List<RateGiftPrmtXML> rateGiftPrmtXmlList, List<RateAdsvcGdncProdRelXML> rateAdsvcGdncProdRelXml) {
        return  getRateDtlInfoList(rateAdsvcCtgCd, mapWrapper, rateGiftPrmtXmlList, rateAdsvcGdncProdRelXml, false) ;
    }


    @Override
    public List<RateDtlInfo> getRateDtlInfoList(String rateAdsvcCtgCd, MapWrapper mapWrapper, List<RateGiftPrmtXML> giftPrmtXmlList, List<RateAdsvcGdncProdRelXML> prodRelXmlList , Boolean isDetailHtml) {
        List<RateDtlInfo> rtnList = new ArrayList<RateDtlInfo>();


        // 요금제 혜택요약 xml 조회

        Map<String, ListXmlWrapper> prodMapXml = mapWrapper.getItem();
        ListXmlWrapper prodListXml = prodMapXml.get("proList");
        List<RateAdsvcGdncProdXML> prodList = prodListXml.getItem();

        String fPer = File.separator;
        String realDir = getRateAdsvcGdncPath(fileuploadBase, serverName);

        // 요금제부가서비스안내상품관계 xml 조회
        // 요금제 SOC코드 확인
        Map<Integer,RateAdsvcGdncProdRelXML> prodRelXmlListMap = prodRelXmlList.stream()
                .collect(Collectors.toMap(
                        RateAdsvcGdncProdRelXML::getRateAdsvcGdncSeq,
                        Function.identity(),
                        (existing, replacement) -> replacement // 기존 값 덮어쓰기
                ));


        for (RateAdsvcGdncProdXML item : prodList) {
            try {
                if (rateAdsvcCtgCd.equals(item.getRateAdsvcCtgCd())
                        && DateTimeUtil.isMiddleDateTime(item.getPstngStartDate(), item.getPstngEndDate())
                ) {
                    RateDtlInfo rateDtlInfo = new RateDtlInfo();

                    //요금제 코드 , 코드명
                    int rateAdsvcGdncSeq = item.getRateAdsvcGdncSeq();
                    RateAdsvcGdncProdRelXML rateAdsvcGdncProdRel = prodRelXmlListMap.get(rateAdsvcGdncSeq);
                    if (rateAdsvcGdncProdRel != null) {
                        rateDtlInfo.setRateAdsvcCd(rateAdsvcGdncProdRel.getRateAdsvcCd());
                        rateDtlInfo.setRateAdsvcNm(rateAdsvcGdncProdRel.getRateAdsvcNm());

                        if(prodRelXmlList != null) {
                            // === START 요금제 혜택요약 ===
                            if(!StringUtil.isEmpty(rateAdsvcGdncProdRel.getGiftPrmtSeqs())){
                                RateGiftPrmtListDTO rateGiftPrmtListDTO = this.initRateGiftPrmtInfo(giftPrmtXmlList, rateAdsvcGdncProdRel.getGiftPrmtSeqs(), "");
                                rateDtlInfo.setRateGiftPrmtListDTO(rateGiftPrmtListDTO);
                            }
                        }
                    }

                    String realFilePath = realDir + fPer + "NMCP_RATE_ADSVC_GDNC_BAS_" + rateAdsvcGdncSeq + ".xml";
                    File file2 = new File(realFilePath);

                    if (file2.exists()) {
                        RateAdsvcGdncBasXML xmlInfo = XmlBindUtil.bindFromXml(RateAdsvcGdncBasXML.class, file2);
                        rateDtlInfo.setRateAdsvcGdncBas(xmlInfo);
                    }

                    realFilePath = realDir + fPer + "NMCP_RATE_ADSVC_BNFIT_GDNC_DTL_" + rateAdsvcGdncSeq + ".xml";
                    File file3 = new File(realFilePath);
                    if (file3.exists()) {
                        List<RateAdsvcBnfitGdncDtlXML> rtnXmlList = XmlBindUtil.bindListFromXml(RateAdsvcBnfitGdncDtlXML.class, file3);
                        for (Iterator<RateAdsvcBnfitGdncDtlXML> iter = rtnXmlList.iterator(); iter.hasNext(); ) {
                            RateAdsvcBnfitGdncDtlXML xmlVo = iter.next();
                            if (!DateTimeUtil.isMiddleDateTime(xmlVo.getPstngStartDate(), xmlVo.getPstngEndDate())) {
                                iter.remove();
                            }
                        }

                        // Map을 사용하여 RateAdsvcBnfitGdncDtlXML getRateAdsvcBnfitItemCd 키로 매핑
                        Map<String, RateAdsvcBnfitGdncDtlXML> rateAdsvcBnfitGdncMap =
                                rtnXmlList.stream().collect(Collectors.toMap(
                                        RateAdsvcBnfitGdncDtlXML::getRateAdsvcBnfitItemCd,
                                        rateAdsvcBnfitGdnc -> rateAdsvcBnfitGdnc,
                                        (existing, replacement) -> replacement // 중복 시 대체 값 선택  //기존 값을 유지하고 싶다면 (existing, replacement) -> existing를 사용하면 됩니다
                                ));
                        rateDtlInfo.setRateAdsvcBnfitGdncMap(rateAdsvcBnfitGdncMap);
                    }

                    realFilePath = realDir + fPer + "NMCP_RATE_ADSVC_GDNC_BANNER_DTL_" + rateAdsvcGdncSeq + ".xml";  //NMCP_RATE_ADSVC_GDNC_BANNER_DTL_303
                    File file4 = new File(realFilePath);
                    if(file4.exists()) {
                        List<RateGdncBannerDtlDTO> xmlList = XmlBindUtil.bindListFromXml(RateGdncBannerDtlDTO.class, file4);
                        List<RateGdncBannerDtlDTO> rtnXmlList = new ArrayList<RateGdncBannerDtlDTO>(
                                xmlList.stream().filter(itemTemp -> itemTemp.getBannerSbst() != null && !itemTemp.getBannerSbst().isEmpty()).collect(Collectors.toList())
                        );

                        rateDtlInfo.setRateGdncBannerList(rtnXmlList);
                    }


                    realFilePath = realDir + fPer + "NMCP_RATE_ADSVC_GDNC_PROPERTY_DTL_" + rateAdsvcGdncSeq + ".xml";  //NMCP_RATE_ADSVC_GDNC_PROPERTY_DTL_303
                    File file5 = new File(realFilePath);
                    if(file5.exists()) {
                        List<RateGdncPropertyDtlDTO> rtnXmlList = XmlBindUtil.bindListFromXml(RateGdncPropertyDtlDTO.class, file5);

                        // Map을 사용하여 RateAdsvcBnfitGdncDtlXML getRateAdsvcBnfitItemCd 키로 매핑
                        Map<String, RateGdncPropertyDtlDTO> rateGdncPropertyMap =
                                rtnXmlList.stream().collect(Collectors.toMap(
                                        RateGdncPropertyDtlDTO::getPropertyCode,
                                        rateGdncProperty -> rateGdncProperty,
                                        (existing, replacement) -> replacement // 중복 시 대체 값 선택  //기존 값을 유지하고 싶다면 (existing, replacement) -> existing를 사용하면 됩니다
                                ));
                        rateDtlInfo.setRateGdncPropertyMap(rateGdncPropertyMap);
                    }


                    if (isDetailHtml) {
                        String realFilePath6 = realDir + fPer + "NMCP_RATE_ADSVC_GDNC_DTL_" + rateAdsvcGdncSeq + ".xml";

                        File file6 = new File(realFilePath6);
                        if(file6.exists()) {
                            List<RateAdsvcGdncDtlXML> rtnXmlList = XmlBindUtil.bindListFromXml(RateAdsvcGdncDtlXML.class, file6);

                            for(Iterator<RateAdsvcGdncDtlXML> iter = rtnXmlList.iterator(); iter.hasNext();) {
                                RateAdsvcGdncDtlXML xmlVo = iter.next();
                                if(!DateTimeUtil.isMiddleDateTime(xmlVo.getPstngStartDate(), xmlVo.getPstngEndDate())) {
                                    iter.remove();
                                }
                            }
                            rateDtlInfo.setRateAdsvcGdncDtlList(rtnXmlList);
                        }

                    }

                    rtnList.add(rateDtlInfo);
                }
            } catch(JAXBException e) {
                logger.error(e.toString());
            } catch (ParseException e) {
                logger.error("Exception e : {}", e.getMessage());
            } catch(Exception e) {
                logger.error(e.toString());
            }

        }
        return rtnList;
    }

    /** 설명 : 요금제 혜택요약 xml 조회 */
    @Override
    public List<RateGiftPrmtXML> getRateGiftPrmtXmlList() {

            List<RateGiftPrmtXML> rtnXmlList = null;

            try{
                String fPer = File.separator;
                String realDir = getRateAdsvcGdncPath(fileuploadBase, serverName);
                String realFilePath = realDir + fPer + "NMCP_RATE_GIFT_PRMT.xml";

                File file = new File(realFilePath);

                if(file.exists()){
                    rtnXmlList = XmlBindUtil.bindListFromXml(RateGiftPrmtXML.class, file);
                }

            }catch(JAXBException e){
                logger.error("getRateGiftPrmtXmlList JAXBException={}", e.toString());
            }catch(Exception e){
                logger.error("getRateGiftPrmtXmlList Exception={}", e.toString());
            }

            return rtnXmlList;
    }

    /** 설명 : 요금제 혜택요약 정보 세팅 */
    @Override
    public RateGiftPrmtListDTO initRateGiftPrmtInfo(List<RateGiftPrmtXML> xmlList, String giftPrmtSeqs, String baseDate) {
        return selectInitRateGiftPrmtInfo(xmlList,giftPrmtSeqs,baseDate,"");
    }

    @Override
    public RateGiftPrmtListDTO initRateGiftPrmtInfo(List<RateGiftPrmtXML> xmlList, String giftPrmtSeqs, String baseDate,String pageUri) {
        return selectInitRateGiftPrmtInfo(xmlList,giftPrmtSeqs,baseDate,pageUri);
    }

    private RateGiftPrmtListDTO selectInitRateGiftPrmtInfo(List<RateGiftPrmtXML> xmlList, String giftPrmtSeqs, String baseDate,String pageUri) {

         RateGiftPrmtListDTO rateGiftPrmtListDTO = new RateGiftPrmtListDTO();

         if(StringUtil.isEmpty(giftPrmtSeqs) || xmlList == null || xmlList.isEmpty()){
           return rateGiftPrmtListDTO; // 빈 객체 리턴
         }

         String[] seqArr = giftPrmtSeqs.split("\\|");
         List<Long> seqList = Arrays.stream(seqArr).map(Long::valueOf).collect(Collectors.toList());

         List<RateGiftPrmtDTO> rateGiftPrmtList = new ArrayList<>(); //기존 혜택(모바일 혜택)
         List<RateGiftPrmtDTO> wireRateGiftPrmtList = new ArrayList<>(); //인터넷 혜택
         List<RateGiftPrmtDTO> freeRateGiftPrmtList = new ArrayList<>(); //무료 혜택

         int totalPrice = 0; //모바일 혜택 총 금액
         int totalWirePrice = 0; //인터넷 혜택 총 금액

         RateGiftPrmtDTO rateGiftPrmtDTO = null;
         boolean flagPartA = false; // A파트 사용여부
         boolean flagPartB = false; // B파트 사용여부

         // 요금제 혜택요약 정보 세팅
         for(RateGiftPrmtXML xml : xmlList){

           if(!seqList.contains(xml.getSeq())){
             continue;
           }

           flagPartA = middleTimeChkWithoutEx(xml.getPstngStartDate(), xml.getPstngEndDate(), baseDate);
           flagPartB = middleTimeChkWithoutEx(xml.getPstngStartDateSec(), xml.getPstngEndDateSec(), baseDate);

           if(!flagPartA && !flagPartB){
             continue;
           }

           rateGiftPrmtDTO = new RateGiftPrmtDTO();
           rateGiftPrmtDTO.setSeq(xml.getSeq());
           rateGiftPrmtDTO.setPrmtId(xml.getPrmtId());

           String giftText = "";
           String mainPrdtImgUrl  = "";
           String mainPrdtNm  = "";
           int giftPrice = 0;

           boolean isVisible = false;

           if(flagPartA){
                 //if(pageUri == null || pageUri.trim().isEmpty() || !"N".equals(xml.getListViewYn())) {
                  if(org.apache.commons.lang3.StringUtils.isBlank(pageUri)   || !"N".equals(xml.getListViewYn())) {
                         // A파트 정보 사용
                   giftText = xml.getGiftText();
                   mainPrdtImgUrl = xml.getMainPrdtImgUrl();
                   mainPrdtNm = xml.getMainPrdtNm();
                   giftPrice = xml.getGiftPrice();
                   isVisible = true;
                 }
           }else{
               //if(pageUri == null || pageUri.trim().isEmpty() || !"N".equals(xml.getListViewYnSec())) {
               if(org.apache.commons.lang3.StringUtils.isBlank(pageUri)   || !"N".equals(xml.getListViewYnSec())) {
                   // B파트 정보 사용
                   giftText = xml.getGiftTextSec();
                   mainPrdtImgUrl = xml.getMainPrdtImgUrlSec();
                   mainPrdtNm = xml.getMainPrdtNmSec();
                   giftPrice = xml.getGiftPriceSec();
                   isVisible = true;
               }
           }

           if (!isVisible) {
                continue;
            }

               RateGiftPrmtDTO gift = new RateGiftPrmtDTO();
               gift.setSeq(xml.getSeq());
               gift.setPrmtId(xml.getPrmtId());
               gift.setGiftText(giftText);
               gift.setMainPrdtImgUrl(mainPrdtImgUrl);
               gift.setMainPrdtNm(mainPrdtNm);
               gift.setGiftPrice(giftPrice);
               gift.setPopupUrl(xml.getPopupUrl());
               gift.setPopupUrlMo(xml.getPopupUrlMo());

               if ("N".equalsIgnoreCase(xml.getWirelessYn())) {
                   // 인터넷 혜택  RE
                   wireRateGiftPrmtList.add(gift);
                   totalWirePrice += giftPrice;
               } else if (giftPrice == 0) {
                   // 무료 혜택
                   freeRateGiftPrmtList.add(gift);
               } else {
                   // 기존 혜택(모바일 혜택)
                   rateGiftPrmtList.add(gift);
                   totalPrice += giftPrice;
               }

         }

         //메인 아코디언 노출 혜택 정하기
         //1순위 모바일(최대 금액), 2순위 무료(최대 갯수), 3순위 인터넷(최대 금액 or 최대 갯수)
         int totalMainPrice = 0;
         int totalMainCount = 0;
         List<RateGiftPrmtDTO> mainGiftPrmtList = new ArrayList<>();

         if (!rateGiftPrmtList.isEmpty()) {
             mainGiftPrmtList = rateGiftPrmtList;
             totalMainPrice = totalPrice;
         } else if (!freeRateGiftPrmtList.isEmpty()) {
             mainGiftPrmtList = freeRateGiftPrmtList;
             totalMainCount = freeRateGiftPrmtList.size();
         } else if (!wireRateGiftPrmtList.isEmpty()) {
             mainGiftPrmtList = wireRateGiftPrmtList;
             totalMainPrice = totalWirePrice;
             totalMainCount = wireRateGiftPrmtList.size();
         }

         rateGiftPrmtListDTO.setRateGiftPrmtList(rateGiftPrmtList);
         rateGiftPrmtListDTO.setTotalPrice(totalPrice);
         rateGiftPrmtListDTO.setTotalCount(rateGiftPrmtList.size());
         rateGiftPrmtListDTO.setWireRateGiftPrmtList(wireRateGiftPrmtList);
         rateGiftPrmtListDTO.setTotalWirePrice(totalWirePrice);
         rateGiftPrmtListDTO.setFreeRateGiftPrmtList(freeRateGiftPrmtList);
         rateGiftPrmtListDTO.setMainGiftPrmtList(mainGiftPrmtList);
         rateGiftPrmtListDTO.setTotalMainPrice(totalMainPrice);
         rateGiftPrmtListDTO.setTotalMainCount(totalMainCount);

         return rateGiftPrmtListDTO;

    }


    /*요금제 체감가 데이터 설정*/
    @Override
    public void initEffPriceInfo(RateDtlInfo rateDtlInfo, List<NmcpCdDtlDto> jehuPriceReflectList, List<NmcpCdDtlDto> promoPriceReflectList, int totalPrice) {
        RateGdncEffPriceDtlDTO rateGdncEffPriceDtl = rateDtlInfo.getRateGdncEffPriceDtl();

        if (rateGdncEffPriceDtl == null || rateGdncEffPriceDtl.getJehuPriceReflectCd() == null) {
            return;
        }

        //제휴 혜택
        for (NmcpCdDtlDto code : jehuPriceReflectList) {
            if (rateGdncEffPriceDtl.getJehuPriceReflectCd().equals(code.getDtlCd())) {
                rateGdncEffPriceDtl.setJehuPriceReflectNm(code.getDtlCdNm()); //제휴혜택 명(ex 지니뮤직 구독 무료)
                rateGdncEffPriceDtl.setJehuBenefitAmt(code.getExpnsnStrVal1()); //제휴혜택 가격(ex 11900)
                rateGdncEffPriceDtl.setJehuImgUrl(code.getImgNm()); //제휴혜택 로고 경로
                break;
            }
        }

        //요금제 가격
        int promotionAmtInt = Integer.parseInt(StringUtil.onlyNum(rateDtlInfo.getRateAdsvcGdncBas().getPromotionAmtVatDesc()));

        //프로모션 혜택 제공 금액
        int promotionAmt = totalPrice;
        //프로모션 혜택 로고 경로
        String promotionImgUrl = null;

        for (NmcpCdDtlDto promo : promoPriceReflectList) {
            int min = Integer.parseInt(StringUtil.onlyNum(promo.getExpnsnStrVal1()));
            int max = Integer.parseInt(StringUtil.onlyNum(promo.getExpnsnStrVal2()));

            if (promotionAmtInt >= min && promotionAmtInt <= max) {
                promotionImgUrl = promo.getImgNm(); //프로모션 혜택 로고 경로
                break;
            }
        }

        //프로모션 개월 수
        String customMonthsStr = rateGdncEffPriceDtl.getCustomMonths();

        if (customMonthsStr == null || customMonthsStr.isEmpty()) {
            customMonthsStr = "24";
            rateGdncEffPriceDtl.setCustomMonths(customMonthsStr);
        }

        int customMonths = Integer.parseInt(customMonthsStr);

        //프로모션 혜택 금액(프로모션 혜택 제공 금액  / 프로모션 개월 수)
        int promotionBenefitAmt = (promotionAmt / customMonths / 100) * 100;
        //제휴혜택 금액
        String jehuBenefitStr = rateGdncEffPriceDtl.getJehuBenefitAmt();
        int jehuAmt = (jehuBenefitStr != null && !jehuBenefitStr.isEmpty()) ? Integer.parseInt(StringUtil.onlyNum(jehuBenefitStr)) : 0;
        //체감가(월 요금 - 제휴혜택 금액 - 프로모션 혜택 금액)
        int monthlyEffPrice = Math.max(0, promotionAmtInt - jehuAmt - promotionBenefitAmt);

        rateGdncEffPriceDtl.setPromotionAmt(String.valueOf(promotionAmt)); //프로모션 혜택 제공 금액
        rateGdncEffPriceDtl.setPromotionBenefitAmt(String.valueOf(promotionBenefitAmt)); //프로모션 혜택 금액
        rateGdncEffPriceDtl.setPromotionImgUrl(promotionImgUrl); //프로모션 혜택 로고
        rateGdncEffPriceDtl.setMonthlyEffPrice(String.valueOf(monthlyEffPrice)); //월 체감가
    }

    @Override
    public List<RateAdsvcCtgBasDTO> getRateAmountList() {
        MapWrapper mapWrapper = getMapWrapper();
        Map<String, ListXmlWrapper> mapXml = mapWrapper.getItem();

        ListXmlWrapper ctgListXml = mapXml.get("ctgList");
        List<RateAdsvcGdncProdXML> ctgList = ctgListXml.getItem();

        ListXmlWrapper proListXml = mapXml.get("proList");
        List<RateAdsvcGdncProdXML> proList = proListXml.getItem();

        Map<Integer, String> prodRelMap = this.getRateAdsvcGdncProdRelXml().stream()
                .collect(Collectors.toMap(
                        RateAdsvcGdncProdRelXML::getRateAdsvcGdncSeq,
                        RateAdsvcGdncProdRelXML::getRateAdsvcCd,
                        (p1, p2) -> p1
                ));

        List<String> upCtgCdList = Optional.ofNullable(NmcpServiceUtils.getCodeList("rateCompCodeList"))
                .orElseGet(Collections::emptyList).stream()
                .map(NmcpCdDtlDto::getDtlCd)
                .collect(Collectors.toList());

        return ctgList.stream()
                .filter(ctg -> upCtgCdList.contains(ctg.getUpRateAdsvcCtgCd()))
                .filter(ctg -> "RATE".equals(ctg.getRateAdsvcDivCd()))
                .filter(ctg -> "CO00130001".equals(ctg.getCtgOutputCd()))
                .filter(ctg -> middleTimeChkWithoutEx(ctg.getPstngStartDate(), ctg.getPstngEndDate()))
                .sorted(Comparator.comparing(ctg -> {
                    try {
                        return Integer.parseInt(ctg.getSortOdrg());
                    } catch (NumberFormatException e) {
                        return Integer.MAX_VALUE; // 숫자가 아닌 값은 가장 마지막으로 정렬
                    }
                }))
                .flatMap(ctg ->
                        proList.stream()
                                .filter(pro -> ctg.getRateAdsvcCtgCd().equals(pro.getRateAdsvcCtgCd()))
                                .filter(pro -> middleTimeChkWithoutEx(pro.getPstngStartDate(), pro.getPstngEndDate()))
                                .sorted(Comparator.comparing(pro -> {
                                    try {
                                        return Integer.parseInt(pro.getSortOdrg());
                                    } catch (NumberFormatException e) {
                                        return Integer.MAX_VALUE; // 숫자가 아닌 값은 가장 마지막으로 정렬
                                    }
                                }))
                                .map(pro -> {
                                    RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO = new RateAdsvcCtgBasDTO();
                                    rateAdsvcCtgBasDTO.setRateAdsvcNm(pro.getRateAdsvcNm());
                                    rateAdsvcCtgBasDTO.setRateAdsvcGdncSeq(pro.getRateAdsvcGdncSeq());
                                    rateAdsvcCtgBasDTO.setPromotionAmtVatDesc(pro.getPromotionAmtVatDesc());
                                    rateAdsvcCtgBasDTO.setRateAdsvcCd(prodRelMap.get(pro.getRateAdsvcGdncSeq()));
                                    return rateAdsvcCtgBasDTO;
                                })
                ).collect(Collectors.toList());
    }

    private static boolean middleTimeChkWithoutEx(String start, String end) {
        return middleTimeChkWithoutEx(start, end, "");
    }

    private static boolean middleTimeChkWithoutEx(String start, String end, String middle) {

          if(StringUtil.isEmpty(start) || StringUtil.isEmpty(end)){
              return false;
          }

          boolean result = false;

            try{

                if(StringUtil.isEmpty(middle)){ // 현재시간으로 비교
                    result = DateTimeUtil.isMiddleDateTime(start, end);
                }else{ // 기준시간으로 비교
                    result = DateTimeUtil.isMiddleDateTime(start, end, middle);
                }

            }catch(DateTimeParseException | ParseException e){
                logger.error("middleTimeChkWithoutEx ParseException={}", e.getMessage());
                result = false;
            }

            return result;
      }

}
