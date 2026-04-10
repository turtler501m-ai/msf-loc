package com.ktmmobile.mcp.event.service;

import com.ktmmobile.mcp.common.util.DateTimeUtil;
import com.ktmmobile.mcp.common.util.XmlBindUtil;
import com.ktmmobile.mcp.cprt.dto.*;
import jakarta.xml.bind.JAXBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class CprtCardEventServiceImpl implements CprtCardEventService {

	private static Logger logger = LoggerFactory.getLogger(CprtCardEventServiceImpl.class);

	/** 파일업로드 경로*/
    @Value("${common.fileupload.base}")
    private String fileuploadBase;

    /** 파일업로드 접근가능한 웹경로 */
    @Value("${common.fileupload.web}")
    private String fileuploadWeb;

    /**
	 * 설명 : 카테고리 조회
	 * @Author : 강채신
	 * @Date : 2021.12.30
	 * @return
	 */
	@Override
	public AliasMapWrapper getAliasMapWrapper() {
		String fPer = File.separator;
    	String realDir = fileuploadBase + File.separator + "cprtCard";
    	AliasMapWrapper aliasMapWrapper = null;

    	try {
			String realFilePath = realDir + fPer + "NMCP_CPRT_CARD_CTG_BAS.xml";

			aliasMapWrapper = XmlBindUtil.bindFromXml(AliasMapWrapper.class, realFilePath);
    	} catch(JAXBException e) {
			logger.error(e.toString());
		} catch(Exception e) {
        	logger.error(e.toString());
        }

    	return aliasMapWrapper;
	}

	/**
	 * 설명 : 제휴카드 안내 목록 조회
	 * @Author : 강채신
	 * @Date : 2021.12.30
	 * @return
	 * @throws ParseException
	 */
	@Override
	public List<AlliCardContDto> getProdXmlList() throws ParseException {
		List<AlliCardContDto> rtnList = new ArrayList<AlliCardContDto>();
		AliasMapWrapper aliasMapWrapper = getAliasMapWrapper();

        Map<String, AliasListXmlWrapper> prodMapXml = aliasMapWrapper.getItem();
        AliasListXmlWrapper prodListXml = prodMapXml.get("proList");
        List<AlliCardCtgaContXML> prodList = prodListXml.getItem();

    	for(AlliCardCtgaContXML item : prodList) {
        	if(DateTimeUtil.isMiddleDate(item.getPstngStartDate(), item.getPstngEndDate())) {

        		AlliCardContDto alliCardContDto = new AlliCardContDto();

        		alliCardContDto.setCprtCardGdncNm(item.getCprtCardGdncNm());
        		alliCardContDto.setCprtCardCtgCd(item.getCprtCardCtgCd());
        		alliCardContDto.setCprtCardCtgNm(item.getCprtCardCtgNm());
        		alliCardContDto.setCprtCardCtgBasDesc(item.getCprtCardCtgBasDesc());
        		alliCardContDto.setCprtCardCtgDtlDesc(item.getCprtCardCtgDtlDesc());
        		alliCardContDto.setUpCprtCardCtgCd(item.getUpCprtCardCtgCd());
        		alliCardContDto.setCprtCardGdncSeq(Integer.parseInt(item.getCprtCardGdncSeq()));
        		alliCardContDto.setDepthKey(item.getDepthKey());
        		alliCardContDto.setSortOdrg(item.getSortOdrg());
        		alliCardContDto.setUseYn(item.getUseYn());
        		alliCardContDto.setPstngStartDate(item.getPstngStartDate());
        		alliCardContDto.setPstngEndDate(item.getPstngEndDate());

            	rtnList.add(alliCardContDto);
        	}
        }

    	return rtnList;
	}

	/**
	 * 설명 : 카테고리에 해당하는 제휴카드 안내 목록 조회
	 * @Author : 강채신
	 * @Date : 2021.12.30
	 * @param alliCardContDto
	 * @return
	 * @throws ParseException
	 */
	@Override
	public List<AlliCardContDto> getProdXmlList(AlliCardContDto alliCardContDto) throws ParseException {
		List<AlliCardContDto> rtnList = new ArrayList<AlliCardContDto>();
		AliasMapWrapper aliasMapWrapper = getAliasMapWrapper();

        Map<String, AliasListXmlWrapper> prodMapXml = aliasMapWrapper.getItem();
        AliasListXmlWrapper prodListXml = prodMapXml.get("proList");
        List<AlliCardCtgaContXML> prodList = prodListXml.getItem();

    	for(AlliCardCtgaContXML item : prodList) {
        	if((alliCardContDto.getCprtCardCtgCd()).equals(item.getCprtCardCtgCd())
        			&& DateTimeUtil.isMiddleDate(item.getPstngStartDate(), item.getPstngEndDate())
        			) {

        		AlliCardContDto alliCardCont = new AlliCardContDto();

        		alliCardCont.setCprtCardGdncNm(item.getCprtCardGdncNm());
        		alliCardCont.setCprtCardCtgCd(item.getCprtCardCtgNm());
        		alliCardCont.setCprtCardCtgNm(item.getCprtCardCtgNm());
        		alliCardCont.setCprtCardCtgBasDesc(item.getCprtCardCtgBasDesc());
        		alliCardCont.setCprtCardCtgDtlDesc(item.getCprtCardCtgDtlDesc());
        		alliCardCont.setUpCprtCardCtgCd(item.getUpCprtCardCtgCd());
        		alliCardCont.setCprtCardGdncSeq(Integer.parseInt(item.getCprtCardGdncSeq()));
        		alliCardCont.setDepthKey(item.getDepthKey());
        		alliCardCont.setSortOdrg(item.getSortOdrg());
        		alliCardCont.setUseYn(item.getUseYn());
        		alliCardCont.setPstngStartDate(item.getPstngStartDate());
        		alliCardCont.setPstngEndDate(item.getPstngEndDate());

            	rtnList.add(alliCardCont);
        	}
        }

    	return rtnList;
	}

	/**
	 * 설명 : 기본 제휴카드정보조회
	 * @Author : 강채신
	 * @Date : 2021.12.30
	 * @param alliCardContDto
	 * @return
	 */
	@Override
	public AlliCardDtlContDtoXML getCprtCardGdncBasXml(AlliCardContDto alliCardContDto) {
		AlliCardDtlContDtoXML xmlInfo = null;
    	try {
    		String fPer = File.separator;
        	String realDir = fileuploadBase + File.separator + "cprtCard";
        	String realFilePath = realDir + fPer + "NMCP_CPRT_CARD_GDNC_BAS_" + alliCardContDto.getCprtCardGdncSeq() + ".xml";

			File file = new File(realFilePath);
			if(file.exists()) {
		        xmlInfo = XmlBindUtil.bindFromXml(AlliCardDtlContDtoXML.class, file);
			}
    	} catch(JAXBException e) {
			logger.error(e.toString());
		} catch(Exception e) {
        	logger.error(e.toString());
        }

		return xmlInfo;
	}

	/**
	 * 설명 : 제휴카드 혜택 상세 xml조회
	 * @Author : 강채신
	 * @Date : 2021.12.30
	 * @param alliCardContDto
	 * @return
	 */
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

	/**
	 * 설명 : 제휴카드 혜택 상세 xml조회
	 * @Author : 강채신
	 * @Date : 2021.12.30
	 * @param alliCardContDto
	 * @return
	 */
	@Override
	public List<AlliCardContDtoXML> getCprtCardBnfitGdncDtlXmlList(AlliCardContDto alliCardContDto) {
		List<AlliCardContDtoXML> rtnXmlList = null;
    	try {
    		String fPer = File.separator;
        	String realDir = fileuploadBase + File.separator + "cprtCard";
			String realFilePath = realDir + fPer + "NMCP_CPRT_CARD_BNFIT_GDNC_DTL_" + alliCardContDto.getCprtCardGdncSeq() + ".xml";

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

	/**
	 * 설명 : 제휴카드 링크상세 xml조회
	 * @Author : 강채신
	 * @Date : 2021.12.30
	 * @param alliCardContDto
	 * @return
	 */
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

	/**
	 * 설명 : 제휴카드 할인금액조회
	 * @Author : 강채신
	 * @Date : 2021.12.30
	 * @param alliCardContDto
	 * @return
	 */
	@Override
	public List<AlliCardContDtoXML> getCprtCardDcAmtXmlList(AlliCardContDto alliCardContDto) {
		List<AlliCardContDtoXML> rtnXmlList = null;
    	try {
    		String fPer = File.separator;
        	String realDir = fileuploadBase + File.separator + "cprtCard";
			String realFilePath = realDir + fPer + "NMCP_CPRT_CARD_DC_AMT_DTL_" + alliCardContDto.getCprtCardGdncSeq() + ".xml";

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
}
