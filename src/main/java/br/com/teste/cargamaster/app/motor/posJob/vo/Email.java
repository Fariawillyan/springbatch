package br.com.teste.cargamaster.app.motor.posJob.vo;

import lombok.Data;

@Data
public class Email{
    private String to;
    private String subject;
    private String body;
}