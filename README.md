# lightwaverf-raspberrypi-qnap

The builtin lightwaveRF app wasn't good enough for me. I found that sometimes the schedule wouldn't work, it would get confused, or it just couldn't do what I wanted it to.

So I built a small application that I leave running on my QNAP. In particular, it;

  * Schedules devices to go on and off (or dim) at set times
  * Schedules events that should only happen on a weekday or weekend day
  * Schedules devices to turn on at random times within a period - e.g. will chose a random time each night between 2AM and 3AM to turn on a light to make it appear that someone is home and the lights are not on a timer
  * Uses sunrise times to only put on lights in the morning if the sun has not risen
  * Uses sunset times to delay turning on lights in the evening, to prevent wasting electricity
  * Broadcasts commands at least once so that signals aren't missed
  * Turns all devices off at specified times to ensure that nothing is left on when you are out or during the night


The application is written in as a maven project using Spring context injection.
