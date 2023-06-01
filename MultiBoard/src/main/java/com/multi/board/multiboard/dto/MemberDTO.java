package com.multi.board.multiboard.dto;

import lombok.Data;

@Data
public class MemberDTO {	//Member DTO
    private String id;
    private String password;
    private String name;
    private String email;
    private String address;
    private String phone;
}
