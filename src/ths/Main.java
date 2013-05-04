package src.ths;

import java.io.File;
import java.io.IOException;
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
		BFIScorer bfiRole = new BFIScorer();
		bfiRole.connectToDatabase();
		BFIScorer bfiSelf = new BFIScorer();
		bfiSelf.connectToDatabase();
		
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
								
								/* print BFI score */
//								BFIScorer bfiRole = new BFIScorer(roleFile.getName() + "_role");
								try {
									bfiRole.setFileName(roleFile.getName() + "_role");
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								int subjectID = Integer.parseInt(subjectDir.getName());
								bfiRole.getPersonalityIndexResults(subjectID);
								bfiRole.printBFIScoresToCsv(subjectID);
								
								System.out.println("FINISHED FILE: "
										+ roleFile.getPath());

								 parser.printIntervalOfPlayerMoveEvents();
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
								
								/* print BFI score */
//								BFIScorer bfiSelf = new BFIScorer(roleFile.getName() + "_self");
								try {
									bfiSelf.setFileName(roleFile.getName() + "_self");
								} catch (IOException e) {
									e.printStackTrace();
								}
								int subjectID = Integer.parseInt(subjectDir.getName());
								bfiSelf.getPersonalityIndexResults(subjectID);
								bfiSelf.printBFIScoresToCsv(subjectID);
								
								// parser.printIntervalOfPlayerMoveEvents();
								System.out.println("FINISHED FILE: "
										+ roleFile.getPath());

								// parser.printIntervalOfPlayerMoveEvents();
							}
						}
					}
				}
			}							
		}
	}
}
