package com.example.product;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import Utils.LoginUtils;

public class MainActivity extends Activity implements View.OnClickListener{

	Button btnAllSubjects, btnChinese, btnMath, btnEnglish, btnPhysics, btnChemistry, btnBiology, btnHistory, btnGeography, btnPolitics;
//	Button btnRegister;
	Button btnLogin;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Buttons
//		btnAllSubjects = (Button) findViewById(R.id.btnAllSubjects);
//		btnAllSubjects.setOnClickListener(this);

//		btnRegister = (Button) findViewById(R.id.btnRegister);
//		btnRegister.setOnClickListener(this);

		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnLogin.setOnClickListener(this);

		btnChinese = (Button) findViewById(R.id.btnChinese);
		btnChinese.setOnClickListener(this);

		btnMath= (Button)findViewById(R.id.btnMath);
		btnMath.setOnClickListener(this);

		btnEnglish=(Button)findViewById(R.id.btnEnglish);
		btnEnglish.setOnClickListener(this);

		btnPhysics=(Button)findViewById(R.id.btnPhysics);
		btnPhysics.setOnClickListener(this);

		btnChemistry=(Button)findViewById(R.id.btnChemistry);
		btnChemistry.setOnClickListener(this);

		btnBiology=(Button)findViewById(R.id.btnBiology);
		btnBiology.setOnClickListener(this);

		btnHistory=(Button)findViewById(R.id.btnHistory);
		btnHistory.setOnClickListener(this);

		btnGeography=(Button)findViewById(R.id.btnGiology);
		btnGeography.setOnClickListener(this);

		btnPolitics=(Button)findViewById(R.id.btnPolitics);
		btnPolitics.setOnClickListener(this);

/*
		// view products click event
		btnViewProducts.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// Launching All products Activity
				Intent i = new Intent(getApplicationContext(),
						AllProductsActivity.class);
				startActivity(i);

			}
		});

		// view products click event
		btnNewProduct.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// Launching create new product activity
				Intent i = new Intent(getApplicationContext(),
						NewProductActivity.class);
				startActivity(i);

			}
		});

		//Login event
		btnLogin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// Launching create new product activity
				Intent i = new Intent(getApplicationContext(),
						LoginActivity.class);
				startActivity(i);

			}
		});
*/
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()){
//			case R.id.btnAllSubjects:
//				Intent getAllSubject = new Intent(getApplicationContext(),ViewActivity.class);
//				getAllSubject.putExtra("subject","all");
//				startActivity(getAllSubject);
//				break;
			case R.id.btnChinese:
				Intent getChinese = new Intent(getApplicationContext(),ViewActivity.class);
				getChinese.putExtra("subject","chinese");
				startActivity(getChinese);
				break;
			case R.id.btnMath:
				Intent getMath = new Intent(getApplicationContext(),ViewActivity.class);
				getMath.putExtra("subject","math");
				startActivity(getMath);
				break;
			case R.id.btnEnglish:
				Intent getEnglish = new Intent(getApplicationContext(),ViewActivity.class);
				getEnglish.putExtra("subject","english");
				startActivity(getEnglish);
				break;
			case R.id.btnPhysics:
				Intent getPhysics = new Intent(getApplicationContext(),ViewActivity.class);
				getPhysics.putExtra("subject","physics");
				startActivity(getPhysics);
				break;
			case R.id.btnChemistry:
				Intent getChemistry = new Intent(getApplicationContext(),ViewActivity.class);
				getChemistry.putExtra("subject","chemistry");
				startActivity(getChemistry);
				break;
			case R.id.btnBiology:
				Intent getBiology = new Intent(getApplicationContext(),ViewActivity.class);
				getBiology.putExtra("subject","biology");
				startActivity(getBiology);
				break;
			case R.id.btnHistory:
				Intent getHistory = new Intent(getApplicationContext(),ViewActivity.class);
				getHistory.putExtra("subject","history");
				startActivity(getHistory);
				break;
			case R.id.btnGiology:
				Intent getGeography = new Intent(getApplicationContext(),ViewActivity.class);
				getGeography.putExtra("subject","geography");
				startActivity(getGeography);
				break;
			case R.id.btnPolitics:
				Intent getPolitics = new Intent(getApplicationContext(),ViewActivity.class);
				getPolitics.putExtra("subject","politics");
				startActivity(getPolitics);
				break;
//			case R.id.btnRegister:
//				Intent register = new Intent(getApplicationContext(),RegisterActivity.class);
//				startActivity(register);
//				break;
			case R.id.btnLogin:
				Intent login = new Intent(getApplicationContext(),LoginActivity.class);
				Intent login2 = new Intent(getApplicationContext(),LoginActivity2.class);
				//判断登陆状态
				SharedPreferences sp = getSharedPreferences("user", Context.MODE_PRIVATE);
				String status = sp.getString("status", null);
				if(LoginUtils.isLogin(status))
					startActivity(login2);
				else
					startActivity(login);
				break;
		}

	}
}