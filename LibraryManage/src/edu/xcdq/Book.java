package edu.xcdq;

import java.util.Date;

/**
 * @author 王艺博
 * @date 2021/4/9 8:06
 */
public class Book {
    public String name;// 图书名字
    public boolean flag;// 如果flag为true图书状态为可借，flag为false时书已被借出
    public String state;// 状态（借出/未借出）
    public String date;// 借出日期
    public int count;// 借出次数

    public void print(int index) {
        if ( flag) {
            state = "可借  ";
        }else {
            state = "已借出";
        }
        System.out.println(index + "\t   " + state + "\t" + name + "\t" + date);
    }

    public void bookSet(String mingzi,boolean zhuangtai,String riqi,int cishu) {
        name = mingzi;
        flag = zhuangtai;
        date = riqi;
        count = cishu;
    }
}
