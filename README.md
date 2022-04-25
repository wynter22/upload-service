## 목차

1. [개발 환경](#개발-환경)
2. [요구 사항](#요구-사항)
3. [문제 해결](#문제-해결)

## 개발 환경
               
1. OS : Mac
2. Back End : [README.md](./web-app/README.md)
3. Front End : [README.md](./web-ui/README.md)

## 요구 사항

대량 파일 (CSV 10만건 이상) 업로드를 통한 Data 저장 기능 구현.

* Drag & Drop으로 CSV 파일 업로드.
* 업로드된 파일을 DB에 저장하고 진행 상태를 progress bar로 상태 표시.
* 업로드 데이터셋은 제공받은 데이터셋.csv를 활용.

## 문제 해결

### 구현

***

##### 해결 전략

1. 클라이언트가 서버에 CSV파일 업로드하는 API를 호출한다.
2. 클라이언트는 SSEClinet를 생성하는 API를 호출하여 업로드 중 서버로부터 message(진행율)를 수신받을 수 있도록 한다.
3. 서버는 CSV파일을 파싱한다.
4. 서버는 파싱된 데이터를 Member.class로 변환한다.
5. 서버는 변환된 데이터를 DB에 10000건씩 저장하면서 message(진행율)를 클라이언트에 전달한다.
6. 클라이언트는 전달 받은 진행율을 표시한다.
7. 완료되면 클라이언트는 SSEClinet를 제거하는 API를 호출한다.

##### Back End 구현

1. DB는 H2를 사용한다.
2. JpaRepository를 사용하여 쿼리 로직을 구현한다.
3. [OpenCSV](http://opencsv.sourceforge.net/)
   라이브러리를 사용하여 CSV파일을 파싱한다.

4. SseEmitter를 생성하여 웹 클라이언트에 데이터 처리 상황을 공유할 수 있도록 구현한다.
5. ApplicationEventPublisher를 이용하여 생성된 SseEmitter에 이벤트(데이터 처리 정보)를 전달한다.

##### Front End 구현

1. [Vue2Dropzone](https://rowanwins.github.io/vue-dropzone/docs/dist)
   을 사용하여 Drag & Drop을 구현한다.

2. [VueCoreUI](https://coreui.io/vue/docs/introduction/)
   의 ProgressBar 컴포넌트로 진행상태를 표시한다.

3. [VueSSE](https://www.npmjs.com/package/vue-sse)
   를 사용하여 진행상태를 서버로 부터 전송받을 수 있도록 한다.

### Entity

***

* CSV 파일에서 파싱한 데이터를 저장하기 위한 Entity를 정의한다.

#### Member

| PK  | Column    | type         |
|:---:|-----------|--------------|
|  O  | id        | bigint       |
|     | firstName | varchar(255) |
|     | lastName  | varchar(255) |
|     | email     | varchar(255) |

### API

***

#### 1. CSV 파일 업로드

* CSV파일을 업로드 하고, 파일에서 추출한 데이터를 DB에 저장한다.

```
 POST /file/upload
```

#### 2. SSE Client 생성

* CSV파일 업로드 후 데이터 저장 진행사항을 공유받기 위해서 SSE Client를 서버에 생성한다.

```
 GET /sse/subscribe?id={clientId}
```

#### 3. SSE Client 제거

* CSV파일 업로드 및 데이터 처리 완료 후 SSE Client를 서버에서 제거한다.

```
 GET /sse/unsubscribe?id={clientId}
```
