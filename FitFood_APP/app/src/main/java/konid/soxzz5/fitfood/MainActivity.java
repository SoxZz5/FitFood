package konid.soxzz5.fitfood;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.speech.RecognizerIntent;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.context.IconicsContextWrapper;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import java.util.ArrayList;

import konid.soxzz5.fitfood.fitfood_fragment.AddRecipeFragment;
import konid.soxzz5.fitfood.fitfood_fragment.HomeFragment;
import konid.soxzz5.fitfood.fitfood_fragment.LastRecipeFragment;
import konid.soxzz5.fitfood.fitfood_fragment.RecipeOfDayFragment;
import konid.soxzz5.fitfood.fitfood_fragment.SearchFragment;
import konid.soxzz5.fitfood.fitfood_fragment.SingleRecipeDisplay;
import konid.soxzz5.fitfood.fitfood_session.SessionManager;


public class MainActivity extends AppCompatActivity{
    int drawableTag;
    private static final int RC_CAMERA = 123;
    private static final int RC_STORAGE = 124;
    private static final int RC_SETTINGS_SCREEN = 125;

    MaterialSearchView searchView;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    FirebaseUser user;
    SessionManager sessionManager;
    String first_sign;
    String recipe_of_day;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        sessionManager = new SessionManager();
        final Fragment HomeFragment = (Fragment) new HomeFragment();
        if(user == null)
        {
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
        else
        {
            first_sign = sessionManager.getPreferences(MainActivity.this,"first_sign");
            if(first_sign.equals("1"))
            {
                Intent intent = new Intent(MainActivity.this,AfterLogin.class);
                startActivity(intent);
                finish();
            }
            else
            {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right, 0, 0);
                transaction.replace(R.id.fragment_container, HomeFragment);
                transaction.commit();
            }
        }

        setContentView(R.layout.activity_main);
        //ON EST DEJA CONNECTER
        //drawableTag PERMET DE SAVOIR QUELLE FRAGMENT LANCER ET LA GESTION DE LA TOOLBAR
        drawableTag=1;

        //DECLARATION DE LA TOOLBAR ET SETSUPPORT EN ACTIONBAR PERMET DE GERER LA TOOLBAR COMME UNE ACTIONBAR
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //DEFINITION ITEM MENU
        PrimaryDrawerItem home_cook = new PrimaryDrawerItem().withName(R.string.drawer_item_home_cook).withIcon(GoogleMaterial.Icon.gmd_home).withIdentifier(1);
        SectionDrawerItem cook = new SectionDrawerItem().withName(R.string.drawer_item_cook).withTextColor(Color.GREEN);
        PrimaryDrawerItem day_cook = new PrimaryDrawerItem().withName(R.string.drawer_item_day_cook).withIcon(GoogleMaterial.Icon.gmd_cake).withIdentifier(2);
        PrimaryDrawerItem top_cook = new PrimaryDrawerItem().withName(R.string.drawer_item_top_cook).withIcon(GoogleMaterial.Icon.gmd_local_play).withIdentifier(3).withSelectable(false);
        PrimaryDrawerItem last_cook = new PrimaryDrawerItem().withName(R.string.drawer_item_last_cook).withIcon(GoogleMaterial.Icon.gmd_style).withIdentifier(4);
        PrimaryDrawerItem seek_cook = new PrimaryDrawerItem().withName(R.string.drawer_item_seek_cook).withIcon(GoogleMaterial.Icon.gmd_loupe).withIdentifier(5).withSelectable(false);
        PrimaryDrawerItem add_cook = new PrimaryDrawerItem().withName(R.string.drawer_item_add_cook).withIcon(GoogleMaterial.Icon.gmd_note_add).withIdentifier(6);
        SectionDrawerItem book = new SectionDrawerItem().withName(R.string.drawer_item_book).withTextColor(Color.GREEN);
        PrimaryDrawerItem mycook_book = new PrimaryDrawerItem().withName(R.string.drawer_item_mycook_book).withIcon(GoogleMaterial.Icon.gmd_restaurant).withIdentifier(7).withSelectable(false);
        PrimaryDrawerItem list_book = new PrimaryDrawerItem().withName(R.string.drawer_item_list_book).withIcon(GoogleMaterial.Icon.gmd_event_note).withIdentifier(8).withSelectable(false);
        PrimaryDrawerItem historic_book = new PrimaryDrawerItem().withName(R.string.drawer_item_historic_book).withIcon(GoogleMaterial.Icon.gmd_done).withIdentifier(9).withSelectable(false);
        PrimaryDrawerItem param_book = new PrimaryDrawerItem().withName(R.string.drawer_item_param_book).withIcon(GoogleMaterial.Icon.gmd_settings).withIdentifier(10).withSelectable(false);
        PrimaryDrawerItem account = new PrimaryDrawerItem().withName(R.string.drawer_item_account).withIcon(GoogleMaterial.Icon.gmd_account_box).withIdentifier(11).withSelectable(false);
        PrimaryDrawerItem disconnect = new PrimaryDrawerItem().withName(R.string.drawer_item_disconnect).withIcon(GoogleMaterial.Icon.gmd_highlight_off).withIdentifier(12);

