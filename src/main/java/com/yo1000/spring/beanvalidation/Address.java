package com.yo1000.spring.beanvalidation;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record Address(
        @Size(max = 80)
        String addressLevel4,
        @Size(max = 80)
        String addressLevel3,
        @Size(max = 80)
        String addressLevel2,
        @Size(max = 80)
        String addressLevel1,
        @Pattern(regexp = "^[A-Z]{2}$")
        String country,
        @Size(max = 80)
        String countryName,
        @Pattern(regexp = "^[0-9A-Za-z -]{0,10}$")
        String postalCode
) {}
