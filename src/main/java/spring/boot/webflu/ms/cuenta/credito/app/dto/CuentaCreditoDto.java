package spring.boot.webflu.ms.cuenta.credito.app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CuentaCreditoDto {

	private String dni;
	private String numeroCuenta;
	private Double credito;
	private Double saldo;
	private Double consumo;
}
