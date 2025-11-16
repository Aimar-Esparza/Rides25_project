package adapters;

import javax.swing.JFrame;

import businessLogic.BLFacade;
import businessLogic.BLFacadeFactory;
import domain.Driver;

public class AdapterMain {

	public static void main(String[]	args)	{
		//the	BL	is	local
		try {
		boolean isLocal =	true;
		BLFacade	blFacade =	BLFacadeFactory.createBLFacade();
		Driver	d= (Driver) blFacade.login("driver1@gmail.com", "444");
		DriverTable	dt=new	DriverTable(d);
		dt.setVisible(true);
		dt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}catch (Exception e) {
			System.out.println("Error in ApplicationLauncher: "+e.toString());
		}
	}

}
