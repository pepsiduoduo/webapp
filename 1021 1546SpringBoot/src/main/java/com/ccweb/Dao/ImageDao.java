package com.ccweb.Dao;

import com.ccweb.Entity.Image;

public interface ImageDao {


    Image getImageById(String id);

    void deleteImage(String id);

    void insertImage(Image image);


}
