package com.company;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonReader;
import java.io.*;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Methods {
    JsonArray personObject;
    ArrayList<Message> list = new ArrayList<>();

    void logOut(String mess) {
        try {
            RandomAccessFile raf = new RandomAccessFile("logfile.txt", "rw");
            raf.skipBytes((int) raf.length());
            raf.writeBytes("\r\n" + mess);
            raf.close();
        } catch (IOException e) {
            System.out.println("Log out error");
        }
    }

    void loadMessages() {
        try {
            list.clear();

            Scanner sc = new Scanner(System.in);
            System.out.println("Enter file name:");
            String fileName = sc.nextLine();
            logOut("Loading messages from " + fileName);
            JsonReader reader = Json.createReader(new FileReader(fileName));
            personObject = reader.readArray();

            JsonNumber timeStampArr;

            for (int i = 0; i < personObject.size(); i++) {
                timeStampArr = personObject.getJsonObject(i).getJsonNumber("timestamp");
                list.add(new Message(personObject.getJsonObject(i).getString("id"),
                        personObject.getJsonObject(i).getString("message"),
                        personObject.getJsonObject(i).getString("author"),
                        timeStampArr.bigDecimalValue().toString()));
            }


        } catch (IOException e) {
            System.out.println("File was not found");
            logOut("    File was not found");
        }
    }

    void saveMessages() {
        if (list.isEmpty()) {
            System.out.println("Nothing to save");
        }

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter file name:");
        String fileName = sc.nextLine();
        logOut("Saving messages in " + fileName);

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
            for (int i = 0; i < list.size(); i++) {
                if (i == 0) {
                    bw.write("[{\"id\":\"" + list.get(i)._id + "\",\"author\":\"" + list.get(i)._author
                            + "\",\"timestamp\":" + list.get(i)._timestamp + ",\"message\":\"" + list.get(i)._message + "\"},");
                    continue;
                }

                if (i == list.size() - 1) {
                    bw.write("{\"id\":\"" + list.get(i)._id + "\",\"author\":\"" + list.get(i)._author
                            + "\",\"timestamp\":" + list.get(i)._timestamp + ",\"message\":\"" + list.get(i)._message + "\"}]");
                    continue;
                }
                bw.write("{\"id\":\"" + list.get(i)._id + "\",\"author\":\"" + list.get(i)._author
                        + "\",\"timestamp\":" + list.get(i)._timestamp + ",\"message\":\"" + list.get(i)._message + "\"},");

            }

            bw.close();

        } catch (IOException e) {
            System.out.println("Error while saving");
            logOut("    Error while saving");
        }
    }

    void addMessage() {
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


        } catch (Exception e) {
            System.out.println("Error while adding message");
            logOut("    Error while adding message");
        }


    }


    void showMessages() {
        //Message.sort(list);
        logOut("Showing messages");
        if (list.isEmpty()) {
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

    void deleteMessage() {
        logOut("Deleting message");
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter index: ");
            int i = sc.nextInt();
            i = i - 1;
            list.remove(i);
        } catch (Exception e) {
            System.out.println("Error while deleting");
            logOut("    Error while deleting");
        }

    }

    void findByAuthor() {
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

        if (!t) {
            System.out.println("Nothing found");
            logOut("    Nothing found");
        }
    }

    void findByToken() {
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

        if (!t) {
            System.out.println("Nothing found");
        }
        logOut("   Nothing found");
    }

    void periodShow() {
        Scanner sc = new Scanner(System.in);
        BigInteger l = BigInteger.valueOf(0);
        BigInteger r = BigInteger.valueOf(0);

        logOut("Showing messages from " + l + "to " + r);

        while (l.toString().length() != 13 && r.toString().length() != 13) {
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

        if (!t) {
            System.out.println("Nothing found");
            logOut("    Nothing found");
        }
    }

    void findByRE() {
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

        if (!t) {
            System.out.println("Nothing found");
            logOut("    Nothing found");
        }

    }
}
