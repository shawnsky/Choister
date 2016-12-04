package com.xtlog.android.choister;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import java.util.List;
import java.util.UUID;


public class EditChoiceFragment extends DialogFragment {

    private UUID choiceId;
    private static final String ARG_EDIT_TEXT = "arg_edit_text";
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_edit,null);
        final EditText mEditText = (EditText)v.findViewById(R.id.dialog_edit_edit_text);
        mEditText.setText(getChoiceDesc(choiceId));
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setPositiveButton(R.string.fragment_edit_positive_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity activity = (MainActivity) getActivity();
                        activity.mAdapter.updateChoice(choiceId, mEditText.getText().toString());
                    }
                })
                .setNegativeButton(R.string.fragment_edit_delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity activity = (MainActivity) getActivity();
                        activity.mAdapter.deleteChoice(choiceId);
                    }
                })
                .setTitle(R.string.fragment_edit_description)
                .create();

    }
    public void setChoiceId(UUID id){
        choiceId = id;
    }
    private String getChoiceDesc(UUID id){
        List<Choice> lists = ChoiceLab.get(getActivity()).getChoices();
        for(Choice c:lists){
            if(c.getId().equals(id)){
                return c.getDesc();
            }
        }
        return null;
    }


}
