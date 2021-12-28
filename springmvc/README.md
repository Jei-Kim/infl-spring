# SECTION 06. 스프링 MVC - 기본 기능



## 프로젝트 생성


- 스프링 부트 스타터 사이트로 이동해서 스프링 프로젝트 생성
    - build.gradle에 이클립스 설정 추가
    - jar 선택 -> jsp 사용 x, webapp경로 사용 x

- Welcome 페이지 만들기
    - 스프링 부트에 Jar 를 사용하면 /resources/static/index.hml 위치에 index.html 파일을 두면 Welcome 페이지로 처리해준다. (스프링 부트가 지원하는 정적 컨텐츠 위치에 /index.html 이 있으면 된다.)



## 로깅 간단히 알아보기


- 운영 시스템에서는 System.out.println() 같은 시스템 콘솔을 사용해서 필요한 정보를 출력하지 않고, 별도의 로깅 라이브러리를 사용해서 로그를 출력

- SLF4J는 인터페이스이고, 그 구현체로 Logback 같은 로그 라이브러리를 선택하면 된다.


* 로그 선언

```
private Logger log = LoggerFactory.getLogger(getClass());

private static final Logger log = LoggerFactory.getLogger(Xxx.class)

```
-  @Slf4j : 롬복 사용 가능


* 로그 호출

```
log.info("hello")
```


* 매핑 정보

    * @RestController

        - @Controller 는 반환 값이 String 이면 뷰 이름으로 인식된다. 그래서 뷰를 찾고 뷰가 랜더링 된다. 

        - @RestController 는 반환 값으로 뷰를 찾는 것이 아니라, HTTP 메시지 바디에 바로 입력한다. 따라서 실행 결과로 ok 메세지를 받을 수 있다. @ResponseBody 와 관련이 있는데, 뒤에서 더 자세히 설명한다.


* 테스트

    - 로그가 출력되는 포멧 확인
        - 시간, 로그 레벨, 프로세스 ID, 쓰레드 명, 클래스명, 로그 메시지

    - 로그 레벨 설정 변경
        - LEVEL: TRACE > DEBUG > INFO > WARN > ERROR 
        - 개발 서버는 debug 출력
        - 운영 서버는 info 출력


* 올바른 로그 사용법

    - log.debug("data={}", data)와 log.debug("data="+data)는 출력 결과 동일
        - 자바 언어 특성상, 연산이 먼저 일어나기 때문에 의미 없는 메모리 사용 -> 전자 사용할 것


* 로그 사용 시 장점

    - 쓰레드 정보, 클래스 이름 같은 부가 정보를 함께 볼 수 있고, 출력 모양을 조정할 수 있다.

    - 로그 레벨에 따라 개발 서버에서는 모든 로그를 출력하고, 운영서버에서는 출력하지 않는 등 로그를 상황에 맞게 조절할 수 있다.

    - 시스템 아웃 콘솔에만 출력하는 것이 아니라, 파일이나 네트워크 등, 로그를 별도의 위치에 남길 수 있다. 특히 파일로 남길 때는 일별, 특정 용량에 따라 로그를 분할하는 것도 가능하다.

    - 성능도 일반 System.out보다 좋다. (내부 버퍼링, 멀티 쓰레드 등등) 그래서 실무에서는 꼭 로그를 사용해야 한다.



## 요청 매핑


* MappingController 생성

    * 매핑 정보(한 번 더)

        - @RestController
            - @Controller 는 반환 값이 String 이면 뷰 이름으로 인식된다. 그래서 뷰를 찾고 뷰가 랜더링 된다.
            - @RestController 는 반환 값으로 뷰를 찾는 것이 아니라, HTTP 메시지 바디에 바로 입력한다. 따라서 실행 결과로 ok 메세지를 받을 수 있다. @ResponseBody 와 관련이 있는데, 뒤에서 더 자세히 설명한다.

        - @RequestMapping("/hello-basic")
            - /hello-basic URL 호출이 오면 이 메서드가 실행되도록 매핑한다.
            - 대부분의 속성을 배열[] 로 제공하므로 다중 설정이 가능하다. {"/hello-basic", "/hello-go"}

