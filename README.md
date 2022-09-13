# example-quartz

이 앱은 Spring boot + Quartz 통합을 설명하기 위해 작성한 예제 코드이다.

## 게시글 위치

- [Spring Boot + Quartz 사용하기](https://hellomike.page/posts/2022/spring-boot-quartz/)

## 실행 방법

다음 프로파일로 변경 해서 각각 테스트 해 볼 수 있다.
- inmemory : DB를 사용하지 않고 메모리 저장을 사용한다.
- local : Local MariaDB를 사용한다.
- cluster : docker-compose.yml을 통해 실행되는 프로파일이며, 클러스터링을 테스트 해 볼 수 있다.

### 로컬 인메모리 실행

``` shell
./gradlew bootRun --args='--spring.profiles.active=inmemory'
```

### 로컬 Mariadb 실행

``` shell
./gradlew bootRun --args='--spring.profiles.active=local'
```

### 클러스터링 실행

* `docker-compose.yml`를 통해 reverse-proxy 및 mariadb, app 2개로 구성하였다.

```shell
docker-compose up -d
```

## 작성자

- Mike

## License

- [The Unlicense](./LICENSE)
