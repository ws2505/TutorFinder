package Utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by wenbo on 2017/7/2.
 */

public class LoginUtils extends Activity{

    public void login(String phone){

        //Remember the login status
        SharedPreferences sp =getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("phone", phone);
//        editor.putString("status", "1");
        editor.commit();
    }

    public void logout(String phone){

        //Remember the login status
        SharedPreferences sp =getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("phone", phone);
        editor.putString("status", "0");
        editor.commit();
    }

    public static boolean isLogin(String status){
        if(status == null || status.equals("0"))
            return false;
        else
            return true;
    }

}
