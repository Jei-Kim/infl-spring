# 스프링 입문 - 코드로 배우는 스프링 부트, 웹 MVC, DB 접근 기술
## hello-spring
<br>
    c.f. 기본편이랑 병행해서 들으면서 틈틈이 정리할 예정



# 스프링 핵심 원리 - 기본편
## core
<br>

## SECTION 1 _ 객체 지향 설계와 스프링


### 스프링 등장의 배경

- 2000년대 초반 사용하던 EJB(Enterprise Java Beans)의 단점: 코드의 복잡성, 속도 저하
- 위의 문제점을 극복하기 위해 POJO(Plain Old Java object): '순수한 자바로 돌아가자'는 의견이 다수 등장
- EJB 엔티티빈 기술을 대체하는 하이버네이트 등장, JPA(Java Persistence API)가 자바 표준이 됨
    - JPA == 표준 인터페이스
    - 하이버네이트, EclipseLink, etc == JPA 구현체들
- 2002년 스프링 핵심 코드를 포함한 오픈 소스 책 출간
    *** 강의는 이 배경의 시간적 흐름을 예제를 통해 살펴볼 예정


### 스프링 역사(릴리즈)

   - 2003 스프링 프레임워크 1.0 출시 - xml
   - 2006 스프링 프레임워크 2.0 출시 - xml 편의 기능 지원
   - 2009 스프링 프레임워크 3.0 출시 - 자바 코드로 설정
   - 2013 스프링 프레임워크 4.0 출시 - 자바8
   - 2014 스프링 부트 1.0 출시
       - 기존 스프링 프레임워크의 단점은 설정에 어려움을 겪는다는 점. 이를 보완하기 위해 기본으로 설정 + 서버까지 내장되어 있는 부트 출시 -> 설정에 대한 부담감을 줄이면서 사실상 표준으로 자리 잡음


### 스프링 생태계

- 필수
    - 스프링 프레임워크

    - 스프링 부트 
            : 스프링을 편리하게 사용할 수 있도록 지원, 최근에는 기본으로 사용
            *** 스프링부트는 스프링프레임워크를 도와주는 도구일 뿐, 단독으로 사용할 수 없다
    
- 선택
    - 스프링 데이터
    - 스프링 세션
    - 스프링 시큐리티
    - 스프링 Rest Docs
    - 스프링 클라우드
    - etc

    *** '스프링'단어는 문맥에 따라 해석해야 함(프레임워크, DI 컨테이너 등)
    *** 스프링은 결국 좋은 객체 프로그래밍을 지향하고, 도와주기 위해 만들어진 것


### 좋은 객체 지향 프로그래밍이란?

- 객체 지향 프로그래밍의 가장 중요한 키워드는 다형성(Polymorphism)
        
    * 다형성의 실세계 비유

            - 역할과 구현으로 세상을 구분
                ex. 로미오와 줄리엣 공연에서 각각의 주인공들은 어떤 배우가 맡아도 무관, 대체 가능함
            - 역할과 구현으로 구분하면 단순, 유연, 변경의 편리함이라는 이점이 있음
            - 클라이언트는 구현 대상의 역할(인터페이스)만 알면 됨
            - 클라이언트는 구현 대상의 내부 구조를 몰라도 됨
                ex. 자동차의 동작 원리를 모르더라도 사용범만 알면 됨
            - 클라이언트는 구현 대상의 내부 구조가 변경되거나 대상 자체를 변경해도 영향을 받지 않음

    * 자바 언어에서의 역할과 구현 분리
        - 다형성을 활용, 역할 == 인터페이스, 구현 == 인터페이스를 구현한 클래스, 구현 객체
        - 객체 설계 시에 역할과 구현을 !명확히! 분리
        - 객체 설계시 역할(인터페이스)을 먼저 부여하고, 그 역할을 수행하는 구현 객체를 만들자
    
    * 객체의 협력이라는 관계부터 생각
        - 클라이언트는 요청, 서버는 응답하는 객체이다. 혼자 있는 객체는 없으며, 수많은 객체 클라이언트와 객체 서버는 서로 협력 관계를 가짐

    * 자바 언어의 다형성
        - 오버라이딩 기반 이헤: 다형성으로 인터페이스를 구현한 객체를 실행 시점에 유연하게 변경 가능

    * 다형성의 본질
        - 협력이라는 객체사이의 관계를 통해 이해, 클라이언트를 변경하지 않고 서버의 구현 기능을 유연하게 변경 가능

    *** but 다형성은 인터페이스의 역할이 중요하기 때문에, 그만큼 안정적으로 잘 설계하는 것이 중요함
    
    * 스프링과 객체 지향

        - 스프링은 결국 다형성을 극대화해서 이용할 수 있게 도와주는 역할
        - 제어의 역전(IoC), 의존관계 주입(DI)은 다형성을 활용해서 '역할'과 '구현'을 편리하게 다룰 수 있도록 지원
        - 레고 블럭 조립이나 위의 예시처럼 공연 배우 선택 등 구현체를 편리하게 변경 가능하다는 이점이 있음


### 좋은 객체 지향 설계의 5가지 원칙(SOLID)

 - SRP 단일 책임 원칙(Single responsibility principle)
    : 한 클래스는 하나의 책임만!

 - ***OCP 개방-폐쇄 원칙(Open/closed principle)
    : 확장에는 열려있으나, 변경에는 닫혀있어야 함
    - 구현 객체를 변경하려면 클라이언트 코드를 변경해야 하는 경우에는 ocp원칙을 지킬 수 없다.
        -> 이러한 경우 해결법으로 스프링 DI 사용, 추후 강의에서 설명 예정

