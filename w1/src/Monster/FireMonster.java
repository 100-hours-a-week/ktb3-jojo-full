package Monster;


import Skill.AttackSkill;
import Skill.DefenseSkill;

public class FireMonster extends ElementalMonster {

    public FireMonster(String name, int mp, int defensePower) {
        super(name, mp, defensePower);
        this.elementalType = "fire";
        this.attackSkills.add(new AttackSkill("화염 방사기", 50));
        this.defenseSkills.add(new DefenseSkill("방화벽", 50));
    }
}