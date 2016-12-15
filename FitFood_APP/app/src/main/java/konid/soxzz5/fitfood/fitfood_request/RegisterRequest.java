package konid.soxzz5.fitfood.fitfood_request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import static konid.soxzz5.fitfood.utils.Constants.REGISTER_REQUEST_URL;


public class RegisterRequest extends StringRequest {


    private Map<String, String> params; //MAP PERMET DE FORMATER POUR L'ENVOIE A LA BDD

    //CONSTRUCTEUR QUI PERMET L'ENVOIE DE LA REQUETE AVEC LES PARAMETRES
    public RegisterRequest(String name, String surname, String pseudo, String mail, String password, Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("name",name);
        params.put("surname",surname);
        params.put("pseudo",pseudo);
        params.put("mail",mail);
        params.put("password",password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
