package com.seventhirtyfive;

import java.io.BufferedOutputStream;

public class AUBatter extends AUPlayer implements Comparable<AUBatter>{
    public static final int SORT_OPS        = 1;
    public static final int SORT_HRS        = 2;
    public static final int SORT_AVG        = 3;
    public static final int SORT_RUNS       = 4;
    public static final int SORT_RBI        = 5;
    public static final int SORT_SB         = 6;
    public static final int SORT_OVERALL    = 7;
    public static final int SORT_PRICE 		= 8;
    
    /**
     * Array of stats, with index 0 being the most recent year.
     */
    private AUBatterStats [] mYears;
    
    // static variable that defines how to sort the batters
    public static int SORT_TYPE = SORT_AVG;

    // player rankings (lower is better)
    private int mSbRank = 0;
    private int mHrRank = 0;
    private int mRbiRank = 0;
    private int mRunRank = 0;
    private int mAvgRank = 0;
    private int mOpsRank = 0;
    
    private int getTotalRank()          {   return mSbRank+mHrRank+mRbiRank+mRunRank+mAvgRank+mOpsRank; }
    
    public void setSbRank(int rank)     {    mSbRank = rank; }
    public void setHrRank(int rank)     {    mHrRank = rank; }
    public void setRbiRank(int rank)    {    mRbiRank = rank; }
    public void setRunRank(int rank)    {    mRunRank = rank; }
    public void setAvgRank(int rank)    {    mAvgRank = rank; }
    public void setOpsRank(int rank)    {    mOpsRank = rank; }

    public int getSbRank()              {   return mSbRank; }
    public int getHrRank()              {   return mHrRank; }
    public int getRbiRank()             {   return mRbiRank; }
    public int getRunRank()             {   return mRunRank; }
    public int getAvgRank()             {   return mAvgRank; }
    public int getOpsRank()             {   return mOpsRank; }
    
    public AUBatter( String first, String last, String pos ){
        super(first, last, pos);
        mYears = new AUBatterStats[YEAR_ALL];
        for (int i = 0; i < YEAR_ALL; i++) {
        	mYears[i] = new AUBatterStats();
        }
    }
    
    public AUBatterStats getStats(final int year) {
    	return mYears[mCurrentYear];
    }
    
    /**
     * Compare two batters based on the SORT_TYPE
     */
    public int compareTo(AUBatter o){
        switch( SORT_TYPE ){
            case SORT_OPS:      return opsCompare( o );
            case SORT_HRS:      return hrsCompare( o );
            case SORT_AVG:      return avgCompare( o );
            case SORT_RUNS:     return runsCompare( o );
            case SORT_RBI:      return rbiCompare( o );
            case SORT_SB:       return sbCompare( o );
            case SORT_OVERALL:  return overallCompare( o );
            case SORT_PRICE:	return priceCompare( o );
        }
        return 0;
    }
    
    public static final String getBatterHeader() {
        return String.format("%-15.15s \t %-25.25s \t %-4.4s \t %3.3s \t %-3.3s \t %-3.3s \t %-3.3s \t %-3.3s \t %-3.3s \t %-3.3s", 
                "Player", "", "", "avg", "Runs", "HR", "RBI", "BB", "SB", "OPS", "Price");
    }
    
    public String toString() {
        //String output = String.format("%-15.15s \t %-25.25s \t %-4.4s \t %-1.4f \t %-3.3s \t %-3.3s \t %-3.3s \t %-3.3s \t %-3.3s \t %-1.4f \t $%-2.2f", 
        String output = String.format("%-20s \t %-25s \t %-1.4f \t %-3s \t %-3s \t %-3s \t %-3s \t %-3s \t %-1.4f \t $%-2.2f", 
                mFirstName,mLastName, 
                getStats(mCurrentYear).getAvg(), 
                getStats(mCurrentYear).getRuns(), 
                getStats(mCurrentYear).getHr(), 
                getStats(mCurrentYear).getRbi(), 
                getStats(mCurrentYear).getBb(), 
                getStats(mCurrentYear).getSb(), 
                getStats(mCurrentYear).getOps(),
                getPrice(getStats(mCurrentYear)));
        return output;
    }
    public double getPrice() {
    	return getPrice(getStats(mCurrentYear));
    }
    
    private boolean isScarcePlayer() {
    	if (PlayerRater.USE_SCARCITY_MODIFIER) {
    		for (int i = 0; i < mPositionElig.size(); i++) {
    			AUPlayer.AUPlayerPosition pos = mPositionElig.elementAt(i);
    			if ( pos.isCatcher() || pos.isSecondBase() || pos.isShortStop() ) {
    				return true;
    			}
    		}
    	} 
    	return false;
    }
    
