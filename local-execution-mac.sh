#!/bin/bash
mvn clean verify -Dcucumber.filter.tags="(@manual and @ANDROID) or (@manual and @IOS)" $*
mvn serenity:aggregate
