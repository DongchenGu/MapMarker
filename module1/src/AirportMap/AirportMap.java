package AirportMap;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.Microsoft;
import de.fhpotsdam.unfolding.utils.MapUtils;
import parsing.ParseFeed;
import processing.core.PApplet;

import javax.media.nativewindow.util.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AirportMap  extends PApplet {

    UnfoldingMap map;
    List<Marker> airportMarkers;
    Marker lastSelected;

    @Override
    public void setup() {
       size(1400,900,OPENGL);
       map = new UnfoldingMap(this,0,40, 1400,860,new Microsoft.RoadProvider());
       MapUtils.createDefaultEventDispatcher(this,map);

       List<PointFeature> airportFeatures = ParseFeed.parseAirports(this,"L:\\MapMarker\\module1\\src\\data\\airports.dat");

       airportMarkers = new ArrayList<>();
       Iterator<PointFeature> it = airportFeatures.iterator();
       while(it.hasNext()){
          // System.out.println(it.next().getLocation());
           AirportMarker airportMarker = new AirportMarker(it.next());
           airportMarkers.add(airportMarker);
       }
       map.addMarkers(airportMarkers);
       map.zoomAndPanTo(5, new Location(39.95f, -75.12f));

    }


    @Override
    public void draw() {
        map.draw();
        fill(color(122,35,125));
        textSize(35);
        text("AirportMap",0,27);
    }

    @Override
    public void mouseMoved() {
        // clear the last selection
        if (lastSelected != null) {
            lastSelected.setSelected(false);
            lastSelected = null;

        }
        selectMarkerIfHover(airportMarkers);
    }


    public void selectMarkerIfHover(List<Marker> airportMarkers){
        if (lastSelected != null) {
            return;
        }
        for(Marker mk :airportMarkers){
            if(mk.isInside(map,mouseX,mouseY)){
                lastSelected = (AirportMarker)mk;
                lastSelected.setSelected(true);
                return;
            }

        }
    }
}
