package com.ktis.msp.base.login;

import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

public class UniqueLoginManager {
    public static ConcurrentHashMap<String, HttpSession> loginTable = new ConcurrentHashMap<String, HttpSession>();

    private static final UniqueLoginManager uniqueLoginManager = new UniqueLoginManager(); // 로그인은 항상 사용하기 때문에 최초 시점부터 바로 할당

    public static UniqueLoginManager getInstance() {
        return uniqueLoginManager;
    }

    public boolean existsLogin(String usrId) {
        return loginTable.containsKey(usrId);
    }

    public void invalidateLogin(String usrId) {
        Enumeration<String> e = loginTable.keys();
        while(e.hasMoreElements()) {
            String key = e.nextElement();
            if(key.equals(usrId)) {
                loginTable.get(usrId).invalidate();
            }
        }
    }

    public void putLogin(String usrId, HttpSession session) {
        loginTable.put(usrId, session);
    }

    public void removeLogin(String usrId) {
        loginTable.remove(usrId);
    }
}
