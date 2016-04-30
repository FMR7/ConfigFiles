import fmr.persistence.ConfigFiles;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Properties;

public class ConfigFilesTestUpdate {
    ConfigFiles cf = new ConfigFiles();
    @Test @Before
    public void newFile() throws Exception {
        cf.deleteFile("Test_Up");
        Properties prop = new Properties();
        prop.setProperty("user", "admin");
        prop.setProperty("pass", "12345");
        cf.newFile("Test_Up", prop, "Test File");
        Assert.assertNotEquals(null, prop);
    }

    @Test
    public void updateFile() throws Exception {
        Properties prop = new Properties();
        prop.setProperty("user", "root");
        prop.setProperty("pass", "toor");
        cf.updateFile("Test_Up", prop, "Test Updated");
        prop = cf.loadFile("Test_Up");
        String usr = prop.getProperty("user");
        String pwd = prop.getProperty("pass");
        Assert.assertEquals("root", usr);
        Assert.assertEquals("toor", pwd);
        boolean test = cf.deleteFile("Test_Up");
        Assert.assertEquals(true, test);
    }
}