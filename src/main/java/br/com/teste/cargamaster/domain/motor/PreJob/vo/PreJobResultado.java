package br.com.teste.cargamaster.domain.motor.PreJob.vo;

import br.com.teste.cargamaster.domain.motor.PreJob.entity.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PreJobResultado {

    private Campo campo;
    private Carga carga;
    private CargaCampo cargaCampo;
    private CargaDestino cargaDestino;
    private CargaDestinoCampo cargaDestinoCampo;
    private TipoCampo tipoCampo;
    private TipoConexao tipoConexao;
    private TipoDelimitador tipoDelimitador;

    @Override
    public String toString() {
        return "PreJobResultado {\n" +
                "    campo = " + campo + ",\n" +
                "    carga = " + carga + ",\n" +
                "    cargaCampo = " + cargaCampo + ",\n" +
                "    cargaDestino = " + cargaDestino + ",\n" +
                "    cargaDestinoCampo = " + cargaDestinoCampo + ",\n" +
                "    tipoCampo = " + tipoCampo + ",\n" +
                "    tipoConexao = " + tipoConexao + ",\n" +
                "    tipoDelimitador = " + tipoDelimitador + "\n" +
                '}';
    }
}