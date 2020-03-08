package com.anfaas.bigeater20;

import android.net.Uri;

public class PlayerScore implements Comparable<PlayerScore> {
 public    String name;
 public    int score;
 public    String uid;
 public String imageurl;
   public PlayerScore()
   {

   }


    public PlayerScore(String name, int score, String uid, String imageurl) {
        this.name = name;
        this.score = score;
        this.uid = uid;
        this.imageurl = imageurl;
    }

    public String getName() {
        return name;
    }

    public void setNAME(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    @Override
    public int compareTo(PlayerScore s) {
        return this.score - s.getScore();
    }
}
