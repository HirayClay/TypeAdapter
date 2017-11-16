package com.hiray.typeadapter.event;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;


public class Bus {

    private static Bus eventBus;
    private Subject<Object, Object> mapping = new SerializedSubject<>(PublishSubject.create());

    public static Bus getInstance() {
        if (eventBus == null)
            synchronized (Bus.class) {
                eventBus = new Bus();
            }

        return eventBus;

    }

    public void post(Object event) {
        mapping.onNext(event);
    }

    //notice : do remember to unSubscribe
    @SuppressWarnings("unchecked")
    public Subscription register(Action1 action) {
        return mapping.observeOn(AndroidSchedulers.mainThread()).subscribe(action);
    }

}
