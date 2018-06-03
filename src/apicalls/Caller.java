package apicalls;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONArray;
import org.json.JSONObject;

import zookeeper.Configuration;

/**
 * Servlet implementation class Caller
 */
@MultipartConfig
@WebServlet("/Caller")
public class Caller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String errormessage;
	private static final String CHARSET = "UTF-8";

	public static String getErrorMessage() {
		return errormessage;
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
			System.out.println(jsonList.toString());
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
		else if(action.equals("getMyURL")) {
			response.setContentType("application/JSON");
			response.setCharacterEncoding("UTF-8");
			JSONObject myurl=new JSONObject();
			myurl.put("url", Configuration.getMyURL());
			response.getWriter().write(myurl.toString());
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding(CHARSET);
		String action = request.getParameter("action");
		String token = request.getParameter("token");
		if (action.equals("getImages")) {
			String galleryid = request.getParameter("gallid");
			String data = getGallery(token, galleryid).toString();
			response.setContentType("application/JSON");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(data);
			response.getWriter().flush();
			return;
		} else if (action.equals("getUsername")) {
			String data = getUsername(token).toString();
			response.setContentType("application/JSON");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(data);
			response.getWriter().flush();
			return;
		} else if (action.equals("postComment")) {
			String text = request.getParameter("text");
			String imageid = request.getParameter("imageid");
			String data = postComment(token, text, imageid).toString();
			response.setContentType("application/JSON");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(data);
			response.getWriter().flush();
			return;
		} else if (action.equals("postComment")) {
			String text = request.getParameter("text");
			String imageid = request.getParameter("imageid");
			String data = postComment("sth", text, imageid).toString();
			response.setContentType("application/JSON");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(data);
			response.getWriter().flush();
			return;
		} else if (action.equals("getComments")) {
			String imageid = request.getParameter("imageid");
			String data = getComments(token, imageid).toString();
			response.setContentType("application/JSON");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(data);
			response.getWriter().flush();
			return;
		} else if (action.equals("getGalleries")) {
			String owner = request.getParameter("user");
			String data = getGalleries(token, owner).toString();
			response.setContentType("application/JSON");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(data);
			response.getWriter().flush();
			return;
		} else if (action.equals("postImage")) {
			Part filePart = request.getPart("file");
			String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
			InputStream fileContent = filePart.getInputStream();
			System.out.println(fileName);
			String galleryid = request.getParameter("gallid");
			String data = postImage(token, fileContent, fileName, galleryid).toString();
			response.setContentType("application/JSON");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(data);
			response.getWriter().flush();
			return;
		} else if (action.equals("signout")) {
			HttpSession session = request.getSession(false);
			if (session != null) {
				session.invalidate();
			}
			response.sendRedirect("index.jsp");
			return;
		}
		else if (action.equals("getFriends")) {
			String data = getFriends(token).toString();
			response.setContentType("application/JSON");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(data);
			response.getWriter().flush();
			return;
		}
		else if (action.equals("addFriend")) {
			String friendname=request.getParameter("friendname");
			String data = addFriend(token,friendname).toString();
			response.setContentType("application/JSON");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(data);
			response.getWriter().flush();
			return;
		}
		else if (action.equals("deleteImage")) {
			String imageid=request.getParameter("imageid");
			String data = deleteImage(token,imageid).toString();
			response.setContentType("application/JSON");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(data);
			response.getWriter().flush();
			return;
		}
		else if (action.equals("deleteGallery")) {
			String galleryname=request.getParameter("galleryname");
			String data = deleteGallery(token,galleryname).toString();
			response.setContentType("application/JSON");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(data);
			response.getWriter().flush();
			return;
		}
	}

	private Object deleteGallery(String token, String galleryname) {
		try {
			System.out.println(Systems.getRandomDirectoryURL());
			Content content = Request
					.Post(Systems.getRandomDirectoryURL()).bodyForm(Form.form().add("token", token)
							.add("action", "deleteGallery")
							.add("galleryname",galleryname )
							.add("token", token)
							.build())
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

	private Object deleteImage(String token, String imageid) {
		try {
			System.out.println(Systems.getRandomDirectoryURL());
			Content content = Request
					.Post(Systems.getRandomDirectoryURL()).bodyForm(Form.form().add("token", token)
							.add("action", "deleteImage")
							.add("imageid",imageid )
							.add("token", token)
							.build())
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

	private Object addFriend(String token, String friendname) {
		try {
			System.out.println(Systems.getRandomDirectoryURL());
			Content content = Request
					.Post(Systems.getRandomDirectoryURL()).bodyForm(Form.form().add("token", token)
							.add("action", "addFriend")
							.add("friendname", friendname)
							.build())
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

	private Object getFriends(String token) {
		try {
			System.out.println(Systems.getRandomDirectoryURL());
			Content content = Request
					.Post(Systems.getRandomDirectoryURL()).bodyForm(Form.form().add("token", token)
							.add("action", "getFriends").build())
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

	private Object postImage(String token, InputStream fileContent, String fileName, String galleryid) {
		StringBody stringBodyToken = new StringBody(String.valueOf(token), ContentType.MULTIPART_FORM_DATA);
		StringBody stringBodyGalleryId = new StringBody(galleryid, ContentType.MULTIPART_FORM_DATA);
		StringBody stringBodyAction = new StringBody("postImage", ContentType.MULTIPART_FORM_DATA);
		HttpEntity entity = MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
				.setCharset(Charset.forName(CHARSET))
				.addBinaryBody("file", fileContent, ContentType.MULTIPART_FORM_DATA, fileName)
				.addPart("token", stringBodyToken).addPart("action", stringBodyAction)
				.addPart("galleryid", stringBodyGalleryId).build();

		try {
			Content content = Request.Post(Systems.getRandomDirectoryURL()).connectTimeout(20000).socketTimeout(20000)
					.body(entity).execute().returnContent();
			System.out.println(content.asString());

			JSONObject tokenjs = new JSONObject(content.asString());
			return tokenjs;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			JSONObject tokenjs = new JSONObject();
			tokenjs.put("error", e.getMessage());
			return tokenjs;
		} catch (IOException e) {
			e.printStackTrace();
			JSONObject tokenjs = new JSONObject();
			tokenjs.put("error", e.getMessage());
			return tokenjs;
		}

	}

	private Object getGalleries(String token, String owner) {
		try {
			System.out.println(Systems.getRandomDirectoryURL());
			Content content = Request
					.Post(Systems.getRandomDirectoryURL()).bodyForm(Form.form().add("token", token)
							.add("action", "getFriendGalleries").add("friendname", owner).build())
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

	private Object getComments(String token, String imageid) {
		try {
			System.out.println(Systems.getRandomDirectoryURL());
			Content content = Request.Post(Systems.getRandomDirectoryURL()).bodyForm(
					Form.form().add("token", token).add("action", "getComments").add("imageid", imageid).build())
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

	private Object postComment(String token, String text, String imageid) {
		try {
			System.out.println(Systems.getRandomDirectoryURL());
			Content content = Request.Post(Systems.getRandomDirectoryURL()).bodyForm(Form.form().add("token", token)
					.add("comment", text).add("action", "postComment").add("imageid", imageid).build()).execute()
					.returnContent();
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

	public static JSONObject getGallery(String token, String galleryid) {

		try {
			System.out.println(Systems.getRandomDirectoryURL());
			Content content = Request.Post(Systems.getRandomDirectoryURL()).bodyForm(
					Form.form().add("token", token).add("galleryid", galleryid).add("action", "getGallery").build())
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
		try {
			System.out.println(Systems.getRandomDirectoryURL());
			Content content = Request.Post(Systems.getRandomDirectoryURL())
					.bodyForm(Form.form().add("token", token).add("action", "getUsername").build()).execute()
					.returnContent();
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
		errormessage = response.getString("error");
		return null;

	}

}
