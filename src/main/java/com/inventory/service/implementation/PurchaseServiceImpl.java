package com.inventory.service.implementation;

import java.util.Collection;
import java.util.Date;

import com.inventory.repository.PurchaseRepository;
import com.inventory.service.ProductService;
import com.inventory.service.PurchaseService;
import com.inventory.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventory.model.Product;
import com.inventory.model.Purchase;
import com.inventory.model.Supplier;

@Service
public class PurchaseServiceImpl implements PurchaseService {
	
	@Autowired
	private PurchaseRepository purchaseRepository;
	
	@Autowired
	private SupplierService supplierService;
	
	@Autowired
	private ProductService productService;

	@Override
	public Collection<Purchase> findAll() {
		
		Collection<Purchase> purchase = purchaseRepository.findAll();
		return purchase;
		
	}

	@Override
	public Collection<Purchase> findByDate(Date date) {
		
		Collection<Purchase> purchase = purchaseRepository.findBytransactionDate(date);
		return purchase;
		
	}

	@Override
	public Purchase create(Long supplierId, Long productId, Integer quantity) {
		
		Supplier supplier = supplierService.findById(supplierId);
		Product product = productService.findById(productId);
//		if ( product.getQuantity() < quantity ) {
//			return null;
//		}
		
		long newProductQuantity = product.getQuantity() + quantity;
		product.setQuantity(newProductQuantity);
		Purchase purchase = new Purchase(product, supplier, quantity);
		purchaseRepository.save(purchase);
		return purchase;
		
	}
	
}