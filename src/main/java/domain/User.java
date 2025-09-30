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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso ({Passenger.class, Driver.class, Admin.class})
@Entity
public abstract class User implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id 
	@XmlID
	private String email;
	private String password;
	private String username;
	private double money;	
	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private List<MoneyTransaction> transactions = new Vector<MoneyTransaction>();
	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private List<Ride> rides=new Vector<Ride>();

	public User() {
		super();
	}

	public User(String email, String username, String password, double money) {
		this.money = money;
		this.email = email;
		this.username = username;
		this.password = password;
	}
	
	public void addTransaction(MoneyTransaction tr) {
		this.transactions.add(tr);
	}
	
	public void removeTransaction(MoneyTransaction tr) {
		this.transactions.remove(tr);
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public String toString(){
		return email+";"+username+";"+money;
	}
	
		
	public List<MoneyTransaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<MoneyTransaction> transactions) {
		this.transactions = transactions;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email != other.email)
			return false;
		return true;
	}

	
	
}
