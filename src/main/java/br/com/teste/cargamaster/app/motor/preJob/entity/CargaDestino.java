package br.com.teste.cargamaster.app.motor.preJob.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "TB_CARGA_DESTINO")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CargaDestino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carga_destino")
    private int idCargaDestino;
    private int idCarga;
    private String descricao;
    private String tabelaDestino;
    private String sqlInsert;
    private String sqlUpdate;
    private String sqlDelete;
    private int idTipoConexao;
    private String dbHost;
    private String dbPorta;
    private String dbUsuario;
    private String dbSenha;
    private String dbInstancia;
    private String dbBanco;
    private String identificadorTabela;
}