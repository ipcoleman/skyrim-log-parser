package src.ths;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String logRoot = "logs/phase2/";
		String logPath = "";
		File[] files = new File(logRoot).listFiles();
		Parser parser;
		BFIScorer bfi = new BFIScorer();
		bfi.connectToDatabase();
//		BFIScorer bfiSelf = new BFIScorer();
//		bfiSelf.connectToDatabase();
		
		/* iterate over all log files */
		for (File subjectDir : files) {
			if (subjectDir.isDirectory()) {
				/* iterate over role & self directories for subject id */
				for (File playTypeDir : subjectDir.listFiles()) {
					if (playTypeDir.isDirectory()) {
						
						for (File logFile : playTypeDir.listFiles()) {
							/* set proper log file directory (self vs role) */
							if (playTypeDir.getName().equals("self")) {
								logPath = logRoot
										+ subjectDir.getName() + "/self/"
										+ logFile.getName();
							}
							else if (playTypeDir.getName().equals("role")) {
								logPath = logRoot
										+ subjectDir.getName() + "/role/"
										+ logFile.getName();
							}						
														
							
								parser = new Parser(logPath);
								/*
								 * set name of file for parser to output to file
								 * w/ same name
								 */
								parser.setFileName(logFile.getName() + "_self");
								System.out.println("FILE NAME: "
										+ logFile.getPath());
								try {
//									parser.parse();
								} catch (Exception e) {
									e.printStackTrace();
									System.exit(0);
								}

								try {
									bfi.setFileName(logFile.getName() + "_role");
								} catch (IOException e) {
									e.printStackTrace();
								}
								int subjectID = Integer.parseInt(subjectDir.getName());
								bfi.getPersonalityIndexResults(subjectID);
								bfi.printBFIScoresToCsv(subjectID);
								
								System.out.println("FINISHED FILE: "
										+ logFile.getPath());

								 parser.printIntervalOfPlayerMoveEvents();
							}
						}

						
						
						/* enter 'role' dir */
//						if (playTypeDir.getName().equals("role")) {
//							/* parse role files */
//							for (File roleFile : playTypeDir.listFiles()) {
//								parser = new Parser(logRoot
//										+ subjectDir.getName() + "/role/"
//										+ roleFile.getName());
//								/*
//								 * set name of file for parser to output to file
//								 * w/ same name
//								 */
//								parser.setFileName(roleFile.getName() + "_role");
//								System.out.println("FILE NAME: "
//										+ roleFile.getPath());
//								try {
////									parser.parse();
//								} catch (Exception e) {
//									e.printStackTrace();
//									System.exit(0);
//								}
//								
//								/* print BFI score */
////								BFIScorer bfiSelf = new BFIScorer(roleFile.getName() + "_self");
//								try {
//									bfiSelf.setFileName(roleFile.getName() + "_self");
//								} catch (IOException e) {
//									e.printStackTrace();
//								}
//								int subjectID = Integer.parseInt(subjectDir.getName());
//								bfiSelf.getPersonalityIndexResults(subjectID);
//								bfiSelf.printBFIScoresToCsv(subjectID);
//								
//								// parser.printIntervalOfPlayerMoveEvents();
//								System.out.println("FINISHED FILE: "
//										+ roleFile.getPath());
//
//								// parser.printIntervalOfPlayerMoveEvents();
//							}
//						}
//					}
				}
			}							
		}
	}
}
