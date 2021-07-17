package com.example.combiruteapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class AuthActivity extends AppCompatActivity {
    EditText emailEditText,passwordEditText;
    Button SignUpButton,btn_registrar;
    TextView forgetPassword;
    AwesomeValidation awesomeValidation;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        btn_registrar = findViewById(R.id.btn_registrar);
        SignUpButton= findViewById(R.id.SignUpButton);
        emailEditText=findViewById(R.id.emailEditText);
        passwordEditText=findViewById(R.id.passwordEditText);
        firebaseAuth=FirebaseAuth.getInstance();
        awesomeValidation=new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this,R.id.emailEditText, Patterns.EMAIL_ADDRESS,R.string.invalid_mail);
        awesomeValidation.addValidation(this,R.id.passwordEditText,".{6,}",R.string.invalid_password);
        forgetPassword=findViewById(R.id.forgetPassword);
        btn_registrar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent i=new Intent(AuthActivity.this,RegistrarseActivity.class);
                startActivity(i);
            }
        });

        SignUpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (awesomeValidation.validate()){
                    String mail=emailEditText.getText().toString();
                    String pass=passwordEditText.getText().toString();
                    firebaseAuth.signInWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                gohome();
                            }else{
                                String errorCode=((FirebaseAuthException) task.getException()).getErrorCode();
                                dameToastdeerror(errorCode);
                            }
                        }
                    });
                }
            }
        });
    }
    private void gohome(){
        Intent i=new Intent(this,MapActivity.class);
        i.putExtra("mail",emailEditText.getText().toString());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
    private void dameToastdeerror(String error) {

        switch (error) {

            case "ERROR_INVALID_CUSTOM_TOKEN":
                Toast.makeText(AuthActivity.this, "El formato del token personalizado es incorrecto. Por favor revise la documentación", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_CUSTOM_TOKEN_MISMATCH":
                Toast.makeText(AuthActivity.this, "El token personalizado corresponde a una audiencia diferente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_CREDENTIAL":
                Toast.makeText(AuthActivity.this, "La credencial de autenticación proporcionada tiene un formato incorrecto o ha caducado.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_EMAIL":
                Toast.makeText(AuthActivity.this, "La dirección de correo electrónico está mal formateada.", Toast.LENGTH_LONG).show();
                emailEditText.setError("La dirección de correo electrónico está mal formateada.");
                emailEditText.requestFocus();
                break;

            case "ERROR_WRONG_PASSWORD":
                Toast.makeText(AuthActivity.this, "La contraseña no es válida o el usuario no tiene contraseña.", Toast.LENGTH_LONG).show();
                passwordEditText.setError("la contraseña es incorrecta ");
                passwordEditText.requestFocus();
                passwordEditText.setText("");
                break;

            case "ERROR_USER_MISMATCH":
                Toast.makeText(AuthActivity.this, "Las credenciales proporcionadas no corresponden al usuario que inició sesión anteriormente..", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_REQUIRES_RECENT_LOGIN":
                Toast.makeText(AuthActivity.this,"Esta operación es sensible y requiere autenticación reciente. Inicie sesión nuevamente antes de volver a intentar esta solicitud.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                Toast.makeText(AuthActivity.this, "Ya existe una cuenta con la misma dirección de correo electrónico pero diferentes credenciales de inicio de sesión. Inicie sesión con un proveedor asociado a esta dirección de correo electrónico.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_EMAIL_ALREADY_IN_USE":
                Toast.makeText(AuthActivity.this, "La dirección de correo electrónico ya está siendo utilizada por otra cuenta..   ", Toast.LENGTH_LONG).show();
                emailEditText.setError("La dirección de correo electrónico ya está siendo utilizada por otra cuenta.");
                emailEditText.requestFocus();
                break;

            case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                Toast.makeText(AuthActivity.this, "Esta credencial ya está asociada con una cuenta de usuario diferente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_DISABLED":
                Toast.makeText(AuthActivity.this, "La cuenta de usuario ha sido inhabilitada por un administrador..", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_TOKEN_EXPIRED":
                Toast.makeText(AuthActivity.this, "La credencial del usuario ya no es válida. El usuario debe iniciar sesión nuevamente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_NOT_FOUND":
                Toast.makeText(AuthActivity.this, "No hay ningún registro de usuario que corresponda a este identificador. Es posible que se haya eliminado al usuario.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_USER_TOKEN":
                Toast.makeText(AuthActivity.this, "La credencial del usuario ya no es válida. El usuario debe iniciar sesión nuevamente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_OPERATION_NOT_ALLOWED":
                Toast.makeText(AuthActivity.this, "Esta operación no está permitida. Debes habilitar este servicio en la consola.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_WEAK_PASSWORD":
                Toast.makeText(AuthActivity.this, "La contraseña proporcionada no es válida..", Toast.LENGTH_LONG).show();
                passwordEditText.setError("La contraseña no es válida, debe tener al menos 6 caracteres");
                passwordEditText.requestFocus();
                break;

        }


    }
    
}