package com.ktis.msp.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class SFTPUtil {
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(SFTPUtil.class);
	
	private Session session = null;

    private Channel channel = null;

    private ChannelSftp channelSftp = null;
    
    /**
     * 서버와 연결에 필요한 값들을 가져와 초기화 시킴
     *
     * @param host
     *            서버 주소
     * @param userName
     *            접속에 사용될 아이디
     * @param password
     *            비밀번호
     * @param port
     *            포트번호
     */
    public void init(String host, String userName, String password, int port) throws JSchException {
    	
        JSch jsch = new JSch();
        try {
            session = jsch.getSession(userName, host, port);
            session.setPassword(password);

            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            //session.setTimeout(10000);
            session.setConfig(config);
            session.connect();

            channel = session.openChannel("sftp");
            channel.connect();
        } catch (JSchException e) {
//            e.printStackTrace();
        	LOGGER.error("FTP 연결 실패 : host=[" + host + "], port=[" + port + "], userName=[" + userName + "], password=[" + password + "]");
        	throw e;
        }

        channelSftp = (ChannelSftp) channel;

    }    

    /**
     * 하나의 파일을 업로드 한다.
     *
     * @param dir
     *            저장시킬 주소(서버)
     * @param file
     *            저장할 파일
     */
    public void upload(String dir, File file) throws SftpException, FileNotFoundException, IOException {

        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            String homeDir = channelSftp.getHome();
            channelSftp.cd(homeDir);
            channelSftp.cd(dir);
            channelSftp.put(in, file.getName());
        } catch (SftpException e) {
        	// 20250523 취약점 소스 코드 수정
        	LOGGER.error("Connection Exception occurred");
        	LOGGER.error("파일 업로드 실패");
        	throw e;
        } catch (FileNotFoundException e) {
//            e.printStackTrace();
        	LOGGER.error("업로드할 파일이 없습니다");
        	throw e;
        } finally {
            try {
                in.close();
            } catch (IOException e) {
//                e.printStackTrace();
            	LOGGER.error("파일 닫기 실패");
            	throw e;
            }
        }
    }

    /**
     * 하나의 파일을 다운로드 한다.
     *
     * @param dir
     *            저장할 경로(서버)
     * @param downloadFileName
     *            다운로드할 파일
     * @param path
     *            저장될 공간
     */
    public void download(String dir, String downloadFileName, String path) throws SftpException, IOException {
        InputStream in = null;
        FileOutputStream out = null;
        try {
        	String homeDir = channelSftp.getHome();
            channelSftp.cd(homeDir);
            channelSftp.cd(dir);
            in = channelSftp.get(downloadFileName);
        } catch (SftpException e) {
//            e.printStackTrace();
        	LOGGER.error("파일 다운로드 실패");
        	throw e;
        }

        try {
            out = new FileOutputStream(new File(path));
            int i;

            while ((i = in.read()) != -1) {
                out.write(i);
            }
        } catch (IOException e) {
//            e.printStackTrace();
        	LOGGER.error("파일 읽기 실패");
        	throw e;
        } finally {
            try {
                out.close();
                in.close();
            } catch (IOException e) {
//                e.printStackTrace();
            	LOGGER.error("파일 읽기 실패2");
            	throw e;
            }

        }

    }

    /**
     * 하나의 파일을 이동 한다.
     *
     * @param dir
     *            저장할 경로(서버)
     * @param oldpath
     *            이동전 파일경로
     * @param newpath
     *            이동후 파일경로
     */
    public void move(String dir, String oldpath, String newpath) throws SftpException, IOException {

        try {
        	String homeDir = channelSftp.getHome();
            channelSftp.cd(homeDir);
            channelSftp.cd(dir);
            channelSftp.rename(oldpath, newpath);
        } catch (SftpException e) {
//            e.printStackTrace();
        	LOGGER.error("파일 다운로드 실패");
        	throw e;
        }

    }

    /**
     * 서버와의 연결을 끊는다.
     */
    public void disconnection() {
        channelSftp.quit();

    }
}
