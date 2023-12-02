#### 여행지, 숙소 추천 웹사이트
- **프로젝트 소개**: Java, Spring Boot를 이용해 여행지, 숙소 추천 웹사이트를 개발 중입니다.
- **개발 기간**: [2023.11~ 개발 중]
- **주요 역할**: 프로젝트 전부 담당.

### 메인페이지(수정)

https://github.com/9817kg/travel/assets/137484097/22d0288e-af88-4cc0-b1db-a342b6f4d400

https://github.com/9817kg/travel/assets/137484097/010f2eba-631f-430c-9cab-9d72fb9e949b



### ( 개발 ) intro 페이지

https://github.com/9817kg/travel/assets/137484097/ce48a942-ce02-4c53-bcfc-f017a6479c08

### 회원가입 / 로그인 / 소셜로그인 / 로그아웃


https://github.com/9817kg/travel/assets/137484097/dd7e9caf-efb9-470c-bcb9-3ac3214d59ec

https://github.com/9817kg/travel/assets/137484097/da3d0749-c8a5-4ecc-b865-c65bfb233557




###  마이페이지 

## 프로필


https://github.com/9817kg/travel/assets/137484097/60af8298-3f8b-4ae3-9bf3-a72b411f270e


## 비밀번호 변경
- passwordEncoder  mach 를 통해 encode 입력한 비밀번호와 dto 에 저장된 비밀번호를 비교하여 일치하면 update 쿼리를 통해 비밀번호 업데이트 되게했습니다.



https://github.com/9817kg/travel/assets/137484097/6d0c9216-db30-4a62-8f37-51e63b2c81f0



## 회원탈퇴
- delete restful 를 이용했습니다.
- memeberId 를 uri 로 보내 해당 유저를 jpa 에 findByMemberId 를 통해 select 하여 제거했습니다.
- ajax 를 이용했습니다. ajax에  delete rest api 를 생성한 곳을 url 로 주어 method 로 delete 를 이용했습니다
- a 태그에 withdraw 함수에 모델로 받은 dto의 id 값과 input 태그에 id 를 통해 value로 얻어 파라미터로 주었습니다.
- ajax url 에 source 로  memberId + '?pw=' + password 를 주어 pw의 value 를 컨트롤러로 전송하게 했습니다.

https://github.com/9817kg/travel/assets/137484097/9620531c-2c52-4416-8dc6-8924d87ce222

### 이메일 찾기, 비밀번호 찾기

#### 비밀번호 찾기는 기존에 임시비밀번호로 전송했지만, 따로 가이드 없이 비밀번호만 변경해주면 회원은 자기 임시비밀번호로 마이페이지에서 비밀번호를 자신의 입맛대로 변경해야 한다는 번거로움이 있었다. 
#### 그래서 메일을 전송할 때 새로운 이메일로 변경할 페이지 link 를 보내는 걸로 진행했다.
- javaMail 이용


https://github.com/9817kg/travel/assets/137484097/e03677e3-88d0-4d99-b8d8-68510a5cd524



https://github.com/9817kg/travel/assets/137484097/b1cab29b-68d0-4dc1-bcf7-b6ec4f5c11af






