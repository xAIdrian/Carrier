To better understand how you approach Android development and write code, we would like you
to complete the following exercise to create and test a new Activity. It should take only a
couple hours. Please return your work within 3 calendar days. If you need special accommodation
please communicate that to your contact at HaulHub.

We will evaluate your code based on:
- Structure
- Maintainability
- Functional scalability
- Testability

If you have any questions, please ask. Thank you for your interest in HaulHub!

~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~

### EXERCISE:

The "Carrier" app is designed for truckers and fleet owners who are contracted to haul materials.
The code found in the Carrier folder is a basic template project created by Android Studio with an
added Activity that needs to be IMPLEMENTED and TESTED.  It also contains a Git repository that
should be used while developing.

The added Activity is a simplified version of a feature that shows details for a trucker's shift.
This screen makes use of multiple data sources, including a local database and a remote API,
to show the correct information on the screen. It also has a button to send a message  to the
trucker on the shift. (For simplicity's sake, there's no user input and it can always send the same
message, "Hello, World!"). Sending the message also makes use of the remote API.

*The remote API should be stubbed.*

You'll find three files in the root folder that contain needed information for each source:
- shift_table.txt = schema for the shift information that's stored locally
- shift_details_response.txt = response for a fake GET with endpoint,
  https://example.haulhub.com/shifts/{id}, where {id} would be the shift's ID
- send_message_shift_request.txt = request body for a fake POST with endpoint,
  https://example.haulhub.com/shifts/{id}/messages, where {id} would be the shift's ID


The information received from the stubbed GET API call should always be used, except for the
shift status. The status in the response may not be current due to asynchronous background
processing. If there is a matching shift stored locally, the status that's stored locally should be
used instead of what's returned by the API. The local data is the source of truth.

Use any libraries, patterns, etc. you'd like to create skeleton code for a testable,
maintainable, and functionally scalable implementation. Classes, properties, methods, and test
classes/methods should be added with stubbed implementations. Use comments to describe
implementations.

An example of a class:
```
class SomeClass {
  val someHelperProperty =
    if (checkSomething) {
      // do something then return
    } else {
      // do something else then return
    }

  fun doSomething(): Single<Response> {
    // do something first
    // then do something else
    // then return response
  }

  fun doSomethingElse(): Single<SomeModel> {
    // do some more stuff
    // return model
  }
}
```
### Final comments:
- choices for libraries/patterns/etc will not be taken into consideration, just how you use them
- you can't use any of the following from Android Jetpack: Architectural Components (View Model,
  Live Data, Saved State), Navigation Components

#### THINGS TO WRITE ABOUT
- how exactly does MVP work and it's benefits in this situation
- how are we using the interceptor and FAKE stubs
- why we are using the negative IDs to indicate an error and not a response pattern
- disposable being used in presenter
- Exactly how SQLiteOpenHelper works with

#### TODO FOR ME
- Databinding
- Create DAO for making our DB queries
