package com.orhanobut.android.dialogplussample;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.dialogplus.BasicHolder;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.GridHolder;
import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.ListHolder;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.OnItemClickListener;


public class MainActivity extends ActionBarActivity {

    private RadioGroup radioGroup;
    private CheckBox headerCheckBox;
    private CheckBox footerCheckBox;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getString(R.string.app_name));

        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        headerCheckBox = (CheckBox) findViewById(R.id.header_check_box);
        footerCheckBox = (CheckBox) findViewById(R.id.footer_check_box);

        findViewById(R.id.button_bottom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(
                        radioGroup.getCheckedRadioButtonId(),
                        DialogPlus.Gravity.BOTTOM,
                        headerCheckBox.isChecked(),
                        footerCheckBox.isChecked()
                );
            }
        });

        findViewById(R.id.button_center).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(
                        radioGroup.getCheckedRadioButtonId(),
                        DialogPlus.Gravity.CENTER,
                        headerCheckBox.isChecked(),
                        footerCheckBox.isChecked()
                );
            }
        });

        findViewById(R.id.button_top).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(
                        radioGroup.getCheckedRadioButtonId(),
                        DialogPlus.Gravity.TOP,
                        headerCheckBox.isChecked(),
                        footerCheckBox.isChecked()
                );
            }
        });
    }

    private void showDialog(int holderId, DialogPlus.Gravity gravity, boolean showHeader, boolean showFooter) {
        boolean isGrid;
        Holder holder;
        switch (holderId) {
            case R.id.basic_holder_radio_button:
                holder = new BasicHolder();
                isGrid = false;
                break;
            case R.id.list_holder_radio_button:
                holder = new ListHolder();
                isGrid = false;
                break;
            default:
                holder = new GridHolder(3);
                isGrid = true;
        }

        OnClickListener clickListener = new OnClickListener() {
            @Override
            public void onClick(DialogPlus dialog, View view) {
                switch (view.getId()) {
                    case R.id.header_container:
                        Toast.makeText(MainActivity.this, "Header clicked", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.footer_confirm_button:
                        Toast.makeText(MainActivity.this, "Confirm button clicked", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.footer_close_button:
                        Toast.makeText(MainActivity.this, "Close button clicked", Toast.LENGTH_LONG).show();
                        break;
                }
                dialog.dismiss();
            }
        };

        OnItemClickListener itemClickListener = new OnItemClickListener() {
            @Override
            public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                TextView textView = (TextView) view.findViewById(R.id.text_view);
                String clickedAppName = textView.getText().toString();
                dialog.dismiss();
                Toast.makeText(MainActivity.this, clickedAppName + " clicked", Toast.LENGTH_LONG).show();
            }
        };

        SimpleAdapter adapter = new SimpleAdapter(MainActivity.this, isGrid);
        if (showHeader && showFooter) {
            showCompleteDialog(holder, gravity, adapter, clickListener, itemClickListener);
            return;
        }

        if (showHeader && !showFooter) {
            showNoFooterDialog(holder, gravity, adapter, clickListener, itemClickListener);
            return;
        }

        if (!showHeader && showFooter) {
            showNoHeaderDialog(holder, gravity, adapter, clickListener, itemClickListener);
            return;
        }

        showOnlyContentDialog(holder, gravity, adapter, itemClickListener);
    }

    private void showCompleteDialog(Holder holder, DialogPlus.Gravity gravity, BaseAdapter adapter,
                                    OnClickListener clickListener, OnItemClickListener itemClickListener) {
        final DialogPlus dialog = new DialogPlus.Builder(this)
                .setHolder(holder)
                .setHeader(R.layout.header)
                .setFooter(R.layout.footer)
                .setCancelable(true)
                .setGravity(gravity)
                .setAdapter(adapter)
                .setOnClickListener(clickListener)
                .setOnItemClickListener(itemClickListener)
                .create();
        dialog.show();
    }

    private void showNoFooterDialog(Holder holder, DialogPlus.Gravity gravity, BaseAdapter adapter,
                                    OnClickListener clickListener, OnItemClickListener itemClickListener) {
        final DialogPlus dialog = new DialogPlus.Builder(this)
                .setHolder(holder)
                .setHeader(R.layout.header)
                .setCancelable(true)
                .setGravity(gravity)
                .setAdapter(adapter)
                .setOnClickListener(clickListener)
                .setOnItemClickListener(itemClickListener)
                .create();
        dialog.show();
    }

    private void showNoHeaderDialog(Holder holder, DialogPlus.Gravity gravity, BaseAdapter adapter,
                                    OnClickListener clickListener, OnItemClickListener itemClickListener) {
        final DialogPlus dialog = new DialogPlus.Builder(this)
                .setHolder(holder)
                .setFooter(R.layout.footer)
                .setCancelable(true)
                .setGravity(gravity)
                .setAdapter(adapter)
                .setOnClickListener(clickListener)
                .setOnItemClickListener(itemClickListener)
                .create();
        dialog.show();
    }

    private void showOnlyContentDialog(Holder holder, DialogPlus.Gravity gravity, BaseAdapter adapter,
                                       OnItemClickListener itemClickListener) {
        final DialogPlus dialog = new DialogPlus.Builder(this)
                .setHolder(holder)
                .setCancelable(true)
                .setGravity(gravity)
                .setAdapter(adapter)
                .setOnItemClickListener(itemClickListener)
                .create();
        dialog.show();
    }
}
