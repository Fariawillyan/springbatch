package br.com.teste.cargamaster.domain.motor.PreJob.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    private String email;
    private String dataNascimento;
    private int idade;
}
