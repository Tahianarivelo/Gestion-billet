/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

/**
 *
 * @author tahiana
 */
public class Billet_Affiche_PDF {
    String nom_personne;
    String age;
    String num_billet;
    String modifiable;
    String remdoursable;
    String montant;

    public Billet_Affiche_PDF(String nom_personne, String age, String num_billet, String modifiable, String remdoursable, String montant) {
        this.nom_personne = nom_personne;
        this.age = age;
        this.num_billet = num_billet;
        this.modifiable = modifiable;
        this.remdoursable = remdoursable;
        this.montant = montant;
    }

    public String getNom_personne() {
        return nom_personne;
    }

    public void setNom_personne(String nom_personne) {
        this.nom_personne = nom_personne;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getNum_billet() {
        return num_billet;
    }

    public void setNum_billet(String num_billet) {
        this.num_billet = num_billet;
    }

    public String getModifiable() {
        return modifiable;
    }

    public void setModifiable(String modifiable) {
        this.modifiable = modifiable;
    }

    public String getRemdoursable() {
        return remdoursable;
    }

    public void setRemdoursable(String remdoursable) {
        this.remdoursable = remdoursable;
    }

    public String getMontant() {
        return montant;
    }

    public void setMontant(String montant) {
        this.montant = montant;
    }
    
    
}
