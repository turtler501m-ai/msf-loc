package com.ktmmobile.mcp.search.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchStatusException;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.ktmmobile.mcp.search.dto.SearchDto;
import com.ktmmobile.mcp.search.service.SearchSvc;

@Controller
@EnableAsync
@EnableScheduling
public class SearchController{

    private static final Logger logger = LoggerFactory.getLogger(SearchController.class);

    static final String SEARCH_INDEX1 = "menu";
    static final String SEARCH_INDEX2 = "phone";
    static final String SEARCH_INDEX3 = "rate";
    static final String SEARCH_INDEX4 = "adsvc";
    static final String SEARCH_INDEX5 = "event";
    static final String SEARCH_INDEX6 = "board";

    @Value("${elastic.url}")
    private String elasticUrl;

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

	@Autowired
    private SearchSvc searchSvc;

	@RequestMapping("/searchAll")
	@ResponseBody
	public String searchAll() {
	    try {
			menuIndexingBatch();
			phoneIndexingBatch();
			rateIndexingBatch();
			adsvcIndexingBatch();
			eventIndexingBatch();
			boardIndexingBatch();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "searchController";
	}

	@RequestMapping("/search")
	public String search() {
		insertSearchPplrWord();

		return "searchController";
	}



	/**
	 * ElasticSearch - 색인 메뉴
	 */
	@Scheduled(cron = "0 0 1 * * *") // 일 1회	 01:00:00	"0 0 1 * * *"
	public void menuIndexingBatch() {

		RestHighLevelClient client = createConnection();

		try {
			DeleteByQueryRequest request = new DeleteByQueryRequest(SEARCH_INDEX1);
			request.setIndicesOptions(IndicesOptions.STRICT_EXPAND_OPEN_FORBID_CLOSED_IGNORE_THROTTLED);
			request.setQuery(QueryBuilders.matchAllQuery());
			BulkByScrollResponse response = client.deleteByQuery(request, RequestOptions.DEFAULT);

			List<SearchDto> retMapList = searchSvc.selectMenuIndexing();
			long i= 0;
			for (SearchDto el : retMapList) {
				IndexRequest indexRequest = new IndexRequest(SEARCH_INDEX1)
						.id(SEARCH_INDEX1 + i)
						.source("menuSeq", el.getMenuSeq(),
								"menuCode", el.getMenuCode(),
								"menuNm", el.getMenuNm(),
								"groupKey", el.getGroupKey(),
								"prntsKey", el.getPrntsKey(),
								"depthKey", el.getDepthKey(),
								"urlAdr", el.getUrlAdr(),
								"repUrlSeq", el.getRepUrlSeq(),
								"chatbotTipSbst", el.getChatbotTipSbst(),
								"menuDesc", el.getMenuDesc(),
								"attYn", el.getAttYn(),
								"sortKey", el.getSortKey()
								);

				try {
					client.index(indexRequest, RequestOptions.DEFAULT);
				} catch (IOException | ElasticsearchStatusException e) {
					logger.error("indexRequest : " + e);
				}
				i++;
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.getMessage();
		}finally {
			if(client != null)
				try {
					client.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

	}


	/**
	 * ElasticSearch - 색인 휴대폰
	 */
	@Scheduled(cron = "0 5 1 * * *") // 일 1회	 01:05:00
	public void phoneIndexingBatch() {

		RestHighLevelClient client = createConnection();

		try {
			DeleteByQueryRequest request = new DeleteByQueryRequest(SEARCH_INDEX2);
			request.setIndicesOptions(IndicesOptions.STRICT_EXPAND_OPEN_FORBID_CLOSED_IGNORE_THROTTLED);
			request.setQuery(QueryBuilders.matchAllQuery());
			BulkByScrollResponse response = client.deleteByQuery(request, RequestOptions.DEFAULT);

			RestTemplate restTemplate = new RestTemplate();

			SearchDto[] resultList = restTemplate.postForObject(apiInterfaceServer + "/common/phoneIndexingBatch", "", SearchDto[].class);
	    	List<SearchDto> retMapList = Arrays.asList(resultList);

			long i= 0;
			for (SearchDto el : retMapList) {
				IndexRequest indexRequest = new IndexRequest(SEARCH_INDEX2)
						.id(SEARCH_INDEX2 + i)
						.source(
							    "prodId", el.getProdId(),
							    "prodCtgId", el.getProdCtgId(),
							    "prodCtgName", el.getProdCtgName(),
							    "prodNm", el.getProdNm(),
							    "makrCd", el.getMakrCd(),
							    "rprsPrdtId", el.getRprsPrdtId(),
							    "showYn", el.getShowYn(),
							    "saleYn", el.getSaleYn(),
							    "ofwDate", el.getOfwDate(),
							    "listShowText", el.getListShowText(),
							    "listShowOptn", el.getListShowOptn(),
							    "apdDesc1", el.getApdDesc1(),
							    "apdDesc2", el.getApdDesc2(),
							    "apdDesc3", el.getApdDesc3(),
							    "stckTypeTop", el.getStckTypeTop(),
							    "stckTypeTail", el.getStckTypeTail(),
							    "sntyProdDesc", el.getSntyProdDesc(),
							    "sntyNet", el.getSntyNet(),
							    "sntyDisp", el.getSntyDisp(),
							    "sntySize", el.getSntySize(),
							    "sntyWeight", el.getSntyWeight(),
							    "sntyMemr", el.getSntyMemr(),
							    "sntyBtry", el.getSntyBtry(),
							    "sntyOs", el.getSntyOs(),
							    "sntyWaitTime", el.getSntyWaitTime(),
							    "sntyCam", el.getSntyCam(),
							    "sntyVideTlk", el.getSntyVideTlk(),
							    "sntyProdNm", el.getSntyProdNm(),
							    "sntyRelMonth", el.getSntyRelMonth(),
							    "sntyColor", el.getSntyColor(),
							    "sntyMaker", el.getSntyMaker(),
							    "sntyModelId", el.getSntyModelId(),
							    "mnfctNm", el.getMnfctNm(),
							    "zipcd", el.getZipcd(),
							    "addr", el.getAddr(),
							    "dtlAddr", el.getDtlAddr(),
							    "telnum", el.getTelnum(),
							    "email", el.getEmail(),
							    "makrNm", el.getMakrNm(),
							    "shandYn", el.getShandYn(),
							    "inventoryAmt", el.getInventoryAmt(),
							    "salePrice", el.getSalePrice(),
							    "prodGrade", el.getProdGrade(),
							    "shandType", el.getShandType(),
							    "shandTypeName", el.getShandTypeName(),
							    "recommendRate", el.getRecommendRate(),
							    "usedWarranty", el.getUsedWarranty(),
							    "usedWarrantyName", el.getUsedWarrantyName(),
							    "sntyColorCd", el.getSntyColorCd(),
							    "imgTypeCd", el.getImgTypeCd(),
							    "imgPath", el.getImgPath(),
							    "hndsetModelNm", el.getHndsetModelNm(),
							    "hndsetModelId", el.getHndsetModelId(),
							    "showOdrg", el.getShowOdrg()
							);

				try {
					client.index(indexRequest, RequestOptions.DEFAULT);
				} catch (IOException | ElasticsearchStatusException e) {
					logger.error("indexRequest : " + e);
	    		}
				i++;
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.getMessage();
		}finally {
			if(client != null)
				try {
					client.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}



	}


	/**
	 * ElasticSearch - 색인 요금제
	 */
	@Scheduled(cron = "0 10 1 * * *") // 일 1회	 01:10:00
	public void rateIndexingBatch() {

		RestHighLevelClient client = createConnection();

		try {
			DeleteByQueryRequest request = new DeleteByQueryRequest(SEARCH_INDEX3);
			request.setIndicesOptions(IndicesOptions.STRICT_EXPAND_OPEN_FORBID_CLOSED_IGNORE_THROTTLED);
			request.setQuery(QueryBuilders.matchAllQuery());
			BulkByScrollResponse response = client.deleteByQuery(request, RequestOptions.DEFAULT);

			List<SearchDto> retMapList = searchSvc.selectRateIndexing();
			long i= 0;
			for (SearchDto el : retMapList) {
				IndexRequest indexRequest = new IndexRequest(SEARCH_INDEX3)
						.id(SEARCH_INDEX3 + i)
						.source(
								"rateAdsvcGdncSeq", el.getRateAdsvcGdncSeq(),
								"rateDivCd", el.getRateDivCd(),
								"rateAdsvcNm", el.getRateAdsvcNm(),
								"rateAdsvcApdNm", el.getRateAdsvcApdNm(),
								"rateAdsvcBasDesc", el.getRateAdsvcBasDesc(),
								"mmBasAmtDesc", el.getMmBasAmtDesc(),
								"mmBasAmtVatDesc", el.getMmBasAmtVatDesc(),
								"promotionAmtDesc", el.getPromotionAmtDesc(),
								"promotionAmtVatDesc", el.getPromotionAmtVatDesc(),
								"rateAdsvcCtgCd", el.getRateAdsvcCtgCd(),
								"rateAdsvcCtgNm", el.getRateAdsvcCtgNm(),
								"rateAdsvcCtgBasDesc", el.getRateAdsvcCtgBasDesc(),
								"rateAdsvcCtgDtlDesc", el.getRateAdsvcCtgDtlDesc(),
								"rateAdsvcProdRelSeq", el.getRateAdsvcProdRelSeq(),
								"rateAdsvcCd", el.getRateAdsvcCd(),
								"rateAdsvcItemCd", el.getRateAdsvcItemCd(),
								"rateAdsvcItemNm1", el.getRateAdsvcItemNm1(),
								"rateAdsvcItemSbst", el.getRateAdsvcItemSbst(),
								"rateAdsvcBnfitItemCd", el.getRateAdsvcBnfitItemCd(),
								"rateAdsvcItemNm2", el.getRateAdsvcItemNm2(),
								"rateAdsvcItemDesc", el.getRateAdsvcItemDesc(),
								"rateAdsvcItemApdDesc", el.getRateAdsvcItemApdDesc(),
								"sortOdrg1", el.getSortOdrg1(),
								"sortOdrg2", el.getSortOdrg2()
							);

				try {
					client.index(indexRequest, RequestOptions.DEFAULT);
				} catch (IOException | ElasticsearchStatusException e) {
					logger.error("indexRequest : " + e);
	    		}
				i++;
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.getMessage();
		}finally {
			if(client != null)
				try {
					client.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}


	}


	/**
	 * ElasticSearch - 색인 부가서비스
	 */
	@Scheduled(cron = "0 15 1 * * *") //일 1회	01:15:00
	public void adsvcIndexingBatch() {

		RestHighLevelClient client = createConnection();

		try {
			DeleteByQueryRequest request = new DeleteByQueryRequest(SEARCH_INDEX4);
			request.setIndicesOptions(IndicesOptions.STRICT_EXPAND_OPEN_FORBID_CLOSED_IGNORE_THROTTLED);
			request.setQuery(QueryBuilders.matchAllQuery());
			BulkByScrollResponse response = client.deleteByQuery(request, RequestOptions.DEFAULT);

			List<SearchDto> retMapList = searchSvc.selectAdsvcIndexing();
			long i= 0;
			for (SearchDto el : retMapList) {
				IndexRequest indexRequest = new IndexRequest(SEARCH_INDEX4)
						.id(SEARCH_INDEX4 + i)
						.source(
								"rateAdsvcGdncSeq", el.getRateAdsvcGdncSeq(),
								"rateAdsvcNm", el.getRateAdsvcNm(),
								"rateAdsvcApdNm", el.getRateAdsvcApdNm(),
								"rateAdsvcBasDesc", el.getRateAdsvcBasDesc(),
								"mmBasAmtDesc", el.getMmBasAmtDesc(),
								"mmBasAmtVatDesc", el.getMmBasAmtVatDesc(),
								"promotionAmtDesc", el.getPromotionAmtDesc(),
								"promotionAmtVatDesc", el.getPromotionAmtVatDesc(),
								"rateAdsvcCtgCd", el.getRateAdsvcCtgCd(),
								"rateAdsvcCtgNm", el.getRateAdsvcCtgNm(),
								"rateAdsvcCtgBasDesc", el.getRateAdsvcCtgBasDesc(),
								"rateAdsvcCtgDtlDesc", el.getRateAdsvcCtgDtlDesc(),
								"rateAdsvcProdRelSeq", el.getRateAdsvcProdRelSeq(),
								"rateAdsvcCd", el.getRateAdsvcCd(),
								"rateAdsvcItemCd", el.getRateAdsvcItemCd(),
								"rateAdsvcItemNm", el.getRateAdsvcItemNm(),
								"rateAdsvcItemSbst", el.getRateAdsvcItemSbst(),
								"sortOdrg1", el.getSortOdrg1(),
								"sortOdrg2", el.getSortOdrg2()
							);

				try {
					client.index(indexRequest, RequestOptions.DEFAULT);
				} catch (IOException | ElasticsearchStatusException e) {
					logger.error("indexRequest : " + e);
	    		}
				i++;
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.getMessage();
		}finally {
			if(client != null)
				try {
					client.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}


	}


	/**
	 * ElasticSearch - 색인 이벤트
	 */
	@Scheduled(cron = "0 20 1 * * *") // 일 1회	 01:20:00
	public void eventIndexingBatch() {

		RestHighLevelClient client = createConnection();

		try {
			DeleteByQueryRequest request = new DeleteByQueryRequest(SEARCH_INDEX5);
			request.setIndicesOptions(IndicesOptions.STRICT_EXPAND_OPEN_FORBID_CLOSED_IGNORE_THROTTLED);
			request.setQuery(QueryBuilders.matchAllQuery());
			BulkByScrollResponse response = client.deleteByQuery(request, RequestOptions.DEFAULT);

			List<SearchDto> retMapList = searchSvc.selectEventIndexing();
			long i= 0;
			for (SearchDto el : retMapList) {
				IndexRequest indexRequest = new IndexRequest(SEARCH_INDEX5)
						.id(SEARCH_INDEX5 + i)
						.source(
								"ntcartSeq", el.getNtcartSeq(),
								"sbstCtg", el.getSbstCtg(),
								"sbstCtgNm", el.getSbstCtgNm(),
								"ntcartSubject", el.getNtcartSubject(),
								"listImg", el.getListImg(),
								"thumbImgNm", el.getThumbImgNm(),
								"thumbImgDesc", el.getThumbImgDesc(),
								"mobileListImgNm", el.getMobileListImgNm(),
								"imgDesc", el.getImgDesc(),
								"ntcartSbst", el.getNtcartSbst(),
								"dtlCdNm", el.getDtlCdNm(),
								"eventStartDt", el.getEventStartDt(),
								"eventEndDt", el.getEventEndDt(),
								"eventStartDate", el.getEventStartDate(),
								"eventEndDate", el.getEventEndDate(),
								"startHour", el.getStartHour(),
								"endHour", el.getEndHour(),
								"linkTarget", el.getLinkTarget(),
								"linkUrlAdr", el.getLinkUrlAdr(),
								"eventUrlAdr", el.getEventUrlAdr(),
								"ntcartHitCnt", el.getNtcartHitCnt(),
								"eventAdd1Yn", el.getEventAdd1Yn(),
								"eventAdd1Sbst", el.getEventAdd1Sbst(),
								"eventAdd2Yn", el.getEventAdd2Yn(),
								"eventAdd3Yn", el.getEventAdd3Yn(),
								"verificationUrl", el.getVerificationUrl(),
								"plnSmallTitle", el.getPlnSmallTitle(),
								"plnContent", el.getPlnContent(),
								"snsSbst", el.getSnsSbst(),
								"ntcartSbst2", el.getNtcartSbst2(),
								"plnContent2", el.getPlnContent2()
							);

				try {
					client.index(indexRequest, RequestOptions.DEFAULT);
				} catch (IOException | ElasticsearchStatusException e) {
					logger.error("indexRequest : " + e);
	    		}
				i++;
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.getMessage();
		}finally {
			if(client != null)
				try {
					client.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}


	}


	/**
	 * ElasticSearch - 색인 자주묻는 질문
	 */
	@Scheduled(cron = "0 25 1 * * *") // 일 1회	 01:25:00
	public void boardIndexingBatch() {

		RestHighLevelClient client = createConnection();

		try {
			DeleteByQueryRequest request = new DeleteByQueryRequest(SEARCH_INDEX6);
			request.setIndicesOptions(IndicesOptions.STRICT_EXPAND_OPEN_FORBID_CLOSED_IGNORE_THROTTLED);
			request.setQuery(QueryBuilders.matchAllQuery());
			BulkByScrollResponse response = client.deleteByQuery(request, RequestOptions.DEFAULT);

			List<SearchDto> retMapList = searchSvc.selectBoardIndexing();
			long i= 0;
			for (SearchDto el : retMapList) {
				IndexRequest indexRequest = new IndexRequest(SEARCH_INDEX6)
						.id(SEARCH_INDEX6 + i)
						.source(
								"sbstCtg", el.getSbstCtg(),
								"sbstSubCtgCd", el.getSbstSubCtgCd(),
								"dtlCdNm", el.getDtlCdNm(),
								"boardSeq", el.getBoardSeq(),
								"boardSubject", el.getBoardSubject(),
								"boardContents", el.getBoardContents(),
								"boardHitCnt", el.getBoardHitCnt()
							);

				try {
					client.index(indexRequest, RequestOptions.DEFAULT);
				} catch (IOException | ElasticsearchStatusException e) {
					logger.error("indexRequest : " + e);
	    		}
				i++;
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.getMessage();
		}finally {
			if(client != null)
				try {
					client.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}


	}

    public RestHighLevelClient createConnection() {

        //return new RestHighLevelClient(RestClient.builder(new HttpHost("10.21.28.100", 9200, "http")));
        return new RestHighLevelClient(RestClient.builder(new HttpHost(elasticUrl, 9200, "http")));
    }



	/**
	 * 인기검색어 추출
	 */
	@Scheduled(cron = "0 30 1 * * *") // 일 1회	 01:30:00
	@Transactional
	public void insertSearchPplrWord() {
		try {
			int deleteResult = searchSvc.deleteSearchPplrWord();
			int insertResult = searchSvc.insertSearchPplrWord();
		}catch(Exception e) {
			logger.error("insertSearchPplrWord() Exception = {}", e.getMessage());

		}
	}


}
