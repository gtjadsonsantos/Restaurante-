package com.example.restaurante.models.vo;

import java.util.ArrayList;
public class Cardapio {
    ArrayList<ItemCardapio> itemCardapios;

    public Cardapio() {}

    public Cardapio(ArrayList<ItemCardapio> itemCardapios) {
        this.itemCardapios = itemCardapios;
    }

    public ArrayList<ItemCardapio> getItemCardapios() {
        return itemCardapios;
    }

    public void setItemCardapios(ArrayList<ItemCardapio> itemCardapios) {
        this.itemCardapios = itemCardapios;
    }
}
