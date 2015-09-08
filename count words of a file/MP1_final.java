import java.io.*;
import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class MP1 {
    Random generator;
    String userName;
    String inputFileName;
    String delimiters = " \t,;.?!-:@[](){}_*/";
    String[] stopWordsArray = {"i", "me", "my", "myself", "we", "our", "ours", "ourselves", "you", "your", "yours",
            "yourself", "yourselves", "he", "him", "his", "himself", "she", "her", "hers", "herself", "it", "its",
            "itself", "they", "them", "their", "theirs", "themselves", "what", "which", "who", "whom", "this", "that",
            "these", "those", "am", "is", "are", "was", "were", "be", "been", "being", "have", "has", "had", "having",
            "do", "does", "did", "doing", "a", "an", "the", "and", "but", "if", "or", "because", "as", "until", "while",
            "of", "at", "by", "for", "with", "about", "against", "between", "into", "through", "during", "before",
            "after", "above", "below", "to", "from", "up", "down", "in", "out", "on", "off", "over", "under", "again",
            "further", "then", "once", "here", "there", "when", "where", "why", "how", "all", "any", "both", "each",
            "few", "more", "most", "other", "some", "such", "no", "nor", "not", "only", "own", "same", "so", "than",
            "too", "very", "s", "t", "can", "will", "just", "don", "should", "now"};

    void initialRandomGenerator(String seed) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA");
        messageDigest.update(seed.toLowerCase().trim().getBytes());
        byte[] seedMD5 = messageDigest.digest();

        long longSeed = 0;
        for (int i = 0; i < seedMD5.length; i++) {
            longSeed += ((long) seedMD5[i] & 0xffL) << (8 * i);
        }

        this.generator = new Random(longSeed);
    }

    Integer[] getIndexes() throws NoSuchAlgorithmException {
        Integer n = 10000;
        Integer number_of_lines = 50000;
        Integer[] ret = new Integer[n];
        this.initialRandomGenerator(this.userName);
        for (int i = 0; i < n; i++) {
            ret[i] = generator.nextInt(number_of_lines);
        }
        return ret;
    }

    public MP1(String userName, String inputFileName) {
        this.userName = userName;
        this.inputFileName = inputFileName;
    }

    public String[] process() throws Exception {
        String[] ret = new String[20];
		ArrayList<String> raw_arrlist = new ArrayList<String>();
		ArrayList<String> arrlist = new ArrayList<String>();
		ArrayList<String> toprocess = new ArrayList<String>();
		String tmp;
		//FileReader in = new FileReader(inputFileName);
		//BufferedReader br = new BufferedReader(in);
		// FileWriter fw = new FileWriter("./new.txt");
		
		FileInputStream fis = new FileInputStream(inputFileName); 
	    InputStreamReader isr = new InputStreamReader(fis, "UTF-8"); 
	    BufferedReader br = new BufferedReader(isr); 
		
		String str1 = br.readLine();
		StringTokenizer str2;
		while (str1 != null) {
		    //System.out.println(str1);
			//str2 = new StringTokenizer(str1, delimiters);
			//System.out.println(str2);
			//while (str2.hasMoreTokens()) {

				//tmp = str2.nextToken().toLowerCase().trim();
				// System.out.println(tmp);
			 raw_arrlist.add(str1);
			    //fw.write(tmp+"\n");
			//}

			str1 = br.readLine();
		}
		br.close();
		fis.close();
		isr.close();
		//fw.close();
		String str;
		
		for(int i=0;i<getIndexes().length;i++){
			str=raw_arrlist.get(getIndexes()[i]);
			toprocess.add(str);
		}
		
		for(int i=0;i<toprocess.size();i++){
			str=toprocess.get(i);
			str2 = new StringTokenizer(str, delimiters);
			while (str2.hasMoreTokens()) {
				tmp = str2.nextToken().toLowerCase().trim();
				arrlist.add(tmp);
		}
		}
		/*for (int i = 0, len = arrlist.size(); i < len; i++) {
			for (int j = 0, len1 = stopWordsArray.length; j < len1; j++) {
				if (arrlist.get(i).equals(stopWordsArray[j])) {
					arrlist.remove(i);
					len--;
					i--;
				}
			}

		}*/
		
		Iterator<String> iter = arrlist.iterator();
		while(iter.hasNext()){
			String s = iter.next();
			for(int i1=0,len=stopWordsArray.length;i1<len;i1++){
				if(s.equals(stopWordsArray[i1])){
					iter.remove();
					break;
				}
			}
		}	
		
		//System.out.println(arrlist);
		//count all the words from the input.txt
		/*Hashtable<String, Integer> wordsCnt = new Hashtable<String, Integer>();
		for (int i = 0; i < arrlist.size(); i++) {
			String key = arrlist.get(i);
			if (wordsCnt.get(key) != null) {
				
				wordsCnt.put(key, wordsCnt.get(key) + 1);
			} else
				wordsCnt.put(key, 1);
		}*/
		
		//count some of the words
		Hashtable<String, Integer> wordsCnt = new Hashtable<String, Integer>();
		for (int ij = 0; ij < arrlist.size(); ij++) {
			String key = arrlist.get(ij);
			if (wordsCnt.get(key) != null) {
			
				wordsCnt.put(key, wordsCnt.get(key) + 1);
			} else
				wordsCnt.put(key, 1);
		}
		
		
		TreeMap<Integer, String> tree = new TreeMap<Integer, String>(new Comparator<Integer>(){  
         
			@Override
			public int compare(Integer o1, Integer o2) {
				  
				return o2.compareTo(o1);
			}     
        });
		for(Iterator<String> itr = wordsCnt.keySet().iterator();itr.hasNext();){
			String key = itr.next();
			int value = wordsCnt.get(key);
			if(tree.containsKey(value)){
				String tmpVa = tree.get(value);
				tree.put(value, tmpVa + " " + key);
			}
			else
				tree.put(value, key);
		} 
		int index= 0;
		for(Iterator<Integer> itr = tree.keySet().iterator();itr.hasNext();){
			int key = itr.next();
			//System.out.print(key+" ");
			String[] result = tree.get(key).split(" ");
			for(int i2 = 0;i2<result.length;i2++){
				if(index>=20)
					break;
				ret[index++]=result[i2];
				}
		}
		
        
		return ret;
    }
	
    public static void main(String[] args) throws Exception {
        if (args.length < 1){
            System.out.println("MP1 <User ID>");
        }
        else {
            String userName = args[0];
            String inputFileName = "./input.txt";
            MP1 mp = new MP1(userName, inputFileName);
            String[] topItems = mp.process();
            for (String item: topItems){
                System.out.println(item);
            }
        }
    }
}
