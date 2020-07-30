/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Global.Customize;

import javafx.application.Platform;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.SVGPath;

/**
 *
 * @author Md Mehedi Hasan
 */
public class SVGView extends StackPane{
    private SVGPath svg;
    private double width;
    private double height;
    private String name;
    
    public SVGView(){
        name = "IDEA_BULB";
        svg = new SVGPath();
        svg.setContent(SVG.valueOf(name).path);
        width = svg.maxWidth(-1);
        height = svg.maxHeight(width);
        this.getChildren().add(svg);
        
        setScaling();
    }
    public SVGView(String svgName) {
        this();
        name = svgName;
        svg.setContent(SVG.valueOf(name).path);
    }
    public SVGView(SVG svg){
        this(svg.toString());
    }
    public SVGView(SVG svg, double width, double height){
        this(svg);
        setPrefSize(width, height);
    }
    public SVGView(String svgName, double width, double height){
        this(svgName);
        setPrefSize(width, height);
    }

    private void setScaling() {
        Platform.runLater(()->{
            double originalWidth = svg.maxWidth(-1);
            double originalHeight = svg.maxHeight(width);
            svg.setScaleX(width/originalWidth);
            svg.setScaleY(width/originalHeight);
        });
    }
    
    public void setPrefSize(double width, double height){
        setPrefWidth(width);
        setPrefHeight(height);
        this.width = width;
        this.height = height;
        setScaling();
    }
    
    public String getSVGName(){
        return name;
    }
    
    public void setIcon(String name){
        this.name = name;
        svg.setContent(SVG.valueOf(name).path);
    }
    
    public void setIcon(SVG icon){
        this.name = icon.name();
        svg.setContent(icon.path);
    }
}
