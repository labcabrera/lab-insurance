package org.lab.insurance.domain.hateoas.insurance;

import org.springframework.hateoas.ResourceSupport;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AssetResource extends ResourceSupport {

	String isin;
	String name;

}
