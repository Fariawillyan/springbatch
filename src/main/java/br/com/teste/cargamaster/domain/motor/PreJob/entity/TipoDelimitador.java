package br.com.teste.cargamaster.domain.motor.PreJob.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TipoDelimitador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_delimitador")
    private int idTipoDelimitador;
    private String descricao;
    private String delimitador;
    private boolean usaPosicionamento;
}