package ths;

import java.io.File;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		String line = null;
//		String tag = null;
		String logRoot = "logs/phase2/";
		File[] files = new File(logRoot).listFiles();
		Parser parser;
		
		for (File subjectDir : files) 
		{
			if(subjectDir.isDirectory())
			{
				parser = new Parser(logRoot + subjectDir.getName() + "/self/0.0.log");
				parser.parse();	
				parser.printIntervalOfPlayerMoveEvents();
				
				parser = new Parser(logRoot + subjectDir.getName() + "/role/0.0.log");
				parser.parse();	
				parser.printIntervalOfPlayerMoveEvents();
			}
		}
		
							
	}
}
