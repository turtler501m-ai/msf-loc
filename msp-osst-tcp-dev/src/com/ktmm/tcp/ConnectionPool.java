package com.ktmm.tcp;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.HashMap;
import org.apache.log4j.Logger;

public class ConnectionPool extends Thread {
	protected static final Logger logger = Logger.getLogger(ConnectionPool.class);
	
	private Socket socket = null;
	private String server = null;

	public ConnectionPool(Socket socket, String server) {
		this.socket = socket;
		this.server = server;
	}

	@Override
	public void run() {
		
		logger.debug("server=" + server);
//		long startTime = System.currentTimeMillis();
		
		ConfigProperty config = new ConfigProperty();
		
		if("DEV".equals(server)){
			config.InitDev();
		}else{
			config.InitProd();
		}
		
		//DB
		DBManager db = new DBManager();
		
		OutputStream stream = null;
		
		try {
			stream = socket.getOutputStream();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		// 리턴메세지
		String rtnCd = "0000";
		String rtnMsg = "";
		
		try {
			// 초기화로딩
			db.init(config.getDbdriver());
			// DB연결
			db.connect(config.getDburl(), config.getDbuser(), config.getDbpass());
			
			// 전문 수신
			BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
			byte[] recv= new byte[2343];
			String chkType = "";
			int read = 0;
			while((read = bis.read(recv)) != -1) {
				logger.debug("len=" + recv.length);
				logger.debug("recv=" + new String(recv, 0, recv.length, "EUC-KR"));
				
				// 직권해지 전문처리 >> 유심바로배송 추가(20210512) >> TOSS 배달주문상태알림 추가(20220608)
				chkType = new String(recv, 4, 4);
				if("CPRC".equals(chkType) || "CRST".equals(chkType) || "DVRY".equals(chkType) || "DVAP".equals(chkType)) break;
				else break;
			}
			
			if(recv == null || "".equals(recv)){
				logger.error("수신전문없음.....");
				socket.close();
				return;
			}
			
			// 연동이력 로그생성
			db.insertOsstIfLog(new String(recv, "EUC-KR"));
			
			if("CPRC".equals(chkType) || "CRST".equals(chkType)){
				String motSize = new String(recv, 0, 4, "EUC-KR");			// 전문길이
				String motType = new String(recv, 4, 4, "EUC-KR");			// 전문종류
				String motSbst = new String(recv, 9, Integer.parseInt(motSize) - 10, "EUC-KR");
				
				HashMap<String, String> map = new HashMap<String, String>();
				
				String result[] = motSbst.split("\\|");
				for(int i=0; i<result.length; i++){
					String param[] = result[i].split("\\=");
					map.put(param[0], param[1]);
				}
				
				logger.debug("map=" + map);
				
				if(map.containsKey("OSST_ORD_NO") == false || "".equals(map.get("OSST_ORD_NO"))){
					rtnCd = "0001";
				}else{
					// 결과update
					db.updatePpsCanUsrMst(map);
					// 프로시져call
					db.callPpsCanUsrRes(map);
					// EP0(해지 요청) 처리 결과 UPDATE
					db.updateMspCanTrg(map);
				}
				
				// 리턴메세지
				rtnMsg = "0008" + motType + rtnCd;
				
			}else if ("DVRY".equals(chkType)){  //구분값 : DVRY 유심바로배송
				// tcp encoding ksc5601 으로 변환

				String motSize = new String(recv, 0, 4, "EUC-KR");			    //전문길이
				int recvSize = Integer.parseInt(motSize);
				
				int recvTrim  = new String(recv,  "EUC-KR").trim().getBytes("EUC-KR").length;
				String motType = new String(recv, 4, 4, "EUC-KR");			// 전문종류
				String reqRecv = new  String(recv, 0, recvSize , "EUC-KR");
				String motSbst = new String(recv, 9, recvTrim - 10, "EUC-KR");
				
				//int recvSize = Integer.parseInt(motSize);
								
				HashMap<String, String> map = new HashMap<String, String>();
				String result[] = motSbst.split("\\|");
				map.put("ORDER_STAT_RSN_DESC", "");
				map.put("BIZ_ORG_CD", "");
				map.put("RE_ACCEPT_YN", "");
				map.put("CHANNEL_CD", "");// 20220610 TOSS 채널 추가
				
				try{
					for(int i=0; i<result.length; i++){
						String param[] = result[i].split("\\=");
						map.put(param[0], param[1] );
					}
					logger.debug("map=" + map);
					
					String dlvryStateCd = map.get("ORDER_STAT_CD");   //배송상태코드
					String deliveryOrderId =  map.get("DELIVERY_ORDER_ID"); //배송오더아이디					
					String dlvryStateRsnDesc = map.get("ORDER_STAT_RSN_DESC");//배달 상태 변경 상세		
					String bizOrgCd = map.get("BIZ_ORG_CD");//배달 업체 코드
					String reAcceptYn = map.get("RE_ACCEPT_YN");//재접수 여부
					
					if(bizOrgCd.trim().length() > 2){ //배달 업체 코드 자릿수 체크
						rtnCd = "9999";
						map.put("BIZ_ORG_CD", "");
					}else if(reAcceptYn.trim().length() > 1){ //재접수 여부 자릿수 체크
						rtnCd = "9999";
						map.put("RE_ACCEPT_YN", "");
					}else if(dlvryStateRsnDesc.length() > 200){
						rtnCd = "9999";
						map.put("ORDER_STAT_RSN_DESC", "");
					}else if(deliveryOrderId.length() > 20 || deliveryOrderId == "null"){   //배송오더아이디 자릿수 체크 및 String null 체크
						rtnCd = "9999";
						map.put("DELIVERY_ORDER_ID", "");
					}else{
						if(dlvryStateCd.trim().length() != 1 || dlvryStateCd.trim() == null ){  //배송상태코드 사이즈체크및 공백체크
							rtnCd = "9999";
							map.put("ORDER_STAT_CD", "");
						}else {
							logger.debug(recvTrim + " >>>>>>>>> 공백제거전문길이");
							logger.debug(recvSize+ " >>>>>>>>> 전문내기재된길이");
							if(recvSize != recvTrim){
								rtnCd = "0003";
								logger.debug("[유심바로배송] 배달상태 알림 전문길이가 맞지 않습니다.");
							}else{
								//kt 오더 id가 있는지 확인
								int ktOrderId =  db.selelctKtOrderId(map);
								if(ktOrderId > 0){
									rtnCd = "0000";
									db.updateDvryDirMst(map);
								}else{
									rtnCd = "1000"; //1000 없는 kt 오더 id
									logger.debug("[유심바로배송] 없는 KT ORDER ID 입니다.");
								}
							}
						}
					}
				}catch(Exception e){
					rtnCd = "9999";
				}
				// 리턴메세지
				String rtn = "0000" + motType +"(RSLT_CD=" +  rtnCd + ")";
				int rtnsize = rtn.length();
				rtnMsg =  String.format("%04d", rtnsize)+ rtn.substring(4 , rtnsize); 

				map.put("REQ_DATA",  reqRecv);
				map.put("RECV_DATA", rtnMsg);
				map.put("RESULT_CD", rtnCd);
    			db.insertDvryDirHist(map); //history 적재
    			
			}else if ("DVAP".equals(chkType)){  //구분값 : DVAP TOSS 배달주문상태알림
				
				logger.debug("DVAP START =" + chkType);
				// tcp encoding ksc5601 으로 변환
				String motSize = new String(recv, 0, 4, "EUC-KR"); //전문길이
				int recvSize = Integer.parseInt(motSize);
				
				int recvTrim  = new String(recv,  "EUC-KR").trim().getBytes("EUC-KR").length;
				String motType = new String(recv, 4, 4, "EUC-KR"); // 전문종류
				String reqRecv = new  String(recv, 0, recvSize , "EUC-KR");
				String motSbst = new String(recv, 9, recvTrim - 10, "EUC-KR");
				
				HashMap<String, String> map = new HashMap<String, String>();
				String result[] = motSbst.split("\\|");

				map.put("ORDER_STAT_RSN_DESC", "");
				map.put("RE_ACCEPT_YN", "");
				map.put("RSV_ORDER_DT", "");
				map.put("TARGET_ADDR_LAT", "");
				map.put("TARGET_ADDR_LNG", "");
				
				try{
					for(int i=0; i<result.length; i++){
						String param[] = result[i].split("\\=");
						map.put(param[0], param[1] );
					}
					logger.debug("DVAP map=" + map);
					// 인서트 로직 넣어야함. 기존 테이블에 컬럼 추가하고 insert
					db.insertDvryChannel(map);
					
				}catch(Exception e){
					rtnCd = "9999";
				}
				// 리턴메세지
				String rtn = "0000" + motType +"(RSLT_CD=" +  rtnCd + ")";
				int rtnsize = rtn.length();
				rtnMsg =  String.format("%04d", rtnsize)+ rtn.substring(4 , rtnsize); 

				map.put("REQ_DATA",  reqRecv);
				map.put("RECV_DATA", rtnMsg);
				map.put("RESULT_CD", rtnCd);
    			//db.insertDvryDirHist(map); //history 적재
    			
			}else if ("UC0N".equals(chkType)) {  //구분값 : UC0N 유심변경 사전체크 및 처리 결과
                // tcp encoding ksc5601 으로 변환
                String motSize = new String(recv, 0, 4, "EUC-KR");                //전문길이
                int recvSize = Integer.parseInt(motSize);

                int recvTrim = new String(recv, "EUC-KR").trim().getBytes("EUC-KR").length;
                String motType = new String(recv, 4, 4, "EUC-KR");            // 전문종류
                String reqRecv = new String(recv, 0, recvSize, "EUC-KR");
                String motSbst = new String(recv, 9, recvTrim - 10, "EUC-KR");

                HashMap<String, String> map = new HashMap<String, String>();
                String result[] = motSbst.split("\\|");
                map.put("TRGT_ATRIB_SBST", "");
                map.put("TRGT_FALU_MSG", "");
                map.put("TRGT_INSUR_MSG", "");

                try {
                    for (int i = 0; i < result.length; i++) {
                        String param[] = result[i].split("\\=");
                        map.put(param[0], param[1]);
                    }
                    logger.debug(" [유심변경] map=" + map);
                    logger.debug(recvTrim + " >>>>>>>>> 공백제거전문길이");
                    logger.debug(recvSize + " >>>>>>>>> 전문내기재된길이");
                    if (recvSize != recvTrim) {
                        rtnCd = "0003";
                        logger.debug("[유심변경] 알림 전문길이가 맞지 않습니다.");
                    } else {
                        //mvnoOrderId 가 있는지 확인
                        int mvnoOrderId = db.selelctKtOrderIdUC0(map);
                        if (mvnoOrderId > 0) {
                            rtnCd = "0000";
                            db.updateUC0(map);
                        } else {
                            rtnCd = "1000"; //1000 이터 미존재
                            logger.debug("[유심변경] 데이터 미존재");
                        }
                    }
                } catch (Exception e) {
                    rtnCd = "9999";
                }
                // 리턴메세지
                String rtn = "0000" + motType + "(RSLT_CD=" + rtnCd + ")";
                int rtnsize = rtn.length();
                rtnMsg = String.format("%04d", rtnsize) + rtn.substring(4, rtnsize);

                map.put("REQ_DATA", reqRecv);
                map.put("RECV_DATA", rtnMsg);
                map.put("RESULT_CD", rtnCd);

            //명의변경사전체크(MC0), 명의변경(MP0) 결과
            }else if("MC0N".equals(chkType) || "MP0N".equals(chkType)) {
                String motSize = new String(recv, 0, 4, "EUC-KR"); //전문길이
                int recvSize = Integer.parseInt(motSize);

                int recvTrim = new String(recv, "EUC-KR").trim().getBytes("EUC-KR").length;
                String motType = new String(recv, 4, 4, "EUC-KR");            // 전문종류
                String reqRecv = new String(recv, 0, recvSize, "EUC-KR");
                String motSbst = new String(recv, 9, recvTrim - 10, "EUC-KR");

                HashMap<String, String> map = new HashMap<String, String>();
                String result[] = motSbst.split("\\|");

                try {
                    for (int i = 0; i < result.length; i++) {
                        String param[] = result[i].split("\\=");
                        map.put(param[0], param[1]);
                    }
                    logger.debug(" [명의변경처리결과] map=" + map);
                    logger.debug(recvTrim + " >>>>>>>>> 공백제거전문길이");
                    logger.debug(recvSize + " >>>>>>>>> 전문내기재된길이");
                    if (recvSize != recvTrim) {
                        rtnCd = "0003";
                        logger.debug("[명의변경처리결과] 알림 전문길이가 맞지 않습니다.");
                    } else {
                        String mvnoOrdNo = "";
                        String osstOrdNo = map.get("OSST_ORD_NO") == null ? "" : map.get("OSST_ORD_NO").trim();
                        //OSST_ORD_NO가 있으면
                        if(!osstOrdNo.isEmpty()) {
                            //MVNO_ORD_NO 찾아오기
                            mvnoOrdNo = db.selectMvnoOrdNo(map);
                        }else {
                            logger.debug("[명의변경처리결과] OSST_ORD_NO를 찾을수 없습니다.");
                        }

                        //MVNO_ORD_NO가 없으면 로그저장
                        if(mvnoOrdNo == null || mvnoOrdNo.isEmpty()) {
                            logger.debug("[명의변경처리결과] OSST_ORD_NO : " + osstOrdNo);
                            logger.debug("[명의변경처리결과] MVNO_ORD_NO를 찾을수 없습니다.");
                        }

                        //MVNO_ORD_NO가 없어도, OSST_ORD_NO를 못찾을수 있는 경우 대비래서 아래 모두 실행

                        String rsltCd = map.get("RSLT_CD") == null ? "" : map.get("RSLT_CD").trim();
                        String rsltMsg = map.get("RSLT_MSG") == null ? "" : map.get("RSLT_MSG").trim();
                        String addMsg = "";
                        /*
                        2000	양도인 사전체크
                        3000	양수인 사전체크
                        4000	양도인 상품사전체크
                        6000	양수인 상품사전체크
                        */
                        if(!rsltMsg.isEmpty()) {
                            if ("2000".equals(rsltCd)) {
                                addMsg = map.get("CUST_TRGT_FALU_MSG") == null ? "" : map.get("CUST_TRGT_FALU_MSG").trim();
                            } else if ("3000".equals(rsltCd)) {
                                addMsg = map.get("RCV_CUST_TRGT_FALU_MSG") == null ? "" : map.get("RCV_CUST_TRGT_FALU_MSG").trim();
                            } else if ("4000".equals(rsltCd)) {
                                addMsg = map.get("PRDC_TRGT_FALU_MSG") == null ? "" : map.get("PRDC_TRGT_FALU_MSG").trim();
                            } else if ("6000".equals(rsltCd)) {
                                addMsg = map.get("RCV_PRDC_TRGT_FALU_MSG") == null ? "" : map.get("RCV_PRDC_TRGT_FALU_MSG").trim();
                            }

                            if (!addMsg.isEmpty()) {
                                rsltMsg = rsltMsg + " : " + addMsg;
                            }

                            //rsltMsg 길이 검사 및 자르기
                            byte[] bytes = rsltMsg.getBytes("UTF-8");
                            if(bytes.length > 990) {
                                int byteCount = 0;
                                int index = 0;
                                while (index < rsltMsg.length()) {
                                    char c = rsltMsg.charAt(index);
                                    int charByte = String.valueOf(c).getBytes("UTF-8").length;
                                    if(byteCount + charByte > 990) {
                                        break;
                                    }
                                    byteCount += charByte;
                                    index++;
                                }
                                rsltMsg = rsltMsg.substring(0, index);
                            }
                        }

                        DataVO vo = new DataVO();
                        vo.setMvnoOrdNo(mvnoOrdNo);
                        vo.setOsstOrdNo(osstOrdNo); //OSST주문번호
                        vo.setPrgrStatCd(map.get("PRGR_STAT_CD") == null ? "" : map.get("PRGR_STAT_CD").trim()); //처리상태
                        vo.setSvcCntrNo(map.get("SVC_CNTR_NO") == null ? "" : map.get("SVC_CNTR_NO").trim()); //서비스계약번호
                        if("MC2".equals(vo.getPrgrStatCd())) {
                            vo.setRcvCustNo(map.get("RCV_CUST_NO") == null ? "" : map.get("RCV_CUST_NO").trim()); //양수인고객번호
                            vo.setRcvBillAcntNo(map.get("RCV_BILL_ACNT_NO") == null ? "" : map.get("RCV_BILL_ACNT_NO").trim()); //양수인청구계정번호
                        }else if("MP2".equals(vo.getPrgrStatCd())) {
                            vo.setSvcCntrNo(map.get("NEW_SVC_CNTR_NO") == null ? "" : map.get("NEW_SVC_CNTR_NO").trim()); //명변후 서비스계약번호
                        }
                        vo.setRsltCd(rsltCd);
                        vo.setRsltMsg(rsltMsg);

                        //MCP_REQUEST_OSST insert
                        db.insertRequestOsst(vo);
                        //MNCP_CUST_REQUEST_NAME_CHG update
                        db.updateNameChg(vo);
                    }
                } catch (Exception e) {
                    rtnCd = "9999";
                }
                // 리턴메세지
                String rtn = "0000" + motType + "(RSLT_CD=" + rtnCd + ")";
                int rtnsize = rtn.length();
                rtnMsg = String.format("%04d", rtnsize) + rtn.substring(4, rtnsize);

                map.put("REQ_DATA", reqRecv);
                map.put("RECV_DATA", rtnMsg);
                map.put("RESULT_CD", rtnCd);

			}else{
				String osstOrdNo        = new String(recv, 0, 14, "EUC-KR");			// OSST오더번호
				String mvnoOrdNo        = new String(recv, 14, 14, "EUC-KR");			// MVNO오더번호
				String prgrStatCd       = new String(recv, 28, 3, "EUC-KR");			// 진행상태코드
				String custId           = new String(recv, 31, 9, "EUC-KR");			// 고객ID
				String svcCntrNo        = new String(recv, 40, 9, "EUC-KR");			// 서비스계약번호
				String rsltCd           = new String(recv, 49, 4, "EUC-KR");			// 처리결과코드
				String rsltMsg          = new String(recv, 53, 500, "EUC-KR");			// 처리결과메시지
				String rsltDt           = new String(recv, 553, 14, "EUC-KR");			// 처리일시
				String nstepGlobalId    = new String(recv, 567, 25, "EUC-KR");			// nstep global id
				String prdcChkNotiMsg   = new String(recv, 592, 1500, "EUC-KR");		// 상품체크안내메세지
				String npBchngCmpnCntrTypeCd = new String(recv, 2092, 1, "EUC-KR");		// 번호이동변경전사업자계약유형코드
				String npFee            = new String(recv, 2093, 15, "EUC-KR");			// 번호이동수수료
				String npNchrgChageAmt  = new String(recv, 2108, 15, "EUC-KR");			// 타사미청구금액
				String npPenltAmt       = new String(recv, 2123, 15, "EUC-KR");			// 번호이동위약금
				String npNpayAmt        = new String(recv, 2138, 15, "EUC-KR");			// 번호이동미납금액
				String npHndsetInslAmt  = new String(recv, 2153, 15, "EUC-KR");			// 번호이동단말기할부금
				String npPrepayAmt      = new String(recv, 2168, 15, "EUC-KR");			// 번호이동선납금액
				String npBaseChage      = new String(recv, 2183, 15, "EUC-KR");			// 번호이동기본료
				String npNtnlTlkChage   = new String(recv, 2198, 15, "EUC-KR");			// 번호이동국내통화료
				String npIntlTlkChage   = new String(recv, 2213, 15, "EUC-KR");			// 번호이동국제통화료
				String npAdtnUseChage   = new String(recv, 2228, 15, "EUC-KR");			// 번호이동부가사용료
				String npEtcUseChage    = new String(recv, 2243, 15, "EUC-KR");			// 번호이동기타사용료
				String npVat            = new String(recv, 2258, 15, "EUC-KR");			// 번호이동부가세
				String npRmnyTgtStDt    = new String(recv, 2273, 8, "EUC-KR");			// 번호이동수납대상시작일자
				String npRmnyTgtFnsDt   = new String(recv, 2281, 8, "EUC-KR");			// 번호이동수납대상종료일자
				String dclaDeedEftDt 	= new String(recv, 2289, 8, "EUC-KR");			// 외국인체류만료일자
				String sbscLmtQnty		= new String(recv, 2297, 9, "EUC-KR");			// 가입한도수량
				String sbscCircuitNum	= new String(recv, 2306, 9, "EUC-KR");			// 현재가입수량
				String dlnqAmt			= new String(recv, 2315, 9, "EUC-KR");			// 미납금액
				String eiccId			= new String(recv, 2324, 19, "EUC-KR");			// eSIM ICC ID
				
				DataVO vo = new DataVO();
				vo.setOsstOrdNo(osstOrdNo.trim());
				vo.setMvnoOrdNo(mvnoOrdNo.trim());
				vo.setPrgrStatCd(prgrStatCd.trim());
				vo.setCustId(custId.trim());
				vo.setSvcCntrNo(svcCntrNo.trim());
				vo.setRsltCd(rsltCd.trim());
				vo.setRsltMsg(rsltMsg.trim());
				vo.setRsltDt(rsltDt.trim());
				vo.setNstepGlobalId(nstepGlobalId.trim());
				vo.setRemark(prdcChkNotiMsg.trim());
				vo.setNpBchngCmpnCntrTypeCd(npBchngCmpnCntrTypeCd.trim());
				vo.setNpFee(npFee.trim());
				vo.setNpNchrgAmt(npNchrgChageAmt.trim());
				vo.setNpPnltAmt(npPenltAmt.trim());
				vo.setNpUnpayAmt(npNpayAmt.trim());
				vo.setNpHndstInstAmt(npHndsetInslAmt.trim());
				vo.setNpPrepayAmt(npPrepayAmt.trim());
				vo.setNpBaseChrgAmt(npBaseChage.trim());
				vo.setNpNtnlChrgAmt(npNtnlTlkChage.trim());
				vo.setNpIntlChrgAmt(npIntlTlkChage.trim());
				vo.setNpAddChrgAmt(npAdtnUseChage.trim());
				vo.setNpEtcChrgAmt(npEtcUseChage.trim());
				vo.setNpVat(npVat.trim());
				vo.setNpRmnStrtDt(npRmnyTgtStDt.trim());
				vo.setNpRmnEndDt(npRmnyTgtFnsDt.trim());
				vo.setDclaDeedEftDt(dclaDeedEftDt.trim());
				vo.setSbscLmtQnty(sbscLmtQnty.trim());
				vo.setSbscCircuitNum(sbscCircuitNum.trim());
				vo.setDlnqAmt(dlnqAmt.trim());
				vo.setEiccId(eiccId.trim());
				
				
				// 결과update
				db.insertRequestOsst(vo);
				
				// 사전체크 오류 발생시 진행상태 변경
				if("PC2".equals(vo.getPrgrStatCd()) && !"0000".equals(vo.getRsltCd())){
					// 진행상태UPDATE
					db.updateRequestState(vo);
				}
				
				// 개통결과전문처리
				if("OP2".equals(vo.getPrgrStatCd())){
					// 기존에는 결과 코드(0000)로 구분하였으나 개통 완료 코드가 확장됨에 따라(3060, 3070) 계약번호 여부로 처리
					String contractNum = vo.getSvcCntrNo();
					if (contractNum == null || "0".equals(contractNum)) {
						logger.debug("OSST 지연으로 인한 개통여부 체크");
						contractNum = db.getOsstDelayOpenYn(vo.getMvnoOrdNo());
					}
					if(contractNum != null) {
						vo.setSvcCntrNo(contractNum);
						vo.setRsltCd("0000");
					}
					// 부가서비스 진행여부 UPDATE
					db.updateRequestAddition(vo);
					// 진행상태UPDATE
					db.updateRequestState(vo);
				}
				
				// 결과코드
				if(osstOrdNo == null || "".equals(osstOrdNo)){
					rtnCd = "0001";
				}
				else if(mvnoOrdNo == null || "".equals(mvnoOrdNo)){
					rtnCd = "0002";
				}
				
				rtnMsg = rtnCd;
				
			}
			
			// 디비연결종료
			db.close();
			
			// 결과리턴
			stream.write(rtnMsg.getBytes("EUC-KR"));
			stream.flush();
			logger.debug(rtnMsg);
			
		} catch (ClassNotFoundException e) {
			logger.debug("ClassNotFoundException Driver 로딩 에러");
			e.printStackTrace();
			rtnCd = "9999";
			try {
				stream.write(rtnCd.getBytes());
				stream.flush();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (SQLException e) {
			logger.error("SQLException DB connection 에러");
			e.printStackTrace();
			rtnCd = "9999";
			try {
				stream.write(rtnCd.getBytes());
				stream.flush();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
			try {
				rtnCd = "9999";
				stream.write(rtnCd.getBytes());
				stream.flush();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				stream.close();
				socket.close(); // 반드시 종료합니다.
				logger.debug("소켓 연결해제");
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		
//		long endTime = System.currentTimeMillis();
//		long estimatedTime = endTime - startTime;
		
//		logger.error("TCP 응답결과=" + rtnMsg);
//		logger.error("startTime=" + startTime);
//		logger.error("endTime=" + endTime);
//		logger.error("소요시간=" + estimatedTime);
	}
	
}
