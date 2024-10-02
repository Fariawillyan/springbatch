package br.com.teste.cargamaster.domain.app;

import br.com.teste.cargamaster.domain.app.Cliente1.Pessoa;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameters;

import java.util.Objects;

@Slf4j
public class FabricaCliente {
    public Object buscaClienteParaCarga(JobParameters jobParameters) {
        return switch (Objects.requireNonNull(jobParameters.getString("cargaId"))) {
            case "3" -> {
                log.info("CRIANDO OBJETO PARA CARGA, {}", jobParameters.getString("cargaId"));
                yield new Pessoa();
            }
            case null -> throw new RuntimeException("Fabrica Cliente NULL");
            default -> {
                log.error("Não encontrado ID: {}", jobParameters.getString("idCarga"));
                throw new RuntimeException("Não foi possível criar o Cliente para Carga");
            }
        };
    }




}
