package br.com.teste.cargamaster.domain.motor.PreJob.vo;

import br.com.teste.cargamaster.domain.motor.PreJob.entity.*;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PreJobResultado {

    private List<Campo> campo;
    private Carga carga;
    private List<CargaCampo> cargaCampo;
    private CargaDestino cargaDestino;
    private CargaDestinoCampo cargaDestinoCampo;
    private TipoCampo tipoCampo;
    private TipoConexao tipoConexao;
    private TipoDelimitador tipoDelimitador;

    @Override
    public String toString() {
        return "PreJobResultado {\n" +
                "    campo = " + campo.stream().map(Object::toString).collect(Collectors.joining(",")) + ",\n" +
                "    carga = " + carga + ",\n" +
                "    cargaCampo = " + cargaCampo.stream().map(Object::toString).collect(Collectors.joining(",")) + ",\n" +
                "    cargaDestino = " + cargaDestino + ",\n" +
                "    cargaDestinoCampo = " + cargaDestinoCampo + ",\n" +
                "    tipoCampo = " + tipoCampo + ",\n" +
                "    tipoConexao = " + tipoConexao + ",\n" +
                "    tipoDelimitador = " + tipoDelimitador + "\n" +
                '}';
    }
}