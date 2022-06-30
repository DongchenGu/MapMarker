package AirportMap;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import processing.core.PConstants;
import processing.core.PGraphics;

import javax.media.nativewindow.util.Point;
import javax.xml.stream.Location;
import java.util.HashMap;
import java.util.Map;

public class AirportMarker extends SimplePointMarker {

    private boolean selected;

    public AirportMarker(PointFeature pointFeature){
        super(pointFeature.getLocation(), pointFeature.getProperties());
    }

    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public boolean isSelected() {
        return selected;
    }

    public void draw(PGraphics pg, float x, float y) {
        if (!hidden) {
            drawMarker(pg, x, y);
            if (selected) {
                showTitle(pg, x, y);
            }
        }
    }

    public  void drawMarker(PGraphics pg, float x, float y){
        pg.fill(122,35,125);
        pg.triangle(x-5,y+5,x,y-5,x+5,y+5);

    }

    public  void showTitle(PGraphics pg, float x, float y){

        String title = this.getStringProperty("code")+ " "+ this.getStringProperty("name")+" "
               + this.getStringProperty("city");
        //System.out.println(title);
        pg.pushStyle();
        pg.rectMode(PConstants.CORNER);

        pg.stroke(110);
        pg.fill(255,255,255);
        pg.rect(x, y + 15, pg.textWidth(title) +6, 28, 5);

        pg.textAlign(PConstants.LEFT, PConstants.TOP);
        pg.fill(0);
        pg.text(title, x + 3 , y +18);

        pg.popStyle();
       // pg.text(title,x,y);
    }
}