* postman 설정
    - POST 전송시
        - Body x-www-form-urlencoded 선택
        - Headers에서 content-type: application/x-www-form-urlencoded (폼으로 데이터를 전송하는 형식)로 지정된 부분 꼭 확인

* HTTP 메서드

    * @RequestMapping("/hello-basic")

        - @RequestMapping 에 method 속성으로 HTTP 메서드를 지정하지 않으면 HTTP 메서드와 무관하게 호출된다.
        - GET, HEAD, POST, PUT, PATCH, DELETE 모두 허용 

    * @RequestMapping(value = "/mapping-get-v1", method = RequestMethod.GET)

        - 만약 여기에 POST 요청을 하면 스프링 MVC는 HTTP 405 상태코드(Method Not Allowed)를 반환

* HTTP 메서드 매핑 축약

    * @GetMapping(value = "/mapping-get-v2")
        - HTTP 메서드를 축약한 애노테이션을 사용하는 것이 더 직관적이다. 코드를 보면 내부에서 @RequestMapping 과 method 를 지정해서 사용하는 것을 확인할 수 있다.

* PathVariable(경로 변수) 사용

    * @GetMapping("/mapping/{userId}")
        - 최근 HTTP API는 다음과 같이 리소스 경로에 식별자를 넣는 스타일을 선호한다.
            - ex. http://localhost:8080/mapping/userA

        - @RequestMapping 은 URL 경로를 템플릿화 할 수 있는데, @PathVariable 을 사용하면 매칭 되는 부분을 편리하게 조회할 수 있다.

        - @PathVariable 의 이름과 파라미터 이름이 같으면 생략할 수 있다.

* PathVariable 사용 - 다중

    * @GetMapping("/mapping/users/{userId}/orders/{orderId}")
        - ex. http://localhost:8080/mapping/users/userA/orders/100

* 특정 파라미터 조건 매핑

    * @GetMapping(value = "/mapping-param", params = "mode=debug")
        - ex. http://localhost:8080/mapping-param?mode=debug
        - 특정 파라미터가 있거나 없는 조건을 추가할 수 있다. 잘 사용하지는 않는다.

* 특정 헤더 조건 매핑

    * @GetMapping(value = "/mapping-header", headers = "mode=debug")
        - 파라미터 매핑과 비슷하지만, HTTP 헤더를 사용한다.
        - Postman으로 테스트 해야 한다.

* 미디어 타입 조건 매핑 - HTTP 요청 Content-Type, consume

    * @PostMapping(value = "/mapping-consume", consumes = "application/json")
        - Postman으로 테스트 해야 한다.
        - HTTP 요청의 Content-Type 헤더를 기반으로 미디어 타입으로 매핑한다.
            만약 맞지 않으면 HTTP 415 상태코드(Unsupported Media Type)을 반환한다.

* 미디어 타입 조건 매핑 - HTTP 요청 Accept, produce

    * @PostMapping(value = "/mapping-produce", produces = "text/html")
        - HTTP 요청의 Accept 헤더를 기반으로 미디어 타입으로 매핑한다. 
            만약 맞지 않으면 HTTP 406 상태코드(Not Acceptable)을 반환한다.



## 요청 매핑 - API 예시


- 회원 관리를 HTTP API로 만든다 생각하고 매핑을 어떻게 하는지 알아보자.
(실제 데이터가 넘어가는 부분은 생략하고 URL 매핑만)

* 회원 관리 API
    - 회원 목록 조회: GET -> /users
    - 회원 등록: POST -> /users
    - 회원 조회: GET -> /users
    - 회원수정: PATCH -> /users/{userId} 
    - 회원 삭제: DELETE -> /users/{userId}


-  MappingClassController 생성
    - 클래스 레벨에 매핑 정보를 두면 메서드 레벨에서 해당 정보를 조합해서 사용한다.



## HTTP 요청 - 기본, 헤더 조회