    public double getPrice(AUBatterStats stats) {
    	if (stats.getAb() < 200)		return 1.0;
    	
    	AULeague l = AULeague.getInstance();
    	
    	// get the percentages
    	double abAvg = (double)stats.getAb() / l.getAbAverage();
    	double runAvg = (double)stats.getRuns() / l.getRunAverage();
    	double hrAvg = (double)stats.getHr() / l.getHrAverage();
    	double rbiAvg = (double)stats.getRbi() / l.getRbiAverage();
    	double sbAvg = (double)stats.getSb() / l.getSbAverage();

    	//double abAvg = ((double)stats.getAb() - (l.getAbAverage()/AULeague.NUMBER_OF_HITTERS)) / (l.getAbAverage()/AULeague.NUMBER_OF_HITTERS);
    	//double runAvg = ((double)stats.getRuns() - (l.getRunAverage()/AULeague.NUMBER_OF_HITTERS)) / (l.getRunAverage()/AULeague.NUMBER_OF_HITTERS);
    	//double hrAvg = ((double)stats.getHr() - (l.getHrAverage()/AULeague.NUMBER_OF_HITTERS)) / (l.getHrAverage()/AULeague.NUMBER_OF_HITTERS);
    	//double rbiAvg = ((double)stats.getRbi() - (l.getRbiAverage()/AULeague.NUMBER_OF_HITTERS)) / (l.getRbiAverage()/AULeague.NUMBER_OF_HITTERS);
    	//double sbAvg = ((double)stats.getSb() - (l.getSbAverage()/AULeague.NUMBER_OF_HITTERS)) / (l.getSbAverage()/AULeague.NUMBER_OF_HITTERS);

    	// Fix these to take into account AB's.  Otherwise, low AB's can skew results
    	double baAvg = ( (double)stats.getAvg() - l.getBaAverage() ) / l.getBaAverage();
    	double slgAvg = ( (double)stats.getSlg() - l.getSlgAverage() ) / l.getSlgAverage();
    	double obpAvg = ( (double)stats.getOnBase() - l.getObpAverage() ) / l.getObpAverage();
    	
    	// get the average
    	double avgPercentage = (abAvg+runAvg+hrAvg+rbiAvg+sbAvg+baAvg+slgAvg+obpAvg) / 8.0;
    	
    	if (avgPercentage*AULeague.BATTER_MONEY < 1.0) {
    		return 1.0;			// return 1 dollar
    	} else {
    		double price = avgPercentage*AULeague.BATTER_MONEY;
    		if (isScarcePlayer()) {
    			price = price + price*PlayerRater.SCARCITY_MODIFIER;
    		}
        	return (double)Math.round(price);
    	}
    }

    public void writePlayer( BufferedOutputStream os ) {
    	writePlayer(os, getPrice() );
    }

    private int overallCompare( AUBatter o ) {
        return getTotalRank() - o.getTotalRank();
    }
    
    private int opsCompare( AUBatter o ){
        if( (o.getStats(mCurrentYear).getOpsWeighted() - getStats(mCurrentYear).getOpsWeighted()) < 0 ) return -1;
        else if( (o.getStats(mCurrentYear).getOpsWeighted() - getStats(mCurrentYear).getOpsWeighted()) > 0 ) return 1;
        else return 0;
    }
    private int hrsCompare( AUBatter o ){
        return o.getStats(mCurrentYear).getHr() - getStats(mCurrentYear).getHr();
    }
    private int avgCompare( AUBatter o ){
        if( (o.getStats(mCurrentYear).getAvgWeighted() - getStats(mCurrentYear).getAvgWeighted()) < 0 ) return -1;
        else if( (o.getStats(mCurrentYear).getAvgWeighted() - getStats(mCurrentYear).getAvgWeighted()) > 0 ) return 1;
        else return 0;
    }
    private int runsCompare( AUBatter o ){
        return o.getStats(mCurrentYear).getRuns() - getStats(mCurrentYear).getRuns();
    }
    private int rbiCompare( AUBatter o ){
        return o.getStats(mCurrentYear).getRbi() - getStats(mCurrentYear).getRbi();
    }
    private int sbCompare( AUBatter o ){
        return o.getStats(mCurrentYear).getSb() - getStats(mCurrentYear).getSb();
    }
    private int priceCompare( AUBatter o ) {
    	return (int)(o.getPrice(o.getStats(mCurrentYear)) - getPrice(getStats(mCurrentYear)));
    }
}
