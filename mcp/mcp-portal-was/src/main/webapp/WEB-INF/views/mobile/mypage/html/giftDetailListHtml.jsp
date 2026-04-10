<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un"
	uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>
<input type="hidden" id="pageNo" name="pageNo" value="${empty pageInfoBean.pageNo?1:pageInfoBean.pageNo}">
<input type="hidden" id="listCount" name="listCount" value="${listCount}">
<input type="hidden" id="maxPage" name="maxPage" value="${maxPage}">


<script>
if(${listCount} >= ${pageInfoBean.totalCount}){
	$('#moreList').css('display','none');
}else{
	$('#moreList').css('display','block');
	$('#pageCnt').html('${listCount}/${pageInfoBean.totalCount }');
}
$('#pageNo').val('${empty pageInfoBean.pageNo?1:pageInfoBean.pageNo}');

</script>