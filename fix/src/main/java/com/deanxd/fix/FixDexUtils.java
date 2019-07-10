package com.deanxd.fix;

import android.content.Context;

import com.deanxd.fix.utils.ArrayUtils;
import com.deanxd.fix.utils.FileUitls;
import com.deanxd.fix.utils.ReflectUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

public class FixDexUtils {

    private static final String DEX_DIR = "odex";
    private final static String DEX_SUFFIX = ".dex";
    private static final String DEX_UNZIP_DIR_NAME = "opt_dex";

    private static HashSet<File> mDexFiles = new HashSet<>();

    /**
     * 将修复包 复制到 私有目录里的临时文件夹odex
     */
    public static boolean copyFixFile(Context context, File newDexFile) {
        File targetFile = new File(context.getDir(DEX_DIR, Context.MODE_PRIVATE).getAbsolutePath() + File.separator + newDexFile.getName());
        if (targetFile.exists()) {
            boolean delete = targetFile.delete();
        }
        try {
            FileUitls.copyFile(newDexFile, targetFile);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static boolean loadFixFile(Context context) {

        try {
            // Dex文件目录（私有目录中，存在之前已经复制过来的修复包）
            File fileDir = context.getDir(DEX_DIR, Context.MODE_PRIVATE);

            File[] files = fileDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.getName().endsWith(DEX_SUFFIX) && !"classes.dex".equals(file.getName())) {
                        mDexFiles.add(file);
                    }
                }
            }

            String optimizedDirectory = fileDir.getAbsolutePath() + File.separator + DEX_UNZIP_DIR_NAME;

            File fOpt = new File(optimizedDirectory);
            if (!fOpt.exists()) {
                boolean mkdirs = fOpt.mkdirs();
            }

            for (File dexFile : mDexFiles) {
                DexClassLoader mClassLoader = new DexClassLoader(dexFile.getAbsolutePath(), optimizedDirectory,
                        null, context.getClassLoader());
                hotFix(context, mClassLoader);
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void hotFix(Context context, DexClassLoader mClassLoader) throws IllegalAccessException, NoSuchFieldException, ClassNotFoundException {
        // 获取系统PathClassLoader类加载器
        PathClassLoader pathLoader = (PathClassLoader) context.getClassLoader();

        // 获取自有的dexElements数组对象
        Object myDexElements = ReflectUtils.getDexElements(ReflectUtils.getPathList(mClassLoader));

        // 获取系统的dexElements数组对象
        Object systemPathList = ReflectUtils.getPathList(pathLoader);
        Object sysDexElements = ReflectUtils.getDexElements(systemPathList);

        // 合并成新的dexElements数组对象
        Object dexElements = ArrayUtils.combineArray(myDexElements, sysDexElements);

        // 重新赋值给系统的pathList属性  --- 修改了pathList中的dexElements数组对象
        ReflectUtils.setField(systemPathList, systemPathList.getClass(), dexElements);
    }

}
