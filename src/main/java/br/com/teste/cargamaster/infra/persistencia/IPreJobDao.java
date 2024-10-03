package br.com.teste.cargamaster.infra.persistencia;

import br.com.teste.cargamaster.domain.motor.PreJob.entity.*;
import br.com.teste.cargamaster.domain.motor.PreJob.vo.ExecutarPreJob;

import java.util.List;

public interface IPreJobDao {

    CargaDestinoCampo buscaCargaDestinoCampo(ExecutarPreJob executarPreJob);

    CargaDestino buscaCargaDestino(ExecutarPreJob executarPreJob);

    List<CargaCampo> buscaCargaCampo(ExecutarPreJob executarPreJob);

    Carga buscaCarga(ExecutarPreJob executarPreJob);

    List<Campo> buscaCampo(ExecutarPreJob executarPreJob);

    TipoCampo buscaTipoCampo(ExecutarPreJob executarPreJob);

    TipoDelimitador buscaTipoDelimitador(ExecutarPreJob executarPreJob);

    TipoConexao buscaTipoConexao(ExecutarPreJob executarPreJob);
}
