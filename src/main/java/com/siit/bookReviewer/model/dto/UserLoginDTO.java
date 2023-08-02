package com.siit.bookReviewer.model.dto;
import lombok.Data;

import java.util.Objects;


@Data

public class UserLoginDTO {
    private Integer id;
    private String email;
    private String firstName;
    private String lastName;

    public UserLoginDTO(Integer id, String email, String firstName, String lastName){
        this.id=id;
        this.email=email;
        this.firstName=firstName;
        this.lastName=lastName;
    }

}
