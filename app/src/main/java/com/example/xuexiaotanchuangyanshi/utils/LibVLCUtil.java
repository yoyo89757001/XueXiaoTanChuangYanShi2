//package com.example.xuexiaotanchuangyanshi.utils;
//
//import android.content.Context;
//
//import org.videolan.libvlc.LibVLC;
//
//
///**
// * Created by Brooks on 2015-12-31.
// * LibVLCUtil LibVLC 单例
// */
//public class LibVLCUtil {
//    private static LibVLC libVLC = null;
//
//    public synchronized static LibVLC getLibVLC(Context context) throws IllegalStateException {
//        if (libVLC == null) {
//
//                libVLC = new LibVLC(context);
//
//        }
//        return libVLC;
//    }
//
//}