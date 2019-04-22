package com.learn.boot.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.assertj.core.data.MapEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.learn.boot.ProjectDemo.model.UserTransactionData;
import com.learn.boot.ProjectDemo.repository.UserTransactionDataRepository;
import com.learn.boot.processor.helper.Worker;

@Component
public class DemoProcessor {

	private static final int fetchSize = 5;

	@Autowired
	private UserTransactionDataRepository repository;

	@Autowired
	MongoTemplate template;

	/**
	 * method to initiate processing of txn data
	 *
	 * @param
	 * @return
	 * @throws Exception
	 */
	public void doProcess() throws Exception {

		List<Future<HashMap<Long, Double>>> listOfFuture = null;
		// Query to get records size
		Integer size = repository.findAll().size();
		long minSize, maxSize = 0;

		// Query to get max size of record id
		Query query = new Query();
		query.with(new Sort(Sort.Direction.DESC, "idd"));
		query.limit(1);
		UserTransactionData maxObject = template.findOne(query, UserTransactionData.class);
		maxSize = maxObject.getIdd();

		// Query to get min size of record id
		Query query1 = new Query();
		query1.with(new Sort(Sort.Direction.ASC, "idd"));
		query1.limit(1);
		UserTransactionData minObject = template.findOne(query1, UserTransactionData.class);
		minSize = minObject.getIdd();

		listOfFuture = initiateProcess(size, maxSize, minSize);
		createFinalMap(listOfFuture);
	}

	/**
	 * creates final map of records and there total txn amount per accountnumber
	 *
	 * @param List<Future<HashMap<Long, Double>>> listOfFuture @return @throws
	 */
	private void createFinalMap(List<Future<HashMap<Long, Double>>> listOfFuture) {
		final HashMap<Long, Double> finalMap = new HashMap<>();
		try {
			for (Future<HashMap<Long, Double>> futureObj : listOfFuture) {
				HashMap<Long, Double> map = futureObj.get();

				for (Map.Entry<Long, Double> entry : map.entrySet()) {
					Long accountNumber = entry.getKey();
					Double amount = entry.getValue();

					if (finalMap.containsKey(accountNumber)) {
						double value = finalMap.get(accountNumber);
						value = value + amount;
						finalMap.put(accountNumber, value);
					} else {
						finalMap.put(accountNumber, amount);
					}

				}

			}

			// printing the Final Value
			for (Map.Entry<Long, Double> entry : finalMap.entrySet()) {
				System.out.println("key:" + entry.getKey() + " :value:" + entry.getValue());
			}

		} catch (Exception ex) {
			System.out.println("Error in createFinalMap: " + ex.getMessage());
		}

	}

	/**
	 * distribute load by enabling parallel processing
	 *
	 * @param Integer
	 *            size , size of data
	 * @param long
	 *            maxId , maxId of data
	 * @param long
	 *            minId , minId of data
	 * @return List<Future<HashMap<Long, Double>>> listOfFuture
	 * @throws InterruptedException
	 */
	private List<Future<HashMap<Long, Double>>> initiateProcess(Integer size, long maxId, long minId)
			throws InterruptedException {

		final ExecutorService tpool = Executors.newFixedThreadPool(5);
		List<Future<HashMap<Long, Double>>> list = new ArrayList<Future<HashMap<Long, Double>>>();
		try {

			Long startId = minId;
			Long endId = minId;
			while (true) {
				endId = (startId + fetchSize) - 1;
				if (endId > maxId) {
					endId = maxId;
				}
				Future<HashMap<Long, Double>> future = (Future<HashMap<Long, Double>>) tpool
						.submit(new Worker(startId, endId, template));
				list.add(future);
				startId = endId + 1;
				if (startId > maxId) {
					break;
				}
			}

		} catch (Exception ex) {
			System.out.println("Error in initiate Process :" + ex.getMessage());
		} finally {
			tpool.shutdown();
			tpool.awaitTermination(1, TimeUnit.MINUTES);

		}
		System.out.println("future list size:" + list.size());
		return list;

	}

}
