# *스프링 MVC 1편 - 백엔드 웹 개발 핵심 기술* 

<br>

# SECTION 01. 웹 애플리케이션 이해

## 웹 서버, 웹 애플리케이션 서버

* 웹 서버

    - HTTP 기반으로 동작
    - 정적 리소스 제공, 기타 부가기능 
    - 정적(파일) HTML, CSS, JS, 이미지, 영상 
    - 예) NGINX, APACHE

* 웹 애플리케이션 서버

    - HTTP 기반으로 동작
    - 웹 서버 기능 포함 + (정적 리소스 제공 가능)
    - 프로그램 코드를 실행해서 애플리케이션 로직 수행
        - 동적 HTML, HTTP API(JSON)
        - 서블릿, JSP, 스프링 MVC
    - 예) 톰캣(Tomcat) Jetty, Undertow

* 차이

    - 웹 서버는 정적 리소스(파일), WAS는 애플리케이션 로직 
        - 사실은 둘의 용어도 경계도 모호함
        - 웹 서버도 프로그램을 실행하는 기능을 포함하기도 함
        - 웹 애플리케이션 서버도 웹 서버의 기능을 제공함 
    - 자바는 서블릿 컨테이너 기능을 제공하면 WAS
        - 서블릿 없이 자바코드를 실행하는 서버 프레임워크도 있음
    - WAS는 애플리케이션 코드를 실행하는데 더 특화

* 웹 시스템 구성

    - WAS와 DB만으로 구성 시 
        - -> WAS가 너무 많은 역할을 담당, 서버 과부하 우려
        - -> WAS 장애시 오류 화면도 노출 불가능

    - WEB, WAS, DB로 구성 시
        - 정적 리소스는 웹 서버가 처리
        - 웹 서버는 애플리케이션 로직같은 동적인 처리가 필요하면 WAS에 요청을 위임
        - WAS는 중요한 애플리케이션 로직 처리 전담
        - 효율적인 리소스 관리(정적 리소스 많이 사용시 웹서버 증설, 애플리케이션 리소스 많이 사용 시 와스 증설)
        - WAS, DB 장애시 WEB 서버가 오류 화면 제공 가능
    
## 서블릿

* 특징

    - urlPatterns(/hello)의 URL이 호출되면 서블릿 코드가 실행
    - HTTP 요청 정보를 편리하게 사용할 수 있는 HttpServletRequest
    - HTTP 응답 정보를 편리하게 제공할 수 있는 HttpServletResponse
    - 개발자는 HTTP 스펙을 매우 편리하게 사용

* HTTP 요청, 응답 흐름

    - HTTP 요청시
        - WAS는 Request, Response 객체를 새로 만들어서 서블릿 객체 호출
        - 개발자는 Request 객체에서 HTTP 요청 정보를 편리하게 꺼내서 사용 
        - 개발자는 Response 객체에 HTTP 응답 정보를 편리하게 입력
        - WAS는 Response 객체에 담겨있는 내용으로 HTTP 응답 정보를 생성

* 서블릿 컨테이너

    - 톰캣처럼 서블릿을 지원하는 WAS를 서블릿 컨테이너라고 함
    - 서블릿 컨테이너는 서블릿 객체를 생성, 초기화, 호출, 종료하는 생명주기 관리 
    - 서블릿 객체는 **싱글톤으로 관리**
        - 고객의 요청이 올 때 마다 계속 객체를 생성하는 것은 비효율
        - 최초 로딩 시점에 서블릿 객체를 미리 만들어두고 재활용
        - 모든 고객 요청은 동일한 서블릿 객체 인스턴스에 접근
        - **공유 변수 사용 주의**
        - 서블릿 컨테이너 종료시 함께 종료
    - JSP도 서블릿으로 변환 되어서 사용
    - 동시 요청을 위한 멀티 쓰레드 처리 지원

## 동시 요청 - 멀티 쓰레드

* 쓰레드
    - 애플리케이션 코드를 하나하나 순차적으로 실행하는 것은 쓰레드
    - 자바 메인 메서드를 처음 실행하면 main이라는 이름의 쓰레드가 실행
    - 쓰레드가 없다면 자바 애플리케이션 실행이 불가능
    - 쓰레드는 한번에 하나의 코드 라인만 수행
    - 동시 처리가 필요하면 쓰레드를 추가로 생성

* 요청 마다 쓰레드 생성 - 장단점

- 장점
    - 동시 요청을 처리할 수 있다.
    - 리소스(CPU, 메모리)가 허용할 때 까지 처리가능
    - 하나의 쓰레드가 지연 되어도, 나머지 쓰레드는 정상 동작한다.

- 단점
    - 쓰레드는 생성 비용은 매우 비싸다.
        - 요청이 올 때마다 쓰레드 생성 시, 응답 속도 저하
    - 쓰레드는 컨텍스트 스위칭 비용이 발생한다.
    - 쓰레드 생성에 제한이 없다.
        - 고객 요청이 너무 많이 오면, CPU, 메모리 임계점을 넘어서 서버가 죽을 수 있다.

* 쓰레드 풀
- 요청 마다 쓰레드 생성의 단점 보완

    * 특징
        - 필요한 쓰레드를 쓰레드 풀에 보관하고 관리한다.
        - 쓰레드 풀에 생성 가능한 쓰레드의 최대치를 관리한다. 톰캣은 최대 200개 기본 설정 (변경 가능)

    * 사용
        - 쓰레드가 필요하면, 이미 생성되어 있는 쓰레드를 쓰레드 풀에서 꺼내서 사용한다.
        - 사용을 종료하면 쓰레드 풀에 해당 쓰레드를 반납한다.
        - 최대 쓰레드가 모두 사용중이어서 쓰레드 풀에 쓰레드가 없으면?
            - 기다리는 요청은 거절하거나 특정 숫자만큼만 대기하도록 설정할 수 있다.

    * 장점
        - 쓰레드가 미리 생성되어 있으므로, 쓰레드를 생성하고 종료하는 비용(CPU)이 절약되고, 응답 시간이 빠르다.
        - 생성 가능한 쓰레드의 최대치가 있으므로 너무 많은 요청이 들어와도 기존 요청은 안전하게 처리할 수 있다.

* WAS의 멀티 쓰레드 지원

- 멀티 쓰레드에 대한 부분은 WAS가 처리
- 개발자가 멀티 쓰레드 관련 코드를 신경쓰지 않아도 됨
- 개발자는 마치 싱글 쓰레드 프로그래밍을 하듯이 편리하게 소스 코드를 개발 
- 멀티 쓰레드 환경이므로 싱글톤 객체(서블릿, 스프링 빈)는 주의해서 사용

## HTML, HTTP API, CSR, SSR

* 정적 리소스
    - 고정된 HTML 파일, CSS, JS, 이미지, 영상 등을 제공 
    - 주로 웹 브라우저

