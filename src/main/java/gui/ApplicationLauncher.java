	package gui;

import java.awt.Color;
import java.net.URL;
import java.util.Locale;

import javax.swing.UIManager;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Driver;
import businessLogic.BLFacade;
import businessLogic.BLFacadeFactory;
import businessLogic.BLFacadeImplementation;

public class ApplicationLauncher { 
	
	
	
	public static void main(String[] args) {

		ConfigXML c=ConfigXML.getInstance();
	
		System.out.println(c.getLocale());
		
		Locale.setDefault(new Locale(c.getLocale()));
		
		System.out.println("Locale: "+Locale.getDefault());
		
	    Driver driver=new Driver("driver3@gmail.com","Test Driver","123123", 111);

		


		try {
			
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		
			BLFacade appFacadeInterface = BLFacadeFactory.createBLFacade(); //Sorkuntza eskatu
			
			MainDriverGUI.setBussinessLogic(appFacadeInterface);
			
		}catch (Exception e) {
			//a.jLabelSelectOption.setText("Error: "+e.toString());
			//a.jLabelSelectOption.setForeground(Color.RED);	
			
			System.out.println("Error in ApplicationLauncher: "+e.toString());
		}
		//a.pack();
		LanguageGUI a=new LanguageGUI(0);
		a.setVisible(true);

	}

}
