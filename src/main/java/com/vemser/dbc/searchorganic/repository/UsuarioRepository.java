package com.vemser.dbc.searchorganic.repository;

import com.vemser.dbc.searchorganic.model.Usuario;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByCpf(String cpf);

    Optional<Usuario> findByLogin(String login);

    Optional<Usuario> findByLoginAndSenha(String login, String senha);

    Optional<Usuario> findByLoginAndEmail(String login, String email);

    boolean existsByLogin(String login);

    @Query(value = "SELECT COUNT(*) FROM USUARIO_CARGO uc2 \n" +
            "WHERE uc2.ID_USUARIO = :userId AND uc2.ID_CARGO = 1", nativeQuery = true)
    Integer existsAdminCargoByUserId(@Param("userId") Integer userId);


}
