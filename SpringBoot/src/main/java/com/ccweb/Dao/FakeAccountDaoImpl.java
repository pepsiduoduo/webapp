package com.ccweb.Dao;

import com.ccweb.Entity.Account;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class FakeAccountDaoImpl implements AccountDao {
    private static Map<String, Account>accounts;

    static {
        accounts = new HashMap<String, Account>(){
            {
                put("@",new Account("@","ps","f","l"));
                put("@@",new Account("@@","wd","ff","ll"));

            }
        };
    }

    @Override
    public Collection<Account> getAllAccounts(){
        return this.accounts.values();
    }

    @Override
    public Account getAccountByEmail(String email){
        return this.accounts.get(email);
    }

    @Override
    public void updateAccount(Account account){
        Account acc = accounts.get(account.getEmail());
        acc.setPassword(account.getPassword());
        acc.setFirstName(account.getFirstName());
        acc.setLastName(account.getLastName());
        accounts.put(account.getEmail(),account);

        String c = BCrypt.hashpw(account.getPassword()+"be5e0323a9195ade5f56695ed9f2eb6b036f3e6417115d0cbe2fb9d74d8740406838dc84f152014b39a2414fb3530a40bc028a9e87642bd03cf5c36a1f70801e",BCrypt.gensalt());
        acc.setPassword(c);
        System.out.println(account.getPassword());
        System.out.println(BCrypt.hashpw(account.getPassword(),BCrypt.gensalt()));
        accounts.put(account.getEmail(),account);
    }

    @Override
    public void insertAccount(Account account) {
        account.setPassword(BCrypt.hashpw(account.getPassword(),BCrypt.gensalt()));
        //this.accounts.put(account.getEmail(),account);
        this.accounts.put(account.getEmail(),account);

    }
}
