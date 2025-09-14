package Player;

import Monster.Monster;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Player {
    List<Monster> myMonsters;
    Monster currentMonster; //현재 사용 중인 몬스터
    private Scanner scanner;

    public Player() {
        this.myMonsters = new ArrayList<>(2);
        this.scanner = new Scanner(System.in);
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

    public void switchMonster() {
        if (myMonsters.size() <= 1) {System.out.println("교체할 몬스터가 없습니다."); return;}

        System.out.println("교체할 포켓몬을 선택하세요:");
        for (int i = 0; i < myMonsters.size(); i++) {
            if (myMonsters.get(i) != currentMonster) {
                System.out.println((i + 1) + ". " + myMonsters.get(i).name);
            }
        }
        int choice = scanner.nextInt() - 1;
        if (choice >= 0 && choice < myMonsters.size() && myMonsters.get(choice) != currentMonster) {
            this.currentMonster = myMonsters.get(choice);
            System.out.println(this.currentMonster.name + "으로 포켓몬을 교체했습니다.");
        }
    }

}
