package com.hiray.typeadapter.event;

/**
 * @author: CJJ
 * @date 2017/10/31
 */
public class BetNumberEvent {

    public int type;
    public int position;
    public String number;

    public BetNumberEvent(int type, int position, String number) {

        this.type = type;
        this.position = position;
        this.number = number;
    }
}
