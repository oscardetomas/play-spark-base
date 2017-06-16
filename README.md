# Play Spark Base

Proyecto base para probar funcionalidades spark...

## Requirements

- SBT
- Scala
- Java 8

## Third part libraries

- https://github.com/sbt/sbt-assembly
- https://github.com/nscala-time/nscala-time

## Build

Assume sbt installed

 * Assembly:                sbt assembly

## Way of working using git

We are using [gitflow](http://danielkummer.github.io/git-flow-cheatsheet/) to manage this project.

The main branches are:

- master
- dev

From *dev* we create the different branches for the new features.

### Releases

Before the freeze state of the project we create a new branch called __relase\<tag\>__ where __tag__ means the version number of the project.

The *release* branch will be deleted after every release to production environment.