- RequestHeaderController 생성

    * MultiValueMap
        - MAP과 유사한데, 하나의 키에 여러 값을 받을 수 있다.
        - HTTP header, HTTP 쿼리 파라미터와 같이 하나의 키에 여러 값을 받을 때 사용한다.
            - keyA=value1&keyA=value2

    * @Slf4j
        - 다음 코드를 자동으로 생성해서 로그를 선언해준다. 개발자는 편리하게 log 라고 사용하면 된다.



## HTTP 요청 - 쿼리, 파라미터, HTML Form


- 클라이언트에서 서버로 요청 데이터를 전달할 때는 주로 다음 3가지 방법을 사용한다.
    
    1. GET - 쿼리 파라미터
        - /url?username=hello&age=20
        - 메시지 바디 없이, URL의 쿼리 파라미터에 데이터를 포함해서 전달 
            - 예) 검색, 필터, 페이징등에서 많이 사용하는 방식

    2. POST - HTML Form
        - content-type: application/x-www-form-urlencoded
        - 메시지 바디에 쿼리 파리미터 형식으로 전달 username=hello&age=20 
            - 예) 회원 가입, 상품 주문, HTML Form 사용

    3. HTTP message body에 데이터를 직접 담아서 요청 
        - HTTP API에서 주로 사용, JSON, XML, TEXT 
        - 데이터 형식은 주로 JSON 사용
        - POST, PUT, PATCH



## HTTP 요청 파라미터 - @RequestParam

- HttpServletRequest 의 request.getParameter() 를 사용하면 다음 두가지 요청 파라미터를 조회할 수 있다. -> 

    1. GET, 쿼리 파라미터 전송
        -  http://localhost:8080/request-param?username=hello&age=20
   
    2. POST, HTML Form 전송

        ```
        POST /request-param ...
        content-type: application/x-www-form-urlencoded
        username=hello&age=20
        ```

- => GET 쿼리 파리미터 전송 방식이든, POST HTML Form 전송 방식이든 둘다 형식이 같으므로 구분없이 조회할 수 있다. 이것을 간단히 요청 파라미터(request parameter) 조회라 한다.

- RequestParamController 생성
    - request.getParameter()
        - 여기서는 단순히 HttpServletRequest가 제공하는 방식으로 요청 파라미터를 조회했다.

- Post Form 페이지 생성
    - main/resources/static/basic/hello-form.html

- c.f. Jar 를 사용하면 webapp 경로를 사용할 수 없다. 이제부터 정적 리소스도 클래스 경로에 함께 포함해야 한다.

<br>

* HTTP 요청 파라미터 - @RequestParam

    - 스프링이 제공하는 @RequestParam 을 사용하면 요청 파라미터를 매우 편리하게 사용할 수 있다.

* requestParamV2

    - @RequestParam : 파라미터 이름으로 바인딩
    - @ResponseBody : View 조회를 무시하고, HTTP message body에 직접 해당 내용 입력

    - @RequestParam의 name(value) 속성이 파라미터 이름으로 사용
        - @RequestParam("username") String memberName ---> request.getParameter("username")

* requestParamV3

    - HTTP 파라미터 이름이 변수 이름과 같으면 @RequestParam(name="xx") 생략 가능

* requestParamV4

    - String , int , Integer 등의 단순 타입이면 @RequestParam 도 생략 가능
        - -> 추천x,  @RequestParam 이 있으면 명확하게 요청 파리미터에서 데이터를 읽는 다는 것을 알 수 있기 때문

* 파라미터 필수 여부 - requestParamRequired

    - @RequestParam.required
        - 파라미터 필수 여부
        - 기본값이 파라미터 필수( true )이다.

    - /request-param 요청
        - username 이 없으므로 400 예외가 발생한다.

    - 주의! - 파라미터 이름만 사용
        - /request-param?username=
        - 파라미터 이름만 있고 값이 없는 경우 빈문자로 통과(공백도 문자로 인식)

    - 주의! - 기본형(primitive)에 null 입력 
        - /request-param 요청   
        - @RequestParam(required = false) int age

    - null 을 int 에 입력하는 것은 불가능(500 예외 발생)
    - 따라서 null 을 받을 수 있는 Integer 로 변경하거나, 또는 다음에 나오는 defaultValue 사용


