# SECTION 07. 스프링 MVC - 웹 페이지 만들기


## 프로젝트 생성
- pdf 참고, hello.itemservice


## 요구사항 분석


- 상품을 관리할 수 있는 서비스를 만들어보자.

    * 상품 도메인 모델
        - 상품 ID 
        - 상품명 
        - 가격 
        - 수량

    * 상품 관리 기능
        - 상품 목록 
        - 상품 상세 
        - 상품 등록 
        - 상품 수정


* 서비스 제공 흐름

    - 요구사항이 정리되고 디자이너, 웹 퍼블리셔, 백엔드 개발자가 업무를 나누어 진행한다.

        - 디자이너: 요구사항에 맞도록 디자인하고, 디자인 결과물을 웹 퍼블리셔에게 넘겨준다.
        - 웹 퍼블리셔: 다자이너에서 받은 디자인을 기반으로 HTML, CSS를 만들어 개발자에게 제공한다. 
        - 백엔드 개발자: 디자이너, 웹 퍼블리셔를 통해서 HTML 화면이 나오기 전까지 시스템을 설계하고, 핵심 비즈니스 모델을 개발한다. 이후 HTML이 나오면 이 HTML을 뷰 템플릿으로 변환해서 동적으로 화면을 그리고, 또 웹 화면의 흐름을 제어한다.

    - 웹 클라이언트 기술을 사용하면, 웹 프론트엔드 개발자가 HTML을 동적으로 만드는 역할과 웹 화면의 흐름을 담당한다. -> 이 경우 백엔드 개발자는 HTML 뷰 템플릿을 직접 만지는 대신에, HTTP API를 통해 웹 클라이언트가 필요로 하는 데이터와 기능을 제공하면 된다.


## 상품 도메인 개발

* Item - 상품 객체 클래스 생성
* ItemRepository - 상품 저장소 클래스 생성
* ItemRepositoryTest - 상품 저장소 테스트 클래스 생성



## 상품 서비스 HTML


- 웹 퍼블리셔가 HTML 마크업 완료했다고 가정, 부트스트랩 다운로드 후 
    resources/static/css 경로에 bootstrap.min.css 복붙


* HTML, css 파일
    - /resources/static 에 넣어두었기 때문에 스프링 부트가 정적 리소스를 제공한다.
    - 그런데 정적 리소스여서 해당 파일을 탐색기를 통해 직접 열어도 동작하는 것을 확인할 수 있다.

    > 이렇게 정적 리소스가 공개되는 /resources/static 폴더에 HTML을 넣어두면, 실제 서비스에서도 공개된다. 서비스를 운영한다면 지금처럼 공개할 필요없는 HTML을 두는 것은 주의하자.

- 상품 목록 HTML, 상품 상세 HTML, 상품 등록 폼 HTML, 상품 수정 폼 HTML 추가


## 상품 목록 - 타임리프

- 본격적으로 컨트롤러와 뷰 템플릿 개발 

    - BasicItemController 생성

    * @RequiredArgsConstructor
        - final 이 붙은 멤버변수만 사용해서 생성자를 자동으로 만들어준다.
        - 생성자가 딱 1개만 있으면 스프링이 해당 생성자에 @Autowired 로 의존관계를 주입해준다. -> 따라서 final 키워드를 빼면 안된다!, 그러면 ItemRepository 의존관계 주입이 안된다.


    - 테스트용 데이터 추가
        * @PostConstruct : 해당 빈의 의존관계가 모두 주입되고 나면 초기화 용도로 호출된다.

- items.html 정적 HTML을 뷰 템플릿(templates) 영역으로 복사하고 다음과 같이 수정하자

    - /resources/static/items.html 복사 -> /resources/templates/basic/items.html


