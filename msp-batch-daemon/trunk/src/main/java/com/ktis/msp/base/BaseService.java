package com.ktis.msp.base;

import javax.annotation.Resource;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class BaseService extends BaseObject {

	//
	// //---------------------------------
	// // transaction
	// //---------------------------------
	//
	@Resource(name = "txManager")
	public DataSourceTransactionManager txManager;
	//
	private DefaultTransactionDefinition mTxDefinition = null;
	private TransactionStatus mTxStatus = null;

	protected void startTx() {
		
		mTxDefinition = new DefaultTransactionDefinition();
		mTxDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		
		mTxStatus = txManager.getTransaction(mTxDefinition);
	}

	protected void commit() {
		
		try {
			txManager.commit(mTxStatus);
		} catch (org.springframework.transaction.IllegalTransactionStateException e) {
			return;
		}
	}

	protected void rollback() {
		
		try {
			txManager.rollback(mTxStatus);
		} catch (org.springframework.transaction.IllegalTransactionStateException e) {
			return;
		}
	}

}
