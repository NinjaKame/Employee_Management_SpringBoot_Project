package demo;

import demo.practiceBean.Car;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class EmployeeManagementApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(EmployeeManagementApplication.class, args);

        Car myCar = context.getBean(Car.class);

        System.out.println("Name of the bean is: " + myCar);
        myCar.showProperties();
//
//        System.out.println("This is car name: " + myCar.getName());
//        System.out.println("This is car branch: " +myCar.getBranch());
//        System.out.println("This is car date: " +myCar.getDate());
    }
}
