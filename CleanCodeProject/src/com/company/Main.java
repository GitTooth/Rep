package com.company;

import java.util.*;

public class Main {


    public static void main(String[] args) {
        Methods mt = new Methods();

        Scanner sc = new Scanner(System.in);
        System.out.print("1 - load messages\n2 - save messages\n3 - add message\n4 - show messages\n5 - find by author\n" +
                "6 - find by token\n7 - show by period\n8 - find, using regular expression\n9 - delete message by index\n10 - help\n" +
                "11 - exit\n");
        int s;

        try {
            while(true) {

                s = sc.nextInt();

                switch (s) {
                    case 1:
                        mt.loadMessages();
                        break;

                    case 2:
                        mt.saveMessages();
                        break;

                    case 3:
                        mt.addMessage();
                        break;

                    case 4:
                        mt.showMessages();
                        break;

                    case 5:
                        mt.findByAuthor();
                        break;

                    case 6:
                        mt.findByToken();
                        break;

                    case 7:
                        mt.periodShow();
                        break;

                    case 8:
                        mt.findByRE();
                        break;

                    case 9:
                        mt.deleteMessage();
                        break;

                    case 10:
                        System.out.print("1 - load messages\n2 - save messages\n3 - add message\n" +
                                "4 - show messages\n5 - find by author\n" +
                                "6 - find by token\n7 - show by period\n8 - find, using regular expression\n" +
                                "9 - delete message by index\n10 - help\n11 - exit\n");
                        mt.logOut("Help");
                        break;

                    case 11:
                        mt.logOut("Exit\r\n------------------------------------------------------------");
                        break;

                }
                if (s == 11)
                    break;
            }
        }catch (Exception e){
            System.out.println("Wrong enter");
        }

    }
}
