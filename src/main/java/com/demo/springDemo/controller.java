package com.demo.springDemo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class controller {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("vehiclemanagement.xml");
		Vehicle vehicle = (Vehicle) context.getBean("id");
		System.out.println(vehicle);
	}
}
