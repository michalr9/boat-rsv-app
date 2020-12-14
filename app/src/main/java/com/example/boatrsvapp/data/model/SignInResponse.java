package com.example.boatrsvapp.data.model;

import java.util.Collection;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Getter
@Setter
@Accessors(chain = true)
public class SignInResponse {
    String accessToken;
    String tokenType;
    Collection<Authority> authorities;
}
