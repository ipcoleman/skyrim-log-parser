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
		
		/* iterate over all log files */
		for (File subjectDir : files) 
		{
			if(subjectDir.isDirectory())
			{
				/* iterate over role & self directories for subject id */
				for (File playTypeDir : subjectDir.listFiles()) 
				{					
					if(playTypeDir.isDirectory())
					{
						/* enter 'self' dir */
						if(playTypeDir.getName().equals("self"))
						{
							/* parse self files */
							for(File selfFile : playTypeDir.listFiles())
							{
								parser = new Parser(logRoot + subjectDir.getName() + "/self/" + selfFile.getName());
								System.out.println("FILE NAME: " + selfFile.getAbsolutePath());
								parser.parse();	
								parser.printIntervalOfPlayerMoveEvents();
							}
						}
												
						/* enter 'role' dir */
						if(playTypeDir.getName().equals("role"))
						{
							/* parse role files */
							for(File roleFile : playTypeDir.listFiles())
							{
								parser = new Parser(logRoot + subjectDir.getName() + "/role/" + roleFile.getName());
								System.out.println("FILE NAME: " + roleFile.getAbsolutePath());
								parser.parse();	
								parser.printIntervalOfPlayerMoveEvents();
							}
						}
					}
				}
					
					
			}
		}
		
							
	}
}
