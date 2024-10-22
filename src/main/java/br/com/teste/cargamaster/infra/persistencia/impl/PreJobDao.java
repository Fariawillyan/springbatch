package br.com.teste.cargamaster.infra.persistencia.impl;

import br.com.teste.cargamaster.app.motor.preJob.entity.*;
import br.com.teste.cargamaster.app.motor.preJob.vo.ExecutarPreJob;
import br.com.teste.cargamaster.infra.persistencia.IPreJobDao;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class PreJobDao implements IPreJobDao {

    private final JdbcClient jdbcClient;
    public PreJobDao(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public CargaDestinoCampo buscaCargaDestinoCampo(ExecutarPreJob executarPreJob) {
        return jdbcClient
                .sql("""
                        SELECT
                            cdc.id_carga_destino_campo,
                            cdc.id_carga_destino,
                            cdc.id_carga_campo
                        FROM
                            TB_CARGA_DESTINO_CAMPO cdc
                        WHERE cdc.id_carga_destino_campo = :idCargaDestinoCampo
                        
                        """)
                .param("idCargaDestinoCampo", executarPreJob.getIdCarga())
                .query(CargaDestinoCampo.class)
                .single();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public CargaDestino buscaCargaDestino(ExecutarPreJob executarPreJob) {
        return jdbcClient
                .sql("""
                        SELECT
                            cd.id_carga_destino,
                            cd.id_carga,
                            cd.descricao,
                            cd.tabela_destino,
                            cd.sql_insert,
                            cd.sql_update,
                            cd.sql_delete,
                            cd.id_tipo_conexao,
                            cd.db_host,
                            cd.db_porta,
                            cd.db_usuario,
                            cd.db_senha,
                            cd.db_instancia,
                            cd.db_banco,
                            cd.identificador_tabela
                        FROM
                            TB_CARGA_DESTINO cd
                        WHERE cd.id_carga_destino = :idCargaDestino
                        
                        """)
                .param("idCargaDestino", executarPreJob.getIdCarga())
                .query(CargaDestino.class)
                .single();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<CargaCampo> buscaCargaCampo(ExecutarPreJob executarPreJob) {
        return jdbcClient
                .sql(""" 
                        SELECT
                            cc.id_Carga_campo,
                            cc.id_carga,
                            cc.id_campo,
                            cc.ordem,
                            cc.posicao_inicial,
                            cc.posicao_final
                         FROM
                            TB_CARGA_CAMPO cc
                         WHERE cc.id_carga = :idCargaCampo
                         
                         """)
                .param("idCargaCampo", executarPreJob.getIdCarga())
                .query(CargaCampo.class)
                .list();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Carga buscaCarga(ExecutarPreJob executarPreJob) {
        return jdbcClient
                .sql("""
                          SELECT
                              c.id_carga,
                              c.descricao,
                              c.ems_numero,
                              c.id_tipo_delimitador,
                              c.nome_arquivo,
                              c.ftp_host,
                              c.ftp_porta,
                              c.ftp_usuario,
                              c.ftp_senha,
                              c.ftp_path,
                              c.interacao_posicao_inicial,
                              c.interacao_posicao_final,
                              c.usa_identificador_tabela,
                              c.identificador_tabela_posicao_inicial,
                              c.identificador_tabela_posicao_final
                          FROM
                            TB_CARGA c WHERE c.id_carga = :idCarga
                          
                          """)
                .param("idCarga", executarPreJob.getIdCarga())
                .query(Carga.class)
                .single();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Campo> buscaCampo(ExecutarPreJob executarPreJob) {
        return jdbcClient
                .sql("""
                         SELECT
                           *
                         FROM
                                TB_CAMPO;
                         
                         """)
                .param("idCampo", executarPreJob.getIdCarga())
                .query((rs , rowNum) -> {
                    Campo campo = new Campo();
                    campo.setIdCampo(rs.getInt("id_campo"));
                    campo.setCampo(rs.getString("campo"));
                    campo.setIdTipoCampo(rs.getInt("id_tipo_campo"));
                    return campo;
        })
                .list();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public TipoCampo buscaTipoCampo(ExecutarPreJob executarPreJob) {
        return jdbcClient
                .sql("""
                        SELECT
                            tc.id_tipo_campo,
                            tc.descricao
                        FROM
                            TB_TIPO_CAMPO tc
                        WHERE tc.id_tipo_campo = :idTipoCampo
                        
                        """)
                .param("idTipoCampo", executarPreJob.getIdCarga())
                .query(TipoCampo.class)
                .single();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public TipoDelimitador buscaTipoDelimitador(ExecutarPreJob executarPreJob) {
        return jdbcClient
                .sql("""
                         SELECT
                            td.id_tipo_delimitador,
                            td.descricao,
                            td.delimitador,
                            td.usa_posicionamento
                         FROM
                            TB_TIPO_DELIMITADOR td
                            WHERE td.id_tipo_delimitador = :idTipoDelimitador 
                         
                         """)
                .param("idTipoDelimitador", executarPreJob.getIdCarga())
                .query(TipoDelimitador.class)
                .single();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public TipoConexao buscaTipoConexao(ExecutarPreJob executarPreJob) {
        return jdbcClient
                .sql("""
                        SELECT
                            tcx.id_tipo_conexao,
                            tcx.descricao
                        FROM
                            TB_TIPO_CONEXAO as tcx
                             WHERE tcx.id_tipo_conexao = :idTipoConexao
                        """)
                .param("idTipoConexao", executarPreJob.getIdCarga())
                .query(TipoConexao.class)
                .single();
    }

}
