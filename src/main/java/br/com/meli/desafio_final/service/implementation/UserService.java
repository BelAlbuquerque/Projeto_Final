package br.com.meli.desafio_final.service.implementation;

import br.com.meli.desafio_final.configJwt.TokenService;
import br.com.meli.desafio_final.dto.AllBuyersBySellerDto;
import br.com.meli.desafio_final.dto.AllSellersByBuyerDto;
import br.com.meli.desafio_final.dto.TokenDto;
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
import br.com.meli.desafio_final.request.LoginRequest;
import br.com.meli.desafio_final.request.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
public class UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

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

    /**
     * Esse metodo salva um novo usuário do DB na tabela User
     * @param user
     * @return
     */
    private User saveUser(UserRequest user) {
        User newUser = new User();
        newUser.setPassword(user.getPassword());
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        newUser.setRole(user.getRole());
        return userRepository.save(newUser);
    }

    /**
     * Esse método salva um novo Agent no DB na tabela Agent
     * @param user
     * @param agentId
     */
    private void saveAgent(UserRequest user, Long agentId) {
        warehouseService.findWarehouse(user.getWarehouse().getId()).orElseThrow(() -> { throw new BadRequest("Armazem não existe em nosso banco de dados");});
        Agent agent = new Agent();
        agent.setId(agentId);
        agent.setName(user.getName());
        agent.setWarehouse(user.getWarehouse());
        agentRepository.save(agent);
    }

    /**
     * Esse método salva um novo Seller no DB na tabela Seller
     * @param user
     */
    private void saveSeller(User user) {
        Seller seller = new Seller();
        seller.setName(user.getName());
        seller.setId(user.getId());
        sellerRepository.save(seller);
    }

    /**
     * Esse método salva um novo Buyer no DB na tabela Buyer
     * @param user
     */
    private void saveBuyer(User user) {
        Buyer buyer = new Buyer();
        buyer.setId(user.getId());
        buyer.setName(user.getName());
        buyerRepository.save(buyer);
    }

    /**
     * Essem método recebe um UserRequest do controller, valida e salva de acordo com a role do User.
     * @param user
     * @return
     */
    @Transactional
    public UserDto saveNewUser(UserRequest user) {
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
        return new UserDto(newUser);
    }

    /**
     * Esse metodo recebe um LoginRequest, valida com o DB e devolve um token.
     * @param loginRequest
     * @return
     */
    public TokenDto userLogin (LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken loginData = loginRequest.convert();
        Authentication authentication = authenticationManager.authenticate(loginData);
        String token = tokenService.generateToken(authentication);
        return new TokenDto(token, "Bearer");
    }

    /**
     * Esse método pede pro DB uma lista de todos os Sellers que o buyerId já comprou.
     * @param buyerId
     * @return
     */
    public List<AllSellersByBuyerDto> findAllSellersByBuyer(Long buyerId) {
        return userRepository.findAllSellesByBuyer(buyerId);
    }

    /**
     * Esse método pede pro DB uma lista de todos os Buyers que já compraram desse sellerId.
     * @param sellerId
     * @return
     */
    public List<AllBuyersBySellerDto> findAllBuyersBySeller(Long sellerId) {
        return userRepository.findAllBuyersBySeller(sellerId);
    }
}
