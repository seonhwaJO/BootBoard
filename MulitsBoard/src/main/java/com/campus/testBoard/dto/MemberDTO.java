package com.campus.testBoard.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Data
public class MemberDTO {
	private String id;
	private String password;
	private String name;
	private String address;
	private String cellphone;
}
