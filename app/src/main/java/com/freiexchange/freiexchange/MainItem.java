package com.freiexchange.freiexchange;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

public class MainItem {
    private String tickerName;
    private String tickerPrice;

    public MainItem(String name, String price) {
        this.tickerName = name;
        this.tickerPrice = price;
    }

    public static ArrayList<MainItem> mainItemsFromJson(String itemsString) throws JSONException {

        ArrayList<MainItem> mainItemsArray = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(itemsString);
        Iterator<String> arrayKeys = jsonObject.keys();
        List<String> keyStringList = new ArrayList<>();
        while (arrayKeys.hasNext()){
          keyStringList.add(arrayKeys.next());
        }
        Collections.sort(keyStringList);

        for (int i = 0; i < keyStringList.size(); i++) {
            String stringListItem = keyStringList.get(i);
            MainItem newMain = new MainItem(stringListItem, jsonObject.getJSONArray(stringListItem).getJSONObject(0).getString("last"));
            mainItemsArray.add(newMain);
        }

        return mainItemsArray;
    }

    public static ArrayList<MainItem> getMainItemArrayFromItems(ArrayList<Item> itemsArray){
        ArrayList<MainItem> mainItemsArray = new ArrayList<>();
        for (int i = 0; i < itemsArray.size(); i++) {
            MainItem newItem = new MainItem(itemsArray.get(i).getItemName(), itemsArray.get(i).getItemBal());
            mainItemsArray.add(newItem);
        }
        return mainItemsArray;
    }

    public String getTickerName() {
        return this.tickerName;
    }

    public String getTickerPrice() {
        return tickerPrice;
    }

    public void setTickerName(String name) {
        this.tickerName = name;
    }

    public void setTickerPrice(String price) {
        this.tickerPrice = price;
    }
}

