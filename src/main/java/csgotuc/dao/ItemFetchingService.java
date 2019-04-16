/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csgotuc.dao;

import csgotuc.domain.Item;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author Viljami
 */
public class ItemFetchingService {

    private String baseUrl;

    public ItemFetchingService() {
        baseUrl = "https://csgostash.com/";
    }

    public List<Item> fetchAllItems() throws IOException {
        List<String> collectionUrls = this.findCollectionUrls();
        List<String> itemUrls = new ArrayList<>();

        for (String curUrl : collectionUrls) {
            Document doc = Jsoup.connect(curUrl).get();
            doc.select("p.nomargin").forEach(p -> {
                String url = p.select("a[href]").attr("href");
                if (!url.isEmpty() && !itemUrls.contains(url)) {
                    itemUrls.add(url);
                }
            });
        }

        List<Item> items = fetchItemsFromUrls(itemUrls);

        return items;
    }

    public List<String> findCollectionUrls() throws IOException {
        Document doc = Jsoup.connect(this.baseUrl).get();
        List<String> skinUrls = new ArrayList<>();
        //Tulee poistaa itse pudotusvalikon linkki
        doc.select("a:contains(Collections)").get(0).parent().children().select("a:not(.dropdown-toggle)").forEach(a -> skinUrls.add(a.attr("href")));

        //Tulee poistaa itse pudotusvalikon linkki sekä 3 viimeistä linkkiä 
        doc.select("a:contains(Cases)")
                .get(0)
                .parent()
                .children()
                .select("a:not(.dropdown-toggle)")
                .forEach(a -> {
                    String skinUrl = a.attr("href");
                    String path = skinUrl.substring(this.baseUrl.length(), this.baseUrl.length() + 4);
                    if (path.equals("case")) {
                        skinUrls.add(skinUrl);
                    }
                });

        return skinUrls;
    }

    private List<Item> fetchItemsFromUrls(List<String> itemUrls) throws IOException {
        ArrayList<Item> items = new ArrayList<>();
        for (String itemUrl : itemUrls) {
            Document doc = Jsoup.connect(itemUrl).get();
            String grade = doc.select("a.nounderline").attr("title");
            grade = grade.substring(4, grade.length() - 6);
            String weapon = doc.select("h2").first().children().select("a").first().text();
            String design = doc.select("h2").first().children().select("a").last().text();
            String imgUrl = doc.select("img.img-responsive.center-block.main-skin-img.margin-top-sm.margin-bot-sm").attr("src");
            String collection = doc.select("p.collection-text-label").text();
            System.out.println("coll!: " + collection);
            byte[] img = downloadUrl(new URL(imgUrl));
            Item newItem = new Item(weapon, design, collection, getGrade(grade), downloadUrl(new URL(imgUrl)));
            System.out.println("loaded: " + newItem.toString());
            items.add(newItem);

        }

        return items;
    }

    private int getGrade(String grade) {
        if (grade.equals("Consumer Grade")) {
            return 0;
        } else if (grade.equals("Industrial Grade")) {
            return 1;
        } else if (grade.equals("Mil-Spec")) {
            return 2;
        } else if (grade.equals("Restricted")) {
            return 3;
        } else if (grade.equals("Classified")) {
            return 4;
        } else if (grade.equals("Covert")) {
            return 5;
        } else {
            return 999;
        }
    }

    private byte[] downloadUrl(URL toDownload) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            byte[] chunk = new byte[4096];
            int bytesRead;
            InputStream stream = toDownload.openStream();

            while ((bytesRead = stream.read(chunk)) > 0) {
                outputStream.write(chunk, 0, bytesRead);
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return outputStream.toByteArray();
    }

}