- LSP 리스코프 치환 원칙(Liskov substitution principle)
    : 프로그램의 객체는 프로그램의 정확성을 깨뜨리지 않으면서 하위 타입의 인스턴스로 바꿀 수 있어야 함
        ex. 자동차 인터페이스의 엑셀은 앞으로 가라는 기능, 뒤로 가게 구현하면 LSP 위반, 느리 더라도 앞으로 가야함

- ISP 인터페이스 분리 원칙(Interface segregation principle)
    : 특정 클라이언트를 위한 인터페이스 여러 개가 범용 인터페이스 하나보다 나음
        -> why? 인터페이스가 명확해지고, 대체 가능성이 높아지기 때문

- ***DIP 의존관계 역전 원칙(Dependency inversion principle)
    : 구현 클래스에 의존하지 말고, 인터페이스에 의존하라 -> why? 구현체에 의존하게 되면 변 경이 아주 어려워진다.

*** 객체 지향의 핵심은 다형성 but 다형성 만으로는 쉽게 부품을 교체하듯 개발하는 것이 어려움(ocp,dip 원칙 준수 어려움) --> 해결책? 스프링


### 객체 지향 설계와 스프링

* 스프링과 객체지향
        
    - 스프링은 다음 기술로 다형성 + OCP, DIP를 가능하게 지원
            - DI 컨테이너 제공
            - DI(Dependency Injection): 의존관계, 의존성 주입
    - 클라이언트 코드의 변경 없이 기능 확장 쉽게 부품을 교체하듯이 개발

*** 책 추천: 객체지향의 사실과 오해, 토비의 스프링3.1, 자바 ORM 표준 JPA 프로그래밍 


## SECTION 2 _ 스프링 핵심 원리 이해1 - 예제 만들기 
    
    c.f. 설정 용이하기 위해 스프링 부트 이용 but 이번 챕터는 순수 자바 코드로만 진행될 예정
    

### 프로젝트 생성

- java 11 설치(기존)
- 스프링 부트
        https://start.spring.io/spring.io.starter 에서 gradle, java, 2.3.3(snapshot없는 최신버전), 11, jar dependencies 추가 없이 생성 -> 압축해제 -> 임포트
            *** 강의와 별도로 build.gradle에 이클립스 관련 설정 추가함


### 비즈니스 요구사항과 설계(pdf 참고)

- 회원
        회원을 가입하고 조회할 수 있다.
        회원은 일반과 VIP 두 가지 등급이 있다.
        회원 데이터는 자체 DB를 구축할 수 있고, 외부 시스템과 연동할 수 있다. (미확정)

- 주문과 할인 정책
        회원은 상품을 주문할 수 있다.
        회원 등급에 따라 할인 정책을 적용할 수 있다.
        할인 정책은 모든 VIP는 1000원을 할인해주는 고정 금액 할인을 적용해달라. (나중에 변경 될 수 있다.)
        할인 정책은 변경 가능성이 높다. 회사의 기본 할인 정책을 아직 정하지 못했고, 오픈 직전까지 고민을 미루고 싶다. 최악의 경우 할인을 적용하지 않을 수 도 있다. (미확정)

    *** 요구사항을 보면 회원 데이터, 할인 정책 같은 부분은 지금 결정하기 어려운 부분이다. 그렇다고 이런 정책이 결정될 때 까지 개발을 무기한 기다릴 수 도 없다. 우리는 앞에서 배운 객체 지향 설계 방법을 이용할 것


### 회원 도메인 설계(pdf 참고)

- 보통 아래 3개의 그림을 기반으로 설계 시작

     - 회원 도메인 협력관계: 기획자 & 개발자가 함께 보는 자료, 이를 기반으로 개발자가 아래 두 개의 다이어그램으로 구체화
    - 회원 클래스 다이어그램: 실제 서버를 실행하지 않고 클래스만 분석해서 볼 수 있는 그림
    - 회원 객체 다이어그램: 서버가 실행될 때 결정, 클래스가 실제로 사용하는 클래스 결정
        ex. 회원 서비스 == MemberServiceImpl


### 회원 도메인 개발

- main > member 패키지 생성, 하위에 아래 객체들 생성
    - 회원 엔티티: Member 클래스, Grade 이넘클래스 생성
    - 회원 저장소: MemberRepository 인터페이스 생성, 구현체 MemoryMemberRepository 클래스 생성 
                *** HashMap 은 동시성 이슈가 발생할 수 있다. 이런 경우 ConcurrentHashMap 을 사용
    - 회원 서비스: MemberService 인터페이스 생성, 구현체 MemberServiceImpl 클래스 생성

    *** 요구사항 및 도메인 설계와 비교하며 객체 구조 이해하면 좋을 듯


### 회원 도메인 실행과 테스트

- 애플리케이션 로직 테스트
    - member 패키지 하위에 회원 도메인 (MemberApp) 생성 but 좋은 방법이 아님 -> 대안: JUnit 테스트를 사용할 것

- JUnit 테스트
        - test > member 패키지 생성, 하위에 테스트용 memberApp 클래스 생성 & 테스트

    *** 회원 도메인 설계 문제점: 의존관계가 인터페이스뿐만 아니라 구현까지 모두 의존함
        -> 주문 도메인까지 개발 후에 문제점, 해결방안 설명 예정


