package com.vemser.dbc.searchorganic.repository;

import com.vemser.dbc.searchorganic.model.Log;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends MongoRepository<Log, String> {
}
