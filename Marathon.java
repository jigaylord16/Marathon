//Jacob Gaylord
//jigaylord16@ole.augie.edu
//Marathon.java
import java.util.Scanner;
import java.util.StringTokenizer;
import java.text.DecimalFormat;
import java.io.*;
class Time24
{
    private int hour;
    private int minute;
    //Post: Sets the hour value in the range 0 to 23 and the minute value in the range 0 to 59
    private void normalizeTime()
    {
        int extraHours = minute / 60;
        minute %= 60;
        hour = (hour + extraHours) % 24;
    }
    /**
    Initializes this Time24 object<p>
    <b>Post:</b><br>hour and minute of this Time24 object both initialized to 0<p>
    */
    public Time24()
    {
        this(0,0);		
    }
    /**
    Initializes this Time24 object<p>
    <b>Pre:</b><br>h and m cannot be negative<p>
    <b>Post:</b><br>hour and minute of this Time24 object initialized to h and m 
    respectively.  This operation will normalize the time if necessary (e.g. 
    9:75 is stored as 10:15).<p>
    <b>Throw:</b><br>IllegalArgumentException if h or m is negative<p>
    */
    public Time24(int h, int m)
    {
        setTime(h, m);
    }
    /**
    Sets the hour and minute of this Time24 object to a particular time<p>
    <b>Pre:</b><br>h and m cannot be negative<p>
    <b>Post:</b><br>hour and minute of this Time24 object set to h and m 
    respectively.  This operation will normalize the time if necessary (e.g. 
    9:75 is stored as 10:15).<p>
    <b>Throw:</b><br>IllegalArgumentException if h or m is negative<p>
    */
    public void setTime(int h, int m)
    {
        if (h < 0 || m < 0)
        throw new IllegalArgumentException("Time24.setTime: argument"
            + " must not be negative");
        this.hour = h;
        this.minute = m;
        normalizeTime();
    }
    /**
    Adds minutes to this Time24 object <p>
    <b>Pre:</b><br>m cannot be negative<p>
    <b>Post:</b><br>This Time24 object set to m minutes later.  This operation will 
    normalize the time if necessary (e.g. 9:75 is stored as 10:15).<p>
    <b>Throw:</b><br>IllegalArgumentException if m is negative<p>
    */
    public void addTime(int m)
    {
        if (m < 0)
        throw new IllegalArgumentException("Time24.addTime: argument"
            + " must not be negative");
        this.minute += m;
        normalizeTime();
    }
    /**
    Measures the interval from this Time24 object to another time<p>
    <b>Return:</b><br>The interval from this Time24 object to t as a Time24 
    */
    public Time24 interval(Time24 t)
    {
        int currTime=hour*60+minute;
        int tTime=t.hour*60+t.minute;
        if(tTime<currTime)
            tTime+=24*60;
        return new Time24(0,tTime-currTime);
    }
    /**
    Gets the hour value of this Time24 object<p>
    <b>Return:</b><br>The hour value of this Time24 object<p>
    */
    public int getHour()
    { 
        return hour; 
    }
    /**
    Gets the minute value of this Time24 object<p>
    <b>Return:</b><br>The minute value of this Time24 object<p>
    */
    public int getMinute()
    { 
        return minute; 
    }
    /**
    Converts this Time24 object to a string<p>
    <b>Return:</b><br>This Time24 object as a String in the form "hh:mm"<p>
    */
    public String toString()
    {
        DecimalFormat f= new DecimalFormat("0");
	return f.format(hour)+":"+f.format(minute);
    }
    /**
    Convert a String to a Time24<p>
    <b>Pre:</b><br>s must be in the form "hh:mm" where hh and mm are positive integers 
    <p>
    <b>Return:</b><br>A Time24 object that corresponds to s<p>
    */
    public static Time24 parseTime(String s)				
    {
        StringTokenizer stk = new StringTokenizer(s," :");
        int hr = Integer.parseInt(stk.nextToken());
        int mn = Integer.parseInt(stk.nextToken());
        Time24 t= new Time24(hr, mn);
        return t;
    }
    public void readTime(Scanner f)
    {
    	while(f.hasNextLine())
    	{
        String s = f.nextLine();
        Time24 t = Time24.parseTime(s);
        t.normalizeTime();
        }
    }
}
class Runner
{
    private String name;
    private Time24 raceTime;
    //  Usage: 	Runner()
    //  Post: 	The Runner object initialized with name="unknown" and 
    //          raceTime=0:0.
    public Runner()
    {
        name="unknown";
        raceTime=new Time24(0,0);
    }
    //  Usage: 	Runner (String s, Time24 t)
    //  Post: 	The Runner object initialized with name=s and raceTime=t.
    public Runner(String s,Time24 t)
    {
        name=s;
        raceTime=new Time24(t.getHour(),t.getMinute());
    }
    //  Usage: 	String getName()	
    //  Return: The name of the Runner object
    public String getName()
    {
        return name;
    }
    //  Usage: 	Time24 getRaceTime()
    //  Return: The raceTime of the Runner object.
    public Time24 getRaceTime()
    {
        return new Time24(raceTime.getHour(), raceTime.getMinute());
    }
    //  Usage: 	void setName(String s)
    //  Post: 	The Runner object's name set to s 
    public void setName(String s)
    {
        this.name=s;
    }
    //  Usage: 	void setRaceTime(Time24 t)
    //  Post: 	The Runner object's raceTime set to t 
    public void setRaceTime(Time24 t)
    {
        this.raceTime=t;
    }
    //  Usage: 	void read(Scanner f)
    //  Pre: 	f has a line in the following format ready to be read:
    //                    name hh:mm 
    //          where name is a String and hh, mm are integers.  The token 
    //          delimiters of f have been set to white space characters and the 
    //          colon by the caller.
    //          Post: 	The line read in from f, the name and the time stored in 
    //          the Runner object. 
    public void read(Scanner f)
    {
    	while(f.hasNextLine())
    	{
        String name=f.next();
        this.name = name;
        int hr = f.nextInt();
        int min = f.nextInt();
        f.nextLine();
        Time24 t = new Time24(hr, min);
        this.raceTime = t;
        break;
        }
    }
    //  Usage: 	int compareTo(Runner r)
    //  Desc: 	Compare 2 Runner objects based on raceTime
    //  Return: 	1 if current object's raceTime > r's raceTime
    //  0 if current object's raceTime == r's raceTime
    //  -1 if current object's raceTime < r's raceTime

