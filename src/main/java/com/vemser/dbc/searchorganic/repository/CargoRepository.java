package com.vemser.dbc.searchorganic.repository;

import com.vemser.dbc.searchorganic.model.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CargoRepository extends JpaRepository<Cargo, Integer> {
    Cargo findByNome(String nome);
}
