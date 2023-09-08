package com.epam;

import org.apache.commons.math.stat.descriptive.summary.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestH2Repository extends JpaRepository<Product, Integer> {
}
