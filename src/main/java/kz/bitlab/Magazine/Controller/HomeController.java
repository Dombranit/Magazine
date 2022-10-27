package kz.bitlab.Magazine.Controller;

import kz.bitlab.Magazine.service.FileUpload.FileUploadService;
import kz.bitlab.Magazine.service.ProductService;
import kz.bitlab.Magazine.service.UserService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Controller
public class HomeController {
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private FileUploadService fileUploadService;

    @Value("${loadURL}")
    private String loadURL;
    @GetMapping(value = "/navbar")
    public String HomePage(Model model) {
        model.addAttribute("currentUser", userService.getUserData());
        return "layout/navbar";
    }

    @PostMapping(value = "/uploadAvatar")
    @PreAuthorize("isAuthenticated()")
    public String uploadAvatar(@RequestParam(name = "user_avatar") MultipartFile file) {
        if (file.getContentType().equals("image/jpeg") || file.getContentType().equals("image/png")) {
            fileUploadService.uploadAvatar(file, userService.getUserData());
        }
        return "redirect:/profile";
    }
    @PostMapping(value = "/uploadImage")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public String uploadImage(@RequestParam(name = "product_image") MultipartFile image,
                              @RequestParam(name = "product_id")Long id) {
        if (image.getContentType().equals("image/jpeg") || image.getContentType().equals("image/png")) {
            fileUploadService.uploadImage(image,productService.getProductById(id));
        }
        return "redirect:/product/edit/"+id;
    }

    @GetMapping(value = "/getAvatar/{tokenn}", produces = MediaType.IMAGE_JPEG_VALUE)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody byte[] getAvatar(@PathVariable(name = "tokenn", required = false) String token) throws IOException {

        String pictureURL = loadURL + "def_user.jpg";
        if(token!=null){
            pictureURL = loadURL + token + ".jpg";
        }
        InputStream in ;
        try {
            ClassPathResource resource = new ClassPathResource(pictureURL);
            in = resource.getInputStream();
        }
        catch (Exception e){
            pictureURL =loadURL + "def_user.jpg";
            ClassPathResource resource = new ClassPathResource(pictureURL);
            in = resource.getInputStream();
        }
        return IOUtils.toByteArray(in);
    }
    @GetMapping(value = "/getImage/{token}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@PathVariable(name = "token", required = false) String token) throws IOException {

        String pictureURL = loadURL + "default-prod.jpg";
        if(token!=null){
            pictureURL = loadURL + token + ".jpg";
        }
        InputStream in ;
        try {
            ClassPathResource resource = new ClassPathResource(pictureURL);
            in = resource.getInputStream();
        }
        catch (Exception e){
            pictureURL =loadURL + "default-prod.jpg";
            ClassPathResource resource = new ClassPathResource(pictureURL);
            in = resource.getInputStream();
        }
        return IOUtils.toByteArray(in);
    }

}
