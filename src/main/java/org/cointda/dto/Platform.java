package org.cointda.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Platform {
    private Integer id;
    private String name;
    private String symbol;
    private String slug;
    private String token_address;
}
