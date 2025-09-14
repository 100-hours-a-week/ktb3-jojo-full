# week1 - java cli 프로그램 제작 
---
## TODO
- [x] 클래스 다이어그램 그리기
- [x] 2차 상속 구현하기
- [x] 클래스 설계하기 (코드 작성)
- [x] 유저 콘솔 입력 구현
- [x] Main 에서 실행
- [ ] loop

---
## ?? - solved 된 경우 체크
- [ ] Skill 클래스에서 record class 로 변경을 고려하라는 경고 문구
- [ ] private / public / default 설계 방법 (public+final vs private+getter 의 차이점 ?)
- [ ] 따로 공용 메서드 분리하는 방법 있나 찾아보기
- [ ] attack 과 같은 경우 .. monster 에서 해야하나 game 에서 처리해야하나 헷갈림
- [ ] 상속 기준 - 불/물/땅 타입의 경우 저렇게 class 따로 만드는 것의 이점 찾아보기
- [ ] 클래스에 들어가야 하는 로직의 범위 고려 (player 선택 모든 로직이 player 클래스 안에 있는 것이 좋은가?)
- [ ] abstract 시 type 선정을 부모로 ? 자식으로 ?
- [ ] Monster vs ElementalMonster - 다른 부류 Monster 생기는 것 고려해서 이렇게 상속 구조 잡았는데, 더 좋은 방법이 있을지 .. 
  처음에 elementType 필드를 ElementalMonster 에만 넣었다가 game 클래스에서 둘의 사용이 꼬여버려 문제가 있었다.