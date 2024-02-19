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

    @PostConstruct
    public void init () {
        createDataFromFile();
    }

    private void createDataFromFile () {
        try {
            List<Product> productList = persist.loadAllProducts();
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

                    //sortByPrice(50);
//                combination(300);
                }
                System.out.println(productList);
            }

        } catch (Exception e)  {

        }
    }
}
