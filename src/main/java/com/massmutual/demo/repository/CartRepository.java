package com.massmutual.demo.repository;

import com.massmutual.demo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.massmutual.demo.entity.Cart;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    List<Product> findById(int cartId);


}