* HTML 페이지
    - 동적으로 필요한 HTML 파일을 생성해서 전달
    - 웹 브라우저: HTML 해석

*  HTTP API
    - HTML이 아니라 데이터를 전달
    - 주로 JSON 형식 사용
    - 다양한 시스템에서 호출
    - 데이터만 주고 받음, UI 화면이 필요하면, 클라이언트가 별도 처리
    - 앱, 웹 클라이언트, 서버 to 서버

    * 다양한 시스템 연동
        - UI 클라이언트 접점
            - 앱 클라이언트
            - 웹 브라우저에서 자바스크립트를 통한 HTTP API 호출
            - React, Vue.js 같은 웹 클라이언트
        - 서버 to 서버
            - 주문 서버 -> 결제 서버
            - 기업간 데이터 통신


* SSR - 서버 사이드 렌더링
    - HTML 최종 결과를 서버에서 만들어서 웹 브라우저에 전달 
    - 주로 정적인 화면에 사용
    - 관련기술: JSP, 타임리프 -> 백엔드 개발자
    - ****SSR을 사용하더라도, 자바스크립트를 사용해서 화면 일부를 동적으로 변경 가능*

* CSR - 클라이언트 사이드 렌더링
    - HTML 결과를 자바스크립트를 사용해 웹 브라우저에서 동적으로 생성해서 적용 
    - 주로 동적인 화면에 사용, 웹 환경을 마치 앱 처럼 필요한 부분부분 변경할 수 있음
        - 예) 구글 지도, Gmail, 구글 캘린더
    - 관련기술: React, Vue.js -> 웹 프론트엔드 개발자

## 자바 백엔드 웹 기술 역사

* 과거 기술

- 서블릿 - 1997: HTML 생성이 어려움
- JSP - 1999: HTML 생성은 편리하지만, 비즈니스 로직까지 너무 많은 역할 담당
- 서블릿, JSP 조합 MVC 패턴 사용: 모델, 뷰 컨트롤러로 역할을 나누어 개발
- MVC 프레임워크 춘추 전국 시대 - 2000년 초 ~ 2010년 초
    - MVC 패턴 자동화, 복잡한 웹 기술을 편리하게 사용할 수 있는 다양한 기능 지원
    - 스트럿츠, 웹워크, 스프링 MVC(과거 버전)

* 현재

- 애노테이션 기반의 스프링 MVC 등장
    - @Controller
    - MVC 프레임워크의 춘추 전국 시대 마무리

- 스프링 부트의 등장
    - 스프링 부트는 서버를 내장
    - 과거에는 서버에 WAS를 직접 설치하고, 소스는 War 파일을 만들어서 설치한 WAS에 배포 
    - 스프링 부트는 빌드 결과(Jar)에 WAS 서버 포함 -> 빌드 배포 단순화

* 최신 기술 - 스프링 웹 플럭스(WebFlux)
    - 특징
        - 비동기 넌 블러킹 처리
        - 최소 쓰레드로 최대 성능 - 쓰레드 컨텍스트 스위칭 비용 효율화 
        - 함수형 스타일로 개발 - 동시처리 코드 효율화
        - 서블릿 기술 사용X
    - 한계
        - 웹 플럭스는 기술적 난이도 매우 높음
        - 아직은 RDB 지원 부족
        - 일반 MVC의 쓰레드 모델도 충분히 빠르다.

* 자바 뷰 템플릿 역사
- HTML을 편리하게 생성하는 뷰 기능


- JSP: 속도 느림, 기능 부족
- 프리마커(Freemarker), Velocity(벨로시티): 속도 문제 해결, 다양한 기능
- 타임리프(Thymeleaf)
    - 내추럴 템플릿: HTML의 모양을 유지하면서 뷰 템플릿 적용 가능
    - 스프링 MVC와 강력한 기능 통합
    - 최선의 선택, 단 성능은 프리마커, 벨로시티가 더 빠름


# SECTION 02. 서블릿

## 프로젝트 생성

- 스프링 프로젝트 생성
    *JSP 실행 환경 위해서 WAR 선택*

- 롬복 적용

- Postman 설치

## HttpServletRequest - 개요

- 서블릿은 개발자가 HTTP 요청 메시지를 편리하게 사용할 수 있도록 개발자 대신에 HTTP 요청 메시지를 파싱
    - ->  결과를 HttpServletRequest 객체에 담아서 제공

* 임시 저장소 기능
: 해당 HTTP 요청이 시작부터 끝날 때 까지 유지되는 임시 저장소 기능

    - 저장: request.setAttribute(name, value)
    - 조회: request.getAttribute(name)

* 세션 관리 기능

- request.getSession(create: true)

## HttpServletRequest - 기본 사용법

- hello.servlet.basic.request.RequestHeaderServlet

## HTTP 요청 데이터 - 개요

- HTTP 요청 메시지를 통해 클라이언트에서 서버로 데이터를 전달하는 방법

* 주로 사용하는 3가지 방법

    * GET - 쿼리 파라미터
        - /url?username=hello&age=20
        - 메시지 바디 없이, URL의 쿼리 파라미터에 데이터를 포함해서 전달 
        - 예) 검색, 필터, 페이징등에서 많이 사용하는 방식

    * POST - HTML Form
        - content-type: application/x-www-form-urlencoded
        - 메시지 바디에 쿼리 파리미터 형식으로 전달 username=hello&age=20 
        - 예) 회원 가입, 상품 주문, HTML Form 사용

    * HTTP message body에 데이터를 직접 담아서 요청 
        - HTTP API에서 주로 사용, JSON, XML, TEXT
        - 데이터 형식은 주로 JSON 사용 
            - POST, PUT, PATCH

## HTTP 요청 데이터 - GET 쿼리 파라미터

- 메시지 바디 없이, URL의 쿼리 파라미터를 사용해서 데이터를 전달하자.
    - 예) 검색, 필터, 페이징등에서 많이 사용하는 방식

- 쿼리 파라미터는 URL에 다음과 같이 ?를 시작으로 보낼 수 있다. 추가 파라미터는 &로 구분하면 된다.
    - http://localhost:8080/request-param?username=hello&age=20

- 서버에서는 HttpServletRequest 가 제공하는 다음 메서드를 통해 쿼리 파라미터를 편리하게 조회할 수 있다.
    - request.getParameter("username"); //단일 파라미터 조회
    - request.getParameterNames(); //파라미터 이름들 모두 조회
    - request.getParameterMap(); //파라미터를 Map 으로 조회
    - request.getParameterValues("username"); //복수 파라미터 조회
        - 값이 중복일 때, 첫 번째 값 반환

## HTTP 요청 데이터 - POST HTML Form

- 이번에는 HTML의 Form을 사용해서 클라이언트에서 서버로 데이터를 전송해보자.
주로 회원 가입, 상품 주문 등에서 사용하는 방식이다.
    \- content-type: application/x-www-form-urlencoded
    \- 메시지 바디에 쿼리 파리미터 형식으로 데이터를 전달한다. username=hello&age=20

