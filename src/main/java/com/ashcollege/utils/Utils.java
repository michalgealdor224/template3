package com.ashcollege.utils;


import com.ashcollege.Persist;
import com.ashcollege.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;

@Component
public class Utils {

    @Autowired
    private Persist persist;
    List<Product> productList;
    private List<Product> newList;


    @PostConstruct
    public void init () {
        productList = persist.loadAllProducts();
        createDataFromFile();
    }

    public List<Product> getAllProducts() {
        return this.productList;
    }


    public void createDataFromFile () {
        try {
            if (productList.isEmpty()) {
                URL dataFile = Utils.class.getClassLoader().getResource("data/menu.csv");
                if (dataFile != null) {
                    File file = new File(dataFile.getFile());
                    {
                        productList = new ArrayList<>();
                        Scanner scanner = null;
                        try {
                            scanner = new Scanner(file);
                        } catch (FileNotFoundException ex) {
                            ex.printStackTrace();
                        }
                        int i = 0;
                        while (scanner.hasNextLine()) {

                            String product = scanner.nextLine();
                            if (i != 0) {
                                persist.save(new Product(product));
                            }
                            i++;
                        }
                    }

                    //sortByChoice(50,"salad");
//                combination(300);
                }
            }

        } catch (Exception e)  {

        }

    }
    public List<Product> sortByChoice(int price, String food) {
        newList = new ArrayList<>();
        for (int i = 0; i < this.productList.size(); i++) {
            if (productList.get(i).getName().contains(food)&&
                    productList.get(i).getPrice() <= price) {
                Product product = productList.get(i);
                newList.add(product);
            }
        }
        return newList;
    }

    public List<Product> combination(double sum) {
        boolean ifExist = false;
        List<Integer> numbers = new ArrayList<>();
        List<Product> possibleOptions = new ArrayList<>();
        Random random = new Random();
        double currentSum = 0;

        while (numbers.size() < 13 || currentSum == sum) {
            int randomNumber = random.nextInt(13 - 1 + 1) + 1;
            for (int i = 0; i < numbers.size(); i++) {
                if (numbers.get(i) == randomNumber) {
                    ifExist = true;
                    randomNumber = random.nextInt(13 - 1 + 1) + 1;
                    ifExist = false;
                    i = 0;
                }
            }
            if (!ifExist) {
                numbers.add(randomNumber);
                if (productList.get(randomNumber).getPrice() + currentSum <= sum) {
                    currentSum = currentSum + productList.get(randomNumber).getPrice();
                    possibleOptions.add(productList.get(randomNumber));
                }
            }
        }

        for (int i = 0; i < possibleOptions.size(); i++) {
            System.out.println(possibleOptions.get(i).getPrice());
        }
        return possibleOptions;
    }
}