* 타임리프 간단히 알아보기

    * 타임리프 사용 선언
        - <html xmlns:th="http://www.thymeleaf.org">

    * 속성 변경 - th:href
        - th:href="@{/css/bootstrap.min.css}"
            - href="value1" 을 th:href="value2" 의 값으로 변경한다.
            - 타임리프 뷰 템플릿을 거치게 되면 원래 값을 th:xxx 값으로 변경한다. 만약 값이 없다면 새로 생성한다. 
            - HTML을 그대로 볼 때는 href 속성이 사용되고, 뷰 템플릿을 거치면 th:href 의 값이 href 로 대체되면서 동적으로 변경할 수 있다.
            - 대부분의 HTML 속성을 th:xxx 로 변경할 수 있다.

    * 타임리프 핵심
        - 핵심은 th:xxx 가 붙은 부분은 서버사이드에서 렌더링 되고, 기존 것을 대체한다. th:xxx 이 없으면 기존 html의 xxx 속성이 그대로 사용된다.
        - HTML을 파일로 직접 열었을 때, th:xxx 가 있어도 웹 브라우저는 th: 속성을 알지 못하므로 무시한다. 따라서 HTML을 파일 보기를 유지하면서 템플릿 기능도 할 수 있다.


    * URL 링크 표현식 - @{...}
        - th:href="@{/css/bootstrap.min.css}"
        - @{...} : 타임리프는 URL 링크를 사용하는 경우 @{...} 를 사용한다. 이것을 URL 링크 표현식이라 한다. URL 링크 표현식을 사용하면 서블릿 컨텍스트를 자동으로 포함한다.

    
    * 속성 변경 - th:onclick
        - onclick="location.href='addForm.html'" 
            ---> th:onclick="|location.href='@{/basic/items/add}'|"

        * 리터럴 대체 - |...|
            - 타임리프에서 문자와 표현식 등은 분리되어 있기 때문에 더해서 사용해야 한다.
                - <span th:text="'Welcome to our application, ' + ${user.name} + '!'">

            - 다음과 같이 리터럴 대체 문법을 사용하면, 더하기 없이 편리하게 사용할 수 있다. 
                - <span th:text="|Welcome to our application, ${user.name}!|">

    * 반복 출력 - th:each
        - <tr th:each="item : ${items}">
        - 반복은 th:each 를 사용한다. 이렇게 하면 모델에 포함된 items 컬렉션 데이터가 item 변수에 하나씩 포함되고, 반복문 안에서 item 변수를 사용할 수 있다.
        - 컬렉션의 수 만큼 <tr>..</tr> 이 하위 테그를 포함해서 생성된다.


    * 변수 표현식 - ${...}
        - <td th:text="${item.price}">10000</td>
            - 모델에 포함된 값이나, 타임리프 변수로 선언한 값을 조회할 수 있다. 프로퍼티 접근법을 사용한다. ( item.getPrice() )

    
    * 내용 변경 - th:text
        - <td th:text="${item.price}">10000</td>
            - 내용의 값을 th:text 의 값으로 변경한다. 여기서는 10000을 ${item.price} 의 값으로 변경한다.


    * URL 링크 표현식2 - @{...}
        - th:href="@{/basic/items/{itemId}(itemId=${item.id})}"
        - 상품 ID를 선택하는 링크를 확인해보자.
        - URL 링크 표현식을 사용하면 경로를 템플릿처럼 편리하게 사용할 수 있다.
        - 경로 변수( {itemId} ) 뿐만 아니라 쿼리 파라미터도 생성한다.
            - 예) th:href="@{/basic/items/{itemId}(itemId=${item.id}, query='test')}"
        - 생성 링크: http://localhost:8080/basic/items/1?query=test

    * URL 링크 간단히
        - th:href="@{|/basic/items/${item.id}|}"
            - 상품 이름을 선택하는 링크를 확인해보자.
            - 리터럴 대체 문법을 활용해서 간단히 사용할 수도 있다.

>  타임리프는 순수 HTML을 파일을 웹 브라우저에서 열어도 내용을 확인할 수 있고, 서버를 통해 뷰 템플릿을 거치면 동적으로 변경된 결과를 확인할 수 있다. 

> JSP를 생각해보면, JSP 파일은 웹 브라우저에서 그냥 열면 JSP 소스코드와 HTML이 뒤죽박죽 되어서 정상적인 확인이 불가능하다. 오직 서버를 통해서 JSP를 열어야 한다.

> 이렇게 순수 HTML을 그대로 유지하면서 뷰 템플릿도 사용할 수 있는 타임리프의 특징을 네츄럴 템플릿 (natural templates)이라 한다.



## 상품 상세


- BasicItemController에 메서드 추가
    - PathVariable 로 넘어온 상품ID로 상품을 조회하고, 모델에 담아둔다. 그리고 뷰 템플릿을 호출한다.

- 상품 상세 뷰
    - 정적 HTML을 뷰 템플릿(templates) 영역으로 복사하고 수정

    * 속성 변경
        - th:value="${item.id}"
            - 모델에 있는 item 정보를 획득하고 프로퍼티 접근법으로 출력한다. ( item.getId() )
            - value 속성을 th:value 속성으로 변경한다.

    * 상품수정 링크
        - th:onclick="|location.href='@{/basic/items/{itemId}/edit(itemId=${item.id})}'|"

    * 목록으로 링크
        - th:onclick="|location.href='@{/basic/items}'|"



## 상품 등록 폼

- BasicItemController에 메서드 추가
    - 상품 등록 폼은 단순히 뷰 템플릿만 호출한다.

