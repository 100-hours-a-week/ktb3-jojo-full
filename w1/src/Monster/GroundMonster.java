package Monster;

import Skill.AttackSkill;
import Skill.DefenseSkill;

public class GroundMonster extends ElementalMonster{

    public GroundMonster(String name, int hp, int mp, int defensePower) {
        super(name, hp, mp, defensePower);
        this.type = "ground";
        this.attackSkills.add(new AttackSkill("흙먹기", 50));
        this.attackSkills.add(new AttackSkill("모래먼지", 70));
        this.defenseSkills.add(new DefenseSkill("모래의 힘", 50));
    }
}
