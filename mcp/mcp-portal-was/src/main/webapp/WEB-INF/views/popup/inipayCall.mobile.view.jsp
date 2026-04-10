<%@ page language = "java" contentType = "text/html;charset=UTF-8" %>
<script language=javascript>
$(document).ready(function() {
	on_web();
});
</script>
<form  name="ini" method="post" accept-charset="euc-kr" action="" >
    <input type="hidden" name="P_MID" value="ktmmobile0">    
    <input type="hidden" name="inipaymobile_type" value="web" />  
    <input type="hidden" name="P_OID"   value="${iniDto.oid}" />
    <input type="hidden" name="P_GOODS" value="${iniDto.goodname}" />
    <input type="hidden" name="P_AMT" value="${iniDto.price}" />
    <input type="hidden" name="P_UNAME" value="${iniDto.buyername}" />
    <input type="hidden" name="P_MOBILE" value="${iniDto.buyertel}"  />
    <input type="hidden" name="paymethod" value="${iniDto.gopaymethod}"  />
    <input type="hidden" name="P_EMAIL" value="${iniDto.buyeremail}" />
    <input type="hidden" name="P_NEXT_URL" value="${callbackUrl}" />
    <input type="hidden" name="P_NOTI_URL" value="${notiUrl}" />
    <input type="hidden" name="P_RETURN_URL" value="${returnUrl}" />
    <input type="hidden" name="P_RESERVED" value="twotrs_isp=Y&block_isp=Y&twotrs_isp_noti=N&app_scheme=2trs" />
    <input type=hidden name="P_HPP_METHOD" value="1">
</form>

