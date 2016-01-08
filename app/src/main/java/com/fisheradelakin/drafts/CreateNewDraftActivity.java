package com.fisheradelakin.drafts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.fisheradelakin.drafts.db.ThoughtsDataSource;
import com.fisheradelakin.drafts.model.Thought;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CreateNewDraftActivity extends AppCompatActivity {

    @Bind(R.id.draft_et) EditText draft;

    private ThoughtsDataSource mDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_draft);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Create New Draft");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String thoughtString = draft.getText().toString();
                if(thoughtString.isEmpty() || thoughtString.trim().length() == 0) {
                    finish();
                } else {
                    new MaterialDialog.Builder(CreateNewDraftActivity.this)
                            .content("Save draft?")
                            .contentColor(getResources().getColor(R.color.colorAccent))
                            .positiveText("Save")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(MaterialDialog dialog, DialogAction which) {
                                    Thought thought = new Thought();
                                    thought.setDrafts(thoughtString);

                                    mDataSource.createThought(thought);

                                    Toast.makeText(CreateNewDraftActivity.this, "Draft saved.", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(CreateNewDraftActivity.this, MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                            })
                            .negativeText("Delete")
                            .onNegative(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(MaterialDialog dialog, DialogAction which) {
                                    finish();
                                }
                            })
                            .build().show();
                }
            }
        });

        mDataSource = new ThoughtsDataSource(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            String thoughtString = draft.getText().toString();

            if(thoughtString.isEmpty() || thoughtString.trim().length() == 0) {
                new MaterialDialog.Builder(this)
                        .content("Save draft?")
                        .contentColor(getResources().getColor(R.color.colorAccent))
                        .positiveText("Delete")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(MaterialDialog dialog, DialogAction which) {
                                Intent intent = new Intent(CreateNewDraftActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        })
                        .neutralText("Cancel")
                        .build().show();
            } else {
                Thought thought = new Thought();
                thought.setDrafts(thoughtString);

                mDataSource.createThought(thought);

                Toast.makeText(this, "Draft saved.", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        }

        return super.onOptionsItemSelected(item);
    }

}
