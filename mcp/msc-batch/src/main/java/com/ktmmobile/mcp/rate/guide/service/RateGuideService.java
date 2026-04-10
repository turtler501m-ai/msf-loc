package com.ktmmobile.mcp.rate.guide.service;

import com.ktmmobile.mcp.alliance.dto.AlliCardDtlContDtoXML;
import com.ktmmobile.mcp.alliance.service.AllianceCardService;
import com.ktmmobile.mcp.rate.guide.dao.RateGuideDao;
import com.ktmmobile.mcp.rate.guide.dto.*;
import jakarta.xml.bind.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.xml.namespace.QName;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;

import static com.ktmmobile.mcp.rate.guide.RateGuideConstants.*;

@Service
public class RateGuideService {
    private static final Logger logger = LoggerFactory.getLogger(RateGuideService.class);

    @Value("${admin.fileupload.path}")
    private String fileUploadPath;

    @Value("${temp.fileupload.path}")
    private String tempUploadPath;

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    private Path nasUploadPath;
    private Path localCreatePath;

    private final RateGuideDao rateGuideDao;
    private final AllianceCardService allianceCardService;

    @PostConstruct
    public void init() throws IOException {
        nasUploadPath = Paths.get(fileUploadPath, "rateAdsvc");
        localCreatePath = Paths.get(tempUploadPath, "rateAdsvc");
        List<Path> paths = Arrays.asList(nasUploadPath, localCreatePath);

        try {
            for (Path path : paths) {
                if (Files.notExists(path)) {
                    Files.createDirectories(path);
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
            Arrays.stream(e.getStackTrace()).forEach(str -> logger.error(str.toString()));
            throw e;
        }
    }

    public RateGuideService(RateGuideDao rateGuideDao, AllianceCardService allianceCardService) {
        this.rateGuideDao = rateGuideDao;
        this.allianceCardService = allianceCardService;
    }

    public boolean createAndUploadAllXmlFiles() {
        try {
            List<File> createdFiles = this.createAllXmlFiles();
            this.uploadFilesToNas(createdFiles);
        } catch (Exception e) {
            logger.error("createAndUploadAllXmlFiles :: {}", e.getMessage());
            Arrays.stream(e.getStackTrace()).forEach(str -> logger.error(str.toString()));
            return false;
        }

        return true;
    }

    public boolean createAndUploadGdncProdRelXmlFile() {
        try {
            File createdFile = this.createGdncProdRelXml();
            if (createdFile == null) {
                return true;
            }

            this.uploadFilesToNas(List.of(createdFile));
        } catch (Exception e) {
            logger.error("createAndUploadGdncProdRelXmlFile :: {}", e.getMessage());
            Arrays.stream(e.getStackTrace()).forEach(str -> logger.error(str.toString()));
            return false;
        }

        return true;
    }

    public boolean createAndUploadRateEventCodePrmtXmlFile() {
        try {
            File createdFile = this.createRateEventCodePrmtXml();
            if (createdFile == null) {
                return true;
            }

            this.uploadFilesToNas(List.of(createdFile));
        } catch (Exception e) {
            logger.error("createAndUploadRateEventCodePrmtXmlFile :: {}", e.getMessage());
            Arrays.stream(e.getStackTrace()).forEach(str -> logger.error(str.toString()));
            return false;
        }

        return true;
    }

    private List<File> createAllXmlFiles() throws JAXBException {
        List<File> createdFiles = new ArrayList<>();
        try {
            List<Integer> rateAdsvcGdncSeqList = rateGuideDao.getRateAdsvcGdncSeqList();

            for (int rateAdsvcGdncSeq : rateAdsvcGdncSeqList) {
                createdFiles.addAll(this.createXmlFilesBySeq(rateAdsvcGdncSeq));
            }
            createdFiles.add(this.createGdncProdRelXml());

            return createdFiles.stream()
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("createAllXmlFiles :: {}", e.getMessage());
            Arrays.stream(e.getStackTrace()).forEach(str -> logger.error(str.toString()));
            throw e;
        }
    }

    private File createGdncProdRelXml() throws JAXBException {

        try {

            RestTemplate restTemplate = new RestTemplate();
            RateAdsvcGdncProdRelXML[] xmlArr = restTemplate.postForObject(apiInterfaceServer + "/rate/selectRateAdsvcGdncProdRelXmlList", null, RateAdsvcGdncProdRelXML[].class);
            List<RateAdsvcGdncProdRelXML> xmlList = (xmlArr == null) ? null : Arrays.asList(xmlArr);

            if(xmlList == null || xmlList.isEmpty()) {
                return null;
            }

            File file = getGuideXmlFile(XML_NAME_GDNC_PROD_REL);
            this.createXmlListFile(xmlList, RateAdsvcGdncProdRelXML.class, file);
            return file;

        } catch (Exception e) {
            logger.error("createGdncProdRelXml :: {}", e.getMessage());
            logger.error(e.getStackTrace()[0].toString());
            throw e;
        }
    }

    //이벤트코드 프로모션 xml
    private File createRateEventCodePrmtXml() throws JAXBException {
        try {
            //이벤트코드프로모션 마스터
            RestTemplate mstRt = new RestTemplate();
            RateEventCodePrmtXML[] mstArr = mstRt.postForObject(apiInterfaceServer + "/event/selectRateEventCodePrmtMstXmlList", null, RateEventCodePrmtXML[].class);
            List<RateEventCodePrmtXML> mstList = (mstArr == null) ? null : Arrays.asList(mstArr);

            if(mstList == null || mstList.isEmpty()) {
                return null;
            }

            HashMap<String, ListXmlWrapper> xmlMap = new HashMap<String, ListXmlWrapper>();

            ListXmlWrapper mstWrapper = new ListXmlWrapper();
            for(RateEventCodePrmtXML vo : mstList) {
                mstWrapper.getItem().add(vo);
            }
            xmlMap.put("eventCodePrmtList", mstWrapper);

            //이벤트코드프로모션 상세
            RestTemplate dtlRt = new RestTemplate();
            RateEventCodePrmtXML[] dtlArr = dtlRt.postForObject(apiInterfaceServer + "/event/selectRateEventCodePrmtXmlList", null, RateEventCodePrmtXML[].class);
            List<RateEventCodePrmtXML> dtlList = (dtlArr == null) ? null : Arrays.asList(dtlArr);

            if(dtlList == null || dtlList.isEmpty()) {
                return null;
            }

            ListXmlWrapper dtlWrapper = new ListXmlWrapper();
            for(RateEventCodePrmtXML vo : dtlList) {
                dtlWrapper.getItem().add(vo);
            }
            xmlMap.put("prmtList", dtlWrapper);

            File file = getGuideXmlFile(XML_NAME_RATE_EVENT_CODE_PRMT);

            MapWrapper mapWrapper = new MapWrapper(xmlMap);

            JAXBContext context = JAXBContext.newInstance(MapWrapper.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(mapWrapper, file);

            return file;

        } catch (Exception e) {
            logger.error("createRateEventCodePrmtXml :: {}", e.getMessage());
            logger.error(e.getStackTrace()[0].toString());
            throw e;
        }
    }


    private List<File> createXmlFilesBySeq(int rateAdsvcGdncSeq) throws JAXBException {
        List<File> createdFiles = new ArrayList<>();

        createdFiles.add(this.createGdncBasXml(rateAdsvcGdncSeq));
        createdFiles.add(this.createBnfitGdncDtlXml(rateAdsvcGdncSeq));
        createdFiles.add(this.createGdncDtlXml(rateAdsvcGdncSeq));
        createdFiles.add(this.createGdncLinkDtlXml(rateAdsvcGdncSeq));
        createdFiles.add(this.createGdncBannerDtlXml(rateAdsvcGdncSeq));
        createdFiles.add(this.createGdncPropertyDtlXml(rateAdsvcGdncSeq));
        createdFiles.add(this.createGdncEffPriceDtlXml(rateAdsvcGdncSeq));

        return createdFiles;
    }

    private File createGdncBasXml(int rateAdsvcGdncSeq) throws JAXBException {
        try {
            RateAdsvcGdncBasXML rateAdsvcGdncBasXml = rateGuideDao.getRateAdsvcGdncBasXml(rateAdsvcGdncSeq);
            if (rateAdsvcGdncBasXml == null) {
                return null;
            }

            File file = getGuideXmlFileWithSeq(XML_NAME_GDNC_BAS, rateAdsvcGdncSeq);
            this.createXmlFile(rateAdsvcGdncBasXml, RateAdsvcGdncBasXML.class, file);
            return file;
        } catch (Exception e) {
            logger.error("createGdncBasXml :: {}", e.getMessage());
            logger.error(e.getStackTrace()[0].toString());
            throw e;
        }
    }

    private File createBnfitGdncDtlXml(int rateAdsvcGdncSeq) throws JAXBException {
        try {
            List<RateAdsvcBnfitGdncDtlXML> rateAdsvcBnfitGdncDtlXmlList = rateGuideDao.getRateAdsvcBnfitGdncDtlXmlList(rateAdsvcGdncSeq);
            if (rateAdsvcBnfitGdncDtlXmlList.isEmpty()) {
                return null;
            }

            File file = getGuideXmlFileWithSeq(XML_NAME_BNFIT_GDNC_DTL, rateAdsvcGdncSeq);
            this.createXmlListFile(rateAdsvcBnfitGdncDtlXmlList, RateAdsvcBnfitGdncDtlXML.class, file);
            return file;
        } catch (Exception e) {
            logger.error("createBnfitGdncDtlXml :: {}", e.getMessage());
            logger.error(e.getStackTrace()[0].toString());
            throw e;
        }
    }

    private File createGdncDtlXml(int rateAdsvcGdncSeq) throws JAXBException {
        try {
            List<RateAdsvcGdncDtlXML> rateAdsvcGdncDtlXmlList = rateGuideDao.getRateAdsvcGdncDtlXmlList(rateAdsvcGdncSeq);
            if (rateAdsvcGdncDtlXmlList.isEmpty()) {
                return null;
            }

            File file = getGuideXmlFileWithSeq(XML_NAME_GDNC_DTL, rateAdsvcGdncSeq);
            this.createXmlListFile(rateAdsvcGdncDtlXmlList, RateAdsvcGdncDtlXML.class, file);
            return file;
        } catch (Exception e) {
            logger.error("createGdncDtlXml :: {}", e.getMessage());
            logger.error(e.getStackTrace()[0].toString());
            throw e;
        }
    }

    private File createGdncLinkDtlXml(int rateAdsvcGdncSeq) throws JAXBException {
        try {
            List<RateAdsvcGdncLinkDtlXML> rateAdsvcGdncLinkDtlXmlList = rateGuideDao.getRateAdsvcGdncLinkDtlXmlList(rateAdsvcGdncSeq);
            if (rateAdsvcGdncLinkDtlXmlList.isEmpty()) {
                return null;
            }

            File file = getGuideXmlFileWithSeq(XML_NAME_GDNC_LINK_DTL, rateAdsvcGdncSeq);
            this.createXmlListFile(rateAdsvcGdncLinkDtlXmlList, RateAdsvcGdncLinkDtlXML.class, file);
            return file;
        } catch (Exception e) {
            logger.error("createGdncLinkDtlXml :: {}", e.getMessage());
            logger.error(e.getStackTrace()[0].toString());
            throw e;
        }
    }

    private File createGdncBannerDtlXml(int rateAdsvcGdncSeq) throws JAXBException {
        try {
            List<RateGdncBannerDtlDTO> rateAdsvcGdncBannerDtlXmlList = rateGuideDao.getRateAdsvcGdncBannerDtlXmlList(rateAdsvcGdncSeq);
            if (rateAdsvcGdncBannerDtlXmlList.isEmpty()) {
                return null;
            }

            File file = getGuideXmlFileWithSeq(XML_NAME_GDNC_BANNER_DTL, rateAdsvcGdncSeq);
            this.createXmlListFile(rateAdsvcGdncBannerDtlXmlList, RateGdncBannerDtlDTO.class, file);
            return file;
        } catch (Exception e) {
            logger.error("createGdncBannerDtlXml :: {}", e.getMessage());
            logger.error(e.getStackTrace()[0].toString());
            throw e;
        }
    }

    private File createGdncPropertyDtlXml(int rateAdsvcGdncSeq) throws JAXBException {
        try {
            List<RateGdncPropertyDtlDTO> rateGdncPropertyList = rateGuideDao.getRateGdncPropertyList(rateAdsvcGdncSeq);
            File file = getGuideXmlFileWithSeq(XML_NAME_GDNC_PROPERTY_DTL, rateAdsvcGdncSeq);
            if (rateGdncPropertyList.isEmpty()) {
                if (file.exists()) {
                    file.delete();
                }
                return null;
            }

            AlliCardDtlContDtoXML alliCardDtlContDtoXML = allianceCardService.getPlanBannerEnabledAllianceCardXml();
            if (alliCardDtlContDtoXML != null) {
                rateGdncPropertyList.stream()
                        .filter(rateProperty -> RATE_PROPERTY_CARD_DISCOUNT.equals(rateProperty.getPropertyCode()))
                        .forEach(rateProperty -> {
                            // Null 체크를 강화하여 안전성 확보
                            String planBannerTitle = alliCardDtlContDtoXML.getPlanBannerTitle();
                            String planBannerText = alliCardDtlContDtoXML.getPlanBannerText();

                            if (planBannerTitle != null) {
                                rateProperty.setPropertyCodeNm(planBannerTitle);
                            }
                            if (planBannerText != null) {
                                rateProperty.setPropertySbst(planBannerText);
                            }
                        });
            }

            this.createXmlListFile(rateGdncPropertyList, RateGdncPropertyDtlDTO.class, file);
            return file;
        } catch (Exception e) {
            logger.error("createGdncPropertyDtlXml :: {}", e.getMessage());
            logger.error(e.getStackTrace()[0].toString());
            throw e;
        }
    }

    private File createGdncEffPriceDtlXml(int rateAdsvcGdncSeq) throws JAXBException {
        try {
            RateGdncEffPriceDtlDTO rateGdncEffPriceDtlList = rateGuideDao.getRateGdncEffPriceList(rateAdsvcGdncSeq);
            if (rateGdncEffPriceDtlList == null) {
                return null;
            }

            File file = getGuideXmlFileWithSeq(XML_NAME_GDNC_EFF_PRICE_DTL, rateAdsvcGdncSeq);
            this.createXmlFile(rateGdncEffPriceDtlList, RateGdncEffPriceDtlDTO.class, file);
            return file;
        } catch (Exception e) {
            logger.error("createGdncEffPriceDtlXml :: {}", e.getMessage());
            logger.error(e.getStackTrace()[0].toString());
            throw e;
        }
    }

    private File getGuideXmlFileWithSeq(String name, int rateAdsvcGdncSeq) {
        return localCreatePath.resolve(name + "_" + rateAdsvcGdncSeq + ".xml").toFile();
    }

    private File getGuideXmlFile(String name) {
        return localCreatePath.resolve(name + ".xml").toFile();
    }

    private void uploadFilesToNas(List<File> createdFiles) throws IOException {
        for (File file : createdFiles) {
            if (file == null || !file.exists()) {
                continue;
            }

            Path targetPath = nasUploadPath.resolve(file.getName());
            try {
                Files.copy(file.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                logger.error("uploadFilesToNas : {}", e.getMessage());
                logger.error(e.getStackTrace()[0].toString());
                throw e;
            }
        }
    }

    /**
     * 설명 : vo 형식의 xml 파일을 생성한다.
     * @Author : 강채신
     * @Date : 2021.12.30
     * @param <T>
     * @param obj
     * @param clz
     * @param file
     * @throws JAXBException
     */
    public <T> void createXmlFile(Object obj, Class<?> clz, File file) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(clz);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        logger.error("createXmlFile :: {} :: Before fileOutputStream open", file.getName());
        try (OutputStream out = new FileOutputStream(file)) {
            logger.error("createXmlFile :: {} :: After fileOutputStream open", file.getName());

            logger.error("createXmlFile :: {} :: Before marshal", file.getName());
            marshaller.marshal(obj, out);
            logger.error("createXmlFile :: {} :: After marshal", file.getName());

        } catch (IOException e) {
            logger.error("createXmlFile :: {} :: {}", file.getName(), e.getMessage());
            logger.error(e.getStackTrace()[0].toString());
        }
    }

    /**
     * 설명 : list 형식의 xml 파일을 생성한다.
     * @Author : 강채신
     * @Date : 2021.12.30
     * @param <T>
     * @param list
     * @param clz
     * @param file
     * @throws JAXBException
     */
    public <T> void createXmlListFile(List<T> list, Class<?> clz, File file) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(XmlWrapper.class, clz);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        QName qName = new QName("items");
        XmlWrapper wrapper = new XmlWrapper(list);
        JAXBElement<XmlWrapper> jaxbElement = new JAXBElement<XmlWrapper>(qName, XmlWrapper.class, wrapper);

        logger.error("createXmlListFile :: {} :: Before fileOutputStream open", file.getName());
        try (OutputStream out = new FileOutputStream(file)) {
            logger.error("createXmlListFile :: {} :: After fileOutputStream open", file.getName());

            logger.error("createXmlListFile :: {} :: Before marshal", file.getName());
            marshaller.marshal(jaxbElement, out);
            logger.error("createXmlListFile :: {} :: After marshal", file.getName());

        } catch (IOException e) {
            logger.error("createXmlListFile :: {} :: {}", file.getName(), e.getMessage());
            logger.error(e.getStackTrace()[0].toString());
        }
    }
}
