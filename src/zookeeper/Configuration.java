package zookeeper;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.zookeeper.*;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.json.JSONObject;




/**
 * Application Lifecycle Listener implementation class Zooconf
 *
 */
@WebListener
public class Configuration implements ServletContextListener {
	private static  List<String> zookeeperIPs = new ArrayList<String>();
	private String host="";
	private static String myip;
	private static String identifier;
	private static String secretkey;
	private static String name;
	private static String dirpath;
	private static String authpath;
	private static String webpath;
	private static String zoouser; 
	private static String zoopass; 
	private  ZooKeeper zoo;
	private List<Map> authSystems=null;
	private List<Map> dirSystems=null;
	final CountDownLatch connectedSignal = new CountDownLatch(1);
	private static Configuration ConfInstance = null;
	
	public static List<Map> getAvailableAs() {
		Configuration instance = getInstance();
		return instance.authSystems;
	}	
public static List<Map> getAvailableDs() {
	Configuration instance = getInstance();
	return instance.dirSystems;
	}
public static String getMyURL() {
	Configuration instance = getInstance();
	return instance.myip;
}
	
		
	public static String getMyIdentifier() {
		Configuration instance = getInstance();
		return instance.identifier;
	}
	
	public static String getZookeeperIPs(){
		Configuration instance = getInstance();
		
		boolean first=true;
		for (String ip:zookeeperIPs) {
			if (first) {
				instance.host+=ip;
				first =false;
			}
			else
				instance.host=instance.host+","+ip;				
		}
		return instance.host;
	}
	
	private ZooKeeper zooConnect() throws IOException,InterruptedException {
		System.out.println("start zooConnect on "+getZookeeperIPs());
		
		ZooKeeper zk = new ZooKeeper(getZookeeperIPs(), 3000, new Watcher() {
			@Override
			public void process(WatchedEvent we) {
				if (we.getState() == KeeperState.SyncConnected) {
					connectedSignal.countDown();
				}
			}
		});
		connectedSignal.await();
		
		zk.addAuthInfo("digest", new String(zoouser+":"+zoopass).getBytes());
		
		System.out.println("finished zooConnect");

		return zk;
	}

	public static ZooKeeper getZooConnection() {
		Configuration instance = getInstance();
		return instance.zoo;
	}
class AsWatcher implements Watcher {
        
        public void process(WatchedEvent event) {
            System.err.println("Watcher triggered");
			Watcher watcher = new AsWatcher();
			watchForAsChanges(watcher);
        }
    }

	private void initAsWatches() {
		authSystems = Collections.synchronizedList(new ArrayList<Map>());
		Watcher watcher = new AsWatcher();
		watchForAsChanges(watcher);
	}
	
	
	private void watchForAsChanges(Watcher watcher) {
		// we want to get the list of available FS, and watch for changes
		Configuration instance = getInstance();
		try {
			authSystems.clear();
			List<String> asChildren = zoo.getChildren(authpath, watcher);
			for (String as : asChildren) {
				Map<String,String> system = null;
				Stat stat = zoo.exists(authpath+"/"+as, watcher);
				byte[] data=zoo.getData(authpath+"/"+as, watcher, stat);
				JSONObject datajson=new JSONObject(new String(data));
				system=new HashMap<String,String>();
				system.put("registerurl", datajson.getString("registerURL"));
				system.put("loginurl", datajson.getString("loginURL"));
				system.put("name", datajson.getString("name"));
				authSystems.add(system);	
			}
		}
		catch (KeeperException ex) {
			System.err.println("getStatusText KeeperException "+ex.getMessage());
		}
		catch (InterruptedException ex) {
			System.err.println("getStatusText InterruptedException");
		}
		
	}
class DsWatcher implements Watcher {
        
        public void process(WatchedEvent event) {
            System.err.println("Watcher triggered");
			Watcher watcher = new DsWatcher();
			watchForDsChanges(watcher);
        }
    }

