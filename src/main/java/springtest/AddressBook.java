package springtest;

import java.util.ArrayList;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.persistence.*;
import javax.swing.*;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

@Entity
public class AddressBook implements Serializable {


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<BuddyInfo> buddies;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private BuddyInfo bookOwner;

    public AddressBook() {
        buddies = new ArrayList<>();
        this.bookOwner = new BuddyInfo();
    } //javabeans convention

    public AddressBook(BuddyInfo bookOwner) {
        this.bookOwner = bookOwner;
        buddies = new ArrayList<>();
    }


      public BuddyInfo getBookOwner() {
          return this.bookOwner;
      }

      public void setBookOwner(BuddyInfo bookOwner) {
        this.bookOwner = bookOwner;
      }
    public void setBuddy(BuddyInfo buddyToAdd) {buddies.add(buddyToAdd);}

    public void addBuddy(BuddyInfo buddyToAdd) {
        buddies.add(buddyToAdd);
    }

    public void removeBuddy(BuddyInfo buddyToRemove) {
        buddies.remove(buddyToRemove);
    }

    public int getSize() {
        return buddies.size();
    }

    public void clearBook() {
        buddies.clear();
    }

    public List<BuddyInfo> getBuddies() {
        return buddies;
    }

    public void setBuddies(List<BuddyInfo> list) {
        this.clearBook();
        for(BuddyInfo e: list) {
            this.addBuddy(e);
        }
    }

    public static void main(String[] args) { // a nice comment
        AddressBook aBook = new AddressBook(new BuddyInfo("John"));
        System.out.println(aBook.getBookOwner().getName());

        BuddyInfo aFriend = new BuddyInfo("Dave");
        aFriend.setPhoneNumber("613 411-1111");

        aBook.addBuddy(aFriend);

        System.out.println(aBook.toXML());
        aBook.exportToXMLFile("xmlFile");
        try {
            aBook.importFromXMLFile("xmlFile.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }

        aBook.removeBuddy(aFriend);

    }

    public void saveAsString(String filename) {
        PrintWriter writer;
        try {
            writer = new PrintWriter(filename, "UTF-8");
            for (int i = 0; i < this.getSize(); i++) {
                writer.println(buddies.get(i).toString());
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void importBookAsString(String filename) {
        BufferedReader reader;
        String line;

        try {
            reader = new BufferedReader(new FileReader(filename));
            buddies.clear();
            while ((line = reader.readLine()) != null) {
                buddies.add(BuddyInfo.importBuddy(line));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save(String filename) {
        try {
            // Saving of object in a file
            FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(file);

            // Method for serialization of object
            out.writeObject(this);

            out.close();
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void importBook(String filename) {
        try {
            // Reading the object from a file
            FileInputStream file = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(file);

            // Method for deserialization of object
            AddressBook importedBook = (AddressBook)in.readObject();
            buddies.clear();

            for (int i = 0; i < importedBook.getSize(); i++) {
                this.addBuddy(importedBook.buddies.get(i));
            }

            in.close();
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void exportToXMLFile(String filename) {
        PrintWriter writer;
        try {
            writer = new PrintWriter(filename + ".xml", "UTF-8");
            writer.print(this.toXML());
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void importFromXMLFile(String filename) throws Exception {
        File f = new File(filename);
        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser s = spf.newSAXParser();
        buddies.clear();//clear old book
        AddressBook newBook = this;

        DefaultHandler dh = new DefaultHandler() {
            private boolean buddyFound = false;
            private boolean buddyNameFound = false;
            private boolean buddyAddressFound = false;
            private boolean buddyNumberFound = false;
            private boolean buddyAgeFound = false;
            private String buddyName;
            private String buddyAddress;
            private String buddyNumber;
            private int buddyAge;

            public void startElement(String u, String ln, String qName, Attributes a) {
                if (qName.equals("springtest.BuddyInfo")) {
                    buddyFound = true;
                }
                else if (qName.equals("name")) {
                    buddyNameFound = true;
                }
                else if (qName.equals("address")) {
                    buddyAddressFound = true;
                }
                else if (qName.equals("phoneNumber")) {
                    buddyNumberFound = true;
                }
                else if (qName.equals("age")) {
                    buddyAgeFound = true;
                }
            }

            public void endElement(String uri, String localName, String qName) {
                if (qName.equals("springtest.BuddyInfo")) {
                    buddyFound = false;
                    BuddyInfo newBuddy = new BuddyInfo(buddyName);
                    newBuddy.setAddress(buddyAddress);
                    newBuddy.setPhoneNumber(buddyNumber);
                    newBuddy.setAge(buddyAge);
                    newBook.addBuddy(newBuddy);
                }
                else if (qName.equals("name")) {
                    buddyNameFound = false;
                }
                else if (qName.equals("address")) {
                    buddyAddressFound = false;
                }
                else if (qName.equals("phoneNumber")) {
                    buddyNumberFound = false;
                }
                else if (qName.equals("age")) {
                    buddyAgeFound = false;
                }
            }

            public void characters(char[] ch, int start, int length) {
                if(buddyFound) {
                    String element = new String(ch, start, length);
                    if(buddyNameFound) {
                        buddyName = element.trim();
                    }
                    else if(buddyAddressFound) {
                        buddyAddress = element.trim();
                    }
                    else if(buddyNumberFound) {
                        buddyNumber = element.trim();
                    }
                    else if(buddyAgeFound) {
                        buddyAge = Integer.parseInt(element.trim());
                    }
                }
            }
        };

        s.parse(f, dh);
    }

    public String toXML() {
        String result = new String();
        result += "<springtest.AddressBook>";
        for(int i = 0; i < this.getSize(); i ++) {
            result += "\n" + buddies.get(i).toXML();
        }
        result += "\n</springtest.AddressBook>";
        return result;
    }

    public String toString() {
        return this.toXML();
    }

    public boolean equals(Object o) {
        if (o == this)
            return true;
        else if (o == null)
            return false;
        else if (!(o instanceof AddressBook))
            return false;
        AddressBook other = (AddressBook) o;
        boolean same = true;
        for(int i= 0; i < this.getSize(); i++) {
            if (!other.buddies.contains(this.buddies.get(i))) {
                same = false;
            }
        }
        return (same && other.getSize() == this.getSize());
    }

    public boolean contains(BuddyInfo b) {
        return this.buddies.contains(b);
    }
}
