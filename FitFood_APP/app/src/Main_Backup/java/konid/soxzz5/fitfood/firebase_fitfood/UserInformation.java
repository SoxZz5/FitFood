package konid.soxzz5.fitfood.firebase_fitfood;

/**
 * Created by Soxzer on 15/12/2016.
 */

public class UserInformation {
    public String name;
    public String surname;
    public String pseudo;
    public String email;
    public int diet;

    public UserInformation(){

    }

    public UserInformation(String name, String surname, String pseudo, int diet, String email) {
        this.name = name;
        this.surname = surname;
        this.pseudo = pseudo;
        this.diet = diet;
        this.email = email;
    }
}
