package demo.practiceBean;

import org.springframework.stereotype.Component;

@Component("rwd")
public class RearWheelDrive implements DriveMode {
    @Override
    public void displayMode() {
        System.out.println("This is REAR wheels drive car.");
    }
}
