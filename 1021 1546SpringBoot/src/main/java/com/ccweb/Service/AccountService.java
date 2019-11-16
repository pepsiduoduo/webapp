package com.ccweb.Service;

import com.ccweb.Dao.AccountDaoImpl;
import com.ccweb.Entity.Account;
import com.ccweb.util.IdCrypto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

//import com.ccweb.Dao.FakeAccountDaoImpl;

@Service
public class AccountService implements UserDetailsService {

//    @Autowired
//    private FakeAccountDaoImpl fakeAccountDaoImpl;

    private IdCrypto  idCrypto;

    public Account currentUser;

    @Autowired
    private AccountDaoImpl accountDaoImpl;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    AccountService(){
        try{
            this.idCrypto = new IdCrypto();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public Collection<Account> getAllAccounts(){
        List<Account> accounts = (List<Account>) this.accountDaoImpl.getAllAccounts();
        for(int i = 0; i < accounts.size(); i++){
            Account item = accounts.get(i);
            item.setUserId(UUID.randomUUID().toString());
        }
        return accounts;
    }

    public Account getAccountByEmail(String email){

        Account result = this.accountDaoImpl.getAccountByEmail(email);
        if(result != null){
            //result.userId = this.idCrypto.encrypt(String.valueOf(result.getId()));
            result.userId = UUID.randomUUID().toString();
        }


        return result;
    }

    public void updateAccount(Account account){
        this.accountDaoImpl.updateAccount(account);
    }

    public void addAccount(Account account){

        this.accountDaoImpl.insertAccount(account);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Account user = getAccountByEmail(s);
        this.currentUser = user;
        User.UserBuilder builder = null;

        if(user != null){
            builder = User.withUsername(s);
            System.out.println(user.getPassword());
            builder.password(user.getPassword());

            builder.roles();
        }
        else{
            throw new UsernameNotFoundException("User not found");
        }

        return builder.build();

//        return null;
    }

//    public void insertAccount(Account account) {
//        this.fakeAccountDaoImpl.insertAccount(account);
//    }
}
