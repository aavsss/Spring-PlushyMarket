package com.example.demo.cart.service;

import com.example.demo.cart.repository.models.CartInDB;
import com.example.demo.cart.repository.models.CartId;
import com.example.demo.cart.model.PlushyInCart;
import com.example.demo.cart.repository.CartRepository;
import com.example.demo.crud.repository.models.PlushyInDB;
import com.example.demo.crud.repository.PlushyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService{

    private final CartRepository cartRepository;
    private final PlushyRepository plushyRepository;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository, PlushyRepository plushyRepository) {
        this.cartRepository = cartRepository;
        this.plushyRepository = plushyRepository;
    }

    @Override
    public Long getNumInCart(Long userId) {
        return cartRepository.getCountOfCartOfUser(userId);
    }

    @Override
    @Transactional
    public Long setItemInCart(CartInDB cartInDB) {
        Optional<CartInDB> cartOptional = cartRepository.findById(
                new CartId(cartInDB.getUserId(), cartInDB.getProductId())
        );
        Optional<PlushyInDB> plushyOptional = plushyRepository.findById(cartInDB.getProductId());
        if (cartOptional.isPresent() && plushyOptional.isPresent()) {
            CartInDB alreadyPresentCartInDB = cartOptional.get();
            PlushyInDB plushyInDBInQuestion = plushyOptional.get();
            Integer quantityToSet = alreadyPresentCartInDB.getQuantity() + cartInDB.getQuantity();
            if (quantityToSet > plushyInDBInQuestion.getQuantity()) {
                alreadyPresentCartInDB.setQuantity(plushyInDBInQuestion.getQuantity());
            } else {
                alreadyPresentCartInDB.setQuantity(quantityToSet);
            }
            cartRepository.save(alreadyPresentCartInDB);
            return cartRepository.count();
        }
        cartRepository.save(cartInDB);
        return getNumInCart(cartInDB.getUserId());
    }

    @Override
    @Transactional
    public Long deleteItemFromCart(CartId cartId) {
        Optional<CartInDB> cartOptional = cartRepository.findById(cartId);
        cartOptional.ifPresent(cartRepository::delete);
        return getNumInCart(cartId.getUserId());
    }

    @Override
    public List<PlushyInCart> getPlushiesInCart(Long userId) {
        return cartRepository.getPlushyInfoFromCartOf();
    }
}