### 주문과 할인 도메인 설계(pdf 참고)

- 회원 도메인과 설명 동일
- 주문 도메인 전체, 주문 도메인 클래스 다이어그램, 주문 도메인 객체 다이어그램으로 나누어서 설계
     *** 설계의 핵심은 아직 정해지거나 구체화되지 않은 조건도 객체지향적인 생각을 배경으로 틀을 만들어 놓을 수 있어야 한다는 것


### 주문과 할인 도메인 개발

- main > discount 패키지 생성, 하위에 아래 객체들 생성
    - 할인 정책 인터페이스 DiscountPolicy & 정액 할인 정책 구현체 FixDiscountPolicy 생성

- main > order 패키지 생성, 하위에 아래 객체들 생성
    - 주문 엔티티: Order 클래스 생성
    - 주문 서비스 인터페이스 OrderService & 주문 서비스 구현체 OrderServiceImpl 생성
        -> 주문 생성 요청이 오면, 회원 정보를 조회하고, 할인 정책을 적용한 다음 주문 객체를 생성해서 반환한다. 
            메모리 회원 리포지토리와, 고정 금액 할인 정책을 구현체로 생성한다.


### 주문과 할인 도메인 실행과 테스트

- 회원과 동일하게 애플리케이션 로직 테스트, JUnit 테스트 사용


## SECTION 3 _ 스프링 핵심 원리 이해2 - 객체 지향 원리 적용 


### 새로운 할인 정책 개발

- 개발 도중 기획자가 요구사항을 변경, 추가할 경우에 대한 강의
    ex. 기존의 정액 할인에서 정률 할인으로 정책을 변경할 경우
    *** 애자일: 규칙을 따르기보다는 변화에 빠르게 대응하라

- discountPolicy의 구현체 RateDiscountPolicy 클래스 생성
- test > discount 패키지 생성, 하위에 RateDiscountPolicyTest 클래스 생성하여 JUnit 테스트 수행
    *** 인텔리제이에서는 테스트 클래스를 자동으로 생성해주는 기능이 있음, 이클립스는 수동으로 만들어 줄 것
    *** 테스트 클래스 작성 시 중요한 점: 성공할 경우 & 실패할 경우의 메서드를 모두 작성해서 테스트해보기


### 새로운 할인 정책 적용과 문제점

- 새로운 할인 정책을 애플리케이션에 적용
    - 클라이언트인 OrderServiceImpl 코드 수정
        private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
        -> private final DiscountPolicy discountPolicy = new RateDiscountPolicy();

- 문제점 발견(pdf 그림 참고)
    - 클래스 의존관계를 분석해 보자. 추상(인터페이스) 뿐만 아니라 구체(구현) 클래스에도 의존하고 있다.
    - 추상(인터페이스) 의존: DiscountPolicy
    - 구체(구현) 클래스: FixDiscountPolicy , RateDiscountPolicy
    ---> DIP(구체에 의존하지말고 추상에만 의존하라) 위반, OCP(변경하지 않고 확장 가능) 위반

- 문제점 해결
    - 해결책 모색은 어떻게 구체에 의존하지 않을 수 있는가?에서 출발
    - 인터페이스에만 의존하도록 설계를 변경
        - 클라이언트인 OrderServiceImpl 코드 수정
            -  private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
                -> private DiscountPolicy discountPolicy;
    ---> but 구현체가 없어서 NPE(null pointer exception)발생
    ----> 해결방안? 누군가가 클라이언트인 OrderServiceImpl에 DiscountPolicy의 구현 객체를 대신 생성하고 주입해주어야 함
            (다음 강의에 이어짐)


### 관심사의 분리

- 이해를 위한 예시
    - 애플리케이션 == 공연 / 인터페이스 == 배역 / 구현체 == 배우라고 가정
    - 배우가 상대배역을 맡을 배우를 캐스팅하지 않음. 그러나 이전 코드는 인터페이스가 구현체를 선택함
        -> 다양한 책임을 가지고 있음(단점)

- 해결법: 관심사를 분리하자
    - 배우는 본인의 역할인 배역을 수행하는 것에만 집중해야 한다.
    - 배우는 어떤 상대역이 선택되더라도 똑같이 공연을 할 수 있어야 한다.
    - 공연을 구성하고, 담당 배우를 섭외하고, 역할에 맞는 배우를 지정하는 책임을 담당하는 **별도의 공연 기획자**가 나올 시점이다.
    - 공연 기획자를 만들고, 배우와 공연 기획자의 책임을 확실히 분리

