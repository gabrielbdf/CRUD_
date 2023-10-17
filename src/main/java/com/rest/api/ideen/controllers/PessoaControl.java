package com.rest.api.ideen.controllers;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rest.api.ideen.entity.Pessoas;
import com.rest.api.ideen.repository.PessoaRepository;
import com.rest.api.ideen.util.ValidaCpf;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/pessoas")
@AllArgsConstructor
public class PessoaControl {

    PessoaRepository repository;

    @GetMapping
    public ResponseEntity<Page<Pessoas>> getAllPessoa(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortOrder
            ) {

        Sort.Direction sortDirection = sortOrder.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortField));
        Page<Pessoas> pessoas = repository.findAll(pageable);

        if (pessoas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(pessoas);
    }

    @GetMapping(path = "outra")
    public ResponseEntity<Page<Pessoas>> getAllPessoa(Pageable page) {

        Page<Pessoas> pessoas = repository.findAll(page);

        if (pessoas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(pessoas);
    }

    // @GetMapping
    // public ResponseEntity<Page<Pessoas>> getAllPessoa(@RequestParam(defaultValue
    // = "0") int page,
    // @RequestParam(defaultValue = "10") int size,
    // @RequestParam(defaultValue = "id") String sortField,
    // @RequestParam(defaultValue = "asc") String sortOrder) {
    //
    // Pageable pageable = PageRequest.of(page, size);
    // Page<Pessoas> pessoas = repository.findAll(pageable);
    //
    // if (pessoas.isEmpty()) {
    // return ResponseEntity.noContent().build();
    // }
    //
    // return ResponseEntity.ok(pessoas);
    //
    //
    // }
    @GetMapping("/{id}")
    public ResponseEntity<Pessoas> getPessoasById(@PathVariable Long id) {
        Optional<Pessoas> pessoa = repository.findById(id);
        if (pessoa.isPresent()) {
            return ResponseEntity.ok().body(pessoa.get());
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping
    public ResponseEntity<String> savePessoas(@RequestBody @Valid Pessoas pessoa) {
        Optional<Pessoas> person = repository.findByCpf(pessoa.getCpf());
        if (person.isPresent()) {
            return ResponseEntity.badRequest().body("CPF already exist!");
        } else {
            if (ValidaCpf.validarCPF(pessoa.getCpf())) {
                this.repository.save(pessoa);
                return ResponseEntity.ok().body("New Record saved");
            }
            return ResponseEntity.badRequest().body("Invalid CPF!");
        }
    }

    @PutMapping("/{id}")
    public Pessoas atualizaPessoas(@PathVariable Long id, @RequestBody Pessoas pessoas) {
        Pessoas p = repository.findById(id).orElse(null);
        if (pessoas.getNome() != null) {
            p.setNome(pessoas.getNome());
        }
        if (pessoas.getIdade() != 0) {
            p.setIdade(pessoas.getIdade());
        }

        if (pessoas.getCpf() != null) {
            p.setCpf(pessoas.getCpf());
        }

        if (pessoas.getNacionalidade() != null) {
            p.setNacionalidade(pessoas.getNacionalidade());
        }
        return repository.save(p);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletaPessoas(@PathVariable Long id) {
        Optional<Pessoas> pessoa = repository.findById(id);

        if (pessoa.isPresent()) {
            repository.deleteById(id);
            return ResponseEntity.ok("Objeto deletado com sucesso");
        } else {
            throw new EntityNotFoundException("Objeto não encontrado");
        }

    }
}
