package com.example.exam.DTO;

public class MemberForm2 {

    private String name = "손흥민";
    private String id = "son";
    private int age = 30;
    private String addr = "서울";
    private String mobile = "010-1234-5678";

    public MemberForm2() {
    }

    public MemberForm2(String name, String id, int age, String addr, String mobile) {
        this.name = name;
        this.id = id;
        this.age = age;
        this.addr = addr;
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "MemberForm2{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", age=" + age +
                ", addr='" + addr + '\'' +
                ", mobile='" + mobile + '\'' +
                '}';
    }
}
