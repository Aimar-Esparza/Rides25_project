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

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class MoneyTransaction implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id 
	@GeneratedValue
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@XmlID
	private Integer transactionId;
	private boolean action;
	private double amount;
	private String reason;
	private Date date;
	@XmlIDREF
	private Booking book;
	@ManyToOne
	private User user;
	
	public MoneyTransaction(){
		super();
	}
	
	public MoneyTransaction(double amount, boolean action, Date date, User user, String reason){
		this.amount = amount;
		this.action = action;
		this.date = date;
		this.user = user;
		this.reason = reason;
		this.book = null;
	}
	
	public MoneyTransaction(double amount, boolean action, Date date, User user, String reason, Booking book){
		this.amount = amount;
		this.action = action;
		this.date = date;
		this.user = user;
		this.reason = reason;
		this.book = book;
	}

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public boolean isAction() {
		return action;
	}

	public void setAction(boolean action) {
		this.action = action;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	//public Ride getRide() {
		//return ride;
	//}

	//public void setRide(Ride ride) {
		//this.ride = ride;
	//}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public String toString(){
		String p;
		if (action) {
			p = "+";
		}else {
			p = "-";
		}
		String ema = date + "" + p + "" + amount + "" + reason;
		return ema;
	}
}
