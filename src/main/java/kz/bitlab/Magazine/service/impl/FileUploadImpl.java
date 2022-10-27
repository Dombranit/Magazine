package kz.bitlab.Magazine.service.impl;

import kz.bitlab.Magazine.Entity.Product;
import kz.bitlab.Magazine.Entity.Users;
import kz.bitlab.Magazine.service.FileUpload.FileUploadService;
import kz.bitlab.Magazine.service.ProductService;
import kz.bitlab.Magazine.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
@Service
public class FileUploadImpl implements FileUploadService {
    @Value("${targetURL}")
    private String targetURL = "static/avatars";
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Override
    public boolean uploadAvatar(MultipartFile file, Users users) {
        try {
            String foleToken=DigestUtils.sha1Hex(users.getId()+"_!&^");
            String filename = foleToken + ".jpg";
            byte bytes [] = file.getBytes();
            Path path = Paths.get(targetURL+"/",filename);
            Files.write(path,bytes);
            users.setAvatar(foleToken);
            userService.saveUser(users);
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean uploadImage(MultipartFile file, Product product) {
        try {
            String foleToken=DigestUtils.sha1Hex(product.getId()+"_!");
            String filename = foleToken + ".jpg";
            byte bytes [] = file.getBytes();
            Path path = Paths.get(targetURL+"/",filename);
            Files.write(path,bytes);
            product.setProductImage(foleToken);
            productService.editProduct(product);
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }
}
