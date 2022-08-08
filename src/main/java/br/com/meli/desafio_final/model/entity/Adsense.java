package br.com.meli.desafio_final.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter @Getter
@Builder @NoArgsConstructor @AllArgsConstructor// Adicionado para usar na 'test/util'
public class Adsense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "seller_id")
    @JsonIgnoreProperties("adsense")
    private Seller seller;

    @ManyToOne()
    @JoinColumn(name = "product_id")
    @JsonIgnoreProperties("adsense")
    private Product product;


    //Alterando tipo para Double para calcular total price e adicionando private.
    private Double price;

    @OneToMany(mappedBy = "adsense")
    @JsonIgnoreProperties("adsense")
    private List<Batch> batchList;

    @OneToMany(mappedBy = "adsense")
    @JsonIgnoreProperties("adsense")
    private List<Item> itemList;
}
