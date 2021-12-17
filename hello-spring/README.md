# 스프링 입문 - 코드로 배우는 스프링 부트, 웹 MVC, DB 접근 기술

## SECTION 01 _ 프로젝트 환경설정

    - pdf 참고, 이클립스 환경 구글링해서 적용

## SECTION 02 _ 스프링 웹 개발 기초

### 정적 컨텐츠

- 스프링 부트 정적 컨텐츠 기능
    - hello-static.html

### MVC와 템플릿 엔진

- MVC: Model, View, Controller

    - 웹브라우저에서 내장 톰캣 서버로 localhost:8080/hello-mvc 요청
    - 톰캣 서버가 스프링 부트 내 스프링 컨테이너 내 helloController 실행(mapping값 확인)
    - helloController가 hello-template 리턴, model(name:spring)
    - ViewResolver가 templates/hello-template.html(Thymeleaf 템플릿 엔진 처리) 웹 브라우저로 보냄 (html 변환 후)

### API

* @ResponseBody 문자 반환
    - ResponseBody 를 사용하면 뷰 리졸버( viewResolver )를 사용하지 않음
    - 대신에 HTTP의 BODY에 문자 내용을 직접 반환(HTML BODY TAG를 말하는 것이 아님)
        - ex. return "hello" + name일 때, http://localhost:8080/hello-string?name=spring실행 -> hello-spring 출력됨

* @ResponseBody 객체 반환
    - @ResponseBody 를 사용하고, 객체를 반환하면 객체가 JSON으로 변환됨


* 정리
 - @ResponseBody 를 사용
    - -> HTTP의 BODY에 문자 내용을 직접 반환
    - -> viewResolver 대신에 HttpMessageConverter 가 동작

****자세한 내용은 스프링 MVC강의에서 설명*

## SECTION 03 _ 회원 관리 예제 - 백엔드 개발

### 비즈니스 요구사항 정리

* 일반적인 웹 애플리케이션 계층 구조

    - 컨트롤러: 웹 MVC의 컨트롤러 역할
    - 서비스: 핵심 비즈니스 로직 구현
    - 리포지토리: 데이터베이스에 접근, 도메인 객체를 DB에 저장하고 관리
    - 도메인: 비즈니스 도메인 객체, 예) 회원, 주문, 쿠폰 등등 주로 데이터베이스에 저장하고 관리됨

### 회원 도메인과 리포지토리 만들기

* 회원 객체(domain)
    - Member 클래스 생성
        - id, name 변수 및 게터세터

* 회원 리포지토리 인터페이스(repository)
    - MemberRepository 클래스 생성
        - save, findById, findByName, findAll 메서드 선언

* 회원 리포지토리 메모리 구현체(repository)
    - MemoryMemberRepository 클래스 생성
        - save, findById, findByName, findAll 메서드 구현

### 회원 리포지토리 테스트 케이스 작성

- 개발한 기능을 실행해서 테스트 할 때 자바의 main 메서드를 통해서 실행하거나, 웹 애플리케이션의 컨트롤러를 통해서 해당 기능을 실행
    - -> 오래 걸리고, 반복 실행하기 어렵고 여러 테스트를 한번에 실행하기 어렵다는 단점
    - ->  JUnit이라는 프레임워크로 테스트를 실행해서 이러한 문제를 해결

    * AfterEach : 한번에 여러 테스트를 실행하면 메모리 DB에 직전 테스트의 결과가 남을 수 있다. 이렇게 되면 다음 이전 테스트 때문에 다음 테스트가 실패할 가능성이 있다. @AfterEach 를 사용하면 각 테스트가 종료될 때 마다 이 기능을 실행한다. 여기서는 메모리 DB에 저장된 데이터를 삭제

### 회원 서비스 개발

* 회원 서비스(service)
    - MemberService 클래스 생성
        - join, findMembers, findOne 메서드 구현

### 회원 서비스 테스트

- 회원 리포지토리의 코드가 회원 서비스 코드를 DI 가능하게 변경
(서비스에서 생성하지 않고 생성자로 주입받도록 코드 변경)

* @BeforeEach : 각 테스트 실행 전에 호출된다. 테스트가 서로 영향이 없도록 항상 새로운 객체를 생성하고, 의존관계도 새로 맺어준다.

## SECTION 04 _ 스프링 빈과 의존관계

### 컴포넌트 스캔과 자동 의존관계 설정

- 회원 컨트롤러에 의존관계 추가
    - MemberController클래스에 @Controller 애노테이션 추가
    - 생성자에 @Autowired 추가

