package com.ccweb.Controller;

import com.ccweb.Entity.Account;
import com.ccweb.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;

@Controller
public class NewController {
    @Autowired
    private AccountService accountService;

    @RequestMapping(method = RequestMethod.GET)
    public Collection<Account> getAllAccounts(){
        return accountService.getAllAccounts();
    }

}
