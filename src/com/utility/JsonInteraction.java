package com.utility;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import test.stuff.TestJson;

import java.util.ArrayList;
import java.util.List;

public class JsonInteraction {
    /*
        DESCRIPTION: This class offers to retrieve data from a nested JSONObject the way it is defined in
        json.simple.
     */

    public Object jsonMultipleGet(List<String> keys, JSONObject obj) { //TODO to json traverse or jsonAccess?
        //Traversing nested JSONObjects
        //Precondition: gets JSONObject and keys to traverse over
        //Post: returns object contained in specified key of JSONObject;
        String key = keys.remove(0);
        if(keys.size() == 0) return obj.get(key);
        else {
            //System.out.println("key: "+ key+ " obj: "+ obj);
            return jsonMultipleGet(keys, (JSONObject) obj.get(key));
        }
    }

    public Object jsonGetField(String key, JSONObject obj) throws ParseException {
        return obj.get(key);
    }

    public Object jsonGetField(String key, String string) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject obj =  (JSONObject) parser.parse(string);
        return obj.get(key);
    }

    public void jsonSingleGet(String key) throws ParseException {

        JSONParser parser = new JSONParser();

        String jsonString = "{\"name\": \"Dietmar Gabriel\", \"Telefonnummer\": 28081}";
        String jsonString2 = "{\"name\": {\"Dietmar Gabriel\": \"Marius\"} , \"Telefonnummer\": 28081}";


        JSONObject json = (JSONObject) parser.parse(jsonString);
        JSONObject json2 = (JSONObject) parser.parse(jsonString2);

        JSONObject jj = (JSONObject) json2.get("name");
        jj.get("Dietmar Gabriel");

        System.out.println(json.get(key));
        System.out.println(json2.get("name"));
        System.out.println(jj.get("Dietmar Gabriel"));
    }

    public static void main(String[] args) throws ParseException {
        JsonInteraction interacters = new JsonInteraction();
        String leagueJson = new TestJson().getJson();
        interacters.jsonSingleGet("name");
        List<String> strings = new ArrayList<String>();
        strings.add("name");
        strings.add("Dietmar Gabriel");
        String jsonString2 = "{\"name\": {\"Dietmar Gabriel\": \"Marius\"} , \"Telefonnummer\": 28081}";
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(jsonString2);
        JsonInteraction interacter = new JsonInteraction();
        String jj = (String) interacter.jsonMultipleGet(strings, json);
        System.out.println(jj);
        List<String> string2 = new ArrayList<String>();
        string2.add("allPlayers");
        JSONObject leagueJSON = (JSONObject) parser.parse(leagueJson);
        JSONArray kk = (JSONArray) interacter.jsonMultipleGet(string2, leagueJSON);
        System.out.println(kk.getClass());
        for(Object o : kk) {
            System.out.println(o);
        }

    }
}
