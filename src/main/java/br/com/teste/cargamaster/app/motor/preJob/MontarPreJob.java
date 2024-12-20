package br.com.teste.cargamaster.app.motor.preJob;

import br.com.teste.cargamaster.app.motor.preJob.entity.*;
import br.com.teste.cargamaster.app.motor.preJob.vo.ExecutarPreJob;
import br.com.teste.cargamaster.app.motor.preJob.vo.PreJobResultado;
import br.com.teste.cargamaster.infra.persistencia.IPreJobDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
@Service
public class MontarPreJob {
    private final IPreJobDao preJobDao;
    public MontarPreJob(IPreJobDao preJobDao) {
        this.preJobDao = preJobDao;
    }

    public PreJobResultado montarPreJob(ExecutarPreJob executarPreJob) {

        log.info("Montando PreJob: ... ");
        log.info("EMS: " + executarPreJob.getEmsNumero());
        log.info("IdCarga: " + executarPreJob.getIdCarga());

        try {
            TipoConexao tipoConexao = buscaTipoConexao(executarPreJob);
            TipoDelimitador tipoDelimitador = buscaTipoDelimitador(executarPreJob);
            TipoCampo tipoCampo = buscaTipoCampo(executarPreJob);
            List<Campo> campo = buscaCampo(executarPreJob);
            Carga carga = buscaCarga(executarPreJob);
            List<CargaCampo> cargaCampo = buscaCargaCampo(executarPreJob);
            CargaDestino cargaDestino = buscaCargaDestino(executarPreJob);
            CargaDestinoCampo cargaDestinoCampo = buscaCargaDestinoCampo(executarPreJob);

            return PreJobResultado.builder()
                    .tipoConexao(tipoConexao)
                    .tipoDelimitador(tipoDelimitador)
                    .tipoCampo(tipoCampo)
                    .campo(campo)
                    .carga(carga)
                    .cargaCampo(cargaCampo)
                    .cargaDestino(cargaDestino)
                    .cargaDestinoCampo(cargaDestinoCampo)
                    .build();

        } catch (Exception e) {
            log.error(" Falha na busca do PreJob");
            e.getMessage();
        }
        return null;
    }

    private CargaDestinoCampo buscaCargaDestinoCampo(ExecutarPreJob executarPreJob) {

        try {
            CargaDestinoCampo cargaDestinoCampo = preJobDao.buscaCargaDestinoCampo(executarPreJob);

            if (cargaDestinoCampo != null) {

                return CargaDestinoCampo.builder()
                        .idCargaDestinoCampo(cargaDestinoCampo.getIdCargaDestinoCampo())
                        .idCargaCampo(cargaDestinoCampo.getIdCargaCampo())
                        .idCargaDestino(cargaDestinoCampo.getIdCargaDestino())
                        .build();
            }
        }catch (Exception e){
            log.error("CargaDestinoCampo is NULL for ExecutarPreJob: {}", executarPreJob, e);
        }
        return null;
    }

    private CargaDestino buscaCargaDestino(ExecutarPreJob executarPreJob) {

        try {
            CargaDestino cargaDestino = preJobDao.buscaCargaDestino(executarPreJob);
            if (cargaDestino != null) {

                return CargaDestino.builder()
                        .idCargaDestino(cargaDestino.getIdCargaDestino())
                        .idCarga(cargaDestino.getIdCarga())
                        .descricao(cargaDestino.getDescricao())
                        .tabelaDestino(cargaDestino.getTabelaDestino())
                        .sqlInsert(cargaDestino.getSqlInsert())
                        .sqlUpdate(cargaDestino.getSqlUpdate())
                        .sqlDelete(cargaDestino.getSqlDelete())
                        .idTipoConexao(cargaDestino.getIdTipoConexao())
                        .dbHost(cargaDestino.getDbHost())
                        .dbPorta(cargaDestino.getDbPorta())
                        .dbUsuario(cargaDestino.getDbUsuario())
                        .dbSenha(cargaDestino.getDbSenha())
                        .dbInstancia(cargaDestino.getDbInstancia())
                        .dbBanco(cargaDestino.getDbBanco())
                        .identificadorTabela(cargaDestino.getIdentificadorTabela())
                        .build();
            }
        }catch (Exception e){
            log.error("CargaDestino is NULL for ExecutarPreJob: {}", executarPreJob, e);
        }
        return null;
    }

