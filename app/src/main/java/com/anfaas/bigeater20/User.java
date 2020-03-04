package com.anfaas.bigeater20;

public class User {
  public   String Name;
 public    String Email;
 public    String  Uid;
public     String PassWord;
public     int score;

    public User(String name, String email, String passWord,String uID,int score) {
        this.Name = name;
        this.Email = email;
        this.PassWord = passWord;
        this.Uid=uID;
        this.score=score;
    }
    public User()
    {

    }
}
