package Monster;
import Skill.Skill;

public class ElementalMonster extends Monster {
    String ElementalType;

    public ElementalMonster(String name, int hp, int mp, int defensePower) {
        super(name, hp, mp, defensePower);
    }

    //methods
    //아래는 추상 클래스에 ..?
    public boolean isWinWithTarget(ElementalMonster target){
        //fire < water, water < ground, ground < fire 관계
        //상성 이기는 지를 반환
        //이건 같은 상성일 때만 해당됨

        String targetType = target.ElementalType;
        boolean isWin = false;
        //이기는지만 체크
        if (this.ElementalType.equals("fire") && targetType.equals("ground")) {
            isWin = true;
        }
        if (this.ElementalType.equals("water") && targetType.equals("fire")) {
            isWin = true;
        }
        if (this.ElementalType.equals("groud") && targetType.equals("water")) {
            isWin = true;
        }
        return isWin;
    }

    @Override
    public boolean attack(Monster target, int skillIdx){
        double damageMultiplier = 1;
        //다운캐스팅
        if (target instanceof ElementalMonster monster){
            //같은 타입이므로 다운캐스팅
            damageMultiplier = this.isWinWithTarget(monster) ? 1.3 : 0.7;
        }


        Skill usedSkill = this.attackSkills.get(skillIdx);
        int damage = (int) (usedSkill.power * damageMultiplier);

        if (this.mp-damage / 2 < 0){
            return false;
        }

        target.takeDamage(damage);


        this.mp -= damage / 2;
        return true;
    };

    @Override
    public boolean defense(Monster target, int skillIdx){
        double damageMultiplier = 1;

        //다운캐스팅
        if (target instanceof ElementalMonster monster){
            //같은 타입이므로 다운캐스팅
            damageMultiplier = this.isWinWithTarget(monster) ? 1.3 : 0.7;
        }


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

