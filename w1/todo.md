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
## 의문점 및 회고 (solved 시 체크)
- [ ] Skill 클래스에서 record class 로 변경을 고려하라는 경고 문구 확인해보기
- [x] private / public / default 설계 방법 (public+final vs private+getter 의 차이점 ?)
  >   => final 사용 시, ‘값의 재할당’을 막아주는 것이라는 점에서 getter 와 차이가 있다.    
  객체의 값을 수정 불가하도록 막기 위해 final 을 사용하면 분명 문제가 발생.    
  꺼내서 보기만 하는 용도면, private 으로 선언한 변수에 getter 를 사용하는 것이 맞을 것 같다.   
  [강의자료 - Type 참고]
---


- [ ] 따로 공용 메서드 분리하는 방법 있나 찾아보기   공용으로 사용하는 로직의 경우 한번에 관리할 수 있는 방법이 있는지 궁금.
  > => Util 클래스 만들어서 그 안에 static 한 메서드들 저장해두는 방식이 존재.   
  / 하지만 위 방법은 OOP 가 아님. 종속성을 커지게 하는 안티패턴인 듯 싶다.    
  해결 방안은 무엇이 있을까 : 1. 해당 클래스 내에서의 중복 메서드는 따로 분리하기 / 2. abstract 사용해서 공용 메서드는 정리하기 ..?    
  코드를 중복으로 작성하는 것을 꺼려서 계속 이런 것을 고민하는 것 같다. 어떤 것이 좋을지는 모르겠다.
---


- [ ] attack 과 같은 경우 .. monster 에서 해야하나 game 에서 처리해야하나 헷갈렸다. 
      원래는 Scanner 로직도 모두 monster 의 attack / defense 메소드에 추가했었는데, Scanner 는 제외 후 순수한 비즈니스 로직만 메서드에 넣기로 했다.
- [ ] 상속 기준 - 불/물/땅 타입의 경우 저렇게 class 따로 만드는 것의 이점을 아직 모르겠다. 
  > => 원래의 의도 : 불 / 물 / 땅 타입 포켓몬의 메서드가 추가될 것을 고려해 따로 만들었었다.    
  elementalType 을 달리해서 이들을 분리했어야 했는지 고민 (아직까지는 저 방식의 이점이 체감되지 않는다.)   
---


- [ ] 클래스에 들어가야 하는 로직의 범위 고려   (player 선택 모든 로직이 player 클래스 안에 있는 것이 좋은가? or game 별로 선택하는거니까 game 에 들어가는 것이 좋은가?)
  > => 고민 후, scanner 로 유저 입력을 맏는 부분은 game 마다 생성되는 것이니, 이 관련한 로직은 game에, 객체 관련 순수한 로직은 객체 메서드로 관리하는 것이 좋을 것 같다고 생각했다.
- [ ] abstract 시 type 선정을 부모로 ? 자식으로 ?
  > 다양한 대분류를 만들려고 (ex. ElementalMonster) Monster 추상 클래스로 만들고, 이를 상속하는 하위의 ElementalMonster 를 만들었다. ElementalMonster는 ElementalType 을 갖도록 제한하고 싶어서   
    필드를 따로 만들었는데, 추후 ElementalMonster 를 비롯한 대분류 몬스터 어느 것이든 게임에서 사용할 수 있을 것이니 게임에서 사용하는 몬스터의 타입을 ```Arraylist<Monster>``` 로 지정했는데,   
    뒤에서 ElementalMonster 들 끼리의 우위를 반환하는 함수에서 문제가 발생했다. (잘못된 abstract 클래스 사용) => elementalType 을 type 으로 변경, 추상 클래스 필드에도 추가. 
    추후 ElementalMonster 로 타입이 지정된 부분은 Monster 로 걷어냈다.

  > 나름의 해결 ... : (업캐스팅과 다운캐스팅 활용) 우선 Monster 로 업캐스팅, 후에 instanceOf 로 타입 확인 후 같은 타입일 시 다운캐스팅하여 isWinTarget 계산,   
  > 다른 대분류 몬스터면 무조건 1로 
---


- [ ] Monster vs ElementalMonster - 다른 부류 Monster 생기는 것 고려해서 이렇게 상속 구조 잡았는데, 더 좋은 방법이 있을지 .. 
  처음에 elementType 필드를 ElementalMonster 에만 넣었다가 game 클래스에서 둘의 사용이 꼬여버려 문제가 있었다.
- [ ] 기존 기획은 동시에 공격 / 방어가 가능한 것이었다. 하지만 공격을 동시에 진행하면 hp 와 mp 갱신에 있어서 문제가 있을 것 같아 기획을 수정했다.
