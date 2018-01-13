Permitter
=========

[![GitHub Stars](https://img.shields.io/github/stars/badges/shields.svg?style=social&label=Star)]()
[![GitHub closed issues](https://img.shields.io/github/issues-closed/badges/shields.svg)]()
[![Twitter](https://img.shields.io/badge/twitter-@vermont42-blue.svg?style=flat)](http://twitter.com/vermont42)
[![license](https://img.shields.io/github/license/mashape/apistatus.svg)]()
[![cat](https://img.shields.io/badge/cat-friendly-blue.svg)](https://twitter.com/vermont42/status/784504585243078656)

<a href="url"><img src="BART.gif" align="center" height="200"></a>
<br />

Parking permits for the Orinda BART parking lot sell out sixty days in advance, by 7:00 AM. To automate interacting with the permit-purchasing website, I made Permitter. Technologies used include Java, IntelliJ, Selenium WebDriver, and Katalon Automation Recorder.

There is a full [video](https://vimeo.com/250967769) of Permitter in action.

Although I developed Permitter on my MacBook Pro, I run this app on my Windows PC using Task Scheduler.

*Technical Notes*

The Safari Selenium WebDriver did not work, so I used the Firefox WebDriver.

In my initial efforts to solve the purchasing problem, I tried [Permitter](https://github.com/jeffkowalski/permitter), a Ruby app. I ultimately decided to implement something else because I am not fond of Ruby's syntax. That said, I thank [Permitter](https://github.com/jeffkowalski/permitter)'s developer, Jeff Kowalski, for demonstrating that this automation is possible.

As a Swift developer, I was inclined to use [WKZombie](https://github.com/mkoehnke/WKZombie), but I could not get forms to work, perhaps because of this [issue](https://github.com/mkoehnke/WKZombie/issues/76).
