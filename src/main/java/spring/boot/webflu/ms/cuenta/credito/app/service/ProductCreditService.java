package spring.boot.webflu.ms.cuenta.credito.app.service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import spring.boot.webflu.ms.cuenta.credito.app.documents.ProductCredit;

public interface ProductCreditService {

	Flux<ProductCredit> findAllProducto();
	Mono<ProductCredit> findByIdProducto(String id);
	Mono<ProductCredit> saveProductoCredito(ProductCredit clientePersonal); //saveProducto
	Mono<Void> delete(ProductCredit prod); //deleteProducto
	
	//------------------------------------------------------------
	
	Flux<ProductCredit> productoCreditoCliente(String dni); //listarProductoCreditoCliente
		
}
