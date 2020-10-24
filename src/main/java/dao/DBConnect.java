package dao;

import accesdb.AccesDb;

public class DBConnect {
	public  DBConnect() {
		
	}
	
	public AccesDb accesDb() throws Exception  {
		AccesDb res = null;
		try {
			res = new AccesDb("postgresql", "postgres", "1234", "5432", "localhost", "evaluation2");
		}
		catch(Exception ex) {
			throw ex;
		}
		return res;
	}
}
