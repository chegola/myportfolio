package com.nadee.myportfolio.model;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class HoldingKey implements Serializable {
	private static final long serialVersionUID = -8231278639938579673L;
	private Integer portfolio;
	private Integer security;
}
