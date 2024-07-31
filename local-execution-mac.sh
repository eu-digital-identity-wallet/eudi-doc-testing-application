#!/bin/bash
mvn clean verify -Dcucumber.filter.tags="@IOS and @automated" $*
mvn serenity:aggregate