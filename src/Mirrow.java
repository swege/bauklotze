import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;

public class Mirrow
{
	static int length;
	static int height;
	public static void main(String[] args) 
	{
		length = Integer.parseInt(args[0]);
		height = Integer.parseInt(args[1]);
		if(length > height)
		{
			int c = height;
			height = length;
			length = c;
		}
		HashMap<String, BigInteger> hm = new HashMap<String, BigInteger>();
		char[] data = new char[height*length];
		Arrays.fill(data, '\u0001');
		hm.put(new String(data), BigInteger.ONE);
		data = new char[height*length];
		System.out.print(fit(0, 0, hm, data));
	}
	
	public static BigInteger solve(int m, int n)
	{
		height = m;
		length = n;
		if(length > height)
		{
			int c = height;
			height = length;
			length = c;
		}
		HashMap<String, BigInteger> hm = new HashMap<String, BigInteger>();
		char[] data = new char[height*length];
		Arrays.fill(data, '\u0001');
		hm.put(new String(data), BigInteger.ONE);
		Arrays.fill(data, '\u0000');
		return fit(0, 0, hm, data);
	}
    
	private static BigInteger fit(int j, int i, HashMap<String, BigInteger> hm, char[] data)
	{
		int pos = j*length+i;
		while(data[pos] == '\u0001')
		{
			i=i+1;
			pos=pos+1;
			if(i >=length)
			{
				i=0;
				j=j+1;
			}
		}
		BigInteger count = BigInteger.ZERO;
		data[pos] = '\u0001';
		if (i+1 < length && data[pos+1] == '\u0000') 
		{
			data[pos+1] = '\u0001';
			if (i+2 < length && data[pos+2] == '\u0000') 
			{
				data[pos+2] = '\u0001';
				String s = new String(data);
				if(hm.containsKey(s))
				{
					count = count.add(hm.get(s));
				}
				else
				{
					if(i+3 >= length)
					{
						hm.put(s, fit(j+1, 0, hm, data));
						hm.put(new String(mirrow(data)), hm.get(s));
						count = count.add(hm.get(s));
					}
					else
					{
						hm.put(s, fit(j, i+3, hm, data));
						hm.put(new String(mirrow(data)), hm.get(s));
						count = count.add(hm.get(s));
					}
				}
				data[pos+2] = '\u0000';
			}
			if (j+1 < height) 
			{
				if (data[pos + length] == '\u0000')
				{
					data[pos + length] = '\u0001';
					String s = new String(data);
					if (hm.containsKey(s))
					{
						count = count.add(hm.get(s));
					} 
					else
					{
						if(i+2 >= length)
						{
							hm.put(s, fit(j+1, 0, hm, data));
							hm.put(new String(mirrow(data)), hm.get(s));
							count = count.add(hm.get(s));
						}
						else
						{
							hm.put(s, fit(j, i+2, hm, data));
							hm.put(new String(mirrow(data)), hm.get(s));
							count = count.add(hm.get(s));
						}
					}
					data[pos + length] = '\u0000';
				}
				if (data[pos + length + 1] == '\u0000')
				{
					data[pos + length + 1] = '\u0001';
					String s = new String(data);
					if (hm.containsKey(s))
					{
						count = count.add(hm.get(s));
					} 
					else
					{
						if(i+2 >= length)
						{
							hm.put(s, fit(j+1, 0, hm, data));
							hm.put(new String(mirrow(data)), hm.get(s));
							count = count.add(hm.get(s));
						}
						else
						{
							hm.put(s, fit(j, i+2, hm, data));
							hm.put(new String(mirrow(data)), hm.get(s));
							count = count.add(hm.get(s));
						}
					}
					data[pos + length + 1] = '\u0000';
				}
			}
			data[pos+1] = '\u0000';
		}
		if (j + 1 < height && data[pos+length] == '\u0000') 
		{
			data[pos+length] = '\u0001';
			if (j + 2 < height && data[pos+2*length] == '\u0000') 
			{
				data[pos+2*length] = '\u0001';
				String s = new String(data);
				if(hm.containsKey(s))
				{
					count = count.add(hm.get(s));
				}
				else
				{
					if(i+1 >= length)
					{
						hm.put(s, fit(j+1, 0, hm, data));
						hm.put(new String(mirrow(data)), hm.get(s));
						count = count.add(hm.get(s));
					}
					else
					{
						hm.put(s, fit(j, i+1, hm, data));
						hm.put(new String(mirrow(data)), hm.get(s));
						count = count.add(hm.get(s));
					}
				}
				data[pos+2*length] = '\u0000';
			}
			if (i - 1 >= 0 && data[pos+length-1] == '\u0000') 
			{
				data[pos+length-1] = '\u0001';
				String s = new String(data);
				if(hm.containsKey(s))
				{
					count = count.add(hm.get(s));
				}
				else
				{
					if(i+1 >= length)
					{
						hm.put(s, fit(j+1, 0, hm, data));
						hm.put(new String(mirrow(data)), hm.get(s));
						count = count.add(hm.get(s));
					}
					else
					{
						hm.put(s, fit(j, i+1, hm, data));
						hm.put(new String(mirrow(data)), hm.get(s));
						count = count.add(hm.get(s));
					}
				}
				data[pos+length-1] = '\u0000';
			}
			if (i + 1 < length && data[pos+length+1] == '\u0000') 
			{
				data[pos+length+1] = '\u0001';
				String s = new String(data);
				if(hm.containsKey(s))
				{
					count = count.add(hm.get(s));
				}
				else
				{
					if(i+1 >= length)
					{
						hm.put(s, fit(j+1, 0, hm, data));
						hm.put(new String(mirrow(data)), hm.get(s));
						count = count.add(hm.get(s));
					}
					else
					{
						hm.put(s, fit(j, i+1, hm, data));
						hm.put(new String(mirrow(data)), hm.get(s));
						count = count.add(hm.get(s));
					}
				}
				data[pos+length+1] = '\u0000';
			}
			data[pos+length] = '\u0000';
		}
		data[pos] = '\u0000';
		return count;
	}
	
	private static char[] mirrow(char[] data)
	{
		char[] x = new char[data.length];
		int a = 0;
		for(int i = height-1; i >= 0; i--)
		{
			for(int j = length-1; j >= 0; j--)
			{
				x[a] = data[i*length+j];
				a++;
			}
		}
		return data;
	}
}
