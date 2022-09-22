package com.anfaas.bigeater20;

public class User {
  private    String Name;
    private    String Email1;
    private    String  Uid;
    private     String PassWord;
    private     int score;

    public User(String name, String email1, String passWord, String uID, int score) {
        this.Name = name;
        this.Email1 = email1;
        this.PassWord = passWord;
        this.Uid=uID;
        this.score=score;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail1() {
        return Email1;
    }

    public void setEmail1(String email1) {
        Email1 = email1;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getPassWord() {
        return PassWord;
    }

    public void setPassWord(String passWord) {
        PassWord = passWord;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public User()
    {

    }
}
