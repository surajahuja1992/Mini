package com.tiny.Error;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.stereotype.Service;

@Service
public class ErrorLogger {
	public static void writeLog(ErrorDetails data) {
		File file = new File("C:\\Users\\c5109\\Desktop\\TinyUrl Test\\Error Logger\\Log.txt");
        //File file = new File("D:\\RBL_TinyTest\\Error Logger\\Error_Log.txt");
        FileWriter fr = null;
        try {
            fr = new FileWriter(file, true);
            fr.write("Date -->"+data.getTimestamp()+"   Error Message-->"+data.getMessage()+"   Error Details-->"+data.getDetails());
            fr.write(System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            //close resources
            try {
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

	}

}