* 기본 값 적용 - requestParamDefault

    - 파라미터에 값이 없는 경우 defaultValue 를 사용하면 기본 값을 적용할 수 있다. 이미 기본 값이 있기 때문에 required 는 의미가 없다.
    - defaultValue 는 빈 문자의 경우에도 설정한 기본 값이 적용된다. 
    - /request-param?username=


* 파라미터를 Map으로 조회하기 - requestParamMap

    - @RequestParamMap ,
        - Map(key=value)
    - @RequestParam MultiValueMap
        - MultiValueMap(key=[value1, value2, ...] ex) (key=userIds, value=[id1, id2])

    - 파라미터의 값이 1개가 확실하다면 Map 을 사용해도 되지만, 그렇지 않다면 MultiValueMap 을 사용하자. -> 사용할 일 거의 없음
        


## HTTP 요청 파라미터 - @ModelAttribute

- 실제 개발을 하면 요청 파라미터를 받아서 필요한 객체를 만들고 그 객체에 값을 넣어주어야 한다. 보통 다음과 같이 코드를 작성할 것이다.

```
@RequestParam String username;
@RequestParam int age;

HelloData data = new HelloData();
data.setUsername(username);
data.setAge(age);

```
- 스프링은 이 과정을 완전히 자동화해주는 @ModelAttribute 기능을 제공한다.

- 먼저 요청 파라미터를 바인딩 받을 객체를 만들자.
    - HelloData 생성
        - 롬복 @Data
            - @Getter , @Setter , @ToString , @EqualsAndHashCode , @RequiredArgsConstructor 를
                자동으로 적용해준다.

* @ModelAttribute 적용 - modelAttributeV1

    - 스프링MVC는 @ModelAttribute 가 있으면 다음을 실행한다. 
        - HelloData 객체를 생성한다.
        - 요청 파라미터의 이름으로 HelloData 객체의 프로퍼티를 찾는다. 그리고 해당 프로퍼티의 setter를 호출해서 파라미터의 값을 입력(바인딩) 한다.
        - 예) 파라미터 이름이 username 이면 setUsername() 메서드를 찾아서 호출하면서 값을 입력한다.

* 바인딩 오류

    - age=abc 처럼 숫자가 들어가야 할 곳에 문자를 넣으면 BindException 이 발생한다. 이런 바인딩 오류를 처리하는 방법은 검증 부분에서 다룬다.

* @ModelAttribute 생략 - modelAttributeV2

    - @ModelAttribute 는 생략할 수 있다. 그런데 @RequestParam 도 생략할 수 있으니 혼란이 발생할 수 있다.
    - 스프링은 해당 생략시 다음과 같은 규칙을 적용한다.
        - String , int , Integer 같은 단순 타입 = @RequestParam
        - 나머지 = @ModelAttribute (argument resolver 로 지정해둔 타입 외)



## HTTP 요청 메시지 - 단순 텍스트


* HTTP message body에 데이터를 직접 담아서 요청 
    - HTTP API에서 주로 사용, JSON, XML, TEXT 
    - 데이터 형식은 주로 JSON 사용
    - POST, PUT, PATCH

- 요청 파라미터와 다르게, HTTP 메시지 바디를 통해 데이터가 직접 데이터가 넘어오는 경우는 @RequestParam , @ModelAttribute 를 사용할 수 없다. 

- RequestBodyStringController
    - HTTP 메시지 바디의 데이터를 InputStream 을 사용해서 직접 읽을 수 있다.

* Input, Output 스트림, Reader - requestBodyStringV2

    - 스프링 MVC는 다음 파라미터를 지원한다.

        - InputStream(Reader): HTTP 요청 메시지 바디의 내용을 직접 조회 
        - OutputStream(Writer): HTTP 응답 메시지의 바디에 직접 결과 출력

