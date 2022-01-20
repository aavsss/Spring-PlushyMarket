package com.example.demo.cart.service;

import com.example.demo.cart.model.Cart;
import com.example.demo.cart.model.CartId;
import com.example.demo.cart.repository.CartRepository;
import com.example.demo.crud.model.Plushy;
import com.example.demo.crud.repository.PlushyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
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
    public Long setItemInCart(Cart cart) {
        Optional<Cart> cartOptional = cartRepository.findById(
                new CartId(cart.getUserId(), cart.getProductId())
        );
        if (cartOptional.isPresent()) {
            Cart alreadyPresentCart = cartOptional.get();
            alreadyPresentCart.setQuantity(cart.getQuantity());
            return cartRepository.count();
        }
        cartRepository.save(cart);
        return getNumInCart(cart.getUserId());
    }

    @Override
    @Transactional
    public Long deleteItemFromCart(CartId cartId) {
        Optional<Cart> cartOptional = cartRepository.findById(cartId);
        cartOptional.ifPresent(cartRepository::delete);
        return getNumInCart(cartId.getUserId());
    }

    @Override
    public List<Plushy> getPlushiesInCart(Long userId) {
        // Bad. Use spring jpa to do this. Have to manually create a join table. Follow Baeldung. https://www.baeldung.com/jpa-many-to-many
        List<Plushy> allPlushies = plushyRepository.findAll();
        List<Cart> cartItems = cartRepository.findAll();
        List<Plushy> ans = new ArrayList<>();
        for (Plushy allPlushy : allPlushies) {
            for (Cart cartItem : cartItems) {
                if (allPlushy.getId().equals(cartItem.getProductId())) {
                    ans.add(allPlushy);
                }
            }
        }
        return ans;
    }
}
