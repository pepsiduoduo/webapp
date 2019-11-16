package com.ccweb.Controller;

import com.ccweb.Entity.Account;
import com.ccweb.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/account")

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

@RestController
@RequestMapping("/v1/user")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @RequestMapping(method = RequestMethod.GET)
    public Collection<Account> getAllAccounts(){
        return accountService.getAllAccounts();
    }

    @RequestMapping(value = "/{email}", method = RequestMethod.GET)
    public Account getAccountByEmail(@PathVariable("email") String email){
        return accountService.getAccountByEmail(email);
    }




    @RequestMapping(value = "/v1/user/self", method = RequestMethod.PUT,consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateAccount(@RequestBody Account account){
        accountService.updateAccount(account);
    }

    @RequestMapping(value = "/v1/user", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createAccount(@RequestBody Account account){
        accountService.insertAccount(account);
    }

//    @RequestMapping(value = "/v1/user/self", method = RequestMethod.PUT,consumes = MediaType.APPLICATION_JSON_VALUE)
//    public void updateAccount(@RequestBody Account account){
//        accountService.updateAccount(account);
//    }

    @RequestMapping(method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createAccount(@RequestBody Account account){
        accountService.insertAccount(account);
    }

    @ResponseBody
    @RequestMapping(value = "/self",/*value = "/AuthTest",*/ method = RequestMethod.PUT,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity fix(HttpServletRequest request, HttpServletResponse response,@RequestBody Account account) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getName());
        accountService.updateAccount(account);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/self",/*value = "/AuthTest",*/ method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Account look(HttpServletRequest request, HttpServletResponse response, @RequestBody Account account) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getName());
        accountService.getAccountByEmail(account.getEmail());
        return accountService.getAccountByEmail(account.getEmail());
    }
}
