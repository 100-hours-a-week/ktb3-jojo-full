package Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import Monster.*;
import Player.Player;
import Skill.AttackSkill;
import Skill.DefenseSkill;

public class Game {
    private Player player;
    private Scanner scanner;
    private Random random;
    private List<ElementalMonster> allMonsters; // 모든 몬스터 리스트

    public Game() {
        this.scanner = new Scanner(System.in);
        this.random = new Random();

        this.player = new Player();
        this.allMonsters = new ArrayList<>(10);
        allMonsters.add(new FireMonster("파이리", 100, 110, 100));
        allMonsters.add(new WaterMonster("꼬부기", 110, 90, 70));
        allMonsters.add(new GroundMonster("디그다", 120, 80, 50));
    }

    public void startGame() {
        System.out.println("이곳은 야생의 포켓몬들이 출현하는 대련장입니다.");

        System.out.println("대전에 참여할 두 마리의 몬스터를 선택하세요.");


        for (int i = 0; i < allMonsters.size(); i++) {
            System.out.println((i + 1) + ". " + allMonsters.get(i).name);
        }

        for (int i = 0; i < 2; i++) {
            System.out.print((i + 1) + "번째 몬스터 선택: ");
            int choice = scanner.nextInt() - 1;
            if (choice >= 0 && choice < allMonsters.size()) {
                player.selectMonster(allMonsters.get(choice));
                if (i == 0){
                    player.setCurrentMonster(allMonsters.get(choice));
                }
            } else {
                System.out.println("잘못된 번호입니다.");
                i--;
            }
        }


        System.out.println("당신은 " + player.getMyMonsters().getFirst().name + "와 " + player.getMyMonsters().get(1).name + "를 선택했습니다.");

        //상대 몬스터 생성 (랜덤)
        ElementalMonster enemyMonster = allMonsters.get(random.nextInt(allMonsters.size()));
        System.out.println("\n야생의 " + enemyMonster.name + "이 나타났다!");

        battle(enemyMonster);

        scanner.close();
    }

    //포켓몬 switch
    void getSwitchInput(){
        List<Monster> candidateMonsters = player.getMyMonsters();
        Monster currentMonster = player.getCurrentMonster();
        if (candidateMonsters.size() <= 1){
            System.out.println("교체할 몬스터가 없습니다.");
            return;
        }


        System.out.println("교체할 포켓몬을 선택하세요:");

        for (int i = 0; i < candidateMonsters.size(); i++) {
            if (candidateMonsters.get(i) != currentMonster) {
                System.out.println((i + 1) + ". " + candidateMonsters.get(i).name);
            }
        }
        int choice = scanner.nextInt() - 1;

        boolean isSwitched = player.switchMonster(choice);

        if (!isSwitched){
            System.out.println("비정상적인 접근입니다.");
            return;
        }

        System.out.println("정상적으로 교체됐습니다.");

    }

    private void battle(ElementalMonster enemyMonster) {
        while (true) {
            // 게임 승패 조건 체크
            if (!player.getCurrentMonster().isAlive()) {
                System.out.println(player.getCurrentMonster().name + "이 쓰러졌습니다!");
                if (isPlayerAllDefeated()) {
                    break;
                } else {
                    //교체
                    this.getSwitchInput();
                }
            }
            if (!enemyMonster.isAlive()) {
                break;
            }

            displayStatus(enemyMonster);
            handlePlayerTurn(enemyMonster);


            if (enemyMonster.isAlive()) {
                displayStatus(enemyMonster);
                handleEnemyTurn(enemyMonster);
            }
        }
        endGame(enemyMonster);
    }

    private void displayStatus(ElementalMonster enemyMonster) {
        System.out.println("\n----<현재 상태>-----");
        System.out.println("(나)" + player.getCurrentMonster().name
                +"\n(hp: " + player.getCurrentMonster().getHp()
                +"\n(mp: "+ player.getCurrentMonster().getMp()
                +"\n(defensePower: "+ player.getCurrentMonster().getDefensePower() +")");
        System.out.println("\n-------------------");
        System.out.println("(적)" + enemyMonster.name + " (hp: " + enemyMonster.getHp() + ")");
        System.out.println("\n-------------------");
    }

