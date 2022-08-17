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

Parking permits for the Orinda BART parking lot sell out sixty days in advance, by 7:00 AM. In early 2018, I developed Permitter on a now-retired [MacBook Pro](https://motherboard.vice.com/en_us/article/53db3a/the-2012-non-retina-macbook-pro-is-still-the-best-laptop-apple-sells) to automate parking-permit purchases for my wife. From then until March, 2020, I ran Permitter daily on my [Windows PC](https://www.intel.com/content/www/us/en/products/boards-kits/nuc.html) using [Task Scheduler](https://docs.microsoft.com/en-us/windows/desktop/taskschd/task-scheduler-start-page). Five days a week, new permits magically appeared in my wife's Select-a-Spot account.

The Permitter stack includes Java, IntelliJ, Selenium WebDriver, and Firefox. I used [Katalon Automation Recorder](https://www.katalon.com/resources-center/blog/katalon-automation-recorder/) to observe interactions with the parking-website DOM. I strongly endorse that product.

Enjoy a [video](https://vimeo.com/250967769) of Permitter in action.

### Use

Permitter requires a credentials file called `credentials` by default. This file should have a BART-parking username and password with the format `username,password`.

Permitter can exclude holidays, vacations, and particular days of the week from purchase. See `exclude.xml` for how. (Though I am comfortable with JSON, I prefer the verbosity of XML.) You should modify or delete that file before running Permitter, as you probably don't want the exclusions currently in `exclude.xml`.

Permitter has three optional launch arguments:
* `credentialsFile=VALUE`: the file to get credentials from; default value: `credentials`
* `logFile=VALUE`: the file to log output to; default value: `logfile.txt`
* `excludeFile=VALUE`: the file to get exclusions from; default value: `exclusions.xml`

Permitter's repo has the MacOS Firefox Selenium WebDriver, `geckodriver`, but for other operating systems or browsers, use the appropriate driver. For example, if you are running Firefox on Windows, replace the string `geckodriver` in `Permitter.java` with `geckodriver.exe`.

As of April 6, 2019, Permitter worked with the following versions:

* IntelliJ IDEA: 2019.1
* JDK: 8, Update 201
* Selenium WebDriver for Java: 3.141.59
* geckodriver: 0.24.0
* MacOS: 10.14.4
* Windows: 10.0.17763.404
* Firefox: 66.0.2

### Technical Notes

`Permitter.java` has the `main` method.

The Safari and Chrome Selenium WebDrivers did not work for me, which is why I used the Firefox Selenium WebDriver. YMMV.

In my initial efforts to solve the purchasing problem, I tried [Permitter](https://github.com/jeffkowalski/permitter), a Ruby app. I ultimately decided to implement something else because I am not fond of Ruby's `unless` keyword. That said, I thank [Permitter](https://github.com/jeffkowalski/permitter)'s developer, Jeff Kowalski, for demonstrating that this automation is possible.

As a Swift developer, I was inclined to use [WKZombie](https://github.com/mkoehnke/WKZombie), but I could not get forms to work, perhaps because of this [issue](https://github.com/mkoehnke/WKZombie/issues/76).
