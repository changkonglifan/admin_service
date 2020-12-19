package com.xuyang.admin.utils;

/**
 * @Author: XuYang
 * @Date: 2020/8/17 11:00
 * @Description:
 */

enum S {GROUND, CRAWLING, HANGING}
class Rock {
    Rock(){
        System.out.println("-----Rock");
    }
}
public class TestEnum {
    public static void main(String args[]){
        new Rock();
        for(S s : S.values()){
            System.out.println(s+"ordinal:" + s.ordinal());
            System.out.println(s+"compareTo:" + s.compareTo(S.GROUND));
            System.out.println(s.equals(S.CRAWLING));
            System.out.println(s.getDeclaringClass());
            System.out.println(s.name());
            System.out.println("----------------------------------");
        }
    }

}