package com.wang.xiaoke.fileexplorer;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.wang.xiaoke.fileexplorer.Utils.OpenFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends Activity {

    private Adapter adapter;
    private ListView listView;
    private TextView path_one;
    private TextView hintMessage;
    private ImageButton backButton;
    private ImageButton deleteButton;
    private ImageButton allButton;
    private Spinner indexButton;
    ArrayList<Entity> list = new ArrayList<>();
    //记录drawable，避免多次获取drawable

    private boolean isCaseDot = true;//是否忽略以点开头的
    private boolean isLong = false;//是否被长按
    private boolean isAll = false;//是否被全选
    private int sortState = 0;//0升序、1降序
    private boolean isOrder = true;//true顺序，false 逆序

    //记录根文档
    File root;
    //记录当前的父文件夹
    File currentParent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getInitialize();//初始化

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            try {
                if (!currentParent.getCanonicalPath().equals("/")) {
                    backButtonClick();
                } else {
                    return super.onKeyDown(keyCode, event);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    private void getInitialize() {
        listView = (ListView) findViewById(R.id.list);
        path_one = (TextView) findViewById(R.id.path_one);
        hintMessage = (TextView) findViewById(R.id.hint_message);
        backButton = (ImageButton) findViewById(R.id.back_button);
        deleteButton = (ImageButton) findViewById(R.id.delete_button);
        allButton = (ImageButton) findViewById(R.id.all_button);
        indexButton = (Spinner) findViewById(R.id.index_button);

        indexClick();//设置spinner

        listView.setOnItemClickListener(new itemClick());
        listView.setOnItemLongClickListener(new itemLongClick());
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backButtonClick();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteButtonClick();
            }
        });
        allButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allCheck();
            }
        });
        indexButton.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //记录上次的位置
                SharedPreferences settings = getSharedPreferences("spinner", MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putInt("Position", position);
                editor.commit();
                sortState = position;//选择排序模式
                File[] files = currentParent.listFiles();
                if (files != null) {
                    InflaterListView(files);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //获取系统的SD卡的目录
        root = Environment.getExternalStorageDirectory();
        if (root != null) {
            currentParent = root;
            setPath(currentParent);
            File[] currentFiles = root.listFiles();
            // 使用当前目录下的全部文件、文件夹来填充ListView
            InflaterListView(currentFiles);
        }
    }

    private void hideButton(boolean method) {
        if (method) {
            hintMessage.setVisibility(View.GONE);
            deleteButton.setVisibility(View.VISIBLE);
            allButton.setVisibility(View.VISIBLE);
            indexButton.setVisibility(View.GONE);
            backButton.setVisibility(View.GONE);
        } else {
            listView.setVisibility(View.VISIBLE);
            hintMessage.setVisibility(View.GONE);
            deleteButton.setVisibility(View.GONE);
            allButton.setVisibility(View.GONE);
            indexButton.setVisibility(View.VISIBLE);
            backButton.setVisibility(View.VISIBLE);
        }
    }

    //设置listView
    private void InflaterListView(File[] files) {
        list.clear();
        try {
            for (int i = 0; i < files.length; i++) {
                if (isCaseDot) {//如果设置了忽略以"."开头的文件
                    if (files[i].getName().substring(0, 1).equals(".")) {
                        continue;
                    }
                }
                Entity entity = new Entity();
                entity.setFileName(files[i].getName());
                entity.setPath(files[i].getCanonicalFile());
                if (files[i].isFile()) {
                    entity.setState(0);
                } else {
                    entity.setState(1);
                }
                list.add(entity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //将文件排序
        Collections.sort(list, new Comparator<Entity>() {
            @Override
            public int compare(Entity lhs, Entity rhs) {
                if (sortState == 0) {
                    if (lhs == null || rhs == null) {//先比较为空的情况
                        if (lhs == null) {
                            return -1;
                        } else {
                            return 1;
                        }
                    } else {
                        if (lhs.getState() == 0 && rhs.getState() == 1) {
                            return 1;
                        } else if (lhs.getState() == 1 && rhs.getState() == 0) {
                            return -1;
                        } else return lhs.getFileName().compareToIgnoreCase(rhs.getFileName());
                    }
                } else {
                    if (lhs == null || rhs == null) {//先比较为空的情况
                        if (lhs == null) {
                            return 1;
                        } else {
                            return -1;
                        }
                    } else {
                        if (lhs.getState() == 0 && rhs.getState() == 1) {
                            return -1;
                        } else if (lhs.getState() == 1 && rhs.getState() == 0) {
                            return 1;
                        } else return rhs.getFileName().compareToIgnoreCase(lhs.getFileName());
                    }
                }
            }
        });

//        for (int i = 0; i < list.size(); i++) {
//            Log.e("ddddd", "list的第" + i + "位的名字为：" + list.get(i).getFileName());
//        }
        adapter = new Adapter(this, list);
        listView.setAdapter(adapter);
    }

    //设置path，暂时显示路径
    private void setPath(File currentPath) {
        if (currentPath != null) {
            try {
                path_one.setText("当前路径为："
                        + currentPath.getCanonicalPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class itemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (isLong) {//处于选择状态
                if (list.get(position).getIsCheck()) {//如果已经被选中
                    list.get(position).setIsCheck(false);
                } else {//没被选中变为选中
                    list.get(position).setIsCheck(true);//将要被删除的item设置为true
                }
//                Log.e("ccccc", position + "被标记或被取消标记");
                adapter.notifyDataSetChanged();//通知adapter改变
            } else {//打开文件或文件夹
                currentParent = list.get(position).getPath();
                // 用户单击了文件，调用打开文件的app
                if (currentParent.isFile()) {
//                方法一：
//                FileUtils fileUtils = new FileUtils();
//                Intent intent=fileUtils.openFile(currentParent.getPath());
//                if(intent == null){
//                    Toast.makeText(MainActivity.this,"文件不存在",Toast.LENGTH_SHORT).show();
//                }else {
//                    boolean flag = fileUtils.isIntentAvailable(MainActivity.this, intent);
//                    if (flag) {
//                        startActivity(intent);
//                    } else {
//                        Toast.makeText(MainActivity.this, "无可打开文件的应用", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                方法二：
                    OpenFile openFiles = new OpenFile();
                    openFiles.openFile(MainActivity.this, currentParent);
                    return;
                }

                // 获取用户点击的文件夹下的所有文件
                File[] tmp = currentParent.listFiles();
                if (tmp == null || tmp.length == 0) {
                    //显示空文件夹
                    setPath(currentParent);
                    hintMessage.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.GONE);
                } else {
                    hintMessage.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    // 获取用户单击的列表项对应的文件夹，设为当前的父文件夹
                    setPath(currentParent);
                    // 保存当前的父文件夹内的全部文件和文件夹
                    // 再次更新ListView
                    InflaterListView(tmp);
                }
            }
        }
    }

    private class itemLongClick implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            list.get(position).setIsCheck(true);//将要被删除的item设置为true
//            Log.e("bbbbb", position + "被标记");
            adapter.notifyDataSetChanged();
            isLong = true;
            hideButton(isLong);
            return true;
        }
    }

    private void allCheck() {
        if (list != null) {
            isAll = true;
            for (int i = 0; i < list.size(); i++) {
                if (!list.get(i).getIsCheck()) {
                    isAll = false;
                }
            }
        }
        if (!isAll) {//没有全被选中
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setIsCheck(true);
            }
            adapter.notifyDataSetChanged();
        } else {
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setIsCheck(false);
            }
            adapter.notifyDataSetChanged();
        }
    }

    private void backButtonClick() {
        try {
            if (isLong) {
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setIsCheck(false);
                }
                adapter.notifyDataSetChanged();
            } else if (!currentParent.getCanonicalPath().equals("/")) {
                // 获取上一级目录
                currentParent = currentParent.getParentFile();
                setPath(currentParent);
                // 列出当前目录下所有文件
                File[] tmp = currentParent.listFiles();
                // 再次更新ListView
                InflaterListView(tmp);
            }
            isLong = false;
            hideButton(isLong);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteButtonClick() {
        if (list != null) {
            int len = list.size();
//            //正版
//            for (int i = 0; i < len; ) {
//                if (list.get(i).getIsCheck()) {
//                    list.get(i).getPath().delete();
//                    list.remove(i);
//                    len--;
//                } else {
//                    i++;
//                }
//            }
//            //注释版本
            int j = 0;
            for (int i = 0; i < len; j++) {
                if (list.get(i).getIsCheck()) {//这里要注意，remove后list.size会改变
                    Log.e("aaaaa", j + "被标记");
                    list.get(i).setIsCheck(false);
//                    list.get(i).getPath().delete();//删除文件，测试的时候我把它注释掉，以免删除不该删的
                    list.remove(i);//从list移除，并没有真正的删除文件
                    len--;
                } else {
                    i++;
                }
            }
            isLong = false;
            adapter.notifyDataSetChanged();
            hideButton(isLong);
        }
    }

    private void indexClick() {
        String[] arr = {"升序", "降序"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_multiple_choice, arr);
        indexButton.setAdapter(spinnerAdapter);
        SharedPreferences shared = getSharedPreferences("spinner", MODE_PRIVATE);
        int position = shared.getInt("Position", 0);
        indexButton.setSelection(position);
    }

}