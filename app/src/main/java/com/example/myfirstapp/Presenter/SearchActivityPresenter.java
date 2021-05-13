package com.example.myfirstapp.Presenter;

import android.content.Intent;
import android.widget.EditText;

public class SearchActivityPresenter {
    // setting search information
    public void settingInformation(Intent i, EditText from, EditText to, EditText keywords) {
        i.putExtra("STARTTIMESTAMP", from.getText() != null ? from.getText().toString() : "");
        i.putExtra("ENDTIMESTAMP", to.getText() != null ? to.getText().toString() : "");
        i.putExtra("KEYWORDS", keywords.getText() != null ? keywords.getText().toString() : "");
    }
}
