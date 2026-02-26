package com.Telusko.Products.Controller;

import com.Telusko.Products.Model.Product;
import com.Telusko.Products.Service.Productservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin // VERY IMPORTANT for frontend connection
public class HomeController {

    @Autowired
    Productservice service;

    @GetMapping("/")
    public ResponseEntity<List<Product>> home()
    {
        return new ResponseEntity<>(service.getProducts(), HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductByID(@PathVariable int id)
    {
        Product product=service.getProductbyId(id);
        if(product!=null)
            return new ResponseEntity<>(product,HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile imagefile)
    {
        try{
            Product prod = service.addProduct(product,imagefile);
            return new ResponseEntity<>(prod,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id ,@RequestPart Product product, @RequestPart MultipartFile imagefile)
    {
        Product product1 = null;
        try {
            product1 = service.upadateProduct(id,product,imagefile);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed",HttpStatus.BAD_REQUEST);
        }
        if(product1!=null)
            return new ResponseEntity<>("Updated",HttpStatus.OK);

        return new ResponseEntity<>("Failed",HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/product/{id}/image")
    public ResponseEntity<byte[]> getImageById(@PathVariable int id)
    {
        Product product = service.getProductbyId(id);
        byte[] imagefile = product.getImageData();

        return ResponseEntity.ok().contentType(MediaType.valueOf(product.getImageType()))
                .body(imagefile);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deletePrduct(@PathVariable int id)
    {
        Product prod = service.getProductbyId(id);
        if(prod!=null)
        {
            service.DeleteProduct(id);
            return new ResponseEntity<>("Deleted",HttpStatus.OK);
        }
        return new ResponseEntity<>("Not Deleted",HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/product/search/{keyword}")
    public ResponseEntity<List<Product>> searchByKey(@PathVariable String keyword)
    {
        List<Product> products = service.searchByKey(keyword);
        return new ResponseEntity<>(products,HttpStatus.OK);
    }

}
