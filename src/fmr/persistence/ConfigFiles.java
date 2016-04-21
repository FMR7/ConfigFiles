package fmr.persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Easier config file managament.
 * @version 0.2
 * @author Fernando
 */
public class ConfigFiles {

    private String pv_fileName;
    private final String pv_fileExt;
    private File pv_confFile;
    private Properties pv_ppts;

    /**
     * Default constructor with default extension. "conf"
     * @since 0.1
     */
    public ConfigFiles() {
        this.pv_fileExt = "conf";
        this.pv_ppts = new Properties();
    }
    
    /**
     * Creates a new config file and write the properties of l_prop into it.
     * @param l_name File name, without extension.
     * @param l_prop Properties object to write.
     * @param l_tag Comments.
     * @since 0.1
     */
    public synchronized void newFile(String l_name, Properties l_prop, String l_tag){
        this.pv_fileName = l_name;
        this.pv_confFile = new File(this.pv_fileName + "." + this.pv_fileExt);
        this.pv_ppts = l_prop;
        
        System.out.println("");
        if ((pv_confFile.exists()) && (!pv_confFile.isDirectory())){
            System.out.println("WARN: newFile(); \nConfig file already exists, " + this.pv_confFile.getName() + "!!! Can't overwrite it!!!\n");
        }else{
            System.out.print("Creating config file...");
            BufferedWriter writer = null;
            try {
                writer = new BufferedWriter(new FileWriter(pv_confFile, true));
                FileOutputStream fileOut = new FileOutputStream(pv_confFile);

                this.pv_ppts.store(fileOut, l_tag);
                
                System.out.println("Created.");
                System.out.println("Config File Location: " + pv_confFile.getCanonicalPath() + "\n");
            } catch (Exception e) {
                System.out.println("WARN: Fail creating the file!!!");
                e.printStackTrace();
            } finally {
                try {
                    writer.close();
                } catch (Exception e) {
                    System.out.println("WARN: Fail closing the file!!!");
                    e.printStackTrace();
                }

            }
        }
    }
    
    /**
     * Returns the Properties object from the file, if exists.
     * @param l_name File name, without extension.
     * @return Properties object to read the properties or NULL if don't exists.
     * @since 0.1
     */
    public synchronized Properties loadFile(String l_name){
        this.pv_fileName = l_name;
        this.pv_confFile = new File(this.pv_fileName + "." + this.pv_fileExt);
        
        System.out.println("");
        if ((pv_confFile.exists()) && (!pv_confFile.isDirectory())){
            try {
                System.out.print("Loading config file...");
                FileInputStream fis = new FileInputStream(pv_confFile);
                BufferedReader in = new BufferedReader(new InputStreamReader(fis));
                
                this.pv_ppts.load(in);
                System.out.println("Loaded.");
                System.out.println("File Location: " + pv_confFile.getCanonicalPath() + "\n");
                in.close();
                fis.close();
            } catch (IOException ex) {
                Logger.getLogger(ConfigFiles.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            System.out.println("WARN: loadFile(); \nConfig file, " + this.pv_confFile.getName() + " not found!!! Can't load it!!!\n");
            this.pv_ppts = null;
        }
        return(this.pv_ppts);
    }
    
    /**
     * Returns the Properties object from the file, if exists.
     * @param l_name File name, without extension.
     * @param debug If true, shows the keys and the values.
     * @return Properties object to read the properties or NULL if don't exists.
     * @since 0.2
     */
    public synchronized Properties loadFile(String l_name, boolean debug){
        this.pv_fileName = l_name;
        this.pv_confFile = new File(this.pv_fileName + "." + this.pv_fileExt);
        
        System.out.println("");
        if ((pv_confFile.exists()) && (!pv_confFile.isDirectory())){
            try {
                System.out.print("Loading config file...");
                FileInputStream fis = new FileInputStream(pv_confFile);
                BufferedReader in = new BufferedReader(new InputStreamReader(fis));
                
                this.pv_ppts.load(in);
                System.out.println("Loaded.");
                System.out.println("File Location: " + pv_confFile.getCanonicalPath() + "\n");
                in.close();
                fis.close();
                if(debug){
                    Set<Object> keySet = this.pv_ppts.keySet();
                    Collection<Object> valueSet = this.pv_ppts.values();
                    System.out.println("PROPERTIES:");
                    
                    //Get keys
                    String[] keys = new String[keySet.size()];
                    int counter = 0;
                    for(Object k: keySet){
                        keys[counter] = k.toString();
                        counter++;
                    }
                    
                    //Get values
                    String[] values = new String[valueSet.size()];
                    counter = 0;
                    for(Object v: valueSet){
                        values[counter] = v.toString();
                        counter++;
                    }
                    //Show keys and values
                    for(int i = 0; i < keys.length; i++){
                        System.out.println(keys[i] + "=" + values[i]);
                    }
                    System.out.println("");
                }
            } catch (IOException ex) {
                Logger.getLogger(ConfigFiles.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            System.out.println("WARN: loadFile(); \nConfig file, " + this.pv_confFile.getName() + " not found!!! Can't load it!!!\n");
            this.pv_ppts = null;
        }
        return(this.pv_ppts);
    }
    
    /**
     * Deletes a config file.
     * @param l_name File name, without extension.
     * @return true if deleted, false if not.
     * @since 0.1
     */
    public synchronized boolean deleteFile(String l_name){
        boolean b = false;
        
        this.pv_fileName = l_name;
        this.pv_confFile = new File(this.pv_fileName + "." + this.pv_fileExt);
        
        System.out.println("");
        if ((pv_confFile.exists()) && (!pv_confFile.isDirectory())){
            try{
                System.out.print("Deleting config file...");
                this.pv_confFile.delete();
                b = true;
                System.out.println("Deleted.");
            }catch(Exception e){
                System.out.println("WARN: deleteFile(); \nFile not found or opened, " + this.pv_confFile.getName() + "!!!\n");
                e.printStackTrace();
            }
        }
        return b;
    }
    
    /**
     * Updates a config file and write the properties of l_prop into it.
     * @param l_name File name, without extension.
     * @param l_prop Properties object to write.
     * @param l_tag  First commented line of the file.
     * @since 0.1
     */
    public synchronized void updateFile(String l_name, Properties l_prop, String l_tag){
        this.pv_fileName = l_name;
        this.pv_confFile = new File(this.pv_fileName + "." + this.pv_fileExt);
        this.pv_ppts = l_prop;
        
        System.out.println("");
        if ((pv_confFile.exists()) && (!pv_confFile.isDirectory())){
            System.out.print("Updating config file...");
            BufferedWriter writer = null;
            try {
                writer = new BufferedWriter(new FileWriter(pv_confFile, true));
                FileOutputStream fileOut = new FileOutputStream(pv_confFile);

                this.pv_ppts.store(fileOut, l_tag);
                
                System.out.println("Updated.");
                System.out.println("Config File Location: " + pv_confFile.getCanonicalPath() + "\n");
            } catch (Exception e) {
                System.out.println("WARN: Fail updating the file!!!");
                e.printStackTrace();
            } finally {
                try {
                    writer.close();
                } catch (Exception e) {
                    System.out.println("WARN: Fail closing the file!!!");
                    e.printStackTrace();
                }

            }
            
        }else{
            System.out.println("WARN: updateFile(); \nConfig file, " + this.pv_confFile.getName() + " not found!!! Can't update it!!!\n");
        }
    }
    
}
