/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */


import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.NoResultException;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;

@Named("co2EmissionController")
@RequestScoped
public class Co2EmissionController {
    private String selectedCountry;
    private int selectedYear;
    private float latestEmission;
    private List<String> countries;
    private List<Integer> years;

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    public Co2EmissionController() {
        countries = new ArrayList<>();
        years = new ArrayList<>();
        selectedCountry = null;
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 0; i < 20; i++) {
            years.add(currentYear - i);
        }
    }

    @PostConstruct
    public void init() {
        loadCountriesAndEmissions();
    }

    @Transactional
    public void loadCountriesAndEmissions() {
        try {
            List<Emission> results = em.createQuery("SELECT e FROM Emission e", Emission.class).getResultList();
            for (Emission e : results) {
                if (!countries.contains(e.getCountry())) {
                    countries.add(e.getCountry());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getSelectedCountry() {
        return selectedCountry;
    }

    public void setSelectedCountry(String selectedCountry) {
        this.selectedCountry = selectedCountry;
    }

    public int getSelectedYear() {
        return selectedYear;
    }

    public void setSelectedYear(int selectedYear) {
        this.selectedYear = selectedYear;
    }

    public float getLatestEmission() {
        return latestEmission;
    }

    public void setLatestEmission(float latestEmission) {
        this.latestEmission = latestEmission;
    }

    public List<String> getCountries() {
        return countries;
    }

    public List<Integer> getYears() {
        return years;
    }

    public void loadEmissionsForSelectedYearAndCountry() {
        Emission emission;
        try {
            emission = em.createQuery("SELECT e FROM Emission e WHERE e.country = :country AND e.year = :year", Emission.class)
                      .setParameter("country", selectedCountry)
                      .setParameter("year", selectedYear)
                      .getSingleResult();
        } catch (NoResultException e) {
            latestEmission = 0;
            return;
        }
        latestEmission = emission.getData();
    }

    @Transactional
    public void saveEmissions() {
        Emission emission;
        try {
            emission = em.createQuery("SELECT e FROM Emission e WHERE e.country = :country AND e.year = :year", Emission.class)
                      .setParameter("country", selectedCountry)
                      .setParameter("year", selectedYear)
                      .getSingleResult();
        } catch (NoResultException e) {
            emission = new Emission();
            emission.setCountry(selectedCountry);
            emission.setYear(selectedYear);
            em.persist(emission);
        }
        emission.setData(latestEmission);
        em.flush();
    }
}
