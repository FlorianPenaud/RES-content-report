Table of Contents

1. [Introduction](#intro)
2. [Version(s) Supported](#versions)
3. [Usage Instructions](#instruction)
4. [Details](#details)
5. [License information](#license)

Introduction<a name="intro"></a>
============

This repository provides an example to report ruleApps and rulesets contained in an IBM Rule Execution Server console.
You can import this sample into an existing IBM Rule Designer.

Version(s) Supported<a name="versions"></a>
====================

IBM ODM 8.6 or greater

Usage Instructions<a name="instruction"></a>
===================

1. Import project
<br>Right click in package explorer
<br>Select Import
<br>General > Existing projects into Workspace
<br>Select archive file then Browse to your sample archive

2. Start your Rule execution server

3. Run sample
<br>Right click on RES_content_report project
<br>Run As > Run configurations > new Java Application
<br>On Main tab :
<br>Project:RES_content_report
<br>Main class:sample.JavaClient
<br>On the Arguments tab:
<br>In Program arguments add : hostname port RESuser RESpassword
<br>for instance localhost 9090 resAdmin resAdmin
<br>Then click on Run

5. Show result
<br>Right click on sample project > Refresh
<br>You now see a report.html file
<br>Right click on it > Open with > Web Browser

Details<a name="details"></a>
============

### Inside the Java Project (RES-content-report):

#### class:

This project has a single class JavaClient.java.

#### retrieve the list of all ruleApps
This is achieved using a call to rest API:
<br>http://HOST:PORT/res/apiauth/ruleapps

#### creation of xml
The output of the rest call is redirected to an xml file : report.xml

#### creation of the html report
Using a stylesheet stylesheet.xsl we create a well formated report.html file extracting the data we need

License Information<a name="license"></a>
====================
The files found in this project are licensed under the [Apache License 2.0](LICENSE).

# Notice
© Copyright IBM Corporation 2019.