	private void initDsWatches() {
		dirSystems = Collections.synchronizedList(new ArrayList<Map>());
		Watcher watcher = new DsWatcher();
		watchForDsChanges(watcher);
	}
	
	
	private void watchForDsChanges(Watcher watcher) {
		// we want to get the list of available FS, and watch for changes
		Configuration instance = getInstance();
		try {
			dirSystems.clear();
			List<String> dsChildren = zoo.getChildren(dirpath, watcher);
			for (String ds : dsChildren) {
				Map<String,String> system = null;
				Stat stat = zoo.exists(dirpath+"/"+ds, watcher);
				byte[] data=zoo.getData(dirpath+"/"+ds, watcher, stat);
				JSONObject datajson=new JSONObject(new String(data));
				system=new HashMap<String,String>();
				system.put("URL", datajson.getString("URL"));
				system.put("identifier", datajson.getString("id"));
				dirSystems.add(system);	
			}
		}
		catch (KeeperException ex) {
			System.err.println("getStatusText KeeperException "+ex.getMessage());
		}
		catch (InterruptedException ex) {
			System.err.println("getStatusText InterruptedException");
		}
		
	}
	public void ReadConfigurationFile() {
		Configuration instance=getInstance();
		URL resource = getClass().getResource("/");
		String path = resource.getPath();
		path = path.replace("WEB-INF/classes/", "conf/config.xml");
		//Read configuration file
	        try {
	            File inputFile = new File(path);
	            SAXBuilder saxBuilder = new SAXBuilder();
	            Document document = saxBuilder.build(inputFile);
	            System.out.println("Root element :" + document.getRootElement().getName());
	            Element classElement = document.getRootElement();
	            System.out.println("----------------------------");
	            Element setting=classElement.getChild("zookeeper");
	            System.out.println("\nCurrent Element webservice :" 
		                  + setting.getName());
	            List<Element>ips=setting.getChildren("zooip");
	            for(Element ip:ips) {
	            	instance.zookeeperIPs.add(ip.getValue().toString());
	            	System.out.println(ip.getValue().toString());
	                
	            	
	            }
	            instance.dirpath=setting.getChild("dirservicepath").getValue();
	            instance.authpath=setting.getChild("authservicepath").getValue();
	            instance.webpath=setting.getChild("webservicepath").getValue();
	            instance.zoouser=setting.getChild("zoouser").getValue();
	            instance.zoopass=setting.getChild("zoopass").getValue();    	          
	            instance.name=classElement.getChild("name").getValue();
	            instance.myip=classElement.getChild("hostname").getValue();
	            
	        

	            
	         } catch(JDOMException e) {
	            e.printStackTrace();
	         } catch(IOException ioe) {
	            ioe.printStackTrace();
	         }
		
	}
	public void PublishService(ServletContextEvent sce) {
		Configuration instance=getInstance();
		ACL acl = null;
		try {
			String base64EncodedSHA1Digest = Base64.getEncoder().encodeToString(MessageDigest.getInstance("SHA1").digest((zoouser+":"+zoopass).getBytes()));
			acl = new ACL(ZooDefs.Perms.ALL, new Id("digest",zoouser+":" + base64EncodedSHA1Digest));
		}
		catch (NoSuchAlgorithmException ex) {
			System.err.println("destroy NoSuchAlgorithmException");
		}
		
	     
	       JSONObject configJSON=new JSONObject();
	     
	       configJSON.put("name", instance.name);
	       configJSON.put("URL", instance.myip);
	       
	       System.out.println("publishing service");  
	       try {
			Stat stat = instance.zoo.exists(webpath, false);
			if(stat==null) {
				System.out.println("Node does not exist, creating node");
				instance.zoo.create(webpath, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
						CreateMode.PERSISTENT);
			}
			instance.zoo.create(webpath+"/"+name, configJSON.toString().getBytes(),Arrays.asList(acl),
					CreateMode.EPHEMERAL);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static Configuration getInstance() {
		if (ConfInstance == null) {
			ConfInstance = new Configuration();
		}
		return ConfInstance;
	}

	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		 System.err.println("Dirservice Context destroyed");
		 Configuration instance = getInstance();
			try {
				if (instance.zoo != null) {
					instance.zoo.close();
				}
			}
			catch ( InterruptedException ex) {
				System.err.println("destroy InterruptedException");
			}

		
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		Configuration instance = getInstance();
		instance.ReadConfigurationFile();
	
		try {
			instance.zoo = instance.zooConnect();
			instance.PublishService(sce);
			instance.initAsWatches();
			instance.initDsWatches();
	  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    }

	
		

	
}