- **별도의 공연 기획자** 역할을 하는 AppConfig의 등장
    : 애플리케이션의 전체 동작 방식을 구성(config)하기 위해 구현 객체를 생성하고 연결하는 책임을 가지는 별도의 설정 클래스

    - AppConfig는 애플리케이션의 실제 동작에 필요한 구현 객체를 생성
        - MemberServiceImpl
        - MemoryMemberRepository
        - OrderServiceImpl
        - FixDiscountPolicy

    - AppConfig는 생성한 객체 인스턴스의 참조(레퍼런스)를 생성자를 통해서 주입(연결)
        - MemberServiceImpl & MemoryMemberRepository
        - OrderServiceImpl & MemoryMemberRepository, FixDiscountPolicy
    *** 생성자 없어서 컴파일 오류 발생, 각각의 구현 클래스에서 생성자를 주입해주어야 함

    - 설계 변경으로 MemberServiceImpl 은 MemoryMemberRepository 를 의존하지 않음
    - 단지 MemberRepository 인터페이스만 의존함
    - MemberServiceImpl 입장에서 생성자를 통해 어떤 구현 객체가 들어올지(주입될지)는 알 수 없음
    - MemberServiceImpl의 생성자를 통해서 어떤 구현 객체를 주입할지는 오직 외부( AppConfig )에서 결정됨
    - MemberServiceImpl 은 이제부터 의존관계에 대한 고민은 외부에 맡기고 실행에만 집중하면 된다.

    *** OrderServiceImpl도 상동

    **DI(Dependency Injection; 의존성 주입)**
       - appConfig 객체는 memoryMemberRepository 객체를 생성하고 그 참조값을 memberServiceImpl 을 생성하면서 생성자로 전달한다.
        - 클라이언트인 memberServiceImpl 입장에서 보면 의존관계를 마치 외부에서 주입해주는 것 같다고 해서 DI(Dependency Injection) 우리말로 의존관계 주입 또는 의존성 주입이라 한다.


### AppConfig 리팩터링

- 현재 AppConfig 코드에서는 구현과 역할을 한 눈에 알아볼 수 없음 
    ex. memberRepository 라는 역할은 보이지 않음
    -> 중복을 제거하고, 역할에 따른 구현이 보이도록 리팩터링 하자
        (구성&설정을 담당하는 AppConfig에서는 역할과 구현 클래스가 한눈에 들어와야 함)


### 새로운 구조와 할인 정책 적용

- AppConfig 에서 할인 정책 역할을 담당하는 구현을 FixDiscountPolicy RateDiscountPolicy 객체로 변경
    - 이제 할인 정책을 변경해도, 애플리케이션의 구성 역할을 담당하는 AppConfig만 변경하면 됨
    - 클라이언트 코드인 OrderServiceImpl 를 포함해서 사용 영역의 어떤 코드도 변경할 필요가 없음
    - 구성 영역은 당연히 변경된다. 구성 역할을 담당하는 AppConfig를 애플리케이션이라는 공연의 기획자로 생각하자. 공연 기획자는 공연 참여자인 구현 객체들을 모두 알아야 한다.


### 전체 흐름 정리

- 새로운 할인 정책 개발 -> 새로운 할인 정책 적용과 문제점 관심사의 분리 -> AppConfig 리팩터링 -> 새로운 구조와 할인 정책 적용
    *** 위의 각 단계 세부설명을 통해 어떤 변화가 있었는지 복습할 것(AppConfig의 등장과 그 역할에 집중해서)


### 좋은 객체 지향 설계의 5가지 원칙의 적용

- 이전 강의에서 학습한 '좋은 객체 지향 설계의 5가지 원칙'이 이번 섹션에서 어떻게 활용되었는지 학습

* SRP(한 클래스는 하나의 책임만)
    - 기존에 클라이언트 객체가 직접 구현 객체를 생성, 연결, 실행하는 다양한 책임을 수행했던 문제점을 해결하기 위해 관심사를 분리함
    - AppConfig 클래스 생성하여 구현 객체를 생성, 연결하는 역할 수행 / 클라이언트는 실행하는 책임만을 담당함

* DIP(추상화에 의존해야지, 구체화에 의존하면 안 된다 -> 의존성 주입 활용)
    - 기존 클라이언트 코드( OrderServiceImpl )는 DIP를 지키며 DiscountPolicy 추상화와 FixDiscountPolicy 구체화 구현 클래스에 동시에 의존
        -> 변경 시 클라이언트 코드도 함께 변경해야 하는 문제 발생

    **해결**
     클라이언트 코드가 DiscountPolicy 추상화 인터페이스에만 의존하도록 코드를 변경 후, AppConfig가 FixDiscountPolicy 객체 인스턴스를 클라이언트 코드 대신 생성해서 클라이언트 코드에 의존관계를 주입

* OCP(확장에는 열리고 변경에는 닫혀야 함)가 적용되었음
    - AppConfig 생성 및 리팩터링으로 변경 시 사용 영역의 어떤 코드도 변경할 필요가 없었고, 구성 영역만 변경하면 되도록 구현


### IoC, DI, 그리고 컨테이너

* 제어의 역전 IoC(Inversion of Control)
    - 내가 직접 호출하는 것이 아니라, 프레임워크와 같은 외부에서 호출을 대신해 줌
    - 스프링에만 국한되는 개념이 아님! 개발 관련해서 자주 나오는 중요한 개념
    
    * 프레임워크 vs 라이브러리
        - 프레임워크는 내가 작성한 코드를 제어하고, 대신 실행함 ex. JUnit
        - 라이브러리는 내가 작성한 코드가 직접 제어의 흐름을 담당함 ex. java코드를 xml이나 json으로 변경하는 경우

