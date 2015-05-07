package com.example.inapppaymenttest;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.firstouch.inapppayment.*;

public class MainActivity extends Activity implements PaymentResponseHandler{
	Context context;
	Payment payment;
	String inappcode;
	String cost;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.context = this;
		
		final EditText itemid = new EditText(this);
		itemid.setHint("Enter InAppCode");
		itemid.setId(1);
		
		final EditText txtItemprice = new EditText(this);
		txtItemprice.setHint("Enter price");
		txtItemprice.setInputType(InputType.TYPE_CLASS_NUMBER);
		txtItemprice.setId(2);
		
		
		Button success = new Button(this);
		success.setText("Success");
		success.setBackgroundColor(Color.GRAY);
		success.setId(3);
		
		Button failure = new Button(this);
		failure.setText("Failure");
		failure.setBackgroundColor(Color.GRAY);
		failure.setId(4);
		
		Button cancel = new Button(this);
		cancel.setText("Cancel");
		cancel.setBackgroundColor(Color.GRAY);
		cancel.setId(5);
		
		Button invoke = new Button(this);
		invoke.setText("Invoke Real");
		invoke.setBackgroundColor(Color.GRAY);
		invoke.setId(6);
		
		CheckBox checktest = new CheckBox(this);
		checktest.setText("Set transaction mode");
		checktest.setId(7);
        
		
		RelativeLayout activity_main = new RelativeLayout(this);
		activity_main.setBackgroundColor(Color.WHITE);
		
		RelativeLayout.LayoutParams textParams = 
                new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, 
                    RelativeLayout.LayoutParams.WRAP_CONTENT);

		textParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		textParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		textParams.setMargins(0, 60, 0, 0);
		
		RelativeLayout.LayoutParams editParams = 
                new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, 
                    RelativeLayout.LayoutParams.WRAP_CONTENT);

		editParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		editParams.addRule(RelativeLayout.BELOW,itemid.getId());
		editParams.setMargins(0, 20, 0, 0);
		
		RelativeLayout.LayoutParams buttonParams1 = 
                new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, 
                    RelativeLayout.LayoutParams.WRAP_CONTENT);

        buttonParams1.addRule(RelativeLayout.CENTER_HORIZONTAL);
        buttonParams1.addRule(RelativeLayout.BELOW, txtItemprice.getId());
        buttonParams1.setMargins(0, 20, 0, 0);
       
        RelativeLayout.LayoutParams buttonParams2 = 
                new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, 
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
        buttonParams2.addRule(RelativeLayout.CENTER_HORIZONTAL);
        
        buttonParams2.addRule(RelativeLayout.BELOW, success.getId());
        buttonParams2.setMargins(0, 20, 0, 0);
        
        RelativeLayout.LayoutParams buttonParams3 = 
                new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, 
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
        buttonParams3.addRule(RelativeLayout.CENTER_HORIZONTAL);
        
        buttonParams3.addRule(RelativeLayout.BELOW, failure.getId());
        buttonParams3.setMargins(0, 20, 0, 0);
        
        RelativeLayout.LayoutParams buttonParams4 = 
                new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, 
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
        buttonParams4.addRule(RelativeLayout.CENTER_HORIZONTAL);
        
        buttonParams4.addRule(RelativeLayout.BELOW, cancel.getId());
        buttonParams4.setMargins(0, 20, 0, 0);
        
        RelativeLayout.LayoutParams checkBoxParams = 
        		new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
        checkBoxParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        checkBoxParams.addRule(RelativeLayout.BELOW, invoke.getId());
        checkBoxParams.setMargins(0, 20, 0, 0);
        
        activity_main.addView(itemid, textParams);
        activity_main.addView(txtItemprice, editParams);
		activity_main.addView(success, buttonParams1);
		activity_main.addView(failure, buttonParams2);
		activity_main.addView(cancel, buttonParams3);
		activity_main.addView(invoke, buttonParams4);
		activity_main.addView(checktest, checkBoxParams);
		setContentView(activity_main);
		
		
		payment = Payment.getInstance(context);
		payment.setListener(this);
		
		success.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				inappcode = itemid.getText().toString();
				cost = txtItemprice.getText().toString();
				payment.initiateTest(inappcode, cost, "success");
				
			}
		});
		
		failure.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				inappcode = itemid.getText().toString();
				cost = txtItemprice.getText().toString();
				payment.initiateTest(inappcode, cost, "failure");
				
			}
		});
		
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				inappcode = itemid.getText().toString();
				cost = txtItemprice.getText().toString();
				payment.initiateTest(inappcode, cost, "cancel");
				
			}
		});
		
		invoke.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						if(payment.isAvailable()){//check whether payment is available or not
							inappcode = itemid.getText().toString();
							cost = txtItemprice.getText().toString();
							payment.initiatePayment(inappcode, cost);//pass the inappcode and price for processing
						}
						else{
							Toast.makeText(context, "Payment not supported!", Toast.LENGTH_LONG).show();
						}
			}
		});
		
		checktest.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(((CheckBox) v).isChecked()){
					payment.setApp_mode();//set the appmode to transaction processing mode, the appmode is reset after every transaction.
				}else{
					payment.unsetApp_mode();//unset the appmode while testing
				}
				
			}
		});
		
	}


	@Override
	public void onSuccess(String code, String response_message) {
		//Utils.showLongToast(context, response_message);
		Toast.makeText(context, code+": "+response_message, Toast.LENGTH_SHORT).show();
		
	}

	@Override
	public void onCancel(String code, String response_message) {
		//Utils.showLongToast(context, response_message);
		Toast.makeText(context, code+": "+response_message, Toast.LENGTH_SHORT).show();
		
	}

	@Override
	public void onFailed(String code, String response_message) {
		//Utils.showLongToast(context, response_message);
		Toast.makeText(context, code+": "+response_message, Toast.LENGTH_SHORT).show();
		Log.d("INAPP",response_message);
		
	}
}
