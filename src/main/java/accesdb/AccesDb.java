package accesdb;

import annotation.Colonne;
import annotation.Table;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;

import org.postgresql.util.PGInterval;
import org.w3c.dom.Document;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
public class AccesDb 
{
    String sgbd;
    String user;
    String password;
    String port;
    String host;
    String database;
    boolean zeroCondition = false;
    int valeurParDefautInt = 0;
    double valeurParDefautDouble = 0;
    float valeurParDefautFloat = 0;
    static HashMap<String, Object[]> cache = new HashMap<String, Object[]>();
    
    public AccesDb(String sgbd,String user,String password,String port,String host,String database)
    {
        this.sgbd =sgbd;
        this.user = user;
        this.password= password;
        this.port = port;
        this.host = host;
        this.database = database;
    }
    
    public AccesDb(String path) throws Exception
    {
        try
        {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder;
            Document document;

            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.parse(path);
            this.sgbd = document.getElementsByTagName("sgbd").item(0).getFirstChild().getNodeValue();
            this.user = document.getElementsByTagName("user").item(0).getFirstChild().getNodeValue();
            this.password = document.getElementsByTagName("password").item(0).getFirstChild().getNodeValue().trim();
            this.port = document.getElementsByTagName("port").item(0).getFirstChild().getNodeValue();
            this.host = document.getElementsByTagName("host").item(0).getFirstChild().getNodeValue();
            this.database = document.getElementsByTagName("database").item(0).getFirstChild().getNodeValue();
        }
        catch(Exception e)
        {
            throw e;
        }
    }
    
    public Connection connect() throws Exception
    {
        try
        {
            String className = "";
            Connection connection = null;
            switch (sgbd)
            {
                case "postgresql":
                    className = "org.postgresql.Driver";
                    break;
                case "mysql":
                    className = "com.mysql.jdbc.Driver";
                    break;
                default:
                    throw new Exception("SGBD non pris en charge");
            }
            String url = "jdbc:" + sgbd + "://" + host + ":" + port + "/" + database;
            Class.forName(className);
            connection = DriverManager.getConnection(url, user, password);
            return connection;
        }
        catch(Exception e)
        {
            throw e;
        }
    }

    public int getValeurParDefautInt()
    {
        return valeurParDefautInt;
    }

    public void setValeurParDefautInt(int valeurParDefautInt)
    {
        this.valeurParDefautInt = valeurParDefautInt;
    }

    public double getValeurParDefautDouble()
    {
        return valeurParDefautDouble;
    }

    public void setValeurParDefautDouble(double valeurParDefautDouble)
    {
        this.valeurParDefautDouble = valeurParDefautDouble;
    }

    public float getValeurParDefautFloat()
    {
        return valeurParDefautFloat;
    }
    
    public void setValeurParDefautFloat(float valeurParDefautFloat)
    {
        this.valeurParDefautFloat = valeurParDefautFloat;
    }    
    
    public Connection connect(String bdd, String user, String password, String port, String host, String database) throws Exception
    {
        try
        {
            Connection connection = null;
            String className = null;
            switch (bdd)
            {
                case "postgresql":
                    className = "org.postgresql.Driver";
                    break;
                case "mysql":
                    className = "com.mysql.jdbc.Driver";
                    break;
                default:
                    throw new Exception("SGBD non pris en charge");
            }
            this.sgbd = bdd;
            String url = "jdbc:" + bdd + "://" + host + ":" + port + "/" + database;
            Class.forName(className);
            connection = DriverManager.getConnection(url, user, password);
            return connection;
        }
        catch(Exception e)
        {
            throw e;
        }
    }
    
    public void setZeroCondition(boolean condition)
    {
        this.zeroCondition = condition;
    }
    
    private String firstCap(String str) 
    {
        String result = "";
        result = str.substring(0,1).toUpperCase() + str.substring(1);
        return result;
    }
    
