package com.ccweb.Entity;

public class Account {
    private String id;
    public String userId;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String user_created;
    private String user_update;


    public Account(String id, String email, String firstName, String lastName, String user_created, String user_update) {
        this.id = id;
        this.email = email;
//        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.user_created = user_created;
        this.user_update = user_created;
    }

    public Account(){}

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUser_created() {
        return user_created;
    }

    public void setUser_created(String user_created) {
        this.user_created = user_created;
    }

    public String getUser_update() {
        return user_update;
    }

    public void setUser_update(String user_update) {
        this.user_update = user_update;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

//    public AccountResponse mapFromAccount(Account account){
//        return new AccountResponse(account.userId, account.getEmail(), account.getFirstName(), account.getLastName(), account.getUser_created(), account.getUser_update());
//    }
}
