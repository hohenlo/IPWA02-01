/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */



import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.NoResultException;

import java.io.Serializable;

@Named
@SessionScoped
public class LoginController implements Serializable {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String login() {
        try {
            User user = em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                          .setParameter("username", username)
                          .getSingleResult();

            if (user != null && user.getPassword().equals(password)) {
                return "editEmissions.xhtml?faces-redirect=true";
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falscher Benutzername oder Passwort", null));
                return null;
            }
        } catch (NoResultException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falscher Benutzername oder Passwort", null));
            return null;
        }
    }
}
