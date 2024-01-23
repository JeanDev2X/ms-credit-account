package spring.boot.webflu.ms.cuenta.credito.app.documents;

import javax.validation.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Document(collection ="ProductCredit")
public class ProductCredit {
	

	@Id
	@NotEmpty
	private String id;
	
	@NotEmpty
	@Field(name="numero_cuenta")
	private String numeroCuenta;

	@NotEmpty
	private String dni;
	
	@NotEmpty
	private TypeCreditProduct tipoProducto;
	
	@NotEmpty
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String fecha_afiliacion;
	
	@NotEmpty
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String fecha_caducidad;
	@NotEmpty
	private Double credito;
	@NotEmpty
	private Double saldo;
	@NotEmpty
	private Double consumo;
	
	@NotEmpty
	@Field(name="codigo_bancario")
	private String codigoBanco;
	
	public ProductCredit() {
	
	}

	public ProductCredit(String numeroCuenta,String dni,TypeCreditProduct tipoProducto,
			Double credito,Double saldo, Double consumo,String codigoBanco) {
		this.numeroCuenta = numeroCuenta;
		this.dni = dni;
		this.tipoProducto = tipoProducto;
		this.credito = credito;
		this.saldo = saldo;
		this.consumo = consumo;
		this.codigoBanco = codigoBanco;
	}
	
	//private tipoProducto tipoCliente;
	
//	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
//	public Date fecha_afiliacion() {
//		return fecha_afiliacion;
//	}
//	
//	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
//	public Date fecha_caducidad() {
//		return fecha_caducidad;
//	}
	
}










