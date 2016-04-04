# ConfigFiles

[Download JAR](https://github.com/FMR7/ConfigFiles/raw/master/dist/ConfigFiles.jar)

[Download source](https://github.com/FMR7/ConfigFiles/archive/master.zip)

###import com.persistence.ConfigFiles;

####Create the properties.
Properties newProperties = new Properties();

newProperties.setProperty("user", "admin");

newProperties.setProperty("pass", "12345");

####Call the class.
ConfigFiles cf = new ConfigFiles();

####Create a new config file. 
//1-Filename without extension. 2-Properties. 3-Comments.

cf.newFile("db_data", newProperties, "DataBase configuration.");

####Update a config file
newProperties.setProperty("user", "root");

newProperties.setProperty("pass", "toor");

cf.updateFile("db_data213", newProperties, "DataBase configuration. UPDATED");

####Load the properties of a file. Filename without extension.
Properties loadedProperties = cf.loadFile("db_data");

####Show loaded properties
if(loadedProperties != null){

    System.out.println(loadedProperties.getProperty("user") + ":" + loadedProperties.getProperty("pass"));
    
}

####Delete config file
cf.deleteFile("db_data");