* @Autowired
    - 생성자에 @Autowired 가 있으면 스프링이 연관된 객체를 스프링 컨테이너에서 찾아서 넣어준다. 
    - -> 이렇게 객체 의존관계를 외부에서 넣어주는 것을 DI (Dependency Injection), 의존성 주입이라 한다.

* 스프링 빈을 등록하는 방법
    - 컴포넌트 스캔과 자동 의존관계 설정 
    - 자바 코드로 직접 스프링 빈 등록하기

    * 컴포넌트 스캔 원리
    - @Component 애노테이션이 있으면 스프링 빈으로 자동 등록된다.
    - @Component 를 포함하는 다음 애노테이션도 스프링 빈으로 자동 등록된다. (@Controller, @Service, @Repository)

****스프링은 스프링 컨테이너에 스프링 빈을 등록할 때, 기본으로 싱글톤으로 등록한다(유일하게 하나만 등록해서 공유한다) 따라서 같은 스프링 빈이면 모두 같은 인스턴스다. 설정으로 싱글톤이 아니게 설정할 수 있지만, 특별한 경우를 제외하면 대부분 싱글톤을 사용한다.*

### 자바 코드로 직접 스프링 빈 등록하기

- SpringConfig 클래스에 @Configuration, @Bean 애노테이션 추가
- c.f. XML로 설정하는 방식도 있지만 최근에는 잘 사용하지 않으므로 생략
- 실무에서는 주로 정형화된 컨트롤러, 서비스, 리포지토리 같은 코드는 컴포넌트 스캔을 사용한다. 그리고 정형화 되지 않거나, 상황에 따라 구현 클래스를 변경해야 하면 설정을 통해 스프링 빈으로 등록한다.

## SECTION 05 _ 회원관리 예제 - 웹 MVC 개발

### 회원 웹 기능 - 홈 화면 추가

- HomeController 클래스 생성
    ( @GetMapping("/") )
- 홈 gtml 생성
- c.f. 컨트롤러가 정적 파일보다 우선순위가 높다.

### 회원 웹 기능 - 등록

* 회원 등록 폼 개발
    - MemberController(회원 등록 폼 컨트롤러) 클래스 생성
    - 회원 등록 폼 html 생성
    ( resources/templates/members/createMemberForm )

* 회원 등록 컨트롤러
    - MemberForm(웹 등록 화면에서 데이터를 전달 받을 폼 객체)
    - 회원 컨트롤러에 회원을 실제 등록하는 기능 추가

### 회원 웹 기능 - 조회

    - 회원 컨트롤러에 조회 기능 추가
    - 회원 리스트 html 생성

## SECTION 06 _ 스프링 DB 접근 기술

### H2 데이터베이스 설치
    
 - 개발이나 테스트 용도로 가볍고 편리한 DB, 웹 화면 제공

* 테이블 생성
    - 테이블 관리를 위해 프로젝트 루트에 sql/ddl.sql 파일을 생성
    - H2 데이터베이스에 접근해서 member 테이블 생성

### 순수 JDBC

* 환경 설정
    - build.gradle 파일에 jdbc, h2 데이터베이스 관련 라이브러리 추가
    - 스프링 부트 데이터베이스 연결 설정 추가
        - (resources/application.properties)

* Jdbc 리포지토리 구현
    - Jdbc 회원 리포지토리(JdbcMemberRepository)
        - MemberRepository 구현체
    - SpringConfig -> 스프링 설정 변경

- DataSource는 데이터베이스 커넥션을 획득할 때 사용하는 객체다. 스프링 부트는 데이터베이스 커넥션 정보를 바탕으로 DataSource를 생성하고 스프링 빈으로 만들어둔다. 그래서 DI를 받을 수 있다.

- 개방-폐쇄 원칙(OCP, Open-Closed Principle) 확장에는 열려있고, 수정, 변경에는 닫혀있다.

- 스프링의 DI (Dependencies Injection)을 사용하면 기존 코드를 전혀 손대지 않고, 설정만으로 구현 클래스를 변경할 수 있다.

- 예제 실습 결과: 데이터를 DB에 저장하므로 스프링 서버를 다시 실행해도 데이터가 안전하게 저장된다.

### 스프링 통합 테스트

- 스프링 컨테이너와 DB까지 연결한 통합 테스트를 진행
    - @SpringBootTest : 스프링 컨테이너와 테스트를 함께 실행한다.
    - @Transactional : 테스트 케이스에 이 애노테이션이 있으면, 테스트 시작 전에 트랜잭션을 시작하고, 테스트 완료 후에 항상 롤백한다. 이렇게 하면 DB에 데이터가 남지 않으므로 다음 테스트에 영향을 주지 않는다.

