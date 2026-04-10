<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>

<div class="c-select-search">
	<div class="phone-line">
		<span class="phone-line__cnt">1</span>
	</div>
	<div class="c-form--line">
		<label class="c-label c-hidden" for="ctn">회선 선택</label>
		<select class="c-select" name="ctn" id="ctn" >
		  <option value="${searchVO.ncn}" selected>${searchVO.ctn}</option>
		</select>
	</div>
	<button class="c-form--line__btn" onclick="select();">조회</button>
</div>
<div style="clear:both;"></div>