package konid.soxzz5.fitfood.fitfood_request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import static konid.soxzz5.fitfood.utils.Constants.LOGIN_REQUEST_URL;

/**
 * Created by Soxzer on 15/12/2016.
 */

public class AddRecipeRequest extends StringRequest {


    private Map<String, String> params; //MAP PERMET DE FORMATER POUR L'ENVOIE A LA BDD

    //CONSTRUCTEUR QUI PERMET L'ENVOIE DE LA REQUETE AVEC LES PARAMETRES
    public AddRecipeRequest(String title, String recipe, Response.Listener<String> listener){
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}