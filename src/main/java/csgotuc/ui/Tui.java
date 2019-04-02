/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csgotuc.ui;

import csgotuc.dao.FileItemDao;
import csgotuc.dao.ItemDao;
import csgotuc.domain.Item;
import csgotuc.domain.ItemService;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author latvavil
 */
public class Tui {
    private Scanner scanner;
    private ItemService itemService;
    
    public Tui(Scanner scanner, ItemService itemService) {
        this.scanner = scanner;
        this.itemService = itemService;
    }
    
    public void start() {
        System.out.println("Welcome!");
        askForInput();
        System.out.println("");
        printInput();
        System.out.println("");
        presentOutcome();
    }
    
    public void askForInput() {
        List<Item> inputs = new ArrayList<>();
        while(true){
            System.out.println("Please give 10 IDs to form a set of items used as an input for the trade-up contract");
            String[] inputString = scanner.nextLine().split(",");
            if(validateInputString(inputString)) {
                int[] ids = new int[10];
                for (int i = 0; i < 10; i++) {
                    ids[i] = Integer.parseInt(inputString[i]);
                }
                itemService.setInputWithIds(ids);
                break;
            }
        }     
    }

    private boolean validateInputString(String[] inputString) {
        if(inputString.length == 10) {
            return true;
        }
        System.out.println("Wrong amount of ids provided!");
        return false;
    }
    
    private void printInput() {
        System.out.println("Selected input for a trade-up contract is:");
        List<Item> inputs = this.itemService.getInput();
        for (int i = 0; i < inputs.size(); i++) {
            System.out.println((i+1)+". " + inputs.get(i).toString());
        }
    }

    private void presentOutcome() {
        List<Item> outcomePool = this.itemService.calculateTradeUp();
        int poolSize = outcomePool.size();
        Map<Item, Integer> outcomeDist = new HashMap<>();
        
        for (int i = 0; i < poolSize; i++) {
            Item curItem = outcomePool.get(i);
            if(outcomeDist.containsKey(curItem)) {
                outcomeDist.put(curItem, outcomeDist.get(curItem) +1);
            } else {
                outcomeDist.put(curItem, 1);
            }
        }
        System.out.println("Outcome pool is as follows: ");
        for (Map.Entry<Item, Integer> entry : outcomeDist.entrySet()) {
            double chance = entry.getValue()/(1.0*poolSize);
            System.out.println(chance*100 +"% - " + entry.getKey().toString());
        }
    }
}
