/* 
 * Copyright (C) 2011 iMellon
 * 
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.imellon.android.vatchecker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Formatter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRoute;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import com.imellon.android.vatchecker.util.AndroidXMLParser;
import com.imellon.android.vatchecker.util.Message;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * The Class VatCheckerActivity.
 *
 * @author Christos Papazafeiropoulos - Email: christos@imellon.com
 * @author Dimitris Makris - Email: dmakris@imellon.com
 */
public class VatCheckerActivity extends Activity {

	/** The Constant TAG. */
	private static final String TAG = VatCheckerActivity.class.getSimpleName();

	/** The Constant sClient. */
	private static final DefaultHttpClient sClient;
	static {
		final HttpParams params = new BasicHttpParams();
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(params,
				HTTP.DEFAULT_CONTENT_CHARSET);

		HttpConnectionParams.setStaleCheckingEnabled(params, false);
		HttpConnectionParams.setConnectionTimeout(params, 20 * 1000);
		HttpConnectionParams.setSoTimeout(params, 20 * 1000);
		HttpConnectionParams.setSocketBufferSize(params, 8192);

		ConnManagerParams.setTimeout(params, 1000);
		ConnManagerParams.setMaxConnectionsPerRoute(params, new ConnPerRoute() {
			@Override
			public int getMaxForRoute(HttpRoute route) {
				return 20;
			}
		});

		HttpClientParams.setRedirecting(params, false);

		HttpProtocolParams.setUserAgent(params, "VatChecker/1.1");

		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		schemeRegistry.register(new Scheme("https", SSLSocketFactory
				.getSocketFactory(), 443));

		ClientConnectionManager manager = new ThreadSafeClientConnManager(
				params, schemeRegistry);
		sClient = new DefaultHttpClient(manager, params);
	}

	/** The activity_description_value. */
	private TextView vat_value, tax_office_code_value,
			tax_office_description_value, vat_indication_value, name_value,
			title_value, address_value, number_value, zip_code_value,
			area_value, start_date_value, end_date_value, telephone_value,
			fax_value, activity_value, activity_description_value;

	/** The search field. */
	private EditText searchField;

	/** The message. */
	private Message message;

	/* *********************************************************************
	 * 
	 * Activity cycle functions
	 */
	/*
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate");

		setupViews();
	}

	/* 
	 * @see android.app.Activity#onStart()
	 */
	@Override
	protected void onStart() {
		super.onResume();
		Log.d(TAG, "onStart");
	}

	/* 
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();
		Log.d(TAG, "onResume");
	}

	/* 
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		super.onPause();
		Log.d(TAG, "onPause");
	}

	/* 
	 * @see android.app.Activity#onStop()
	 */
	@Override
	protected void onStop() {
		super.onStop();
		Log.d(TAG, "onStop");
	}

	/* 
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroy");
	}

	/* ********************************************************************* */

	/* *********************************************************************
	 * 
	 * UI functions
	 */

	/**
	 * Setup views.
	 */
	private void setupViews() {
		Log.d(TAG, "setupViews");
		setContentView(R.layout.activity_vat_checker);

		searchField = (EditText) findViewById(R.id.searchField);

		vat_value = (TextView) findViewById(R.id.vat_value);
		tax_office_code_value = (TextView) findViewById(R.id.tax_office_code_value);
		tax_office_description_value = (TextView) findViewById(R.id.tax_office_description_value);
		vat_indication_value = (TextView) findViewById(R.id.vat_indication_value);
		name_value = (TextView) findViewById(R.id.name_value);
		title_value = (TextView) findViewById(R.id.title_value);
		address_value = (TextView) findViewById(R.id.address_value);
		number_value = (TextView) findViewById(R.id.number_value);
		zip_code_value = (TextView) findViewById(R.id.zip_code_value);
		area_value = (TextView) findViewById(R.id.area_value);
		start_date_value = (TextView) findViewById(R.id.start_date_value);
		end_date_value = (TextView) findViewById(R.id.end_date_value);
		telephone_value = (TextView) findViewById(R.id.telephone_value);
		fax_value = (TextView) findViewById(R.id.fax_value);
		activity_value = (TextView) findViewById(R.id.activity_value);
		activity_description_value = (TextView) findViewById(R.id.activity_description_value);
	}

