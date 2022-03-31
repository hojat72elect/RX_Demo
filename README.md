### RX Demo

<a href="https://github.com/ReactiveX/RxJava">RxJava</a> is a JVM based library that helps in composing asynchronous and
event-based programs using observable sequences.

This repository is my template app for implementing RX java in an Android app.

----------------------------------

In <a href="https://en.wikipedia.org/wiki/Reactive_programming">Reactive programming</a> the consumer code blocks react
to the data as it comes in. <a href="https://reactivex.io/">ReactiveX</a> is a project which provides implementations
for this reactive programming concept in different programming languages and various platforms. All implementations of
ReactiveX libraries provide these patterns of reactive programming paradigm 👇👇
<img alt="reactive patterns" src="DocsAsset/reactive patterns.png" width="80%" />

**<a href="https://github.com/ReactiveX/Rxandroid">RxAndroid</a>** is just a layer on top
of **<a href="https://github.com/ReactiveX/RxJava">RxJava</a> which provides Android specific support.** For example, it
provides a "scheduler". RxAndroid can't replace RxJava, so we need to use both of them in our Android apps.

----------------------------------

### Observer Pattern

<img alt="observer pattern" src="DocsAsset/observer pattern.png"  />

**Observables** are instances
of <a href="http://reactivex.io/RxJava/3.x/javadoc/io/reactivex/rxjava3/core/Observable.html">
Observable</a> class and can emit data. You can sometimes get datastreams directly in the form of observables. For
example, when you're using Retrofit, you can ask your result to be an Observable object. You can also write some code to
convert an existing data stream into an Observable object.<br/><br/> **Observers** are classes that implement
the <a href="http://reactivex.io/RxJava/3.x/javadoc/io/reactivex/rxjava3/core/Observer.html">Observer</a> interface; and
they can consume data emitted by observables.<br/>
One Observable can have so many Observers. When an Observable emits data, that means there's at least one Observer which
is subscribed to that Observable. If there are not any subscribers, no data can be emitted.

----------------------------------

<img alt="observer pattern" src="DocsAsset/observer methods.png"  />

An Observer has 3 main methods:

1- onNext():
<br/>when an observable emits data, it firstly calls to the onNext() method of the Observers that have subscribed to it.

2- onComplete():
<br/>After finishing the data emission, Observable will call the onComplete() method of the Observer.

3- onError():
<br/>If any errors occurred in this operation, Observable will call the onError() method of the Observer. This way, with
the help of RxJava, we don't have to put try/catch block all over our code, all the errors will be handled in onError()
method.

There's also another function in some cases of Observers 👇👇

4- OnSubscribe():
<br/>This function gets called when Observable subscribes to the Observable.


----------------------------------

<img alt="scheduler" src="DocsAsset/scheduler.png"  />

Between an Observable and Observer, we can
have <a href="http://reactivex.io/RxJava/3.x/javadoc/io/reactivex/rxjava3/core/Scheduler.html">Schedulers</a> which help
us to handle multithreading in a nice way. Scheduler decides whether a particular part of code should run on the
MainThread or on a separate one. There are numerous schedulers available in RxJava but **Schedulers.io()** and
**AndroidSchedulers.mainThread()** are the most common schedulers used in Android world.

----------------------------------

<img alt="operator" src="DocsAsset/operator.png" width="30%" height="30%" />

Operators allow you to convert data streams before they're received by the observers; you can chain multiple Operators
together. There are so many operators available in RxJava.

### Map Operators:

<ol>
<li><a href="https://reactivex.io/documentation/operators/map.html">Map</a></li>
<li><a href="https://reactivex.io/documentation/operators/flatmap.html">FlatMap</a></li>
<li>SwitchMap</li>
</ol>

Another group of operators is:

<ol>
<li><a href="https://reactivex.io/documentation/operators/from.html">From</a></li>
<li><a href="https://reactivex.io/documentation/operators/just.html">Just</a></li>
<li><a href="https://reactivex.io/documentation/operators/range.html">Range</a></li>
</ol>
we also have: 

<ol>
<li><a href="https://reactivex.io/documentation/operators/debounce.html">Debounce</a></li>
<li><a href="https://reactivex.io/documentation/operators/buffer.html">Buffer</a></li>
</ol>

----------------------------------

There are numerous variables of observer pattern with specific Observable and Observer objects; for example:

<img alt="single-observer-pattern" src="DocsAsset/single-observer-pattern.png" width="50%" height="50%" />
<img alt="completable-observer-pattern" src="DocsAsset/completable-observer-pattern.png" width="50%" height="50%" />
<img alt="flowable-observer-pattern" src="DocsAsset/flowable-observer-pattern.png" width="50%" height="50%" />
<img alt="maybe-observer-pattern" src="DocsAsset/maybe-observer-pattern.png" width="50%" height="50%"/>

----------------------------------

<img alt="disposable" src="DocsAsset/Disposable.png" width="200" height="119"/>
<img alt="composite disposable" src="DocsAsset/CompositeDisposable.png" width="240" height="119"/>
<br/><a href="http://reactivex.io/RxJava/javadoc/io/reactivex/disposables/Disposable.html">Disposable</a> interface and <a href="http://reactivex.io/RxJava/javadoc/io/reactivex/disposables/CompositeDisposable.html">CompositeDisposable</a> class can be used to avoid memory leaks in reactive programming paradigm.

----------------------------------

<img alt="subscription" src="DocsAsset/subscription.png" width="60%" height="60%"/>

Each set of Observable and Observer connection is called a **Subscription**.
A <a href="http://reactivex.io/RxJava/javadoc/rx/Subscription.html">subscription</a> is an interface in this library and
can be viewed as a well managed data stream in our app.

----------------------------------

### Dependencies

In order to use RxJava and RxAndroid in your Android app, you need to add these dependencies to your build.gradle
scripts (If the latest versions are different from what comes here, the IDE itself will ask you to update the versions):

```
    // RxJava:
    implementation "io.reactivex.rxjava3:rxjava:3.1.4"
    // RxAndroid:
    implementation 'io.reactivex.rxjava3:rxandroid:3.0.0'
```

----------------------------------

### Concurrency and multithreading with Schedulers

One of the best advantages of Rxjava is its ability to easily schedule works and process result on various threads which
allows us to heavily optimize system performance

**Scheduler:**
RxJava solution for handling multithreading. You can imagine a Scheduler as a thread pool which manages one or more
threads.<br/> Whenever a Scheduler needs to execute a task, it will take out a thread from its pool and run the task on
that thread.

There are various types of Schedulers available in RxJava.
<ol>
<li>Schedulers.io()</li>
This scheduler can have a limitless thread pool. It's best suited for non CPU intensive tasks; such as database interactions, network communications, and local file system interactions.
<li>AndroidSchedulers.mainThread()</li>
The main thread for all the UI interactions in Android and where all the user interactions happen. Remember that this scheduler is provided by RxAndroid and not RxJava.
</ol>

**In the Android world, 90% of times we only use the 2 schedulers above.**

These are Some other Schedulers you have access to in RxJava:
<ol>
<li><b>Schedulers.newThread()</b></li>
This Scheduler creates a new thread for each unit of the work that has been scheduled. 
<li><b>Schedulers.single()</b></li>
This Scheduler has only a single thread which executes tasks one after another according to their order. 
<li><b>Schedulers.trampoline()</b></li>
This Scheduler executes all the given tasks in a first in first out (FIFO) fashion. This scheduler fits well for implementing recurring tasks. 
<li>Schedulers.from(Executor executor)</li>
This function creates and returns a customized Scheduler backed by a specific <a href="https://developer.android.com/reference/java/util/concurrent/Executor">executor</a>.
</ol>