- 클라이언트(웹 브라우저) 입장에서는 두 방식에 차이가 있지만, 서버 입장에서는 둘의 형식이 동일하므로, request.getParameter() 로 편리하게 구분없이 조회할 수 있다.
- => request.getParameter() 는 GET URL 쿼리 파라미터 형식도 지원하고, POST HTML Form 형식도 둘 다 지원한다.

- GET URL 쿼리 파라미터 형식은 메시지 바디를 사용하지 않기 때문에 content-type이 없다.
- POST HTML Form 형식으로 데이터를 전달하면 HTTP 메시지 바디에 해당 데이터를 포함해서 보내기 때문에 content-type을 꼭 지정해야 한다.

## HTTP 요청 데이터 - API 메시지 바디 - 단순 텍스트

-  HTTP message body에 데이터를 직접 담아서 요청
    - HTTP API에서 주로 사용, JSON, XML, TEXT 
    - 데이터 형식은 주로 JSON 사용
    - POST, PUT, PATCH
- HTTP 메시지 바디의 데이터를 InputStream을 사용해서 직접 읽을 수 있다.
- inputStream은 byte 코드를 반환한다. byte 코드를 우리가 읽을 수 있는 문자(String)로 보려면 문자표
(Charset)를 지정해주어야 한다. 여기서는 UTF_8 Charset을 지정해주었다.

## HTTP 요청 데이터 - API 메시지 바디 - JSON

- JSON 형식 파싱 추가
    - JSON 형식으로 파싱할 수 있게 객체를 하나 생성하자
    - hello.servlet.basic.HelloData

## HttpServletResponse - 기본 사용법

* 역할
- HTTP 응답 메시지 생성 
    - HTTP 응답코드 지정 
    - 헤더 생성
    - 바디 생성

    * 편의 기능 제공
    - Content-Type, 쿠키, Redirect

## HTTP 응답 데이터 - 단순 텍스트, HTML

- 단순 텍스트 응답
    - 앞에서 살펴봄 ( writer.println("ok"); )
- HTML 응답
- HTTP API - MessageBody JSON 응답

## HTTP 응답 데이터 - API JSON

- HTTP 응답으로 JSON을 반환할 때는 content-type을 application/json 로 지정해야 한다. 
- Jackson 라이브러리가 제공하는 objectMapper.writeValueAsString() 를 사용하면 객체를 JSON 문자로 변경할 수 있다.
    -  application/json 은 스펙상 utf-8 형식을 사용하도록 정의되어 있다. 그래서 스펙에서 charset=utf-8과 같은 추가 파라미터를 지원하지 않음.



# SECTION 03. 서블릿, JSP, MVC 패턴

## 회원 관리 웹 애플리케이션 요구사항

- 회원 정보: 이름, 나이
- 기능 요구사항: 회원 저장, 회원 목록 조회

- 회원 도메인 모델 생성: Member
    - id는 Member 를 회원 저장소에 저장하면 회원 저장소가 할당한다.

- 회원 저장소: MemberRepository
    - 싱글톤 패턴 적용 -> 순수 서블릿 만으로 구현할 예정이기 때문
    - 싱글톤 패턴은 객체를 단 하나만 생생해서 공유해야 하므로 생성자를 private 접근자로 막아둔다.

- 회원 저장소 테스트 코드
    - . 각 테스트가 끝날 때, 다음 테스트에 영향을 주지 않도록 각 테스트의 저장소를 clearStore() 를 호출해서 초기화

## 서블릿으로 회원 관리 웹 애플리케이션 만들기

- MemberFormServlet - 회원 등록 폼
    - HTML Form 데이터를 POST로 전송해도, 전달 받는 서블릿을 아직 만들지 않았다.    
    - -> 오류가 발생하는 것이 정상

- MemberSaveServlet - 회원 저장
    - 동작 순서
    1. 파라미터를 조회해서 Member 객체를 만든다.
    2. Member 객체를 MemberRepository를 통해서 저장한다.
    3. Member 객체를 사용해서 결과 화면용 HTML을 동적으로 만들어서 응답한다.

- MemberListServlet - 회원 목록
    - 동작 순서
    1. memberRepository.findAll() 을 통해 모든 회원을 조회한다.
    2. 회원 목록 HTML을 for 루프를 통해서 회원 수 만큼 동적으로 생성하고 응답한다.

* 템플릿 엔진으로

- 자바 코드로 HTML을 만들어 내는 것 보다 차라리 HTML 문서에 동적으로 변경해야 하는 부분만 자바 코드를 넣을 수 있다면 더 편리할 것
    - -> 템플릿 엔진이 나온 이유
    - 템플릿 엔진을 사용하면 HTML 문서에서 필요한 곳만 코드를 적용해서 동적으로 변경할 수 있다.
    - JSP, Thymeleaf, Freemarker, Velocity등

- Welcome 페이지 변경

## JSP로 회원 관리 웹 애플리케이션 만들기

- build.gradle에 JSP 라이브러리 추가

- 기존의 회원 등록 폼, 회원 저장, 회원 목록 모두 jsp로 변경
- JSP는 서버 내부에서 서블릿으로 변환되는데, 우리가 만들었던 MemberFormServlet과 거의 비슷한 모습으로 변환

- JSP는 자바 코드를 그대로 다 사용할 수 있다.
    - <% ~~ %>  -> 이 부분에는 자바 코드를 입력할 수 있다.
    - <%= ~~ %> -> 이 부분에는 자바 코드를 출력할 수 있다.

* MVC 패턴의 등장

- 서블릿의 한계: 뷰(View)화면을 위한 HTML을 만드는 작업이 자바 코드에 섞여서 지저분하고 복잡

- JSP의 한계: JAVA 코드, 데이터를 조회하는 리포지토리 등등 다양한 코드가 모두 JSP에 노출되어 있다. JSP가 너무 많은 역할을 함

- 비즈니스 로직은 서블릿 처럼 다른곳에서 처리하고, JSP는 목적에 맞게 HTML로 화면(View)을 그리는 일에 집중하도록 하자 -> MVC 패턴

## MVC 패턴 - 개요

- 너무 많은 역할, 변경의 라이프 사이클 -> 유지보수에 좋지 않음

