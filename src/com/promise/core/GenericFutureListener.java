package com.promise.core;

@FunctionalInterface
public interface GenericFutureListener {
	void operationComplete(Object result);
}