    private List<CargaCampo> buscaCargaCampo(ExecutarPreJob executarPreJob) {

        try {
            List<CargaCampo> cargaCampoList = preJobDao.buscaCargaCampo(executarPreJob);
            if (cargaCampoList != null) {
                return cargaCampoList.stream()
                        .map(cargaCampo -> CargaCampo.builder()
                                .idCargaCampo(cargaCampo.getIdCargaCampo())
                                .idCarga(cargaCampo.getIdCarga())
                                .idCampo(cargaCampo.getIdCampo())
                                .ordem(cargaCampo.getOrdem())
                                .posicaoInicial(cargaCampo.getPosicaoInicial())
                                .posicaoFinal(cargaCampo.getPosicaoFinal())
                                .build())
                        .collect(Collectors.toList());
            }
        }catch (Exception e){
            log.error("CargaCampo is NULL for ExecutarPreJob: {}", executarPreJob);
        }
        return null;
    }

    private Carga buscaCarga(ExecutarPreJob executarPreJob) {

        try {
            Carga carga = preJobDao.buscaCarga(executarPreJob);
            if (carga != null) {

                //List<CargaCampo> cargaCampo = buscaCargaCampo(executarPreJob);

                return Carga.builder()
                        .idCarga(carga.getIdCarga())
                        .descricao(carga.getDescricao())
                        .emsNumero(carga.getEmsNumero())
                        .idTipoDelimitador(carga.getIdTipoDelimitador())
                        .nomeArquivo(carga.getNomeArquivo())
                        .ftpHost(carga.getFtpHost())
                        .ftpPorta(carga.getFtpPorta())
                        .ftpUsuario(carga.getFtpUsuario())
                        .ftpSenha(carga.getFtpSenha())
                        .ftpPath(carga.getFtpPath())
                        .interacaoPosicaoInicial(carga.getInteracaoPosicaoInicial())
                        .interacaoPosicaoFinal(carga.getInteracaoPosicaoFinal())
                        .identificadorTabelaPosicaoInicial(carga.getIdentificadorTabelaPosicaoInicial())
                        .identificadorTabelaPosicaoFinal(carga.getIdentificadorTabelaPosicaoFinal())
                        .build();
            }
        }catch (Exception e){
            log.error("Carga is NULL for ExecutarPreJob: {}", executarPreJob, e);
        }
        return null;
    }

    private List<Campo> buscaCampo(ExecutarPreJob executarPreJob) {

      try {
          List<Campo> campoList = preJobDao.buscaCampo(executarPreJob);
          if (campoList != null) {
              return campoList.stream()
                              .map(campo -> Campo.builder()
                                      .idCampo(campo.getIdCampo())
                                      .campo(campo.getCampo())
                                      .idTipoCampo(campo.getIdTipoCampo())
                                      .build())
                      .collect(Collectors.toList());
          }
      }catch (Exception e){
          log.error("Campo is NULL for ExecutarPreJob: {}", executarPreJob, e);
      }
        return null;
    }

    private TipoCampo buscaTipoCampo(ExecutarPreJob executarPreJob) {

        try {
            TipoCampo tipoCampo = preJobDao.buscaTipoCampo(executarPreJob);
            if (tipoCampo != null) {
                return TipoCampo.builder()
                        .idTipoCampo(tipoCampo.getIdTipoCampo())
                        .descricao(tipoCampo.getDescricao())
                        .build();
            }
        }catch (Exception e){
            log.error("TipoCapo is NULL for ExecutarPreJob: {}", executarPreJob, e);
        }
        return null;
    }


    private TipoDelimitador buscaTipoDelimitador(ExecutarPreJob executarPreJob) {

        try {
            TipoDelimitador tipoDelimitador = preJobDao.buscaTipoDelimitador(executarPreJob);
            if (tipoDelimitador != null) {
                return TipoDelimitador.builder()
                        .idTipoDelimitador(tipoDelimitador.getIdTipoDelimitador())
                        .descricao(tipoDelimitador.getDescricao())
                        .delimitador(tipoDelimitador.getDelimitador())
                        .usaPosicionamento(tipoDelimitador.isUsaPosicionamento())
                        .build();
            }
        }catch (Exception e){
            log.error("TipoDelimitador is NULL for ExecutarPreJob: {}", executarPreJob, e);
        }
        return null;
    }

    private TipoConexao buscaTipoConexao(ExecutarPreJob executarPreJob) {

        try {
            TipoConexao tipoConexao = preJobDao.buscaTipoConexao(executarPreJob);
            if (tipoConexao != null) {
               return TipoConexao.builder()
                       .idTipoConexao(tipoConexao.getIdTipoConexao())
                       .descricao(tipoConexao.getDescricao())
                       .build();
           }
       }catch (Exception e){
           log.error("TipoConexao is NULL for ExecutarPreJob: {}", executarPreJob , e);
       }
        return null;
    }

