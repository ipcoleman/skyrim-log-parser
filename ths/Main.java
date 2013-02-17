package ths;

import java.io.File;
import java.util.Scanner;

public class Main {

	private static Scanner input;

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
		input = new Scanner(System.in);
		
		
		
		/* TESTING */
//		String pattern = "(?i)(<title.*?>)(.+?)(</title>)";
//		String example = "<title>you should get this</title>";
//		String updated = example.replaceAll(pattern, "$2"); 
//		System.out.print(updated);
//		System.exit(0);	
		
		
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
							for(File roleFile : playTypeDir.listFiles())
							{
								parser = new Parser(logRoot + subjectDir.getName() + "/self/" + roleFile.getName());
								System.out.println("FILE NAME: " + roleFile.getPath());
								parser.parse();	
//								parser.printIntervalOfPlayerMoveEvents();
								System.out.println("FINISHED FILE: " + roleFile.getPath());
							}
						}
												
						/* enter 'role' dir */
						if(playTypeDir.getName().equals("role"))
						{
							/* parse role files */
							for(File roleFile : playTypeDir.listFiles())
							{
								parser = new Parser(logRoot + subjectDir.getName() + "/role/" + roleFile.getName());
								System.out.println("FILE NAME: " + roleFile.getPath());
								parser.parse();	
//								parser.printIntervalOfPlayerMoveEvents();
								System.out.println("FINISHED FILE: " + roleFile.getPath());
							}
						}
						
						
						
						/* ask if we should continue */
						System.out.print("Continue (y/n)? > ");
						String choice = input.nextLine();
						// exit program
						if(choice.equals("n"))
							System.exit(0);
					}
				}
					
					
			}
		}
		
							
	}
}
