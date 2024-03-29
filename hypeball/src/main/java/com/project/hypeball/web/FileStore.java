package com.project.hypeball.web;

import com.project.hypeball.domain.AttachedFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {

    @Value("${file.dir}/files/")
    private String fileDir;

    @Value("${file.dir}"+"/profiles/")
    private String picDir;


    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    public String getFullPathPicture(String filename) {
        return picDir + filename;
    }

    public List<AttachedFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {

        List<AttachedFile> storeFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                storeFileResult.add(storeFile(multipartFile));
            } }
        return storeFileResult;
    }

    public AttachedFile storeFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);
        multipartFile.transferTo(new File(getFullPath(storeFileName)));
        return new AttachedFile(originalFilename, storeFileName);
    }

    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

    public String storePicture(MultipartFile picture) throws IOException {
        if (picture.isEmpty()) {
            return "???";
        }

        String originalFilename = picture.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);
        String fullPathPicture = getFullPathPicture(storeFileName);
        picture.transferTo(new File(getFullPathPicture(storeFileName)));

        return fullPathPicture;
    }
}

