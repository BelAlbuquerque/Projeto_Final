package br.com.meli.desafio_final.service.implementation;

import br.com.meli.desafio_final.dto.AdsenseByWarehouseDto;
import br.com.meli.desafio_final.dto.AdsenseIdDto;
import br.com.meli.desafio_final.exception.NotFound;
import br.com.meli.desafio_final.model.entity.Adsense;
import br.com.meli.desafio_final.model.enums.Category;
import br.com.meli.desafio_final.repository.AdsenseRepository;
import br.com.meli.desafio_final.util.AdsenseByWarehouseDtoUtils;
import br.com.meli.desafio_final.util.AdsenseUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AdsenseServiceTest {

    @InjectMocks
    private AdsenseService adsenseService;

    @Mock
    private AdsenseRepository adsenseRepository;

    @Mock
    private BatchService batchService;

    // TODO: REMOVER A PALAVRA "TEST" DOS NOMES DOS MÉTODOS, POIS A MAIORIA NÃO POSSUI
    // TODO: ADICIONAR @DisplayName() AOS TESTES QUE NÃO O POSSUI

    @Test
    void find_findByCategory_whenAdsensesByCategoryExist() {
        BDDMockito.when(adsenseRepository.findAll())
                .thenReturn(AdsenseUtils.generateAdsenseList());

        List<Adsense> adsenseList = adsenseService.findByCategory(Category.FRESH);

        Assertions.assertThat(adsenseList).isNotNull();
        Assertions.assertThat(adsenseList.size()).isEqualTo(2);
    }

    @Test
    void find_findByCategory_whenAdsensesByCategoryDontExist() {
        BDDMockito.when(adsenseRepository.findAll()).thenReturn(Collections.emptyList());
        Exception exception = null;
        List<Adsense> adsenseList = null;
        try {
            adsenseList = adsenseService.findByCategory(Category.FRESH);
        } catch (Exception e) {
            exception = e;
        }
        verify(adsenseRepository, atLeastOnce()).findAll();
        Assertions.assertThat(adsenseList).isNull();
        assertThat(exception.getMessage()).isEqualTo("💢 Lista de anúncios não encontrada");
        // TODO: Mensagem do erro
    }

    @Test
    @DisplayName("Busca pelo ID: Valida se retorna um anúncio completo quando o ID é válido.")
    void findById_returnAdsense_whenIdIsValid() {
        BDDMockito.when(adsenseRepository.findById(anyLong()))
            .thenReturn(Optional.of(AdsenseUtils.newAdsense1ToSave()));

        Adsense adsense = AdsenseUtils.newAdsense1ToSave();

        Adsense adsenseFound = adsenseService.findById(1L);

        assertThat(adsenseFound).isNotNull();
        assertThat(adsenseFound.getId()).isEqualTo(adsense.getId());
    }

    @Test
    @DisplayName("Busca pelo ID: Valida se dispara a exceção NOT FOUND quando o ID é inválido.")
    void findById_throwException_whenIdInvalid() {
        assertThrows(NotFound.class, () -> {
           adsenseService.findById(0L);
        });
    }

    @Test
    @DisplayName("Listar anúncios: Valida se retorna uma lista de anúncios.")
    void findAll_returnListAdsense_whenAdsensesExists() {
        BDDMockito.when(adsenseRepository.findAll())
            .thenReturn(List.of(AdsenseUtils.newAdsense1ToSave()));

        List<Adsense> adsenseList = adsenseService.findAll();

        assertThat(adsenseList).isNotNull();
        assertThat(adsenseList.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Listar anúncios: Valida se dispara a execeção NOT FOUND quando não há anúncios cadastrados.")
    void findAll_throwException_whenAdsensesNotExists() {
        assertThrows(NotFound.class, () -> {
            adsenseService.findAll();
        });
    }

    @Test
    void find_findAdsensesByProductId_whenSuccess() {
        BDDMockito.when(adsenseRepository.findAll())
                .thenReturn(List.of(AdsenseUtils.newAdsense1ToSave()));
        List<AdsenseIdDto> adsenseList = adsenseService.findByProductId(1L);
        List<AdsenseIdDto> newList = AdsenseIdDto.convertDto(List.of(AdsenseUtils.newAdsense1ToSave()));
        assertThat(adsenseList).isNotNull();
        assertThat(adsenseList.contains(newList));
    }


    @Test
    void testFindAdsenseByWarehouseAndQuantity() {
        long adsenseId = AdsenseUtils.newAdsense1ToSave().getId();
        BDDMockito.when(batchService.getAdsenseByWarehouseAndQuantity(adsenseId))
                .thenReturn(AdsenseByWarehouseDtoUtils.AdsenseByWarehouseDtoListDto());

        List<AdsenseByWarehouseDto> adsenseList = adsenseService.findAdsenseByWarehouseAndQuantity(adsenseId);

        Assertions.assertThat(adsenseList).isNotNull();
        Assertions.assertThat(adsenseList.size()).isEqualTo(4);
    }
}
