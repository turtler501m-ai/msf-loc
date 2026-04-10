package com.ktis.mcpif.nstep;

/**
 * @author sungsumoon
 *
 */
public class NstepQueryVo {
	
	public String res_no;
	public String cstmr_native_rrn;
	public String cstmr_private_number;
	public String cstmr_juridical_number;
	public String cstmr_juridical_rrn;
	public String cstmr_foreigner_rrn; 
	public String cstmr_type;
	public String soc_code;
	public String model_id; 
	public String oper_type;
	public String cstmr_name; 
	public String cstmr_tel_fn;
	public String cstmr_tel_mn; 
	public String cstmr_tel_rn;
	public String cstmr_mobile_fn;
	public String cstmr_mobile_mn;
	public String cstmr_mobile_rn;
	public String cstmr_mail;
	public String cstmr_bill_send_code;
	public String cstmr_addr;
	public String cstmr_addr_dtl;
	public String cstmr_post;
	public String req_pay_type;
	public String req_bank;
	public String req_account_number;
	public String req_account_name;
	public String req_card_no;
	public String req_card_yy;
	public String req_card_mm;
	public String join_price_type;
	public String usim_price_type;
	public String req_want_number;
	public String req_want_number2;
	public String move_company;
	public String move_mobile_fn;
	public String move_mobile_mn;
	public String move_mobile_rn;
	public String move_auth_type;
	public String move_auth_number;
	public String req_phone_sn;
	public String req_usim_sn;
	public String agent_code;
	public String req_in_day;
	public String clause_pri_collect_flag;
	public String clause_pri_offer_flag;
	public String clause_pri_ad_flag;
	public String clause_pri_trust_flag;
	public String minor_agent_rrn;
	public String minor_agent_name;
	public String minor_agent_relation;
	public String minor_agent_tel_fn;
	public String minor_agent_tel_mn;
	public String minor_agent_tel_rn;
	public String online_auth_info;
	public String model_discount1;
	public String model_discount2;
	public String cstmr_juridical_cname;
	public String spc_code;  
	public  String cntpnt_shop_id;
	public String req_card_company;
	public String service_type;
	public String req_buy_type;
	public String cstmr_mail_receive_flag;
	public String model_monthly;
	public String move_refund_agree_flag;
	public String req_card_name;
	public String req_account_rrn;
	public String req_card_rrn;
	public String engg_mnth_cnt;
	public String cstmr_foreigner_dod;
	
	// 판매점아이디 추가(20180104)
	public String salorg_id;
	
	// 2018-10-05, 서비스계약번호 추가 ( 기기변경시 사용 )
	public String svc_cntr_no;
	
	//2022-08-09 유심종류추가(ESIM여부사용)
	public String usim_kinds_cd;

	//2024-03-11 본인인증값
	public String self_cstmr_ci;
	public String online_athn_div_cd;
	
	public String getCstmr_foreigner_dod() {
		return cstmr_foreigner_dod;
	}
	public void setCstmr_foreigner_dod(String cstmr_foreigner_dod) {
		this.cstmr_foreigner_dod = cstmr_foreigner_dod;
	}
	public String getEngg_mnth_cnt() {
		return engg_mnth_cnt;
	}
	public void setEngg_mnth_cnt(String engg_mnth_cnt) {
		this.engg_mnth_cnt = engg_mnth_cnt;
	}
	public String getReq_account_rrn() {
		return req_account_rrn;
	}
	public void setReq_account_rrn(String req_account_rrn) {
		this.req_account_rrn = req_account_rrn;
	}
	public String getReq_card_rrn() {
		return req_card_rrn;
	}
	public void setReq_card_rrn(String req_card_rrn) {
		this.req_card_rrn = req_card_rrn;
	}
	public String getReq_card_name() {
		return req_card_name;
	}
	public void setReq_card_name(String req_card_name) {
		this.req_card_name = req_card_name;
	}
	public String getMove_refund_agree_flag() {
		return move_refund_agree_flag;
	}
	public void setMove_refund_agree_flag(String move_refund_agree_flag) {
		this.move_refund_agree_flag = move_refund_agree_flag;
	}
	public String getModel_monthly() {
		return model_monthly;
	}
	public void setModel_monthly(String model_monthly) {
		this.model_monthly = model_monthly;
	}
	public String getCstmr_mail_receive_flag() {
		return cstmr_mail_receive_flag;
	}
	public void setCstmr_mail_receive_flag(String cstmr_mail_receive_flag) {
		this.cstmr_mail_receive_flag = cstmr_mail_receive_flag;
	}
	public String getReq_buy_type() {
		return req_buy_type;
	}
	public void setReq_buy_type(String req_buy_type) {
		this.req_buy_type = req_buy_type;
	}
	public String getService_type() {
		return service_type;
	}
	public void setService_type(String service_type) {
		this.service_type = service_type;
	}
	public String getReq_card_company() {
		return req_card_company;
	}
	public void setReq_card_company(String req_card_company) {
		this.req_card_company = req_card_company;
	}
	
