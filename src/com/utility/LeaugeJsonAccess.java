package com.utility;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import test.stuff.TestJson;

import java.util.ArrayList;
import java.util.List;

public class LeaugeJsonAccess {
    /*
        DESCRIPTION
        Class used to access the different fields of json
     */

    private JSONObject leaugeJson;
    private JsonInteraction interacter;

    public LeaugeJsonAccess() throws ParseException {
        TestJson json = new TestJson();
        JSONParser parser = new JSONParser();
        this.leaugeJson = (JSONObject) parser.parse(json.getJson());
        this.interacter = new JsonInteraction();
    }

    public JSONObject getGameData() {
        List<String> keys = new ArrayList<String>();
        keys.add("gameData");
        return (JSONObject) interacter.jsonMultipleGet(keys, leaugeJson);
    }

    public JSONObject getActivePlayer() {
        List<String> keys = new ArrayList<String>();
        keys.add("activePlayer");
        return (JSONObject)  interacter.jsonMultipleGet(keys, leaugeJson);
    }

    private List<String> keyObjectGenerator(String s) {
        List<String> keys = new ArrayList<String>();
        keys.add(s);
        return keys;
    }

    public JSONObject getAbilities() {
        List<String> keys = keyObjectGenerator("abilities");
        JSONObject obj = getActivePlayer();
        return (JSONObject) interacter.jsonMultipleGet(keys, obj);
    }

    public JSONObject getActiveAbility(String s){
        List<String> keys = keyObjectGenerator(s);
        JSONObject obj = getAbilities();
        return (JSONObject) interacter.jsonMultipleGet(keys, obj);
    }

    public String getActiveAbilityName(String qwer) { //put in enums
        List<String> keys = keyObjectGenerator("displayName");
        JSONObject obj = getActiveAbility(qwer);
        return (String) interacter.jsonMultipleGet(keys, obj);
    }

    public long getActiveAbilityLevel(String q) {
        List<String> keys = keyObjectGenerator("abilityLevel");
        JSONObject  obj = getActiveAbility(q);
        return (long) interacter.jsonMultipleGet(keys, obj);
    }

    public double getCurrentGold() {
        List<String> keys = keyObjectGenerator("activePlayer");
        keys.add("currentGold");
        return (double) interacter.jsonMultipleGet(keys, leaugeJson);
    }

    public Double getGameTime() {
        List<String> keys = new ArrayList<String>();
        JSONObject obj = getGameData();
        keys.add("gameTime");
        Double stri = (Double) interacter.jsonMultipleGet(keys, obj);
        return stri;
    }

    public String getGameMode() {
        List<String> keys = new ArrayList<String>();
        JSONObject obj = getGameData();
        keys.add("gameMode");
        return (String) interacter.jsonMultipleGet(keys, obj);
    }

    public JSONArray getItemsOf(String summonerName) throws ParseException {
        JSONObject obj = getPlayer(summonerName);
        return (JSONArray) interacter.jsonGetField("items", obj);
    }

    public long getItemPricesOf(String summonerName) throws ParseException {
        long totalMoneySpent = 0;
        JSONArray array = getItemsOf(summonerName);
        for (Object o : array) {
            JSONObject  obj = (JSONObject) o;
            long itemPrice = (long) interacter.jsonGetField("price", obj);
            totalMoneySpent += itemPrice;
        }
        return totalMoneySpent;
    }

    public long getTotalGoldOfActivePlayer(String summonerName) throws ParseException {
        long itemPrices = getItemPricesOf(summonerName);
        long currentGold = (long) getCurrentGold();
        return itemPrices + currentGold;
    }

    public JSONObject getPlayer(String summonerName) {
        List<String>  keys = keyObjectGenerator("allPlayers");
        JSONArray array  = (JSONArray) interacter.jsonMultipleGet(keys, leaugeJson);
        for (Object obj : array) {
            JSONObject o = (JSONObject) obj;
            List<String> key = keyObjectGenerator("summonerName");
            String currentPlayerName = (String) interacter.jsonMultipleGet(key, o);
            if(currentPlayerName.equals(summonerName)) {
                return o;
            }
        }
        return null;
    }

    public long getCreepscoreOf(String summonerName) {
        List<String>  keys = keyObjectGenerator("allPlayers");
        List<String> creepKey = keyObjectGenerator("scores");
        creepKey.add("creepScore");
        JSONArray array  = (JSONArray) interacter.jsonMultipleGet(keys, leaugeJson);
        for (Object obj : array) {
            JSONObject o = (JSONObject) obj;
            List<String> key = new ArrayList<String >();
            key.add("summonerName");
            String currentPlayerName = (String) interacter.jsonMultipleGet(key, o);
            if(currentPlayerName.equals(summonerName)) { //TODO Configuration System --> Enviromental variables
                return (long) interacter.jsonMultipleGet(creepKey, o);
            }
        }
        return -5;
    }

    public static void main(String[] args) throws ParseException {
        LeaugeJsonAccess access = new LeaugeJsonAccess();
        Object obj = access.getGameData();
        System.out.println(obj);
        System.out.println(access.getGameTime());
        System.out.println(access.getGameMode());
        System.out.println(access.getActivePlayer());
        System.out.println(access.getAbilities());
        System.out.println(access.getActiveAbility("Q"));
        System.out.println(access.getActiveAbilityName("Q"));
        System.out.println(access.getActiveAbilityLevel("Q"));
        System.out.println(access.getCurrentGold());
        System.out.println(access.getCreepscoreOf("BonusServus"));
        System.out.println(access.getItemsOf("Garen-Bot"));
        System.out.println(access.getItemPricesOf("Garen-Bot"));
        System.out.println(access.getTotalGoldOfActivePlayer("BonusServus"));
    }
}
