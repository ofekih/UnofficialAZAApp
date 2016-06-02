package org.ramonaza.unofficialazaapp.helpers.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import org.ramonaza.unofficialazaapp.R;
import org.ramonaza.unofficialazaapp.helpers.backend.DatabaseHandler;
import org.ramonaza.unofficialazaapp.settings.ui.activities.SettingsActivity;

/**
 * Created by ilanscheinkman on 5/9/15.
 */
public abstract class BaseActivity extends Activity {

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_default, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        DatabaseHandler.init(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
