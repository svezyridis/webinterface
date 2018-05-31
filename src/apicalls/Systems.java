package apicalls;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import zookeeper.Configuration;

public class Systems {
	public static URI getRandomDirectoryURL() {
		List<Map> availableSystems = Configuration.getAvailableDs();
		int randomNum = ThreadLocalRandom.current().nextInt(0, availableSystems.size());
		Map randomElement = availableSystems.get(randomNum);
		try {
			URI uri=new URI(randomElement.get("URL").toString());
			return uri;
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return null;
		}
			
	}


}
