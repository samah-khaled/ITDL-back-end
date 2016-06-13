package intelligent_To_Do.Controller;

import intelligent_To_Do.Model.MeetingNoteEntity;
import intelligent_To_Do.Model.NoteEntity;
import intelligent_To_Do.Model.OrdinaryNoteEntity;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;





@Path("/")
@Produces("text/html")
public class UserController {

	@GET
	@Path("/")
	public String  index() {
		return "<p> in controller  </p>";
		
	}

	@POST
	@Path("/getuserinfo")
	public Response getuserinfo (@FormParam("userId") String id){
		String serviceUrl = "http://localhost:8888/rest/GetUserInfoService";
		String urlParameters ="userId="+ id;
		
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		Map<String, String> user = new HashMap<String, String>();

		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			/*User(String userName, String userEmail,  String userPassword
				,String userTwitterAccount, String gender, String city,
				String dateOfBirth)*/
			user.put("username", object.get("username").toString());
			user.put("useremail", object.get("useremail").toString());
			user.put("userpassword", object.get("userpassword").toString());
			user.put("usertwiterAcc", object.get("usertwiterAcc").toString());
			user.put("usergender", object.get("usergender").toString());
			user.put("usercity", object.get("usercity").toString());
			user.put("userbirthdate", object.get("userbirthdate").toString());
			//return Response.ok(new Viewable("/jsp/ViewUserInfo"), user).build();
			return Response.ok(new Viewable("/jsp/ViewUserInfo", user)).build(); 

					//object.get("Status"
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return Response.ok(new Viewable("/html/index", user)).build();
	}
	@POST
	@Path("/signup")
	public String Signup (@FormParam("username") String uname,
			 @FormParam("userpassword") String pass ,@FormParam("useremail") String email 
				,@FormParam("gender") String gender,@FormParam("city") String city
				,@FormParam("birth_date")String birth_date,@FormParam("Twitter_Account")String Twitter_Account){

		String serviceUrl = "http://localhost:8888/rest/RegestrationService";
		String urlParameters = "username=" + uname + "&userpassword=" + pass
				+ "&useremail=" + email + "&gender=" + gender
				+ "&city=" + city +"&birth_date="+birth_date+"&Twitter_Account="+Twitter_Account;
	
String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
		"application/x-www-form-urlencoded;charset=UTF-8");

JSONParser parser = new JSONParser();
Object obj;
	try {
		obj = parser.parse(retJson);
		JSONObject object = (JSONObject) obj;
		if (object.get("Status").equals("OK"))
			return "Registered Successfully <br> your id is "+object.get("userId");

	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
return "Failed";
	}
	
	
@POST
@Path("/signin")
public String SignIn(@FormParam("useremail") String email,@FormParam("userpassword") String pass ){
	
	String serviceUrl = "http://localhost:8888/rest/LoginService";
	String urlParameters = "useremail=" + email + "&userpassword=" + pass;
	
	String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
			"application/x-www-form-urlencoded;charset=UTF-8");

	JSONParser parser = new JSONParser();
	Object obj;
	//Map<String, String> map = new HashMap<String, String>();

	try {
		obj = parser.parse(retJson);
		JSONObject object = (JSONObject) obj;
		if (object.get("Status").equals("OK")){
			return "<p>"+object.get("id")+"</p><br>"
				 + "<p>"+object.get("name")+"</p>";
			//map.put("id", object.get("userId").toString());
			//return Response.ok(new Viewable("/jsp/home", map)).build(); 

			//return Response.ok(new Viewable("/jsp/home"), map).build();
		}

	} catch (ParseException e) {
		e.printStackTrace();
	}
	//return Response.ok(new Viewable("/html/index")).build();
return "Failed";
}
	
@POST
@Path("/SYnc")
public String SYnc(@FormParam("NoteList") String nnotes){
	//NoteEntity noteobj ;
	/*ArrayList<NoteEntity> nnotes=new ArrayList<NoteEntity>();
	noteobj =new OrdinaryNoteEntity("33", "5678", Timestamp.valueOf("2016-04-27 13:50:25.0"),
			false, false, "Ordinary", "note from controller");
	nnotes.add(noteobj);
	noteobj= new MeetingNoteEntity(java.sql.Date.valueOf("2016-04-27"), Time.valueOf("13:49:00"), 
			"contr meeting", "mmmm", 
			"agnda", "12","123", Timestamp.valueOf("2016-04-30 13:50:25.0"), false, false, "Meeting");
	nnotes.add(noteobj);
	System.out.println("notessobjs=="+nnotes);*/
	
	String serviceUrl = "http://localhost:8880/rest/synchroinzationService";
	String urlParameters = "NoteList=" + nnotes;
	
	String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
			"application/x-www-form-urlencoded;charset=UTF-8");
	/*
	JSONParser parser =new JSONParser();
	try {
		JSONArray array = (JSONArray) parser.parse(retJson);
		for (int i=0 ;i<array.size();i++)
		{ JSONObject object ;
		object =(JSONObject) array.get(i);
		String status=object.get("syncType").toString();
		String noteId=object.get("noteid").toString();
		
		System.out.println("<p> syncType= "+status+"</p><br>"
		  + "<p> noteId="+noteId+"</p>");
		}
		
	} catch (ParseException e) {
		e.printStackTrace();
	}*/
	//return Response.ok(new Viewable("/html/index")).build();
return "Failed";
}
}
