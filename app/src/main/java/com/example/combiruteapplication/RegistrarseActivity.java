package com.example.combiruteapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

import java.util.regex.Pattern;

public class RegistrarseActivity extends AppCompatActivity {
    EditText emailreg,passwordreg;
    Button btn_register;
    FirebaseAuth firebaseAuth;
    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);
        firebaseAuth=FirebaseAuth.getInstance();
        awesomeValidation=new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this,R.id.emailreg, Patterns.EMAIL_ADDRESS,R.string.invalid_mail);
        awesomeValidation.addValidation(this,R.id.contrareg,".{6,}",R.string.invalid_password);
        emailreg=findViewById(R.id.emailreg);
        passwordreg=findViewById(R.id.contrareg);
        btn_register=findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String mail=emailreg.getText().toString();
                String pass=passwordreg.getText().toString();
                if(awesomeValidation.validate()){
                    firebaseAuth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task){
                            if (task.isSuccessful()){
                                Toast.makeText(RegistrarseActivity.this,"Usuario creado con Exito!",Toast.LENGTH_SHORT).show();
                                finish();
                            }else{
                                String errorCode=((FirebaseAuthException) task.getException()).getErrorCode();
                                dameToastdeerror(errorCode);
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(RegistrarseActivity.this,"Contrase??a mayor o igual a 6 caracteres / Completa todos los campos",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
        private void dameToastdeerror(String error) {

            switch (error) {

                case "ERROR_INVALID_CUSTOM_TOKEN":
                    Toast.makeText(RegistrarseActivity.this, "El formato del token personalizado es incorrecto. Por favor revise la documentaci??n", Toast.LENGTH_LONG).show();
                    break;

                case "ERROR_CUSTOM_TOKEN_MISMATCH":
                    Toast.makeText(RegistrarseActivity.this, "El token personalizado corresponde a una audiencia diferente.", Toast.LENGTH_LONG).show();
                    break;

                case "ERROR_INVALID_CREDENTIAL":
                    Toast.makeText(RegistrarseActivity.this, "La credencial de autenticaci??n proporcionada tiene un formato incorrecto o ha caducado.", Toast.LENGTH_LONG).show();
                    break;

                case "ERROR_INVALID_EMAIL":
                    Toast.makeText(RegistrarseActivity.this, "La direcci??n de correo electr??nico est?? mal formateada.", Toast.LENGTH_LONG).show();
                    emailreg.setError("La direcci??n de correo electr??nico est?? mal formateada.");
                    emailreg.requestFocus();
                    break;

                case "ERROR_WRONG_PASSWORD":
                    Toast.makeText(RegistrarseActivity.this, "La contrase??a no es v??lida o el usuario no tiene contrase??a.", Toast.LENGTH_LONG).show();
                    passwordreg.setError("la contrase??a es incorrecta ");
                    passwordreg.requestFocus();
                    passwordreg.setText("");
                    break;

                case "ERROR_USER_MISMATCH":
                    Toast.makeText(RegistrarseActivity.this, "Las credenciales proporcionadas no corresponden al usuario que inici?? sesi??n anteriormente..", Toast.LENGTH_LONG).show();
                    break;

                case "ERROR_REQUIRES_RECENT_LOGIN":
                    Toast.makeText(RegistrarseActivity.this,"Esta operaci??n es sensible y requiere autenticaci??n reciente. Inicie sesi??n nuevamente antes de volver a intentar esta solicitud.", Toast.LENGTH_LONG).show();
                    break;

                case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                    Toast.makeText(RegistrarseActivity.this, "Ya existe una cuenta con la misma direcci??n de correo electr??nico pero diferentes credenciales de inicio de sesi??n. Inicie sesi??n con un proveedor asociado a esta direcci??n de correo electr??nico.", Toast.LENGTH_LONG).show();
                    break;

                case "ERROR_EMAIL_ALREADY_IN_USE":
                    Toast.makeText(RegistrarseActivity.this, "La direcci??n de correo electr??nico ya est?? siendo utilizada por otra cuenta..   ", Toast.LENGTH_LONG).show();
                    emailreg.setError("La direcci??n de correo electr??nico ya est?? siendo utilizada por otra cuenta.");
                    emailreg.requestFocus();
                    break;

                case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                    Toast.makeText(RegistrarseActivity.this, "Esta credencial ya est?? asociada con una cuenta de usuario diferente.", Toast.LENGTH_LONG).show();
                    break;

                case "ERROR_USER_DISABLED":
                    Toast.makeText(RegistrarseActivity.this, "La cuenta de usuario ha sido inhabilitada por un administrador..", Toast.LENGTH_LONG).show();
                    break;

                case "ERROR_USER_TOKEN_EXPIRED":
                    Toast.makeText(RegistrarseActivity.this, "La credencial del usuario ya no es v??lida. El usuario debe iniciar sesi??n nuevamente.", Toast.LENGTH_LONG).show();
                    break;

                case "ERROR_USER_NOT_FOUND":
                    Toast.makeText(RegistrarseActivity.this, "No hay ning??n registro de usuario que corresponda a este identificador. Es posible que se haya eliminado al usuario.", Toast.LENGTH_LONG).show();
                    break;

                case "ERROR_INVALID_USER_TOKEN":
                    Toast.makeText(RegistrarseActivity.this, "La credencial del usuario ya no es v??lida. El usuario debe iniciar sesi??n nuevamente.", Toast.LENGTH_LONG).show();
                    break;

                case "ERROR_OPERATION_NOT_ALLOWED":
                    Toast.makeText(RegistrarseActivity.this, "Esta operaci??n no est?? permitida. Debes habilitar este servicio en la consola.", Toast.LENGTH_LONG).show();
                    break;

                case "ERROR_WEAK_PASSWORD":
                    Toast.makeText(RegistrarseActivity.this, "La contrase??a proporcionada no es v??lida..", Toast.LENGTH_LONG).show();
                    passwordreg.setError("La contrase??a no es v??lida, debe tener al menos 6 caracteres");
                    passwordreg.requestFocus();
                    break;

            }


        }
}