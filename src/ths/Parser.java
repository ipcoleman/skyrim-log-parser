package src.ths;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.ListIterator;
import org.joda.time.Interval;

public class Parser {

	private String				fileName;
	private FileInputStream 	fInputStream;
	private DataInputStream 	dInputStream;
	private BufferedReader 		buffReader;
	private String[] 			logTags;
	private int					tagCount;
	private ArrayList<Event>	events;
	private EventFactory		factory;
	private static int			currentLine;
	private PrintWriter			out;
	private PrintWriter			csvOut;
	
	
	public Parser() {
	}
	
	public Parser(String file)
	{
		fileName = file;
		try {			
			fInputStream = new FileInputStream(file);
			dInputStream = new DataInputStream(fInputStream);
			buffReader = new BufferedReader(new InputStreamReader(dInputStream));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		logTags = new String[100];
		tagCount = 0;
		events = new ArrayList<Event>();
		factory = new EventFactory();
		currentLine = 1;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public void parse()
	{
		String line;
		Event e;

		try {
			out = new PrintWriter(new FileWriter("logs/output/" + this.fileName + ".txt"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		while((line = nextLine()) != null)
		{
			System.out.println("Line " + currentLine + ": " + line + "\n");
			e = factory.makeEvent(line);
//			System.out.println(e);
			out.print(e.toString());
			
			events.add(e);
			currentLine++;
		}
		
		printIntervalOfPlayerMoveEvents();
		
		out.close();
		if(csvOut != null)
			csvOut.close();
	}
	
	public String nextLine()
	{
		String line = null;
		
		try {
			line = buffReader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return line;
	}
	
	public boolean containsTag(String tag)
	{	
		try {
			for(int i=0; i<logTags.length; i++)
			{
				if(tag.equals(logTags[i]))
				{
					return true;
				}
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public void addTag(String tag)
	{
		try
		{
			logTags[tagCount++] = tag;
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			e.printStackTrace();
		}
	}
	
	
	public boolean addEvent(Event e)
	{
		return this.events.add(e);
	}
	
	public Interval calcPlayerMoveInterval(Event e)
	{
		Interval i = null;
		Event nextEvent;
		Date nextTimestamp = null;
		int index;
		ListIterator<Event> li;
		
		index = events.indexOf(e);
		if(index >= 0) // object found
		{
			li = events.listIterator(index+1); // iterator over events after e
			while(li.hasNext())
			{
				nextEvent = li.next();
				if(nextEvent instanceof PlayerMoveEvent)
				{
					nextTimestamp = nextEvent.getTimestamp();
					break;
				}
			}
			if(nextTimestamp != null)
				i = new Interval(e.getTimestamp().getTime(), nextTimestamp.getTime());
		}
		
		return i;
	}
	
	
	public void printIntervalOfPlayerMoveEvents()
	{
		Event e;
		Interval i;
		
		ListIterator<Event> li = events.listIterator();
		while(li.hasNext())
		{
			e = li.next();
			if(e instanceof PlayerMoveEvent)
			{
				i = calcPlayerMoveInterval(e);
				System.out.println("PlayerMoveEvent Interval: " + i);
				out.println("PlayerMoveEvent Interval: " + i);
				outputIntervalOfPlayerMoveEvent((PlayerMoveEvent)e, i);
			}
		}
	}
	
	private void outputIntervalOfPlayerMoveEvent(PlayerMoveEvent e, Interval i)
	{
		System.out.println(e.getType() + "," + i.toDurationMillis()/1000);
		
		/* print to file */
		try {
			csvOut = new PrintWriter(new FileWriter("logs/output/" + this.fileName + ".csv"));
			csvOut.println(e.getType() + "," + i.toDurationMillis()/1000);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
}
