package com.ktis.mcpif.nstep;

public class NstepResponse {

	//response Code
	public String svc_status;
	//response Message
	public String err_msg;
	
	
	public String getSvc_status() {
		return svc_status;
	}
	public void setSvc_status(String svc_status) {
		this.svc_status = svc_status;
	}
	public String getErr_msg() {
		return err_msg;
	}
	public void setErr_msg(String err_msg) {
		this.err_msg = err_msg;
	}
	
}
