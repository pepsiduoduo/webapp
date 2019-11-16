package com.ccweb.Dao;

import com.ccweb.Entity.Account;

import java.util.Collection;

public interface AccountDao {
    //void insertAccount(String userName, String password);

    Collection<Account> getAllAccounts();

    Account getAccountByEmail(String email);

    void updateAccount(Account account);

    void insertAccount(Account account);

}
