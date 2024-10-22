package br.com.teste.cargamaster.app.rest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobExecutionParamsView {

    private BigInteger jobExecutionId;
    private String parameterName;
    private String parameterType;
    private String parameterValue;
    private char identifying;
}
