package com.wander.questworld.Release;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.wander.questworld.R;

import java.util.Arrays;

public class AddTag extends ActionBarActivity implements View.OnClickListener, TextWatcher {
    @ViewInject(R.id.bt_tag_add)
    private Button bt_add;
    @ViewInject(R.id.tags_edit)
    private EditText tags_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tag);
        ViewUtils.inject(this);
        setActionBar();

        bt_add.setOnClickListener(this);
        tags_edit.addTextChangedListener(this);
    }


    void setActionBar() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("添加标签");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        String tag = tags_edit.getText().toString();
        tag=tag.replaceAll("，", ",");
        String[] s1 = tag.split(",");
        Tags.tags.addAll(Arrays.asList(s1));
        Intent intent = new Intent(this, PublishedActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        String text = s.toString();
        text.replaceAll("，", ",");
        if (text.split("，").length == 5) {
            tags_edit.setEnabled(false);
            Toast.makeText(this, "最多添加五个标签", Toast.LENGTH_SHORT).show();
        }
    }
}
