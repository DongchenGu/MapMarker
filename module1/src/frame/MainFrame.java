package frame;


import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.Microsoft;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainFrame extends PApplet {

    private UnfoldingMap map;
    @Override
    public void setup() {
        size(1080, 800, OPENGL);
        map  = new UnfoldingMap(this, 100,50,900,700, new Microsoft.RoadProvider());
        map.zoomToLevel(3);
        MapUtils.createDefaultEventDispatcher(this, map);

        Location loc1 = new Location(-31.4f,-73.03f);
        Location loc2 = new Location(61.02f,-147.65f);
        Location loc3= new Location(3.3f,95.7f);
        Location loc4 = new Location(38.32f,142.36f);


        PointFeature eq1 = new PointFeature(loc1);
        eq1.addProperty("title","Valdivia, Chile");
        eq1.addProperty("magnitude","9.5");
        eq1.addProperty("date","may 22, 1960");
        eq1.addProperty("year", "1960");

        PointFeature eq2 = new PointFeature(loc2);
        eq2.addProperty("title","greatAlaska, USA");
        eq2.addProperty("magnitude","9.2");
        eq2.addProperty("date","may 22, 1997");
        eq2.addProperty("year", "1997");

        PointFeature eq3 = new PointFeature(loc3);
        eq3.addProperty("title","west Coast, NS");
        eq3.addProperty("magnitude","9.1");
        eq3.addProperty("date","may 22, 2005");
        eq3.addProperty("year", "2005");

        PointFeature eq4 = new PointFeature(loc4);
        eq4.addProperty("title","hongshu, Japan");
        eq4.addProperty("magnitude","9.0");
        eq4.addProperty("date","may 22, 2004");
        eq4.addProperty("year", "2004");


        List<PointFeature> bigEq = new ArrayList<PointFeature>();
        bigEq.add(eq1);
        bigEq.add(eq2);
        bigEq.add(eq3);
        bigEq.add(eq4);

        List<Marker> markers = new ArrayList<Marker>();
        Iterator<PointFeature> it1 = bigEq.iterator();
        while (it1.hasNext()){
            PointFeature pf = it1.next();
            markers.add(new SimplePointMarker(pf.getLocation(),pf.getProperties()));
        }
        int yellow =color(255,255,0);
        int gray =color(150,150,0);


        Iterator<Marker> it2 = markers.iterator();
        while (it2.hasNext()){
            Marker marker = it2.next();
            //System.out.println(it2.next().getStringProperty("year"));
            int Year = Integer.parseInt(marker.getStringProperty("year"));
            if(Year>2000){
                marker.setColor(yellow);
            }else{
                marker.setColor(gray);
            }
        }

        map.addMarkers(markers);
    }

    @Override
    public void draw() {
        background(GRAY);
        map.draw();
    }
}
