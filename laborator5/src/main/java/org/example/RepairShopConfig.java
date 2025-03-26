package org.example;

import org.example.repository.ComputerRepairRequestRepository;
import org.example.repository.ComputerRepairedFormRepository;
import org.example.repository.file.ComputerRepairRequestFileRepository;
import org.example.repository.file.ComputerRepairedFormFileRepository;
import org.example.repository.jdbc.ComputerRepairRequestJdbcRepository;
import org.example.repository.jdbc.ComputerRepairedFormJdbcRepository;
import org.example.services.ComputerRepairServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

@Configuration
public class RepairShopConfig {
    @Bean
    Properties getProps() {
        Properties properties=new Properties();
        try{
            System.out.println(System.getProperty("Searching bd.config in directory "+ ((new File(".")).getAbsolutePath())));
            properties.load(new FileReader("bd.config"));
        }catch(IOException e){
            System.err.println(e);
        }
        return properties;

    }

    @Bean
    ComputerRepairRequestRepository requestsRepo(){
        //return new ComputerRepairRequestFileRepository("D:\\mpp_labs\\teme-lab-rauloanea\\laborator5\\ComputerRequests.txt");
        return new ComputerRepairRequestJdbcRepository(getProps());
    }

    @Bean
    ComputerRepairedFormRepository formsRepo(){
        //return new ComputerRepairedFormFileRepository("D:\\mpp_labs\\teme-lab-rauloanea\\laborator5\\RepairedForms.txt",requestsRepo());
        return new ComputerRepairedFormJdbcRepository(getProps());
    }

    @Bean
    ComputerRepairServices services(){
        return new ComputerRepairServices(requestsRepo(),formsRepo());

    }

}