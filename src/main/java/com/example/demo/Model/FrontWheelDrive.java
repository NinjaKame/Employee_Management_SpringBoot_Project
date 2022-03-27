package com.example.demo.Model;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class FrontWheelDrive implements DriveMode{
    @Override
    public void displayMode() {
        System.out.println("This is FRONT wheels drive car.");
    }
}
