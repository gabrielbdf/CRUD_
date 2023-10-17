package com.rest.api.ideen;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pessoas {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private int idade;
    private String cpf;
    private String nascionalidade;

    public Long getId() {
        return id;
    }
    public String getNome() {
       return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public int getIdade() {
        return idade;
    }
    public void setIdade(int idade) {
        this.idade = idade;
    }
    public String getCpf() {
        return cpf;
   }
   public void setCpf(String cpf) {
       this.cpf = cpf;
   }
   public String getNascionalidade() {
        return nascionalidade;
    }
    public void setNascionalidade(String nascionalidade) {
        this.nascionalidade = nascionalidade;
    }
}
