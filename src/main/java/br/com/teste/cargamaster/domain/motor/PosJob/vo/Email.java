package br.com.teste.cargamaster.domain.motor.PosJob.vo;

import lombok.Data;

@Data
public class Email{
    private String to;
    private String subject;
    private String body;
}