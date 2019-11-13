package com.ccweb.Controller;

import com.alibaba.fastjson.JSON;
import com.ccweb.Entity.Account;
import com.ccweb.Entity.AccountResponse;
import com.ccweb.Service.AccountService;
import com.ccweb.util.EmailValidationUtil;
import com.ccweb.util.PasswordUtilImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.*;


@RestController
//@RequestMapping("/v1/user")
public class AccountController {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    EmailValidationUtil emailValidationUtil;

    @Autowired
    private AccountService accountService;

    @Autowired
    PasswordUtilImpl passwordUtil;

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> welcome() {

        HashMap<String, String> response = new HashMap<>();

        if (SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken) {
            response.put("message", "you are not logged in!!!");
        } else {
            //Call database, repository(userMapper), service, retrieve info, hash JWT === input>>>passin basic string, decode => username password..
            response.put("message", "you are logged in. current time is " + new Date().toString());
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/users", method = RequestMethod.GET)
    public Collection<AccountResponse> getAllAccounts(){

        List<Account> accounts = (List<Account>) accountService.getAllAccounts();
        List<AccountResponse> views = new ArrayList<AccountResponse>();

        for(int i = 0; i < accounts.size(); i++){
            AccountResponse item = new AccountResponse();
            item.setId(accounts.get(i).getUserId());
            item.setEmail(accounts.get(i).getEmail());
            item.setFirstName(accounts.get(i).getFirstName());
            item.setLastName(accounts.get(i).getLastName());
            item.setUser_created(accounts.get(i).getUser_created());
            item.setUser_update(accounts.get(i).getUser_update());
            views.add(item);
        }

        return views;

    }

    //@RequestMapping(value = "/v1/user/{email}", method = RequestMethod.GET)
    @RequestMapping(value = "/v1/user/self", method = RequestMethod.GET)
    public ResponseEntity<AccountResponse> getAccountByEmail(){

        Account user_db = this.accountService.currentUser;
        Account account = accountService.getAccountByEmail(user_db.getEmail());
        AccountResponse response = new AccountResponse();
        response.setId(account.getUserId());
        response.setEmail(account.getEmail());
        response.setFirstName(account.getFirstName());
        response.setLastName(account.getLastName());
        response.setUser_created(account.getUser_created());
        response.setUser_update(account.getUser_update());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/user/self", method = RequestMethod.PUT,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateAccount(@RequestBody Account account){
        Account user_db = this.accountService.currentUser;
        if(account.getPassword() != null && !account.getPassword().equals(user_db.getPassword())){
            String passwordHash = BCrypt.hashpw(account.getPassword(), BCrypt.gensalt());
            user_db.setPassword(passwordHash);
        }
        if(account.getFirstName() != null && !account.getFirstName().equals(user_db.getFirstName())){
            user_db.setFirstName(account.getFirstName());
        }
        if(account.getLastName() != null && !account.getLastName().equals(user_db.getLastName())){
            user_db.setLastName(account.getLastName());
        }

        HashMap<String, String> response = new HashMap<>();
        if(user_db == null){
            response.put("Warning", "The username is not exists!");
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
        else{
            this.accountService.updateAccount(user_db);
            //response.put("Success", "The account has been updated");
            //return new ResponseEntity<>(response, HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

   // @RequestMapping(method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    //public void addAccount(@RequestBody Account account){
      //  accountService.addAccount(account);
    //}

//    @RequestMapping(method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
//    public void createAccount(@RequestBody Account account){
//
//        accountService.insertAccount(account);
//    }

//    @ResponseBody
//    @RequestMapping(value = "/self",/*value = "/AuthTest",*/ method = RequestMethod.PUT,consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity fix(HttpServletRequest request, HttpServletResponse response,@RequestBody Account account) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        System.out.println(auth.getName());
//        accountService.updateAccount(account);
//        return new ResponseEntity(HttpStatus.OK);
//    }
//
//    @ResponseBody
//    @RequestMapping(value = "/self",/*value = "/AuthTest",*/ method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_VALUE)
//    public Account look(HttpServletRequest request, HttpServletResponse response, @RequestBody Account account) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        System.out.println(auth.getName());
//        accountService.getAccountByEmail(account.getEmail());
//        return accountService.getAccountByEmail(account.getEmail());
//    }

    @RequestMapping(value = "/v1/user",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> registerPost(@RequestBody String jsonUser) {
        Account account = JSON.parseObject(jsonUser, Account.class);
        HashMap<String, String> response = new HashMap<>();

        String username = account.getEmail();
        String password = account.getPassword();
        if (null == username || username.equals("") || null == password || password.equals("")) {
            //response.put("Warning", "Please enter username or password!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        if (!emailValidationUtil.isEmail(username)) {
            response.put("Warning", "Please use a valid email address as your username");
            return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        if (!passwordUtil.isStrongPassword(password)) {
            response.put("Warning", "Your password is too weak!");
            return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
        account.setPassword(passwordHash);
        account.setUserId(UUID.randomUUID().toString());
        Account user_db = accountService.getAccountByEmail(username);

        if (user_db == null) {
            accountService.addAccount(account);
            //response.put("Message", "You have registered successfully!");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            response.put("Warning", "The username already exists!");
            //return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

    }
}
