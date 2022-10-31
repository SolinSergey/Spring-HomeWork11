package ru.gb.homework11.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.homework11.entities.Product;

public interface ProductRepository extends JpaRepository<Product,Long> {

}