* HttpEntity - requestBodyStringV3

    - 스프링 MVC는 다음 파라미터를 지원한다.

        - HttpEntity: HTTP header, body 정보를 편리하게 조회
            - 메시지 바디 정보를 직접 조회
            - 요청 파라미터를 조회하는 기능과 관계 없음 @RequestParam X, @ModelAttribute X  

        - HttpEntity는 응답에도 사용 가능
            - 메시지 바디 정보 직접 반환
            - 헤더 정보 포함 가능, view 조회X

    - HttpEntity 를 상속받은 다음 객체들도 같은 기능을 제공한다.
        - RequestEntity: HttpMethod, url 정보가 추가, 요청에서 사용
        - ResponseEntity: HTTP 상태 코드 설정 가능, 응답에서 사용


- ****스프링MVC 내부에서 HTTP 메시지 바디를 읽어서 문자나 객체로 변환해서 전달해주는데, 이때 HTTP 메시지 컨버터( HttpMessageConverter )라는 기능을 사용한다. 이것은 조금 뒤에 HTTP 메시지 컨버터에서 자세히 설명한다.*        

* @RequestBody - requestBodyStringV4

    - @RequestBody
        - @RequestBody 를 사용하면 HTTP 메시지 바디 정보를 편리하게 조회할 수 있다. 참고로 헤더 정보가 필요하다면 HttpEntity 를 사용하거나 @RequestHeader 를 사용하면 된다.
        - 이렇게 메시지 바디를 직접 조회하는 기능은 요청 파라미터를 조회하는 @RequestParam , @ModelAttribute 와는 전혀 관계가 없다.

    * 요청 파라미터 vs HTTP 메시지 바디

        - 요청 파라미터를 조회하는 기능: @RequestParam , @ModelAttribute
        - HTTP 메시지 바디를 직접 조회하는 기능: @RequestBody

    - @ResponseBody
        - @ResponseBody 를 사용하면 응답 결과를 HTTP 메시지 바디에 직접 담아서 전달할 수 있다. 물론 이 경우에도 view를 사용하지 않는다.



## HTTP 요청 메시지 - JSON

- 이번에는 HTTP API에서 주로 사용하는 JSON 데이터 형식을 조회해보자.

    * RequestBodyJsonController 생성
        - HttpServletRequest를 사용해서 직접 HTTP 메시지 바디에서 데이터를 읽어와서, 문자로 변환한다.
        - 문자로 된 JSON 데이터를 Jackson 라이브러리인 objectMapper 를 사용해서 자바 객체로 변환한다.

    * requestBodyJsonV2 - @RequestBody 문자 변환
        - 이전에 학습했던 @RequestBody 를 사용해서 HTTP 메시지에서 데이터를 꺼내고 messageBody에 저장한다.
        - 문자로 된 JSON 데이터인 messageBody 를 objectMapper 를 통해서 자바 객체로 변환한다.

    - 문자로 변환하고 다시 json으로 변환하는 과정이 불편하다. @ModelAttribute처럼 한번에 객체로 변환할 수는 없을까?

    * requestBodyJsonV3 - @RequestBody 객체 변환
        - @RequestBody 객체 파라미터
            - @RequestBody HelloData data
            - @RequestBody 에 직접 만든 객체를 지정할 수 있다.

        - HttpEntity , @RequestBody 를 사용하면 HTTP 메시지 컨버터가 HTTP 메시지 바디의 내용을 우리가 원하는 문자나 객체 등으로 변환해준다.

        - HTTP 메시지 컨버터는 문자 뿐만 아니라 JSON도 객체로 변환해주는데, 우리가 방금 V2에서 했던 작업을 대신 처리해준다.

        - @RequestBody는 생략 불가능

       - ****HTTP 요청시에 content-type이 application/json인지 꼭! 확인해야 한다. 그래야 JSON을 처리할 수 있는 HTTP 메시지 컨버터가 실행된다*

       
    *  requestBodyJsonV4 - HttpEntity
        - HttpEntity를 사용

    * requestBodyJsonV5
        - @ResponseBody
            - 응답의 경우에도 @ResponseBody 를 사용하면 해당 객체를 HTTP 메시지 바디에 직접 넣어줄 수 있다.
            - 물론 이 경우에도 HttpEntity 를 사용해도 된다.
            

    - @RequestBody 요청
        - JSON 요청 HTTP 메시지 컨버터 객체

    - @ResponseBody 응답
        - 객체 HTTP 메시지 컨버터 JSON 응답  



