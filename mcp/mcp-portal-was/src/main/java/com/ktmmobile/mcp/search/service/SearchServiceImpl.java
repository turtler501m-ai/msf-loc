package com.ktmmobile.mcp.search.service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ktmmobile.mcp.common.util.StringUtil;
import com.ktmmobile.mcp.common.util.XmlBindUtil;
import com.ktmmobile.mcp.rate.dto.RateAdsvcGdncDtlXML;
import com.ktmmobile.mcp.search.dao.SearchDao;
import com.ktmmobile.mcp.search.dto.SearchCrrlDto;
import com.ktmmobile.mcp.search.dto.SearchParamDto;
import com.ktmmobile.mcp.search.dto.SearchPplrDto;
import com.ktmmobile.mcp.search.dto.SearchRecomDto;
import jakarta.xml.bind.JAXBException;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.WarningsHandler;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ktmmobile.mcp.rate.RateAdsvcGdncUtil.getRateAdsvcGdncPath;

@Service
public class SearchServiceImpl implements SearchService {

	private static Logger logger = LoggerFactory.getLogger(SearchService.class);

    @Value("${elastic.url}")
    private String elasticUrl;

    /** 파일업로드 경로*/
    @Value("${common.fileupload.base}")
    private String fileuploadBase;

	@Value("${SERVER_NAME}")
	private String serverName;

	@Autowired
	SearchDao searchDao;

	@Override
	public List<SearchCrrlDto> getRelationWordListAjax(String srchText) {
		return searchDao.getRelationWordListAjax(srchText);
	}

	@Override
	public List<SearchRecomDto> getRecommendWordListAjax() {
		return searchDao.getRecommendWordListAjax();
	}

	@Override
	public List<SearchPplrDto> getPopularWordListAjax() {
		return searchDao.getPopularWordListAjax();
	}

