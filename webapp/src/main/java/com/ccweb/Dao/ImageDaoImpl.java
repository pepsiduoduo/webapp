package com.ccweb.Dao;

import com.ccweb.Entity.Image;
import com.ccweb.Sql.SqlImage;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class ImageDaoImpl implements ImageDao {
    private SqlImage repo;

    ImageDaoImpl(){
        this.repo = new SqlImage();
    }

//    @Override
//    public Collection<Image> getAllAccounts() {
//        return this.repo.getAccounts();
//    }

//    @Override
//    public Image getAccountByEmail(String email) {
//        return this.repo.getImageById(email);
//    }
//
//    @Override
//    public void updateAccount(Image account) {
//        this.repo.deleteImage(account);
//    }

    @Override
    public Image getImageById(String id) {
        return this.repo.getImageById(id);
    }

    @Override
    public void deleteImage(String id) {
        this.repo.deleteImage(id);
    }

    @Override
    public void insertImage(Image image) {
        this.repo.insertImage(image);
    }

    @Override
    public void updateImage(Image image) {
        this.repo.updateImage(image.getId(), image.getUrl());
    }
}
