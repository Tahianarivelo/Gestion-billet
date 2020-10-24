/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package outil;

import accesdb.AccesDb;
import dao.DBConnect;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author tahiana
 */
public class UG {
    
    //verification format mail
    public static boolean emailValidationFormat(String email){
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    
    //verification format password
    public static boolean passwordValidationFoemat(int lettre,int chiffre,int caracterSp,String password){
        String regexChiffre = "[0-9]";
        String regexLettre = "[a-zA-Z]";
        String regexCS = "[^A-Za-z0-9]";
        int countChiffre = 0;
        int countLettre = 0;
        int countCS = 0;
        Pattern pattern = Pattern.compile(regexChiffre);
        Matcher matcher = pattern.matcher(password);
        while(matcher.find()) 
        {
            countChiffre++;
        }
        pattern = Pattern.compile(regexLettre);
        matcher = pattern.matcher(password);
        while(matcher.find()) 
        {
            countLettre++;
        }
        pattern = Pattern.compile(regexCS);
        matcher = pattern.matcher(password);
        while(matcher.find()) 
        {
            countCS++;
        }
        System.out.println("Lettre:"+countLettre+",Chiffre:"+countLettre+",CS:"+countCS);
        boolean test = true;
        if(countLettre < lettre || countChiffre < chiffre || countCS < caracterSp){
            test = false;
        }
        return test;
    }
    
    //hashage 
    //type: SHA-256,SHA-1....
    public static String Hashage(String value,String type) throws NoSuchAlgorithmException{
        MessageDigest digest = MessageDigest.getInstance(type);
        byte[] hash = digest.digest(value.getBytes(StandardCharsets.UTF_8));
        
        // Convert byte array into signum representation  
        BigInteger number = new BigInteger(1, hash);  
  
        // Convert message digest into hex value  
        StringBuilder hexString = new StringBuilder(number.toString(16));  
  
        // Pad with leading zeros 
        while (hexString.length() < 32)  
        {  
            hexString.insert(0, '0');  
        }  
  
        return hexString.toString();
    }
    
    //transform requete
    public static String transform(Object o){
        String res = "";
        if(o == null){
            res = " like '%'";
        }else{
            res = " like '"+o+"'";
        }
        return res;
    }
    
    //date sql add day
    public static Date getDate(Date ts, int day) {
        ts.setDate(ts.getDate() + day);
        return ts;
    }
    
    //get next val
   public static String getsequence(String nom_seq, Connection c) throws Exception {
        String seq = null;
        String requete = " SELECT nextval('" + nom_seq + "') as nb";
        ResultSet rs2;
        try (Statement st2 = c.createStatement()) {
            rs2 = st2.executeQuery(requete);
            while (rs2.next()) {
                seq = rs2.getString("nb");
                break;
            }
        }
        rs2.close();
        return seq;
    }
   
   //count
   public static int Count(String nomTable,String apresWhere, Connection connex) throws Exception {
        AccesDb ac = new DBConnect().accesDb();
        boolean test = false;
         int res = 0;
        if(connex == null){
            test = true;
            connex = ac.connect();
        }
        try{    
            String requete = "SELECT COUNT(*) as nb FROM "+nomTable;
            if(apresWhere.equals("") == false){
                requete += " where "+apresWhere;
            }
            System.out.println(requete);
            ResultSet rs2;
            try (Statement st2 = connex.createStatement()) {
                rs2 = st2.executeQuery(requete);
                while (rs2.next()) {
                    res = rs2.getInt("nb");
                    break;
                }
            }
            rs2.close();
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
    
    //Modele de Code fonction BDD
    //Modification ou creation
    public void nom_fonction(Connection connex) throws Exception  {
        AccesDb ac = new DBConnect().accesDb();
        boolean test = false;
        if(connex == null){
            test = true;
            connex = ac.connect();
        }
        try{
            if(test) connex.setAutoCommit(false);
            
            //code
            
            if(test) connex.commit();
        }
        catch(Exception ex){
            if(test) connex.rollback();
            throw ex;
        }
        finally{
            if(connex != null && test == true){
                connex.close();
            }
        }
    }
    
    //modele de get in bdd
    public Object nom_fonction_get(Connection connex) throws Exception  {
        AccesDb ac = new DBConnect().accesDb();
        boolean test = false;
        if(connex == null){
            test = true;
            connex = ac.connect();
        }
        try{    
            //code
        }
        catch(Exception ex){
            throw ex;
        }
        finally{
            if(connex != null && test == true){
                connex.close();
            }
        }
        return null;
    }
    
    
    //modele fonction pagination
     public Object nom_fonction_pagination(int page,int nb_elem,Connection connex) throws Exception  {
        AccesDb ac = new DBConnect().accesDb();
        boolean test = false;
        if(connex == null){
            test = true;
            connex = ac.connect();
        }
        try{    
            
        }
        catch(Exception ex){
            throw ex;
        }
        finally{
            if(connex != null && test == true){
                connex.close();
            }
        }
        return null;
    }
}
