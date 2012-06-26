package com.niuyi.test;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FileListAdapter extends BaseAdapter {

	private List<File> mfiles = new ArrayList<File>();
	private final Context mContext;
	private final File mCurrent;
	private LayoutInflater inflater;

	public FileListAdapter(Context context, File currentDir) {
		this.mContext = context;
		this.mCurrent = currentDir;
		if (!isRootDir(currentDir)) {
			mfiles.add(currentDir.getParentFile());
		}
		mfiles.addAll(getFileList(currentDir));
		inflater = LayoutInflater.from(context);
	}

	private boolean isRootDir(File currentDir) {
		return currentDir.getPath().equals(".");
	}

	private List<File> getFileList(File current) {
		List<File> files = new ArrayList<File>();
		File[] listFiles = current.listFiles();

		if (listFiles != null) {
			for (File f : listFiles) {
				if (!isVideoFileOrDir(f))
					continue;
				files.add(f);
			}
		}
		Collections.sort(files, new FileComparator());
		return files;
	}

	private boolean isVideoFileOrDir(File f) {
		return f.isDirectory() || isVideoFile(f);
	}

	private boolean isVideoFile(File f) {
		return f.isFile()
				&& (f.getName().endsWith(".mp4") || f.getName()
						.endsWith(".3gp"));
	}

	public int getCount() {
		return mfiles.size();
	}

	public Object getItem(int position) {
		return mfiles.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_item, null);
		}
		ImageView iconView = (ImageView) convertView.findViewById(R.id.icon);
		TextView textView = (TextView) convertView.findViewById(R.id.text);

		File file = mfiles.get(position);
		iconView.setImageResource(
				isVideoFile(file) ? 
						R.drawable.videos_icon : R.drawable.folder_icon);
		textView.setText(getDisplayText(file));
		convertView.setTag(file);
		return convertView;
	}

	private String getDisplayText(File file) {
		if (isUpToParent(file)) {
			return "..";
		}
		return file.getName();
	}

	private boolean isUpToParent(File file) {
		return mfiles.indexOf(file) == 0 && !isRootDir(mCurrent);
	}

	class FileComparator implements Comparator<File> {

		public int compare(File lhs, File rhs) {
			if (lhs.isDirectory() && rhs.isFile()) {
				return 1;
			}

			if (lhs.isFile() && rhs.isDirectory()) {
				return -1;
			}

			return lhs.getName().compareTo(rhs.getName());
		}

	}

}
