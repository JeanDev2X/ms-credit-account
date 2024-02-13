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

	// ----------------------------------------------------------------

	@Override
	public Flux<ProductCredit> productoCreditoCliente(String dni) {
		
		//Si me devulve null o vacio		
		//cunado me devuelve con data
		
		Flux<ProductCredit> productCreditFlux = productoDao.findByDni(dni);
	    return productCreditFlux != null ? productCreditFlux : Flux.empty();
		
		
	}

	@Override
	public Mono<ProductCredit> consumosCredito(Double monto, String numero_cuenta, String codigo_bancario) {
		// verifica si existe el numero de cuenta
		return productoDao.findByNumeroCuentaAndCodigoBanco(numero_cuenta, codigo_bancario).flatMap(c -> {

			System.out.println("Objeto credito -->>>" + c.toString());
			System.out.println("Monto -->>>>" + monto);
						
			if (c.getCredito() >= c.getSaldo()) {
						
				if (monto <= c.getSaldo()) {
					c.setSaldo((c.getSaldo() - monto));
					c.setConsumo(c.getConsumo() + monto);
					return productoDao.save(c);
				} else {
					//return Mono.empty();
					return Mono.error(new InterruptedException("SALDO INSUFICIENTE :"+c.getSaldo() ));
					// throw new RequestException("SALDO INSUFICIENTE");
				}
				
			} else {

				return Mono.empty();
			}

		});
	}

	@Override
	public Mono<ProductCredit> pagosCredito(Double monto, String numero_cuenta, String codigo_bancario) {
		return productoDao.findByNumeroCuentaAndCodigoBanco(numero_cuenta, codigo_bancario).flatMap(c -> {

//			if (c.getConsumo() == 0) {
//				return Mono.error(new InterruptedException("SIN DEUDA"));
//			
//			}else {
//				
//				//ACTUALIZANDO EL SALDO Y EL CONSUMO
//				
//				c.setSaldo((c.getSaldo() + monto));
//				c.setConsumo(c.getConsumo() - monto);
//				return productoDao.save(c);
//			}

			c.setSaldo((c.getSaldo() + monto));

			if (monto > c.getConsumo()) {
				c.setConsumo(0.0);
			} else {
				c.setConsumo(c.getConsumo() - monto);
			}

			return productoDao.save(c);

		});
	}

	@Override
	public Flux<ProductCredit> cuentaSinConsumo(String dni) {
		return productoDao.verDeudaCredito(dni);
	}

	@Override
	public Mono<ProductCredit> productosCredito(String numero_cuenta, String codigo_bancario) {
		return productoDao.findByNumeroCuentaAndCodigoBanco(numero_cuenta, codigo_bancario);
	}

}
