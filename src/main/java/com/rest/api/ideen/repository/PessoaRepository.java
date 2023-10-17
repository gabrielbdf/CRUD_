package com.rest.api.ideen.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.api.ideen.entity.Pessoas;

import java.util.Optional;

public interface PessoaRepository extends JpaRepository<Pessoas, Long> {


    Optional<Pessoas> findByCpf(String cpf);
}