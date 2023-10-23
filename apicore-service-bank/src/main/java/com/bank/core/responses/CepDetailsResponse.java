package com.bank.core.responses;

import com.fasterxml.jackson.annotation.*;

public class CepDetailsResponse {
    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String localidade;
    private String uf;
    private String ibge;
    private String gia;
    private String ddd;
    private String siafi;

    @JsonProperty("cep")
    public String getCep() {
        return cep;
    }

    @JsonProperty("cep")
    public void setCep(String cep) {
        this.cep = cep;
    }

    @JsonProperty("logradouro")
    public String getLogradouro() {
        return logradouro;
    }

    @JsonProperty("logradouro")
    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    @JsonProperty("complemento")
    public String getComplemento() {
        return complemento;
    }

    @JsonProperty("complemento")
    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    @JsonProperty("bairro")
    public String getBairro() {
        return bairro;
    }

    @JsonProperty("bairro")
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    @JsonProperty("localidade")
    public String getLocalidade() {
        return localidade;
    }

    @JsonProperty("localidade")
    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    @JsonProperty("uf")
    public String getUf() {
        return uf;
    }

    @JsonProperty("uf")
    public void setUf(String uf) {
        this.uf = uf;
    }

    @JsonProperty("ibge")
    public String getIbge() {
        return ibge;
    }

    @JsonProperty("ibge")
    public void setIbge(String ibge) {
        this.ibge = ibge;
    }

    @JsonProperty("gia")
    public String getGia() {
        return gia;
    }

    @JsonProperty("gia")
    public void setGia(String gia) {
        this.gia = gia;
    }

    @JsonProperty("ddd")
    public String getDdd() {
        return ddd;
    }

    @JsonProperty("ddd")
    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    @JsonProperty("siafi")
    public String getSiafi() {
        return siafi;
    }

    @JsonProperty("siafi")
    public void setSiafi(String siafi) {
        this.siafi = siafi;
    }
}
