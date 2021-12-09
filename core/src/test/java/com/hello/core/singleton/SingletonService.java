package com.hello.core.singleton;

public class SingletonService {

	//static 영역에 하나만 생성 
	// jvm이 실행될 때 자기자신을 생성해서 instance에 넣어놓음 
	private static final SingletonService instance = new SingletonService();
	
	//조회(이 메서드로만 호출 가능,항상 같은 인스턴스를 반환함)
	public static SingletonService getInstance() { 
		return instance;
	}
	
	//private 생성자를 만들어 외부에서 SingletonService 생성하는 것을 방지함 
	//--> 딱 하나의 객체 인스턴스만 존재해야하기 때문에 
	private SingletonService () {
	}
	
	public void logic() {
		System.out.println("싱글톤 객체 로직 호출");
	}
	
	
}