## 응답 - 정적 리소스, 뷰 템플릿


- 스프링(서버)에서 응답 데이터를 만드는 방법은 크게 3가지이다.

    * 정적 리소스
        - 예) 웹 브라우저에 정적인 HTML, css, js을 제공할 때는, 정적 리소스를 사용한다.
    * 뷰 템플릿 사용
        - 예) 웹 브라우저에 동적인 HTML을 제공할 때는 뷰 템플릿을 사용한다.
    * HTTP 메시지 사용
        - HTTP API를 제공하는 경우에는 HTML이 아니라 데이터를 전달해야 하므로, HTTP 메시지 바디에 JSON 같은 형식으로 데이터를 실어 보낸다.

* 정적 리소스
    - 스프링 부트는 클래스패스의 다음 디렉토리에 있는 정적 리소스를 제공한다.
        - /static , /public , /resources , /META-INF/resources
    - src/main/resources 는 리소스를 보관하는 곳이고, 또 클래스패스의 시작 경로이다. 따라서 다음 디렉토리에 리소스를 넣어두면 스프링 부트가 정적 리소스로 서비스를 제공한다.

    * 정적 리소스 경로
        - src/main/resources/static

        - 다음 경로에 파일이 들어있으면
            - src/main/resources/static/basic/hello-form.html
        - 웹 브라우저에서 다음과 같이 실행하면 된다. 
            - http://localhost:8080/basic/hello-form.html
    
    - 정적 리소스는 해당 파일을 변경 없이 그대로 서비스하는 것이다.

* 뷰 템플릿

    - 뷰 템플릿을 거쳐서 HTML이 생성되고, 뷰가 응답을 만들어서 전달한다.
    - 일반적으로 HTML을 동적으로 생성하는 용도로 사용하지만, 다른 것들도 가능하다. 뷰 템플릿이 만들 수 있는 것이라면 뭐든지 가능하다.

    * 뷰 템플릿 경로
        - src/main/resources/templates

    * 뷰 템플릿 생성
        - src/main/resources/templates/response/hello.html

    * ResponseViewController - 뷰 템플릿을 호출하는 컨트롤러 생성   

        * String을 반환하는 경우 - View or HTTP 메시지

            - @ResponseBody 가 없으면 response/hello 로 뷰 리졸버가 실행되어서 뷰를 찾고, 렌더링 한다. 
            - @ResponseBody 가 있으면 뷰 리졸버를 실행하지 않고, HTTP 메시지 바디에 직접 response/hello 라는 문자가 입력된다.

        * Void를 반환하는 경우

            - @Controller 를 사용하고, HttpServletResponse , OutputStream(Writer) 같은 HTTP 메시지 바디를 처리하는 파라미터가 없으면 요청 URL을 참고해서 논리 뷰 이름으로 사용
                - 요청 URL: /response/hello
                - 실행: templates/response/hello.html
            - -> 참고로 이 방식은 명시성이 너무 떨어지고 이렇게 딱 맞는 경우도 많이 없어서, 권장하지 않는다.

        * HTTP 메시지

            - @ResponseBody , HttpEntity 를 사용하면, 뷰 템플릿을 사용하는 것이 아니라, HTTP 메시지 바디에 직접 응답 데이터를 출력할 수 있다.

* Thymeleaf 스프링 부트 설정
    - 라이브러리 추가,   application.properties에 코드 추가



## HTTP 응답 - HTTP API, 메시지 바디에 직접 입력

- HTTP API를 제공하는 경우에는 HTML이 아니라 데이터를 전달해야 하므로, HTTP 메시지 바디에 JSON 같은 형식으로 데이터를 실어 보낸다.


