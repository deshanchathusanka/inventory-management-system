package com.inventory.service.implementation;

import java.util.Collection;
import java.util.List;

import com.inventory.repository.CategoryRepository;
import com.inventory.service.CategoryService;
import com.inventory.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventory.model.Category;
import com.inventory.model.Product;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ProductService productService;

	@Override
	public Collection<Category> findAll() {
		
		Collection<Category> categories = categoryRepository.findBycategoryEnable(true);
		return categories;
		
	}

	@Override
	public Category findById(Long id) {
		
		Category category = categoryRepository.findOne(id);
		return category;
		
	}

	@Override
	public Category findByName(String name) {
		
		Category category = categoryRepository.findByName(name);
		return category;
	}

	@Override
	public Category create(Category category) {
		
		if (category.getName() == null) {
			//cannot create category without a name
			return null;
		}
		
		categoryRepository.save(category);
		return category;
	}

	@Override
	public Category update(Category category) {
		
		if (category.getName() == null) {
			//cannot update category without a category name
			return null;
		}
		
		categoryRepository.save(category);
		return category;
		
	}

	@Override
	public void delete(Long id) {
		
		Category category = findById(id);
		if (category == null) {
			return;
		}
		
		category.setCategoryEnable(false);
		categoryRepository.save(category);
		
	}

	@Override
	public Collection<Product> addProduct(Long categoryId, Long productId) {
		
		Category category = findById(categoryId);
		Product product = productService.findById(productId);
		if (category == null || product == null ) {
			return null;
		}
				
		List<Product> products = category.getProducts();
		products.add(product);
		category.setProducts(products);
		categoryRepository.save(category);
		return category.getProducts();
	}

}
