package com.example.demo.crud.service;

import com.example.demo.crud.model.Plushy;
import com.example.demo.crud.repository.PlushyRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class PlushyServiceTest {

    @Mock
    private PlushyRepository mockPlushyRepository;
    private PlushyServiceImpl plushyService;
    @Captor
    ArgumentCaptor<Sort> argumentCaptor;
    @Captor
    ArgumentCaptor<Long> longArgumentCaptor;
    @Captor
    ArgumentCaptor<Plushy> plushyArgumentCaptor;

    @Before
    public void init() {
        plushyService = new PlushyServiceImpl(mockPlushyRepository);
    }

    @Test
    public void getPlushies_success(){
        when(mockPlushyRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))).thenReturn(
                Stream.of(new Plushy(
                        "Naruto",
                        20,
                        5,
                        "naruto is the next hokage",
                        ""
                ), new Plushy(
                        "Sasuke",
                        19,
                        6,
                        "sasuke unlocks mangekyou sharingan",
                        ""
                )).collect(Collectors.toList()));
        List<Plushy> returnedPlushy = plushyService.getPlushies();
        assert(returnedPlushy.size() == 2);
        verify(mockPlushyRepository, times(1)).findAll(argumentCaptor.capture());
        assert(argumentCaptor.getValue().equals(Sort.by(Sort.Direction.ASC, "id")));
    }

    @Test
    public void getPlushies_nullValues_success() {
        when(mockPlushyRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))).thenReturn(null);
        List<Plushy> returnedPlushy = plushyService.getPlushies();
        assert(returnedPlushy == null);
        verify(mockPlushyRepository, times(1)).findAll(argumentCaptor.capture());
        assert(argumentCaptor.getValue().equals(Sort.by(Sort.Direction.ASC, "id")));
    }

    @Test
    public void getPlushyById_narutoPlushy_success() {
        Plushy plushy = new Plushy(
                "Naruto",
                20,
                5,
                "naruto is the next hokage",
                ""
        );
        Long id = 1L;
        when(mockPlushyRepository.findById(any())).thenReturn(Optional.of(plushy));
        Plushy returnedPlushy = plushyService.getPlushyById(id);
        assert(returnedPlushy.equals(plushy));
        verify(mockPlushyRepository, times(1)).findById(longArgumentCaptor.capture());
        assert(Objects.equals(longArgumentCaptor.getValue(), id));
    }

    @Test
    public void getPlushyById_null_success() {
        Long id = 1L;
        when(mockPlushyRepository.findById(any())).thenReturn(Optional.ofNullable(null));
        Plushy returnedPlushy = plushyService.getPlushyById(id);
        assert(returnedPlushy == null);
        verify(mockPlushyRepository, times(1)).findById(longArgumentCaptor.capture());
        assert(Objects.equals(longArgumentCaptor.getValue(), id));
    }

    @Test
    public void addPlushy_success() {
        Plushy plushy = new Plushy(
                "Naruto",
                20,
                5,
                "naruto is the next hokage",
                ""
        );
        when(mockPlushyRepository.findById(any())).thenReturn(Optional.empty());
        plushyService.addPlushy(plushy);
        verify(mockPlushyRepository, times(1)).save(plushyArgumentCaptor.capture());
        assert(plushy.equals(plushyArgumentCaptor.getValue()));
    }

    @Test(expected = IllegalStateException.class)
    public void addPlushy_throwsIllegalStateException() {
        Plushy plushy = new Plushy(
                "Naruto",
                20,
                5,
                "naruto is the next hokage",
                ""
        );
        when(mockPlushyRepository.findById(any())).thenReturn(Optional.of(plushy));
        plushyService.addPlushy(plushy);
    }

    @Test
    public void deletePlushy_success() {
        Long id = 1L;
        when(mockPlushyRepository.existsById(any())).thenReturn(true);
        plushyService.deletePlushy(id);
        verify(mockPlushyRepository, times(1)).deleteById(longArgumentCaptor.capture());
        assert(Objects.equals(longArgumentCaptor.getValue(), id));
    }

    @Test(expected = IllegalStateException.class)
    public void deletePlushy_throwsIllegalStateException() {
        Long id = 1L;
        when(mockPlushyRepository.existsById(any())).thenReturn(false);
        plushyService.deletePlushy(id);
    }
}