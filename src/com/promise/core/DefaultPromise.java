package com.promise.core;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class DefaultPromise implements Promise {

	private Queue<GenericFutureListener> thenQueue = new ConcurrentLinkedQueue<GenericFutureListener>();
	private Queue<GenericFutureListener> catchQueue = new ConcurrentLinkedQueue<GenericFutureListener>();
	
	private boolean isDone = false;
	
	private Object result;
	private Object error;
	
	@Override
	public void resolve(Object result) {
		isDone = true;
		notifyListeners(result, thenQueue);
		this.result = result;
	}

	@Override
	public void reject(Object error) {
		isDone = true;
		notifyListeners(error, catchQueue);
		this.error = error;
	}

	@Override
	public Promise then(GenericFutureListener listener) {
		
		if (isDone() && this.result != null) {
			listener.operationComplete(result);
			return this;
		}
		
		thenQueue.add(listener);
		return this;
	}
	
	@Override
	public Promise caught(GenericFutureListener listener) {
		if (isDone() && this.error != null) {
			listener.operationComplete(error);
			return this;
		}
		
		catchQueue.add(listener);
		return this;
	}

	private void notifyListeners(Object result, Queue<GenericFutureListener> queue) {
		
		while (!queue.isEmpty()) {
			GenericFutureListener listener = queue.poll();
			listener.operationComplete(result);
		}
	}

	public boolean isDone() {
		return isDone;
	}
}
