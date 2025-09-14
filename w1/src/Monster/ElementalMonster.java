package Monster;

import Skill.AttackSkill;
import Skill.DefenseSkill;
import Skill.Skill;


import java.util.List;

public class ElementalMonster extends Monster {
    String elementalType; //'fire'|'water'|'ground'

    public ElementalMonster(String name, int mp, int defensePower) {
        super(name, mp, defensePower);
    }

    //methods
    //아래는 추상 클래스에 ..?
    public boolean isWinWithTarget(ElementalMonster target){
        //fire < water, water < ground, ground < fire 관계
        //상성 이기는 지를 반환
        String targetType = target.elementalType;
        boolean isWin = false;
        //이기는지만 체크
        if (this.elementalType.equals("fire") && targetType.equals("ground")) {
            isWin = true;
        }
        if (this.elementalType.equals("water") && targetType.equals("fire")) {
            isWin = true;
        }
        if (this.elementalType.equals("groud") && targetType.equals("water")) {
            isWin = true;
        }
        return isWin;
    }


    public boolean attack(ElementalMonster target, int skillIdx){
        double damageMultiplier = this.isWinWithTarget(target) ? 1.3 : 0.7;
        Skill usedSkill = this.attackSkills.get(skillIdx);
        int damage = (int) (usedSkill.power * damageMultiplier);

        if (this.mp-damage / 2 < 0){
            return false;
        }

        this.mp -= damage / 2;
        return true;

        //TODO:외부에서 target.takeDamage 처리
    };

    public boolean defense(ElementalMonster target, int skillIdx){
        double damageMultiplier = this.isWinWithTarget(target) ? 1.3 : 0.7;
        Skill usedSkill = this.defenseSkills.get(skillIdx);
        int defense = (int) (usedSkill.power * damageMultiplier);
        if (this.defensePower-defense / 2 < 0){
            return false;
        }

        this.defensePower -= defense / 2;
        return true;
    };
}

