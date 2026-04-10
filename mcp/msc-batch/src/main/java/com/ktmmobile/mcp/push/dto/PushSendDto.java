package com.ktmmobile.mcp.push.dto;

import java.io.Serializable;
import java.util.List;

public class PushSendDto implements Serializable {
	

	private static final long serialVersionUID = 1L;
	
	private String pushSndProcSno;					// 푸시 발송 처리 일련번호
	private List<String> pushSndProcSnos;			// 일괄 푸시 발송 처리 일련번호
	public String getPushSndProcSno() {
		return pushSndProcSno;
	}
	public void setPushSndProcSno(String pushSndProcSno) {
		this.pushSndProcSno = pushSndProcSno;
	}
	public List<String> getPushSndProcSnos() {
		return pushSndProcSnos;
	}
	public void setPushSndProcSnos(List<String> pushSndProcSnos) {
		this.pushSndProcSnos = pushSndProcSnos;
	}
	
	
}
