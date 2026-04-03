package com.ktmmobile.msf.system.common.dto;

import java.io.Serializable;

public class AppInfoConvertDto implements Serializable  {

    private static final long serialVersionUID = 1L;


    public String cstmr_foreigner_pn;
    public String cstmr_foreigner_rrn;
    public String cstmr_native_rrn;
    public String others_payment_rrn;
    public String minor_agent_rrn;
    public String entrust_res_rrn;
    public String name_change_rrn;
    public String req_account_number;
    public String req_account_rrn;
    public String req_card_no;

    public String getCstmr_foreigner_pn() {
        return cstmr_foreigner_pn;
    }
    public void setCstmr_foreigner_pn(String cstmr_foreigner_pn) {
        this.cstmr_foreigner_pn = cstmr_foreigner_pn;
    }
    public String getCstmr_foreigner_rrn() {
        return cstmr_foreigner_rrn;
    }
    public void setCstmr_foreigner_rrn(String cstmr_foreigner_rrn) {
        this.cstmr_foreigner_rrn = cstmr_foreigner_rrn;
    }
    public String getCstmr_native_rrn() {
        return cstmr_native_rrn;
    }
    public void setCstmr_native_rrn(String cstmr_native_rrn) {
        this.cstmr_native_rrn = cstmr_native_rrn;
    }
    public String getOthers_payment_rrn() {
        return others_payment_rrn;
    }
    public void setOthers_payment_rrn(String others_payment_rrn) {
        this.others_payment_rrn = others_payment_rrn;
    }
    public String getMinor_agent_rrn() {
        return minor_agent_rrn;
    }
    public void setMinor_agent_rrn(String minor_agent_rrn) {
        this.minor_agent_rrn = minor_agent_rrn;
    }
    public String getEntrust_res_rrn() {
        return entrust_res_rrn;
    }
    public void setEntrust_res_rrn(String entrust_res_rrn) {
        this.entrust_res_rrn = entrust_res_rrn;
    }
    public String getName_change_rrn() {
        return name_change_rrn;
    }
    public void setName_change_rrn(String name_change_rrn) {
        this.name_change_rrn = name_change_rrn;
    }
    public String getReq_account_number() {
        return req_account_number;
    }
    public void setReq_account_number(String req_account_number) {
        this.req_account_number = req_account_number;
    }
    public String getReq_account_rrn() {
        return req_account_rrn;
    }
    public void setReq_account_rrn(String req_account_rrn) {
        this.req_account_rrn = req_account_rrn;
    }
    public String getReq_card_no() {
        return req_card_no;
    }
    public void setReq_card_no(String req_card_no) {
        this.req_card_no = req_card_no;
    }
}
