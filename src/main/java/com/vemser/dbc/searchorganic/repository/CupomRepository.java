package com.vemser.dbc.searchorganic.repository;

import com.vemser.dbc.searchorganic.model.Cupom;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CupomRepository extends JpaRepository<Cupom, Integer> {
    @Query(value = "SELECT * FROM CUPOM p WHERE p.id_empresa = :idEmpresa", nativeQuery = true)
    List<Cupom> findAllByIdEmpresa(@Param("idEmpresa") Integer idEmpresa);
}