* Model View Controller
    - MVC 패턴은 지금까지 학습한 것 처럼 하나의 서블릿이나, JSP로 처리하던 것을 컨트롤러(Controller)와 뷰(View)라는 영역으로 서로 역할을 나눈 것
    - 웹 애플리케이션은 보통 이 MVC 패턴을 사용

    * 컨트롤러
    - HTTP 요청을 받아서 파라미터를 검증하고, 비즈니스 로직을 실행한다. 그리고 뷰에 전달할 결과 데이터를 조회해서 모델에 담는다.

    * 모델
    - 뷰에 출력할 데이터를 담아둔다. 뷰가 필요한 데이터를 모두 모델에 담아서 전달해주는 덕분에 뷰는 비즈니스 로직이나 데이터 접근을 몰라도 되고, 화면을 렌더링 하는 일에 집중할 수 있다.

    * 뷰
    - 모델에 담겨있는 데이터를 사용해서 화면을 그리는 일에 집중한다. 여기서는 HTML을 생성하는 부분을 말한다.

    * 참고
    - 컨트롤러에 비즈니스 로직을 둘 수도 있지만, 이렇게 되면 컨트롤러가 너무 많은 역할을 담당한다. 
    - 그래서 일반적으로 비즈니스 로직은 서비스(Service)라는 계층을 별도로 만들어서 처리한다. 그리고 컨트롤러는 비즈니스 로직이 있는 서비스를 호출하는 담당한다. 
    - 참고로 비즈니스 로직을 변경하면 비즈니스 로직을 호출하는 컨트롤러의 코드도 변경될 수 있다

## MVC 패턴 - 적용

- 서블릿을 컨트롤러로 사용하고, JSP를 뷰로 사용해서 MVC 패턴을 적용해보자.
- Model은 HttpServletRequest 객체를 사용한다. request는 내부에 데이터 저장소를 가지고 있는데, request.setAttribute() , request.getAttribute() 를 사용하면 데이터를 보관하고, 조회할 수 있다.

* 적용

- 회원 등록 폼 - 컨트롤러
    -  dispatcher.forward() : 다른 서블릿이나 JSP로 이동할 수 있는 기능이다. 서버 내부에서 다시 호출이 발생한다.

    - /WEB-INF
        - -> 이 경로안에 JSP가 있으면 외부에서 직접 JSP를 호출할 수 없다. 우리가 기대하는 것은 항상 컨트롤러를 통해서 JSP를 호출하는 것이다.

    - redirect vs forward
        - 리다이렉트는 실제 클라이언트(웹 브라우저)에 응답이 나갔다가, 클라이언트가 redirect 경로로 다시 요청한다. 따라서 클라이언트가 인지할 수 있고, URL 경로도 실제로 변경된다. 
        - 반면에 포워드는 서버 내부에서 일어나는 호출이기 때문에 클라이언트가 전혀 인지하지 못한다.

- 회원 등록 폼 - 뷰
    -  form의 action을 보면 절대 경로(슬래시로 시작)이 아니라 상대경로(슬래시로 시작X)하는 것을 확인할 수 있다. 이렇게 상대경로를 사용하면 폼 전송시 현재 URL이 속한 계층 경로 + save가 호출된다.

- 회원 저장 - 컨트롤러
    - HttpServletRequest를 Model로 사용한다.
    - request가 제공하는 setAttribute() 를 사용하면 request 객체에 데이터를 보관해서 뷰에 전달할 수 있다.
    - 뷰는 request.getAttribute() 를 사용해서 데이터를 꺼내면 된다.

- 회원 저장 - 뷰
    -  <%= request.getAttribute("member")%> 로 모델에 저장한 member 객체를 꺼낼 수 있지만, 너무 복잡해진다.
    - JSP는 ${} 문법을 제공하는데, 이 문법을 사용하면 request의 attribute에 담긴 데이터를 편리하게 조회할 수 있다.

- 회원 목록 조회 - 컨트롤러
    - request 객체를 사용해서 List<Member> members 를 모델에 보관했다.

- 회원 목록 조회 - 뷰
    - 모델에 담아둔 members를 JSP가 제공하는 taglib기능을 사용해서 반복하면서 출력했다. members 리스트에서 member 를 순서대로 꺼내서 item 변수에 담고, 출력하는 과정을 반복한다.
    - <c:forEach> 이 기능을 사용하려면 다음과 같이 선언해야 한다.
        - <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

## MVC 패턴 - 한계

- MVC 패턴을 적용한 덕분에 컨트롤러의 역할과 뷰를 렌더링 하는 역할을 명확하게 구분할 수 있다.
- 그런데 컨트롤러는 딱 봐도 중복이 많고, 필요하지 않는 코드들도 많이 보인다.

* MVC 컨트롤러의 단점

    * 포워드 중복
        - View로 이동하는 코드가 항상 중복 호출되어야 한다. 물론 이 부분을 메서드로 공통화해도 되지만, 해당 메서드도 항상 직접 호출해야 한다.

    * ViewPath에 중복
        - prefix: /WEB-INF/views/
        - suffix: .jsp
    - 만약 jsp가 아닌 thymeleaf 같은 다른 뷰로 변경한다면 전체 코드를 다 변경해야 한다.

    * 사용하지 않는 코드
        - HttpServletRequest request, HttpServletResponse response 사용할 때도 있고, 아닐 때도 있음

    * 정리
    - 공통 처리가 어렵다는 문제가 있다.
    - -> 컨트롤러 호출 전에 먼저 공통 기능을 처리해야 한다. 소위 수문장 역할을 하는 기능이 필요하다. 프론트 컨트롤러(Front Controller) 패턴을 도입하면 이런 문제를 깔끔하게 해결할 수 있다.(입구를 하나로!)


# SECTION 04. MVC 프레임워크 만들기


## 프론트 컨트롤러 패턴 소개

* FrontController 패턴 특징
    - 프론트 컨트롤러 서블릿 하나로 클라이언트의 요청을 받음 
    - 프론트 컨트롤러가 요청에 맞는 컨트롤러를 찾아서 호출 
    - 입구를 하나로!
    - 공통 처리 가능
    - 프론트 컨트롤러를 제외한 나머지 컨트롤러는 서블릿을 사용하지 않아도 됨

* 스프링 웹 MVC와 프론트 컨트롤러
    - 스프링 웹 MVC의 핵심도 바로 FrontController
    - 스프링 웹 MVC의 DispatcherServlet이 FrontController 패턴으로 구현되어 있음


## 프론트 컨트롤러 도입 - v1

* v1 구조

1. 클라이언트 -> 프론트 컨트롤러에 http 요청
2. 프론트 컨트롤러 -> url 매핑 정보에서 컨트롤러 조회 -> 컨트롤러 호출
3. 컨트롤러가 jsp forward -> jsp가 html 응답

* ControllerV1 생성
    - 서블릿과 비슷한 모양의 컨트롤러 인터페이스를 도입한다. 각 컨트롤러들은 이 인터페이스를 구현하면 된다. 프론트 컨트롤러는 이 인터페이스를 호출해서 구현과 관계없이 로직의 일관성을 가져갈 수 있다.
    - 이제 이 인터페이스를 구현한 컨트롤러를 만들어보자. 지금 단계에서는 기존 로직을 최대한 유지하는게 핵심이다.

* MemberSaveControllerV1 - 회원 저장 컨트롤러,  MemberListControllerV1 - 회원 목록 컨트롤러 생성

