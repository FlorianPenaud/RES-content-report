Table of Contents

1. [Introduction](#intro)
2. [Version(s) Supported](#versions)
3. [Usage Instructions](#instruction)
4. [Details](#details)
5. [License information](#license)

Introduction<a name="intro"></a>
============

This document provides an example on how to create a report of the RES content : ruleApps and rulesets
You can import this sample into an existing rule Designer.

Version(s) Supported<a name="versions"></a>
====================

IBM ODM 8.6 or greater

Usage Instructions<a name="instruction"></a>
===================

1. Import project
<p>Right click in package explorer
Select Import
General > Existing projects into Workspace
Select archive file then Browse to your sample archive

2. Start your Rule execution server

3. Run sample
Right click on RES_content_report project
Run As > Run configurations > new Java Application
On Main tab :
Project:RES_content_report
Main class:sample.JavaClient
On the Arguments tab:
In Program arguments add : hostname port RESuser RESpassword
for instance localhost 9090 resAdmin resAdmin
Then click on Run

5. Show result
Right click on sample project > Refresh
You now see a report.html file
Right click on it > Open with > Web Browser

Details<a name="details"></a>
============

### Inside the Java Project (RES-content-report):

#### class:

This project has a single class JavaClient.java.

#### retrieve the list of all ruleApps
This is achieved using a call to rest API:
http://<HOST>:<PORT>/res/apiauth/ruleapps

#### creation of xml
The output of the rest call is redirected to an xml file : report.xml

#### creation of the html report
Using a stylesheet stylesheet.xsl we create a well formated report.html file extracting the data we need

License Information<a name="license"></a>
====================
The files found in this project are licensed under the [Apache License 2.0](LICENSE).

# Notice
© Copyright IBM Corporation 2019.