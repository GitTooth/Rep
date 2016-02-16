package com.company;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonReader;
import java.io.*;
import java.math.BigInteger;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.Timestamp;
import java.util.Date;


public class Main {

    static JsonArray personObject;
    static ArrayList<Message> list = new ArrayList<>();


    public static class Message implements Comparable<Message>{
        String _id;
        String _message;
        String _author;
        String _timestamp;

        Message(String id, String message, String author, String timestamp){
            _id = id;
            _message = message;
            _author = author;
            _timestamp = timestamp;
        }

        @Override public int compareTo(Message k){
            return _timestamp.compareTo(k._timestamp);
        }

        static void sort(ArrayList<Message> list){
            Collections.sort(list);
        }

    }

    static void logOut(String mess){
        try{
            RandomAccessFile raf = new RandomAccessFile("logfile.txt", "rw");
            raf.skipBytes((int)raf.length());
            raf.writeBytes("\r\n" + mess);
            raf.close();
        }catch (IOException e){
            System.out.println("Log out error");
        }
    }

    static void loadMessages(){
        try{
            list.clear();

            Scanner sc = new Scanner(System.in);
            System.out.println("Enter file name:");
            String fileName = sc.nextLine();
            logOut("Loading messages from " + fileName);
            JsonReader reader = Json.createReader(new FileReader(fileName));
            personObject = reader.readArray();


            JsonNumber timeStampArr;

            for (int i = 0; i < personObject.size(); i++){
                timeStampArr = personObject.getJsonObject(i).getJsonNumber("timestamp");
                list.add(new Message(personObject.getJsonObject(i).getString("id"),
                        personObject.getJsonObject(i).getString("message"),
                        personObject.getJsonObject(i).getString("author"),
                        timeStampArr.bigDecimalValue().toString()));
            }



        }catch (IOException e){
            System.out.println("File was not found");
            logOut("    File was not found");
        }
    }

