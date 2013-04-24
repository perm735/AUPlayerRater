package com.seventhirtyfive;

/**
 * Pitcher data class
 * 
 * @author mschassberger
 */
public class AUPitcherStats {
	private float mInnings = 0.0f;
	private int mWins = 0;
	private int mLosses = 0;
	private int mSaves = 0;
	private int mStrikeouts = 0;
	private int mWalks = 0;
	private int mHitsAgainst = 0;
	private float mEra = Float.MAX_VALUE;

	public void setInnings(float val)			{	mInnings = val;		}
	public float getInnings()					{	return mInnings;	}
	
	public void setWins(int val)				{	mWins = val;		}
	public int getWins()						{	return mWins;		}
	
	public void setLosses(int val)				{	mLosses = val;		}
	public int getLosses()						{	return mLosses;		}
	
	public void setSaves(int val)				{	mSaves = val; 		}
	public int getSaves()						{	return mSaves;		}
	
	public void setStrikeouts(int val)			{	mStrikeouts = val;	}
	public int getStrikeouts()					{	return mStrikeouts;	}
	
	public void setWalks(int val)				{	mWalks = val;		}
	public int getWalks()						{	return mWalks;		}
	
	public void setHitsAgainst(int val)			{	mHitsAgainst = val;	}
	public int getHitsAgainst()					{	return mHitsAgainst;}
	
	public void setEra(float val)				{	mEra = val;			}
	public float getEra()						{	return mEra;		}
	
    public float getWhip(){
        if( getInnings() > 0.0f ){
            return ( ((float)getWalks()+(float)getHitsAgainst()) / (float)getInnings() );
        }else return 0.0f;
    }
}
