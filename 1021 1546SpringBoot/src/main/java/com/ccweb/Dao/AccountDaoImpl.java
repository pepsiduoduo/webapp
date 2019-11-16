package com.ccweb.Dao;

import com.ccweb.Entity.Account;
import com.ccweb.Sql.Sql;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class AccountDaoImpl implements AccountDao{
    private Sql repo;

    AccountDaoImpl(){
        this.repo = new Sql();
    }
    @Override
    public Collection<Account> getAllAccounts() {
        return this.repo.getAccounts();
    }

    @Override
    public Account getAccountByEmail(String email) {
        return this.repo.getAccountByEmail(email);
    }

    @Override
    public void updateAccount(Account account) {
        this.repo.updateAccount(account);
    }

    @Override
    public void insertAccount(Account account) {
        this.repo.addAccount(account);
    }
}
