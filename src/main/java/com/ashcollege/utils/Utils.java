package com.ashcollege.utils;


import com.ashcollege.Persist;
import com.ashcollege.entities.Product;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONObject;


@Component
public class Utils {

    @Autowired
    private Persist persist;


    List<Product> productList;
    private List<Product> newList;


    public static String decode (String encoded) {
        try {
            return StringEscapeUtils.unescapeJava(encoded);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @PostConstruct
    public void init() {
        productList = persist.loadAllProducts();
        createDataFromFile();

        sendApiRequest("https://consumer-api.wolt.com/v1/pages/venue-list/category-kosher?lon=34.952290639252624&lat=29.548890477854528");
    }


    public static void sendApiRequest (String url) {
        try {
            URL myUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) myUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("App-Language", "he");
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                JSONObject jsonObject = new JSONObject(response.toString());
                System.out.println(decode(jsonObject.toString()));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public List<Product> getAllProducts () {
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