* 의존관계 주입 DI(Dependency Injection)
    - 의존관계는 정적인 클래스 의존 관계와, 실행 시점에 결정되는 동적인 객체(인스턴스) 의존 관계 둘을 분리해서 생각해야 함
    - 정적인 클래스 의존관계는 애플리케이션을 실행하지 않아도 분석할 수 있음
    - 동적인 갹체 의존관계는 애플리케이션 실행 시점에 실제 생성된 객체 인스턴스의 참조가 연결된 의존 관계임

    - 애플리케이션 실행 시점(런타임)에 외부에서 실제 구현 객체를 생성하고 클라이언트에 전달해서 클라이언트와 서버의 실제 의존관계가 연결 되는 것을 의존관계 주입이라 함
        -> 정적인 클래스 의존관계를 변경하지 않고, 동적인 객체 인스턴스 의존관계를 쉽게 변경할 수 있다.

* IoC 컨테이너, DI 컨테이너
    - AppConfig 처럼 객체를 생성하고 관리하면서 의존관계를 연결해 주는 것
    - 의존관계 주입에 초점을 맞추어 최근에는 주로 DI 컨테이너라 함(어샘블러, 오브젝트 팩토리라고 불리기도 함)
    - 스프링이 DI 컨테이너 역할을 하는 것은 맞지만, DI 컨테이너가 반드시 스프링을 뜻하는 것은 아님!


### 스프링으로 전환하기

- AppConfig 스프링 기반으로 변경
    - @Configuration 애노테이션으로 AppConfig가 설정을 구성하는 클래스임을 명시
    - 각 메서드에 @Bean 을 붙여 스프링 컨테이너에 스프링 빈으로 등록
- MemberApp에 스프링 컨테이너 적용, OrderApp에 스프링 컨테이너 적용

    **스프링 컨테이너**

    - ApplicationContext 를 스프링 컨테이너라 함
    - 기존(이전 강의까지)에는 개발자가 AppConfig 를 사용해서 직접 객체를 생성하고 DI를 했지만, 이제 스프링 컨테이너를 통해서 사용할 수 있음

    - 스프링 컨테이너는 @Configuration 이 붙은 AppConfig 를 설정(구성) 정보로 사용
        - 여기서 @Bean 이라 적힌 메서드를 모두 호출해서 반환된 객체를 스프링 컨테이너에 등록 -> 이렇게 스프링 컨테이너에 등록된 객체를 스프링 빈이라 한다.
    
    - 스프링 빈은 @Bean 이 붙은 메서드의 명을 스프링 빈의 이름으로 사용한다. ( memberService , orderService )
        -> @Bean(name:"aa") 이런 식으로 변경 가능하지만, 관례는 메서드 이름을 따르는 것
        
    - 기존에는 개발자가 필요한 객체를 AppConfig 를 사용해서 직접 조회 but 이제 스프링 컨테이너를 통해서 필요한 스프링 빈(객체)를 찾아야 함. 스프링 빈은 applicationContext.getBean() 메서드를 사용해서 찾음
    - 기존에는 개발자가 직접 자바코드로 모든 것을 했다면 이제 스프링 컨테이너에 객체를 스프링 빈으로 등록하고, 스프링 컨테이너에서 스프링 빈을 찾아서 사용하도록 변경됨
    
    ***스프링 컨테이너를 사용하면 어떤 장점이 있는지는 다음 강의를 통해 알아 볼 예정


## SECTION 4 _ 스프링 컨테이너와 스프링 빈


### 스프링 컨테이너 생성

***

ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

***

- ApplicationContext
    - 스프링 컨테이너, 인터페이스
    - 스프링 컨테이너는 XML기반 혹은 애노테이션 기반의 자바 설정 클래스로 만들 수 있음
    - 이전 강의에서 AppConfig를 사용했던 방식이 애노테이션 기반의 자바 설정 클래스 스프링 컨테이너를 만든 것

- 자바 설정 클래스를 기반으로 스프링 컨테이너(ApplicationContext) 생성
    - new AnnotationConfigApplicationContext(AppConfig.class);
        - 이 클래스는 ApplicationContext 인터페이스의 구현체

*** 정확히는 스프링 컨테이너를 부를 때 BeanFactory, ApplicationContext 로 구분 -> 다음 강의에 이어짐 
    BeanFactory를 직접 사용하는 경우는 거의 없으므로 일반적으로 ApplicationContext 를 스프링 컨테이너라 부름

* 스프링 컨테이너 생성 과정

    1. 스프링 컨테이너 생성
        - new AnnotationConfigApplicationContext(AppConfig.class)
            - 스프링 컨테이너를 생성할 때는 구성 정보를 지정해주어야 함
            - 여기서는 AppConfig.class 를 구성 정보로 지정함

    2. 스프링 빈 등록
        - AppConfig 클래스 메서드에 @Bean 애노테이션 추가
            - 스프링 컨테이너는 파라미터로 넘어온 설정 클래스 정보를 사용해서 스프링 빈을 등록
            *** 빈 이름은 메서드 이름을 기본으로 사용, 빈 이름을 직접 부여할 수도 있음. but 관례상 기본으로 사용
        
    3. 스프링 빈 의존관계 설정 - 준비
        - 스프링 컨테이너는 설정 정보를 참고해서 의존관계를 주입(DI)함
        *** 싱글톤 컨테이너에서 설명 예정

### 컨테이너에 등록된 모든 빈 조회

- ApplicationContextInfoTest 클래스 생성 -> 등록된 빈 조회
    * 모든 빈 출력하기
         - ac.getBeanDefinitionNames() : 스프링에 등록된 모든 빈 이름을 조회
         - ac.getBean() : 빈 이름으로 빈 객체(인스턴스)를 조회한다.
    * 애플리케이션 빈 출력하기
    : 스프링 내부 빈 제외하고 내가 등록한 빈만 출력 -> 스프링이 내부에서 사용하는 빈은 getRole() 로 구분
    - ROLE_APPLICATION : 일반적으로 사용자가 정의한 빈
    - ROLE_INFRASTRUCTURE : 스프링이 내부에서 사용하는 빈