    private boolean champNull(Field champ, Object obj) throws Exception
    {
        String nomChamp = this.firstCap(champ.getName());
        Class c = obj.getClass();
        Method method = c.getMethod("get" + nomChamp);
        if(method.getReturnType().getName().equals("int"))
        {
            if((int) method.invoke(obj) == this.getValeurParDefautInt())
            {
                return true;
            }
        }
        else if(method.getReturnType().getName().equals("double"))
        {
            if((double) method.invoke(obj) == this.getValeurParDefautDouble())
            {
                return true;
            }
        }
        else if(method.getReturnType().getName().equals("float"))
        {
            if((float) method.invoke(obj) == this.getValeurParDefautFloat())
            {
                return true;
            }
        }
        if(method.getReturnType().getName().contains("."))
        {
            if(method.invoke(obj) == null)
            {
                return true;
            }
        }
        return false;
    }
    
    private String getStringValueField(Field field, Object o) throws Exception
    {
        String result = "";
        Class c = o.getClass();
        String champ = firstCap(field.getName());
        Method method = c.getMethod("get" + champ);
        if(method.getReturnType().getName().contains("Date"))
        {
            java.util.Date date = (java.util.Date)method.invoke(o);
            result += "'" + date.toString() + "'";
        }
        else if(method.getReturnType().getName().contains("String"))
        {
            result += "'" + method.invoke(o) + "'";
        }
        else if(method.getReturnType().getName().contains("Timestamp"))
        {
            result += "'" + method.invoke(o) + "'";
        }
        else
        {
            result += method.invoke(o);
        }
        return result;
    }
    
    private Object getValueField(ResultSet res, Field field) throws Exception
    {
        try
        {
            Object object = new Object();
            String retour = field.getType().getName();
            Colonne colonne = (Colonne)field.getAnnotation(Colonne.class);
            String nomColonne = colonne.colonne();
            if(retour.contains("String"))
            {
                object = res.getString(nomColonne);
            }
            else if(retour.equals("int") || retour.equals("java.lang.Integer"))
            {
                object = res.getInt(nomColonne);
            }
            else if(retour.equals("float") || retour.equals("java.lang.Float"))
            {
                object = res.getFloat(nomColonne);
            }
            else if(retour.equals("double")|| retour.equals("java.lang.Double"))
            {
                object = res.getDouble(nomColonne);
            }
            else if(retour.contains("Date"))
            {
                object = res.getDate(nomColonne);
            }
            else if(retour.contains("Timestamp"))
            {
                object = res.getTimestamp(nomColonne);
            }
            else if(retour.contains("LocalTime"))
            {
                object = LocalTime.parse(res.getTime(nomColonne).toString());
            }
            else if(retour.equals("boolean") || retour.equals("java.lang.Boolean"))
            {
                object = res.getBoolean(nomColonne);
            }
            else if(retour.equals("PGInterval") || retour.equals("org.postgresql.util.PGInterval"))
            {
                object =(PGInterval) res.getObject(nomColonne);
            }
            return object;
        }
        catch(Exception e)
        {
            throw e;
        }
    }
    
    private Object[] getData(ResultSet res, Class c, List<Field> field, Connection connection) throws Exception
    {
        try
        {
            Object result = null;
            res.last();
            int ligne = res.getRow();
            res.beforeFirst();
            result = Array.newInstance(c, ligne);
            Object[] nullTab = new Object[0];
            Constructor constructor = c.getConstructor();
            int j = 0;
            while(res.next())
            {
                Object tmp = constructor.newInstance(nullTab);
                for(int i = 0; i < field.size(); i++)
                {
                    Colonne colonne = (Colonne)field.get(i).getAnnotation(Colonne.class);
                    String nomColonne = "";
                    if(colonne != null)
                    {
                        nomColonne = colonne.colonne();
                    }
                    String champ = firstCap(field.get(i).getName());
                    String nomMethode = "set" + champ;
                    Method methode = c.getMethod(nomMethode, field.get(i).getType());
                    if(colonne != null && !nomColonne.equals(""))
                    {          
                        methode.invoke(tmp, getValueField(res, field.get(i)));
                    }
                    else
                    {
                        if(field.get(i).getType().getName().equals("int") || field.get(i).getType().getName().equals("java.lang.Integer"))
                        {
                            methode.invoke(tmp, 0);
                        }
                        else if(field.get(i).getType().getName().equals("double") || field.get(i).getType().getName().equals("java.lang.Double") ||
                                field.get(i).getType().getName().equals("float") || field.get(i).getType().getName().equals("java.lang.Float"))
                        {
                            methode.invoke(tmp, 0.0);
                        }
                        else
                        {
                            methode.invoke(tmp, new Object[1]);
                        }
                    }
                }
                Array.set(result, j, tmp);
                j++;
            }
            return (Object[])result;
        }
        catch(Exception e)
        {
            throw e;
        }
    }
    
