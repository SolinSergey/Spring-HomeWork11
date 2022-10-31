package ru.gb.homework11.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.gb.homework11.entities.Product;
import ru.gb.homework11.repositories.ProductRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private ProductRepository productRepository;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public int getAmountPages() {
        int fullAmountRecord = 0;
        List<Product> list = productRepository.findAll();
        if (list.size() % 10 != 0) {
            fullAmountRecord = list.size() / 10 + 1;
        } else {
            fullAmountRecord =  list.size()/ 10;
        }
        return fullAmountRecord;
    }

    public List<Product> findAll(int page) {
        Page <Product> pageres = productRepository.findAll(PageRequest.of(page-1, 10));
        List <Product> pr=new ArrayList<>();
        for(Product p:pageres){
            pr.add(p);
        }
        return pr;
    }

    @Transactional
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
    @Transactional
    public void addProduct(Product product) {
        productRepository.save(product);
    }
}
