package com.learn.boot.processor.helper;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Criteria;

import com.learn.boot.ProjectDemo.model.UserTransactionData;


public class Worker extends Task {

	
	MongoTemplate template;
	private Long startId;
	private Long endId;

	public Worker(Long startid, Long endId, MongoTemplate template) {
		super("accountTxn", new CountDownLatch(1));
		this.startId = startid;
		this.endId = endId;
		this.template = template;
	}

	@Override
	public HashMap<Long, Double> execute() throws Exception {
		final HashMap<Long, Double> map = new HashMap<>();
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("idd").gte(this.startId).lte(this.endId));
			List<UserTransactionData> txnDataList = template.find(query, UserTransactionData.class);

			for (UserTransactionData userTxnData : txnDataList) {
				Long accountNumber = userTxnData.getAccountNumber();

				if (map.containsKey(accountNumber)) {
					double value = map.get(accountNumber);
					value = value + userTxnData.getAmount();
					map.put(accountNumber, value);
				} else {
					map.put(accountNumber, userTxnData.getAmount());
				}
			}
			return map;

		} catch (Exception ex) {
			System.out.println("Exception in Worker Thread :" + this.getTaskName() + ":Message:" + ex.getMessage());
		}
		this.latch.countDown();
		return null;
	}

}
