package com.ccweb.Service;

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

    public String getUrlByImageId(String id) {

        Image img = this.imageDapImpl.getImageById(id);

        if(img == null) return null;

        return img.getUrl();
    }

    public void updateImage(Image image){
        this.imageDapImpl.updateImage(image);
    }
}
