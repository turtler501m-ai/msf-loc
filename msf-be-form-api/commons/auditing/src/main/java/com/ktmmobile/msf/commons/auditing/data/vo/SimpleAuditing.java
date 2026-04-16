package com.ktmmobile.msf.commons.auditing.data.vo;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SimpleAuditing {

    private LocalDateTime registeredDate;
    private String registeredIp;
    private LocalDateTime modifiedDate;
    private String modifiedIp;
}
