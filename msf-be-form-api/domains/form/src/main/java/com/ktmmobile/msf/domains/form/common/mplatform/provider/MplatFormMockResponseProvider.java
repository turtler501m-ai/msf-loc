package com.ktmmobile.msf.domains.form.common.mplatform.provider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.ktmmobile.msf.domains.form.common.exception.SelfServiceException;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.CommonXmlNoSelfServiceException;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.CommonXmlVO;

@Slf4j
@Component
public class MplatFormMockResponseProvider {

    /*
     * 테스트를 위해 정상적인 리턴을 강제로 넘겨준다.
     */
    public boolean getVo(int param, CommonXmlVO vo) {
        boolean result = true;
        //////////////////////////////////
        StringBuffer responseXml = new StringBuffer();

        responseXml.append(
            "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><ns2:moscPerInfoResponse xmlns:ns2=\"http://selfcare.so.itl.mvno.kt.com/\">");

        switch (param) {
            case 1://가입정보조회------
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X01</appEventCd><appSendDateTime>20160112153248</appSendDateTime><appRecvDateTime>20160112153246</appRecvDateTime><appLgDateTime>20160112153246</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160112153243531</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><addr>인천 옹진군 영흥면 선재로34번길 141 </addr><email>bluemoor9521@naver.com</email><homeTel>01075116741</homeTel><initActivationDate>20140807163028</initActivationDate></outDto></return>");
                break;
            case 2://청구지주소변경
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X01</appEventCd><appSendDateTime>20160112153248</appSendDateTime><appRecvDateTime>20160112153246</appRecvDateTime><appLgDateTime>20160112153246</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160112153243531</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><addr>인천 옹진군 영흥면 선재로34번길 141 </addr><email>bluemoor9521@naver.com</email><homeTel>01075116741</homeTel><initActivationDate>20140807163028</initActivationDate></outDto></return>");
                break;
            case 3://e-mail청구서조회------
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X03</appEventCd><appSendDateTime>20160112154248</appSendDateTime><appRecvDateTime>20160112154245</appRecvDateTime><appLgDateTime>20160112154245</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160112154242512</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><outApplyChgDto><currentState>e-Mail명세서 등록 상태</currentState><ecRcvAgreYn>Y</ecRcvAgreYn><email>bluemoor9521@naver.com</email><option>true</option><securMailYn>Y</securMailYn><sendGubun>Y</sendGubun><svcMsg></svcMsg></outApplyChgDto><outApplyDto><ecRcvAgreYn></ecRcvAgreYn><email></email><safetyEmailFlag>B</safetyEmailFlag><securMailYn></securMailYn><sendGubun></sendGubun><status>1</status></outApplyDto><outChgDto><ecRcvAgreYn>Y</ecRcvAgreYn><effectiveDate></effectiveDate><email></email><giroGubun>2</giroGubun><msgGubun></msgGubun><oriEcRcvAgreYn>Y</oriEcRcvAgreYn><oriSecurMailYn>Y</oriSecurMailYn><oriSendGubun>1</oriSendGubun><orieMail>bluemoor9521@naver.com</orieMail><securMailYn>Y</securMailYn><sendGubun></sendGubun><status>9</status></outChgDto><outOrgDto><option>true</option><orgEmail>bluemoor9521@naver.com</orgEmail></outOrgDto><outTermDto><email></email><status></status></outTermDto></outDto></return>");
                break;
            case 4://email청구서변경
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X04</appEventCd><appSendDateTime>20160114110952</appSendDateTime><appRecvDateTime>20160114110947</appRecvDateTime><appLgDateTime>20160114110947</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160114110943883</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader></return>");
                //responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X04</appEventCd><appSendDateTime>20160114113711</appSendDateTime><appRecvDateTime>20160114113711</appRecvDateTime><appLgDateTime>20160114113711</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160114113708462</globalNo><encYn></encYn><responseType>E</responseType><responseCode>ITL_COM_E0003</responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic>[inDto.status] 항목에 [2]값은 허용되지 않은 값입니다.</responseBasic><langCode></langCode><filler></filler></commHeader></return>");
                break;
            case 10://종이청구서조회------
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd></appAgncCd><appEventCd>X10</appEventCd><appSendDateTime>20160112155236</appSendDateTime><appRecvDateTime>20160112155233</appRecvDateTime><appLgDateTime>20160112155233</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160112155230089</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><arBalance>0</arBalance><ban>1111</ban><disableFlag>disabled</disableFlag><option>true</option><outItemBillDto><amt>0</amt><billDate>201512201601</billDate></outItemBillDto><outItemBillDto><amt>0</amt><billDate>1111</billDate></outItemBillDto><outItemBillDto><amt>0</amt><billDate>1111</billDate></outItemBillDto><outReqReDto><requestReason>NR</requestReason></outReqReDto><outReqReDto><requestReason>AC</requestReason></outReqReDto><outSndGuDto><sendGubun>F</sendGubun></outSndGuDto><outSndGuDto><sendGubun>R</sendGubun></outSndGuDto><zipCode></zipCode><zipCode1></zipCode1><pAddr></pAddr><sAddr></sAddr></outDto></return>");
                break;
            case 12://총통화시간조회------
                //responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X12</appEventCd><appSendDateTime>20241218113150</appSendDateTime><appRecvDateTime>20241218113149</appRecvDateTime><appLgDateTime>20241218113149</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020241218113148852</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><totUseTimeCntDto><strFreeSmsCur>999999999</strFreeSmsCur><strFreeSmsRemain>999999971</strFreeSmsRemain><strFreeSmsRoll>0</strFreeSmsRoll><strFreeSmsTotal>999999999</strFreeSmsTotal><strFreeSmsUse>28</strFreeSmsUse><strSvcNameSms>통화 맘껏 15GB</strSvcNameSms></totUseTimeCntDto><totUseTimeCntTotDto><strFreeSmsCur>999999999</strFreeSmsCur><strFreeSmsRemain>999999971</strFreeSmsRemain><strFreeSmsRoll>0</strFreeSmsRoll><strFreeSmsTotal>999999999</strFreeSmsTotal><strFreeSmsUse>28</strFreeSmsUse><total>합계</total></totUseTimeCntTotDto><totalUseTimeDto><strBunGun>V</strBunGun><strCtnSecs>25301</strCtnSecs><strFreeMinCur>999999999</strFreeMinCur><strFreeMinRemain>999971733</strFreeMinRemain><strFreeMinRoll></strFreeMinRoll><strFreeMinTotal>999999999</strFreeMinTotal><strFreeMinUse>28266</strFreeMinUse><strSecsToAmt>0</strSecsToAmt><strSecsToRate>0</strSecsToRate><strSvcName>음성</strSvcName></totalUseTimeDto><totalUseTimeDto><strBunGun>V</strBunGun><strCtnSecs>2965</strCtnSecs><strFreeMinCur>0</strFreeMinCur><strFreeMinRemain>0</strFreeMinRemain><strFreeMinRoll></strFreeMinRoll><strFreeMinTotal>0</strFreeMinTotal><strFreeMinUse>0</strFreeMinUse><strSecsToAmt>0</strSecsToAmt><strSecsToRate>0</strSecsToRate><strSvcName>HD보이스</strSvcName></totalUseTimeDto><totalUseTimeDto><strBunGun>V</strBunGun><strCtnSecs>207</strCtnSecs><strFreeMinCur>0</strFreeMinCur><strFreeMinRemain>0</strFreeMinRemain><strFreeMinRoll></strFreeMinRoll><strFreeMinTotal>0</strFreeMinTotal><strFreeMinUse>0</strFreeMinUse><strSecsToAmt>0</strSecsToAmt><strSecsToRate>0</strSecsToRate><strSvcName>음성(부가)</strSvcName></totalUseTimeDto><totalUseTimeDto><strBunGun>U</strBunGun><strCtnSecs>0</strCtnSecs><strFreeMinCur>1800</strFreeMinCur><strFreeMinRemain>1593</strFreeMinRemain><strFreeMinRoll></strFreeMinRoll><strFreeMinTotal>1800</strFreeMinTotal><strFreeMinUse>207</strFreeMinUse><strSecsToAmt>0</strSecsToAmt><strSecsToRate>0</strSecsToRate><strSvcName>영상/부가</strSvcName></totalUseTimeDto><totalUseTimeDto><strBunGun>D</strBunGun><strCtnSecs>23</strCtnSecs><strFreeMinCur>0</strFreeMinCur><strFreeMinRemain>0</strFreeMinRemain><strFreeMinRoll></strFreeMinRoll><strFreeMinTotal>0</strFreeMinTotal><strFreeMinUse>0</strFreeMinUse><strSecsToAmt>0</strSecsToAmt><strSecsToRate>0</strSecsToRate><strSvcName>SMS</strSvcName></totalUseTimeDto><totalUseTimeDto><strBunGun>D</strBunGun><strCtnSecs>5</strCtnSecs><strFreeMinCur>0</strFreeMinCur><strFreeMinRemain>0</strFreeMinRemain><strFreeMinRoll></strFreeMinRoll><strFreeMinTotal>0</strFreeMinTotal><strFreeMinUse>0</strFreeMinUse><strSecsToAmt>0</strSecsToAmt><strSecsToRate>0</strSecsToRate><strSvcName>MMS</strSvcName></totalUseTimeDto><totalUseTimeDto><strBunGun>D</strBunGun><strCtnSecs>0</strCtnSecs><strFreeMinCur>999999999</strFreeMinCur><strFreeMinRemain>999999971</strFreeMinRemain><strFreeMinRoll></strFreeMinRoll><strFreeMinTotal>999999999</strFreeMinTotal><strFreeMinUse>28</strFreeMinUse><strSecsToAmt>0</strSecsToAmt><strSecsToRate>0</strSecsToRate><strSvcName>SMS/MMS</strSvcName></totalUseTimeDto><totalUseTimeDto><strBunGun>P</strBunGun><strCtnSecs>9818022</strCtnSecs><strFreeMinCur>0</strFreeMinCur><strFreeMinRemain>0</strFreeMinRemain><strFreeMinRoll></strFreeMinRoll><strFreeMinTotal>0</strFreeMinTotal><strFreeMinUse>0</strFreeMinUse><strSecsToAmt>0</strSecsToAmt><strSecsToRate>0</strSecsToRate><strSvcName>데이터-LTE</strSvcName></totalUseTimeDto><totalUseTimeDto><strBunGun>P</strBunGun><strCtnSecs>9818022</strCtnSecs><strFreeMinCur>31457280</strFreeMinCur><strFreeMinRemain>21639258</strFreeMinRemain><strFreeMinRoll></strFreeMinRoll><strFreeMinTotal>31457280</strFreeMinTotal><strFreeMinUse>9818022</strFreeMinUse><strSecsToAmt>0</strSecsToAmt><strSecsToRate>0</strSecsToRate><strSvcName>데이터-합계</strSvcName></totalUseTimeDto><totalUseTimeDto><strBunGun>D</strBunGun><strCtnSecs>24</strCtnSecs><strFreeMinCur>0</strFreeMinCur><strFreeMinRemain>0</strFreeMinRemain><strFreeMinRoll></strFreeMinRoll><strFreeMinTotal>0</strFreeMinTotal><strFreeMinUse>0</strFreeMinUse><strSecsToAmt>0</strSecsToAmt><strSecsToRate>0</strSecsToRate><strSvcName>국제로밍MMS</strSvcName></totalUseTimeDto><totalUseTimeDto><strBunGun>V</strBunGun><strCtnSecs>39</strCtnSecs><strFreeMinCur>0</strFreeMinCur><strFreeMinRemain>0</strFreeMinRemain><strFreeMinRoll></strFreeMinRoll><strFreeMinTotal>0</strFreeMinTotal><strFreeMinUse>0</strFreeMinUse><strSecsToAmt>71</strSecsToAmt><strSecsToRate>39</strSecsToRate><strSvcName>국제음성</strSvcName></totalUseTimeDto><totalUseTimeDto><strBunGun>P</strBunGun><strCtnSecs>0</strCtnSecs><strFreeMinCur>0</strFreeMinCur><strFreeMinRemain>0</strFreeMinRemain><strFreeMinRoll></strFreeMinRoll><strFreeMinTotal>0</strFreeMinTotal><strFreeMinUse>0</strFreeMinUse><strSecsToAmt>0</strSecsToAmt><strSecsToRate>0</strSecsToRate><strSvcName>국제데이터</strSvcName></totalUseTimeDto><voiceCallDetailDto><strBunGun>V</strBunGun><strFreeMinCur>999999999</strFreeMinCur><strFreeMinRemain>999971733</strFreeMinRemain><strFreeMinRoll>0</strFreeMinRoll><strFreeMinTotal>999999999</strFreeMinTotal><strFreeMinUse>28266</strFreeMinUse><strSvcName>통화 맘껏 15GB</strSvcName></voiceCallDetailDto><voiceCallDetailTotDto><tottal>합계</tottal><iFreeMinCurSum>999999999</iFreeMinCurSum><iFreeMinRemainSum>999971733</iFreeMinRemainSum><iFreeMinRollSum>0</iFreeMinRollSum><iFreeMinTotalSum>999999999</iFreeMinTotalSum><iFreeMinUseSum>28266</iFreeMinUseSum></voiceCallDetailTotDto></outDto></return>");
                responseXml.append("<return>");
                responseXml.append("	<bizHeader>");
                responseXml.append("		<appEntrPrsnId>KIS</appEntrPrsnId>");
                responseXml.append("		<appAgncCd>AA00364</appAgncCd>");
                responseXml.append("		<appEventCd>X12</appEventCd>");
                responseXml.append("		<appSendDateTime>20251126152302</appSendDateTime>");
                responseXml.append("		<appRecvDateTime>20251126152301</appRecvDateTime>");
                responseXml.append("		<appLgDateTime>20251126152301</appLgDateTime>");
                responseXml.append("		<appNstepUserId>91225330</appNstepUserId>");
                responseXml.append("		<appOrderId></appOrderId>");
                responseXml.append("	</bizHeader>");
                responseXml.append("	<commHeader>");
                responseXml.append("		<globalNo>9122533020251126152301337</globalNo>");
                responseXml.append("		<encYn></encYn>");
                responseXml.append("		<responseType>N</responseType>");
                responseXml.append("		<responseCode></responseCode>");
                responseXml.append("		<responseLogcd></responseLogcd>");
                responseXml.append("		<responseTitle></responseTitle>");
                responseXml.append("		<responseBasic></responseBasic>");
                responseXml.append("		<langCode></langCode>");
                responseXml.append("		<filler></filler>");
                responseXml.append("	</commHeader>");
                responseXml.append("	<outDto>");
                responseXml.append("		<totUseTimeCntDto>");
                responseXml.append("			<strFreeSmsCur>999999999</strFreeSmsCur>");
                responseXml.append("			<strFreeSmsRemain>999999998</strFreeSmsRemain>");
                responseXml.append("			<strFreeSmsRoll>0</strFreeSmsRoll>");
                responseXml.append("			<strFreeSmsTotal>999999999</strFreeSmsTotal>");
                responseXml.append("			<strFreeSmsUse>1</strFreeSmsUse>");
                responseXml.append("			<strSvcNameSms>모두다 맘껏 안심 1.5GB+</strSvcNameSms>");
                responseXml.append("		</totUseTimeCntDto>");
                responseXml.append("		<totUseTimeCntTotDto>");
                responseXml.append("			<strFreeSmsCur>999999999</strFreeSmsCur>");
                responseXml.append("			<strFreeSmsRemain>999999998</strFreeSmsRemain>");
                responseXml.append("			<strFreeSmsRoll>0</strFreeSmsRoll>");
                responseXml.append("			<strFreeSmsTotal>999999999</strFreeSmsTotal>");
                responseXml.append("			<strFreeSmsUse>1</strFreeSmsUse>");
                responseXml.append("			<total>합계</total>");
                responseXml.append("		</totUseTimeCntTotDto>");
                responseXml.append("		<totalUseTimeDto>");
                responseXml.append("			<strBunGun>V</strBunGun>");
                responseXml.append("			<strCtnSecs>753</strCtnSecs>");
                responseXml.append("			<strFreeMinCur>999999999</strFreeMinCur>");
                responseXml.append("			<strFreeMinRemain>999999246</strFreeMinRemain>");
                responseXml.append("			<strFreeMinRoll></strFreeMinRoll>");
                responseXml.append("			<strFreeMinTotal>999999999</strFreeMinTotal>");
                responseXml.append("			<strFreeMinUse>753</strFreeMinUse>");
                responseXml.append("			<strSecsToAmt>0</strSecsToAmt>");
                responseXml.append("			<strSecsToRate>0</strSecsToRate>");
                responseXml.append("			<strSvcName>음성</strSvcName>");
                responseXml.append("		</totalUseTimeDto>");
                responseXml.append("		<totalUseTimeDto>");
                responseXml.append("			<strBunGun>U</strBunGun>");
                responseXml.append("			<strCtnSecs>0</strCtnSecs>");
                responseXml.append("			<strFreeMinCur>1800</strFreeMinCur>");
                responseXml.append("			<strFreeMinRemain>1800</strFreeMinRemain>");
                responseXml.append("			<strFreeMinRoll></strFreeMinRoll>");
                responseXml.append("			<strFreeMinTotal>1800</strFreeMinTotal>");
                responseXml.append("			<strFreeMinUse>0</strFreeMinUse>");
                responseXml.append("			<strSecsToAmt>0</strSecsToAmt>");
                responseXml.append("			<strSecsToRate>0</strSecsToRate>");
                responseXml.append("			<strSvcName>영상/부가</strSvcName>");
                responseXml.append("		</totalUseTimeDto>");
                responseXml.append("		<totalUseTimeDto>");
                responseXml.append("			<strBunGun>D</strBunGun>");
                responseXml.append("			<strCtnSecs>1</strCtnSecs>");
                responseXml.append("			<strFreeMinCur>0</strFreeMinCur>");
                responseXml.append("			<strFreeMinRemain>0</strFreeMinRemain>");
                responseXml.append("			<strFreeMinRoll></strFreeMinRoll>");
                responseXml.append("			<strFreeMinTotal>0</strFreeMinTotal>");
                responseXml.append("			<strFreeMinUse>0</strFreeMinUse>");
                responseXml.append("			<strSecsToAmt>0</strSecsToAmt>");
                responseXml.append("			<strSecsToRate>0</strSecsToRate>");
                responseXml.append("			<strSvcName>SMS</strSvcName>");
                responseXml.append("		</totalUseTimeDto>");
                responseXml.append("		<totalUseTimeDto>");
                responseXml.append("			<strBunGun>D</strBunGun>");
                responseXml.append("			<strCtnSecs>0</strCtnSecs>");
                responseXml.append("			<strFreeMinCur>999999999</strFreeMinCur>");
                responseXml.append("			<strFreeMinRemain>999999998</strFreeMinRemain>");
                responseXml.append("			<strFreeMinRoll></strFreeMinRoll>");
                responseXml.append("			<strFreeMinTotal>999999999</strFreeMinTotal>");
                responseXml.append("			<strFreeMinUse>1</strFreeMinUse>");
                responseXml.append("			<strSecsToAmt>0</strSecsToAmt>");
                responseXml.append("			<strSecsToRate>0</strSecsToRate>");
                responseXml.append("			<strSvcName>SMS/MMS</strSvcName>");
                responseXml.append("		</totalUseTimeDto>");
                responseXml.append("		<totalUseTimeDto>");
                responseXml.append("			<strBunGun>P</strBunGun>");
                responseXml.append("			<strCtnSecs>6403788</strCtnSecs>");
                responseXml.append("			<strFreeMinCur>0</strFreeMinCur>");
                responseXml.append("			<strFreeMinRemain>0</strFreeMinRemain>");
                responseXml.append("			<strFreeMinRoll></strFreeMinRoll>");
                responseXml.append("			<strFreeMinTotal>0</strFreeMinTotal>");
                responseXml.append("			<strFreeMinUse>0</strFreeMinUse>");
                responseXml.append("			<strSecsToAmt>0</strSecsToAmt>");
                responseXml.append("			<strSecsToRate>0</strSecsToRate>");
                responseXml.append("			<strSvcName>데이터-LTE</strSvcName>");
                responseXml.append("		</totalUseTimeDto>");
                responseXml.append("		<totalUseTimeDto>");
                responseXml.append("			<strBunGun>P</strBunGun>");
                responseXml.append("			<strCtnSecs>6403788</strCtnSecs>");
                responseXml.append("			<strFreeMinCur>3145728</strFreeMinCur>");
                responseXml.append("			<strFreeMinRemain>0</strFreeMinRemain>");
                responseXml.append("			<strFreeMinRoll></strFreeMinRoll>");
                responseXml.append("			<strFreeMinTotal>3145728</strFreeMinTotal>");
                responseXml.append("			<strFreeMinUse>3145728</strFreeMinUse>");
                responseXml.append("			<strSecsToAmt>0</strSecsToAmt>");
                responseXml.append("			<strSecsToRate>0</strSecsToRate>");
                responseXml.append("			<strSvcName>데이터-합계</strSvcName>");
                responseXml.append("		</totalUseTimeDto>");
                responseXml.append("		<totalUseTimeDto>");
                responseXml.append("			<strBunGun>P</strBunGun>");
                responseXml.append("			<strCtnSecs>0</strCtnSecs>");
                responseXml.append("			<strFreeMinCur>999999999</strFreeMinCur>");
                responseXml.append("			<strFreeMinRemain>996741939</strFreeMinRemain>");
                responseXml.append("			<strFreeMinRoll></strFreeMinRoll>");
                responseXml.append("			<strFreeMinTotal>999999999</strFreeMinTotal>");
                responseXml.append("			<strFreeMinUse>3258060</strFreeMinUse>");
                responseXml.append("			<strSecsToAmt>0</strSecsToAmt>");
                responseXml.append("			<strSecsToRate>0</strSecsToRate>");
                responseXml.append("			<strSvcName>속도제어(QoS)데이터-합계</strSvcName>");
                responseXml.append("		</totalUseTimeDto>");
                responseXml.append("		<voiceCallDetailDto>");
                responseXml.append("			<strBunGun>V</strBunGun>");
                responseXml.append("			<strFreeMinCur>999999999</strFreeMinCur>");
                responseXml.append("			<strFreeMinRemain>999999246</strFreeMinRemain>");
                responseXml.append("			<strFreeMinRoll>0</strFreeMinRoll>");
                responseXml.append("			<strFreeMinTotal>999999999</strFreeMinTotal>");
                responseXml.append("			<strFreeMinUse>753</strFreeMinUse>");
                responseXml.append("			<strSvcName>모두다 맘껏 안심 1.5GB+</strSvcName>");
                responseXml.append("		</voiceCallDetailDto>");
                responseXml.append("		<voiceCallDetailTotDto>");
                responseXml.append("			<tottal>합계</tottal>");
                responseXml.append("			<iFreeMinCurSum>999999999</iFreeMinCurSum>");
                responseXml.append("			<iFreeMinRemainSum>999999246</iFreeMinRemainSum>");
                responseXml.append("			<iFreeMinRollSum>0</iFreeMinRollSum>");
                responseXml.append("			<iFreeMinTotalSum>999999999</iFreeMinTotalSum>");
                responseXml.append("			<iFreeMinUseSum>753</iFreeMinUseSum>");
                responseXml.append("		</voiceCallDetailTotDto>");
                responseXml.append("	</outDto>");
                responseXml.append("</return>");

                break;
            case 15://요금조회------201512
                //			responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X15</appEventCd><appSendDateTime>20160119160359</appSendDateTime><appRecvDateTime>20160119160358</appRecvDateTime><appLgDateTime>20160119160358</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160119160357902</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><ban>976563580</ban><billEndDateList>20151231|20151130|20151031|20150930|20150831|20150731</billEndDateList><billSeqNumList>6|5|4|3|2|1</billSeqNumList><billStartDateList>20151201|20151101|20151001|20150901|20150801|20150715</billStartDateList><ctnNumTotalSum>0.0</ctnNumTotalSum><ctnNumproductionDate>201512|201511|201510|201509|201508|201507</ctnNumproductionDate><payMentDTO><billDueDateList>20160106|20151204|20151104|20151004|20150904|20150804</billDueDateList><billEndDate>20151231</billEndDate><billMonth>20160106</billMonth><billSeqNo>6</billSeqNo><billStartDate>20151201</billStartDate><pastDueAmt>28690</pastDueAmt><thisMonth>26540</thisMonth><totalDueAmt>55230</totalDueAmt></payMentDTO><payMentDTO><billDueDateList>20160106|20151204|20151104|20151004|20150904|20150804</billDueDateList><billEndDate>20151130</billEndDate><billMonth>20151204</billMonth><billSeqNo>5</billSeqNo><billStartDate>20151101</billStartDate><pastDueAmt>0</pastDueAmt><thisMonth>28690</thisMonth><totalDueAmt>28690</totalDueAmt></payMentDTO><payMentDTO><billDueDateList>20160106|20151204|20151104|20151004|20150904|20150804</billDueDateList><billEndDate>20151031</billEndDate><billMonth>20151104</billMonth><billSeqNo>4</billSeqNo><billStartDate>20151001</billStartDate><pastDueAmt>0</pastDueAmt><thisMonth>22880</thisMonth><totalDueAmt>22880</totalDueAmt></payMentDTO><payMentDTO><billDueDateList>20160106|20151204|20151104|20151004|20150904|20150804</billDueDateList><billEndDate>20150930</billEndDate><billMonth>20151004</billMonth><billSeqNo>3</billSeqNo><billStartDate>20150901</billStartDate><pastDueAmt>0</pastDueAmt><thisMonth>25280</thisMonth><totalDueAmt>25280</totalDueAmt></payMentDTO><payMentDTO><billDueDateList>20160106|20151204|20151104|20151004|20150904|20150804</billDueDateList><billEndDate>20150831</billEndDate><billMonth>20150904</billMonth><billSeqNo>2</billSeqNo><billStartDate>20150801</billStartDate><pastDueAmt>-10080</pastDueAmt><thisMonth>25280</thisMonth><totalDueAmt>15200</totalDueAmt></payMentDTO><payMentDTO><billDueDateList>20160106|20151204|20151104|20151004|20150904|20150804</billDueDateList><billEndDate>20150731</billEndDate><billMonth>20150804</billMonth><billSeqNo>1</billSeqNo><billStartDate>20150715</billStartDate><pastDueAmt>-30000</pastDueAmt><thisMonth>19970</thisMonth><totalDueAmt>-10030</totalDueAmt></payMentDTO></outDto></return>");
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X15</appEventCd><appSendDateTime>20220810010036</appSendDateTime><appRecvDateTime>20220810010035</appRecvDateTime><appLgDateTime>20220810010035</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220810010125312</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><ban>94028246820</ban><billEndDateList>20220630|20220531</billEndDateList><billSeqNumList></billSeqNumList><billStartDateList>20220601|20220506</billStartDateList><ctnNumTotalSum>0.0</ctnNumTotalSum><ctnNumproductionDate>202206|202205</ctnNumproductionDate><payMentDTO><billDueDateList>20220705|20220605</billDueDateList><billEndDate>20220630</billEndDate><billMonth>20220705</billMonth><billSeqNo>0</billSeqNo><billStartDate>20220601</billStartDate><pastDueAmt>0</pastDueAmt><thisMonth>138780</thisMonth><totalDueAmt>138780</totalDueAmt></payMentDTO><payMentDTO><billDueDateList>20220705|20220605</billDueDateList><billEndDate>20220531</billEndDate><billMonth>20220605</billMonth><billSeqNo>0</billSeqNo><billStartDate>20220506</billStartDate><pastDueAmt>0</pastDueAmt><thisMonth>126070</thisMonth><totalDueAmt>126070</totalDueAmt></payMentDTO></outDto></return>");
                break;
            case 16://요금조회상세------17, 20151201|20151101|20151001|20150901|20150801|20150701|, 201512, 20151201, 20151231
                //			responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X16</appEventCd><appSendDateTime>20160112160926</appSendDateTime><appRecvDateTime>20160112160925</appRecvDateTime><appLgDateTime>20160112160925</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160112160919038</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><dateView>201512|201511|201510|201509|201508|201507</dateView><detListDto><actvAmt>18000</actvAmt><billSeqNo>17</billSeqNo><messageLine>MA000</messageLine><splitDescription>월정액</splitDescription></detListDto><detListDto><actvAmt>1800</actvAmt><billSeqNo></billSeqNo><messageLine></messageLine><splitDescription>할인전부가세(세금)</splitDescription></detListDto><detListDto><actvAmt>19800</actvAmt><billSeqNo></billSeqNo><messageLine></messageLine><splitDescription>당월 요금</splitDescription></detListDto><detListDto><actvAmt>0</actvAmt><billSeqNo></billSeqNo><messageLine></messageLine><splitDescription>미납요금</splitDescription></detListDto><detListDto><actvAmt>19800</actvAmt><billSeqNo></billSeqNo><messageLine></messageLine><splitDescription>납부하실 금액</splitDescription></detListDto><useDate>1201~1231</useDate></outDto></return>");
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X16</appEventCd><appSendDateTime>20220810010036</appSendDateTime><appRecvDateTime>20220810010036</appRecvDateTime><appLgDateTime>20220810010036</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220810010125881</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><dateView>202207|202206</dateView><detListDto><actvAmt>12000</actvAmt><billSeqNo></billSeqNo><messageLine>GMMA0000</messageLine><splitDescription>월정액   ▶ 초알뜰 1GB/60분</splitDescription></detListDto><detListDto><actvAmt>134890</actvAmt><billSeqNo></billSeqNo><messageLine>GMPY0000</messageLine><splitDescription>소액결제</splitDescription></detListDto><detListDto><actvAmt>-8</actvAmt><billSeqNo></billSeqNo><messageLine></messageLine><splitDescription>10원미만할인요금</splitDescription></detListDto><detListDto><actvAmt>1200</actvAmt><billSeqNo></billSeqNo><messageLine></messageLine><splitDescription>부가가치세</splitDescription></detListDto><detListDto><actvAmt>138780</actvAmt><billSeqNo></billSeqNo><messageLine></messageLine><splitDescription>당월요금계</splitDescription></detListDto><detListDto><actvAmt>138780</actvAmt><billSeqNo></billSeqNo><messageLine></messageLine><splitDescription>납부하실 금액</splitDescription></detListDto><detListDto><actvAmt>-9302</actvAmt><billSeqNo></billSeqNo><messageLine>DISCBYSVC</messageLine><splitDescription>할인요금</splitDescription></detListDto><hndFarDto><subscriberNo></subscriberNo><installmentAmt>180000</installmentAmt><totalNoOfInstall>6 개월</totalNoOfInstall></hndFarDto><useDate>0601~0630</useDate></outDto></return>");
                break;
            case 17://요금항목별조회------17, 201512, DCNOR
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X17</appEventCd><appSendDateTime>20160112161437</appSendDateTime><appRecvDateTime>20160112161436</appRecvDateTime><appLgDateTime>20160112161436</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160112161432843</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><amt>0</amt><descr>요금할인액 부가세(세금)</descr></outDto></return>");
                break;
            case 171://요금항목별조회test1------13, 201603, DCNOR  //할인전부가세
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X17</appEventCd><appSendDateTime>20160302120743</appSendDateTime><appRecvDateTime>20160302120742</appRecvDateTime><appLgDateTime>20160302120742</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160302120741935</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><amt>900</amt><descr>요금할인액 부가세(세금)</descr></outDto><outDto><amt>9000</amt><descr>요금할인-알뜰폰</descr></outDto></return>");//
                break;
            case 172://요금항목별조회test2------13, 201603, PY000  //소액결제
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X17</appEventCd><appSendDateTime>20160302121054</appSendDateTime><appRecvDateTime>20160302121053</appRecvDateTime><appLgDateTime>20160302121053</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160302121053221</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><amt>1000</amt><descr>실물결제(다날)</descr></outDto><outDto><amt>2000</amt><descr>실물결제(test)</descr></outDto></return>");
                break;
            case 173://요금항목별조회test3------13, 201603, MA000  //월정액
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X17</appEventCd><appSendDateTime>20160302121232</appSendDateTime><appRecvDateTime>20160302121231</appRecvDateTime><appLgDateTime>20160302121231</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160302121231148</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><amt>35000</amt><descr>월정액</descr></outDto></return>");
                break;
            case 18://실시간요금조회------
                //			responseXml.append("response massage is null.");//비정상
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X18</appEventCd><appSendDateTime>20160112161906</appSendDateTime><appRecvDateTime>20160112161905</appRecvDateTime><appLgDateTime>20160112161905</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160112161901518</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><amntDto><gubun>사용요금</gubun><payMent>15870</payMent></amntDto><amntDto><gubun>당월요금계</gubun><payMent>15870</payMent></amntDto><searchDay>20260416</searchDay><searchTime>101500</searchTime></outDto></return>");
                break;
            case 19://요금상품변경
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X19</appEventCd><appSendDateTime>20160114133336</appSendDateTime><appRecvDateTime>20160114133334</appRecvDateTime><appLgDateTime>20160114133334</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160114133330197</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader></return>");
                //responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X19</appEventCd><appSendDateTime>20160114133336</appSendDateTime><appRecvDateTime>20160114133334</appRecvDateTime><appLgDateTime>20160114133334</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160114133330197</globalNo><encYn></encYn><responseType>S</responseType><responseCode>ITL_SYS_E9999</responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic>M-PLATFORM SYSTEM ERROR.</responseBasic><langCode></langCode><filler></filler></commHeader></return>");
                break;
            case 20://가입중부가서비스조회------
                //responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X20</appEventCd><appSendDateTime>20220331105448</appSendDateTime><appRecvDateTime>20220331105446</appRecvDateTime><appLgDateTime>20220331105446</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220331105042626</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><outDto><effectiveDate>20211110144758</effectiveDate><prodHstSeq>300000783571478</prodHstSeq><soc>PL19AS353</soc><socDescription>M 요금할인 5000(VAT포함)</socDescription><socRateValue>-4,546 WON</socRateValue></outDto><outDto><effectiveDate>20191031174605</effectiveDate><prodHstSeq>300000444731236</prodHstSeq><soc>MPAYBLOCK</soc><socDescription>휴대폰결제 이용거부</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20191031174605</effectiveDate><prodHstSeq>300000444731240</prodHstSeq><soc>NOSPAM3</soc><socDescription>정보제공사업자번호차단</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20210115101023</effectiveDate><prodHstSeq>300000645764018</prodHstSeq><soc>LTECERTID</soc><socDescription>LTE_인증상품</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20191031174605</effectiveDate><prodHstSeq>300000444731239</prodHstSeq><soc>WVMS</soc><socDescription>통합사서함</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20210730111701</effectiveDate><prodHstSeq>300000736324810</prodHstSeq><soc>VLTEAUTSV</soc><socDescription>HD 보이스</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20220331160000</effectiveDate><prodHstSeq>300000845621282</prodHstSeq><soc>PL2078760</soc><socDescription>로밍 하루종일ON</socDescription><socRateValue>10,000 WON</socRateValue></outDto><outDto><effectiveDate>20210728154003</effectiveDate><prodHstSeq>300000735456700</prodHstSeq><soc>SMARTNMON</soc><socDescription>스마트폰(종량)-일반</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20191031174605</effectiveDate><prodHstSeq>300000444731242</prodHstSeq><soc>SPMFILTER</soc><socDescription>스팸차단서비스</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20211110144758</effectiveDate><prodHstSeq>300000783571477</prodHstSeq><soc>PL19AS352</soc><socDescription>M 요금할인 3000(VAT포함)</socDescription><socRateValue>-2,728 WON</socRateValue></outDto><outDto><effectiveDate>20191031174605</effectiveDate><prodHstSeq>300000444731241</prodHstSeq><soc>SMSB</soc><socDescription>SMS(문자서비스) 기본제공</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20210730111701</effectiveDate><prodHstSeq>300000736324809</prodHstSeq><soc>PSVTAUTSV</soc><socDescription>HD 영상통화</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20191031174605</effectiveDate><prodHstSeq>300000444731237</prodHstSeq><soc>CLIPF</soc><socDescription>발신번호표시무료</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20210401085835</effectiveDate><prodHstSeq>300000682894264</prodHstSeq><soc>LTEULTDC5</soc><socDescription>LTE안심QoS옵션 프로모션할인</socDescription><socRateValue>-5,000 WON</socRateValue></outDto></outDto></return>");
                //responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X20</appEventCd><appSendDateTime>20220330185419</appSendDateTime><appRecvDateTime>20220330185417</appRecvDateTime><appLgDateTime>20220330185417</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220330185015020</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><outDto><effectiveDate>20211110144758</effectiveDate><prodHstSeq>300000783571478</prodHstSeq><soc>PL19AS353</soc><socDescription>M 요금할인 5000(VAT포함)</socDescription><socRateValue>-4,546 WON</socRateValue></outDto><outDto><effectiveDate>20191031174605</effectiveDate><prodHstSeq>300000444731236</prodHstSeq><soc>MPAYBLOCK</soc><socDescription>휴대폰결제 이용거부</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20191031174605</effectiveDate><prodHstSeq>300000444731240</prodHstSeq><soc>NOSPAM3</soc><socDescription>정보제공사업자번호차단</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20210115101023</effectiveDate><prodHstSeq>300000645764018</prodHstSeq><soc>LTECERTID</soc><socDescription>LTE_인증상품</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20220330220000</effectiveDate><prodHstSeq>300000845143353</prodHstSeq><soc>PL2078760</soc><socDescription>로밍 하루종일ON</socDescription><socRateValue>10,000 WON</socRateValue></outDto><outDto><effectiveDate>20191031174605</effectiveDate><prodHstSeq>300000444731239</prodHstSeq><soc>WVMS</soc><socDescription>통합사서함</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20210730111701</effectiveDate><prodHstSeq>300000736324810</prodHstSeq><soc>VLTEAUTSV</soc><socDescription>HD 보이스</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20210728154003</effectiveDate><prodHstSeq>300000735456700</prodHstSeq><soc>SMARTNMON</soc><socDescription>스마트폰(종량)-일반</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20191031174605</effectiveDate><prodHstSeq>300000444731242</prodHstSeq><soc>SPMFILTER</soc><socDescription>스팸차단서비스</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20211110144758</effectiveDate><prodHstSeq>300000783571477</prodHstSeq><soc>PL19AS352</soc><socDescription>M 요금할인 3000(VAT포함)</socDescription><socRateValue>-2,728 WON</socRateValue></outDto><outDto><effectiveDate>20191031174605</effectiveDate><prodHstSeq>300000444731241</prodHstSeq><soc>SMSB</soc><socDescription>SMS(문자서비스) 기본제공</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20210730111701</effectiveDate><prodHstSeq>300000736324809</prodHstSeq><soc>PSVTAUTSV</soc><socDescription>HD 영상통화</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20191031174605</effectiveDate><prodHstSeq>300000444731237</prodHstSeq><soc>CLIPF</soc><socDescription>발신번호표시무료</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20210401085835</effectiveDate><prodHstSeq>300000682894264</prodHstSeq><soc>LTEULTDC5</soc><socDescription>LTE안심QoS옵션 프로모션할인</socDescription><socRateValue>-5,000 WON</socRateValue></outDto></outDto></return>");
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X20</appEventCd><appSendDateTime>20160406161559</appSendDateTime><appRecvDateTime>20160406161557</appRecvDateTime><appLgDateTime>20160406161557</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160406161556269</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><outDto><effectiveDate>20160328134219</effectiveDate><soc>DTPLSU100</soc><socDescription>데이터플러스 m(결합) 100M</socDescription><socRateValue>5,000 WON</socRateValue></outDto><outDto><effectiveDate>20160328134233</effectiveDate><soc>DTPLSU500</soc><socDescription>데이터플러스 m(결합) 500M</socDescription><socRateValue>10,000 WON</socRateValue></outDto><outDto><effectiveDate>20160323030009</effectiveDate><soc>RCC1</soc><socDescription>통화가능알리미</socDescription><socRateValue>500 WON</socRateValue></outDto><outDto><effectiveDate>20160323094134</effectiveDate><soc>INTLIST</soc><socDescription>국제통화내역통보</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160208150750</effectiveDate><soc>NESPFMCD3</soc><socDescription>olleh WiFi싱글(무료)</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160208150750</effectiveDate><soc>VLTEAUTSV</soc><socDescription>HD 보이스</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160208150750</effectiveDate><soc>PSVTAUTSV</soc><socDescription>HD 영상통화</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160328135921</effectiveDate><soc>WIFISGLM4</soc><socDescription>WiFi 싱글 할인M6</socDescription><socRateValue>-6,000 WON</socRateValue></outDto><outDto><effectiveDate>20160208150750</effectiveDate><soc>WVMS</soc><socDescription>통합사서함</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160328134206</effectiveDate><soc>DTPLSU02G</soc><socDescription>데이터플러스 m(결합) 2G</socDescription><socRateValue>20,000 WON</socRateValue></outDto><outDto><effectiveDate>20160208150750</effectiveDate><soc>NOSPAM3</soc><socDescription>정보제공사업자번호차단</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160328135241</effectiveDate><soc>RCC1R</soc><socDescription>통화가능알리미 거부</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160328135427</effectiveDate><soc>SMS26N</soc><socDescription>신메시지매니저</socDescription><socRateValue>900 WON</socRateValue></outDto><outDto><effectiveDate>20160208150750</effectiveDate><soc>SMSB</soc><socDescription>SMS(문자서비스) 기본제공</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160328135627</effectiveDate><soc>USEBILSMS</soc><socDescription>이용요금내역알리미</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160322224830</effectiveDate><soc>CYBDANGNT</soc><socDescription>정보보호알림이(일반)</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160323040140</effectiveDate><soc>ITC</soc><socDescription>국제전화 발신제한</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160323025931</effectiveDate><soc>LOC119</soc><socDescription>119 긴급구조 위치제공</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160323134617</effectiveDate><soc>MMCISS</soc><socDescription>쇼미</socDescription><socRateValue>900 WON</socRateValue></outDto><outDto><effectiveDate>20160323134748</effectiveDate><soc>NOIPCRVE</soc><socDescription>음성로밍 완전 차단</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160323134455</effectiveDate><soc>RCC2</soc><socDescription>통화요구알리미</socDescription><socRateValue>500 WON</socRateValue></outDto><outDto><effectiveDate>20160323040102</effectiveDate><soc>CNIRDO</soc><socDescription>익명호수신거부</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160328134003</effectiveDate><soc>CNIRS</soc><socDescription>발신번호표시제한</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160208150750</effectiveDate><soc>MPAYBLOCK</soc><socDescription>휴대폰결제 이용거부</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160208150750</effectiveDate><soc>SPMFILTER</soc><socDescription>스팸차단서비스</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160322224737</effectiveDate><soc>WFSMSNDSP</soc><socDescription>웹 및 국외발신 미표시 서비스</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160328135909</effectiveDate><soc>WIFISGLM3</soc><socDescription>WiFi 싱글 M3</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160325200008</effectiveDate><soc>XRINGSMS</soc><socDescription>링투유알리미</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160208150750</effectiveDate><soc>CLIPF</soc><socDescription>발신번호표시무료</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160328134153</effectiveDate><soc>DTPLSU01G</soc><socDescription>데이터플러스 m(결합) 1G</socDescription><socRateValue>15,000 WON</socRateValue></outDto><outDto><effectiveDate>20160323065520</effectiveDate><soc>NOIPCRDT</soc><socDescription>데이터로밍 완전 차단</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160328135150</effectiveDate><soc>PPINFO</soc><socDescription>요금납부알림서비스</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160208150750</effectiveDate><soc>SMARTNMON</soc><socDescription>스마트폰(종량)-일반</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160328135933</effectiveDate><soc>WIRELESSC</soc><socDescription>무선데이터차단서비스</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160208150750</effectiveDate><soc>AIPNESPOT</soc><socDescription>WiFi 인증서비스</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160323065714</effectiveDate><soc>CATCHCALL</soc><socDescription>캐치콜서비스</socDescription><socRateValue>500 WON</socRateValue></outDto><outDto><effectiveDate>20160323025938</effectiveDate><soc>DPCBLC060</soc><socDescription>060발신차단서비스(무료)</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160208150750</effectiveDate><soc>LTECERTID</soc><socDescription>LTE_인증상품</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160323135150</effectiveDate><soc>XRING</soc><socDescription>링투유</socDescription><socRateValue>900 WON</socRateValue></outDto></outDto></return>");
                //                responseXml.append("<return>");
                //                responseXml.append("	<bizHeader>");
                //                responseXml.append("		<appEntrPrsnId>KIS</appEntrPrsnId>");
                //                responseXml.append("		<appAgncCd>AA00364</appAgncCd>");
                //                responseXml.append("		<appEventCd>X20</appEventCd>");
                //                responseXml.append("		<appSendDateTime>20220331105448</appSendDateTime>");
                //                responseXml.append("		<appRecvDateTime>20220331105446</appRecvDateTime>");
                //                responseXml.append("		<appLgDateTime>20220331105446</appLgDateTime>");
                //                responseXml.append("		<appNstepUserId>91225330</appNstepUserId>");
                //                responseXml.append("		<appOrderId></appOrderId>");
                //                responseXml.append("	</bizHeader>");
                //                responseXml.append("	<commHeader>");
                //                responseXml.append("		<globalNo>9122533020220331105042626</globalNo>");
                //                responseXml.append("		<encYn></encYn>");
                //                responseXml.append("		<responseType>N</responseType>");
                //                responseXml.append("		<responseCode></responseCode>");
                //                responseXml.append("		<responseLogcd></responseLogcd>");
                //                responseXml.append("		<responseTitle></responseTitle>");
                //                responseXml.append("		<responseBasic></responseBasic>");
                //                responseXml.append("		<langCode></langCode>");
                //                responseXml.append("		<filler></filler>");
                //                responseXml.append("	</commHeader>");
                //                responseXml.append("	<outDto>");
                //                responseXml.append("		<outDto>");
                //                responseXml.append("			<effectiveDate>20191031174605</effectiveDate>");
                //                responseXml.append("			<prodHstSeq>300000444731236</prodHstSeq>");
                //                responseXml.append("			<soc>MPAYBLOCK</soc>");
                //                responseXml.append("			<socDescription>휴대폰결제 이용거부</socDescription>");
                //                responseXml.append("			<socRateValue>Free</socRateValue>");
                //                responseXml.append("		</outDto>");
                //                responseXml.append("		<outDto>");
                //                responseXml.append("			<effectiveDate>20191031174605</effectiveDate>");
                //                responseXml.append("			<prodHstSeq>300000444731240</prodHstSeq>");
                //                responseXml.append("			<soc>NOSPAM3</soc>");
                //                responseXml.append("			<socDescription>정보제공사업자번호차단</socDescription>");
                //                responseXml.append("			<socRateValue>Free</socRateValue>");
                //                responseXml.append("		</outDto>");
                //                responseXml.append("		<outDto>");
                //                responseXml.append("			<effectiveDate>20210115101023</effectiveDate>");
                //                responseXml.append("			<prodHstSeq>300000645764018</prodHstSeq>");
                //                responseXml.append("			<soc>LTECERTID</soc>");
                //                responseXml.append("			<socDescription>LTE_인증상품</socDescription>");
                //                responseXml.append("			<socRateValue>Free</socRateValue>");
                //                responseXml.append("		</outDto>");
                //                responseXml.append("		<outDto>");
                //                responseXml.append("			<effectiveDate>20191031174605</effectiveDate>");
                //                responseXml.append("			<prodHstSeq>300000444731239</prodHstSeq>");
                //                responseXml.append("			<soc>WVMS</soc>");
                //                responseXml.append("			<socDescription>통합사서함</socDescription>");
                //                responseXml.append("			<socRateValue>Free</socRateValue>");
                //                responseXml.append("		</outDto>");
                //                responseXml.append("		<outDto>");
                //                responseXml.append("			<effectiveDate>20210730111701</effectiveDate>");
                //                responseXml.append("			<prodHstSeq>300000736324810</prodHstSeq>");
                //                responseXml.append("			<soc>VLTEAUTSV</soc>");
                //                responseXml.append("			<socDescription>HD 보이스</socDescription>");
                //                responseXml.append("			<socRateValue>Free</socRateValue>");
                //                responseXml.append("		</outDto>");
                //                responseXml.append("		<outDto>");
                //                responseXml.append("			<effectiveDate>20220331160000</effectiveDate>");
                //                responseXml.append("			<prodHstSeq>300000845621282</prodHstSeq>");
                //                responseXml.append("			<soc>PL2078760</soc>");
                //                responseXml.append("			<socDescription>로밍 하루종일ON</socDescription>");
                //                responseXml.append("			<socRateValue>10,000 WON</socRateValue>");
                //                responseXml.append("		</outDto>");
                //                responseXml.append("		<outDto>");
                //                responseXml.append("			<effectiveDate>20210728154003</effectiveDate>");
                //                responseXml.append("			<prodHstSeq>300000735456700</prodHstSeq>");
                //                responseXml.append("			<soc>SMARTNMON</soc>");
                //                responseXml.append("			<socDescription>스마트폰(종량)-일반</socDescription>");
                //                responseXml.append("			<socRateValue>Free</socRateValue>");
                //                responseXml.append("		</outDto>");
                //                responseXml.append("		<outDto>");
                //                responseXml.append("			<effectiveDate>20191031174605</effectiveDate>");
                //                responseXml.append("			<prodHstSeq>300000444731242</prodHstSeq>");
                //                responseXml.append("			<soc>SPMFILTER</soc>");
                //                responseXml.append("			<socDescription>스팸차단서비스</socDescription>");
                //                responseXml.append("			<socRateValue>Free</socRateValue>");
                //                responseXml.append("		</outDto>");
                //                responseXml.append("		<outDto>");
                //                responseXml.append("			<effectiveDate>20211110144758</effectiveDate>");
                //                responseXml.append("			<prodHstSeq>300000783571477</prodHstSeq>");
                //                responseXml.append("			<soc>PL19AS352</soc>");
                //                responseXml.append("			<socDescription>M 요금할인 3000(VAT포함)</socDescription>");
                //                responseXml.append("			<socRateValue>-2,728 WON</socRateValue>");
                //                responseXml.append("		</outDto>");
                //                responseXml.append("		<outDto>");
                //                responseXml.append("			<effectiveDate>20191031174605</effectiveDate>");
                //                responseXml.append("			<prodHstSeq>300000444731241</prodHstSeq>");
                //                responseXml.append("			<soc>SMSB</soc>");
                //                responseXml.append("			<socDescription>SMS(문자서비스) 기본제공</socDescription>");
                //                responseXml.append("			<socRateValue>Free</socRateValue>");
                //                responseXml.append("		</outDto>");
                //                responseXml.append("		<outDto>");
                //                responseXml.append("			<effectiveDate>20210730111701</effectiveDate>");
                //                responseXml.append("			<prodHstSeq>300000736324809</prodHstSeq>");
                //                responseXml.append("			<soc>PSVTAUTSV</soc>");
                //                responseXml.append("			<socDescription>HD 영상통화</socDescription>");
                //                responseXml.append("			<socRateValue>Free</socRateValue>");
                //                responseXml.append("		</outDto>");
                //                responseXml.append("		<outDto>");
                //                responseXml.append("			<effectiveDate>20191031174605</effectiveDate>");
                //                responseXml.append("			<prodHstSeq>300000444731237</prodHstSeq>");
                //                responseXml.append("			<soc>CLIPF</soc>");
                //                responseXml.append("			<socDescription>발신번호표시무료</socDescription>");
                //                responseXml.append("			<socRateValue>Free</socRateValue>");
                //                responseXml.append("		</outDto>");
                //                responseXml.append("	</outDto>");
                //                responseXml.append("</return>");

                break;
            case 21://부가서비스신청
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>INL</appEntrPrsnId><appAgncCd>AA11070</appAgncCd><appEventCd>X21</appEventCd><appSendDateTime>20240311145506</appSendDateTime><appRecvDateTime>20240311145501</appRecvDateTime><appLgDateTime>20240311145501</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9911100201502061201011234</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader></return>");
                //responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X21</appEventCd><appSendDateTime>20220311145506</appSendDateTime><appRecvDateTime>20220311145501</appRecvDateTime><appLgDateTime>20220311145501</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220311145132712</globalNo><encYn></encYn><responseType>E</responseType><responseCode>ITL_SFC_E021</responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic>[오토링]상품과 [링투유] 상품은 동시에 가입할 수 없습니다.</responseBasic><langCode></langCode><filler></filler></commHeader></return>");
                break;
            case 22://납부/미납요금조회------

                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X22</appEventCd><appSendDateTime>20220810010038</appSendDateTime><appRecvDateTime>20220810010037</appRecvDateTime><appLgDateTime>20220810010037</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220810010126507</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><noDate>현재 미납된 요금이 없습니다.</noDate><outItemPayDto><confirmDate>20220129</confirmDate><payMentDate>20220129</payMentDate><payMentMethod>간편결제</payMentMethod><payMentMoney>803</payMentMoney></outItemPayDto><outItemPayDto><confirmDate>20220229</confirmDate><payMentDate>20220229</payMentDate><payMentMethod>온라인 카드수납</payMentMethod><payMentMoney>5999</payMentMoney></outItemPayDto><outItemPayDto><confirmDate>20220328</confirmDate><payMentDate>20220328</payMentDate><payMentMethod>온라인 카드수납</payMentMethod><payMentMoney>5999</payMentMoney></outItemPayDto><outItemPayDto><confirmDate>20220427</confirmDate><payMentDate>20220427</payMentDate><payMentMethod>온라인 카드수납</payMentMethod><payMentMoney>5999</payMentMoney></outItemPayDto><outItemPayDto><confirmDate>20220526</confirmDate><payMentDate>20220526</payMentDate><payMentMethod>온라인 카드수납</payMentMethod><payMentMoney>5999</payMentMoney></outItemPayDto><outItemPayDto><confirmDate>20220625</confirmDate><payMentDate>20220625</payMentDate><payMentMethod>온라인 카드수납</payMentMethod><payMentMoney>5999</payMentMoney></outItemPayDto><outItemPayDto><confirmDate>20220724</confirmDate><payMentDate>20220724</payMentDate><payMentMethod>온라인 카드수납</payMentMethod><payMentMoney>5999</payMentMoney></outItemPayDto><outItemPayDto><confirmDate>20220823</confirmDate><payMentDate>20220823</payMentDate><payMentMethod>온라인 카드수납</payMentMethod><payMentMoney>5999</payMentMoney></outItemPayDto><outItemPayDto><confirmDate>20220922</confirmDate><payMentDate>20220922</payMentDate><payMentMethod>온라인 카드수납</payMentMethod><payMentMoney>5999</payMentMoney></outItemPayDto><outItemPayDto><confirmDate>20221021</confirmDate><payMentDate>20221021</payMentDate><payMentMethod>온라인 카드수납</payMentMethod><payMentMoney>5999</payMentMoney></outItemPayDto><outItemPayDto><confirmDate>20221120</confirmDate><payMentDate>20221120</payMentDate><payMentMethod>온라인 카드수납</payMentMethod><payMentMoney>5999</payMentMoney></outItemPayDto><outItemPayDto><confirmDate>20221219</confirmDate><payMentDate>20221219</payMentDate><payMentMethod>온라인 카드수납</payMentMethod><payMentMoney>5999</payMentMoney></outItemPayDto></outDto></return>");

                //            responseXml.append("<return>");
                //            responseXml.append("    <bizHeader>");
                //            responseXml.append("        <appEntrPrsnId>KIS</appEntrPrsnId>");
                //            responseXml.append("        <appAgncCd>AA00364</appAgncCd>");
                //            responseXml.append("        <appEventCd>X22</appEventCd>");
                //            responseXml.append("        <appSendDateTime>20220616131515</appSendDateTime>");
                //            responseXml.append("        <appRecvDateTime>20220616131515</appRecvDateTime>");
                //            responseXml.append("        <appLgDateTime>20220616131515</appLgDateTime>");
                //            responseXml.append("        <appNstepUserId>91225330</appNstepUserId>");
                //            responseXml.append("        <appOrderId></appOrderId>");
                //            responseXml.append("    </bizHeader>");
                //            responseXml.append("    <commHeader>");
                //            responseXml.append("        <globalNo>9122533020220616131544017</globalNo>");
                //            responseXml.append("        <encYn></encYn>");
                //            responseXml.append("        <responseType>S</responseType>");
                //            responseXml.append("        <responseCode>ITL_SYS_E0001</responseCode>");
                //            responseXml.append("        <responseLogcd></responseLogcd>");
                //            responseXml.append("        <responseTitle></responseTitle>");
                //            responseXml.append("        <responseBasic>NSTEP ESB 연동 오류.</responseBasic>");
                //            responseXml.append("        <langCode></langCode>");
                //            responseXml.append("        <filler></filler>");
                //            responseXml.append("    </commHeader>");
                //            responseXml.append("</return>");


                //        	responseXml.append("<return>");
                //            responseXml.append("	<bizHeader>");
                //            responseXml.append("		<appEntrPrsnId>KIS</appEntrPrsnId>");
                //            responseXml.append("		<appAgncCd>AA00364</appAgncCd>");
                //            responseXml.append("		<appEventCd>X22</appEventCd>");
                //            responseXml.append("		<appSendDateTime>20160112162057</appSendDateTime>");
                //            responseXml.append("		<appRecvDateTime>20160112162055</appRecvDateTime>");
                //            responseXml.append("		<appLgDateTime>20160112162055</appLgDateTime>");
                //            responseXml.append("		<appNstepUserId>91060728</appNstepUserId>");
                //            responseXml.append("		<appOrderId></appOrderId>");
                //            responseXml.append("	</bizHeader>");
                //            responseXml.append("	<commHeader>");
                //            responseXml.append("		<globalNo>9106072820160112162055330</globalNo>");
                //            responseXml.append("		<encYn></encYn>");
                //            responseXml.append("		<responseType>N</responseType>");
                //            //responseXml.append("		<responseType>E</responseType>");
                //            responseXml.append("		<responseCode></responseCode>");
                //            //responseXml.append("		<responseCode>ITL_SYS_E001</responseCode>");
                //            responseXml.append("		<responseLogcd></responseLogcd>");
                //            responseXml.append("		<responseTitle></responseTitle>");
                //            responseXml.append("		<responseBasic></responseBasic>");
                //            responseXml.append("		<langCode></langCode>");
                //            responseXml.append("		<filler></filler>");
                //            responseXml.append("	</commHeader>");
                //            responseXml.append("	<outDto>");
                //            responseXml.append("		<noDate>현재 미납된 요금이 없습니다.</noDate>");
                //            responseXml.append("		<outItemPayDto>");
                //            responseXml.append("			<confirmDate>20151221</confirmDate>");
                //            responseXml.append("			<payMentDate>20151221</payMentDate>");
                //            responseXml.append("			<payMentMethod>은행계좌자동이체</payMentMethod>");
                //            responseXml.append("			<payMentMoney>19800</payMentMoney>");
                //            responseXml.append("		</outItemPayDto>");
                //            responseXml.append("		<outItemPayDto>");
                //            responseXml.append("			<confirmDate>20151123</confirmDate>");
                //            responseXml.append("			<payMentDate>20151123</payMentDate>");
                //            responseXml.append("			<payMentMethod>은행계좌자동이체</payMentMethod>");
                //            responseXml.append("			<payMentMoney>19800</payMentMoney>");
                //            responseXml.append("		</outItemPayDto>");
                //            responseXml.append("		<outItemPayDto>");
                //            responseXml.append("			<confirmDate>20151021</confirmDate>");
                //            responseXml.append("			<payMentDate>20151021</payMentDate>");
                //            responseXml.append("			<payMentMethod>은행계좌자동이체</payMentMethod>");
                //            responseXml.append("			<payMentMoney>19800</payMentMoney>");
                //            responseXml.append("		</outItemPayDto>");
                //            responseXml.append("		<outItemPayDto>");
                //            responseXml.append("			<confirmDate>20150921</confirmDate>");
                //            responseXml.append("			<payMentDate>20150921</payMentDate>");
                //            responseXml.append("			<payMentMethod>은행계좌자동이체</payMentMethod>");
                //            responseXml.append("			<payMentMoney>19800</payMentMoney>");
                //            responseXml.append("		</outItemPayDto>");
                //            responseXml.append("		<outItemPayDto>");
                //            responseXml.append("			<confirmDate>20150821</confirmDate>");
                //            responseXml.append("			<payMentDate>20150821</payMentDate>");
                //            responseXml.append("			<payMentMethod>은행계좌자동이체</payMentMethod>");
                //            responseXml.append("			<payMentMoney>19800</payMentMoney>");
                //            responseXml.append("		</outItemPayDto>");
                //            responseXml.append("		<outItemPayDto>");
                //            responseXml.append("			<confirmDate>20150721</confirmDate>");
                //            responseXml.append("			<payMentDate>20150721</payMentDate>");
                //            responseXml.append("			<payMentMethod>은행계좌자동이체</payMentMethod>");
                //            responseXml.append("			<payMentMoney>20080</payMentMoney>");
                //            responseXml.append("		</outItemPayDto>");
                //            responseXml.append("	</outDto>");
                //            responseXml.append("</return>");
                break;

            case 23://납부방법조회------
                //자동이체
                //responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X23</appEventCd><appSendDateTime>20160112162141</appSendDateTime><appRecvDateTime>20160112162138</appRecvDateTime><appLgDateTime>20160112162138</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160112162138010</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><bankAcctHolderName>장기용</bankAcctHolderName><billCycleDueDay>21</billCycleDueDay><blBankAcctNo>177620**********</blBankAcctNo><blBankName>수협중앙회</blBankName><payMethod>자동이체</payMethod></outDto></return>");
                //지로
                //                responseXml.append("<return> <bizHeader> <appEntrPrsnId>KIS</appEntrPrsnId> <appAgncCd>AA00364</appAgncCd> <appEventCd>X23</appEventCd> <appSendDateTime>20160112162141</appSendDateTime> <appRecvDateTime>20160112162138</appRecvDateTime> <appLgDateTime>20160112162138</appLgDateTime> <appNstepUserId>91060728</appNstepUserId> <appOrderId></appOrderId> </bizHeader> <commHeader> <globalNo>9106072820160112162138010</globalNo> <encYn></encYn> <responseType>N</responseType> <responseCode></responseCode> <responseLogcd></responseLogcd> <responseTitle></responseTitle> <responseBasic></responseBasic> <langCode></langCode> <filler></filler> </commHeader> <outDto> <bankAcctHolderName>장기용</bankAcctHolderName> <billCycleDueDay>21</billCycleDueDay> <blBankAcctNo>177620**********</blBankAcctNo> <blBankName>수협중앙회</blBankName> <payMethod>지로</payMethod> <blAddr>서울시 삼성동 595-1</blAddr> </outDto> </return>   ");
                //신용카드
                responseXml.append(
                    "<return> <bizHeader> <appEntrPrsnId>KIS</appEntrPrsnId> <appAgncCd>AA00364</appAgncCd> <appEventCd>X23</appEventCd> <appSendDateTime>20160112162141</appSendDateTime> <appRecvDateTime>20160112162138</appRecvDateTime> <appLgDateTime>20160112162138</appLgDateTime> <appNstepUserId>91060728</appNstepUserId> <appOrderId></appOrderId> </bizHeader> <commHeader> <globalNo>9106072820160112162138010</globalNo> <encYn></encYn> <responseType>N</responseType> <responseCode></responseCode> <responseLogcd></responseLogcd> <responseTitle></responseTitle> <responseBasic></responseBasic> <langCode></langCode> <filler></filler> </commHeader> <outDto> <bankAcctHolderName>장기용</bankAcctHolderName> <payMethod>신용카드</payMethod> <billCycleDueDay>21</billCycleDueDay> <prevCardNo>5361489001011656</prevCardNo> <billCycleDueDay>99</billCycleDueDay> <prevExpirDt>20180221</prevExpirDt> <payTmsCd>02</payTmsCd></outDto> </return> ");
                //카카오
                //                responseXml.append("<return>");
                //                responseXml.append("    <bizHeader>");
                //                responseXml.append("        <appEntrPrsnId>KIS</appEntrPrsnId>");
                //                responseXml.append("        <appAgncCd>AA00364</appAgncCd>");
                //                responseXml.append("        <appEventCd>X23</appEventCd>");
                //                responseXml.append("        <appSendDateTime>20210721105427</appSendDateTime>");
                //                responseXml.append("        <appRecvDateTime>20210721105422</appRecvDateTime>");
                //                responseXml.append("        <appLgDateTime>20210721105422</appLgDateTime>");
                //                responseXml.append("        <appNstepUserId>91225330</appNstepUserId>");
                //                responseXml.append("        <appOrderId></appOrderId>");
                //                responseXml.append("    </bizHeader>");
                //                responseXml.append("    <commHeader>");
                //                responseXml.append("        <globalNo>9122533020210721105308250</globalNo>");
                //                responseXml.append("        <encYn></encYn>");
                //                responseXml.append("        <responseType>N</responseType>");
                //                responseXml.append("        <responseCode></responseCode>");
                //                responseXml.append("        <responseLogcd></responseLogcd>");
                //                responseXml.append("        <responseTitle></responseTitle>");
                //                responseXml.append("        <responseBasic></responseBasic>");
                //                responseXml.append("        <langCode></langCode>");
                //                responseXml.append("        <filler></filler>");
                //                responseXml.append("    </commHeader>");
                //                responseXml.append("    <outDto>");
                //                responseXml.append("        <billCycleDueDay>25</billCycleDueDay>");
                //                responseXml.append("        <payBizrCd>KKO</payBizrCd>");
                //                responseXml.append("        <payMethod>간편결제</payMethod>");
                //                responseXml.append("    </outDto>");
                //                responseXml.append("</return>");
                break;
            case 25://납부방법변경
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X01</appEventCd><appSendDateTime>20160112153248</appSendDateTime><appRecvDateTime>20160112153246</appRecvDateTime><appLgDateTime>20160112153246</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160112153243531</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><addr>인천 옹진군 영흥면 선재로34번길 141 </addr><email>bluemoor9521@naver.com</email><homeTel>01075116741</homeTel><initActivationDate>20140807163028</initActivationDate></outDto></return>");
                break;
            case 26://일시정지이력조회(X26)
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KTF</appEntrPrsnId><appAgncCd>AA11070</appAgncCd><appEventCd>X26</appEventCd><appSendDateTime>20150210134545</appSendDateTime><appRecvDateTime>20150210134542</appRecvDateTime><appLgDateTime>20150210134542</appLgDateTime><appNstepUserId>6833564</appNstepUserId><appOrderId/></bizHeader><commHeader><globalNo>9911100201501191201011234</globalNo><encYn/><responseType>N</responseType><responseCode/><responseLogcd/><responseTitle/><responseBasic/><langCode/><filler/></commHeader><outDto><bkInfoDto><colSusDays>20150113</colSusDays><csaActivityRsnDesc>고객요청 - 발신정지</csaActivityRsnDesc><remainOgDays>0</remainOgDays><remainSusCnt>20150113</remainSusCnt></bkInfoDto><bkInfoDto><colSusDays>20150113</colSusDays><csaActivityRsnDesc>고객요청 - 발신정지</csaActivityRsnDesc><remainOgDays>0</remainOgDays><remainSusCnt>20150113</remainSusCnt></bkInfoDto><bkInfoDto><colSusDays>20150113</colSusDays><csaActivityRsnDesc>고객요청 - 발신정지</csaActivityRsnDesc><remainOgDays>0</remainOgDays><remainSusCnt>20150113</remainSusCnt></bkInfoDto><bkInfoDto><colSusDays>20150113</colSusDays><csaActivityRsnDesc>고객요청 - 발신정지</csaActivityRsnDesc><remainOgDays>0</remainOgDays><remainSusCnt>20150113</remainSusCnt></bkInfoDto><bkInfoDto><colSusDays>20150113</colSusDays><csaActivityRsnDesc>고객요청 - 발신정지</csaActivityRsnDesc><remainOgDays>0</remainOgDays><remainSusCnt>20150113</remainSusCnt></bkInfoDto><bkInfoDto><colSusDays>20150113</colSusDays><csaActivityRsnDesc>고객요청 - 발신정지</csaActivityRsnDesc><remainOgDays>0</remainOgDays><remainSusCnt>20150113</remainSusCnt></bkInfoDto><bkInfoDto><colSusDays>20150113</colSusDays><csaActivityRsnDesc>고객요청 - 발신정지</csaActivityRsnDesc><remainOgDays>0</remainOgDays><remainSusCnt>20150113</remainSusCnt></bkInfoDto><colSusDays>0</colSusDays><expectSpDate/><reckonFromDate>20150101</reckonFromDate><remainOgDays>30</remainOgDays><remainSusCnt>2</remainSusCnt><subStatus>A</subStatus><susCnt>0</susCnt><susDays>0</susDays></outDto></return>");
                //responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X27</appEventCd><appSendDateTime>20160112162621</appSendDateTime><appRecvDateTime>20160112162619</appRecvDateTime><appLgDateTime>20160112162619</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160112162619104</globalNo><encYn></encYn><responseType>S</responseType><responseCode>ITL_SYS_E9999</responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic>M-PLATFORM SYSTEM ERROR.</responseBasic><langCode></langCode><filler></filler></commHeader></return>");
                break;
            case 27://일시정지가능여부조회------오류발생
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X27</appEventCd><appSendDateTime>20160211144217</appSendDateTime><appRecvDateTime>20160211144213</appRecvDateTime><appLgDateTime>20160211144213</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160211144209462</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><ctnStatus>A</ctnStatus><insurMsg></insurMsg><rsltInd>Y</rsltInd><rsltMsg></rsltMsg></outDto></return>");
                //responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X27</appEventCd><appSendDateTime>20160112162621</appSendDateTime><appRecvDateTime>20160112162619</appRecvDateTime><appLgDateTime>20160112162619</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160112162619104</globalNo><encYn></encYn><responseType>S</responseType><responseCode>ITL_SYS_E9999</responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic>M-PLATFORM SYSTEM ERROR.</responseBasic><langCode></langCode><filler></filler></commHeader></return>");
                break;
            case 28://일시정지해제 가능여부 조회
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KTF</appEntrPrsnId><appAgncCd>AA11070</appAgncCd><appEventCd>X28</appEventCd><appSendDateTime>20150210134918</appSendDateTime><appRecvDateTime>20150210134918</appRecvDateTime><appLgDateTime>20150210134918</appLgDateTime><appNstepUserId>6833564</appNstepUserId>appOrderId/></bizHeader><commHeader><globalNo>9911100201501191201011234</globalNo><encYn/><responseType>N</responseType><responseCode/><responseLogcd/><responseTitle/><responseBasic/><langCode/><filler/></commHeader><outDto><ctnStatus>A</ctnStatus><rsltInd>Y</rsltInd><rsltMsg/><rsnDesc>-</rsnDesc><sndarvStatCd>01</sndarvStatCd><subStatusDate>20180831133251</subStatusDate><subStatusRsnCode/></outDto></return>");
                //responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X27</appEventCd><appSendDateTime>20160112162621</appSendDateTime><appRecvDateTime>20160112162619</appRecvDateTime><appLgDateTime>20160112162619</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160112162619104</globalNo><encYn></encYn><responseType>S</responseType><responseCode>ITL_SYS_E9999</responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic>M-PLATFORM SYSTEM ERROR.</responseBasic><langCode></langCode><filler></filler></commHeader></return>");
                break;
            case 29://일시정지신청
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X29</appEventCd><appSendDateTime>20160216193710</appSendDateTime><appRecvDateTime>20160216193704</appRecvDateTime><appLgDateTime>20160216193704</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160216193659192</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader></return>");
                //responseXml.append("<return><commHeader><globalNo>9106072820160115153506272</globalNo><encYn/><responseType>S</responseType><responseCode>MCG_SYS_E0500</responseCode><responseLogcd/><responseTitle>시스템 오류</responseTitle><responseBasic>[서버] 시스템 내부처리 오류</responseBasic><langCode/><filler/></commHeader><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X29</appEventCd><appSendDateTime>20160115153506</appSendDateTime><appRecvDateTime/><appLgDateTime/><appNstepUserId>91060728</appNstepUserId><appOrderId/></bizHeader></return>");
                break;
            case 30://일시정지해제신청(X30)
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KTF</appEntrPrsnId><appAgncCd>AA11070</appAgncCd><appEventCd>X02</appEventCd><appSendDateTime>20150210135222</appSendDateTime><appRecvDateTime>20150210135150</appRecvDateTime><appLgDateTime>20150210135150</appLgDateTime><appNstepUserId>6833564</appNstepUserId><appOrderId /></bizHeader><commHeader><globalNo>9911100201501191201011234</globalNo><encYn /><responseType>N</responseType><responseCode /><responseLogcd /><responseTitle /><responseBasic /><langCode /><filler /></commHeader></return>");
                //responseXml.append("<return><commHeader><globalNo>9106072820160114172107306</globalNo><encYn/><responseType>S</responseType><responseCode>MCG_SYS_E0500</responseCode><responseLogcd/><responseTitle>시스템 오류</responseTitle><responseBasic>[서버] 시스템 내부처리 오류</responseBasic><langCode/><filler/></commHeader><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X31</appEventCd><appSendDateTime>20160114172107</appSendDateTime><appRecvDateTime/><appLgDateTime/><appNstepUserId>91060728</appNstepUserId><appOrderId/></bizHeader></return>");
                break;
            case 31://번호목록조회
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X31</appEventCd><appSendDateTime>20160129173453</appSendDateTime><appRecvDateTime>20160129173451</appRecvDateTime><appLgDateTime>20160129173451</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160129173446675</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><ctn>01029026316</ctn><marketGubun>KTF</marketGubun><sctn>scvQDLall75RExOhy2lPqg==</sctn></outDto><outDto><ctn>01029406316</ctn><marketGubun>KTF</marketGubun><sctn>dIRo+cQGphVRExOhy2lPqg==</sctn></outDto><outDto><ctn>01029436316</ctn><marketGubun>KTF</marketGubun><sctn>iwbQp4UTNpdRExOhy2lPqg==</sctn></outDto><outDto><ctn>01029616316</ctn><marketGubun>KTF</marketGubun><sctn>QsuTEwz+sLxRExOhy2lPqg==</sctn></outDto><outDto><ctn>01029646316</ctn><marketGubun>KTF</marketGubun><sctn>at6V4IHV0f9RExOhy2lPqg==</sctn></outDto><outDto><ctn>01029706316</ctn><marketGubun>KTF</marketGubun><sctn>bJMzy6Tx79BRExOhy2lPqg==</sctn></outDto><outDto><ctn>01029726316</ctn><marketGubun>KTF</marketGubun><sctn>S3t+vepjZdhRExOhy2lPqg==</sctn></outDto><outDto><ctn>01029756316</ctn><marketGubun>KTF</marketGubun><sctn>8RJlmRaqD3ZRExOhy2lPqg==</sctn></outDto><outDto><ctn>01029916316</ctn><marketGubun>KTF</marketGubun><sctn>4IZDoF+0LrlRExOhy2lPqg==</sctn></outDto><outDto><ctn>01030176316</ctn><marketGubun>KTF</marketGubun><sctn>j5FTR6MDNj5RExOhy2lPqg==</sctn></outDto><outDto><ctn>01030196316</ctn><marketGubun>KTF</marketGubun><sctn>ZODkAmLfECFRExOhy2lPqg==</sctn></outDto><outDto><ctn>01030256316</ctn><marketGubun>KTF</marketGubun><sctn>iSB7BvrfFapRExOhy2lPqg==</sctn></outDto><outDto><ctn>01030266316</ctn><marketGubun>KTF</marketGubun><sctn>wKVaFNVsVFJRExOhy2lPqg==</sctn></outDto><outDto><ctn>01030276316</ctn><marketGubun>KTF</marketGubun><sctn>4+Oc56z1c15RExOhy2lPqg==</sctn></outDto><outDto><ctn>01030286316</ctn><marketGubun>KTF</marketGubun><sctn>IBKM0NMjO2RRExOhy2lPqg==</sctn></outDto><outDto><ctn>01030296316</ctn><marketGubun>KTF</marketGubun><sctn>ZmRV9timo/dRExOhy2lPqg==</sctn></outDto><outDto><ctn>01030416316</ctn><marketGubun>KTF</marketGubun><sctn>BO8Rw/PXOMhRExOhy2lPqg==</sctn></outDto><outDto><ctn>01030426316</ctn><marketGubun>KTF</marketGubun><sctn>sBq/nnbZ/hNRExOhy2lPqg==</sctn></outDto><outDto><ctn>01030436316</ctn><marketGubun>KTF</marketGubun><sctn>5C5U6KLlxERRExOhy2lPqg==</sctn></outDto><outDto><ctn>01030466316</ctn><marketGubun>KTF</marketGubun><sctn>1XdXuZsnMBBRExOhy2lPqg==</sctn></outDto></return>");
                //responseXml.append("<return><commHeader><globalNo>9106072820160114172107306</globalNo><encYn/><responseType>S</responseType><responseCode>MCG_SYS_E0500</responseCode><responseLogcd/><responseTitle>시스템 오류</responseTitle><responseBasic>[서버] 시스템 내부처리 오류</responseBasic><langCode/><filler/></commHeader><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X31</appEventCd><appSendDateTime>20160114172107</appSendDateTime><appRecvDateTime/><appLgDateTime/><appNstepUserId>91060728</appNstepUserId><appOrderId/></bizHeader></return>");
                break;
            case 32://번호변경
                //			responseXml.append("<return><commHeader><globalNo>9106072820160114172107306</globalNo><encYn/><responseType>S</responseType><responseCode>MCG_SYS_E0500</responseCode><responseLogcd/><responseTitle>시스템 오류</responseTitle><responseBasic>[서버] 시스템 내부처리 오류</responseBasic><langCode/><filler/></commHeader><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X31</appEventCd><appSendDateTime>20160114172107</appSendDateTime><appRecvDateTime/><appLgDateTime/><appNstepUserId>91060728</appNstepUserId><appOrderId/></bizHeader></return>");
                //responseXml.append("<return><commHeader><globalNo>9106072820160114172107306</globalNo><encYn/><responseType>S</responseType><responseCode>MCG_SYS_E0500</responseCode><responseLogcd/><responseTitle>시스템 오류</responseTitle><responseBasic>[서버] 시스템 내부처리 오류</responseBasic><langCode/><filler/></commHeader><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X31</appEventCd><appSendDateTime>20160114172107</appSendDateTime><appRecvDateTime/><appLgDateTime/><appNstepUserId>91060728</appNstepUserId><appOrderId/></bizHeader></return>");

                //정상
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KTF</appEntrPrsnId><appAgncCd>AA11070</appAgncCd><appEventCd>X32</appEventCd><appSendDateTime>20150210135049</appSendDateTime><appRecvDateTime>20150210135016</appRecvDateTime><appLgDateTime>20150210135016</appLgDateTime><appNstepUserId>6833564</appNstepUserId><appOrderId/></bizHeader><commHeader><globalNo>9911100201501191201011234</globalNo><encYn/><responseType>N</responseType><responseCode/><responseLogcd/><responseTitle/><responseBasic/><langCode/><filler/></commHeader></return>");

                //비정상
                //			responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X32</appEventCd><appSendDateTime>20160201121354</appSendDateTime><appRecvDateTime>20160201121351</appRecvDateTime><appLgDateTime>20160201121351</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160201121347984</globalNo><encYn></encYn><responseType>E</responseType><responseCode>ITL_999_COME1002</responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic>번호변경 안내:선택하신 전화번호가 비정상적인 방법으로 변경되었습니다. 확인 후 작업하시기 바랍니다.</responseBasic><langCode></langCode><filler></filler></commHeader></return>");

                break;
            case 33://분실신고가능여부조회------
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X33</appEventCd><appSendDateTime>20160112162508</appSendDateTime><appRecvDateTime>20160112162505</appRecvDateTime><appLgDateTime>20160112162505</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160112162505018</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><asfdYn>N</asfdYn><coldeLinqStatus>N</coldeLinqStatus><rsltCd>Y</rsltCd><rsltMsg></rsltMsg><runMode>I</runMode><subStatusLastAct>NAC</subStatusLastAct></outDto></return>");
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X33</appEventCd><appSendDateTime>20160114173714</appSendDateTime><appRecvDateTime>20160114173712</appRecvDateTime><appLgDateTime>20160114173712</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160114173707460</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><asfdYn>N</asfdYn><coldeLinqStatus>N</coldeLinqStatus><rsltCd>N</rsltCd><rsltMsg>분실신고 상태입니다. </rsltMsg><runMode>U</runMode><subStatusLastAct>SUS</subStatusLastAct></outDto></return>");
                break;
            case 34://분실신고신청
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X34</appEventCd><appSendDateTime>20160112153248</appSendDateTime><appRecvDateTime>20160112153246</appRecvDateTime><appLgDateTime>20160112153246</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160112153243531</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader></return>");
                break;

            case 38://부가서비스해지
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X38</appEventCd><appSendDateTime>20160112153248</appSendDateTime><appRecvDateTime>20160112153246</appRecvDateTime><appLgDateTime>20160112153246</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160112153243531</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader></return>");
                break;

            case 49://
                //MMS
                //responseXml.append("<return><bizHeader><appEntrPrsnId>INL</appEntrPrsnId><appAgncCd>AA11070</appAgncCd><appEventCd>X03</appEventCd><appSendDateTime>20150210104630</appSendDateTime><appRecvDateTime>20150210104620</appRecvDateTime><appLgDateTime>20150210104620</appLgDateTime><appNstepUserId>6833564</appNstepUserId><appOrderId/></bizHeader><commHeader><globalNo>9911100201501191201011234</globalNo><encYn/><responseType>N</responseType><responseCode/><responseLogcd/><responseTitle/><responseBasic/><langCode/><filler/></commHeader><outDto><outMmsDto><billTypeCd>MB</billTypeCd><ctn>01011117263</ctn><slsCmpnCd>KIS</slsCmpnCd></outMmsDto></outDto></return>");
                //이메일
                //responseXml.append(" <return> <bizHeader> <appEntrPrsnId>INL</appEntrPrsnId> <appAgncCd>AA11070</appAgncCd> <appEventCd>X03</appEventCd> <appSendDateTime>20150210104630</appSendDateTime> <appRecvDateTime>20150210104620</appRecvDateTime> <appLgDateTime>20150210104620</appLgDateTime> <appNstepUserId>6833564</appNstepUserId> <appOrderId /> </bizHeader> <commHeader> <globalNo>9911100201501191201011234</globalNo> <encYn /> <responseType>N</responseType> <responseCode /> <responseLogcd /> <responseTitle /> <responseBasic /> <langCode /> <filler /> </commHeader> <outDto> <outEmailDto> <billTypeCd>CB</billTypeCd> <email>test@gmail.com</email> <sendGubun>Y</sendGubun> <securMailYn>Y</securMailYn> <ecRcvAgreYn>Y</ecRcvAgreYn> </outEmailDto> </outDto> </return> ");
                //우편명세서
                responseXml.append(
                    " <return> <bizHeader> <appEntrPrsnId>INL</appEntrPrsnId> <appAgncCd>AA11070</appAgncCd> <appEventCd>X03</appEventCd> <appSendDateTime>20150210104630</appSendDateTime> <appRecvDateTime>20150210104620</appRecvDateTime> <appLgDateTime>20150210104620</appLgDateTime> <appNstepUserId>6833564</appNstepUserId> <appOrderId /> </bizHeader> <commHeader> <globalNo>9911100201501191201011234</globalNo> <encYn /> <responseType>N</responseType> <responseCode /> <responseLogcd /> <responseTitle /> <responseBasic /> <langCode /> <filler /> </commHeader> <outDto> <outMailDto> <billTypeCd>LX</billTypeCd> <adrCustNm>홍길동</adrCustNm> <adrBasSbst>서울시 삼성동</adrBasSbst> <adrDtlSbst>KT선릉타워 12층</adrDtlSbst> <adrZipCd>100-100</adrZipCd> <rdAdrBasSbst>서울시 삼성동</rdAdrBasSbst> <rdAdrDtlSbst>KT선릉타워 12층</rdAdrDtlSbst> <rdAdrZipCd>100-100</rdAdrZipCd> </outMailDto> </outDto> </return> ");
                break;

            //PMD권장사항 수정 디폴트값지정

            case 50://청구서변경(X50)
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>ENX</appEntrPrsnId><appAgncCd>AA11070</appAgncCd><appEventCd>X50</appEventCd><appSendDateTime>20160112153248</appSendDateTime><appRecvDateTime>20160112153246</appRecvDateTime><appLgDateTime>20160112153246</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160112153243531</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader></return>");
                //responseXml.append("<return><bizHeader><appEntrPrsnId>ENX</appEntrPrsnId><appAgncCd>AA11070</appAgncCd><appEventCd>X50</appEventCd><appSendDateTime>20160112153248</appSendDateTime><appRecvDateTime>20160112153246</appRecvDateTime><appLgDateTime>20160112153246</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160112153243531</globalNo><encYn></encYn><responseType>E</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic>Selfcare 오류</responseBasic><langCode></langCode><filler></filler></commHeader></return>");
                break;

            case 51:// 사이버명세서 발송 이력 조회(X51)
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>SPT8050</appAgncCd><appEventCd>X51</appEventCd><appSendDateTime>20230717093626</appSendDateTime><appRecvDateTime>20230717093625</appRecvDateTime><appLgDateTime>20230717093625</appLgDateTime><appNstepUserId>82023154</appNstepUserId><appOrderId/></bizHeader><commHeader><globalNo>JHC1222233334444555500190009</globalNo><encYn/><responseType>N</responseType><responseCode/><responseLogcd/><responseTitle/><responseBasic/><langCode/><filler/></commHeader><outDto><outSendInfoListlDto><email>F504092A3304764F4CF9C7F972A1166E</email><sendDay>20230315</sendDay><state>MMS발송성공.자세한내용은고객센터로문의바랍니다.</state><thisMonth>202303</thisMonth></outSendInfoListlDto><outSendInfoListlDto><email>F504092A3304764F4CF9C7F972A1166E</email><sendDay>20230214</sendDay><state>MMS발송성공.자세한내용은고객센터로문의바랍니다.</state><thisMonth>202302</thisMonth></outSendInfoListlDto><outSendInfoListlDto><email>F504092A3304764F4CF9C7F972A1166E</email><sendDay>20230117</sendDay><state>MMS발송성공.자세한내용은고객센터로문의바랍니다.</state><thisMonth>202301</thisMonth></outSendInfoListlDto></outDto></return>");
                break;

            case 53: // 명세서 재발송 (X53)
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>SPT8050</appAgncCd><appEventCd>X53</appEventCd><appSendDateTime>20230717093511</appSendDateTime><appRecvDateTime>20230717093505</appRecvDateTime><appLgDateTime>20230717093505</appLgDateTime><appNstepUserId>82023154</appNstepUserId><appOrderId/></bizHeader><commHeader><globalNo>JHC1222233334444555500190006</globalNo><encYn/><responseType>N</responseType><responseCode/><responseLogcd/><responseTitle/><responseBasic/><langCode/><filler/></commHeader></return>");
                //responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>SPT8050</appAgncCd><appEventCd>X53</appEventCd><appSendDateTime>20230717093511</appSendDateTime><appRecvDateTime>20230717093505</appRecvDateTime><appLgDateTime>20230717093505</appLgDateTime><appNstepUserId>82023154</appNstepUserId><appOrderId/></bizHeader><commHeader><globalNo>JHC1222233334444555500190006</globalNo><encYn/><responseType>E</responseType><responseCode/><responseLogcd/><responseTitle/><responseBasic>Selfcare 오류</responseBasic><langCode/><filler/></commHeader></return>");
                break;

            case 54://스폰서조회(X54)
                //responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X54</appEventCd><appSendDateTime>20180703142355</appSendDateTime><appRecvDateTime>20180703142354</appRecvDateTime><appLgDateTime>20180703142354</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820180703142350780</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><outBasInfoDto><engtAplyStDate>20180102</engtAplyStDate><engtExpirPamDate>20200101</engtExpirPamDate><engtUseDayNum>183</engtUseDayNum><saleEngtNm>알뜰폰스폰서2[베이직코스]</saleEngtNm><saleEngtOptnCd>KD</saleEngtOptnCd><saleEngtTypeDivCd>KM1</saleEngtTypeDivCd></outBasInfoDto><outKDDto><apdSuprtAmt>8000</apdSuprtAmt><engtRmndDate>527</engtRmndDate><firstSuprtAmt>0</firstSuprtAmt><ktSuprtPenltAmt>0</ktSuprtPenltAmt><punoSuprtAmt>0</punoSuprtAmt><realDcAmt>43716</realDcAmt><rtrnAmtAndChageDcAmt>43716</rtrnAmtAndChageDcAmt><storSuprtPenltAmt>0</storSuprtPenltAmt><tgtKtSuprtPenltAmt>0</tgtKtSuprtPenltAmt><trmnForecBprmsAmt>0</trmnForecBprmsAmt></outKDDto></outDto></return>");
                //responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X54</appEventCd><appSendDateTime>20180704170036</appSendDateTime><appRecvDateTime>20180704170030</appRecvDateTime><appLgDateTime>20180704170030</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820180704170025896</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><outBasInfoDto><engtAplyStDate>20180115</engtAplyStDate><engtExpirPamDate>20200114</engtExpirPamDate><engtUseDayNum>171</engtUseDayNum><saleEngtNm>알뜰폰스폰서2 [요금할인(지원금)]</saleEngtNm><saleEngtOptnCd>PM</saleEngtOptnCd><saleEngtTypeDivCd>KM1</saleEngtTypeDivCd></outBasInfoDto><outPMDto><chageDcAmt>0</chageDcAmt><chageDcAmtSuprtMonth>9900</chageDcAmtSuprtMonth><chageDcAmtSuprtRtrnAmt>117079</chageDcAmtSuprtRtrnAmt><engtRmndDate>539</engtRmndDate><realDcAmt>117079</realDcAmt><rtrnAmtAndChageDcAmt>0</rtrnAmtAndChageDcAmt></outPMDto></outDto></return>");
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X54</appEventCd><appSendDateTime>20180705093218</appSendDateTime><appRecvDateTime>20180705093212</appRecvDateTime><appLgDateTime>20180705093212</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820180705093209193</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><outBasInfoDto><engtAplyStDate>20180115</engtAplyStDate><engtExpirPamDate>20200114</engtExpirPamDate><engtUseDayNum>172</engtUseDayNum><saleEngtNm>알뜰폰스폰서2[베이직코스]</saleEngtNm><saleEngtOptnCd>KD</saleEngtOptnCd><saleEngtTypeDivCd>KM1</saleEngtTypeDivCd></outBasInfoDto><outKDDto><apdSuprtAmt>0</apdSuprtAmt><engtRmndDate>538</engtRmndDate><firstSuprtAmt>0</firstSuprtAmt><ktSuprtPenltAmt>42000</ktSuprtPenltAmt><punoSuprtAmt>0</punoSuprtAmt><realDcAmt>57870</realDcAmt><rtrnAmtAndChageDcAmt>57870</rtrnAmtAndChageDcAmt><storSuprtPenltAmt>0</storSuprtPenltAmt><tgtKtSuprtPenltAmt>0</tgtKtSuprtPenltAmt><tgtStorSuprtPenltAmt>0</tgtStorSuprtPenltAmt><trmnForecBprmsAmt>42000</trmnForecBprmsAmt></outKDDto></outDto></return>");
                //responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X54</appEventCd><appSendDateTime>20180705093323</appSendDateTime><appRecvDateTime>20180705093317</appRecvDateTime><appLgDateTime>20180705093317</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820180705093314927</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><outBasInfoDto><engtAplyStDate>20180207</engtAplyStDate><engtExpirPamDate>20200206</engtExpirPamDate><engtUseDayNum>149</engtUseDayNum><saleEngtNm>알뜰폰스폰서2 [요금할인(지원금)]</saleEngtNm><saleEngtOptnCd>PM</saleEngtOptnCd><saleEngtTypeDivCd>KM1</saleEngtTypeDivCd></outBasInfoDto><outPMDto><chageDcAmt>10000</chageDcAmt><chageDcAmtSuprtMonth>19000</chageDcAmtSuprtMonth><chageDcAmtSuprtRtrnAmt>79121</chageDcAmtSuprtRtrnAmt><engtRmndDate>561</engtRmndDate><realDcAmt>120763</realDcAmt><rtrnAmtAndChageDcAmt>34053</rtrnAmtAndChageDcAmt></outPMDto></outDto></return>");
                //responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X54</appEventCd><appSendDateTime>20180705093412</appSendDateTime><appRecvDateTime>20180705093406</appRecvDateTime><appLgDateTime>20180705093406</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820180705093403832</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><outBasInfoDto><engtAplyStDate>20180102</engtAplyStDate><engtExpirPamDate>20200101</engtExpirPamDate><engtUseDayNum>185</engtUseDayNum><saleEngtNm>알뜰폰스폰서2[베이직코스]</saleEngtNm><saleEngtOptnCd>KD</saleEngtOptnCd><saleEngtTypeDivCd>KM1</saleEngtTypeDivCd></outBasInfoDto><outKDDto><apdSuprtAmt>0</apdSuprtAmt><engtRmndDate>525</engtRmndDate><firstSuprtAmt>143000</firstSuprtAmt><ktSuprtPenltAmt>106760</ktSuprtPenltAmt><punoSuprtAmt>143000</punoSuprtAmt><realDcAmt>0</realDcAmt><rtrnAmtAndChageDcAmt>0</rtrnAmtAndChageDcAmt><storSuprtPenltAmt>0</storSuprtPenltAmt><tgtKtSuprtPenltAmt>143000</tgtKtSuprtPenltAmt><trmnForecBprmsAmt>106760</trmnForecBprmsAmt></outKDDto></outDto></return>");

                break;
            case 59://심플할인 사전체크(X59)
                //실패
                //responseXml.append("<return><bizHeader><appEntrPrsnId>ENX</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X59</appEventCd><appSendDateTime>20190503145537</appSendDateTime><appRecvDateTime>20190503145515</appRecvDateTime><appLgDateTime>20190503145515</appLgDateTime><appNstepUserId>8500056</appNstepUserId><appOrderId/></bizHeader><commHeader><globalNo>9105420120150508150101001</globalNo><encYn/><responseType>N</responseType><responseCode/><responseLogcd/><responseTitle/><responseBasic/><langCode/><filler/></commHeader><outDto><resltMsg>신청, 해지 불가</resltMsg><sbscYn>N</sbscYn></outDto></return>");

           /*sbscYn       사전체크 결과코드   1   M   "Y(신청가능) -> 신청만 가능함.
           E(중도해지) -> 해지만 가능함.
           N(신청불가) -> 신청, 해지 불가"
          resltMsg        결과 메시지      M   */

                //성공
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>ENX</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X59</appEventCd><appSendDateTime>20190503145537</appSendDateTime><appRecvDateTime>20190503145515</appRecvDateTime><appLgDateTime>20190503145515</appLgDateTime><appNstepUserId>8500056</appNstepUserId><appOrderId/></bizHeader><commHeader><globalNo>9105420120150508150101001</globalNo><encYn/><responseType>N</responseType><responseCode/><responseLogcd/><responseTitle/><responseBasic/><langCode/><filler/></commHeader><outDto><resltMsg>신청만 가능함.</resltMsg><sbscYn>Y</sbscYn></outDto></return>");
                break;
            case 60://심플할인 가입(X60)
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>ENX</appEntrPrsnId><appAgncCd>AA11070</appAgncCd><appEventCd>X50</appEventCd><appSendDateTime>20160112153248</appSendDateTime><appRecvDateTime>20160112153246</appRecvDateTime><appLgDateTime>20160112153246</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160112153243531</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader></return>");
                break;
            case 61://심플할인 해지(X61)
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>ENX</appEntrPrsnId><appAgncCd>AA11070</appAgncCd><appEventCd>X50</appEventCd><appSendDateTime>20160112153248</appSendDateTime><appRecvDateTime>20160112153246</appRecvDateTime><appLgDateTime>20160112153246</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160112153243531</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader></return>");
                break;
            case 62://심플할인 정보조회(X61)
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>ENX</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X62</appEventCd><appSendDateTime>20190503104357</appSendDateTime><appRecvDateTime>20190503104327</appRecvDateTime><appLgDateTime>20190503104327</appLgDateTime><appNstepUserId>8500056</appNstepUserId><appOrderId/></bizHeader><commHeader><globalNo>9105420120150508150101001</globalNo><encYn/><responseType>N</responseType><responseCode/><responseLogcd/><responseTitle/><responseBasic/><langCode/><filler/></commHeader><outDto><chageDcAplyYn>Y</chageDcAplyYn><dcSuprtAmt>1000</dcSuprtAmt><engtAplyStDate>20170515</engtAplyStDate><engtExpirPamDate>20190514</engtExpirPamDate><engtPerdMonsNum>24</engtPerdMonsNum><ppPenlt>4611</ppPenlt></outDto></return>");
                break;
            case 68://즉시납부
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X68</appEventCd><appSendDateTime>20220222164543</appSendDateTime><appRecvDateTime>20220222164540</appRecvDateTime><appLgDateTime>20220222164540</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220222164242447</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><url>https://sandbox-billgates-web.kakao.com/r/platform/pages/paynow/search/1633/6/7404c2d1-cd37-4d28-8234-133e504817bb</url></outDto></return>");
                break;
            case 74://쿠폰 정보조회(X74)
                responseXml.append("<return>");
                responseXml.append("    <bizHeader>");
                responseXml.append("        <appEntrPrsnId>KIS</appEntrPrsnId>");
                responseXml.append("        <appAgncCd>AA11070</appAgncCd>");
                responseXml.append("        <appEventCd>X01</appEventCd>");
                responseXml.append("        <appSendDateTime>20180405140000</appSendDateTime>");
                responseXml.append("        <appRecvDateTime>20201216142110</appRecvDateTime>");
                responseXml.append("        <appLgDateTime>20201216142110</appLgDateTime>");
                responseXml.append("        <appNstepUserId>116833564</appNstepUserId>");
                responseXml.append("        <appOrderId/>");
                responseXml.append("    </bizHeader>");
                responseXml.append("    <commHeader>");
                responseXml.append("        <globalNo>9114053920180405150101001</globalNo>");
                responseXml.append("        <encYn/>");
                responseXml.append("        <responseType>N</responseType>");
                responseXml.append("        <responseCode/>");
                responseXml.append("        <responseLogcd/>");
                responseXml.append("        <responseTitle/>");
                responseXml.append("        <responseBasic/>");
                responseXml.append("        <langCode/>");
                responseXml.append("        <filler/>");
                responseXml.append("    </commHeader>");
                responseXml.append("    <outDto>");
                responseXml.append("        <coupInfoList>");
                responseXml.append("            <coupAplyLimitCd>BIZ</coupAplyLimitCd>");
                responseXml.append("            <coupCategoryCd>01</coupCategoryCd>");
                responseXml.append("            <coupCreId>22020121500003124853</coupCreId>");
                responseXml.append("            <coupNm>쿠폰5: KIS/부가서비스형/KIS</coupNm>");
                responseXml.append("            <coupSerialNo>TQHYVNA4N15ROWR</coupSerialNo>");
                responseXml.append("            <coupStatCd>BPCO</coupStatCd>");
                responseXml.append("            <coupTypeCd>02</coupTypeCd>");
                responseXml.append("            <coupValu>5000</coupValu>");
                responseXml.append("            <dscnTypeCd>FXM</dscnTypeCd>");
                responseXml.append("            <rsvPsblYn>Y</rsvPsblYn>");
                responseXml.append("            <smsRcvCtn>010272192xx</smsRcvCtn>");
                responseXml.append("            <svcTypeCd>MMB</svcTypeCd>");
                responseXml.append("            <useEndDt>20210116235959</useEndDt>");
                responseXml.append("            <useStrtDt>20201216000000</useStrtDt>");
                responseXml.append("        </coupInfoList>");
                responseXml.append("        <coupInfoList>");
                responseXml.append("            <coupAplyLimitCd>BIZ</coupAplyLimitCd>");
                responseXml.append("            <coupCategoryCd>01</coupCategoryCd>");
                responseXml.append("            <coupCreId>22020121500003124853</coupCreId>");
                responseXml.append("            <coupNm>쿠폰5: KIS/부가서비스형/KIS</coupNm>");
                responseXml.append("            <coupSerialNo>TQHYVNA4N15ROWR</coupSerialNo>");
                responseXml.append("            <coupStatCd>BPCO</coupStatCd>");
                responseXml.append("            <coupTypeCd>02</coupTypeCd>");
                responseXml.append("            <coupValu>5000</coupValu>");
                responseXml.append("            <dscnTypeCd>FXM</dscnTypeCd>");
                responseXml.append("            <rsvPsblYn>Y</rsvPsblYn>");
                responseXml.append("            <smsRcvCtn>010272192xx</smsRcvCtn>");
                responseXml.append("            <svcTypeCd>MMB</svcTypeCd>");
                responseXml.append("            <useEndDt>20210116235959</useEndDt>");
                responseXml.append("            <useStrtDt>20201216000000</useStrtDt>");
                responseXml.append("        </coupInfoList>");
                responseXml.append("        <rtnCode>0000</rtnCode>");
                responseXml.append("        <rtnMsg>Success</rtnMsg>");
                responseXml.append("        <totalContentCnt>1</totalContentCnt>");
                responseXml.append("    </outDto>");
                responseXml.append("</return>");
                break;
            case 75://쿠폰사용(X75)
                responseXml.append("<return>");
                responseXml.append("    <bizHeader>");
                responseXml.append("        <appEntrPrsnId>KIS</appEntrPrsnId>");
                responseXml.append("        <appAgncCd>AA11070</appAgncCd>");
                responseXml.append("        <appEventCd>X01</appEventCd>");
                responseXml.append("        <appSendDateTime>20180405140000</appSendDateTime>");
                responseXml.append("        <appRecvDateTime>20201216144116</appRecvDateTime>");
                responseXml.append("        <appLgDateTime>20201216144116</appLgDateTime>");
                responseXml.append("        <appNstepUserId>116833564</appNstepUserId>");
                responseXml.append("        <appOrderId/>");
                responseXml.append("    </bizHeader>");
                responseXml.append("    <commHeader>");
                responseXml.append("        <globalNo>9114053920180405150101001</globalNo>");
                responseXml.append("        <encYn/>");
                responseXml.append("        <responseType>N</responseType>");
                responseXml.append("        <responseCode/>");
                responseXml.append("        <responseLogcd/>");
                responseXml.append("        <responseTitle/>");
                responseXml.append("        <responseBasic/>");
                responseXml.append("        <langCode/>");
                responseXml.append("        <filler/>");
                responseXml.append("    </commHeader>");
                responseXml.append("    <outDto>");
                responseXml.append("        <dscnTypeCd>FXM</dscnTypeCd>");
                responseXml.append("        <rtnCode>0000</rtnCode>");
                responseXml.append("        <rtnMsg>Success</rtnMsg>");
                responseXml.append("        <useEndDt>20210116235959</useEndDt>");
                responseXml.append("        <useStrtDt>20201216000000</useStrtDt>");
                responseXml.append("    </outDto>");
                responseXml.append("</return>");
                break;
            case 76://사용완료 쿠폰 목록 조회(X76)
                responseXml.append("<return>");
                responseXml.append("    <bizHeader>");
                responseXml.append("        <appEntrPrsnId>KIS</appEntrPrsnId>");
                responseXml.append("        <appAgncCd>AA11070</appAgncCd>");
                responseXml.append("        <appEventCd>X01</appEventCd>");
                responseXml.append("        <appSendDateTime>20180405140000</appSendDateTime>");
                responseXml.append("        <appRecvDateTime>20201216154114</appRecvDateTime>");
                responseXml.append("        <appLgDateTime>20201216154114</appLgDateTime>");
                responseXml.append("        <appNstepUserId>116833564</appNstepUserId>");
                responseXml.append("        <appOrderId/>");
                responseXml.append("    </bizHeader>");
                responseXml.append("    <commHeader>");
                responseXml.append("        <globalNo>9114053920180405150101001</globalNo>");
                responseXml.append("        <encYn/>");
                responseXml.append("        <responseType>N</responseType>");
                responseXml.append("        <responseCode/>");
                responseXml.append("        <responseLogcd/>");
                responseXml.append("        <responseTitle/>");
                responseXml.append("        <responseBasic/>");
                responseXml.append("        <langCode/>");
                responseXml.append("        <filler/>");
                responseXml.append("    </commHeader>");
                responseXml.append("    <outDto>");
                responseXml.append("        <rtnCode>0000</rtnCode>");
                responseXml.append("        <rtnMsg>Success</rtnMsg>");
                responseXml.append("        <totalContentCnt>1</totalContentCnt>");
                responseXml.append("        <usedCoupList>");
                responseXml.append("            <coupAplyLimitCd>BIZ</coupAplyLimitCd>");
                responseXml.append("            <coupNm>쿠폰5: KIS/부가서비스형/KIS</coupNm>");
                responseXml.append("            <coupSerialNo>YKHYMQA4TQ5RPUQ</coupSerialNo>");
                responseXml.append("            <coupStatCd>SYCO</coupStatCd>");
                responseXml.append("            <rgstStrtDt>20201216141750</rgstStrtDt>");
                responseXml.append("            <smsRcvCtn>010726036XX</smsRcvCtn>");
                responseXml.append("            <svcTypeCd>MMB</svcTypeCd>");
                responseXml.append("            <useReqDt>20201216141750</useReqDt>");
                responseXml.append("        </usedCoupList>");
                responseXml.append("    </outDto>");
                responseXml.append("</return>");
                break;
            case 83://회선 사용기간조회
                responseXml.append("<return>");
                responseXml.append("    <bizHeader>");
                responseXml.append("        <appEntrPrsnId>KIS</appEntrPrsnId>");
                responseXml.append("        <appAgncCd>AA00364</appAgncCd>");
                responseXml.append("        <appEventCd>X83</appEventCd>");
                responseXml.append("        <appSendDateTime>20211103155502</appSendDateTime>");
                responseXml.append("        <appRecvDateTime>20211103155458</appRecvDateTime>");
                responseXml.append("        <appLgDateTime>20211103155458</appLgDateTime>");
                responseXml.append("        <appNstepUserId>91225330</appNstepUserId>");
                responseXml.append("        <appOrderId></appOrderId>");
                responseXml.append("    </bizHeader>");
                responseXml.append("    <commHeader>");
                responseXml.append("        <globalNo>9122533020211103155029878</globalNo>");
                responseXml.append("        <encYn></encYn>");
                responseXml.append("        <responseType>N</responseType>");
                responseXml.append("        <responseCode></responseCode>");
                responseXml.append("        <responseLogcd></responseLogcd>");
                responseXml.append("        <responseTitle></responseTitle>");
                responseXml.append("        <responseBasic></responseBasic>");
                responseXml.append("        <langCode></langCode>");
                responseXml.append("        <filler></filler>");
                responseXml.append("    </commHeader>");
                responseXml.append("    <outDto>");
                responseXml.append("        <longUseAdjDayNum>0</longUseAdjDayNum>");
                responseXml.append("        <realUseDayNum>2089</realUseDayNum>");
                responseXml.append("        <svcContSbscDt>20160208150750</svcContSbscDt>");
                responseXml.append("        <totStopDayNum>6</totStopDayNum>");
                responseXml.append("        <totUseDayNum>2095</totUseDayNum>");
                responseXml.append("    </outDto>");
                responseXml.append("  </return>");
                break;
            case 85://USIM 유효성 체크(X85)
                responseXml.append("<return>");
                responseXml.append("    <bizHeader>");
                responseXml.append("        <appEntrPrsnId>KIS</appEntrPrsnId>");
                responseXml.append("        <appAgncCd>AA00364</appAgncCd>");
                responseXml.append("        <appEventCd>X19</appEventCd>");
                responseXml.append("        <appSendDateTime>20210902150015</appSendDateTime>");
                responseXml.append("        <appRecvDateTime>20210902145952</appRecvDateTime>");
                responseXml.append("        <appLgDateTime>20210902145952</appLgDateTime>");
                responseXml.append("        <appNstepUserId>82023154</appNstepUserId>");
                responseXml.append("        <appOrderId/>");
                responseXml.append("    </bizHeader>");
                responseXml.append("    <commHeader>");
                responseXml.append("        <globalNo>912788510902000000000001</globalNo>");
                responseXml.append("        <encYn/>");
                responseXml.append("        <responseType>N</responseType>");
                responseXml.append("    </commHeader>");
                responseXml.append("    <outDto>");
                responseXml.append("        <psblYn>Y</psblYn>  ");
                responseXml.append("        <rsltMsg>rsltMsgrsltMsgrsltMsgrsltMsg</rsltMsg>");
                responseXml.append("    </outDto>     ");
                responseXml.append("</return>");
                break;
            case 88: //요금상품예약변경(X88)
                //responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X88</appEventCd><appSendDateTime>20220406191431</appSendDateTime><appRecvDateTime>20220406191429</appRecvDateTime><appLgDateTime>20220406191429</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220406191429656</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><message><rsltCd>Y</rsltCd><ruleId>100001842</ruleId><ruleMsgSbst>요금제 변경시 자동해지 되는 부가상품입니다. &#xD;- M 요금할인 5000(VAT포함)&#xD;(http--0.0.0.0-7006-4)  - M 요금할인 3000(VAT포함)</ruleMsgSbst></message><rsltYn>Y</rsltYn></outDto></return>");
                //  responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X88</appEventCd><appSendDateTime>20220406181627</appSendDateTime><appRecvDateTime>20220406181625</appRecvDateTime><appLgDateTime>20220406181625</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220406181626460</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><message><rsltCd>N</rsltCd><ruleId>100000413</ruleId><ruleMsgSbst>고객님은요금제 예약한 고객이므로 예약 취소후 처리하십시요.</ruleMsgSbst></message><message><rsltCd>Y</rsltCd><ruleId>100001842</ruleId><ruleMsgSbst>요금제 변경 시 자동해지 되는 부가상품입니다. &#xD;- M 요금할인 5000(VAT포함)&#xD; - M 요금할인 3000(VAT포함)</ruleMsgSbst></message><rsltYn>N</rsltYn></outDto></return>");
                //responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X88</appEventCd><appSendDateTime>20220406125323</appSendDateTime><appRecvDateTime>20220406125316</appRecvDateTime><appLgDateTime>20220406125316</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220406124901628</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><message><rsltCd>N</rsltCd><ruleId>MSG_100999998_1</ruleId><ruleMsgSbst>현재 선택한 요금제에서는 가입할 수 없는 부가서비스[USIM 10GB 할인프로모션]입니다.</ruleMsgSbst></message><message><rsltCd>N</rsltCd><ruleId>MSG_100999998_1</ruleId><ruleMsgSbst>현재 선택한 요금제에서는 가입할 수 없는 부가서비스[LTE 데이터 추가제공 100GB(12개월)]입니다.</ruleMsgSbst></message><rsltYn>N</rsltYn></outDto></return>");
                // responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X88</appEventCd><appSendDateTime>20220310093349</appSendDateTime><appRecvDateTime>20220310093336</appRecvDateTime><appLgDateTime>20220310093336</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220310093010331</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><message><rsltCd>Y</rsltCd><ruleId>100001136</ruleId><ruleMsgSbst>고객 문의 후 희망하면, 무선데이터차단서비스를 해지해주시기 바랍니다.</ruleMsgSbst></message><rsltYn>Y</rsltYn></outDto></return>");
                responseXml.append("			<return>");
                responseXml.append("				<bizHeader>");
                responseXml.append("					<appEntrPrsnId>KIS</appEntrPrsnId>");
                responseXml.append("					<appAgncCd>AA00364</appAgncCd>");
                responseXml.append("					<appEventCd>X88</appEventCd>");
                responseXml.append("					<appSendDateTime>20220530134033</appSendDateTime>");
                responseXml.append("					<appRecvDateTime>20220530134031</appRecvDateTime>");
                responseXml.append("					<appLgDateTime>20220530134031</appLgDateTime>");
                responseXml.append("					<appNstepUserId>91225330</appNstepUserId>");
                responseXml.append("					<appOrderId></appOrderId>");
                responseXml.append("				</bizHeader>");
                responseXml.append("				<commHeader>");
                responseXml.append("					<globalNo>9122533020220530134054060</globalNo>");
                responseXml.append("					<encYn></encYn>");
                responseXml.append("					<responseType>N</responseType>");
                responseXml.append("					<responseCode></responseCode>");
                responseXml.append("					<responseLogcd></responseLogcd>");
                responseXml.append("					<responseTitle></responseTitle>");
                responseXml.append("					<responseBasic></responseBasic>");
                responseXml.append("					<langCode></langCode>");
                responseXml.append("					<filler></filler>");
                responseXml.append("				</commHeader>");
                responseXml.append("				<outDto>");
                responseXml.append("					<message>");
                responseXml.append("						<rsltCd>N</rsltCd>");
                responseXml.append("						<ruleId>MSG_100999998_1</ruleId>");
                responseXml.append("						<ruleMsgSbst>현재 선택한 요금제에서는 가입할 수 없는 부가서비스[LTE 데이터 추가제공 6GB]입니다.</ruleMsgSbst>");
                responseXml.append("					</message>");
                responseXml.append("					<rsltYn>N</rsltYn>");
                responseXml.append("				</outDto>");
                responseXml.append("			</return>");
                break;
            case 89: //요금상품예약변경조회(X89)
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X89</appEventCd><appSendDateTime>20220308175501</appSendDateTime><appRecvDateTime>20220308175459</appRecvDateTime><appLgDateTime>20220308175459</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220308175136057</globalNo><encYn></encYn><responseType>E</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><aplyDate>20220308170741</aplyDate><basicAmt>25000</basicAmt><efctStDate>20220401000001</efctStDate><prdcCd>PL208J932</prdcCd><prdcNm>모두다 맘껏 안심 2.5G+</prdcNm></outDto></return>");
                // responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X88</appEventCd><appSendDateTime>20220308171155</appSendDateTime><appRecvDateTime>20220308171142</appRecvDateTime><appLgDateTime>20220308171142</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220308170818766</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><message><rsltCd>N</rsltCd><ruleId>100000413</ruleId><ruleMsgSbst>고객님은요금제 예약한 고객이므로 예약 취소후 처리하십시요.</ruleMsgSbst></message><message><rsltCd>Y</rsltCd><ruleId>100001136</ruleId><ruleMsgSbst>고객 문의 후 희망하면, 무선데이터차단서비스를 해지해주시기 바랍니다.</ruleMsgSbst></message><rsltYn>N</rsltYn></outDto></return>");
                break;
            case 90: //요금상품예약변경취소(X90)
                responseXml.append("<return>");
                responseXml.append("    <bizHeader>");
                responseXml.append("        <appEntrPrsnId>KIS</appEntrPrsnId>");
                responseXml.append("        <appAgncCd>AA00364</appAgncCd>");
                responseXml.append("        <appEventCd>X89</appEventCd>");
                responseXml.append("        <appSendDateTime>20210902150015</appSendDateTime>");
                responseXml.append("        <appRecvDateTime>20210902145952</appRecvDateTime>");
                responseXml.append("        <appLgDateTime>20210902145952</appLgDateTime>");
                responseXml.append("        <appNstepUserId>82023154</appNstepUserId>");
                responseXml.append("        <appOrderId/>");
                responseXml.append("    </bizHeader>");
                responseXml.append("    <commHeader>");
                responseXml.append("        <globalNo>912788510902000000000001</globalNo>");
                responseXml.append("        <encYn/>");
                responseXml.append("        <responseType>N</responseType>");
                responseXml.append("    </commHeader>");
                responseXml.append("</return>");
                break;

            case 78: //x78
                //           responseXml.append("    <return>    <bizHeader>     <appEntrPrsnId>KIS</appEntrPrsnId>     <appAgncCd>AA00364</appAgncCd>     <appEventCd>X78</appEventCd>     <appSendDateTime>20220127123920</appSendDateTime>     <appRecvDateTime>20220127123910</appRecvDateTime>     <appLgDateTime>20220127123910</appLgDateTime>     <appNstepUserId>91225330</appNstepUserId>     <appOrderId></appOrderId>    </bizHeader>    <commHeader>     <globalNo>9122533020220127123659194</globalNo>     <encYn></encYn>     <responseType>N</responseType>     <responseCode></responseCode>     <responseLogcd></responseLogcd>     <responseTitle></responseTitle>     <responseBasic></responseBasic>     <langCode></langCode>     <filler></filler>    </commHeader>    <outDto>     <moscCombPreChkListOutDTO>      <resltMsg>인터넷뭉치면올레 결합할인은 결합약정이 3년만 가입이 가능합니다.</resltMsg>      <sbscYn>N</sbscYn>     </moscCombPreChkListOutDTO>     <moscCombPreChkListOutDTO>      <resltMsg>MVNO모바일 회선은 인터넷 개통일자[20150925] 익월말 이후 결합은 불가합니다      </resltMsg>      <sbscYn>N</sbscYn>      <svcNo>01029672627</svcNo>     </moscCombPreChkListOutDTO>     <moscCombPreChkListOutDTO>      <resltMsg>정상</resltMsg>      <sbscYn>Y</sbscYn>     </moscCombPreChkListOutDTO>     <moscCombPreChkListOutDTO>      <resltMsg>정상</resltMsg>      <sbscYn>Y</sbscYn>     </moscCombPreChkListOutDTO>     <moscCombPreChkListOutDTO>      <resltMsg>정상</resltMsg>      <sbscYn>Y</sbscYn>     </moscCombPreChkListOutDTO>     <moscCombPreChkListOutDTO>      <resltMsg>정상</resltMsg>      <sbscYn>Y</sbscYn>     </moscCombPreChkListOutDTO>     <moscCombPreChkListOutDTO>      <resltMsg>정상</resltMsg>      <sbscYn>Y</sbscYn>     </moscCombPreChkListOutDTO>     <moscCombPreChkListOutDTO>      <resltMsg>정상</resltMsg>      <sbscYn>Y</sbscYn>      <svcNo>01029672627</svcNo>     </moscCombPreChkListOutDTO>     <resltMsg>약정만료일이 지난 결합의 약정기간 하향처리는 불가합니다.</resltMsg>     <sbscYn>N</sbscYn>    </outDto>   </return>");
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X77</appEventCd><appSendDateTime>20221115134810</appSendDateTime><appRecvDateTime>20221115134804</appRecvDateTime><appLgDateTime>20221115134804</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020221115134909093</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><moscCombPreChkListOutDTO><resltMsg>정상</resltMsg><sbscYn>N</sbscYn><svcNo>01097785180</svcNo></moscCombPreChkListOutDTO><moscCombPreChkListOutDTO><resltMsg>인터넷뭉치면올레 결합할인은 결합약정이 3년만 가입이 가능합니다.</resltMsg><sbscYn>N</sbscYn><svcNo>z!64139196676</svcNo></moscCombPreChkListOutDTO><moscCombPreChkListOutDTO><resltMsg>정상</resltMsg><sbscYn>Y</sbscYn><svcNo>01097785180</svcNo></moscCombPreChkListOutDTO><resltMsg>정상</resltMsg><sbscYn>Y</sbscYn></outDto></return>");
                break;
            case 79: //x79
                //           responseXml.append("  <return>    <bizHeader>     <appEntrPrsnId>KIS</appEntrPrsnId>     <appAgncCd>AA00364</appAgncCd>     <appEventCd>X79</appEventCd>     <appSendDateTime>20220127162449</appSendDateTime>     <appRecvDateTime>20220127162424</appRecvDateTime>     <appLgDateTime>20220127162424</appLgDateTime>     <appNstepUserId>91225330</appNstepUserId>     <appOrderId></appOrderId>    </bizHeader>    <commHeader>     <globalNo>9122533020220127162212808</globalNo>     <encYn></encYn>     <responseType>N</responseType>     <responseCode></responseCode>     <responseLogcd></responseLogcd>     <responseTitle></responseTitle>     <responseBasic></responseBasic>     <langCode></langCode>     <filler></filler>    </commHeader>    <outDto>     <resltMsg>69141094682</resltMsg>     <sbscYn>Y</sbscYn>    </outDto>   </return>");
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X77</appEventCd><appSendDateTime>20221115134810</appSendDateTime><appRecvDateTime>20221115134804</appRecvDateTime><appLgDateTime>20221115134804</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020221115134909093</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><moscCombPreChkListOutDTO><resltMsg>정상</resltMsg><sbscYn>N</sbscYn><svcNo>01097785180</svcNo></moscCombPreChkListOutDTO><moscCombPreChkListOutDTO><resltMsg>인터넷뭉치면올레 결합할인은 결합약정이 3년만 가입이 가능합니다.</resltMsg><sbscYn>N</sbscYn><svcNo>z!64139196676</svcNo></moscCombPreChkListOutDTO><moscCombPreChkListOutDTO><resltMsg>정상</resltMsg><sbscYn>Y</sbscYn><svcNo>01097785180</svcNo></moscCombPreChkListOutDTO><resltMsg>정상</resltMsg><sbscYn>Y</sbscYn></outDto></return>");
                break;
            case 67: //x67
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X67</appEventCd><appSendDateTime>20220222130132</appSendDateTime><appRecvDateTime>20220222130127</appRecvDateTime><appLgDateTime>20220222130127</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220222125829569</globalNo><encYn></encYn> <responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><infoMsg></infoMsg><outNonPymnDto><invSum>12260</invSum><month>202201</month></outNonPymnDto><outNonPymnDto><invSum>10890</invSum><month>202112</month></outNonPymnDto><total>23150</total></outDto></return>");
                //sponseXml.append("OrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220222131514528</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader<outDto><infoMsg></infoMsg><outNonPymnDto><invSum>12260</invSum><month>202201</month></outNonPymnDto><outNonPymnDto><invSum>10890</invSum><month>202112</month></outNonPymnDto><total>23150</total></outDto></return>");
                break;
            case 86: //x67
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X86</appEventCd><appSendDateTime>20220302151305</appSendDateTime><appRecvDateTime>20220302151303</appRecvDateTime><appLgDateTime>20220302151303</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220302150950662</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><outWireDto><sbscBindNowCnt>0</sbscBindNowCnt><sbscBindRemdCnt>0</sbscBindRemdCnt><sbscPsblTotCnt>0</sbscPsblTotCnt></outWireDto></outDto></return>");
                //responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X67</appEventCd><appSendDateTime>20220222130132</appSendDateTime><appRecvDateTime>20220222130127</appRecvDateTime><appLgDateTime>20220222130127</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220222125829569</globalNo><encYn></encYn> <responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><infoMsg></infoMsg><outNonPymnDto><invSum>12260</invSum><month>202201</month></outNonPymnDto><outNonPymnDto><invSum>10890</invSum><month>202112</month></outNonPymnDto><total>23150</total></outDto></return>");
                //sponseXml.append("OrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220222131514528</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader<outDto><infoMsg></infoMsg><outNonPymnDto><invSum>12260</invSum><month>202201</month></outNonPymnDto><outNonPymnDto><invSum>10890</invSum><month>202112</month></outNonPymnDto><total>23150</total></outDto></return>");
                break;
            case 92: //x92
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X92</appEventCd><appSendDateTime>20220401160325</appSendDateTime> <appRecvDateTime>20220401160320</appRecvDateTime><appLgDateTime>20220401160320</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220401155914243</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><currMthNpayAmt>0</currMthNpayAmt><infoMsg></infoMsg><payTgtAmt>12110</payTgtAmt><totNpayAmt>12110</totNpayAmt></outDto></return>");
                //responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X67</appEventCd><appSendDateTime>20220222130132</appSendDateTime><appRecvDateTime>20220222130127</appRecvDateTime><appLgDateTime>20220222130127</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220222125829569</globalNo><encYn></encYn> <responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><infoMsg></infoMsg><outNonPymnDto><invSum>12260</invSum><month>202201</month></outNonPymnDto><outNonPymnDto><invSum>10890</invSum><month>202112</month></outNonPymnDto><total>23150</total></outDto></return>");
                //sponseXml.append("OrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220222131514528</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader<outDto><infoMsg></infoMsg><outNonPymnDto><invSum>12260</invSum><month>202201</month></outNonPymnDto><outNonPymnDto><invSum>10890</invSum><month>202112</month></outNonPymnDto><total>23150</total></outDto></return>");
                break;
            case 93: //x92
                responseXml.append("<return>");
                responseXml.append("	<bizHeader>");
                responseXml.append("		<appEntrPrsnId>KIS</appEntrPrsnId>");
                responseXml.append("		<appAgncCd>AA00364</appAgncCd>");
                responseXml.append("		<appEventCd>X93</appEventCd>");
                responseXml.append("		<appSendDateTime>20230814102338</appSendDateTime>");
                responseXml.append("		<appRecvDateTime>20230814102336</appRecvDateTime>");
                responseXml.append("		<appLgDateTime>20230814102336</appLgDateTime>");
                responseXml.append("		<appNstepUserId>91225330</appNstepUserId>");
                responseXml.append("		<appOrderId></appOrderId>");
                responseXml.append("	</bizHeader>");
                responseXml.append("	<commHeader>");
                responseXml.append("		<globalNo>9122533020230814102336881</globalNo>");
                responseXml.append("		<encYn></encYn>");
                responseXml.append("		<responseType>N</responseType>");
                //responseXml.append("		<responseType>E</responseType>");
                responseXml.append("		<responseCode>ITL_999_21</responseCode>");
                responseXml.append("		<responseLogcd></responseLogcd>");
                responseXml.append("		<responseTitle></responseTitle>");
                responseXml.append("		<responseBasic>비밀번호 오류횟수 초과-비밀번호 불일치 횟수 초과(3회)</responseBasic>");
                responseXml.append("		<langCode></langCode>");
                responseXml.append("		<filler></filler>");
                responseXml.append("	</commHeader>");
                responseXml.append("</return>");
                break;
            case 70: //x92
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X70</appEventCd><appSendDateTime>20220414162253</appSendDateTime><appRecvDateTime>20220414162249</appRecvDateTime><appLgDateTime>20220414162249</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220414162252889</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader></return>");
                //responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA11070</appAgncCd><appEventCd>X70</appEventCd><appSendDateTime>20200512154018</appSendDateTime><appRecvDateTime>20200512154006</appRecvDateTime><appLgDateTime>20200512154006</appLgDateTime><appNstepUserId>116833564</appNstepUserId><appOrderId/></bizHeader><commHeader><globalNo>9114053920180405150101014</globalNo><encYn/><responseType>N</responseType><responseCode/><responseLogcd/><responseTitle/><responseBasic/><langCode/><filler/></commHeader></return>");
                break;
            case 97://X97
                responseXml.append("<return>");
                responseXml.append("	<bizHeader>");
                responseXml.append("		<appEntrPrsnId>KIS</appEntrPrsnId>");
                responseXml.append("		<appAgncCd>AA00364</appAgncCd>");
                responseXml.append("		<appEventCd>X97</appEventCd>");
                responseXml.append("		<appSendDateTime>20230710170609</appSendDateTime>");
                responseXml.append("		<appRecvDateTime>20230710170606</appRecvDateTime>");
                responseXml.append("		<appLgDateTime>20230710170606</appLgDateTime>");
                responseXml.append("		<appNstepUserId>91225330</appNstepUserId>");
                responseXml.append("		<appOrderId></appOrderId>");
                responseXml.append("	</bizHeader>");
                responseXml.append("	<commHeader>");
                responseXml.append("		<globalNo>9122533020230710170748566</globalNo>");
                responseXml.append("		<encYn></encYn>");
                responseXml.append("		<responseType>N</responseType>");
                responseXml.append("		<responseCode></responseCode>");
                responseXml.append("		<responseLogcd></responseLogcd>");
                responseXml.append("		<responseTitle></responseTitle>");
                responseXml.append("		<responseBasic></responseBasic>");
                responseXml.append("		<langCode></langCode>");
                responseXml.append("		<filler></filler>");
                responseXml.append("	</commHeader>");
                responseXml.append("	<outDto>");
                responseXml.append("		<svcList>");
                responseXml.append("			<effectiveDate>20221221112445</effectiveDate>");
                responseXml.append("			<prodHstSeq>300000967342586</prodHstSeq>");
                responseXml.append("			<soc>SPMFILTER</soc>");
                responseXml.append("			<socDescription>스팸차단서비스</socDescription>");
                responseXml.append("			<socRateValue>Free</socRateValue>");
                responseXml.append("		</svcList>");
                responseXml.append("		<svcList>");
                responseXml.append("			<effectiveDate>20230412102646</effectiveDate>");
                responseXml.append("			<prodHstSeq>300001027926291</prodHstSeq>");
                responseXml.append("			<soc>NESPFMCD3</soc>");
                responseXml.append("			<socDescription>WiFi싱글(무료)</socDescription>");
                responseXml.append("			<socRateValue>Free</socRateValue>");
                responseXml.append("		</svcList>");
                responseXml.append("		<svcList>");
                responseXml.append("			<effectiveDate>20230703172649</effectiveDate>");
                responseXml.append("			<prodHstSeq>300001070592787</prodHstSeq>");
                responseXml.append("			<soc>RCSPRVSVC</soc>");
                responseXml.append("			<socDescription>채팅+</socDescription>");
                responseXml.append("			<socRateValue>Free</socRateValue>");
                responseXml.append("		</svcList>");
                responseXml.append("		<svcList>");
                responseXml.append("			<effectiveDate>20230725000000</effectiveDate>");
                responseXml.append("			<paramSbst>STRT_DT=20230725000000|END_DT=20230725235959|PRDC_SRL_NO=1|");
                responseXml.append("			</paramSbst>");
                responseXml.append("			<prodHstSeq>300001073639407</prodHstSeq>");
                responseXml.append("			<soc>PL2079771</soc>");
                responseXml.append("			<socDescription>로밍 하루종일ON 플러스</socDescription>");
                responseXml.append("			<socRateValue>11,819 WON</socRateValue>");
                responseXml.append("		</svcList>");
                responseXml.append("		<svcList>");
                responseXml.append("			<effectiveDate>20230729000000</effectiveDate>");
                responseXml.append(
                    "			<paramSbst>STRT_DT=20230729000000|SHARE_SUB_CONTID1=626380439|SHARE_SUB_CONTID2=|SHARE_SUB_CONTID3=|SHARE_SUB_CONTID4=|PRDC_SRL_NO=1|");
                responseXml.append("			</paramSbst>");
                responseXml.append("			<prodHstSeq>300001073638402</prodHstSeq>");
                responseXml.append("			<soc>PL199N122</soc>");
                responseXml.append("			<socDescription>로밍 데이터 함께ON 아시아/미주 12GB(대표)</socDescription>");
                responseXml.append("			<socRateValue>60,000 WON</socRateValue>");
                responseXml.append("		</svcList>");
                responseXml.append("		<svcList>");
                responseXml.append("			<effectiveDate>20230412102655</effectiveDate>");
                responseXml.append("			<prodHstSeq>300001027926322</prodHstSeq>");
                responseXml.append("			<soc>PL19AS350</soc>");
                responseXml.append("			<socDescription>M 요금할인 1000(VAT포함)</socDescription>");
                responseXml.append("			<socRateValue>-910 WON</socRateValue>");
                responseXml.append("		</svcList>");
                responseXml.append("		<svcList>");
                responseXml.append("			<effectiveDate>20221221112445</effectiveDate>");
                responseXml.append("			<prodHstSeq>300000967342580</prodHstSeq>");
                responseXml.append("			<soc>MPAYBLOCK</soc>");
                responseXml.append("			<socDescription>휴대폰결제 이용거부</socDescription>");
                responseXml.append("			<socRateValue>Free</socRateValue>");
                responseXml.append("		</svcList>");
                responseXml.append("		<svcList>");
                responseXml.append("			<effectiveDate>20221221112445</effectiveDate>");
                responseXml.append("			<prodHstSeq>300000967342581</prodHstSeq>");
                responseXml.append("			<soc>CLIPF</soc>");
                responseXml.append("			<socDescription>발신번호표시무료</socDescription>");
                responseXml.append("			<socRateValue>Free</socRateValue>");
                responseXml.append("		</svcList>");
                responseXml.append("		<svcList>");
                responseXml.append("			<effectiveDate>20230418153151</effectiveDate>");
                responseXml.append("			<prodHstSeq>300001030912105</prodHstSeq>");
                responseXml.append("			<soc>PL22CG717</soc>");
                responseXml.append("			<socDescription>LTE 데이터 추가제공 5GB(결합)</socDescription>");
                responseXml.append("			<socRateValue>Free</socRateValue>");
                responseXml.append("		</svcList>");
                responseXml.append("		<svcList>");
                responseXml.append("			<effectiveDate>20230628100335</effectiveDate>");
                responseXml.append("			<prodHstSeq>300001067425311</prodHstSeq>");
                responseXml.append("			<soc>PL224R614</soc>");
                responseXml.append("			<socDescription>데이터 Free 쿠폰 30GB(1회)</socDescription>");
                responseXml.append("			<socRateValue>Free</socRateValue>");
                responseXml.append("		</svcList>");
                responseXml.append("		<svcList>");
                responseXml.append("			<effectiveDate>20221221112445</effectiveDate>");
                responseXml.append("			<prodHstSeq>300000967342582</prodHstSeq>");
                responseXml.append("			<soc>LTECERTID</soc>");
                responseXml.append("			<socDescription>LTE_인증상품</socDescription>");
                responseXml.append("			<socRateValue>Free</socRateValue>");
                responseXml.append("		</svcList>");
                responseXml.append("		<svcList>");
                responseXml.append("			<effectiveDate>20230412102646</effectiveDate>");
                responseXml.append("			<prodHstSeq>300001027926293</prodHstSeq>");
                responseXml.append("			<soc>LTEULDAF1</soc>");
                responseXml.append("			<socDescription>LTE데이터무제한V 1M(PM)</socDescription>");
                responseXml.append("			<socRateValue>Free</socRateValue>");
                responseXml.append("		</svcList>");
                responseXml.append("		<svcList>");
                responseXml.append("			<effectiveDate>20221221112711</effectiveDate>");
                responseXml.append("			<prodHstSeq>300000967341905</prodHstSeq>");
                responseXml.append("			<soc>VLTEAUTSV</soc>");
                responseXml.append("			<socDescription>HD 보이스</socDescription>");
                responseXml.append("			<socRateValue>Free</socRateValue>");
                responseXml.append("		</svcList>");
                responseXml.append("		<svcList>");
                responseXml.append("			<effectiveDate>20230704171732</effectiveDate>");
                responseXml.append("			<prodHstSeq>300001071204206</prodHstSeq>");
                responseXml.append("			<soc>PL214L310</soc>");
                responseXml.append("			<socDescription>휴대폰안심보험 안드로이드 플래티넘</socDescription>");
                responseXml.append("			<socRateValue>5,300 WON</socRateValue>");
                responseXml.append("		</svcList>");
                responseXml.append("		<svcList>");
                responseXml.append("			<effectiveDate>20221221112445</effectiveDate>");
                responseXml.append("			<prodHstSeq>300000967342585</prodHstSeq>");
                responseXml.append("			<soc>SMSB</soc>");
                responseXml.append("			<socDescription>SMS(문자서비스) 기본제공</socDescription>");
                responseXml.append("			<socRateValue>Free</socRateValue>");
                responseXml.append("		</svcList>");
                responseXml.append("		<svcList>");
                responseXml.append("			<effectiveDate>20230412102659</effectiveDate>");
                responseXml.append("			<prodHstSeq>300001027928132</prodHstSeq>");
                responseXml.append("			<soc>KISADDC07</soc>");
                responseXml.append("			<socDescription>M 요금할인 7,000</socDescription>");
                responseXml.append("			<socRateValue>-7,000 WON</socRateValue>");
                responseXml.append("		</svcList>");
                responseXml.append("		<svcList>");
                responseXml.append("			<effectiveDate>20230412102651</effectiveDate>");
                responseXml.append("			<prodHstSeq>300001027928111</prodHstSeq>");
                responseXml.append("			<soc>PL19AS355</soc>");
                responseXml.append("			<socDescription>M 요금할인 9000(VAT포함)</socDescription>");
                responseXml.append("			<socRateValue>-8,182 WON</socRateValue>");
                responseXml.append("		</svcList>");
                responseXml.append("		<svcList>");
                responseXml.append("			<effectiveDate>20221221112711</effectiveDate>");
                responseXml.append("			<prodHstSeq>300000967341904</prodHstSeq>");
                responseXml.append("			<soc>PSVTAUTSV</soc>");
                responseXml.append("			<socDescription>HD 영상통화</socDescription>");
                responseXml.append("			<socRateValue>Free</socRateValue>");
                responseXml.append("		</svcList>");
                responseXml.append("		<svcList>");
                responseXml.append("			<effectiveDate>20221221112445</effectiveDate>");
                responseXml.append("			<prodHstSeq>300000967342583</prodHstSeq>");
                responseXml.append("			<soc>WVMS</soc>");
                responseXml.append("			<socDescription>통합사서함</socDescription>");
                responseXml.append("			<socRateValue>Free</socRateValue>");
                responseXml.append("		</svcList>");
                responseXml.append("		<svcList>");
                responseXml.append("			<effectiveDate>20221221112445</effectiveDate>");
                responseXml.append(
                    "			<paramSbst>BLCK_NO1=060|BLCK_TYPE1=3|BLCK_NO2=|BLCK_TYPE2=|BLCK_NO3=|BLCK_TYPE3=|BLCK_NO4=|BLCK_TYPE4=|BLCK_NO5=|BLCK_TYPE5=|BLCK_NO6=|BLCK_TYPE6=|BLCK_NO7=|BLCK_TYPE7=|BLCK_NO8=|BLCK_TYPE8=|BLCK_NO9=|BLCK_TYPE9=|BLCK_NO10=|BLCK_TYPE10=|");
                responseXml.append("			</paramSbst>");
                responseXml.append("			<prodHstSeq>300000967342584</prodHstSeq>");
                responseXml.append("			<soc>NOSPAM3</soc>");
                responseXml.append("			<socDescription>정보제공사업자번호차단</socDescription>");
                responseXml.append("			<socRateValue>Free</socRateValue>");
                responseXml.append("		</svcList>");
                responseXml.append("		<svcList>");
                responseXml.append("			<effectiveDate>20221221112711</effectiveDate>");
                responseXml.append("			<prodHstSeq>300000967341903</prodHstSeq>");
                responseXml.append("			<soc>SMARTNMON</soc>");
                responseXml.append("			<socDescription>스마트폰(종량)-일반</socDescription>");
                responseXml.append("			<socRateValue>Free</socRateValue>");
                responseXml.append("		</svcList>");
                responseXml.append("		<svcList>");
                responseXml.append("			<effectiveDate>20230412102646</effectiveDate>");
                responseXml.append("			<prodHstSeq>300001027926292</prodHstSeq>");
                responseXml.append("			<soc>PL217Q731</soc>");
                responseXml.append("			<socDescription>MVNO결합전용(블라이스)</socDescription>");
                responseXml.append("			<socRateValue>Free</socRateValue>");
                responseXml.append("		</svcList>");
                responseXml.append("        <svcList>");
                responseXml.append("            <effectiveDate>20230818000000</effectiveDate>");
                responseXml.append(
                    "            <paramSbst>STRT_DT=20230818000000|END_DT=20230818235959|SHARE_MAIN_CONTID=626506218|SHARE_MAIN_PROD_HST_SEQ=300001091066712|PRDC_SRL_NO=1|</paramSbst>");
                responseXml.append("            <prodHstSeq>300001091066715</prodHstSeq>");
                responseXml.append("            <soc>PL2079778</soc>");
                responseXml.append("            <socDescription>로밍 하루종일ON 투게더(서브)</socDescription>");
                responseXml.append("            <socRateValue>5,000 WON</socRateValue>");
                responseXml.append("        </svcList>");
                responseXml.append("		<svcList>");
                responseXml.append("			<effectiveDate>20230101000000</effectiveDate>");
                responseXml.append("			<paramSbst>NOTI_NO1=01012345678|NOTI_NO2=|</paramSbst>");
                responseXml.append("			<prodHstSeq>300000000000001</prodHstSeq>");
                responseXml.append("			<soc>RCC1</soc>");
                responseXml.append("			<socDescription>통화가능알리미</socDescription>");
                responseXml.append("			<socRateValue>500 WON</socRateValue>");
                responseXml.append("		</svcList>");
                responseXml.append("		<svcList>");
                responseXml.append("			<effectiveDate>20260703100000</effectiveDate>");
                responseXml.append("			<paramSbst>BLCK_NO1=0605051505|BLCK_TYPE1=2|BLCK_NO2=|BLCK_TYPE2=|BLCK_NO3=|BLCK_TYPE3=|BLCK_NO4=|BLCK_TYPE4=|BLCK_NO5=|BLCK_TYPE5=|BLCK_NO6=|BLCK_TYPE6=|BLCK_NO7=|BLCK_TYPE7=|BLCK_NO8=|BLCK_TYPE8=|BLCK_NO9=|BLCK_TYPE9=|BLCK_NO10=|BLCK_TYPE10=|</paramSbst>");
                responseXml.append("			<prodHstSeq>300001200000001</prodHstSeq>");
                responseXml.append("			<soc>NOSPAM4</soc>");
                responseXml.append("			<socDescription>불법 TM 수신차단</socDescription>");
                responseXml.append("			<socRateValue>Free</socRateValue>");
                responseXml.append("		</svcList>");
                responseXml.append("		<svcList>");
                responseXml.append("			<effectiveDate>20260703100000</effectiveDate>");
                responseXml.append("			<paramSbst>BLCK_NO1=0601001000|BLCK_TYPE1=2|BLCK_NO2=|BLCK_TYPE2=|BLCK_NO3=|BLCK_TYPE3=|BLCK_NO4=|BLCK_TYPE4=|BLCK_NO5=|BLCK_TYPE5=|BLCK_NO6=|BLCK_TYPE6=|BLCK_NO7=|BLCK_TYPE7=|BLCK_NO8=|BLCK_TYPE8=|BLCK_NO9=|BLCK_TYPE9=|BLCK_NO10=|BLCK_TYPE10=|</paramSbst>");
                responseXml.append("			<prodHstSeq>300001200000002</prodHstSeq>");
                responseXml.append("			<soc>NOSPAM2</soc>");
                responseXml.append("			<socDescription>특정번호수신차단서비스</socDescription>");
                responseXml.append("			<socRateValue>2,000 WON</socRateValue>");
                responseXml.append("		</svcList>");
                responseXml.append("		<svcList>");
                responseXml.append("			<effectiveDate>20260703100000</effectiveDate>");
                responseXml.append("			<paramSbst>ENABLED=Y|</paramSbst>");
                responseXml.append("			<prodHstSeq>300001200000003</prodHstSeq>");
                responseXml.append("			<soc>STLPVTPHN</soc>");
                responseXml.append("			<socDescription>(안심)번호도용 문자차단서비스</socDescription>");
                responseXml.append("			<socRateValue>Free</socRateValue>");
                responseXml.append("		</svcList>");
                responseXml.append("		<svcList>");
                responseXml.append("			<effectiveDate>20260703100000</effectiveDate>");
                responseXml.append("			<paramSbst>STRT_DT=20260703100000|</paramSbst>");
                responseXml.append("			<prodHstSeq>300001200000004</prodHstSeq>");
                responseXml.append("			<soc>DATAROM01</soc>");
                responseXml.append("			<socDescription>데이터로밍 20MB</socDescription>");
                responseXml.append("			<socRateValue>10,000 WON</socRateValue>");
                responseXml.append("		</svcList>");
                responseXml.append("		<svcList>");
                responseXml.append("			<effectiveDate>20260703100000</effectiveDate>");
                responseXml.append("			<paramSbst>STRT_DT=20260703100000|END_DT=20260703235959|</paramSbst>");
                responseXml.append("			<prodHstSeq>300001200000005</prodHstSeq>");
                responseXml.append("			<soc>DYDTROM05</soc>");
                responseXml.append("			<socDescription>데이터로밍 하루 30MB</socDescription>");
                responseXml.append("			<socRateValue>5,000 WON</socRateValue>");
                responseXml.append("		</svcList>");
                responseXml.append("		<svcList>");
                responseXml.append("			<effectiveDate>20260818000000</effectiveDate>");
                responseXml.append("			<paramSbst>STRT_DT=20260818000000|END_DT=20260818235959|SHARE_SUB_CONTID1=626506218|PRDC_SRL_NO=1|</paramSbst>");
                responseXml.append("			<prodHstSeq>300001200000006</prodHstSeq>");
                responseXml.append("			<soc>PL2079777</soc>");
                responseXml.append("			<socDescription>하루종일 로밍 베이직 투게더(대표)</socDescription>");
                responseXml.append("			<socRateValue>10,000 WON</socRateValue>");
                responseXml.append("		</svcList>");
                responseXml.append("		<svcList>");
                responseXml.append("			<effectiveDate>20260703100000</effectiveDate>");
                responseXml.append("			<paramSbst>INFO_IND1=1|INFO_SBST1=01032890018|</paramSbst>");
                responseXml.append("			<prodHstSeq>300001200000007</prodHstSeq>");
                responseXml.append("			<soc>DATAROMSM</soc>");
                responseXml.append("			<socDescription>데이터로밍요금알림(태블릿PC용)</socDescription>");
                responseXml.append("			<socRateValue>Free</socRateValue>");
                responseXml.append("		</svcList>");
                responseXml.append("		<svcList>");
                responseXml.append("			<effectiveDate>20260302000000</effectiveDate>");
                responseXml.append("			<paramSbst>STRT_DT=20260302000000|END_DT=20260331235959|SHARE_MAIN_CONTID=626506218|SHARE_MAIN_PROD_HST_SEQ=300000832191831|PRDC_SRL_NO=1|</paramSbst>");
                responseXml.append("			<prodHstSeq>300001200000008</prodHstSeq>");
                responseXml.append("			<soc>PL199N117</soc>");
                responseXml.append("			<socDescription>함께 쓰는 로밍 4GB(서브)</socDescription>");
                responseXml.append("			<socRateValue>Free</socRateValue>");
                responseXml.append("		</svcList>");
                responseXml.append("		<svcList>");
                responseXml.append("			<effectiveDate>20260404000000</effectiveDate>");
                responseXml.append("			<paramSbst>STRT_DT=20260404000000|SHARE_SUB_CONTID1=626506218|SHARE_SUB_CONTID2=|SHARE_SUB_CONTID3=|SHARE_SUB_CONTID4=|PRDC_SRL_NO=3|</paramSbst>");
                responseXml.append("			<prodHstSeq>300001200000009</prodHstSeq>");
                responseXml.append("			<soc>PL199N109</soc>");
                responseXml.append("			<socDescription>함께 쓰는 로밍 4GB(대표)</socDescription>");
                responseXml.append("			<socRateValue>30,000 WON</socRateValue>");
                responseXml.append("		</svcList>");
                responseXml.append("		<svcList>");
                responseXml.append("			<effectiveDate>20260703100000</effectiveDate>");
                responseXml.append("			<paramSbst>APNT_NO1=01011112222|APNT_NO2=01012345678|APNT_NO3=|APNT_NO4=|APNT_NO5=|</paramSbst>");
                responseXml.append("			<prodHstSeq>300001200000010</prodHstSeq>");
                responseXml.append("			<soc>FCARVLSMS</soc>");
                responseXml.append("			<socDescription>로밍 해외도착알리미</socDescription>");
                responseXml.append("			<socRateValue>Free</socRateValue>");
                responseXml.append("		</svcList>");
                responseXml.append("		<svcList>");
                responseXml.append("			<effectiveDate>20260816000000</effectiveDate>");
                responseXml.append("			<paramSbst>TIME_OPTION1=11|TIME_DUR=00000300|ENLIS_DT=20260816|END_DT=20270216|</paramSbst>");
                responseXml.append("			<prodHstSeq>300001200000011</prodHstSeq>");
                responseXml.append("			<soc>PL253A854</soc>");
                responseXml.append("			<socDescription>MY TIME PLAN</socDescription>");
                responseXml.append("			<socRateValue>5,000 WON</socRateValue>");
                responseXml.append("		</svcList>");
                responseXml.append("		<svcList>");
                responseXml.append("			<effectiveDate>20260724000000</effectiveDate>");
                responseXml.append("			<prodHstSeq>300001200000012</prodHstSeq>");
                responseXml.append("			<soc>PL199N120</soc>");
                responseXml.append("			<socDescription>함께 쓰는 로밍 8GB(대표)</socDescription>");
                responseXml.append("			<socRateValue>40,000 WON</socRateValue>");
                responseXml.append("		</svcList>");
                responseXml.append("		<svcList>");
                responseXml.append("			<effectiveDate>20260724000000</effectiveDate>");
                responseXml.append("			<prodHstSeq>300001200000013</prodHstSeq>");
                responseXml.append("			<soc>PL199N126</soc>");
                responseXml.append("			<socDescription>Y 함께 쓰는 로밍 5GB(대표)</socDescription>");
                responseXml.append("			<socRateValue>18,000 WON</socRateValue>");
                responseXml.append("		</svcList>");
                responseXml.append("		<svcList>");
                responseXml.append("			<effectiveDate>20260724000000</effectiveDate>");
                responseXml.append("			<prodHstSeq>300001200000014</prodHstSeq>");
                responseXml.append("			<soc>PL199N129</soc>");
                responseXml.append("			<socDescription>Y 함께 쓰는 로밍 9GB(대표)</socDescription>");
                responseXml.append("			<socRateValue>24,000 WON</socRateValue>");
                responseXml.append("		</svcList>");
                responseXml.append("		<svcList>");
                responseXml.append("			<effectiveDate>20260724000000</effectiveDate>");
                responseXml.append("			<prodHstSeq>300001200000015</prodHstSeq>");
                responseXml.append("			<soc>PL199N132</soc>");
                responseXml.append("			<socDescription>Y 함께 쓰는 로밍 13GB(대표)</socDescription>");
                responseXml.append("			<socRateValue>36,000 WON</socRateValue>");
                responseXml.append("		</svcList>");
                responseXml.append("		<svcList>");
                responseXml.append("			<effectiveDate>20260724000000</effectiveDate>");
                responseXml.append("			<prodHstSeq>300001200000016</prodHstSeq>");
                responseXml.append("			<soc>DATAROM03</soc>");
                responseXml.append("			<socDescription>데이터로밍 100MB</socDescription>");
                responseXml.append("			<socRateValue>30,000 WON</socRateValue>");
                responseXml.append("		</svcList>");
                responseXml.append("		<svcList>");
                responseXml.append("			<effectiveDate>20260724000000</effectiveDate>");
                responseXml.append("			<prodHstSeq>300001200000017</prodHstSeq>");
                responseXml.append("			<soc>LTEDTROM5</soc>");
                responseXml.append("			<socDescription>데이터로밍 300MB</socDescription>");
                responseXml.append("			<socRateValue>50,000 WON</socRateValue>");
                responseXml.append("		</svcList>");
                responseXml.append("		<svcList>");
                responseXml.append("			<effectiveDate>20260724000000</effectiveDate>");
                responseXml.append("			<prodHstSeq>300001200000018</prodHstSeq>");
                responseXml.append("			<soc>ITGSAFE3G</soc>");
                responseXml.append("			<socDescription>중국/일본 알뜰 로밍</socDescription>");
                responseXml.append("			<socRateValue>22,727 WON</socRateValue>");
                responseXml.append("		</svcList>");
                responseXml.append("	</outDto>");
                responseXml.append("</return>");
                break;
            default:
                log.debug("Default MsfMplatFormService.java");
        }

        responseXml.append("</ns2:moscPerInfoResponse></soap:Body></soap:Envelope>");
        vo.setResponseXml(responseXml.toString());
        try {
            vo.toResponseParse();
        } catch (SelfServiceException e) {
            throw e;
        } catch (Exception e) {
            result = false;
        }
        //////////////////////////////////

        return result;
    }

