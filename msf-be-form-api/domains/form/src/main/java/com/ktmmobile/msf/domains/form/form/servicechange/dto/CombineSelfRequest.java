package com.ktmmobile.msf.domains.form.form.servicechange.dto;


import lombok.Data;

@Data
public class CombineSelfRequest {

    private Long requestKey;
    private String custId; //	고객번호
    private String ncn; //	사용자 서비스계약번호
    private String ctn; //	사용자 전화번호
    private String userBirthDate;
    private String userGender;

}