- 상품 등록 폼 뷰
    - 정적 HTML을 뷰 템플릿(templates) 영역으로 복사하고 수정

    * 속성 변경 - th:action th:action
        - HTML form에서 action 에 값이 없으면 현재 URL에 데이터를 전송한다.
        - 상품 등록 폼의 URL과 실제 상품 등록을 처리하는 URL을 똑같이 맞추고 HTTP 메서드로 두 기능을 구분한다.
            - 상품 등록 폼: GET /basic/items/add
            - 상품 등록 처리: POST /basic/items/add
        - > 이렇게 하면 하나의 URL로 등록 폼과, 등록 처리를 깔끔하게 처리할 수 있다.

    * 취소
        - 취소시 상품 목록으로 이동한다. 
            - th:onclick="|location.href='@{/basic/items}'|"



## 상품 등록 처리 - @ModelAttribute


* POST - HTML Form
    -  content-type: application/x-www-form-urlencoded
    -  메시지 바디에 쿼리 파리미터 형식으로 전달 
        - itemName=itemA&price=10000&quantity=10 
        - 예) 회원 가입, 상품 주문, HTML Form 사용

- 요청 파라미터 형식을 처리해야 하므로 @RequestParam 을 사용하자

    * 상품 등록 처리 - @RequestParam
        
        - addItemV1 - BasicItemController에 추가
            - 먼저 @RequestParam String itemName : itemName 요청 파라미터 데이터를 해당 변수에 받는다. 
            - Item 객체를 생성하고 itemRepository 를 통해서 저장한다.
            - 저장된 item 을 모델에 담아서 뷰에 전달한다.
        
    * 상품 등록 처리 - @ModelAttribute

    - @RequestParam 으로 변수를 하나하나 받아서 Item 을 생성하는 과정은 불편함 -> ModelAttribute 이용해서 한번에 처리하자 

        - addItemV2 - 상품 등록 처리 - ModelAttribute
        
        * @ModelAttribute - 요청 파라미터 처리
            - @ModelAttribute 는 Item 객체를 생성하고, 요청 파라미터의 값을 프로퍼티 접근법(setXxx)으로 입력해준다.

        * @ModelAttribute - Model 추가
            - 모델(Model)에 @ModelAttribute 로 지정한 객체를 자동으로 넣어준다.
            - (model.addAttribute("item", item) 안 해줘도 됨)
            - 모델에 데이터를 담을 때는 이름이 필요하다. 이름은 @ModelAttribute 에 지정한 name(value) 속성을 사용한다. 다르게 지정하면 다른 이름으로 모델에 포함됨
                - @ModelAttribute("hello") Item item 이름을 hello 로 지정 
                - model.addAttribute("hello", item); 모델에 hello 이름으로 저장

        - addItemV3 - 상품 등록 처리 - ModelAttribute 이름 생략
            - ModelAttribute 의 이름을 생략하면 모델에 저장될 때 클래스명을 사용한다. 이때 클래스의 첫글자만 소문자로 변경해서 등록한다.
                - 예) @ModelAttribute 클래스명 모델에 자동 추가되는 이름
                        : Item -> item

        - addItemV4 - 상품 등록 처리 - ModelAttribute 전체 생략
            -  @ModelAttribute 자체도 생략가능하다. 대상 객체는 모델에 자동 등록된다. 나머지 사항은 기존과 동일하다.

         
## 상품 수정

- 상품 수정 폼 컨트롤러 
    - BasicItemController에 추가
        - 수정에 필요한 정보를 조회하고, 수정용 폼 뷰를 호출한다.

- 상품 수정 폼 뷰
    - 정적 HTML을 뷰 템플릿(templates) 영역으로 복사하고 수정

- 상품 수정 개발
    - 상품 수정은 상품 등록과 전체 프로세스가 유사하다. 
        - GET /items/{itemId}/edit: 상품 수정 폼 
        - POST /items/{itemId}/edit : 상품 수정 처리

* 리다이렉트

    - 상품 수정은 마지막에 뷰 템플릿을 호출하는 대신에 상품 상세 화면으로 이동하도록 리다이렉트를 호출한다.

        - 스프링은 redirect:/... 으로 편리하게 리다이렉트를 지원한다.
            - redirect:/basic/items/{itemId}"

        - 컨트롤러에 매핑된 @PathVariable 의 값은 redirect 에도 사용 할 수 있다.
            - redirect:/basic/items/{itemId}의 {itemId} 는 @PathVariable Long itemId 의 값을 그대로 사용한다.