    //     private <T> T getVo2(String eventCd, Class<T> clazz) throws IOException {
    //////////////////////////////////
    //         StringBuilder responseXml = new StringBuilder();
    //
    //         responseXml.append(
    //             "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><ns2:moscPerInfoResponse xmlns:ns2=\"http://selfcare.so.itl.mvno.kt.com/\">");
    //
    //         switch (eventCd) {
    //             case "Y02":
    //                 responseXml.append("<return>\n" +
    //                     "\t<outDto>\n" +
    //                     "\t   <efctStDt>20200814</efctStDt>\n" +
    //                     "\t   <famtTarifAmt>9091</famtTarifAmt>\n" +
    //                     "\t   <prodId>PL204T391</prodId>\n" +
    //                     "\t   <prodNm>응급안전 안심요금제</prodNm>\n" +
    //                     "\t</outDto>\n" +
    //                     " </return>");
    //                 break;
    //             case "Y04":
    //                 responseXml.append("<return>\n" +
    //                     "            <outDto>\n" +
    //                     "               <bthdayDate>19930714000000</bthdayDate>\n" +
    //                     "               <contPurpCd>R</contPurpCd>\n" +
    //                     "               <custNm>정교뎌</custNm>\n" +
    //                     "               <custPtclTypeCd>N1</custPtclTypeCd>\n" +
    //                     "               <custTypeCd>1</custTypeCd>\n" +
    //                     "               <intmModelId>K7001100</intmModelId>\n" +
    //                     "               <intmModelNm>PTA-VOLTE</intmModelNm>\n" +
    //                     "               <intmSeq>355398095327777</intmSeq>\n" +
    //                     "               <resultCd>00</resultCd>\n" +
    //                     "            </outDto>\n" +
    //                     "         </return>");
    //                 break;
    //             case "X01"://가입정보조회------
    //                 responseXml.append(
    //                     "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X01</appEventCd><appSendDateTime>20160112153248</appSendDateTime><appRecvDateTime>20160112153246</appRecvDateTime><appLgDateTime>20160112153246</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160112153243531</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><addr>인천 옹진군 영흥면 선재로34번길 141 </addr><email>bluemoor9521@naver.com</email><homeTel>01075116741</homeTel><initActivationDate>20140807163028</initActivationDate></outDto></return>");
    //                 break;
    //             case "X18"://실시간요금조회------
    //			responseXml.append("response massage is null.");//비정상
    //                 responseXml.append(
    //                     "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X18</appEventCd><appSendDateTime>20160112161906</appSendDateTime><appRecvDateTime>20160112161905</appRecvDateTime><appLgDateTime>20160112161905</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160112161901518</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><amntDto><gubun>월정액</gubun><payMent>6387</payMent></amntDto><amntDto><gubun>부가세</gubun><payMent>638</payMent></amntDto><amntDto><gubun>원단위절사금액</gubun><payMent>-5</payMent></amntDto><amntDto><gubun>당월요금계</gubun><payMent>7025</payMent></amntDto><searchDay>20160112</searchDay><searchTime>0101~0112</searchTime></outDto></return>");
    //                 break;
    //             case "X20":
    //                 responseXml.append(
    //                     "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X20</appEventCd><appSendDateTime>20160406161559</appSendDateTime><appRecvDateTime>20160406161557</appRecvDateTime><appLgDateTime>20160406161557</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160406161556269</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><outDto><effectiveDate>20160328134219</effectiveDate><soc>DTPLSU100</soc><socDescription>데이터플러스 m(결합) 100M</socDescription><socRateValue>5,000 WON</socRateValue></outDto><outDto><effectiveDate>20160328134233</effectiveDate><soc>DTPLSU500</soc><socDescription>데이터플러스 m(결합) 500M</socDescription><socRateValue>10,000 WON</socRateValue></outDto><outDto><effectiveDate>20160323030009</effectiveDate><soc>RCC1</soc><socDescription>통화가능알리미</socDescription><socRateValue>500 WON</socRateValue></outDto><outDto><effectiveDate>20160323094134</effectiveDate><soc>INTLIST</soc><socDescription>국제통화내역통보</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160208150750</effectiveDate><soc>NESPFMCD3</soc><socDescription>olleh WiFi싱글(무료)</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160208150750</effectiveDate><soc>VLTEAUTSV</soc><socDescription>HD 보이스</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160208150750</effectiveDate><soc>PSVTAUTSV</soc><socDescription>HD 영상통화</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160328135921</effectiveDate><soc>WIFISGLM4</soc><socDescription>WiFi 싱글 할인M6</socDescription><socRateValue>-6,000 WON</socRateValue></outDto><outDto><effectiveDate>20160208150750</effectiveDate><soc>WVMS</soc><socDescription>통합사서함</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160328134206</effectiveDate><soc>DTPLSU02G</soc><socDescription>데이터플러스 m(결합) 2G</socDescription><socRateValue>20,000 WON</socRateValue></outDto><outDto><effectiveDate>20160208150750</effectiveDate><soc>NOSPAM3</soc><socDescription>정보제공사업자번호차단</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160328135241</effectiveDate><soc>RCC1R</soc><socDescription>통화가능알리미 거부</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160328135427</effectiveDate><soc>SMS26N</soc><socDescription>신메시지매니저</socDescription><socRateValue>900 WON</socRateValue></outDto><outDto><effectiveDate>20160208150750</effectiveDate><soc>SMSB</soc><socDescription>SMS(문자서비스) 기본제공</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160328135627</effectiveDate><soc>USEBILSMS</soc><socDescription>이용요금내역알리미</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160322224830</effectiveDate><soc>CYBDANGNT</soc><socDescription>정보보호알림이(일반)</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160323040140</effectiveDate><soc>ITC</soc><socDescription>국제전화 발신제한</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160323025931</effectiveDate><soc>LOC119</soc><socDescription>119 긴급구조 위치제공</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160323134617</effectiveDate><soc>MMCISS</soc><socDescription>쇼미</socDescription><socRateValue>900 WON</socRateValue></outDto><outDto><effectiveDate>20160323134748</effectiveDate><soc>NOIPCRVE</soc><socDescription>음성로밍 완전 차단</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160323134455</effectiveDate><soc>RCC2</soc><socDescription>통화요구알리미</socDescription><socRateValue>500 WON</socRateValue></outDto><outDto><effectiveDate>20160323040102</effectiveDate><soc>CNIRDO</soc><socDescription>익명호수신거부</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160328134003</effectiveDate><soc>CNIRS</soc><socDescription>발신번호표시제한</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160208150750</effectiveDate><soc>MPAYBLOCK</soc><socDescription>휴대폰결제 이용거부</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160208150750</effectiveDate><soc>SPMFILTER</soc><socDescription>스팸차단서비스</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160322224737</effectiveDate><soc>WFSMSNDSP</soc><socDescription>웹 및 국외발신 미표시 서비스</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160328135909</effectiveDate><soc>WIFISGLM3</soc><socDescription>WiFi 싱글 M3</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160325200008</effectiveDate><soc>XRINGSMS</soc><socDescription>링투유알리미</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160208150750</effectiveDate><soc>CLIPF</soc><socDescription>발신번호표시무료</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160328134153</effectiveDate><soc>DTPLSU01G</soc><socDescription>데이터플러스 m(결합) 1G</socDescription><socRateValue>15,000 WON</socRateValue></outDto><outDto><effectiveDate>20160323065520</effectiveDate><soc>NOIPCRDT</soc><socDescription>데이터로밍 완전 차단</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160328135150</effectiveDate><soc>PPINFO</soc><socDescription>요금납부알림서비스</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160208150750</effectiveDate><soc>SMARTNMON</soc><socDescription>스마트폰(종량)-일반</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160328135933</effectiveDate><soc>WIRELESSC</soc><socDescription>무선데이터차단서비스</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160208150750</effectiveDate><soc>AIPNESPOT</soc><socDescription>WiFi 인증서비스</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160323065714</effectiveDate><soc>CATCHCALL</soc><socDescription>캐치콜서비스</socDescription><socRateValue>500 WON</socRateValue></outDto><outDto><effectiveDate>20160323025938</effectiveDate><soc>DPCBLC060</soc><socDescription>060발신차단서비스(무료)</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160208150750</effectiveDate><soc>LTECERTID</soc><socDescription>LTE_인증상품</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160323135150</effectiveDate><soc>XRING</soc><socDescription>링투유</socDescription><socRateValue>900 WON</socRateValue></outDto></outDto></return>");
    //                 break;
    //             case "X83"://회선 사용기간조회
    //                 responseXml.append("<return>");
    //                 responseXml.append("    <bizHeader>");
    //                 responseXml.append("        <appEntrPrsnId>KIS</appEntrPrsnId>");
    //                 responseXml.append("        <appAgncCd>AA00364</appAgncCd>");
    //                 responseXml.append("        <appEventCd>X83</appEventCd>");
    //                 responseXml.append("        <appSendDateTime>20211103155502</appSendDateTime>");
    //                 responseXml.append("        <appRecvDateTime>20211103155458</appRecvDateTime>");
    //                 responseXml.append("        <appLgDateTime>20211103155458</appLgDateTime>");
    //                 responseXml.append("        <appNstepUserId>91225330</appNstepUserId>");
    //                 responseXml.append("        <appOrderId></appOrderId>");
    //                 responseXml.append("    </bizHeader>");
    //                 responseXml.append("    <commHeader>");
    //                 responseXml.append("        <globalNo>9122533020211103155029878</globalNo>");
    //                 responseXml.append("        <encYn></encYn>");
    //                 responseXml.append("        <responseType>N</responseType>");
    //                 responseXml.append("        <responseCode></responseCode>");
    //                 responseXml.append("        <responseLogcd></responseLogcd>");
    //                 responseXml.append("        <responseTitle></responseTitle>");
    //                 responseXml.append("        <responseBasic></responseBasic>");
    //                 responseXml.append("        <langCode></langCode>");
    //                 responseXml.append("        <filler></filler>");
    //                 responseXml.append("    </commHeader>");
    //                 responseXml.append("    <outDto>");
    //                 responseXml.append("        <longUseAdjDayNum>0</longUseAdjDayNum>");
    //                 responseXml.append("        <realUseDayNum>2089</realUseDayNum>");
    //                 responseXml.append("        <svcContSbscDt>20160208150750</svcContSbscDt>");
    //                 responseXml.append("        <totStopDayNum>6</totStopDayNum>");
    //                 responseXml.append("        <totUseDayNum>2095</totUseDayNum>");
    //                 responseXml.append("    </outDto>");
    //                 responseXml.append("  </return>");
    //                 break;
    //             case "X88": //요금상품예약변경(X88)
    //responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X88</appEventCd><appSendDateTime>20220406191431</appSendDateTime><appRecvDateTime>20220406191429</appRecvDateTime><appLgDateTime>20220406191429</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220406191429656</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><message><rsltCd>Y</rsltCd><ruleId>100001842</ruleId><ruleMsgSbst>요금제 변경시 자동해지 되는 부가상품입니다. &#xD;- M 요금할인 5000(VAT포함)&#xD;(http--0.0.0.0-7006-4)  - M 요금할인 3000(VAT포함)</ruleMsgSbst></message><rsltYn>Y</rsltYn></outDto></return>");
    //  responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X88</appEventCd><appSendDateTime>20220406181627</appSendDateTime><appRecvDateTime>20220406181625</appRecvDateTime><appLgDateTime>20220406181625</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220406181626460</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><message><rsltCd>N</rsltCd><ruleId>100000413</ruleId><ruleMsgSbst>고객님은요금제 예약한 고객이므로 예약 취소후 처리하십시요.</ruleMsgSbst></message><message><rsltCd>Y</rsltCd><ruleId>100001842</ruleId><ruleMsgSbst>요금제 변경 시 자동해지 되는 부가상품입니다. &#xD;- M 요금할인 5000(VAT포함)&#xD; - M 요금할인 3000(VAT포함)</ruleMsgSbst></message><rsltYn>N</rsltYn></outDto></return>");
    //responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X88</appEventCd><appSendDateTime>20220406125323</appSendDateTime><appRecvDateTime>20220406125316</appRecvDateTime><appLgDateTime>20220406125316</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220406124901628</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><message><rsltCd>N</rsltCd><ruleId>MSG_100999998_1</ruleId><ruleMsgSbst>현재 선택한 요금제에서는 가입할 수 없는 부가서비스[USIM 10GB 할인프로모션]입니다.</ruleMsgSbst></message><message><rsltCd>N</rsltCd><ruleId>MSG_100999998_1</ruleId><ruleMsgSbst>현재 선택한 요금제에서는 가입할 수 없는 부가서비스[LTE 데이터 추가제공 100GB(12개월)]입니다.</ruleMsgSbst></message><rsltYn>N</rsltYn></outDto></return>");
    // responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X88</appEventCd><appSendDateTime>20220310093349</appSendDateTime><appRecvDateTime>20220310093336</appRecvDateTime><appLgDateTime>20220310093336</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220310093010331</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><message><rsltCd>Y</rsltCd><ruleId>100001136</ruleId><ruleMsgSbst>고객 문의 후 희망하면, 무선데이터차단서비스를 해지해주시기 바랍니다.</ruleMsgSbst></message><rsltYn>Y</rsltYn></outDto></return>");
    //                 responseXml.append("			<return>");
    //                 responseXml.append("				<bizHeader>");
    //                 responseXml.append("					<appEntrPrsnId>KIS</appEntrPrsnId>");
    //                 responseXml.append("					<appAgncCd>AA00364</appAgncCd>");
    //                 responseXml.append("					<appEventCd>X88</appEventCd>");
    //                 responseXml.append("					<appSendDateTime>20220530134033</appSendDateTime>");
    //                 responseXml.append("					<appRecvDateTime>20220530134031</appRecvDateTime>");
    //                 responseXml.append("					<appLgDateTime>20220530134031</appLgDateTime>");
    //                 responseXml.append("					<appNstepUserId>91225330</appNstepUserId>");
    //                 responseXml.append("					<appOrderId></appOrderId>");
    //                 responseXml.append("				</bizHeader>");
    //                 responseXml.append("				<commHeader>");
    //                 responseXml.append("					<globalNo>9122533020220530134054060</globalNo>");
    //                 responseXml.append("					<encYn></encYn>");
    //                 responseXml.append("					<responseType>N</responseType>");
    //                 responseXml.append("					<responseCode></responseCode>");
    //                 responseXml.append("					<responseLogcd></responseLogcd>");
    //                 responseXml.append("					<responseTitle></responseTitle>");
    //                 responseXml.append("					<responseBasic></responseBasic>");
    //                 responseXml.append("					<langCode></langCode>");
    //                 responseXml.append("					<filler></filler>");
    //                 responseXml.append("				</commHeader>");
    //                 responseXml.append("				<outDto>");
    //                 responseXml.append("					<message>");
    //                 responseXml.append("						<rsltCd>N</rsltCd>");
    //                 responseXml.append("						<ruleId>MSG_100999998_1</ruleId>");
    //                 responseXml.append("						<ruleMsgSbst>현재 선택한 요금제에서는 가입할 수 없는 부가서비스[LTE 데이터 추가제공 6GB]입니다.</ruleMsgSbst>");
    //                 responseXml.append("					</message>");
    //                 responseXml.append("					<rsltYn>N</rsltYn>");
    //                 responseXml.append("				</outDto>");
    //                 responseXml.append("			</return>");
    //                 break;
    //             case "Y24": // 부가서비스 가입가능여부 사전체크 — 성공 목
    //                 responseXml.append("<return><outDto><rsltCd>0000</rsltCd><sbscYn>Y</sbscYn><globalNo>LOCAL_MOCK_Y24</globalNo></outDto></return>");
    //                 break;
    //             case "X89": //요금상품예약변경조회(X89)
    //                 responseXml.append(
    //                     "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X89</appEventCd><appSendDateTime>20220308175501</appSendDateTime><appRecvDateTime>20220308175459</appRecvDateTime><appLgDateTime>20220308175459</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220308175136057</globalNo><encYn></encYn><responseType>E</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><aplyDate>20220308170741</aplyDate><basicAmt>25000</basicAmt><efctStDate>20220401000001</efctStDate><prdcCd>PL208J932</prdcCd><prdcNm>모두다 맘껏 안심 2.5G+</prdcNm></outDto></return>");
    // responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X88</appEventCd><appSendDateTime>20220308171155</appSendDateTime><appRecvDateTime>20220308171142</appRecvDateTime><appLgDateTime>20220308171142</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220308170818766</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><message><rsltCd>N</rsltCd><ruleId>100000413</ruleId><ruleMsgSbst>고객님은요금제 예약한 고객이므로 예약 취소후 처리하십시요.</ruleMsgSbst></message><message><rsltCd>Y</rsltCd><ruleId>100001136</ruleId><ruleMsgSbst>고객 문의 후 희망하면, 무선데이터차단서비스를 해지해주시기 바랍니다.</ruleMsgSbst></message><rsltYn>N</rsltYn></outDto></return>");
    //                 break;
    //             case "X90": //요금상품예약변경취소(X90)
    //                 responseXml.append("<return>");
    //                 responseXml.append("    <bizHeader>");
    //                 responseXml.append("        <appEntrPrsnId>KIS</appEntrPrsnId>");
    //                 responseXml.append("        <appAgncCd>AA00364</appAgncCd>");
    //                 responseXml.append("        <appEventCd>X89</appEventCd>");
    //                 responseXml.append("        <appSendDateTime>20210902150015</appSendDateTime>");
    //                 responseXml.append("        <appRecvDateTime>20210902145952</appRecvDateTime>");
    //                 responseXml.append("        <appLgDateTime>20210902145952</appLgDateTime>");
    //                 responseXml.append("        <appNstepUserId>82023154</appNstepUserId>");
    //                 responseXml.append("        <appOrderId/>");
    //                 responseXml.append("    </bizHeader>");
    //                 responseXml.append("    <commHeader>");
    //                 responseXml.append("        <globalNo>912788510902000000000001</globalNo>");
    //                 responseXml.append("        <encYn/>");
    //                 responseXml.append("        <responseType>N</responseType>");
    //                 responseXml.append("        <responseBasic>요금제 예약 이력이 존재하지 않습니다.</responseBasic>\n");
    //                 responseXml.append("    </commHeader>");
    //                 responseXml.append("</return>");
    //                 break;
    //             default:
    //                 log.debug("Default MsfMplatFormService.java");
    //         }
    //
    //         responseXml.append("</ns2:moscPerInfoResponse></soap:Body></soap:Envelope>");

