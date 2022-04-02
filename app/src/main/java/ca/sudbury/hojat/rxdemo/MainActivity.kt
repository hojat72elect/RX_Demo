package ca.sudbury.hojat.rxdemo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers


class MainActivity : AppCompatActivity() {

    private val TAG = "RxJavaDemo"


    private val nums = arrayOf(2, 6, 5, 3, 4, 8, 5, 4, 2, 5, 4)// imagine this is a data stream

    private lateinit var myObservable: Observable<Int> // The Observable object

//        private lateinit var myObserver: Observer<String> // The Observer object

    // It's a customized version of Observer which makes disposing the observer a lot easier.
    private lateinit var myObserver: DisposableObserver<Int>
//    private lateinit var myObserver2: DisposableObserver<String>

//    private lateinit var textView: TextView
//    private lateinit var disposable: Disposable

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        textView = findViewById(R.id.tv_Greetings)
        // Making an Observable<String> object out of the String we have
        // provided to it.
        // The "just"
        myObservable = Observable.fromArray(*nums)

        //We'll put our schedulers and operators in between of Observable and Observer (just like how the architecture shows it in README.md)
//        myObservable.subscribeOn(Schedulers.io()) // choosing the Scheduler
        /*
        * According to the line code above, from this point onwards,
        *  the data stream will be executed on the io thread. Observer
        *  will receive data through the io thread.   */

//        myObservable.observeOn(AndroidSchedulers.mainThread())
        /*
        * According to the code line above, this data stream will move to main thread again; so we can use that data for UI operations. */

//        myObserver = object : Observer<String> { // An object that complies to Observer interface.
//
//            /**
//             * onSubscribe() function gets called when the Observer firstly subscribes to the Observable.
//             * Pay attention that there's an object of type "Disposable" in this callback function.
//             */
//            override fun onSubscribe(d: Disposable) {
//                Log.i(TAG, "onSubscribe() function was invoked")
//
//
//                disposable = d
//            }
//
//            /**
//             * onNext() function gets called whenever the observable emits
//             * new data (which is of type string in this case)
//             */
//            override fun onNext(t: String) {
//                Log.i(TAG, "onNext() function was invoked")
//                textView.text = t
//            }
//
//            /**
//             * If any errors occurred in the data emission process, this
//             * function will be called (and you will get a reference to the
//             * cause of that error).
//             */
//            override fun onError(e: Throwable) {
//                Log.i(TAG, "onError() function was invoked")
//            }
//
//            /**
//             * After all the data emissions are finished, this function will
//             * be called.
//             */
//            override fun onComplete() {
//                Log.i(TAG, "onComplete() function was invoked")
//            }
//
//        }
        // the Observer should specifically subscribe to an observer before any of our implementation works.

        /*
        * In this case, Instead of working with Observer<T> interface we're
        * working DisposableObserver<String> which is an abstract class.
        * Pay attention that in this case, we don't have an onSubscribe()
        * between our callbacks (it's because a DisposableObserver doesn't need that).*/
//        myObserver = object : DisposableObserver<String>() {
//            override fun onNext(t: String) {
//                Log.i(TAG, "onNext() function was invoked")
//                textView.text = t
//            }
//
//            override fun onError(e: Throwable) {
//                Log.i(TAG, "onError() function was invoked")
//            }
//
//            override fun onComplete() {
//                Log.i(TAG, "onComplete() function was invoked")
//            }
//        }


        // Adding (subscribing) an Observer to a CompositeDisposable class.
//        compositeDisposable.add(myObserver)

//        myObservable.subscribe(myObserver)

        /*
        * by chaining multiple function calls together, we have added 2
        * Schedulers to our Observable and then subscribed an Observer to
        * it. The method "subscribeWith()" returns an object of type
        * Observer, and in this case, since "myObserver" is a
        * DisposableObserver, the result of all of this line will be a
        * DisposableObserver which we pass to compositeDisposable.add()
        * function. */
        compositeDisposable.add(
            myObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getObserver())
        ) // so now our CompositeDisposable has a full-blown disposable observer

//        myObserver2 = object : DisposableObserver<String>() {
//            override fun onNext(t: String) {
//                Log.i(TAG, "onNext() function was invoked")
//                Toast.makeText(applicationContext, t, Toast.LENGTH_LONG).show()
//
//            }
//
//            override fun onError(e: Throwable) {
//                Log.i(TAG, "onError() function was invoked")
//            }
//
//            override fun onComplete() {
//                Log.i(TAG, "onComplete() function was invoked")
//            }
//        }

//        compositeDisposable.add(myObserver2)

//        myObservable.subscribe(myObserver2)

        /*
        * We have already */
//        compositeDisposable.add(
//            myObservable.subscribeWith(myObserver2)
//        )


    }

    override fun onDestroy() {
        super.onDestroy()
        /*
        * When an Activity or Fragment is destroyed by the system, its "onDestroy()" method callback will be invoked.
        * And that's the best place for removing the subscription between Observer and Observable.*/

//        disposable.dispose()

        // You can directly dispose a DisposableObserver<T> which is totally different from a normal Observer.
//        myObserver.dispose()

        // In this case we're disposing all observers manually (can cause problems if you forget to dispose one).
//        myObserver2.dispose()

        // In just one line of code, all subscriptions are disposed
//        compositeDisposable.clear()

    }

    /**
     * The code for making a disposable observer was taken out to a separate method in order to make everything more concise/modular.
     */
    fun getObserver(): DisposableObserver<Int> {

        myObserver = object : DisposableObserver<Int>() {
            override fun onNext(t: Int) {
                Log.i(TAG, "onNext() function was invoked - $t")

            }

            override fun onError(e: Throwable) {
                Log.i(TAG, "onError() function was invoked")
            }

            override fun onComplete() {
                Log.i(TAG, "onComplete() function was invoked")
            }
        }

        return myObserver
    }
}