/*
 * Created on 2023-05-24 ( 16:59:34 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.3.0
 */
package com.lct.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

/**
 * JPA entity class for "Category"
 *
 * @author Telosys
 *
 */
@Entity
@Table(name="category", schema="dbo", catalog="chicken_app" )
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    //--- ENTITY PRIMARY KEY 
    @Id
    @Column(name="categoryid", nullable=false, length=45)
    private String     categoryid ;

    //--- ENTITY DATA FIELDS 
    @Column(name="categoryname", nullable=false, length=45)
    private String     categoryname ;

    @Column(name="notes", length=100)
    private String     notes ;


    //--- ENTITY LINKS ( RELATIONSHIP )
    @OneToMany(mappedBy="category")
    private List<Product> listOfProduct ; 


    /**
     * Constructor
     */
    public Category() {
		super();
    }
    
    //--- GETTERS & SETTERS FOR FIELDS
    public void setCategoryid( String categoryid ) {
        this.categoryid = categoryid ;
    }
    public String getCategoryid() {
        return this.categoryid;
    }

    public void setCategoryname( String categoryname ) {
        this.categoryname = categoryname ;
    }
    public String getCategoryname() {
        return this.categoryname;
    }

    public void setNotes( String notes ) {
        this.notes = notes ;
    }
    public String getNotes() {
        return this.notes;
    }

    //--- GETTERS FOR LINKS
    public List<Product> getListOfProduct() {
        return this.listOfProduct;
    } 

    //--- toString specific method
	@Override
    public String toString() { 
        StringBuilder sb = new StringBuilder(); 
        sb.append(categoryid);
        sb.append("|");
        sb.append(categoryname);
        sb.append("|");
        sb.append(notes);
        return sb.toString(); 
    } 

}