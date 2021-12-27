# SECTION 01. 인터넷 네트워크

## 인터넷 통신

- 클라이언트와 서버가 인터넷을 통해 상호작용

## IP(인터넷 프로토콜)

- 인터넷 프로토콜 역할
    - 지정한 IP주소에 데이터 전달
    - 패킷이라는 통신 단위로 데이터 전달

- IP 패킷
    - 간략히 요약하면 출발지의 IP, 목적지 IP, 전송 데이터로 구성
    - 클라이언트 패킷, 서버 패킷 왕복하며 데이터 주고 받음

- IP 프로토콜의 한계
    - 비연결성
        - 패킷을 받을 대상이 없거나, 서비스 불능 상태여도 패킷 전송
    - 비신뢰성
        - 중간에 패킷이 사라지거나, 순서대로 오지 않을 경우?
    - 프로그램 구분
        - 같은 IP를 사용하는 서버에서 통신하는 애플리케이션이 둘 이상이면?

## TCP, UDP

* 인터넷 프로토콜 스택의 4계층
    - 애플리케이션 계층 - HTTP, FTP
    - 전송 계층 - TCP, UDP
    - 인터넷 계층 - IP
    - 네트워크 인터페이스 계층

    1. 프로그램이 메세지 생성 (애플리케이션)
    2. SOCKET 라이브러리를 통해 전달 (애플리케이션)
    3. TCP 정보 생성, 메시지 데이터 포함 (OS)
    4. IP 패킷 생성, TCP 데이터 포함(OS)
        -> 랜카드(네트워크 인터페이스) -> 인터넷 -> 서버

- IP 패킷 정보
    - 출발지, 목적지의 ip, 데이터

- TCP/IP 패킷 정보
    - IP 패킷 정보 + TCP 세그먼트(출발지, 목적이 port, 전송 제어, 순서, 검증 정보)

- TCP 특징
    - 전송 제어 프로토콜(Transmission Control Protocol)
    - 연결지향 - TCP 3 way handshake (가상 연결) 
    - 데이터 전달 보증 순서 보장
    - 신뢰할 수 있는 프로토콜 -> 현재는 대부분 TCP 사용

-  TCP 3 way handshake
    1. SYN: 접속 요청
    2. SYN: 접속 요청 + ACK: 요청 수락
    3. ACK: 요청 수락
<br>
    *참고: 3. ACK와 함께 데이터 전송 가능*
<br>

- UDP 특징
    - 사용자 데이터그램 프로토콜(User Datagram Protocol)
    - 하얀 도화지에 비유(기능이 거의 없음)
    - 데이터 전달 및 순서가 보장되지 않지만, 단순하고 빠름
    - IP와 거의 같다. +PORT +체크섬 정도만 추가
    - 애플리케이션에서 추가 작업 필요

## PORT

- 같은 IP 내에서 프로세스 구분하기 위한 역할
    (한 번에 두 가지 이상 서버에 연결 시)
- 0 ~ 65535 할당 가능
- 0 ~ 1023: 잘 알려진 포트, 사용하지 않는 것이 좋음
    - FTP - 20, 21
    - TELNET - 23 
    - HTTP - 80 
    - HTTPS - 443

## DNS

- IP는 기억하기 어렵고, 변경될 수 있다는 문제점이 있음
    - -> DNS 사용 시 두 가지 문제 모두 해결 가능

- 도메인 네임 시스템(Domain Name System)
    - 전화번호부와 같다고 이해, 도메인 명(ex. google.com)을 IP 주소(ex. 200.200.200.2)로 변환해준다
    1. 클라이언트-> DNS 서버로 도메인 명 입력
    2. DNS 서버 -> 클라이언트로 IP 주소로 응답
    3. 클라이언트 -> 서버로 접속(IP 주소)


