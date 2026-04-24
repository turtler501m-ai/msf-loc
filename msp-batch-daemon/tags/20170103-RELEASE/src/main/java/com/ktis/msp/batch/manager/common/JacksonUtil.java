package com.ktis.msp.batch.manager.common;

import java.io.IOException;
import java.util.Map;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.ktis.msp.base.exception.MvnoServiceException;

public class JacksonUtil {
	
	public final static Object makeVOFromJson(String pJsonString, Class<?> pClass) throws MvnoServiceException {
		
		Map<String, Object> items = makeMapFromJson(pJsonString);
		
		ObjectMapper lObjectMapper = new ObjectMapper();
		lObjectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		return lObjectMapper.convertValue(items, pClass);
		
	}
	
	public final static Map<String, Object> makeMapFromJson(String pJsonString) throws MvnoServiceException {
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		
		JsonFactory f = new JsonFactory();
		JsonParser p;
		
		String[] errParam = new String[2];
		errParam[0] = "createJsonParser";
		errParam[1] = pJsonString;
		
		try {
			p = f.createJsonParser( pJsonString );
		} catch (JsonParseException e) {
			String errCode = "ECMN1004";
			
			throw new MvnoServiceException(errCode, errParam, e);
			
		} catch (IOException e) {
			String errCode = "ECMN1005";
			
			throw new MvnoServiceException(errCode, errParam, e);
		}
		Map<String, Object> items;
		
		errParam[0] = "readValue";
		try {
			items = mapper.readValue( p , new TypeReference<Map<String, Object>>() { });
		} catch (JsonParseException e) {
			String errCode = "ECMN1004";
			
			throw new MvnoServiceException(errCode, errParam, e);
		} catch (JsonMappingException e) {
			String errCode = "ECMN1006";
			
			throw new MvnoServiceException(errCode, errParam, e);
		} catch (IOException e) {
			String errCode = "ECMN1005";
			
			throw new MvnoServiceException(errCode, errParam, e);
		}
		
		return items;
		
	}
	
	public final static String makeJsonFromMap(Map<String, Object> map) throws MvnoServiceException {
		
		ObjectMapper mapper = new ObjectMapper();
		String resultJson = "";
		
		String[] errParam = new String[1];
		errParam[0] = map.toString();
		
		try {
			resultJson = mapper.writeValueAsString(map);
		} catch (JsonGenerationException e) {
			throw new MvnoServiceException("ECMN1007", errParam, e);
		} catch (JsonMappingException e) {
			throw new MvnoServiceException("ECMN1008", errParam, e);
		} catch (IOException e) {
			throw new MvnoServiceException("ECMN1009", errParam, e);
		}
		
		return resultJson;
	}

}
