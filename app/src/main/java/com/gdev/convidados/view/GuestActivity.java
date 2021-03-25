package com.gdev.convidados.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.gdev.convidados.R;
import com.gdev.convidados.constants.GuestConstants;
import com.gdev.convidados.model.GuestModel;
import com.gdev.convidados.viewmodel.GuestViewModel;

public class GuestActivity extends AppCompatActivity implements View.OnClickListener {

    private final ViewHolder mViewHolder = new ViewHolder();
    private GuestViewModel mViewModel;
    private int mGuestId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_form);

        this.mViewModel = new ViewModelProvider(this).get(GuestViewModel.class);

        this.mViewHolder.editName = findViewById(R.id.edit_name);
        this.mViewHolder.radioAbsent = findViewById(R.id.radio_absent);
        this.mViewHolder.radioNotConfirmed = findViewById(R.id.radio_not_confirmed);
        this.mViewHolder.radioPresent = findViewById(R.id.radio_present);
        this.mViewHolder.buttonSave = findViewById(R.id.button_save);
        this.mViewHolder.buttonSave.setOnClickListener(this);

        observers();
        this.mGuestId = getIntent().getIntExtra(GuestConstants.INTENT_ID, 0);
        if (mGuestId != 0) {
            modeEdit();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_save) {
            handleSave();
        }
    }

    private void observers() {
        this.mViewModel.guestModel.observe(this, guestModel -> {
            this.mViewHolder.editName.setText(guestModel.getName());
            if (guestModel.getConfirmation() == GuestConstants.NOT_CONFIRMED) {
                this.mViewHolder.radioNotConfirmed.setChecked(true);
            } else if (guestModel.getConfirmation() == GuestConstants.PRESENT) {
                this.mViewHolder.radioPresent.setChecked(true);
            } else if (guestModel.getConfirmation() == GuestConstants.ABSENT) {
                this.mViewHolder.radioAbsent.setChecked(true);
            }
        });

        this.mViewModel.feedback.observe(this, feedback ->{
            Toast.makeText(getApplicationContext(), feedback.getMessage(), Toast.LENGTH_SHORT).show();
            if(feedback.isSucess()){
                finish();
            }
        } );
    }

    private void modeEdit() {
        this.mViewHolder.buttonSave.setText("Atualizar");
        this.mViewModel.get(this.mGuestId);
    }

    private void handleSave() {
        String name = this.mViewHolder.editName.getText().toString();
        int confirmation = 0;
        if (this.mViewHolder.radioPresent.isChecked()) {
            confirmation = GuestConstants.PRESENT;
        } else if (this.mViewHolder.radioAbsent.isChecked()) {
            confirmation = GuestConstants.ABSENT;
        }

        GuestModel guestModel = new GuestModel(this.mGuestId, name, confirmation);
        this.mViewModel.saveOrUpdate(guestModel);
    }

    private static class ViewHolder {
        EditText editName;
        RadioButton radioAbsent;
        RadioButton radioNotConfirmed;
        RadioButton radioPresent;
        Button buttonSave;
    }
}