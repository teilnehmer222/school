package de.bbq.java.tasks.school;

import java.util.TimerTask;

public class JdbcTimerTask extends TimerTask {
	DaoSchoolJdbcMysql myDoa;
	boolean connected = false;
	
	public JdbcTimerTask(DaoSchoolJdbcMysql doa){
		myDoa = doa;
	}
	public boolean isConnected() {
		return connected;
	}
	public void run() {
    	if (myDoa != null) {
    		connected = myDoa.isConnected(); 
    	}
    }
}

