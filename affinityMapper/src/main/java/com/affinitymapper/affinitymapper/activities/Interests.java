package com.affinitymapper.affinitymapper.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import com.affinitymapper.affinitymapper.R;
import android.widget.Toast;
import android.app.TabActivity;
import android.widget.TabHost.OnTabChangeListener;

public class Interests extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interests);
    }

    public void sportsClicked(View view)
    {
        Toast.makeText(getApplicationContext(), "Sports button is clicked", Toast.LENGTH_LONG).show();
    }

    public void literatureClick(View view)
    {
        Toast.makeText(getApplicationContext(), "Literature button is clicked", Toast.LENGTH_LONG).show();
    }

    public void historyClick(View view)
    {
        Toast.makeText(getApplicationContext(), "History button is clicked", Toast.LENGTH_LONG).show();
    }

    public void PoliticsClick(View view)
    {
        Toast.makeText(getApplicationContext(), "Politics button is clicked", Toast.LENGTH_LONG).show();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
