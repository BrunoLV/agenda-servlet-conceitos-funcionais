package br.com.valhala.agenda.erro;

import java.io.Serializable;

public class Erro implements Serializable {

    private static final long serialVersionUID = 1L;
    private String mensagem;
    private String uri;
    private Integer statusCode;

    private Erro(Builder builder) {
        super();
        mensagem = builder.mensagem;
        uri = builder.uri;
        statusCode = builder.statusCode;
    }

    public String getMensagem() {
        return mensagem;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public String getUri() {
        return uri;
    }

    public static class Builder {

        private String mensagem;
        private String uri;
        private Integer statusCode;

        public Builder() {
            super();
        }

        public Erro build() {
            return new Erro(this);
        }

        public Builder mensagem(String mensagem) {
            this.mensagem = mensagem;
            return this;
        }

        public Builder statusCode(Integer statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public Builder uri(String uri) {
            this.uri = uri;
            return this;
        }

    }

}
