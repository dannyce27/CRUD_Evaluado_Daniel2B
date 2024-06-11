package Daniel.Soriano.crud_evaluado_daniels

import Modelo.claseConexion
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class activity_Registrarse : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registrarse)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val txtUsuario =findViewById<EditText>(R.id.txtRegistrarUser)
        val txtCorreo = findViewById<EditText>(R.id.txtRegistrarCorreo)
        val contrasena = findViewById<EditText>(R.id.txtRegistrarContraseña)
        val btnCrearCuenta = findViewById<Button>(R.id.btnRegistrarUser)
        val imgVolver = findViewById<ImageButton>(R.id.imgVolver)


        btnCrearCuenta.setOnClickListener{
            GlobalScope.launch(Dispatchers.IO) {

                val objConexion = claseConexion().cadenaConexion()
                val crearUsuario = objConexion?.prepareStatement("INSERT INTO Usuario(UUID, nombre_Usuario,contrasena_Usuario, correo_Usuario) VALUES (?, ?, ?, ?)")!!
                crearUsuario.setString(1, UUID.randomUUID().toString())
                crearUsuario.setString(2, txtUsuario.text.toString())
                crearUsuario.setString(3, contrasena.text.toString())
                crearUsuario.setString(4, txtCorreo.text.toString())
                crearUsuario.executeUpdate()
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@activity_Registrarse, "Usuario creado", Toast.LENGTH_SHORT).show()
                    txtUsuario.setText("")
                    txtCorreo.setText("")
                    contrasena.setText("")
                }
            }
        }

        imgVolver.setOnClickListener{
            val pantallaLogin = Intent(this, activity_Login::class.java)
            startActivity(pantallaLogin)
        }
    }


}