# TheLastChance

[Version Info] \
JDK 17.0.6 corretto / Spring 6.0.6 / SpringBoot 3.0.4 / H2 database 2.1.214

First ADD Dependencies
 - Spring Data JPA
 - H2 Database
 - Spring Boot DevTools
 - Lombok
 - MyBatis Framework

# 이슈(였던) 사항
- SDK 최신버전 사용 시, UTF-8 깨짐 현상 발생 (19버전) -> 17버전으로 다운그레이드
- Global Exception Handler와 Try-Catch의 가불기 관계(OpenAPI 기준으로 Exception 처리를 하면 )
- 멀티 모듈 구성 및 모듈간 의존성 제약
- 대용량 트래픽을 염두에 둔 아키텍처 및 구현
- API 장애 시, 다른 서버 API 호출 대응 구현

# 프로젝트를 진행하면서 아쉬웠던 점.
- 특정 OpenAPI 장애 시, 다른 OpenAPI로 돌리는 로직에서 **서킷브레이커 디자인 패턴**을 적용해보고 싶었으나 하지 못했다.
- 처음 해당 프로젝트의 요구사항을 잘못 해석해 Thymeleaf로 앞단을 만들고 있었던 시간이 무척 아쉬웠다.

# 결론
- 해당 프로젝트를 진행하면서 짧은 시간이었지만 내 생각보다 내가 놓치고 있는 개념들이 많았다는 것을 느낄 수 있었으며, 한 단계급은 아니지만 한 걸음 정도는 더욱 성장한 느낌이 들었다.
- 앞으로는 이런 사이드 코딩을 더욱 자주 하면서 지속적인 성장의 양분으로 삼을 계획이다.


[블로그 검색 API 명세]
 
![image](https://user-images.githubusercontent.com/128284659/226656549-1d03f895-4f7c-4e0e-9d10-d3e67a1b1c29.png)
![image](https://user-images.githubusercontent.com/128284659/226657739-40b174a0-689d-4958-b9a6-2ffb624732d8.png)

success result
```
{
    "documents": [
        {
            "title": "디아블로4 시작 전에 알아보는 디아블로 스토리 및 세계관 #1 ~ 프롤로그",
            "contents": "To be countinue - 참고자료 https://diablo.fandom.com/ https://store.steampowered.com/app/8930/Sid_Meiers_Civilization_V/ https://www.beastsofwar.com/board-games/mage-wars-reveals-academy-forcemaster-expansion/ https://namu.wiki/w/%<b>EB</b>%<b>94%</b><b>94%</b><b>EC</b>%<b>95%</b><b>84%</b><b>EB</b>%<b>B</b><b>8%</b><b>94%</b><b>EB</b>%<b>A</b><b>1%</b>9C%20%<b>EC</b>%8B%9C%<b>EB</b>%A6%AC%<b>EC</b>%A6%88",
            "url": "https://blog.naver.com/z00o0o/223046784117",
            "blogname": "라이트 즐겜러 직장인의 게임 이야기",
            "thumbnail": "",
            "datetime": "2023-03-17T10:00:00.000+09:00"
        }
    ],
    "meta": {
        "total_count": 13839,
        "is_end": false,
        "favorite_keywords": [
            {
                "keyword": "디아블로",
                "count": 2
            },
            {
                "keyword": "디아블로2",
                "count": 1
            }
        ],
        "pageable_count": 799,
        "pageinfo": {
            "sort_type": "accuracy",
            "total_page": 799,
            "current_page": 1
        }
    }
}
```

