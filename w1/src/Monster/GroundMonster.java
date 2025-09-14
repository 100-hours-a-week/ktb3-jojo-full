package Monster;

import Skill.AttackSkill;
import Skill.DefenseSkill;

public class GroundMonster extends ElementalMonster{

    public GroundMonster(String name, int mp, int defensePower) {
        super(name, mp, defensePower);
        this.elementalType = "ground";
        this.attackSkills.add(new AttackSkill("흙먹기", 50));
        this.defenseSkills.add(new DefenseSkill("모래의 힘", 50));
    }
}
