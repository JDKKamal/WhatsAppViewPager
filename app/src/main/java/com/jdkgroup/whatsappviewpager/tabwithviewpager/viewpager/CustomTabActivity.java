package com.jdkgroup.whatsappviewpager.tabwithviewpager.viewpager;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jdkgroup.whatsappviewpager.R;
import com.jdkgroup.whatsappviewpager.tabwithviewpager.ViewPagerAdapter;
import com.jdkgroup.whatsappviewpager.tabwithviewpager.fragment.CallsFragment;
import com.jdkgroup.whatsappviewpager.tabwithviewpager.fragment.ChatFragment;
import com.jdkgroup.whatsappviewpager.tabwithviewpager.fragment.ContactsFragment;

public class CustomTabActivity extends AppCompatActivity {

    //This is our TabLayout
    private TabLayout tabLayout;

    //This is our ViewPager
    private ViewPager viewPager;

    //Fragments
    private ChatFragment chatFragment;
    private CallsFragment callsFragment;
    private ContactsFragment contactsFragment;

    private String[] tabTitle = {"CALLS", "CHAT", "CONTACTS"};
    int[] unreadCount = {0, 5, 0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_without_icon);

        //Initializing ViewPager
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(3);
        setupViewPager(viewPager);

        //Initializing the TabLayout
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);

        try {
            setupTabIcons();
        } catch (Exception e) {
            e.printStackTrace();
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewPager.setCurrentItem(position, false);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        // Associate searchable configuration with the SearchView
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_status:
                Toast.makeText(this, "Home Status Click", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_settings:
                Toast.makeText(this, "Home Settings Click", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_with_icon:
                Intent withicon = new Intent(this, TabWithIconActivity.class);
                startActivity(withicon);
                finish();
                return true;
            case R.id.action_without_icon:
                Intent withouticon = new Intent(this, TabWOIconActivity.class);
                startActivity(withouticon);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        callsFragment = new CallsFragment();
        chatFragment = new ChatFragment();
        contactsFragment = new ContactsFragment();
        adapter.addFragment(callsFragment, "CALLS");
        adapter.addFragment(chatFragment, "CHAT");
        adapter.addFragment(contactsFragment, "CONTACTS");
        viewPager.setAdapter(adapter);
    }

    private View prepareTabView(int pos) {
        View view = getLayoutInflater().inflate(R.layout.custom_tab, null);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        TextView tv_count = (TextView) view.findViewById(R.id.tv_count);
        tv_title.setText(tabTitle[pos]);

        tv_count.setText("" + unreadCount[pos]);
        if (unreadCount[pos] > 0) {
            tv_count.setVisibility(View.VISIBLE);
            tv_count.setText("" + unreadCount[pos]);
        } else
            tv_count.setVisibility(View.GONE);

        return view;
    }

    private void setupTabIcons() {
        for (int i = 0; i < tabTitle.length; i++) {

            /*TabLayout.Tab tabitem = tabLayout.newTab();
            tabitem.setCustomView(prepareTabView(i));
            tabLayout.addTab(tabitem);*/

            tabLayout.getTabAt(i).setCustomView(prepareTabView(i));
        }
    }
}