	public String getCntpnt_shop_id() {
		return cntpnt_shop_id;
	}
	public void setCntpnt_shop_id(String cntpnt_shop_id) {
		this.cntpnt_shop_id = cntpnt_shop_id;
	}
	public String getSpc_code() {
		return spc_code;
	}
	public void setSpc_code(String spc_code) {
		this.spc_code = spc_code;
	}
	public String getCstmr_juridical_cname() {
		return cstmr_juridical_cname;
	}
	public void setCstmr_juridical_cname(String cstmr_juridical_cname) {
		this.cstmr_juridical_cname = cstmr_juridical_cname;
	}
	public String getRes_no() {
		return res_no;
	}
	public void setRes_no(String res_no) {
		this.res_no = res_no;
	}
	public String getCstmr_native_rrn() {
		return cstmr_native_rrn;
	}
	public void setCstmr_native_rrn(String cstmr_native_rrn) {
		this.cstmr_native_rrn = cstmr_native_rrn;
	}

	public String getCstmr_private_number() {
		return cstmr_private_number;
	}
	public void setCstmr_private_number(String cstmr_private_number) {
		this.cstmr_private_number = cstmr_private_number;
	}
	public String getCstmr_juridical_number() {
		return cstmr_juridical_number;
	}
	public void setCstmr_juridical_number(String cstmr_juridical_number) {
		this.cstmr_juridical_number = cstmr_juridical_number;
	}
	public String getCstmr_juridical_rrn() {
		return cstmr_juridical_rrn;
	}
	public void setCstmr_juridical_rrn(String cstmr_juridical_rrn) {
		this.cstmr_juridical_rrn = cstmr_juridical_rrn;
	}
	public String getCstmr_foreigner_rrn() {
		return cstmr_foreigner_rrn;
	}
	public void setCstmr_foreigner_rrn(String cstmr_foreigner_rrn) {
		this.cstmr_foreigner_rrn = cstmr_foreigner_rrn;
	}
	public String getCstmr_type() {
		return cstmr_type;
	}
	public void setCstmr_type(String cstmr_type) {
		this.cstmr_type = cstmr_type;
	}
	public String getSoc_code() {
		return soc_code;
	}
	public void setSoc_code(String soc_code) {
		this.soc_code = soc_code;
	}
	public String getModel_id() {
		return model_id;
	}
	public void setModel_id(String model_id) {
		this.model_id = model_id;
	}
	public String getOper_type() {
		return oper_type;
	}
	public void setOper_type(String oper_type) {
		this.oper_type = oper_type;
	}
	public String getCstmr_name() {
		return cstmr_name;
	}
	public void setCstmr_name(String cstmr_name) {
		this.cstmr_name = cstmr_name;
	}
	public String getCstmr_tel_fn() {
		return cstmr_tel_fn;
	}
	public void setCstmr_tel_fn(String cstmr_tel_fn) {
		this.cstmr_tel_fn = cstmr_tel_fn;
	}
	public String getCstmr_tel_mn() {
		return cstmr_tel_mn;
	}
	public void setCstmr_tel_mn(String cstmr_tel_mn) {
		this.cstmr_tel_mn = cstmr_tel_mn;
	}
	public String getCstmr_tel_rn() {
		return cstmr_tel_rn;
	}
	public void setCstmr_tel_rn(String cstmr_tel_rn) {
		this.cstmr_tel_rn = cstmr_tel_rn;
	}
	public String getCstmr_mobile_fn() {
		return cstmr_mobile_fn;
	}
	public void setCstmr_mobile_fn(String cstmr_mobile_fn) {
		this.cstmr_mobile_fn = cstmr_mobile_fn;
	}
	public String getCstmr_mobile_mn() {
		return cstmr_mobile_mn;
	}
	public void setCstmr_mobile_mn(String cstmr_mobile_mn) {
		this.cstmr_mobile_mn = cstmr_mobile_mn;
	}
	public String getCstmr_mobile_rn() {
		return cstmr_mobile_rn;
	}
	public void setCstmr_mobile_rn(String cstmr_mobile_rn) {
		this.cstmr_mobile_rn = cstmr_mobile_rn;
	}
	public String getCstmr_mail() {
		return cstmr_mail;
	}
	public void setCstmr_mail(String cstmr_mail) {
		this.cstmr_mail = cstmr_mail;
	}
	public String getCstmr_bill_send_code() {
		return cstmr_bill_send_code;
	}
	public void setCstmr_bill_send_code(String cstmr_bill_send_code) {
		this.cstmr_bill_send_code = cstmr_bill_send_code;
	}
	public String getCstmr_addr() {
		return cstmr_addr;
	}
	public void setCstmr_addr(String cstmr_addr) {
		this.cstmr_addr = cstmr_addr;
	}
	public String getCstmr_addr_dtl() {
		return cstmr_addr_dtl;
	}
	public void setCstmr_addr_dtl(String cstmr_addr_dtl) {
		this.cstmr_addr_dtl = cstmr_addr_dtl;
	}
	public String getCstmr_post() {
		return cstmr_post;
	}
	public void setCstmr_post(String cstmr_post) {
		this.cstmr_post = cstmr_post;
	}
	public String getReq_pay_type() {
		return req_pay_type;
	}
	public void setReq_pay_type(String req_pay_type) {
		this.req_pay_type = req_pay_type;
	}
	public String getReq_bank() {
		return req_bank;
	}
	public void setReq_bank(String req_bank) {
		this.req_bank = req_bank;
	}
	public String getReq_account_number() {
		return req_account_number;
	}
	public void setReq_account_number(String req_account_number) {
		this.req_account_number = req_account_number;
	}
	public String getReq_account_name() {
		return req_account_name;
	}
	public void setReq_account_name(String req_account_name) {
		this.req_account_name = req_account_name;
	}
	public String getReq_card_no() {
		return req_card_no;
	}
	public void setReq_card_no(String req_card_no) {
		this.req_card_no = req_card_no;
	}
	public String getReq_card_yy() {
		return req_card_yy;
	}
	public void setReq_card_yy(String req_card_yy) {
		this.req_card_yy = req_card_yy;
	}
	public String getReq_card_mm() {
		return req_card_mm;
	}
	public void setReq_card_mm(String req_card_mm) {
		this.req_card_mm = req_card_mm;
	}
	public String getJoin_price_type() {
		return join_price_type;
	}
	public void setJoin_price_type(String join_price_type) {
		this.join_price_type = join_price_type;
	}
	public String getUsim_price_type() {
		return usim_price_type;
	}
	public void setUsim_price_type(String usim_price_typ) {
		this.usim_price_type = usim_price_typ;
	}
	public String getReq_want_number() {
		return req_want_number;
	}
	public void setReq_want_number(String req_want_number) {
		this.req_want_number = req_want_number;
	}
	public String getReq_want_number2() {
		return req_want_number2;
	}
	public void setReq_want_number2(String req_want_number2) {
		this.req_want_number2 = req_want_number2;
	}
	public String getMove_company() {
		return move_company;
	}
	public void setMove_company(String move_company) {
		this.move_company = move_company;
	}
	public String getMove_mobile_fn() {
		return move_mobile_fn;
	}
	public void setMove_mobile_fn(String move_mobile_fn) {
		this.move_mobile_fn = move_mobile_fn;
	}
	public String getMove_mobile_mn() {
		return move_mobile_mn;
	}
	public void setMove_mobile_mn(String move_mobile_mn) {
		this.move_mobile_mn = move_mobile_mn;
	}
	public String getMove_mobile_rn() {
		return move_mobile_rn;
	}
	public void setMove_mobile_rn(String move_mobile_rn) {
		this.move_mobile_rn = move_mobile_rn;
	}
	public String getMove_auth_type() {
		return move_auth_type;
	}
	public void setMove_auth_type(String move_auth_type) {
		this.move_auth_type = move_auth_type;
	}
	public String getMove_auth_number() {
		return move_auth_number;
	}
	public void setMove_auth_number(String move_auth_number) {
		this.move_auth_number = move_auth_number;
	}
	public String getReq_phone_sn() {
		return req_phone_sn;
	}
	public void setReq_phone_sn(String req_phone_sn) {
		this.req_phone_sn = req_phone_sn;
	}
	public String getReq_usim_sn() {
		return req_usim_sn;
	}
	public void setReq_usim_sn(String req_usim_sn) {
		this.req_usim_sn = req_usim_sn;
	}
	public String getAgent_code() {
		return agent_code;
	}
	public void setAgent_code(String agent_code) {
		this.agent_code = agent_code;
	}
	public String getReq_in_day() {
		return req_in_day;
	}
	public void setReq_in_day(String req_in_day) {
		this.req_in_day = req_in_day;
	}
	public String getClause_pri_collect_flag() {
		return clause_pri_collect_flag;
	}
	public void setClause_pri_collect_flag(String clause_pri_collect_flag) {
		this.clause_pri_collect_flag = clause_pri_collect_flag;
	}
	public String getClause_pri_offer_flag() {
		return clause_pri_offer_flag;
	}
	public void setClause_pri_offer_flag(String clause_pri_offer_flag) {
		this.clause_pri_offer_flag = clause_pri_offer_flag;
	}
	public String getClause_pri_ad_flag() {
		return clause_pri_ad_flag;
	}
	public void setClause_pri_ad_flag(String clause_pri_ad_flag) {
		this.clause_pri_ad_flag = clause_pri_ad_flag;
	}
	public String getClause_pri_trust_flag() {
		return clause_pri_trust_flag;
	}
	public void setClause_pri_trust_flag(String clause_pri_trust_flag) {
		this.clause_pri_trust_flag = clause_pri_trust_flag;
	}
	public String getMinor_agent_rrn() {
		return minor_agent_rrn;
	}
	public void setMinor_agent_rrn(String minor_agent_rrn) {
		this.minor_agent_rrn = minor_agent_rrn;
	}
	public String getMinor_agent_name() {
		return minor_agent_name;
	}
	public void setMinor_agent_name(String minor_agent_name) {
		this.minor_agent_name = minor_agent_name;
	}
	public String getMinor_agent_relation() {
		return minor_agent_relation;
	}
	public void setMinor_agent_relation(String minor_agent_relation) {
		this.minor_agent_relation = minor_agent_relation;
	}
	public String getMinor_agent_tel_fn() {
		return minor_agent_tel_fn;
	}
	public void setMinor_agent_tel_fn(String minor_agent_tel_fn) {
		this.minor_agent_tel_fn = minor_agent_tel_fn;
	}
	public String getMinor_agent_tel_mn() {
		return minor_agent_tel_mn;
	}
	public void setMinor_agent_tel_mn(String minor_agent_tel_mn) {
		this.minor_agent_tel_mn = minor_agent_tel_mn;
	}
	public String getMinor_agent_tel_rn() {
		return minor_agent_tel_rn;
	}
	public void setMinor_agent_tel_rn(String minor_agent_tel_rn) {
		this.minor_agent_tel_rn = minor_agent_tel_rn;
	}
	public String getOnline_auth_info() {
		return online_auth_info;
	}
	public void setOnline_auth_info(String online_auth_info) {
		this.online_auth_info = online_auth_info;
	}
	public String getModel_discount1() {
		return model_discount1;
	}
	public void setModel_discount1(String model_discount1) {
		this.model_discount1 = model_discount1;
	}
	public String getModel_discount2() {
		return model_discount2;
	}
	public void setModel_discount2(String model_discount2) {
		this.model_discount2 = model_discount2;
	}
	public String getSalorg_id() {
		return salorg_id;
	}
	public void setSalorg_id(String salorg_id) {
		this.salorg_id = salorg_id;
	}
	public String getSvc_cntr_no() {
		return svc_cntr_no;
	}
	public void setSvc_cntr_no(String svc_cntr_no) {
		this.svc_cntr_no = svc_cntr_no;
	}
	public String getUsim_kinds_cd() {
		return usim_kinds_cd;
	}
	public void setUsim_kinds_cd(String usim_kinds_cd) {
		this.usim_kinds_cd = usim_kinds_cd;
	}
	public String getSelf_cstmr_ci() {
		return self_cstmr_ci;
	}
	public void setSelf_cstmr_ci(String self_cstmr_ci) {
		this.self_cstmr_ci = self_cstmr_ci;
	}
	public String getOnline_athn_div_cd() {
		return online_athn_div_cd;
	}
	public void setOnline_athn_div_cd(String online_athn_div_cd) {
		this.online_athn_div_cd = online_athn_div_cd;
	}
	
}