    private void handlePlayerTurn(ElementalMonster enemyMonster) {
        int enemyChoice = random.nextInt(2); // 0: 방어, 1: 대기
        System.out.println("어떤 행동을 하시겠습니까?");
        System.out.println("1. 공격");
        System.out.println("2. 몬스터 교체 후 공격");
        System.out.println("3. 휴식");
        System.out.print("선택: ");

        int choice = scanner.nextInt();


        if (enemyChoice == 0) {
            Monster currentMonster = player.getCurrentMonster();
            List<AttackSkill> skills = currentMonster.getAttackSkills();
            enemyMonster.defense(currentMonster, 0);
        }

        if (!(choice == 1 || choice == 2)){
            System.out.println("휴식 후 상대 턴으로 돌아갑니다. ");
            return;
        }

        if (choice == 2){
            this.getSwitchInput();
        }

        System.out.println("사용할 스킬을 선택하세요:");
        Monster currentMonster = player.getCurrentMonster();
        List<AttackSkill> skills = currentMonster.getAttackSkills();
        for (int i = 0; i < skills.size(); i++) {
            System.out.println((i + 1) + ". " + skills.get(i).name+ " / [power] "+ skills.get(i).power);
        }
        System.out.print("스킬 번호: ");
        int skillChoice = scanner.nextInt() - 1;
        boolean isSuccess = currentMonster.attack(enemyMonster, skillChoice);
        if (!isSuccess){  System.out.println("MP가 부족하여 스킬을 사용할 수 없습니다.");}
    }

    private void handleEnemyTurn(ElementalMonster enemyMonster) {
        System.out.println("\n상대 몬스터의 턴입니다.");
        int enemyChoice = random.nextInt(2); // 0: 공격, 1: 대기

        System.out.println("어떤 행동을 하시겠습니까?");
        System.out.println("1. 방어 태세 돌입");
        System.out.println("2. 대기");
        System.out.print("선택: ");
        int choice = scanner.nextInt();


        if (choice == 1){
            Monster currentMonster = player.getCurrentMonster();

            List<DefenseSkill> skills = currentMonster.getDefenseSkills();
            System.out.println("사용할 스킬을 선택하세요:");
            for (int i = 0; i < skills.size(); i++) {
                System.out.println((i + 1) + ". " + skills.get(i).name+ " / [power] "+ skills.get(i).power);
            }
            System.out.print("스킬 번호: ");
            int skillChoice = scanner.nextInt() - 1;
            boolean isSuccess = currentMonster.defense(enemyMonster, skillChoice);
            if (!isSuccess) { System.out.println("방어력이 부족하여 스킬을 사용할 수 없습니다."); }
        }else{
            System.out.println("대기합니다.");
        }

        if (enemyChoice == 0) {
            Monster currentMonster = player.getCurrentMonster();

            int enemyRandSkill = random.nextInt(2);
            boolean isSuccess = enemyMonster.attack(currentMonster, enemyRandSkill);
            if (!isSuccess){  System.out.println("상대방의 mp가 부족해 공격에 실패했어요!");}
        } else {
            System.out.println("상대방이 공격을 하지 않았습니다.");
        }
    }

    private boolean isPlayerAllDefeated() {
        for (Monster m : player.getMyMonsters()) {
            if (m.isAlive()) {
                return false;
            }
        }
        return true;
    }

    private void endGame(ElementalMonster enemyMonster) {
        System.out.println("\n-----<게임 종료>--------");
        if (isPlayerAllDefeated()) {
            System.out.println("모든 포켓몬이 패배했습니다");
        } else if (!enemyMonster.isAlive()) {
            System.out.println("!상대 몬스터를 쓰러뜨렸습니다!");
        }
    }
}