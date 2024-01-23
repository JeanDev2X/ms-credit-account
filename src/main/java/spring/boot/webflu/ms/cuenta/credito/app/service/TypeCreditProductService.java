package spring.boot.webflu.ms.cuenta.credito.app.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import spring.boot.webflu.ms.cuenta.credito.app.documents.TypeCreditProduct;

public interface TypeCreditProductService {
	
	Flux<TypeCreditProduct> findAllTipoproducto();
	Mono<TypeCreditProduct> findByIdTipoProducto(String id);
	Mono<TypeCreditProduct> saveTipoProducto(TypeCreditProduct tipoProducto);
	Mono<Void> deleteTipo(TypeCreditProduct tipoProducto);
	
}
