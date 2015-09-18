# lightwaverf-raspberrypi

The in-built lightwaveRF app wasn't good enough for me. I found that sometimes the schedule wouldn't work, it would get confused, or it just couldn't do what I wanted it to.

So I built a small application to control the devices via UDP. In particular, it;

- Schedules on, off and dimmable events
- Broadcasts the on, off or dim command three times, in case one was not recieved by the device
- Allows for scheduled events to only be active on weekdays, rather than every day
- looks up the sunrise and sunset times, so that if I schedule an outside lamp to go on from 7am-8am (when I leave for work), it will only do this if the sun has not risen (as otherwise there is no point). Similar if I set lights to come on at 5pm for when I get home from work, if the sun has not set, it will delay this time, so as to not light the house in the summer months. 


The application was running on my raspberry Pi happily for months, but I've now moved it to my QNAP as that is always on.

The LightwaveRF module must pair with the raspberry pi or QNAP or whatever is running the java application.
Do this by running the jar with "init" as a parameter which will send the code to pair. Then click
yes on the LightwaveRF box itself.


Earthtools.org has gone offline recently, so I'll be looking to using an offline library for this. 

Additionally the entire codebase needs refactoring and I'll likely change it to include a simple webapp so that you can adjust the schedules via a phone browser instead of having to recompile (and at least get it in some xml first).