    static void saveMessages(){
        if(list.isEmpty()){
            System.out.println("Nothing to save");
        }

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter file name:");
        String fileName = sc.nextLine();
        logOut("Saving messages in " + fileName);

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
            for(int i = 0; i < list.size(); i++){
                if(i == 0) {
                    bw.write("[{\"id\":\"" + list.get(i)._id + "\",\"author\":\"" + list.get(i)._author
                            + "\",\"timestamp\":" + list.get(i)._timestamp + ",\"message\":\"" + list.get(i)._message + "\"},");
                    continue;
                }

                if(i == list.size() - 1){
                    bw.write("{\"id\":\"" + list.get(i)._id + "\",\"author\":\"" + list.get(i)._author
                            + "\",\"timestamp\":" + list.get(i)._timestamp + ",\"message\":\"" + list.get(i)._message + "\"}]");
                    continue;
                }
                bw.write("{\"id\":\"" + list.get(i)._id + "\",\"author\":\"" + list.get(i)._author
                        + "\",\"timestamp\":" + list.get(i)._timestamp + ",\"message\":\"" + list.get(i)._message + "\"},");

            }

            bw.close();

            /*JsonWriter writer = Json.createWriter(new FileWriter(fileName));
            writer.writeArray(personObject);
            writer.close();*/

        }catch(IOException e){
            System.out.println("Error while saving");
            logOut("    Error while saving");
        }
    }

    static void addMessage(){
        try {
            logOut("Adding message");
            Scanner sc = new Scanner(System.in);

            System.out.println("Author:");
            String author = sc.nextLine();

            System.out.println("Message:");
            String message = sc.nextLine();

            Date date = new Date();
            Timestamp timestamp = new Timestamp(date.getTime());
            Long t = timestamp.getTime();

            list.add(new Message(UUID.randomUUID().toString(), message, author, t.toString()));

        /*JsonValue jsonObject = Json.createObjectBuilder()
                .add("id", UUID.randomUUID().toString())
                .add("message", message)
                .add("author", author)
                .add("timestamp", t)
                .build();
        personObject.add(jsonObject);*/

        }catch (Exception e){
            System.out.println("Error while adding message");
            logOut("    Error while adding message");
        }



    }


    static void showMessages(){
        Message.sort(list);
        logOut("Showing messages");
        if(list.isEmpty()){
            System.out.println("Nothing to show");
            logOut("    Nothing to show");
        }
        for (Message aList : list) {
            System.out.println("Author: " + aList._author + "\n"
                    + "ID: " + aList._id + "\n"
                    + "Timestamp: " + aList._timestamp + "\n"
                    + "Message: " + aList._message + "\n");
        }
        System.out.println("----------------------------------------------------------" + "\n");



    }

    static void deleteMessage(){
        logOut("Deleting message");
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter index: ");
            int i = sc.nextInt();
            i = i - 1;
            list.remove(i);
        }catch (Exception e){
            System.out.println("Error while deleting");
            logOut("    Error while deleting");
        }

    }

    static void findByAuthor(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Author name: ");
        String str = sc.nextLine();
        logOut("Searching message, written by " + str);

        boolean t = false;

        for (Message aList : list) {
            if (str.compareTo(aList._author) == 0) {
                System.out.println("Author: " + aList._author + "\n"
                        + "ID: " + aList._id + "\n"
                        + "Timestamp: " + aList._timestamp + "\n"
                        + "Message: " + aList._message + "\n");
                t = true;

            }
        }

        if(!t) {
            System.out.println("Nothing found");
            logOut("    Nothing found");
        }
    }

    static void findByToken(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Token: ");
        String str = sc.nextLine();
        logOut("Searching message with token " + str);

        boolean t = false;
        for (Message aList : list) {
            if (str.compareTo(aList._author) == 0 || str.compareTo(aList._timestamp) == 0 ||
                    str.compareTo(aList._id) == 0 || str.compareTo(aList._message) == 0) {

                System.out.println("Author: " + aList._author + "\n"
                        + "ID: " + aList._id + "\n"
                        + "Timestamp: " + aList._timestamp + "\n"
                        + "Message: " + aList._message + "\n");
                t = true;
            }
        }

        if(!t) {
            System.out.println("Nothing found");
        }logOut("   Nothing found");
    }

    static void periodShow(){
        Scanner sc = new Scanner(System.in);
        BigInteger l = BigInteger.valueOf(0);
        BigInteger r = BigInteger.valueOf(0);

        logOut("Showing messages from " + l + "to " + r);

        while(l.toString().length() != 13 && r.toString().length() != 13) {
            System.out.println("Enter left border:");
            l = sc.nextBigInteger();
            System.out.println("Enter right border:");
            r = sc.nextBigInteger();

            if (l.toString().length() != 13 && r.toString().length() != 13) {
                System.out.println("Wrong enter, try again");
            }
        }

        boolean t = false;

        for (Message aList : list) {
            if ((l.compareTo(new BigInteger(aList._timestamp)) == -1 ||
                    l.compareTo(new BigInteger(aList._timestamp)) == 0) &&
                    (r.compareTo(new BigInteger(aList._timestamp)) == 1 ||
                            r.compareTo(new BigInteger(aList._timestamp)) == 0)) {

                System.out.println("Author: " + aList._author + "\n"
                        + "ID: " + aList._id + "\n"
                        + "Timestamp: " + aList._timestamp + "\n"
                        + "Message: " + aList._message + "\n");
                t = true;
            }
        }

        if(!t) {
            System.out.println("Nothing found");
            logOut("    Nothing found");
        }
    }

    static void findByRE(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter regular expression:");
        String str = sc.nextLine();

        logOut("Searching, using regular expression");

        boolean t = false;

        Pattern pattern = Pattern.compile(str);
        for (Message aList : list) {
            Matcher matcher = pattern.matcher(aList._message);
            if (matcher.matches()) {
                System.out.println("Author: " + aList._author + "\n"
                        + "ID: " + aList._id + "\n"
                        + "Timestamp: " + aList._timestamp + "\n"
                        + "Message: " + aList._message + "\n");
                t = true;
            }
        }

        if(!t) {
            System.out.println("Nothing found");
            logOut("    Nothing found");
        }

    }

    public static void main(String[] args) {

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
                        loadMessages();
                        break;

                    case 2:
                        saveMessages();
                        break;

                    case 3:
                        addMessage();
                        break;

                    case 4:
                        showMessages();
                        break;

                    case 5:
                        findByAuthor();
                        break;

                    case 6:
                        findByToken();
                        break;

                    case 7:
                        periodShow();
                        break;

                    case 8:
                        findByRE();
                        break;

                    case 9:
                        deleteMessage();
                        break;

                    case 10:
                        System.out.print("1 - load messages\n2 - save messages\n3 - add message\n" +
                                "4 - show messages\n5 - find by author\n" +
                                "6 - find by token\n7 - show by period\n8 - find, using regular expression\n" +
                                "9 - delete message by index\n10 - help\n11 - exit\n");
                        logOut("Help");
                        break;

                    case 11:
                        logOut("Exit\r\n------------------------------------------------------------");
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
