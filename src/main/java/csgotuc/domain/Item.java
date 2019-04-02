/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csgotuc.domain;

import java.util.Objects;

public class Item {
    private String name;
    private String collection;
    private int grade;

    public Item(String name, String collection, int grade) {
        this.name = name;
        this.collection = collection;
        this.grade = grade;
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

    @Override
    public String toString() {
        return "Item{" + "name=" + name + ", collection=" + collection + ", grade=" + grade + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.name);
        hash = 89 * hash + Objects.hashCode(this.collection);
        hash = 89 * hash + this.grade;
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
        if (this.grade != other.grade) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.collection, other.collection)) {
            return false;
        }
        return true;
    }
    
    
    
    
    
    
}
