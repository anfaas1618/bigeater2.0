package com.anfaas.bigeater20;

import android.net.Uri;

import java.net.URI;

public class UserImage {
    public  String useridd;
    public  String image_reference;

    public String getUseridd() {
        return useridd;
    }

    public void setUseridd(String UID) {
        this.useridd = UID;
    }

    public String getImage_reference() {
        return image_reference;
    }

    public void setImage_reference(String image_reference) {
        this.image_reference = image_reference;
    }
    public UserImage()
    {

    }
    public UserImage(String UID, String image_reference)
    {
        this.useridd=UID;
        this.image_reference=image_reference;
    }
}
