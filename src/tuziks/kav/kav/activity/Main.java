package tuziks.kav.kav.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import tuziks.kav.R;
import tuziks.kav.kav.model.KeyValue;
import tuziks.kav.kav.repository.KeyValueRepository;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * Date: 12.22.12
 * Time: 14:21
 */
public class Main extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        KeyValueRepository kvr = new KeyValueRepository(this);
        kvr.open();
        ListView listView = (ListView) findViewById(R.id.mylist);
        final Intent intent = new Intent(this, Form.class);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View v, int i, long l) {
                TextView twId = (TextView) v.findViewById(R.id.twItemId);

                int id = Integer.parseInt(twId.getText().toString());
                intent.putExtra("id", id);
                startActivity(intent);

                return true;
            }
        });
        listView.setAdapter(new KeyValueAdapter(this, R.layout.key_value_row, kvr.all()));
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
            default:
                return super.onOptionsItemSelected(item);
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
            KeyValue kv = stuff.get(position);
            if (kv == null)
                return v;

            TextView key = (TextView) v.findViewById(R.id.twItemLabel);
            TextView value = (TextView) v.findViewById(R.id.twItemValue);
            TextView id = (TextView) v.findViewById(R.id.twItemId);
            if (key == null || value == null)
                return v;

            key.setText(kv.getKey());
            value.setText(kv.getValue());
            id.setText(Long.toString(kv.getId()));

            return v;

        }
    }
}