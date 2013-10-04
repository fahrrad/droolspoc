package com.gb.cropdesign.droolspoc;


import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: CoesseW
 * Date: 4/10/13
 * Time: 14:23
 * To change this template use File | Settings | File Templates.
 */
public class PlantSummary {
    private String name;

    private Map<String, Boolean> targetResults = new HashMap<String, Boolean>();


    public PlantSummary(String name){
        this.name = name;
    }

    public Boolean addTest(String target, Boolean result){
        return targetResults.put(target, result);
    }

    public Boolean getResult(String target){
        return targetResults.get(target);
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<String> getTargets(){
        return Collections.unmodifiableCollection(targetResults.keySet());
    }

    public String getName(){
        return name;
    }
}
