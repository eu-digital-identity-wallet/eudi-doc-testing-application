#!/usr/bin/env bash
mvn clean verify -Dappium.hub=http://127.0.0.1:4723 -Dwebdriver.driver=appium -Dappium.platformName=Android -Dappium.deviceName="Pixel 6 API 33" -Dcucumber.filter.tags="@IOS and (not @WIP)"
mvn serenity:aggregate