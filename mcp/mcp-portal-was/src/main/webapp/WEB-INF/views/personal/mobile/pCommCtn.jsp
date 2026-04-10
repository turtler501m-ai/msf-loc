<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>

<div class="phone-line-wrap u-mt--40">
	<div class="phone-line">
		<span class="phone-line__cnt">1</span>
	</div>
	<select name="ctn" id="ctn" onchange="select()">
		<option value="${searchVO.ncn}" selected>${searchVO.ctn}</option>
	</select>
</div>
<div style="clear:both;"></div>