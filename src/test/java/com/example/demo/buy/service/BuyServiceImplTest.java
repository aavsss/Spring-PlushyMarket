package com.example.demo.buy.service;

import com.example.demo.buy.model.BuyRequestBody;
import com.example.demo.cart.repository.CartRepository;
import com.example.demo.crud.repository.PlushyRepository;
import com.example.demo.crud.repository.models.PlushyInDB;
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
    private PlushyRepository mockPlushyRepository;
    @Mock
    private CartRepository mockCartRepository;
    private BuyServiceImpl buyService;

    @Before
    public void init() {
//        buyService = new BuyServiceImpl(mockPlushyRepository, mockCartRepository);
    }

    @Test
    public void buyPlushyById_success() {
        List<BuyRequestBody> buyRequestBodies = List.of(new BuyRequestBody(1L, 3));
        List<PlushyInDB> plushiesFromRepo = new ArrayList<>();
        List<PlushyInDB> plushies = buyService.buyPlushyById(buyRequestBodies);
        when(mockPlushyRepository.findAllById(any())).thenReturn(plushiesFromRepo);

        assert(plushies.size() == 0);

        ArgumentCaptor<List<Long>> listArgumentCaptor = ArgumentCaptor.forClass(List.class);

        verify(mockPlushyRepository, times(1)).findAllById(listArgumentCaptor.capture());
        verify(mockCartRepository, times(1)).deleteAll();

        assert(listArgumentCaptor.getValue().size() == 1);
    }
}
