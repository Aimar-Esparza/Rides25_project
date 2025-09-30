package domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Entity
public class Admin extends User implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public Admin() {
		super();
	}

	public Admin(String email, String username, String password, double money) {
		super(email, username, password, money);
	}
	
	public String toString(){
		return super.toString();
	}
		
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
	
}
