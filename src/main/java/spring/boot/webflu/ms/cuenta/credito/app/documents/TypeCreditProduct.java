package spring.boot.webflu.ms.cuenta.credito.app.documents;

import javax.validation.constraints.NotEmpty;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection ="TypeCreditProduct")
public class TypeCreditProduct {

	@NotEmpty
	private String id;
	@NotEmpty
	private String descripcion;
	
	public TypeCreditProduct() {

	}

	public TypeCreditProduct(String id, String descripcion) {
		this.id = id;
		this.descripcion = descripcion;
	}
	
}
