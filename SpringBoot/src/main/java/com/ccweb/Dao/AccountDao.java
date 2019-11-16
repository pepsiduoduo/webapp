package com.ccweb.Dao;

import com.ccweb.Entity.Account;

import java.util.Collection;

public interface AccountDao {
    Collection<Account> getAllAccounts();

    Account getAccountByEmail(String email);

    void updateAccount(Account account);

    void insertAccount(Account account);
}
