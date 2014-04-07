package alisovets.example.simpletesttask.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class URLReader {
	/**
	 * Gets a raw string from url
	 * @param urlString - the url where the desired data is
	 * @return the gotten string
	 * @throws IOException if there are problems at receiving data
	 */
	public static String obtainStringFromUrl(String urlString) throws IOException {

		URL url = new URL(urlString);
		URLConnection urlConnection = url.openConnection();
		urlConnection.setConnectTimeout(1500);
		urlConnection.setReadTimeout(1500);
		urlConnection.setDoOutput(true);
		InputStream inputStream = urlConnection.getInputStream();
		

		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
		String result = "";
		String line;
		try {
			while((line = br.readLine()) != null){
				result += line;
			}
		} finally {
			br.close();
		}
		return result;
	}

}
