package Player;

import Monster.Monster;

import java.util.List;

public class Player {
    List<Monster> myMonsters;
    Monster currentMonster; //현재 사용 중인 몬스터

    public Player(List<Monster> myMonsters) {
        this.myMonsters = myMonsters;
        this.currentMonster = myMonsters.getFirst();
    }

    public void selectMonster(Monster newMonster){
        this.myMonsters.add(newMonster);
    };
    public void switchMonster(Monster monster){
        this.currentMonster = monster;
    };
}
