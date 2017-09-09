package org.lab.insurance.asset.gateway.domain;

import org.springframework.hateoas.ResourceSupport;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AssetResource extends ResourceSupport {

	private String isin;
	private String name;

}
