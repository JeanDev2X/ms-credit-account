package spring.boot.webflu.ms.cuenta.credito.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import spring.boot.webflu.ms.cuenta.credito.app.documents.ProductCredit;
import spring.boot.webflu.ms.cuenta.credito.app.documents.TypeCreditProduct;
import spring.boot.webflu.ms.cuenta.credito.app.dto.CuentaCreditoDto;
import spring.boot.webflu.ms.cuenta.credito.app.service.ProductCreditService;
import spring.boot.webflu.ms.cuenta.credito.app.service.TypeCreditProductService;

@RequestMapping("/api/ProductCredit")
@RestController
public class ProductCreditControllers {

	@Autowired
	private ProductCreditService productoService;

	@Autowired
	private TypeCreditProductService tipoProductoService;

	//LISTAR LAS CUENTAS DE CREDITO
	@GetMapping
	public Mono<ResponseEntity<Flux<ProductCredit>>> findAll() {
		return Mono.just(
				ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(productoService.findAllProducto())

		);
	}
	
	//BUSCAR LAS CUENTAS POR ID	
	@GetMapping("/{id}")
	public Mono<ResponseEntity<ProductCredit>> viewId(@PathVariable String id) {
		return productoService.findByIdProducto(id)
				.map(p -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(p))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	//BUSCAR LAS CUENTAS POR ID	
	@PutMapping
	public Mono<ProductCredit> updateProducto(@RequestBody ProductCredit producto) {
		System.out.println(producto.toString());
		return productoService.saveProductoCredito(producto);
	}

	//REGISTRAR UN PRODUCTO DE CREDITO
	/*
		Un cliente puede tener un producto de crédito sin la obligación de tener una cuenta bancaria en la institución.
	*/
	@PostMapping
	public Mono<ProductCredit> registrarProductoCredito(@RequestBody ProductCredit pro) {
		
		// BUSCA SI EL TIPO DE CREDITO EXISTE
		Mono<TypeCreditProduct> tipo = tipoProductoService.findByIdTipoProducto(pro.getTipoProducto().getId());
		return tipo.defaultIfEmpty(new TypeCreditProduct()).flatMap(c -> {
			if (c.getId() == null) {
				return Mono.error(new InterruptedException("NO EXISTE ESTE TIPO"));
			}
			return Mono.just(c);
		}).flatMap(t -> {
			pro.setTipoProducto(t);
			return productoService.saveProductoCredito(pro);
		});
	}

	//GUARDA CUENTA PRODUCTO BANCO DE CREDITO - SIN VALIDACION	
	@PostMapping("/guardarCredito")
	public Mono<ProductCredit> guardarProBanco(@RequestBody ProductCredit cuentaCredito) {
		return productoService.saveProductoCredito(cuentaCredito);
	}
	
	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
		System.out.println("eliminar producto credito");
		return productoService.findByIdProducto(id)
				.flatMap(s -> {
			return productoService.delete(s).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
		}).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NO_CONTENT));
	}
	
	//==============================
		
	@GetMapping("/dni/{dni}")
	public Flux<ProductCredit> productosCreditoCliente(@PathVariable String dni) {
		Flux<ProductCredit> credito = productoService.productoCreditoCliente(dni);
		return credito;
	}
	
	//consumir tarjeta de credito
	//Un cliente puede cargar consumos a sus tarjetas de crédito en base a su límite de crédito
	@PutMapping("/consumo/{monto}/{numero_cuenta}/{codigo_bancario}")
	public Mono<ProductCredit> retiroCredito(@PathVariable Double monto,@PathVariable String numero_cuenta,@PathVariable String codigo_bancario) {
			return productoService.consumosCredito(monto, numero_cuenta,codigo_bancario);
	}
	
	//Pago de credito
	@PutMapping("/pago/{numero_cuenta}/{monto}/{codigo_bancario}")
	public Mono<ProductCredit> despositoCredito(@PathVariable Double monto,@PathVariable String numero_cuenta,@PathVariable String codigo_bancario) {		
			return productoService.pagosCredito(monto, numero_cuenta,codigo_bancario);
	}
	
	//CUENTAS SIN DEUDA
	@GetMapping("/dniSinDeuda/{dni}")
	public Flux<ProductCredit> cuentasSinDeuda(@PathVariable String dni) {
			Flux<ProductCredit> credito = productoService.cuentaSinConsumo(dni);
			//TODO : Implementar mensaje si no tiene deuda
			return credito;

	}
	
	//MUESTRA LA CUENTA BANCARIA CON EL NUMERO DE CUENTA
	@GetMapping("/numero_cuenta/{numero_cuenta}/{codigo_bancario}")
	public Mono<ProductCredit> cuentaBancariaCredito(@PathVariable String numero_cuenta, @PathVariable String codigo_bancario) {
		Mono<ProductCredit> credito = productoService.productosCredito(numero_cuenta, codigo_bancario);
		return credito;
	}
	
	//MUESTRA LOS SALDOS - POR NUMERO DE CUENTA
	@GetMapping("/saldoDisponible/{numero_cuenta}/{codigo_bancario}")
	public Mono<CuentaCreditoDto> saldosCredito(@PathVariable String numero_cuenta, @PathVariable String codigo_bancario) {
		
		//TODO : Adaptar a tarjetas de credito
		Mono<ProductCredit> operacion = productoService.productosCredito(numero_cuenta, codigo_bancario);

		return operacion.flatMap(c -> {

			CuentaCreditoDto cd = new CuentaCreditoDto();
			
				cd.setDni(c.getDni());
				cd.setNumeroCuenta(c.getNumeroCuenta());
				cd.setSaldo(c.getSaldo());
		
				cd.setConsumo(c.getConsumo());
				cd.setCredito(c.getCredito());
			
			return Mono.just(cd);
		});

	}
	
	
}
