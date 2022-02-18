package springtest;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Scanner;

@Entity
public class BuddyInfo implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String name;
    private String address;
    private String phoneNumber;
    private int age;

    public BuddyInfo() {} //javabeans convention

    public BuddyInfo(String name) {
        this.name = name;
        this.address = this.phoneNumber = "unknown";
    }

    public BuddyInfo(BuddyInfo b) {
        this.name = b.getName();
        this.address = b.getAddress();
        this.phoneNumber = b.getPhoneNumber();
        this.age = b.getAge();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isOver18() {
        return age > 18;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String toString() {
        return this.getName() + "#" + this.getAddress() + "#" + this.getPhoneNumber() + "#" + this.getAge();
    }

    public String getGreeting() {
        return "Hi! My name is " + this.name;
    }

    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o == null)
            return false;
        if (!(o instanceof BuddyInfo))
            return false;
        BuddyInfo other = (BuddyInfo) o;
        return this.name.equals(other.getName()) && this.phoneNumber.equals(other.getPhoneNumber())
                && this.address.equals(other.getAddress()) && this.age == other.getAge();
    }

    public static BuddyInfo importBuddy(String buddyText) {
        Scanner scnr = new Scanner(buddyText);
        scnr.useDelimiter("#");

        BuddyInfo buddyToReturn = new BuddyInfo(scnr.next());
        buddyToReturn.setAddress(scnr.next());
        buddyToReturn.setPhoneNumber(scnr.next());
        buddyToReturn.setAge(scnr.nextInt());

        scnr.close();

        return buddyToReturn;
    }

    public String toXML() {
        String result = new String();
        result += "	<springtest.BuddyInfo>\n";
        result += "	 	<name> " + this.name + " </name>\n";
        result += "	 	<address> " + this.address + " </address>\n";
        result += "	 	<phoneNumber> " + this.phoneNumber + " </phoneNumber>\n";
        result += "	 	<age> " + this.age + " </age>\n";
        result += "	</springtest.BuddyInfo>";
        return result;
    }

    public static void main(String[] args) {
        System.out.println("Hello World");

        BuddyInfo myBuddy = new BuddyInfo("Dave");
        myBuddy.setAddress("122 St-Laurent Blvd.");
        myBuddy.setPhoneNumber("(613) 222-6667");

        System.out.println("Hello " + myBuddy.getName());

        BuddyInfo copy = importBuddy(myBuddy.toString());
        System.out.println(copy);
        System.out.println(copy.getName());
        System.out.println(copy.getAddress());
        System.out.println(copy.getPhoneNumber());

        System.out.println(myBuddy.toXML());
    }

}

