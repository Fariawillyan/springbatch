package br.com.teste.cargamaster.domain.motor.exception;

import java.io.Serial;

public class CargaMasterException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    private Throwable t;

    public CargaMasterException() {

    }

    public CargaMasterException(final String s, final Throwable t) {
        super(s, t);
        this.t = t;
    }

    public CargaMasterException(final String s) {
        super(s);
    }

    @Override
    public String getMessage() {
        return getErrorMessage().isEmpty() ? super.getMessage() : getErrorMessage();
    }

    private String getErrorMessage() {
        if(t != null && t.getCause() != null){
            return t.getCause().getMessage();
        }

        return "";
    }
}
