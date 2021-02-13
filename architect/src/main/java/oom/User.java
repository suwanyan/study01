package oom;


public class User {
    private String uid;
    private String uname;
    private String age;

    public User() {
    }

    public User(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

//     重写 eq方法
/*    @Override
    public boolean equals(Object o) {
        boolean response = false;
        if (o instanceof User) {
            response = (((User) o).uid).equals(this.uid);
        }
        return response;
    }*/

    @Override
    public int hashCode() {
        return uid.hashCode();
    }
}
