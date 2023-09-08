package com.epam;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import com.epam.dto.Credentials;
import com.epam.dto.request.TraineeRegistrationRequest;

import jakarta.validation.Valid;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTest {

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";

    private static RestTemplate restTemplate;

    @Autowired
    private TestH2Repository h2Repository;

    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void setUp() {
        baseUrl = baseUrl.concat(":").concat(port + "").concat("/products");
    }
    
//    @PostMapping("/registration")
//	public ResponseEntity<Credentials> traineeRegistrartion(
//			@RequestBody @Valid TraineeRegistrationRequest traineeRegistrationRequest) {
//		log.info("TraineeController : traineeRegistration ");
//		return new ResponseEntity<>(traineeService.traineeRegistration(traineeRegistrationRequest), HttpStatus.CREATED);
//
//	}
    
    

//    @Test
//    public void testAddProduct() {
//        Product product = new Product("headset", 2, 7999);
//        Product response = restTemplate.postForObject(baseUrl, product, Product.class);
//        assertEquals("headset", response.getName());
//        assertEquals(1, h2Repository.findAll().size());
//    }
//
//    @Test
//    @Sql(statements = "INSERT INTO PRODUCT_TBL (id,name, quantity, price) VALUES (4,'AC', 1, 34000)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//    @Sql(statements = "DELETE FROM PRODUCT_TBL WHERE name='AC'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
//    public void testGetProducts() {
//        List<Product> products = restTemplate.getForObject(baseUrl, List.class);
//        assertEquals(1, products.size());
//        assertEquals(1, h2Repository.findAll().size());
//    }
//
//    @Test
//    @Sql(statements = "INSERT INTO PRODUCT_TBL (id,name, quantity, price) VALUES (1,'CAR', 1, 334000)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//    @Sql(statements = "DELETE FROM PRODUCT_TBL WHERE id=1", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
//    public void testFindProductById() {
//        Product product = restTemplate.getForObject(baseUrl + "/{id}", Product.class, 1);
//        assertAll(
//                () -> assertNotNull(product),
//                () -> assertEquals(1, product.getId()),
//                () -> assertEquals("CAR", product.getName())
//        );
//
//    }
//
//    @Test
//    @Sql(statements = "INSERT INTO PRODUCT_TBL (id,name, quantity, price) VALUES (2,'shoes', 1, 999)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//    @Sql(statements = "DELETE FROM PRODUCT_TBL WHERE id=1", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
//    public void testUpdateProduct(){
//        Product product = new Product("shoes", 1, 1999);
//        restTemplate.put(baseUrl+"/update/{id}", product, 2);
//        Product productFromDB = h2Repository.findById(2).get();
//        assertAll(
//                () -> assertNotNull(productFromDB),
//                () -> assertEquals(1999, productFromDB.getPrice())
//        );
//
//
//
//    }
//
//    @Test
//    @Sql(statements = "INSERT INTO PRODUCT_TBL (id,name, quantity, price) VALUES (8,'books', 5, 1499)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//    public void testDeleteProduct(){
//        int recordCount=h2Repository.findAll().size();
//        assertEquals(1, recordCount);
//        restTemplate.delete(baseUrl+"/delete/{id}", 8);
//        assertEquals(0, h2Repository.findAll().size());
//
//    }


}
