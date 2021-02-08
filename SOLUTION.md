# Solution
This document contains description of the solution for Ex1, including what, how and why has been done.


**FEATURES**
I have managed to cover all *User Story* points, as well as *Bonus Points*.

As an extra, I have prepared
- full State restore after process death
- *Favourites* feature, as it introduces some difficulty in data processing: a need to merge data from 2 different sources
- *refresh* button on "FAILED" screen

**STACK**
I have used a typical Android stack:
- Retrofit for networking
- ViewModel&SavedStateHandle for screen view model
- RxJava3 for state and threading management
- Timber for logs
- Hilt for dependency injection
- MockK&AssertJ for UTs

**CODE ARCHITECTURE**
I have implemented some sort of MVVM architecture that is quite close to MVI. ViewModel only exposes 2 Observables
a) viewState implemented by BehaviorSubject,
b) viewActions implemented by PublishSubject.

This way we can handle all typical requirements: a state that survives both config change and process death,
as well as transient actions, like showing a Snackbar, or navigating between screens.

The fact that viewActions is implemented by a PublishSubject introduces one drawback:
if a transient action happens after onStop, it won't be ever processed by the Fragment.
This could be addressed by either ReplaySubject+filter, or something like
(https://github.com/akarnokd/RxJavaExtensions#unicastworksubject), without a much change to the Consumer.
I keep PublishSubject for simplicity.

I know that both WWs and CodeQuests default architectures are more strictly "MVI" and are more Rx-centric.
I think it's always nice to try something a little different.

**VIEW DETAILS**
Please note the MVxView base interface/class. They are designed to completely handle the "V" part of MVx architecture,
including view inflate. By this design MVxView can be used as Fragment/Activity/Recycler Item view without a change.
It does not apply to Adapter, which is threaded as part of the MainScreenView. As a rule of thumb: anything that
causes view inflation should be MVxView. Adapter does not do that (on its own)

**MODEL DETAILS**
I have used the typical UseCase pattern. I plays nicely for FetchImagesUseCase.
I'm not that happy with the separation of GetFavsUseCase and SaveFavsUseCase, as they share the Result
type and Storage(as a Repository). Moreover, it seems more natural to test them together.
One of the many dogmatic-vs-pragmatic discussions.

**UNIT TESTS**
I have implemented UTs for all UseCases and ViewModel. I tend to use test-doubles rather than MockK,
except for Retrofit (as a showcase). I always try to keep test-data centralized (see, eg. FetchImagesUseCaseResults).
It always helped me to keep UTs nice&clean as the project grows. I'm used to AssertJ, so I used it here as well.





