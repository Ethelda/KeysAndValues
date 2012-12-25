package tuziks.kav.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import tuziks.kav.R;
import tuziks.kav.Constants;
import tuziks.kav.model.KeyValue;
import tuziks.kav.repository.KeyValueRepository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Date: 12.22.12
 * Time: 14:21
 */
public class Main extends RoboActivity {

    @InjectView(R.id.mylist)
    ListView listView;

    @InjectView(R.id.btNewItem)
    Button btNewItem;

    KeyValueRepository kvr;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        listView.setOnItemLongClickListener(new KeyValueListItemLongClickListener());
        btNewItem.setOnClickListener(new AddNewOnclickListener());
        kvr = new KeyValueRepository(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        kvr.open();
        listView.setAdapter(new KeyValueAdapter(this, R.layout.key_value_row, kvr.all()));
    }

    @Override
    protected void onPause() {
        super.onPause();
        kvr.close();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_new:
                startActivity(new Intent(this, Form.class));
                return true;
            case R.id.about:
                startActivity(new Intent(this, About.class));
                return true;
        }
        return false;
    }

    private class KeyValueListItemLongClickListener implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View v, int i, long l) {
            Intent intent = new Intent(Main.this, Form.class);
            int id = Integer.parseInt(((TextView) v.findViewById(R.id.twItemId)).getText().toString());
            intent.putExtra("id", id);
            startActivity(intent);
            return true;
        }
    }

    private class KeyValueAdapter extends ArrayAdapter<KeyValue> {
        private List<KeyValue> stuff;

        public KeyValueAdapter(Context context, int textViewResourceId, List<KeyValue> items) {
            super(context, textViewResourceId, items);
            stuff = items;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = li.inflate(R.layout.key_value_row, null);
            }
            // view injektēšana nedarbojas šeit.
            TextView twItemId = (TextView) v.findViewById(R.id.twItemId);
            TextView twItemKey = (TextView) v.findViewById(R.id.twItemKey);
            TextView twItemValue = (TextView) v.findViewById(R.id.twItemValue);
            if (twItemKey == null || twItemValue == null || twItemId == null || twItemKey.getText().length() > 0 ||
                    twItemValue.getText().length() > 0 || twItemId.getText().length() > 0)
                return v;
            KeyValue kv = stuff.get(position);
            if (kv == null)
                return v;
            Log.w(Constants.LOG_KEY, "Set view to: " + kv.toString());

            twItemValue.setText(kv.getValue());
            twItemKey.setText(kv.getKey());
            twItemId.setText(Long.toString(kv.getId()));

            return v;
        }
    }


    private class AddNewOnclickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(Main.this, Form.class));
        }
    }
}