package br.com.meli.desafio_final.repository;

import br.com.meli.desafio_final.dto.AdsenseByDueDateAndCategoryDto;
import br.com.meli.desafio_final.dto.AllBuyersBySellerDto;
import br.com.meli.desafio_final.dto.AllSellersByBuyerDto;
import br.com.meli.desafio_final.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    @Query(value = "SELECT\n" +
            "frescos.user.id,\n" +
            "frescos.user.email,\n" +
            "frescos.user.name,\n" +
            "frescos.purchase_order.id AS purchase_order_id\n" +
            "FROM frescos.user\n" +
            "JOIN frescos.purchase_order\n" +
            "JOIN frescos.item\n" +
            "JOIN frescos.adsense\n" +
            "WHERE frescos.user.id = frescos.adsense.seller_id\n" +
            "AND frescos.adsense.id = frescos.item.adsense_id\n" +
            "AND frescos.item.purchase_order_id = frescos.purchase_order.id\n" +
            "AND frescos.purchase_order.buyer_id = ?1", nativeQuery = true)
    List<AllSellersByBuyerDto> findAllSellesByBuyer(Long buyerId);

    @Query(value = "SELECT\n" +
            "frescos.user.id,\n" +
            "frescos.user.email,\n" +
            "frescos.user.name,\n" +
            "frescos.purchase_order.id AS purchase_order_id\n" +
            "FROM frescos.user\n" +
            "JOIN frescos.purchase_order\n" +
            "JOIN frescos.item\n" +
            "JOIN frescos.adsense\n" +
            "WHERE frescos.user.id = frescos.purchase_order.buyer_id\n" +
            "AND frescos.purchase_order.id = frescos.item.purchase_order_id\n" +
            "AND frescos.item.adsense_id = frescos.adsense.id\n" +
            "AND frescos.adsense.seller_id = ?1", nativeQuery = true)
    List<AllBuyersBySellerDto> findAllBuyersBySeller(Long sellerId);
}
