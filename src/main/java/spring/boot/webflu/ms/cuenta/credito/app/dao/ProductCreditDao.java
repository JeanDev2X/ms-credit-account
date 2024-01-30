package spring.boot.webflu.ms.cuenta.credito.app.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Flux;
import spring.boot.webflu.ms.cuenta.credito.app.documents.ProductCredit;

public interface ProductCreditDao extends ReactiveMongoRepository<ProductCredit, String> {
	
	Flux<ProductCredit> findByDni(String dni);
	
}
