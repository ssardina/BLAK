package agents;

import trees.*;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import weka.core.*;

public class Experience 
{
	//private Instance state;
	private double previousProbability;
	private double deltaProbability;
	private int numberOfSuccesses;
	private int numberOfAttempts;
    private double coverage;
    private Logger logger;
	
	
	public Experience(Logger logger)
	{
		//setState(null);
		this.logger = logger;
		previousProbability = 0.0;
		deltaProbability = 0.0;//Double.MAX_VALUE;
		numberOfSuccesses = 0;
		numberOfAttempts = 0;
        coverage = 0.0;
		
	}

	

	public void setCoverage(double coverage) 
	{
		this.coverage = coverage;
	}
	public double coverage() 
	{
		return coverage;
	}


	public void setPreviousProbability(double previousProbability) 
	{
		this.previousProbability = previousProbability;
	}


	public double getPreviousProbability() 
	{
		return previousProbability;
	}


	public void setDeltaProbability(double deltaProbability) 
	{
		this.deltaProbability = deltaProbability;
	}


	public double getDeltaProbability() 
	{
		return deltaProbability;
	}


	public void setNumberOfSuccesses(int numberOfSuccesses) 
	{
		this.numberOfSuccesses = numberOfSuccesses;
	}


	public int getNumberOfSuccesses() 
	{
		return numberOfSuccesses;
	}


	public void setNumberOfAttempts(int numberOfAttempts) 
	{
		this.numberOfAttempts = numberOfAttempts;
	}


	public int getNumberOfAttempts() 
	{
		return numberOfAttempts;
	}
	
	public void incrementSuccesses()
	{
		this.numberOfSuccesses++;
	}
	
	public void incrementAttempts()
	{
		this.numberOfAttempts++;
	}
	
	public double calculateCurrentProbability()
	{
		Double noSuc = new Double(this.numberOfSuccesses);
		
		Double noAtt = new Double(this.numberOfAttempts);
		double temp = noSuc.doubleValue()/noAtt.doubleValue();
		return temp;
	}
	
	public void updateProbability()
	{
		double newProb = this.calculateCurrentProbability();
		double newDelta = Math.abs(this.previousProbability - newProb);
		this.setPreviousProbability(newProb);
		this.setDeltaProbability(newDelta);
		
	}
	
	public String toString()
	{
		String returnString = "previous probability:"+previousProbability+", delta probability:"+deltaProbability
		+", successful attempts:"+numberOfSuccesses+", total attempts:"+numberOfAttempts+", coverage:"+((double)((int)(coverage*10000)))/10000;
		
		return returnString;
	}
	
	public String toString(int k, double e)
	{
		String returnString = "previous probability:"+previousProbability+", delta probability:"+deltaProbability
		+", successful attempts:"+numberOfSuccesses+", total attempts:"+numberOfAttempts+", coverage:"+((double)((int)(coverage*10000)))/10000;
		if(this.isStateStable(k, e))
		{
			returnString = returnString + ", stable: yes";
		}
		else
		{
			returnString = returnString + ", stable: no";
		}
		
		return returnString;
	}
	
	public boolean isStateStable(int k, double e)
	{
		//System.out.println("State stable check. K: "+k+", epis: "+e);
		if(this.getNumberOfAttempts()>=k && e>=this.getDeltaProbability())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public Object clone()
	{
		Experience myClone  = new Experience(this.logger);
		myClone.setDeltaProbability(this.getDeltaProbability());
		myClone.setPreviousProbability(this.getPreviousProbability());
		myClone.setNumberOfSuccesses(this.getNumberOfSuccesses());
		myClone.setNumberOfAttempts(this.getNumberOfAttempts());
		
		return myClone;
	}
}
