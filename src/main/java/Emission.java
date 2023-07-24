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
    private String data; // Geändert von double year2023 zu String data

    public Emission() {}

    public Emission(int id, String country, String data) {
        this.id = id;
        this.country = country;
        this.data = data;
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

    public String getData() { // Geändert von getYear2023 zu getData
        return data;
    }

    public void setData(String data) { // Geändert von setYear2023 zu setData
        this.data = data;
    }

    @Override
    public String toString() {
        return "Emission [id=" + id + ", country=" + country + ", data=" + data + "]"; // Geändert von year2023 zu data
    }
}