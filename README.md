Permitter
=========

[![GitHub Stars](https://img.shields.io/github/stars/badges/shields.svg?style=social&label=Star)]()
[![GitHub closed issues](https://img.shields.io/github/issues-closed/badges/shields.svg)]()
[![Twitter](https://img.shields.io/badge/twitter-@vermont42-blue.svg?style=flat)](http://twitter.com/vermont42)
[![license](https://img.shields.io/github/license/mashape/apistatus.svg)]()
[![cat](https://img.shields.io/badge/cat-friendly-blue.svg)](https://twitter.com/vermont42/status/784504585243078656)

<a href="url"><img src="BART.gif" align="center" height="300"></a>
<br />

### Introduction

Parking permits for the Orinda BART parking lot sell out sixty days in advance, by 7:00 AM. To automate interacting with the permit-purchasing website, I made Permitter. Technologies used include Java, IntelliJ, Selenium WebDriver, and Katalon Automation Recorder.

There is a full [video](https://vimeo.com/250967769) of Permitter in action.

Although I developed Permitter on my MacBook Pro, I run this app on my Windows PC using Task Scheduler.

### Use

*Permitter.java* has the *main* method.

Permitter requires a credentials file called *credentials*. This file should have a BART-parking username and password with the format *username,password*.

Permitter can exclude holidays and vacations from purchase. See *exclude.xml* for how.

Permitter's repo has the MacOS Firefox Selenium WebDriver, *geckodriver*, but for other operating systems or browsers, use a different driver.

### Technical Notes

The Safari Selenium WebDriver did not work for me, which is why I used the Firefox Selenium WebDriver.

In my initial efforts to solve the purchasing problem, I tried [Permitter](https://github.com/jeffkowalski/permitter), a Ruby app. I ultimately decided to implement something else because I am not fond of Ruby's syntax. That said, I thank [Permitter](https://github.com/jeffkowalski/permitter)'s developer, Jeff Kowalski, for demonstrating that this automation is possible.

As a Swift developer, I was inclined to use [WKZombie](https://github.com/mkoehnke/WKZombie), but I could not get forms to work, perhaps because of this [issue](https://github.com/mkoehnke/WKZombie/issues/76).

### Future Plans

The credentials-reading code assumes that the credentials do not contain a comma. If my wife's credentials ever gain a comma, I might use XML for the credentials file, which would allow a comma.

Results of attempts to purchase permits currently go to a log file. Successfully purchased permits are also stored in BART's backend. For more-timely notice of results, however, I would like to send a push notification.