        //CONSTRUCTION DU MENU
        Drawer menu = new DrawerBuilder()
                .withActivity(this)
                .withRootView(R.id.drawer_layout)
                .withSavedInstance(savedInstanceState)
                .withDisplayBelowStatusBar(false)
                .withDrawerWidthDp(250)
                .withToolbar(toolbar)
                .withSelectedItem(drawableTag)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .addDrawerItems(
                        home_cook,
                        cook,
                        day_cook,
                        top_cook,
                        last_cook,
                        seek_cook,
                        add_cook,
                        book,
                        mycook_book,
                        list_book,
                        historic_book,
                        param_book
                )
                .addStickyDrawerItems(
                        account,
                        disconnect
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem)
                    {
                        if (drawerItem != null)
                        {
                            if(drawerItem.getIdentifier() == 1)
                            {
                                if(drawableTag!=1)
                                {
                                    refreshMenu(MainActivity.this);
                                    drawableTag=1;
                                    HomeFragment newFragment = new HomeFragment();
                                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                    transaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right, 0, 0);
                                    transaction.replace(R.id.fragment_container, newFragment);
                                    transaction.commit();
                                    toolbar.setTitle(R.string.drawer_item_home_cook);
                                }
                            }
                            if(drawerItem.getIdentifier() == 2)
                            {
                                if(drawableTag != 2)
                                {
                                    refreshMenu(MainActivity.this);
                                    drawableTag=2;
                                    Bundle args = new Bundle();
                                    args.putString("recipe_key", recipe_of_day);
                                    RecipeOfDayFragment newFragment = new RecipeOfDayFragment();
                                    newFragment.setArguments(args);
                                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                    transaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right, 0, 0);
                                    transaction.replace(R.id.fragment_container, newFragment);
                                    transaction.commit();
                                    toolbar.setTitle(R.string.drawer_item_day_cook);
                                }
                            }
                            if(drawerItem.getIdentifier() == 4)
                            {
                                if(drawableTag!=4) {
                                    refreshMenu(MainActivity.this);
                                    drawableTag = 4;
                                    LastRecipeFragment newFragment = new LastRecipeFragment();
                                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                    transaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right, 0, 0);
                                    transaction.replace(R.id.fragment_container, newFragment);
                                    transaction.commit();
                                    toolbar.setTitle(R.string.drawer_item_last_cook);
                                }
                            }
                            if(drawerItem.getIdentifier() == 5)
                            {
                                if(drawableTag!=5)
                                {
                                    drawableTag=5;
                                }
                            }

                            if(drawerItem.getIdentifier() == 6)
                            {
                                if(drawableTag!=6)
                                {
                                    drawableTag=6;
                                    AddRecipeFragment newFragment = new AddRecipeFragment();
                                    Bundle args = new Bundle();
                                    args.putInt("position", position);
                                    newFragment.setArguments(args);

                                    FragmentTransaction transaction = getFragmentManager().beginTransaction();

                                    transaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right, 0, 0);
                                    transaction.replace(R.id.fragment_container, newFragment);

                                    transaction.commit();
                                    toolbar.setTitle(R.string.drawer_item_add_cook);
                                    toolbar.getMenu().clear();
                                    toolbar.inflateMenu(R.menu.bypass_menuitem);
                                }
                            }

                            if(drawerItem.getIdentifier() == 12)
                            {
                                firebaseAuth.signOut();
                                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                        return false;
                    }
                }).build();



        //ON CREER NOTRE SEARCHVIEW
        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        //ON ACTIVE VOICE SEARCH
        searchView.setVoiceSearch(true);

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                drawableTag = -1;
                SearchFragment newFragment = new SearchFragment();
                Bundle bundle = new Bundle();
                bundle.putString("seek", query);
                newFragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right, 0, 0);
                transaction.replace(R.id.fragment_container, newFragment);
                transaction.commit();
                toolbar.setTitle(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });

        DatabaseReference newdatabaseReference = FirebaseDatabase.getInstance().getReference();
        newdatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    if(snapshot.getKey().equals("recipe_of_day")) {
                        recipe_of_day = snapshot.getValue().toString();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(IconicsContextWrapper.wrap(newBase));
    }

    //PERMET LA GESTION DE LA TOOLBAR ON AJOUTE UN MENU CONTENANT DES ITEMS
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }

    //PERMET DE FERMER LA SEARCHVIEW AVEC BACK
    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
        if(drawableTag != -1)
        {
            finish();
        }
        else
        {
            //NOTHING
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        //GESTION DE LA RECHERCHE VIA LA VOIX
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    searchView.setQuery(searchWrd, false);
                }
            }

            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void openRecipe(String recipeID) {
        SingleRecipeDisplay newFragment = new SingleRecipeDisplay();
        Bundle args = new Bundle();
        newFragment.setRecipeID(recipeID);
        newFragment.setArguments(args);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right, 0, 0);
        transaction.replace(R.id.fragment_container, newFragment);
        transaction.commit();
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.drawer_item_display);
        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.bypass_menuitem);
        drawableTag=50;
    }

    public static void refreshMenu(Activity activity)
    {
        activity.invalidateOptionsMenu();
    }
}
