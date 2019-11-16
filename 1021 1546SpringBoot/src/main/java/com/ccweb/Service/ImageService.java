package com.ccweb.Service;

import com.ccweb.Dao.ImageDao;
import com.ccweb.Dao.ImageDaoImpl;
import com.ccweb.Entity.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    @Autowired
    private ImageDaoImpl imageDapImpl;

    public void addImage(Image image) {
        this.imageDapImpl.insertImage(image);
    }

    public void deleteImage(String id) {
        this.imageDapImpl.deleteImage(id);
    }

    public void getImageById(String id) {
        this.imageDapImpl.getImageById(id);
    }
}
