package com.seventhirtyfive;

import java.io.BufferedOutputStream;

public class AUPitcher extends AUPlayer implements Comparable<AUPitcher> {
    /**
     * Array of stats, with index 0 being the most recent year.
     */
    private AUPitcherStats [] mYears;

    public static final int SORT_WINS   = 1;
    public static final int SORT_INN    = 2;
    public static final int SORT_K      = 3;
    public static final int SORT_S      = 4;
    public static final int SORT_WHIP   = 5;
    public static final int SORT_ERA    = 6;
    public static final int SORT_PRICE	= 7;
    
    // static variable that defines how to sort the pitchers
    public static int SORT_TYPE = SORT_WINS;

    public AUPitcher( String first, String last, String position ){
        super( first, last, position );
        mYears = new AUPitcherStats[YEAR_ALL];
        for (int i = 0; i < YEAR_ALL; i++) {
        	mYears[i] = new AUPitcherStats();
        }
    }
    
    public int compareTo(AUPitcher arg0) {
        switch( SORT_TYPE ){
        case SORT_WINS:     return winCompare( arg0 );
        case SORT_WHIP:     return whipCompare( arg0 );
        case SORT_INN:      return innCompare( arg0 );
        case SORT_K:        return kCompare( arg0 );
        case SORT_S:        return sCompare( arg0 );
        case SORT_ERA:      return eraCompare( arg0 );
        case SORT_PRICE:	return priceCompare( arg0 );
        }
        // TODO Auto-generated method stub
        return 0;
    }
    
    public AUPitcherStats getStats(int year) {
    	return mYears[year];
    }
    
    public AUPitcherStats getStats() {
    	return mYears[mCurrentYear];
    }
    
    public static final String getPitcherHeader() {
        return String.format("%-15.15s \t %-25.25s \t %-4.4s \t %3.4s \t %-3.4s \t %-3.4s \t %-3.4s \t %-3.4s \t %-3.4s \t %-3.6s", 
                "Player", "", "", "Inn", "Wins", "Saves", "K", "ERA", "Whip", "Price");
    }

    public String toString() {
        String output = String.format("%-20s \t %-25s \t %-1.1f \t %-3s \t %-3s \t %-3s \t %-1.3f \t %-1.3f \t %2.2f", 
                mFirstName,mLastName, 
                getStats().getInnings(), 
                getStats().getWins(), 
                getStats().getSaves(), 
                getStats().getStrikeouts(), 
                getStats().getEra(),
                getStats().getWhip(),
                getPrice());
        return output;
    }

    public double getPrice() {
    	return getPrice(getStats(mCurrentYear));
    }
    
    public double getPrice(AUPitcherStats stats) {
    	if (stats.getInnings() < 10.0) return 1.0;
    	
    	AULeague l = AULeague.getInstance();

    	// get the percentages
    	double innAvg = (double)stats.getInnings() / (l.getInnAverage());     // 2013: Weight innings more without affecting ERA/WHIP
    	                                                                      // 2014: Roll back 2013 change
    	double savesAvg = (double)stats.getSaves() / (l.getSaveAverage());      // pare down the value of saves
    	                                                                      // 2016: actually pare down the value of saves
    	
    	double kAvg = (double)stats.getStrikeouts() / l.getKAverage();
    	double winAvg = (double)stats.getWins() / l.getWinAverage();
    	
    	// weight whip and average with innings
    	double weight = innAvg*1.4;
    	double eraAvg = (( l.getEraAverage() - (double)stats.getEra() ) / l.getEraAverage()) + weight;
    	double whipAvg = (( l.getWhipAverage() - (double)stats.getWhip() ) / l.getWhipAverage()) + weight;

    	// get the average
    	double avgPercentage = (innAvg+savesAvg+kAvg+eraAvg+whipAvg+winAvg) / 6;
    	
    	if (avgPercentage*AULeague.PITCHER_MONEY < 5.0) {
    		return 1.0;			// return 1 dollar
    	} else {
    		if(this.isEligible(AUPlayerPosition.RELIEF_PITCHER)) {
    			return (double)Math.round(avgPercentage*AULeague.RELIEF_PITCHER_MONEY);
    		}
    		else {
    			return (double)Math.round(avgPercentage*AULeague.PITCHER_MONEY);
    		}
        	
    	}
    }
    public void writePlayer( BufferedOutputStream os ) {
    	writePlayer(os, getPrice() );
    }

    public void writePitcher( BufferedOutputStream os ) {
    	this.writePlayer(os, getPrice());
    }
    
    private int winCompare( AUPitcher p ){
        return getStats().getWins() - p.getStats().getWins();
    }
    private int whipCompare( AUPitcher p ){
        if( (getStats().getWhip() - p.getStats().getWhip()) < 0 ) return -1;
        else if( (getStats().getWhip() - p.getStats().getWhip()) > 0 ) return 1;
        else return 0;
    }
    private int innCompare( AUPitcher p ){
        if( (getStats().getInnings() - p.getStats().getInnings()) < 0 ) return -1;
        else if( (getStats().getInnings() - p.getStats().getInnings()) > 0 ) return 1;
        else return 0;
    }
    private int kCompare( AUPitcher p ){
        return getStats().getStrikeouts() - p.getStats().getStrikeouts();
    }
    private int sCompare( AUPitcher p ){
        return getStats().getSaves() - p.getStats().getSaves();
    }
    private int eraCompare( AUPitcher p ){
        if( (getStats().getEra() - p.getStats().getEra()) < 0 ) return -1;
        else if( (getStats().getEra() - p.getStats().getEra()) > 0 ) return 1;
        else return 0;
    }
    private int priceCompare( AUPitcher o ) {
    	return (int)(o.getPrice(o.getStats(mCurrentYear)) - getPrice(getStats(mCurrentYear)));
    }
}
