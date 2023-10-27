package com.example.puclove.storage;

import com.example.puclove.filedata.FileData;
import com.example.puclove.filedata.FileDataRepository;
import com.example.puclove.user.User;
import com.example.puclove.user.UserService;
import com.example.puclove.userimage.UserImage;
import com.example.puclove.userimage.UserImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class StorageService {
    @Autowired
    private FileDataRepository fileDataRepository;
    @Autowired
    private UserImageRepository userImageRepository;
    @Autowired
    private UserService userService;

    private final String FOLDER_PATH="./src/main/resources/static/images/";


    public String uploadUserImageToFileSystem(MultipartFile file, User user) throws IOException {
        String filePath = FOLDER_PATH + file.getOriginalFilename();
        FileData filedata = fileDataRepository.save(FileData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .filePath(filePath)
                .build());

        file.transferTo(new File(filePath));

        UserImage userImage = userImageRepository.save(UserImage.builder()
                .userId(user.getId())
                .imagePath(filePath)
                .build());

        return filePath;
    }

    public byte[] downloadImageFromFileSystem(String fileName) throws IOException {
        Optional<FileData> fileData = fileDataRepository.findByName(fileName);
        String filePath=fileData.get().getFilePath();
        return Files.readAllBytes(new File(filePath).toPath());
    }

    public List<byte[]> downloadUserImagesFromFileSystem(String username) throws IOException {
        User user = userService.findUserByUsername(username).orElse(null);

        if (user != null) {
            List<UserImage> userImages = userImageRepository.findByUserId(user.getId());

            List<byte[]> imageBytesList = new ArrayList<>();

            for (UserImage userImage : userImages) {
                String imagePath = userImage.getImagePath();
                byte[] imageBytes = Files.readAllBytes(new File(imagePath).toPath());
                imageBytesList.add(imageBytes);
            }

            return imageBytesList;
        } else {
            return Collections.emptyList();
        }
    }

    public List<UserImage> userImages(String username) {
        User user = userService.findUserByUsername(username).orElse(null);

            if (user != null) {
                return userImageRepository.findByUserId(user.getId());
            } else {
                return Collections.emptyList();
            }
    }
}
