package Daniel.Soriano.crud_evaluado_daniels

import Modelo.claseConexion
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class activity_Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val txtCorreo = findViewById<EditText>(R.id.txtCorreoElec)
        val txtContrasena = findViewById<EditText>(R.id.txtContrasena)
        val btnIngresar = findViewById<Button>(R.id.btnIngresar)
        val btnRegistrarse = findViewById<Button>(R.id.btnRegistrarse)

        btnIngresar.setOnClickListener{
            val pantallaMain = Intent(this, MainActivity::class.java)

            GlobalScope.launch(Dispatchers.IO) {
                val objConexion = claseConexion().cadenaConexion()
                val comprobarUsuario =objConexion?.prepareStatement("SELECT * FROM tbUsuario WHERE contrasena_Usuario = ? AND correo_Usuario = ?")!!
                comprobarUsuario.setString(1, txtContrasena.text.toString())
                comprobarUsuario.setString(2, txtCorreo.text.toString())
                val resultado = comprobarUsuario.executeQuery()
                if(resultado.next()){
                    startActivity(pantallaMain)
                }
                else  {
                    println("Usuario no encontrado, verifica tus credenciales")
                }
            }
        }

        btnRegistrarse.setOnClickListener{
            val pantallaRegistrarse =Intent(this, activity_Registrarse::class.java)
            startActivity(pantallaRegistrarse)
        }
    }
}