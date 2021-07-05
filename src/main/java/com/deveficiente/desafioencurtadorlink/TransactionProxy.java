package com.deveficiente.desafioencurtadorlink;

import javax.transaction.Transactional;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class TransactionProxy {

	
	@Transactional
	public void executeInTransaction(Runnable runnable) {
		runnable.run();
	}
	
	@Transactional
	@Async
	public void executeAsyncInTransaction(Runnable runnable) {
		runnable.run();
	}
}