    public JobParameters resultado(PreJobResultado result){

        Map<String, Object> params = new HashMap<>();

        // TipoConexao
        params.put("tipoConexaoId", result.getTipoConexao().getIdTipoConexao());
        params.put("tipoConexaoDescricao", result.getTipoConexao().getDescricao());

        // TipoDelimitador
        params.put("tipoDelimitadorId", result.getTipoDelimitador().getIdTipoDelimitador());
        params.put("tipoDelimitadorDescricao", result.getTipoDelimitador().getDescricao());
        params.put("delimitador", result.getTipoDelimitador().getDelimitador());
        params.put("usaPosicionamento", result.getTipoDelimitador().isUsaPosicionamento());

        // TipoCampo
        params.put("tipoCampoId", result.getTipoCampo().getIdTipoCampo());
        params.put("tipoCampoDescricao", result.getTipoCampo().getDescricao());

        // Campo
        List<Campo> campos = result.getCampo();
        for(Campo campo : campos){
            params.put("campoId_" + campo.getIdCampo(), campo.getIdCampo());
            params.put("campoNome_" + campo.getCampo(), campo.getCampo());
            params.put("campoTipoCampo_" + campo.getIdTipoCampo(), campo.getIdTipoCampo());

            params.put("todosCampos", campos);
        }

        // Carga
        params.put("cargaId", result.getCarga().getIdCarga());
        params.put("cargaDescricao", result.getCarga().getDescricao());
        params.put("cargaEmsNumero", result.getCarga().getEmsNumero());
        params.put("cargaNomeArquivo", result.getCarga().getNomeArquivo());
        params.put("cargaFtpHost", result.getCarga().getFtpHost());
        params.put("cargaFtpUsuario", result.getCarga().getFtpUsuario());
        params.put("cargaFtpSenha", result.getCarga().getFtpSenha());
        params.put("cargaFtpPath", result.getCarga().getFtpPath());
        params.put("cargaInteracaoPosicaoInicial", result.getCarga().getInteracaoPosicaoInicial());
        params.put("cargaInteracaoPosicaoFinal", result.getCarga().getInteracaoPosicaoFinal());

        List<CargaCampo> cargaCampos = result.getCargaCampo();
        for (CargaCampo cargaCampo : cargaCampos) {

            //usar paramentro nome_"numero id" para carregar campo individualmente
            params.put("cargaCampoId_" + cargaCampo.getIdCargaCampo(), cargaCampo.getIdCargaCampo());
            params.put("cargaCampoOrdem_" + cargaCampo.getIdCargaCampo(), cargaCampo.getOrdem());
            params.put("cargaCampoPosicaoInicial_" + cargaCampo.getIdCargaCampo(), cargaCampo.getPosicaoInicial());
            params.put("cargaCampoPosicaoFinal_" + cargaCampo.getIdCargaCampo(), cargaCampo.getPosicaoFinal());

            //para carregar todos os campos
            params.put("todasCargaCampos", cargaCampos);
        }

        // CargaDestino
        params.put("cargaDestinoId", result.getCargaDestino().getIdCargaDestino());
        params.put("cargaDestinoDescricao", result.getCargaDestino().getDescricao());
        params.put("cargaDestinoTabelaDestino", result.getCargaDestino().getTabelaDestino());
        params.put("cargaDestinoSqlInsert", result.getCargaDestino().getSqlInsert());
        params.put("cargaDestinoSqlUpdate", result.getCargaDestino().getSqlUpdate());
        params.put("cargaDestinoSqlDelete", result.getCargaDestino().getSqlDelete());
        params.put("cargaDestinoTipoConexao", result.getCargaDestino().getIdTipoConexao());
        params.put("cargaDestinoDbHost", result.getCargaDestino().getDbHost());
        params.put("cargaDestinoDbPorta", result.getCargaDestino().getDbPorta());
        params.put("cargaDestinoDbUsuario", result.getCargaDestino().getDbUsuario());
        params.put("cargaDestinoDbSenha", result.getCargaDestino().getDbSenha());
        params.put("cargaDestinoDbInstancia", result.getCargaDestino().getDbInstancia());
        params.put("cargaDestinoDbBanco", result.getCargaDestino().getDbBanco());
        params.put("cargaDestinoIdentificadorTabela", result.getCargaDestino().getIdentificadorTabela());

        // CargaDestinoCampo
        params.put("cargaDestinoCampoId", result.getCargaDestinoCampo().getIdCargaDestinoCampo());
        params.put("cargaDestinoCampoCargaDestinoId", result.getCargaDestinoCampo().getIdCargaDestinoCampo());
        params.put("cargaDestinoCampoCargaCampoId", result.getCargaDestinoCampo().getIdCargaDestinoCampo());

        // Construindo os JobParameters com base no mapa
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();

        params.forEach((key, value) -> {
            if(key != null && value != null){
                jobParametersBuilder.addString(key, String.valueOf(value));
            }else{
                log.info("Chave ou valor nulo encontrado: " + key + " = " + value);
            }
        });

        return jobParametersBuilder.toJobParameters();
    }
}
