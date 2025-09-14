package Monster;


import Skill.AttackSkill;
import Skill.DefenseSkill;

public class FireMonster extends ElementalMonster {

    public FireMonster(String name, int hp, int mp, int defensePower) {
        super(name, hp, mp, defensePower);
        this.type = "fire";
        this.attackSkills.add(new AttackSkill("화염 방사기", 50));
        this.attackSkills.add(new AttackSkill("화염벽", 70));
        this.defenseSkills.add(new DefenseSkill("방화벽", 50));
    }
}