package Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.*;

import Monster.*;
import Player.Player;
import Skill.AttackSkill;
import Skill.DefenseSkill;

public class Game {
    private Player player;
    private Scanner scanner;
    private Random random;
    private List<ElementalMonster> allMonsters; // 모든 몬스터 리스트
    private View view;
    //thread 실행
    private ExecutorService timerExecutor;
    private ExecutorService inputExecutor;
    //공유 필드
    private volatile boolean timeout;
    private volatile int turnId;
    //future 필드
    private Future<Boolean> timerFuture;
    private Future<Integer> inputFuture;


    public Game() {
        this.scanner = new Scanner(System.in);
        this.random = new Random();
        this.view = new View();
        this.timeout = false;
        this.turnId = 0;

        this.player = new Player();
        this.allMonsters = new ArrayList<>(10);
        allMonsters.add(new FireMonster("파이리", 100, 110, 100));
        allMonsters.add(new WaterMonster("꼬부기", 110, 90, 70));
        allMonsters.add(new GroundMonster("디그다", 120, 80, 50));

        this.inputExecutor = Executors.newSingleThreadExecutor();
        this.timerExecutor = Executors.newSingleThreadExecutor();
    }



    public boolean isTimeout() {
        return timeout;
    }

//    public boolean isTurnOpen() {
//        return turnOpen;
//    }

    public int getTurnId() {
        return turnId;
    }

    public void setTimeout(boolean timeout) {
        this.timeout = timeout;
    }
//    public void setTurnOpen(boolean turnOpen) {
//        this.turnOpen = turnOpen;
//    }


    public void startGame() throws ExecutionException, InterruptedException {
        view.printStartGameMessage();

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
                view.printSelectedNumberError();
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
            view.printNoMonstersToSwitch();
            return;
        }

        view.printMonsterToSelect();

        for (int i = 0; i < candidateMonsters.size(); i++) {
            if (candidateMonsters.get(i) != currentMonster) {
                System.out.println((i + 1) + ". " + candidateMonsters.get(i).name);
            }
        }
        int choice = scanner.nextInt() - 1;

        boolean isSwitched = player.switchMonster(choice);

        if (!isSwitched){
            view.printBasicErrorMessage();
            return;
        }

        view.printSwitchedSuccessfully();
    }

    private void battle(ElementalMonster enemyMonster) throws ExecutionException, InterruptedException {
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

            view.displayStatus(enemyMonster, this.player);
            handlePlayerTurn(enemyMonster);


            if (enemyMonster.isAlive()) {
                view.displayStatus(enemyMonster, this.player);
                handleEnemyTurn(enemyMonster);
            }
        }
        endGame(enemyMonster);
    }

    private void startTurn() {
        turnId++;
        timeout = false;
    }

    private void startMultiThread(){
        //타이머 실행
        TurnTimer runTimer = new TurnTimer(this, this.turnId);
        this.timerFuture = timerExecutor.submit(runTimer);

        //input 도 따로 스레드를
        this.inputFuture = inputExecutor.submit(() -> {
            int choice = scanner.nextInt() - 1; // 1-based → 0-based
            return choice;
        });
    }


    private void handlePlayerTurn(ElementalMonster enemyMonster) throws ExecutionException, InterruptedException {
        //init 시 timeOut 초기화 및 상대 동작 정리
        this.startTurn();
        int enemyChoice = random.nextInt(2); // 0: 방어, 1: 대기
        if (enemyChoice == 0) {
            Monster currentMonster = player.getCurrentMonster();
            enemyMonster.defense(currentMonster, 0);
        }

        Monster currentMonster = player.getCurrentMonster();
        List<AttackSkill> skills = currentMonster.getAttackSkills();

        System.out.println("사용할 스킬을 선택하세요:");
        for (int i = 0; i < skills.size(); i++) {
            System.out.println((i + 1) + ". " + skills.get(i).name+ " / [power] "+ skills.get(i).power);
        }
        System.out.print("스킬 번호: ");

        //이때부터 스레드 시작 (input + timer 동시)

        this.startMultiThread();
        Integer skillChoice = null;

        //input 에도 타이머를 설정
        try{
            skillChoice = inputFuture.get(10, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            System.out.println("!타임아웃!");
            this.setTimeout(true);
            timerFuture.cancel(true);
            return;
        }



        if (0 <= skillChoice && skillChoice < skills.size()){
            this.setTimeout(true);
            timerFuture.cancel(true);
        }

        boolean isSuccess = currentMonster.attack(enemyMonster, skillChoice);
        if (!isSuccess){  System.out.println("MP가 부족하여 스킬을 사용할 수 없습니다.");}
    }

    private void handleEnemyTurn(ElementalMonster enemyMonster) throws ExecutionException, InterruptedException {
        this.startTurn();
        int enemyChoice = random.nextInt(2); // 0: 공격, 1: 대기

        Monster currentMonster = player.getCurrentMonster();
        List<DefenseSkill> skills = currentMonster.getDefenseSkills();

        System.out.println("사용할 스킬을 선택하세요:");
        for (int i = 0; i < skills.size(); i++) {
            System.out.println((i + 1) + ". " + skills.get(i).name+ " / [power] "+ skills.get(i).power);
        }
        System.out.print("스킬 번호: ");

        this.startMultiThread();

        Integer skillChoice = null;

        //input 에도 타이머를 설정
        try{
            skillChoice = inputFuture.get(10, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            System.out.println("!타임아웃!");
        } finally {
            this.setTimeout(true);
            timerFuture.cancel(true);
        }

        if( skillChoice != null && 0 <= skillChoice && skillChoice < skills.size()){
            enemyMonster.defense(currentMonster, skillChoice);
        }


        if (enemyChoice == 0) {
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