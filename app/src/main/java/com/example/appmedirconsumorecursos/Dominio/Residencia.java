package com.example.appmedirconsumorecursos.Dominio;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Galdino on 24/08/2015.
 */
public class Residencia extends EntidadeDominio{
    private String  ds_nome,
                    ds_endereco,
                    ds_bairro,
                    ds_cidade,
                    ds_uf,
                    nr_cep,
                    nr_morador,
                    nr_comodos,
                    ds_senha;
    private int nr_numero,
                fg_excluido;
    public static final String DF_NOME = "ds_nome",
                                DF_ENDERECO = "ds_endereco",
                                DF_BAIRRO = "ds_bairro",
                                DF_CIDADE = "ds_cidade",
                                DF_CEP = "ds_cep",
                                DF_UF = "ds_uf",
                                DF_MORADOR = "nr_morador",
                                DF_COMODOS = "nr_comodos",
                                DF_SENHA = "ds_senha",
                                DF_NUMERO = "nr_numero",
                                DF_FG_EXCLUIDO = "fg_excluido";


    // sets


    public void setCep(String nr_cep) {
        this.nr_cep = nr_cep;
    }

    public void setNome(String ds_nome) {
        this.ds_nome = ds_nome;
    }

    public void setEndereco(String ds_endereco) {
        this.ds_endereco = ds_endereco;
    }

    public void setBairro(String ds_bairro) {
        this.ds_bairro = ds_bairro;
    }

    public void setCidade(String ds_cidade) {
        this.ds_cidade = ds_cidade;
    }

    public void setUf(String ds_uf) {
        this.ds_uf = ds_uf;
    }

    public void setNrMorador(String nr_morador) {
        this.nr_morador = nr_morador;
    }

    public void setNrComodos(String nr_comodos) {
        this.nr_comodos = nr_comodos;
    }

    public void setSenha(String ds_senha) {
        this.ds_senha = ds_senha;
    }

    public void setNumero(int nr_numero) {
        this.nr_numero = nr_numero;
    }

    public void setFgExcluido(int fg_excluido) {
        this.fg_excluido = fg_excluido;
    }

    // gets


    public String getCep() {
        return nr_cep;
    }


    public String getNome() {
        return ds_nome;
    }

    public String getEndereco() {
        return ds_endereco;
    }

    public String getBairro() {
        return ds_bairro;
    }

    public String getCidade() {
        return ds_cidade;
    }

    public String getUf() {
        return ds_uf;
    }

    public String getNrMorador() {
        return nr_morador;
    }

    public String getNrComodos() {
        return nr_comodos;
    }

    public String getSenha() {
        return ds_senha;
    }

    public int getNumero() {
        return nr_numero;
    }

    public int getFgExcluido() {
        return fg_excluido;
    }


    public void popularMap(EntidadeDominio entidadeDominio,String acao, String nomeClasse)
    {
        Residencia residencia = (Residencia)entidadeDominio;
        map = new HashMap<String, String>();
        map.put(residencia.DF_NOME, residencia.getNome());
        map.put(residencia.DF_ENDERECO,residencia.getEndereco());
        map.put(residencia.DF_BAIRRO,residencia.getBairro());
        map.put(residencia.DF_CIDADE,residencia.getCidade());
        map.put(residencia.DF_CIDADE,residencia.getCep());
        map.put(residencia.DF_UF,residencia.getUf());
        map.put(residencia.DF_MORADOR,residencia.getNrMorador());
        map.put(residencia.DF_COMODOS,residencia.getNrComodos());
        map.put(residencia.DF_SENHA,residencia.getSenha());
        map.put(residencia.DF_NUMERO,String.valueOf(residencia.getNumero()));
        map.put(residencia.DF_FG_EXCLUIDO,String.valueOf(residencia.getFgExcluido()));
        map.put("operacao", acao);          // indica a operação que está sendo realizada
        map.put("classe", nomeClasse);
    }
}
