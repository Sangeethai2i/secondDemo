/*
 * Copyright (c) 2022 Ideas2it, Inc.All rights are reserved.
 *
 * This document is protected by copyright. No part of this document may be
 * reproduced in any form by any means without prior written authorization of
 * Ideas2it and its licensors, if any.
 */
package com.ideas2it.onlinestore.util.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ideas2it.onlinestore.dto.CategoryDTO;
import com.ideas2it.onlinestore.model.Category;

/**
 * This class convert entity to DTO, DTO to entity
 * 
 * @author arunkumar
 * @version 1.0
 */
@Component
public class CategoryMapper {

	/**
	 * <p>
	 * This method is used change the entity object to 
	 * DTO object. This entity is comes from category service 
	 * and create one empty object for category DTO and 
	 * get the value from category object and set the value 
	 * into category DTO then It will return category DTO object
	 * whenever this method was called.
	 * </p> 
	 * 
	 * @param category
	 * @return categoryDTO
	 */
	public CategoryDTO convertEntityToDTO(Category category) {
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setId(category.getId());
		categoryDTO.setName(category.getName());
		categoryDTO.setCategory(category.getCategory());
		categoryDTO.setSubCategories(category.getSubCategories());
		return categoryDTO;
	}
	
	/**
	 * <p>
	 * This method is used change the DTO object to 
	 * entity object. This category DTO is comes from category service 
	 * and create one empty object for category and 
	 * get the value from category DTO object and set the value 
	 * into category then It will return category object
	 * whenever this method was called. 
	 * </p>
	 * 
	 * @param categoryDTO
	 * @return category
	 */
	public Category convertDTOToEntity(CategoryDTO categoryDTO) {
		Category category = new Category();
		category.setId(categoryDTO.getId());
		category.setName(categoryDTO.getName());
		category.setCategory(categoryDTO.getCategory());
		category.setSubCategories(categoryDTO.getSubCategories());
		return category;
	}
	
	/**
	 * <p>
	 * This method is used change the list of entity object to 
	 * list of DTO object. This list of category is comes from category service 
	 * and create one empty list for category DTO and 
	 * add the value from list of category object to list of category DTO object 
	 * then It will return list of category DTO object
	 * whenever this method was called.  
	 * </p>
	 * 
	 * @param categories
	 * @return categoriesDTO
	 */
	public List<CategoryDTO> convertEntityToDTO(List<Category> categories) {
        List<CategoryDTO> categoriesDTO = new ArrayList<>();
        for (Category category: categories) {
        	categoriesDTO.add(convertEntityToDTO(category));
        }
		return categoriesDTO;
	}
}
