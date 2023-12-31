package com.booking.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "databaseSequences")
public class DatabaseSequence {
	
	@Transient
	//for auto generating id  11 line 
	public static final String SEQUENCE_NAME = "users_sequence";

	@Id
	private String id;
	private int seq;

	public DatabaseSequence() {
		super();
	}

	public DatabaseSequence(int seq) {
		super();
		this.seq = seq;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	@Override
	public String toString() {
		return "DatabaseSequence [id=" + id + ", seq=" + seq + "]";
	}

}
