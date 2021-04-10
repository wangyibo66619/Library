package edu.xcdq;

import com.sun.deploy.security.AbstractBrowserAuthenticator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

/**
 * @author 王艺博
 * @date 2021/4/9 8:07
 */
// 图书管理类
public class LibraryManage {
    Scanner scanner = new Scanner(System.in);
    BookSet bookSet = new BookSet();
    //这一行代码确立了转换的格式，其中 yyyy 是完整的公元年，MM 是月份，dd 是日期
    SimpleDateFormat outBookDate = new SimpleDateFormat("yyyy-MM-dd");
    // 初始化仓库
    public void initial() {
        Book java = new Book();// new一个图书对象
        java.bookSet("java基础教程",false,"2015-7-1",1);// 用bookSet方法给java对象传入数据

        Book mysql = new Book();
        mysql.bookSet("数据库技术",true,"",0);


        Book renYue = new Book();
        renYue.bookSet("人月神话",true,"",0);

        // 把图书信息对象传入图书仓库内
        bookSet.book[0] = java;
        bookSet.book[1] = mysql;
        bookSet.book[2] = renYue;
    }

    // 开始菜单
    public void startMenu() throws ParseException {
        boolean flag1 = true;
        int choose;
        int fanhui;
        stopStartMenu:do {
            System.out.println("欢迎使用图书管理系统");
            System.out.println("——————————————————————————————————————————");
            System.out.println("1. 新增图书");
            System.out.println("2. 查看图书");
            System.out.println("3. 删除图书");
            System.out.println("4. 借出图书");
            System.out.println("5. 归还图书");
            System.out.println("6. 退   出");
            System.out.println("请选择：");
            choose = scanner.nextInt();
            switch (choose) {
                case 1:
                    System.out.println("---> 新增图书");
                    add();
                    break;
                case 2:
                    System.out.println("---> 查看图书");
                    chakan();
                    break;
                case 3:
                    System.out.println("---> 删除图书");
                    delete();
                    break;
                case 4:
                    System.out.println("---> 借出图书");
                    out();
                    break;
                case 5:
                    System.out.println("---> 归还图书");
                    returnBook();
                    break;
                case 6:
                    System.out.println("---> 退   出");
                    break stopStartMenu;
            }
            System.out.print("输入0返回：");
            fanhui = scanner.nextInt();


        }while( fanhui == 0 ) ;
    }

    // 新增图书
    private void add() {
        System.out.print("请输入图书名称：");
        String name = scanner.next();
        boolean flagDelete = true;
        int count = 0;
        String date = "";
        
        Book newBook = new Book();
        newBook.bookSet(name,flagDelete,date,count);
        // 循环找到第一个不为null的对象类数组，并给他存入这个新增的对象，然后结束循环
        for (int i = 0; i < bookSet.book.length; i++) {
            if ( bookSet.book[i] == null ) {
                bookSet.book[i] = newBook;
                break;
            }
        }
        System.out.println("增加" + newBook.name + "成功");
    }
    // 查看图书
    private void chakan() {
        System.out.println("序号 \t状 态 \t  名称  \t\t借出日期");
        // 循环找到并打印不为null的对象
        for (int i = 0; i < bookSet.book.length; i++) {
            if ( bookSet.book[i] != null) {
                bookSet.book[i].print( i+1 );
            }
        }
        System.out.println("***********************************");
    }
    // 删除图书
    private void delete() {
        System.out.println("请输入你要删除图书的名称：");
        String deleteBookName = scanner.next();
        // 循环找到要删除图书的名称
        for (int i = 0; i < bookSet.book.length; i++) {
            if ( bookSet.book[i] != null ) {
                if ( deleteBookName.equals(bookSet.book[i].name) ) {
                    if (bookSet.book[i].flag ) {
                        int j = i;
                        while ( bookSet.book[j+1] != null ) {// 循环把要删除的图书后面的每一位不为null的对象的属性给到前一位
                            bookSet.book[j] = bookSet.book[j+1];
                            j++;
                        }
                        /*
                        这时bookSst.book[j]已经是除去null最后一个有值的对象，前一位对象的属性与当前对象的属性相同，
                        这个对象就多余了，所以把他的值为null
                         */
                        bookSet.book[j] = null;
                        System.out.println("删除成功");
                        break;
                    }else {
                        System.out.println("已借出的书不能删除");
                    }
                }else {
                    System.out.println("图书馆不存在此书");
                    break;
                }

            }

        }


        }
    // 借出图书
    private void out(){
        System.out.print("请输入图书名称：");
        String bookName = scanner.next();
        String outDate;// 借出图书日期
        // 循环找到要借的书的名字，并让用户输入借出的日期，然后用bookSet方法修改图书的状态和借出次数
        for (int i = 0; i < bookSet.book.length; i++) {
            if ( bookSet.book[i] !=null && bookName.equals(bookSet.book[i].name) ) {
                if ( bookSet.book[i].flag ) {
                    System.out.print("请输入借出日期(年-月-日)：");
                    outDate = scanner.next();
                    bookSet.book[i].count = bookSet.book[i].count + 1;

                    bookSet.book[i].bookSet(bookName,false,outDate,bookSet.book[i].count);
                    System.out.println("借出成功");
                    break;
                }else {
                    System.out.println("此书已被借走");
                }

            }
        }
    }
    // 归还图书
    private void returnBook() throws ParseException{
        System.out.print("请输入图书名称：");
        String returnBook = scanner.next();
        String guihuanBookDate;// 归还日期
        /*
            循环找到归还图书名称是否与图书馆内图书名字是否相同，并查询此图书状态（借出/ 未借出）,
            如果借出那么就让用户输入归还日期,并计算应付金额（1天/1元）
         */

        for (int i = 0; i < bookSet.book.length; i++) {

            if ( returnBook.equals(bookSet.book[i].name) ) {
                if ( bookSet.book[i].flag ) {
                    System.out.println(bookSet.book[i].name + "未借出");
                }else {
                    System.out.println("请输入归还日期（年/月/日）：");
                    guihuanBookDate = scanner.next();
                    System.out.println("归还《" + bookSet.book[i].name + "》成功！");
                    bookSet.book[i].flag = true;
                    System.out.println("借出日期为：" + bookSet.book[i].date);
                    System.out.println("归还日期为：" + guihuanBookDate);

                    Date d1 = outBookDate.parse(bookSet.book[i].date);
                    Date d2 = outBookDate.parse(guihuanBookDate);
                    //使用 getTime() 方法获取两个日期（自1970年1月1日经历的毫秒数值），然后比较这两个值。
                    long daysBetween = (d2.getTime()-d1.getTime()) / (24*60*60*1000);

                    System.out.println("应付租金（元）" + daysBetween + "元");
                    break;
                }

            }else {
                System.out.println("图书馆不存在此书");
                break;
            }
        }

    }

}
