package jpabook.jpashop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // 스프링 부트의 자동 설정, 스프링 Bean 읽기와 생성을 모두 자동으로 설정된다.
public class JpashopApplication {

	public static void main(String[] args) {// main 메소드를 실행하면 스프링 부트 애플리케이션이 실행된다.


		Hello hello = new Hello(); // Hello 객체를 생성한다.
		hello.setData("hello"); // Hello 객체의 data 필드에 hello를 넣어준다.
		String data = hello.getData(); // Hello 객체의 data 필드를 가져와서 data에 넣어준다.
		System.out.println("data = " + data); // data = hello를 출력한다.

		SpringApplication.run(JpashopApplication.class, args); // 스프링 부트 애플리케이션을 실행한다.
	}

}
