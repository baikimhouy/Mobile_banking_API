package org.example.mobilebankingapi.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Cutomer {
    private Integer id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String remarks;
    private boolean active;
}
