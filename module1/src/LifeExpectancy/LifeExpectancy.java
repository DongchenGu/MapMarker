package LifeExpectancy;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.Microsoft;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;


import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LifeExpectancy extends PApplet {

    UnfoldingMap map;
    Map<String, Float> lifeExpectancy =new HashMap<String, Float>();
    List<Feature> features = new ArrayList<>();
    List<Marker> markers = new ArrayList<Marker>();
   // String[] rows = loadStrings("L:\\MapMarker\\module1\\src\\data\\LifeExpectancyWorldBank.csv");
    public void setup(){
        size(1000,800,OPENGL);
        map = new UnfoldingMap(this, 50,50,900,850,new Microsoft.RoadProvider());
        MapUtils.createDefaultEventDispatcher(this,map);
       // String[] rows = loadStrings("MapMarker/module1/src/data/LifeExpectancyWorldBank.csv");

        try {
            System.out.println(new File("./").getAbsolutePath());
            loadLifeExpectancyFromCSV("L:\\MapMarker\\module1\\src\\data\\LifeExpectancyWorldBank.csv");

        } catch (IOException e) {
            e.printStackTrace();
        }


        features = GeoJSONReader.loadData(this,"data/countries.geo.json");
        markers = MapUtils.createSimpleMarkers(features);
        map.addMarkers(markers);
        shadeCountries();
    }

    public void draw(){
        map.draw();
        shadeCountries();
    }
    private void loadLifeExpectancyFromCSV(String fileStr) throws IOException {
        List<String> rows = new ArrayList<String>();
        //File csv = new File(fileStr);
        //InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(csv), "UTF-8");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileStr));
        String line;
        while ((line = bufferedReader.readLine())!=null){
            rows.add(line);
        }
        for(String row : rows){

            String [] column = row.trim().split(",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)",-1);
            //System.out.println(column[3]);
            if(!column[4].contains("..")){
                Float value = Float.parseFloat(column[4]);
                lifeExpectancy.put(column[3],value);
            }
        }

    }

    private  void shadeCountries(){
        for(Marker mk: markers){
            String countryID = mk.getId();
            if(lifeExpectancy.containsKey(countryID)){
                float lifeEX  = lifeExpectancy.get(countryID);
                int colorIN = (int)map(lifeEX,40,90,0,255);
                //System.out.println("color is:" + colorIN);
                mk.setColor(color(0,0,colorIN));
            }else{
                mk.setColor(color(150,150,150));
            }
        }
    }

}
