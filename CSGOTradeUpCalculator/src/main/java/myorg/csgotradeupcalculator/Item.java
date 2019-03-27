/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myorg.csgotradeupcalculator;

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
    
    
    
    
}
