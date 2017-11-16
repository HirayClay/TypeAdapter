package com.hiray.typeadapter.event;

/**
 * @author: CJJ
 * @date 2017/11/1
 */
public class ItemNotifyEvent {
    public int type;
    public int position;

    public ItemNotifyEvent(int type, int position) {
        this.type = type;
        this.position = position;
    }
}