    private void setPreparedStatement(PreparedStatement ps, List<Field> field, Object o) throws Exception
    {
        try
        {
            int j = 1;
            Class c = o.getClass();
            for(int i = 0; i < field.size(); i++)
            {
                Colonne annotation = (Colonne)field.get(i).getAnnotation(Colonne.class);
                String colonne = "";
                if(annotation != null)
                {
                    colonne = annotation.colonne();
                }
                if(!champNull(field.get(i), o) && annotation != null && !colonne.equals(""))
                {
                    String champ = firstCap(field.get(i).getName());
                    Method method = c.getMethod("get" + champ); 
                    if(field.get(i).getType().getName().contains("String"))
                    {
                        ps.setString(j, (String) method.invoke(o));
                    }
                    else if(field.get(i).getType().getName().contains("int") || field.get(i).getType().getName().contains("java.lang.Integer"))
                    {
                        ps.setInt(j, (int) method.invoke(o));
                    }
                    else if(field.get(i).getType().getName().contains("double") || field.get(i).getType().getName().contains("java.lang.Double"))
                    {
                        ps.setDouble(j, (double) method.invoke(o));
                    }
                    else if(field.get(i).getType().getName().contains("float") || field.get(i).getType().getName().contains("java.lang.Float"))
                    {
                        ps.setFloat(j, (float) method.invoke(o));
                    }
                    else if(field.get(i).getType().getName().contains("Date"))
                    {
                        ps.setDate(j, (Date) method.invoke(o));
                    }
                    else if(field.get(i).getType().getName().contains("Timestamp"))
                    {
                        ps.setTimestamp(j, (Timestamp) method.invoke(o));
                    }
                    else if(field.get(i).getType().getName().contains("boolean"))
                    {
                        ps.setBoolean(j, (boolean) method.invoke(o));
                    }
                    j++;
                }
            }
        }
        catch(Exception e)
        {
            throw e;
        }
    }
    
    private String getPaginationRequete(int limite, int depart, String sql, Connection c) throws Exception
    {
        String bdd = c.getMetaData().getDatabaseProductName().toLowerCase();
        String result = "";
        if(limite < 0 || depart < 0)
        {
            throw new Exception("Les parametres de pagination ne doivent pas etre negative");
        }
        if(bdd.contains("postgresql"))
        {
            if(limite != 0)
            {
                result = "LIMIT " + limite + " OFFSET " + depart + ""; 
            }
            else if(limite == 0 && depart != 0)
            {
                result = " OFFSET "+depart;
            }
            else 
            {
            	result = "";
            }
        }
        else if(bdd.contains("mysql") || bdd.contains("mariadb"))
        {
            if(limite != 0)
            {
                result = "LIMIT " + depart + ", " + limite + "";
            }
            else if(limite == 0)
            {
                result = "";
            }
        }
        else
        {
            if(limite != 0)
            {
                result = "SELECT * FROM (SELECT a.*, ROWNUM num FROM ("+sql+") a WHERE ROWNUM < "+(depart + limite)+") WHERE num >= "+ depart;
            }
            else
            {
                result = "";
            }
        }
        return result;
    }