* FrontControllerServletV1 - 프론트 컨트롤러 생성

* 프론트 컨트롤러 분석

    * urlPatterns
        - urlPatterns = "/front-controller/v1/*" : /front-controller/v1 를 포함한 하위 모든 요청은 이 서블릿에서 받아들인다.
        - 예) /front-controller/v1 , /front-controller/v1/a , /front-controller/v1/a/b

    * controllerMap
        - key: 매핑 URL
        - value: 호출될 컨트롤러

    * service()
        - 먼저 requestURI 를 조회해서 실제 호출할 컨트롤러를 controllerMap 에서 찾는다. 만약 없다면 404(SC_NOT_FOUND) 상태 코드를 반환한다.
        - 컨트롤러를 찾고 controller.process(request, response); 을 호출해서 해당 컨트롤러를 실행한다.

    * JSP
    - JSP는 이전 MVC에서 사용했던 것을 그대로 사용한다.

    * 실행
    - 결과: 기존 서블릿, JSP로 만든 MVC와 동일하게 실행 되는 것을 확인


## View 분리 - v2

- 모든 컨트롤러에서 뷰로 이동하는 부분에 중복이 있고, 깔끔하지 않음
    - -> 이 부분을 깔끔하게 분리하기 위해 별도로 뷰를 처리하는 객체를 만들자

* v2 구조

1. 클라이언트 -> 프론트 컨트롤러에 http 요청
2. 프론트 컨트롤러 -> url 매핑 정보에서 컨트롤러 조회 -> 컨트롤러 호출
3. 컨트롤러가 프론트 컨트롤러에 MyView 반환
4. 프론트컨트롤러가 render() 호출 -> MyView
5. MyView에서 JSP forward
6. html 응답

* MyView 생성

* ControllerV2 인터페이스
    - 컨트롤러가 뷰를 반환하는 특징이 있다.

* MemberFormControllerV2 - 회원 등록 폼, MemberSaveControllerV2 - 회원 저장, MemberListControllerV2 - 회원 목록 생성
    - 이제 각 컨트롤러는 복잡한 dispatcher.forward() 를 직접 생성해서 호출하지 않아도 된다. 단순히 MyView 객체를 생성하고 거기에 뷰 이름만 넣고 반환하면 된다.
    - return new MyView("/WEB-INF/views/new-form.jsp");

* 프론트 컨트롤러 V2
    - ControllerV2의 반환 타입이 MyView 이므로 프론트 컨트롤러는 컨트롤러의 호출 결과로 MyView 를 반환 받는다. 그리고 view.render() 를 호출하면 forward 로직을 수행해서 JSP가 실행된다.
        - dispatcher.forward(request, response);가 컨트롤러에서 마이뷰로 이동

* 프론트 컨트롤러의 도입으로 MyView 객체의 render() 를 호출하는 부분을 모두 일관되게 처리할 수 있다. 각각의 컨트롤러는 MyView 객체를 생성만 해서 반환하면 된다.


## Model 추가 - v3

* 서블릿 종속성 제거

컨트롤러 입장에서 HttpServletRequest, HttpServletResponse이 꼭 필요할까?
요청 파라미터 정보는 자바의 Map으로 대신 넘기도록 하면 지금 구조에서는 컨트롤러가 서블릿 기술을 몰라도 동작할 수 있다.
그리고 request 객체를 Model로 사용하는 대신에 별도의 Model 객체를 만들어서 반환하면 된다. 우리가 구현하는 컨트롤러가 서블릿 기술을 전혀 사용하지 않도록 변경해보자.
이렇게 하면 구현 코드도 매우 단순해지고, 테스트 코드 작성이 쉽다.

* 뷰 이름 중복 제거

컨트롤러에서 지정하는 뷰 이름에 중복이 있는 것을 확인할 수 있다.
컨트롤러는 뷰의 논리 이름을 반환하고, 실제 물리 위치의 이름은 프론트 컨트롤러에서 처리하도록 단순화 하자.
이렇게 해두면 향후 뷰의 폴더 위치가 함께 이동해도 프론트 컨트롤러만 고치면 된다.

/WEB-INF/views/new-form.jsp new-form 
/WEB-INF/views/save-result.jsp save-result 
/WEB-INF/views/members.jsp members

* V3 구조

1. 클라이언트 -> 프론트 컨트롤러에 http 요청
2. 프론트 컨트롤러 -> url 매핑 정보에서 컨트롤러 조회 -> 컨트롤러 호출
3. 컨트롤러가 프론트 컨트롤러에 ModelView 반환
4. 프론트컨트롤러가 viewResolver 호출
5. viewResolver가 MyView 반환
6. 프론트컨트롤러가 render(model) 호출 -> MyView
5. MyView에서 html 응답

* ModelView
    - 지금까지 컨트롤러에서 서블릿에 종속적인 HttpServletRequest를 사용했다. 그리고 Model도 request.setAttribute() 를 통해 데이터를 저장하고 뷰에 전달했다.
    - 서블릿의 종속성을 제거하기 위해 Model을 직접 만들고, 추가로 View 이름까지 전달하는 객체를 만들어보자.
    - (이번 버전에서는 컨트롤러에서 HttpServletRequest를 사용할 수 없다. 따라서 직접 request.setAttribute() 를 호출할 수 도 없다. 따라서 Model이 별도로 필요하다.)
    - 참고로 ModelView 객체는 다른 버전에서도 사용하므로 패키지를 frontcontroller 에 둔다.

* ModelView 생성
    - 뷰의 이름과 뷰를 렌더링할 때 필요한 model 객체를 가지고 있다. model은 단순히 map으로 되어 있으므로 컨트롤러에서 뷰에 필요한 데이터를 key, value로 넣어주면 된다.

* ControllerV3 생성
    - 이 컨트롤러는 서블릿 기술을 전혀 사용하지 않는다. 따라서 구현이 매우 단순해지고, 테스트 코드 작성시 테스트 하기 쉽다.
    - HttpServletRequest가 제공하는 파라미터는 프론트 컨트롤러가 paramMap에 담아서 호출해주면 된다. 응답 결과로 뷰 이름과 뷰에 전달할 Model 데이터를 포함하는 ModelView 객체를 반환하면 된다.

* MemberFormControllerV3 - 회원 등록 폼
    - ModelView 를 생성할 때 new-form 이라는 view의 논리적인 이름을 지정한다. 실제 물리적인 이름은 프론트 컨트롤러에서 처리한다.

* MemberSaveControllerV3 - 회원 저장
    - paramMap.get("username");
        - 파라미터 정보는 map에 담겨있다. map에서 필요한 요청 파라미터를 조회하면 된다. 
    - mv.getModel().put("member", member);
        - 모델은 단순한 map이므로 모델에 뷰에서 필요한 member 객체를 담고 반환한다.

