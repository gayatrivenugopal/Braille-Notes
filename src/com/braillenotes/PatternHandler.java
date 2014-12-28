package com.braillenotes;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;
import java.util.Set;

public class PatternHandler {

	private static ArrayList<String> word = new ArrayList<String>();
	private static ArrayList<String> body = new ArrayList<String>();
	private static ArrayList<String> recipientList = new ArrayList<String>();
	private static ArrayList<String> ccList = new ArrayList<String>();
	private static ArrayList<String> bccList = new ArrayList<String>();
	private static ArrayList<String> subject = new ArrayList<String>();
	private static int caps;
	private static int openingBracket;
	private static int numericIndicator;
	
	public static void setCaps()
	{
		if(word.size()>0 && word.get(word.size()-1).toString().equalsIgnoreCase("capital"))
		{
			caps = 2;
			word.remove(word.size()-1);
		}
		else
			caps = 1;
		numericIndicator = 0;
	}
	
	public static void setNumericIndicator()
	{
		numericIndicator = 1;
		caps = 0;
	}
	
	public static boolean matchPattern(int[] cell, int index)
	{
		BitSet cellBitset = new BitSet(6);
		for(int i=0; i<6; i++)
		{
			if(cell[i]==1)
				cellBitset.set(i);
		}
		
		if(ChartModel.getChart().containsValue(cellBitset))
		{
			Set<?> keys = (ChartModel.getChart()).keySet();  
			Iterator<?> keyIter = keys.iterator();
			while (keyIter.hasNext())
			{  
				String key = keyIter.next().toString(); 
				BitSet value = (ChartModel.getChart()).get(key);
			
				if(value.toString().equals(cellBitset.toString()))
				{
					if(key.toString().equals(" "))
					{
						caps = 0;
						numericIndicator = 0;
					}
					if(index == -1)
					{

						if(key.equals("()"))
						{
							if(openingBracket == 1)
							{
								key = ")";
								openingBracket = 0;
							}
							else
							{
								key = "(";
								openingBracket = 1;
							}
						}
						if(caps == 1 || caps == 2)
						{
							if(key.charAt(0)>='a' && key.charAt(0)<='z')
							{
								if(word.size() > 0 && word.get(word.size()-1).toString().equalsIgnoreCase("capital"))
									word.remove(word.size()-1);
								word.add(key.toUpperCase());
							}
						}						
						else if(numericIndicator == 1)
						{
							if(word.size()>0 && word.get(word.size()-1).toString().equalsIgnoreCase("capital"))
								word.remove(word.size()-1);
							switch(key.charAt(0))
							{
							case 'a':
								word.add("1");
								break;
							case 'b':
								word.add("2");
								break;
							case 'c':
								word.add("3");
								break;
							case 'd':
								word.add("4");
								break;
							case 'e':
								word.add("5");
								break;
							case 'f':
								word.add("6");
								break;
							case 'g':
								word.add("7");
								break;
							case 'h':
								word.add("8");
								break;
							case 'i':
								word.add("9");
								break;
							case 'j':
								word.add("0");
								break;
							}
						}
						else
						{
							word.add(key);
						}
					}
					else
					{

						if(key.equals("()"))
						{
							if(openingBracket == 1)
							{
								key = ")";
								openingBracket = 0;
							}
							else
							{
								key = "(";
								openingBracket = 1;
							}
						}
						if(word.size()>0)
							word.remove(index);
						if(caps == 1 || caps == 2)
						{
							if(word.size()>0 && word.get(word.size()-1).toString().equalsIgnoreCase("capital"))
								word.remove(word.size()-1);
							if(key.charAt(0)>='a' && key.charAt(0)<='z')
								word.add(index,key.toUpperCase());
						}
						else if(numericIndicator == 1)
						{
							if(word.size()>0 && word.get(word.size()-1).toString().equalsIgnoreCase("numeral"))
								word.remove(word.size()-1);
							switch(key.charAt(0))
							{
							case 'a':
								word.add(index,"1");
								break;
							case 'b':
								word.add(index,"2");
								break;
							case 'c':
								word.add(index,"3");
								break;
							case 'd':
								word.add(index,"4");
								break;
							case 'e':
								word.add(index,"5");
								break;
							case 'f':
								word.add(index,"6");
								break;
							case 'g':
								word.add(index,"7");
								break;
							case 'h':
								word.add(index,"8");
								break;
							case 'i':
								word.add(index,"9");
								break;
							case 'j':
								word.add(index,"0");
								break;
							}
						}
						else
							word.add(index, key);
					}
					if(caps == 1 && !key.equalsIgnoreCase("capital"))
					{
						caps = 0;
					}
					return true;
				}
			}
		}
		return false;
	}
	
	public static ArrayList<String> getWord()
	{
		return word;
	}
	
	public static void saveBody()
	{
		body = new ArrayList<String>(word.size());
		body.addAll(word);
		word.clear();
		caps = 0;
		numericIndicator = 0;
	}
	
	public static void saveRecipient()
	{
		recipientList = new ArrayList<String>(word.size());
		for(int i=0; i<word.size(); i++)
			recipientList.add(word.get(i));
		word.clear();
		caps = 0;
		numericIndicator = 0;
	}
	
	public static void saveCC()
	{
		ccList = new ArrayList<String>(word.size());
		for(int i=0; i<word.size(); i++)
			ccList.add(word.get(i));
		word.clear();
		caps = 0;
		numericIndicator = 0;
	}
	
	public static void saveBCC()
	{
		bccList = new ArrayList<String>(word.size());
		for(int i=0; i<word.size(); i++)
			bccList.add(word.get(i));
		word.clear();
		caps = 0;
		numericIndicator = 0;
	}
	
	public static void saveSubject()
	{
		subject = new ArrayList<String>(word.size());
		for(int i=0; i<word.size(); i++)
			subject.add(word.get(i));
		word.clear();
		caps = 0;
		numericIndicator = 0;
	}
	
	public static String getWordAt(int index)
	{
		return (String) word.get(index);
	}
	
	public static BitSet getSymbol(String str)
	{
		return ChartModel.getChart().get(str);
	}
	
	public static BitSet getSymbol(int index)
	{
		return ChartModel.getChart().get((String) word.get(index));
	}
	
	public static void restore()
	{
		word.clear();
		body.clear();
		recipientList.clear();
		ccList.clear();
		bccList.clear();
		subject.clear();
		caps = 0;
		openingBracket = 0;
		numericIndicator = 0;
	}

	public static ArrayList<String> getBody() {
		return body;
	}
	
	public static ArrayList<String> getRecipients() {
		return recipientList;
	}
	
	public static ArrayList<String> getCc() {
		return ccList;
	}
	
	public static ArrayList<String> getBcc() {
		return bccList;
	}
	
	public static ArrayList<String> getSubject() {
		return subject;
	}

}
