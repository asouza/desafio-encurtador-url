package com.deveficiente.desafioencurtadorlink;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.stereotype.Component;

@Component
public class TransactionProxy {

	
	@Transactional
	public void executeInTransaction(Runnable runnable) {
		runnable.run();
	}
}
