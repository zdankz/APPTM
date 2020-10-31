package com.example.apptm.View

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.apptm.MainActivity
import com.example.apptm.R
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class login : AppCompatActivity() {
    companion object{
        var sdt_get :String? =""
        var pass_get : String? = ""
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        var intent : Intent = Intent(this,Home::class.java)
        tvSignUp.setOnClickListener {
            var intensignin : Intent = Intent(this,register::class.java)
            startActivity(intensignin)
            //finishAffinity()
        }
        btnLogin.setOnClickListener {
            val sdt :String = tenuser.text.toString()
            val password : String = passworduser.text.toString()


            if(sdt.isNullOrBlank()== true || password.isNullOrBlank() == true){
                Toast.makeText(applicationContext,"Điền đủ thông tin!!!",Toast.LENGTH_LONG).show()

            }else
            {
                var url: String = "https://apptm.000webhostapp.com/login.php?sdt="
                //var url2:String ="http://192.168.1.106:8000/APPTM/login.php?sdt=3245451&password=321"
                url = url.plus(sdt)
                url = url.plus("&")
                url = url.plus("password=")
                url = url.plus(password)
                //Toast.makeText(applicationContext,url,Toast.LENGTH_LONG).show()
                Getdata().execute(url)
                var laysdt : String = sdt_get.toString()
                var laypass : String = pass_get.toString()

                if(laysdt.equals("khongco")== true && laypass.equals("khongco") == true){
                    Toast.makeText(applicationContext,laysdt ,Toast.LENGTH_LONG).show()
                    Toast.makeText(applicationContext,laypass ,Toast.LENGTH_LONG).show()

                }else if(laysdt.equals(sdt)== true && laypass.equals(password) == true){
                    Toast.makeText(applicationContext,"Đăng nhập thành công",Toast.LENGTH_LONG).show()
                    startActivity(intent)

                }


            }
        }

    }
    inner class Getdata : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg p0: String?): String {
            return getContentURL(p0[0])
        }
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            var SDT_API: String?=""
            var PASS_API: String? =""
            //var url_s: String = ""
            var jsonArray: JSONArray = JSONArray(result)
            for (i in 0..jsonArray.length() -1 ) {
                var objectVD: JSONObject = jsonArray.getJSONObject(i)
                SDT_API= objectVD.getString("sdt")
                PASS_API = objectVD.getString("password")
                sdt_get = SDT_API
                pass_get = PASS_API
                Log.d("sdt", sdt_get.toString())
                Log.d("pass", pass_get.toString())
                //Log.d(PASS_API,"ok")
                //url_s = objectVD.getString("songkey")
            }


        }
    }
    private fun getContentURL(url: String?) : String{
        var content: StringBuilder = StringBuilder();
        val url: URL = URL(url)
        val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
        val inputStreamReader = InputStreamReader(urlConnection.inputStream)
        val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)

        var line: String = ""
        try {
            do {
                line = bufferedReader.readLine()
                if(line != null){
                    content.append(line)
                }
            }while (line != null)
            bufferedReader.close()
        }catch (e: Exception){
            Log.d("AAA", e.toString())
        }
        return content.toString()
    }
}