> * 참고
> HTML이나 뷰 템플릿을 사용해도 HTTP 응답 메시지 바디에 HTML 데이터가 담겨서 전달된다. 여기서 설명하는 내용은 정적 리소스나 뷰 템플릿을 거치지 않고, 직접 HTTP 응답 메시지를 전달하는 경우를 말한다.

* ResponseBodyController 생성

    - responseBodyV1
        - 서블릿을 직접 다룰 때 처럼 HttpServletResponse 객체를 통해서 HTTP 메시지 바디에 직접 ok 응답 메시지를 전달한다.
        - response.getWriter().write("ok")

    - responseBodyV2
        - ResponseEntity 엔티티는 HttpEntity 를 상속 받았는데, HttpEntity는 HTTP 메시지의 헤더, 바디 정보를 가지고 있다. ResponseEntity 는 여기에 더해서 HTTP 응답 코드를 설정할 수 있다.
        - HttpStatus.CREATED 로 변경하면 201 응답이 나가는 것을 확인할 수 있다.

    - responseBodyV3
        - @ResponseBody 를 사용하면 view를 사용하지 않고, HTTP 메시지 컨버터를 통해서 HTTP 메시지를 직접 입력할 수 있다. ResponseEntity 도 동일한 방식으로 동작한다.

    - responseBodyJsonV1
        - ResponseEntity 를 반환한다. HTTP 메시지 컨버터를 통해서 JSON 형식으로 변환되어서 반환된다.

    - responseBodyJsonV2
        - ResponseEntity 는 HTTP 응답 코드를 설정할 수 있는데, @ResponseBody 를 사용하면 이런 것을 설정하기 까다롭다.
        - @ResponseStatus(HttpStatus.OK) 애노테이션을 사용하면 응답 코드도 설정할 수 있다.
        - 물론 애노테이션이기 때문에 응답 코드를 동적으로 변경할 수는 없다. 프로그램 조건에 따라서 동적으로 변경하려면 ResponseEntity 를 사용하면 된다.

    * @RestController
        - @Controller 대신에 @RestController 애노테이션을 사용하면, 해당 컨트롤러에 모두 @ResponseBody 가 적용되는 효과가 있다. 따라서 뷰 템플릿을 사용하는 것이 아니라, HTTP 메시지 바디에 직접 데이터를 입력한다. 이름 그대로 Rest API(HTTP API)를 만들 때 사용하는 컨트롤러이다.

        - 참고로 @ResponseBody 는 클래스 레벨에 두면 전체에 메서드에 적용되는데, @RestController 에노테이션 안에 @ResponseBody 가 적용되어 있다.



## HTTP 메시지 컨버터

- 뷰 템플릿으로 HTML을 생성해서 응답하는 것이 아니라, HTTP API처럼 JSON 데이터를 HTTP 메시지
바디에서 직접 읽거나 쓰는 경우 HTTP 메시지 컨버터를 사용하면 편리하다.

* @ResponseBody 사용 원리
    - HTTP의 BODY에 문자 내용을 직접 반환
    - viewResolver 대신에 HttpMessageConverter 가 동작
    - 기본 문자처리: StringHttpMessageConverter
    - 기본 객체처리: MappingJackson2HttpMessageConverter
    - byte 처리 등등 기타 여러 HttpMessageConverter가 기본으로 등록되어 있음

- 응답의 경우 클라이언트의 HTTP Accept 해더와 서버의 컨트롤러 반환 타입 정보 둘을 조합해서 HttpMessageConverter 가 선택된다.

- 스프링 MVC는 다음의 경우에 HTTP 메시지 컨버터를 적용한다. 
    - HTTP 요청: @RequestBody , HttpEntity(RequestEntity)
    - HTTP 응답: @ResponseBody , HttpEntity(ResponseEntity)

- HTTP 메시지 컨버터(인터페이스)는 HTTP 요청, HTTP 응답 둘 다 사용된다.
    - canRead() , canWrite() : 메시지 컨버터가 해당 클래스, 미디어타입을 지원하는지 체크 
    - read() , write() : 메시지 컨버터를 통해서 메시지를 읽고 쓰는 기능

