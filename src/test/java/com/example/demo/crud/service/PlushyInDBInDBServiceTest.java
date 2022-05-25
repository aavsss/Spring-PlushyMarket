package com.example.demo.crud.service;

import com.example.demo.appUser.security.dependency.JWTUtils;
import com.example.demo.crud.repository.PlushyRepository;
import com.example.demo.crud.repository.models.PlushyInDB;
import com.example.demo.globalService.fileService.FileService;
import com.example.demo.globalService.stringValidator.StringValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
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
public class PlushyInDBInDBServiceTest {

    private PlushyServiceImpl plushyService;
    @Mock
    private PlushyRepository mockPlushyRepository;
    @Mock
    private FileService mockFileService;
    @Mock
    private JWTUtils mockJwtUtils;
    @Mock
    private StringValidator stringValidator;
    @Captor
    ArgumentCaptor<Sort> argumentCaptor;
    @Captor
    ArgumentCaptor<Long> longArgumentCaptor;
    @Captor
    ArgumentCaptor<PlushyInDB> plushyArgumentCaptor;

    @Before
    public void init() {
        plushyService = new PlushyServiceImpl(mockPlushyRepository, mockFileService, mockJwtUtils, stringValidator);
    }

    @Test
    public void getPlushies_success() {
        when(mockPlushyRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))).thenReturn(
                Stream.of(new PlushyInDB(
                        "Naruto",
                        20,
                        5,
                        "naruto is the next hokage",
                        "",
                        "",
                        0
                ), new PlushyInDB(
                        "Sasuke",
                        19,
                        6,
                        "sasuke unlocks mangekyou sharingan",
                        "",
                        "",
                        0
                )).collect(Collectors.toList()));
        List<PlushyInDB> returnedPlushyInDB = plushyService.getPlushies();
        assert (returnedPlushyInDB.size() == 2);
        verify(mockPlushyRepository, times(1)).findAll(argumentCaptor.capture());
        assert (argumentCaptor.getValue().equals(Sort.by(Sort.Direction.ASC, "id")));
    }

    @Test
    public void getPlushies_nullValues_success() {
        when(mockPlushyRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))).thenReturn(null);
        List<PlushyInDB> returnedPlushyInDB = plushyService.getPlushies();
        assert (returnedPlushyInDB == null);
        verify(mockPlushyRepository, times(1)).findAll(argumentCaptor.capture());
        assert (argumentCaptor.getValue().equals(Sort.by(Sort.Direction.ASC, "id")));
    }

    @Test
    public void getPlushyById_narutoPlushy_success() {
        PlushyInDB plushyInDB = new PlushyInDB(
                "Naruto",
                20,
                5,
                "naruto is the next hokage",
                "",
                "",
                0
        );
        Long id = 1L;
        when(mockPlushyRepository.findById(any())).thenReturn(Optional.of(plushyInDB));
        PlushyInDB returnedPlushyInDB = plushyService.getPlushyById(id);
        assert (returnedPlushyInDB.equals(plushyInDB));
        verify(mockPlushyRepository, times(1)).findById(longArgumentCaptor.capture());
        assert (Objects.equals(longArgumentCaptor.getValue(), id));
    }

    @Test
    public void getPlushyById_null_success() {
        Long id = 1L;
        when(mockPlushyRepository.findById(any())).thenReturn(Optional.ofNullable(null));
        PlushyInDB returnedPlushyInDB = plushyService.getPlushyById(id);
        assert (returnedPlushyInDB == null);
        verify(mockPlushyRepository, times(1)).findById(longArgumentCaptor.capture());
        assert (Objects.equals(longArgumentCaptor.getValue(), id));
    }

    @Test
    public void addPlushy_success() {
        PlushyInDB plushyInDB = new PlushyInDB(
                "Naruto",
                20,
                5,
                "naruto is the next hokage",
                "",
                "",
                0
        );
        when(mockPlushyRepository.findById(any())).thenReturn(Optional.empty());
        plushyService.addPlushy(plushyInDB);
        verify(mockPlushyRepository, times(1)).save(plushyArgumentCaptor.capture());
        assert (plushyInDB.equals(plushyArgumentCaptor.getValue()));
    }

    @Test(expected = IllegalStateException.class)
    public void addPlushy_throwsIllegalStateException() {
        PlushyInDB plushyInDB = new PlushyInDB(
                "Naruto",
                20,
                5,
                "naruto is the next hokage",
                "",
                "",
                0
        );
        when(mockPlushyRepository.findById(any())).thenReturn(Optional.of(plushyInDB));
        plushyService.addPlushy(plushyInDB);
    }

    @Test
    public void deletePlushy_success() {
        Long id = 1L;
        when(mockPlushyRepository.existsById(any())).thenReturn(true);
        plushyService.deletePlushy(id);
        verify(mockPlushyRepository, times(1)).deleteById(longArgumentCaptor.capture());
        assert (Objects.equals(longArgumentCaptor.getValue(), id));
    }

    @Test(expected = IllegalStateException.class)
    public void deletePlushy_throwsIllegalStateException() {
        Long id = 1L;
        when(mockPlushyRepository.existsById(any())).thenReturn(false);
        plushyService.deletePlushy(id);
    }
}