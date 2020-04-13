package com.inventory.service.implementation;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.inventory.repository.SaleRepository;
import com.inventory.service.CustomerService;
import com.inventory.service.ProductService;
import com.inventory.service.SaleService;
import com.inventory.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventory.model.Customer;
import com.inventory.model.Product;
import com.inventory.model.Sale;
import com.inventory.model.User;

@Service
public class SaleServiceImpl implements SaleService {
	
	@Autowired
	private SaleRepository saleRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CustomerService customerService;
	
	private ConcurrentMap<Long, Long> productCart = new ConcurrentHashMap<Long, Long>();

	@Override
	public Collection<Sale> findAll() {
		Collection<Sale> sales = saleRepository.findAll();
		return sales;
	}

	@Override
	public Sale findById(Long id){
		Sale sale = saleRepository.findOne(id);
		return sale;
	}
	
	@Override
	public Sale create(Long quantity, Long agentId, Long productId, Long customerId) {
		User user = userService.findById(agentId);
		Customer customer = customerService.findById(customerId);
		Product product = productService.findById(productId);

		/*for(Map.Entry<Long, Long> cartEntry : getCart().entrySet()){
			productId = cartEntry.getKey();
			Long productCount = cartEntry.getValue();
				
			Product product = productService.findById(productId);
			quantityInStock = product.getQuantity();
				
			if(quantityInStock < productCount){
					return null;
			}
				
			else{
				totalProductPrice = productCount * product.getUnitPrice();
				totalSalePrice += totalProductPrice;
			}	
		
		} */
		if(product.getQuantity() < quantity){
			return null;
		}
		
		else{
			
			Long quantityProductLeft = product.getQuantity() - quantity;
			product.setQuantity(quantityProductLeft);
			Sale sale = new Sale(30.0, quantity, user, product,customer);
			saleRepository.save(sale);
			
			return sale;
		}
	}
	
	/**
	 * This method inserts a valid scanned product in to the in-memory 
	 * data structure used to store product contents.
	 */
	@Override
	public Sale addProduct(Sale sale){
		return null;	
	}
	
	/**
	 * This method returns the cart in its current state. 
	 */
	@Override
	public Map<Long, Long> getCart(){
		return productCart;
	}

}
