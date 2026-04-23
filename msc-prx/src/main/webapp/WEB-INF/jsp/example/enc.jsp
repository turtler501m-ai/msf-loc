<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<style>
	td {font-size:8px}
</style>

<h1>평문   </h1>
<Table border="1">
	<tr>
		<td></td>
		<td>Sangsun Park</td>
		<td>01025161123</td>
	</tr>
</Table>
<br/>
<br/>
<h1>AES 128</h1>
<Table border="1">
	<tr>
		<td>Encrypt</td>
		<td>${V1.userName } </td>
		<td>${V1.passwd } </td>
	</tr>
	<tr>
		<td>Decrypt</td>
		<td>${V2.userName } </td>
		<td>${V2.passwd } </td>
	</tr>
</table>	
<br/>
<br/>
<h1>AES 256</h1>
<Table border="1">
	<tr>
		<td>Encrypt</td>
		<td>${V3.userName } </td>
		<td>${V3.passwd } </td>
	</tr>
	<tr>
		<td>Decrypt</td>
		<td>${V4.userName } </td>
		<td>${V4.passwd } </td>
	</tr>
</table>	
<br/>
<br/>
<h1>SHA 512</h1>
<Table border="1">
	<tr>
	
		<td>Encrypt</td>
		<td>${V5.userName } </td>
		<td>${V5.passwd } </td>
	</tr>
</Table>
