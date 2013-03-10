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
import java.util.ListIterator;
import org.joda.time.Interval;

public class Parser {

	private FileInputStream 	fInputStream;
	private DataInputStream 	dInputStream;
	private BufferedReader 		buffReader;
	private String[] 			logTags;
	private int					tagCount;
	private ArrayList<Event>	events;
	private EventFactory		factory;
	private static int			currentLine;
	
	public Parser() {
		// TODO Auto-generated constructor stub
	}
	
	public Parser(String fileName)
	{
		try {
			fInputStream = new FileInputStream(fileName);
			dInputStream = new DataInputStream(fInputStream);
			buffReader = new BufferedReader(new InputStreamReader(dInputStream));
			logTags = new String[100];
			tagCount = 0;
			events = new ArrayList<Event>();
			factory = new EventFactory();
			currentLine = 1;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void parse()
	{
		String line;
		Event e;
		PrintWriter out = null;
		try {
			out = new PrintWriter(new FileWriter("out.txt"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		while((line = nextLine()) != null)
		{
			System.out.println("Line " + currentLine + ": " + line + "\n");
			e = factory.makeEvent(line);
			System.out.println(e);
			out.print(e.toString());
			
			events.add(e);
			currentLine++;
		}
		
		out.close();
	}
	
	public String nextLine()
	{
		String line = null;
		
		try {
			line = buffReader.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	public void addTag(String tag)
	{
		try
		{
			logTags[tagCount++] = tag;
//			System.out.println("Tag [" + tag + "] added");
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			e.printStackTrace();
		}
	}
	
	/*public String getDateTimeAsString(String line)
	{
		int beginIndex, endIndex;
		
		beginIndex = line.indexOf('[') + 1;
		endIndex = line.indexOf(']');
		return line.substring(beginIndex, endIndex);	
	}
	
	public Date getDateTimeAsDate(String line)
	{
		return convertDateTime(getDateTimeAsString(line));	
	}
	
	public Date convertDateTime(String dateString){
	    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy - hh:mm:ssaa");
	    Date convertedDate = new Date();
	    try {
	        convertedDate = dateFormat.parse(dateString);
	    } catch (ParseException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	    return convertedDate;
	}*/
	
	
	
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
			while((nextEvent = li.next()) != null)
			{
				if(nextEvent instanceof PlayerMoveEvent)
				{
					nextTimestamp = nextEvent.getTimestamp();
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
		
		ListIterator<Event> li = events.listIterator();
		while((e = li.next()) != null)
		{
			if(e instanceof PlayerMoveEvent)
			{
				System.out.println("PlayerMoveEvent Interval: " + calcPlayerMoveInterval(e));
			}
		}
	}
	
}
