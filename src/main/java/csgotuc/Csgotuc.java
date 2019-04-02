/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csgotuc;

import csgotuc.dao.FileItemDao;
import csgotuc.dao.ItemDao;
import csgotuc.domain.ItemService;
import csgotuc.ui.Tui;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 *
 * @author latvavil
 */
public class Csgotuc {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ItemDao itemDao;
        ItemService itemService;
        try {
            Path path = Paths.get(".", "/src/main/resources/object_data.csv");
            itemDao = new FileItemDao(path.normalize().toString());
            itemService = new ItemService(itemDao);
            Tui tui = new Tui(scanner, itemService);
            tui.start();
        } catch (Exception ex) {
            System.out.println(ex);;
        }

    }
}