	@Override
	public int insertSearchTxt(SearchParamDto searchParamDto) {
		return searchDao.insertSearchTxt(searchParamDto);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getSearchResult(SearchParamDto searchParamDto, HttpServletRequest request) {

		//일반 더보기 - 5개씩 뿌려주기
	    Integer DISPLAY_COUNT = 5;

	    // menu
		String[] HIGHLIGHT_RANGE1 = {"menuNm", "menuDesc"};

	    // menu
	    String[] INCLUDE_FIELDS1 = new String[] {"menuSeq","menuCode","menuNm","groupKey","prntsKey","depthKey","urlAdr","repUrlSeq", "chatbotTipSbst","menuDesc","attYn", "sortKey"};

	    // phone
	    String[] INCLUDE_FIELDS2 = new String[] {"prodId","prodCtgId","prodCtgName","prodNm","makrCd","rprsPrdtId",
	    		"showYn","saleYn","ofwDate","listShowText","listShowOptn","apdDesc1","apdDesc2","apdDesc3","stckTypeTop",
	    		"stckTypeTail","sntyProdDesc","sntyNet","sntyDisp","sntySize","sntyWeight","sntyMemr","sntyBtry","sntyOs",
	    		"sntyWaitTime","sntyCam","sntyVideTlk","sntyProdNm","sntyRelMonth","sntyColor","sntyMaker","sntyModelId",
	    		"mnfctNm","zipcd","addr","dtlAddr","telnum","email","makrNm","shandYn","inventoryAmt","salePrice","prodGrade",
	    		"shandType","shandTypeName","recommendRate","usedWarranty","usedWarrantyName","sntyColorCd","imgTypeCd","imgPath",
	    		"hndsetModelNm","hndsetModelId","showOdrg"
	    };

	    // rate
	    String[] INCLUDE_FIELDS3 = new String[] {"rateAdsvcGdncSeq","rateDivCd","rateAdsvcNm","rateAdsvcApdNm",
	    		"rateAdsvcBasDesc","mmBasAmtDesc","mmBasAmtVatDesc","promotionAmtDesc","promotionAmtVatDesc","rateAdsvcCtgCd",
	    		"rateAdsvcCtgNm","rateAdsvcCtgBasDesc","rateAdsvcCtgDtlDesc","rateAdsvcProdRelSeq","rateAdsvcCd","rateAdsvcItemCd",
	    		"rateAdsvcItemNm","rateAdsvcItemSbst","rateAdsvcBnfitItemCd","rateAdsvcItemNm","rateAdsvcItemDesc",
	    		"rateAdsvcItemApdDesc","sortOdrg1","sortOdrg2"
	    };

	    // adsvc
	    String[] INCLUDE_FIELDS4 = new String[] {"rateAdsvcGdncSeq","rateAdsvcNm","rateAdsvcApdNm","rateAdsvcBasDesc",
	    		"mmBasAmtDesc","mmBasAmtVatDesc","promotionAmtDesc","promotionAmtVatDesc","rateAdsvcCtgCd","rateAdsvcCtgNm",
	    		"rateAdsvcCtgBasDesc","rateAdsvcCtgDtlDesc","rateAdsvcProdRelSeq","rateAdsvcCd","rateAdsvcItemCd","rateAdsvcItemNm",
	    		"rateAdsvcItemSbst","sortOdrg1","sortOdrg2"
	    };

	    // event
	    String[] INCLUDE_FIELDS5 = new String[] {"ntcartSeq","sbstCtg","sbstCtgNm","ntcartSubject","listImg",
	    		"thumbImgNm","thumbImgDesc","mobileListImgNm","imgDesc","ntcartSbst","dtlCdNm","eventStartDt","eventStartDate",
	    		"eventEndDt","eventEndDate","startHour","endHour","linkTarget","linkUrlAdr","eventUrlAdr","ntcartHitCnt",
	    		"eventAdd1Yn","eventAdd1Sbst","eventAdd2Yn","eventAdd3Yn","verificationUrl","plnSmallTitle","plnContent",
	    		"snsSbst","ntcartSbst2","plnContent2"
	    };

	    // board
	    String[] INCLUDE_FIELDS6 = new String[] {"sbstCtg","sbstSubCtgCd","dtlCdNm","boardSeq","boardSubject","boardContents","boardHitCnt"};

	    // menu
	    String[] SEARCH_RANGE1 = {"menuNm", "chatbotTipSbst", "menuDesc"};

	    // phone
		String[] SEARCH_RANGE2 = {"prodCtgName", "prodNm", "listShowText", "listShow_optn", "apdDesc1", "apdDesc2",
				"apdDesc3", "sntyProdDesc", "sntyNet", "sntyDisp", "sntySize", "sntyWeight", "sntyMemr", "sntyBtry", "sntyOs",
				"sntyWaitTime", "sntyCam", "sntyVideTlk", "sntyProdNm", "sntyRelMonth", "sntyColor", "sntyMaker", "sntyModelId",
				"mnfctNm", "addr", "dtlAddr", "telnum", "email", "shandTypeName", "usedWarranty", "usedWarrantyName", "hndsetModelNm"
		};

		// rate
		String[] SEARCH_RANGE3 = {"rateAdsvcNm","rateAdsvcApdNm","rateAdsvcBasDesc","mmBasAmtDesc",
				"mmBasAmtVatDesc","promotionAmtDesc","promotionAmtVatDesc","rateAdsvcCtgNm","rateAdsvcCtgBasDesc",
				"rateAdsvcCtgDtlDesc","rateAdsvcItemNm","rateAdsvcItemSbst","rateAdsvcItemNm","rateAdsvcItemDesc","rateAdsvcItemApdDesc"
		};

		// adsvc
		String[] SEARCH_RANGE4 = {"rateAdsvcNm","rateAdsvcApdNm","rateAdsvcBasDesc","mmBasAmtDesc","mmBasAmtVatDesc",
				"promotionAmtDesc","promotionAmtVatDesc","rateAdsvcCtgNm","rateAdsvcCtgBasDesc","rateAdsvcCtgDtlDesc","rateAdsvcItemNm",
	    		"rateAdsvcItemSbst"
		};

		// event
		String[] SEARCH_RANGE5 = {"sbstCtg","sbstCtgNm","ntcartSubject","thumbImgDesc","imgDesc","ntcartSbst","dtlCdNm",
	    		"plnSmallTitle","plnContent","snsSbst","ntcartSbst2","plnContent2"
		};

		// board
		String[] SEARCH_RANGE6 = {"dtlCdNm","boardSubject","boardContents"};

	    //모바일 더보기 - 2개씩 뿌려주기(휴대폰)
		Integer DISPLAY_MOBILE_COUNT = 2;

		//pc 더보기 - 3개씩 뿌려주기(휴대폰)
		Integer DISPLAY_PC_COUNT = 3;

		// phone
		String[] HIGHLIGHT_RANGE2 = {};

		// rate
		String[] HIGHLIGHT_RANGE3 = {};

		// adsvc
		String[] HIGHLIGHT_RANGE4 = {"rateAdsvcNm"};

		// event
		String[] HIGHLIGHT_RANGE5 = {};

		// board
		String[] HIGHLIGHT_RANGE6 = {"boardSubject"};


		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.from((searchParamDto.getPage()-1) * DISPLAY_COUNT);							// 페이징
		searchSourceBuilder.size(DISPLAY_COUNT);
		if("menu".equals(searchParamDto.getSearchCategory())) {
			searchSourceBuilder.size(1000);
			searchSourceBuilder.highlighter(makeHighlightField (HIGHLIGHT_RANGE1));
			searchSourceBuilder.fetchSource(INCLUDE_FIELDS1, null);
			searchSourceBuilder.sort(new FieldSortBuilder("sortKey").order(SortOrder.ASC));
			searchSourceBuilder.query( makeQuery ( SEARCH_RANGE1, searchParamDto.getSearchKeyword().split(" "),  searchParamDto ));
		}else if("phone".equals(searchParamDto.getSearchCategory())) {
			if(request.getRequestURI().equals("/m/search/commonSearchAjax.do") || request.getRequestURI().equals("/m/search/searchResultView.do")) {
				searchSourceBuilder.from((searchParamDto.getPage()-1) * DISPLAY_MOBILE_COUNT);							// 페이징
				searchSourceBuilder.size(DISPLAY_MOBILE_COUNT);
			}else if(request.getRequestURI().equals("/search/commonSearchAjax.do") || request.getRequestURI().equals("/search/searchResultView.do")) {
				searchSourceBuilder.from((searchParamDto.getPage()-1) * DISPLAY_PC_COUNT);							// 페이징
				searchSourceBuilder.size(DISPLAY_PC_COUNT);
			}
			searchSourceBuilder.highlighter(makeHighlightField (HIGHLIGHT_RANGE2));
			searchSourceBuilder.fetchSource(INCLUDE_FIELDS2, null);
			searchSourceBuilder.sort(new FieldSortBuilder("showOdrg").order(SortOrder.ASC));
			searchSourceBuilder.query( makeQuery ( SEARCH_RANGE2, searchParamDto.getSearchKeyword().split(" "),  searchParamDto ));
		}else if("rate".equals(searchParamDto.getSearchCategory())) {
			searchSourceBuilder.highlighter(makeHighlightField (HIGHLIGHT_RANGE3));
			searchSourceBuilder.fetchSource(INCLUDE_FIELDS3, null);
			searchSourceBuilder.sort(new FieldSortBuilder("sortOdrg1").order(SortOrder.ASC));
			searchSourceBuilder.sort(new FieldSortBuilder("sortOdrg2").order(SortOrder.ASC));
			searchSourceBuilder.query( makeQuery ( SEARCH_RANGE3, searchParamDto.getSearchKeyword().split(" "),  searchParamDto ));
		}else if("adsvc".equals(searchParamDto.getSearchCategory())) {
			searchSourceBuilder.highlighter(makeHighlightField (HIGHLIGHT_RANGE4));
			searchSourceBuilder.fetchSource(INCLUDE_FIELDS4, null);
			searchSourceBuilder.sort(new FieldSortBuilder("sortOdrg1").order(SortOrder.ASC));
			searchSourceBuilder.sort(new FieldSortBuilder("sortOdrg2").order(SortOrder.ASC));
			searchSourceBuilder.query( makeQuery ( SEARCH_RANGE4, searchParamDto.getSearchKeyword().split(" "),  searchParamDto ));
		}else if("event".equals(searchParamDto.getSearchCategory())) {
			searchSourceBuilder.highlighter(makeHighlightField (HIGHLIGHT_RANGE5));
			searchSourceBuilder.fetchSource(INCLUDE_FIELDS5, null);
			searchSourceBuilder.sort(new FieldSortBuilder("eventEndDate").order(SortOrder.ASC));
			searchSourceBuilder.query( makeQuery ( SEARCH_RANGE5, searchParamDto.getSearchKeyword().split(" "),  searchParamDto ));
		}else if("board".equals(searchParamDto.getSearchCategory())) {
			searchSourceBuilder.highlighter(makeHighlightField (HIGHLIGHT_RANGE6));
			searchSourceBuilder.fetchSource(INCLUDE_FIELDS6, null);
			searchSourceBuilder.sort(new FieldSortBuilder("boardHitCnt").order(SortOrder.ASC));
			searchSourceBuilder.query( makeQuery ( SEARCH_RANGE6, searchParamDto.getSearchKeyword().split(" "),  searchParamDto ));
		}else {
			return null;
		}

    	SearchRequest searchRequest = new SearchRequest();
		searchRequest.indices(searchParamDto.getSearchCategory());
		searchRequest.source(searchSourceBuilder);
		searchRequest.indicesOptions(IndicesOptions.STRICT_EXPAND_OPEN_FORBID_CLOSED_IGNORE_THROTTLED);

//		IndicesOptions indOptions = new IndicesOptions(Option.NONE, WildcardStates.NONE);
//		searchRequest.indicesOptions(indOptions);

        RestHighLevelClient client = null;
 		SearchResponse searchResponse = null;
		try {
			RequestOptions requestOptions = RequestOptions.DEFAULT;
			requestOptions.toBuilder().setWarningsHandler(WarningsHandler.PERMISSIVE);

			logger.debug("=== searchRequest:{}", searchRequest.toString());
			client =  this.createConnection();
			searchResponse = client.search(searchRequest, requestOptions);

			JSONParser parser = new JSONParser();

			Object obj = parser.parse(searchResponse.toString());

			if("Y".equals(StringUtil.NVL(searchParamDto.getXmlYn(), "N"))) {
				JSONObject dummy1 = (JSONObject) parser.parse(searchResponse.toString());
				JSONObject dummy2 = (JSONObject) dummy1.get("hits");
				JSONArray dummy3 = (JSONArray) dummy2.get("hits");
				List<Map<String, Object>> list = getListMapFromJsonArray(dummy3);

				String fPer = File.separator;
		    	String realDir = getRateAdsvcGdncPath(fileuploadBase, serverName);

		    	try {
		    		File file1 = null;
		    		Map<String, Object> item = new HashMap<String, Object>();
	                for(Map<String, Object> adsvcItem : list) {
	                	item = (Map<String, Object>) adsvcItem.get("_source");
            			String realFilePath = realDir + fPer + "NMCP_RATE_ADSVC_GDNC_DTL_" + item.get("rateAdsvcGdncSeq") + ".xml";
                        file1 = new File(realFilePath);

                        if(file1.exists()) {
                        	List<RateAdsvcGdncDtlXML> rateAdsvcGdncDtlList = XmlBindUtil.bindListFromXml(RateAdsvcGdncDtlXML.class, file1);

                        	for(RateAdsvcGdncDtlXML adsvcDtl : rateAdsvcGdncDtlList) {
                        		if("ADDSV101".equals(adsvcDtl.getRateAdsvcItemCd())) {
                        			adsvcItem.put("ADDSV101", adsvcDtl.getRateAdsvcItemSbst());
                        		}else if("ADDSV102".equals(adsvcDtl.getRateAdsvcItemCd())) {
                        			adsvcItem.put("ADDSV102", adsvcDtl.getRateAdsvcItemSbst());
                        		}else if("ADDSV103".equals(adsvcDtl.getRateAdsvcItemCd())) {
                        			adsvcItem.put("ADDSV103", adsvcDtl.getRateAdsvcItemSbst());
                        		}else if("ADDSV104".equals(adsvcDtl.getRateAdsvcItemCd())) {
                        			adsvcItem.put("ADDSV104", adsvcDtl.getRateAdsvcItemSbst());
                        		}else if("ADDSV105".equals(adsvcDtl.getRateAdsvcItemCd())) {
                        			adsvcItem.put("ADDSV105", adsvcDtl.getRateAdsvcItemSbst());
                        		}else if("ADDSV106".equals(adsvcDtl.getRateAdsvcItemCd())) {
                        			adsvcItem.put("ADDSV106", adsvcDtl.getRateAdsvcItemSbst());
                        		}else if("ADDSV107".equals(adsvcDtl.getRateAdsvcItemCd())) {
                        			adsvcItem.put("ADDSV107", adsvcDtl.getRateAdsvcItemSbst());
                        		}else if("ADDSV108".equals(adsvcDtl.getRateAdsvcItemCd())) {
                        			adsvcItem.put("ADDSV108", adsvcDtl.getRateAdsvcItemSbst());
                        		}else if("ADDSV199".equals(adsvcDtl.getRateAdsvcItemCd())) {
                        			adsvcItem.put("ADDSV199", adsvcDtl.getRateAdsvcItemSbst());
                        		}
                        		/* 항목명
                        		ADDSV101	서비스 정의
                        		ADDSV102	서비스 이용요금(VAT포함)
                        		ADDSV103	서비스 신청방법
                        		ADDSV104	서비스 이용방법
                        		ADDSV105	서비스 유의사항
                        		ADDSV106	서비스 선택유형
                        		ADDSV107	서비스 제공내용
                        		ADDSV108	서비스 신청(가입 및 해지)방법
                        		ADDSV199	직접입력
								*/
                        	}
                        }

		            }
		            return list;

		    	} catch(JAXBException e) {
					return null;
				} catch(Exception e) {
		    		return null;
		    	}

			} else {
				return obj;
			}

		} catch(IOException e) {
			return null;
		} catch (Exception e) {
			return null;
		} finally {
			try {
				client.close();
			} catch (IOException ioe) {
				logger.debug("ioe");
			}
		}
	}

	public RestHighLevelClient createConnection() {
		return new RestHighLevelClient(RestClient.builder(new HttpHost(elasticUrl, 9200, "http")));
    }

    public HighlightBuilder makeHighlightField (String[] fields) {
    	HighlightBuilder highlightBuilder = new HighlightBuilder();

    	for( String fld : fields) {
        	HighlightBuilder.Field hField = new HighlightBuilder.Field(fld);
        	highlightBuilder.field(hField);
    	}

		return highlightBuilder;
    }

    /*
     * 검색식 작성
     * @param fields: 검색할 필드.
     * @param words: 검색할 키워드.
     * @param searchParamDto: 검색 조건. 데이터 소스 종류(searchType), 날짜(getSearchTerm).
     */
    public BoolQueryBuilder makeQuery (String[] fields, String[] words, SearchParamDto searchParamDto) {
    	BoolQueryBuilder qb = QueryBuilders.boolQuery();

    	for( String word : words) {
    		word = word.trim().toLowerCase();
    		if ("".equals(word)) continue;

        	BoolQueryBuilder qb1 = QueryBuilders.boolQuery();
        	for( String fld : fields) {
    			qb1.should(QueryBuilders.boolQuery().must(QueryBuilders.wildcardQuery(fld,  "*"+word+"*")));
        	}

        	qb.must(qb1);
    	}

		return qb;
    }

	@SuppressWarnings("unchecked")
	public static Map<String, Object> getMapFromJsonObject(JSONObject jsonObject) {

		Map<String, Object> map = null;

		try {

			map = new ObjectMapper().readValue(jsonObject.toJSONString(), Map.class);

		} catch(JsonParseException e) {
			logger.debug("getMapFromJsonObject JsonParseException 발생");
		} catch (Exception e) {
			logger.debug("getMapFromJsonObject 호출");
		}

		return map;
	}

	public static List<Map<String, Object>> getListMapFromJsonArray(JSONArray jsonArray) {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		if (jsonArray != null) {

			int jsonSize = jsonArray.size();

			for (int i = 0; i < jsonSize; i++) {

				Map<String, Object> map = getMapFromJsonObject((JSONObject)jsonArray.get(i));
				list.add(map);
			}
		}

		return list;
	}

}