    /**
     *
     * @param o
     * @param afterWhere
     * @param limitePagination
     * @param departPagination
     * @param isCache si on veut mettre les donnees en cache
     * @param c
     * @return
     * @throws Exception
     */
    public Object[] find(Object o, String table, String afterWhere, int limitePagination, int departPagination, boolean isCache, Connection c)throws Exception
    { 
        ResultSet rs = null;
        PreparedStatement statement = null;
        try
        {    
            String sql = "SELECT * FROM";
            Class classe = o.getClass(); 
            Table annotationTable = (Table)classe.getAnnotation(Table.class);
            String nomTable = "";
            if(table == null || table.equals(""))
            {
            	nomTable = annotationTable.nom();
            }    
            else
            {
            	nomTable = table;
            }
            sql += " " + nomTable + " WHERE 1 = 1";
            String sqlVerification = sql;
            List<Field> field = new ArrayList();
            Class superClass = classe;
            while(!superClass.getName().equals("java.lang.Object"))
            {
                Field[] fieldTmp = superClass.getDeclaredFields();
                for(int i = 0; i < fieldTmp.length; i++)
                {
                    field.add(fieldTmp[i]);
                }
                superClass = superClass.getSuperclass();
            }
            for(int i = 0; i < field.size(); i++)
            {
                Colonne annotation = (Colonne)field.get(i).getAnnotation(Colonne.class);
                String colonne = "";
                if(annotation != null)
                {
                    colonne = annotation.colonne();
                }
                if(!champNull(field.get(i), o) && annotation != null && !colonne.equals(""))
                {             
                    String whereVerification = "" + annotation.colonne() + " = " + getStringValueField(field.get(i), o);
                    sqlVerification += " AND " + whereVerification;
                    String where = "" + annotation.colonne() + " = " + "?";
                    sql += " AND " + where; 
                }
            }
            if(afterWhere == null)
            {
                afterWhere = "";
            }
            sql += " " + afterWhere;
            sqlVerification += " " + afterWhere;
            String pagination = getPaginationRequete(limitePagination, departPagination, sql, c);
            sql += " " + pagination;
            sqlVerification += " " + pagination;
            String bdd = c.getMetaData().getDatabaseProductName().toLowerCase();
            if(bdd.contains("oracle"))
            {
                sql = pagination;
            }
            if(isCache)
            {
                if(AccesDb.cache.containsKey(sqlVerification))
                {
                    System.out.println("Donn�es pris dans le cache");
                    return cache.get(sqlVerification);
                }
            }
            System.out.println(sql);
            statement = c.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            setPreparedStatement(statement, field, o);
            rs = statement.executeQuery();
            Object[] result = getData(rs, classe, field, c);
            if(isCache)
            {
                cache.put(sqlVerification, result);
            }
            return result;
        }
        catch(Exception e)
        {
            throw e;
        }
        finally
        {
            if(rs != null) rs.close();
            if(statement != null) statement.close();
        }
    }
    
     private String[] keysCache() {
    	String[] res = new String[cache.size()];
    	int i = 0;
        for (HashMap.Entry<String,Object[]> entry : cache.entrySet())
        {
           res[i] = entry.getKey();
           //entry.getValue();
           i++;
        }
    	return res;
    }
    
    private void remove_request_in_cache(String table) {
    	String[] keys = this.keysCache();
    	for(int i=0; i<keys.length; i++) {
    		if(keys[i].split(" ")[3].equals(table)) {
    			cache.remove(keys[i]);
    		}
    	}
    }
    
    public boolean TestValeurParDefaut(Field f,Object o) throws Exception {
    	boolean temp = true;
    	if(f.getType().getName().equals("double")) {
            if((double) o.getClass().getMethod("get"+firstCap(f.getName()),new Class[0]).invoke(o) == 0) {
                if(this.valeurParDefautDouble == 0){
                    temp = false;
                }
            }
    	}
    	else if(f.getType().getName().equals("int")) {
    	    if((int) o.getClass().getMethod("get"+firstCap(f.getName()),new Class[0]).invoke(o) == 0){
                if(this.valeurParDefautInt == 0) {
                    temp = false;
                }
            }
    	}
    	else if(f.getType().getName().equals("float")) {
    	    if((float) o.getClass().getMethod("get"+firstCap(f.getName()),new Class[0]).invoke(o) == 0){
                if(this.valeurParDefautFloat == 0) {
                    temp = false;
                }
            }
    	}
    	else {
    		temp = true;
    	}
    	return temp;
    }
    
