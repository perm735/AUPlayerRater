package com.seventhirtyfive;

/**
 * Data container class that holds a years worth of statistics for batters.
 * 
 * @author mschassberger
 */
public class AUBatterStats {
    private int sb = 0;
    private int hr = 0;
    private int rbi = 0;
    private int runs = 0;
    private int abs = 0;
    private int bb = 0;
    private int hitSingle= 0;
    private int hitDouble = 0;
    private int hitTriple = 0;

    public void setSb(int val)  {   sb = val;           }
    public int getSb()          {   return sb;          }
    
    public void setHr(int val)  {   hr = val;           }
    public int getHr()          {   return hr;          }
    
    public void setRuns(int val){   runs = val;         }
    public int getRuns()        {   return runs;        }
    
    public void setRbi(int val) {   rbi = val;          }
    public int getRbi()         {   return rbi;         }
    
    public void setBb(int val)  {   bb = val;           }
    public int getBb()			{	return bb;			}
    
    public void setAb(int val)  {   abs = val;          }
    public int getAb()			{	return abs;			}
    
    public void set1B(int val)  {   hitSingle = val;    }
    public int get1B()			{	return hitSingle;	}
    
    public void set2B(int val)  {   hitDouble = val;    }
    public int get2B()			{	return hitDouble;	}
    
    public void set3B(int val)  {   hitTriple = val;    }
    public int get3B()			{	return hitTriple;	}
    
    public float getOps(){
        // OPS is defined to be on base percentage + slugging
        return getSlg() + getOnBase();
    }
    
    public float getOpsWeighted() {
        return ( getOps() * (float)getAb());
    }
    
    public float getAvg(){
        if( getAb() > 0 ){
            return( ((float)getTotalHits()) / ((float)getAb()) );
        } else {
        	return 0.0f;
        }
    }
    
    public float getAvgWeighted() {
        return getAvg() * ((float)getAb());
    }

    private int getTotalHits()  {
    	return get1B()+get2B()+get3B()+getHr(); 
	}

	public float getOnBase(){
		float totalAbs = getAb()+getBb();
		if( totalAbs > 0 ){
			return( ((float)getTotalHits()+(float)getBb()) / totalAbs );
		} else {
			return 0.0f;
		}
	}

	public float getSlg(){
		float totalBases = get1B()+(get2B()*2)+(get3B()*3)+(getHr()*4);
		if( getAb() > 0 ){
			return totalBases / ((float)getAb());
		}else {
			return 0.0f;
		}
	}
}
