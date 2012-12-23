package tuziks.kav.kav.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import tuziks.kav.R;
import tuziks.kav.kav.Constants;
import tuziks.kav.kav.model.KeyValue;
import tuziks.kav.kav.repository.KeyValueRepository;

/**
 * Created with IntelliJ IDEA.
 * Date: 12.22.12
 * Time: 22:13
 */
public class Form extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form);

        final KeyValueRepository kvr = new KeyValueRepository(this);
        kvr.open();


        findViewById(R.id.btDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                new AlertDialog.Builder(Form.this)
                        .setTitle(R.string.delete)
                        .setMessage(R.string.do_you_want_to_delete_keyvalue)
                        .setIcon(android.R.drawable.ic_delete)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                                TextView tvId = (TextView) findViewById(R.id.tvFormId);

                                int id;
                                try {
                                    id = Integer.parseInt(tvId.getText().toString());
                                } catch (NumberFormatException nfe) {
                                    id = 0;
                                }
                                KeyValue kv = kvr.get(id);
                                Log.i(Constants.LOG_KEY, "deleting:" + kv.toString());
                                kvr.delete(kv);
                                finish();

                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();


            }
        });
        findViewById(R.id.btSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextView key = (TextView) findViewById(R.id.etFormKey);
                TextView value = (TextView) findViewById(R.id.etFormValue);
                TextView tvId = (TextView) findViewById(R.id.tvFormId);

                int id;
                try {
                    id = Integer.parseInt(tvId.getText().toString());
                } catch (NumberFormatException nfe) {
                    id = 0;
                }
                if (id == 0) {
                    KeyValue kv = kvr.create(key.getText().toString(), value.getText().toString());
                    Log.i(Constants.LOG_KEY, "new:" + kv.toString());
                } else {
                    KeyValue kv = kvr.get(id);
                    kv.setKey(key.getText().toString());
                    kv.setValue(value.getText().toString());
                    kvr.update(kv);
                    Log.i(Constants.LOG_KEY, "update:" + kv.toString());
                }

                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();    //To change body of overridden methods use File | Settings | File Templates.

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);

        if (id > 0) {
            KeyValue kv = new KeyValueRepository(this).open().get(id);

            ((EditText) findViewById(R.id.etFormKey)).setText(kv.getKey());
            ((EditText) findViewById(R.id.etFormValue)).setText(kv.getValue());
            ((TextView) findViewById(R.id.tvFormId)).setText(Integer.toString(kv.getId()));
        } else {
            ((Button) findViewById(R.id.btDelete)).setVisibility(View.GONE);
        }
    }
}
