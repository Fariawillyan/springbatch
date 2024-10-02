package br.com.teste.cargamaster.infra.persistencia;

import br.com.teste.cargamaster.domain.motor.PreJob.entity.*;
import br.com.teste.cargamaster.domain.motor.PreJob.vo.ExecutarPreJob;

public interface IPreJobDao {

    CargaDestinoCampo buscaCargaDestinoCampo(ExecutarPreJob executarPreJob);

    CargaDestino buscaCargaDestino(ExecutarPreJob executarPreJob);

    CargaCampo buscaCargaCampo(ExecutarPreJob executarPreJob);

    Carga buscaCarga(ExecutarPreJob executarPreJob);

    Campo buscaCampo(ExecutarPreJob executarPreJob);

    TipoCampo buscaTipoCampo(ExecutarPreJob executarPreJob);

    TipoDelimitador buscaTipoDelimitador(ExecutarPreJob executarPreJob);

    TipoConexao buscaTipoConexao(ExecutarPreJob executarPreJob);
}
