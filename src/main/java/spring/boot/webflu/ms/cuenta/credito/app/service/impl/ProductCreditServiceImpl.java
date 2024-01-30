package spring.boot.webflu.ms.cuenta.credito.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import spring.boot.webflu.ms.cuenta.credito.app.dao.ProductCreditDao;
import spring.boot.webflu.ms.cuenta.credito.app.documents.ProductCredit;
import spring.boot.webflu.ms.cuenta.credito.app.service.ProductCreditService;

@Service
public class ProductCreditServiceImpl implements ProductCreditService {

	@Autowired
	public ProductCreditDao productoDao;

	@Override
	public Flux<ProductCredit> findAllProducto() {
		return productoDao.findAll();
	}

	@Override
	public Mono<ProductCredit> findByIdProducto(String id) {
		return productoDao.findById(id);
	}

	@Override
	public Mono<ProductCredit> saveProductoCredito(ProductCredit clientePersonal) {
		return productoDao.save(clientePersonal);
	}

	@Override
	public Mono<Void> delete(ProductCredit prod) {		
		return productoDao.delete(prod);
	}

	//----------------------------------------------------------------

	@Override
	public Flux<ProductCredit> productoCreditoCliente(String dni) {
		return productoDao.findByDni(dni);
	}
	
}
