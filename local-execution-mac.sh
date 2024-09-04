#!/bin/bash
mvn clean verify -Dcucumber.filter.tags="(@IOS and @automated) or (@IOS and @manual)" $*
mvn serenity:aggregate