/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "emissions")
public class Emission {

    @Id
    private int id;
    private String country;
    private float data; 
    private int year;  // Das neue "year" Feld

    public Emission() {}

    public Emission(int id, String country, float data, int year) {
        this.id = id;
        this.country = country;
        this.data = data;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public float getData() { 
    return data;
}

    public void setData(float data) { 
    this.data = data;
}
    
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Emission [id=" + id + ", country=" + country + ", data=" + data + ", year=" + year + "]"; 
    }
}
