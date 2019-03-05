/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package addprojectheader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
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
    static long FileCount = 0;
    static long FilesChanged = 0;
    static long FilesNtChanged = 0;
    static String strFilesChanged = "";
    static String strDirItertd = "";
    static String strFilesNtChanged = "";
    static final String strMainDirPath = "C:\\Users\\Eshwar SIX\\Documents\\NetBeansProjects\\AddProjectHeader\\SampleProject";
    static int InnerCallCount = 0;
    
    static File mLogFile = new File(strMainDirPath + "\\log.txt");

    public static void main(String[] args) throws IOException {
        Log("Main Directory \r\n:: " + strMainDirPath + "\\* ::\r\n");
        ListFiles(strMainDirPath);
        Log("--------------------------------- SUMMARY --");
        Log("Total Directories Iter : \r\n" + strDirItertd);
        Log("Total Files Count : " + FileCount);
        Log("Files Changed Count : " + FilesChanged);
        Log("Files Changed : \r\n" + strFilesChanged);
        Log("Files Not Changed : \r\n" + strFilesNtChanged);
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
                    Log(GetTabbedStr(InnerCallCount) + "DIRECTORY - " + file.getCanonicalPath());
                    ++InnerCallCount;
                    int dwMfdCount = ListFiles(file.getCanonicalPath());
                    --InnerCallCount;
                    Log(GetTabbedStr(InnerCallCount) + "Modified : " + dwMfdCount + "\r\n");
                    strDirItertd += file.getCanonicalPath() + "\r\n";
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
                        strFilesChanged += file.getAbsolutePath() + "\r\n";
                        Log(GetTabbedStr(InnerCallCount) + (++FilesChanged) + " Done.." + file.getAbsoluteFile());
                    }
                    else
                    {
                        Log(GetTabbedStr(InnerCallCount)+ "!" + (++FilesNtChanged) + " Not Done.." + file.getAbsoluteFile());
                        strFilesNtChanged += file.getAbsolutePath() + "\r\n";
                    }
                }
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return dwFilesModified;
    }
    
    public static void Log(String strOutputString) throws IOException
    {
        System.out.println("\r\n"+strOutputString);
        if (!mLogFile.exists()) 
        {
            mLogFile.createNewFile();
        }
        
        BufferedWriter bw;
        FileWriter fw;
        fw = new FileWriter(mLogFile.getAbsoluteFile(), true);
        bw = new BufferedWriter(fw);
        bw.write("\r\n"+strOutputString);
        if (bw != null)
            bw.close();

        if (fw != null)
            fw.close();
    }
    
    public static String GetTabbedStr(int dwTabCount)
    {
        String strFormattedString = "";
        while(dwTabCount > 0)
        {
            strFormattedString += "\t";
            dwTabCount--;
        }
        return strFormattedString;
    }
}