	/**
	 * Update views.
	 */
	private void updateViews() {
		Log.d(TAG, "updateViews");

		switch (message.errorCode) {
		case RG_NOT_INDIVIDUAL_NF:
			Toast.makeText(VatCheckerActivity.this, message.errorDescr,
					Toast.LENGTH_SHORT).show();
			return;
		case RG_WRONG_AFM:
			Toast.makeText(VatCheckerActivity.this, message.errorDescr,
					Toast.LENGTH_SHORT).show();
			return;
		case UNKNOWN_ERROR:
			Toast.makeText(VatCheckerActivity.this, message.errorDescr,
					Toast.LENGTH_SHORT).show();
			return;
		}

		if (!TextUtils.isEmpty(message.vat))
			vat_value.setText(message.vat);
		else
			vat_value.setText(R.string.empty);

		if (!TextUtils.isEmpty(message.tax_office_code))
			tax_office_code_value.setText(message.tax_office_code);
		else
			tax_office_code_value.setText(R.string.empty);

		if (!TextUtils.isEmpty(message.tax_office_description))
			tax_office_description_value
					.setText(message.tax_office_description);
		else
			tax_office_description_value.setText(R.string.empty);

		if (!TextUtils.isEmpty(message.vat_indication))
			vat_indication_value.setText(message.vat_indication);
		else
			vat_indication_value.setText(R.string.empty);

		if (!TextUtils.isEmpty(message.name))
			name_value.setText(message.name);
		else
			name_value.setText(R.string.empty);

		if (!TextUtils.isEmpty(message.title))
			title_value.setText(message.title);
		else
			title_value.setText(R.string.empty);

		if (!TextUtils.isEmpty(message.address))
			address_value.setText(message.address);
		else
			address_value.setText(R.string.empty);

		if (!TextUtils.isEmpty(message.address_no))
			number_value.setText(message.address_no);
		else
			number_value.setText(R.string.empty);

		if (!TextUtils.isEmpty(message.zip_code))
			zip_code_value.setText(message.zip_code);
		else
			zip_code_value.setText(R.string.empty);

		if (!TextUtils.isEmpty(message.area))
			area_value.setText(message.area);
		else
			area_value.setText(R.string.empty);

		if (!TextUtils.isEmpty(message.start_date))
			start_date_value.setText(message.start_date);
		else
			start_date_value.setText(R.string.empty);

		if (!TextUtils.isEmpty(message.end_date))
			end_date_value.setText(message.end_date);
		else
			end_date_value.setText(R.string.empty);

		if (!TextUtils.isEmpty(message.telephone))
			telephone_value.setText(message.telephone);
		else
			telephone_value.setText(R.string.empty);
		if (!TextUtils.isEmpty(message.fax))
			fax_value.setText(message.fax);
		else
			fax_value.setText(R.string.empty);

		if (!TextUtils.isEmpty(message.activity))
			activity_value.setText(message.activity);
		else
			activity_value.setText(R.string.empty);

		if (!TextUtils.isEmpty(message.activity_description))
			activity_description_value.setText(message.activity_description);
		else
			activity_description_value.setText(R.string.empty);

	}

	/* ********************************************************************* */

	/* *********************************************************************
	 * 
	 * UI Interaction functions
	 */

	/**
	 * On search btn clicked.
	 *
	 * @param v the v
	 */
	public void onSearchBtnClicked(View v) {
		final String keyphrase = searchField.getText().toString();
		if (keyphrase.length() != 9) {
			searchField.setError(getString(R.string.error));
		} else
			requestInfo(keyphrase);
	}

	/**
	 * On clear btn clicked.
	 *
	 * @param v the v
	 */
	public void onClearBtnClicked(View v) {
		searchField.setText("");
	}

	/* ********************************************************************* */

	/* *********************************************************************
	 * 
	 * Utility functions
	 */
	/**
	 * Show.
	 *
	 * @param c the c
	 */
	public static void show(Context c) {
		Log.d(TAG, "show");
		final Intent intent = new Intent(c, VatCheckerActivity.class);
		c.startActivity(intent);
	}

	/**
	 * Request info.
	 *
	 * @param keyphrase the keyphrase
	 */
	public void requestInfo(String keyphrase) {
		HttpPost httppost = new HttpPost(
				"https://www1.gsis.gr/wsgsis/RgWsBasStoixN/RgWsBasStoixNSoapHttpPort");

		try {
			InputStream inputStream = getResources().openRawResource(
					R.raw.gsisrequesttemplate);
			String envelope = convertInputStreamToString(inputStream);
			Formatter formatter = new Formatter();
			formatter.format(envelope, keyphrase);
			envelope = formatter.toString();

			StringEntity se = new StringEntity(envelope, HTTP.UTF_8);

			se.setContentType("text/xml");
			httppost.setEntity(se);

			HttpEntity entity = null;
			HttpResponse response = sClient.execute(httppost);
			InputStream in = null;

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				entity = response.getEntity();
				in = entity.getContent();
			}

			String retStr = convertInputStreamToString(in);

			AndroidXMLParser p = new AndroidXMLParser(retStr);
			message = p.parse();
			updateViews();

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Convert input stream to string.
	 *
	 * @param ginstream the ginstream
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static String convertInputStreamToString(InputStream ginstream)
			throws IOException {
		StringBuffer sb = new StringBuffer();

		if (ginstream != null) {
			InputStreamReader reader = new InputStreamReader(ginstream);
			BufferedReader in = new BufferedReader(reader);
			String readed;

			while ((readed = in.readLine()) != null) {
				sb.append(readed + "\n");
			}
			ginstream.close();
		}

		return sb.toString();
	}

	/* ********************************************************************* */
}