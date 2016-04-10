package com.wang.xiaoke.fileexplorer.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2016/4/7.
 */
public class FileUtils {

    public static Intent openFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) return null;
        /* 取得扩展名 */
        String end = file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length()).toLowerCase();
        /* 依扩展名的类型决定MimeType */
        if (end.equals("m4a") || end.equals("mp3") || end.equals("mid") || end.equals("m3u") || end.equals("ogg")
                || end.equals("xmf") || end.equals("ogg") || end.equals("wav") || end.equals("m4b") || end.equals("mp2")
                || end.equals("m4p") || end.equals("wav") || end.equals("wma") || end.equals("wmv")) {
            return getAudioFileIntent(filePath);
        } else if (end.equals("3gp") || end.equals("mp4") || end.equals("avi") || end.equals("asf")
                || end.equals("m4v") || end.equals("m4u") || end.equals("mov")
                || end.equals("mpe") || end.equals("mpeg") || end.equals("mepg4") || end.equals("mpg")) {
            return getVideoFileIntent(filePath);
        } else if (end.equals("jpg") || end.equals("gif") || end.equals("png") ||
                end.equals("jpeg") || end.equals("bmp")) {
            return getImageFileIntent(filePath);
        } else if (end.equals("apk")) {
            return getApkFileIntent(filePath);
        } else if (end.equals("ppt")) {
            return getPptFileIntent(filePath);
        } else if (end.equals("xls")) {
            return getExcelFileIntent(filePath);
        } else if (end.equals("doc")) {
            return getWordFileIntent(filePath);
        } else if (end.equals("pdf")) {
            return getPdfFileIntent(filePath);
        } else if (end.equals("chm")) {
            return getChmFileIntent(filePath);
        } else if (end.equals("txt")) {
            return getTextFileIntent(filePath, false);
        } else {
            return getAllIntent(filePath);
        }
    }

    //Android获取一个用于打开APK文件的intent
    public static Intent getAllIntent(String param) {
        Intent intent = null;
        try {
            intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(android.content.Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(new File(param));
            intent.setDataAndType(uri, "*/*");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return intent;
    }

    //Android获取一个用于打开APK文件的intent
    public static Intent getApkFileIntent(String param) {

        Intent intent = null;
        try {
            intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(android.content.Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(new File(param));
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return intent;
    }

    //Android获取一个用于打开VIDEO文件的intent
    public static Intent getVideoFileIntent(String param) {
        Intent intent = null;
        try {
            intent = new Intent("android.intent.action.VIEW");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("oneshot", 0);
            intent.putExtra("configchange", 0);
            Uri uri = Uri.fromFile(new File(param));
            intent.setDataAndType(uri, "video/*");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return intent;
    }

    //Android获取一个用于打开AUDIO文件的intent

    public static Intent getAudioFileIntent(String param) {
        Intent intent = null;
        try {
            intent = new Intent("android.intent.action.VIEW");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("oneshot", 0);
            intent.putExtra("configchange", 0);
            Uri uri = Uri.fromFile(new File(param));
            intent.setDataAndType(uri, "audio/*");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return intent;
    }

    //Android获取一个用于打开Html文件的intent

    public static Intent getHtmlFileIntent(String param) {

        Intent intent = null;
        try {
            Uri uri = Uri.parse(param).buildUpon().encodedAuthority("com.android.htmlfileprovider").scheme("content").encodedPath(param).build();
            intent = new Intent("android.intent.action.VIEW");
            intent.setDataAndType(uri, "text/html");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return intent;
    }

    //Android获取一个用于打开图片文件的intent

    public static Intent getImageFileIntent(String param) {

        Intent intent = null;
        try {
            intent = new Intent("android.intent.action.VIEW");
            intent.addCategory("android.intent.category.DEFAULT");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = Uri.fromFile(new File(param));
            intent.setDataAndType(uri, "image/*");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return intent;
    }

    //Android获取一个用于打开PPT文件的intent

    public static Intent getPptFileIntent(String param) {
        Intent intent = null;
        try {
            intent = new Intent("android.intent.action.VIEW");
            intent.addCategory("android.intent.category.DEFAULT");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = Uri.fromFile(new File(param));
            intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return intent;
    }

    //Android获取一个用于打开Excel文件的intent

    public static Intent getExcelFileIntent(String param) {
        Intent intent = null;
        try {
            intent = new Intent("android.intent.action.VIEW");
            intent.addCategory("android.intent.category.DEFAULT");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = Uri.fromFile(new File(param));
            intent.setDataAndType(uri, "application/vnd.ms-excel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return intent;
    }

    //Android获取一个用于打开Word文件的intent

    public static Intent getWordFileIntent(String param) {
        Intent intent = null;
        try {
            intent = new Intent("android.intent.action.VIEW");
            intent.addCategory("android.intent.category.DEFAULT");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = Uri.fromFile(new File(param));
            intent.setDataAndType(uri, "application/msword");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return intent;
    }

    //Android获取一个用于打开CHM文件的intent
    public static Intent getChmFileIntent(String param) {
        Intent intent = null;
        try {
            intent = new Intent("android.intent.action.VIEW");
            intent.addCategory("android.intent.category.DEFAULT");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = Uri.fromFile(new File(param));
            intent.setDataAndType(uri, "application/x-chm");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return intent;
    }

    //Android获取一个用于打开文本文件的intent
    public static Intent getTextFileIntent(String param, boolean paramBoolean) {
        Intent intent = null;
        try {
            intent = new Intent("android.intent.action.VIEW");
            intent.addCategory("android.intent.category.DEFAULT");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (paramBoolean) {
                Uri uri1 = Uri.parse(param);
                intent.setDataAndType(uri1, "text/plain");
            } else {
                Uri uri2 = Uri.fromFile(new File(param));
                intent.setDataAndType(uri2, "text/plain");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return intent;
    }

    //Android获取一个用于打开PDF文件的intent

    public static Intent getPdfFileIntent(String param) {
        Intent intent = null;
        try {
            intent = new Intent("android.intent.action.VIEW");
            intent.addCategory("android.intent.category.DEFAULT");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = Uri.fromFile(new File(param));
            intent.setDataAndType(uri, "application/pdf");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return intent;
    }

    //防崩溃
    public static boolean isIntentAvailable(Context context, Intent intent) {
        final PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
                PackageManager.GET_ACTIVITIES);
        return list.size() > 0;
    }

    public static boolean isIntentAvailable(Context context, String action) {
        final PackageManager packageManager = context.getPackageManager();
        final Intent intent = new Intent(action);
        List<ResolveInfo> resolveInfo =
                packageManager.queryIntentActivities(intent,
                        PackageManager.MATCH_DEFAULT_ONLY);
        if (resolveInfo.size() > 0) {
            return true;
        }
        return false;
    }
}
