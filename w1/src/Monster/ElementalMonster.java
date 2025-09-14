package Monster;

import Skill.AttackSkill;
import Skill.DefenseSkill;
import Skill.Skill;


import java.util.List;
import java.util.Scanner;

public class ElementalMonster extends Monster {
//    String type; //'fire'|'water'|'ground'
    private Scanner scanner;

    public ElementalMonster(String name, int hp, int mp, int defensePower) {
        super(name, hp, mp, defensePower);
        this.scanner = new Scanner(System.in);
    }

    //methods
    //아래는 추상 클래스에 ..?
    public boolean isWinWithTarget(Monster target){
        //fire < water, water < ground, ground < fire 관계
        //상성 이기는 지를 반환
        String targetType = target.type;
        boolean isWin = false;
        //이기는지만 체크
        if (this.type.equals("fire") && targetType.equals("ground")) {
            isWin = true;
        }
        if (this.type.equals("water") && targetType.equals("fire")) {
            isWin = true;
        }
        if (this.type.equals("groud") && targetType.equals("water")) {
            isWin = true;
        }
        return isWin;
    }


    public boolean attack(Monster target, int skillIdx){
        double damageMultiplier = this.isWinWithTarget(target) ? 1.3 : 0.7;

        Skill usedSkill = this.attackSkills.get(skillIdx);
        int damage = (int) (usedSkill.power * damageMultiplier);

        if (this.mp-damage / 2 < 0){
            return false;
        }

        target.takeDamage(damage);


        this.mp -= damage / 2;
        return true;
    };

    public boolean defense(Monster target, int skillIdx){
        double damageMultiplier = this.isWinWithTarget(target) ? 1.3 : 0.7;
        Skill usedSkill = this.defenseSkills.get(skillIdx);

        int defense = (int) (usedSkill.power * damageMultiplier);
        if (this.defensePower-defense / 2 < 0){
            return false;
        }

        this.defensePower -= defense / 2;
        this.setDefending(true);
        return true;
    };
}

