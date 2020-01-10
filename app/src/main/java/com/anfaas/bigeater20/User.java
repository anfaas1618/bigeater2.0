package com.anfaas.bigeater20;

public class User {
  public   String Nane;
 public    String Email;
 public    String  Uid;
public     String PassWord;
public     int score;

    public User(String nane, String email, String passWord,String uID,int score) {
        this.Nane = nane;
        this.Email = email;
        this.PassWord = passWord;
        this.Uid=uID;
        this.score=score;

    }
    public User()
    {

    }
}
