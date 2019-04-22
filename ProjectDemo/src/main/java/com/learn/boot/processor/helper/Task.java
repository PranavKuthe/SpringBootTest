package com.learn.boot.processor.helper;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

public abstract class Task implements Callable<HashMap<Long, Double>> {

	/**
	 * Sores the taskName for debugging
	 */
	protected String taskName = null;
	protected CountDownLatch latch = null;

	public Task() {
		final int i = new Random().nextInt(1000);
		this.taskName = "Default-" + i;
	}

	public Task(String taskName, CountDownLatch latch) {
		final int i = new Random().nextInt(10000);
		this.taskName = taskName + "-" + i;
		this.latch = latch;
	}

	/* Get the task Name */
	public String getTaskName() {
		return taskName;
		
	}
	
	public abstract HashMap<Long, Double> execute() throws Exception;
	
	

	@Override
	public HashMap<Long, Double> call() throws Exception {
		try{
			return this.execute();
		}catch(Exception ex){
			System.out.println("Error in call method:"+ex.getMessage());
		}
		return null;
	}

	

}
