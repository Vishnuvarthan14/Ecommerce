package com.Telusko.Products.Service;

import com.Telusko.Products.Model.Product;
import com.Telusko.Products.Repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class Productservice {
    @Autowired
    ProductRepo repo;

    public List<Product> getProducts()
    {
        return repo.findAll();
    }

    public Product addProduct(Product product, MultipartFile imagefile) throws IOException {
        product.setImageName(imagefile.getOriginalFilename());
        product.setImageType(imagefile.getContentType());
        product.setImageData(imagefile.getBytes());
        return repo.save(product);
    }

    public Product getProductbyId(int id) {
        return repo.findById(id).orElse(null);
    }

    public Product upadateProduct(int id, Product product, MultipartFile imagefile) throws IOException {
        Product existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        existing.setName(product.getName());
        existing.setDescription(product.getDescription());
        existing.setBrand(product.getBrand());
        existing.setPrice(product.getPrice());
        existing.setCategory(product.getCategory());
        existing.setReleaseDate(product.getReleaseDate());
        existing.setQuantity(product.getQuantity());
        existing.setAvailable(product.isAvailable());

        if (imagefile != null && !imagefile.isEmpty()) {
            existing.setImageData(imagefile.getBytes());
            existing.setImageType(imagefile.getContentType());
            existing.setImageName(imagefile.getOriginalFilename());
        }

        return repo.save(existing);
    }
    public void DeleteProduct(int id)
    {
        repo.deleteById(id);
    }

    public List<Product> searchByKey(String key)
    {
        return repo.searchByKey(key);
    }
//public List<Product> searchByKey(String keyword) {
//    return repo
//            .findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrBrandContainingIgnoreCaseOrCategoryContainingIgnoreCase(
//                    keyword, keyword, keyword, keyword);
//}
}
