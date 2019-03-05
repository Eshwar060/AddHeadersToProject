/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package addprojectheader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author Eshwar SIX
 */
public class AddProjectHeader 
{

    /**
     * @param args the command line arguments
     */
    static int FileCount = 0;
    static int FilesChanged = 0;
    static String strFilesChanged = "";
    static String strFilesNtChanged = "";
    public static void main(String[] args) {
        String strMainDirPath = "C:\\Users\\Eshwar SIX\\Documents\\NetBeansProjects\\AddProjectHeader\\SampleProject";
        Log("Main Directory \n:: " + strMainDirPath + "\\* ::");
        ListFiles(strMainDirPath);
        
        Log("Total Files Count : " + FileCount);
        Log("Files Changed Count : " + FilesChanged);
        Log("Files Changed : \n" + strFilesChanged);
        Log("Files Not Changed : \n" + strFilesNtChanged);
    }
    
    public static int ListFiles(String strMainDirPath) 
    {
        int dwFilesModified = 0;
        try 
        {
            File MainDir = new File(strMainDirPath);
            File[] files = MainDir.listFiles();
            for (File file : files)
            {
                if (file.isDirectory()) 
                {
                    Log("DIRECTORY - " + file.getCanonicalPath());
                    int dwMfdCount = ListFiles(file.getCanonicalPath());
                    Log("Modified : " + dwMfdCount + "\n");
                } 
                else if(file.isFile())
                {
                    //Log("Done.." + file.getAbsoluteFile());
                    FileCount++;
                    String strFileName = file.getName();
                    String strFileExt = strFileName.substring(strFileName.indexOf('.') + 1);
                    boolean bIsSpecificExtFile = strFileExt.equalsIgnoreCase("java") || strFileExt.equalsIgnoreCase("jsp");
                    if(bIsSpecificExtFile)
                    {
                        String result = "";                     
                        String strAddHeaderContent = //"/**";
"/**\n" +
" * To change this license header, choose License Headers in Project Properties.\n" +
" * To change this template file, choose Tools | Templates\n" +
" * and open the template in the editor.\n" +
" */";

                        FileInputStream fileInputStream = new FileInputStream(file);
                        byte[] FileData = new byte[(int) file.length()];
                        fileInputStream.read(FileData);
                        fileInputStream.close();

                        String fileContent = new String(FileData, "UTF-8");

                        result = strAddHeaderContent + "\n" + fileContent;

                        file.delete();
                        FileOutputStream fos = new FileOutputStream(file);
                        fos.write(result.getBytes());
                        fos.flush();
                        dwFilesModified++;
                        FilesChanged++;
                        strFilesChanged += file.getAbsolutePath() + "\n";
                        Log("Done.." + file.getAbsoluteFile());
                    }
                    else
                    {
                        Log("Not Done.." + file.getAbsoluteFile());
                        strFilesNtChanged += file.getAbsolutePath() + "\n";
                    }
                }
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return dwFilesModified;
    }
    
    public static void Log(String strOutputString)
    {
        System.out.println(strOutputString);
    }
}
