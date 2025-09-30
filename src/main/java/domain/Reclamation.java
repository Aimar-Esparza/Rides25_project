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


@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Reclamation implements Serializable {
	@Id 
	@GeneratedValue
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@XmlID
	private Integer reclamationId;
	private String comment;
	@XmlIDREF
	private Booking book;
	private Date date;
	
	public Reclamation() {
		super();
	}
	
	public Reclamation(Booking book, String comment) {
		this.comment = comment;
		this.book = book;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Booking getBook() {
		return book;
	}

	public void setBook(Booking book) {
		this.book = book;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getReclamationId() {
		return reclamationId;
	}

	public void setReclamationId(Integer reclamationId) {
		this.reclamationId = reclamationId;
	}
	
	public Ride getRide() {
		return book.getRide();
	}
	
	public Passenger getPassenger() {
		return book.getPassenger();
	}
	
	public Driver getDriver() {
		return book.getDriver();
	}

	public String toString(){
		return book + " " + comment;  
	}
	
	
	
}
