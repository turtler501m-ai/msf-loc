package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BannAccessTxnDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private int bannAccessSeq;
    private long bannAccessSeqLong;
    private int bannSeq;
    private String accessDate;
    private String accessTime;
    private String platformCd;
    private String bannCtg;
    private int menuSeq;
    private int urlSeq;
    private String reqTrtCd;
    private String userId;
    private String accessIp;

}
