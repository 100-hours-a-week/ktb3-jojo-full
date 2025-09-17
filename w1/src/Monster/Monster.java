package Monster;

import Skill.AttackSkill;
import Skill.DefenseSkill;

import java.util.ArrayList;
import java.util.List;

//직접 생성 안함
abstract public class Monster {
    public final String name;
    int hp; //체력 - 떨엉지면 죽음
    int mp; //스킬 사용 시 스킬의 공격력만큼 감소
    int defensePower;
    boolean isDefending;
    List<AttackSkill> attackSkills;
    List<DefenseSkill> defenseSkills;

    public Monster(String name, int hp, int mp, int defensePower) {
        this.name = name;
        this.hp = hp;
        this.mp = mp;
        this.defensePower = defensePower;
        this.attackSkills = new ArrayList<>(2);
        this.defenseSkills = new ArrayList<>(1);
        this.isDefending = false;
        //TODO: 이 부분 좀 더 고민해보기
    }


    //getter
    public int getHp(){ return this.hp;}
    public int getMp(){ return this.mp;}
    public int getDefensePower(){ return this.defensePower;}
    public List<AttackSkill> getAttackSkills(){ return this.attackSkills;}
    public List<DefenseSkill> getDefenseSkills(){ return this.defenseSkills;}


    //method
    public void takeDamage(int damage){
        if (!isDefending){
            this.hp -= damage;
        }
        this.setDefending(false);
    };

    public void setDefending(boolean state){
        this.isDefending = state;
    }

    public boolean isAlive(){
        return this.hp > 0;
    };

    public abstract boolean attack(Monster target, int skillIdx);
    public abstract boolean defense(Monster target, int skillIdx);
}
