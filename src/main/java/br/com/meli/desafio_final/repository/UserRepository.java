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
            "user.id,\n" +
            "user.email,\n" +
            "user.name,\n" +
            "purchase_order.id AS purchase_order_id\n" +
            "FROM user\n" +
            "JOIN purchase_order\n" +
            "JOIN item\n" +
            "JOIN adsense\n" +
            "WHERE user.id = adsense.seller_id\n" +
            "AND adsense.id = item.adsense_id\n" +
            "AND item.purchase_order_id = purchase_order.id\n" +
            "AND purchase_order.buyer_id = ?1", nativeQuery = true)
    List<AllSellersByBuyerDto> findAllSellesByBuyer(Long buyerId);

    @Query(value = "SELECT\n" +
            "user.id,\n" +
            "user.email,\n" +
            "user.name,\n" +
            "purchase_order.id AS purchase_order_id\n" +
            "FROM user\n" +
            "JOIN purchase_order\n" +
            "JOIN item\n" +
            "JOIN adsense\n" +
            "WHERE user.id = purchase_order.buyer_id\n" +
            "AND purchase_order.id = item.purchase_order_id\n" +
            "AND item.adsense_id = adsense.id\n" +
            "AND adsense.seller_id = ?1", nativeQuery = true)
    List<AllBuyersBySellerDto> findAllBuyersBySeller(Long sellerId);
}