### 스프링 빈 조회 - 기본

- ApplicationContextBasicFindTest
    - ac.getBean(빈이름, 타입)
    - ac.getBean(타입)

c.f. 조회 대상 스프링 빈이 없으면 예외 발생
    NoSuchBeanDefinitionException: No bean named 'xxxxx' available

### 스프링 빈 조회 - 동일한 타입이 둘 이상

- ApplicationContextSameBeanFindTest
    - 타입으로 조회시 같은 타입의 스프링 빈이 둘 이상이면 오류가 발생 -> 빈 이름 지정으로 해결
    - ac.getBeansOfType() 을 사용하면 해당 타입의 모든 빈을 조회함

### 스프링 빈 조회 - 상속 관계

- ApplicationContextExtendsFindTest

### BeanFactory와 ApplicationContext

* BeanFactory
    - 스프링 컨테이너의 최상위 인터페이스
    - 스프링 빈을 관리하고 조회하는 역할을 담당
    - getBean() 을 제공

* ApplicationContext
    - BeanFactory 기능을 모두 상속받아서 제공함
    - 빈을 관리하고 검색하는 기능을 하는데, BeanFactory와의 차이는? 
        - 애플리케이션을 개발할 때는 빈은 관리하고 조회하는 기능은 물론이고, 수 많은 부가기능이 필요

    * ApplicationContext 부가기능
        - 메시지소스를 활용한 국제화 기능: 접근 위치에 따라 그에 알맞는 언어 제공
        - 애플리케이션 이벤트: 이벤트를 발행하고 구독하는 모델을 편리하게 지원
        - 편리한 리소스 조회: 파일, 클래스패스, 외부 등에서 리소스를 편리하게 조회

-> BeanFactory를 직접 사용할 일은 거의 없다. 부가기능이 포함된 ApplicationContext를 사용
-> BeanFactory나 ApplicationContext를 스프링 컨테이너라 함

### 다양한 설정 형식 지원 - 자바 코드, XML

- 스프링 컨테이너는 다양한 형식의 설정 정보를 받아드릴 수 있게 유연하게 설계되어 있음
    -> 자바 코드, XML, Groovy 등등

* 애노테이션 기반 자바 코드 설정 사용
    - 강의에서 계속 진행되었던 설정 방법
    - new AnnotationConfigApplicationContext(AppConfig.class)
    - AnnotationConfigApplicationContext 클래스를 사용하면서 자바 코드로된 설정 정보를 넘기면 됨

* XML 설정 사용
    - GenericXmlApplicationContext 를 사용하면서 xml 설정 파일을 넘기는 방식

### 스프링 빈 설정 메타 정보 -BeanDefinition

- 스프링이 다양한 설정 형식을 지원할 수 있는 이유는 BeanDefinition 추상화
- 역할과 구현을 개념적으로 나눈 것
    - XML을 읽어서 BeanDefinition을 만들거나 자바 코드를 읽어서 BeanDefinition을 만들면 됨
    - 스프링 컨테이너는 자바 코드인지, XML인지 몰라도 BeanDefinition만 알면 됨

- BeanDefinition 을 빈 설정 메타정보라 함
- @Bean , <bean> 당 각각 하나씩 메타 정보가 생성
    - 스프링 컨테이너는 이 메타정보를 기반으로 스프링 빈을 생성

- AnnotationConfigApplicationContext 는 AnnotatedBeanDefinitionReader 를 사용해서 AppConfig.class 를 읽고 BeanDefinition 을 생성
- GenericXmlApplicationContext 는 XmlBeanDefinitionReader 를 사용해서 appConfig.xml 설정 정보를 읽고 BeanDefinition 을 생성

-> 스프링이 다양한 형태의 설정 정보를 BeanDefinition으로 추상화해서 사용하는 것 정도만 이해하기!


## SECTION 5 _ 싱글톤 컨테이너


### 웹 애플리케이션과 싱글톤

- 스프링은 기업용 온라인 서비스 기술을 지원하기 위해 탄생 
    -> 대부분의 스프링 애플리케이션은 웹 애플리케이션
    -> 웹 애플리케이션은 보통 여러 고객이 동시에 요청을 함

* 스프링 없는 순수한 DI 컨테이너 테스트 생성
    *** 직접 생성하고 단점 및 한계 알아보기
    - test 하위에 Singleton패키지 생성, SingletonTest 클래스 생성
    -> 스프링 없는 순수한 DI 컨테이너인 AppConfig는 요청을 할 때 마다 객체를 새로 생성 -> 자원낭비
        => 해결방안? 해당 객체가 딱 1개만 생성되고, 공유하도록 설계할 것


### 싱글톤 패턴

- 클래스의 인스턴스가 딱 1개만 생성되는 것을 보장하는 디자인 패턴
- 객체 인스턴스를 2개 이상 생성하지 못하게 막아야 함(private 생성자 이용)

