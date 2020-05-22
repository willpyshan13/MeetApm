package com.android.okhttpsample.id;

/**
 * Created by hxd on 16/5/4.
 */
public class UniqueException extends Exception {

    public UniqueException(Throwable e) {
        super(e);
    }

    public UniqueException(String e) {
        super(e);
    }
}
