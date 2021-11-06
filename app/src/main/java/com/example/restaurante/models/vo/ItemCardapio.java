package com.example.restaurante.models.vo;

import android.text.BoringLayout;

public class ItemCardapio {
    Integer id;
    String nome;
    String descricao;
    String categoria;
    String preco;
    boolean isGluten;
    double calorias;
    String image;

    public ItemCardapio() { }

    public ItemCardapio(Integer id, String nome, String descricao, String categoria, String preco, boolean isGluten, double calorias,String image) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.categoria = categoria;
        this.preco = preco;
        this.isGluten = isGluten;
        this.calorias = calorias;
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public boolean isGluten() {
        return isGluten;
    }

    public void setGluten(boolean gluten) {
        isGluten = gluten;
    }

    public double getCalorias() {
        return calorias;
    }

    public void setCalorias(double calorias) {
        this.calorias = calorias;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
