package Game;

import Monster.ElementalMonster;
import Player.Player;
import Skill.Skill;

import java.util.List;
import java.util.Scanner;

public class View {
    private Scanner scanner;

    public View(){
        this.scanner = new Scanner(System.in);
    }

    public void printStartGameMessage(){
        System.out.println("이곳은 야생의 포켓몬들이 출현하는 대련장입니다.");
        System.out.println("대전에 참여할 두 마리의 몬스터를 선택하세요.");
    }

    public void printSelectedNumberError(){
        System.out.println("잘못된 번호입니다.");
    }

    public void printNoMonstersToSwitch(){
        System.out.println("교체할 몬스터가 없습니다.");
    }

    public void printMonsterToSelect(){
        System.out.println("교체할 포켓몬을 선택하세요:");
    }

    public void printBasicErrorMessage(){
        System.out.println("비정상적인 접근입니다.");
    }

    public void printSwitchedSuccessfully(){
        System.out.println("정상적으로 교체됐습니다.");
    }

    public void displayStatus(ElementalMonster enemyMonster, Player player) {
        System.out.println("\n----<현재 상태>-----");
        System.out.println("(나)" + player.getCurrentMonster().name
                +"\n(hp: " + player.getCurrentMonster().getHp()
                +"\n(mp: "+ player.getCurrentMonster().getMp()
                +"\n(defensePower: "+ player.getCurrentMonster().getDefensePower() +")");
        System.out.println("\n-------------------");
        System.out.println("(적)" + enemyMonster.name + " (hp: " + enemyMonster.getHp() + ")");
        System.out.println("\n-------------------");
    }

    public void printSelectSkills(List<Skill> skills){
        System.out.println("사용할 스킬을 선택하세요:");
        for (int i = 0; i < skills.size(); i++) {
            System.out.println((i + 1) + ". " + skills.get(i).name+ " / [power] "+ skills.get(i).power);
        }
        System.out.print("스킬 번호: ");

    }


}
