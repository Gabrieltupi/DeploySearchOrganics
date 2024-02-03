package com.vemser.dbc.searchorganic.repository;

import com.vemser.dbc.searchorganic.model.Cupom;
import feign.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CupomRepository extends JpaRepository<Cupom, Integer> {
    @Query(value = "SELECT * FROM CUPOM p WHERE p.id_empresa = :idEmpresa", nativeQuery = true)
    Page<Cupom> findAllByIdEmpresa(@Param("idEmpresa") Integer idEmpresa, Pageable pageable);
}
