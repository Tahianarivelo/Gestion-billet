/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import accesdb.AccesDb;
import annotation.Colonne;
import annotation.Table;
import dao.DBConnect;
import java.sql.Connection;
import outil.UG;

/**
 *
 * @author tahiana
 */
@Table()
public class Reduction {
    @Colonne(colonne = "id",sequence = "seq_Reduction",identifiant = true,prefixe = "RD")
    String id;
    @Colonne(colonne = "description")
    String description;
    @Colonne(colonne = "age_max")
    Integer age_max;
    @Colonne(colonne = "age_min")
    Integer age_min;
    @Colonne(colonne = "pourcentage")
    double pourcentage;

    public Reduction(){}
    public Reduction(String id, String description, Integer age_max, Integer age_min, double pourcentage) {
        this.id = id;
        this.description = description;
        this.age_max = age_max;
        this.age_min = age_min;
        this.pourcentage = pourcentage;
    }

    //get In base
    public Reduction[] getInBase(String id,String description,String afterWhere,Connection connex) throws Exception  {
        AccesDb ac = new DBConnect().accesDb();
        Reduction[] res = new Reduction[0];
        boolean test = false;
        if(connex == null){
            test = true;
            connex = ac.connect();
        }
        try{    
            String apWhere = " id "+UG.transform(id)+" and description"+UG.transform(description);
            if(!afterWhere.equals("")){
                apWhere = apWhere+" and "+afterWhere;
            }
             res =(Reduction[]) ac.find(this,"Reduction"," and " +apWhere, 0, 0,false,connex);
        }
        catch(Exception ex){
            throw ex;
        }
        finally{
            if(connex != null && test == true){
                connex.close();
            }
        }
        return res;
    }
    
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAge_max() {
        return age_max;
    }

    public void setAge_max(Integer age_max) {
        this.age_max = age_max;
    }

    public Integer getAge_min() {
        return age_min;
    }

    public void setAge_min(Integer age_min) {
        this.age_min = age_min;
    }

    public double getPourcentage() {
        return pourcentage;
    }

    public void setPourcentage(double pourcentage) {
        this.pourcentage = pourcentage;
    }
    
}
