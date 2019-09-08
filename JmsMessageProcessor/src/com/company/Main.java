package com.company;

import javax.naming.InitialContext;
import javax.jms.Topic;

public class Main {

    public static void main(String[] args) {
        System.out.printf("Test");
        try {
            InitialContext ctx = new InitialContext();
            Topic topic = (Topic) ctx.lookup("Message/AB");
        } catch (Exception e) {
            System.out.printf("Error: %s",e.getMessage());
        }


    }
}