* 싱글톤 패턴 생성
    - SingletonService클래스, singletonServiceTest 메서드 생성

    *** main이 아닌 test위치에 생성(main 내 클래스들에 영향 주지 않기 위해)
    - 1. static 영역에 객체 instance를 미리 하나 생성해서 올려둔다.
    - 2. 이 객체 인스턴스가 필요하면 오직 getInstance() 메서드를 통해서만 조회할 수 있다. 이 메서드를 호출하면 항상 같은 인스턴스를 반환한다.
    - 3. 딱 1개의 객체 인스턴스만 존재해야 하므로, 생성자를 private으로 막아서 혹시라도 외부에서 new 키워드로 객체 인스턴스가 생성되는 것을 막는다.

c.f. 싱글톤 패턴을 구현하는 방법은 여러가지가 존재, 여기서는 객체를 미리 생성해두는 방법을 택함(안전, 단순)

-> 싱글톤 패턴을 적용해서 고객의 요청이 들어올 때마다 객체를 생성하는 문제점 해결,
    이미 만들어진 객체를 공유해서 효율적으로 사용 가능해짐. but 여전히 문제점이 존재함(아래 계속)

* 싱글톤 패턴의 문제점
    - 싱글톤 패턴을 구현하는 코드 자체가 많이 들어감
    - 의존관계상 클라이언트가 구체 클래스에 의존 -> DIP 위반
    - 클라이언트가 구체 클래스에 의존 -> OCP 원칙을 위반할 가능성이 높음
    - 테스트 어려움
    - 내부 속성을 변경하거나 초기화 하기 어려움
    - private 생성자로 자식 클래스를 만들기 어려움
    -> 결론적으로 유연성이 떨어짐


### 싱글톤 컨테이너

- 기존에 싱글톤 패턴의 문제점을 모두 해결 + 객체 인스턴스를 하나로 유지하는 장점만 살리는 스프링 컨테이너
- 스프링 빈 = 싱글톤으로 관리되는 빈

* 싱글톤 컨테이너
    - 스프링 컨테이너는 싱글턴 패턴을 적용하지 않아도 객체 인스턴스를 싱글톤으로 관리해 줌
    - 스프링 컨테이너는 싱글톤 컨테이너 역할을 함
    *** 싱글톤 객체를 생성하고 관리하는 기능을 싱글톤 레지스트리라고 함
    - DIP, OCP, 테스트, private 생성자로부터 자유롭게 싱글톤을 사용할 수 있음

- 스프링 컨테이너를 사용하는 테스트 코드 작성
    - springContainer 테스트 메서드 생성
-> 스프링 컨테이너 덕분에 고객의 요청이 올 때 마다 객체를 생성하는 것이 아니라, 이미 만들어진 객체를 공유 & 재사용

c.f. 스프링의 기본 빈 등록 방식은 싱글톤이지만, 싱글톤 방식만 지원하는 것은 아님
     요청할 때마다 새로운 객체를 생성해서 반환하는 기능도 제공함. (이후 빈 스코프 강의에서 설명 예정)


### 싱글톤 방식의 주의점

- 싱글톤 패턴이든, 스프링 같은 싱글톤 컨테이너를 사용하든, 객체 인스턴스를 하나만 생성해서 공유하는 싱글톤 방식은 여러 클라이언트가 하나의 같은 객체 인스턴스를 공유하기 때문에,
     싱글톤 객체는 상태를 유지(stateful)하게 설계하면 안된다.

    - 상태를 유지할 경우 발생하는 문제점 예시
        - StatefulService, StatefulServiceTest생성
            -> 기대되는 값과 다르게 담기는 것을 확인 (고객A의 주문금액에 고객B의 주문금액이 담기는 문제점)
                why? StatefulService 의 price 필드는 공유되는 필드인데, 특정 클라이언트가 값을 변경하기 때문
            **공유필드는 조심해야 함! 스프링 빈은 항상 무상태(stateless)로 설계하자**


### @Configuration과 싱글톤

- AppConfig 코드를 보면 결과적으로 각각 다른 2개의 MemoryMemberRepository가 생성되면서 싱글톤이 깨지는 것처럼 보임
    -> 스프링 컨테이너는 이 문제를 어떻게 해결할까?
    - 테스트 용도로 MemberServiceImpl에 getMemberRepository메서드 추가, 테스트 코드 추가
        -> 테스트 결과, memberRepository 인스턴스는 모두 같은 인스턴스가 공유되어 사용되는 것을 확인
        -> AppConfig에 호출 로그를 남겨 확인해보면, 자바 코드상으로는 memberRepository가 세 번 호출되어야 하지만, 한 번만 호출됨
*** 이유는 다음 강의에서 설명


### @Configuration과 바이트코드 조작의 마법

- 스프링은 클래스의 바이트코드를 조작하는 라이브러리를 사용하여 스프링 빈이 싱글톤이 되도록 보장함
    - configurationDeep 테스트 코드 생성하여 확인
        -> 순수 클래스명이 아닌, 클래스 명에 xxxCGLIB가 붙어 출력됨.
        -> 스프링이 CGLIB라는 바이트코드 조작 라이브러리를 사용해서 AppConfig 클래스를 상속받은 임의의 다른 클래스를 만들고, 그 다른 클래스를 스프링 빈으로 등록한 것
    - @Bean이 붙은 메서드마다 이미 스프링 빈이 존재하면 존재하는 빈을 반환,
        만약 스프링 빈이 없으면 생성해서 스프링 빈으로 등록하고 반환하는 코드가 동적으로 생성됨 -> 덕분에 싱글톤 보장됨

