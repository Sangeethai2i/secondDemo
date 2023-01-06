/*
 * Copyright (c) 2022 Ideas2it, Inc.All rights are reserved.
 *
 * This document is protected by copyright. No part of this document may be
 * reproduced in any form by any means without prior written authorization of
 * Ideas2it and its licensors, if any.
 */
package com.ideas2it.onlinestore.service;

import java.util.List;

import com.ideas2it.onlinestore.dto.CategoryDTO;
import com.ideas2it.onlinestore.util.customException.OnlineStoreException;

/**
 * This interface handles read all categories, read sub categories by category name 
 * for category model
 *
 * @version 1.0
 * @author arunkumar
 */
public interface CategoryService {
	
	/**
	 * <p>
	 * This method is used save category object into database
	 * This category DTO object come from the category controller
	 * inside this method the category DTO object convert into 
	 * category object by using category mapper. Then it will
	 * call a category repository method to put a category object 
	 * into database.
	 * </p>
	 * 
	 * @return categoryDTO - details of the category DTO
	 * @throws OnlineStoreException - occur category object is not saved into database
	 */
	public CategoryDTO createCategory(CategoryDTO categoryDTO);

	/**
	 * <p>
	 * This method is used get all categories
	 * this method fetch the non deleted records
	 * from database then it will give list of category 
	 * object. This list of category object convert into
	 * list of category DTO object by using category mapper.
	 * </p>
	 * 
	 * @return List<CategoryDTO> - list of categoryDTO object
	 * @throws OnlineStoreException - occur data could be empty
	 */
	public List<CategoryDTO> getCategories();
	
	/**
	 * <p>
	 * This method is used category and get sub categories by using category name.
	 * This method get the category name from controller and it check 
	 * if category name valid it get the category object inside that object
	 * I have list of sub categories based on 
	 * that category name then it will convert category DTO object 
	 * and send this object wherever this method was called
	 * else it will throw exception. 
	 * </p>
	 * 
	 * @param categoryName - user given category name
	 * @return List<CategoryDTO> - list of sub category product
	 * @throws OnlineStoreException - occur searching name shouldn't present in database
	 */
	public CategoryDTO getCategoryByName(String categoryName);
}
