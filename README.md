# SearchBlog
1.프로젝트 사용 기술 스택
- Spring Boot 3.0.4
- Java Development Kit (JDK) 17.0.6
- Gradle 7.6.1
- H2 in-memory database
- Java Persistence API (JPA)

2.프로젝트 구현 기능
  1) 카카오 블로그 검색
  2) 네이버 블로그 검색(추가)
  3) 인기검색어 상위 10개 조회 기능<br>
    - inMemory DB(h2) 사용으로 서버 down시 데이터 같이 삭제(file모드 OFF)
   
3.필요 기능 외 사용한 외부 라이브러리
  1) lombok : Getter,Setter로 효율적인 소스 관리를 위하여 사용
  2) jackston-databind : JSONData를 효율적으로 맵핑하기 위하여 사용

4.기능 점검을 위한 빌드 결과물
  1) 다운로드 링크 : https://drive.google.com/file/d/118y6zU7Vy4SRsjz3gewG_25QZ00fJvFI/view?usp=sharing
  2) 주의사항<br>
    - jar파일 다운로드하는데 시간이 좀 걸립니다 기다려주세요.<br>
    - JDK 17 사용한 프로젝트로 java-jar 실행시 안되면 jDK 버전 확인 요청드립니다.

5. API 명세서
  1) 다운로드 링크 : https://docs.google.com/document/d/1dK9CO8GaPvu_mFnDKMC00srCbYoww9iS/edit?usp=sharing&ouid=114601060427137825583&rtpof=true&sd=true
  