    public List<Field> Attribut_Qui_a_Colonne(List<Field> toutAttribut){
    	List<Field> res = new ArrayList<Field>();
    	for(int i=0; i<toutAttribut.size(); i++) {
    		if(toutAttribut.get(i).getAnnotation(Colonne.class) != null) {
    			res.add(toutAttribut.get(i));
    		}
    	}
    	return res;
    }
    
    
    public void insert(Object obj,boolean seq,Connection connex) throws Exception {
        int res = 0;
        String nomTable = obj.getClass().getAnnotation(Table.class).nom();
        String requete = "INSERT INTO "+nomTable+" (";
        String Values = "VALUES(";
        PreparedStatement ps = null;
        try {
                connex.setAutoCommit(false);
                Class c = obj.getClass();
                Field[] attribus = c.getDeclaredFields();
                List<Field> totalField = new ArrayList();
                for(int i=0; i<attribus.length; i++) {
                	totalField.add(attribus[i]);
                }
                if(!c.getSuperclass().getName().equals("java.lang.Object")){
                	Field[] ettribuSupClasse = c.getSuperclass().getDeclaredFields();
                	for(int i=0; i<ettribuSupClasse.length; i++) {
                		totalField.add(ettribuSupClasse[i]);
                    }
                }
                
                //Verification annotation field
                totalField = this.Attribut_Qui_a_Colonne(totalField);
                
                Colonne tempAnnot = null;
                for(int i=0; i<totalField.size(); i++) {
                    tempAnnot =(Colonne) totalField.get(i).getAnnotation(Colonne.class);
                    if(seq && !tempAnnot.sequence().equals("")) {
                            Values += "'"+tempAnnot.prefixe()+"'||nextVal(?)";
                            if(i == totalField.size()-1) {
                                    requete += tempAnnot.colonne()+") ";
                                    Values += ")";
                            }
                            else {
                                    requete += tempAnnot.colonne() + ",";
                                    Values += ",";
                            }
                    }
                    else {
                        Values += "?";
                        if(i == totalField.size()-1) {
                                requete += tempAnnot.colonne()+") ";
                                Values += ")";
                        }
                        else {
                                requete += tempAnnot.colonne() + ",";
                                Values += ",";
                        }
                    }	
                }
                requete += Values;
                ps = connex.prepareStatement(requete);
                int j = 1;
                for(int i=0; i<totalField.size(); i++) {
                    if(seq && !totalField.get(i).getAnnotation(Colonne.class).sequence().equals("")) {
                            ps.setObject(j,totalField.get(i).getAnnotation(Colonne.class).sequence());
                            j++;
                    }
                    else{
                    	if(totalField.get(i).getType().getName().equals("java.util.Date")) {
                    		java.util.Date date = (java.util.Date) c.getMethod("get"+firstCap(totalField.get(i).getName()),new Class[0]).invoke(obj);
                    		ps.setObject(j,new java.sql.Date(date.getTime()));
                    	}
                    	else {
                    		ps.setObject(j,c.getMethod("get"+firstCap(totalField.get(i).getName()),new Class[0]).invoke(obj));
                    	}       
                        j++;
                    }
                }
                ps.executeUpdate();
                System.out.println(ps.toString());
                this.remove_request_in_cache(nomTable);
            }
            catch(Exception e) {
                    throw e;
            }
            finally {
            	if(ps != null)
                {
                    ps.close();
                }
            }
	}
	
