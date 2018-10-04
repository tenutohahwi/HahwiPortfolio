package com.tenutohahwi.hahwiportfolio.foodanalysis;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class FirebasePost {
    public String id;
    public String name;
    public String company;
    public String type;
    public String capacity;
    public int cal; //칼로리

    public FirebasePost(){
        // Default constructor required for calls to DataSnapshot.getValue(FirebasePost.class)
    }

    public FirebasePost(String id, String name, String company, String type, String capacity, int cal) {
        this.id = id;
        this.name = name;
        this.company = company;
        this.type = type;
        this.capacity = capacity;
        this.cal = cal;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("name", name);
        result.put("company", company);
        result.put("type", type);
        result.put("capacity", capacity);
        result.put("cal", cal);
        return result;
    }
}