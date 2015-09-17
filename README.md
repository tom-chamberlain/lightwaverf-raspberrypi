# lightwaverf-raspberrypi
Java standalone app to run LightwaveRF devices from rapsberry PI

This application is currently in development. It uses a schedule to set devices on or off,
so that you can say outside light 80% on at 7AM off at 9AM. It also takes into account sunrise
and sunset, so that if sunrise is before 7AM, it doesn't bother putting on the lights etc.

The LightwaveRF module must pair with the raspberry pi or QNAP or whatever is running the code.
Do this by running the jar with "init" as a parameter which will send the code to pair. Then click
yes on the LightwaveRF box itself.
