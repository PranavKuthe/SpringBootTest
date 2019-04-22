package com.learn.boot.scheduler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.learn.boot.ProjectDemo.model.UserTransactionData;
import com.learn.boot.ProjectDemo.repository.UserTransactionDataRepository;

@Component
public class UserAppScheduler {
	
	@Autowired
	UserTransactionDataRepository repo;
	
	//@Scheduled(cron = "0 0/5 * * * ?")
	//@Scheduled(fixedRate = 900000, initialDelay = 300)
    public void refresh(){
		
		System.out.println("=========SCHEDULER INVOKED==================");
		List<UserTransactionData>list = repo.findAll();
		System.out.println("list size:"+list.size());
		System.out.println("==========SCHEDULER RUN COMPLETED=================");
		
	}

}
