package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PopupEditorDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String popupSeq;
    private String device;
    private String popupSbst;

}
