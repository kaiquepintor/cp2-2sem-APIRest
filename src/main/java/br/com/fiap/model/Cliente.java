package br.com.fiap.model;

import java.util.Date;

public class Cliente {
    private int id;
    private String nome;
    private String email;
    private String telefone;
    private Date dataCadastro;
    private TipoCliente tipo;

    // Enumerador para Tipo de Cliente
    public enum TipoCliente {
        INDIVIDUAL,
        CORPORATIVO
    }

    // Construtores
    public Cliente() {}

    public Cliente(int id, String nome, String email, String telefone, Date dataCadastro, TipoCliente tipo) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.dataCadastro = dataCadastro;
        this.tipo = tipo;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public TipoCliente getTipo() {
        return tipo;
    }

    public void setTipo(TipoCliente tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", telefone='" + telefone + '\'' +
                ", dataCadastro=" + dataCadastro +
                ", tipo=" + tipo +
                '}';
    }
}
