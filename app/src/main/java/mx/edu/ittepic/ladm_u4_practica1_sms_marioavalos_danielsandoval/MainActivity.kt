package mx.edu.ittepic.ladm_u4_practica1_sms_marioavalos_danielsandoval

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val siPermiso = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnEnviar.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(this,
                    android.Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.SEND_SMS), siPermiso)
            }else {
                enviarSMS(txtDestino, txtSMS)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == siPermiso){
            enviarSMS(txtDestino,txtSMS)
        }
    }

    fun enviarSMS(destino:EditText,msg:EditText){
        try {
            var telefono = destino.text.toString()
            var mensaje = msg.text.toString()
            var msj = SmsManager.getDefault()
            if (telefono.length==10 && mensaje!="") {
                msj.sendTextMessage(
                    "$telefono", null, "$mensaje",
                    null, null
                )
                Toast.makeText(this, "SE ENVIO EL MENSAJE", Toast.LENGTH_SHORT).show()
            }else{
                AlertDialog.Builder(this)
                    .setTitle("ERROR")
                    .setMessage("-> 10 digitos para el telefono.\n      Ej: 3112141938" +
                            "\n-> El mensaje no debe estar vacio")
                    .setPositiveButton("OK"){d,i->}
                    .show()
            }
        }catch (e:Exception){
            Log.d("~Error","${e.message}")
        }
    }



}