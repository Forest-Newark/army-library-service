package com.forestnewark.Library.Manager.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Resource {
	
    @Id
    @GeneratedValue
    private Integer id;
    
    @Column
    private String title;
    
    @Column
    private String ensemble;
    
    @Column
    private String instrument;
    
    @Column
    private String location;
    
    @Lob
    private String notes;
    
    public Resource() {}

	public Resource(Integer id, String title, String ensemble, String instrument, String location, String notes) {
		this.id = id;
		this.title = title;
		this.ensemble = ensemble;
		this.instrument = instrument;
		this.location = location;
		this.notes = notes;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the ensemble
	 */
	public String getEnsemble() {
		return ensemble;
	}

	/**
	 * @param ensemble the ensemble to set
	 */
	public void setEnsemble(String ensemble) {
		this.ensemble = ensemble;
	}

	/**
	 * @return the instrument
	 */
	public String getInstrument() {
		return instrument;
	}

	/**
	 * @param instrument the instrument to set
	 */
	public void setInstrument(String instrument) {
		this.instrument = instrument;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}
    
	
    
    
    
    
    

}
