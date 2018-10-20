package com.toast.game.langton;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JFrame;

import com.toast.game.engine.Game;
import com.toast.game.engine.Scene;
import com.toast.game.engine.message.Message;
import com.toast.game.engine.resource.Resource;
import com.toast.game.engine.resource.ResourceCreationException;
import com.toast.xml.exception.XmlFormatException;
import com.toast.xml.exception.XmlParseException;

public class LangtonsAnt
{
   public static void main(final String args[])
   {
      final Dimension SCREEN_DIMENSION = new Dimension(800, 600);
      
      JFrame frame = new JFrame("Langton's Ant");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize((int)SCREEN_DIMENSION.getWidth(), (int)SCREEN_DIMENSION.getHeight());
      
      // Resources
      try
      {
         Resource.loadResources("/resources");
      }
      catch (ResourceCreationException e)
      {
      }
      
      Game.create("Langton", (int)SCREEN_DIMENSION.getWidth(), (int)SCREEN_DIMENSION.getHeight(), 1);
      
      Scene levelOne = new Scene("level 1");
      
      try
      {
         levelOne.load(Resource.getFile("/resources/scenes/scene_00.xml"));
      }
      catch (IOException | XmlParseException | XmlFormatException e)
      {
         e.printStackTrace();
      }
      
      Game.setCurrentScene(levelOne);
      
      frame.getContentPane().add(Game.getGamePanel(), BorderLayout.CENTER);
      frame.setVisible(true);
      
      Message message = new Message("nextStep", "game", "ant");
      Game.startTimer("antTimer",  50,  true,  message);
      
      Game.play();
   }
}
