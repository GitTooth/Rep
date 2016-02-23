package com.company;

public class Message{
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

        /*@Override public int compareTo(Message k){
            return _timestamp.compareTo(k._timestamp);
        }

        static void sort(ArrayList<Message> list){
            Collections.sort(list);
        }*/

}