- 스프링 부트는 다양한 메시지 컨버터를 제공하는데, 대상 클래스 타입과 미디어 타입 둘을 체크해서 사용여부를 결정한다. 만약 만족하지 않으면 다음 메시지 컨버터로 우선순위가 넘어간다.



## 요청 매핑 핸들러 어댑터 구조

- HTTP 메시지 컨버터는 애노테이션 기반의 컨트롤러, 즉 @RequestMapping 을 처리하는 핸들러 어댑터인 RequestMappingHandlerAdapter (요청 매핑 헨들러 어뎁터)를 보면서 구조 이해해야 함

* RequestMappingHandlerAdapter 동작 방식
    
    1. 컨트롤러의 파라미터, 애노테이션 정보를 기반으로 전달 데이터 생성
        - HttpServletRequest, Model, @RequestParam, @ModelAttribute, @RequestBody, HttpEntity
    
    2. 핸들러(컨트롤러) 호출
    
    3. 컨트롤러의 반환 값을 변환
        - ModelAndView, @ResponseBody, HttpEntity

* ArgumentResolver(HandlerMethodArgumentResolver)

    - 애노테이션 기반의 컨트롤러는 매우 다양한 파라미터를 사용할 수 있었다. HttpServletRequest , Model 은 물론이고, @RequestParam , @ModelAttribute 같은 애노테이션 그리고 @RequestBody , HttpEntity 같은 HTTP 메시지를 처리하는 부분까지 매우 큰 유연함을 보여주었다.이렇게 파라미터를 유연하게 처리할 수 있는 이유가 바로 ArgumentResolver 덕분이다.

    - 애노테이션 기반 컨트롤러를 처리하는 RequestMappingHandlerAdaptor 는 바로 이 ArgumentResolver 를 호출해서 컨트롤러(핸들러)가 필요로 하는 다양한 파라미터의 값(객체)을 생성한다. 그리고 이렇게 파리미터의 값이 모두 준비되면 컨트롤러를 호출하면서 값을 넘겨준다.

    - 스프링은 30개가 넘는 ArgumentResolver 를 기본으로 제공한다.

    * 동작 방식

       - ArgumentResolver 의 supportsParameter() 를 호출해서 해당 파라미터를 지원하는지 체크하고, 지원하면 resolveArgument() 를 호출해서 실제 객체를 생성한다. 그리고 이렇게 생성된 객체가 컨트롤러 호출시 넘어가는 것이다.

* ReturnValueHandler

    - HandlerMethodReturnValueHandler 를 줄여서 ReturnValueHandle 라 부른다. ArgumentResolver 와 비슷한데, 이것은 응답 값을 변환하고 처리한다.

    - 컨트롤러에서 String으로 뷰 이름을 반환해도, 동작하는 이유가 바로 ReturnValueHandler 덕분이다.

* HTTP 메시지 컨버터 위치
1. ArgumentResolver에서 HTTP 메시지를 처리해서 요청 데이터 생성(@RequestBody, HttpEntity)
2. 핸들러 호출
3. ReturnValueHandler에서 응답데이터를 HTTP 메시지에 입력(@ResponseBody, HttpEntity)

- 요청의 경우 @RequestBody 를 처리하는 ArgumentResolver 가 있고, HttpEntity 를 처리하는 ArgumentResolver 가 있다. 이 ArgumentResolver 들이 HTTP 메시지 컨버터를 사용해서 필요한 객체를 생성하는 것이다. 

- 응답의 경우 @ResponseBody 와 HttpEntity 를 처리하는 ReturnValueHandler 가 있다. 그리고 여기에서 HTTP 메시지 컨버터를 호출해서 응답 결과를 만든다.

* WebMvcConfigurer 확장
    - 스프링이 필요한 대부분의 기능을 제공하기 때문에 실제 기능을 확장할 일이 많지는 않다. 기능 확장은 WebMvcConfigurer 를 상속 받아서 스프링 빈으로 등록하면 된다. 
    -> 거의 사용 x


