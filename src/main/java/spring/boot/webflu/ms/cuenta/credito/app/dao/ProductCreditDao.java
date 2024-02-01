package spring.boot.webflu.ms.cuenta.credito.app.dao;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import spring.boot.webflu.ms.cuenta.credito.app.documents.ProductCredit;

public interface ProductCreditDao extends ReactiveMongoRepository<ProductCredit, String> {
	
	Flux<ProductCredit> findByDni(String dni);
	Mono<ProductCredit> findByNumeroCuentaAndCodigoBanco(String numero_cuenta, String codigo_bancario);
	@Query("{ 'dni' : ?0 , $where : 'this.consumo > 0'}  }")
	Flux<ProductCredit> verDeudaCredito(String dni);	
}
