/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csgotuc.domain;

import java.util.Objects;

/**
 * This class represents a weapon design, and contains relevant variables that
 * such item needs as well as getters and setters for them.
 */
public class Item {

    private String name;
    private String collection;
    private double floatValue;
    private int grade;
    private byte[] image;
    private double minWear;
    private double maxWear;

    /**
     * @param collection
     * @param grade
     * @param image
     */
    public Item(String name, String collection, int grade, byte[] image, double minWear, double maxWear) {
        this.name = name;
        this.collection = collection;
        this.grade = grade;
        this.image = image;
        this.floatValue = 0;
        this.minWear = minWear;
        this.maxWear = maxWear;
    }

    public Item(Item item) {
        this.name = item.getName();
        this.collection = item.getCollection();
        this.floatValue = item.getFloatValue();
        this.grade = item.getGrade();
        this.image = item.getImage();
        this.minWear = item.minWear;
        this.maxWear = item.maxWear;

    }

    public double getFloatValue() {
        return floatValue;
    }

    public void setFloatValue(double floatValue) {
        this.floatValue = floatValue;
    }

    public double getMinWear() {
        return minWear;
    }

    public double getMaxWear() {
        return maxWear;
    }

    public String getName() {
        return name;
    }

    public String getCollection() {
        return collection;
    }

    public int getGrade() {
        return grade;
    }

    public byte[] getImage() {
        return image;
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
        if (!Objects.equals(this.collection, other.collection)) {
            return false;
        }
        if (this.grade != other.grade) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.name);
        hash = 29 * hash + Objects.hashCode(this.collection);
        hash = 29 * hash + this.grade;
        return hash;
    }
    
    

    @Override
    public String toString() {
        return "Item{" + "name=" + name + ", collection=" + collection + ", maxWear=" + maxWear + '}';
    }
    
    

    public String getCondition() {
        double fv = this.floatValue;
        if (fv < 0.07) {
            return "Factory New";
        } else if (fv < 0.15) {
            return "Minimal Wear";
        } else if (fv < 0.38) {
            return "Field-Tested";
        } else if (fv < 0.45) {
            return "Well-Worn";
        }
        return "Battle-Scarred";
    }

}
