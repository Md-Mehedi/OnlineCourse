/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Global.Customize;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialicons.MaterialIcon;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import javafx.application.Platform;
import javafx.scene.layout.StackPane;

/**
 *
 * @author Md Mehedi Hasan
 */
public class Icon extends StackPane{
    public enum IconType{
        FONT_AWESOME_ICON("Font Awesome Icon"),
        MATERIAL_ICON("Material Icon"),
        SVG("SVG");
        private String name;
        IconType(String s){
            this.name = s;
        }
    }
    
    private IconType type;
    private String iconName;
    private SVGView svgView;
    private FontAwesomeIconView faiView;
    private MaterialIconView miView;
    
    private double width;
    private double height;
    
    public Icon(IconType type){
        this.type = type;
        width = 12;
        height = 12;
        if(type == IconType.FONT_AWESOME_ICON){
            faiView = new FontAwesomeIconView();
            this.getChildren().add(faiView);
            faiView.glyphSizeProperty().bind(this.widthProperty());
//            faiView.glyphSizeProperty().bind(this.heightProperty());
//            this.prefHeightProperty().bind(faiView.glyphSizeProperty());
            //ToolKit.setAnchor(faiView, 0, 0, 0, 0);
        }
        else if(type == IconType.MATERIAL_ICON){
            miView = new MaterialIconView();
            this.getChildren().add(miView);
            miView.glyphSizeProperty().bind(this.widthProperty());
            miView.glyphSizeProperty().bind(this.heightProperty());
            //ToolKit.setAnchor(miView, 0, 0, 0, 0);
        }
        else if(type == IconType.SVG){
            svgView = new SVGView();
            this.getChildren().add(svgView);
            svgView.prefWidthProperty().bind(this.widthProperty());
            svgView.prefHeightProperty().bind(this.heightProperty());
            //ToolKit.setAnchor(svgView, 0, 0, 0, 0);
        }
        setPrefSize(width, height);
    }
    
    public Icon(IconType type, String iconName){
        this(type);
        this.iconName = iconName;
        if(type == IconType.FONT_AWESOME_ICON){
            faiView.setIcon(FontAwesomeIcon.valueOf(iconName));
        }
        else if(type == IconType.MATERIAL_ICON){
            miView.setIcon(MaterialIcon.valueOf(iconName));
        }
        else if(type == IconType.SVG){
            svgView.setIcon(iconName);
        }
    }
    public Icon(IconType type, String iconName, double width, double height){
        this(type,iconName);
        setGlyphSize(Math.min(width, height));
        setPrefSize(width, height);
    }
    
    public Icon(FontAwesomeIcon icon){
        this(IconType.FONT_AWESOME_ICON, icon.name());
    }
    public Icon(FontAwesomeIcon icon, double size){
        this(icon);
        setGlyphSize(size);
        setPrefSize(size, size);
    }
    public Icon(MaterialIcon icon){
        this(IconType.MATERIAL_ICON, icon.name());
    }
    public Icon(MaterialIcon icon, double size){
        this(icon);
        setGlyphSize(size);
        setPrefSize(size, size);
    }
    
    public Icon(SVG icon){
        this(IconType.SVG, icon.name());
    }
    public Icon(SVG icon, double width, double height){
        this(icon);
        setPrefSize(width, height);
    }
    
    public IconType getType() {
        return type;
    }
    
    public String getIconName(){
        return iconName;
    }
    public void setSize(double size){
        //setGlyphSize(size);
        setPrefSize(size, size);
    }
    private void setGlyphSize(double size){
        Platform.runLater(()->{
            if(type == IconType.FONT_AWESOME_ICON) faiView.setGlyphSize(size);
            if(type == IconType.MATERIAL_ICON) miView.setGlyphSize(size);
        });
    }
}