    //////////////////////////////////
    //
    //         XmlMapper mapper = new XmlMapper();
    //         JsonNode root = mapper.readTree(responseXml.toString().getBytes());
    //         JsonNode outDtoNode = root.findValue("return");
    //
    //         return mapper.treeToValue(outDtoNode, clazz);
    //     }


    /*
     * 테스트를 위해 정상적인 리턴을 강제로 넘겨준다.
     */
    public boolean getVoNe(int param, CommonXmlNoSelfServiceException vo) {
        boolean result = true;
        //////////////////////////////////
        StringBuffer responseXml = new StringBuffer();

        responseXml.append(
            "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><ns2:moscPerInfoResponse xmlns:ns2=\"http://selfcare.so.itl.mvno.kt.com/\">");

        switch (param) {
            case 19://요금상품변경
                //responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X19</appEventCd><appSendDateTime>20220406204440</appSendDateTime><appRecvDateTime>20220406204437</appRecvDateTime><appLgDateTime>20220406204437</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220406204437223</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader></return>");
                //responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X19</appEventCd><appSendDateTime>20220406201742</appSendDateTime><appRecvDateTime>20220406201741</appRecvDateTime><appLgDateTime>20220406201741</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220406201326559</globalNo><encYn></encYn><responseType>E</responseType><responseCode>ITL_SFC_E033</responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic>고객님께서는 일시정지 상태이므로 상품을 변경 하실 수 없습니다.</responseBasic><langCode></langCode><filler></filler></commHeader></return>");
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X19</appEventCd><appSendDateTime>20160114133336</appSendDateTime><appRecvDateTime>20160114133334</appRecvDateTime><appLgDateTime>20160114133334</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160114133330197</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader></return>");
                //responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X19</appEventCd><appSendDateTime>20160114133336</appSendDateTime><appRecvDateTime>20160114133334</appRecvDateTime><appLgDateTime>20160114133334</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160114133330197</globalNo><encYn></encYn><responseType>S</responseType><responseCode>ITL_SYS_E9999</responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic>M-PLATFORM SYSTEM ERROR.</responseBasic><langCode></langCode><filler></filler></commHeader></return>");
                break;
            case 21://부가서비스신청
                //responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X01</appEventCd><appSendDateTime>20160112153248</appSendDateTime><appRecvDateTime>20160112153246</appRecvDateTime><appLgDateTime>20160112153246</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160112153243531</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><addr>인천 옹진군 영흥면 선재로34번길 141 </addr><email>bluemoor9521@naver.com</email><homeTel>01075116741</homeTel><initActivationDate>20140807163028</initActivationDate></outDto></return>");
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X21</appEventCd><appSendDateTime>20220311145506</appSendDateTime><appRecvDateTime>20220311145501</appRecvDateTime><appLgDateTime>20220311145501</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220311145132712</globalNo><encYn></encYn><responseType>E</responseType><responseCode>ITL_SFC_E021</responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic>[오토링]상품과 [링투유] 상품은 동시에 가입할 수 없습니다.</responseBasic><langCode></langCode><filler></filler></commHeader></return>");
                break;
            case 38://부가서비스해지
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X38</appEventCd><appSendDateTime>20160112153248</appSendDateTime><appRecvDateTime>20160112153246</appRecvDateTime><appLgDateTime>20160112153246</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160112153243531</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader></return>");
                break;
            case 80://OTP인증서비스(X80)
                responseXml.append("<return>");
                responseXml.append("    <bizHeader>");
                responseXml.append("       <appEntrPrsnId>SKY</appEntrPrsnId>");
                responseXml.append("       <appAgncCd>SPT8050</appAgncCd>");
                responseXml.append("       <appEventCd>X80</appEventCd>");
                responseXml.append("       <appSendDateTime>20210203171537</appSendDateTime>");
                responseXml.append("       <appRecvDateTime>20210203171536</appRecvDateTime>");
                responseXml.append("       <appLgDateTime>20210203171536</appLgDateTime>");
                responseXml.append("       <appNstepUserId>82023154</appNstepUserId>");
                responseXml.append("       <appOrderId/>");
                responseXml.append("    </bizHeader>");
                responseXml.append("    <commHeader>");
                responseXml.append("       <globalNo>20210203104300120</globalNo>");
                responseXml.append("       <encYn/>");
                responseXml.append("       <responseType>N</responseType>");
                responseXml.append("       <responseCode/>");
                responseXml.append("       <responseLogcd/>");
                responseXml.append("       <responseTitle/>");
                responseXml.append("       <responseBasic/>");
                responseXml.append("       <langCode/>");
                responseXml.append("       <filler/>");
                responseXml.append("    </commHeader>");
                responseXml.append("    <outDto>");
                responseXml.append("       <otpNo>7805</otpNo>");
                responseXml.append("       <resltCd>00</resltCd>");
                responseXml.append("       <resltMsgSbst>정상 처리 가능</resltMsgSbst>");
                responseXml.append("    </outDto>");
                responseXml.append("</return>");
                break;
            case 87: //x87
                responseXml.append("<return>");
                responseXml.append("	<bizHeader>");
                responseXml.append("		<appEntrPrsnId>KIS</appEntrPrsnId>");
                responseXml.append("		<appAgncCd>AA00364</appAgncCd>");
                responseXml.append("		<appEventCd>X87</appEventCd>");
                responseXml.append("		<appSendDateTime>20220408142116</appSendDateTime>");
                responseXml.append("		<appRecvDateTime>20220408142115</appRecvDateTime>");
                responseXml.append("		<appLgDateTime>20220408142115</appLgDateTime>");
                responseXml.append("		<appNstepUserId>91225330</appNstepUserId>");
                responseXml.append("		<appOrderId></appOrderId>");
                responseXml.append("	</bizHeader>");
                responseXml.append("	<commHeader>");
                responseXml.append("		<globalNo>9122533020220408142115206</globalNo>");
                responseXml.append("		<encYn></encYn>");
                responseXml.append("		<responseType>N</responseType>");
                responseXml.append("		<responseCode></responseCode>");
                responseXml.append("		<responseLogcd></responseLogcd>");
                responseXml.append("		<responseTitle></responseTitle>");
                responseXml.append("		<responseBasic></responseBasic>");
                responseXml.append("		<langCode></langCode>");
                responseXml.append("		<filler></filler>");
                responseXml.append("	</commHeader>");
                responseXml.append("	<outDto>");
                responseXml.append("		<combDcTypeNm>알뜰폰 동일명의 결합</combDcTypeNm>");
                responseXml.append("		<combProdNm>(MVNO 결합) 모바일</combProdNm>");
                responseXml.append("		<combTypeNm>MVNO 결합</combTypeNm>");
                responseXml.append("		<engtPerdMonsNum>0년</engtPerdMonsNum>");
                responseXml.append("		<moscCombDtlListOutDTO>");
                responseXml.append("			<combEngtPerdMonsNum>3년</combEngtPerdMonsNum>");
                responseXml.append("			<prodNm>인터넷 베이직</prodNm>");
                responseXml.append("			<svcContDivNm>Internet</svcContDivNm>");
                responseXml.append("			<svcNo></svcNo>");
                responseXml.append("		</moscCombDtlListOutDTO>");

                responseXml.append("		<moscCombDtlListOutDTO>");
                responseXml.append("			<combEngtExpirDt>20280403</combEngtExpirDt>");
                responseXml.append("			<combEngtPerdMonsNum>3년</combEngtPerdMonsNum>");
                responseXml.append("			<combEngtStDt>20250404</combEngtStDt>");
                responseXml.append("			<prodNm>지니 TV 슬림</prodNm>");
                responseXml.append("			<svcContDivNm>IPTV</svcContDivNm>");
                responseXml.append("			<svcNo></svcNo>");
                responseXml.append("		</moscCombDtlListOutDTO>");

                responseXml.append("		<moscCombDtlListOutDTO>");
                responseXml.append("			<combEngtPerdMonsNum>약정없음</combEngtPerdMonsNum>");
                responseXml.append("			<prodNm>LTE 표준</prodNm>");
                responseXml.append("			<svcContDivNm>Mobile(KIS)</svcContDivNm>");
                responseXml.append("			<svcNo>01027148794</svcNo>");
                responseXml.append("		</moscCombDtlListOutDTO>");
                responseXml.append("	</outDto>");
                responseXml.append("</return>");
                break;

            // responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X87</appEventCd><appSendDateTime>20220408142116</appSendDateTime><appRecvDateTime>20220408142115</appRecvDateTime><appLgDateTime>20220408142115</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220408142115206</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><combDcTypeNm>회선별 기여도 할인</combDcTypeNm><combProdNm>(홈) 인터넷+모바일</combProdNm><combTypeNm>홈</combTypeNm><engtPerdMonsNum>3년</engtPerdMonsNum><moscCombDtlListOutDTO><combEngtExpirDt>20241109</combEngtExpirDt><combEngtPerdMonsNum>3년</combEngtPerdMonsNum><prodNm>인터넷 베이직</prodNm><svcContDivNm>Internet</svcContDivNm><svcNo>z!64120575992</svcNo></moscCombDtlListOutDTO>	<moscCombDtlListOutDTO><combEngtPerdMonsNum>약정없음</combEngtPerdMonsNum>	<prodNm>데이터 맘껏 15GB+/100분</prodNm><svcContDivNm>Mobile(KIS)</svcContDivNm><svcNo>01025817234</svcNo></moscCombDtlListOutDTO><moscCombDtlListOutDTO><combEngtPerdMonsNum>약정없음</combEngtPerdMonsNum><prodNm>데이터ON 비디오</prodNm><svcContDivNm>Mobile</svcContDivNm><svcNo>01096014852</svcNo>	</moscCombDtlListOutDTO></outDto></return>");
            //                responseXml.append(" <return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X87</appEventCd><appSendDateTime>20220408142116</appSendDateTime><appRecvDateTime>20220408142115</appRecvDateTime><appLgDateTime>20220408142115</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220408142115206</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><combDcTypeNm>알뜰폰 동일명의 결합</combDcTypeNm><combProdNm>(MVNO 결합) 모바일</combProdNm><combTypeNm>MVNO 결합</combTypeNm><engtPerdMonsNum>0년</engtPerdMonsNum><moscCombDtlListOutDTO><combEngtPerdMonsNum>약정없음</combEngtPerdMonsNum><prodNm>실용 USIM 1.7</prodNm><svcContDivNm>Mobile(KIS)</svcContDivNm><svcNo>01097785180</svcNo></moscCombDtlListOutDTO><moscCombDtlListOutDTO><combEngtPerdMonsNum>약정없음</combEngtPerdMonsNum><prodNm>LTE 표준</prodNm><svcContDivNm>Mobile(KIS)</svcContDivNm><svcNo>01027148794</svcNo></moscCombDtlListOutDTO></outDto></return>");
            //responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X87</appEventCd><appSendDateTime>20220406165031</appSendDateTime><appRecvDateTime>20220406165030</appRecvDateTime><appLgDateTime>20220406165030</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220406165146658</globalNo><encYn></encYn><responseType>E</responseType><responseCode>ITL_SFC_E114</responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic>[01043255080]회선은 결합되지 않은 회선입니다. </responseBasic><langCode></langCode><filler></filler></commHeader></return>");
            //데이터있음 responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X87</appEventCd><appSendDateTime>20220225103208</appSendDateTime><appRecvDateTime>20220225103206</appRecvDateTime><appLgDateTime>20220225103206</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220225102903049</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><combDcTypeNm>알뜰폰 결합</combDcTypeNm><combProdNm>(MVNO 결합) 모바일+모바일</combProdNm><combTypeNm>MVNO 결합</combTypeNm><engtPerdMonsNum>0년</engtPerdMonsNum><moscCombDtlListOutDTO><combEngtPerdMonsNum>약정없음</combEngtPerdMonsNum><prodNm>데이터 맘껏 안심 1GB+/100분</prodNm><svcContDivNm>Mobile(KIS)</svcContDivNm><svcNo>01045859158</svcNo></moscCombDtlListOutDTO><moscCombDtlListOutDTO><combEngtPerdMonsNum>약정없음</combEngtPerdMonsNum><prodNm>LTE 표준</prodNm><svcContDivNm>Mobile(KIS)</svcContDivNm><svcNo>01027148794</svcNo></moscCombDtlListOutDTO></outDto></return>");
            //불가능   responseXml.append("  <return>    <bizHeader>     <appEntrPrsnId>KIS</appEntrPrsnId>     <appAgncCd>AA00364</appAgncCd>     <appEventCd>X87</appEventCd>     <appSendDateTime>20220127162615</appSendDateTime>     <appRecvDateTime>20220127162611</appRecvDateTime>     <appLgDateTime>20220127162611</appLgDateTime>     <appNstepUserId>91225330</appNstepUserId>     <appOrderId></appOrderId>    </bizHeader>    <commHeader>     <globalNo>9122533020220127162359616</globalNo>     <encYn></encYn>     <responseType>E</responseType>     <responseCode>ITL_SFC_E114</responseCode>     <responseLogcd></responseLogcd>     <responseTitle></responseTitle>     <responseBasic>[01029672627]회선은 결합되지 않은 회선입니다. </responseBasic>     <langCode></langCode>     <filler></filler>    </commHeader>   </return>");

            case 77: //x77
                responseXml.append("<return>");
                responseXml.append("    <bizHeader>");
                responseXml.append("        <appEntrPrsnId>KIS</appEntrPrsnId>");
                responseXml.append("        <appAgncCd>AA00364</appAgncCd>");
                responseXml.append("        <appEventCd>X77</appEventCd>");
                responseXml.append("        <appSendDateTime>20230818120443</appSendDateTime>");
                responseXml.append("        <appRecvDateTime>20230818120442</appRecvDateTime>");
                responseXml.append("        <appLgDateTime>20230818120442</appLgDateTime>");
                responseXml.append("        <appNstepUserId>91225330</appNstepUserId>");
                responseXml.append("        <appOrderId></appOrderId>");
                responseXml.append("    </bizHeader>");
                responseXml.append("    <commHeader>");
                responseXml.append("        <globalNo>9122533020230818120442749</globalNo>");
                responseXml.append("        <encYn></encYn>");
                responseXml.append("        <responseType>N</responseType>");
                responseXml.append("        <responseCode></responseCode>");
                responseXml.append("        <responseLogcd></responseLogcd>");
                responseXml.append("        <responseTitle></responseTitle>");
                responseXml.append("        <responseBasic></responseBasic>");
                responseXml.append("        <langCode></langCode>");
                responseXml.append("        <filler></filler>");
                responseXml.append("    </commHeader>");
                responseXml.append("    <outDto>");
                responseXml.append("        <moscMvnoComInfo>");
                responseXml.append("            <combSvcNo>01030004307</combSvcNo>");
                responseXml.append("            <combYn>N</combYn>");
                responseXml.append("            <prdcCd>PL225N754</prdcCd>");
                responseXml.append("            <prdcNm>M 프리미엄 11GB</prdcNm>");
                responseXml.append("            <svcDivCd>모바일</svcDivCd>");
                responseXml.append("            <svcNo>01030004307</svcNo>");
                responseXml.append("            <indvInfoAgreeMsgSbst></indvInfoAgreeMsgSbst>");
                responseXml.append("        </moscMvnoComInfo>");
                responseXml.append("        <moscSrchCombInfoList>");
                responseXml.append("            <combSvcNo>01095104513</combSvcNo>");
                responseXml.append("            <combYn>N</combYn>");
                responseXml.append("            <prdcCd>099S01</prdcCd>");
                responseXml.append("            <prdcNm>M 프리미엄 11GB</prdcNm>");
                responseXml.append("            <svcContOpnDt>20250301</svcContOpnDt>");
                responseXml.append("            <svcDivCd>모바일</svcDivCd>");
                responseXml.append("            <svcNo>01012341234</svcNo>");
                responseXml.append("            <corrNm>MVNOKIS</corrNm>");
                responseXml.append("        </moscSrchCombInfoList>");
                responseXml.append("        <moscSrchCombInfoList>");
                responseXml.append("            <combSvcNo>01095104513</combSvcNo>");
                responseXml.append("            <combYn>N</combYn>");
                responseXml.append("            <prdcCd>099S01</prdcCd>");
                responseXml.append("            <prdcNm>인터넷 슬림</prdcNm>");
                responseXml.append("            <svcContOpnDt>20250426</svcContOpnDt>");
                responseXml.append("            <svcDivCd>인터넷</svcDivCd>");
                responseXml.append("            <svcNo>z!62194901411</svcNo>");
                responseXml.append("            <corrNm>MVNOKIS</corrNm>");
                responseXml.append("        </moscSrchCombInfoList>");
                responseXml.append("        <moscSrchCombInfoList>");
                responseXml.append("            <combSvcNo>01095104513</combSvcNo>");
                responseXml.append("            <combYn>N</combYn>");
                responseXml.append("            <prdcCd>PL203D127</prdcCd>");
                responseXml.append("            <prdcNm>시니어 안심 2GB+</prdcNm>");
                responseXml.append("            <svcContOpnDt>20250430</svcContOpnDt>");
                responseXml.append("            <svcDivCd>인터넷</svcDivCd>");
                responseXml.append("            <svcNo>z!73205012522</svcNo>");
                responseXml.append("            <corrNm>MVNOKIS</corrNm>");
                responseXml.append("        </moscSrchCombInfoList>");
                responseXml.append("    </outDto>");
                responseXml.append("</return>");

                break;
            case 78: //x78
                responseXml.append("<return>");
                responseXml.append("    <bizHeader>");
                responseXml.append("        <appEntrPrsnId>KIS</appEntrPrsnId>");
                responseXml.append("        <appAgncCd>AA00364</appAgncCd>");
                responseXml.append("        <appEventCd>X78</appEventCd>");
                responseXml.append("        <appSendDateTime>20230818120446</appSendDateTime>");
                responseXml.append("        <appRecvDateTime>20230818120443</appRecvDateTime>");
                responseXml.append("        <appLgDateTime>20230818120443</appLgDateTime>");
                responseXml.append("        <appNstepUserId>91225330</appNstepUserId>");
                responseXml.append("        <appOrderId></appOrderId>");
                responseXml.append("    </bizHeader>");
                responseXml.append("    <commHeader>");
                responseXml.append("        <globalNo>9122533020230818120443444</globalNo>");
                responseXml.append("        <encYn></encYn>");
                responseXml.append("        <responseType>N</responseType>");
                responseXml.append("        <responseCode></responseCode>");
                responseXml.append("        <responseLogcd></responseLogcd>");
                responseXml.append("        <responseTitle></responseTitle>");
                responseXml.append("        <responseBasic></responseBasic>");
                responseXml.append("        <langCode></langCode>");
                responseXml.append("        <filler></filler>");
                responseXml.append("    </commHeader>");
                responseXml.append("    <outDto>");
                responseXml.append("        <moscCombPreChkListOutDTO>");
                responseXml.append("            <resltMsg>정상</resltMsg>");
                responseXml.append("            <sbscYn>Y</sbscYn>");
                responseXml.append("        </moscCombPreChkListOutDTO>");
                responseXml.append("        <moscCombPreChkListOutDTO>");
                responseXml.append("            <resltMsg>정상</resltMsg>");
                responseXml.append("            <sbscYn>Y</sbscYn>");
                responseXml.append("            <svcNo>01095104513</svcNo>");
                responseXml.append("        </moscCombPreChkListOutDTO>");
                responseXml.append("        <resltMsg>정상</resltMsg>");
                responseXml.append("        <sbscYn>Y</sbscYn>");
                responseXml.append("    </outDto>");
                responseXml.append("</return>");
                break;
            case 79: //x79
                responseXml.append("<return>");
                responseXml.append("    <bizHeader>");
                responseXml.append("        <appEntrPrsnId>KIS</appEntrPrsnId>");
                responseXml.append("        <appAgncCd>AA00364</appAgncCd>");
                responseXml.append("        <appEventCd>X79</appEventCd>");
                responseXml.append("        <appSendDateTime>20230818120446</appSendDateTime>");
                responseXml.append("        <appRecvDateTime>20230818120443</appRecvDateTime>");
                responseXml.append("        <appLgDateTime>20230818120443</appLgDateTime>");
                responseXml.append("        <appNstepUserId>91225330</appNstepUserId>");
                responseXml.append("        <appOrderId></appOrderId>");
                responseXml.append("    </bizHeader>");
                responseXml.append("    <commHeader>");
                responseXml.append("        <globalNo>9122533020230818120443444</globalNo>");
                responseXml.append("        <encYn></encYn>");
                responseXml.append("        <responseType>N</responseType>");
                responseXml.append("        <responseCode></responseCode>");
                responseXml.append("        <responseLogcd></responseLogcd>");
                responseXml.append("        <responseTitle></responseTitle>");
                responseXml.append("        <responseBasic></responseBasic>");
                responseXml.append("        <langCode></langCode>");
                responseXml.append("        <filler></filler>");
                responseXml.append("    </commHeader>");
                responseXml.append("    <outDto>");
                responseXml.append("        <moscCombPreChkListOutDTO>");
                responseXml.append("            <resltMsg>정상</resltMsg>");
                responseXml.append("            <sbscYn>Y</sbscYn>");
                responseXml.append("        </moscCombPreChkListOutDTO>");
                responseXml.append("        <moscCombPreChkListOutDTO>");
                responseXml.append("            <resltMsg>정상</resltMsg>");
                responseXml.append("            <sbscYn>Y</sbscYn>");
                responseXml.append("            <svcNo>01095104513</svcNo>");
                responseXml.append("        </moscCombPreChkListOutDTO>");
                responseXml.append("        <resltMsg>정상</resltMsg>");
                responseXml.append("        <sbscYn>Y</sbscYn>");
                responseXml.append("    </outDto>");
                responseXml.append("</return>");
                break;
            case 71: //x77
                //responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X71</appEventCd><appSendDateTime>20220304092547</appSendDateTime><appRecvDateTime>20220304092546</appRecvDateTime><appLgDateTime>20220304092546</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220304092230512</globalNo><encYn></encYn><responseType>E</responseType><responseCode>ITL_SFC_E105</responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic>데이터쉐어링 결합 중인 대상이 없습니다.</responseBasic><langCode></langCode><filler></filler></commHeader></return>");
                //responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA11070</appAgncCd><appEventCd>X71</appEventCd><appSendDateTime>20200527133020</appSendDateTime><appRecvDateTime>20200527133019</appRecvDateTime><appLgDateTime>20200527133019</appLgDateTime><appNstepUserId>116833564</appNstepUserId><appOrderId/></bizHeader><commHeader><globalNo>9114053920180405150101014</globalNo><encYn/><responseType>N</responseType><responseCode/><responseLogcd/><responseTitle/><responseBasic/><langCode/><filler/></commHeader><outDto><outDataSharingDto><efctStDt>20200525</efctStDt><svcNo>01074285434</svcNo></outDataSharingDto><outDataSharingDto><efctStDt>20200513</efctStDt><svcNo>01073044224</svcNo></outDataSharingDto></outDto></return>");
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X71</appEventCd><appSendDateTime>20220303175825</appSendDateTime><appRecvDateTime>20220303175824</appRecvDateTime><appLgDateTime>20220303175824</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220303175509424</globalNo><encYn></encYn><responseType>E</responseType><responseCode>ITL_SFC_E105</responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic>데이터쉐어링 결합 중인 대상이 없습니다.</responseBasic><langCode></langCode><filler></filler></commHeader></return>");
                break;
            case 69: //x69
                //실패케이스
                //responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X69</appEventCd><appSendDateTime>20220412164939</appSendDateTime><appRecvDateTime>20220412164938</appRecvDateTime><appLgDateTime>20220412164938</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220412164940813</globalNo><encYn></encYn><responseType>E</responseType><responseCode>ITL_SFC_E106</responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic>데이터쉐어링 결합 가능한 개통된 고객이 없습니다.</responseBasic><langCode></langCode><filler></filler></commHeader></return>");
                //성공케이스
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X69</appEventCd><appSendDateTime>20220414162249</appSendDateTime><appRecvDateTime>20220414162248</appRecvDateTime><appLgDateTime>20220414162248</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220414162252205</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><outDataSharingDto><rsltInd>Y</rsltInd><svcNo>01098132788</svcNo></outDataSharingDto></outDto></return>");
                break;
            case 91://신용카드 인증조회(X91)
                responseXml.append("<return>");
                responseXml.append("    <bizHeader>");
                responseXml.append("        <appEntrPrsnId>KIS</appEntrPrsnId>");
                responseXml.append("        <appAgncCd>AA00364</appAgncCd>");
                responseXml.append("        <appEventCd>X91</appEventCd>");
                responseXml.append("        <appSendDateTime>20240401135847</appSendDateTime>");
                responseXml.append("        <appRecvDateTime>20240401135847</appRecvDateTime>");
                responseXml.append("        <appLgDateTime>20240401135847</appLgDateTime>");
                responseXml.append("        <appNstepUserId>91225330</appNstepUserId>");
                responseXml.append("        <appOrderId></appOrderId>");
                responseXml.append("    </bizHeader>");

                /*responseXml.append("    <commHeader>");
                responseXml.append("        <globalNo>9122533020240401135847448</globalNo>");
                responseXml.append("        <encYn></encYn>");
                responseXml.append("        <responseType>N</responseType>");
                responseXml.append("        <responseCode></responseCode>");
                responseXml.append("        <responseLogcd></responseLogcd>");
                responseXml.append("        <responseTitle></responseTitle>");
                responseXml.append("        <responseBasic></responseBasic>");
                responseXml.append("        <langCode></langCode>");
                responseXml.append("        <filler></filler>");
                responseXml.append("    </commHeader>");
                responseXml.append("    <outDto>");
                responseXml.append("        <trtMsg>주민번호 불일치</trtMsg>");
                responseXml.append("        <trtResult>N</trtResult>");
                responseXml.append("    </outDto>");*/


                responseXml.append("<commHeader>");
                responseXml.append("    <globalNo>moscCrdtCardAthnInfo-TEST-00010</globalNo>");
                responseXml.append("    <responseType>N</responseType>");
                responseXml.append("</commHeader>");
                responseXml.append("<outDto>");
                responseXml.append("    <crdtCardKindCd>GM</crdtCardKindCd>");
                responseXml.append("    <crdtCardNm>GM</crdtCardNm>");
                responseXml.append("    <trtMsg>정상완료</trtMsg>");
                responseXml.append("    <trtResult>Y</trtResult>");
                responseXml.append("</outDto>");



                /*
                responseXml.append("    <outDto>");
                responseXml.append("        <trtMsg>유효기간 오류</trtMsg>");
                responseXml.append("        <trtResult>N</trtResult>");
                responseXml.append("    </outDto>");


                responseXml.append("    <outDto>");
                responseXml.append("        <crdtCardKindCd>DY</crdtCardKindCd>");
                responseXml.append("        <crdtCardNm>DY</crdtCardNm>");
                responseXml.append("        <trtMsg></trtMsg>");
                responseXml.append("       <trtResult>Y</trtResult>");
                responseXml.append("    </outDto>");*/

                responseXml.append("</return>");
                break;
            default:
                log.debug("Default MsfMplatFormService.java");
        }

        responseXml.append("</ns2:moscPerInfoResponse></soap:Body></soap:Envelope>");
        vo.setResponseXml(responseXml.toString());
        try {
            vo.toResponseParse();
        } catch (Exception e) {
            result = false;
        }
        //////////////////////////////////

        return result;
    }


