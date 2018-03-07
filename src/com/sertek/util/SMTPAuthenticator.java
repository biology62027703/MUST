package com.sertek.util;

import javax.mail.*;

class SMTPAuthenticator extends javax.mail.Authenticator {
  private String fUser;
  private String fPassword;

  public SMTPAuthenticator(String user, String password) {
    this.fUser = user;
    this.fPassword = password;
  }

  
  public PasswordAuthentication getPasswordAuthentication() {
    return new PasswordAuthentication(fUser, fPassword);
  }
}