* FrontControllerServletV3
    - createParamMap() 추가
        - HttpServletRequest에서 파라미터 정보를 꺼내서 Map으로 변환한다. 그리고 해당 Map( paramMap )을 컨트롤러에 전달하면서 호출한다.

    * 뷰 리졸버
    - MyView view = viewResolver(viewName)
    - 컨트롤러가 반환한 논리 뷰 이름을 실제 물리 뷰 경로로 변경한다. 그리고 실제 물리 경로가 있는 MyView 객체를 반환한다.
        - 논리 뷰 이름: members
        - 물리 뷰 경로: /WEB-INF/views/members.jsp
    
    - view.render(mv.getModel(), request, response)
        - 뷰 객체를 통해서 HTML 화면을 렌더링 한다.
        - 뷰 객체의 render() 는 모델 정보도 함께 받는다.
        - JSP는 request.getAttribute() 로 데이터를 조회하기 때문에, 모델의 데이터를 꺼내서 request.setAttribute() 로 담아둔다.
        - JSP로 포워드 해서 JSP를 렌더링 한다.


## 단순하고 실용적인 컨트롤러 - v4

- 앞서 만든 v3 컨트롤러는 잘 설계된 컨트롤러이다. 그런데 실제 컨트톨러 인터페이스를 구현하는 개발자 입장에서 보면, 항상 ModelView 객체를 생성하고 반환해야 하는 부분이 조금은 번거롭다.
- 이번에는 v3를 조금 변경해서 실제 구현하는 개발자들이 매우 편리하게 개발할 수 있는 v4 버전을 개발해보자.

* V4 구조

1. 클라이언트 -> 프론트 컨트롤러에 http 요청
2. 프론트 컨트롤러 -> url 매핑 정보에서 컨트롤러 조회 -> 컨트롤러 호출(paramMap,model)
3. 컨트롤러가 프론트 컨트롤러에 ViewName 반환
4. 프론트컨트롤러가 viewResolver 호출
5. viewResolver가 MyView 반환
6. 프론트컨트롤러가 render(model) 호출 -> MyView
5. MyView에서 html 응답

* ControllerV4
-  인터페이스에 ModelView가 없다. model 객체는 파라미터로 전달되기 때문에 그냥 사용하면 되고, 결과로 뷰의 이름만 반환해주면 된다.

* MemberFormControllerV4
- 정말 단순하게 new-form 이라는 뷰의 논리 이름만 반환하면 된다.

* MemberSaveControllerV4
    - model.put("member", member)
        - 모델이 파라미터로 전달되기 때문에, 모델을 직접 생성하지 않아도 된다.

* FrontControllerServletV4
    - FrontControllerServletV4 는 사실 이전 버전과 거의 동일하다.

    * 모델 객체 전달
        - Map<String, Object> model = new HashMap<>(); //추가
        - 모델 객체를 프론트 컨트롤러에서 생성해서 넘겨준다. 컨트롤러에서 모델 객체에 값을 담으면 여기에 그대로 담겨있게 된다.

    * 뷰의 논리 이름을 직접 반환
        - String viewName = controller.process(paramMap, model);
        - MyView view = viewResolver(viewName);
            - 컨트롤러가 직접 뷰의 논리 이름을 반환하므로 이 값을 사용해서 실제 물리 뷰를 찾을 수 있다.
    
## 유연한 컨트롤러1 - v5

* 어댑터 패턴
지금까지 우리가 개발한 프론트 컨트롤러는 한가지 방식의 컨트롤러 인터페이스만 사용할 수 있다. ControllerV3 , ControllerV4 는 완전히 다른 인터페이스이다. 따라서 호환이 불가능하다. 마치 v3는 110v이고, v4는 220v 전기 콘센트 같은 것이다. 이럴 때 사용하는 것이 바로 어댑터이다.
어댑터 패턴을 사용해서 프론트 컨트롤러가 다양한 방식의 컨트롤러를 처리할 수 있도록 변경해보자.

* V5 구조

1. 클라이언트 -> 프론트 컨트롤러에 http 요청
2. 프론트 컨트롤러 -> 핸들러 어댑터 목록에서 핸들러를 처리할 수 있는 어댑터 조회 -> handle(handler)로 핸들러 어댑터를 통해 핸들러 호출
3. 핸들러 어댑터가 프론트 컨트롤러에 ModelView 반환
4. 프론트컨트롤러가 viewResolver 호출
5. viewResolver가 MyView 반환
6. 프론트컨트롤러가 render(model) 호출 -> MyView
5. MyView에서 html 응답

    * 핸들러 어댑터
    : 중간에 어댑터 역할을 하는 어댑터가 추가되었는데 이름이 핸들러 어댑터이다. 여기서 어댑터 역할을 해주는 덕분에 다양한 종류의 컨트롤러를 호출할 수 있다.

    * 핸들러
    : 컨트롤러의 이름을 더 넓은 범위인 핸들러로 변경했다. 그 이유는 이제 어댑터가 있기 때문에 꼭 컨트롤러의 개념 뿐만 아니라 어떠한 것이든 해당하는 종류의 어댑터만 있으면 다 처리할 수 있기 때문이다.

* MyHandlerAdapter 인터페이스

    -  boolean supports(Object handler)
        - handler는 컨트롤러를 말한다.
        - 어댑터가 해당 컨트롤러를 처리할 수 있는지 판단하는 메서드다.

    -  ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler)
        - 어댑터는 실제 컨트롤러를 호출하고, 그 결과로 ModelView를 반환해야 한다.
        - 실제 컨트롤러가 ModelView를 반환하지 못하면, 어댑터가 ModelView를 직접 생성해서라도 반환해야 한다.
        - 이전에는 프론트 컨트롤러가 실제 컨트롤러를 호출했지만 이제는 이 어댑터를 통해서 실제 컨트롤러가 호출된다.

* ControllerV3HandlerAdapter
    - ControllerV3 을 처리할 수 있는 어댑터를 뜻한다.

* FrontControllerServletV5

    * 컨트롤러(Controller) 핸들러(Handler)
        - 이전에는 컨트롤러를 직접 매핑해서 사용했다. 그런데 이제는 어댑터를 사용하기 때문에, 컨트롤러 뿐만 아니라 어댑터가 지원하기만 하면, 어떤 것이라도 URL에 매핑해서 사용할 수 있다. 그래서 이름을 컨트롤러에서 더 넒은 범위의 핸들러로 변경했다.

    * 생성자
        - 생성자는 핸들러 매핑과 어댑터를 초기화(등록)한다.

     * 매핑 정보
        - 매핑 정보의 값이 ControllerV3 , ControllerV4 같은 인터페이스에서 아무 값이나 받을 수 있는 Object 로 변경되었다.
    
    * 핸들러 매핑
        - Object handler = getHandler(request)
            - 핸들러 매핑 정보인 handlerMappingMap 에서 URL에 매핑된 핸들러(컨트롤러) 객체를 찾아서 반환한다.

    * 핸들러를 처리할 수 있는 어댑터 조회
        - handler 를 처리할 수 있는 어댑터를 adapter.supports(handler) 를 통해서 찾는다. handler가 ControllerV3 인터페이스를 구현했다면, ControllerV3HandlerAdapter 객체가 반환된다.

    * 어댑터 호출
        - ModelView mv = adapter.handle(request, response, handler);
            - 어댑터의 handle(request, response, handler) 메서드를 통해 실제 어댑터가 호출된다. 어댑터는 handler(컨트롤러)를 호출하고 그 결과를 어댑터에 맞추어 반환한다. 
            - ControllerV3HandlerAdapter 의 경우 어댑터의 모양과 컨트롤러의 모양이 유사해서 변환 로직이 단순하다.

