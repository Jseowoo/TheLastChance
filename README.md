# TheLastChance

[Version Info] \
**JDK 17.0.6 corretto / Spring 6.0.6 / SpringBoot 3.0.4 / H2 database 2.1.214**

[Executable jar] 다운로드 링크(42.1MB) \
#1 https://drive.google.com/file/d/1kqT9N-N-CyDfw2DtuZTpFF1NuznskAW6/view?usp=sharing \
#2 http://naver.me/GMvGyntx \
*구글 드라이브가 느리면 네이버 클라우드 링크를 이용해 주세요.

[jar 실행 명령어]
1. 커맨드 창을 열어 jar가 있는 디렉토리로 이동
2. **java -jar ./chance-0.0.1-SNAPSHOT.jar --spring.profiles.active=local** 입력 (JDK 17 필요)

First ADD Dependencies
 - Spring Data JPA
 - H2 Database
 - Spring Boot DevTools
 - Lombok

# 이슈(였던) 사항
- SDK 최신버전 사용 시, UTF-8 깨짐 현상 발생 (19버전) -> 17버전으로 다운그레이드
- Global Exception Handler와 Try-Catch의 가불기 관계(OpenAPI 기준으로 Exception 처리를 하면 )
- 멀티 모듈 구성 및 모듈간 의존성 제약
- 대용량 트래픽을 염두에 둔 아키텍처 및 구현
- API 장애 시, 다른 서버 API 호출 대응 구현

# 프로젝트를 진행하면서 아쉬웠던 점.
- 특정 OpenAPI 장애 시, 다른 OpenAPI로 돌리는 로직에서 유사 서킷브레이커 디자인 패턴을 적용해보고 싶었으나 하지 못했다.
- 처음 해당 프로젝트의 요구사항을 잘못 해석해 Thymeleaf로 앞단을 만들고 있었던 시간이 무척 아쉬웠다.
- OpenAPI 서버 당으로 모듈을 나누고(ex. 카카오모듈, 네이버모듈 등) 본 서버에서 해당 모듈을 사용하는 식으로 구성했으면 어땠을까 하는 생각도 들었다.
- 카카오 OpenAPI 대신 네이버 OpenAPI로 데이터를 받아왔을 때, 데이터가 많이 상이하여 어떤 식으로 구성해야 하는지에 대해 감을 잘 잡지 못해서 까다로웠던 것 같다. 같은 데이터를 return 해주어야 했기에 얻을 수 없는 데이터는 null 처리를 했는데 이 점도 살짝 아쉬웠다.

# 결론
- 해당 프로젝트를 진행하면서 짧은 시간이었지만 내 생각보다 내가 놓치고 있는 개념들이 많았다는 것을 느낄 수 있었으며, 한 단계급은 아니지만 한 걸음 정도는 더욱 성장한 느낌이 들었다.
- 앞으로는 이런 사이드 코딩을 더욱 자주 하면서 지속적인 성장의 양분으로 삼을 계획이다.
 
