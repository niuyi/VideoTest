package com.niuyi.test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class VideoTestActivity extends Activity {
	private ListView listView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.main);
		listView = (ListView) findViewById(R.id.listView1);
		File root = Environment.getRootDirectory();
		setTitle(root.getAbsolutePath());
		listView.setAdapter(new FileListAdapter(this, getFileAdapters(new File(
				"/"))));

		listView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				IFileAdapter file = (IFileAdapter) view.getTag();
				if (file.getNextList() == null)
					return;

				listView.setAdapter(new FileListAdapter(VideoTestActivity.this,
						file.getNextList()));
				setTitle(file.getPath());
				listView.invalidate();
			}
		});
	}

	public List<IFileAdapter> getFileAdapters(File dir) {
		List<IFileAdapter> results = new ArrayList<IFileAdapter>();
		File[] listFiles = dir.listFiles();
		if (listFiles == null) {
			return results;
		}

		for (File f : listFiles) {
			results.add(new RealFileAdapter(f));
		}
		return results;
	}

	class FileListAdapter extends BaseAdapter {

		private final Context context;
		private List<IFileAdapter> files = new ArrayList<IFileAdapter>();

		public FileListAdapter(Context context, List<IFileAdapter> files) {
			this.context = context;
			if (files != null) {
				this.files = files;
			}
		}

		public int getCount() {
			// TODO Auto-generated method stub
			return files.size();
		}

		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return files.get(position);
		}

		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			TextView textView = new TextView(context);
			IFileAdapter file = files.get(position);
			textView.setText(file.getText());
			textView.setTag(file);
			return textView;
		}
	}

	interface IFileAdapter {
		public String getText();

		public List<IFileAdapter> getNextList();

		public String getPath();
	}

	class UpFileAdapter implements IFileAdapter {
		private final File parent;

		public UpFileAdapter(File parent) {
			this.parent = parent;
		}

		@Override
		public String getText() {
			return "..";
		}

		@Override
		public List<IFileAdapter> getNextList() {
			List<IFileAdapter> result = new ArrayList<IFileAdapter>();
			if (parent.getParentFile() != null) {
				result.add(new UpFileAdapter(parent.getParentFile()));
			}

			result.addAll(getFileAdapters(parent));
			return result;
		}

		@Override
		public String getPath() {
			return parent.getAbsolutePath();
		}
	}

	class RealFileAdapter implements IFileAdapter {
		private final File file;

		public RealFileAdapter(File file) {
			this.file = file;
		}

		public String getText() {
			if (file.isDirectory()) {
				return "/" + file.getName();
			}
			return file.getName();
		}

		public List<IFileAdapter> getNextList() {
			if (file.isDirectory()){
				List<IFileAdapter> result = new ArrayList<IFileAdapter>();
				result.add(new UpFileAdapter(file.getParentFile()));
				result.addAll(getFileAdapters(file));
				return result;
			}
			return null;
		}

		@Override
		public String getPath() {
			return file.getAbsolutePath();
		}
	}
}