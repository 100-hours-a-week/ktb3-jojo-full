package Monster;

import Skill.AttackSkill;
import Skill.DefenseSkill;

import java.util.ArrayList;
import java.util.List;

//직접 생성 안함
abstract public class Monster {
    String name;
    int hp; //체력 - 떨엉지면 죽음
    int mp; //스킬 사용 시 스킬의 공격력만큼 감소
    int defensePower;
    List<AttackSkill> attackSkills;
    List<DefenseSkill> defenseSkills;

    public Monster(String name, int mp, int defensePower
//            , List<AttackSkill> attackSkills, List<DefenseSkill> defenseSkills
    ) {
        this.name = name;
        this.hp = 1000;
        this.mp = mp;
        this.defensePower = defensePower;
        this.attackSkills = new ArrayList<>(2);
        this.defenseSkills = new ArrayList<>(1);
        //TODO: 이 부분 좀 더 고민해보기
//        Skill defenseSkill = new Skill("defenseSkill", 100, "defense");
//        Skill attackSkill = new Skill("attackSkill", 100, "defense");
//        skills.add(defenseSkill);
//        skills.add(attackSkill);
    }

    //method
    void takeDamage(int damage){
        this.hp -= damage;
    };

    boolean isAlive(){
        return this.hp > 0;
    };

    public abstract boolean attack(ElementalMonster target, int skillIdx);
    public abstract boolean defense(ElementalMonster target, int skillIdx);

}
