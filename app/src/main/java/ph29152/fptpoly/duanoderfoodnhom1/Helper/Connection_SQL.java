package ph29152.fptpoly.duanoderfoodnhom1.Helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connection_SQL {
    Connection connection= null;
    Context context;
    @SuppressLint("NewApi")
    public Connection SQLconnection(){

        String ip = "192.168.1.3", port = "1433",data = "demoDuAn1",user = "test",password = "034203009607";
        StrictMode.ThreadPolicy tp = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(tp);
        String URLcon = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            URLcon="jdbc:jtds:sqlserver://"+ip+ ":"+port+";"+"databasename="+data+";user=" +user+";password="+password+";";
            connection = DriverManager.getConnection(URLcon);
            Toast.makeText(context, "Ket noi sql thanh cong", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Log.d("error",e.getMessage());
        }

        return  connection;}
}

