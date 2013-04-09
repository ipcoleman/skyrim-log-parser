package src.ths;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Set;

public class BFIScorer {
	
	private Connection connection;
	private Statement statement;
	private ResultSet results;
	private HashMap<String, int[]> reverseScoreItems; 
	
	public BFIScorer()
	{
		initReverseScoreItems();
	}
	
	private void initReverseScoreItems()
	{
		reverseScoreItems = new HashMap<String, int[]>();
		reverseScoreItems.put("extraversion", new int[]{ 6, 21, 31 });
		reverseScoreItems.put("agreeableness", new int[]{ 2, 12, 27, 37 });
		reverseScoreItems.put("conscientiousness", new int[]{ 8, 18, 23, 43 });
		reverseScoreItems.put("neuroticism", new int[]{ 9, 24, 34 });
		reverseScoreItems.put("openness", new int[]{ 35, 41 });
	}
	
	public HashMap<String, int[]> getReverseScoreItems()
	{
		return this.reverseScoreItems;
	}
	
	public void connectToDatabase()
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/ipcolema_db", "ipcolema", "ipcolema");
		} catch (ClassNotFoundException e) {
			System.out.println("MySQL driver NOT registered");
			e.printStackTrace();
			return;
		} catch (SQLException e) {
			System.out.println("Connection failed");
			e.printStackTrace();
			return;
		}
	}
	
	public void getPersonalityIndexResults(int userID)
	{
//		Statement statement = null;
//		ResultSet results = null;
		String query = "";
		
		try {
			this.statement = this.connection.createStatement();
			query = "SELECT * FROM pi_responses WHERE subject_id = " + userID;
			this.results = statement.executeQuery(query);
			
			while(this.results.next())
			{
				System.out.println(this.results.getString(1));
				BFIScorer bfi = new BFIScorer();
				HashMap<String, int[]> reverseScoreItems = bfi.getReverseScoreItems();
//				String traits[] = {"openness", "conscientiousness", "extraversion", "agreeableness", "neuroticism"};
				Set<String> keySet = reverseScoreItems.keySet();
				for(int i= 0; i<keySet.size(); i++)
				{
					int nextValueArray[] = reverseScoreItems.get(keySet.iterator().next());   
					for(int j = 0; j < nextValueArray.length; j++)
					{
						System.out.println(nextValueArray[j]);
					}
				
				}
			}
			
			this.connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
}
