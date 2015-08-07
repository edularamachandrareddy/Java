import java.util.Hashtable;
import java.net.HttpURLConnection;
import java.net.URL;

public class DataScraper {

	/**
	 * @param args
	 * http://mlai-lirik.blogspot.com/2010/04/getting-data-real-time-data.html
	 * 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/* HashTable to store all the stock symbols and the description.
		 * This can later be used by a gui to pick and chose which ones I may want.
		 * 
		 */
		Hashtable<String, String> dictionary = new Hashtable<String, String>();

		
		
		
	}
	private void DownLoadData(String stockSymbol){
		URL u = new URL();
		HttpURLConnection connection;
	};

}
