package S16030021049;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public final class ReadRoadValue 
{
	public static final HashMap<Integer,Integer> values = new HashMap<>();
	static
	{
		 ReadValue();
	}
	private static void ReadValue()// throws IOException 
	{
		File file = new File("lib/valueStore.txt");
		InputStreamReader reader = null;
		try
		{
			reader = new InputStreamReader(new FileInputStream(file));
			char[] strpoint=new char[1000];
			reader.read(strpoint);
			String[] split = new String(strpoint).split("\r\n");
			for(String a:split)
			{
				if(a.charAt(0)=='\0')
					break;
				String[] split1 = new String(a).split(" ");
				values.put(Integer.parseInt(split1[0]), Integer.parseInt(split1[1]));
			}
			
			
		}catch(final EOFException eofe){
		}catch (final FileNotFoundException fnfe) {
            System.err.println("Error: Cannot find file" + ".");
            System.exit(1);
        } catch (final IOException ioe) {
            System.err.println("Error: Cannot read from file "  + ".");
            System.exit(1);
        } finally {
        	try {
                    reader.close();
                }catch (final IOException ioe) { }
        }
	}	
}
