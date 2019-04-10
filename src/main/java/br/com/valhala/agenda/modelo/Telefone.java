package br.com.valhala.agenda.modelo;

import br.com.valhala.agenda.modelo.enums.EnumTipoTelefone;

import java.io.Serializable;

public class Telefone implements Serializable {

    private static final long serialVersionUID = 7361262600196016914L;
    private Long id;
    private String ddd;
    private String numero;
    private EnumTipoTelefone tipo;

    private Telefone(Builder builder) {
        id = builder.id;
        ddd = builder.ddd;
        numero = builder.numero;
        tipo = builder.tipo;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Telefone)) {
            return false;
        }
        Telefone other = (Telefone) obj;
        if (ddd == null) {
            if (other.ddd != null) {
                return false;
            }
        } else if (!ddd.equals(other.ddd)) {
            return false;
        }
        if (numero == null) {
            if (other.numero != null) {
                return false;
            }
        } else if (!numero.equals(other.numero)) {
            return false;
        }
        if (tipo != other.tipo) {
            return false;
        }
        return true;
    }

    public String getDdd() {
        return ddd;
    }

    public Long getId() {
        return id;
    }

    public String getNumero() {
        return numero;
    }

    public EnumTipoTelefone getTipo() {
        return tipo;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((ddd == null) ? 0 : ddd.hashCode());
        result = (prime * result) + ((numero == null) ? 0 : numero.hashCode());
        result = (prime * result) + ((tipo == null) ? 0 : tipo.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder2 = new StringBuilder();
        builder2.append("Telefone [");
        if (id != null) {
            builder2.append("id=").append(id).append(", ");
        }
        if (ddd != null) {
            builder2.append("ddd=").append(ddd).append(", ");
        }
        if (numero != null) {
            builder2.append("numero=").append(numero).append(", ");
        }
        if (tipo != null) {
            builder2.append("tipo=").append(tipo);
        }
        builder2.append("]");
        return builder2.toString();
    }

    public static class Builder {

        private Long id;
        private String ddd;
        private String numero;
        private EnumTipoTelefone tipo;

        public Builder() {
            super();
        }

        public Telefone build() {
            return new Telefone(this);
        }

        public Builder ddd(String ddd) {
            this.ddd = ddd;
            return this;
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder numero(String numero) {
            this.numero = numero;
            return this;
        }

        public Builder tipo(EnumTipoTelefone tipo) {
            this.tipo = tipo;
            return this;
        }

    }

}
