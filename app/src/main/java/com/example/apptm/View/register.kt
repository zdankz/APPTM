package com.example.apptm.View

import android.content.DialogInterface
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.apptm.R
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class register : AppCompatActivity() {
    companion object{
        var phone_get :String? = ""
        var password_get :String? = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        comebackLOGIN.setOnClickListener {
            val inten :Intent = Intent(this,login::class.java)
            startActivity(inten)
            finish()
        }
        btnRegister.setOnClickListener {
            var name_dk :String = nameUser_dk.text.toString()
            var phone_dk :String = phone_Userdk.text.toString()
            var password_dk :String = password_Userdk.text.toString()
            if(name_dk.isNullOrBlank() == true || phone_dk.isNullOrBlank()== true || password_dk.isNullOrBlank()== true)
            {
                Toast.makeText(applicationContext,"Điền đủ thông tin!!!", Toast.LENGTH_LONG).show()
            }else
            {
                var url: String = "https://apptm.000webhostapp.com/index.php?sdt="
                url = url.plus(phone_dk)
                url = url.plus("&")
                url = url.plus("password=")
                url = url.plus(password_dk)
                url = url.plus("&")
                url = url.plus("name=")
                url = url.plus(name_dk)
                Getdata().execute(url)
                var laysodt :String = phone_get.toString()
                var laypass :String = password_get.toString()
                if(laysodt.equals("khongco")== true || laypass.equals("khongco")==true){

                }
                else if(laysodt.equals(phone_dk)==true && laypass.equals(password_dk)== true){
                    Toast.makeText(applicationContext,"Thanh Cong" ,Toast.LENGTH_LONG).show()
                    val inten :Intent = Intent(this,login::class.java)
                    startActivity(inten)
                    //dialogconnectlogin()
                    finish()

                }
            }
        }
    }
    fun dialogconnectlogin()
    {
        val inten :Intent = Intent(this,login::class.java)
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Thông báo")
        dialog.setMessage("Đăng kí thành công")
        dialog.setPositiveButton("EXIT", { dialogInterface: DialogInterface, i: Int ->
            finish()
        })
        dialog.setNegativeButton("LOGIN", { dialogInterface: DialogInterface, i: Int ->
            startActivity(inten)
        })
        dialog.show()
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
                phone_get= objectVD.getString("sdt")
                password_get = objectVD.getString("password")
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