## 유연한 컨트롤러2 - v5

- FrontControllerServletV5 에 ControllerV4 기능도 추가해보자.
    - 핸들러 매핑( handlerMappingMap )에 ControllerV4 를 사용하는 컨트롤러를 추가하고, 해당 컨트롤러를 처리할 수 있는 어댑터인 ControllerV4HandlerAdapter 도 추가하자.

* ControllerV4HandlerAdapter
    - handler 가 ControllerV4 인 경우에만 처리하는 어댑터이다.

    * 실행 로직
    - handler를 ControllerV4로 케스팅 하고, paramMap, model을 만들어서 해당 컨트롤러를 호출한다. 그리고 viewName을 반환 받는다.

    * 어댑터 변환
    - 어댑터가 호출하는 ControllerV4 는 뷰의 이름을 반환한다. 그런데 어댑터는 뷰의 이름이 아니라 ModelView 를 만들어서 반환해야 한다. 여기서 어댑터가 꼭 필요한 이유가 나온다.
    - ControllerV4 는 뷰의 이름을 반환했지만, 어댑터는 이것을 ModelView로 만들어서 형식을 맞추어 반환한다. 마치 110v 전기 콘센트를 220v 전기 콘센트로 변경하듯이! 


## 정리

* v1: 프론트 컨트롤러를 도입
    - 기존 구조를 최대한 유지하면서 프론트 컨트롤러를 도입

* v2: View 분류
    - 단순 반복 되는 뷰 로직 분리

* v3: Model 추가 서블릿 종속성 제거
    - 뷰 이름 중복 제거

* v4: 단순하고 실용적인 컨트롤러 v3와 거의 비슷
    - 구현 입장에서 ModelView를 직접 생성해서 반환하지 않도록 편리한 인터페이스 제공

* v5: 유연한 컨트롤러 어댑터 도입
    - 어댑터를 추가해서 프레임워크를 유연하고 확장성 있게 설계

# SECTION 05. 스프링 MVC - 구조 이해


## 스프링 MVC 전체 구조

* 직접 만든 프레임워크(section04) vs 스프링 MVC 비교 
    - FrontController -> DispatcherServlet 
    - handlerMappingMap -> HandlerMapping 
    - MyHandlerAdapter -> HandlerAdapter 
    - ModelView -> ModelAndView 
    - viewResolver -> ViewResolver
    - MyView -> View

* DispatcherServlet 구조 살펴보기

    - 스프링 MVC도 프론트 컨트롤러 패턴으로 구현되어 있다.
    - 스프링 MVC의 프론트 컨트롤러가 바로 디스패처 서블릿(DispatcherServlet)이다. 
    - 그리고 이 디스패처 서블릿이 바로 스프링 MVC의 핵심이다.

    * DispacherServlet 서블릿 등록
        - DispacherServlet 도 부모 클래스에서 HttpServlet 을 상속 받아서 사용하고, 서블릿으로 동작한다.
        - 스프링 부트는 DispacherServlet 을 서블릿으로 자동으로 등록하면서 모든 경로( urlPatterns="/" )에 대해서 매핑한다.

    * 요청 흐름
        - 서블릿이 호출되면 HttpServlet 이 제공하는 serivce() 가 호출된다.
        - 스프링 MVC는 DispatcherServlet 의 부모인 FrameworkServlet 에서 service() 를 오버라이드 해두었다.
        - FrameworkServlet.service() 를 시작으로 여러 메서드가 호출되면서 DispacherServlet.doDispatch() 가 호출된다.

* DispacherServlet.doDispatch() 분석

1. 핸들러 조회
```
mappedHandler = getHandler(processedRequest); 
    if (mappedHandler == null) {
        noHandlerFound(processedRequest, response);
        return; 
    }
```

2. 핸들러 어댑터 조회 - 핸들러를 처리할 수 있는 어댑터
```
HandlerAdapter ha = getHandlerAdapter(mappedHandler.getHandler());
```

3. 핸들러 어댑터 실행 ->
4. 핸들러 어댑터를 통해 핸들러 실행
5. ModelAndView 반환
```
mv = ha.handle(processedRequest, response, mappedHandler.getHandler());
  processDispatchResult(processedRequest, response, mappedHandler, mv,
dispatchException);
}

private void processDispatchResult(HttpServletRequest request,
HttpServletResponse response, HandlerExecutionChain mappedHandler, ModelAndView
mv, Exception exception) throws Exception {

// 뷰 렌더링 호출
render(mv, request, response);
}
protected void render(ModelAndView mv, HttpServletRequest request,
HttpServletResponse response) throws Exception {
  View view;
String viewName = mv.getViewName();

```

6. 뷰 리졸버를 통해서 뷰 찾기 ->
7. View 반환
```
  view = resolveViewName(viewName, mv.getModelInternal(), locale, request);
```

8. 뷰 렌더링
```
 view.render(mv.getModelInternal(), request, response);
}
```

* SpringMVC 동작 순서

1. 핸들러 조회
    : 핸들러 매핑을 통해 요청 URL에 매핑된 핸들러(컨트롤러)를 조회한다.

2. 핸들러 어댑터 조회
    : 핸들러를 실행할 수 있는 핸들러 어댑터를 조회한다.

3. 핸들러 어댑터 실행
    : 핸들러 어댑터를 실행한다.

4. 핸들러 실행
    : 핸들러 어댑터가 실제 핸들러를 실행한다.

5. ModelAndView 반환
    : 핸들러 어댑터는 핸들러가 반환하는 정보를 ModelAndView로 변환해서 반환한다.

6. viewResolver 호출
    : 뷰 리졸버를 찾고 실행한다.
    - JSP의 경우: InternalResourceViewResolver 가 자동 등록되고, 사용된다.

7. View반환
    : 뷰리졸버는 뷰의 논리이름을 물리이름으로 바꾸고, 렌더링 역할을 담당하는 뷰 객체를 반환한다.
    - JSP의 경우 InternalResourceView(JstlView) 를 반환하는데, 내부에 forward() 로직이 있다.

