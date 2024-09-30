package com.farmstory.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private String uid;
    private String pass;
    private String name;
    private String nick;
    private String email;
    private String hp;
    private int point;

    @Builder.Default
    private String role="USER";
    private String zip;
    private String addr1;
    private String addr2;
    private String regip;
    private String regDate;
    private String leaveDate;



}
