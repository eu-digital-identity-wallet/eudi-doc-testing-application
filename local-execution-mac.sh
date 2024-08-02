#!/bin/bash
mvn clean verify -Dcucumber.filter.tags="(@IOS and @automated) or (@manual and @IOS)" $*
mvn serenity:aggregate