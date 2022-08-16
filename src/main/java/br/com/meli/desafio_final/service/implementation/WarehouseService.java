package br.com.meli.desafio_final.service.implementation;

import br.com.meli.desafio_final.model.entity.Warehouse;
import br.com.meli.desafio_final.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WarehouseService {

    @Autowired
    WarehouseRepository warehouseRepository;

    public Optional<Warehouse> findWarehouse(Long id) {
        return warehouseRepository.findById(id);
    }
}
