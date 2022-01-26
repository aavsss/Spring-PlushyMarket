package com.example.demo.buy.service;

import com.example.demo.buy.model.BuyRequestBody;
import com.example.demo.buy.repository.BuyRepository;
import com.example.demo.cart.repository.CartRepository;
import com.example.demo.crud.model.Plushy;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class BuyServiceImplTest {

    @Mock
    private BuyRepository mockBuyRepository;
    @Mock
    private CartRepository mockCartRepository;
    private BuyServiceImpl buyService;

    @Before
    public void init() {
        buyService = new BuyServiceImpl(mockBuyRepository, mockCartRepository);
    }

    @Test
    public void buyPlushyById_success() {
        List<BuyRequestBody> buyRequestBodies = List.of(new BuyRequestBody(1L, 3));
        List<Plushy> plushiesFromRepo = new ArrayList<>();
        List<Plushy> plushies = buyService.buyPlushyById(buyRequestBodies);
        when(mockBuyRepository.findAllById(any())).thenReturn(plushiesFromRepo);

        assert(plushies.size() == 0);

        ArgumentCaptor<List<Long>> listArgumentCaptor = ArgumentCaptor.forClass(List.class);

        verify(mockBuyRepository, times(1)).findAllById(listArgumentCaptor.capture());
        verify(mockCartRepository, times(1)).deleteAll();

        assert(listArgumentCaptor.getValue().size() == 1);
    }
}
