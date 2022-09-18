package com.example.tsekh_task.utils;

import com.example.tsekh_task.exception.InvalidFileFormatException;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;

public class MediaService {
    public static void checkFileIsImage(MultipartFile file){
        try {
            InputStream inputStream = file.getInputStream();
            BufferedImage bufferedImage = ImageIO.read(inputStream);
            if(bufferedImage == null)
                throw new InvalidFileFormatException();
        } catch (Exception e) {
            throw new InvalidFileFormatException("Can not save image: Invalid format");
        }
    }
}
