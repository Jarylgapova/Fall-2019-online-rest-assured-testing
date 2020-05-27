package com.automation.pojos;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

/**
 * This class represents spartan POJO
 * Example of JSON response:
 * {
 * "id": 393,               ->   private int id;
 * "name": "Michael Scott", ->   private String name;
 * "gender": "Male",        ->   private String gender;
 * "phone": 6969696969      ->   @SerializedName("phone") private long phoneNumber;
 * }
 * SerializedName  - an annotation that indicates this member should be serialized to JSON with
 * * the provided name value as its field name.
 */
public class Spartan {
    private int id;
    private String name;
    private String gender;

    @SerializedName("phone") // if i want to change origin name
    private long phoneNumber;

    public Spartan(String name, String gender, long phoneNumber) {
        this.name = name;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
    }

    public Spartan(int id, String name, String gender, long phoneNumber) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
    }

    public Spartan() {

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName() {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender() {
        this.gender = gender;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber() {
        this.phoneNumber = phoneNumber;
    }


    @Override
    public String toString() {
        return "Spartan{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", phoneNumber=" + phoneNumber +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Spartan spartan = (Spartan) o;
        return id == spartan.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, gender, phoneNumber);
    }
}
