package com.example.puclove.storage;

import com.example.puclove.user.User;
import com.example.puclove.userimage.UserImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Controller para o armazenamento de arquivos no sistema.
 */

@RestController()
@CrossOrigin("http://localhost:5173")
@RequestMapping("/api/v1/image")
public class StorageController {
    @Autowired
    private StorageService storageService;

    @PostMapping
    public ResponseEntity<?> uploadUserImageToFileSystem(@RequestParam("image") MultipartFile file,
                                                     @AuthenticationPrincipal User user) throws IOException {
        String uploadImage = storageService.uploadUserImageToFileSystem(file, user);
        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadImage);
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<?> downloadImageFromFileSystem(@PathVariable String fileName) throws IOException {
        byte[] imageData=storageService.downloadImageFromFileSystem(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> downloadUserImagesFromFileSystem(@PathVariable String username) throws IOException {
        List<byte[]> imagesData = storageService.downloadUserImagesFromFileSystem(username);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imagesData);
    }

    @GetMapping("/filepath/{username}")
    public ResponseEntity<Optional<List<UserImage>>> getUserImages(@PathVariable String username) throws IOException {
        Optional<List<UserImage>> userImages = Optional.ofNullable(storageService.userImages(username));

        if (userImages.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(userImages, HttpStatus.OK);
    }

}
