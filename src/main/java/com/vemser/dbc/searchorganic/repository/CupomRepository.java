package com.vemser.dbc.searchorganic.repository;

import com.vemser.dbc.searchorganic.model.Cupom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CupomRepository extends JpaRepository<Cupom, Integer> {

}
