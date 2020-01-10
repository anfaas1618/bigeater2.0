package com.anfaas.bigeater20;

public class CONSTANTCLASS {
    LoginActivity login = new LoginActivity();
//    public String id = login.user.Uid;

  //  public String getId() {
  //      return id;
 //   }

    public LoginActivity getLogin() {
        return login;
    }

    String getUid
            () {
 //       if (!id.isEmpty()) {
            final String UID = login.user.Uid;
            return UID;
  //      }
     //   return "default";
    }
}