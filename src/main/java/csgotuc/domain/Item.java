/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csgotuc.domain;

import java.util.Objects;

public class Item {

    private String name;
    private String design;
    private String weapon;
    private String collection;
    private double floatValue;
    private int grade;
    private byte[] image;


    public Item(String weapon, String design, String collection, int grade, byte[] image) {
        this.name = weapon + " | " + design;
        this.weapon = weapon;
        this.design = design;
        this.collection = collection;
        this.grade = grade;
        this.image = image;
        this.floatValue = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesign() {
        return design;
    }

    public void setDesign(String design) {
        this.design = design;
    }

    public String getWeapon() {
        return weapon;
    }

    public void setWeapon(String weapon) {
        this.weapon = weapon;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
    
        public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.name);
        hash = 23 * hash + Objects.hashCode(this.design);
        hash = 23 * hash + Objects.hashCode(this.weapon);
        hash = 23 * hash + Objects.hashCode(this.collection);
        hash = 23 * hash + Objects.hashCode(this.grade);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Item other = (Item) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.design, other.design)) {
            return false;
        }
        if (!Objects.equals(this.weapon, other.weapon)) {
            return false;
        }
        if (!Objects.equals(this.collection, other.collection)) {
            return false;
        }
        if (this.grade != other.grade) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Item{" + "name=" + name + ", collection=" + collection + ", grade=" + grade + '}';
    }
   
    
    
}
