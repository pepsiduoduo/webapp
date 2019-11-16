package com.ccweb.Service;

import com.ccweb.Dao.FakeAccountDaoImpl;
import com.ccweb.Entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class AccountService {

    @Autowired
    private FakeAccountDaoImpl fakeAccountDaoImpl;

    public Collection<Account> getAllAccounts(){
        return this.fakeAccountDaoImpl.getAllAccounts();
    }

    public Account getAccountByEmail(String email){
        return this.fakeAccountDaoImpl.getAccountByEmail(email);
    }

    public void updateAccount(Account account){
        this.fakeAccountDaoImpl.updateAccount(account);
    }

    public void insertAccount(Account account) {
        this.fakeAccountDaoImpl.insertAccount(account);
    }
}