	public void update(Object obj,String afterWhere,Connection connex) throws Exception {
        String nomTable = obj.getClass().getAnnotation(Table.class).nom();
        String requete = "UPDATE "+nomTable+" SET ";
        PreparedStatement ps = null;
        try {
            connex.setAutoCommit(false);
            Class c = obj.getClass();
            Field[] attribus = c.getDeclaredFields();
            List<Field> totalField = new ArrayList();
            for(int i=0; i<attribus.length; i++) {
                totalField.add(attribus[i]);
            }
            if(!c.getSuperclass().getName().equals("java.lang.Object")){
                Field[] ettribuSupClasse = c.getSuperclass().getDeclaredFields();
                for(int i=0; i<ettribuSupClasse.length; i++) {
                    totalField.add(ettribuSupClasse[i]);
                }
            }

            //Verification annotation field
            totalField = this.Attribut_Qui_a_Colonne(totalField);

            boolean placervirg = false;
            Colonne tempAnnot = null;
            int indexIdent = -1;
            int nombreIndent = 0;
            for(int i=0; i<totalField.size(); i++) {
                tempAnnot = totalField.get(i).getAnnotation(Colonne.class);
                if(this.TestValeurParDefaut(totalField.get(i),obj)) {
                    if(c.getMethod("get"+firstCap(totalField.get(i).getName()),new Class[0]).invoke(obj) != null) {
                        if(!tempAnnot.identifiant()) {
                            if(placervirg) {
                                requete += ","+tempAnnot.colonne()+" = ?";
                            }
                            else {
                                requete += tempAnnot.colonne()+" = ?";
                                placervirg = true;
                            }
                        }
                        else {
                            indexIdent = i;
                            nombreIndent++;
                        }
                    }
                }
            }
            if(indexIdent < 0) {
                throw new Exception("l'indentifiant n'as pas été spécifié.");
            }
            if(nombreIndent > 1){
                throw new Exception("Une classe ne doit pas avoir plusieurs indentifiants.");
            }

            requete += " WHERE "+totalField.get(indexIdent).getAnnotation(Colonne.class).colonne()+" = ?";
            if(afterWhere != null || afterWhere.equals("") == false) {
            	requete += afterWhere;
            }

            ps = connex.prepareStatement(requete);
            int j = 1;
            for(int i=0; i<totalField.size(); i++) {
                tempAnnot = totalField.get(i).getAnnotation(Colonne.class);
                if(this.TestValeurParDefaut(totalField.get(i),obj)) {
                    if(c.getMethod("get"+firstCap(totalField.get(i).getName()),new Class[0]).invoke(obj) != null) {
                        if(!tempAnnot.identifiant()) {
                            if(totalField.get(i).getType().getName().equals("java.util.Date")) {
                                java.util.Date date = (java.util.Date) c.getMethod("get"+firstCap(totalField.get(i).getName()),new Class[0]).invoke(obj);
                                ps.setObject(j,new java.sql.Date(date.getTime()));
                            }
                            else {
                                ps.setObject(j,c.getMethod("get"+firstCap(totalField.get(i).getName()),new Class[0]).invoke(obj));
                            }
                            j++;
                        }
                    }
                }
            }
            ps.setObject(j,c.getMethod("get"+firstCap(totalField.get(indexIdent).getName()),new Class[0]).invoke(obj));

            ps.executeUpdate();
            System.out.println(ps.toString());
            ps.close();
            this.remove_request_in_cache(nomTable);
        }
        catch(Exception e) {
            throw e;
        }
        finally {
            if(ps != null)
            {
                ps.close();
            }
        }
    }

	
	public void delete(Object obj,Connection connex) throws Exception  {
         String nomTable = obj.getClass().getAnnotation(Table.class).nom();
         String requete = "DELETE FROM "+nomTable+" WHERE ";
         PreparedStatement ps = null;
         try {
                 connex.setAutoCommit(false);
                 Class c = obj.getClass();
                 Field[] attribus = c.getDeclaredFields();
                 List<Field> totalField = new ArrayList();
                 for(int i=0; i<attribus.length; i++) {
                 	totalField.add(attribus[i]);
                 }
                 if(!c.getSuperclass().getName().equals("java.lang.Object")){
                 	Field[] ettribuSupClasse = c.getSuperclass().getDeclaredFields();
                 	for(int i=0; i<ettribuSupClasse.length; i++) {
                 		totalField.add(ettribuSupClasse[i]);
                     }
                 }
                 
                 //Verification annotation field
                 totalField = this.Attribut_Qui_a_Colonne(totalField);
                 
                 Colonne tempAnnot = null;
                 int indexIdent = -1;
                 int nombreIndent = 0;
                 for(int i=0; i<totalField.size(); i++) {
                	 if(totalField.get(i).getAnnotation(Colonne.class) != null){
                		 tempAnnot = totalField.get(i).getAnnotation(Colonne.class);
                    	 if(tempAnnot.identifiant()) {
                    		 indexIdent = i;
                    		 nombreIndent++;
                    		 requete += ""+tempAnnot.colonne()+" = ?";
                    	 } 
                	 }
                 }
                 if(indexIdent < 0) {
                     throw new Exception("l'indentifiant n'as pas été spécifié.");
                 }
	             if(nombreIndent > 1){
	             	throw new Exception("Une classe ne doit pas avoir plusieurs indentifiants.");
	             }
	             
            	 ps = connex.prepareStatement(requete);
            	 ps.setObject(1,c.getDeclaredMethod("get"+firstCap(totalField.get(indexIdent).getName()),new Class[0]).invoke(obj));
                 
                 ps.executeUpdate();
                 ps.close();
                 this.remove_request_in_cache(nomTable);
         }
         catch(Exception e) {
                 throw e;
         }
         finally {
        	 if(ps != null)
                {
                    ps.close();
                }
         }
	}
}
