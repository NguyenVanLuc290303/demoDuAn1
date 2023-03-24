package ph29152.fptpoly.duanoderfoodnhom1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import ph29152.fptpoly.duanoderfoodnhom1.FragmentAdmin.MessageFragment;
import ph29152.fptpoly.duanoderfoodnhom1.FragmentAdmin.NewOrder;
import ph29152.fptpoly.duanoderfoodnhom1.FragmentAdmin.ProductFragment;
import ph29152.fptpoly.duanoderfoodnhom1.FragmentAdmin.RevenueStatisticFragment;

public class AdminActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private static final int Fragment_product=0;
    DrawerLayout drawerLayout;
    private int mCurrentFragment = Fragment_product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Toolbar toolbar = findViewById(R.id.toolbar);
        //add tool bar
        setSupportActionBar(toolbar);
        //set toolbar và toolbar bây giống như actionbar
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);                                 //đóng mở drawlayout
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        replaceFragment(new ProductFragment());
        //set check cho menu
        navigationView.getMenu().findItem(R.id.sanPham).setChecked(true);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.sanPham){
            // frag hien tai la frag addnew roi neu khac frag thi moi xu li logic

            replaceFragment(new ProductFragment());
            mCurrentFragment = Fragment_product;

        }else if(id==R.id.tinNhan){

            replaceFragment(new MessageFragment());
            mCurrentFragment=Fragment_product;
        }else if(id==R.id.xacNhanDH){

            replaceFragment(new NewOrder());
            mCurrentFragment=Fragment_product;
        }
        else if(id==R.id.donHangThanhCong){

            replaceFragment(new RevenueStatisticFragment());
            mCurrentFragment=Fragment_product;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
//return true
        return true;
    }
    @Override
    public void onBackPressed() {
        //check neu nhu drawable dang mo ma an nut back cua ung dung thi tat drawable
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            //nguoc lai neu draw da dong roi thi thoat app
            super.onBackPressed();
        }
    }



    private void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame,fragment);
        transaction.commit();
    }

}