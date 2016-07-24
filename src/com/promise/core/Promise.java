package com.promise.core;

public interface Promise {
	
	Promise then(GenericFutureListener listener);
	
	Promise caught(GenericFutureListener listener);
	
	void resolve(Object result);
	
	void reject(Object error);
}
