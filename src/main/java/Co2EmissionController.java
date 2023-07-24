/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */



import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Named("co2EmissionController")
@RequestScoped
public class Co2EmissionController {
    private String selectedCountry;
    private String latestEmission;
    private List<String> countries;

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    public Co2EmissionController() {
        System.out.println("Co2EmissionController was instantiated.");
        countries = new ArrayList<>();
        selectedCountry = null; 
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
                countries.add(e.getCountry());
                if (selectedCountry == null) {
                    selectedCountry = e.getCountry(); // setze das erste Land als das ausgewählte Land
                    latestEmission = e.getData(); // setze die Emissionen für das ausgewählte Land
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
        updateEmissions();
    }

    public String getLatestEmission() {
        return latestEmission;
    }

    public void setLatestEmission(String latestEmission) {
        if(latestEmission == null || latestEmission.trim().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Emissionsdaten müssen befüllt sein", null));
            return;
        }

        try {
            Double.parseDouble(latestEmission);
        } catch (NumberFormatException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Emissionsdaten müssen eine Zahl sein", null));
            return;
        }

        this.latestEmission = latestEmission;
    }

    public List<String> getCountries() {
        return countries;
    }

    public void updateEmissions() {
        System.out.println("Update emissions method was called.");
        Emission emission = em.createQuery("SELECT e FROM Emission e WHERE e.country = :country", Emission.class)
                              .setParameter("country", selectedCountry)
                              .getSingleResult();
        if (emission != null) {
            latestEmission = emission.getData();
        }
    }

    @Transactional
    public void saveEmissions() {
        System.out.println("Save emissions method was called.");
        Emission emission = em.createQuery("SELECT e FROM Emission e WHERE e.country = :country", Emission.class)
                              .setParameter("country", selectedCountry)
                              .getSingleResult();
        if (emission != null) {
            emission.setData(latestEmission);
            em.persist(emission);
            em.flush();
        }
    }
}
