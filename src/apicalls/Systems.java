package apicalls;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import zookeeper.Configuration;

public class Systems extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CHARSET = "UTF-8";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		if (action.equals("getAvailableAs")) {
			List<Map> availableAS = Configuration.getAvailableAs();
			List<JSONObject> jsonList = new ArrayList<JSONObject>();
			for (Map system : availableAS) {
				JSONObject obj = new JSONObject(system);
				jsonList.add(obj);
			}
			response.setContentType("application/JSON");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(jsonList.toString());
		}
		else if(action.equals("getAvailableDs")) {
			List<Map> availableDS = Configuration.getAvailableDs();
			List<JSONObject> jsonList = new ArrayList<JSONObject>();
			for (Map system : availableDS) {
				JSONObject obj = new JSONObject(system);
				jsonList.add(obj);
			}
			response.setContentType("application/JSON");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(jsonList.toString());
			
		}
	}

	public static URI getRandomDirectoryURL() {
		List<Map> availableSystems = Configuration.getAvailableDs();
		int randomNum = ThreadLocalRandom.current().nextInt(0, availableSystems.size());
		Map randomElement = availableSystems.get(randomNum);
		try {
			URI uri = new URI(randomElement.get("URL").toString());
			return uri;
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return null;
		}

	}

}
