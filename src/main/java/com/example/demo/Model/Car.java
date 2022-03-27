package com.example.demo.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Car {
    private String name;
    private String branch;
    private Date date;

    @Autowired
    @Qualifier("rwd")
    private DriveMode mode;

//    @Autowired
//    public Car(DriveMode mode) {
//        this.mode = mode;
//    }

//    @Autowired
//    public void setMode(DriveMode mode) {
//        this.mode = mode;
//    }

    public void showProperties(){
        mode.displayMode();
    }

    public String getName() {
        return name;
    }

    public String getBranch() {
        return branch;
    }

    public Date getDate() {
        return date;
    }
}
