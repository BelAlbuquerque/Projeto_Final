package br.com.meli.desafio_final.service.implementation;

import br.com.meli.desafio_final.model.entity.*;
import br.com.meli.desafio_final.repository.*;
import br.com.meli.desafio_final.util.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class WarehouseServiceTest {
    @InjectMocks
    private WarehouseService warehouseService;


    @Mock
    WarehouseRepository warehouseRepository;


    @Test
    public void testValidateSection() {
        Warehouse warehouse = WarehouseUtils.newWarehouse();

        BDDMockito.when(warehouseRepository.findById(ArgumentMatchers.any(Long.class)))
                .thenReturn(Optional.of(warehouse));

        warehouseService.findWarehouse(warehouse.getId());
        verify(warehouseRepository, only()).findById(1L);
    }

}
