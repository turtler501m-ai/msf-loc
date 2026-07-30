package com.ktmmobile.msf.domains.form.common.cache;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.ktmmobile.msf.domains.form.common.service.FCommonSvc;

import static com.ktmmobile.msf.domains.form.common.constants.Constants.FILE_CACHE_DIR;
import static com.ktmmobile.msf.domains.form.common.constants.Constants.FILE_CACHE_NAME;
import static com.ktmmobile.msf.domains.form.common.constants.Constants.SEPARATOR;

@Slf4j
public class DbCacheRefresher implements InitializingBean, Runnable {

    /**
     * 밀리세컨드
     */
    private static final int MILISECOND = 1000;

    /**
     * 인터벌 초
     */
    private static final int DEFAULT_CHECK_INTERVAL = 60;

    @Autowired
    private FCommonSvc fCommonSvc;

    private Thread daemonThread;

    private boolean isAlive;

    private String lastDummyContents;

    /** 파일업로드 경로*/
    @Value("${common.fileupload.base}")
    private String fileuploadBase;


    @Override
    public void run() {
        while (isAlive) {
            waitNext();
            if (isDummyContentsModified()) {
                refresh();
            }
        }
    }

    @Override
    public void afterPropertiesSet() {
        startDemon();
    }

    public void close() {
        isAlive = false;
    }

    public void refresh() {
        fCommonSvc.getCodeCahe();
        lastDummyContents = readDummyContents();
    }

    public void startDemon() {
        isAlive = true;
        lastDummyContents = readDummyContents();
        daemonThread = new Thread(this);
        daemonThread.setDaemon(true);
        daemonThread.start();
    }

    public void waitNext() {
        try {
            Thread.sleep(DEFAULT_CHECK_INTERVAL * MILISECOND);
        } catch (InterruptedException e) {
            log.error("Error InterruptedException");
        }
    }

    private Boolean isDummyContentsModified() {
        Boolean check = false;

        String readDummyContents = readDummyContents();

        if(StringUtils.isNotBlank(readDummyContents) && !readDummyContents.equals(lastDummyContents)) {
            check = true;
        }
        return check;
    }

    private String readDummyContents() {
        BufferedReader reader = null;
        try {
            File timestamp = new File(fileuploadBase + FILE_CACHE_DIR + SEPARATOR + FILE_CACHE_NAME);
            reader = new BufferedReader(new FileReader(timestamp));
            return reader.readLine();

        } catch (Exception e) {
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    log.debug("Can not read cache refresh timestamp", e);
                }
            }
        }
    }
}
