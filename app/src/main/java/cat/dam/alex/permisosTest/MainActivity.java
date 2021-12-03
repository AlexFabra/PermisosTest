package cat.dam.alex.permisosTest;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.Manifest;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    final int MY_PERMISSIONS_REQUEST=1;
    public static final int REQUEST_CAMERA_PERMISSION= 100;
    private Button btn_camera,btn_enmagatzematge_lectura, btn_enmagatzematge_escriptura,btn_cam2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_camera = (Button) findViewById(R.id.btn_camera);
        btn_enmagatzematge_lectura = (Button) findViewById(R.id.btn_enmagatzematge_lectura);
        btn_enmagatzematge_escriptura = (Button) findViewById(R.id.btn_enmagatzematge_escriptura);
        btn_cam2 = (Button) findViewById(R.id.btn_cam2);

        btn_camera.setOnClickListener(view -> {
            // El procés d'execució entra en aquesta condició si no tenim permís.
            if (ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) { //també podriem fer la condició a la inversa amb PackageManager.PERMISSION_DENIED.
                // Si no tenim el permís el demanem. Resposta onRequestPermissionsResult
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST);
            } else { //si el permís ja s'ha concedit
                Toast.makeText(MainActivity.this, "El permís de la càmera ja està concedit", Toast.LENGTH_SHORT).show();
            }
        });
        btn_enmagatzematge_lectura.setOnClickListener(view -> {
            if (ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST);
            } else {
                Toast.makeText(MainActivity.this, "El permís de lectura d'emmagatzematge ja està concedit", Toast.LENGTH_SHORT).show();
            }
        });
        btn_enmagatzematge_escriptura.setOnClickListener(view -> {
            if (ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST);
            } else {
                Toast.makeText(MainActivity.this, "El permís d'escriptura d'emmagatzematge ja està concedit", Toast.LENGTH_SHORT).show();
            }
        });

        btn_cam2.setOnClickListener(view -> {
                askCameraPermission();
        });
    }

    /**
     * Aquesta funció rep la informació dels permisos i crea un toast o altre segons estàn denegats o acceptats.
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // És el mètode callback que retorna el resultat de la sol·licitut de permís.
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST: {
                // Si sol·licitud de permís ha estat denegada, l'array resultant és buit, pel que entra a aquesta condició si és major a 0:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "Gràcies per la seva col·laboració", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Com que no?", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            // hi pot haver altres línies 'case' per comprovar altres permisos
        }
    }

    /** altra forma de demanar permissos a Android:
     */
    private void askCameraPermission()   {
        if (android.os.Build.VERSION.SDK_INT >= 23) { //si la versió del api es major o igual a 23:
            int cameraPermission = this.checkSelfPermission(Manifest.permission.CAMERA); //comprovem si tenim el permís i guardem la resposta a cameraPermission.
            if (cameraPermission != PackageManager.PERMISSION_GRANTED  ) { //si camera permission no és equivalent a PackageManager.PERMISSION_GRANTED, no tenim permís.
                this.requestPermissions(new String[]{Manifest.permission.CAMERA }, REQUEST_CAMERA_PERMISSION); //per tant, demanem permís.
            } else {
                // this.requestPermissions(new String[]{Manifest.permission.CAMERA }, REQUEST_CAMERA_PERMISSION); això no fa res, ja que no es pot demanar permís si ja ho tens.
                Toast.makeText(MainActivity.this, "El permís de la càmera ja està concedit", Toast.LENGTH_SHORT).show();
            }
        }
    }
}