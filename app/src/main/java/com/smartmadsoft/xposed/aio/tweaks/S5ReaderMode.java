package com.smartmadsoft.xposed.aio.tweaks;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class S5ReaderMode {

    public static void hook(final XC_LoadPackage.LoadPackageParam lpparam) {
        try {
            XposedHelpers.findAndHookConstructor("dalvik.system.PathClassLoader", lpparam.classLoader, String.class, ClassLoader.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    String path = (String) param.args[0];

                    if (path.equals("/system/framework/ssrm.jar")) {
                        // this method calls addPackage("com.google.android.apps.books");
                        XposedHelpers.findAndHookMethod("c.aB", (ClassLoader)param.thisObject, "eK", new XC_MethodHook() {
                            @Override
                            protected void afterHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                                XposedHelpers.callMethod(param.thisObject, "addPackage", "com.flyersoft.moonreader");
                                XposedHelpers.callMethod(param.thisObject, "addPackage", "com.flyersoft.moonreaderp");
                                XposedHelpers.callMethod(param.thisObject, "addPackage", "com.adobe.reader");
                            }
                        });
                    }
                }
            });
        } catch (Throwable t) {
            XposedBridge.log(t);
        }
    }
}