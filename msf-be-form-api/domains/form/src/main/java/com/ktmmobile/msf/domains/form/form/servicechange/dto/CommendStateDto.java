package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class CommendStateDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 최초 개통일자 yyyyMM    */
    private String  lstComActvDate ;

    /** 구매유형 단말
     * 구매:MM
     * USIM(유심)단독 구매:UU
     * */
    private String  reqBuyType ;

    /** 추천 아이디     * */
    private String  commendId ;

    private int  sumCount ;

    /** 가입계약번호 */
    private String contractNum ;

}
