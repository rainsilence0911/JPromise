package com.promise.core;

import java.util.concurrent.atomic.AtomicInteger;

public class Promises {
	
	public static Promise when(Promise[] promises) {
		return new PromiseWhenStrategy(promises);
	}
	
	private static class PromiseWhenStrategy extends DefaultPromise {

		private Promise[] promises;
		
		private AtomicInteger counter = new AtomicInteger();
		
		private Object[] results;
		
		public PromiseWhenStrategy(Promise[] promises) {
			this.promises = promises;
			
			results = new Object[promises.length];
			
			for (int i = 0; i < promises.length; i++) {
				wrapperPromise(i, promises[i]);
			}
		}
		
		private void wrapperPromise(int index, Promise p) {
			p.then((result)->{
				complete(index, result);
			}).caught((result)->{
				complete(index, result);
			});
		}
		
		private void complete(int index, Object result) {
			int len = counter.incrementAndGet();
			
			results[index] = result;
			
			if (len == promises.length) {
				this.resolve(results);
			}
		}
	}
}
