package com.ktis.msp.rcp.old.vo;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/**
 * @project : default
 * @file 	: CommonVO.java
 * @author	: HanNamSik
 * @date	: 2013. 4. 2. 오후 2:23:45
 * @history	:
 * 
 * @comment : 
 * 
 * 
 * 
 */
public class CommonVO extends PaginationInfo {

	
	private Integer menuKey;
	
	private String searchLikeColumn;
	
	private String searchLikeValue;
	
	private String searchEqualColumn;
	
	private String searchEqualValue;
	
	private String searchUserid;
	
	private String orderBy;
	
	/** 공통으로 스크랩 하기 위하여 */
	private String module;
	private Integer moduleKey;
	
	 /** firstIndex */
    private int firstIndex = 1;

    /** lastIndex */
    private int lastIndex = 10;
    
	public int getFirstIndex() {
		return firstIndex;
	}
	public void setFirstIndex(int firstIndex) {
		this.firstIndex = firstIndex;
	}
	public int getLastIndex() {
		return lastIndex;
	}
	public void setLastIndex(int lastIndex) {
		this.lastIndex = lastIndex;
	}

	public int getPageSize() {
		if(super.getPageSize() == 0) {
			return 10;
		} 
		return super.getPageSize();
	}
	
	
	public int getCurrentPageNo() {
		if(super.getCurrentPageNo() == 0) {
			return 1;
		}
		return super.getCurrentPageNo();
	}
	
	public int getRecordCountPerPage() {
		
		if(super.getRecordCountPerPage() == 0) {
			return 10;
		}
		
		return super.getRecordCountPerPage();
	}


	/**
	 * @return the menuKey
	 */
	public Integer getMenuKey() {
		return menuKey;
	}


	/**
	 * @param menuKey the menuKey to set
	 */
	public void setMenuKey(Integer menuKey) {
		this.menuKey = menuKey;
	}
	
	public String getSearchLikeColumn() {
		return searchLikeColumn;
	}
	public void setSearchLikeColumn(String searchLikeColumn) {
		this.searchLikeColumn = searchLikeColumn;
	}
	public String getSearchLikeValue() {
		return searchLikeValue;
	}
	public void setSearchLikeValue(String searchLikeValue) {
		this.searchLikeValue = searchLikeValue;
	}
	public String getSearchEqualColumn() {
		return searchEqualColumn;
	}
	public void setSearchEqualColumn(String searchEqualColumn) {
		this.searchEqualColumn = searchEqualColumn;
	}
	public String getSearchEqualValue() {
		return searchEqualValue;
	}
	public void setSearchEqualValue(String searchEqualValue) {
		this.searchEqualValue = searchEqualValue;
	}
	/**
	 * @method : getParameter
	 * @date : 2011. 8. 20.
	 * @author : HanNamSik
	 * @history :
	 *
	 * @comment : SearchBaseDomain 타입 클래스에서 ParameterColumn Annotation 이 있는 필드를 이용해 파라미터를
	 *          만드는 메소드
	 *
	 * @param exceptParams
	 *            제외할 파라미터 String 배열
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException 
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws ArrayIndexOutOfBoundsException 
	 * @throws UnsupportedEncodingException 
	 */
	public String getParam() throws ArrayIndexOutOfBoundsException, IllegalArgumentException, SecurityException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, UnsupportedEncodingException {
		return getParameter("");
	}
	public String getParameter(String... exceptParams)
			throws IllegalArgumentException, IllegalAccessException, ArrayIndexOutOfBoundsException, SecurityException, NoSuchMethodException, InvocationTargetException, UnsupportedEncodingException {

		Class<?> paramClass = this.getClass();
		StringBuffer param = new StringBuffer();
		param = _makeParam(param, paramClass, exceptParams);

		if(param.length() == 0) {
			return "1=1";
		}

		String parameter = param.toString();

		// 끝 문자 & 제거
		if(StringUtils.right(parameter, 1).equals("&")) {
			parameter = parameter.substring(0, param.length()-1);
		}

		return parameter;
	}

	private StringBuffer _makeParam(StringBuffer sb, Class<?> c,
			String... exceptParams) throws IllegalArgumentException,
			IllegalAccessException, SecurityException, NoSuchMethodException, ArrayIndexOutOfBoundsException, InvocationTargetException, UnsupportedEncodingException {
		StringBuffer param = sb;
		
		Field[] fields = c.getDeclaredFields();
		for (Field f : fields) {
			ParameterColumn a = f.getAnnotation(ParameterColumn.class);

			boolean isExcept = false;
			for (String eParam : exceptParams) {
				if (eParam.equals(f.getName())) {
					isExcept = true;
				}
			}
			
			String getMethodName = "get"+ f.getName().substring(0,1).toUpperCase() + f.getName().substring(1);
						
			Method getMethod = c.getMethod(getMethodName);
			
			if (!isExcept && (a != null || f.getName().equals("currentPageNo") || f.getName().equals("recordCountPerPage")) && getMethod.invoke(this) != null
					&& StringUtils.isNotEmpty(String.valueOf(getMethod.invoke(this)))) {
				param.append(f.getName() + "=" + URLEncoder.encode(String.valueOf((Object) getMethod.invoke(this)), "UTF-8") + "&");
			}
			
		}

		if (c.getSuperclass() != null)
			param = _makeParam(param, c.getSuperclass());

		return param;

	}

	public String getHiddenTag() throws ArrayIndexOutOfBoundsException, IllegalArgumentException, SecurityException, UnsupportedEncodingException, IllegalAccessException, NoSuchMethodException, InvocationTargetException  {
		
		StringBuffer tag = new StringBuffer();
		
		String parameter = getParam();
		
		String[] params = parameter.split("\\&");
		for (String p : params) {
			String[] f = p.split("\\=");
			tag.append("<input type='hidden' name='"+f[0]+"' id='"+f[0]+"' value=\""+URLDecoder.decode(f[1], "UTF-8")+"\" /> \n");
		}
		return tag.toString();
	}
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	/**
	 * @return the module
	 */
	public String getModule() {
		return module;
	}
	/**
	 * @param module the module to set
	 */
	public void setModule(String module) {
		this.module = module;
	}
	/**
	 * @return the moduleKey
	 */
	public Integer getModuleKey() {
		return moduleKey;
	}
	/**
	 * @param moduleKey the moduleKey to set
	 */
	public void setModuleKey(Integer moduleKey) {
		this.moduleKey = moduleKey;
	}
	/**
	 * @return the orderBy
	 */
	public String getOrderBy() {
		return orderBy;
	}
	/**
	 * @param orderBy the orderBy to set
	 */
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	/**
	 * @return the searchUserid
	 */
	public String getSearchUserid() {
		return searchUserid;
	}
	/**
	 * @param searchUserid the searchUserid to set
	 */
	public void setSearchUserid(String searchUserid) {
		this.searchUserid = searchUserid;
	}
}