    public boolean getVoEtc(String param, CommonXmlVO vo) {
        boolean result = true;
        //////////////////////////////////
        StringBuffer responseXml = new StringBuffer();

        if ("Y42".equals(param)) {
            responseXml.append(
                "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><ns2:moscRemindSmsStatMgmtResponse xmlns:ns2=\"http://selfcare.so.itl.mvno.kt.com/\">");
        } else {
            responseXml.append(
                "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><ns2:moscPerInfoResponse xmlns:ns2=\"http://selfcare.so.itl.mvno.kt.com/\">");
        }
        if ("D01".equals(param)) {

            responseXml.append("<return>");
            responseXml.append("	<bizHeader>");
            responseXml.append("		<appEntrPrsnId>KIS</appEntrPrsnId>");
            responseXml.append("		<appAgncCd>AA00364</appAgncCd>");
            responseXml.append("		<appEventCd>OP0</appEventCd>");
            responseXml.append("		<appSendDateTime>20210507153839</appSendDateTime>");
            responseXml.append("		<appRecvDateTime>20210507153839</appRecvDateTime>");
            responseXml.append("		<appLgDateTime>20210507153839</appLgDateTime>");
            responseXml.append("		<appNstepUserId>82023154</appNstepUserId>");
            responseXml.append("		<appOrderId/>");
            responseXml.append("	</bizHeader>");
            responseXml.append("	<commHeader>");
            responseXml.append("		<globalNo>202104191519010006</globalNo>");
            responseXml.append("		<encYn/>");
            responseXml.append("		<responseType>N</responseType>");
            responseXml.append("		<responseCode/>");
            responseXml.append("		<responseLogcd/>");
            responseXml.append("		<responseTitle/>");
            responseXml.append("		<responseBasic/>");
            responseXml.append("		<langCode/>");
            responseXml.append("		<filler/>");
            responseXml.append("	</commHeader>");
            responseXml.append("	<outDto>");
            responseXml.append("		<psblYn>Y</psblYn>");//Y N
            responseXml.append("		<rsltCd>00</rsltCd>");
            responseXml.append("		<rsltMsg>성공</rsltMsg>");
            responseXml.append("		<bizOrgCd>01</bizOrgCd>");
            responseXml.append("		<acceptTime>09:00</acceptTime>");
            responseXml.append("	</outDto>");
            responseXml.append("</return>");
        } else if ("D02".equals(param)) {

            responseXml.append("<return>");
            responseXml.append("	<bizHeader>");
            responseXml.append("		<appEntrPrsnId>KIS</appEntrPrsnId>");
            responseXml.append("		<appAgncCd>AA00364</appAgncCd>");
            responseXml.append("		<appEventCd>OP0</appEventCd>");
            responseXml.append("		<appSendDateTime>20210507153839</appSendDateTime>");
            responseXml.append("		<appRecvDateTime>20210507153839</appRecvDateTime>");
            responseXml.append("		<appLgDateTime>20210507153839</appLgDateTime>");
            responseXml.append("		<appNstepUserId>82023154</appNstepUserId>");
            responseXml.append("		<appOrderId/>");
            responseXml.append("	</bizHeader>");
            responseXml.append("	<commHeader>");
            responseXml.append("		<globalNo>202104191519010006</globalNo>");
            responseXml.append("		<encYn/>");
            responseXml.append("		<responseType>N</responseType>");
            responseXml.append("		<responseCode/>");
            responseXml.append("		<responseLogcd/>");
            responseXml.append("		<responseTitle/>");
            responseXml.append("		<responseBasic/>");
            responseXml.append("		<langCode/>");
            responseXml.append("		<filler/>");
            responseXml.append("	</commHeader>");
            responseXml.append("	<outDto>");
            responseXml.append("		<deliveryOrderId>143253999</deliveryOrderId>");
            responseXml.append("		<ktOrderId>KIS2021050710013</ktOrderId>");
            responseXml.append("		<rsltCd>00</rsltCd>");
            responseXml.append("		<rsltMsg>정상</rsltMsg>");
            responseXml.append("	</outDto>");
            responseXml.append("</return>");
        } else if ("D03".equals(param)) {

            responseXml.append("<return>");
            responseXml.append("	<bizHeader>");
            responseXml.append("		<appEntrPrsnId>KIS</appEntrPrsnId>");
            responseXml.append("		<appAgncCd>AA00364</appAgncCd>");
            responseXml.append("		<appEventCd>OP0</appEventCd>");
            responseXml.append("		<appSendDateTime>20210507153839</appSendDateTime>");
            responseXml.append("		<appRecvDateTime>20210507153839</appRecvDateTime>");
            responseXml.append("		<appLgDateTime>20210507153839</appLgDateTime>");
            responseXml.append("		<appNstepUserId>82023154</appNstepUserId>");
            responseXml.append("		<appOrderId/>");
            responseXml.append("	</bizHeader>");
            responseXml.append("	<commHeader>");
            responseXml.append("		<globalNo>202104191519010006</globalNo>");
            responseXml.append("		<encYn/>");
            responseXml.append("		<responseType>N</responseType>");
            responseXml.append("		<responseCode/>");
            responseXml.append("		<responseLogcd/>");
            responseXml.append("		<responseTitle/>");
            responseXml.append("		<responseBasic/>");
            responseXml.append("		<langCode/>");
            responseXml.append("		<filler/>");
            responseXml.append("	</commHeader>");
            responseXml.append("	<outDto>");
            responseXml.append("		<deliveryOrderId>143253999</deliveryOrderId>");
            responseXml.append("		<ktOrderId>KIS2021050710013</ktOrderId>");
            responseXml.append("		<rsltCd>00</rsltCd>");
            responseXml.append("		<rsltMsg>성공</rsltMsg>");
            responseXml.append("	</outDto>");
            responseXml.append("</return>");
        } else if ("Y07".equals(param)) {
            responseXml.append("<return>");
            responseXml.append("	<bizHeader>");
            responseXml.append("		<appEntrPrsnId>KIS</appEntrPrsnId>");
            responseXml.append("		<appAgncCd>AA00364</appAgncCd>");
            responseXml.append("		<appEventCd>Y07</appEventCd>");
            responseXml.append("		<appSendDateTime>20220801133105</appSendDateTime>");
            responseXml.append("		<appRecvDateTime>20220801133110</appRecvDateTime>");
            responseXml.append("		<appLgDateTime>20220801133110</appLgDateTime>");
            responseXml.append("		<appNstepUserId>82254451</appNstepUserId>");
            responseXml.append("		<appOrderId/>");
            responseXml.append("	</bizHeader>");
            responseXml.append("	<commHeader>");
            responseXml.append("		<globalNo>202104191519010006</globalNo>");
            responseXml.append("		<encYn/>");
            responseXml.append("		<responseType>N</responseType>");
            responseXml.append("		<responseCode/>");
            responseXml.append("		<responseLogcd/>");
            responseXml.append("		<responseTitle/>");
            responseXml.append("		<responseBasic/>");
            responseXml.append("		<langCode/>");
            responseXml.append("		<filler/>");
            responseXml.append("	</commHeader>");
            responseXml.append("	<outDto>");
            responseXml.append("		<intmMdlId>K7901367</intmMdlId>");
            responseXml.append("		<intmSeq>8982300421002878851</intmSeq>");
            responseXml.append("		<pukNo1>03243060</pukNo1>");
            responseXml.append("	</outDto>");
            responseXml.append("</return>");
        } else if ("Y39".equals(param)) {
            responseXml.append("<return>");
            responseXml.append("	<bizHeader>");
            responseXml.append("		<appEntrPrsnId>KCA</appEntrPrsnId>");
            responseXml.append("		<appAgncCd>SPT8050</appAgncCd>");
            responseXml.append("		<appEventCd>Y39</appEventCd>");
            responseXml.append("		<appSendDateTime>20240304133105</appSendDateTime>");
            responseXml.append("		<appRecvDateTime>20240304133110</appRecvDateTime>");
            responseXml.append("		<appLgDateTime>20240304133110</appLgDateTime>");
            responseXml.append("		<appNstepUserId>82023154</appNstepUserId>");
            responseXml.append("		<appOrderId/>");
            responseXml.append("	</bizHeader>");
            responseXml.append("	<commHeader>");
            responseXml.append("		<globalNo>JHW15616510004</globalNo>");
            responseXml.append("		<encYn/>");
            responseXml.append("		<responseType>N</responseType>");
            responseXml.append("		<responseCode/>");
            responseXml.append("		<responseLogcd/>");
            responseXml.append("		<responseTitle/>");
            responseXml.append("		<responseBasic/>");
            responseXml.append("		<langCode/>");
            responseXml.append("		<filler/>");
            responseXml.append("	</commHeader>");
            responseXml.append("	<outDto>");
            responseXml.append("		<ipinCi>vTAH+121212+tCH5bVO/crf7t9a3w==</ipinCi>");
            responseXml.append("	</outDto>");
            responseXml.append("</return>");
        } else if ("Y41".equals(param)) {
            responseXml.append("<return>");
            responseXml.append("	<bizHeader>");
            responseXml.append("		<appEntrPrsnId>KIS</appEntrPrsnId>");
            responseXml.append("		<appAgncCd>AA00364</appAgncCd>");
            responseXml.append("		<appEventCd>Y41</appEventCd>");
            responseXml.append("		<appSendDateTime>20250204153409</appSendDateTime>");
            responseXml.append("		<appRecvDateTime>20250204153407</appRecvDateTime>");
            responseXml.append("		<appLgDateTime>20250204153407</appLgDateTime>");
            responseXml.append("		<appNstepUserId>91225330</appNstepUserId>");
            responseXml.append("		<appOrderId />");
            responseXml.append("	</bizHeader>");
            responseXml.append("	<commHeader>");
            responseXml.append("		<globalNo>9122533020250204153407442</globalNo>");
            responseXml.append("		<encYn />");
            responseXml.append("		<responseType>N</responseType>");
            responseXml.append("		<responseCode />");
            responseXml.append("		<responseLogcd />");
            responseXml.append("		<responseTitle />");
            responseXml.append("		<responseBasic />");
            responseXml.append("		<langCode />");
            responseXml.append("		<filler />");
            responseXml.append("	</commHeader>");
            responseXml.append("</return>");
        } else if ("Y42".equals(param)) {
            responseXml.append("<return>");
            responseXml.append("	<bizHeader>");
            responseXml.append("		<appEntrPrsnId>SKY</appEntrPrsnId>");
            responseXml.append("		<appAgncCd>SPT8050</appAgncCd>");
            responseXml.append("		<appEventCd>Y42</appEventCd>");
            responseXml.append("		<appSendDateTime>20241018105844</appSendDateTime>");
            responseXml.append("		<appRecvDateTime>20240304133110</appRecvDateTime>");
            responseXml.append("		<appLgDateTime>20241018105841</appLgDateTime>");
            responseXml.append("		<appNstepUserId>82022194</appNstepUserId>");
            responseXml.append("	</bizHeader>");
            responseXml.append("	<commHeader>");
            responseXml.append("		<globalNo>moscRemindSmsStat-CU-00001</globalNo>");
            responseXml.append("		<responseType>N</responseType>");
            responseXml.append("	</commHeader>");
            responseXml.append("	<outDto>");
            responseXml.append("		<resultCd>00</resultCd>");
            responseXml.append("		<smsRcvBlckYn>Y</smsRcvBlckYn>");
            responseXml.append("	</outDto>");
            responseXml.append("</return>");
        } else if ("Y48".equals(param)) {
            responseXml.append("<return>");
            responseXml.append("	<bizHeader>");
            responseXml.append("		<appEntrPrsnId>KIS</appEntrPrsnId>");
            responseXml.append("		<appAgncCd>AA00364</appAgncCd>");
            responseXml.append("		<appEventCd>Y48</appEventCd>");
            responseXml.append("		<appSendDateTime>20250627161537</appSendDateTime>");
            responseXml.append("		<appRecvDateTime>20250627161527</appRecvDateTime>");
            responseXml.append("		<appLgDateTime>20250627161527</appLgDateTime>");
            responseXml.append("		<appNstepUserId>91225330</appNstepUserId>");
            responseXml.append("	</bizHeader>");
            responseXml.append("	<commHeader>");
            responseXml.append("		<globalNo>9122533020250627161527609</globalNo>");
            responseXml.append("		<responseType>N</responseType>");
            responseXml.append("	</commHeader>");
            responseXml.append("	<outDto>");
            responseXml.append("		<moscVirtlBnkacnNoListInfoOutDTO>");
            responseXml.append("			<bankCd>090</bankCd>");
            responseXml.append("			<bankNm>카카오뱅크</bankNm>");
            responseXml.append("			<efctFnsDt>99991231000000</efctFnsDt>");
            responseXml.append("			<efctStDt>20240203001727</efctStDt>");
            responseXml.append("			<repVirtlBnkacnYn>N</repVirtlBnkacnYn>");
            responseXml.append("			<virtlBnkacnNo>9206033603712</virtlBnkacnNo>");
            responseXml.append("		</moscVirtlBnkacnNoListInfoOutDTO>");
            responseXml.append("		<moscVirtlBnkacnNoListInfoOutDTO>");
            responseXml.append("			<bankCd>004</bankCd>");
            responseXml.append("			<bankNm>국민은행</bankNm>");
            responseXml.append("			<efctFnsDt>99991231000000</efctFnsDt>");
            responseXml.append("			<efctStDt>20240202235342</efctStDt>");
            responseXml.append("			<repVirtlBnkacnYn>N</repVirtlBnkacnYn>");
            responseXml.append("			<virtlBnkacnNo>71799073446495</virtlBnkacnNo>");
            responseXml.append("		</moscVirtlBnkacnNoListInfoOutDTO>");
            responseXml.append("		<moscVirtlBnkacnNoListInfoOutDTO>");
            responseXml.append("			<bankCd>088</bankCd>");
            responseXml.append("			<bankNm>신한은행</bankNm>");
            responseXml.append("			<efctFnsDt>99991231000000</efctFnsDt>");
            responseXml.append("			<efctStDt>20230403000253</efctStDt>");
            responseXml.append("			<repVirtlBnkacnYn>N</repVirtlBnkacnYn>");
            responseXml.append("			<virtlBnkacnNo>56215781702331</virtlBnkacnNo>");
            responseXml.append("		</moscVirtlBnkacnNoListInfoOutDTO>");
            responseXml.append("		<moscVirtlBnkacnNoListInfoOutDTO>");
            responseXml.append("			<bankCd>089</bankCd>");
            responseXml.append("			<bankNm>케이뱅크</bankNm>");
            responseXml.append("			<efctFnsDt>99991231000000</efctFnsDt>");
            responseXml.append("			<efctStDt>20230402232816</efctStDt>");
            responseXml.append("			<repVirtlBnkacnYn>N</repVirtlBnkacnYn>");
            responseXml.append("			<virtlBnkacnNo>70005420400822</virtlBnkacnNo>");
            responseXml.append("		</moscVirtlBnkacnNoListInfoOutDTO>");
            responseXml.append("		<moscVirtlBnkacnNoListInfoOutDTO>");
            responseXml.append("			<bankCd>011</bankCd>");
            responseXml.append("			<bankNm>농협은행</bankNm>");
            responseXml.append("			<efctFnsDt>99991231000000</efctFnsDt>");
            responseXml.append("			<efctStDt>20260402234611</efctStDt>");
            responseXml.append("			<repVirtlBnkacnYn>N</repVirtlBnkacnYn>");
            responseXml.append("			<virtlBnkacnNo>79019324019884</virtlBnkacnNo>");
            responseXml.append("		</moscVirtlBnkacnNoListInfoOutDTO>");
            responseXml.append("	</outDto>");
            responseXml.append("</return>");
        }

        if ("Y42".equals(param)) {
            responseXml.append("</ns2:moscRemindSmsStatMgmtResponse></soap:Body></soap:Envelope>");
        } else {
            responseXml.append("</ns2:moscPerInfoResponse></soap:Body></soap:Envelope>");
        }

        vo.setResponseXml(responseXml.toString());
        try {
            vo.toResponseParse();
        } catch (Exception e) {
            result = false;
        }
        //////////////////////////////////

        return result;
    }


}
