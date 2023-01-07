/*
 * Copyright (c) 2022 Ideas2it, Inc.All rights are reserved.
 * 
 * This document is protected by copyright. No part of this document may be 
 * reproduced in any form by any means without prior written authorization of 
 * Ideas2it and its licensors, if any.
 */
package com.ideas2it.onlinestore.util.mapper;

import org.springframework.stereotype.Component;

import com.ideas2it.onlinestore.dto.BrandDTO;
import com.ideas2it.onlinestore.model.Brand;

/**
 * Converts brand.
 *
 * @author Sangeetha Ilangovan
 * @version 1.0
 * @since 21.12.2022
 */
@Component
public class BrandMapper {

	/**
	 * Converts brandDTO to brand
	 * 
	 * @param brandDTO
	 * @return brand
	 */
	public Brand convertBrandDTOToBrand(BrandDTO brandDTO) {
		Brand brand = null;
		
		if (null != brandDTO.getId()) {
		    brand.setId(brandDTO.getId());
		}
		brand.setName(brandDTO.getName());
		return brand;
	}
	
	/**
	 * Converts brand to brandDTO
	 * 
	 * @param brand
	 * @return brandDTO
	 */
	public BrandDTO convertBrandToBrandDTO(Brand brand) {
		BrandDTO brandDTO = new BrandDTO();
		brandDTO.setId(brand.getId());
		brandDTO.setName(brand.getName());
		return brandDTO;
	}
}
