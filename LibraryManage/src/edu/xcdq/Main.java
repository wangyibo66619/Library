package edu.xcdq;

import java.text.ParseException;

public class Main {

    public static void main(String[] args) throws ParseException {
        LibraryManage libraryManage = new LibraryManage();
        libraryManage.initial();
        libraryManage.startMenu();
    }
}
