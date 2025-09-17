package Player;

import Monster.Monster;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Player {
    List<Monster> myMonsters;
    Monster currentMonster; //현재 사용 중인 몬스터

    public Player() {
        this.myMonsters = new ArrayList<>(2);
//        this.scanner = new Scanner(System.in);
//        this.currentMonster = myMonsters.getFirst();
    }

    public void selectMonster(Monster newMonster){
        this.myMonsters.add(newMonster);
    };

    public void setCurrentMonster(Monster monster){
        this.currentMonster = monster;
    }

    public List<Monster> getMyMonsters(){
        return this.myMonsters;
    }

    public Monster getCurrentMonster(){
        return this.currentMonster;
    }

    public boolean switchMonster(int choice) {
        //정상적으로 종료 시 true
        if (choice >= 0 && choice < myMonsters.size() && myMonsters.get(choice) != currentMonster) {
            this.currentMonster = myMonsters.get(choice);
            return true; //정상적 종료
        };
        return false;
    }

}
