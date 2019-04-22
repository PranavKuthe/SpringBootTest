package com.learn.boot.ProjectDemo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.learn.boot.ProjectDemo.model.UserTransactionData;

@Repository
public interface UserTransactionDataRepository extends MongoRepository<UserTransactionData, Long> {
	

}
