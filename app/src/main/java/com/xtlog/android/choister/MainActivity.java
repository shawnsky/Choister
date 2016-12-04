package com.xtlog.android.choister;



import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private static final String DIALOG_ADD = "DialogAdd";
    private static final String DIALOG_EDIT = "DialogEdit";
    private static final String SAVE_VIEW = "saveView";
    private RecyclerView mChoiceRecyclerView;
    public ChoiceAdapter mAdapter;
    private List<UUID> selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String res;
                if(selected.size()<2){
                    res = "请至少有两个选择";
                }
                else{
                    res = ChoiceLab.get(MainActivity.this).getChoice(getRandomChoiceId()).getDesc();
                }
                Snackbar.make(view, res, Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .show();

            }
        });
        mChoiceRecyclerView = (RecyclerView)findViewById(R.id.choice_recycler_view);
        mChoiceRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
        selected = new ArrayList<>();
        updateUI();

    }

    private void updateUI(){
        ChoiceLab choiceLab = ChoiceLab.get(this);
        List<Choice> choices = choiceLab.getChoices();
        if(mAdapter ==null){
            mAdapter = new ChoiceAdapter(choices);
            mChoiceRecyclerView.setAdapter(mAdapter);
        }
        else{
            mAdapter.notifyDataSetChanged();
        }

    }

    private class ChoiceHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener,View.OnClickListener{
        private Button mDescButton;
        private Choice mChoice;

        public void bindChoice(Choice choice){
            mChoice = choice;
            mDescButton.setText(mChoice.getDesc());
            String color = "#"+mChoice.getColor();
            mDescButton.setBackgroundColor(Color.parseColor(color));

        }

        public ChoiceHolder(View itemView) {
            super(itemView);
            mDescButton = (Button) itemView.findViewById(R.id.list_item_choice_desc_text_view);
            mDescButton.setOnLongClickListener(this);
            mDescButton.setOnClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {

            FragmentManager fm = getSupportFragmentManager();
            EditChoiceFragment DialogEdit = new EditChoiceFragment();
            DialogEdit.setChoiceId(mChoice.getId());
            DialogEdit.show(fm,DIALOG_EDIT);



            return true;
        }

        @Override
        public void onClick(View v) {
            float f = 1;
            mDescButton.setAlpha(f);
            mDescButton.setTextSize(20);
            selected.add(mChoice.getId());


        }
    }

    public class ChoiceAdapter extends RecyclerView.Adapter<ChoiceHolder> {
        private List<Choice> mChoices;

        public ChoiceAdapter(List<Choice> choices) {
            mChoices = choices;
        }

        @Override
        public ChoiceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ChoiceHolder holder = new ChoiceHolder(LayoutInflater.from(MainActivity.this).inflate(R.layout.list_item_choice, parent, false));
            return holder;
        }

        @Override
        public void onBindViewHolder(ChoiceHolder holder, int position) {
            Choice choice = mChoices.get(position);
            holder.bindChoice(choice);
        }

        @Override
        public int getItemCount() {
            return mChoices.size();
        }

        public void addChoice(Choice newChoice) {
            ChoiceLab.get(MainActivity.this).addChoice(newChoice);
//            notifyItemInserted(0);
            notifyDataSetChanged();
            mChoiceRecyclerView.removeAllViews();
        }

        public void deleteChoice(UUID id) {
            ChoiceLab.get(MainActivity.this).deleteChoice(id);
//            notifyItemRemoved(findChoiceIndex(id));
            notifyDataSetChanged();
        }

        public void updateChoice(UUID id, String str){
            ChoiceLab.get(MainActivity.this).getChoice(id).setDesc(str);
            notifyDataSetChanged();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_how_to_use:
                FragmentManager f = getSupportFragmentManager();
                AlertDialog alertDialog = new AlertDialog.Builder(this)
                        .setView(View.inflate(this,R.layout.dialog_howtouse,null))
                        .setTitle(R.string.action_how_to_use)
                        .setPositiveButton(R.string.how_to_use_button,null)
                        .create();
                alertDialog.show();
                return true;

            case R.id.action_add:
                FragmentManager fm = getSupportFragmentManager();
                AddChoiceFragment DialogAdd = new AddChoiceFragment();
                DialogAdd.show(fm,DIALOG_ADD);
                return true;

            case R.id.action_reselect:
                selected = new ArrayList<UUID>();
                mChoiceRecyclerView.removeAllViews();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onResume(){
        super.onResume();
        updateUI();
    }

    public UUID getRandomChoiceId(){
        int size = selected.size();
        int a =(int) (Math.random()*size);
        return selected.get(a);
    }


}
