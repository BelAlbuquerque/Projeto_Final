package br.com.meli.desafio_final.service.implementation;

import br.com.meli.desafio_final.dto.UserDto;
import br.com.meli.desafio_final.exception.BadRequest;
import br.com.meli.desafio_final.model.entity.Agent;
import br.com.meli.desafio_final.model.entity.Buyer;
import br.com.meli.desafio_final.model.entity.Seller;
import br.com.meli.desafio_final.model.entity.User;
import br.com.meli.desafio_final.repository.AgentRepository;
import br.com.meli.desafio_final.repository.BuyerRepository;
import br.com.meli.desafio_final.repository.SellerRepository;
import br.com.meli.desafio_final.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.beans.Transient;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AgentRepository agentRepository;

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    BuyerRepository buyerRepository;

    @Autowired
    WarehouseService warehouseService;

    private User saveUser(UserDto user) {
        User newUser = new User();
        newUser.setPassword(user.getPassword());
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        newUser.setRole(user.getRole());
        return userRepository.save(newUser);
    }

    private void saveAgent(UserDto user, Long agentId) {
        warehouseService.findWarehouse(user.getWarehouse().getId()).orElseThrow(() -> { throw new BadRequest("Armazem não existe em nosso banco de dados");});
        Agent agent = new Agent();
        agent.setId(agentId);
        agent.setName(user.getName());
        agent.setWarehouse(user.getWarehouse());
        agentRepository.save(agent);
    }

    private void saveSeller(User user) {
        Seller seller = new Seller();
        seller.setName(user.getName());
        seller.setId(user.getId());
        sellerRepository.save(seller);
    }

    private void saveBuyer(User user) {
        Buyer buyer = new Buyer();
        buyer.setId(user.getId());
        buyer.setName(user.getName());
    }

    @Transactional
    public User save(UserDto user) {
        User newUser = null;
        if (user.getRole().equalsIgnoreCase("Agent")) {
            if(user.getWarehouse() == null) throw new BadRequest("Para cadastrar um representante, é necessário informar o id do armazem.");
            newUser = saveUser(user);
            saveAgent(user, newUser.getId());
        }
        if (user.getRole().equalsIgnoreCase("seller")) {
            newUser = saveUser(user);
            saveSeller(newUser);
        }
        if (user.getRole().equalsIgnoreCase("Buyer")) {
            newUser = saveUser(user);
            saveBuyer(newUser);
        }
        if(newUser == null) throw new BadRequest("Campos inválidos.");
        return newUser;
    }
}
