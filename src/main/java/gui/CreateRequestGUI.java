package gui;

import businessLogic.BLFacade;
import configuration.UtilDate;

import com.toedter.calendar.JCalendar;

import domain.Passenger;
import domain.Ride;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.text.DateFormat;
import java.util.*;
import java.util.List;

import javax.swing.table.DefaultTableModel;


public class CreateRequestGUI extends JFrame {
	private static final long serialVersionUID = 1L;


	private JComboBox<String> jComboBoxOrigin = new JComboBox<String>();
	DefaultComboBoxModel<String> originLocations = new DefaultComboBoxModel<String>();

	private JComboBox<String> jComboBoxDestination = new JComboBox<String>();
	DefaultComboBoxModel<String> destinationCities = new DefaultComboBoxModel<String>();
	
	DefaultComboBoxModel<String> val = new DefaultComboBoxModel<String>();

	private JLabel jLabelOrigin = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.LeavingFrom"));
	private JLabel jLabelDestination = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.GoingTo"));
	private final JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.RideDate"));

	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("SeeBookingGUI.Back"));

	// Code for JCalendar
	private JCalendar jCalendar1 = new JCalendar();
	private Calendar calendarAnt = null;
	private Calendar calendarAct = null;

	private List<Date> datesWithRidesCurrentMonth = new Vector<Date>();

	private JTextField textFieldOrigin;
	private JTextField textFieldDestination;
	private final ButtonGroup buttonGroupDestination = new ButtonGroup();
	private final ButtonGroup buttonGroupOrigin = new ButtonGroup();
	private final JLabel jLabelValoration = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ValorationGUI.MinValoration")); //$NON-NLS-1$ //$NON-NLS-2$
	private JTextField textFieldSeats;
	private final ButtonGroup buttonGroupAutoBuy = new ButtonGroup();
	
	
	public CreateRequestGUI(Passenger p)
	{
		Passenger passenger = p;
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(700, 500));
		this.setTitle("Create Request");
		jLabelEventDate.setBounds(new Rectangle(426, 33, 140, 25));

		this.getContentPane().add(jLabelEventDate, null);

		jButtonClose.setBounds(new Rectangle(6, 419, 130, 30));

		jButtonClose.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			} 
		});
		
		
		BLFacade facade = MainDriverGUI.getBusinessLogic();
		List<String> origins=facade.getDepartCities();
		
		for(String location:origins) originLocations.addElement(location);
		
		jLabelOrigin.setBounds(new Rectangle(6, 89, 108, 20));
		jLabelDestination.setBounds(6, 153, 108, 16);
		getContentPane().add(jLabelOrigin);

		getContentPane().add(jLabelDestination);

		jComboBoxOrigin.setModel(originLocations);
		jComboBoxOrigin.setBounds(new Rectangle(128, 89, 122, 20));
		

		List<String> aCities=facade.getDestinationCities((String)jComboBoxOrigin.getSelectedItem());
		for(String aciti:aCities) {
			destinationCities.addElement(aciti);
		}
		
		jComboBoxOrigin.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				destinationCities.removeAllElements();
				BLFacade facade = MainDriverGUI.getBusinessLogic();

				List<String> aCities=facade.getDestinationCities((String)jComboBoxOrigin.getSelectedItem());
				for(String aciti:aCities) {
					destinationCities.addElement(aciti);
				}

				
			}
		});
 

		jComboBoxDestination.setModel(destinationCities);
		jComboBoxDestination.setBounds(new Rectangle(128, 151, 122, 20));
		jComboBoxDestination.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				paintDaysWithEvents(jCalendar1,datesWithRidesCurrentMonth,	new Color(210,228,238));

				BLFacade facade = MainDriverGUI.getBusinessLogic();

				datesWithRidesCurrentMonth=facade.getThisMonthDatesWithRides((String)jComboBoxOrigin.getSelectedItem(),(String)jComboBoxDestination.getSelectedItem(),jCalendar1.getDate());
				paintDaysWithEvents(jCalendar1,datesWithRidesCurrentMonth,Color.CYAN);

			}
		});

		this.getContentPane().add(jButtonClose, null);
		this.getContentPane().add(jComboBoxOrigin, null);

		this.getContentPane().add(jComboBoxDestination, null);


		jCalendar1.setBounds(new Rectangle(426, 69, 225, 150));


		// Code for JCalendar
		jCalendar1.addPropertyChangeListener(new PropertyChangeListener()
		{
			public void propertyChange(PropertyChangeEvent propertychangeevent)
			{

				if (propertychangeevent.getPropertyName().equals("locale"))
				{
					jCalendar1.setLocale((Locale) propertychangeevent.getNewValue());
				}
				else if (propertychangeevent.getPropertyName().equals("calendar"))
				{
					calendarAnt = (Calendar) propertychangeevent.getOldValue();
					calendarAct = (Calendar) propertychangeevent.getNewValue();
					

					
					DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar1.getLocale());

					int monthAnt = calendarAnt.get(Calendar.MONTH);
					int monthAct = calendarAct.get(Calendar.MONTH);

					if (monthAct!=monthAnt) {
						if (monthAct==monthAnt+2) {
							// Si en JCalendar está 30 de enero y se avanza al mes siguiente, devolvería 2 de marzo (se toma como equivalente a 30 de febrero)
							// Con este código se dejará como 1 de febrero en el JCalendar
							calendarAct.set(Calendar.MONTH, monthAnt+1);
							calendarAct.set(Calendar.DAY_OF_MONTH, 1);
						}						

						jCalendar1.setCalendar(calendarAct);

					}
				}
			} 
			
		});

		this.getContentPane().add(jCalendar1, null);
		datesWithRidesCurrentMonth=facade.getThisMonthDatesWithRides((String)jComboBoxOrigin.getSelectedItem(),(String)jComboBoxDestination.getSelectedItem(),jCalendar1.getDate());
		paintDaysWithEvents(jCalendar1,datesWithRidesCurrentMonth,Color.CYAN);
		
		JComboBox<String> jComboBoxValoration = new JComboBox<String>();
		jComboBoxValoration.setBounds(new Rectangle(108, 151, 122, 20));
		jComboBoxValoration.setBounds(128, 211, 61, 20);
		jComboBoxValoration.setModel(val);
		for(int i = 0; i <= 10; i++) {
			val.addElement(Integer.toString(i));
		}
		jComboBoxValoration.setSelectedIndex(0);
		getContentPane().add(jComboBoxValoration);

		JLabel jLabelAutoBuy = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRequestGUI.jLabelAutoBuy")); //$NON-NLS-1$ //$NON-NLS-2$
		jLabelAutoBuy.setBounds(6, 317, 108, 16);
		getContentPane().add(jLabelAutoBuy);
		
		JRadioButton rdbtYes = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("ValorationGUI.Yes")); //$NON-NLS-1$ //$NON-NLS-2$
		buttonGroupAutoBuy.add(rdbtYes);
		rdbtYes.setBounds(128, 315, 96, 21);
		getContentPane().add(rdbtYes);
		
		JRadioButton rdbtNo = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("ValorationGUI.No")); //$NON-NLS-1$ //$NON-NLS-2$
		buttonGroupAutoBuy.add(rdbtNo);
		rdbtNo.setBounds(237, 315, 96, 21);
		getContentPane().add(rdbtNo);

		JRadioButton rdbtnOrigin1 = new JRadioButton(); //$NON-NLS-1$ //$NON-NLS-2$
		rdbtnOrigin1.setSelected(true);
		buttonGroupOrigin.add(rdbtnOrigin1);
		rdbtnOrigin1.setBounds(249, 89, 21, 21);
		getContentPane().add(rdbtnOrigin1);
		
		JRadioButton rdbtnDestination1 = new JRadioButton((String) null);
		rdbtnDestination1.setSelected(true);
		buttonGroupDestination.add(rdbtnDestination1);
		rdbtnDestination1.setBounds(249, 151, 21, 21);
		getContentPane().add(rdbtnDestination1);
		
		textFieldOrigin = new JTextField();
		textFieldOrigin.setText(""); //$NON-NLS-1$ //$NON-NLS-2$
		textFieldOrigin.setBounds(285, 90, 96, 19);
		getContentPane().add(textFieldOrigin);
		textFieldOrigin.setColumns(10);
		
		textFieldDestination = new JTextField();
		textFieldDestination.setText(""); //$NON-NLS-1$ //$NON-NLS-2$
		textFieldDestination.setColumns(10);
		textFieldDestination.setBounds(285, 152, 96, 19);
		getContentPane().add(textFieldDestination);
		
		JRadioButton rdbtnOrigin2 = new JRadioButton((String) null);
		buttonGroupOrigin.add(rdbtnOrigin2);
		rdbtnOrigin2.setBounds(382, 89, 21, 21);
		getContentPane().add(rdbtnOrigin2);
		
		JRadioButton rdbtnDestination2 = new JRadioButton((String) null);
		buttonGroupDestination.add(rdbtnDestination2);
		rdbtnDestination2.setBounds(382, 151, 21, 21);
		getContentPane().add(rdbtnDestination2);
		jLabelValoration.setBounds(6, 213, 108, 16);
		
		getContentPane().add(jLabelValoration);
		
		JLabel jLabelSeats = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("FindRidesGUI.NPlaces")); //$NON-NLS-1$ //$NON-NLS-2$
		jLabelSeats.setBounds(6, 272, 108, 16);
		getContentPane().add(jLabelSeats);
		
		textFieldSeats = new JTextField();
		textFieldSeats.setColumns(10);
		textFieldSeats.setBounds(128, 271, 96, 19);
		getContentPane().add(textFieldSeats);
		
		JButton jButtonRequest = new JButton((String) null);
		jButtonRequest.setBounds(new Rectangle(6, 419, 130, 30));
		jButtonRequest.setBounds(546, 419, 130, 30);
		jButtonRequest.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateRequest.Request"));
		jButtonRequest.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				String or;
				String de;
				if(rdbtnOrigin1.isSelected()) {
					or = (String)jComboBoxOrigin.getSelectedItem();
				}else{
					or = textFieldOrigin.getText();
				}
				if(rdbtnDestination1.isSelected()) {
					de = (String)jComboBoxDestination.getSelectedItem();
				}else{
					de = textFieldDestination.getText();
				}
				int val = Integer.parseInt((String)jComboBoxValoration.getSelectedItem());
				Date date = UtilDate.trim(jCalendar1.getDate());
				if (or != null && or != "" && de != null && de != "" && date.after(new Date())) {
					try {
						int kant = Integer.parseInt((String)textFieldSeats.getText());
						MainDriverGUI.getBusinessLogic().createRequest(passenger.getEmail(), or, de, val, kant, date, rdbtYes.isSelected());
					}catch(NumberFormatException er) {
						JOptionPane.showMessageDialog(jButtonRequest, ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.error"));
					}
				}else {
					JOptionPane.showMessageDialog(jButtonRequest, ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.error"));
				}
				
			}
		});
		getContentPane().add(jButtonRequest);
	}
	
	
	
	public static void paintDaysWithEvents(JCalendar jCalendar,List<Date> datesWithEventsCurrentMonth, Color color) {
		//		// For each day with events in current month, the background color for that day is changed to cyan.


		Calendar calendar = jCalendar.getCalendar();

		int month = calendar.get(Calendar.MONTH);
		int today=calendar.get(Calendar.DAY_OF_MONTH);
		int year=calendar.get(Calendar.YEAR);

		calendar.set(Calendar.DAY_OF_MONTH, 1);
		int offset = calendar.get(Calendar.DAY_OF_WEEK);

		//if (Locale.getDefault().equals(new Locale("es")))
			//offset += 4;
		//else
			offset += 5;


		for (Date d:datesWithEventsCurrentMonth){

			calendar.setTime(d);


			// Obtain the component of the day in the panel of the DayChooser of the
			// JCalendar.
			// The component is located after the decorator buttons of "Sun", "Mon",... or
			// "Lun", "Mar"...,
			// the empty days before day 1 of month, and all the days previous to each day.
			// That number of components is calculated with "offset" and is different in
			// English and Spanish
			//			    		  Component o=(Component) jCalendar.getDayChooser().getDayPanel().getComponent(i+offset);; 
			Component o = (Component) jCalendar.getDayChooser().getDayPanel()
					.getComponent(calendar.get(Calendar.DAY_OF_MONTH) + offset);
			o.setBackground(color);
		}

		calendar.set(Calendar.DAY_OF_MONTH, today);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.YEAR, year);


	}
}