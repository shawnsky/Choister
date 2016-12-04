package com.xtlog.android.choister;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddChoiceFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_add,null);
        final EditText mEditText = (EditText) v.findViewById(R.id.dialog_add_edit_text);
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.fragment_add_title)
                .setPositiveButton(R.string.fragment_add_positive_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(!mEditText.getText().toString().equals("")) {
                            Choice choice = new Choice();
                            choice.setDesc(mEditText.getText().toString());

                            MainActivity activity =(MainActivity) getActivity();
                            activity.mAdapter.addChoice(choice);


                        }
                    }
                })
                .setNegativeButton(R.string.fragment_add_negative_button,null)
                .create();
    }

}
