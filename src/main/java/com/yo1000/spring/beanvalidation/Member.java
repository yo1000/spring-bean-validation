package com.yo1000.spring.beanvalidation;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.UUID;

public record Member(
        @UUID
        String id,
        @Size(min = 1, max = 40)
        String givenName,
        @Size(min = 1, max = 40)
        String familyName,
        @Email
        String email,
        @Valid
        Address address
) {
        public Member newId(String newId) {
                return new Member(
                        newId,
                        givenName(),
                        familyName(),
                        email(),
                        address()
                );
        }
}
