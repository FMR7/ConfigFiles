import fmr.persistence.ConfigFiles;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Properties;

public class ConfigFilesTestCreate {
    ConfigFiles cf = new ConfigFiles();
    @Test @Before
    public void newFile() throws Exception {
        cf.deleteFile("Test");
        Properties prop = new Properties();
        prop.setProperty("user", "admin");
        prop.setProperty("pass", "12345");
        cf.newFile("Test", prop, "Test File");
        Assert.assertNotEquals(null, prop);
    }

    @Test
    public void loadFile() throws Exception {
        Properties prop;
        prop = cf.loadFile("Test");
        String usr = prop.getProperty("user");
        String pwd = prop.getProperty("pass");
        Assert.assertEquals("admin", usr);
        Assert.assertEquals("12345", pwd);
        boolean test = cf.deleteFile("Test");
        Assert.assertEquals(true, test);
    }
}