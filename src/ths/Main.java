package src.ths;

import java.io.File;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

	private static Scanner input;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String logRoot = "logs/phase2/";
		File[] files = new File(logRoot).listFiles();
		Parser parser;
		input = new Scanner(System.in);
		
		/* iterate over all log files */
		for (File subjectDir : files) {
			if (subjectDir.isDirectory()) {
				/* iterate over role & self directories for subject id */
				for (File playTypeDir : subjectDir.listFiles()) {
					if (playTypeDir.isDirectory()) {
						/* enter 'self' dir */
						if (playTypeDir.getName().equals("self")) {
							/* parse self files */
							for (File roleFile : playTypeDir.listFiles()) {
								parser = new Parser(logRoot
										+ subjectDir.getName() + "/self/"
										+ roleFile.getName());
								/*
								 * set name of file for parser to output to file
								 * w/ same name
								 */
								parser.setFileName(roleFile.getName() + "_self");
								System.out.println("FILE NAME: "
										+ roleFile.getPath());
								try {
//									parser.parse();
								} catch (Exception e) {
									e.printStackTrace();
									System.exit(0);
								}
								System.out.println("FINISHED FILE: "
										+ roleFile.getPath());

								// parser.printIntervalOfPlayerMoveEvents();
							}
						}

						/* enter 'role' dir */
						if (playTypeDir.getName().equals("role")) {
							/* parse role files */
							for (File roleFile : playTypeDir.listFiles()) {
								parser = new Parser(logRoot
										+ subjectDir.getName() + "/role/"
										+ roleFile.getName());
								/*
								 * set name of file for parser to output to file
								 * w/ same name
								 */
								parser.setFileName(roleFile.getName() + "_role");
								System.out.println("FILE NAME: "
										+ roleFile.getPath());
								try {
//									parser.parse();
								} catch (Exception e) {
									e.printStackTrace();
									System.exit(0);
								}
								// parser.printIntervalOfPlayerMoveEvents();
								System.out.println("FINISHED FILE: "
										+ roleFile.getPath());

								// parser.printIntervalOfPlayerMoveEvents();
							}
						}

						/* ask if we should continue */
						// System.out.print("Continue (y/n)? > ");
						// String choice = input.nextLine();
						// // exit program
						// if(choice.equals("n"))
						// System.exit(0);
					}
				}

			}
			
			/* print BFI score */
			BFIScorer bfi = new BFIScorer();
			bfi.connectToDatabase();
			int subjectID = Integer.parseInt(subjectDir.getName());
			bfi.getPersonalityIndexResults(subjectID);	
		}
	}
}
