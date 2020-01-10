package com.anfaas.bigeater20;

public class PlayerScore implements Comparable<PlayerScore> {
 public    String name;
 public    int score;
 public    String uid;
   public PlayerScore()
   {

   }
    public PlayerScore(String name, int score, String uid) {
        this.name = name;
        this.score = score;
        this.uid = uid;
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

    @Override
    public int compareTo(PlayerScore s) {
        return this.score - s.getScore();
    }
}
