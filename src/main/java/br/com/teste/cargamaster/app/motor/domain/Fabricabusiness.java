package br.com.teste.cargamaster.app.motor.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameters;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
public class Fabricabusiness extends  FabricaCliente{

    public Object buscaClienteParaCarga(JobParameters jobParameters){
        return super.buscaClienteParaCarga(jobParameters);
    }

    public List<String> gerarCamposParaProcessamento(JobParameters jobParameters) {
        List<String> cargaCampos = List.of(Objects.requireNonNull(jobParameters.getString("todasCargaCampos")).split(","));
        List<String> campos = List.of(Objects.requireNonNull(jobParameters.getString("todosCampos")).split(","));
        List<String> resultadoCampos = new ArrayList<>();

        for (String campoStr : campos) {
            for (int i = 0; i < cargaCampos.size(); i++) {
                String cargaCampoId = jobParameters.getString("cargaCampoId_" + i);
                String idCampo = jobParameters.getString("campoId_" + i);

                if (cargaCampoId != null && idCampo != null && cargaCampoId.equals(idCampo)) {
                    if (campoStr.contains("campo=")) {

                        String nomeCampo = campoStr.split("=")[1].trim();

                        if(!resultadoCampos.contains(nomeCampo)){
                            resultadoCampos.add(nomeCampo);
                        }
                    }
                }
            }
        }

        resultadoCampos.remove("ID");

        log.info(" --------------------> CAMPOS CRIADO PARA PROCESSAMENTO: {} <-----------------------", resultadoCampos);
        return resultadoCampos;
    }
}
