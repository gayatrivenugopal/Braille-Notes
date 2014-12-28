package com.braillenotes;

import java.util.BitSet;
import java.util.HashMap;

public class ChartModel {
	private static HashMap<String, BitSet> chart;
	private static BitSet symbol[];
	private static String charactr[];
	
	
	public static void initialize()
	{
		chart=new HashMap<String, BitSet>();
		symbol = new BitSet[53];
		charactr = new String[53];
		for(int i=0; i<53; i++)
		{
			symbol[i] = new BitSet(6);
		}
		
		symbol[0].set(0); //pattern for a
		symbol[1].set(0); symbol[1].set(2); //pattern for b
		symbol[2].set(0); symbol[2].set(1); //pattern for c
		symbol[3].set(0); symbol[3].set(1); symbol[3].set(3); //pattern for d
	    symbol[4].set(0); symbol[4].set(3); //pattern for e
	    symbol[5].set(0); symbol[5].set(2); symbol[5].set(1); //pattern for f
	    symbol[6].set(0); symbol[6].set(2); symbol[6].set(1); symbol[6].set(3); //pattern for g
	    symbol[7].set(0); symbol[7].set(2); symbol[7].set(3); //pattern for h
	    symbol[8].set(2); symbol[8].set(1); //pattern for i
	    symbol[9].set(2); symbol[9].set(1); symbol[9].set(3); //pattern for j
	    symbol[10].set(0); symbol[10].set(4); //pattern for k
	    symbol[11].set(0); symbol[11].set(2); symbol[11].set(4); //pattern for l
	    symbol[12].set(0); symbol[12].set(4); symbol[12].set(1); //pattern for m
	    symbol[13].set(0); symbol[13].set(4); symbol[13].set(1); symbol[13].set(3); //pattern for n
	    symbol[14].set(0); symbol[14].set(4); symbol[14].set(3); //pattern for o
	    symbol[15].set(0); symbol[15].set(1); symbol[15].set(2); symbol[15].set(4); //pattern for p
	    symbol[16].set(0); symbol[16].set(2); symbol[16].set(3); symbol[16].set(1);symbol[16].set(4); //pattern for q
	    symbol[17].set(0); symbol[17].set(2); symbol[17].set(4); symbol[17].set(3); //pattern for r
	    symbol[18].set(2); symbol[18].set(4); symbol[18].set(1); //pattern for s
	    symbol[19].set(2); symbol[19].set(4); symbol[19].set(1); symbol[19].set(3); //pattern for t
	    symbol[20].set(0); symbol[20].set(4); symbol[20].set(5); //pattern for u
	    symbol[21].set(0); symbol[21].set(2); symbol[21].set(4); symbol[21].set(5); //pattern for v
	    symbol[22].set(2); symbol[22].set(1); symbol[22].set(3); symbol[22].set(5); //pattern for w
	    symbol[23].set(0); symbol[23].set(4); symbol[23].set(1); symbol[23].set(5); //pattern for x
	    symbol[24].set(0); symbol[24].set(4); symbol[24].set(1); symbol[24].set(3); symbol[24].set(5); //pattern for y
	    symbol[25].set(0); symbol[25].set(4); symbol[25].set(3); symbol[25].set(5); //pattern for z
	    
	    symbol[26].set(2); //pattern for ','
	    symbol[27].set(2); symbol[27].set(4); //pattern for ';'
	    symbol[28].set(2); symbol[28].set(3); //pattern for ':'
	    symbol[29].set(1); symbol[29].set(3); symbol[29].set(5); //pattern for '.'
	    symbol[30].set(2); symbol[30].set(4); symbol[30].set(3); //pattern for '!'
	    symbol[31].set(2); symbol[31].set(4); symbol[31].set(3); symbol[31].set(5); //pattern for ()
	    symbol[32].set(2); symbol[32].set(4); symbol[32].set(5); //pattern for '?'
	    symbol[33].set(4);  symbol[33].set(3); //pattern for '*'
	    symbol[34].set(2); symbol[34].set(4); symbol[34].set(5); //pattern for 'opening "'
	    symbol[35].set(4); //pattern for '''
	    symbol[36].set(4); symbol[36].set(5); //pattern for '-'
	    symbol[37].set(3); symbol[37].set(5); //pattern for letter
	    symbol[38].set(5); //pattern for caps
	    symbol[39].set(4); symbol[39].set(1); symbol[39].set(3); symbol[39].set(5); //pattern for numeral
	    symbol[40].set(1); //pattern for @
	 //   symbol[41].set(1); symbol[41].set(3); //pattern for literal index
	    symbol[41].set(1); symbol[41].set(5); //pattern for italic
	    symbol[42].set(1); symbol[42].set(4); //pattern for '/'
	    symbol[43].set(2); symbol[43].set(3); symbol[43].set(5); //pattern for '$'
	    symbol[44].set(0); symbol[44].set(1); symbol[44].set(2); symbol[44].set(3); symbol[44].set(4); symbol[44].set(5); //pattern for '_'
	    /*symbol[45]
	    /*symbol[46]
	    symbol[47]
	    symbol[48]
	    symbol[49]
	    symbol[50]
	    symbol[51]
	    symbol[52]*/
	    char ch='a';
	    for(int i=0; i<26 && ch<='z'; ch++,i++)
	    {
			charactr[i]=Character.toString(ch);
	    }
	   
		
		
	    charactr[26]=",";
	    charactr[27]=";";
	    charactr[28]=":";
	    charactr[29]=".";
	    charactr[30]="!";
	    charactr[31]="()";
	    charactr[32]="?";
	    charactr[33]="*";
	    charactr[34]="\"";
	    charactr[35]="'";
	    charactr[36]="-";
	    charactr[37]="letter";
	    charactr[38]="capital";
	    charactr[39]="numeral";
	    charactr[40]="@";
	   // charactr[41]="literalindex";
	    charactr[41]="italic";
	    charactr[42]="/";
	    charactr[43]="$";
	    charactr[44]="_";
	    charactr[45]=" ";
	    
	    /*for(int i=43; i<=52; i++)
		{
			charactr[i]=Integer.toString(52-i);
		}*/
	    
	    for(int i=0; i<=44; i++)
	    {
	    	chart.put(charactr[i], symbol[i]);
	    }
	    
	    //pattern for space is all 0's
	    chart.put(charactr[45], symbol[45]);
	    
	   /* for(int i=1; i<=9; i++)
		{
	    	chart.put(Integer.toString(i), symbol[i-1]);
		}
	    //CHECK
	    chart.put("0", symbol[9]);*/
	}
			
	public static void addSymbol(String charactr,BitSet symbol)
	{
		chart.put(charactr, symbol);
	}
	
	public static HashMap<String, BitSet> getChart()
	{
		return chart;
	}
	
	public static BitSet getBits(String character)
	{
		return chart.get(character);
	}

}
