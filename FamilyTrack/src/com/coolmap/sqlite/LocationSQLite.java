package com.coolmap.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocationSQLite  extends  SQLiteOpenHelper{

	public LocationSQLite(Context context) {
		super(context,"Location.db",null,1);
		// TODO Auto-generated constructor stub
	}
	//�����ݿ��һ�α�����ʱִ�У��紴������ʼ�����ݵ�
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		//������(id ��ʾΪ�������������ģ�Ҳ�ͱ���ÿ�β���ʱ��id�����ǲ�һ����)
		String   sql = "create table  Location (id integer primary key autoincrement, name varchar(20), latitude double , longtitude double) ";
		db.execSQL(sql);
	}

	//�����ݿ����ʱ��ɾ���ɱ������±�
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
}
