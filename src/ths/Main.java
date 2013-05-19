package src.ths;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String logRoot = "logs/phase2/";
		String outputRoot = "logs/output/";
		String logPath = "";
		String outputPath = "";
		String playType = "";
		File[] files = new File(logRoot).listFiles();
		PrintWriter csvOut = null;
		Parser parser;
		BFIScorer bfi = new BFIScorer();
		bfi.connectToDatabase();
		
		try {
			outputPath = outputRoot + "all.csv";
			csvOut = new PrintWriter(new FileWriter(
					outputPath));
			csvOut.println("PRTCPNT,NONE,RUN,SPRINT,SNEAK,EXTRAV,AGREE,CONSC,NEUROT,OPEN");
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		/* iterate over all log files */
		for (File subjectDir : files) {
			if (subjectDir.isDirectory()) {
				/* iterate over role & self directories for subject id */
				for (File playTypeDir : subjectDir.listFiles()) {
					if (playTypeDir.isDirectory()) {

						for (File logFile : playTypeDir.listFiles()) {

							playType = playTypeDir.getName();
							/* set proper log file directory (self vs role) */
							logPath = logRoot + subjectDir.getName() + "/"
									+ playType + "/" + logFile.getName();
							parser = new Parser(logPath);
//							outputPath = outputRoot + "/" + logFile.getName()
//									+ "_" + playType + ".csv";
							/*
							 * set name of file for parser to output to file w/
							 * same name
							 */
							// parser.setFileName(logFile.getName() + "_"
							// + playType);
							System.out.println("FILE NAME: "
									+ logFile.getPath());

							bfi.setCsvOut(csvOut);
							parser.setCsvOut(csvOut);

							/* PARSER */
							try {
								parser.parse();
							} catch (Exception e) {
								e.printStackTrace();
								System.exit(0);
							}

							/* BFI SCORER */
							// try {
							// bfi.setFileName(logFile.getName() + "_" +
							// playType);
							// } catch (IOException e) {
							// e.printStackTrace();
							// }

							int subjectID = Integer.parseInt(subjectDir
									.getName());
							bfi.getPersonalityIndexResults(subjectID);
							bfi.printBFIScoresToCsv(subjectID);

							System.out.println("FINISHED FILE: "
									+ logFile.getPath());

							parser.printIntervalOfPlayerMoveEvents();

							
						}
					}
				}
			}
		}
		csvOut.close();
	}
}
