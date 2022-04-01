package ca.sudbury.hojat.rxdemo

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers


class MainActivity : AppCompatActivity() {

    private val TAG = "RxJavaDemo"
    private val greeting = "Hello From RxJava" // imagine this is a data stream
    private lateinit var myObservable: Observable<String> // The Observable object
    private lateinit var myObserver: Observer<String> // The Observer object

    private lateinit var textView: TextView
    private lateinit var disposable: Disposable


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.tv_Greetings)
        // Making an Observable<String> object out of the String we have
        // provided to it.
        myObservable = Observable.just(greeting)

        //We'll put our schedulers and operators in between of Observable and Observer (just like how the architecture shows it in README.md)
        myObservable.subscribeOn(Schedulers.io()) // choosing the Scheduler
        /*
        * According to the line code above, from this point onwards,
        *  the data stream will be executed on the io thread. Observer
        *  will receive data through the io thread.   */

        myObservable.observeOn(AndroidSchedulers.mainThread())
        /*
        * According to the code line above, this data stream will move to main thread again; so we can use that data for UI operations. */

        myObserver = object : Observer<String> { // An object that complies to Observer interface.

            /**
             * onSubscribe() function gets called when the Observer firstly subscribes to the Observable.
             * Pay attention that there's an object of type "Disposable" in this callback function.
             */
            override fun onSubscribe(d: Disposable) {
                Log.i(TAG, "onSubscribe() function was invoked")


                disposable = d
            }

            /**
             * onNext() function gets called whenever the observable emits
             * new data (which is of type string in this case)
             */
            override fun onNext(t: String) {
                Log.i(TAG, "onNext() function was invoked")
                textView.text = t
            }

            /**
             * If any errors occurred in the data emission process, this
             * function will be called (and you will get a reference to the
             * cause of that error).
             */
            override fun onError(e: Throwable) {
                Log.i(TAG, "onError() function was invoked")
            }

            /**
             * After all the data emissions are finished, this function will
             * be called.
             */
            override fun onComplete() {
                Log.i(TAG, "onComplete() function was invoked")
            }

        }
        // the Observer should specifically subscribe to an observer before any of our implementation works.

        myObservable.subscribe(myObserver)

    }

    override fun onDestroy() {
        super.onDestroy()
        /*
        * When an Activity or Fragment is destroyed by the system, its "onDestroy()" method callback will be invoked.
        * And that's the best place for removing the subscription between Observer and Observable.*/
        disposable.dispose()
    }
}