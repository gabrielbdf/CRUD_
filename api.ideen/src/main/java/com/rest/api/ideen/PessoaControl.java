package com.rest.api.ideen;

import com.rest.api.ideen.util.ValidaCpf;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
            @RequestParam(defaultValue = "asc") String sortOrder) {

        Sort.Direction sortDirection = sortOrder.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortField));
        Page<Pessoas> pessoas = repository.findAll(pageable);

        if (pessoas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(pessoas);
    }


    //    @GetMapping
//    public ResponseEntity<Page<Pessoas>> getAllPessoa(@RequestParam(defaultValue = "0") int page,
//                                                      @RequestParam(defaultValue = "10") int size,
//                                                      @RequestParam(defaultValue = "id") String sortField,
//                                                      @RequestParam(defaultValue = "asc") String sortOrder) {
//
//        Pageable pageable = PageRequest.of(page, size);
//        Page<Pessoas> pessoas = repository.findAll(pageable);
//
//        if (pessoas.isEmpty()) {
//            return ResponseEntity.noContent().build();
//        }
//
//        return ResponseEntity.ok(pessoas);
//
//
//    }
    @GetMapping("/{id}")
    public ResponseEntity<Pessoas> getPessoasById(@PathVariable Long id){
         Optional<Pessoas> pessoa = repository.findById( id);
         if (pessoa.isPresent()){
             return ResponseEntity.ok().body(pessoa.get());
         }else{
             return ResponseEntity.notFound().build();
         }

    }



    @PostMapping
    public ResponseEntity<String> savePessoas(@RequestBody @Valid Pessoas pessoa) {
        Optional<Pessoas> person = repository.findByCpf(pessoa.getCpf());
        if (person.isPresent()) {
            return ResponseEntity.badRequest().body("CPF already exist!");
        } else {
            if(ValidaCpf.validarCPF(pessoa.getCpf())) {
                this.repository.save(pessoa);
                return ResponseEntity.ok().body("New Record saved");
            }
            return ResponseEntity.badRequest().body("Invalid CPF!");
        }
    }

    @PutMapping("/{id}")
                public Pessoas atualizaPessoas(@PathVariable Long id, @RequestBody  Pessoas pessoas){
            Pessoas p = repository.findById(id).orElse(null);
            if (pessoas.getNome()!= null) {
                p.setNome(pessoas.getNome());
            }
            if (pessoas.getIdade()!= 0) {
                p.setIdade(pessoas.getIdade());
            }

            if (pessoas.getCpf()!= null){
                    p.setCpf(pessoas.getCpf());
            }

            if (pessoas.getNascionalidade()!= null) {
                p.setNascionalidade(pessoas.getNascionalidade());
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




