package com.nttdatabc.mscuentabancaria.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * Clase customer.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerExt {
  private String _id;

  private String identifier;

  private String fullname;

  private String type;

  private String address;

  private String phone;

  private String email;

  private String birthday;

  private List<AuthorizedSignerExt> authorizedSigners;
}
