package com.vemser.dbc.searchorganic.repository;

import com.vemser.dbc.searchorganic.model.Relatorio;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelatorioMongoRepository<T> extends MongoRepository<Relatorio<T>, String> {


}