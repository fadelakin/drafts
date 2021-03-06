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

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateNewDraftActivity extends AppCompatActivity {

    @BindView(R.id.draft_et) EditText draft;
    @BindView(R.id.draft_title_et) EditText draftTitle;

    private ThoughtsDataSource mDataSource;
    private Thought mIntentThought;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_draft);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Create New Draft");
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mDataSource = new ThoughtsDataSource(this);

        mIntentThought = (Thought) getIntent().getSerializableExtra("thought");
        if(mIntentThought != null) {
            draft.setText(mIntentThought.getDrafts());
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIntentThought != null) {
                    wasted();
                } else {
                    doneWriting();
                }
            }
        });
    }

    private void wasted() {
        final String thoughtString = draft.getText().toString();
        final String thoughtTitle = draftTitle.getText().toString();
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
                            updateOrCreateThought(thoughtString, thoughtTitle);
                        }
                    })
                    .neutralText("Discard")
                    .onNeutral(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(MaterialDialog dialog, DialogAction which) {
                            goHome();
                        }
                    })
                    .build().show();
        }
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
        switch(id) {
            case R.id.action_save:
                if (mIntentThought != null) {
                    saveDraft();
                } else {
                    saveDraft();
                }
                break;
            case R.id.action_delete:
                deleteDraft();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteDraft() {
        if (mIntentThought != null) {
            mDataSource.deleteThought(mIntentThought);
            Toast.makeText(CreateNewDraftActivity.this, "Draft deleted.", Toast.LENGTH_SHORT).show();
            goHome();
        }
    }

    private void saveDraft() {
        String thoughtString = draft.getText().toString();
        String thoughtTitle = draftTitle.getText().toString();
        if (thoughtString.isEmpty() || thoughtString.trim().length() == 0) {
            goHome();
        } else {
            updateOrCreateThought(thoughtString, thoughtTitle);
        }
    }

    private void doneWriting() {
        final String thoughtString = draft.getText().toString();
        final String thoughtTitle = draftTitle.getText().toString();

        if(thoughtString.isEmpty() || thoughtString.trim().length() == 0) {
            goHome();
        } else {
            new MaterialDialog.Builder(this)
                    .content("Save draft?")
                    .contentColor(getResources().getColor(R.color.colorAccent))
                    .positiveText("Save")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(MaterialDialog dialog, DialogAction which) {
                            updateOrCreateThought(thoughtString, thoughtTitle);
                        }
                    })
                    .neutralText("Discard")
                    .onNeutral(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(MaterialDialog dialog, DialogAction which) {
                            goHome();
                        }
                    })
                    .build().show();
        }
    }

    private void goHome() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void updateOrCreateThought(String thoughtString, String thoughtTitle) {
        if(mIntentThought != null) {
            ThoughtsDataSource dataSource = new ThoughtsDataSource(this);
            mIntentThought.setDrafts(thoughtString);
            mIntentThought.setTitle(thoughtTitle);
            dataSource.updateThought(mIntentThought);

            Toast.makeText(this, "Draft updated.", Toast.LENGTH_SHORT).show();

            goHome();
        } else {
            Thought thought = new Thought();
            thought.setDrafts(thoughtString);
            thought.setTitle(thoughtTitle);

            mDataSource.createThought(thought);

            Toast.makeText(this, "Draft saved.", Toast.LENGTH_SHORT).show();

            goHome();
        }
    }

    @Override
    public void onBackPressed() {
        if (mIntentThought != null) {
            wasted();
        } else {
            doneWriting();
        }
    }
}
