package au.com.geekseat.theta.service;

import au.com.geekseat.theta.model.Shop;
import au.com.geekseat.theta.repository.ShopRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ShopService {
    private final ShopRepository repository;

    public ShopService(ShopRepository repository) {
        this.repository = repository;
    }

    public List<Shop> findAll() {
        return repository.findAll();
    }

    public Shop save(Shop shop) {
        return repository.save(shop);
    }
}
