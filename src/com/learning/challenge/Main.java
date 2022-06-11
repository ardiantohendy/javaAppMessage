package com.learning.challenge;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static ArrayList<Contact> contacts;
    private static Scanner scanner;
    private static String name;
    private static int id = 0;

    public static void main(String[] args) {
        System.out.println("Plese enter your username for have access to message:");
        contacts = new ArrayList<>();
        scanner = new Scanner(System.in);
        name = scanner.nextLine();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 3; i++) {
                    System.out.println("Waiting...");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println("Welcome back to Message " + name);
                showInitialOption();
            }
        }); thread.start();

    }

    private static void showInitialOption() {
        System.out.println("Select Options: \n\t1. Manage contacts \n\t2. Messages \n\t3. Quit");

        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                manageContacts();
                break;
            case 2:
                manageMessages();
                break;
            default:
                break;
        }
    }

    private static void manageContacts() {
        System.out.println("Chose options: \n1. Show all contacts \n2. Add a new contact \n3. Search for a contact " +
                "\n4. Delete a contact \n5. Go back to previous menu");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                showAllContacts();
                break;
            case 2:
                addNewContact();
                break;
            case 3:
                searchForContact();
                break;
            case 4:
                deleteContact();
                break;
            default:
                showInitialOption();
                break;
        }
    }

    private static void deleteContact() {
        System.out.println("Enter th contact name you're gonna delete");
        String name = scanner.next();
        if(name.equals("")) {
            System.out.println("Please enter the name:");
            deleteContact();
        } else {
            boolean doesExist = false;
            for (Contact c: contacts) {
                if(c.getName().equals(name)) {
                    doesExist = true;
                    contacts.remove(c);
                }
            }

            if(!doesExist) {
                System.out.println("There's no contact named " + name);
            }

        }
        showInitialOption();
    }

    private static void searchForContact() {
        System.out.println("Enter the contact name you're looking for:");
        String name = scanner.next();
        if (name.equals("")) {
            System.out.println("Please enter the name:");
            searchForContact();
        } else {
            boolean doesExist = false;
            for (Contact c : contacts) {
                if(c.getName().equals(name)) {
                    doesExist = true;
                    c.getDetails();
                }
            }
            if(!doesExist) {
                System.out.println("There's no contact named " + name);
            }
        }
        showInitialOption();
    }

    private static void addNewContact() {
        System.out.println("Please insert name, email, and number:");
        System.out.println("Insert name contact:");
        String name = scanner.next();
//        boolean doesExist = false;
        for (Contact c : contacts) {
            if(c.getName().equals(name)) {
//                doesExist = true;
                System.out.println("There's a contact named " + name + " in your phone, please insert different name!");
                addNewContact();
            }
        }
//        if (doesExist) {
//
//        }
        System.out.println("Insert number:");
        String number = scanner.next();
        System.out.println("Insert phone email:");
        String email = scanner.next();

        if(name.equals("") || number.equals("") || email.equals("")) {
            System.out.println("Please fill in all contact detail");
            addNewContact();
        } else {
            Contact contact = new Contact(name,number,email);
            contacts.add(contact);
            System.out.println("Added contact successfully");
        }
        showInitialOption();
    }

    private static void showAllContacts() {
        for (Contact c: contacts) {
            c.getDetails();
            System.out.println("************");
        }
        if (contacts.size() == 0) {
            System.out.println("There's no contact in your phone! Please add new contact");
        }
        showInitialOption();
    }

    private static void manageMessages() {
        System.out.println("Chose option: \n1. See the list of all messages \n2. Send a new message" +
                " \n3. Go back to previous menu");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                seeAllMessages();
                break;
            case 2:
                sendNewMessage();
                break;
            default:
                showInitialOption();
                break;
        }
    }

    private static void sendNewMessage() {
        System.out.println("Who are you going to send a message?");
        String name = scanner.next();
        if (name.equals("")) {
            System.out.println("Please enter the name of the contact");
            sendNewMessage();
        } else {
            boolean doesExist = false;
            for (Contact c : contacts) {
                if(c.getName().equals(name)) {
                    doesExist = true;
                }
            }
            if (doesExist) {
                System.out.println("What are you going to say:");
                String text = scanner.next();
                if(text.equals("")) {
                    System.out.println("Please enter some message");
                    sendNewMessage();
                } else {
                     id++;
                     Message newMessage = new Message(text,name,id);
                     for(Contact c : contacts) {
                         if(c.getName().equals(name)) {
                             ArrayList<Message> newMessages = c.getMessages();
                             newMessages.add(newMessage);
                             c.setMessages(newMessages);
                         }
                     }
                }
            } else {
                System.out.println("There's no contact named " + name + " in your phone");
            }
        }
        showInitialOption();
    }

    private static void seeAllMessages() {
        ArrayList<Message> allMessage = new ArrayList<>();
        for (Contact c: contacts) {
            allMessage.addAll(c.getMessages());
        }
        if(allMessage.size() > 0) {
            for (Message m : allMessage) {
                m.getDetails();
                System.out.println("*****************");
            }
        } else {
            System.out.println("There's no messages in your phone");
        }
        showInitialOption();
    }
}
