package spring.boot.webflu.ms.cuenta.credito.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import spring.boot.webflu.ms.cuenta.credito.app.dao.TypeCreditProductDao;
import spring.boot.webflu.ms.cuenta.credito.app.documents.TypeCreditProduct;
import spring.boot.webflu.ms.cuenta.credito.app.service.TypeCreditProductService;

@Service
public class TypeCreditProductServiceImpl implements TypeCreditProductService {

	@Autowired
	public TypeCreditProductDao tipoProductoDao;

	@Override
	public Flux<TypeCreditProduct> findAllTipoproducto() {
		return tipoProductoDao.findAll();

	}

	@Override
	public Mono<TypeCreditProduct> findByIdTipoProducto(String id) {		
		return tipoProductoDao.findById(id);

	}

	@Override
	public Mono<TypeCreditProduct> saveTipoProducto(TypeCreditProduct tipoCliente) {
		return tipoProductoDao.save(tipoCliente);
	}

	@Override
	public Mono<Void> deleteTipo(TypeCreditProduct tipoProducto) {
		return tipoProductoDao.delete(tipoProducto);
	}

}
