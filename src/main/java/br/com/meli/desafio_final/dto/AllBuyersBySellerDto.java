package br.com.meli.desafio_final.dto;

import java.util.Date;

public interface AllBuyersBySellerDto {
    Long getId();
    String getEmail();
    String getName();
    Long getPurchase_order_id();
}
