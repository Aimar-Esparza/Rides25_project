 package domain;

import java.io.*;
import java.util.Date;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


//@SuppressWarnings("serial")
//@XmlAccessorType(XmlAccessType.FIELD)
//@Entity
public class Valoration implements Serializable {
	//@Id 
	//@GeneratedValue
	//@XmlJavaTypeAdapter(IntegerAdapter.class)
	//@XmlID
	private Integer bookingId;
	private int score;
	private String comment;
	private Boolean done;
	private Booking book;
	
	public Valoration() {
		super();
	}
	
	public Valoration(Booking book, boolean done, int val, String comment) {
		this.score = val;
		this.done = done;
		this.comment = comment;
		this.book = book;
	}
	
	public Integer getBookingId() {
		return bookingId;
	}

	public void setBookingId(Integer bookingId) {
		this.bookingId = bookingId;
	}

	public int getValoration() {
		return score;
	}

	public void setValoration(int valoration) {
		this.score = valoration;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Boolean getDone() {
		return done;
	}

	public void setDone(Boolean done) {
		this.done = done;
	}

	public Booking getBook() {
		return book;
	}

	public void setBook(Booking book) {
		this.book = book;
	}

	public String toString(){
		return done + " " + score + " " + comment;  
	}
	
	
	
}