- @Configuration 을 적용하지 않고, @Bean 만 적용한다면?
    -> MemberRepository가 총 3번 호출됨
    -> 각각 다 다른 MemoryMemberRepository 인스턴스를 가지고 있음

* 정리
- @Bean만 사용해도 스프링 빈으로 등록되지만, 싱글톤을 보장하지 않음
    - memberRepository() 처럼 의존관계 주입이 필요해서 메서드를 직접 호출할 때 싱글톤을 보장하지 않음
    - 크게 고민할 것이 없다. 스프링 설정 정보는 항상 @Configuration 을 사용하자

## SECTION 6 _ 컴포넌트 스캔

### 컴포넌트 스캔과 의존관계 자동 주입 시작하기

- 스프링 빈이 수백개가 되면 일일이 등록하기 귀찮고 설정 정보가 커지며 누락이 생기는 문제점 등 발생
    - AutoAppConfig 클래스 생성, @ComponentScan 애노테이션 추가
        - 컴포넌트 스캔은 이름 그대로 @Component 애노테이션이 붙은 클래스를 스캔해서 스프링 빈으로 등록
    - 클래스마다 @Component 추가

- 이전에 AppConfig에서는 @Bean 으로 직접 설정 정보를 작성, 의존관계도 직접 명시함
- 이제 설정 정보 자체가 없기 때문에, 의존관계 주입도 이 클래스 안에서 해결해야 함
<br> -> @Autowired로 의존관계를 자동으로 주입
(ComponentScan을 쓰게 되면 수동으로 설정해 줄 수 있는 공간이 없어지기 때문에 자연스레 Autowired도 쓰게 됨)
<br> **** ac.getBean(MemberRepository.class)와 비슷하게 작동한다고 이해(자세한 설명은 다음 섹션에서 이어짐)*

### 탐색 위치와 기본 스캔 대상

* 탐색 위치

    - 탐색할 패키지의 시작 위치 지정
        - 모든 자바 클래스를 다 컴포넌트 스캔하면 시간이 오래 걸림 -> 필요한 위치부터 탐색하도록 시작
위치를 지정 -> @ComponentScan(basePackages = "hello.core",}

   - 권장하는 방법
      - 설정 정보 클래스의 위치를 프로젝트 최상단에 둠
        - com.hello(프로젝트 시작 루트)에 AppConfig 같은 메인 설정 정보를 두고 컴포넌트 스캔 애노테이션 추가하기
        - 위의 방법과 같은 basePackages 지정은 생략

- c.f. 스프링 부트를 사용하면 스프링 부트의 대표 시작 정보인 @SpringBootApplication 를 이 프로젝트 시작 루트 위치에 두는 것이 관례

* 컴포넌트 스캔 기본 대상

- 컴포넌트 스캔은 @Component 뿐만 아니라 다음과 내용도 추가로 대상에 포함
 - & 부가 기능 수행 (+로 표시)
    - @Component : 컴포넌트 스캔에서 사용 
    - @Controlller : 스프링 MVC 컨트롤러에서 사용 (+ 스프링 MVC 컨트롤러로 인식)
    - @Service : 스프링 비즈니스 로직에서 사용 (특별한 처리 x, 개발자들이 핵심 비즈니스 계층 인식하는데 도움)
    - @Repository : 스프링 데이터 접근 계층에서 사용 (+ 데이터 계층의 예외를 스프링 예외로 변환)
    - @Configuration : 스프링 설정 정보에서 사용 (스프링 설정 정보로 인식하고, 스프링 빈이 싱글톤을 유지하도록 추가 처리)

### 필터

- MyIncludeComponent, MyExcludeComponent 인터페이스 생성 후 @Component 내 애노테이션 추가
- ComponentFilterAppConfigTest 클래스 생성 후 테스트
    - includeFilters 에 MyIncludeComponent 애노테이션을 추가해서 BeanA가 스프링 빈에 등록
    - excludeFilters 에 MyExcludeComponent 애노테이션을 추가해서 BeanB는 스프링 빈에 등록되지 않음을 확인

- FilterType에는 ANNOTATION, ASSIGNABLE_TYPE, ASPECTJ, REGEX, CUSTOM의 5가지가 있음
    - 옵션을 변경하면서 사용하기 보다는 스프링의 기본 설정에 최대한 맞추어 사용하는 것을 권장

### 중복 등록과 충돌

* 자동 빈 등록 vs 자동 빈 등록
    - ConflictingBeanDefinitionException 예외 발생

* 수동 빈 등록 vs 자동 빈 등록
    - 수동 빈이 자동 빈을 오버라이딩, 수동 빈 등록이 우선권을 가짐
    - 현실적으로 개발자의 의도와 달리 이런 결과가 만들어지기 때문에, 최근 스프링부트에서는 이런 경우 오류가 발생하도록 기본값을 바꿈
    - CoreApplication 실행하면(스프링부트를 통해 실행) 오류가 발생하는 것을 확인할 수 있음
        - -> (Consider renaming one of the beans or enabling overriding by setting
        spring.main.allow-bean-definition-overriding=true)
        - -> 안내대로 application.properties에 true로 바꿔주는 코드 추가하면 위와 같이 수동 빈이 오버라이딩 됨 but 권장하지 않음

## SECTION 7 _ 의존관계 자동 주입

## SECTION 8 _ 빈 생명주기 콜백

## SECTION 9 _ 빈 스코프

## SECTION 10 _ 다음으로