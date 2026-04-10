package com.ktis.msp.base.login;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import java.io.Serializable;

public class SessionBindingListener implements HttpSessionBindingListener, Serializable {
    private static final long serialVersionUID = 1L;

    @Override
    public void valueBound(HttpSessionBindingEvent event) {
        UniqueLoginManager uniqueLoginManager = UniqueLoginManager.getInstance();
        if( uniqueLoginManager.existsLogin(event.getName()) ) {
            uniqueLoginManager.invalidateLogin(event.getName());
        }
        uniqueLoginManager.putLogin(event.getName(), event.getSession());    // event.getName() = usrId가 loginTable의 key 값

    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        UniqueLoginManager uniqueLoginManager = UniqueLoginManager.getInstance();
        uniqueLoginManager.removeLogin(event.getName());					    // event.getName() = usrId가 loginTable의 key 값
    }
}
