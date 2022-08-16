package br.com.meli.desafio_final.util;

import br.com.meli.desafio_final.model.entity.Seller;

public class SellerUtils {

    public static Seller newSeller1ToSave() {
        Seller seller = new Seller();
        seller.setId(1L);
        seller.setName("Joao");
        return seller;
    }

    public static Seller newSeller2ToSave() {
        Seller seller = new Seller();
        seller.setId(2L);
        seller.setName("Nicolas");
        return seller;
    }

}
