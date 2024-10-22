package br.com.teste.cargamaster.app.motor.preJob.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CargaCampo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carga_campo")
    private int idCargaCampo;
    private int idCarga;
    private int idCampo;
    private int ordem;
    private Integer posicaoInicial;
    private Integer posicaoFinal;
}