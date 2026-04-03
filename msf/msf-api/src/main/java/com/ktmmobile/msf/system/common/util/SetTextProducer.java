package com.ktmmobile.msf.system.common.util;

import nl.captcha.text.producer.TextProducer;

public class SetTextProducer implements TextProducer {

	private final String _srcStr;

    public SetTextProducer(String answer) {    
        _srcStr = answer; 
   }

    

    //@Override
   public String getText() {        
        return _srcStr;
   }


}