### 스프링 JdbcTemplate

- 순수 Jdbc와 동일한 환경설정을 하면 된다.
스프링 JdbcTemplate과 MyBatis 같은 라이브러리는 JDBC API에서 본 반복 코드를 대부분 제거해준다. 하지만 SQL은 직접 작성해야 한다.
    
- JdbcTemplateMemberRepository 생성
- JdbcTemplate을 사용하도록 스프링 설정 변경

### JPA

- JPA는 기존의 반복 코드는 물론이고, 기본적인 SQL도 JPA가 직접 만들어서 실행해준다.
- JPA를 사용하면, SQL과 데이터 중심의 설계에서 객체 중심의 설계로 패러다임을 전환을 할 수 있다. JPA를 사용하면 개발 생산성을 크게 높일 수 있다.

* 환경설정
    - build.gradle 파일에 JPA, h2 데이터베이스 관련 라이브러리 추가
    - 스프링 부트에 JPA 설정 추가
        - show-sql : JPA가 생성하는 SQL을 출력
        - ddl-auto : JPA는 테이블을 자동으로 생성하는 기능을 제공하는데 none 를 사용하면 해당 기능을 끈다.
        - create 를 사용하면 엔티티 정보를 바탕으로 테이블도 직접 생성해준다.

- JPA 엔티티 매핑
    - Member 클래스에 @Entity

- JPA 회원 리포지토리
    - JpaMemberRepository

- 서비스 계층에 트랜잭션 추가
    - 스프링은 해당 클래스의 메서드를 실행할 때 트랜잭션을 시작하고, 메서드가 정상 종료되면 트랜잭션을 커밋한다. 만약 런타임 예외가 발생하면 롤백한다.
    - JPA를 통한 모든 데이터 변경은 트랜잭션 안에서 실행해야 한다.

- JPA를 사용하도록 스프링 설정 변경

### 스프링 데이터 JPA

- 스프링 부트와 JPA만 사용해도 개발 생산성이 정말 많이 증가하고, 개발해야할 코드도 확연히 줄어듭니다. 여기에 스프링 데이터 JPA를 사용하면, 리포지토리에 구현 클래스 없이 인터페이스 만으로 개발을 완료할 수 있습니다. 
- 반복 개발해온 기본 CRUD 기능도 스프링 데이터 JPA가 모두 제공합니다.

- 스프링 데이터 JPA는 JPA를 편리하게 사용하도록 도와주는 기술입니다. 따라서 JPA를 먼저 학습한 후에 스프링 데이터 JPA를 학습해야 합니다.

* 스프링 데이터 JPA 제공 기능
    - 인터페이스를 통한 기본적인 CRUD
    - findByName() , findByEmail() 처럼 메서드 이름 만으로 조회 기능 제공
    - 페이징 기능 자동 제공

## SECTION 07 _ AOP

## AOP가 필요한 상황

- 모든 메소드의 호출 시간을 측정하고 싶다면?
- 공통 관심 사항(cross-cutting concern) vs 핵심 관심 사항(core concern) 회원 가입 시간, 회원 조회 시간을 측정하고 싶다면?

* MemberService 회원 조회 시간 측정 추가
    - -> 문제
        - 회원가입, 회원 조회에 시간을 측정하는 기능은 핵심 관심 사항이 아니다. 시간을 측정하는 로직은 공통 관심 사항이다.
        - 시간을 측정하는 로직과 핵심 비즈니스의 로직이 섞여서 유지보수가 어렵다. 시간을 측정하는 로직을 별도의 공통 로직으로 만들기 매우 어렵다.
        - 시간을 측정하는 로직을 변경할 때 모든 로직을 찾아가면서 변경해야 한다.

## AOP 적용

- AOP: Aspect Oriented Programming
- 공통 관심 사항(cross-cutting concern) vs 핵심 관심 사항(core concern) 분리

* 시간 측정 AOP 등록
    - -> 해결
    - 회원가입, 회원 조회등 핵심 관심사항과 시간을 측정하는 공통 관심 사항을 분리한다. 시간을 측정하는 로직을 별도의 공통 로직으로 만들었다.
    - 핵심 관심 사항을 깔끔하게 유지할 수 있다.
    - 변경이 필요하면 이 로직만 변경하면 된다.
    - 원하는 적용 대상을 선택할 수 있다.


