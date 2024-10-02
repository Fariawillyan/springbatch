package br.com.teste.cargamaster.domain.motor.PreJob.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Carga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carga")
    private int idCarga;
    private String descricao;
    private int emsNumero;
    private int idTipoDelimitador;
    private String nomeArquivo;
    private String ftpHost;
    private String ftpPorta;
    private String ftpUsuario;
    private String ftpSenha;
    private String ftpPath;
    private Integer interacaoPosicaoInicial;
    private Integer interacaoPosicaoFinal;
    private boolean usaIdentificadorTabela;
    private Integer identificadorTabelaPosicaoInicial;
    private Integer identificadorTabelaPosicaoFinal;
}