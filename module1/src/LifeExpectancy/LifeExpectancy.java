package LifeExpectancy;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;

import javax.rmi.CORBA.Util;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LifeExpectancy extends PApplet {

    UnfoldingMap map;
    Map<String, Float> lifeExpectancyCountry;
    List<Feature> features = new ArrayList<>();
    List<Marker> markers = new ArrayList<Marker>();
   // String[] rows = loadStrings("L:\\MapMarker\\module1\\src\\data\\LifeExpectancyWorldBank.csv");
    public void setup(){
        size(1000,800,OPENGL);
        map = new UnfoldingMap(this, 50,50,900,850,new Google.GoogleMapProvider());
        MapUtils.createDefaultEventDispatcher(this,map);
       // String[] rows = loadStrings("MapMarker/module1/src/data/LifeExpectancyWorldBank.csv");

        try {
            lifeExpectancyCountry = loadLifeExpectancyFromCSV("L:\\MapMarker\\module1\\src\\data\\LifeExpectancyWorldBank.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }



        features = GeoJSONReader.loadData(this,"L:\\MapMarker\\module1\\src\\data\\countries.geo.json");
        markers = MapUtils.createSimpleMarkers(features);
        map.addMarkers(markers);
        shadeCountries();

    }

    public void draw(){
        map.draw();
        shadeCountries();
    }

    private Map<String, Float> loadLifeExpectancyFromCSV(String fileStr) throws IOException {
        Map<String, Float> lifeExpectancy =new HashMap<String, Float>();

        List<String> rows = new ArrayList<String>();
        File csv = new File(fileStr);
        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(csv), "UTF-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line;
        while ((line = bufferedReader.readLine())!=null){
            rows.add(line);
        }
        for(String row : rows){

            String [] column = row.trim().split(",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)",-1);
            System.out.println(column[3]);
            if(!column[4].contains("..")){
                Float value = Float.parseFloat(column[4]);
                lifeExpectancy.put(column[3],value);
            }
        }
        return lifeExpectancy;
    }

    private  void shadeCountries(){
        for(Marker mk: markers){
            String countryID = mk.getId();
            if(lifeExpectancyCountry.containsKey(countryID)){
                float lifeEX  = lifeExpectancyCountry.get(countryID);
                int colorIN = (int)map(lifeEX,40,90,10,255);
                mk.setColor(colorIN);
            }else{
                mk.setColor(color(150,150,150));
            }
        }
    }

}
