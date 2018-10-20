package com.toast.game.langton;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.AffineTransform;

import com.toast.game.common.CoordinatesType;
import com.toast.game.common.XmlUtils;
import com.toast.game.engine.Renderer;
import com.toast.game.engine.actor.Actor;
import com.toast.game.engine.interfaces.Drawable;
import com.toast.game.engine.property.Property;
import com.toast.xml.XmlNode;
import com.toast.xml.exception.XmlFormatException;

public class Grid extends Actor implements Drawable
{
   public Grid(String id,
               Dimension gridDimension,
               Dimension cellDimension,
               Color borderColor,
               Color fillColor)
   {
      super(id);
      
      this.gridDimension = gridDimension;
      this.cellDimension = cellDimension; 
      this.borderColor = borderColor;
      this.fillColor = fillColor;
      
      gridArray = new boolean[gridDimension.width][gridDimension.height];
      
      setDimension(new Dimension((gridDimension.width * cellDimension.width), (gridDimension.height * cellDimension.height)));
   }
   
   public Grid(XmlNode node) throws XmlFormatException
   {
      super(node);
      
      gridDimension = XmlUtils.getDimension(node.getChild("gridDimension"));
      cellDimension = XmlUtils.getDimension(node.getChild("cellDimension"));
      borderColor = com.toast.xml.XmlUtils.getColor(node, "borderColor", Color.WHITE);
      fillColor = com.toast.xml.XmlUtils.getColor(node, "fillColor", Color.BLACK);
      
      gridArray = new boolean[gridDimension.width][gridDimension.height];
      
      setDimension(new Dimension((gridDimension.width * cellDimension.width), (gridDimension.height * cellDimension.height)));
      
      setCell(10, 10, true);
   }
   
   public void draw(Renderer renderer)
   {
      super.draw(renderer);
      
      renderer.draw(this,  new AffineTransform(), 0,  CoordinatesType.WORLD);
   }

   @Override
   public void draw(Graphics graphics)
   {
      for (int x= 0; x < gridDimension.width; x++)
      {
         for (int y = 0; y < gridDimension.height; y++)
         {
            Point position = new Point((x * cellDimension.width), (y * cellDimension.height));
            
            if (getCell(x, y))
            {
               graphics.setColor(fillColor);
               graphics.fillRect(position.x, position.y, cellDimension.width, cellDimension.height);
            }
            
            graphics.setColor(borderColor);
            graphics.drawRect(position.x, position.y, cellDimension.width, cellDimension.height);
         }
      }
   }
   
   public Dimension getCellDimension()
   {
      return (cellDimension);
   }
   
   public Dimension getGridDimension()
   {
      return (gridDimension);
   }
   
   public boolean getCell(int x, int y)
   {
      return (gridArray[x][y]);
   }
   
   public void setCell(int x, int y, boolean value)
   {
      gridArray[x][y] = value;
   }
   
   public boolean toggleCell(int x, int y)
   {
      return (gridArray[x][y] = !gridArray[x][y]);
   }
   
   private Dimension cellDimension;
            
   private Dimension gridDimension;
   
   private boolean[][] gridArray;
   
   private Color borderColor;
   
   private Color fillColor;
   
}
