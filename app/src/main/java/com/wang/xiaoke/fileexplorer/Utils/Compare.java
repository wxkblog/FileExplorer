package com.wang.xiaoke.fileexplorer.Utils;

import com.wang.xiaoke.fileexplorer.R;

import java.util.Comparator;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Administrator on 2016/4/9.
 */
public class Compare implements Comparator<Map<String ,Objects>> {
    @Override
    public int compare(Map<String, Objects> lhs, Map<String, Objects> rhs) {
        if (lhs == null || rhs == null) {//先比较为空的情况
            if (lhs == null) {
                return -1;
            } else {
                return 1;
            }
        } else {
            //然后比较文件夹
            if (lhs.get("icon").equals(R.drawable.folder) && rhs.get("icon").equals(R.drawable.folder)) {
                return lhs.get("fileName").toString().compareToIgnoreCase(rhs.get("fileName").toString());
            } else if (lhs.get("icon").equals(R.drawable.folder) && (!rhs.get("icon").equals(R.drawable.folder))) {
                return -1;
            } else if (!lhs.get("icon").equals(R.drawable.folder) && rhs.get("icon").equals(R.drawable.folder)) {
                return 1;
            } else {
                return lhs.get("fileName").toString().compareToIgnoreCase(rhs.get("fileName").toString());
            }
        }
    }
}