8. 뷰렌더링
    :뷰를 통해서 뷰를 렌더링한다.



## 핸들러 매핑과 핸들러 어댑터

* 과거 버전 스프링 컨트롤러: 컨트롤러 인터페이스
    - OldController 생성, 구현

    * HandlerMapping(핸들러 매핑)
    - 핸들러 매핑에서 이 컨트롤러를 찾을 수 있어야 한다.
        - BeanNameUrlHandlerMapping

    * HandlerAdapter(핸들러 어댑터)
    - 핸들러 매핑을 통해서 찾은 핸들러를 실행할 수 있는 핸들러 어댑터가 필요하다.
        - SimpleControllerHandlerAdapter

* HttpRequestHandler
    - 핸들러 매핑과, 어댑터를 더 잘 이해하기 위해 Controller 인터페이스가 아닌 다른 핸들러를 알아보자. HttpRequestHandler 핸들러(컨트롤러)는 서블릿과 가장 유사한 형태의 핸들러이다.
        - MyHttpRequestHandler 생성, 구현

    * 순서

        1. 핸들러 매핑으로 핸들러 조회
            - BeanNameUrlHandlerMapping

        2. 핸들러 어댑터 조회
            - HandlerAdapter 의 supports() 를 순서대로 호출한다.
            - HttpRequestHandlerAdapter 가 HttpRequestHandler 인터페이스를 지원하므로 대상이 된다.

        3. 핸들러 어댑터 실행
            - 디스패처 서블릿이 조회한 HttpRequestHandlerAdapter 를 실행하면서 핸들러 정보도 함께 넘겨준다.
            - HttpRequestHandlerAdapter 는 핸들러인 MyHttpRequestHandler 를 내부에서 실행하고, 그 결과를 반환한다.

* @RequestMapping
    - 가장 우선순위가 높은 핸들러 매핑과 핸들러 어댑터는 RequestMappingHandlerMapping, RequestMappingHandlerAdapter이다.
    - 실무에서 99.9% 이 방식의 컨트롤러를 사용한다.



## 뷰 리졸버

- OldController - View 조회할 수 있도록 변경
- application.properties에 view prefix, suffix 코드 추가

* 뷰 리졸버 동작 방식

    * 스프링 부트가 자동으로 등록하는 뷰 리졸버
    
    - 1 = BeanNameViewResolver : 빈 이름으로 뷰를 찾아서 반환한다. 
                                    (예: 엑셀 파일 생성 기능에 사용)
    - 2 = InternalResourceViewResolver : JSP를 처리할 수 있는 뷰를 반환한다.

1. 핸들러 어댑터 호출
    : 핸들러 어댑터를 통해 new-form 이라는 논리 뷰 이름을 획득한다.

2. ViewResolver 호출
    : new-form 이라는 뷰 이름으로 viewResolver를 순서대로 호출한다.
    - BeanNameViewResolver 는 new-form 이라는 이름의 스프링 빈으로 등록된 뷰를 찾아야 하는데 없다. InternalResourceViewResolver 가 호출된다.

3. InternalResourceViewResolver
    : 이 뷰 리졸버는 InternalResourceView 를 반환한다.
    
4. 뷰 - InternalResourceView
    : InternalResourceView 는 JSP처럼 포워드 forward() 를 호출해서 처리할 수 있는 경우에 사용한다. 
    
5. view.render()
    : view.render() 가 호출되고 InternalResourceView 는 forward() 를 사용해서 JSP를 실행한다.

- 참고
    - InternalResourceViewResolver 는 만약 JSTL 라이브러리가 있으면 InternalResourceView 를 상속받은 JstlView 를 반환
    - 다른 뷰는 실제 뷰를 렌더링하지만, JSP의 경우 forward() 통해서 해당 JSP로 이동(실행)해야 렌더링이 된다. JSP를 제외한 나머지 뷰 템플릿들은 forward() 과정 없이 바로 렌더링 된다

## 스프링 MVC - 시작하기

- 지금까지 만들었던 프레임워크에서 사용했던 컨트롤러를 @RequestMapping 기반의 스프링 MVC 컨트롤러 변경해보자.

* SpringMemberFormControllerV1 - 회원 등록 폼

 - @Controller
    - 스프링이 자동으로 스프링 빈으로 등록한다. (내부에 @Component 애노테이션이 있어서 컴포넌트 스캔의 대상이 됨)
    - 스프링 MVC에서 애노테이션 기반 컨트롤러로 인식한다.

 - @RequestMapping 
    - 요청 정보를 매핑한다. 해당 URL이 호출되면 이 메서드가 호출된다. 
    - 애노테이션을 기반으로 동작하기 때문에, 메서드의 이름은 임의로 지으면 된다.

 - ModelAndView 
    - 모델과 뷰 정보를 담아서 반환하면 된다.

* SpringMemberSaveControllerV1 - 회원 저장
    
    - mv.addObject("member", member)
        - 스프링이 제공하는 ModelAndView 를 통해 Model 데이터를 추가할 때는 addObject() 를 사용하면 된다. 이 데이터는 이후 뷰를 렌더링 할 때 사용된다.

* SpringMemberListControllerV1 - 회원 목록



## 스프링 MVC - 컨트롤러 통합

- @RequestMapping 을 잘 보면 클래스 단위가 아니라 메서드 단위에 적용된 것을 확인할 수 있다. 따라서 컨트롤러 클래스를 유연하게 하나로 통합할 수 있다.
    - SpringMemberControllerV2

- 컨트롤러 클래스를 통합하는 것을 넘어서 조합도 가능하다.
    - 클래스 레벨에 다음과 같이 @RequestMapping 을 두면 메서드 레벨과 조합이 된다.
    - ex. 클래스 레벨 @RequestMapping("/springmvc/v2/members") + 메서드 레벨 @RequestMapping("/new-form") == /springmvc/v2/members/new-form

## 스프링 MVC - 실용적인 방식

- 실무에서는 주로 이 방법 사용

- SpringMemberControllerV3 생성

    * Model 파라미터
        - save() , members() 를 보면 Model을 파라미터로 받는 것을 확인할 수 있다. 스프링 MVC도 이런 편의 기능을 제공한다.
    
    * ViewName 직접 반환
        - 뷰의 논리 이름을 반환할 수 있다.

    * @RequestParam 사용
        - 스프링은 HTTP 요청 파라미터를 @RequestParam 으로 받을 수 있다. @RequestParam("username") 은 request.getParameter("username") 와 거의 같은 코드라 생각하면 된다. 물론 GET 쿼리 파라미터, POST Form 방식을 모두 지원한다.

    * @RequestMapping --> @GetMapping, @PostMapping
        - @RequestMapping 은 URL만 매칭하는 것이 아니라, HTTP Method도 함께 구분할 수 있다.
            - @RequestMapping(value = "/new-form", method = RequestMethod.GET)
                == @GetMapping("/new-form")


