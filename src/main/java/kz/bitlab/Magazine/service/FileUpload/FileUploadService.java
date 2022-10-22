package kz.bitlab.Magazine.service.FileUpload;

import kz.bitlab.Magazine.Entity.Product;
import kz.bitlab.Magazine.Entity.Users;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
    boolean uploadAvatar (MultipartFile file, Users users);
    boolean uploadImage (MultipartFile file, Product product);
}
