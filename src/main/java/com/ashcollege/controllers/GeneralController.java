package com.ashcollege.controllers;

import com.ashcollege.Persist;
import com.ashcollege.entities.Product;
import com.ashcollege.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.List;

@RestController
public class GeneralController {

    @Autowired
    private Persist persist;

    @Autowired
    private Utils utils;


    @RequestMapping (value = "get-products")
    public List<Product> getProducts () {
        return utils.getAllProducts();
    }

    @RequestMapping(value = {"get-products-after-filter"})
    public List<Product> productsAfterFilter (int price , String food) {
        return utils.sortByChoice(price,food);
    }

    @RequestMapping(value = {"get-combination"})
    public List<Product> combination (double price) {
        return utils.combination(price);
    }

//
//    @RequestMapping (value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
//    public BasicResponse login (String username, String password) {
//        BasicResponse basicResponse = null;
//        boolean success = false;
//        Integer errorCode = null;
//        if (username != null && username.length() > 0) {
//            if (password != null && password.length() > 0) {
//                User user = persist.login(username, password);
//                if (user != null) {
//                    basicResponse = new LoginResponse(true, errorCode, user.getId(), user.getSecret());
//                } else {
//                    errorCode = ERROR_LOGIN_WRONG_CREDS;
//                }
//            } else {
//                errorCode = ERROR_SIGN_UP_NO_PASSWORD;
//            }
//        } else {
//            errorCode = ERROR_SIGN_UP_NO_USERNAME;
//        }
//        if (errorCode != null) {
//            basicResponse = new BasicResponse(success, errorCode);
//        }
//        return basicResponse;
//    }
//
//    @RequestMapping (value = "add-user")
//    public boolean addUser (String username, String password) {
//        User userToAdd = new User(username, password);
//        return dbUtils.addUser(userToAdd);
//    }
//

//
//
//    @RequestMapping (value = "add-product")
//    public boolean addProduct (String description, float price, int count) {
//        Product toAdd = new Product(description, price, count);
//        dbUtils.addProduct(toAdd);
//        return true;
//    }
//
//    @RequestMapping(value = "get-products")
//    public BasicResponse getProducts (String secret) {
//        boolean success = false;
//        Integer errorCode = null;
//        BasicResponse basicResponse = null;
//        if (secret != null) {
//            User user = dbUtils.getUserBySecret(secret);
//            if (user != null) {
//                List<Product> products = dbUtils.getProductsByUserSecret(secret);
//                basicResponse = new ProductsResponse(true, null, products);
//            } else {
//                errorCode = ERROR_NO_SUCH_USER;
//            }
//        } else {
//            errorCode = ERROR_SECRET_WAS_NOT_SENT;
//        }
//        if (errorCode != null) {
//            basicResponse = new BasicResponse(false, errorCode);
//        }
//        return basicResponse;
//    }
//
//    @RequestMapping(value = "test")
//    public Client test (String firstName, String newFirstName) {
//        Client client = persist.getClientByFirstName(firstName);
//        client.setFirstName(newFirstName);
//        client.setLastName(newFirstName);
//        persist.save(client);
//        return client;
//    }
//
//    @RequestMapping(value = "get-notes")
//    public BasicResponse getNotes (String name) {
//        boolean success = false;
//        Integer errorCode = null;
//        BasicResponse basicResponse = null;
//        if (name != null) {
//            List<Note> notes = persist.getNotesByCollegeName(name);
//            basicResponse = new NotesResponse(success, errorCode, notes);
//        } else {
//            errorCode = ERROR_SECRET_WAS_NOT_SENT;
//        }
//        if (errorCode != null) {
//            basicResponse = new BasicResponse(false, errorCode);
//        }
//        return basicResponse;
//    }
//



}
