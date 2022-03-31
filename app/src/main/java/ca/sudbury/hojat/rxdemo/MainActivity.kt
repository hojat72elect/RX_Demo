package ca.sudbury.hojat.rxdemo

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable


class MainActivity : AppCompatActivity() {

    private val TAG = "RxJavaDemo"
    private val greeting = "Hello From RxJava" // imagine this is a data stream
    private lateinit var myObservable: Observable<String> // The Observable object
    private lateinit var myObserver: Observer<String> // The Observer object

    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.tv_Greetings)

        // Making an Observable<String> object out of the String we have
        // provided to it.
        myObservable = Observable.just(greeting)

        myObserver = object : Observer<String> { // An object that complies to Observer interface.

            override fun onSubscribe(d: Disposable) {
                Log.i(TAG, "onSubscribe() function was invoked")
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
}