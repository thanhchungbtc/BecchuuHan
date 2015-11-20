package com.btc.supports;

import de.javasoft.plaf.synthetica.*;

import javax.swing.*;

public class Config {
   public static String HonShaBangou = "00357518";
   public static String UserID;
   public static String UserName;
   public static String RoleID = "IP";
   public static String Password;
   public static void setLookAndField() {
      try {
         //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//			 UIManager.setLookAndFeel(SyntheticaBlackEyeLookAndFeel.class.getName());
//         UIManager.setLookAndFeel(SyntheticaWhiteVisionLookAndFeel.class.getName());
         UIManager.setLookAndFeel(SyntheticaBlueMoonLookAndFeel.class.getName());
//         UIManager.setLookAndFeel(SyntheticaAluOxideLookAndFeel.class.getName());
//         UIManager.setLookAndFeel(SyntheticaBlackStarLookAndFeel.class.getName());
//         UIManager.setLookAndFeel(SyntheticaBlueIceLookAndFeel.class.getName());
         SyntheticaBlackStarLookAndFeel.setFont("Dialog", 12);
         //UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
//		} catch (ClassNotFoundException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} catch (InstantiationException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} catch (IllegalAccessException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
      } catch (UnsupportedLookAndFeelException e1) {
         // TODO Auto-generated catch block
         e1.printStackTrace();
      } catch (IllegalAccessException e) {
         e.printStackTrace();
      } catch (InstantiationException e) {
         e.printStackTrace();
      } catch (ClassNotFoundException e) {
         e.printStackTrace();
      }
   }
}
