package com.toast.game.langton;

import java.awt.Point;
import java.awt.geom.Point2D;

import com.toast.game.common.Direction;
import com.toast.game.common.XmlUtils;
import com.toast.game.engine.Game;
import com.toast.game.engine.actor.Actor;
import com.toast.game.engine.message.Message;
import com.toast.game.engine.message.Messenger;
import com.toast.xml.XmlNode;
import com.toast.xml.exception.XmlFormatException;

public class Ant extends Actor
{
   public Ant(String id)
   {
      super(id);
      
      gridPosition = new Point(0, 0);
      
      direction = Direction.UP;
   }
   
   public Ant(XmlNode node) throws XmlFormatException
   {
      super(node);

      gridPosition = new Point(0, 0);
      if (node.hasChild("gridPosition"))
      {
         gridPosition = XmlUtils.getPoint(node.getChild("gridPosition"));
      }
      
      direction = Direction.UP;
      
      Messenger.register(this);
   }
   
   Point getGridPosition()
   {
      return (gridPosition);
   }
   
   void setGridPosition(
      Point gridPosition)
   {
      this.gridPosition = gridPosition;
   }
   
   public Point2D.Double getPosition()
   {
      Point2D.Double position = new Point2D.Double(0, 0);
      
      Grid grid = getGrid();
      
      if (grid != null)
      {
         position = new Point2D.Double((gridPosition.x * grid.getCellDimension().width),
                                       (gridPosition.y * grid.getCellDimension().height));
      }
      
      return (position);
   }
   
   private void langtonAlgorithm()
   {
      Grid grid = getGrid();
      
      if (grid != null)
      {
         if (grid.getCell(gridPosition.x,  gridPosition.y))
         {
            turn(CLOCKWISE);
            
            grid.toggleCell(gridPosition.x,  gridPosition.y);
            
            move();
         }
         else
         {
            turn(COUNTERCLOCKWISE);
            
            grid.toggleCell(gridPosition.x,  gridPosition.y);
            
            move();
         }
      }
   }
   
   private void turn(boolean clockwise)
   {
      switch (direction)
      {
         case UP:
         {
            direction = clockwise ? Direction.RIGHT : Direction.LEFT;
            break;
         }
         
         case DOWN:
         {
            direction = clockwise ? Direction.LEFT : Direction.RIGHT;
            break;
         }
         
         case LEFT:
         {
            direction = clockwise ? Direction.UP : Direction.DOWN;
            break;
         }
         
         case RIGHT:
         {
            direction = clockwise ? Direction.DOWN : Direction.UP;
            break;
         }
         
         default:
         {
            break;
         }
      }
   }
   
   private void move()
   {
      switch (direction)
      {
         case UP:
         {
            gridPosition.y--;
            break;
         }
         
         case DOWN:
         {
            gridPosition.y++;
            break;
         }
         
         case LEFT:
         {
            gridPosition.x--;
            break;
         }
         
         case RIGHT:
         {
            gridPosition.x++;
            break;
         }
         
         default:
         {
            break;
         }
      }
      
      gridPosition.x = Math.max(gridPosition.x,  0);
      gridPosition.y = Math.max(gridPosition.y,  0);
      
      Grid grid = getGrid();
      if (grid != null)
      {
         gridPosition.x = Math.min(gridPosition.x, (grid.getGridDimension().width - 1));
         gridPosition.y = Math.min(gridPosition.y,  (grid.getGridDimension().height - 1));
      }
   }
   
   private Grid getGrid()
   {
      if (grid == null)
      {
         grid = (Grid)Game.getActor("grid");
      }
      
      return (grid);
   }
   
   static final boolean CLOCKWISE = true;
   
   static final boolean COUNTERCLOCKWISE = false;
   
   private Point gridPosition;
   
   private Direction direction;
   
   Grid grid;
   
   public void queueMessage(Message message)
   {
      if (message.getMessageId().equals("nextStep"))
      {
         langtonAlgorithm();
      }
   }
   
   
}
