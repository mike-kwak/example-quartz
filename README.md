## example-quartz

이 앱은 Spring boot + Quartz 통합을 설명하기 위해 작성한 예제 코드이다.

### 게시글 위치

- [TBD](https://hellomike.page)

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

### 작성자

- Mike

### License

- Public Domain
