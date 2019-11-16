package com.ccweb.Service;

import com.ccweb.Dao.FakeAccountDaoImpl;
import com.ccweb.Entity.Account;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

public class AccountService {
    @Autowired
    private FakeAccountDaoImpl fakeAccountDaoImpl;

    public Collection<Account> getAllAccounts(){
        return this.fakeAccountDaoImpl.getAllAccounts();
    }
}
