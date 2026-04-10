package com.ktis.msp.base.mvc;

import javax.annotation.Resource;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class BaseService extends BaseObject{
	

	
//	// 조효제 추가 2014.08.14 문의 바람
//    
//	//---------------------------------
//	// transaction
//	//---------------------------------
//
	@Resource(name="txManager")
    public DataSourceTransactionManager txManager;
//	
	private DefaultTransactionDefinition mTxDefinition = null;
	private TransactionStatus mTxStatus = null;
//	private DataSourceTransactionManager txManager = null;

	protected void startTx()
	{
		
		mTxDefinition = new DefaultTransactionDefinition();    
		mTxDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);    

		mTxStatus = txManager.getTransaction(mTxDefinition);
	}
    
    protected void commit(){
    	
    	try{
    		txManager.commit(mTxStatus);
    	}catch ( org.springframework.transaction.IllegalTransactionStateException e)
    	{
    	    return;
    	}    	
    }
    
    protected void rollback(){
    	
    	try{
    		txManager.rollback(mTxStatus);
    	}catch ( org.springframework.transaction.IllegalTransactionStateException e)
    	{
    	    return;
    	}    	
    }

}
