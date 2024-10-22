package br.com.teste.cargamaster.app.motor.domain;

import br.com.teste.cargamaster.app.motor.domain.Cliente1.Pessoa;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameters;

import java.util.Objects;

@Slf4j
public abstract class FabricaCliente {
    public Object buscaClienteParaCarga(JobParameters jobParameters) {
        return switch (Objects.requireNonNull(jobParameters.getString("cargaId"))) {
            case "3" -> {
                log.info("CRIANDO OBJETO DA CARGA ID: {}", jobParameters.getString("cargaId"));
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
