package com.leaf.helabojun.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorityDTO extends CommonDTO{
    private String code;
    private String description;
    private String status;
    private Long sectionId;
    private String sectionDescription;

}
