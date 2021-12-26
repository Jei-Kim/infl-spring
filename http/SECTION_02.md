# SECTION 02. URI와 웹 브라우저 요청 흐름

## URI

- URI(Uniform Resource Identifier)
- Uniform: 리소스 식별하는 통일된 방식
- Resource: 자원, URI로 식별할 수 있는 모든 것(제한 없음)
- Identifier: 다른 항목과 구분하는데 필요한 정보
    <br>
    - URL(Resource Locator): 리소스가 있는 위치를 지정
    - URN(Resource Name): 리소스에 이름을 부여
    - 위치는 변할 수 있으나 이름은 불변
- URN 이름만으로 실제 리소스를 찾을 수 있는 방법이 보편화 되지 않음 
- 앞으로 URI를 URL과 같은 의미로 이야기

* URL 전체 문법
- https://www.google.com:443/search?q=hello&hl=ko
    
    - 프로토콜(https) 
    - 호스트명(www.google.com) 
    - 포트 번호(443)
    - 패스(/search)
    - 쿼리 파라미터(q=hello&hl=ko)

* URL scheme
- scheme://[userinfo@]host[:port][/path][?query][#fragment]
- 예) https://www.google.com:443/search?q=hello&hl=ko
    - 주로 프로토콜 사용
    - 프로토콜: 어떤 방식으로 자원에 접근할 것인가 하는 약속 규칙
    - 예) http, https, ftp 등등
    - http는 80 포트, https는 443 포트를 주로 사용, 포트는 생략 가능 
    - https는 http에 보안 추가 (HTTP Secure)

* URL userinfo
- scheme://[userinfo@]host[:port][/path][?query][#fragment]

    - URL에 사용자정보를 포함해서 인증 -> 거의 사용하지 않음

* URL host
- scheme://[userinfo@]host[:port][/path][?query][#fragment]
- 예) https://www.google.com:443/search?q=hello&hl=ko

    - 호스트명 -> 도메인명 또는 IP 주소를 직접 사용가능

* URL PORT
- scheme://[userinfo@]host[:port][/path][?query][#fragment]
- 예) https://www.google.com:443/search?q=hello&hl=ko

    - 포트(PORT)
    - 접속 포트
    - 일반적으로 생략, 생략시 http는 80, https는 443

* URL path
- scheme://[userinfo@]host[:port][/path][?query][#fragment]
- 예) https://www.google.com:443/search?q=hello&hl=ko

    - 리소스 경로(path), 계층적 구조

* URL query
- scheme://[userinfo@]host[:port][/path][?query][#fragment]
- 예) https://www.google.com:443/search?q=hello&hl=ko

    - key=value 형태
    - ?로 시작, &로 추가 가능 ?keyA=valueA&keyB=valueB
    - query parameter, query string 등으로 불림, 웹서버에 제공하는 파라미터, 문자 형태

* URL fragment
- scheme://[userinfo@]host[:port][/path][?query][#fragment] 
- 예) https://docs.spring.io/spring-boot/docs/current/reference/html/getting-
started.html#getting-started-introducing-spring-boot 

    - fragment
    - html 내부 북마크 등에 사용
    - 서버에 전송하는 정보 아님

## 웹 브라우저 요청 흐름

1. 웹 브라우저가 HTTP 메시지 생성(애플리케이션 계층)
    - HTTP 요청 메세지
        -  GET /search?q=hello&hl=ko HTTP/1.1 Host: www.google.com
2. SOCKET 라이브러리를 통해 전달(애플리케이션 계층)
    - A: TCP/IP 연결(IP, PORT)
    - B: 데이터 전달
3. TCP/IP 패킷 생성, HTTP 메시지 포함(os 계층)
    -> LAN (네트워크 인터페이스 계층)-> 인터넷 -> 서버

- 웹 브라우저 -> 구글 서버로 요청 패킷 전달
    - 요청 패킷: TCP/IP패킷에 HTTP 요청 메세지 담아서
    
- 요청 패킷 도착 후, 구글 서버 -> 웹 브라우저로 응답 패킷 전달
    - 응답 패킷: TCP/IP패킷에 HTTP 응답 메세지 담아서

