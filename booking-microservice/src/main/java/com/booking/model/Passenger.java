package com.booking.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Passenger {
	
	private String name;
	private int age;
	private String sex;
	
	@Override
	public String toString() {
		return "Passenger [name=" + name + ", age=" + age + ", sex=" + sex + "]";
	}
}
