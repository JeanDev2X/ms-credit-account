package spring.boot.webflu.ms.cuenta.credito.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Flux;
import spring.boot.webflu.ms.cuenta.credito.app.service.ProductCreditService;
import spring.boot.webflu.ms.cuenta.credito.app.service.TypeCreditProductService;
import spring.boot.webflu.ms.cuenta.credito.app.documents.ProductCredit;
import spring.boot.webflu.ms.cuenta.credito.app.documents.TypeCreditProduct;

@SpringBootApplication
public class SpringBootWebfluMsCuentaCreditoApplication implements CommandLineRunner{

	@Autowired
	private ProductCreditService serviceCredito;
	
	@Autowired
	private TypeCreditProductService serviceTipoCredito;
	
	@Autowired
	private ReactiveMongoTemplate mongoTemplate;
	
	private static final Logger log = LoggerFactory.getLogger(SpringBootWebfluMsCuentaCreditoApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootWebfluMsCuentaCreditoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		mongoTemplate.dropCollection("ProductCredit").subscribe();
		mongoTemplate.dropCollection("TypeCreditProduct").subscribe();
		
		TypeCreditProduct creditoPersonal = new TypeCreditProduct("1","creditoPersonal");
		TypeCreditProduct creditoEmpresarial = new TypeCreditProduct("2","creditoEmpresarial");
		TypeCreditProduct tarjetaCreditoPersonal = new TypeCreditProduct("3","tarjetaCreditoPersonal");
		TypeCreditProduct tarjetaCreditoEmpresarial = new TypeCreditProduct("4","tarjetaCreditoEmpresarial");
		
		
		//
		Flux.just(creditoPersonal,creditoEmpresarial,tarjetaCreditoPersonal,tarjetaCreditoEmpresarial)
		.flatMap(serviceTipoCredito::saveTipoProducto)
		.doOnNext(c -> {
			log.info("Tipo de producto creado: " +  c.getDescripcion() + ", Id: " + c.getId());
		}).thenMany(					
				Flux.just(
						new ProductCredit("47305710","450001","9057880460332731",creditoPersonal,5000.0,5000.0,0.0,"bcp"),
						new ProductCredit("47305711","450002","9057880460332833",creditoEmpresarial,2000.0,2000.0,0.0,"bcp"),
						new ProductCredit("47305712","450003","9057880460330004",tarjetaCreditoPersonal,5000.0,4000.0,1000.0,"bcp"),
						new ProductCredit("47305713","450004","9057880460334500",tarjetaCreditoEmpresarial,6000.0,1500.0,4500.0,"bcp"),
						new ProductCredit("99091450","450077","9057880460334577",tarjetaCreditoEmpresarial,4000.0,4000.0,0.0,"bcp"),
						new ProductCredit("99091440","450088","9057880460334590",tarjetaCreditoEmpresarial,5000.0,1800.0,3200.0,"bcp")
						
						)					
					.flatMap(procredito -> {
						return serviceCredito.saveProductoCredito(procredito);
					})					
				).subscribe(procredito -> log.info("Insert: " + procredito.getId() + " " + procredito.getNumeroCuenta()));
		
		
	}

}
