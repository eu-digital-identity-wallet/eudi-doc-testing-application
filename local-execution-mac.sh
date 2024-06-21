#!/bin/bash
mvn clean verify -Dcucumber.filter.tags="@WEB" $*
mvn serenity:aggregate