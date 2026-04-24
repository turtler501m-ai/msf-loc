package com.ktis.msp.batch.manager.common.file;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ktis.msp.base.exception.MvnoServiceException;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public class FileDataListResultHandler implements ResultHandler {
   
   private final Logger LOGGER = LoggerFactory.getLogger(getClass());
   
   private final String fullFileName;

   public FileDataListResultHandler(String fullFileName) throws MvnoServiceException {
      this.fullFileName = fullFileName;
   }

   @Override
   public void handleResult(ResultContext context) {
      EgovMap egovMap = (EgovMap)context.getResultObject();
      
      try {
         addRow(egovMap);
      } catch (MvnoServiceException e) {
         LOGGER.debug(e.getMessage() + " ==> " + e.getCause());
      }
   }
   
   private void addRow(EgovMap map) throws MvnoServiceException {
      // BufferedWriter 와 FileWriter를 조합하여 사용 (속도 향상)
      try {
         BufferedWriter fw = new BufferedWriter(new FileWriter(fullFileName, true));
         
         fw.write(String.valueOf(map.get("arpu")));
         fw.newLine();
         fw.flush();
         
         //객체 닫기
         fw.close(); 
      } catch (IOException e) {
         String[] errParam = new String[1];
         errParam[0] = fullFileName;
         throw new MvnoServiceException("EMSP1001", errParam, e);
      }
   }
}
