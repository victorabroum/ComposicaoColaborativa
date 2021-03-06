package victor_vasconcelos.icomp.ufam.edu.br.composicaocolaborativa.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import victor_vasconcelos.icomp.ufam.edu.br.composicaocolaborativa.R;
import victor_vasconcelos.icomp.ufam.edu.br.composicaocolaborativa.Usuarios;
import victor_vasconcelos.icomp.ufam.edu.br.composicaocolaborativa.cdp.CustomJsonObjectRequest;
import victor_vasconcelos.icomp.ufam.edu.br.composicaocolaborativa.helper.PermissionUtils;

public class MainActivity extends AppCompatActivity {

    String[] permissoes = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };


    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;

    Intent intent;
    String url;
    RequestQueue rq;
    private ProgressDialog pDialog;
    Usuarios usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);

        rq = Volley.newRequestQueue(MainActivity.this);

        url = getResources().getString(R.string.ip) +"/composicaomusical/app.php/getThisUsuario";
        //url = getResources().getString(R.string.ip_celular) +"/composicaomusical/teste.php/getThisUsuario";

        PermissionUtils.validate(this, 0, permissoes);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        checkPlayServices();

    }

    public void entrarClicado(View view){

        EditText edtLogin = (EditText) findViewById(R.id.edtEmailMain);
        EditText edtSenha = (EditText) findViewById(R.id.edtSenhaMain);

        Map<String, String> params = new HashMap<>();

        params.put("senha",edtSenha.getText().toString());
        params.put("email",edtLogin.getText().toString());

        showpDialog();

        CustomJsonObjectRequest request = new CustomJsonObjectRequest(Request.Method.POST,
                url,
                params,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            usuario = new Usuarios(response.getInt("id"),response.getString("nome"),
                                    response.getString("email"), response.getString("senha"), response.getString("audio"));
                            if (usuario.getNome() != null){
                                intent = new Intent(MainActivity.this, SonsActivity.class);
                                //intent = new Intent(MainActivity.this, CadastroSomActivity.class);
                                intent.putExtra("usuario", usuario);
                                startActivity(intent);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        hidepDialog();
                        Log.i("Script", "SUCCESS: "+response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        usuario = null;
                        Toast.makeText(MainActivity.this,"Usuario e/ou Senha inválidos",
                                Toast.LENGTH_SHORT).show();
                        Log.e("Error", "VolleyError " + error.getMessage());
                        hidepDialog();
                    }
                });

        request.setTag("tag");
        rq.add(request);

    }

    public void btCad(View view){
        intent = new Intent(this, CadastroActivity.class);
        startActivity(intent);
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.configurarLocation) {
            intent = new Intent(MainActivity.this, SetLocationActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    /**
     * Method to verify google play services on the device
     * */
    private boolean checkPlayServices() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(resultCode)) {
                googleApiAvailability.getErrorDialog(this,resultCode,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            return false;
        }
        return true;
    }

}
