package org.example;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        Properties props=new Properties();

        try {
            props.load(new FileReader("bd.config"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config "+e);
        }

        // adaugare inregistrare
        CarRepository carRepo=new CarsDBRepository(props);
        carRepo.add(new Car("Tesla","Model S", 2019));

        for(Car car : carRepo.findAll()) {
            System.out.println(car);
        }

        // modificare inregistrare
        carRepo.update(3, new Car("tesla","model s", 2019));

        // toate masinile in functie de manufacturer
        String manufacturer="skoda";
        System.out.println("Masinile produse de "+manufacturer);
        for(Car car:carRepo.findByManufacturer(manufacturer))
            System.out.println(car);
    }
}