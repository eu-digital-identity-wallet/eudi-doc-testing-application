#!bin/sh 
            echo "Waiting for Android Emulator..."
            adb wait-for-device
            adb devices
            while [ "$(adb shell getprop sys.boot_completed)" != "1" ]; do echo "Waiting for Android to fully boot..."; sleep 10; done
            echo "Android has booted!"
            echo "***************"
            echo "Checking cmd setting service availabilty..."
            if ! adb shell service list | grep -q 'settings'; then echo "Settings service is not yet available, waiting a bit longer..."; sleep 10; fi
            echo "Starting maven verify..."
            sleep 120 && mvn clean verify -Dcucumber.filter.tags='@automated and @ANDROID and @US_VP_TC_01'