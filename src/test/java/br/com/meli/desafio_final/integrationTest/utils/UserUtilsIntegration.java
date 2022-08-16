package br.com.meli.desafio_final.integrationTest.utils;

import br.com.meli.desafio_final.model.entity.Adsense;
import br.com.meli.desafio_final.model.entity.Product;
import br.com.meli.desafio_final.model.enums.Category;
import br.com.meli.desafio_final.util.SellerUtils;
import br.com.meli.desafio_final.util.UserUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserUtilsIntegration {

    public static String sellerToken() {
        Date today = new Date();
        Long exp = Long.valueOf(86400000);
        Date expiration = new Date(today.getTime()+exp);
        return Jwts.builder()
                .setSubject(UserUtils.newUserSeller2().getEmail())
                .setIssuedAt(today)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, "TOP_SECRET")
                .compact();
    }


    public static String buyerToken() {
        Date today = new Date();
        Long exp = Long.valueOf(86400000);
        Date expiration = new Date(today.getTime()+exp);
        return Jwts.builder()
                .setSubject(UserUtils.newUserBuyer().getEmail())
                .setIssuedAt(today)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, "TOP_SECRET")
                .compact();
    }

    public static String buyerPayload () {
        return  "{\n" +
                "    \"name\": \"Name\",\n" +
                "    \"email\": \"buyer@email.com\",\n" +
                "    \"password\": \"password\",\n" +
                "    \"role\": \"BUYER\"\n" +
                "}";
    }

    public static String purchaseOrder1() {
        return "{\n" +
                "    \"status\": \"OPEN\",\n" +
                "    \"date\": \"2022-08-10\",\n" +
                "    \"buyer\": {\n" +
                "        \"id\": 1\n" +
                "    },\n" +
                "    \"itemList\": [\n" +
                "        {\n" +
                "            \"currentPrice\": 1.99,\n" +
                "            \"quantity\": 2000,\n" +
                "            \"adsense\": {\n" +
                "                \"id\": 1\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"currentPrice\": 1.99,\n" +
                "            \"quantity\": 700,\n" +
                "            \"adsense\": {\n" +
                "                \"id\": 2\n" +
                "            }\n" +
                "        }\n" +
                "    ]\n" +
                "}";
    }

    public static String purchaseOrder2() {
        return "{\n" +
                "    \"status\": \"OPEN\",\n" +
                "    \"date\": \"2022-08-10\",\n" +
                "    \"buyer\": {\n" +
                "        \"id\": 1\n" +
                "    },\n" +
                "    \"itemList\": [\n" +
                "        {\n" +
                "            \"currentPrice\": 1.99,\n" +
                "            \"quantity\": 2000,\n" +
                "            \"adsense\": {\n" +
                "                \"id\": 3\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"currentPrice\": 1.99,\n" +
                "            \"quantity\": 700,\n" +
                "            \"adsense\": {\n" +
                "                \"id\": 4\n" +
                "            }\n" +
                "        }\n" +
                "    ]\n" +
                "}";
    }

    public static List<Product> productList() {
        ArrayList<Product> list = new ArrayList<>();
        list.add(new Product(1L, "produto1", 10.00, Category.FRESH ));
        list.add(new Product(2L, "produto2", 10.00, Category.FRESH ));
        list.add(new Product(3L, "produto3", 10.00, Category.FRESH ));
        list.add(new Product(4L, "produto4", 10.00, Category.FRESH ));
        list.add(new Product(5L, "produto5", 10.00, Category.FRESH ));
        return list;
    }

    public static List<Adsense> adsenseList() {
        Adsense adsense = new Adsense();
        adsense.setSeller(SellerUtils.newSeller1ToSave());
        adsense.setProduct(productList().get(0));
        adsense.setId(1L);
        adsense.setPrice(1.99D);

        Adsense adsense2 = new Adsense();
        adsense.setSeller(SellerUtils.newSeller1ToSave());
        adsense.setProduct(productList().get(1));
        adsense.setId(2L);
        adsense.setPrice(1.99D);

        Adsense adsense3 = new Adsense();
        adsense.setSeller(SellerUtils.newSeller2ToSave());
        adsense.setProduct(productList().get(0));
        adsense.setId(3L);
        adsense.setPrice(1.99D);

        Adsense adsense4 = new Adsense();
        adsense.setSeller(SellerUtils.newSeller2ToSave());
        adsense.setProduct(productList().get(1));
        adsense.setId(4L);
        adsense.setPrice(1.99D);

        ArrayList<Adsense> list = new ArrayList<>();
        list.add(adsense);
        list.add(adsense2);
        list.add(adsense3);
        list.add(adsense4);
        return list;
    }

}
