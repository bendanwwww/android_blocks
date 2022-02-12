package com.androidblocks.blocks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.androidblocks.vo.BlockInfo;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class ScheduleFormActivity extends AppCompatActivity {

    private LinearLayout mainLinerLayout;
    private Map<String, EditText> editTextMap = new HashMap<>();
    private List<String> editTextQueue = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_form);
        mainLinerLayout = this.findViewById(R.id.scheduleForm);
    }


}