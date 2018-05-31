package apicalls;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Servlet implementation class Caller
 */
@WebServlet("/Caller")
public class Caller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String errormessage;
	public static String getErrorMessage() {
		return errormessage;
	}

	public static JSONObject GetToken(String system, String username, String password) {
		try {
			Content content = Request
					.Post("https://snf-815309.vm.okeanos.grnet.gr:8443/AuthenticationService/ApiAuthentication")
					.bodyForm(Form.form().add("system", system).add("password", password).add("username", username)
							.add("action", "authenticate").build())
					.execute().returnContent();
			JSONObject tokenjs = new JSONObject(content.asString());
			JSONObject token = new JSONObject();
			token.put("error", "");
			token.put("token", tokenjs);
			System.out.println(token.toString());

			return token;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			JSONObject token = new JSONObject();
			token.put("error", e.getMessage());
			return token;
		}
	}

	public static JSONObject getGallery(String token, String galleryid) {
		JSONObject auth = GetToken("DIRID", "savvas1", "savvas1");
		auth = auth.getJSONObject("token");
		auth = auth.getJSONObject("data");
		System.out.println(auth.toString());

		try {
			System.out.println(Systems.getRandomDirectoryURL());
			Content content = Request.Post(Systems.getRandomDirectoryURL()).bodyForm(Form.form()
					.add("token", auth.toString()).add("galleryid", galleryid).add("action", "getGallery").build())
					.execute().returnContent();
			System.out.println(content.asString());

			JSONObject tokenjs = new JSONObject(content.asString());
			return tokenjs;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JSONObject tokenjs = new JSONObject();
			tokenjs.put("error", e.getMessage());
			return tokenjs;
		}
	}
	public static JSONObject getUsername(String token) {
		JSONObject auth = GetToken("DIRID", "savvas1", "savvas1");
		auth = auth.getJSONObject("token");
		auth = auth.getJSONObject("data");
		System.out.println(auth.toString());

		try {
			System.out.println(Systems.getRandomDirectoryURL());
			Content content = Request.Post(Systems.getRandomDirectoryURL()).bodyForm(Form.form()
					.add("token", auth.toString()).add("action", "getUsername").build())
					.execute().returnContent();
			System.out.println(content.asString());

			JSONObject tokenjs = new JSONObject(content.asString());
			return tokenjs;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JSONObject tokenjs = new JSONObject();
			tokenjs.put("error", e.getMessage());
			return tokenjs;
		}
	}

	public static List<JSONObject> getImages(String token, String galleryid) {
		JSONObject response = getGallery(token, galleryid);
		if (response.getString("error").equals("")) {
			JSONArray array = response.getJSONArray("result");
			List<JSONObject> listJSON = new ArrayList<JSONObject>();
			for (int n = 0; n < array.length(); n++) {
				JSONObject object = array.getJSONObject(n);
				listJSON.add(object);

			}
			return listJSON;
		}
		errormessage=response.getString("error");
		return null;

	}

	public static List<JSONObject> getComments(JSONObject image) {
		JSONArray array = image.getJSONArray("comments");
		List<JSONObject> listJSON = new ArrayList<JSONObject>();
		for (int n = 0; n < array.length(); n++) {
			JSONObject object = array.getJSONObject(n);
			listJSON.add(object);

		}
		return listJSON;

	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Caller() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String data = "Hello World!";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(data);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {		
		String action = request.getParameter("action");
		if (action.equals("getImages")) {
			String data = getGallery("sth","1").toString();
			response.setContentType("application/JSON");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(data);
			response.getWriter().flush();
			return;
		}
		else if (action.equals("getUsername")) {
			String data = getUsername("sth").toString();
			response.setContentType("application/JSON");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(data);
			response.getWriter().flush();
			return;
		}
		else if (action.equals("postComment")) {
			String text=request.getParameter("text");
			String imageid=request.getParameter("imageid");
			String data = postComment("sth",text,imageid).toString();
			response.setContentType("application/JSON");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(data);
			response.getWriter().flush();
			return;
		}
		else if (action.equals("postComment")) {
			String text=request.getParameter("text");
			String imageid=request.getParameter("imageid");
			String data = postComment("sth",text,imageid).toString();
			response.setContentType("application/JSON");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(data);
			response.getWriter().flush();
			return;
		}
		else if (action.equals("getComments")) {
			String imageid=request.getParameter("imageid");
			String data = getComments("sth",imageid).toString();
			response.setContentType("application/JSON");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(data);
			response.getWriter().flush();
			return;
		}
	}

	private Object getComments(String string, String imageid) {
		JSONObject auth = GetToken("DIRID", "savvas1", "savvas1");
		auth = auth.getJSONObject("token");
		auth = auth.getJSONObject("data");
		try {
			System.out.println(Systems.getRandomDirectoryURL());
			Content content = Request.Post(Systems.getRandomDirectoryURL()).bodyForm(Form.form()
					.add("token", auth.toString()).add("action", "getComments").add("imageid", imageid).build())
					.execute().returnContent();
			System.out.println(content.asString());

			JSONObject tokenjs = new JSONObject(content.asString());
			return tokenjs;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JSONObject tokenjs = new JSONObject();
			tokenjs.put("error", e.getMessage());
			return tokenjs;
		}
	}

	private Object postComment(String token, String text,String imageid) {
		JSONObject auth = GetToken("DIRID", "savvas1", "savvas1");
		auth = auth.getJSONObject("token");
		auth = auth.getJSONObject("data");
		try {
			System.out.println(Systems.getRandomDirectoryURL());
			Content content = Request.Post(Systems.getRandomDirectoryURL()).bodyForm(Form.form()
					.add("token", auth.toString()).add("comment", text).add("action", "postComment").add("imageid", imageid).build())
					.execute().returnContent();
			System.out.println(content.asString());

			JSONObject tokenjs = new JSONObject(content.asString());
			return tokenjs;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JSONObject tokenjs = new JSONObject();
			tokenjs.put("error", e.getMessage());
			return tokenjs;
		}
	}

}
