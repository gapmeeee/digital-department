package com.example.demo.moduls.enums;

import com.example.demo.moduls.Image;
import com.example.demo.moduls.enums.Product;
import com.example.demo.moduls.User;
import com.example.demo.repositoties.ProductRepositories;
import com.example.demo.repositoties.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepositories productRepositories;
    private final UserRepository userRepository;
    public List<Product> listProducts(String title){
        if (title!=null) return productRepositories.findByTitle(title);
        return productRepositories.findAll();
    }

    public void saveProduct(Principal principal, Product product, MultipartFile file1, MultipartFile file2, MultipartFile file3) throws IOException {
        product.setUser(getUserByPrincipal(principal));
        Image image1;
        Image image2;
        Image image3;
        if (file1.getSize()!=0){
            System.out.println("Worked!");
            image1=toImageEntity(file1);
            image1.setPreviewImage(true);
            product.addImageToProduct(image1);
        }
        if (file2.getSize()!=0){
            image2=toImageEntity(file2);
            image2.setPreviewImage(true);
            product.addImageToProduct(image2);
        }
        if (file3.getSize()!=0){
            image3=toImageEntity(file3);
            image3.setPreviewImage(true);
            product.addImageToProduct(image3);
        }
        log.info("Saving new Product. Title: {}; Author emal: {}", product.getTitle(), product.getUser().getEmail());

        Product productFromDb = productRepositories.save(product);

       //productFromDb.setPreviewImageId(productFromDb.getImages().get(0).getId());
        productRepositories.save(product);

    }

    public User getUserByPrincipal(Principal principal) {
        if (principal==null){
            return new User();
        }
        return userRepository.findByEmail(principal.getName());
    }

    public void deleteProduct(long id){
        productRepositories.deleteById(id);
    }


    public Product getProductById(Long id) {
        return productRepositories.findById(id).orElse(null);
    }


   private Image toImageEntity(MultipartFile file) throws IOException {
       Image image = new Image();
       image.setName(file.getName());
       image.setOriginalFileName(file.getOriginalFilename());
       image.setContentType(file.getContentType());
       image.setSize(file.getSize());
       image.setBytes(file.getBytes());
       return image;

   }
}
