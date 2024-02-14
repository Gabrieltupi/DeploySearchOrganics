package com.vemser.dbc.searchorganic.repository;

import com.vemser.dbc.searchorganic.model.Cupom;
import feign.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CupomRepository extends JpaRepository<Cupom, Integer> {
    @Query(value = "SELECT * FROM CUPOM p WHERE p.id_empresa = :idEmpresa", nativeQuery = true)
    Page<Cupom> findAllByIdEmpresa(@Param("idEmpresa") Integer idEmpresa, Pageable pageable);

    @Query(value = "SELECT COUNT(*) FROM USUARIO_CARGO uc2 \n" +
            "WHERE uc2.ID_USUARIO = :userId AND uc2.ID_CARGO = 1", nativeQuery = true)
    Integer existsAdminCargoByUserId(@Param("userId") Integer userId);

    @Query(value = "SELECT EC.ID_CUPOM " +
            "FROM EMPRESA E " +
            "LEFT JOIN CUPOM EC ON E.ID_EMPRESA = EC.ID_EMPRESA " +
            "WHERE E.ID_USUARIO = :userId " +
            "AND EC.ATIVO = 'S'", nativeQuery = true)
    Optional<Integer> findCupomIdByUserId(@Param("userId") Integer userId);


}
