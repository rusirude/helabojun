package com.leaf.helabojun.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorityDTO extends CommonDTO{
    private String description;
    private String status;
    private String section;
    private String sectionDescription;

}
