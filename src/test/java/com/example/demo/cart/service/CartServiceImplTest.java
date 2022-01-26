package com.example.demo.cart.service;

import com.amazonaws.services.apigateway.model.Op;
import com.example.demo.cart.model.Cart;
import com.example.demo.cart.model.CartId;
import com.example.demo.cart.model.PlushyInCart;
import com.example.demo.cart.repository.CartRepository;
import com.example.demo.crud.model.Plushy;
import com.example.demo.crud.repository.PlushyRepository;
import org.checkerframework.checker.units.qual.C;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class CartServiceImplTest {

    @Mock
    private CartRepository mockCartRepository;
    @Mock
    private PlushyRepository mockPlushyRepository;
    private CartServiceImpl cartService;
    @Captor
    private ArgumentCaptor<Cart> cartArgumentCaptor;

    @Before
    public void init() {
        cartService = new CartServiceImpl(mockCartRepository, mockPlushyRepository);
    }

    @Test
    public void getNumInCart_Success() {
        Long count = 1L;
        Long userId = 1L;
        when(mockCartRepository.getCountOfCartOfUser(any())).thenReturn(count);
        Long returnedCount = cartService.getNumInCart(userId);
        assert(count.equals(returnedCount));
    }

    @Test
    public void setItemInCart_firstTime_Success() {
        Long count = 1L;

        Optional<Cart> cartOptional = Optional.empty();
        when(mockCartRepository.findById(any())).thenReturn(cartOptional);
        Optional<Plushy> plushyOptional = Optional.empty();
        when(mockPlushyRepository.findById(any())).thenReturn(plushyOptional);
        when(mockCartRepository.getCountOfCartOfUser(any())).thenReturn(count);

        Cart cart = new Cart();
        Long itemInCart = cartService.setItemInCart(cart);
        assert(count.equals(itemInCart));

        verify(mockCartRepository, times(1)).save(cartArgumentCaptor.capture());
        assert(cartArgumentCaptor.getValue() != null);
        verify(mockCartRepository, times(1)).findById(any());
        verify(mockPlushyRepository, times(1)).findById(any());
    }

    @Test
    public void setItemInCart_alreadyPresentCartPlushy_UnderQuantity_Success() {
        Cart cart = new Cart(1L, 1L, 3);
        Cart inputCart = new Cart(1L, 1L, 1);
        Plushy plushy = new Plushy(
                1L,
                "Naruto",
                20,
                5,
                "naruto is the next hokage",
                "url"
        );
        Optional<Cart> cartOptional = Optional.of(cart);
        when(mockCartRepository.findById(any())).thenReturn(cartOptional);
        Optional<Plushy> plushyOptional = Optional.of(plushy);
        when(mockPlushyRepository.findById(any())).thenReturn(plushyOptional);

        Long returnedCount = cartService.setItemInCart(inputCart);

        verify(mockCartRepository, times(1)).save(cartArgumentCaptor.capture());
        verify(mockCartRepository, times(1)).count();
        assert(cartArgumentCaptor.getValue().equals(cart));
        assert(cart.getQuantity().equals(4));
    }

    @Test
    public void setItemInCart_alreadyPresentCart_overQuantity_success() {
        Cart cart = new Cart(1L, 1L, 3);
        Cart inputCart = new Cart(1L, 1L, 7);
        Plushy plushy = new Plushy(
                1L,
                "Naruto",
                20,
                5,
                "naruto is the next hokage",
                "url"
        );
        Optional<Cart> cartOptional = Optional.of(cart);
        when(mockCartRepository.findById(any())).thenReturn(cartOptional);
        Optional<Plushy> plushyOptional = Optional.of(plushy);
        when(mockPlushyRepository.findById(any())).thenReturn(plushyOptional);
//        when(mockCartRepository.getCountOfCartOfUser(any())).thenReturn(1L);

        Long returnedCount = cartService.setItemInCart(inputCart);

//        assert(returnedCount.equals(1L));

        verify(mockCartRepository, times(1)).save(cartArgumentCaptor.capture());
        verify(mockCartRepository, times(1)).count();
        assert(cartArgumentCaptor.getValue().equals(cart));
        assert(cart.getQuantity().equals(5));
    }

    @Test
    public void deleteItemFromCart_success() {
        CartId cartId = new CartId(1L, 1L);
        Long id = 1L;
        Cart cart = new Cart(1L, 1L, 3);
        Optional<Cart> cartOptional = Optional.of(cart);
        when(mockCartRepository.findById(any())).thenReturn(cartOptional);
        when(mockCartRepository.getCountOfCartOfUser(any())).thenReturn(1L);
        Long returnedCount = cartService.deleteItemFromCart(cartId);

        assert(returnedCount.equals(1L));

        ArgumentCaptor<CartId> cartIdArgumentCaptor = ArgumentCaptor.forClass(CartId.class);
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);

        verify(mockCartRepository, times(1)).findById(cartIdArgumentCaptor.capture());
        verify(mockCartRepository, times(1)).delete(any());
        verify(mockCartRepository, times(1)).getCountOfCartOfUser(longArgumentCaptor.capture());

        assert(cartIdArgumentCaptor.getValue().equals(cartId));
        assert(longArgumentCaptor.getValue().equals(id));
    }

    @Test
    public void getPlushiesInCart_success() {
        Long id = 1L;
        List<PlushyInCart> plushyInCartList = List.of(new PlushyInCart());
        when(mockCartRepository.getPlushyInfoFromCartOf()).thenReturn(plushyInCartList);
        List<PlushyInCart> returnedPlushy = cartService.getPlushiesInCart(id);
        assert(returnedPlushy.equals(plushyInCartList));

        verify(mockCartRepository, times(1)).getPlushyInfoFromCartOf();
    }
}
