package com.sertek.cron;
import org.apache.commons.httpclient.HttpStatus;
import org.quartz.impl.*;
import org.quartz.*;

import java.util.*;
import java.io.*;
/**
 * 去執行本機端指令類
 * @author Administrator
 *
 */
public class RunCommand implements StatefulJob {

	
	
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO 自動產生方法 Stub
		String command = (String)arg0.getJobDetail().getJobDataMap().get("command");
		try{
			Process process = Runtime.getRuntime().exec(command);
			try{
				process.waitFor();
			} catch (InterruptedException e2) {
				e2.printStackTrace();
				int i = process.exitValue();
				if (i != 0)
					throw new JobExecutionException(e2.getMessage());
				
			}
		}catch(IOException e){
			e.printStackTrace();
			throw new JobExecutionException(e.getMessage());
		}			
			//System.out.println("Process Successful.");
			
			
		
	}

}
