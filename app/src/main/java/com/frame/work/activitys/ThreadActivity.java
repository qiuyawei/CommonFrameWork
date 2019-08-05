package com.frame.work.activitys;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.SystemClock;
import android.renderscript.RenderScript;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.frame.work.R;
import com.frame.work.base.BaseActivity;
import com.frame.work.beans.ActivityBean;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class ThreadActivity extends BaseActivity {
    private String TAG = "ThreadActivity";
    private Handler mHandler;
    @BindView(R.id.btTestHandler)
    Button btTestHandler;

    @BindView(R.id.ivPic)
    ImageView ivPic;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private Thread thread;
    private HandlerThread handlerThread=new HandlerThread("threadCalculate", Process.THREAD_PRIORITY_BACKGROUND);
    private Handler mainHandler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            return false;
        }
    });
    @Override
    public int innitLayout() {
        return R.layout.activity_thread_test;
    }


    @Override
    protected void onStart() {
        super.onStart();
        handlerThread.start();
        mHandler=new Handler(handlerThread.getLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                SystemClock.sleep(6000);
                Log.i(TAG,"thradName:"+msg.what);
                //
                Log.i(TAG,(Looper.myLooper()==Looper.getMainLooper())+"");
                return false;
            }
        });
    }

    @OnClick({R.id.btTestHandler})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btTestHandler:
//                testThree();
//                startActivity(new Intent(ThreadActivity.this,ClipActivity.class));
                mHandler.sendEmptyMessage(100);
                Log.i(TAG,(Looper.myLooper()==Looper.getMainLooper())+"___");
                break;
        }

    }


    public void test() {
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.d("kaelpu", "Observable thread is : " + Thread.currentThread().getName());
                Log.d("kaelpu", "emitter 1");
                emitter.onNext(1);
            }
        });

        Consumer<Integer> consumer = new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.d("kaelpu", "Observer thread is :" + Thread.currentThread().getName());
                Log.d("kaelpu", "onNext: " + integer);
            }
        };

        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer);
    }

    public void testjust() {
        String[] names = {"haha", "hello", "end"};
        Observable.just(names).subscribe(new Consumer<String[]>() {
            @Override
            public void accept(String[] s) throws Exception {
//                        Toast.makeText(getmContext(),s,Toast.LENGTH_LONG).show();
                Snackbar.make(btTestHandler, s.length + "", Snackbar.LENGTH_LONG).show();

            }
        });
    }

    public void realWork() {
        disposables.add(getVa()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ActivityBean>() {
                    @Override
                    public void accept(ActivityBean s) throws Exception {
                        Log.i(TAG, "onNext:" + s.getActivityName());

//                        Snackbar.make(btTestHandler, s.getActivityName(), Snackbar.LENGTH_LONG).show();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i(TAG, "accept:" + throwable.getMessage());
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.i(TAG, "Action:");
                    }
                }));

    }


    public static Observable<ActivityBean> getVa() {
        /*return Observable.defer(new Callable<ObservableSource<? extends String>>() {
            @Override
            public ObservableSource<? extends String> call() throws Exception {
                int sum=0;
                for(int i=0;i<=100;i++){
                    sum+=i;
                }
                SystemClock.sleep(5000);
                return Observable.just(String.valueOf(sum));
            }
        });*/
        return Observable.create(new ObservableOnSubscribe<ActivityBean>() {
            @Override
            public void subscribe(ObservableEmitter<ActivityBean> emitter) throws Exception {
                ActivityBean activtyBean = new ActivityBean("MainActivity", "test");
//                emitter.onError(new Throwable());
                emitter.onNext(activtyBean);
                emitter.onComplete();
            }
        });
    }

    //map zhuanHuan
    public void testRxjavaMap() {
        Observable.just(R.mipmap.ic_launcher)
                .map(integer -> {
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), integer);
                    SystemClock.sleep(5000);
                    return bitmap;
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bitmap -> ivPic.setImageBitmap(bitmap));
    }


    //from test
    @SuppressLint("CheckResult")
    public void fromRxjava() {
        Observable.just(new int[]{1, 2, 3, 4, 5, 6})
                .map(new Function<int[], ArrayList<Integer>>() {
                    @Override
                    public ArrayList apply(int[] ints) throws Exception {
                        ArrayList<Integer> integers = new ArrayList<>();
                        for (int i = 0; i < ints.length; i++) {
                            if (i % 2 == 0) {
                                integers.add(i);
                            }
                        }
                        return integers;
                    }
                }).subscribe(new Consumer<ArrayList<Integer>>() {
            @Override
            public void accept(ArrayList<Integer> integers) throws Exception {
                Log.i(TAG, "size:" + integers.size());
            }
        });


        Observable.fromArray(new int[]{1, 2, 3, 4}).take(2)
                .subscribe(new Consumer<int[]>() {
                    @Override
                    public void accept(int[] ints) throws Exception {
                        Log.i(TAG, "leg:" + ints.length);
                    }
                });
    }

    private Looper looper;

    public void testThreadToMain() {
//        thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
////                SystemClock.sleep(10*1000);
//                Looper.prepare();
//                mHandler = new Handler(Looper.myLooper()
//                        , new Handler.Callback() {
//                    @Override
//                    public boolean handleMessage(@NonNull Message message) {
//                        Log.i(TAG, "message:" + message.what);
//                        return false;
//                    }
//                });
//                Looper.loop();
//            }
//        });
//        thread.start();


    }

    public void testThree(){
       /* Single.just("1")
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Throwable {

                    }
                });
*/
      /*  Single.just("abc").subscribe(
                new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        Snackbar.make(btTestHandler,s,Snackbar.LENGTH_LONG).show();
                    }
                });*/

      /*Single.create((SingleOnSubscribe<String>) emitter -> emitter.onSuccess("onSuccess")).subscribe((s, throwable) -> {
          Log.i(TAG,"s_"+s);
          Log.i(TAG,"throwable_"+throwable);

      });*/
      String[] afs={"123","adb","dddd"};
      Observable.create(new ObservableOnSubscribe<String>() {
          @Override
          public void subscribe(ObservableEmitter<String> emitter) throws Exception {
              emitter.onNext("1ppppWWW");
          }
      }).doOnNext(new Consumer<String>() {
          @Override
          public void accept(String s) throws Exception {
              Log.i(TAG,"s_"+s);

          }

      }).subscribe(new Observer<String>() {
                       @Override
                       public void onSubscribe(Disposable d) {
                           Log.i(TAG,"onSubscribe_"+d.isDisposed());

                       }

                       @Override
                       public void onNext(String s) {
                            Log.i(TAG,"onNext_"+s);
                       }

                       @Override
                       public void onError(Throwable e) {

                       }

                       @Override
                       public void onComplete() {
                           Log.i(TAG,"onComplete");

                       }
                   }
      );
    }

}
