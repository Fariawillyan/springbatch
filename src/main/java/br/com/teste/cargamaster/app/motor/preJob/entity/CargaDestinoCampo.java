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
public class CargaDestinoCampo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carga_destino_campo")
    private int idCargaDestinoCampo;
    private int idCargaDestino;
    private int idCargaCampo;
}