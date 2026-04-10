package com.ktmmobile.mcp.cprt.service;

import com.ktmmobile.mcp.common.util.DateTimeUtil;
import com.ktmmobile.mcp.common.util.XmlBindUtil;
import com.ktmmobile.mcp.cprt.dto.*;
import com.ktmmobile.mcp.event.service.CprtCardEventService;
import jakarta.xml.bind.JAXBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CprtServiceImpl implements CprtService{

	private static Logger logger = LoggerFactory.getLogger(CprtServiceImpl.class);

	/** 파일업로드 경로*/
    @Value("${common.fileupload.base}")
    private String fileuploadBase;

    /** 파일업로드 접근가능한 웹경로 */
    @Value("${common.fileupload.web}")
    private String fileuploadWeb;

    @Autowired
    private CprtCardEventService cprtCardEventService;

	@Override
	public List<AlliCardContDto> cprtCardGdncListXml() {

		List<AlliCardContDto> result = new ArrayList<AlliCardContDto>();

		String fPer = File.separator;
    	String realDir = fileuploadBase + File.separator + "cprtCard";
    	AliasMapWrapper mapWrapper = null;

    	try {
			String realFilePath = realDir + fPer + "NMCP_CPRT_CARD_CTG_BAS.xml";

			File file1 = new File(realFilePath);
            if(file1.exists()) {
            	mapWrapper = XmlBindUtil.bindFromXml(AliasMapWrapper.class, file1);

            	Map<String, AliasListXmlWrapper> proMapXml = mapWrapper.getItem();
            	AliasListXmlWrapper proListXml = proMapXml.get("proList");
            	List<AlliCardCtgaContXML> prodList = proListXml.getItem();

            	for(AlliCardCtgaContXML item : prodList) {

            		//GDNC_BAS XML 읽어오기
                    String realFilePath2 = realDir + fPer + "NMCP_CPRT_CARD_GDNC_BAS_" + item.getCprtCardGdncSeq() + ".xml";
            		File file2 = new File(realFilePath2);

            		if(file2.exists()) {
                    	AlliCardDtlContDtoXML alliCardDtlContDtoXML = XmlBindUtil.bindFromXml(AlliCardDtlContDtoXML.class, file2);

                    	if("Y".equals(alliCardDtlContDtoXML.getUseYn())) {
                            AlliCardContDto alliCardContDto = new AlliCardContDto();
                    		alliCardContDto.setCprtCardCtgCd(item.getCprtCardCtgCd());
                    		alliCardContDto.setCprtCardGdncSeq(alliCardDtlContDtoXML.getCprtCardGdncSeq());
                    		alliCardContDto.setCprtCardGdncNm(alliCardDtlContDtoXML.getCprtCardGdncNm());
                    		alliCardContDto.setCprtCardBasDesc(alliCardDtlContDtoXML.getCprtCardBasDesc());
                    		alliCardContDto.setCprtCardThumbImgNm(alliCardDtlContDtoXML.getCprtCardThumbImgNm());
                    		alliCardContDto.setCprtCardLargeImgNm(alliCardDtlContDtoXML.getCprtCardLargeImgNm());

                    		List<AlliCardContDtoXML> bnfitList = cprtCardEventService.getCprtCardBnfitGdncDtlXmlList(alliCardContDto);
							if ( bnfitList != null) { // 취약성 277
								for(AlliCardContDtoXML bnfitInfo : bnfitList) {
									if("PCARD107".equals(bnfitInfo.getCprtCardBnfitItemCd())) {
										alliCardContDto.setCprtCardItemNm(bnfitInfo.getCprtCardItemNm());
										alliCardContDto.setCprtCardItemDesc(bnfitInfo.getCprtCardItemDesc());
									}
                                    //연회비 문구 추가
                                    if("PCARD106".equals(bnfitInfo.getCprtCardBnfitItemCd())) {
                                        alliCardContDto.setAnnualFeeItemDesc(bnfitInfo.getCprtCardItemDesc());
                                    }
								}
							}

                    		String realFilePath3 = realDir + fPer + "NMCP_CPRT_CARD_DC_AMT_DTL_" + item.getCprtCardGdncSeq() + ".xml";
                    		File file3 = new File(realFilePath3);

                    		if(file3.exists()) {
                                List<AlliCardContDtoXML> alliCardContDtoXMLList = XmlBindUtil.bindListFromXml(AlliCardContDtoXML.class, file3);

                                List<AlliCardDcAmtDto> alliCardDcAmtDtoList = alliCardContDtoXMLList.stream()
                                        .sorted(Comparator.comparing(dtlInfo -> Integer.parseInt(dtlInfo.getDcAmt()), Comparator.reverseOrder()))
                                        .map(dtlInfo -> {
                                            AlliCardDcAmtDto amtDtlInfo = new AlliCardDcAmtDto();
                                            amtDtlInfo.setDcFormlCd(dtlInfo.getDcFormlCd());
                                            amtDtlInfo.setDcAmt(dtlInfo.getDcAmt());
                                            amtDtlInfo.setDcLimitAmt(dtlInfo.getDcLimitAmt());
                                            amtDtlInfo.setDcSectionStAmt(dtlInfo.getDcSectionStAmt());
                                            amtDtlInfo.setDcSectionEndAmt(dtlInfo.getDcSectionEndAmt());
                                            return amtDtlInfo;
                                        })
                                        .collect(Collectors.toList());

                                alliCardContDto.setAlliCardDcAmtDtoList(alliCardDcAmtDtoList);
                    		}

                    		result.add(alliCardContDto);
                    	}
            		}
            	}
            }
    	} catch(SecurityException e) {
			logger.error(e.toString());
		} catch(Exception e) {
        	logger.error(e.toString());
        }

    	return result;
	}

    @Override
    public List<AlliCardContDtoXML> getCprtCardGdncDtlXmlList(AlliCardContDto alliCardContDto) {
        List<AlliCardContDtoXML> rtnXmlList = null;
        try {
            String fPer = File.separator;
            String realDir = fileuploadBase + File.separator + "cprtCard";
            String realFilePath = realDir + fPer + "NMCP_CPRT_CARD_GDNC_DTL_" + alliCardContDto.getCprtCardGdncSeq() + ".xml";

            File file = new File(realFilePath);
            if(file.exists()) {
                rtnXmlList = XmlBindUtil.bindListFromXml(AlliCardContDtoXML.class, file);

                Iterator<AlliCardContDtoXML> iter = rtnXmlList.iterator();
                while(iter.hasNext()) {
                    AlliCardContDtoXML xmlVo = iter.next();
                    if(!DateTimeUtil.isMiddleDateTime2(xmlVo.getPstngStartDate(), xmlVo.getPstngEndDate())) {
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

    @Override
    public List<AlliCardContDtoXML> getCprtCardLinkXmlList(AlliCardContDto alliCardContDto) {
        List<AlliCardContDtoXML> rtnXmlList = null;
        try {
            String fPer = File.separator;
            String realDir = fileuploadBase + File.separator + "cprtCard";
            String realFilePath = realDir + fPer + "NMCP_CPRT_CARD_GDNC_LINK_DTL_" + alliCardContDto.getCprtCardGdncSeq() + ".xml";

            File file = new File(realFilePath);
            if(file.exists()) {
                rtnXmlList = XmlBindUtil.bindListFromXml(AlliCardContDtoXML.class, file);

                Iterator<AlliCardContDtoXML> iter = rtnXmlList.iterator();
                while(iter.hasNext()) {
                    AlliCardContDtoXML xmlVo = iter.next();
                    if(!DateTimeUtil.isMiddleDateTime2(xmlVo.getPstngStartDate(), xmlVo.getPstngEndDate())) {
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
}
