package spring.boot.webflu.ms.cuenta.credito.app.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import spring.boot.webflu.ms.cuenta.credito.app.documents.TypeCreditProduct;


public interface TypeCreditProductDao extends ReactiveMongoRepository<TypeCreditProduct, String> {
	
}
