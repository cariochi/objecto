package com.cariochi.objecto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

@FieldNameConstants
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String username;
    private String fullName;
    private String phone;
    private String email;
    private String companyName;

}
