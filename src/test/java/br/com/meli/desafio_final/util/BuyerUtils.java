package br.com.meli.desafio_final.util;

import br.com.meli.desafio_final.model.entity.Buyer;

public class BuyerUtils {

    public static Buyer newBuyer1ToSave() {
        Buyer buyer = new Buyer();
        buyer.setId(1L);
        buyer.setName("Mulher Maravilha");
        return buyer;
    }

    public static Buyer newBuyer2ToSave() {
        Buyer buyer = new Buyer();
        buyer.setId(2L);
        buyer.setName("Steve Trevor");
        return buyer;
    }

}
