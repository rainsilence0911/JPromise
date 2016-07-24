package com.promise.test;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.promise.core.DefaultPromise;
import com.promise.core.Promise;
import com.promise.core.Promises;

public class Main {

	private static Random random = new Random();
	
	public static Promise doSomething(ExecutorService executor, Object result) {
		
		Promise p = new DefaultPromise();
		
		executor.execute(()->{
			
			try {
				Thread.sleep(random.nextInt(1000));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			p.resolve("result-" + result);
		});
		
		return p;
	}
	
	public static void main(String[] args) {
		
		ExecutorService executor = Executors.newCachedThreadPool();
		
		int count = 100;
		
		Promise[] promises = new Promise[count];
		
		for (int i = 0; i < count; i++) {
			promises[i] = doSomething(executor, i);
		}

		Promises.when(promises).then((results)->{
			Object[] rs = (Object[]) results;
			
			for (int i = 0; i < rs.length; i++) {
				System.out.println(rs[i]);
			}
			
			executor.shutdown();
		});
		
	}

}