    public int compareTo(Runner r)
    {
       Time24 thisRaceTime = new Time24(0,0);
       Time24 rRaceTime = new Time24(0,0);
       int currTime = 0;
       int rTime = 0;
       thisRaceTime = this.getRaceTime();
       rRaceTime = r.getRaceTime();
       currTime = thisRaceTime.getHour()*60+thisRaceTime.getMinute();
       rTime = rRaceTime.getHour()*60+rRaceTime.getMinute();
       if(currTime > rTime) return 1;
       else if(currTime == rTime) return 0;
       else return -1;
    }
    //  Usage: 	String toString()
    //  Return: A String object in the form "name hh:mm"
    public String toString()
    {
        Time24 thisRaceTime = new Time24(0,0);
        thisRaceTime = this.getRaceTime();
        String time = thisRaceTime.toString();
        return name+" "+time;
    }
}
//Desc:   Output the name and time of the runner who came in first, as well as 
//        the name and time of the runner who came in last in a marathon race 
//        (assuming there are no ties).
//Input:  A text file named marathon.txt containing the name and time of each 
//        participant in the following format (the file has at least 1 
//        participant, name is just 1 word with no space, and name and time are 
//        separated by tabs, blanks, and newlines):
//	  John	2:40
//	  Paul	3:20
//	  Carl	2:10
//Output: The name and time of the runner who came in first, as well as the name
//        and time of the runner who came in last printed to the screen.
public class Marathon
{
    public static void main(String[] args) throws FileNotFoundException
    {
        Runner temp1 = new Runner();
        Runner temp2 = new Runner();
        Runner first = new Runner();
        Runner last = new Runner();
        Scanner input = null;
        try 
        {
            input = new Scanner(new File("marathon.txt"));
            input = input.useDelimiter("[: \n]+");
            while(input.hasNextLine())
            {
            temp1.read(input);
            temp2.read(input);
            }
        }
        catch(FileNotFoundException e)
        {
            System.out.println ("marathon.txt does not exist.  Program aborted");
            System.exit(1);
        }
        if(temp1.compareTo(temp2) == 1)
        {
            first.setName(temp1.getName());
            first.setRaceTime(temp1.getRaceTime());
            last.setName(temp2.getName());
            last.setRaceTime(temp2.getRaceTime());
        }
        else if(temp1.compareTo(temp2) == -1)
        {
            last.setName(temp1.getName());
            last.setRaceTime(temp1.getRaceTime());
            first.setName(temp1.getName());
            first.setRaceTime(temp1.getRaceTime());
        }
        while(input.hasNext())
        {
            temp1.read(input);
            if(temp1.compareTo(first) == 1)
            {
                first.setName(temp1.getName());
            	first.setRaceTime(temp1.getRaceTime());
            }
            else if(temp1.compareTo(first) == -1)
                if(temp1.compareTo(last) == -1)
                {
                    last.setName(temp1.getName());
            		last.setRaceTime(temp1.getRaceTime());
            	}
            else continue;
        }
        System.out.println("first place: "+temp1.toString());
        System.out.println("last place: "+temp2.toString());
        input.close();
    }
}