> HTML Form 전송은 PUT, PATCH를 지원하지 않는다. GET, POST만 사용할 수 있다.
> PUT, PATCH는 HTTP API 전송시에 사용



## PRG Post/Redirect/Get

- 지금까지 진행한 상품 등록 처리 컨트롤러는 심각한 문제가 있다. (addItemV1 ~ addItemV4)     
- -> 상품 등록을 완료하고 새로고침 시, 상품이 계속해서 중복 등록됨


- 웹 브라우저의 새로 고침은 마지막에 서버에 전송한 데이터를 다시 전송한다.
    
    - 상품 등록 폼에서 데이터를 입력하고 저장을 선택하면 POST /add + 상품 데이터를 서버로 전송한다. 이 상태에서 새로 고침을 또 선택하면 마지막에 전송한 POST /add + 상품 데이터를 서버로 다시 전송하게 된다.

    - 그래서 내용은 같고, ID만 다른 상품 데이터가 계속 쌓이게 된다.


* POST, Redirect GET

    - 상품 저장 후에 뷰 템플릿으로 이동하는 것이 아니라, 상품 상세 화면으로 리다이렉트를 호출해주면 된다.

    - 웹 브라우저는 리다이렉트의 영향으로 상품 저장 후에 실제 상품 상세 화면으로 다시 이동한다. 따라서 마지막에 호출한 내용이 상품 상세 화면인 GET /items/{id} 가 되는 것이다. 
        - -> 이후 새로고침을 해도 상품 상세 화면으로 이동하게 되므로 새로 고침 문제를 해결할 수 있다.


    - 상품 등록 처리 이후에 뷰 템플릿이 아니라 상품 상세 화면으로 리다이렉트 하도록 코드를 작성해보자. 이런 문제 해결 방식을 PRG Post/Redirect/Get 라 한다.
        - BasicItemController에 메서드 추가


> "redirect:/basic/items/" + item.getId() redirect에서 +item.getId() 처럼 URL에 변수를 더해서 사용하는 것은 URL 인코딩이 안되기 때문에 위험하다. 다음에 설명하는 RedirectAttributes 를 사용하자.



## RedirectAttribute

- 리다이렉트 할 때 간단히 status=true 를 추가해보자. 그리고 뷰 템플릿에서 이 값이 있으면, 저장되었습니다. 라는 메시지를 출력해보자.

    - BasicItemController에 메서드 추가
        - edirectAttributes 를 사용하면 URL 인코딩도 해주고, pathVarible , 쿼리 파라미터까지 처리해준다.
            - pathVariable 바인딩: {itemId}
            - 나머지는 쿼리 파라미터로 처리: ?status=true


    - 뷰 템플릿 메시지 추가
        - th:if : 해당 조건이 참이면 실행
        - ${param.status} : 타임리프에서 쿼리 파라미터를 편리하게 조회하는 기능
        - 원래는 컨트롤러에서 모델에 직접 담고 값을 꺼내야 한다. 그런데 쿼리 파라미터는 자주 사용해서 타임리프에서 직접 지원한다.



# SECTION 08. 다음으로 


- MVC1편 강의 흐름 정리 및 다음 강의 소개, 로드맵 안내



* 스프링 MVC 2편 - 백엔드 웹 개발 활용 기술 안내

    - 실제 예제에 단계적으로 기능을 발전시키며, 각 기능을 코드로 개발하면서 자연스럽게 학습

    * 타임리프 뷰 템플릿 주요 기능 정리, 활용

        타임리프가 제공하는 기능 없이 사용할 때 타임리프가 제공하는 기능을 학습하고 사용할 때 차이 타임리프 뷰 템플릿 기능 정리, 다양한 기능 학습

    * 메시지, 국제화 처리

        스프링의 메시지 처리 메커니즘 이해 메시지, 국제화 처리 소개 및 구현

    * 검증 - Validation

        컨트롤러에서 직접 검증 메커니즘 구현
        
        스프링이 제공하는 검증 메커니즘을 단계적으로 학습하고 적용 
        
        수동 검증에서 애노테이션 기반의 검증까지

    * 로그인 처리 - 쿠키, 세션
    
        WAS가 제공하는 세션을 사용하지 않고, 직접 세션 구현해서 사용하기
        
        WAS가 제공하는 세션 사용하기

    * 로그인 처리 - 필터, 인터셉터 

        개념과 차이 설명

        사용 예시 

    * 예외 처리

        순수한 서블릿 예외 처리 이해

        스프링 MVC가 제공하는 예외 처리 이해

        스프링 부트가 제공하는 예외 처리 이해

        API 예외 처리

        예외 처리 완전 정복


    * 자주 사용하는 기능, 기타 

        나머지
