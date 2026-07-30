package com.ktmmobile.msf.appboot.template.form.svcChg.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SvcChgController {

    @RequestMapping(value="/test")
    public List<Meta> test() {
        return List.of(new Meta("customerType1","내국인"), new Meta("customerType2","미성년자(19세 미만)"), new Meta("customerType3","외국인(Foeigner)"));
    }


    public record Meta(String value, String label) { }
}
