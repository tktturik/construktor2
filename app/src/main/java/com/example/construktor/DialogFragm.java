package com.example.construktor;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.DialogFragment;

import static com.example.construktor.MainActivity.DF_VERSION;
import static com.example.construktor.Consturktor2.correct;
import static com.example.construktor.Consturktor2.n2;
import static com.example.construktor.Construktor.DataBaseName;
public class DialogFragm  extends DialogFragment implements View.OnClickListener {
    String ans,title;
    TextView corrAns,procCorrAns,txTitle;
    

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setCanceledOnTouchOutside(false);
        switch (DF_VERSION){
            case 1:
                View v = inflater.inflate(R.layout.dialogfrag, null);
                v.findViewById(R.id.buttonclose).setOnClickListener(this);
                getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                return v;
            case 2:
                View v2 = inflater.inflate(R.layout.fragmnet2, null);
                v2.findViewById(R.id.nextbtn).setOnClickListener(this);
                v2.findViewById(R.id.btnpotom).setOnClickListener(this);
                getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                return v2;
            case 3:
                View v3 = inflater.inflate(R.layout.fragment3,null);
                v3.findViewById(R.id.btnOK).setOnClickListener(this);
                title=DataBase.DATABASE_QUES;
                txTitle=v3.findViewById(R.id.title);
                corrAns=v3.findViewById(R.id.corrAns);
                procCorrAns=v3.findViewById(R.id.procCorrAns);
                ans=String.format("%.1f",((correct/n2)*100));
                corrAns.setText("Правиьные ответы"+" "+String.valueOf((int)(correct)));
                corrAns.append("/"+String.valueOf(n2));
                txTitle.setText(title);
                procCorrAns.setText(ans+"%");
                getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                correct=0;
                return v3;
        }
       return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonclose:
                dismiss();
                break;
            case R.id.nextbtn:
                Intent inte = new Intent(getContext(), Consturktor2.class);
                inte.putExtra("id", DataBaseName);
                startActivity(inte);
                break;
            case R.id.btnpotom:
                Intent inte2 = new Intent(getContext(), MainActivity.class);
                startActivity(inte2);
                break;
            case R.id.btnOK:
                Intent inte3 = new Intent(getContext(), MainActivity.class);
                startActivity(inte3);
                break;


        }


    }


}

