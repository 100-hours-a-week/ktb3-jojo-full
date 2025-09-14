package Monster;

import Skill.AttackSkill;
import Skill.DefenseSkill;

public class WaterMonster extends ElementalMonster{
    public WaterMonster(String name, int mp, int defensePower) {
        super(name, mp, defensePower);
        this.elementalType = "water";
        this.attackSkills.add(new AttackSkill("물대포", 50));
        this.defenseSkills.add(new DefenseSkill("워터월", 50));
    }
}
