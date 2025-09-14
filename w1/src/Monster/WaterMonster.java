package Monster;

import Skill.AttackSkill;
import Skill.DefenseSkill;

public class WaterMonster extends ElementalMonster{
    public WaterMonster(String name, int hp, int mp, int defensePower) {
        super(name, hp, mp, defensePower);
        this.type = "water";
        this.attackSkills.add(new AttackSkill("물대포", 50));
        this.attackSkills.add(new AttackSkill("해일", 70));
        this.defenseSkills.add(new DefenseSkill("워터월", 50));
    }
}
