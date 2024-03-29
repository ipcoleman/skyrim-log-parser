package src.ths;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class BFIScorer {
	
	private Connection connection;
	private Statement statement;
	private ResultSet results;
	/* Hash Maps */
	private HashMap<Integer, String> regularScoreItems;
	private HashMap<Integer, String> reverseScoreItems;
	private String fileName;
	private PrintWriter csvOut;
	/* Arrays */
	int extraversionItems[];
	int extraversionReverseItems[];
	int agreeablenessItems[];
	int agreeablenessReverseItems[];
	int conscientiousnessItems[];
	int conscientiousnessReverseItems[];
	int neuroticismItems[];
	int neuroticismReverseItems[];
	int opennessItems[];
	int opennessReverseItems[];
	/* Total Score Values*/
	int oScore, cScore, eScore, aScore, nScore;
	
	public BFIScorer()
	{
		oScore = 0;
		cScore = 0;
		eScore = 0;
		aScore = 0;
		nScore = 0;
		initScoreItems();
		initReverseScoreItems();
	}
	
	public BFIScorer(String _fileName)
	{
		oScore = 0;
		cScore = 0;
		eScore = 0;
		aScore = 0;
		nScore = 0;
		initScoreItems();
		initReverseScoreItems();
		this.fileName = _fileName;
		
		try {
//			System.out.println("BFI FILENAME: " + this.fileName);
			this.setCsvOut(new PrintWriter(new FileWriter("logs/output/"
					+ this.fileName + ".csv")));
		} catch (IOException e1) {
			e1.printStackTrace();
			return;
		}
	}
	
	public void setFileName(String fName) throws IOException
	{
		this.fileName = fName;		
		this.setCsvOut(new PrintWriter(new FileWriter("logs/output/"
				+ this.fileName + ".csv")));
	}
	
	public PrintWriter getCsvOut() {
		return csvOut;
	}

	public void setCsvOut(PrintWriter csvOut) {
		this.csvOut = csvOut;
	}

	private void initScoreItems()
	{
		this.extraversionItems = new int[]{1, 11, 16, 26, 36};
		this.agreeablenessItems = new int[]{7,  17, 22,  32,  42};
		this.conscientiousnessItems = new int[]{3, 13, 28, 33, 38};
		this.neuroticismItems = new int[]{4, 14, 19, 29, 39};
		this.opennessItems = new int[]{5, 10, 15, 20, 25, 30, 40, 44};
		
		regularScoreItems = new HashMap<Integer, String>();
		for(int i=0; i<extraversionItems.length; i++)
			regularScoreItems.put(extraversionItems[i], "extraversion");
		for(int i=0; i<agreeablenessItems.length; i++)
			regularScoreItems.put(agreeablenessItems[i], "agreeableness");
		for(int i=0; i<conscientiousnessItems.length; i++)
			regularScoreItems.put(conscientiousnessItems[i], "conscientiousness");
		for(int i=0; i<neuroticismItems.length; i++)
			regularScoreItems.put(neuroticismItems[i], "neuroticism");
		for(int i=0; i<opennessItems.length; i++)
			regularScoreItems.put(opennessItems[i], "openness");
	}
	
	private void initReverseScoreItems()
	{
		this.extraversionReverseItems = new int[]{6, 21, 31};
		this.agreeablenessReverseItems = new int[]{2, 12, 27, 37};
		this.conscientiousnessReverseItems = new int[]{2, 12, 27, 37};
		this.neuroticismReverseItems = new int[]{9, 24, 34};
		this.opennessReverseItems = new int[]{35, 41};
		
		reverseScoreItems = new HashMap<Integer, String>();
		for(int i=0; i<extraversionReverseItems.length; i++)
			reverseScoreItems.put(extraversionReverseItems[i], "extraversion");
		for(int i=0; i<agreeablenessReverseItems.length; i++)
			reverseScoreItems.put(agreeablenessReverseItems[i], "agreeableness");
		for(int i=0; i<conscientiousnessReverseItems.length; i++)
			reverseScoreItems.put(conscientiousnessReverseItems[i], "conscientiousness");
		for(int i=0; i<neuroticismReverseItems.length; i++)
			reverseScoreItems.put(neuroticismReverseItems[i], "neuroticism");
		for(int i=0; i<opennessReverseItems.length; i++)
			reverseScoreItems.put(opennessReverseItems[i], "openness");
	}
	
	public HashMap<Integer, String> getReverseScoreItems()
	{
		return this.reverseScoreItems;
	}
	
	public void connectToDatabase()
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ipcolema_db", "ipcolema", "ipcolema");
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
		String query = "";
		String trait = "";
		int score = 0;
		
		eScore = aScore = cScore = nScore = oScore = 0;
		
		try {
			this.statement = this.connection.createStatement();
			query = "SELECT * FROM pi_responses WHERE subject_id = " + userID;
			this.results = statement.executeQuery(query);
			
			while(this.results.next())
			{
				score = this.results.getInt(4);
				/* Look in regular score list */
				trait = regularScoreItems.get(this.results.getInt(3));
				/* Look in reverse score list */
				if(trait == null)
				{
					trait = reverseScoreItems.get(this.results.getInt(3));
					score = (6 - score);
				}
				
				if(trait != null)
				{
					if(trait.equals("extraversion")) eScore += score;
					if(trait.equals("agreeableness")) aScore += score;
					if(trait.equals("conscientiousness")) cScore += score;
					if(trait.equals("neuroticism")) nScore += score;
					if(trait.equals("openness")) oScore += score;
				}
			}
			
//			this.connection.close();
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void printBFIScoresToCsv(int userID)
	{
		csvOut.print(userID + ",");
//		System.out.println("Extraversion for " + userID + ": " + eScore);
		csvOut.print(eScore + ",");
//		System.out.println("Agreeableness for " + userID + ": " + aScore);
		csvOut.print(aScore + ",");
//		System.out.println("Conscientiousness for " + userID + ": " + cScore);
		csvOut.print(cScore + ",");
//		System.out.println("Neuroticism for " + userID + ": " + nScore);
		csvOut.print(nScore + ",");
//		System.out.println("Openness for " + userID + ": " + oScore);
		csvOut.print(oScore + ",");		
//		this.csvOut.close();
	}
	
}
