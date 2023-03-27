package Seminar_1.Task_2;

public class Member {
    private String name;
    private Role role;

    public enum Role {
        MEMBER, ADMINISTRATOR, OWNER
    }

    public Member(String name, Role role) {
        setName(name);
        setRole(role);

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Role getRole() {
        return this.role;
    }
}
