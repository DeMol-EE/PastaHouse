/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logging;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Warkst
 */
public class MyLogger {
    
    public static int OFF = -1;
    public static int LOW = 0;
    public static int NORMAL = 1;
    public static int HIGH = 2;
    
    @SuppressWarnings("StaticNonFinalUsedInInitialization")
    private static int logLevel = NORMAL;
    
    /**
     * Sets the logLevel to the given level. The higher the value the more 
     * logging will occur in the log files.
     * Values should be picked from:
     * <ul>
     * <li>MyLogger.OFF</li>
     * <li>MyLogger.LOW</li>
     * <li>MyLogger.NORMAL</li>
     * <li>MyLogger.HIGH</li>
     * </ul>
     * 
     * @param level The desired logLevel.
     */
    public static void setLogLevel(int level){
	logLevel = level;
    }
    
    /**
     * Logs a given message to the system's standard out and to a file. 
     * Output is grouped per month and year in the "Logs" folder. 
     * Log files are of the format "log_month_year".
     * Level determines the level of logging. This requires the logLevel to be
     * at least this level to log the message in the logs. Log commands with
     * low logLevel values are more commonly found in logs.
     * Values should be picked from:
     * <ul>
     * <li>MyLogger.LOW</li>
     * <li>MyLogger.NORMAL</li>
     * <li>MyLogger.HIGH</li>
     * </ul>
     * 
     * @param msg The message to log.
     * @param lvl The level of logging.
     */
    public static void log(String msg, int lvl){
	logToChat(msg, lvl);
	if (lvl <= logLevel) {
	    logToFile(msg);
	}
    }
    
    /**
     * Logs a given message to the system's standard out. Calls <code>log</code>
     * with lvl = <code>MyLogger.NORMAL</code>.
     */
    public static void log(String msg){
	log(msg, NORMAL);
    }
    
    private static void logToChat(String s, int lvl){
	System.out.println("["+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) +" LogLevel="+lvl+"]: "+s);
    }
    
    private static void logToFile(String s){
	/*
	 * Calculate month and year
	 */
	int month = Calendar.getInstance().get(Calendar.MONTH)+1;
	int year = Calendar.getInstance().get(Calendar.YEAR);
	
	/*
	 * Find the logs folder
	 */
	File logs = new File("Logs");
	if (!logs.exists() || !logs.isDirectory()) {
	    logs.mkdir();
	}
	
	/*
	 * Try to write to the appropriate log file
	 */
	try{
	    File log = new File(logs, "log_"+new DecimalFormat("00").format(month)+"_"+year+".txt");
	    if (!log.exists()) {
		log.createNewFile();
	    }
	    /*
	     * Open a file stream and append (true-flag)
	     */
	    FileWriter fstr = new FileWriter(log, true);
	    /*
	     * Buffed all output for efficiency
	     */
	    BufferedWriter b = new BufferedWriter(fstr);
	    /*
	     * User a printwriter for output
	     */
	    PrintWriter p = new PrintWriter(b, true);
	    /*
	     * Actually write to the file
	     */
	    p.println(s);
	} catch(Exception e){
	    System.err.println("Exception caught when trying to append to file...\n"+e.getMessage());
	}
    }
}
