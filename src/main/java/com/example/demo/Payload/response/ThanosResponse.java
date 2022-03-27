package com.example.demo.Payload.response;

import com.example.demo.Entity.Thanos;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ThanosResponse {

    private int id;

    private String firstName;

    private String lastName;

    private String department;

    private String email;

    public ThanosResponse(Thanos responseThanos) {
        this.id = responseThanos.getId();
        this.firstName = responseThanos.getFirstName();
        this.lastName = responseThanos.getLastName();
        this.department = responseThanos.getDepartment();
        this.email = responseThanos.getEmail();
//        System.out.println("Response constructor called");
    }
}
