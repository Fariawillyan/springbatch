package br.com.teste.cargamaster.domain.motor.PreJob.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ExecutarPreJob {
    @NotNull(message = "Número EMS não pode ser nulo")
    private Integer emsNumero;

    @NotNull(message = "ID da carga não pode ser nulo")
    private Integer idCarga;
}
