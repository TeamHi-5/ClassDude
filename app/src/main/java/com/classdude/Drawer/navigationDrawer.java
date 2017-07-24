package com.classdude.Drawer;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.classdude.R;
import com.mikepenz.crossfadedrawerlayout.view.CrossfadeDrawerLayout;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.MiniDrawer;
import com.mikepenz.materialdrawer.interfaces.ICrossfader;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.mikepenz.materialize.util.UIUtils;

/**
 * Created by nesarjony on 24-Jul-17.
 */

public class navigationDrawer
{
    private Toolbar toolbar           = null;
    private Bundle savedInstanceState = null;
    private Activity currentActivity  = null;

    private CrossfadeDrawerLayout crossfadeDrawerLayout = null;

    public navigationDrawer(Activity activity, Bundle instance, Toolbar toolbar)
    {
        navigationDrawer.this.currentActivity = activity;
        navigationDrawer.this.savedInstanceState = instance;
        navigationDrawer.this.toolbar = toolbar;
    }

    public AccountHeader buildHeader()
    {
        final IProfile profile = new ProfileDrawerItem()
                .withName("Nesar Ahammed Jony")
                .withEmail("jonybd64@gmail.com");
        //.withIcon("https://upload.wikimedia.org/wikipedia/en/7/70/Shawn_Tok_Profile.jpg");

        final IProfile profile2 = new ProfileDrawerItem()
                .withName("Bernat Borras")
                .withEmail("alorma@github.com");
        //.withIcon(Uri.parse("https://avatars3.githubusercontent.com/u/887462?v=3&s=460"));

        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(currentActivity)
                .withTranslucentStatusBar(true)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(
                        profile, profile2
                )
                .withSavedInstance(savedInstanceState)
                .build();

        return headerResult;
    }

    public Drawer buildDrawer(AccountHeader headerResult)
    {

        Drawer result = new DrawerBuilder()
                .withActivity(currentActivity)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withDrawerLayout(R.layout.navigation_drawer)
                .withHasStableIds(true)
                .withDrawerWidthDp(72)
                .withGenerateMiniDrawer(true)
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_first).withIcon(MaterialDesignIconic.Icon.gmi_3d_rotation).withIdentifier(1),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_second).withIcon(FontAwesome.Icon.faw_home).withIdentifier(2),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_third).withIcon(FontAwesome.Icon.faw_gamepad).withIdentifier(3),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_fourth).withIcon(FontAwesome.Icon.faw_eye).withIdentifier(4),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_fifth).withIcon(MaterialDesignIconic.Icon.gmi_adb).withIdentifier(5),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_sixth).withIcon(MaterialDesignIconic.Icon.gmi_car).withIdentifier(6),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_seventh).withIcon(FontAwesome.Icon.faw_github).withIdentifier(7).withSelectable(false)
                ) // add the items we want to use with our Drawer
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem.getIdentifier() == 7) {
                            //new LibsBuilder() .withFields(R.string.class.getFields()).withActivityStyle(Libs.ActivityStyle.DARK).start(MainActivity.this);
                        } else {
                            if (drawerItem instanceof Nameable) {
                                Toast.makeText(currentActivity, ((Nameable) drawerItem).getName().getText(currentActivity), Toast.LENGTH_SHORT).show();
                            }
                        }

                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(true)
                .build();

        return result;
    }

    public void miniDrawer(final Drawer result)
    {
        crossfadeDrawerLayout = (CrossfadeDrawerLayout) result.getDrawerLayout();
        crossfadeDrawerLayout.setMaxWidthPx(730);

        MiniDrawer miniResult = result.getMiniDrawer();
        View view = miniResult.build(currentActivity);
        view.setBackgroundColor(UIUtils.getThemeColorFromAttrOrRes(currentActivity, com.mikepenz.materialdrawer.R.attr.material_drawer_background, com.mikepenz.materialdrawer.R.color.material_drawer_background));
        crossfadeDrawerLayout.getSmallView().addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        miniResult.withCrossFader(new ICrossfader() {
            @Override
            public void crossfade() {
                boolean isFaded = isCrossfaded();
                crossfadeDrawerLayout.crossfade(400);

                if (isFaded) {
                    result.getDrawerLayout().closeDrawer(GravityCompat.START);
                }
            }

            @Override
            public boolean isCrossfaded() {
                return crossfadeDrawerLayout.isCrossfaded();
            }
        });
    }
}
