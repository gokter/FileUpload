package com.flyingh.fileupload;

import java.io.File;
import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";
	private EditText editText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		editText = (EditText) findViewById(R.id.file_name);
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD) {
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().build());
		}
	}

	public void upload(View view) {
		try {
			PostMethod post = new PostMethod("http://10.1.79.23:8080/News/UploadServlet");
			Part[] parts = new Part[] { new FilePart(editText.getText().toString(), new File(Environment.getExternalStorageDirectory(), editText
					.getText().toString())) };
			post.setRequestEntity(new MultipartRequestEntity(parts, post.getParams()));
			if (new HttpClient().executeMethod(post) == HttpStatus.SC_OK) {
				Toast.makeText(getApplicationContext(), R.string.upload_success, Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(getApplicationContext(), R.string.upload_fail, Toast.LENGTH_LONG).show();
			}
		} catch (IOException e) {
			Log.i(TAG, e.getMessage());
			Toast.makeText(getApplicationContext(), R.string.upload_fail, Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
