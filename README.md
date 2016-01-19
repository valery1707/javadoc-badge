[![Stories in Ready](https://badge.waffle.io/valery1707/javadoc-badge.png?label=ready&title=Ready)](https://waffle.io/valery1707/javadoc-badge)
[![Build Status](https://travis-ci.org/valery1707/javadoc-badge.svg)](https://travis-ci.org/valery1707/javadoc-badge)
[![Dependency Status](https://www.versioneye.com/user/projects/5680e5fdeb4f47003c000271/badge.svg?style=flat)](https://www.versioneye.com/user/projects/5680e5fdeb4f47003c000271)

Simple Javadoc badge that automatically detect current artifact version, deployed to [Maven Central](https://search.maven.org/) and create badge showing that version with [Shields.io](http://shields.io/).

##### API

URL: `http://javadoc-emblem.rhcloud.com/doc/${groupId}/${artifactId}/badge.${ext}`
* `groupId` and `artifactId` describe maven artifact information
* `ext` - supported extensions from [Shields.io](http://shields.io/#styles), eg `svg` or `png`

Also exists some optional params:
* `style` - supported styles from [Shields.io](http://shields.io/#styles)
* `color` - supported colors from [Shields.io](http://shields.io/#your-badge)
* `subject` - text for first part of badge (default: `javadoc`)
* `prefix` - prefix for version in badge
* `suffix` - suffix for version in badge

##### Examples

* Spring Core: [![Javadoc](http://javadoc-emblem.rhcloud.com/doc/org.springframework/spring-core/badge.svg)](http://www.javadoc.io/doc/org.springframework/spring-core) `![Javadoc](http://javadoc-emblem.rhcloud.com/doc/org.springframework/spring-core/badge.svg)`
* Scala Library: [![Javadoc](http://javadoc-emblem.rhcloud.com/doc/org.scala-lang/scala-library/badge.svg)](http://www.javadoc.io/doc/org.scala-lang/scala-library) `![Javadoc](http://javadoc-emblem.rhcloud.com/doc/org.scala-lang/scala-library/badge.svg)`
* Lamma: [![Javadoc](http://javadoc-emblem.rhcloud.com/doc/io.lamma/lamma_2.11/badge.svg)](http://www.javadoc.io/doc/io.lamma/lamma_2.11) `![Javadoc](http://javadoc-emblem.rhcloud.com/doc/io.lamma/lamma_2.11/badge.svg)`

##### Version providers

Actual version detected from [Javadoc.io](http://www.javadoc.io) and cached 2 hours.
