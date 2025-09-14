package Skill;

//skill 은 변경 불가
//TODO: record class 란 ?
abstract public class Skill { //Elemental type 상관없이 공통으로 공유하는 skill
    public final String name; //스킬 이름
    public final int power; //스킬의 공결결 - 사용 시 이만큼의 mp 가 깎이거나, 방어 가능

    public Skill(String name, int power) {
        this.name = name;
        this.power = power;
    }
}
