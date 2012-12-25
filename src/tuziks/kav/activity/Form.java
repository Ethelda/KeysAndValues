package tuziks.kav.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import tuziks.kav.R;
import tuziks.kav.Constants;
import tuziks.kav.model.KeyValue;
import tuziks.kav.repository.KeyValueRepository;

/**
 * Created with IntelliJ IDEA.
 * Date: 12.22.12
 * Time: 22:13
 */
public class Form extends RoboActivity {
    private KeyValueRepository kvr;

    @InjectView(R.id.etFormKey)
    EditText etFormKey;

    @InjectView(R.id.etFormValue)
    EditText etFormValue;

    @InjectView(R.id.btDelete)
    Button btDelete;

    @InjectView(R.id.btSave)
    Button btSave;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form);
        kvr = new KeyValueRepository(this);
        btDelete.setOnClickListener(new DeleteOnClickListener());
        btSave.setOnClickListener(new SaveOnClickListener());
    }

    @Override
    protected void onResume() {
        super.onResume();
        int id = getId();
        kvr.open();
        assignFormValues(id > 0 ? kvr.get(id) : null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        kvr.close();
    }

    private int getId() {
        return getIntent().getIntExtra("id", 0);
    }

    private void assignFormValues(KeyValue kv) {
        if (kv != null) {
            String key = kv.getKey();
            etFormKey.setText(key);
            etFormValue.setText(kv.getValue());
            setTitle(key.substring(0, Math.min(50, key.length())));

        } else {
            btDelete.setVisibility(View.GONE);
            setTitle(R.string.new_key_value_item);
        }
    }

    private class SaveOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int id = getId();
            if (id == 0) {
                KeyValue kv = kvr.create(etFormKey.getText().toString(), etFormValue.getText().toString());
                Log.i(Constants.LOG_KEY, "new:" + kv.toString());
            } else {
                KeyValue kv = kvr.get(id);
                kv.setKey(etFormKey.getText().toString());
                kv.setValue(etFormValue.getText().toString());
                kvr.update(kv);
                Log.i(Constants.LOG_KEY, "update:" + kv.toString());
            }
            finish();
        }
    }

    private class DeleteOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            new AlertDialog.Builder(Form.this)
                    .setTitle(R.string.delete)
                    .setMessage(R.string.do_you_want_to_delete_keyvalue)
                    .setIcon(android.R.drawable.ic_delete)
                    .setPositiveButton(android.R.string.yes, new DeleteConfirmedListener())
                    .setNegativeButton(android.R.string.no, null).show();
        }
    }

    private class DeleteConfirmedListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            int id = getId();
            KeyValue kv = kvr.get(id);
            Log.i(Constants.LOG_KEY, "deleting:" + kv.toString());
            kvr.delete(kv);
            finish();
        }
    }
}